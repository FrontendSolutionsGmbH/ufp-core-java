#!/usr/bin/env bash


function printIt {
	echo "[$(date)] ${1}"
}


export MAVEN_VOLUME_NAME="ufp-maven-build-deploy3"
printIt "UFP Maven Dockerized"
printIt "source: https://github.com/trifox/ufp-docker-wraps"
printIt "Use environment vars UFP_MAVEN_USER and UFP_MAVEN_PASSWORD to identify against nexus.power.froso.de"
printIt "Docker Maven Volume Name is ${MAVEN_VOLUME_NAME}"
printIt "Start"

docker volume ls

set -x # trace commands
date
if [  "$(docker volume ls|grep ${MAVEN_VOLUME_NAME})" ]; then

printIt "Docker Maven Helper Volume Exists"
printIt "Use docker volume prune ${MAVEN_VOLUME_NAME} to reinitialise"

else
printIt "Docker Maven Helper Volume Exists, create new with OpenClover config"
#docker build -f Dockerfile.mavenclover -t ufp-maven9-clover  .
docker container create --name dummy -v ${MAVEN_VOLUME_NAME}:/root/.m2 hello-world
printIt "Enabling Registry user password access in container maven config"
printIt "making temporary file"


echo  "<settings xmlns=\"http://maven.apache.org/SETTINGS/1.0.0\"
      xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"
      xsi:schemaLocation=\"http://maven.apache.org/SETTINGS/1.0.0
                          https://maven.apache.org/xsd/settings-1.0.0.xsd\">
      <servers>
         <server>
		  <id>snapshots.froso.de</id>
		  <username>${UFP_MAVEN_USER}</username>
		  <password>${UFP_MAVEN_PASSWORD}</password>
         </server>
         <server>
		  <id>release.froso.de</id>
		  <username>${UFP_MAVEN_USER}</username>
		  <password>${UFP_MAVEN_PASSWORD}</password>
         </server>
      </servers>
  </settings>
">settings-delete.xml


docker cp $(pwd)/settings-delete.xml dummy:/root/.m2/settings.xml
printIt "Removing docker helper container and temporary file"
docker rm dummy
rm settings-delete.xml
fi


docker run \
  -m "2G" --memory-swap "2G" \
  -w "/usr/src/myapp" \
  --rm \
  --name 	"app-${MAVEN_VOLUME_NAME}" \
  -v "${MAVEN_VOLUME_NAME}:/root/.m2" \
  -v $(pwd):/usr/src/myapp  \
   maven:3.6-jdk-8 mvn $@

printIt "Finish"
