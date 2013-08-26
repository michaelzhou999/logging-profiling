package com.test.logging.log4jv2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.test.logging.common.BaseProfiler;
import com.test.logging.common.LoggingTest;
import com.test.logging.common.ProfilerOptions;
import com.test.logging.common.TestFactoryType;

/**
 * Profiling LOG4J version 2 via Native API
 * 
 * @author Michael.Zhou
 */
public class Profiler extends BaseProfiler {
    static {
        setProfilerName("log4j v2 (native) profiler");
    }

    public static void main(String[] args) throws InterruptedException {
        printBanner();

        ProfilerOptions opts = getProfilerOptions();
        opts.setDefaultResultsFilename("log4jv2-native.csv");
        if (!opts.parseCliOptions(getProfilerName(), args)) {
            return;
        }

        TestFactoryType[] allTypes = TestFactory.getSupportedTypes();

        // Synchronous file logger
        System.out.println("(((((((((((((   SYNC FILE LOGGER ))))))))))))))))");
        Logger logger = LogManager.getLogger("SyncFileLogger");
        // Iterate through all unit work types and execute test scenarios
        for (TestFactoryType t : allTypes) {
            new LoggingTest<Logger>(t.toString() + "-FileAppender", new TestFactory(t, logger), opts).run();
        }

        // Synchronous fast file logger
        System.out.println("(((((((((((((   SYNC FAST FILE LOGGER ))))))))))))))))");
        Logger fastLogger = LogManager.getLogger("SyncFastFileLogger");
        // Iterate through all unit work types and execute test scenarios
        for (TestFactoryType t : allTypes) {
            new LoggingTest<Logger>(t.toString() + "-FastFileAppender", new TestFactory(t, fastLogger), opts).run();
        }

        // Asynchronous fast file logger
        System.out.println("(((((((((((((   ASYNC FAST FILE LOGGER ))))))))))))))))");
        Logger asyncLogger = LogManager.getLogger("AsyncFastFileLogger");
        // Iterate through all unit work types and execute test scenarios
        for (TestFactoryType t : allTypes) {
            new LoggingTest<Logger>(t.toString() + "-AsyncFastFileLogger", new TestFactory(t, asyncLogger), opts).run();
        }
        // Wait for 20 seconds for I/O to catch up and closing of async appenders
        System.out.println("Waiting for 20 seconds for I/O to catch up and proper closing of async appenders.");
        Thread.sleep(20000);
    }

}
