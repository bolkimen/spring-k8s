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


List persistent volumes
```
kubectl get pv
```
List persistent volume claim
```
kubectl get pvc
```

## Monitoring

prometeus


