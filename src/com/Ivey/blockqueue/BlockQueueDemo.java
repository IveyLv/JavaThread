package com.Ivey.blockqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @Description 阻塞队列
 * @Author IveyLv
 * @Date 2019/11/22 11:22
 * @Version 1.0
 */
public class BlockQueueDemo {

    public static void main(String[] args) {
        BlockingQueue<String> blockingQueue = new SynchronousQueue<>();

        new Thread(() -> {
            try {
                blockingQueue.put("1");
                System.out.println(Thread.currentThread().getName() + " put the value: 1");

                blockingQueue.put("2");
                System.out.println(Thread.currentThread().getName() + " put the value: 2");

                blockingQueue.put("3");
                System.out.println(Thread.currentThread().getName() + " put the value: 3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Thread-A").start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName() + " take the value: " + blockingQueue.take());

                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName() + " take the value: " + blockingQueue.take());

                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName() + " take the value: " + blockingQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Thread-B").start();
    }
}
