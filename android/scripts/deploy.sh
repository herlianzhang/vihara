#!/bin/zsh
set -e

cd android
pwd
fastlane change_version_code
cd ..
pwd
sh scripts/change_env.sh
echo '{}' > android/app/BundleConfig.pb.json
bazel build --compilation_mode=opt //android/app:android_app_aab
apksigner sign --ks $KEYSTORE_PATH --ks-key-alias alias_name --ks-pass pass:"$KEYSTORE_PASSWORD" --min-sdk-version=24 bazel-bin/android/app/android_app.aab
cd android
pwd
fastlane deploy