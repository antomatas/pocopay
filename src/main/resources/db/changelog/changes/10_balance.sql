-- liquibase formatted sql

-- changeset anto:create_account

CREATE TABLE ACCOUNT
(
    ID             UUID PRIMARY KEY not null default gen_random_uuid(),
    ACCOUNT_NUMBER VARCHAR          not null
);
