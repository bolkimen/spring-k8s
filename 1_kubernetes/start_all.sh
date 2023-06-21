#!/bin/bash

SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )

if [[ -z "${DOCKERHUB_USER}" ]]; then
  echo "DOCKERHUB_USER env variable is not set" >&2
  exit 1
fi

export KUBERNETES_NAMESPACE=bolkimen-spring-k8s-ns

$SCRIPT_DIR/1_init.sh

