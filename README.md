# k8s-starter-project

Spring Boot 4 + Java 25 starter for Kubernetes.

## APIs

| Endpoint | Header | Description |
|----------|--------|-------------|
| `GET /actuator/health` | — | Health check |
| `GET /welcome` | `X-API-Version: 1.0` | v1 message |
| `GET /welcome` | `X-API-Version: 2.0` | v2 message |

## Build

```bash
mvn spring-boot:run
```

## Docker

```bash
docker build -t k8s-starter . && docker run -p 8080:8080 k8s-starter
```
