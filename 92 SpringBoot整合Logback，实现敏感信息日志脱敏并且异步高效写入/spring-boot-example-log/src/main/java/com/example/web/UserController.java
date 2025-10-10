package com.example.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

//

@RestController
public class UserController {

    // 获取日志记录器
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * SpringBoot整合Logback，实现敏感信息日志脱敏并且异步高效写入
     * @param idCard 用户身份证号码
     * @param phoneNumber 用户手机号码
     * @return 响应字符串
     */
    @GetMapping("/user")
    public String getUserInfo(@RequestParam String idCard, @RequestParam String phoneNumber) {
        // 记录用户信息到日志
        logger.info("User Info - ID Card: {}, Phone Number: {}", idCard, phoneNumber);
        return"User info logged";
    }
}