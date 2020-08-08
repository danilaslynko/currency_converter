create role converter_db_admin with
    login
    nosuperuser
    inherit
    createdb
    createrole
    replication
    encrypted password 'md551b4e7b79f2ce6b22cd6718cd931d37e';

create database converter_db with
    owner = converter_db_admin
    encoding = 'UTF8'
    lc_collate = 'Russian_Russia.1251'
    lc_ctype = 'Russian_Russia.1251'
    tablespace = pg_default
    connection limit = -1;