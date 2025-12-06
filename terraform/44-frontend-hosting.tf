resource "aws_cloudfront_origin_access_control" "oac" {
  name                              = "S3-OAC-${var.project_name}"
  description                       = "Acceso seguro CloudFront -> S3"
  origin_access_control_origin_type = "s3"
  signing_behavior                  = "always"
  signing_protocol                  = "sigv4"
}

resource "aws_s3_bucket" "landing_bucket" {
  bucket        = "landing-${var.project_name}-${var.stage}-free"
  force_destroy = true 
}

resource "aws_s3_bucket_policy" "landing_policy" {
  bucket = aws_s3_bucket.landing_bucket.id
  policy = jsonencode({
    Version = "2012-10-17",
    Statement = [{
      Action   = "s3:GetObject",
      Effect   = "Allow",
      Resource = "${aws_s3_bucket.landing_bucket.arn}/*",
      Principal = { Service = "cloudfront.amazonaws.com" },
      Condition = { StringEquals = { "AWS:SourceArn" = aws_cloudfront_distribution.landing_dist.arn } }
    }]
  })
}

resource "aws_cloudfront_distribution" "landing_dist" {
  origin {
    domain_name              = aws_s3_bucket.landing_bucket.bucket_regional_domain_name
    origin_id                = "S3-Landing"
    origin_access_control_id = aws_cloudfront_origin_access_control.oac.id
  }

  enabled             = true
  is_ipv6_enabled     = true
  default_root_object = "index.html"
  
  custom_error_response {
    error_code         = 403
    response_code      = 200
    response_page_path = "/index.html"
  }
  custom_error_response {
    error_code         = 404
    response_code      = 200
    response_page_path = "/index.html"
  }

  default_cache_behavior {
    allowed_methods  = ["GET", "HEAD"]
    cached_methods   = ["GET", "HEAD"]
    target_origin_id = "S3-Landing"
    viewer_protocol_policy = "redirect-to-https"
    forwarded_values {
      query_string = false
      cookies { forward = "none" }
    }
  }

  viewer_certificate {
    cloudfront_default_certificate = true
  }
  
  restrictions {
    geo_restriction {
      restriction_type = "none"
      locations        = []
    }
  }
}

resource "aws_s3_object" "landing_config" {
  bucket       = aws_s3_bucket.landing_bucket.id
  key          = "config.js"
  content_type = "application/javascript"
  content = <<EOF
window.ENV = {
  UPLOAD_ENDPOINT: "${aws_apigatewayv2_api.main.api_endpoint}/upload"
};
EOF
}

resource "aws_s3_bucket" "admin_bucket" {
  bucket        = "admin-${var.project_name}-${var.stage}-free"
  force_destroy = true
}

resource "aws_s3_bucket_policy" "admin_policy" {
  bucket = aws_s3_bucket.admin_bucket.id
  policy = jsonencode({
    Version = "2012-10-17",
    Statement = [{
      Action   = "s3:GetObject",
      Effect   = "Allow",
      Resource = "${aws_s3_bucket.admin_bucket.arn}/*",
      Principal = { Service = "cloudfront.amazonaws.com" },
      Condition = { StringEquals = { "AWS:SourceArn" = aws_cloudfront_distribution.admin_dist.arn } }
    }]
  })
}

resource "aws_cloudfront_distribution" "admin_dist" {
  origin {
    domain_name              = aws_s3_bucket.admin_bucket.bucket_regional_domain_name
    origin_id                = "S3-Admin"
    origin_access_control_id = aws_cloudfront_origin_access_control.oac.id
  }

  enabled             = true
  default_root_object = "index.html"

  custom_error_response {
    error_code         = 403
    response_code      = 200
    response_page_path = "/index.html"
  }
  custom_error_response {
    error_code         = 404
    response_code      = 200
    response_page_path = "/index.html"
  }

  default_cache_behavior {
    allowed_methods  = ["GET", "HEAD"]
    cached_methods   = ["GET", "HEAD"]
    target_origin_id = "S3-Admin"
    viewer_protocol_policy = "redirect-to-https"
    forwarded_values {
      query_string = false
      cookies { forward = "none" }
    }
  }

  viewer_certificate {
    cloudfront_default_certificate = true
  }
  
  restrictions {
    geo_restriction {
      restriction_type = "none"
      locations        = []
    }
  }
}

resource "aws_s3_object" "admin_config" {
  bucket       = aws_s3_bucket.admin_bucket.id
  key          = "config.js"
  content_type = "application/javascript"
  content = <<EOF
window.ENV = {
  DB_ENDPOINT: "${aws_apigatewayv2_api.main.api_endpoint}/data"
};
EOF
}