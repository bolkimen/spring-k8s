resource "aws_instance" "instnode" {
  instance_type = "t2.micro"
  ami           = "ami-abc123"
}