package com.foo.concurrent.exception;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class CaptureUncaughtException {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool(new HandleThreadFactory());

        executorService.execute(new ExceptionThread3());
    }
}

class ExceptionThread3 implements Runnable {

    @Override
    public void run() {
        Thread t = Thread.currentThread();
        System.out.println("run() by : " + t);

        System.out.println("eh = " + t.getUncaughtExceptionHandler());
        throw new RuntimeException();
    }

}

/**
 * 异常处理器
 */
class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("MyUncaughtExceptionHandler.uncaughtException caught " + e + " in thread " + t);
    }
}

/**
 * 线程工厂, 给线程设置异常处理器
 */
class HandleThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {
        System.out.println(this + " create new Thread");

        Thread thread = new Thread(r);

        System.out.println("created " + thread);
        thread.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());

        System.out.println("eh = " + thread.getUncaughtExceptionHandler());

        return thread;
    }

}


