-- Database: programingfy

-- DROP DATABASE IF EXISTS programingfy;

CREATE DATABASE programingfy
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Portuguese_Brazil.1252'
    LC_CTYPE = 'Portuguese_Brazil.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

\c programingfy

CREATE TABLE users (
    id INT NOT NULL PRIMARY KEY UNIQUE,
    name VARCHAR(255) NOT NULL ,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR NOT NULL
);