# Variables
APP_NAME := saveursolidaire
DOCKER_COMPOSE_FILE := compose.yaml
DOCKER_COMPOSE := docker-compose -f $(DOCKER_COMPOSE_FILE)

# Cibles
build:
	./mvnw clean package -DskipTests

docker-build: build
	$(DOCKER_COMPOSE) build

docker-up: docker-build
	$(DOCKER_COMPOSE) up -d

docker-down:
	$(DOCKER_COMPOSE) down

run: docker-up

stop: docker-down

restart: stop run

# Cible pour le build Java et le build Docker
build-and-docker: build docker-build

.PHONY: build docker-build docker-up docker-down run stop restart build-and-docker
