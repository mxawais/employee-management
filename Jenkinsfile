pipeline {

    agent any

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

        stage('Docker Compose Build') {
            steps {
                sh 'docker compose build'
            }
        }

        stage('Deploy using Docker Compose') {
            steps {
                sh '''
                docker compose down
                docker compose up -d
                '''
            }
        }

        stage('Health Check') {
            steps {
                sh '''
                echo "Waiting for application to start..."

                for i in {1..12}
                do
                    STATUS=$(curl -s http://localhost:8081/actuator/health | grep -o '"status":"UP"' || true)

                    if [ "$STATUS" = '"status":"UP"' ]; then
                        echo "Application is UP!"
                        exit 0
                    fi

                    echo "Waiting... ($i/12)"
                    sleep 10
                done

                echo "Application failed health check!"
                exit 1
                '''
            }
        }
    }

    post {

        success {
            echo '========================================='
            echo 'CI/CD Pipeline completed successfully!'
            echo 'Application deployed using Docker Compose'
            echo '========================================='
        }

        failure {
            echo '========================================='
            echo 'CI/CD Pipeline FAILED!'
            echo 'Check the Console Output for details.'
            echo '========================================='
        }

        always {
            sh 'docker compose ps || true'
        }
    }
}
