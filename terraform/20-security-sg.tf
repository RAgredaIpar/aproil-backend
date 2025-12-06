resource "aws_security_group" "sg_lambda" {
  name        = "SG-Lambda"
  description = "Permite comunicacion con RDS y Endpoints"
  vpc_id      = aws_vpc.VPC_Aproil.id

  egress {
    from_port = 0
    to_port   = 0
    protocol  = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = { Name = "SG-Lambda" }
}

resource "aws_security_group" "sg_rds" {
  name        = "SG-RDS"
  description = "Permite acceso PostgreSQL solo desde Lambda"
  vpc_id      = aws_vpc.VPC_Aproil.id
  ingress {
    description = "Acceso PostgreSQL desde Lambda"
    from_port   = 5432
    to_port     = 5432
    protocol    = "tcp"
    security_groups = [aws_security_group.sg_lambda.id]
  }

  egress {
    from_port = 0
    to_port   = 0
    protocol  = "-1"
    cidr_blocks = [var.vpc_cidr]
  }

  tags = { Name = "SG-RDS" }
}


resource "aws_security_group" "sg_endpoints" {
  name        = "SG-Endpoints"
  description = "Permite HTTPS desde Lambda hacia endpoints privados"
  vpc_id      = aws_vpc.VPC_Aproil.id


  ingress {
    description = "HTTPS desde Lambda"
    from_port   = 443
    to_port     = 443
    protocol    = "tcp"
    security_groups = [aws_security_group.sg_lambda.id]
  }


  egress {
    from_port = 0
    to_port   = 0
    protocol  = "-1"
    cidr_blocks = [var.vpc_cidr]
  }


  tags = { Name = "SG-Endpoints" }
}