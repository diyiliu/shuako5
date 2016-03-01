package com.diyiliu.support.plugin;

import com.diyiliu.support.other.Pagination;
import com.diyiliu.support.other.PaginationHelper;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Description: PaginationPlugin
 * Author: DIYILIU
 * Update: 2015-11-13 14:32
 */

@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})})
public class PaginationPlugin implements Interceptor {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String pageSqlId = "pageSqlId"; // mybaits的数据库xml映射文件中需要拦截的ID(正则匹配)
    private String dialect = ""; // 数据库方言

    private final static ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
    private final static ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
    private final static ReflectorFactory DEFAULT_REFLECTOR_FACTORY = new DefaultReflectorFactory();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaStatementHandler = MetaObject.forObject(statementHandler, DEFAULT_OBJECT_FACTORY,
                DEFAULT_OBJECT_WRAPPER_FACTORY, DEFAULT_REFLECTOR_FACTORY);

        // 分离代理对象链(由于目标类可能被多个拦截器拦截，从而形成多次代理，通过下面的两次循环可以分离出最原始的的目标类)
        while (metaStatementHandler.hasGetter("h")) {
            Object object = metaStatementHandler.getValue("h");

            metaStatementHandler = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY,
                    DEFAULT_OBJECT_WRAPPER_FACTORY, DEFAULT_REFLECTOR_FACTORY);
        }
        // 分离最后一个代理对象的目标类
        while (metaStatementHandler.hasGetter("target")) {
            Object object = metaStatementHandler.getValue("target");
            metaStatementHandler = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY,
                    DEFAULT_OBJECT_WRAPPER_FACTORY, DEFAULT_REFLECTOR_FACTORY);
        }

        MappedStatement mappedStatement = (MappedStatement)
                metaStatementHandler.getValue("delegate.mappedStatement");

        DataSource dataSource = (DataSource)
                metaStatementHandler.getValue("delegate.configuration.environment.dataSource");

        Connection connection = dataSource.getConnection();

        String url = connection.getMetaData().getURL();

        dialect = fromJdbcUrl(url);

        Pagination pagination = PaginationHelper.getPage();

        // 只重写需要分页的sql语句。通过MappedStatement的ID匹配，默认重写以Page结尾的MappedStatement的sql
        if (mappedStatement.getId().matches(pageSqlId) &&
                pagination != null && dialect != null) {

            BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
            String sql = boundSql.getSql();
            // 设置参数
            setPageParameter(sql, connection, mappedStatement, boundSql, pagination);
            // 分页sql
            sql = buidPageSql(sql, pagination.getCurrentPage(), pagination.getPageSize());
            // 重写sql
            metaStatementHandler.setValue("delegate.boundSql.sql", sql);
        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        // 当目标类是StatementHandler类型时，才包装目标类，否者直接返回目标本身,减少目标被代理的次数
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {
        this.pageSqlId = properties.getProperty(pageSqlId);
    }

    public String buidPageSql(String sql, int currentPage, int pageSize) {

        if (dialect.equals("mysql")) {

            return buildPageSqlForMysql(sql, currentPage, pageSize).toString();
        }

        if (dialect.equals("oracle")) {

            return buildPageSqlForOracle(sql, currentPage, pageSize).toString();
        }

        return null;
    }


    public StringBuilder buildPageSqlForMysql(String sql, int currentPage, int pageSize) {
        StringBuilder pageSql = new StringBuilder(sql);
        pageSql.append(" limit " + (currentPage - 1) * pageSize + "," + pageSize);
        return pageSql;
    }

    public StringBuilder buildPageSqlForOracle(String sql, int currentPage, int pageSize) {
        StringBuilder pageSql = new StringBuilder(sql);
        pageSql.append("select * from ( select temp.*, rownum row_id from ( ");
        pageSql.append(sql);
        pageSql.append(" ) temp where rownum <= ").append(currentPage * pageSize);
        pageSql.append(") where row_id > ").append((currentPage - 1) * pageSize);
        return pageSql;
    }

    public String fromJdbcUrl(String jdbcUrl) {
        String[] dialects = new String[]{"mysql", "oracle"};
        for (String dialect : dialects) {
            if (jdbcUrl.indexOf(":" + dialect + ":") != -1) {
                return dialect;
            }
        }
        return null;
    }

    /**
     * 从数据库里查询总的记录数并计算总页数，回写进分页参数
     *
     * @param sql
     * @param connection
     * @param mappedStatement
     * @param boundSql
     * @param page
     */
    private void setPageParameter(String sql, Connection connection, MappedStatement mappedStatement,
                                  BoundSql boundSql, Pagination page) {
        // 记录总记录数
        String countSql = "select count(0) from (" + sql + ") as total";

        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.prepareStatement(countSql);
            BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql,
                    boundSql.getParameterMappings(), boundSql.getParameterObject());
            setParameters(statement, mappedStatement, countBS, boundSql.getParameterObject());
            rs = statement.executeQuery();
            int totalCount = 0;
            if (rs.next()) {
                totalCount =  Long.valueOf(rs.getLong(1)).intValue();
            }
            page.setTotalCount(totalCount);
        } catch (SQLException e) {
            logger.error("SQLException", e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                logger.error("SQLException", e);
            }
        }
    }

    /**
     * 对SQL参数(?)设值
     *
     * @param preparedStatement
     * @param mappedStatement
     * @param boundSql
     * @param parameterObject
     * @throws SQLException
     */
    private void setParameters(PreparedStatement preparedStatement, MappedStatement mappedStatement, BoundSql boundSql,
                               Object parameterObject) throws SQLException {
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
        parameterHandler.setParameters(preparedStatement);
    }

}
