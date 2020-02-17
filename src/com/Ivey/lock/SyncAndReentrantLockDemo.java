package com.Ivey.lock;

/**
 * @Description
 * @Author IveyLv
 * @Date 2019/11/22 15:36
 * @Version 1.0
 */

/**
 * 题目: synchronized 和 Lock有什么区别? 用新的Lock有什么好处?你举例说说
 * 1. 原始构成
 *    synchronized 是关键字属于 JVM 层面，
 *      monitorenter(底层是通过monitor对象来完成，其实 wait/notify 等方法也依赖monitor对象，只有在同步块或方法中才能调 wait/notify等方法
 *      monitorexit
 *    Lock.是具体类(java.util.concurrent.locks.Lock)是 api 层面的锁
 * 2. 使用方法
 *    synchronized 不需要用户去主动释放锁，当synchronized代码执行完后系统会自动让线程释放对锁的占用
 *    ReentrantLock 则需要用户去手动释放锁若没有主动释放锁，就有可能导致出现死锁现象。
 *    需要 lock() 和 unlock()方法配合 try/finally 语句块来完成。
 * 3. 等待是否可中断
 *    synchronized 不可中断，除非抛出异常或者正常运行完成
 *    ReentrantLock 可中断：3.1 设置超时方法tryLock(long timeout, TimeUnit unit)
 *                         3.2 lockInterruptibly()放代码块中，调用interrupt() 方法可中断
 * 4. 加锁是否公平
 *    synchronized非公平锁
 *    ReentrantLock两者都可以，默认非公平锁，构造方法可以传入boolean值, true为公平锁，false为非公平锁
 * 5. 锁绑定多个条件Condition
 *    synchronized没有
 *    ReentrantLock 用来实现分组唤醒需要唤醒的线程们，可以精确唤醒，而不是像synchronized要么随机唤醒一个线程，要么唤醒全部线程。
 */

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 题目：A、B、C 三个线程顺序工作，分别打印 5、10、15次
 */
class ShareResource {
    private int number = 1;  // A:1 B:2 C:3
    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    public void aPrint() {
        lock.lock();
        try {
            while (number != 1) {
                condition1.await();
            }
            for (int i = 1; i <= 5; i++) {
                System.out.println(Thread.currentThread().getName() + " " + i);
            }
            number = 2;
            condition2.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void bPrint() {
        lock.lock();
        try {
            while (number != 2) {
                condition2.await();
            }
            for (int i = 1; i <= 10; i++) {
                System.out.println(Thread.currentThread().getName() + " " + i);
            }
            number = 3;
            condition3.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void cPrint() {
        lock.lock();
        try {
            while (number != 3) {
                condition3.await();
            }
            for (int i = 1; i <= 15; i++) {
                System.out.println(Thread.currentThread().getName() + " " + i);
            }
            number = 1;
            condition1.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

public class SyncAndReentrantLockDemo {

    public static void main(String[] args) {
        ShareResource shareResource = new ShareResource();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareResource.aPrint();
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareResource.bPrint();
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareResource.cPrint();
            }
        }, "C").start();
    }
}
