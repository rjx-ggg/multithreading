package com.woniu.demo.concurrent;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.*;

/**
 * 一个页面有多达10个左右的一个用户行为数据,
 * 比如:点赞数,发布文章数,点赞数,消息数,关注数,收藏数,粉丝数,卡券数,红包数等等！
 */
@Slf4j
@Component
public class MyFutureTask {
    @Resource
    UserService userService;

    /**
     * 核心线程 8 最大线程 20 保活时间30s 存储队列 10 有守护线程 拒绝策略:将超负荷任务回退到调用者
     */
    private static ExecutorService executor = new ThreadPoolExecutor(20, 50, 30L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(100), Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());

    public UserBehaviorDataDTO getUserAggregatedResult(final Long userId) {
        System.out.println("MyFutureTask的线程:" + Thread.currentThread());
        try {
            // 发布文章数
            CompletableFuture<Long> articleCountFT = CompletableFuture.supplyAsync(() -> userService.countArticleCountByUserId(userId), executor);
            // 点赞数
            CompletableFuture<Long> LikeCountFT = CompletableFuture.supplyAsync(() -> userService.countLikeCountByUserId(userId), executor);
            // 粉丝数
            CompletableFuture<Long> fansCountFT = CompletableFuture.supplyAsync(() -> userService.countFansCountByUserId(userId), executor);
            //消息数
            CompletableFuture<Long> msgCountFT = CompletableFuture.supplyAsync(() -> userService.countMsgCountByUserId(userId), executor);
            //收藏数
            CompletableFuture<Long> collectCountFT = CompletableFuture.supplyAsync(() -> userService.countCollectCountByUserId(userId), executor);
            //关注数
            CompletableFuture<Long> followCountFT = CompletableFuture.supplyAsync(() -> userService.countFollowCountByUserId(userId), executor);
            //红包数
            CompletableFuture<Long> redBagCountFT = CompletableFuture.supplyAsync(() -> userService.countRedBagCountByUserId(userId), executor);
            //卡券数
            CompletableFuture<Long> couponCountFT = CompletableFuture.supplyAsync(() -> userService.countCouponCountByUserId(userId), executor);

            // 聚合所有的查询 等所有的查询都查询完
            CompletableFuture.allOf(articleCountFT, LikeCountFT, fansCountFT, msgCountFT, collectCountFT, followCountFT, redBagCountFT, couponCountFT).get(20, TimeUnit.SECONDS);

            UserBehaviorDataDTO userBehaviorData = UserBehaviorDataDTO.builder().articleCount(articleCountFT.get()).likeCount(LikeCountFT.get()).fansCount(fansCountFT.get()).msgCount(msgCountFT.get()).collectCount(collectCountFT.get()).followCount(followCountFT.get()).redBagCount(redBagCountFT.get()).couponCount(couponCountFT.get()).build();
            return userBehaviorData;
        } catch (Exception e) {
            log.error("get user behavior data error", e);
            return new UserBehaviorDataDTO();
        }
    }

}