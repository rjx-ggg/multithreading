package com.woniu.dynamicsql.intecepter;


import com.mysql.cj.util.StringUtils;
import com.woniu.dynamicsql.util.TenantRequestContext;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userId = request.getHeader("tenant_id");
        if (!StringUtils.isNullOrEmpty(userId)) {
            TenantRequestContext.setTenantLocal(userId);
            System.out.printf("当前租户ID:" + userId);
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}