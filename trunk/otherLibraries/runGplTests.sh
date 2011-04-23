#!/bin/bash

GRAPH=$1
TESTS=`ls gplPrograms`

rm -rf results/gplResluts
mkdir results/gplResults
cd gplPrograms
for test in ${TESTS}
do
    rm -f results/gplResults/${test}.${GRAPH}.txt
    cd ${test}
    echo "running ${test}"
    for count in `seq 5000`
    do
        echo "  run number: ${count}"
        ../../runTest.sh gpl.Main "../../${GRAPH} v1" ../../results/gplResults/${test}.${GRAPH}.txt
    done
    cd ..
done



