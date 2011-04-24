#!/bin/bash

GRAPH=$1
GRAPHNAME=`echo "${GRAPH}" | sed 's/\//./g'`
TESTS="jdslTests.jdslCycleTest jdslTests.jdslMstPrimTest"

#rm -rf results/jdslResults
#mkdir results/jdslResults
for test in ${TESTS}
do
    rm -f results/jdslResults/${test}.${GRAPHNAME}.txt
    echo "running ${test}"
    for count in `seq 500`
    do
        echo "  run number: ${count}  jdsl ${test} ${GRAPH}"
        ./runTest.sh ${test} ${GRAPH} results/jdslResults/${test}.${GRAPHNAME}.txt
    done
done

