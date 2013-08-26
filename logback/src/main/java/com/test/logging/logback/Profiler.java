package com.test.logging.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.logging.common.BaseProfiler;
import com.test.logging.common.LoggingTest;
import com.test.logging.common.TestFactoryType;
import com.test.logging.common.ProfilerOptions;

/**
 * Profiling LOGBACK via SLF4J API
 * 
 * @author Michael.Zhou
 */
public class Profiler extends BaseProfiler {
    static {
        setAppName("logback profiler");
    }

    public static void main(String[] args) throws InterruptedException {
        printBanner();

        ProfilerOptions opts = getTestOptions();
        opts.setDefaultResultsFilename("logback.csv");
        if (!opts.parseCliOptions(getAppName(), args)) {
            return;
        }

        // File appender
        Logger logger = LoggerFactory.getLogger(Profiler.class);
        // Iterate through all unit work types and execute test scenarios
        TestFactoryType[] allTypes = TestFactory.getSupportedTypes();
        for (TestFactoryType t : allTypes) {
            new LoggingTest<Logger>(t.toString(), new TestFactory(t, logger), opts).run();
        }
    }

}
