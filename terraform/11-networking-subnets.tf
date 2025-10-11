resource "aws_subnet" "subnet_A1" {
  vpc_id                  = aws_vpc.VPC_Aproil.id
  cidr_block              = var.private_subnet_a1_cidr
  availability_zone       = var.az_a
  map_public_ip_on_launch = false

  tags = {
    Name = "PrivateSubnet_A1"
  }
}

resource "aws_subnet" "subnet_B1" {
  vpc_id                  = aws_vpc.VPC_Aproil.id
  cidr_block              = var.private_subnet_b1_cidr
  availability_zone       = var.az_b
  map_public_ip_on_launch = false

  tags = {
    Name = "PrivateSubnet_B1"
  }
}