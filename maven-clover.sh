#!/usr/bin/env bash

function printIt {
	echo "[$(date)] ${1}"
}

export MAVEN_VOLUME_NAME="ufp-maven-clover"
printIt "UFP Maven Dockerized"
printIt "source: https://github.com/trifox/ufp-docker-wraps"
printIt "Docker Maven Volume Name is ${MAVEN_VOLUME_NAME}"

set -x # trace commands
date
if [  "$(docker volume ls|grep ${MAVEN_VOLUME_NAME})" ]; then

	printIt "Docker Maven Helper Volume Exists"
	printIt "Use docker volume prune ${MAVEN_VOLUME_NAME} to reinitialise"
  else
	printIt "Docker Maven Helper Volume Exists, create new with openclover config"
	#docker build -f Dockerfile.mavenclover -t ufp-maven9-clover  .
	docker container create --name dummy -v ${MAVEN_VOLUME_NAME}:/root/.m2 hello-world
	printIt "Enabling openclover in container maven config"
	docker cp $(pwd)/m2/settings.xml dummy:/root/.m2/settings.xml
	printIt "Removing docker helper container"
	docker rm dummy
fi

printIt "Running Dockerized maven command"

docker run \
  -m "2G" --memory-swap "2G" \
  -w "/usr/src/myapp" \
  --rm \
  --name "ufp-maven-app" \
  -v "${MAVEN_VOLUME_NAME}:/root/.m2" \
  -v $(pwd):/usr/src/myapp  \
   maven:3.6-jdk-8 mvn $@

printIt "Finish"
