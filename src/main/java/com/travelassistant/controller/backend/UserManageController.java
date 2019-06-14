package com.travelassistant.controller.backend;

import com.travelassistant.common.Const;
import com.travelassistant.common.ServerResponse;
import com.travelassistant.common.UserWithToken;
import com.travelassistant.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/manage/user")
public class UserManageController {

    @Autowired
    private IUserService iUserService;

    @RequestMapping(value="login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<UserWithToken> login(String phone, String password, HttpSession session){
        ServerResponse<UserWithToken> response = iUserService.login(phone,password);
        if(response.isSuccess()){
            UserWithToken user = response.getData();
            if(user.getRole() == Const.Role.ROLE_ADMIN){
                //说明登录的是管理员
                session.setAttribute(Const.CURRENT_USER,user);
                return response;
            }else{
                return ServerResponse.createByErrorMsg("不是管理员,无法登录");
            }
        }
        return response;
    }

}
