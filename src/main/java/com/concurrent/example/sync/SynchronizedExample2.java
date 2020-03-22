package com.concurrent.example.sync;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class SynchronizedExample2 {

    private int i = 0;

    // 修饰一个类
    public static void test1 (int j) {
        synchronized (SynchronizedExample2.class) {
            for (int i = 0; i < 10; i ++) {
                log.info("test1 {} - {}", j, i);
            }
        }
    }

    // 修饰一个静态方法
    public static synchronized void test2 (int j) {
        for (int i = 0; i < 10; i ++) {
            log.info("test2 {} - {}", j, i);
        }
    }

    public static void main(String[] args) {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
//        mainTest1(cachedThreadPool);
        mainTest2(cachedThreadPool);
    }


    private static void mainTest1 (ExecutorService cachedThreadPool) {
        SynchronizedExample2 synchronizedExample1 = new SynchronizedExample2();
        SynchronizedExample2 synchronizedExample2 = new SynchronizedExample2();
        cachedThreadPool.execute(() -> {
            synchronizedExample1.test1(1);
        });
        cachedThreadPool.execute(() -> {
            synchronizedExample2.test1(2);
        });
    }

    private static void mainTest2 (ExecutorService cachedThreadPool) {
        SynchronizedExample2 synchronizedExample1 = new SynchronizedExample2();
        SynchronizedExample2 synchronizedExample2 = new SynchronizedExample2();
        cachedThreadPool.execute(() -> {
            synchronizedExample1.test2(1);
        });
        cachedThreadPool.execute(() -> {
            synchronizedExample2.test2(2);
        });
    }
}
