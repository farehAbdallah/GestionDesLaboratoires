pipeline {
    agent any  // This ensures the pipeline can run on any available agent

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
                script {
                    docker.image('node:16-alpine').inside {
                        dir('Frontend-GestionLaboratoires') {
                            sh 'npm install'
                            sh 'npm run build --prod'
                        }
                    }
                }
            }
        }

        stage('Build Spring Boot') {
            steps {
                script {
                    docker.image('maven:3.8.4-openjdk-17').inside {
                        dir('Backend-GestionLaboratoires') {
                            sh './mvnw clean package'
                        }
                    }
                }
            }
        }

        stage('Build Docker Images') {
            steps {
                script {
                    docker.build('frontend-gestion-laboratoires-image', 'Frontend-GestionLaboratoires/')
                    docker.build('backend-gestion-laboratoires-image', 'Backend-GestionLaboratoires/')
                }
            }
        }

        stage('Deploy with Docker Compose') {
            steps {
                script {
                    sh 'docker-compose up -d'
                }
            }
        }
    }

    post {
        always {
            node('any') {  // Ensure this node block has a label, use "any" for flexibility
                cleanWs()  // Clean workspace after build
            }
        }
    }
}
