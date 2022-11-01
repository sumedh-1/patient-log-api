-- liquibase formatted sql
-- changeSet ts-1:1 failOnError:true logicalFilePath:initpostgres.sql

CREATE TABLE IF NOT EXISTS "user_profiles"
(
    "id"                SERIAL PRIMARY KEY NOT NULL,
    "name"              VARCHAR(100) NOT NULL,
    "email"             VARCHAR(100) UNIQUE NOT NULL,
    "password"          VARCHAR(100) NOT NULL,
    "user_type"         VARCHAR(50) NOT NULL,
    "dob"               VARCHAR(50),
    "ssn"               VARCHAR(100) UNIQUE,
    "address"           VARCHAR(300),
    "phone"             VARCHAR(15),
    "mrn"               VARCHAR(100) UNIQUE
);
