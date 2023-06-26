#/bin/bash

envsubst < $SCRIPT_DIR/monitoring/prometheus-cluster-role.yaml | kubectl apply -f -
envsubst < $SCRIPT_DIR/monitoring/prometheus-pv-pvc.yaml | kubectl apply -f -

