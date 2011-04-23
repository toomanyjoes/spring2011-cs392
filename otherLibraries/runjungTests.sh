#!/bin/bash

GRAPH=$1
TESTS="jungTests.mstPrimTest"

rm -rf results/jungResults
mkdir results/jungResults
for test in ${TESTS}
do
    rm -f results/yfilesResults/${test}.${GRAPH}.txt
    echo "running ${test}"
    for count in `seq 5000`
    do
        echo "  run number: ${count}"
        ./runTest.sh ${test} ${GRAPH} results/jungResults/${test}.${GRAPH}.txt
    done
done

