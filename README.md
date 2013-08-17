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

Limitations
-----------

The project is never intended to be a full-blown testing suite for the
afore-mentioned logging frameworks. Nor is it intended for arousing debates
on which logging framework is the best.

Goals
-----

Hopefully, the testing results will help Java developers choose the best
logging framework suited for their Java projects based on their unique hardware
and software architectures, as well as practice using the most efficient
way of formatting logging messages.

Project Management
------------------

The project is entirely managed by Maven, structured as follows which should
be self-explanatary:
- Project "common" contains common classes other projects depend on
- Project "log4jv1-native" tests LOG4J version 1 via native API
- Project "log4jv1-slf4j" tests LOG4J version 1 via SLF4J API
- Project "log4jv2-native" tests LOG4J version 2 via native API
- Project "log4jv2-native" tests LOG4J version 2 via native API
- Project "log4jv2-slf4j" tests LOG4J version 2 via SLF4J API
- Project "logback" tests Logback via SLF4J API

Build
-----

Once cloned, at parent directory level, run

    mvn clean install


Run
---

To run each individual test, e.g., log4jv2-native,

    cd log4jv2-native
    mvn exec:java

To change the number of threads and repeats,

    mvn exec:java -Dexec.args="-t <number_of_threads> -r <number_of_repeats>"

To get help,

    mvn exec:java -Dexec.args="-h"

