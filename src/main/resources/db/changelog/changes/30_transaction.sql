-- liquibase formatted sql

-- changeset anto:create_transaction

CREATE TABLE TRANSACTION
(
    ID                   UUID PRIMARY KEY not null default gen_random_uuid(),
    RECIPIENT_ACCOUNT_ID UUID             not null,
    SENDER_ACCOUNT_ID    UUID             not null,
    AMOUNT               NUMERIC          not null,
    DESCRIPTION          VARCHAR          not null,
    CONSTRAINT FK_RECIPIENT_ACCOUNT FOREIGN KEY (RECIPIENT_ACCOUNT_ID) REFERENCES ACCOUNT (ID),
    CONSTRAINT FK_SENDER_ACCOUNT FOREIGN KEY (SENDER_ACCOUNT_ID) REFERENCES ACCOUNT (ID)
);
