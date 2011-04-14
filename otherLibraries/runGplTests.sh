#!/bin/bash

GRAPH=$1
TESTS=`ls gplPrograms`

rm -rf gplResluts
mkdir gplResults
cd gplPrograms
for test in ${TESTS}
do
    rm -f ${test}.txt
    cd ${test}
    echo "running ${test}"
    for count in `seq 5`
    do
        echo "  run number: ${count}"
        ../../runTest.sh gpl.Main "../../${GRAPH} v1" ../../gplResults/${test}.txt
    done
    cd ..
done



