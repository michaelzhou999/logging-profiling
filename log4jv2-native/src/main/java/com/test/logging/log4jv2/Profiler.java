package com.test.logging.log4jv2;

import com.test.logging.common.BaseProfiler;
import com.test.logging.common.LoggingTest;
import com.test.logging.common.TestOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Profiling LOG4J version 2 via Native API
 *
 * @author Michael.Zhou
 */
public class Profiler extends BaseProfiler
{
    static {
        setAppName("log4j v2 (native) profiler");
    }

    public static void main(String[] args) throws InterruptedException
    {
        TestOptions opts = getTestOptions();
        if (!opts.parseCliOptions(getAppName(), args)) {
            return;
        }
        
        printBanner();

        // File appender
        Logger logger = LogManager.getLogger("SyncFileLogger");
        // Iterate through all unit work types and execute test scenarios
        System.out.println("(((((((((((((   SLOW FILE LOGGER ))))))))))))))))");
        UnitWorkFactory.Type[] allTypes = UnitWorkFactory.Type.class.getEnumConstants();
        for (UnitWorkFactory.Type t : allTypes) {
            new LoggingTest<Logger>(t.toString(), new UnitWorkFactory(t, logger), opts).run();
        }

        // Fast file appender
        Logger fastLogger = LogManager.getLogger("SyncFastFileLogger");
        // Iterate through all unit work types and execute test scenarios
        System.out.println("(((((((((((((   FAST FILE LOGGER ))))))))))))))))");
        for (UnitWorkFactory.Type t : allTypes) {
            new LoggingTest<Logger>(t.toString(), new UnitWorkFactory(t, fastLogger), opts).run();
        }
        
    }

}
