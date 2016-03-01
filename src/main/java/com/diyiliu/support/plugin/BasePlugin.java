package com.diyiliu.support.plugin;

import com.diyiliu.support.other.Constant;
import com.diyiliu.support.annotation.TableField;
import com.diyiliu.web.entity.base.BaseEntity;
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

import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

/**
 * Description: BasePlugin
 * Author: DIYILIU
 * Update: 2016-02-19 11:22
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})})
public class BasePlugin  implements Interceptor {

    private String baseSqlId = "baseSqlId";

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

        String sqlId = mappedStatement.getId().substring(mappedStatement.getId().lastIndexOf(".") + 1);

        if (sqlId.matches(baseSqlId)){

            BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
            Object parameterObject = boundSql.getParameterObject();

            if (parameterObject instanceof BaseEntity){

                metaStatementHandler.setValue("delegate.boundSql.sql", joinSql(sqlId, (BaseEntity) parameterObject));
            }
        }

        return invocation.proceed();
    }

    public String joinSql(String sqlId, BaseEntity entity){


        TableField tableField = entity.getClass().getAnnotation(TableField.class);
        String table = tableField.tableName();
        String key = tableField.primaryKey();

        HashMap map = entity.toHashMap();
        String[] fields = new String[map.size()];
        Object[] values = new Object[map.size()];

        int i = 0;
        for (Iterator iter = map.keySet().iterator(); iter.hasNext();){
            String column = (String) iter.next();
            fields[i] = column;
            values[i] = map.get(column);
            i++;
        }

        if (sqlId.equals(Constant.Crud.INSERT)){

            StringBuilder strb = new StringBuilder("INSERT INTO ");
            strb.append(table).append("(");
            for (String field: fields){
                strb.append(field).append(",");
            }
            strb = strb.replace(strb.length() - 1, strb.length(), ")");
            strb.append("VALUES(");
            for (Object value: values){
                strb.append(format(value)).append(",");
            }
            strb = strb.replace(strb.length() - 1, strb.length(), ")");

            return strb.toString();
        }

        return  null;
    }

    public static Object format(Object value){

        if (value instanceof String){

            value = "'" + value + "'";
        }

        return value;
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
        this.baseSqlId = properties.getProperty(baseSqlId);
    }
}
