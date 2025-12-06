pipeline {
    agent { 
        docker { 
            image 'sebastiangilian/agent:latest' 
            label 'terraform'
            alwaysPull true 
        }
    }

    environment {
        AWS_ACCESS_KEY_ID     = credentials('aws-access-key')
        AWS_SECRET_ACCESS_KEY = credentials('aws-secret-key')
        AWS_DEFAULT_REGION    = "sa-east-1"
        LANDING_BUCKET        = ""
        ADMIN_BUCKET          = ""
    }

    stages {

        stage('Checkout Infraestructure') {
            steps {
                checkout scm
            }
        }

        stage('Terraform Init & Validate') {
            steps {
                dir('terraform') {
                    sh 'terraform init'
                    sh 'terraform validate'
                }
            }
        }

        stage('Terraform Plan') {
            steps {
                script {
                    def dbPassword = input(
                        id: 'DB_PASSWORD_INPUT', message: 'Ingrese contraseña para RDS (DB)', 
                        parameters: [password(defaultValue: '', description: 'Password para PostgreSQL', name: 'DB_PASSWORD')]
                    )
                    env.TF_VAR_db_password = dbPassword
                    
                    dir('terraform') {
                        sh "terraform plan -out=tfplan"
                    }
                }
            }
        }

        stage('Terraform Apply') {
            steps {
                dir('terraform') {
                    sh "terraform apply -auto-approve tfplan"
                    
                    script {
                        env.LANDING_BUCKET = sh(script: "terraform output -raw landing_bucket_name", returnStdout: true).trim()
                        env.ADMIN_BUCKET   = sh(script: "terraform output -raw admin_bucket_name", returnStdout: true).trim()
                        
                        echo "Infraestructura lista."
                        echo "Bucket Landing detectado: ${env.LANDING_BUCKET}"
                        echo "Bucket Admin detectado: ${env.ADMIN_BUCKET}"
                    }
                }
            }
        }

        stage('Deploy Landing Frontend') {
            when { expression { return env.LANDING_BUCKET != "" } }
            steps {
                dir('workspace_landing') {
                    echo "Iniciando despliegue de Landing Page"
                    
                    git branch: 'main', url: 'https://github.com/SebastianG15/Landing.git'
                    
                    sh 'npm install'
                    
                    sh 'npm run test'
                    
                    sh 'npm run build'
                    
                    sh "aws s3 sync ./out s3://${env.LANDING_BUCKET} --delete --exclude 'config.js'"
                }
            }
        }
        stage('Deploy Admin Frontend') {
            when { expression { return env.ADMIN_BUCKET != "" } }
            steps {
                dir('workspace_admin') {
                    echo "Iniciando despliegue de Admin Panel..."
                    
                    git branch: 'main', url: 'https://github.com/SebastianG15/Admin.git'
                    
                    sh 'npm install'
                    
                    sh 'npm run test'
                    
                    sh 'npm run build'
                    
                    sh "aws s3 sync ./out s3://${env.ADMIN_BUCKET} --delete --exclude 'config.js'"
                }
            }
        }
        post {
        success {
            echo 'Despliegue Completo Exitoso. Tus webs están actualizadas.'
        }
        failure {
            echo 'Algo falló en el pipeline.'
        }
    }
    }
}
