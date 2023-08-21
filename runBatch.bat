@echo off

echo Deleting existing class files
del *.class

echo Compilling class files
javac *.java

echo Executing program
java Hatch

