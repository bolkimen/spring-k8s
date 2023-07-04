#!/bin/bash

envsubst < $SCRIPT_DIR/database/postgres-config-map.yaml | kubectl apply -f -
envsubst < $SCRIPT_DIR/database/postgres-pv-pvc.yaml | kubectl apply -f -
envsubst < $SCRIPT_DIR/database/postgres-deployment.yaml | kubectl apply -f -
envsubst < $SCRIPT_DIR/database/postgres-service.yaml | kubectl apply -f -

