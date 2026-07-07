pipeline {

    agent any

    environment {
        IMAGE_NAME = "employee-management"
        CONTAINER_NAME = "employee-app"
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/mxawais/employee-management.git'
            }
        }

        stage('Build') {
            steps {
                sh './mvnw clean package'
            }
        }

        stage('Run Tests') {
            steps {
                sh './mvnw test'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t ${IMAGE_NAME}:latest .'
            }
        }

        stage('Stop Old Container') {
            steps {
                sh '''
                docker stop ${CONTAINER_NAME} || true
                docker rm ${CONTAINER_NAME} || true
                '''
            }
        }

        stage('Deploy Container') {
            steps {
                sh '''
                docker run -d \
                  --name ${CONTAINER_NAME} \
                  -p 8081:8081 \
                  --network host \
                  ${IMAGE_NAME}:latest
                '''
            }
        }

        stage('Health Check') {
            steps {
                sh '''
                sleep 20
                curl http://localhost:8081/actuator/health
                '''
            }
        }
    }

    post {

        success {
            echo 'Application deployed successfully!'
        }

        failure {
            echo 'Deployment failed!'
        }

    }

}
