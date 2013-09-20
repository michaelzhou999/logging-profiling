#!/bin/bash

cd ~/logging-profiling
mvn clean install -DskipTests

cd ~/logging-profiling/log4jv1-native
mvn install
echo ""
echo ""
echo "=============================="
echo " number of lines in log files"
wc -l *.out*
rm *.out*
echo "=============================="
echo ""
echo ""

cd ~/logging-profiling/log4jv1-slf4j
mvn install
echo ""
echo ""
echo "=============================="
echo "number of lines in log files"
wc -l *.out*
rm *.out*
echo "=============================="
echo ""
echo ""

cd ~/logging-profiling/log4jv2-native
mvn install
echo ""
echo ""
echo "=============================="
echo "number of lines in log files"
wc -l *.out*
rm *.out*
echo "=============================="
echo ""
echo ""

cd ~/logging-profiling/log4jv2-slf4j
mvn install
echo ""
echo ""
echo "=============================="
echo "number of lines in log files"
wc -l *.out*
rm *.out*
echo "=============================="
echo ""
echo ""

cd ~/logging-profiling/logback
mvn install
echo ""
echo ""
echo "=============================="
echo "number of lines in log files"
wc -l *.out*
rm *.out*
echo "=============================="
echo ""
echo ""
