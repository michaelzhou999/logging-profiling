package com.test.logging.log4jv2;

import com.test.logging.common.LoggingTest;
import com.test.logging.common.TestOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Profiling LOG4J version 1 via Native API
 *
 * @author Michael.Zhou
 */
public class Profiler 
{
    /** Options for running the profiler */
    private static final TestOptions opts = new TestOptions();

    private static void printBanner() {
        System.out.println("");
        System.out.println("################################################################");
        System.out.println("   Starting LOG4J version 2 via Native API profiling test ...");
        System.out.println("################################################################");
        System.out.println("");
    }

    public static void main(String[] args) throws InterruptedException
    {
        if (!opts.parseCliOptions("log4j v2 (native) profiler", args)) {
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
