-- Database: logusletter

DROP DATABASE IF EXISTS logusletter;

CREATE DATABASE logusletter
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Portuguese_Brazil.1252'
    LC_CTYPE = 'Portuguese_Brazil.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

\c logusletter

CREATE TABLE users (
    id INT NOT NULL PRIMARY KEY UNIQUE,
    name VARCHAR(255) NOT NULL ,
    email VARCHAR(255) NOT NULL UNIQUE
);