@echo off
set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-17.0.18.8-hotspot
set PATH=%JAVA_HOME%\bin;%PATH%
echo Java Home set to %JAVA_HOME%
echo Starting Spring Boot...
.\mvnw.cmd spring-boot:run
