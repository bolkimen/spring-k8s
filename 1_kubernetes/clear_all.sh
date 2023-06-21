#!/bin/bash

export KUBERNETES_NAMESPACE=bolkimen-spring-k8s-ns

#kubectl delete -n $KUBERNETES_NAMESPACE --all

kubectl delete namespaces $KUBERNETES_NAMESPACE

