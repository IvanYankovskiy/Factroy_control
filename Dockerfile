FROM adoptopenjdk/openjdk11
EXPOSE 8080

WORKDIR root/
ARG JAR_FILE=target/control-*.jar
ADD ${JAR_FILE} ./application.jar

ENTRYPOINT ["java", "-server", "-Xms256M", "-Xmx256M",\
            "-XX:+HeapDumpOnOutOfMemoryError", "-XX:HeapDumpPath=dump.hprof", "-Djava.security.egd=/dev/zrandom",\
            "-jar", "/root/application.jar"]
