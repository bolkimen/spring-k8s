#!/bin/bash

export SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )

#if [[ -z "${DOCKERHUB_USER}" ]]; then
#  echo "DOCKERHUB_USER env variable is not set" >&2
#  exit 1
#fi

#if [[ -z "${DOCKERHUB_PASSWORD}" ]]; then
#  echo "DOCKERHUB_PASSWORD env variable is not set" >&2
#  exit 1
#fi


export MONITORING_NAMESPACE=bolkimen-monitoring-ns
export GMONITORING_NAMESPACE=bolkimen-gmonitoring-ns
export DATABASE_NAMESPACE=bolkimen-database-ns
export KUBERNETES_NAMESPACE=bolkimen-spring-k8s-ns


$SCRIPT_DIR/1_init.sh
$SCRIPT_DIR/2_monitoring.sh
$SCRIPT_DIR/3_gmonitoring.sh
$SCRIPT_DIR/4_database.sh

