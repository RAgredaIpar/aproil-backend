data "archive_file" "zip_s3" {
  type        = "zip"
  source_dir  = "${path.module}/src/s3_uploader"
  output_path = "${path.module}/s3_payload.zip"
}

resource "aws_lambda_function" "lambda_s3" {
  function_name    = "${var.project_name}-s3-uploader"
  filename         = data.archive_file.zip_s3.output_path
  source_code_hash = data.archive_file.zip_s3.output_base64sha256
  handler          = "main.lambda_handler"
  runtime          = "python3.12"
  role             = aws_iam_role.lambda_backend_role.arn
  timeout          = 15

  environment {
    variables = {
      BUCKET_NAME = aws_s3_bucket.aproil_files.bucket
    }
  }
}


data "archive_file" "zip_db" {
  type        = "zip"
  source_dir  = "${path.module}/src/db_writer"
  output_path = "${path.module}/db_payload.zip"
}
resource "aws_lambda_function" "lambda_db" {
  function_name    = "${var.project_name}-db-writer"
  filename         = data.archive_file.zip_db.output_path
  source_code_hash = data.archive_file.zip_db.output_base64sha256
  handler          = "main.lambda_handler"
  runtime          = "python3.12"
  role             = aws_iam_role.lambda_backend_role.arn
  timeout          = 30

  vpc_config {
    subnet_ids         = [aws_subnet.subnet_A1.id]
    security_group_ids = [aws_security_group.sg_lambda.id]
  }

  environment {
    variables = {
      SECRET_ARN = aws_secretsmanager_secret.app_config11.arn
      DB_HOST    = aws_db_instance.rds_instance.address
      DB_NAME    = aws_db_instance.rds_instance.db_name
    }
  }
}