resource "aws_s3_bucket" "aproil_files" {
  bucket = "${var.project_name}-files-${var.stage}-unique-id"
  force_destroy = true

  tags = {
    Name        = "${var.project_name}-files"
    Environment = var.stage
  }
}

resource "aws_s3_bucket_public_access_block" "aproil_files_block" {
  bucket = aws_s3_bucket.aproil_files.id
  block_public_acls       = true
  block_public_policy     = true
  ignore_public_acls      = true
  restrict_public_buckets = true
}