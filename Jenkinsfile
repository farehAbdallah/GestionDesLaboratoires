pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    credentialsId: 'GitHub-PAT',
                    url: 'https://github.com/farehAbdallah/GestionDesLaboratoires.git'
            }
        }

        stage('Build Angular') {
            steps {
                dir('Frontend-GestionLaboratoires') {
                    sh 'npm install --verbose'  // Install the Node.js dependencies with verbose logging
                    sh 'npm run build --prod'   // Run Angular production build
                }
            }
        }

        stage('Build Spring Boot') {
            steps {
                dir('Backend-GestionLaboratoires') {
                    sh './mvnw clean package -X'  // Run Maven build with detailed logging
                }
            }
        }
    }

    post {
        always {
            cleanWs()  // Clean workspace after build
        }
    }
}
