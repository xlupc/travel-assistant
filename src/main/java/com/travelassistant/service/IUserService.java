package com.travelassistant.service;

import com.travelassistant.common.ServerResponse;
import com.travelassistant.pojo.BrowsingHistory;
import com.travelassistant.pojo.User;
import com.travelassistant.common.UserWithToken;

import java.util.List;

public interface IUserService {
    ServerResponse<UserWithToken> login(String username, String password);

    ServerResponse<String> register(User user);

    ServerResponse<String> checkValid(String str,String type);

    ServerResponse<String> forgetResetPassword(String phone,String passwordNew,String forgetToken);

    ServerResponse<String> resetPassword(String passwordOld,String passwordNew,User user);

    ServerResponse<User> updateInformation(User user, String oldName);

    ServerResponse<User> getInformation(Integer userId);

    ServerResponse<String> insertBrowseRecord(BrowsingHistory browsingHistory);

    ServerResponse<List<BrowsingHistory>> selectBrowseRecord(Integer userId);

    ServerResponse<String> deleteBrowseRecord(Integer userId);
}
