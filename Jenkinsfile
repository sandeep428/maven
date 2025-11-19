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
            when { branch 'main' }       // only for main branch
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Run Tests') {
            steps {
                sh 'mvn test'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv(SONARQUBE) {
                    sh """
                        mvn sonar:sonar \
                        -Dsonar.projectKey=java-app \
                        -Dsonar.projectName=java-app \
                        -Dsonar.sources=src/main/java \
                        -Dsonar.tests=src/test/java \
                        -Dsonar.java.binaries=target/classes
                    """
                }
            }
        }

        stage('Deploy to Tomcat') {
            when { branch 'main' }       // only for main branch
            steps {
                sshagent(credentials: ['ssh']) {
                    sh "scp ${WAR_FILE} ${DEPLOY_USER}@${DEPLOY_HOST}:${DEPLOY_PATH}/"
                }
            }
        }
    }

    post {
        success { echo "Pipeline completed successfully!" }
        failure { echo "Pipeline failed. Check logs for details." }
    }
}

