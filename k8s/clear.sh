#!/bin/bash


# Clear Kubernetes persistent volume
kubectl delete pvc local-pvc
kubectl delete pv local-pv

# Clean persistent folder
sudo rm -r /mnt/persistForKuber
