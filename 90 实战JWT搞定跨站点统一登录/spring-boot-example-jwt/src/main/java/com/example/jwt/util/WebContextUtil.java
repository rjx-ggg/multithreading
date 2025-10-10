package com.example.jwt.util;

import com.alibaba.fastjson.JSONObject;
import com.example.jwt.entity.LoginUser;


public class WebContextUtil {

    //本地线程缓存token
    private static ThreadLocal<String> local = new ThreadLocal<>();

    /**
     * 设置token信息
     * @param content
     */
    public static void setUserToken(String content){
        removeUserToken();
        local.set(content);
    }

    /**
     * 获取token信息
     * @return
     */
    public static LoginUser getUserToken(){
        if(local.get() != null){
            LoginUser loginUser = JSONObject.parseObject(local.get() , LoginUser.class);
            return loginUser;
        }
        return null;
    }

    /**
     * 移除token信息
     * @return
     */
    public static void removeUserToken(){
        if(local.get() != null){
            local.remove();
        }
    }
}
