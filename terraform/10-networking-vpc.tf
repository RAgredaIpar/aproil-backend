resource "aws_vpc" "VPC_Aproil" {
  cidr_block           = var.vpc_cidr
  enable_dns_support   = true
  enable_dns_hostnames = true

  tags = {
    Name = "VPC_${var.project_name}"
  }
}