#!/bin/zsh
set -e

pwd

# File path
FILE="core/common/src/main/kotlin/env/Env.kt"

# Write the content to Env.kt, creating or replacing it
cat <<EOF > "$FILE"
package pro.herlian.vihara.core.common.env

// this value might change on CI
// value in this file only for dev purposes
object Env {
    const val IS_DEBUG = false
    const val BACKEND_URL = "$BACKEND_URL"
    const val GOOGLE_CLIENT_ID = "$GOOGLE_CLIENT_ID"
}
EOF