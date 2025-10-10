package com.woniu.advice;


import com.woniu.annotation.SecuritySupport;
import com.woniu.handler.SecurityHandler;
import com.woniu.holder.ContextHolder;
import com.woniu.holder.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonInputMessage;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

/**
 * @author woniu
 * @describe: RequestBodyAdvice由于是对@RequestBody进行增强处理，拦截不到无@RequestBody的方法
 * @date 2024 2 19
 */
@Slf4j
@RestControllerAdvice
@ConditionalOnProperty(name = "encryption.advice", havingValue = "true")
public class RequestBodyDecryptAdvice implements RequestBodyAdvice {

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return methodParameter.hasMethodAnnotation(SecuritySupport.class);
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        log.info("进入【RequestBodyDecryptAdvice】beforeBodyRead的操作，方法：{}",parameter.getMethod());
        SecuritySupport securitySupport = parameter.getMethodAnnotation(SecuritySupport.class);
        assert securitySupport != null;
        String handler = securitySupport.securityHandler();
        ContextHolder.setCryptHolder(handler);
        String original = IOUtils.toString(inputMessage.getBody(), Charset.defaultCharset());
        //todo
        log.info("该流水已插入当前请求流水表");
        String plainText = original;
        if(StringUtils.isNotBlank(handler)){
            SecurityHandler securityHandler = SpringContextHolder.getBean(handler, SecurityHandler.class);
            plainText = securityHandler.decrypt(original);
        }
        return new MappingJacksonInputMessage(IOUtils.toInputStream(plainText, Charset.defaultCharset()), inputMessage.getHeaders());
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }
}
