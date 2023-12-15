pipeline {
    agent any
    tools { go 'go-1.21' }
    stages {
        stage('Compile') {
            steps {
                sh 'go build'
            }
        }
        stage('Test') {
            environment {
                CODECOV_TOKEN = credentials('codecov_token')
            }
            steps {
                sh 'go test ./... -coverprofile=coverage.txt'
                sh "curl -Os https://uploader.codecov.io/latest/macos/codecov; chmod +x codecov; ./codecov -t ${CODECOV_TOKEN}"
            }
        }
    }
}