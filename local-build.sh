#!/bin/bash

mvn compile > /dev/null
exit_status=$?
if [[ $exit_status -ne 0 ]]
then
  echo "Compile Failed."
  echo "Run mvn compile to find errors"
  rm -rf ./target/
  exit 1 
fi

mvn test > /dev/null
exit_status=$?
if [[ $exit_status -ne 0 ]]
then
  echo "Test Suite Failed."
  echo "Run mvn test to find errors"
  rm -rf ./target/
  exit 1
fi

bash checkstyle-script.sh
exit_status=$?
if [[ $exit_status -ne 0 ]]
then
  echo "Checkstyle returned errors"
  echo "Run java -jar jars/checkstyle-8.42-all.jar -c src/test/checkstyle/google_checks.xml src/main/java/ to find errors"
  rm -rf ./target/
  exit 1
fi

echo "Build passed!"
rm -rf ./target/
exit 0
