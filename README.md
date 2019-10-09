# Take_-Home_Exercise
Validity Co-Op Take Home Exercise by Guanzhou Song

## How to compile
This project is a Maven Project that you can simply request all dependencies using the pom.xml under root folder.
All the configuration will be done automatically with Maven.

## How to run the program
The program has been packaged to a single file called `duplicateFinder.war`.
Steps to run the program:
1. under folder RUN_WITH_JAR, put you csv file as input data under RUN_WITH_JAR/data and rename it with userInfo.csv.
2. make sure jetty-run.jar exists.
3. under command line, cd into /RUN_WITH_JAR.
4. run command `java -jar jetty-runner.jar duplicateFinder.war`.
5. after the server start, open localhost:8080 in a browser.
6. result will be shown.

## How to generate war file
If you want to make some changes and generate the war file again:
1. build the project.
2. under root folder of the project, run `mvn clean package`.
3. under folder /target you should be able to see the duplicateFinder.war file.
4. copy this file to folder RUN_WITH_JAR and run it!



2019-10 Guanzhou Song
