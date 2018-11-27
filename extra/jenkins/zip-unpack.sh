#!/bin/bash

####
# Sample script to unpack reports stored in Jenkins
# To be used along w/ the data move-test-report functionality

echo "Unpacking $1"

dir="$(dirname $1)"
cd "${dir}"
if [[ $? != 0 ]] ; then
    echo "Unable to enter directory from ${dir}"
    exit 1
fi

filename="$(basename $1)"
unzip "${filename}"

if [[ $? != 0 ]] ; then
    echo "Unable to extract files from ${filename}"
    exit 1
fi

for reportDir in * ; do
    if [[ -d "${reportDir}" ]] ; then
        echo "Erasing bogus directory ${reportDir}"
        mv "${reportDir}"/* .  && rm -rf "${reportDir}"
    fi
done
