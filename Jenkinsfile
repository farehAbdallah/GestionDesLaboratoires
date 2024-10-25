pipeline {
    agent any
     tools {
        dockerTool 'Docker'
    }
    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub') // Assurez-vous que les identifiants Docker Hub sont configurés dans Jenkins
    }

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

        stage('Build Docker Images') {
            steps {
                script {
                    // Build the images using docker-compose
                    sh 'docker-compose build'
                }
            }
        }

        stage("Login to Docker Hub") {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'dockerhub', passwordVariable: 'DOCKER_PWD', usernameVariable: 'DOCKER_USER')]) {
                        sh 'docker login -u $DOCKER_USER -p $DOCKER_PWD'
                    }
                }
            }
        }

        stage("Push Docker Images") {
            steps {
                script {
                    // Push the frontend image
                    sh "docker tag frontend $DOCKER_IMAGE_NAME_FRONTEND:latest"
                    sh "docker push $DOCKER_IMAGE_NAME_FRONTEND:latest"

                    // Push the backend image
                    sh "docker tag backend $DOCKER_IMAGE_NAME_BACKEND:latest"
                    sh "docker push $DOCKER_IMAGE_NAME_BACKEND:latest"

                    // Push the database image
                    sh "docker tag mysql $DOCKER_IMAGE_NAME_DB:latest"
                    sh "docker push $DOCKER_IMAGE_NAME_DB:latest"
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
