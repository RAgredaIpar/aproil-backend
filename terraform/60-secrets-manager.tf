resource "aws_secretsmanager_secret" "app_config11" {
  name = "/${var.project_name}/${var.stage}/app-config"
}

resource "aws_secretsmanager_secret_version" "app_config_v" {
  secret_id = aws_secretsmanager_secret.app_config11.id
  secret_string = jsonencode({
    "spring.datasource.url"      = "jdbc:postgresql://${aws_db_instance.rds_instance.address}:${aws_db_instance.rds_instance.port}/${aws_db_instance.rds_instance.db_name}"
    "spring.datasource.username" = var.db_username
    "spring.datasource.password" = var.db_password
  })
}
