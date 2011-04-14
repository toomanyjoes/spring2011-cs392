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

OUTPUT=`java -classpath ".${SEP}jgrapht-0.8.2/build" ${TEST} ${INPUT} | grep "Time elapsed:" | awk '{print $3}'`



echo $OUTPUT >> ${OUTFILE}.txt


