#!/bin/bash

GRAPH=$1
GRAPHNAME=`echo "${GRAPH}" | sed 's/\//./g'`
TESTS="jungTests.mstPrimTest"

#rm -rf results/jungResults
#mkdir results/jungResults
for test in ${TESTS}
do
    rm -f results/jungResults/${test}.${GRAPHNAME}.txt
    echo "running jung ${test} ${GRAPH}"
    for count in `seq 500`
    do
        echo "  run number: ${count}  jung ${test} ${GRAPHNAME}"
        ./runTest.sh ${test} ${GRAPH} results/jungResults/${test}.${GRAPHNAME}.txt
    done
done

