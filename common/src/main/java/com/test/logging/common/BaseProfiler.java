package com.test.logging.common;

/**
 * Base class for logging profilers
 * 
 * @author Michael.Zhou
 */
public class BaseProfiler {
    /** Application name */
    protected static String PROFILER_NAME = "logging profiler (please set to a specific name)";

    /** Options for running the profiler */
    protected static ProfilerOptions opts = new ProfilerOptions(PROFILER_NAME);

    /** Print banner of test being run */
    public static void printBanner() {
        System.out.println("");
        System.out.println("################################################################");
        System.out.println("   Starting " + PROFILER_NAME + " ...");
        System.out.println("################################################################");
        System.out.println("");
    }

    /** Set profiler name */
    public static void setProfilerName(String profilerName) {
        PROFILER_NAME = profilerName;
    }

    /** Return profiler name */
    public static String getProfilerName() {
        return PROFILER_NAME;
    }

    /** Return test options */
    public static ProfilerOptions getProfilerOptions() {
        return opts;
    }
}
