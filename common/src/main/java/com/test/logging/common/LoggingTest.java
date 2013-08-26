package com.test.logging.common;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * The logging test launches multiple threads and executes logging statements repeatedly within each single thread. Time
 * spent on execution will be recorded to the results file.
 * 
 * @author Michael.Zhou
 */
public class LoggingTest<L> {

    /** Test description */
    protected String description;

    /** Factory to create units of logging test work */
    protected ITestScenarioFactory<L> testFactory;

    /** Logging test options */
    protected ProfilerOptions options;

    public LoggingTest(String description, ITestScenarioFactory<L> testFactory, ProfilerOptions options) {
        this.description = description;
        this.options = options;
        this.testFactory = testFactory;
    }

    /**
     * Run a unit of logging piece in multiple threads.
     * 
     * @param nThreads number of threads that concurrently log
     * @param nRepeats number of times to call logging in a thread
     * @param output where to write results
     */
    public void oneRun(final int nThreads, final int nRepeats, final PrintStream output) {
        Thread[] threads = new Thread[nThreads];
        for (int i = 0; i < nThreads; i++) {
            final TestScenario<L> unitWork = testFactory.createTestScenario(new TestScenarioOptions(nRepeats));
            threads[i] = new Thread(new Runnable() {
                public void run() {
                    unitWork.run();
                }
            }, "Thread-" + i);
        }

        System.out.println("");
        System.out.println("Running: " + description + " ... ");
        long startTime = System.nanoTime();
        for (int i = 0; i < nThreads; i++) {
            threads[i].start();
        }

        // Wait till all threads are done with logging
        try {
            for (int i = 0; i < nThreads; i++) {
                threads[i].join();
            }
        } catch (InterruptedException ie) {
            System.out.println("Thread is interrupted!");
            return;
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / (1000 * 1000);

        System.out.println("Number of threads: " + nThreads);
        System.out.println("Number of repeats: " + nRepeats);
        System.out.println("Total number of logging statements: " + nThreads * nRepeats);
        System.out.println("Time used: " + duration + " milli-seconds.");
        System.out.println("");

        if (output != null) {
            output.print("\"");
            output.print(description);
            output.print("\",\"");
            output.print(nThreads);
            output.print("*");
            output.print(nRepeats);
            output.print("\",");
            output.print(duration);
            output.println();
        }
    }

    /** Warm up JVM with 1 thread. */
    protected void warmupJVM() {
        oneRun(1, options.getNumberOfWarmups(), null);
    }

    /** Main method to run a logging test . */
    public void run() {
        PrintStream output = null;
        String outputFilename = options.getResultsFilename();
        if (outputFilename != null) {
            try {
                // Append to file
                output = new PrintStream(new FileOutputStream(outputFilename, true));
            } catch (IOException ioe) {
                System.err.println("Failed to open result output file " + outputFilename);
            }
        } else {
            System.err.println("No result summary file is specified.");
        }

        // Warm up JVM to get more accurate reading on time spent
        warmupJVM();

        for (int r = 0; r < options.getNumberOfRuns(); r++) {
            if (!options.isUseThreadSeries()) {
                oneRun(options.getNumberOfThreads(), options.getNumberOfRepeats(), output);
            } else {
                final int[] ts = options.getThreadSeries();
                for (int tsi = 0; tsi < ts.length; tsi++) {
                    final int nThreads = ts[tsi];
                    final int nRepeats = options.getNumberOfWrites() / nThreads;
                    oneRun(nThreads, nRepeats, output);
                }
            }
        }

        if (output != null) {
            output.flush();
            output.close();
        }
    }

}
