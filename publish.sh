#!/bin/bash

version=""
source build.properties

published_versions=(
  "1.0.0"
  "1.1.0"
  "1.1.1"
)

match=$(echo "${published_versions[@]:0}" | grep -o "${version}");

if [ -z "${match}" ]; then
  ./gradlew publishPlugins \
    -Pgradle.publish.key="${GRADLE_PUBLISH_KEY}" \
    -Pgradle.publish.secret="${GRADLE_PUBLISH_SECRET}"
else
  echo "Version ${match} already published";
fi
