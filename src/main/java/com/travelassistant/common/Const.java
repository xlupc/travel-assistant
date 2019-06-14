package com.travelassistant.common;

public class Const {
    public static final String CURRENT_USER = "current_user";
    public static final String USERNAME = "username";
    public static final String PHONE = "phone";
    public static final String CURRENT_USER_TOKEN = "current_user_token";

    public interface Role{
        int ROLE_CUSTOMER = 0; //普通用户
        int ROLE_ADMIN = 1;//管理员
    }
}
