-- -- TABLE CATEGORY
-- INSERT INTO `dev-db`.CATEGORY (CATEGORY_ID, NAME) VALUES (1, 'Beschwerde');
-- INSERT INTO `dev-db`.CATEGORY (CATEGORY_ID, NAME) VALUES (2, 'Anfrage');
-- INSERT INTO `dev-db`.CATEGORY (CATEGORY_ID, NAME) VALUES (3, 'Sonstiges');
--
-- -- TABLE STATUS
-- INSERT INTO `dev-db`.STATUS (STATUS_ID, NAME) VALUES (1, 'NEW');
-- INSERT INTO `dev-db`.STATUS (STATUS_ID, NAME) VALUES (2, 'IN_PROCESS');
-- INSERT INTO `dev-db`.STATUS (STATUS_ID, NAME) VALUES (3, 'CLOSED');


-- TABLE USER
INSERT INTO `dev-db`.USER (USER.USER_ID, EMAIL, NAME, ROLE) VALUES (1,'citizen1@test.de', 'testcitizen1', 'citizen');
INSERT INTO `dev-db`.USER (USER.USER_ID, EMAIL, NAME, ROLE) VALUES (10,'employee1@test.de', 'testemployee1', 'employee');

INSERT INTO `dev-db`.FEEDBACK(CATEGORY, TITLE, CONTENT, CITIZEN_ID, EMPLOYEE_ID, COMMENT, STATUS, CREATED_AT, UPDATED_AT)
 VALUES (
'REQUEST',
'das ist ein Testitel',
'Das ist der sehr aussagekraeftige Feedbacktitel',
1,
10,
         null,
         'NEW',
CURRENT_TIMESTAMP,
CURRENT_TIMESTAMP);