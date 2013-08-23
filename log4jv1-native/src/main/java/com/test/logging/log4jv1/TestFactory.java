package com.test.logging.log4jv1;

import org.apache.log4j.Logger;

import com.test.logging.common.ITestScenarioFactory;
import com.test.logging.common.TestScenario;

/**
 * Factory to create units of logging test work to use with Log4j v1 native
 * APIs.
 * 
 * @author Michael.Zhou
 */
public class TestFactory implements ITestScenarioFactory<Logger> {

    public static enum Type {
        // TRACE_CHECKED_STRING("TRACE Level checked string literal (ms)"),
        LITERAL("String literal (ms)"),
        LEVEL_CHECKED_LITERAL("Level checked string literal (ms)"),
        CONCAT_STRING("Level checked string concatenated via + (ms)"),
        JAVA_FORMATTED_STRING("Level checked string Java built-in formatting (ms)");

        /** String representation of the factory type */
        private final String name;

        private Type(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    /** Type of factory */
    private Type type;

    /** Logger */
    private Logger logger;

    public TestFactory(Type type, Logger logger) {
        this.type = type;
        this.logger = logger;
    }

    /** All unit works created by this factory share the same logger. */
    public TestScenario<Logger> createTestScenario() {
        switch (this.type) {
        // case TRACE_CHECKED_STRING:
        // return new TraceCheckedLogging(logger);
        case LITERAL:
            return new LiteralLogging(logger);
        case LEVEL_CHECKED_LITERAL:
            return new LevelCheckedLogging(logger);
        case CONCAT_STRING:
            return new ConcatStringLogging(logger);
        case JAVA_FORMATTED_STRING:
            return new JavaFormattedStringLogging(logger);
        default:
            throw new RuntimeException("Unsupported type of factory");
        }
    }

}

/**
 * TRACE logs a string literal wrapped within TRACE level check.
 * 
 * @author Michael.Zhou
 */
class TraceCheckedLogging extends TestScenario<Logger> {

    public TraceCheckedLogging(Logger logger) {
        super(logger);
    }

    @Override
    public void run() {
        if (logger.isTraceEnabled()) {
            logger.trace("Hello world from test log");
        }
    }

}

/**
 * Logs a string literal without level check.
 * 
 * @author Michael.Zhou
 */
class LiteralLogging extends TestScenario<Logger> {

    public LiteralLogging(Logger logger) {
        super(logger);
    }

    @Override
    public void run() {
        logger.info("Hello world from test log");
    }

}

/**
 * Logs a string literal wrapped within level check.
 * 
 * @author Michael.Zhou
 */
class LevelCheckedLogging extends TestScenario<Logger> {

    public LevelCheckedLogging(Logger logger) {
        super(logger);
    }

    @Override
    public void run() {
        if (logger.isInfoEnabled()) {
            logger.info("Hello world from test log");
        }
    }

}

/**
 * Logs a string concatenated via '+' wrapped within level check.
 * 
 * @author Michael.Zhou
 */
class ConcatStringLogging extends TestScenario<Logger> {

    public ConcatStringLogging(Logger logger) {
        super(logger);
    }

    @Override
    public void run() {
        String world = "world";
        String testLog = "test log";
        if (logger.isInfoEnabled()) {
            logger.info("Hello " + world + " from " + testLog);
        }
    }

}

/**
 * Logs a string via Java built-in formatting wrapped within level check.
 * 
 * @author Michael.Zhou
 */
class JavaFormattedStringLogging extends TestScenario<Logger> {

    public JavaFormattedStringLogging(Logger logger) {
        super(logger);
    }

    @Override
    public void run() {
        String world = "world";
        String testLog = "test log";
        if (logger.isInfoEnabled()) {
            logger.info(String.format("Hello %s from %s", world, testLog));
        }
    }

}
