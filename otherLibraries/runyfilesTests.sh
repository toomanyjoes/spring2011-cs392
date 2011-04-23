#!/bin/bash

GRAPH=$1
TESTS="yfilesTests.yfilesCycleTest yfilesTests.yfilesMstKruskalTest yfilesTests.yfilesMstPrimTest yfilesTests.yfilesConnectedTest yfilesTests.yfilesStrongCTest"

rm -rf results/yfilesResults
mkdir results/yfilesResults
for test in ${TESTS}
do
    rm -f results/yfilesResults/${test}.${GRAPH}.txt
    echo "running ${test}"
    for count in `seq 5000`
    do
        echo "  run number: ${count}"
        ./runTest.sh ${test} ${GRAPH} results/yfilesResults/${test}.${GRAPH}.txt
    done
done

