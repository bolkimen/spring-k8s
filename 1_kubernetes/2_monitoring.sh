#/bin/bash

envsubst < $SCRIPT_DIR/monitoring/monClusterRole.yaml | kubectl apply -f -

