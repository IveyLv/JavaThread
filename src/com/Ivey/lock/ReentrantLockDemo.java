package com.Ivey.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description 模拟可重入锁（递归锁）
 * @Author IveyLv
 * @Date 2019/11/20 10:52
 * @Version 1.0
 */
class Phone implements Runnable {
    public synchronized void sendSMS() {
        System.out.println(Thread.currentThread().getName() + " send SMS...");
        sendEmail();
    }

    public synchronized void sendEmail() {
        System.out.println(Thread.currentThread().getName() + " send email...");
    }

    Lock lock = new ReentrantLock();

    @Override
    public void run() {
        get();
    }

    public void get() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " get method...");
            set();
        } finally {
            lock.unlock();
        }
    }

    public void set() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " set method...");
        } finally {
            lock.unlock();
        }
    }
}

public class ReentrantLockDemo {

    public static void main(String[] args) {
        Phone phone = new Phone();

        /*new Thread(() -> {
            phone.sendSMS();
        }, "Thread1").start();

        new Thread(() -> {
            phone.sendSMS();
        }, "Thread2").start();*/

        Thread thread3 = new Thread(phone, "Thread3");
        Thread thread4 = new Thread(phone, "Thread4");
        thread3.start();
        thread4.start();
    }
}
