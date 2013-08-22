logging-profiling
=================

Purpose
-------

The purpose of creating this project was to test and compare performance
characteristics (mainly speed) of frequently used Java logging frameworks,
which for the time being include,
- **Log4j version 1**
- **Log4j version 2**
- **Logback**.

Permutations
------------

The permutations of testing revolve around these dimensions:
- number of concurrent threads
- number of repeating logs within a thread
- different ways of formatting log messages
- choice of logging frameworks
- access methods, e.g., SLF4J facade vs native API

Goals
-----

Hopefully, the testing results will help Java developers choose the best
logging framework suited for their Java projects based on their unique hardware
and software architectures, as well as practice using the most efficient
way of formatting logging messages.

Limitations
-----------

The project is never intended to be a full-blown testing suite for the
afore-mentioned logging frameworks. Nor is it intended for arousing debates
on which logging framework is the best.

Project Structure
-----------------

The project is entirely managed by Maven, structured as follows which should
be self-explanatary:
- Project "common" contains common classes other projects depend on
- Project "log4jv1-native" tests LOG4J version 1 via native API
- Project "log4jv1-slf4j" tests LOG4J version 1 via SLF4J API
- Project "log4jv2-native" tests LOG4J version 2 via native API
- Project "log4jv2-native" tests LOG4J version 2 via native API
- Project "log4jv2-slf4j" tests LOG4J version 2 via SLF4J API
- Project "logback" tests Logback via SLF4J API

Dependencies
------------

The projects require JDK v1.6+ and Maven 2.0.6+.

In addition, they are dependent upon these Maven plugins for build:
- Exec (http://mojo.codehaus.org/exec-maven-plugin/)
- Enforcer (http://maven.apache.org/enforcer/maven-enforcer-plugin/)

At compile and runtime, they depend on:
- Apache Commons CLI (http://commons.apache.org/proper/commons-cli/)

See each individual project's pom.xml file for its corresponding logging
framework's version.

Build
-----

At parent directory level, run

    mvn clean install


Run
---

To run profiling test against each individual logging framework, e.g., log4jv2-native,

- Project "log4jv1-native"

    cd log4jv1-native
    mvn exec:exec -Dexec.args="-cp %classpath com.test.logging.log4jv1.Profiler"

- Project "log4jv1-slf4j"

    cd log4jv1-slf4j
    mvn exec:exec -Dexec.args="-cp %classpath com.test.logging.log4jv1.Profiler"

- Project "log4jv2-native"

    cd log4jv2-native
    mvn exec:exec -Dexec.args="-cp %classpath com.test.logging.log4jv2.Profiler"

- Project "log4jv2-slf4j"

    cd log4jv2-slf4j
    mvn exec:exec -Dexec.args="-cp %classpath com.test.logging.log4jv2.Profiler"

- Project "logback"

    cd logback
    mvn exec:exec -Dexec.args="-cp %classpath com.test.logging.logback.Profiler"

To change the number of threads and repeats, add additional arguments to the command line. For example

    mvn exec:exec -Dexec.args="-cp %classpath com.test.logging.logback.Profiler -t <number_of_threads> -r <number_of_repeats>"

or in the long format

    mvn exec:exec -Dexec.args="-cp %classpath com.test.logging.logback.Profiler --thread <number_of_threads> --repeat <number_of_repeats>"

Instead of specifying threads and repeats, the test can also be run by specifying
a single parameter: the number of writes for each particular test. A preset
series of numbers of threads will then be chosen, 1, 2, 5, 10, 20, 50, ... , 500.

    mvn exec:exec -Dexec.args="-cp %classpath com.test.logging.logback.Profiler -w <number_of_writes>"

or in the long format

    mvn exec:exec -Dexec.args="-cp %classpath com.test.logging.logback.Profiler --write <number_of_writes>"

To repeat the test over and over again, use "run" option.

    mvn exec:exec -Dexec.args="-cp %classpath com.test.logging.logback.Profiler -n <number_of_runs>"

or in the long format

    mvn exec:exec -Dexec.args="-cp %classpath com.test.logging.logback.Profiler --run <number_of_runs>"

To get help,

    mvn exec:exec -Dexec.args="-cp %classpath com.test.logging.logback.Profiler -h"

or in the long format

    mvn exec:exec -Dexec.args="-cp %classpath com.test.logging.logback.Profiler --help"

