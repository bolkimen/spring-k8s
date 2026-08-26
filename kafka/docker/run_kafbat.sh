#!/bin/bash

docker run -d  \
  --name kafbat \
  -p 38080:8080 \
  -e DYNAMIC_CONFIG_ENABLED=true \
  -e KAFKA_CLUSTERS_0_NAME=local \
  -e KAFKA_CLUSTERS_0_BOOTSTRAPSERVERS=broker:9092 \
  ghcr.io/kafbat/kafka-ui:latest