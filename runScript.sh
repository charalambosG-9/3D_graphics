#!/bin/sh

echo "Deleting existing class files"
rm *.class

echo "Compilling class files"
javac *.java

echo "Executing program"
java Hatch