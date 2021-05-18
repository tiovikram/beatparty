#!/bin/bash

if [ $(java -jar jars/checkstyle-8.42-all.jar -c src/test/checkstyle/google_checks.xml src/*/java/ | wc -l) == 2 ]
then
  exit 0
fi

exit 1
