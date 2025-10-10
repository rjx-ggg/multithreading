package com.springboot.sample.service.impl;

import com.springboot.sample.bean.User;
import com.springboot.sample.service.UserService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.*;


@Service
@Slf4j
public class UserWrapBatchService {
    @Resource
    private UserService userService;

    @Autowired
    private ExecutorService executorService;

    /**
     * 最大任务数
     **/
    public static int MAX_TASK_NUM = 100;

    /**
     * 并行执行得到结果
     * @param userId
     * @return
     */
    public User queryUserAsync(Long userId) {
        User user = new User();
        CompletableFuture<Void> nameFuture = CompletableFuture.runAsync(() -> {
            // 根据userId 查询用户姓名
            String name = userService.queryUserByUserId(userId);
            user.setName(name);
        }, executorService);

        CompletableFuture<Void> integralFuture = CompletableFuture.runAsync(() -> {
            // 根据userId 查积分系统
            Integer integral = userService.queryIntegralByUserId(userId);
            user.setIntegral(integral);
        }, executorService);

        CompletableFuture<Void> couponFuture = CompletableFuture.runAsync(() -> {
            // 根据userId 查coupon 优惠券系统
            Integer coupon = userService.queryCouponByUserId(userId);
            user.setCoupon(coupon);
        }, executorService);
        try {
            CompletableFuture.allOf(nameFuture, integralFuture, couponFuture).get(5, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 请求类,code为查询的共同特征,例如查询商品,通过不同id的来区分
     * CompletableFuture将处理结果返回
     */
    @Data
    public class Request {
        // 请求id 唯一
        String requestId;
        // 参数
        Long userId;
        //TODO Java 8 的 CompletableFuture 并没有 timeout 机制
        CompletableFuture<User> completableFuture;
    }

    private final Queue<Request> queue = new LinkedBlockingQueue();

    @PostConstruct
    public void init() {
        //定时任务线程池,创建一个支持定时、周期性或延时任务的限定线程数目(这里传入的是1)的线程池
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

        scheduledExecutorService.scheduleAtFixedRate(() -> {
            //如果队列没数据,表示这段时间没有请求,直接返回
            if (queue.isEmpty()) {
                return;
            }
            List<Request> list = new ArrayList<>();
            System.out.println("合并了 [" + queue.size() + "] 个请求");
            //将队列的请求消费到一个集合保存
            for (int i = 0; i < queue.size(); i++) {
                // 后面的SQL语句是有长度限制的，所以还要做限制每次批量的数量,超过最大任务数，等下次执行
                if (i < MAX_TASK_NUM) {
                    list.add(queue.poll());
                }
            }
            //拿到我们需要去数据库查询的特征,保存为集合
            List<Request> userReqs = new ArrayList<>();
            for (Request request : list) {
                userReqs.add(request);
            }
            //将参数传入service处理, 这里是本地服务，也可以把userService 看成RPC之类的远程调用
            Map<String, User> response = userService.queryUserByIdBatch(userReqs);
            //将处理结果返回各自的请求
            for (Request request : list) {
                User result = response.get(request.requestId);

                //completableFuture.complete方法完成赋值,这一步执行完毕,下面future.get()阻塞的请求可以继续执行了
                request.completableFuture.complete(result);
            }
        }, 100, 10, TimeUnit.MILLISECONDS);
        //scheduleAtFixedRate是周期性执行 schedule是延迟执行 initialDelay是初始延迟 period是周期间隔 后面是单位
    }

    public User queryUser(Long userId) {
        Request request = new Request();
        // 这里用UUID做请求id 用作线程号
        request.requestId = UUID.randomUUID().toString().replace("-", "");
        request.userId = userId;
        CompletableFuture<User> future = new CompletableFuture<>();
        request.completableFuture = future;
        //将对象传入队列
        queue.offer(request);
        //如果这时候没完成赋值,那么就会阻塞,直到能够拿到值
        try {
            return future.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}