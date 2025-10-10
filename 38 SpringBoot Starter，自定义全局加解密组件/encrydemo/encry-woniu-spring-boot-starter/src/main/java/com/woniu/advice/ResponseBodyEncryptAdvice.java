package com.woniu.advice;

import com.woniu.annotation.SecuritySupport;

import com.woniu.handler.SecurityHandler;
import com.woniu.holder.ContextHolder;
import com.woniu.holder.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author woniu
 * @date 2024 2 19
 */
@Slf4j
@RestControllerAdvice
@ConditionalOnProperty(name = "encryption.advice", havingValue = "true")
public class ResponseBodyEncryptAdvice  implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return returnType.hasMethodAnnotation(SecuritySupport.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        log.info("进入【ResponseBodyEncryptAdvice】beforeBodyWrite的操作，方法：{}",returnType.getMethod());
        String cryptHandler = ContextHolder.getCryptHandler();
        SecurityHandler securityHandler = SpringContextHolder.getBean(cryptHandler, SecurityHandler.class);
        assert body != null;
        ContextHolder.clearContext();
        return securityHandler.encrypt(body.toString());
    }
}