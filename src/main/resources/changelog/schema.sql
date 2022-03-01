CREATE TABLE users (
    id              SERIAL              PRIMARY KEY,
    username        VARCHAR ( 16 )      UNIQUE NOT NULL,
    password        VARCHAR ( 64 )      NOT NULL,
    role            VARCHAR ( 16 )      NOT NULL,
    terms_agreed    BOOLEAN             DEFAULT FALSE
);

CREATE TABLE sectors (
    code                    VARCHAR ( 50 )      PRIMARY KEY,
    name                    VARCHAR ( 50 )      NOT NULL,
    numeric_value           INT                 NOT NULL UNIQUE,
    sub_type_level          INT                 NOT NULL
);

CREATE TABLE user_sectors (
    user_id                 INT                 NOT NULL REFERENCES users (id),
    sector_code             VARCHAR ( 50 )      NOT NULL REFERENCES sectors (code),
    UNIQUE (user_id, sector_code)
);