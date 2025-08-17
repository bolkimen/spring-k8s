kubectl create namespace bolkimen-kafka-ns
kubectl delete namespace bolkimen-kafka-ns

./bin/kafka-topics.sh --create --topic quickstart-events --bootstrap-server localhost:19092