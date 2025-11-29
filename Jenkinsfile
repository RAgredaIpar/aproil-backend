pipeline {
    agent { label 'terraform' }

    environment {
        AWS_ACCESS_KEY_ID     = credentials('aws-access-key')
        AWS_SECRET_ACCESS_KEY = credentials('aws-secret-key')
        AWS_DEFAULT_REGION    = "sa-east-1"
    }

    stages {

        stage('Checkout Repository') {
            steps {
                checkout scm
            }
        }

        stage('Terraform Init') {
            steps {
                dir('terraform') {
                    sh 'terraform init'
                }
            }
        }

        stage('Terraform Validate') {
            steps {
                dir('terraform') {
                    sh 'terraform validate'
                }
            }
        }

        stage('Terraform Plan') {
            steps {
                script {
                    def dbPassword = input(
                        id: 'DB_PASSWORD_INPUT', message: 'Enter DB password', 
                        parameters: [password(defaultValue: '', description: 'Database password', name: 'DB_PASSWORD')]
                    )
                    dir('terraform') {
                        sh "terraform plan -out=tfplan -var 'db_password=${dbPassword}'"
                    }
                }
            }
        }

        stage('Terraform Apply') {
            when {
                branch 'main'
            }
            steps {
                script {
                    def dbPassword = input(
                        id: 'DB_PASSWORD_APPLY', message: 'Enter DB password for apply', 
                        parameters: [password(defaultValue: '', description: 'Database password', name: 'DB_PASSWORD')]
                    )
                    dir('terraform') {
                        sh "terraform apply -auto-approve -var 'db_password=${dbPassword}' tfplan"
                    }
                }
            }
        }
    }
}
