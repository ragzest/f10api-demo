#!groovy

node {
    stage('Preparation') { // for display purposes
        // Get some code from a GitHub repository
        git 'https://github.com/zuhlkef10/f10api-demo.git'
    }
    stage('Build') {
        // Run the maven build
        sh "'/usr/share/maven/bin/mvn' -Dmaven.test.failure.ignore clean install"
    }
    stage('Results') {
        junit '**/target/surefire-reports/TEST-*.xml'
    }
    stage('Docker Bank API Build') {
        sh 'docker build --tag=zuhlke/bank-api:dev bank'
    }
    stage('Docker Account API Build') {
        sh 'docker build --tag=zuhlke/account-api:dev account'
    }
    stage('Docker Deploy') {
        sh 'docker-compose up -d'
    }
}