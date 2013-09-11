package com.test.logging.log4jv2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.test.logging.common.LoggingTest;
import com.test.logging.common.ProfilerOptions;
import com.test.logging.common.TestFactoryType;

/** Log4j v2 logger test */
public class Log4jv2NativeTest {

    /** Profiler options */
    private static final ProfilerOptions opts = new ProfilerOptions("Log4j v2 (via native API) Profiler");

    /** Different types of tests */
    private static final TestFactoryType[] allTypes = TestFactory.getSupportedTypes();

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSyncFileLogger() {
        // Synchronous file logger
        System.out.println("(((((((((((((   SYNC FILE LOGGER   ))))))))))))))))");
        Logger logger = LogManager.getLogger("SyncFileLogger");
        // Iterate through all unit work types and execute test scenarios
        for (TestFactoryType t : allTypes) {
            new LoggingTest<Logger>(t.toString() + "-SyncFile", new TestFactory(t, logger), opts).run();
        }
    }

    @Test
    public void testSyncFastFileLogger() {
        // Synchronous fast file logger
        System.out.println("(((((((((((((   SYNC FAST FILE LOGGER   ))))))))))))))))");
        Logger fastLogger = LogManager.getLogger("SyncFastFileLogger");
        // Iterate through all unit work types and execute test scenarios
        for (TestFactoryType t : allTypes) {
            new LoggingTest<Logger>(t.toString() + "-SyncFastFile", new TestFactory(t, fastLogger), opts).run();
        }
    }

    @Test
    public void testAsyncFastFileLogger() {
        // Asynchronous file logger
        System.out.println("(((((((((((((   ASYNC FAST FILE LOGGER   ))))))))))))))))");
        Logger asyncLogger = LogManager.getLogger("AsyncFastFileLogger");
        // Iterate through all unit work types and execute test scenarios
        for (TestFactoryType t : allTypes) {
            new LoggingTest<Logger>(t.toString() + "-AsyncFastFile", new TestFactory(t, asyncLogger), opts).run();
        }
        // Wait for 20 seconds for I/O to catch up and closing of async appenders
        System.out.println("Waiting for 20 seconds for I/O to catch up and proper closing of async appenders.");
        try {
            Thread.sleep(20000);
        } catch (InterruptedException ie) {
            System.err.println("Interrupted while waiting for I/O to catch up.");
        }
    }

}
