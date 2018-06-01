#!/bin/bash

####
# Sample script to unpack reports stored in Jenkins
# To be used along w/ the data move-test-report functionality

echo "Unpacking $1"

dir=$(dirname $1)
cd $dir


filename=$(basename $1)
unzip $filename

mv Performance_Test_Report/* .
rm -rf Performance_Test_Report
