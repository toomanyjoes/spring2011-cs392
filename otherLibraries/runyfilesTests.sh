#!/bin/bash

GRAPH=$1
GRAPHNAME=`echo "${GRAPH}" | sed 's/\//./g'`
TESTS="yfilesTests.yfilesCycleTest yfilesTests.yfilesMstKruskalTest yfilesTests.yfilesMstPrimTest yfilesTests.yfilesConnectedTest yfilesTests.yfilesStrongCTest"

#rm -rf results/yfilesResults
#mkdir results/yfilesResults
for test in ${TESTS}
do
    rm -f results/yfilesResults/${test}.${GRAPHNAME}.txt
    echo "running ${test}"
#    for count in `seq 500`
#    do
#        echo "  run number: ${count}  yfiles ${test} ${GRAPH}"
        ./runTest.sh ${test} ${GRAPH} results/yfilesResults/${test}.${GRAPHNAME}.txt
#    done
done

