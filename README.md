# vihara
[![codecov](https://codecov.io/gh/herlianzhang/vihara/graph/badge.svg?token=DM5R8PWQQV)](https://codecov.io/gh/herlianzhang/vihara)

# Android Setup

Install [bazelisk](https://github.com/bazelbuild/bazelisk)

export `ANDROID_HOME` variable
on mac `export ANDROID_HOME=$HOME/Library/Android/sdk`
on linux `export ANDROID_HOME=$HOME/Android/Sdk/`

To Update maven_install.json (this was source of truth)
`bazel run @unpinned_maven//:pin`

To Build
`bazel build //android/app:compose_app`

To Run
`bazel mobile-install //android/app:compose_app`

To open on Android Studio IDE
install this [plugin](https://plugins.jetbrains.com/plugin/9185-bazel-for-android-studio)

# iOS Setup
To install Carthage dependencies (on iOS folder)
`carthage bootstrap --use-xcframeworks --platform iOS`

To Generate xcodeproj
`bazel run //ios:xcodeproj`

To Run Directly
`bazel run //ios:App`

To Build signed ipa
`bazel build \           
  --compilation_mode=opt \
  --cpu=ios_arm64 \
  --output_groups=+dsyms \
  --define=apple.package_swift_support=yes \
  //ios:DeployApp`

# Backend

To Generate Build file
`bazel run //:gazelle`

To Run
`bazel run //backend`

Generate pb.go for development purpose
`protoc --go_out=paths=source_relative:./backend --go-grpc_out=paths=source_relative:./backend proto/**/*.proto`

`protoc --swift_out=./ios --grpc-swift_out=./ios proto/servicea.proto`

Clean
`bazel clean --expunge`

To Run frontend admin for development purposes
`cd frontend/admin`
`npm start`

`bazel build --compilation_mode=opt //android/app:android_app_aab`
`apksigner sign --ks my-release-key.keystore --ks-key-alias alias_name --min-sdk-version=24 bazel-bin/android/app/android_app.aab`