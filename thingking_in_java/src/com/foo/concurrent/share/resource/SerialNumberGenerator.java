package com.foo.concurrent.share.resource;

public class SerialNumberGenerator {
    private static volatile int serialNumber = 0;

    public static int nextSerialNumber() {
        return serialNumber++;// 不是线程安全的
    }
}
