#!/bin/bash

export SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
if [[ -z "${KAFKA_NAMESPACE}" ]]; then
  export KAFKA_NAMESPACE=bolkimen-kafka-ns
fi

echo "SCRIPT_DIR: $SCRIPT_DIR"
echo "KAFKA_NAMESPACE: $KAFKA_NAMESPACE"

envsubst < $SCRIPT_DIR/kafka/kafka-deployment.yaml | kubectl apply -f -
envsubst < $SCRIPT_DIR/kafka/kafka-service.yaml | kubectl apply -f -


kubectl wait pods -n $KAFKA_NAMESPACE -l app=kafka-server --for condition=Ready --timeout=90s

MONITORING_POD=$(kubectl get pod -l app=kafka-server -n $KAFKA_NAMESPACE -o jsonpath="{.items[0].metadata.name}")
kubectl port-forward $MONITORING_POD 19092:9092 -n $KAFKA_NAMESPACE &

