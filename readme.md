# Cron Parser

Cron Parser is a command line tool that takes a cron expression and outputs a human-readable breakdown.

# How to run

## Prerequisites

- [Java 21](https://adoptium.net/en-GB/temurin/releases/?os=any&arch=any&version=21)

## Build Application

```shell
./gradlew clean build
```

## Run Application

```shell
java -jar build/libs/cron-parser-1.0.jar "*/15 0 1,15 * 1-5 /usr/bin/find"
```

### Output

```
minute 0 15 30 45
hour 0
day of month 1 15
month 1 2 3 4 5 6 7 8 9 10 11 12
day of week 1 2 3 4 5
command /usr/bin/find
```
