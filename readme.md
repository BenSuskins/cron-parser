# Cron Parser

Cron Parser is a command line tool that takes a cron expression and outputs a human-readable breakdown.

## Prerequisites
- [Java 21](https://adoptium.net/en-GB/temurin/releases/?os=any&arch=any&version=21)

## How to build

```shell
./gradlew clean build
```

## How to run

```shell
java -jar /build/libs/cron-parser-1.0.jar "*/15 0 1,15 * 1-5 /usr/bin/find"
```