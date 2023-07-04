#!/usr/bin/env bash
docker rm -vf pocopay_pg
./build.sh
./run.sh
