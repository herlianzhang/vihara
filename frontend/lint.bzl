"Define linters as aspects"

load("@aspect_rules_lint//lint:eslint.bzl", "eslint_aspect")
load("@aspect_rules_lint//lint:lint_test.bzl", "make_lint_test")

eslint = eslint_aspect(
    binary = "@@//frontend:eslint",
    configs = [
        "@@//frontend/admin:package_json",
    ],
)

eslint_test = make_lint_test(aspect = eslint)
