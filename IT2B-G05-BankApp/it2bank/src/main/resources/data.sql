INSERT INTO user_model(first_name, last_name, bsn, phone_number, email, password, role, daily_limit, transaction_limit) -- the password for this user is "Qwerty!" but hashed --
VALUES ('Soft', 'Kitty', 12345678, '0612345678', 'softest_kitty@gmail.com', '$2a$10$PyLBChOtTvGmqxl5Zi/w9uM0RcEFPs3qDHbjuKuTxo7Ko1RmRLqI.', 2, 100, 50);
INSERT INTO user_model(first_name, last_name, bsn, phone_number, email, password, role, daily_limit, transaction_limit)
VALUES ('Revolver', 'Ocelot', 23456789, '0676934748', 'shalashaska_ocelot@gmail.com', 'liquidOcelot4', 0, 100, 50);
INSERT INTO user_model(first_name, last_name, bsn, phone_number, email, password, role, daily_limit, transaction_limit) -- the password for this account is "Bankje!" but hashed --
VALUES ('Solid', 'Snake', 34567890, '0612345678', 'solidsnake@gmail.com', '$2a$10$8JSYaqGtWoZvaGhkbrI5/eUxwtlIHrmcDlJWgYlq5CMjVtSMwkBdG', 1, 100, 50);

-- This below is the bank itself bank account. DO NOT remove this.
INSERT INTO user_model(first_name, last_name, bsn, phone_number, email, password, role, daily_limit, transaction_limit) -- the password for this account is "Bankje!" but hashed --
VALUES ('Bank', 'je', 00000000, '0612345678', 'bankje@gmail.com', '$2a$10$8JSYaqGtWoZvaGhkbrI5/eUxwtlIHrmcDlJWgYlq5CMjVtSMwkBdG', 1, 100, 50);
CREATE TABLE ACCOUNT_MODEL (
  id INT PRIMARY KEY AUTO_INCREMENT,
  iban VARCHAR(50) NOT NULL,
  owner_id INT NOT NULL,
  status_id INT NOT NULL,
  balance DECIMAL(10, 2) NOT NULL,
  absolute_limit DECIMAL(10, 2) NOT NULL,
  type_id INT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
INSERT INTO account_model (iban, owner_id, status_id, balance, absolute_limit, type_id)
VALUES ('NL01INHO0000000001', 4, 0, 10000000, 0, 1);
INSERT INTO account_model (iban, owner_id, status_id, balance, absolute_limit, type_id)
VALUES ('NL01INHO0000000014', 3, 0, 1000, 0, 2);
INSERT INTO account_model (iban, owner_id, status_id, balance, absolute_limit, type_id)
VALUES ('NL01INHO0000000022', 1, 0, 1000, 0, 2);
INSERT INTO account_model (iban, owner_id, status_id, balance, absolute_limit, type_id)
VALUES ('NL01INHO0000000030', 2, 0, 1000, 0, 2);
