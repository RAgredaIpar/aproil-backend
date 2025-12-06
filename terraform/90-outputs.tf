output "vpc_id" {
  value = aws_vpc.VPC_Aproil.id
}

output "private_subnet_ids" {
  value = [
    aws_subnet.subnet_A1.id,
    aws_subnet.subnet_B1.id
  ]
}

output "sg_lambda_id" {
  value = aws_security_group.sg_lambda.id
}

output "sg_rds_id" {
  value = aws_security_group.sg_rds.id
}

output "sg_endpoints_id" {
  value = aws_security_group.sg_endpoints.id
}

output "rds_endpoint" {
  value = aws_db_instance.rds_instance.address
}

output "rds_port" {
  value = aws_db_instance.rds_instance.port
}

output "rds_db_name" {
  value = aws_db_instance.rds_instance.db_name
}

output "ecr_repository_url" {
  value = aws_ecr_repository.lambda_backend_repo.repository_url
}

output "lambda_function_name" {
  value = aws_lambda_function.lambda_db.function_name
}

output "secretsmanager_secret_arn" {
  value = aws_secretsmanager_secret.app_config11.arn
}

output "vpce_ids" {
  value = {
    logs       = aws_vpc_endpoint.vpce_cloudwatch_logs.id
    monitoring = aws_vpc_endpoint.vpce_cloudwatch_monitoring.id
    xray       = aws_vpc_endpoint.vpce_xray.id
    secrets    = aws_vpc_endpoint.vpce_secretsmanager.id
  }
}
output "landing_bucket_name" {
  value = aws_s3_bucket.landing_bucket.id
}

output "admin_bucket_name" {
  value = aws_s3_bucket.admin_bucket.id
}

output "landing_url" {
  description = "URL Publica de la Landing (CloudFront)"
  value       = "https://${aws_cloudfront_distribution.landing_dist.domain_name}"
}

output "admin_url" {
  description = "URL Publica del Admin (CloudFront)"
  value       = "https://${aws_cloudfront_distribution.admin_dist.domain_name}"
}