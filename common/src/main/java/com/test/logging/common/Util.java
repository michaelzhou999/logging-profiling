package com.test.logging.common;

public class Util {
    public static void sleepForIOCatchup() {
        // Wait for 20 seconds for I/O to catch up and closing of async appenders
        System.out.println("Waiting for 20 seconds for I/O to catch up and proper closing of async appenders.");
        try {
            Thread.sleep(20000);
        } catch (InterruptedException ie) {
            System.err.println("Interrupted while waiting for I/O to catch up.");
        }
    }
}
