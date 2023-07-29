#!/bin/bash

envsubst < $SCRIPT_DIR/database/postgres-config-map.yaml | kubectl apply -f -
envsubst < $SCRIPT_DIR/database/postgres-pv-pvc.yaml | kubectl apply -f -
envsubst < $SCRIPT_DIR/database/postgres-deployment.yaml | kubectl apply -f -
envsubst < $SCRIPT_DIR/database/postgres-service.yaml | kubectl apply -f -

kubectl wait pods -n $DATABASE_NAMESPACE -l app=postgresdb --for condition=Ready --timeout=90s

DATABASE_MONITORING_POD=$(kubectl get pod -l app=postgresdb -n $DATABASE_NAMESPACE -o jsonpath="{.items[0].metadata.name}")
kubectl port-forward $DATABASE_MONITORING_POD 18081:9187 -n $DATABASE_NAMESPACE &

