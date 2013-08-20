package com.test.logging.logback;

import com.test.logging.common.TestUnitWork;
import com.test.logging.common.TestUnitWorkFactory;
import org.slf4j.Logger;


/**
 * Factory to create units of logging test work to use with Logback APIs.
 * 
 * @author Michael.Zhou
 */
public class UnitWorkFactory extends TestUnitWorkFactory<Logger> {
    
    public static enum Type {
        //TRACE_CHECKED_STRING("TRACE Level checked string literal (ms)"),
        LITERAL("String literal (ms)"),
        LEVEL_CHECKED_LITERAL("Level checked string literal (ms)"),
        CONCAT_STRING("Level checked string concatenated via + (ms)"),
        SLF4J_FORMATTED_STRING("Level checked string via SLF4J formatting (ms)"),
        JAVA_FORMATTED_STRING("Level checked string via Java built-in formatting (ms)");

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
    
    public UnitWorkFactory(Type type, Logger logger) {
        this.type = type;
        this.logger = logger;
    }
    
    /** All unit works created by this factory share the same logger. */
    @Override
    public TestUnitWork<Logger> createUnitWork() {
        switch (this.type) {
            //case TRACE_CHECKED_STRING:
            //    return new TraceCheckedLogging(logger);
            case LITERAL:
                return new LiteralLogging(logger);
            case LEVEL_CHECKED_LITERAL:
                return new LevelCheckedLogging(logger);
            case CONCAT_STRING:
                return new ConcatStringLogging(logger);
            case SLF4J_FORMATTED_STRING:
                return new FormattedStringLogging(logger);
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
class TraceCheckedLogging extends TestUnitWork<Logger> {
    
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
class LiteralLogging extends TestUnitWork<Logger> {
    
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
class LevelCheckedLogging extends TestUnitWork<Logger> {
    
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
class ConcatStringLogging extends TestUnitWork<Logger> {
    
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
 * Logs a string via SLF4J-style formatting wrapped within level check.
 * 
 * @author Michael.Zhou
 */
class FormattedStringLogging extends TestUnitWork<Logger> {

    public FormattedStringLogging(Logger logger) {
        super(logger);
    }
    
    @Override
    public void run() {
        String world = "world";
        String testLog = "test log";
        if (logger.isInfoEnabled()) {
            logger.info("Hello {} from {}", world, testLog);
        }
    }

}

/**
 * Logs a string via Java built-in formatting wrapped within level check.
 * 
 * @author Michael.Zhou
 */
class JavaFormattedStringLogging extends TestUnitWork<Logger> {

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
