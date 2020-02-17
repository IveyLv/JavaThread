package com.Ivey.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * @Description CountDownLatch 类（倒计时）
 * @Author IveyLv
 * @Date 2019/11/21 11:09
 * @Version 1.0
 */
public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        nationalUnification();
    }

private static void nationalUnification() throws InterruptedException {
    CountDownLatch countDownLatch = new CountDownLatch(6);

    for (int i = 1; i <= 6; i++) {
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "国被灭...");
            countDownLatch.countDown();
        }, CountryEnum.forEachCountryEnum(i).getCountry()).start();
    }

    countDownLatch.await();
    System.out.println(Thread.currentThread().getName() + " 秦国灭六国，一统华夏");
}

    private static void closeDoor() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);

        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " 离开教室");
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }

        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + " 班长锁门离开教室");
    }
}
