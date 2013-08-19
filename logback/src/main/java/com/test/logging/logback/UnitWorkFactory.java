package com.test.logging.logback;

import com.test.logging.common.TestUnitWork;
import com.test.logging.common.TestUnitWorkFactory;
import org.slf4j.Logger;


/**
 * Factory to create units of logging test work to use with SLF4J APIs.
 * 
 * @author Michael.Zhou
 */
public class UnitWorkFactory extends TestUnitWorkFactory<Logger> {
    
    public static enum Type {
        TRACE_CHECKED_STRING("TRACE Level checked string literal (ms)"),
        DEBUG_CHECKED_STRING("DEBUG Level checked string literal (ms)"),
        INFO_CHECKED_STRING("INFO Level checked string literal (ms)"),
        LITERAL("INFO Level string literal (ms)"),
        CONCAT_STRING("INFO Level checked + concatenated string (ms)"),
        SLF4J_FORMATTED_STRING("INFO Level checked SLF4J formatted string (ms)"),
        JAVA_FORMATTED_STRING("INFO Level checked Java built-in formatted string (ms)");

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
            case LITERAL:
                return new LiteralLogging(logger);
            case INFO_CHECKED_STRING:
                return new InfoCheckedLogging(logger);
            case DEBUG_CHECKED_STRING:
                return new DebugCheckedLogging(logger);
            case TRACE_CHECKED_STRING:
                return new TraceCheckedLogging(logger);
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
 * INFO logs a string literal.
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
 * INFO logs a string literal wrapped within INFO level check.
 * 
 * @author Michael.Zhou
 */
class InfoCheckedLogging extends TestUnitWork<Logger> {
    
    public InfoCheckedLogging(Logger logger) {
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
 * DEBUG logs a string literal wrapped within DEBUG level check.
 * 
 * @author Michael.Zhou
 */
class DebugCheckedLogging extends TestUnitWork<Logger> {
    
    public DebugCheckedLogging(Logger logger) {
        super(logger);
    }

    @Override
    public void run() {
        if (logger.isDebugEnabled()) {
            logger.debug("Hello world from test log");
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
 * INFO logs a concatenated string within INFO level check.
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
 * INFO logs a SLF4J-style formatted string within INFO level check.
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
 * INFO logs a Java built-in formatted string within INFO level check.
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
