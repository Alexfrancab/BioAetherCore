#!/bin/sh
# Gradle wrapper script (Linux/Mac)
# Android Studio lo usa automáticamente

GRADLE_OPTS="${GRADLE_OPTS:-"-Xmx512m -Xms256m"}"
APP_HOME="$(cd "$(dirname "$0")" && pwd)"

exec "$APP_HOME/gradle/wrapper/gradle-wrapper.jar" "$@"
