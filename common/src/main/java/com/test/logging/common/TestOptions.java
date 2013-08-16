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
    /** Default number of repeating logging statements */
    protected static int DEFAULT_NUMBER_OF_REPEATS = 10 * 1000;  // 10 thousand
    /** Number of repeating logging statements */
    protected int NUMBER_OF_REPEATS = DEFAULT_NUMBER_OF_REPEATS;

    /** Maximum number of threads that concurrently write to log files */
    protected static final int MAX_NUMBER_OF_THREADS = 100 * 1000;  // 100 thousand
    /** Default number of threads */
    protected static final int DEFAULT_NUMBER_OF_THREADS = 10;
    /** Current number of threads */
    protected int NUMBER_OF_THREADS = DEFAULT_NUMBER_OF_THREADS;

    /** Command line options backed by Apache Commons CLI library */
    protected Options options = new Options();
    
    public TestOptions() {
        // Build command line options
        Option help = new Option("h", "help", false, "usage");
        options.addOption(help);
        
        Option thread = OptionBuilder.withArgName("threads")
                                     .hasArg()
                                     .withDescription("number of threads: 1 ~ " + MAX_NUMBER_OF_THREADS)
                                     .create("t");
        thread.setLongOpt("thread");
        options.addOption(thread);

        Option repeat = OptionBuilder.withArgName("repeats")
                                     .hasArg()
                                     .withDescription("number of times each thread executes the intended logging statement: 1 ~ " + MAX_NUMBER_OF_REPEATS)
                                     .create("r");
        repeat.setLongOpt("repeat");
        options.addOption(repeat);
    }
    
    /** Parse CLI options. If invalid options are seen, print out usage,
     *  use default settings, and keep executing.
     * 
     * @param args Command line argument list
     * 
     * @return true if execution should continue; false if command line options indicate printing
     *         the help message.
     */
    public boolean parseCliOptions(String[] args) {
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
            formatter.printHelp("log4j profiler", options);
            return false;
        }

        if (line.hasOption("thread")) {
            try {
                Integer nThread = Integer.valueOf(line.getOptionValue("thread"));
                if (0 < nThread && nThread < MAX_NUMBER_OF_THREADS) {
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
                if (0 < nRepeat && nRepeat < MAX_NUMBER_OF_REPEATS) {
                    NUMBER_OF_REPEATS = nRepeat;
                }
            }
            finally {
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
    
}
