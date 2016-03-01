package com.diyiliu.web.entity;

import com.diyiliu.support.annotation.TableColumn;
import com.diyiliu.support.annotation.TableField;
import com.diyiliu.web.entity.base.BaseEntity;
import org.springframework.stereotype.Component;

/**
 * Description: User
 * Author: DIYILIU
 * Update: 2016-02-19 14:05
 */
@Component
@TableField(tableName = "USER", primaryKey = "ID")
public class User extends BaseEntity {

    @TableColumn(columnName = "U_ID")
    private Long id;
    @TableColumn(columnName = "U_USERNAME")
    private String username;
    @TableColumn(columnName = "U_PASSWORD")
    private String password;
    @TableColumn(columnName = "U_SALT")
    private String salt;
    @TableColumn(columnName = "U_LOCKED")
    private Boolean locked = Boolean.FALSE;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public String getCredentialsSalt() {
        return username + salt;
    }
}
