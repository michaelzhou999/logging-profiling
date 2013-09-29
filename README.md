logging-profiling
=================

Purpose
-------

The purpose of creating this project was to micro-benchmark and compare performance
characteristics (mainly speed/throughput) of frequently used Java logging frameworks,
which for the time being include,
- **Log4j version 1**
- **Log4j version 2**
- **Logback**
- **Java Util Logging (JUL)**

Permutations
------------

The permutations of testing include these dimensions:
- number of concurrent threads
- number of repeating logs within a thread
- different ways of formatting log messages
- choice of logging frameworks
- access APIs, e.g., SLF4J facade vs native API

Goals
-----

Hopefully, the testing results will help developers choose the best
logging framework suited for their Java projects based on their unique hardware
and software architectures, as well as best practice using the most efficient
way of formatting logging messages.

Limitations
-----------

The project is never intended to be a full-blown testing suite for the
afore-mentioned logging frameworks. Nor is it intended for arousing debates
on which logging framework is the best.

Project Structure
-----------------

The project is managed by Maven, structured as follows which should
be self-explanatary:
- Project "common" contains common classes other projects depend on
- Project "log4jv1-native" tests LOG4J version 1 via native API
- Project "log4jv1-slf4j" tests LOG4J version 1 via SLF4J API
- Project "log4jv2-native" tests LOG4J version 2 via native API
- Project "log4jv2-native" tests LOG4J version 2 via native API
- Project "log4jv2-slf4j" tests LOG4J version 2 via SLF4J API
- Project "logback" tests Logback via SLF4J API
- Project "java-util-logging" tests JUL via native API

Log level is set to DEBUG across the board and INFO is used to log actual
messages. The only exception is java-util-logging, where level INFO is used.

Dependencies
------------

The projects require JDK v1.6+ and Maven 2.0.6+.

In addition, they are dependent upon the following Maven plugins for build and run:
- Enforcer (http://maven.apache.org/enforcer/maven-enforcer-plugin/)
- Compiler (http://maven.apache.org/plugins/maven-compiler-plugin/)
- Surefire (http://maven.apache.org/surefire/maven-surefire-plugin/)

Build
-----

At parent directory level, run

    mvn clean compile

Run
---

By default, each test run will start with warming up JVM by writing 500K log messages,
wait for 10 seconds, and measure the time spent on writing another 500K messages as
the profiling result.

To run profiling test against each individual logging framework, e.g.,
log4jv2-native,

    cd log4jv2-native
    mvn test

A file named "test.properties" under each project's test/resources directory
will be used to specify profiling test options. See comments in the file.

Instead of specifying threads and repeats, the test can also be run by
specifying a single parameter: the number of writes for each particular test.
A pre-set series of thread counts will then be chosen, 1, 2, 5, 10, 20, 50.

Results will be written to results.csv file after each run.

Results
-------

Under "testresults" directory, there is a spreadsheet that serves as the template
for recording and comparing test results. It also recorded the actual results
on virtual machine running Ubuntu 12.04LTS (64-bit, 8 cores, no hyper-threading).

To use it, copy/paste the time column from results.csv (in previous step) into
the corresponding worksheet (look for <project-name>-raw-data worksheets toward
the end of the workbook), the average time will be automatically calculated and
populated to other worksheets (named <project-name>-results). A grand summary of
comparison is presented in a worksheet named "comparison between frameworks" (first
worksheet).
