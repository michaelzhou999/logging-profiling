package com.test.logging.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;

import com.test.logging.common.BaseProfiler;
import com.test.logging.common.LoggingTest;
import com.test.logging.common.ProfilerOptions;
import com.test.logging.common.TestFactoryType;

/**
 * Profiling LOGBACK via SLF4J API
 * 
 * @author Michael.Zhou
 */
public class Profiler extends BaseProfiler {
    static {
        setProfilerName("logback profiler");
    }

    public static void main(String[] args) throws InterruptedException {
        printBanner();

        ProfilerOptions opts = getProfilerOptions();
        opts.setDefaultResultsFilename("logback.csv");
        if (!opts.parseCliOptions(getProfilerName(), args)) {
            return;
        }

        TestFactoryType[] allTypes = TestFactory.getSupportedTypes();

        // Synchronous file logger
        // System.out.println("(((((((((((((   SYNC FILE LOGGER ))))))))))))))))");
        // Logger logger = LoggerFactory.getLogger("SyncFileLogger");
        // // Iterate through all unit work types and execute test scenarios
        // for (TestFactoryType t : allTypes) {
        // new LoggingTest<Logger>(t.toString() + "-Sync", new TestFactory(t, logger), opts).run();
        // }

        // Async file logger
        System.out.println("(((((((((((((   ASYNC FILE LOGGER ))))))))))))))))");
        Logger asyncLogger = LoggerFactory.getLogger("AsyncFileLogger");
        // Iterate through all unit work types and execute test scenarios
        for (TestFactoryType t : allTypes) {
            new LoggingTest<Logger>(t.toString() + "-Async", new TestFactory(t, asyncLogger), opts).run();
        }
        // Wait for 20 seconds for I/O to catch up and closing of async appenders
        System.out.println("Waiting for 20 seconds for I/O to catch up and proper closing of async appenders.");
        Thread.sleep(20000);

        // Stopping the logger context to force flushing the log events
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        loggerContext.stop();
    }

}
