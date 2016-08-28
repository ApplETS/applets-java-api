#!/bin/bash
chown postgres:postgres /root/applets_api_db.bak

set -e


psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    CREATE DATABASE applets_api_db;
    GRANT ALL PRIVILEGES ON DATABASE applets_api_db TO $POSTGRES_USER;
EOSQL


pg_restore -U postgres -d applets_api_db --no-owner -v "/root/applets_api_db.bak"