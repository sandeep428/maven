pipeline {
    agent any
    environment {
        // Set environment variable based on branch
        IS_MAIN = "${env.BRANCH_NAME == 'main'}"
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: "${env.BRANCH_NAME}", url: 'https://github.com/your-repo.git'
            }
        }

        stage('Build') {
            when { branch 'main' } // only main branch
            steps {
                echo "Building application..."
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                echo "Running tests..."
                sh 'mvn test'
            }
        }

        stage('Code Scan') {
            steps {
                echo "Running SonarQube or other scans..."
                sh 'mvn sonar:sonar'
            }
        }

        stage('Deploy') {
            when { branch 'main' } // only main branch
            steps {
                echo "Deploying to production server..."
                sh 'scp target/*.war user@server:/tomcat/webapps/'
            }
        }
    }
    post {
        always {
            echo "Cleaning workspace..."
            cleanWs()
        }
    }
}
