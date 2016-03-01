package com.diyiliu.web.dao;

import com.diyiliu.web.dao.mapper.BaseMapper;
import com.diyiliu.web.entity.User;

import java.util.List;

/**
 * Description: UserDao
 * Author: DIYILIU
 * Update: 2016-02-19 10:55
 */
public interface UserDao extends BaseMapper {

    public User findByUsername(String username);

    public List findUserByPage(String name);
}
