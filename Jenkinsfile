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
                    bat 'npm install --verbose'  
                    bat 'npm run build --prod'   
                }
            }
        }

        stage('Build Spring Boot') {
            steps {
                dir('Backend-GestionLaboratoires') {
                    bat './mvnw clean package -X'  
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
