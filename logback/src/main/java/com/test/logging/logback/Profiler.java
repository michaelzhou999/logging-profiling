package com.test.logging.logback;

import com.test.logging.common.BaseProfiler;
import com.test.logging.common.TestOptions;
import com.test.logging.common.LoggingTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Profiling LOGBACK via SLF4J API
 *
 * @author Michael.Zhou
 */
public class Profiler extends BaseProfiler
{
    static {
        setAppName("logback profiler");
    }

    public static void main(String[] args) throws InterruptedException
    {
        TestOptions opts = getTestOptions();
        if (!opts.parseCliOptions(getAppName(), args)) {
            return;
        }
        
        printBanner();

        // File appender
        Logger logger = LoggerFactory.getLogger(Profiler.class);
        // Iterate through all unit work types and execute test scenarios
        UnitWorkFactory.Type[] allTypes = UnitWorkFactory.Type.class.getEnumConstants();
        for (UnitWorkFactory.Type t : allTypes) {
            new LoggingTest<Logger>(t.toString(), new UnitWorkFactory(t, logger), opts).run();
        }
    }

}
