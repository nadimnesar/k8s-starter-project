# k8s-starter-project

[![Java](https://img.shields.io/badge/Java-25-%23ED8B00?logo=openjdk)](https://openjdk.org/projects/jdk/25/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0-6DB33F?logo=springboot)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-blue)](LICENSE)

A modern, minimal **Spring Boot 4 + Java 25** starter project designed for Kubernetes deployment. Serves as a reference
template for building and deploying containerized Spring Boot services on Minikube.

## Prerequisites

* Java 25
* Maven 3.9+
* Docker
* Minikube
* kubectl

## Quick Start

```bash
# Clone and run locally (without Kubernetes)
git clone https://github.com/nadimnesar/k8s-starter-project.git
cd k8s-starter-project

# Run the app
make run
```

## API Reference

| Endpoint       | Header               | Response                        |
|----------------|----------------------|---------------------------------|
| `GET /health`  | —                    | `{"status":"UP"}`               |
| `GET /welcome` | `X-API-Version: 1.0` | `{"text":"...","apiVersion":1}` |
| `GET /welcome` | `X-API-Version: 2.0` | `{"text":"...","apiVersion":2}` |

## Testing

```bash
make test
```

## Kubernetes (Minikube) — Step by Step

### 1. Clone the repository

```bash
git clone https://github.com/nadimnesar/k8s-starter-project.git
cd k8s-starter-project
```

### 1. Start Minikube

```bash
minikube config set driver docker
minikube start
```

Verify the cluster:

```bash
kubectl cluster-info
kubectl get nodes
```

### 2. Build the application

```bash
mvn package -DskipTests
```

### 3. Shows images inside Minikube cluster

This allows image is built directly inside Minikube, so Kubernetes can use it immediately.

```bash
eval $(minikube docker-env)
```

### 4. Build the Docker image

```bash
docker build -t k8s-starter .
```

### 5. Load the image into Minikube

Skip this step if you already applied step 3 to build the image directly inside Minikube.

```bash
minikube image load k8s-starter
```

### 5. Deploy to Kubernetes

Apply all manifests (namespace, ConfigMap, deployment, service):

```bash
kubectl apply -f k8s/
```

Watch the pods spin up:

```bash
kubectl -n k8s-starter get pods
```

Check logs to ensure the application is running:

```bash
kubectl -n k8s-starter logs -l app=k8s-starter
```

### 6. Access the application

```bash
minikube service k8s-starter -n k8s-starter
```

This opens the service in your browser. Also give you NodePort and Minikube IP to access the endpoints directly via
curl.

Example:

```text
➜ k8s-starter-project git:(master) ✗ minikube service k8s-starter -n k8s-starter
┌─────────────┬─────────────┬─────────────┬───────────────────────────┐
│ NAMESPACE   │ NAME        │ TARGET PORT │ URL                       │
├─────────────┼─────────────┼─────────────┼───────────────────────────┤
│ k8s-starter │ k8s-starter │ 8080        │ http://192.168.49.2:30080 │
└─────────────┴─────────────┴─────────────┴───────────────────────────┘
🎉 Opening service k8s-starter/k8s-starter in default browser...
```

Curl the endpoints:

```bash
curl http://<MINIKUBE_IP>:<NODE_PORT>/health
curl http://<MINIKUBE_IP>:<NODE_PORT>/welcome -H "X-API-Version: 1.0"
curl http://<MINIKUBE_IP>:<NODE_PORT>/welcome -H "X-API-Version: 2.0"
```

### 7. Clean up

Tear down all Kubernetes resources:

```bash
kubectl delete -f k8s/
```

## Contributing

Contributions are welcome! Open an issue or submit a pull request.

## License

[MIT](LICENSE) — Nesar Ahmed
