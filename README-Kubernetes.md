https://ubuntu.com/kubernetes/install

sudo snap install k8s --classic
sudo k8s bootstrap
sudo k8s status
sudo k8s kubectl get all --all-namespaces

Local development:
docker tag service_b:0.0.1-SNAPSHOT bolkimen/service_b:0.0.1-SNAPSHOT
docker push bolkimen/service_b:0.0.1-SNAPSHOT

kubectl port-forward spring-service-b-696d6fcd4-98ffq 8091:8091

kubectl delete deployments,services -l proj=spring-cloud-test
