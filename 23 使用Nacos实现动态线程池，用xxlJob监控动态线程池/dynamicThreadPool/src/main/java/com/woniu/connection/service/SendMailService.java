package com.woniu.connection.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadPoolExecutor;

@Service
public class SendMailService {

//    @Autowired
    private JavaMailSender javaMailSender;

    public boolean sendMailToWarn(ThreadPoolExecutor threadPoolExecutor) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo("xxx@qq.com");
        simpleMailMessage.setFrom("xxx@qq.com");
        simpleMailMessage.setSubject("线程池情况汇报");
        String s = "CorePoolSize=" + threadPoolExecutor.getCorePoolSize() + "   " +
                "LargestPoolSize=" + threadPoolExecutor.getLargestPoolSize() + "   " +
                "MaximumPoolSize=" + threadPoolExecutor.getMaximumPoolSize();
        simpleMailMessage.setText(s);
        javaMailSender.send(simpleMailMessage);
        return true;
    }
}
