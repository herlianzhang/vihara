pipeline {
    agent any
    tools { go 'go-1.21' }
    stages {
        stage('Build') {
            environment {
                KEY_ID = credentials('key_id')
                ISSUER_ID = credentials('issuer_id')
            }
            steps {
                sh '''
                #!/bin/zsh
                source ~/.bash_profile
                cd ios
                fastlane update_build_number
                cd ..
                bazel build --compilation_mode=opt --cpu=ios_arm64 --output_groups=+dsyms --define=apple.package_swift_support=yes //ios:DeployApp
                cd ios
                fastlane upload_app
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