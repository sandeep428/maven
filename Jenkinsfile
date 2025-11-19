pipeline {
    agent none

    environment {
        SONARQUBE = 'sonar'

        // JFrog details
        ART_URL = "https://devops2025sep.jfrog.io/artifactory"
        ART_REPO = "libs-release-local"
        ARTIFACT_NAME = "TomcatMavenApp-2.0.war"

        // Local build artifact
        WAR_FILE = "target/TomcatMavenApp-2.0.war"

        // Tomcat deployment path
        DEPLOY_PATH = "/opt/tomcat/webapps"
    }

    stages {

        /* ------------------ COMMON STAGES FOR ALL BRANCHES ------------------ */

        stage('Checkout') {
            agent { label 'built-in' }
            steps {
                git url: 'https://github.com/sandeep428/maven.git', branch: env.BRANCH_NAME
                echo "Branch: ${env.BRANCH_NAME}"
            }
        }

        stage('Build & Package') {
            agent { label 'built-in' }
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Run Tests') {
            agent { label 'built-in' }
            steps {
                sh 'mvn test'
            }
        }

        stage('SonarQube Analysis') {
            agent { label 'built-in' }
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

        stage('Security Scan (Trivy)') {
            agent { label 'tomcat-agent' }
            steps {
                sh 'trivy fs --format json --output trivy-report.json --severity HIGH,CRITICAL .'
            }
            post {
                always {
                    archiveArtifacts artifacts: 'trivy-report.json', fingerprint: true
                }
            }
        }

        /* ------------------ MAIN BRANCH ONLY STAGES ------------------ */

        stage('Upload to JFrog Artifactory') {
            when { branch 'main' }
            agent { label 'built-in' }
            steps {
                withCredentials([string(credentialsId: 'jfrog-new-token', variable: 'JFROG_TOKEN')]) {
                    sh """
                        echo "Uploading WAR to JFrog..."
                        curl -H "X-JFrog-Art-Api: ${JFROG_TOKEN}" \
                            -T ${WAR_FILE} \
                            ${ART_URL}/${ART_REPO}/${ARTIFACT_NAME}
                    """
                }
            }
        }

        stage('Download Artifact from JFrog') {
            when { branch 'main' }
            agent { label 'tomcat-agent' }
            steps {
                withCredentials([string(credentialsId: 'jfrog-new-token', variable: 'JFROG_TOKEN')]) {
                    sh """
                        echo "Downloading WAR from JFrog..."
                        curl -H "X-JFrog-Art-Api: ${JFROG_TOKEN}" \
                            -o /tmp/${ARTIFACT_NAME} \
                            ${ART_URL}/${ART_REPO}/${ARTIFACT_NAME}
                    """
                }
            }
        }

        stage('Deploy to Tomcat') {
            when { branch 'main' }
            agent { label 'tomcat-agent' }
            steps {
                sh """
                    echo "Deploying WAR to Tomcat..."
                    cp /tmp/${ARTIFACT_NAME} ${DEPLOY_PATH}/
                """
            }
        }
    }

    post {
        success {
            echo "Pipeline completed successfully (${env.BRANCH_NAME})!"
        }
        failure {
            echo "Pipeline failed for branch ${env.BRANCH_NAME}. Check logs."
        }
    }
}
