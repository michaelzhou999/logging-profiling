package com.test.logging.common;

/**
 * Base class for logging profilers
 * 
 * @author Michael.Zhou
 */
public class BaseProfiler {
    /** Options for running the profiler */
    protected static TestOptions opts = new TestOptions();

    /** Application name */
    protected static String APPNAME = "logging profiler (please set to a specific name)";

    /** Print banner of test being run */
    public static void printBanner() {
        System.out.println("");
        System.out.println("################################################################");
        System.out.println("   Starting " + APPNAME + " ...");
        System.out.println("################################################################");
        System.out.println("");
    }

    /** Set application name */
    public static void setAppName(String appName) {
        APPNAME = appName;
    }

    /** Return application name */
    public static String getAppName() {
        return APPNAME;
    }

    /** Return test options */
    public static TestOptions getTestOptions() {
        return opts;
    }
}
