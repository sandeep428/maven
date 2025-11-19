pipeline {
    agent any

    environment {
        SONARQUBE = 'sonar'
        DEPLOY_USER = 'ubuntu'
        DEPLOY_HOST = '54.227.140.74'
        DEPLOY_PATH = '/opt/tomcat/webapps'
        WAR_FILE = 'target/TomcatMavenApp-2.0.war'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/sandeep428/maven.git'
            }
        }

        stage('Build & Package') {
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
        success {
            echo "Pipeline completed successfully!"
        }
        failure {
            echo "Pipeline failed. Check logs for details."
        }
    }
}
