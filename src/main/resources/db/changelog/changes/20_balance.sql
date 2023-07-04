-- liquibase formatted sql

-- changeset anto:create_balance

CREATE TABLE BALANCE
(
    ID         UUID PRIMARY KEY not null default gen_random_uuid(),
    ACCOUNT_ID UUID UNIQUE      not null,
    BALANCE    NUMERIC          not null,
    CONSTRAINT FK_ACCOUNT FOREIGN KEY (ACCOUNT_ID) REFERENCES ACCOUNT (ID)
);
