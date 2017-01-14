#!/bin/bash

docker build -t applets_api_db docker-dir/db
docker run --name applets_api_db -p 5432:5432 -e POSTGRES_PASSWORD=postgres -d applets_api_db
