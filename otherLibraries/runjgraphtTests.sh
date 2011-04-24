#!/bin/bash

GRAPH=$1
GRAPHNAME=`echo "${GRAPH}" | sed 's/\//./g'`
TESTS="jgraphtTests.jgraphtConnectedTest jgraphtTests.jgraphtCycleTest jgraphtTests.jgraphtMstKruskalTest jgraphtTests.jgraphtStrongCTest"

#rm -rf results/jgrapftResults
#mkdir results/jgraphtResults
for test in ${TESTS}
do
    rm -f results/jgraphtResults/${test}.${GRAPHNAME}.txt
    echo "running ${test}"
    for count in `seq 500`
    do
        echo "  run number: ${count}  jgrapht ${test} ${GRAPHNAME}"
        ./runTest.sh ${test} ${GRAPH} results/jgraphtResults/${test}.${GRAPHNAME}.txt
    done
done

