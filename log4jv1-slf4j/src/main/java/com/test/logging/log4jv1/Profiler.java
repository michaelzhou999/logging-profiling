package com.test.logging.log4jv1;

import com.test.logging.common.BaseProfiler;
import com.test.logging.common.TestOptions;
import com.test.logging.common.LoggingTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Profiling LOG4J version 1 via SLF4J API
 *
 * @author Michael.Zhou
 */
public class Profiler extends BaseProfiler
{
    static {
        setAppName("log4j v1 (via slf4j) profiler");
    }

    public static void main(String[] args) throws InterruptedException
    {
        TestOptions opts = getTestOptions();
        if (!opts.parseCliOptions(getAppName(), args)) {
            return;
        }
        
        printBanner();

        Logger logger = LoggerFactory.getLogger(Profiler.class);

        // Iterate through all unit work types and execute test scenarios
        UnitWorkFactory.Type[] allTypes = UnitWorkFactory.Type.class.getEnumConstants();
        for (UnitWorkFactory.Type t : allTypes) {
            new LoggingTest<Logger>(t.toString(), new UnitWorkFactory(t, logger), opts).run();
        }
        
    }

}
