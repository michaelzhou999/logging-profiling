package com.test.logging.log4jv1;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.logging.common.LoggingTest;
import com.test.logging.common.ProfilerOptions;
import com.test.logging.common.TestFactoryType;

/** Log4j v1 logger test */
public class Log4jv1Slf4jTest {

    /** Profiler options */
    private static final ProfilerOptions opts = new ProfilerOptions("Log4j v1 (via slf4j API) Profiler");

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

}
