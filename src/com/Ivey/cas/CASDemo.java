package com.Ivey.cas;

import java.util.concurrent.atomic.AtomicInteger;

public class CASDemo {

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);

        System.out.print(atomicInteger.compareAndSet(5, 2019));
        System.out.println(" the value are " + atomicInteger.get());

        System.out.print(atomicInteger.compareAndSet(5, 1024));
        System.out.println(" the value are " + atomicInteger.get());
    }
}
