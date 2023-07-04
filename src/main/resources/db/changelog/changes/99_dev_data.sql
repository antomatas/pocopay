-- liquibase formatted sql

-- changeset anto:dev_data contextFilter:dev

INSERT INTO pocopay.account
    (id, account_number)
VALUES ('e66b0000-14c7-11ee-be56-0242ac120001', 'EE01');
INSERT INTO pocopay.balance
    (id, account_id, balance)
VALUES (gen_random_uuid(), 'e66b0000-14c7-11ee-be56-0242ac120001', 100);

INSERT INTO pocopay.account
    (id, account_number)
VALUES ('e66b0000-14c7-11ee-be56-0242ac120002', 'RU01');
INSERT INTO pocopay.balance
    (id, account_id, balance)
VALUES (gen_random_uuid(), 'e66b0000-14c7-11ee-be56-0242ac120002', 100);
