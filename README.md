# spring-k8s

## Pre install
1. Create the directory you want to share with Kubernetes
```
sudo mkdir -p /mnt/persistForKuber
```
2. Make the folder public
```
sudo chown nobody:nogroup /mnt/persistForKuber
```
3. Allow read, write and execute in this folder
```
sudo chmod 777 /mnt/persistForKuber
```

List namespaces
```
kubectl get ns
```
List persistent volumes
```
kubectl get pv
```
List persistent volume claim
```
kubectl get pvc
```

http://localhost:8761/
http://localhost:8073/api/servicea/ab
http://192.168.0.199:8090/api/servicea/ab
http://192.168.0.199:8091/api/serviceb

## AWS emulator:
https://docs.getmoto.org/en/latest/docs/server_mode.html
https://docs.aws.amazon.com/prescriptive-guidance/latest/patterns/test-aws-infra-localstack-terraform.html

## Debug

Run shell on debug pod
```
kubectl exec -it debug-pv-pod -- /bin/bash
```

Ingress:
kubectl describe ing nginx-app-ingress -n bolkimen-nginx-ns

https://gateway.envoyproxy.io/latest/tasks/quickstart/
kubectl apply -f https://github.com/envoyproxy/gateway/releases/download/latest/quickstart.yaml -n default

## Monitoring

prometeus


