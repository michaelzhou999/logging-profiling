package com.test.logging.common;

/**
 * Template factory that creates units of logging test work. Derived classes must override the createUnitWork method.
 * 
 * @author Michael.Zhou
 */
abstract public class BaseTestScenarioFactory<L> {

    /** Log message of 2000 bytes */
    public static final String LOG_MSG = "00000000000000000000" + "11111111111111111111" + "22222222222222222222"
            + "33333333333333333333" + "44444444444444444444" + "55555555555555555555" + "66666666666666666666"
            + "77777777777777777777" + "88888888888888888888" + "99999999999999999999";

    /** Log message format used in SLF4J style formatting */
    public static final String SLF4J_STRING_FORMAT = "00000000000000000000" + "11111111111111111111" + "{}"
            + "33333333333333333333" + "44444444444444444444" + "55555555555555555555" + "66666666666666666666"
            + "77777777777777777777" + "{}" + "99999999999999999999";

    /** Log message format used in String.format */
    public static final String JAVA_STRING_FORMAT = "00000000000000000000" + "11111111111111111111" + "%s"
            + "33333333333333333333" + "44444444444444444444" + "55555555555555555555" + "66666666666666666666"
            + "77777777777777777777" + "%s" + "99999999999999999999";

    /** Part 1 of fixed portion used in string concatenation */
    public static final String FIXED_STRING_PART_1 = "00000000000000000000" + "11111111111111111111";
    /** Part 2 of fixed portion used in string concatenation */
    public static final String FIXED_STRING_PART_2 = "33333333333333333333" + "44444444444444444444"
            + "55555555555555555555" + "66666666666666666666" + "77777777777777777777";
    /** Part 3 of fixed portion used in string concatenation */
    public static final String FIXED_STRING_PART_3 = "99999999999999999999";

    /** Part 1 of variable portion used in string concatenation/formatting */
    public static final String VARIABLE_STRING_PART_1 = "22222222222222222222";
    /** Part 2 of variable portion used in string concatenation/formatting */
    public static final String VARIABLE_STRING_PART_2 = "88888888888888888888";

    /**
     * Creates a logging test scenario
     * 
     * @param options test scenario options
     */
    abstract public TestScenario<L> createTestScenario(TestScenarioOptions options);

}
