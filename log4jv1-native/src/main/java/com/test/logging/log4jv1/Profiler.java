package com.test.logging.log4jv1;

import org.apache.log4j.Logger;

import com.test.logging.common.BaseProfiler;
import com.test.logging.common.LoggingTest;
import com.test.logging.common.TestFactoryType;
import com.test.logging.common.TestOptions;

/**
 * Profiling LOG4J version 1 via Native API
 * 
 * @author Michael.Zhou
 */
public class Profiler extends BaseProfiler {
    static {
        setAppName("log4j v1 (native) profiler");
    }

    public static void main(String[] args) throws InterruptedException {
        printBanner();

        TestOptions opts = getTestOptions();
        opts.setDefaultResultsFilename("log4jv1-native.csv");
        if (!opts.parseCliOptions(getAppName(), args)) {
            return;
        }

        Logger logger = Logger.getLogger(Profiler.class);

        // Iterate through all unit work types and execute test scenarios
        TestFactoryType[] allTypes = TestFactory.getSupportedTypes();
        for (TestFactoryType t : allTypes) {
            new LoggingTest<Logger>(t.toString(), new TestFactory(t, logger), opts).run();
        }
    }

}
