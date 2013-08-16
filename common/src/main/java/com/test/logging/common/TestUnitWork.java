package com.test.logging.common;

/** 
 * Abstract template class for a piece of logging test work.
 * Derived classes must override the run method.
 * 
 * @author Michael.Zhou
 */
public abstract class TestUnitWork<L> {
    
    /** Logger */
    protected L logger;
    
    public TestUnitWork(L logger) {
        this.logger = logger;
    }

    /** Piece of logging work to be executed over and over again in a thread */
    abstract public void run();

}
