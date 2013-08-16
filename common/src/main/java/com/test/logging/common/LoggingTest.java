package com.test.logging.common;

/** 
 * The logging test launches multiple threads and executes logging statements
 * repeatedly within each single thread.
 * 
 * @author Michael.Zhou
 */
public class LoggingTest<L> {

    /** Test description */
    protected String description;

    /** Factory to create units of logging test work */
    protected TestUnitWorkFactory<L> unitWorkFactory;

    /** Logging test options */
    protected TestOptions options;

    public LoggingTest(String description, TestUnitWorkFactory<L> unitWorkFactory, TestOptions options) {
        this.description = description;
        this.options = options;
        this.unitWorkFactory = unitWorkFactory;
    }
    
    public void run() {
        Thread[] threads = new Thread[options.getNumberOfThreads()];
        for (int i = 0; i < options.getNumberOfThreads(); i++) {
            final TestUnitWork<L> unitWork = unitWorkFactory.createUnitWork();
            threads[i] = new Thread(new Runnable() {
                public void run() {
                    for (int j = 0; j < options.getNumberOfRepeats(); j++) {
                        unitWork.run();
                    }
                }
            }, "Thread-" + i);
        }

        System.out.println("");
        System.out.println("Running: " + description + " ... ");
        System.out.println("********************************************");
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < options.getNumberOfThreads(); i++) {
            threads[i].start();
        }
        
        // Wait till all threads are finished logging
        try {
            for (int i = 0; i < options.getNumberOfThreads(); i++) {
                threads[i].join();
            }
        }
        catch (InterruptedException ie) {
            System.out.println("!!!!!!!!!!!   ERROR   !!!!!!!!!!");
            return;
        }
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        System.out.println("Number of threads: " + options.getNumberOfThreads());
        System.out.println("Total number of logging statements: " + options.getNumberOfThreads() * options.getNumberOfRepeats());
        System.out.println("Time used: " + duration + " milli-seconds.");
        System.out.println("");
    }

}
