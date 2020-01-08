CREATE TABLE user_information (
	user_id Serial NOT NULL PRIMARY key,
	user_userid TEXT NOT null,
	user_password TEXT NOT null,
	user_name TEXT NOT NULL,
	user_questionid int NOT NULL REFERENCES question_table(question_id),
	user_answer TEXT NOT null 
);

SELECT * FROM user_information ui;


CREATE TABLE account_numbers (
	account_number int NOT NULL PRIMARY KEY,
	account_type TEXT NOT NULL,
	account_balance numeric(19,4) NOT NULL, 
	user_id integer REFERENCES user_information(user_id)
);

CREATE TABLE list_transactions (
	transaction_number serial NOT NULL PRIMARY KEY,
	account_number integer REFERENCES account_numbers(account_number),
	transaction_description TEXT NOT NULL,
	balance_adjustment numeric(19,4) NOT NULL,
	transaction_time timestamp NOT null
);

SELECT * FROM list_transactions lt;
INSERT INTO list_transactions(account_number,transaction_description,balance_adjustment,transaction_time)
VALUES
(?,'Internal Transfer',?,current_timestamp)

CREATE TABLE question_table (
	question_id integer PRIMARY KEY,
	question TEXT
);

CREATE TABLE ssid_table(
	user_ssid int NOT NULL,
	userid int REFERENCES user_information(user_id)
);

INSERT INTO user_information(user_userid, user_password, user_name, user_questionid, user_answer)
VALUES
('billybob','password','billy bobby', 1, 'Cat');

INSERT INTO question_table(question_id, question)
VALUES
(4, 'In what city where you born?');

SELECT * FROM user_information ui;

SELECT * FROM ssid_table st2;

INSERT INTO ssid_table(user_ssid, userid)
VALUES
(987654321, 2);

UPDATE question_table SET question = 'What is your mother''s maiden name?' WHERE question_id = 1;

SELECT * FROM question_table;

SELECT user_id FROM user_information WHERE user_userid = 'testing1';

SELECT * FROM ssid_table st;

SELECT * FROM account_numbers WHERE user_id = 24;

INSERT INTO account_numbers(account_number, account_type, account_balance, user_id)
VALUES
(400000000, 'Checking', 0, 2);

UPDATE account_numbers SET account_balance = 100.25 WHERE account_number = 400000000;

SELECT * FROM user_information

SELECT * FROM user_information WHERE user_userid = 'testing5';

ALTER TABLE user_information ADD user_pendingtransferaccount int;

UPDATE user_information SET user_pendingtransferaccount = 400000000  WHERE user_userid = 'testing5';

UPDATE user_information SET user_haspendingtransfer = FALSE, user_pendingTransfersender = NULL, user_pendingtransferamount = NULL,
user_pendingtransferaccount = NULL
WHERE user_id = 17;

SELECT * FROM list_transactions lt;
