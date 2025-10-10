package com.woniu.connection.web;

import com.woniu.connection.constant.AuthServerConstant;
import com.woniu.connection.entity.User;
import com.woniu.connection.interceptor.LoginUserInterceptor;
import com.woniu.connection.service.AccountService;
import com.woniu.connection.service.DealService;
import com.woniu.connection.service.LoginService;
import com.woniu.connection.utils.R;
import com.woniu.connection.utils.ThreadSafeFormatterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 公众号 ：woniuxgg ,回复 ：”源码“
 */
@RestController
@RequestMapping("/app")
public class WebController {


    @Autowired
    private AccountService accountService;

    /**
     * ThreadLocal实战 代码演示 工作中是怎么使用的
     * demo1 每个线程绑定一个连接 实现线程内隔离 控制事务
     * @return
     */
    @GetMapping("transfer")
    public R transfer() {
        // 模拟数据 : woniu 给 lili 转账 100
        String outUser = "woniu";
        String inUser = "lili";
        int money = 100;

        boolean result = accountService.transfer(outUser, inUser, money);

        if (!result) {
            System.out.println("转账失败!");
        } else {
            System.out.println("转账成功!");
        }
        return R.ok();
    }


    @Autowired
    private LoginService loginService;

    /**
     * demo2 每个线程内需要保存全局变量（例如在拦截器中获取用户信息），可以让不同方法直接使用，避免参数传递的麻烦
     *
     * @return
     */
    @GetMapping("login")
    public R login(@RequestParam String userName, @RequestParam String passWord, HttpServletRequest httpServletRequest) {

        //用户登录 返回实体类
        User userLogin = loginService.login(userName,passWord);

        //放入session中
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute(AuthServerConstant.LOGIN_USER, userLogin);

        return R.ok();
    }


    /**
     * 获得用户信息  localhost:8080/app/getUser
     * @return
     */
    @GetMapping("getUser")
    public R getUser() {

        User user = LoginUserInterceptor.loginUser.get();
        return R.ok().put("user",user);
    }


    @Autowired
    private DealService dealService;

    /**
     * demo3 在方法调用的过程中 用来代替参数传值！
     * @return
     */
    @GetMapping("dealTransfer")
    public R dealTransfer() {

        dealService.dealTransfer();
        return R.ok();
    }


    /**
     * demo4 每个线程需要一个独享对象（通常是工具类，典型需要使用的类有SimpleDateFormat）
     * 每个Thread内有自己的实例副本，不共享
     * @return
     */
    @GetMapping("dealDate")
    public R dealDate() {

        for (int i = 0; i < 30; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SimpleDateFormat simpleDateFormat = ThreadSafeFormatterUtil.dateFormatThreadLocal.get();
                    String date = simpleDateFormat.format(new Date());
                    System.out.println(date);
                }
            }).start();
        }
      return R.ok();
    }


}
