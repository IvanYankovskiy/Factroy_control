#! /bin/bash

java -server -Xms256M -Xmx256M \
            -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=dump.hprof -Djava.security.egd=/dev/zrandom \
            -jar target/control-0.0.1-SNAPSHOT.jar
