INSERT INTO user_model(first_name, last_name, bsn, phone_number, email, password, role, daily_limit, transaction_limit) -- the password for this user is "Qwerty!" but hashed --
VALUES ('Soft', 'Kitty', 12345678, '0612345678', 'softest_kitty@gmail.com', '$2a$10$PyLBChOtTvGmqxl5Zi/w9uM0RcEFPs3qDHbjuKuTxo7Ko1RmRLqI.', 2, 100, 50);
INSERT INTO user_model(first_name, last_name, bsn, phone_number, email, password, role, daily_limit, transaction_limit)
VALUES ('Revolver', 'Ocelot', 23456789, '0676934748', 'shalashaska_ocelot@gmail.com', 'liquidOcelot4', 0, 100, 50);
INSERT INTO user_model(first_name, last_name, bsn, phone_number, email, password, role, daily_limit, transaction_limit) -- the password for this account is "Bankje!" but hashed --
VALUES ('Solid', 'Snake', 34567890, '0612345678', 'solidsnake@gmail.com', '$2a$10$8JSYaqGtWoZvaGhkbrI5/eUxwtlIHrmcDlJWgYlq5CMjVtSMwkBdG', 1, 100, 50);

-- This below is the bank itself bank account. DO NOT remove this.
INSERT INTO user_model(first_name, last_name, bsn, phone_number, email, password, role, daily_limit, transaction_limit) -- the password for this account is "Bankje!" but hashed --
VALUES ('Bank', 'je', 00000000, '0612345678', 'bankje@gmail.com', '$2a$10$8JSYaqGtWoZvaGhkbrI5/eUxwtlIHrmcDlJWgYlq5CMjVtSMwkBdG', 1, 100, 50);
INSERT INTO account_model (iban, owner_id, status_id, amount, absolute_limit, type_id)
VALUES ('NL01INHO0000000001', 4, 0, 10000000, 0, 1);
INSERT INTO account_model (iban, owner_id, status_id, amount, absolute_limit, type_id)
VALUES ('NL01INHO0000000014', 3, 0, 1000, 0, 1);
INSERT INTO account_model (iban, owner_id, status_id, amount, absolute_limit, type_id)
VALUES ('NL01INHO0000000022', 3, 0, 1000, 0, 0);
INSERT INTO account_model (iban, owner_id, status_id, amount, absolute_limit, type_id)
VALUES ('NL01INHO0000000030', 2, 0, 1000, 0, 1);
INSERT INTO account_model (iban, owner_id, status_id, amount, absolute_limit, type_id)
VALUES ('NL01INHO0000000048', 4, 0, 1000, 0, 0);
