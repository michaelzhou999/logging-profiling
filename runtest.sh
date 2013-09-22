#!/bin/bash

PROJECTS=$(find . -maxdepth 1 -type d -name log\* -printf '%f\n' | sort)
CURR_DIR=`pwd`

RESULTS_DIR="$CURR_DIR/results"
echo "Creating result-storing directory: $RESULTS_DIR"
mkdir -p $RESULTS_DIR
rm -rf $RESULTS_DIR/*

runs="5"
if [ $# -gt 0 ]
then
    if [[ $1 =~ ^[1-9][0-9]*$ ]]
    then
        echo "Intended number of runs: $1"
        runs=$1
    else
        echo "Invalid integer format: $1"
    fi
fi

run="1"
while [ $run -le $runs ]
do
    echo "----------------------"
    echo "   Test Run #$run"
    echo "----------------------"
    RUN_RESULT_DIR=$RESULTS_DIR/run-$run
    mkdir -p $RUN_RESULT_DIR

    for proj in $PROJECTS;
    do
        cd $proj
        echo "Working in directory `pwd`"

        # run the test
        mvn test

        # sanity check and clean up log files
        echo ""
        echo ""
        echo "=============================="
        echo " Number of lines in log files"
        wc -l *out*
        rm *out*
        echo "=============================="
        echo ""
        echo ""

        # transfer results
        mv -f results.csv $RUN_RESULT_DIR/$proj-results.csv
        cd ..
    done

    run=$[$run+1]
done

