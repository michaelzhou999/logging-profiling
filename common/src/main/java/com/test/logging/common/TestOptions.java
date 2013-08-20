package com.test.logging.common;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;


/** Logging profiler options
 *
 * @author Michael.Zhou
 */
public class TestOptions {

    /** Maximum number of times logging statements should be executed in a thread */
    protected static int MAX_NUMBER_OF_REPEATS = 10 * 1000 * 1000;  // 10 million
    /** Minimum number of times logging statements should be executed in a thread */
    protected static int MIN_NUMBER_OF_REPEATS = 1;
    /** Default number of repeating logging statements */
    protected static int DEFAULT_NUMBER_OF_REPEATS = 10 * 1000;  // 10 thousand
    /** Number of repeating logging statements */
    protected int NUMBER_OF_REPEATS = DEFAULT_NUMBER_OF_REPEATS;

    /** Maximum number of threads that concurrently write to log files */
    protected static final int MAX_NUMBER_OF_THREADS = 100 * 1000;  // 100 thousand
    /** Minimum number of threads that concurrently write to log files */
    protected static final int MIN_NUMBER_OF_THREADS = 1;
    /** Default number of threads */
    protected static final int DEFAULT_NUMBER_OF_THREADS = 10;
    /** Current number of threads */
    protected int NUMBER_OF_THREADS = DEFAULT_NUMBER_OF_THREADS;

    /** Maximum number of writes allowed in a single run */
    protected static final int MAX_NUMBER_OF_WRITES = 10 * 1000 * 1000;  // 10 million
    /** Minimum number of writes allowed in a single run */
    protected static final int MIN_NUMBER_OF_WRITES = 1000;  // 1000
    /** Default number of writes */
    protected static final int DEFAULT_NUMBER_OF_WRITES = 1 * 1000 * 1000;  // 1 million
    /** Current number of writes */
    protected int NUMBER_OF_WRITES = DEFAULT_NUMBER_OF_WRITES;

    /** Maximum number of runs */
    protected static final int MAX_NUMBER_OF_RUNS = 50;
    /** Minimum number of runs */
    protected static final int MIN_NUMBER_OF_RUNS = 1;
    /** Default number of runs */
    protected static final int DEFAULT_NUMBER_OF_RUNS = 3;
    /** Current number of runs */
    protected int NUMBER_OF_RUNS = DEFAULT_NUMBER_OF_RUNS;

    /** Thread series for each test run. Used with --write option. */
    protected int[] THREAD_SERIES = new int[]{1, 2, 5, 10, 20, 50, 100, 200, 500, 1000};

    /** Command line options backed by Apache Commons CLI library */
    protected Options options = new Options();

    /** Flag to indicate multiple runs */
    protected boolean multipleRuns = false;

    public TestOptions() {
        // Build command line options
        Option help = new Option("h", "help", false, "usage");
        options.addOption(help);

        Option thread = OptionBuilder.withArgName("threads")
            .hasArg()
            .withDescription("number of threads: " + MIN_NUMBER_OF_THREADS + " ~ " + MAX_NUMBER_OF_THREADS)
            .create("t");
        thread.setLongOpt("thread");
        options.addOption(thread);

        Option repeat = OptionBuilder.withArgName("repeats")
            .hasArg()
            .withDescription("number of times each thread executes the intended logging statement: " + MIN_NUMBER_OF_REPEATS + " ~ " + MAX_NUMBER_OF_REPEATS)
            .create("r");
        repeat.setLongOpt("repeat");
        options.addOption(repeat);

        Option write = OptionBuilder.withArgName("writes")
            .hasArg()
            .withDescription("number of writes in each run (number of threads/repeats will be ignored if this option is specified): " + MIN_NUMBER_OF_WRITES + " ~ " + MAX_NUMBER_OF_WRITES)
            .create("w");
        write.setLongOpt("write");
        options.addOption(write);

        Option run = OptionBuilder.withArgName("runs")
            .hasArg()
            .withDescription("number of runs: " + MIN_NUMBER_OF_RUNS + " ~ " + MAX_NUMBER_OF_RUNS)
            .create("n");
        run.setLongOpt("run");
        options.addOption(run);
    }

    /** Parse CLI options. If invalid options are seen, print out usage,
     *  use default settings, and keep executing.
     *
     * @param args Command line argument list
     *
     * @return true if execution should continue; false if command line options indicate printing
     *         the help message.
     */
    public boolean parseCliOptions(String appName, String[] args) {
        CommandLineParser parser = new PosixParser();
        CommandLine line = null;
        try {
            line = parser.parse(options, args);
        }
        catch (ParseException pe) {
            System.out.println("Invalid CLI options.");
            pe.printStackTrace();
            return true;
        }

        if (line.hasOption("help")) {
            // If --help is specified on CLI, all other options will be ignored, usage will be printed
            // and program exits.
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp(appName, options);
            return false;
        }

        if (!line.hasOption("write")) {
            if (line.hasOption("thread")) {
                try {
                    Integer nThread = Integer.valueOf(line.getOptionValue("thread"));
                    if (MIN_NUMBER_OF_THREADS <= nThread && nThread <= MAX_NUMBER_OF_THREADS) {
                        NUMBER_OF_THREADS = nThread;
                    }
                }
                finally {
                    // Ignore any NumberFormatException and use default
                }
            }

            if (line.hasOption("repeat")) {
                try {
                    Integer nRepeat = Integer.valueOf(line.getOptionValue("repeat"));
                    if (MIN_NUMBER_OF_REPEATS <= nRepeat && nRepeat <= MAX_NUMBER_OF_REPEATS) {
                        NUMBER_OF_REPEATS = nRepeat;
                    }
                }
                finally {
                    // Ignore any NumberFormatException and use default
                }
            }
        }
        else {
            multipleRuns = true;
            try {
                Integer nWrite = Integer.valueOf(line.getOptionValue("write"));
                if (MIN_NUMBER_OF_WRITES <= nWrite && nWrite <= MAX_NUMBER_OF_WRITES) {
                    // Round the number of writes to nearest 1000
                    NUMBER_OF_WRITES = nWrite / 1000 * 1000;
                    System.out.println("number of writes: " + NUMBER_OF_WRITES);
                }
            }
            finally {
                // Ignore any NumberFormatException and use default
            }

            if (line.hasOption("run")) {
                try {
                    Integer nRun = Integer.valueOf(line.getOptionValue("run"));
                    if (MIN_NUMBER_OF_RUNS <= nRun && nRun <= MAX_NUMBER_OF_RUNS) {
                        NUMBER_OF_RUNS = nRun;
                        System.out.println("number of runs: " + NUMBER_OF_RUNS);
                    }
                }
                finally {
                    // Ignore any NumberFormatException and use default
                }
            }
        }

        return true;
    }

    /** Returns the CLI options object */
    public Options getOptions() {
        return options;
    }

    /** Returns the number of threads concurrently running */
    public int getNumberOfThreads() {
        return NUMBER_OF_THREADS;
    }

    /** Returns the number of times each thread executes logging statement */
    public int getNumberOfRepeats() {
        return NUMBER_OF_REPEATS;
    }

    /** Returns the number of writes in a single run */
    public int getNumberOfWrites() {
        return NUMBER_OF_WRITES;
    }

    /** Returns the number of runs in each single run */
    public int getNumberOfRuns() {
        return NUMBER_OF_RUNS;
    }

    /** Returns true if the test should run multiple times */
    public boolean isMultipleRuns() {
        return multipleRuns;
    }

    /** Returns thread series */
    public int[] getThreadSeries() {
        return THREAD_SERIES;
    }

}

