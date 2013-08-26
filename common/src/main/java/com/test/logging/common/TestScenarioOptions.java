package com.test.logging.common;

/**
 * Options for individual test scenario.
 * 
 * @author Michael.Zhou
 */
public class TestScenarioOptions {

    /** Number of repeats */
    private int nRepeats;

    public TestScenarioOptions(int nRepeats) {
        this.nRepeats = nRepeats;
    }

    public int getNumberOfRepeats() {
        return nRepeats;
    }

}
