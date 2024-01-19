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
                cd backend
                bazel run @io_bazel_rules_go//go -- mod tidy -v
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