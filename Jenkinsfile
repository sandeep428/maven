pipeline {
    agent any

    environment {
        SONARQUBE = 'sonar-token'        // Jenkins SonarQube server name
        DEPLOY_USER = 'ubuntu'
        DEPLOY_HOST = '54.227.140.74'
        DEPLOY_PATH = '/opt/tomcat/webapps'
        WAR_FILE = 'target/TomcatMavenApp-2.0.war'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: "${env.BRANCH_NAME}", url: 'https://github.com/sandeep428/maven.git'
            }
        }

        stage('Build & Package') {
            when { branch 'master' }       // only for main branch
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Run Tests') {
            steps {
                sh 'mvn test'
            }
        }

    }

    post {
        success { echo "Pipeline completed successfully!" }
        failure { echo "Pipeline failed. Check logs for details." }
    }
}

