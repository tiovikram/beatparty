#!/bin/bash

if [ $(java -jar jars/checkstyle-8.42-all.jar -c src/test/checkstyle/google_checks.xml src/*/java/ | wc -l) -ne 2 ]
then
  exit 1
fi

exit 0
