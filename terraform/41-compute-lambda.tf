resource "aws_lambda_function" "aproil-backend-lambda" {
  function_name = var.lambda_function_name

  package_type = "Image"
  image_uri    = "${aws_ecr_repository.lambda_backend_repo.repository_url}@${var.lambda_image_digest}"

  role          = aws_iam_role.lambda_backend_role.arn
  memory_size   = var.lambda_memory
  timeout       = var.lambda_timeout
  architectures = var.lambda_architectures

  vpc_config {
    subnet_ids = [aws_subnet.subnet_A1.id, aws_subnet.subnet_B1.id]
    security_group_ids = [aws_security_group.sg_lambda.id]
  }

  environment {
    variables = {
      STAGE = var.stage
    }
  }

  depends_on = [aws_ecr_repository.lambda_backend_repo]
}