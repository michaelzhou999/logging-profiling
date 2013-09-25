package com.test.logging.jul;

import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.test.logging.common.LoggingTest;
import com.test.logging.common.ProfilerOptions;
import com.test.logging.common.TestFactoryType;
import static com.test.logging.common.Util.sleepForIOCatchup;

/** JUL logger test */
public class JulTest {

    /** Profiler options */
    private static final ProfilerOptions opts = new ProfilerOptions("Java Util Logging Profiler");

    /** Different types of tests */
    private static final TestFactoryType[] allTypes = TestFactory.getSupportedTypes();

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
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
        Logger logger = Logger.getLogger("SyncFileLogger");
        // Iterate through all unit work types and execute test scenarios
        for (TestFactoryType t : allTypes) {
            new LoggingTest<Logger>(t.toString() + "-Sync", new TestFactory(t, logger), opts).run();
        }
    }

}
