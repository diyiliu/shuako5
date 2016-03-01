package com.diyiliu.web.service.impl;

import com.diyiliu.support.shiro.helper.PasswordHelper;
import com.diyiliu.web.dao.UserDao;
import com.diyiliu.web.entity.User;
import com.diyiliu.web.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Description: UserServiceImpl
 * Author: DIYILIU
 * Update: 2016-02-22 17:19
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Resource
    private PasswordHelper passwordHelper;

    @Override
    public void register(User user) {

        passwordHelper.encryptPassword(user);
        userDao.insertEntity(user);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }
}
