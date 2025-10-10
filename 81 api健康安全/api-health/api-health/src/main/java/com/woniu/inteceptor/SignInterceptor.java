package com.woniu.inteceptor;


import com.woniu.appservice.AppSecretService;
import com.woniu.exception.CommonException;
import com.woniu.redis.RedisUtil;
import com.woniu.util.SignUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

/**
 * 如何保证API接口安全?接口签名可以试试
 */
public class SignInterceptor implements HandlerInterceptor {

    @Autowired
    private AppSecretService appSecretService;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        //appId验证
        final String appId = request.getHeader("appid");
        if (StringUtils.isEmpty(appId)) {
            throw new CommonException("appid不能为空");
        }
        String appSecret = appSecretService.getAppSecretByAppId(appId);
        if (StringUtils.isEmpty(appSecret)) {
            throw new CommonException("appid不合法");
        }
        //时间戳验证
        final String timestamp = request.getHeader("timestamp");
        if (StringUtils.isEmpty(timestamp)) {
            throw new CommonException("timestamp不能为空");
        }
        //大于5分钟，非法请求
        long diff = System.currentTimeMillis() - Long.parseLong(timestamp);
        if (Math.abs(diff) > 1000 * 60 * 5) {
            throw new CommonException("timestamp已过期");
        }
        //临时流水号，防止重复提交
        final String nonce = request.getHeader("nonce");
        if (StringUtils.isEmpty(nonce)) {
            throw new CommonException("nonce不能为空");
        }
        //验证签名
        final String signature = request.getHeader("signature");
        if (StringUtils.isEmpty(nonce)) {
            throw new CommonException("signature不能为空");
        }
        final String method = request.getMethod();
        final String url = request.getRequestURI();
        final String body = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
        String signResult = SignUtil.getSignature(method, url, body, timestamp, nonce, appSecret);
        if (!signature.equals(signResult)) {
            throw new CommonException("签名验证失败");
        }
        //检查是否重复请求
        String key = appId + "_" + timestamp + "_" + nonce;
        if (redisUtil.exist(key)) {
            throw new CommonException("当前请求正在处理，请不要重复提交");
        }
        //设置5分钟
        redisUtil.save(key, signResult, 5 * 60);
        request.setAttribute("reidsKey", key);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        //请求处理完毕之后，移除缓存
        String value = (String) request.getAttribute("reidsKey");
        if (!StringUtils.isEmpty(value)) {
            redisUtil.remove(value);
        }
    }

}