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

    public TestScenario(L logger) {
        this.logger = logger;
    }

    /** Piece of logging work to be executed over and over again in a thread */
    abstract public void run();

}
