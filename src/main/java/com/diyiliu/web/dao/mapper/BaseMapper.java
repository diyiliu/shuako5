package com.diyiliu.web.dao.mapper;

/**
 * Description: BaseMapper
 * Author: DIYILIU
 * Update: 2016-02-19 10:53
 */
public interface BaseMapper {

    /**
     * 保存数据
     *
     * @param entity
     */
    public void insertEntity(Object entity);

    /**
     * 删除数据
     *
     * @param entity
     */
    public void deleteEntity(Object entity);

    /**
     * 修改数据
     *
     * @param entity
     */
    public void updateEntity(Object entity);
}
