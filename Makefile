.PHONY: run test build docker-build docker-run k8s-start k8s-build k8s-load k8s-deploy k8s-pods k8s-service k8s-clean

APP_NAME := k8s-starter

run:
	mvn spring-boot:run

test:
	mvn test

build:
	mvn package -DskipTests

docker-build:
	docker build -t $(APP_NAME) .

docker-run:
	docker run -p 8080:8080 $(APP_NAME)

k8s-start:
	minikube start --driver=docker

k8s-build: build docker-build

k8s-load:
	minikube image load $(APP_NAME):latest

k8s-deploy: k8s-load
	kubectl apply -f k8s/

k8s-pods:
	kubectl -n $(APP_NAME) get pods -w

k8s-service:
	minikube service $(APP_NAME) -n $(APP_NAME)

k8s-clean:
	kubectl delete -f k8s/
