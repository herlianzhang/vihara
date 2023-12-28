# vihara
[![codecov](https://codecov.io/gh/herlianzhang/vihara/graph/badge.svg?token=DM5R8PWQQV)](https://codecov.io/gh/herlianzhang/vihara)

# Android Setup

Install [bazelisk](https://github.com/bazelbuild/bazelisk)

export `ANDROID_HOME` variable
on mac `export ANDROID_HOME=$HOME/Library/Android/sdk`
on linux `export ANDROID_HOME=$HOME/Android/Sdk/`

To Build
`bazel build //android/app:compose_app`

To Run
`bazel mobile-install //android/app:compose_app`

To open on Android Studio IDE
install this [plugin](https://plugins.jetbrains.com/plugin/9185-bazel-for-android-studio)