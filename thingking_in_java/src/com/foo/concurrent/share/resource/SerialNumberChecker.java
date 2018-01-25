package com.foo.concurrent.share.resource;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SerialNumberChecker {

    private static final int SIZE = 10;
    
    private static CircularSet serials = new CircularSet(1000);

    private static ExecutorService executorService = Executors.newCachedThreadPool();

    static class SerialChecker implements Runnable {
        @Override
        public void run() {
            while (true) {
                int serial = SerialNumberGenerator.nextSerialNumber();
                if (serials.contains(serial)) {
                    System.out.println("Duplicate: " + serial);
                    System.exit(0);
                }

                serials.add(serial);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        
        for (int i = 0; i < SIZE; i++) {// 开启10个线程
            executorService.execute(new SerialChecker());

            // stop after n seconds if there's an argument
            if (args.length > 0) {
                TimeUnit.SECONDS.sleep(new Integer(args[0]));
                System.out.println("No duplicates detected");

                System.exit(0);
            }
        }
    }

}

class CircularSet {
    private int[] array;
    private int len;
    private int index = 0;

    public CircularSet(int size) {
        array = new int[size];
        this.len = size;

        for (int i = 0; i < size; i++) {
            array[i] = -1;
        }
    }

    public synchronized void add(int i) {
        array[index] = i;

        index = ++index % len;
    }

    public synchronized boolean contains(int val) {
        for (int i = 0; i < len; i++) {
            if (array[i] == val) {
                return true;
            }
        }
        return false;
    }
}
