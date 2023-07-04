#!/usr/bin/env bash

name="pocopay_pg"
image="pocopay/postgresql:13.5-bullseye"
port="${1:-5432}"

echo "Removing docker container '$name'";
docker rm -vf $name || true

echo "Creating new docker container '$name' from image '$image', listening on port $port"
docker run -d --network=pocopay-network --name=$name \
  -e TZ='Europe/Tallinn' \
  -e POSTGRES_PASSWORD=postgres \
  -p "$port":5432 \
  --restart=unless-stopped \
  -d $image
