package com.test.logging.common;

/**
 * Abstract template factory that creates units of logging test work.
 * Derived classes must override the createUnitWork method.
 * 
 * @author Michael.Zhou
 */
abstract public class TestUnitWorkFactory<L> {
    
    /** Creates a unit of logging test work */
    abstract public TestUnitWork<L> createUnitWork();
    
}
