#!/bin/bash

envsubst < $SCRIPT_DIR/gmonitoring/grafana-pv-pvc.yaml | kubectl apply -f -
envsubst < $SCRIPT_DIR/gmonitoring/grafana-deployment.yaml | kubectl apply -f -
envsubst < $SCRIPT_DIR/gmonitoring/grafana-service.yaml | kubectl apply -f -


kubectl wait pods -n $GMONITORING_NAMESPACE -l app=grafana-server --for condition=Ready --timeout=90s

MONITORING_POD=$(kubectl get pod -l app=grafana-server -n $GMONITORING_NAMESPACE -o jsonpath="{.items[0].metadata.name}")
kubectl port-forward $MONITORING_POD 18082:3000 -n $GMONITORING_NAMESPACE &

