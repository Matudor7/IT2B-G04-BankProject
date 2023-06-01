INSERT INTO user_model(first_name, last_name, bsn, phone_number, email, password, role, daily_limit, transaction_limit)
VALUES ('Soft', 'Kitty', 12345678, '0612345678', 'softest_kitty@gmail.com', 'Myverystr0ngpassworD1234!', 1, 100, 50);
INSERT INTO user_model(first_name, last_name, bsn, phone_number, email, password, role, daily_limit, transaction_limit)
VALUES ('Revolver', 'Ocelot', 23456789, '0676934748', 'shalashaska_ocelot@gmail.com', 'liquidOcelot4', 0, 100, 50);
INSERT INTO user_model(first_name, last_name, bsn, phone_number, email, password, role, daily_limit, transaction_limit)
VALUES ('Solid', 'Snake', 34567890, '0612345678', 'solidsnake@gmail.com', 'solidSnake4', 2, 100, 50);

-- This below is the bank itself bank account. DO NOT remove this.
INSERT INTO user_model(first_name, last_name, bsn, phone_number, email, password, role, daily_limit, transaction_limit)
VALUES ('Bank', 'je', 00000000, '0612345678', 'bankje@gmail.com', 'bankje', 1, 100, 50);
INSERT INTO account_model (iban, owner_id, status_id, amount, absolute_limit, type_id)
VALUES ('NL01INHO0000000001', 4, 0, 10000000, 0, 1);
INSERT INTO account_model (iban, owner_id, status_id, amount, absolute_limit, type_id)
VALUES ('NL01INHO0000000014', 3, 0, 1000, 0, 2);
INSERT INTO account_model (iban, owner_id, status_id, amount, absolute_limit, type_id)
VALUES ('NL01INHO0000000022', 1, 0, 1000, 0, 2);
INSERT INTO account_model (iban, owner_id, status_id, amount, absolute_limit, type_id)
VALUES ('NL01INHO0000000030', 2, 0, 1000, 0, 2);
