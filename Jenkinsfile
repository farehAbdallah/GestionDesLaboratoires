pipeline {
    agent any

    environment {
        NODEJS_HOME = tool name: 'NodeJS', type: 'NodeJSInstallation'  // Name must match the one configured in Jenkins
        MAVEN_HOME = tool name: 'Maven', type: 'MavenInstallation'     // Name must match the one configured in Jenkins
        PATH = "${NODEJS_HOME}/bin:${MAVEN_HOME}/bin:${env.PATH}"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    credentialsId: 'GitHub-PAT',  // Use your GitHub Personal Access Token (PAT)
                    url: 'https://github.com/farehAbdallah/GestionDesLaboratoires.git'  // Your repository URL
            }
        }

        stage('Build Angular') {
            steps {
                script {
                    docker.image('node:16-alpine').inside {
                        dir('Frontend-GestionLaboratoires') {  // Directory containing your Angular app
                            sh 'npm install'                   // Install dependencies
                            sh 'npm run build --prod'          // Build Angular app for production
                        }
                    }
                }
            }
        }

        stage('Build Spring Boot') {
            steps {
                script {
                    docker.image('maven:3.8.4-openjdk-17').inside {
                        dir('Backend-GestionLaboratoires') {  // Directory containing your Spring Boot app
                            sh './mvnw clean package'         // Maven build command
                        }
                    }
                }
            }
        }

        stage('Build Docker Images') {
            steps {
                script {
                    // Build the Docker image for the Angular frontend
                    docker.build('frontend-gestion-laboratoires-image', 'Frontend-GestionLaboratoires/')

                    // Build the Docker image for the Spring Boot backend
                    docker.build('backend-gestion-laboratoires-image', 'Backend-GestionLaboratoires/')
                }
            }
        }

        stage('Deploy with Docker Compose') {
            steps {
                script {
                    sh 'docker-compose up -d'  // Deploy using Docker Compose
                }
            }
        }
    }

    post {
        always {
            cleanWs()  // Clean the workspace after the build
        }
    }
}
