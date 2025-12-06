resource "aws_apigatewayv2_api" "main" {
  name          = "${var.project_name}-api-${var.stage}"
  protocol_type = "HTTP"
  
  cors_configuration {
    allow_origins = ["*"]
    allow_methods = ["POST", "GET", "OPTIONS"]
    allow_headers = ["content-type", "authorization"]
    max_age       = 300
  }
}

resource "aws_apigatewayv2_stage" "default" {
  api_id = aws_apigatewayv2_api.main.id
  name   = "$default"
  auto_deploy = true
}

resource "aws_apigatewayv2_integration" "lambda_s3" {
  api_id           = aws_apigatewayv2_api.main.id
  integration_type = "AWS_PROXY"

  integration_uri    = aws_lambda_function.lambda_s3.invoke_arn
  integration_method = "POST"
  payload_format_version = "2.0"
}

resource "aws_apigatewayv2_route" "post_upload" {
  api_id    = aws_apigatewayv2_api.main.id
  route_key = "POST /upload"
  target    = "integrations/${aws_apigatewayv2_integration.lambda_s3.id}"
}

resource "aws_lambda_permission" "api_gw_s3" {
  statement_id  = "AllowExecutionFromAPIGateway"
  action        = "lambda:InvokeFunction"
  function_name = aws_lambda_function.lambda_s3.function_name
  principal     = "apigateway.amazonaws.com"
  source_arn    = "${aws_apigatewayv2_api.main.execution_arn}/*/*/upload"
}


resource "aws_apigatewayv2_integration" "lambda_db" {
  api_id           = aws_apigatewayv2_api.main.id
  integration_type = "AWS_PROXY"

  integration_uri    = aws_lambda_function.lambda_db.invoke_arn
  integration_method = "POST"
  payload_format_version = "2.0"
}

resource "aws_apigatewayv2_route" "post_data" {
  api_id    = aws_apigatewayv2_api.main.id
  route_key = "POST /data"
  target    = "integrations/${aws_apigatewayv2_integration.lambda_db.id}"
}

resource "aws_lambda_permission" "api_gw_db" {
  statement_id  = "AllowExecutionFromAPIGatewayDB"
  action        = "lambda:InvokeFunction"
  function_name = aws_lambda_function.lambda_db.function_name
  principal     = "apigateway.amazonaws.com"
  source_arn    = "${aws_apigatewayv2_api.main.execution_arn}/*/*/data"
}