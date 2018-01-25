package com.foo.concurrent.exception;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * main thread not handle exception
 */
public class ExceptionThread2 implements Runnable {
    @Override
    public void run() {
        throw new RuntimeException();
    }

    public static void main(String[] args) {
        try {
            ExecutorService executorService = Executors.newCachedThreadPool();
            executorService.execute(new ExceptionThread2());
        } catch (RuntimeException e) {
            System.out.println("RuntimeException has been handled");
        }
    }
}
