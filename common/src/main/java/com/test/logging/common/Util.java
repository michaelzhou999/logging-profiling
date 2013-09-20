package com.test.logging.common;

public class Util {
    public static void sleepForIOCatchup() {
        // Wait for I/O to catch up and close async appenders
        System.out.println("Waiting 10 seconds for I/O to catch up and properly close async appenders.");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ie) {
            System.err.println("Interrupted while waiting for I/O to catch up.");
        }
    }
}
