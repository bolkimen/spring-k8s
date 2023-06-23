#!/bin/bash

export MONITORING_NAMESPACE=bolkimen-monitoring-ns
export KUBERNETES_NAMESPACE=bolkimen-spring-k8s-ns

kubectl delete namespaces $MONITORING_NAMESPACE $KUBERNETES_NAMESPACE

for ns in $(kubectl get namespaces |grep -v NAME|awk '{print $1}')
do
   kubectl delete secret docker.registry -n $ns
done

