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

    /**
     * Run a unit of logging piece in multiple threads.
     *
     * @param nThreads number of threads that concurrently log
     * @param nRepeats number of times to call logging in a thread
     */
    public void oneRun(final int nThreads, final int nRepeats) {
        Thread[] threads = new Thread[nThreads];
        for (int i = 0; i < nThreads; i++) {
            final TestUnitWork<L> unitWork = unitWorkFactory.createUnitWork();
            threads[i] = new Thread(new Runnable() {
                    public void run() {
                        for (int j = 0; j < nRepeats; j++) {
                            unitWork.run();
                        }
                    }
                }, "Thread-" + i);
        }

        System.out.println("");
        System.out.println("Running: " + description + " ... ");
        System.out.println("********************************************");
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < nThreads; i++) {
            threads[i].start();
        }

        // Wait till all threads are finished logging
        try {
            for (int i = 0; i < nThreads; i++) {
                threads[i].join();
            }
        }
        catch (InterruptedException ie) {
            System.out.println("!!!!!!!!!!!   ERROR   !!!!!!!!!!");
            return;
        }
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        System.out.println("Number of threads: " + nThreads);
        System.out.println("Number of repeats: " + nRepeats);
        System.out.println("Total number of logging statements: " + nThreads * nRepeats);
        System.out.println("Time used: " + duration + " milli-seconds.");
        System.out.println("");
    }

    public void run() {
        if (!options.isMultipleRuns()) {
            oneRun(options.getNumberOfThreads(), options.getNumberOfRepeats());
        }
        else {
            final int[] ts = options.getThreadSeries();
            for (int r = 0; r < options.getNumberOfRuns(); r++) {
                for (int tsi = 0; tsi < ts.length; tsi++) {
                    final int nThreads = ts[tsi];
                    final int nRepeats = options.getNumberOfWrites() / nThreads;
                    oneRun(nThreads, nRepeats);
                }
            }
        }
    }

}
