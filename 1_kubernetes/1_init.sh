#!/bin/bash

# Create namespace for the project
kubectl create namespace $MONITORING_NAMESPACE
kubectl create namespace $GMONITORING_NAMESPACE
kubectl create namespace $DATABASE_NAMESPACE
kubectl create namespace $KUBERNETES_NAMESPACE
kubectl create namespace $KAFKA_NAMESPACE
kubectl create namespace $NGINX_NAMESPACE


# Setup dockerhub credentials
for ns in $(kubectl get namespaces |grep -v NAME|awk '{print $1}')
do
   kubectl create secret docker-registry docker.registry \
       --docker-username='$DOCKERHUB_USER' \
       --docker-password='$DOCKERHUB_PASSWORD' -n $ns
done



