run "check_instnode_name" {

  command = apply

  assert {
    condition     = output.instnode_name != null
    error_message = "Instnode name not found"
  }

}