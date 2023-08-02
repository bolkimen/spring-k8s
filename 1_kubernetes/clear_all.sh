#!/bin/bash

export MONITORING_NAMESPACE=bolkimen-monitoring-ns
export GMONITORING_NAMESPACE=bolkimen-gmonitoring-ns
export DATABASE_NAMESPACE=bolkimen-database-ns
export KUBERNETES_NAMESPACE=bolkimen-spring-k8s-ns

kubectl delete namespaces $MONITORING_NAMESPACE $GMONITORING_NAMESPACE $DATABASE_NAMESPACE $KUBERNETES_NAMESPACE

for ns in $(kubectl get namespaces |grep -v NAME|awk '{print $1}')
do
   kubectl delete secret docker.registry -n $ns
done


# Additional steps for monitoring
kubectl delete clusterrolebinding prometheus
kubectl delete clusterrole prometheus

kubectl delete pv grafana-volume
kubectl delete pv prometheus-storage
kubectl delete pv postgresdb-storage

ps aux | grep forward | grep -v grep | awk {'print $2'} | xargs kill

