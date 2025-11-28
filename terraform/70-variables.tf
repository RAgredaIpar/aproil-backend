variable "aws_region" {
  type = string
  default = "sa-east-1"
}

variable "aws_profile" {
  type = string
  default = "Proyecto"
}

variable "project_name" {
  type = string
  default = "aproil"
}

variable "stage" {
  type = string
  default = "prod"
}

variable "vpc_cidr" {
  type = string
  default = "10.0.0.0/16"
}

variable "az_a" {
  type = string
  default = "sa-east-1a"
}

variable "az_b" {
  type = string
  default = "sa-east-1b"
}

variable "private_subnet_a1_cidr" {
  type = string
  default = "10.0.1.0/24"
}

variable "private_subnet_b1_cidr" {
  type = string
  default = "10.0.2.0/24"
}

variable "ecr_repo_name" {
  type = string
  default = "lambda-backend"
}

variable "lambda_function_name" {
  type = string
  default = "aproil-backend-lambda"
}

variable "lambda_memory" {
  type = number
  default = 1024
}

variable "lambda_timeout" {
  type = number
  default = 30
}

variable "lambda_architectures" {
  type = list(string)
  default = ["x86_64"]
}

variable "lambda_image_digest" {
  type = string
  default = "sha256:1db16a2cf8c0d9647f23c1f792a8791599d06239b3e8ae199421c5509621935c"
  description = "Digest SHA256 de la imagen en ECR (sin URI)."
}

variable "db_allocated_storage" {
  type = number
  default = 20
}

variable "db_engine" {
  type = string
  default = "postgres"
}

variable "db_engine_version" {
  type = string
  default = "17.6"
}

variable "db_instance_class" {
  type = string
  default = "db.t3.micro"
}

variable "db_name" {
  type = string
  default = "aproil_db"
}

variable "db_username" {
  type = string
  default = "aproil"
}

variable "db_password" {
  type = string
  sensitive = true
}