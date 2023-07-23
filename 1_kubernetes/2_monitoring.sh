#!/bin/bash

envsubst < $SCRIPT_DIR/monitoring/prometheus-cluster-role.yaml | kubectl apply -f -
envsubst < $SCRIPT_DIR/monitoring/prometheus-pv-pvc.yaml | kubectl apply -f -


envsubst < $SCRIPT_DIR/monitoring/prometheus-config-map.yaml | kubectl create -f -

envsubst < $SCRIPT_DIR/monitoring/prometheus-deployment.yaml | kubectl apply -f -

envsubst < $SCRIPT_DIR/monitoring/prometheus-service.yaml | kubectl apply -f -


kubectl wait pods -n $MONITORING_NAMESPACE -l app=prometheus-server --for condition=Ready --timeout=90s

MONITORING_POD=$(kubectl get pod -l app=prometheus-server -n $MONITORING_NAMESPACE -o jsonpath="{.items[0].metadata.name}")
kubectl port-forward $MONITORING_POD 18080:9090 -n $MONITORING_NAMESPACE &

