package com.Ivey.lock;

import java.util.concurrent.TimeUnit;

/**
 * @Description 死锁演示
 * @Author IveyLv
 * @Date 2019/12/18 17:33
 * @Version 1.0
 */
class HoldLockDemo implements Runnable {

    private String lockA;
    private String lockB;

    public HoldLockDemo(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA) {
            System.out.println(Thread.currentThread().getName() + "\t 自己持有" + lockA + "\t 尝试获得" + lockB);

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName() + "\t 自己持有" + lockB + "\t 尝试获得" + lockA);
            }
        }
    }
}

public class DeadLockDemo {

    public static void main(String[] args) {
        String lockA = "lockA";
        String lockB = "lockB";

        new Thread(new HoldLockDemo(lockA, lockB), "Thread-A").start();
        new Thread(new HoldLockDemo(lockB, lockA), "Thread-B").start();
    }
}
