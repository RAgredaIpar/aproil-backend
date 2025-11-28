resource "aws_lambda_function" "aproil-backend-lambda" {
  function_name = var.lambda_function_name

  package_type = "Zip"
  filename     = "lambda-function.zip"
  handler      = "main.lambda_handler"
  runtime      = "python3.12"

  role          = aws_iam_role.lambda_backend_role.arn
  memory_size   = var.lambda_memory
  timeout       = var.lambda_timeout
  architectures = var.lambda_architectures

  vpc_config {
    subnet_ids = [aws_subnet.subnet_A1.id]
    security_group_ids = [aws_security_group.sg_lambda.id]
  }

  environment {
    variables = {
      STAGE      = var.stage
      DB_HOST    = aws_db_instance.rds_instance.address
      DB_PORT    = aws_db_instance.rds_instance.port
      DB_NAME    = aws_db_instance.rds_instance.db_name
      SECRET_ARN = aws_secretsmanager_secret.app_config.arn
    }
  }
}