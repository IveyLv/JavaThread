package com.Ivey.volatiledemo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class MyData {

    volatile int number = 0;

    public void add() {
        this.number = 60;
    }

    public void addPlusPlus() {
        number++;
    }

    AtomicInteger atomicInteger = new AtomicInteger();
    public void addMyAtomic() {
        atomicInteger.getAndIncrement();
    }
}

/**
 * 1. 验证 volatile 的可见性
 *    1.1 int number = 0， number 变量之前没有添加 volatile 关键字，没有可见性
 *    1.2 添加 volatile 可以保证可见性
 *
 * 2. 验证 volatile 不保证原子性
 *    2.1 原子性指的是什么意思?
 *        不可分割，完整性，也即某个线程正在做某个具体业务时，中间不可以被加塞或者被分割。需要整体完整
 *        要么同时成功，要么同时失败。
 *    2.2 解决问题
 *
 */
public class VolatileDemo {

    public static void main(String[] args) {
        MyData myData = new MyData();

        for (int i = 1; i <= 20; i++) {
            new Thread(() -> {
                for (int j = 1; j <= 1000; j++) {
                    myData.addPlusPlus();
                    myData.addMyAtomic();
                }
            }, String.valueOf(i)).start();
        }

        while (Thread.activeCount() > 2) {
            Thread.yield();
        }

        System.out.println(Thread.currentThread().getName() + " finally number value are " + myData.number);
        System.out.println(Thread.currentThread().getName() + " finally atomic number value are " + myData.atomicInteger);
    }

    // volatile 可以保证可见性，及时通知其他线程，主物理内存的值已经被修改
    private static void seeOkByVolatile() {
        MyData myData = new MyData();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " come in");

            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            myData.add();
            System.out.println(Thread.currentThread().getName() + " update the value to " + myData.number);
        }, "Thread A").start();

        while (myData.number == 0) {
            // main 线程一直循环等待直到 number 的值不再是 0
        }
        System.out.println(Thread.currentThread().getName() + " mission is over, main get the value " + myData.number);
    }
}
