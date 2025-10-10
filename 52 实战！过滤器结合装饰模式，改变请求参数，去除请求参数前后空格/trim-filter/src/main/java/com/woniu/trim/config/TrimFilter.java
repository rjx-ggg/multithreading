package com.woniu.trim.config;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 *  自定义过滤器  通过继承OncePerRequestFilter实现每次请求该过滤器只被执行一次
 * @author 蜗牛
 * @date 2024/03/11 下午4:11
 */
public class TrimFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException {
        //自定义TrimRequestWrapper，在这里实现参数去空
        TrimRequestWrapper requestWrapper = new TrimRequestWrapper(httpServletRequest);
        filterChain.doFilter(requestWrapper, httpServletResponse);
    }
}
