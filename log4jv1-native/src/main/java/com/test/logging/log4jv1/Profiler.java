package com.test.logging.log4jv1;

import com.test.logging.common.LoggingTest;
import com.test.logging.common.TestOptions;
import org.apache.log4j.Logger;

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
        System.out.println("############################################################");
        System.out.println("   Starting LOG4J version 1 via SLF4J profiling test ...");
        System.out.println("############################################################");
        System.out.println("");
    }

    public static void main(String[] args) throws InterruptedException
    {
        if (!opts.parseCliOptions("log4j v1 (native) profiler", args)) {
            return;
        }
        
        printBanner();

        Logger logger = Logger.getLogger(Profiler.class);

        // Iterate through all unit work types and execute test scenarios
        UnitWorkFactory.Type[] allTypes = UnitWorkFactory.Type.class.getEnumConstants();
        for (UnitWorkFactory.Type t : allTypes) {
            new LoggingTest<Logger>(t.toString(), new UnitWorkFactory(t, logger), opts).run();
        }
        
    }

}
