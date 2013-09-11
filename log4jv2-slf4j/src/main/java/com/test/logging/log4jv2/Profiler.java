package com.test.logging.log4jv2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.logging.common.BaseProfiler;
import com.test.logging.common.LoggingTest;
import com.test.logging.common.ProfilerOptions;
import com.test.logging.common.TestFactoryType;

/**
 * Profiling LOG4J version 2 via SLF4J API
 * 
 * @author Michael.Zhou
 */
public class Profiler extends BaseProfiler {
    static {
        setProfilerName("log4j v2 (via slf4j) profiler");
    }

    public static void main(String[] args) throws InterruptedException {
        printBanner();

        ProfilerOptions opts = getProfilerOptions();
        if (!opts.parseCliOptions(getProfilerName(), args)) {
            return;
        }

        TestFactoryType[] allTypes = TestFactory.getSupportedTypes();

        // File appender
        Logger logger = LoggerFactory.getLogger("SyncFileLogger");
        // Iterate through all unit work types and execute test scenarios
        System.out.println("(((((((((((((   SLOW FILE LOGGER   ))))))))))))))))");
        for (TestFactoryType t : allTypes) {
            new LoggingTest<Logger>(t.toString() + "-SyncFile", new TestFactory(t, logger), opts).run();
        }

        // Fast file appender
        Logger fastLogger = LoggerFactory.getLogger("SyncFastFileLogger");
        // Iterate through all unit work types and execute test scenarios
        System.out.println("(((((((((((((   FAST FILE LOGGER   ))))))))))))))))");
        for (TestFactoryType t : allTypes) {
            new LoggingTest<Logger>(t.toString() + "-SyncFastFile", new TestFactory(t, fastLogger), opts).run();
        }

        // Async logger
        Logger asyncLogger = LoggerFactory.getLogger("AsyncFastFileLogger");
        // Iterate through all unit work types and execute test scenarios
        System.out.println("(((((((((((((   ASYNC FAST FILE LOGGER   ))))))))))))))))");
        for (TestFactoryType t : allTypes) {
            new LoggingTest<Logger>(t.toString() + "-AsyncFastFile", new TestFactory(t, asyncLogger), opts).run();
        }
        // Wait for 20 seconds for I/O to catch up and closing of async
        // appenders
        System.out.println("Waiting for 20 seconds for I/O to catch up and proper closing of async appenders.");
        Thread.sleep(20000);
    }

}
