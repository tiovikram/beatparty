if [ $(java -jar jars/checkstyle-8.42-all.jar -c src/test/checkstyle/google_checks.xml src/main/java/ | wc -l) == 0 ]
then
  exit 0
fi

exit 1
