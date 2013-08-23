package com.test.logging.common;

/**
 * Template factory that creates units of logging test work. Derived classes
 * must override the createUnitWork method.
 * 
 * @author Michael.Zhou
 */
abstract public interface ITestScenarioFactory<L> {

    /**
     * Creates a unit of logging test work
     * 
     * @param options test scenario options
     */
    public TestScenario<L> createTestScenario(TestScenarioOptions options);

}
