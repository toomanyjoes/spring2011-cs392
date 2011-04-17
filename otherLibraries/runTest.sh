#!/bin/bash
TEST=$1
INPUT=$2
OUTFILE=$3
SEP=":"
OS=`uname -s | grep CYGWIN`
if [ -n "${OS}" ]
then
    SEP=";"
fi

CP=".${SEP}jgrapht-0.8.2/build${SEP}jung2-2_0_1/collections-generic-4.01.jar${SEP}jung2-2_0_1/jung-3d-demos-2.0.1.jar${SEP}jung2-2_0_1/jung-jai-2.0.1.jar${SEP}jung2-2_0_1/vecmath-1.3.1.jar${SEP}jung2-2_0_1/colt-1.2.0.jar${SEP}jung2-2_0_1/jung-algorithms-2.0.1.jar${SEP}jung2-2_0_1/jung-jai-samples-2.0.1.jar${SEP}jung2-2_0_1/wstx-asl-3.2.6.jar${SEP}jung2-2_0_1/concurrent-1.3.4.jar${SEP}jung2-2_0_1/jung-api-2.0.1.jar${SEP}jung2-2_0_1/jung-samples-2.0.1.jar${SEP}jung2-2_0_1/j3d-core-1.3.1.jar${SEP}jung2-2_0_1/jung-graph-impl-2.0.1.jar${SEP}jung2-2_0_1/jung-visualization-2.0.1.jar${SEP}jung2-2_0_1/jung-3d-2.0.1.jar${SEP}jung2-2_0_1/jung-io-2.0.1.jar${SEP}jung2-2_0_1/stax-api-1.0.1.jar"

OUTPUT=`java -classpath ${CP} ${TEST} ${INPUT} | grep "Time elapsed:" | awk '{print $3}'`



echo $OUTPUT >> ${OUTFILE}.txt

