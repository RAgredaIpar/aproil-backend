resource "aws_db_subnet_group" "db_subnet_group" {
  name = "rds_subnet_group"
  subnet_ids = [aws_subnet.subnet_A1.id, aws_subnet.subnet_B1.id]

  tags = { Name = "rds_subnet_group" }
}

resource "aws_db_instance" "rds_instance" {
  allocated_storage    = var.db_allocated_storage
  engine               = var.db_engine
  engine_version       = var.db_engine_version
  instance_class       = var.db_instance_class
  username             = var.db_username
  password             = var.db_password
  db_subnet_group_name = aws_db_subnet_group.db_subnet_group.name
  vpc_security_group_ids = [aws_security_group.sg_rds.id]
  skip_final_snapshot  = true
  db_name              = var.db_name


  tags = { Name = "RDS-Instance" }
}