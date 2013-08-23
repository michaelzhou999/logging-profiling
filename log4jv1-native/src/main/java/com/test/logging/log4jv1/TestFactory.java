package com.test.logging.log4jv1;

import org.apache.log4j.Logger;

import com.test.logging.common.ITestScenarioFactory;
import com.test.logging.common.TestScenario;
import com.test.logging.common.TestScenarioOptions;

/**
 * Factory to create units of logging test work to use with Log4j v1 native APIs.
 * 
 * @author Michael.Zhou
 */
public class TestFactory implements ITestScenarioFactory<Logger> {

    public static enum Type {
        LITERAL("String literal"),
        LEVEL_CHECKED_LITERAL("Level checked string literal"),
        CONCAT_STRING("'+' concatenation"),
        JAVA_FORMATTED_STRING("String.format");

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
    public TestScenario<Logger> createTestScenario(TestScenarioOptions options) {
        switch (this.type) {
        case LITERAL:
            return new LiteralLogging(logger, options);
        case LEVEL_CHECKED_LITERAL:
            return new LevelCheckedLogging(logger, options);
        case CONCAT_STRING:
            return new ConcatStringLogging(logger, options);
        case JAVA_FORMATTED_STRING:
            return new JavaFormattedStringLogging(logger, options);
        default:
            throw new RuntimeException("Unsupported type of factory");
        }
    }

}

/**
 * Logs a string literal without level check.
 * 
 * @author Michael.Zhou
 */
class LiteralLogging extends TestScenario<Logger> {

    public LiteralLogging(Logger logger, TestScenarioOptions options) {
        super(logger, options);
    }

    @Override
    public void run() {
        for (int i = 0; i < options.getNumberOfRepeats(); i++) {
            logger.info("Hello world from test log");
        }
    }

}

/**
 * Logs a string literal wrapped within level check.
 * 
 * @author Michael.Zhou
 */
class LevelCheckedLogging extends TestScenario<Logger> {

    public LevelCheckedLogging(Logger logger, TestScenarioOptions options) {
        super(logger, options);
    }

    @Override
    public void run() {
        for (int i = 0; i < options.getNumberOfRepeats(); i++) {
            if (logger.isInfoEnabled()) {
                logger.info("Hello world from test log");
            }
        }
    }

}

/**
 * Logs a string concatenated via '+' wrapped within level check.
 * 
 * @author Michael.Zhou
 */
class ConcatStringLogging extends TestScenario<Logger> {

    public ConcatStringLogging(Logger logger, TestScenarioOptions options) {
        super(logger, options);
    }

    @Override
    public void run() {
        String world = "world";
        String testLog = "test log";
        for (int i = 0; i < options.getNumberOfRepeats(); i++) {
            if (logger.isInfoEnabled()) {
                logger.info("Hello " + world + " from " + testLog);
            }
        }
    }

}

/**
 * Logs a string via Java built-in formatting wrapped within level check.
 * 
 * @author Michael.Zhou
 */
class JavaFormattedStringLogging extends TestScenario<Logger> {

    public JavaFormattedStringLogging(Logger logger, TestScenarioOptions options) {
        super(logger, options);
    }

    @Override
    public void run() {
        String world = "world";
        String testLog = "test log";
        for (int i = 0; i < options.getNumberOfRepeats(); i++) {
            if (logger.isInfoEnabled()) {
                logger.info(String.format("Hello %s from %s", world, testLog));
            }
        }
    }

}
