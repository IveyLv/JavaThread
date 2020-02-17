package com.Ivey.blockqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description
 * @Author IveyLv
 * @Date 2019/11/22 16:28
 * @Version 1.0
 */
class MyResource {
    private volatile boolean flag = true;
    private AtomicInteger atomicInteger = new AtomicInteger();

    private BlockingQueue<String> blockingQueue = null;

    public MyResource(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    public void producer() throws InterruptedException {
        String data = null;
        boolean resFlag;

        while (flag) {
            data = atomicInteger.incrementAndGet() + "";
            resFlag = blockingQueue.offer(data, 2L, TimeUnit.SECONDS);
            if (resFlag) {
                System.out.println(Thread.currentThread().getName() + " 生产队列, " + data + " 插入队列成功");
            } else {
                System.out.println(Thread.currentThread().getName() + " 生产队列, " + data + " 插入队列失败");
            }
            TimeUnit.SECONDS.sleep(1);
        }

        System.out.println(Thread.currentThread().getName() + "flag = false, 生产动作结束");
    }

    public void consumer() throws InterruptedException {
        String result = null;

        while (flag) {
            result = blockingQueue.poll(2, TimeUnit.SECONDS);

            if (result == null || result.equalsIgnoreCase("")) {
                flag = false;
                System.out.println(Thread.currentThread().getName() + " 超过2秒没有取到，停止消费");
                return;
            }

            System.out.println(Thread.currentThread().getName() + " 消费队列：" + result);
        }
    }

    public void stop() {
        this.flag = false;
    }
}

public class ProdConsumer_BlockQueueDemo {

    public static void main(String[] args) {
        MyResource myResource = new MyResource(new ArrayBlockingQueue<>(10));

        new Thread(() -> {
            System.out.println("生产线程启动...");
            try {
                myResource.producer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "producer").start();

        new Thread(() -> {
            System.out.println("消费线程启动...\n\n");
            try {
                myResource.consumer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "consumer").start();

        try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }

        System.out.println("\n");
        myResource.stop();
        System.out.println(Thread.currentThread().getName() + " main 线程终止生产-消费");
    }
}
