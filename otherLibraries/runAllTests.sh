#!/bin/bash

#GRAPHS="TestV500E45K.bench randomGraphs/randommultigraph.txt randomGraphs/randompseudograph.txt randomGraphs/randomsimplegraph.txt randomGraphs/simplelineargraph.txt"
GRAPHS="randomGraphs/randompseudograph.txt randomGraphs/randomsimplegraph.txt randomGraphs/simplelineargraph.txt"

for graph in ${GRAPHS}
do
    echo "Running gpl tests for graph: ${graph}"
    ./runGplTests.sh ${graph}
    echo "Running yfiles tests for graph: ${graph}"
    ./runyfilesTests.sh ${graph}
    echo "Running jgrapht tests for graph: ${graph}"
    ./runjgraphtTests.sh ${graph}
#    echo "Running jung tests for graph: ${graph}"
#    ./runjungTests.sh ${graph}
#    echo "Running jdsl tests for graph: ${graph}"
#    ./runjdslTests.sh ${graph}
done

for graph in ${GRAPHS}
do
    echo "Running jung tests for graph: ${graph}"
    ./runjungTests.sh ${graph}
    echo "Running jdsl tests for graph: ${graph}"
    ./runjdslTests.sh ${graph}
done
