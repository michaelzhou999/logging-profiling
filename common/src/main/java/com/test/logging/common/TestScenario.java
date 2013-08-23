package com.test.logging.common;

/**
 * Abstract template class for a piece of logging test work. Derived classes
 * must override the run method.
 * 
 * @author Michael.Zhou
 */
public abstract class TestScenario<L> {

    /** Logger */
    protected L logger;

    /** Test scenario options */
    protected TestScenarioOptions options;

    public TestScenario(L logger, TestScenarioOptions options) {
        this.logger = logger;
        this.options = options;
    }

    /**
     * Piece of logging work to be executed in a thread
     */
    abstract public void run();

}
