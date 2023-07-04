#!/bin/bash
this=$(pwd)/$(dirname "$0")
docker build -t pocopay/postgresql:13.5-bullseye "$this" || exit 1
