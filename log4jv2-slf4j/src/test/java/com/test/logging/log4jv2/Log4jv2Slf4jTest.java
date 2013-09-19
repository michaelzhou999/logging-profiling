package com.test.logging.log4jv2;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.logging.common.LoggingTest;
import com.test.logging.common.ProfilerOptions;
import com.test.logging.common.TestFactoryType;
import static com.test.logging.common.Util.sleepForIOCatchup;

/** Log4j v2 logger test */
public class Log4jv2Slf4jTest {

    /** Profiler options */
    private static final ProfilerOptions opts = new ProfilerOptions("Log4j v2 (via slf4j API) Profiler");

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
        Logger logger = LoggerFactory.getLogger("SyncFileLogger");
        // Iterate through all unit work types and execute test scenarios
        for (TestFactoryType t : allTypes) {
            new LoggingTest<Logger>(t.toString() + "-SyncFile", new TestFactory(t, logger), opts).run();
        }
    }

    @Test
    public void testAsyncFileLogger() {
        // Asynchronous file logger
        System.out.println("(((((((((((((   ASYNC FILE LOGGER   ))))))))))))))))");
        Logger asyncLogger = LoggerFactory.getLogger("AsyncFileLogger");
        // Iterate through all unit work types and execute test scenarios
        for (TestFactoryType t : allTypes) {
            new LoggingTest<Logger>(t.toString() + "-AsyncFile", new TestFactory(t, asyncLogger), opts).run();
        }

        sleepForIOCatchup();
    }

    @Test
    public void testSyncRandomAccessFileLogger() {
        // Synchronous random access file logger
        System.out.println("(((((((((((((   SYNC RANDOMACCESSFILE LOGGER   ))))))))))))))))");
        Logger logger = LoggerFactory.getLogger("SyncRandomAccessFileLogger");
        // Iterate through all unit work types and execute test scenarios
        for (TestFactoryType t : allTypes) {
            new LoggingTest<Logger>(t.toString() + "-SyncRandomAccessFile", new TestFactory(t, logger), opts).run();
        }
    }

    @Test
    public void testAsyncRandomAccessFileLogger() {
        // Asynchronous random access file logger
        System.out.println("(((((((((((((   ASYNC RANDOMACCESSFILE LOGGER   ))))))))))))))))");
        Logger asyncLogger = LoggerFactory.getLogger("AsyncRandomAccessFileLogger");
        // Iterate through all unit work types and execute test scenarios
        for (TestFactoryType t : allTypes) {
            new LoggingTest<Logger>(t.toString() + "-AsyncRandomAccessFile", new TestFactory(t, asyncLogger), opts).run();
        }

        sleepForIOCatchup();
    }

}
