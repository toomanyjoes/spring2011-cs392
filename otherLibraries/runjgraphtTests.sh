#!/bin/bash

GRAPH=$1
TESTS="jgraphtTests.jgraphtConnectedTest jgraphtTests.jgraphtCycleTest jgraphtTests.jgraphtMstKruskalTest jgraphtTests.jgraphtStrongCTest"

rm -rf results/jgrapftResults
mkdir results/jgraphtResults
for test in ${TESTS}
do
    rm -f results/jgraphtResults/${test}.${GRAPH}.txt
    echo "running ${test}"
    for count in `seq 5000`
    do
        echo "  run number: ${count}"
        ./runTest.sh ${test} ${GRAPH} results/jgraphtResults/${test}.${GRAPH}.txt
    done
done

