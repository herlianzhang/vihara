#!/bin/zsh
set -e

cd ..
sed -i '' s/##//g app/BUILD.bazel
sed -i '' s/__VERSION_CODE__/$1/g app/BUILD.bazel