package com.test.logging.log4jv1;

import org.apache.log4j.Logger;

import com.test.logging.common.BaseTestScenarioFactory;
import com.test.logging.common.TestFactoryType;
import com.test.logging.common.TestScenario;
import com.test.logging.common.TestScenarioOptions;

/**
 * Factory to create units of logging test work to use with Log4j v1 native APIs.
 * 
 * @author Michael.Zhou
 */
public class TestFactory extends BaseTestScenarioFactory<Logger> {

    /** Supported types */
    private static final TestFactoryType[] types = new TestFactoryType[] { TestFactoryType.LITERAL,
            TestFactoryType.LEVEL_CHECKED_LITERAL, TestFactoryType.CONCAT_STRING, TestFactoryType.JAVA_FORMATTED_STRING };

    /** Type of factory */
    private TestFactoryType type;

    /** Logger */
    private Logger logger;

    public TestFactory(TestFactoryType type, Logger logger) {
        this.type = type;
        this.logger = logger;
    }

    public static TestFactoryType[] getSupportedTypes() {
        return types;
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
            logger.info(TestFactory.LOG_MSG);
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
                logger.info(TestFactory.LOG_MSG);
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
        String varPart1 = TestFactory.VARIABLE_STRING_PART_1;
        String varPart2 = TestFactory.VARIABLE_STRING_PART_2;
        for (int i = 0; i < options.getNumberOfRepeats(); i++) {
            if (logger.isInfoEnabled()) {
                logger.info(TestFactory.FIXED_STRING_PART_1 + varPart1 + TestFactory.FIXED_STRING_PART_2 + varPart2
                        + TestFactory.FIXED_STRING_PART_3);
            }
        }
    }

}

/**
 * Logs a string via String.format wrapped within level check.
 * 
 * @author Michael.Zhou
 */
class JavaFormattedStringLogging extends TestScenario<Logger> {

    public JavaFormattedStringLogging(Logger logger, TestScenarioOptions options) {
        super(logger, options);
    }

    @Override
    public void run() {
        String varPart1 = TestFactory.VARIABLE_STRING_PART_1;
        String varPart2 = TestFactory.VARIABLE_STRING_PART_2;
        for (int i = 0; i < options.getNumberOfRepeats(); i++) {
            if (logger.isInfoEnabled()) {
                logger.info(String.format(TestFactory.JAVA_STRING_FORMAT, varPart1, varPart2));
            }
        }
    }

}
