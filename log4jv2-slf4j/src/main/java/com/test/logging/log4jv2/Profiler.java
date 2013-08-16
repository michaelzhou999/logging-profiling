package com.test.logging.log4jv2;

import com.test.logging.common.TestOptions;
import com.test.logging.common.LoggingTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Profiling LOG4J version 2 via SLF4J API
 *
 * @author Michael.Zhou
 */
public class Profiler 
{
    /** Options for running the profiler */
    private static final TestOptions opts = new TestOptions();

    private static void printBanner() {
        System.out.println("");
        System.out.println("############################################################");
        System.out.println("   Starting LOG4J version 2 via SLF4J profiling test ...");
        System.out.println("############################################################");
        System.out.println("");
    }

    public static void main(String[] args) throws InterruptedException
    {
        if (!opts.parseCliOptions(args)) {
            return;
        }
        
        printBanner();

        // File appender
        Logger logger = LoggerFactory.getLogger("SyncFileLogger");
        // Iterate through all unit work types and execute test scenarios
        System.out.println("(((((((((((((   SLOW FILE LOGGER ))))))))))))))))");
        UnitWorkFactory.Type[] allTypes = UnitWorkFactory.Type.class.getEnumConstants();
        for (UnitWorkFactory.Type t : allTypes) {
            new LoggingTest<Logger>(t.toString(), new UnitWorkFactory(t, logger), opts).run();
        }

        // Fast file appender
        Logger fastLogger = LoggerFactory.getLogger("SyncFastFileLogger");
        // Iterate through all unit work types and execute test scenarios
        System.out.println("(((((((((((((   FAST FILE LOGGER ))))))))))))))))");
        for (UnitWorkFactory.Type t : allTypes) {
            new LoggingTest<Logger>(t.toString(), new UnitWorkFactory(t, fastLogger), opts).run();
        }
        
    }

}
