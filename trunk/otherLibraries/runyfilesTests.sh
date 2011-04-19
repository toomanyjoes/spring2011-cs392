#!/bin/bash

GRAPH=$1
TESTS="yfilesTests.yfilesCycleTest yfilesTests.yfilesMstKruskalTest yfilesTests.yfilesMstPrimTest yfilesTests.yfilesConnectedTest yfilesTests.yfilesStrongCTest"

rm -rf yfilesResults
mkdir yfilesResults
for test in ${TESTS}
do
    rm -f yfilesResults/${test}.txt
    echo "running ${test}"
    for count in `seq 5`
    do
        echo "  run number: ${count}"
        ./runTest.sh ${test} ${GRAPH} yfilesResults/${test}.txt
    done
done

