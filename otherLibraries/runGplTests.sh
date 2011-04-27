#!/bin/bash

GRAPH=$1
GRAPHNAME=`echo "${GRAPH}" | sed 's/\//./g'`
#TESTS=`ls gplPrograms`
TESTS="connectedDFSAL connectedDFSNL connectedDFSEL cycleAL cycleNL cycleEL mstKruskalAL mstKruskalNL mstKruskalEL mstPrimAL mstPrimNL mstPrimEL strongCAL strongCNL strongCEL"

#rm -rf results/gplResluts
#mkdir results/gplResults
cd gplPrograms
for test in ${TESTS}
do
    rm -f ../results/gplResults/${test}.${GRAPHNAME}.txt
    cd ${test}
    echo "running ${test}"
#    for count in `seq 500`
#    do
        echo "  run number: ${count}  gpl ${test} ${GRAPH}"
        ../../runTest.sh gpl.Main "../../${GRAPH} v1" ../../results/gplResults/${test}.${GRAPHNAME}.txt
#    done
    cd ..
done



