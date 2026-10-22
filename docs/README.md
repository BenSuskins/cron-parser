# Cron Parser

> A command-line tool that turns cron expressions into a readable schedule.

## Overview

Cron Parser reads a cron expression and prints the selected minute, hour, day, month, and weekday values. It also prints the command that follows the schedule.

## Tech Stack

- Kotlin
- Gradle
- Java 21

## Getting Started

### Prerequisites

- Java 21

### Setup

```sh
./gradlew clean build
```

### Run

```sh
java -jar build/libs/cron-parser-1.0.jar "*/15 0 1,15 * 1-5 /usr/bin/find"
```

The command prints each field in the schedule and then prints `/usr/bin/find`.

## Assumptions

- The command cannot contain spaces.
- Weekdays start at 1, not 0.

## Security

See [SECURITY.md](SECURITY.md) for vulnerability reporting.
