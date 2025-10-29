package com.woniu.connection.async;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 聊一聊CompletionService，如何优雅地处理异步任务？
 * 阻塞等待
 */
public class JDKThreadPoolExecutorTest {
//    public static void main(String[] args) throws Exception {
//        ExecutorService executorService = Executors.newCachedThreadPool();
//        ArrayList<Future<Integer>> list = new ArrayList<>();
//        Future<Integer> future_15 = executorService.submit(() -> {
//            TimeUnit.SECONDS.sleep(15);
//            System.out.println("执行时长为15s的执行完成。");
//            return 15;
//        });
//        list.add(future_15);
//
//        Future<Integer> future_5 = executorService.submit(() -> {
//            TimeUnit.SECONDS.sleep(5);
//            System.out.println("执行时长为5s的执行完成。");
//            return 5;
//        });
//        list.add(future_5);
//
//        Future<Integer> future_10 = executorService.submit(() -> {
//            TimeUnit.SECONDS.sleep(10);
//            System.out.println("执行时长为10s的执行完成。");
//            return 10;
//        });
//        list.add(future_10);
//
//        System.out.println("开始准备获取结果");
//        for (Future<Integer> future : list) {
//            System.out.println("future.get() = " + future.get());
//        }
//        Thread.currentThread().join();
//    }

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        ArrayList<Future<String>> list = new ArrayList<>();
        System.out.println("约几个妹子一起吃个饭吧。");
        Future<String> future_15 = executorService.submit(() -> {
            System.out.println("小红：好的，哥哥。我化妆要2个小时。等一下哦。");
            TimeUnit.SECONDS.sleep(15);
            System.out.println("小红：我2个小时准时化好了，哥哥来接我吧。");
            return "小红化完了。";
        });
        list.add(future_15);
        Future<String> future_5 = executorService.submit(() -> {
            System.out.println("小媛：好的，哥哥。我化妆要30分钟。等一下哦。");
            TimeUnit.SECONDS.sleep(5);
            System.out.println("小媛：我30分钟准时化好了，哥哥来接我吧。");
            return "小媛化完了。";
        });
        list.add(future_5);

        Future<String> future_10 = executorService.submit(() -> {
            System.out.println("小花：好的，哥哥。我化妆要1个小时。等一下哦。");
            TimeUnit.SECONDS.sleep(10);
            System.out.println("小花：我1个小时准时化好了，哥哥来接我吧。");
            return "小花化完了。";
        });
        list.add(future_10);
        TimeUnit.SECONDS.sleep(1);
        System.out.println("都通知完,等着吧。");
        for (Future<String> future : list) {
            System.out.println(future.get()+"我去接她。");
        }
        Thread.currentThread().join();
    }

}
