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

        stage('Environment') {
            steps {
                sh '''
                    echo "===== Environment ====="
                    java -version
                    git --version
                    docker --version
                '''
            }
        }

        stage('Build') {
            steps {
                sh './mvnw clean compile'
            }
        }

        stage('Unit Tests') {
            steps {
                sh './mvnw test'
            }
        }

        stage('Package') {
            steps {
                sh './mvnw package -DskipTests'
            }
        }

        stage('Archive Artifact') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        stage('Build Docker Image') {
            steps {
                sh '''
                    docker build -t ${IMAGE_NAME}:${BUILD_NUMBER} .
                    docker tag ${IMAGE_NAME}:${BUILD_NUMBER} ${IMAGE_NAME}:latest
                '''
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
                      --add-host=host.docker.internal:host-gateway \
                      -p 8081:8081 \
                      -e SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/employee_db \
                      -e SPRING_DATASOURCE_USERNAME=employee \
                      -e SPRING_DATASOURCE_PASSWORD=employee123 \
                      ${IMAGE_NAME}:latest
                '''
            }
        }

        stage('Health Check') {
            steps {
                sh '''
                    echo "Waiting for Spring Boot..."

                    sleep 20

                    curl http://localhost:8081/actuator/health
                '''
            }
        }
    }

    post {

        success {
            echo "Pipeline completed successfully!"
        }

        failure {
            echo "Pipeline failed!"
        }

    }

}
