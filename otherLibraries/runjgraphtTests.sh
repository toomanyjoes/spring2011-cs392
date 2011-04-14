#!/bin/bash

GRAPH=$1
TESTS="jgraphtTests.jgraphtConnectedTest jgraphtTests.jgraphtCycleTest jgraphtTests.jgraphtMstKruskalTest jgraphtTests.jgraphtStrongCTest"

rm -rf jgrapftResults
mkdir jgraphtResults
for test in ${TESTS}
do
    rm -f ${test}.txt
    echo "running ${test}"
    for count in `seq 5`
    do
        echo "  run number: ${count}"
        ./runTest.sh ${test} ${GRAPH} jgraphtResults/${test}
    done
done

