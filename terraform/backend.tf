terraform {
  backend "s3" {
    bucket = "aproil-terraform-state-backend"
    key    = "prod/terraform.tfstate"
    region = "sa-east-1"
  }
}