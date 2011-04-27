#!/bin/bash
gpldirs=`ls gplPrograms`

for dir in ${gpldirs}
do
    javac gplPrograms/${dir}/gpl/*.java
done
