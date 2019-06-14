package com.travelassistant.common;

import java.util.Date;

public class UserWithToken {
    private Integer id;

    private String phone;

    private String username;

    private String password;

    private Integer role;

    private Date createTime;

    private Date updateTime;

    private String token;

    public UserWithToken(Integer id, String phone, String username, String password, Integer role, Date createTime, Date updateTime, String token) {
        this.id = id;
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.role = role;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.token = token;
    }

    public UserWithToken() {
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
