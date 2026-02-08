resource "aws_instance" "instnode" {
  instance_type = "t2.micro"
  ami           = "ami-abc123"

  provisioner "local-exec" {
    command = "echo 'Hello World' >example.txt"
  }

}