![Build Status](https://travis-ci.com/JicLotus/football-api.svg?branch=master)


# Overview

Basically is a simple REST API project to consume 
We have set up travis ci for CI. On every commit 

https://www.football-data.org/

# Configuration

To setup your application modify

- importer.sleeptime.miliseconds=>Your desireds miliseconds between every importer request
- importer.token=Your football-data.org token account

# Development flow

- Master - Production code
- Release - All development branches needs to be based from here
- Bugfixes - Based on Master or Releanse, depending where the bug becomes
- Creating Issues && Milestones based on requirements
- Before mergin a freature, we are creating PR.

# Requirements

- Install jdk8
- Install docker in case that you want to use docker-compose mysql image
- Install docker-compose

# Install

// Or use mvn if you installed that before
- ./mvnw Install

// To build docker database image, you can skip this if you are using a previous mysql one
- docker-compose build

# RUN

// To start mysql on port 3306, you can skip this if you are using a previous mysql one
- docker-compose up -d
- ./mvnw spring-boot:run