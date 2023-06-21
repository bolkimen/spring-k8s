#!/bin/bash

# Create namespace for the project
kubectl create namespace $KUBERNETES_NAMESPACE

# Setup dockerhub credentials
for ns in $(kubectl get namespaces |grep -v NAME|awk '{print $1}')
do
   kubectl delete secret docker.registry -n $ns

   kubectl create secret docker-registry docker.registry \
       --docker-username='$DOCKERHUB_USER' \
       --docker-password='$DOCKERHUB_PASSWORD' -n $ns
done



