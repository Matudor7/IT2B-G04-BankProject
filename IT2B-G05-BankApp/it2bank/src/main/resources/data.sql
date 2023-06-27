INSERT INTO user_model(first_name, last_name, bsn, phone_number, email, password, role, daily_limit, transaction_limit) -- the password for this user is "Qwerty!" but hashed --
VALUES ('Soft', 'Kitty', 12345678, '0612345678', 'softest_kitty@gmail.com', '$2a$10$PyLBChOtTvGmqxl5Zi/w9uM0RcEFPs3qDHbjuKuTxo7Ko1RmRLqI.', 2, 100, 50);
INSERT INTO user_model(first_name, last_name, bsn, phone_number, email, password, role, daily_limit, transaction_limit)
VALUES ('Revolver', 'Ocelot', 23456789, '0676934748', 'shalashaska_ocelot@gmail.com', 'liquidOcelot4', 0, 100, 50);
INSERT INTO user_model(first_name, last_name, bsn, phone_number, email, password, role, daily_limit, transaction_limit) -- the password for this account is "Bankje!" but hashed --
VALUES ('Solid', 'Snake', 34567890, '0612345678', 'solidsnake@gmail.com', '$2a$10$8JSYaqGtWoZvaGhkbrI5/eUxwtlIHrmcDlJWgYlq5CMjVtSMwkBdG', 1, 100, 50);

-- This below is the bank itself bank account. DO NOT remove this.
INSERT INTO user_model(first_name, last_name, bsn, phone_number, email, password, role, daily_limit, transaction_limit) -- the password for this account is "Bankje!" but hashed --
VALUES ('Bank', 'je', 00000000, '0612345678', 'bankje@gmail.com', '$2a$10$8JSYaqGtWoZvaGhkbrI5/eUxwtlIHrmcDlJWgYlq5CMjVtSMwkBdG', 1, 100, 50);
-- This below is the bank itself bank account. DO NOT remove this.
INSERT INTO user_model(first_name, last_name, bsn, phone_number, email, password, role, daily_limit, transaction_limit) -- the password for this account is "Bankje!" but hashed --
VALUES ('Liquid', 'Snake', 34567899, '0612545678', 'liquidsnake@gmail.com', '$2a$10$8JSYaqGtWoZvaGhkbrI5/eUxwtlIHrmcDlJWgYlq5CMjVtSMwkBdG', 0, 100, 50);
INSERT INTO user_model(first_name, last_name, bsn, phone_number, email, password, role, daily_limit, transaction_limit) -- the password for this account is "Bankje!" but hashed --
VALUES ('Otacon', 'Emmerich', 45678901, '0612876678', 'octaconemmerich@gmail.com', '$2a$10$8JSYaqGtWoZvaGhkbrI5/eUxwtlIHrmcDlJWgYlq5CMjVtSMwkBdG', 0, 100, 50);
INSERT INTO user_model(first_name, last_name, bsn, phone_number, email, password, role, daily_limit, transaction_limit) -- the password for this account is "Bankje!" but hashed --
VALUES ('Liquid', 'Ocelot', 56789012, '0613245678', 'liquidocelot@gmail.com', '$2a$10$8JSYaqGtWoZvaGhkbrI5/eUxwtlIHrmcDlJWgYlq5CMjVtSMwkBdG', 0, 100, 50);

INSERT INTO bank_account_model (iban, owner_id, status_id, balance, absolute_limit, type_id)
VALUES ('NL01INHO0000000001', 4, 0, 10000000, 0, 1);
INSERT INTO bank_account_model (iban, owner_id, status_id, balance, absolute_limit, type_id)
VALUES ('NL01INHO0000000014', 3, 0, 1000, 0, 1);
INSERT INTO bank_account_model (iban, owner_id, status_id, balance, absolute_limit, type_id)
VALUES ('NL01INHO0000000022', 3, 0, 1000, 0, 0);
INSERT INTO bank_account_model (iban, owner_id, status_id, balance, absolute_limit, type_id)
VALUES ('NL01INHO0000000030', 2, 0, 1000.0, 0, 1);
INSERT INTO bank_account_model (iban, owner_id, status_id, balance, absolute_limit, type_id)
VALUES ('NL01INHO0000000048', 4, 0, 1000, 0, 0);
INSERT INTO transaction_model (user_performing, account_from, account_to, amount, date_time, comment)
VALUES (2, 'NLHO234', 'NLHOIO23456', '1234', '2023-06-12T10:04:00', '');
INSERT INTO transaction_model (user_performing, account_from, account_to, amount, date_time, comment)
VALUES (3, 'NL01INHO0000000001', 'NLHOIO23456', '1234', '2023-06-12T10:04:00', 'test');
INSERT INTO transaction_model (user_performing, account_from, account_to, amount, date_time, comment)
VALUES (2, 'NLHO234', 'NLHOIO23456', '500', '2023-06-13T09:15:00', 'Payment for rent');

INSERT INTO transaction_model (user_performing, account_from, account_to, amount, date_time, comment)
VALUES (3, 'NL01INHO0000000001', 'NLHOIO23456', '300', '2023-06-13T12:30:00', 'Grocery shopping');

INSERT INTO transaction_model (user_performing, account_from, account_to, amount, date_time, comment)
VALUES (4, 'NL01INHO0000000014', 'NLHO234', '150', '2023-06-14T14:45:00', 'Dinner with friends');

INSERT INTO transaction_model (user_performing, account_from, account_to, amount, date_time, comment)
VALUES (2, 'NL01INHO0000000030', 'NL01INHO0000000048', '1000', '2023-06-14T16:30:00', 'Loan repayment');

INSERT INTO transaction_model (user_performing, account_from, account_to, amount, date_time, comment)
VALUES (3, 'NLHOIO23456', 'NL01INHO0000000022', '200', '2023-06-15T10:00:00', 'Clothing purchase');

INSERT INTO transaction_model (user_performing, account_from, account_to, amount, date_time, comment)
VALUES (4, 'NL01INHO0000000048', 'NLHO234', '800', '2023-06-16T13:15:00', 'Travel expenses');

INSERT INTO transaction_model (user_performing, account_from, account_to, amount, date_time, comment)
VALUES (2, 'NL01INHO0000000030', 'NLHOIO23456', '600', '2023-06-17T16:45:00', 'Payment for services');

INSERT INTO transaction_model (user_performing, account_from, account_to, amount, date_time, comment)
VALUES (3, 'NLHOIO23456', 'NL01INHO0000000022', '150', '2023-06-18T11:30:00', 'Coffee with colleagues');

INSERT INTO transaction_model (user_performing, account_from, account_to, amount, date_time, comment)
VALUES (4, 'NL01INHO0000000014', 'NLHO234', '400', '2023-06-18T14:00:00', 'Gift purchase');

INSERT INTO transaction_model (user_performing, account_from, account_to, amount, date_time, comment)
VALUES (2, 'NL01INHO0000000001', 'NL01INHO0000000048', '250', '2023-06-19T09:30:00', 'Transfer to savings account');
