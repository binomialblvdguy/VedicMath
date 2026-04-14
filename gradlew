#!/bin/sh
set -e
APP_HOME=$(cd "$(dirname "$0")" && pwd -P)
GRADLE_USER_HOME=${GRADLE_USER_HOME:-$HOME/.gradle}
GRADLE_VERSION=8.4
GRADLE_URL=https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip
GRADLE_DIR=$GRADLE_USER_HOME/wrapper/dists/gradle-${GRADLE_VERSION}

if [ ! -d "$GRADLE_DIR" ]; then
    mkdir -p "$GRADLE_USER_HOME/wrapper/dists"
    cd "$GRADLE_USER_HOME/wrapper/dists"
    wget -q "$GRADLE_URL" -O gradle.zip || curl -sO "$GRADLE_URL"
    unzip -q gradle.zip
    rm -f gradle.zip
fi

GRADLE_BIN="$GRADLE_DIR/gradle-${GRADLE_VERSION}/bin/gradle"
"$GRADLE_BIN" "$@"
