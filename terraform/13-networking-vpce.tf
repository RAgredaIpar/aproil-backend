resource "aws_vpc_endpoint" "vpce_cloudwatch_logs" {
  vpc_id            = aws_vpc.VPC_Aproil.id
  service_name      = "com.amazonaws.${var.aws_region}.logs"
  vpc_endpoint_type = "Interface"

  subnet_ids = [
    aws_subnet.subnet_A1.id,
    aws_subnet.subnet_B1.id
  ]

  security_group_ids = [aws_security_group.sg_endpoints.id]
  private_dns_enabled = true

  tags = { Name = "VPCE-CloudWatchLogs" }
}

resource "aws_vpc_endpoint" "vpce_cloudwatch_monitoring" {
  vpc_id            = aws_vpc.VPC_Aproil.id
  service_name      = "com.amazonaws.${var.aws_region}.monitoring"
  vpc_endpoint_type = "Interface"

  subnet_ids = [
    aws_subnet.subnet_A1.id,
    aws_subnet.subnet_B1.id
  ]

  security_group_ids = [aws_security_group.sg_endpoints.id]
  private_dns_enabled = true

  tags = { Name = "VPCE-CloudWatchMonitoring" }
}

resource "aws_vpc_endpoint" "vpce_xray" {
  vpc_id            = aws_vpc.VPC_Aproil.id
  service_name      = "com.amazonaws.${var.aws_region}.xray"
  vpc_endpoint_type = "Interface"

  subnet_ids = [
    aws_subnet.subnet_A1.id,
    aws_subnet.subnet_B1.id
  ]

  security_group_ids = [aws_security_group.sg_endpoints.id]
  private_dns_enabled = true

  tags = { Name = "VPCE-XRay" }
}

resource "aws_vpc_endpoint" "vpce_secretsmanager" {
  vpc_id            = aws_vpc.VPC_Aproil.id
  service_name      = "com.amazonaws.${var.aws_region}.secretsmanager"
  vpc_endpoint_type = "Interface"

  subnet_ids = [
    aws_subnet.subnet_A1.id,
    aws_subnet.subnet_B1.id
  ]

  security_group_ids = [aws_security_group.sg_endpoints.id]
  private_dns_enabled = true

  tags = { Name = "VPCE-SecretsManager" }
}