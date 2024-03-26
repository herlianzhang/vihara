"""External dependencies required by rules_android_app_bundles"""

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_jar")
load("@bazel_tools//tools/build_defs/repo:utils.bzl", "maybe")

ANDROID_LIB = [
    "@maven//:androidx_activity_activity_compose",
    "@maven//:androidx_appcompat_appcompat",
    "@maven//:androidx_compose_runtime_runtime",
    "@maven//:androidx_lifecycle_lifecycle_viewmodel_ktx",
    "@maven//:androidx_lifecycle_lifecycle_viewmodel_compose",
    "@maven//:androidx_compose_material3_material3",
    "@maven//:androidx_compose_ui_ui",
    "@maven//:androidx_compose_ui_ui_tooling",
    "@maven//:androidx_compose_ui_ui_graphics",
    "@maven//:androidx_core_core_ktx",
    "@maven//:androidx_credentials_credentials",
    "@maven//:androidx_compose_foundation_foundation",
    "@maven//:androidx_lifecycle_lifecycle_runtime_compose",
    "@maven//:androidx_navigation_navigation_compose",
    "@maven//:com_google_dagger_dagger_android",
    "@maven//:org_jetbrains_kotlin_kotlin_stdlib",
]

def download_app_bundle_dependencies():
    """Fetches the external app bundle dependencies.

  	"""

    maybe(
        http_jar,
        name = "bundletool_all",
        sha256 = "e740e7d38562c5e8d87cc817548b2db94e42802e9a0774fdf674e758ff79694d",
        urls = ["https://github.com/google/bundletool/releases/download/1.14.0/bundletool-all-1.14.0.jar"],
    )