package com.diyiliu.web.service;

import com.diyiliu.web.entity.User;

/**
 * Description: UserService
 * Author: DIYILIU
 * Update: 2016-02-19 17:17
 */
public interface UserService {

    public void register(User user);

    public User findByUsername(String username);
}
