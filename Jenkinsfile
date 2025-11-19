pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                echo "Checking out branch: ${env.BRANCH_NAME}"
                git branch: "${env.BRANCH_NAME}", url: 'https://github.com/sandeep428/maven.git'
            }
        }

        stage('Build & Package') {
            when { branch 'master' } // only master branch
            steps {
                echo "Building application..."
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Run Tests') {
            steps {
                echo "Running tests..."
                sh 'mvn test'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                echo "Running SonarQube scan..."
                // replace 'SonarQube-Local' with your Jenkins SonarQube installation name
                withSonarQubeEnv('SonarQube-Local') {
                    sh 'mvn sonar:sonar'
                }
            }
        }

        stage('Deploy') {
            when { branch 'master' } // only master branch
            steps {
                echo "Deploying to Tomcat..."
                sh 'scp target/*.war ubutnu@54.227.140.74:opt/tomcat/webapps/'
            }
        }
    }
    post {
        always {
            echo "Cleaning workspace..."
            cleanWs()
        }
        success {
            echo "Pipeline succeeded!"
        }
        failure {
            echo "Pipeline failed!"
        }
    }
}
