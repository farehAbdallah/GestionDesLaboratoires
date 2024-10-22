pipeline {
    agent any

    environment {
        NODEJS_HOME = tool name: 'NodeJS', type: 'NodeJSInstallation' 
        MAVEN_HOME = tool name: 'Maven', type: 'MavenInstallation'    
        PATH = "${NODEJS_HOME}/bin:${MAVEN_HOME}/bin:${env.PATH}"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    credentialsId: 'GitHub-PAT',  // GitHub Personal Access Token
                    url: 'https://github.com/farehAbdallah/GestionDesLaboratoires.git'  // Your repository URL
            }
        }

        stage('Build Angular with Docker') {
            steps {
                script {
                    docker.image('node:16-alpine').inside {
                        dir('Frontend-GestionLaboratoires') { // Directory for the Angular app
                            sh 'npm install'                  // Install dependencies
                            sh 'npm run build --prod'         // Build the Angular project for production
                        }
                    }
                }
            }
        }

        stage('Build Spring Boot with Maven') {
            steps {
                script {
                    docker.image('maven:3.8.4-openjdk-17').inside {
                        dir('Backend-GestionLaboratoires') { // Directory for the Spring Boot app
                            sh './mvnw clean package'        // Maven build command (wrapper included)
                        }
                    }
                }
            }
        }

        stage('Build Docker Images') {
            steps {
                script {
                    // Build Docker image for Angular frontend
                    docker.build('frontend-gestion-laboratoires-image', 'Frontend-GestionLaboratoires/')

                    // Build Docker image for Spring Boot backend
                    docker.build('backend-gestion-laboratoires-image', 'Backend-GestionLaboratoires/')
                }
            }
        }

        stage('Deploy with Docker') {
            steps {
                script {
                    // Run Docker containers using docker-compose (if you have a docker-compose.yml file)
                    sh 'docker-compose up -d'
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
