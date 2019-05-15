package com.travelassistant.service;

import com.travelassistant.common.ServerResponse;
import com.travelassistant.pojo.User;
import com.travelassistant.common.UserWithToken;

public interface IUserService {
    ServerResponse<UserWithToken> login(String username, String password);

    ServerResponse<String> register(User user);

    ServerResponse<String> checkValid(String str,String type);

    ServerResponse<String> forgetResetPassword(String phone,String passwordNew,String forgetToken);

    ServerResponse<String> resetPassword(String passwordOld,String passwordNew,User user);

    ServerResponse<User> updateInformation(User user);

    ServerResponse<User> getInformation(Integer userId);
}
