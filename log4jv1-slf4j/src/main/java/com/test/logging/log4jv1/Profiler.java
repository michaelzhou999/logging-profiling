package com.test.logging.log4jv1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.logging.common.BaseProfiler;
import com.test.logging.common.LoggingTest;
import com.test.logging.common.ProfilerOptions;
import com.test.logging.common.TestFactoryType;

/**
 * Profiling LOG4J version 1 via SLF4J API
 * 
 * @author Michael.Zhou
 */
public class Profiler extends BaseProfiler {
    static {
        setProfilerName("log4j v1 (via slf4j) profiler");
    }

    public static void main(String[] args) throws InterruptedException {
        printBanner();

        ProfilerOptions opts = getProfilerOptions();
        if (!opts.parseCliOptions(getProfilerName(), args)) {
            return;
        }

        Logger logger = LoggerFactory.getLogger(Profiler.class);

        // Iterate through all unit work types and execute test scenarios
        TestFactoryType[] allTypes = TestFactory.getSupportedTypes();
        for (TestFactoryType t : allTypes) {
            new LoggingTest<Logger>(t.toString(), new TestFactory(t, logger), opts).run();
        }
    }
}
