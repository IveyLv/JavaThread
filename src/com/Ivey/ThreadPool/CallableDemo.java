package com.Ivey.ThreadPool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Description 线程的第三种实现方式
 * @Author IveyLv
 * @Date 2019/12/17 15:55
 * @Version 1.0
 */
class MyThread implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName() + "*************come in callable*************");
        return 1024;
    }
}

public class CallableDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask = new FutureTask<>(new MyThread());
        new Thread(futureTask, "Thread-1").start();
        new Thread(futureTask, "Thread-2").start();

        int result1 = 100;
        // 获得 Callable 线程的计算结果，如果没有计算完成，那么主线程会被阻塞
        int result2 = futureTask.get();

        System.out.println("result-->" + (result1 + result2));
    }
}
