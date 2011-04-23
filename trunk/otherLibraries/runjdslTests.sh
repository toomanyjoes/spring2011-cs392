#!/bin/bash

GRAPH=$1
TESTS="jdslTests.jdslCycleTest jdslTests.jdslMstPrimTest"

rm -rf results/jdslResults
mkdir results/jdslResults
for test in ${TESTS}
do
    rm -f results/jdslResults/${test}.${GRAPH}.txt
    echo "running ${test}"
    for count in `seq 5000`
    do
        echo "  run number: ${count}"
        ./runTest.sh ${test} ${GRAPH} results/jdslResults/${test}.${GRAPH}.txt
    done
done

