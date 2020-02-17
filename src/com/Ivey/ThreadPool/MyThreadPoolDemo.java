package com.Ivey.ThreadPool;

import java.util.concurrent.*;

/**
 * @Description 使用线程池获得多线程的方式
 * @Author IveyLv
 * @Date 2019/12/17 16:30
 * @Version 1.0
 */
public class MyThreadPoolDemo {

    public static void main(String[] args) {
        ExecutorService threadPool = new ThreadPoolExecutor(
                2,
                5,
                1L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

        try {
            for (int i = 1; i <= 10; i++) {
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t 办理业务");
                });
            }
        } finally {
            threadPool.shutdown();
        }
    }

    public static void threadPoolInit() {
        // 一池 5 个处理线程
        //ExecutorService threadPool = Executors.newFixedThreadPool(5);

        // 一池 1 个处理线程
        //ExecutorService threadPool = Executors.newSingleThreadExecutor();

        // 一池 N 个处理线程
        ExecutorService threadPool = Executors.newCachedThreadPool();

        try {
            for (int i = 1; i <= 10; i++) {
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t 办理业务");
                });
            }
        } finally {
            threadPool.shutdown();
        }
    }
}
