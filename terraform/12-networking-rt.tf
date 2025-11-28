resource "aws_route_table" "route_aproil" {
  vpc_id = aws_vpc.VPC_Aproil.id

  tags = {
    Name = "Route_Aproil"
  }
}

resource "aws_route_table_association" "subnet_A1" {
  subnet_id      = aws_subnet.subnet_A1.id
  route_table_id = aws_route_table.route_aproil.id
}

resource "aws_route_table_association" "subnet_B1" {
  subnet_id      = aws_subnet.subnet_B1.id
  route_table_id = aws_route_table.route_aproil.id
}