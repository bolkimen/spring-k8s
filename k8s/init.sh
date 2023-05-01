#!/bin/bash

SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )

# Create persistent folder
sudo mkdir -p /mnt/persistForKuber
sudo chown nobody:nogroup /mnt/persistForKuber
sudo chmod 777 /mnt/persistForKuber

# Create persistent volume in Kubernetes
kubectl apply -f $SCRIPT_DIR/local-pv.yaml
kubectl apply -f $SCRIPT_DIR/local-pvc.yaml
