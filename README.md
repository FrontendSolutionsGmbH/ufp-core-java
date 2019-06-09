# UFP Core

Maven Project for the ufp-core java module. It is a spring boot application ready to use, and extend

# Prerequisites 

- ___Docker___ this project relies on docker for building and unit testing

# Unit Test

utilize maven openclover reporting for unit tests

	./maven.sh clean clover:setup test clover:aggregate clover:clover

# Build

utilize the maven dockerized script to build the module

	./maven-build.sh clean install

__Maven build requires jdk8__ the runtime can use openJDK12

# Update Version

	./update_versions.sh tag


# How to deploy to nexus.power.froso.de

set up environment vars:

	export UFP_MAVEN_USER=[USERNAME]
	export UFP_MAVEN_PASSWORD=[PASSWORD]
	
execute dockerized maven 

	./maven.sh deploy


# Repository/Linking

- use this repository as a linked reference git submodul - as it is in demo application

- rely only on maven dependency using the repository


        <repositories>
           <repository>
               <id>snapshots.froso.de</id>
               <name>Frontend Solutions GmBH Registry</name>
               <url>https://nexus.power.froso.de/repository/ufp-snapshots/</url>
               <layout>default</layout>
           </repository>
           <repository>
               <id>release.froso.de</id>
               <name>Frontend Solutions GmBH Registry</name>
               <url>https://nexus.power.froso.de/repository/ufp-release/</url>
               <layout>default</layout>
           </repository>
       </repositories>
       
and the dependency 

        <dependency>
            <groupId>com.froso.ufp.backend</groupId>
            <artifactId>ufp-core</artifactId>
            <version>1.0.5-RELEASE</version>
        </dependency>