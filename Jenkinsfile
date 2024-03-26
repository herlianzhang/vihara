pipeline {
    agent any
    tools { go 'go-1.21' }
    stages {
        stage('Build') {
            steps {
                sh '''
                #!/bin/zsh
                source ~/.jenkins_profile
                cd ios
                carthage bootstrap --use-xcframeworks --platform iOS
                cd ..
                bazel build //...
                '''
            }
        }
        stage('Test') {
            steps {
                sh '''
                #!/bin/zsh
                source ~/.jenkins_profile
                bazel test //...
                '''
            }
        }
        stage('Deploy') {
            when { 
                expression { env.BRANCH_NAME == 'main' }
            }
            environment {
                KEY_ID = credentials('key_id')
                ISSUER_ID = credentials('issuer_id')
                APP_IDENTIFIER = "pro.herlian.vihara"
            }
            steps {
                sh '''
                #!/bin/zsh
                source ~/.jenkins_profile
                cd ios
                fastlane update_build_number_for_testflight
                cd ..
                bazel build --compilation_mode=opt --cpu=ios_arm64 --output_groups=+dsyms --define=apple.package_swift_support=yes //ios:DeployApp
                cd ios
                fastlane upload_app_to_testflight
                '''
            }
        }
        stage('Deploy Android') {
            when { 
                expression { env.BRANCH_NAME == 'main' }
            }
            environment {
                KEYSTORE_PATH = credentials('keystore_path')
                KEYSTORE_PASSWORD = credentials('keystore_password')
                BACKEND_URL = credentials('backend_url')
                GOOGLE_CLIENT_ID = credentials('google_client_id_for_android')
                JSON_KEY_PATH = credentials('android_json_key_path')
            }
            steps {
                sh './android/scripts/deploy.sh'
            }
        }
        // stage('Test') {
        //     environment {
        //         CODECOV_TOKEN = credentials('codecov_token')
        //     }
        //     steps {
        //         sh 'go test ./... -coverprofile=coverage.txt'
        //         sh "curl -Os https://uploader.codecov.io/latest/macos/codecov; chmod +x codecov; ./codecov -t ${CODECOV_TOKEN}"
        //     }
        // }
    }
}