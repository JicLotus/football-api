![Build Status](https://travis-ci.com/JicLotus/football-api.svg?branch=master)


# Overview

Basically is a simple REST API project that consumes https://www.football-data.org/ to import to the local database
We have set up Travis ci for CI. So on every commit will be running all tests 

To view API documentation you can check swagger

http://localhost:8080/swagger-ui.html


# Configuration

To set up your application modifies "application-dev.properties" file:

- importer.sleeptime.miliseconds=>Your desired milliseconds between every importer request
- importer.token=Your football-data.org token account

# Development flow

- Master - Production code
- Release - All development branches need to be based from here
- Bugfixes - Based on Master or Release, depending on where the bug becomes
- Creating Issues && Milestones based on requirements
- Before merging a feature, we are creating PR.

# Requirements

- Install jdk8
- Install docker in case that you want to use docker-compose MySQL image
- Install docker-compose

# Install

// Or use mvn if you installed that before
- ./mvnw Install

// To build docker database image, you can skip this if you are using a previous mysql one
- docker-compose build

# RUN

// To start mysql on port 3306, you can skip this if you are using a previous MySQL one
- docker-compose up -d
- ./mvnw spring-boot:run