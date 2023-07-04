#!/bin/bash
RM pocopay-home-assignment.jar
CP ../../../build/libs/pocopay-home-assignment.jar pocopay-home-assignment.jar
docker build -t pocopay-api .

