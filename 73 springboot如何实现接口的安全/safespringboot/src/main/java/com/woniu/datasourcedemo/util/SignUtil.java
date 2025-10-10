package com.woniu.datasourcedemo.util;

import com.alibaba.fastjson.JSON;
import com.woniu.datasourcedemo.model.RequestHeader;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.SortedMap;

public class SignUtil {
    /**
     * 验证签名
     * 验证算法：把timestamp + JsonUtil.object2Json(SortedMap)合成字符串，然后MD5
     */
    @SneakyThrows
    public static boolean verifySign(SortedMap<String, String> map, RequestHeader requestHeader) {
        String params = requestHeader.getNonce() + requestHeader.getTimestamp() + JSON.toJSONString(map);
        return verifySign(params, requestHeader);
    }

    /**
     * 验证签名
     */
    public static boolean verifySign(String params, RequestHeader requestHeader) {
//        log.debug("客户端签名: {}", requestHeader.getSign());
        System.out.println(requestHeader.getSign());
        if (StringUtils.isEmpty(params)) {
            return false;
        }
//        log.info("客户端上传内容: {}", params);
        System.out.println(params);
        String paramsSign = DigestUtils.md5DigestAsHex(params.getBytes()).toUpperCase();
//        log.info("客户端上传内容加密后的签名结果: {}", paramsSign);
        System.out.println(paramsSign);
        return requestHeader.getSign().equals(paramsSign);
    }
}
