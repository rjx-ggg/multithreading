package com.xxl.job.plus.executor.core;


import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 看看这段线程池的代码
 * 有没有啥问题？
 */
public class ThreadPoolExecutorTest {

   static ThreadPoolExecutor productThreadPoolExecutor = new ThreadPoolExecutor(
           1,
            1,
            1,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1),
            new MyThreadFactory("product"),
            new MyRejectedPolicy()
    );

    public static void main(String[] args) throws Exception {
        while (true) {
            TimeUnit.SECONDS.sleep(1);
            new Thread(() -> {
                ArrayList<Future<Integer>> futureList = new ArrayList<>();
                int productNum = 5;
                for (int i = 0; i < productNum; i++) {
                    try {
                        int finalI = i;
                        Future<Integer> future = productThreadPoolExecutor.submit(() -> {
                            System.out.println("Thread.currentThread().getName() = " + Thread.currentThread().getName());
                            return finalI * 10;
                        });
                        futureList.add(future);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                for (Future<Integer> future: futureList) {
                    try {
//                        Integer integer = future.get();
                        Integer integer = future.get(10,TimeUnit.SECONDS);
                        System.out.println(integer);
                        System.out.println("future.get() = " + integer);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    static class MyThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;
        private final String threadFactoryName;

        public String getThreadFactoryName() {
            return threadFactoryName;
        }


        public MyThreadFactory(String threadStartName) {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = threadStartName + "-pool-" + poolNumber.getAndIncrement() + "-thread-";
            threadFactoryName = threadStartName;
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }

    public static class MyRejectedPolicy implements RejectedExecutionHandler{

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            if (e.getThreadFactory() instanceof MyThreadFactory) {
                MyThreadFactory myThreadFactory = (MyThreadFactory)e.getThreadFactory();
                if ("product".equals(myThreadFactory.getThreadFactoryName())) {
                  System.out.println("线程池有任务被拒绝了,请关注");
                }
                throw new RejectedExecutionException("Task " + r.toString() +
                        " rejected from " +
                        e.toString());
            }
        }
    }


}



