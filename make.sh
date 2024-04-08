#!/bin/bash

mongod --fork --logpath /var/log/mongod.log

cd milestone1
mvn package
java -jar ./target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar
