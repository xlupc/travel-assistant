package com.travelassistant.service.impl;

import com.travelassistant.common.Const;
import com.travelassistant.common.ServerResponse;
import com.travelassistant.common.TokenCache;
import com.travelassistant.dao.UserMapper;
import com.travelassistant.pojo.User;
import com.travelassistant.service.IUserService;
import com.travelassistant.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse<User> login(String phone, String password) {
        int resultCount = userMapper.checkPhone(phone);
        if(resultCount == 0 ){
            return ServerResponse.createByErrorMsg("手机号尚未注册");
        }

        String md5Password = MD5Util.MD5EncodeUtf8(password);
        User user  = userMapper.selectUser(phone,md5Password);
        System.out.println(md5Password);
        if(user == null){
            return ServerResponse.createByErrorMsg("密码错误");
        }

        user.setPassword(org.apache.commons.lang3.StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登录成功",user);
    }

    public ServerResponse<String> register(User user){
        System.out.println(user.getPhone());
        ServerResponse validResponse = this.checkValid(user.getPhone(), Const.PHONE);
        if(!validResponse.isSuccess()){
            return validResponse;
        }

        //MD5加密
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        int resultCount = userMapper.insert(user);
        if(resultCount == 0){
            return ServerResponse.createByErrorMsg("注册失败");
        }
        return ServerResponse.createBySuccessMsg("注册成功");
    }

    public ServerResponse<String> checkValid(String str,String type){
        if(org.apache.commons.lang3.StringUtils.isNotBlank(type)){
            //开始校验
            if(Const.USERNAME.equals(type)){
                int resultCount = userMapper.checkUsername(str);
                if(resultCount > 0 ){
                    return ServerResponse.createByErrorMsg("用户名已存在");
                }
            }
            if(Const.PHONE.equals(type)){
                int resultCount = userMapper.checkPhone(str);
                if(resultCount > 0 ){
                    return ServerResponse.createByErrorMsg("手机号已注册");
                }
            }
        }else{
            return ServerResponse.createByErrorMsg("参数错误");
        }
        return ServerResponse.createBySuccessMsg("校验成功");
    }

    public ServerResponse<String> forgetResetPassword(String phone,String passwordNew,String forgetToken){
//        if(org.apache.commons.lang3.StringUtils.isBlank(forgetToken)){
//            return ServerResponse.createByErrorMsg("参数错误,token需要传递");
//        }
        ServerResponse validResponse = this.checkValid(phone,Const.PHONE);
        if(validResponse.isSuccess()){
            //用户不存在
            return ServerResponse.createByErrorMsg("用户不存在");
        }
//        String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX + phone);
//        if(org.apache.commons.lang3.StringUtils.isBlank(token)){
//            return ServerResponse.createByErrorMsg("token无效或者过期");
//        }

//        if(org.apache.commons.lang3.StringUtils.equals(forgetToken,token)){
//            String md5Password  = MD5Util.MD5EncodeUtf8(passwordNew);
//            int rowCount = userMapper.updatePasswordByPhone(phone,md5Password);
//
//            if(rowCount > 0){
//                return ServerResponse.createBySuccessMsg("修改密码成功");
//            }
//        }else{
//            return ServerResponse.createByErrorMsg("token错误,请重新获取重置密码的token");
//        }
        String md5Password  = MD5Util.MD5EncodeUtf8(passwordNew);
        int rowCount = userMapper.updatePasswordByPhone(phone,md5Password);

        if(rowCount > 0){
            return ServerResponse.createBySuccessMsg("修改密码成功");
        }
        return ServerResponse.createByErrorMsg("修改密码失败");
    }

    public ServerResponse<String> resetPassword(String passwordOld,String passwordNew,User user){
        //防止横向越权,要校验一下这个用户的旧密码,一定要指定是这个用户.因为我们会查询一个count(1),如果不指定id,那么结果就是true啦count>0;
        int resultCount = userMapper.checkPassword(MD5Util.MD5EncodeUtf8(passwordOld),user.getId());
        if(resultCount == 0){
            return ServerResponse.createByErrorMsg("旧密码错误");
        }

        user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if(updateCount > 0){
            return ServerResponse.createBySuccessMsg("密码更新成功");
        }
        return ServerResponse.createByErrorMsg("密码更新失败");
    }

    public ServerResponse<User> updateInformation(User user){
        //phone是不能被更新的
        int resultCount = userMapper.checkUsername(user.getUsername());
        if(resultCount > 0){
            return ServerResponse.createByErrorMsg("用户名已存在");
        }
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setUsername(user.getUsername());

        int updateCount = userMapper.updateByPrimaryKeySelective(updateUser);
        if(updateCount > 0){
            return ServerResponse.createBySuccess("更新个人信息成功",updateUser);
        }
        return ServerResponse.createByErrorMsg("更新个人信息失败");
    }

    public ServerResponse<User> getInformation(Integer userId){
        User user = userMapper.selectByPrimaryKey(userId);
        if(user == null){
            return ServerResponse.createByErrorMsg("找不到当前用户");
        }
        user.setPassword(org.apache.commons.lang3.StringUtils.EMPTY);
        return ServerResponse.createBySuccess(user);

    }
}
