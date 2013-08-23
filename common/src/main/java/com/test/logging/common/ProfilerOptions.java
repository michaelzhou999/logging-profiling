package com.test.logging.common;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

/**
 * Logging profiler options
 * 
 * @author Michael.Zhou
 */
public class ProfilerOptions {

    /**
     * Maximum number of times logging statements should be executed in a thread
     */
    protected static int MAX_NUMBER_OF_REPEATS = 10 * 1000 * 1000; // 10 million
    /**
     * Minimum number of times logging statements should be executed in a thread
     */
    protected static int MIN_NUMBER_OF_REPEATS = 1;
    /** Default number of repeating logging statements */
    protected static int DEFAULT_NUMBER_OF_REPEATS = 10 * 1000; // 10 thousand
    /** Number of repeating logging statements */
    protected int NUMBER_OF_REPEATS = DEFAULT_NUMBER_OF_REPEATS;

    /** Maximum number of threads that concurrently write to log files */
    protected static final int MAX_NUMBER_OF_THREADS = 100 * 1000; // 100 thousand
    /** Minimum number of threads that concurrently write to log files */
    protected static final int MIN_NUMBER_OF_THREADS = 1;
    /** Default number of threads */
    protected static final int DEFAULT_NUMBER_OF_THREADS = 10;
    /** Current number of threads */
    protected int NUMBER_OF_THREADS = DEFAULT_NUMBER_OF_THREADS;

    /** Maximum number of writes allowed in a single run */
    protected static final int MAX_NUMBER_OF_WRITES = 10 * 1000 * 1000; // 10 million
    /** Minimum number of writes allowed in a single run */
    protected static final int MIN_NUMBER_OF_WRITES = 1000; // 1000
    /** Default number of writes */
    protected static final int DEFAULT_NUMBER_OF_WRITES = 1 * 1000 * 1000; // 1 million
    /** Current number of writes */
    protected int NUMBER_OF_WRITES = DEFAULT_NUMBER_OF_WRITES;

    /** Maximum number of runs */
    protected static final int MAX_NUMBER_OF_RUNS = 50;
    /** Minimum number of runs */
    protected static final int MIN_NUMBER_OF_RUNS = 1;
    /** Default number of runs */
    protected static final int DEFAULT_NUMBER_OF_RUNS = 1;
    /** Current number of runs */
    protected int NUMBER_OF_RUNS = DEFAULT_NUMBER_OF_RUNS;

    /** Thread series for each test run, used with --write option */
    protected int[] THREAD_SERIES = new int[] { 1, 2, 5, 10, 20, 50, 100, 200 };
    /** Indication of using thread series */
    protected boolean useThreadSeries;

    /** Default Filename of test results summary */
    protected String defaultResultsFilename;
    /** File name of test results summary */
    protected String resultsFilename;

    /** Command line options backed by Apache Commons CLI library */
    protected Options options = new Options();

    /** CLI arguments */
    private static final String ARG_HELP = "help";
    private static final String ARG_THREAD = "thread";
    private static final String ARG_REPEAT = "repeat";
    private static final String ARG_WRITE = "write";
    private static final String ARG_RUN = "run";
    private static final String ARG_FILENAME = "filename";

    public ProfilerOptions() {
        // Build command line options
        Option help = new Option("h", ARG_HELP, false, "usage");
        options.addOption(help);

        Option thread = OptionBuilder.withArgName(ARG_THREAD).hasArg()
                .withDescription("number of threads: " + MIN_NUMBER_OF_THREADS + " ~ " + MAX_NUMBER_OF_THREADS)
                .create("t");
        thread.setLongOpt(ARG_THREAD);
        options.addOption(thread);

        Option repeat = OptionBuilder
                .withArgName(ARG_REPEAT)
                .hasArg()
                .withDescription(
                        "number of times each thread executes the intended logging statement: " + MIN_NUMBER_OF_REPEATS
                                + " ~ " + MAX_NUMBER_OF_REPEATS).create("r");
        repeat.setLongOpt(ARG_REPEAT);
        options.addOption(repeat);

        Option write = OptionBuilder
                .withArgName(ARG_WRITE)
                .hasArg()
                .withDescription(
                        "number of writes in each run (number of threads/repeats will be ignored if this option is specified): "
                                + MIN_NUMBER_OF_WRITES + " ~ " + MAX_NUMBER_OF_WRITES).create("w");
        write.setLongOpt(ARG_WRITE);
        options.addOption(write);

        Option run = OptionBuilder.withArgName(ARG_RUN).hasArg()
                .withDescription("number of runs: " + MIN_NUMBER_OF_RUNS + " ~ " + MAX_NUMBER_OF_RUNS).create("n");
        run.setLongOpt(ARG_RUN);
        options.addOption(run);

        Option filename = OptionBuilder.withArgName(ARG_FILENAME).hasArg()
                .withDescription("filename of results summary").create("f");
        filename.setLongOpt(ARG_FILENAME);
        options.addOption(filename);
    }

