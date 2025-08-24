#!/bin/bash

export SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
if [[ -z "${NGINX_NAMESPACE}" ]]; then
  export NGINX_NAMESPACE=bolkimen-nginx-ns
fi

echo "SCRIPT_DIR: $SCRIPT_DIR"
echo "NGINX_NAMESPACE: $NGINX_NAMESPACE"

envsubst < $SCRIPT_DIR/nginx/nginx-service.yaml | kubectl apply -f -
envsubst < $SCRIPT_DIR/nginx/nginx-deployment.yaml | kubectl apply -f -
envsubst < $SCRIPT_DIR/nginx/nginx-ingress.yaml | kubectl apply -f -

kubectl wait pods -n $NGINX_NAMESPACE -l app=nginx-app --for condition=Ready --timeout=90s

#MONITORING_POD=$(kubectl get pod -l app=nginx-app -n $NGINX_NAMESPACE -o jsonpath="{.items[0].metadata.name}")
#kubectl port-forward $MONITORING_POD 19092:9092 -n $NGINX_NAMESPACE &

