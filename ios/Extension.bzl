load(
    "@build_bazel_rules_apple//apple:ios.bzl",
    "ios_application",
)

team_id = "YTF7PKCABJ"

def ios_app(
    name,
    provisioning_profile
):
    ios_application(
        name = name,
        bundle_id = "pro.herlian.vihara",
        app_icons = native.glob(["Resources/Assets.xcassets/**"]),
        families = [
            "iphone",
        ],
        launch_storyboard = "Resources/LaunchScreen.storyboard",
        infoplists = ["Resources/Info.plist"],
        minimum_os_version = "15.0",
        visibility = ["//visibility:public"],
        deps = [
            ":Lib",
        ],
        provisioning_profile = provisioning_profile,
    )