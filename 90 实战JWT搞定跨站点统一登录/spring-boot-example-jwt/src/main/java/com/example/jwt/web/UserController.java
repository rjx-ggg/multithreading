package com.example.jwt.web;

import com.alibaba.fastjson.JSONObject;
import com.example.jwt.config.interceptor.JwtIgnore;
import com.example.jwt.entity.LoginUser;
import com.example.jwt.entity.UserDTO;
import com.example.jwt.entity.UserVO;
import com.example.jwt.util.JwtTokenUtil;
import com.example.jwt.util.WebContextUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;


@RestController
public class UserController {

    /**
     * SpringBoot整合JWT搞定跨站点统一登录！
     */
    @JwtIgnore
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public UserVO login(@RequestBody UserDTO userDTO, HttpServletResponse response){
        //...参数合法性验证
        if(!("woniuxgg".equals(userDTO.getUserNo()) && "123456".equals(userDTO.getUserPwd()))){
            throw new RuntimeException("用户名或者密码不正确!");
        }
        // todo...此处为了演示，用户写死，可以改成从数据库获取用户信息，进行用户、密码验证
        // 将用户信息加密成token，并返回给客户端
        LoginUser loginUser = new LoginUser();
        loginUser.setUserId("1");
        loginUser.setUserNo("woniuxgg");
        loginUser.setUserName("woniu");
        String token = JwtTokenUtil.createToken(JSONObject.toJSONString(loginUser));
        response.setHeader(JwtTokenUtil.AUTH_HEADER_KEY, token);
        // 定义返回结果
        UserVO result = new UserVO();
        result.setUserNo("woniuxgg");
        result.setUserName("woniu");
        return result;
    }


    /**
     * 登陆成功之后，测试token信息
     */
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public LoginUser add(){
        // 获取登陆的用户信息
        LoginUser loginUser = WebContextUtil.getUserToken();
        System.out.println(loginUser.toString());
        return loginUser;
    }
}