    /** Prints usage */
    public void printUsage(String appName) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp(appName, options);
    }

    /**
     * Parse CLI options. If invalid options are seen, print out usage, use default settings, and keep executing.
     * 
     * @param args Command line argument list
     * 
     * @return true if execution should continue; false if command line options indicate printing the help message.
     */
    public boolean parseCliOptions(String appName, String[] args) {
        CommandLineParser parser = new PosixParser();
        CommandLine line = null;
        try {
            line = parser.parse(options, args);
        } catch (ParseException pe) {
            System.out.println("Invalid CLI options.");
            printUsage(appName);
            return true;
        }

        if (line.hasOption(ARG_HELP)) {
            // If --help is specified on CLI, all other options will be ignored,
            // usage will be printed
            // and program exits.
            printUsage(appName);
            return false;
        }

        if (line.hasOption(ARG_FILENAME)) {
            resultsFilename = line.getOptionValue(ARG_FILENAME);
        }

        if (!line.hasOption(ARG_WRITE)) {
            if (line.hasOption(ARG_THREAD)) {
                try {
                    Integer nThread = Integer.valueOf(line.getOptionValue(ARG_THREAD));
                    if (MIN_NUMBER_OF_THREADS <= nThread && nThread <= MAX_NUMBER_OF_THREADS) {
                        NUMBER_OF_THREADS = nThread;
                    }
                } finally {
                    // Ignore any NumberFormatException and use default
                }
            }

            if (line.hasOption(ARG_REPEAT)) {
                try {
                    Integer nRepeat = Integer.valueOf(line.getOptionValue(ARG_REPEAT));
                    if (MIN_NUMBER_OF_REPEATS <= nRepeat && nRepeat <= MAX_NUMBER_OF_REPEATS) {
                        NUMBER_OF_REPEATS = nRepeat;
                    }
                } finally {
                    // Ignore any NumberFormatException and use default
                }
            }
        } else {
            useThreadSeries = true;
            try {
                Integer nWrites = Integer.valueOf(line.getOptionValue(ARG_WRITE));
                if (MIN_NUMBER_OF_WRITES <= nWrites && nWrites <= MAX_NUMBER_OF_WRITES) {
                    // Round the number of writes to nearest 1000
                    NUMBER_OF_WRITES = nWrites / 1000 * 1000;
                    System.out.println("number of writes: " + NUMBER_OF_WRITES);
                }
            } finally {
                // Ignore any NumberFormatException and use default
            }
        }

        if (line.hasOption(ARG_RUN)) {
            try {
                Integer nRun = Integer.valueOf(line.getOptionValue(ARG_RUN));
                if (MIN_NUMBER_OF_RUNS <= nRun && nRun <= MAX_NUMBER_OF_RUNS) {
                    NUMBER_OF_RUNS = nRun;
                    System.out.println("number of runs: " + NUMBER_OF_RUNS);
                }
            } finally {
                // Ignore any NumberFormatException and use default
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

    /** Returns thread series */
    public int[] getThreadSeries() {
        return THREAD_SERIES;
    }

    /** Returns true if using thread series */
    public boolean isUseThreadSeries() {
        return useThreadSeries;
    }

    /** Returns results filename */
    public String getResultsFilename() {
        if (resultsFilename != null) {
            return resultsFilename;
        } else {
            return defaultResultsFilename;
        }
    }

    /**
     * Sets default results filename that will be used if no filename is passed through command line
     */
    public void setDefaultResultsFilename(String filename) {
        defaultResultsFilename = filename;
    }

}
