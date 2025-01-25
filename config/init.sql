create table USER
(
    USER_ID  INT not null AUTO_INCREMENT PRIMARY KEY,
    NAME     VARCHAR(256) not null,
    EMAIL    VARCHAR(256) not null,
    PASSWORD VARCHAR(256) not null,
    ROLE ENUM('EMPLOYEE', 'CITIZEN')
);

create table FEEDBACK
(
    FEEDBACK_ID INT not null AUTO_INCREMENT primary key,
    CATEGORY    ENUM('COMPLAINT', 'REQUEST', 'OTHER'),
    TITLE       VARCHAR(1000),
    CONTENT     TEXT,
    CITIZEN_ID  int,
    EMPLOYEE_ID int,
    COMMENT     TEXT,
    STATUS      ENUM('NEW', 'IN_PROGRESS', 'CLOSED'),
    CREATED_AT  timestamp(6),
    UPDATED_AT  timestamp(6),
    FOREIGN KEY (EMPLOYEE_ID) REFERENCES USER (USER_ID),
    FOREIGN KEY (CITIZEN_ID) REFERENCES USER (USER_ID)
);

-- TABLE USER
INSERT INTO `devdb`.USER (USER.USER_ID, NAME, EMAIL, PASSWORD, ROLE) VALUES (1,'testcitizen1', 'citizen1@test.de', '$2a$12$oQai/Cn6JX5OkF4NeSD1/OZpivbS/7oyEBc/.0KRkI54FjVUn/fpu', 'citizen');
INSERT INTO `devdb`.USER (USER.USER_ID, NAME, EMAIL, PASSWORD, ROLE) VALUES (1,'felicitas', 'felictias.yeboah@stud.th-luebeck.de', '$2a$12$HGwOADB1Mex5aLNR05SAgOgxGcadVEic.ReLl0ahhONJsCQBhZ4yq', 'citizen');
INSERT INTO `devdb`.USER (USER.USER_ID, NAME, EMAIL, PASSWORD, ROLE) VALUES (10,'testemployee1', 'employee1@test.de', '$2a$12$D.PR2/HqKeQn6a5uCQSJ1OGT955G1U2eb1J/QQnBtQoXyRz25jbIi', 'employee');


INSERT INTO `devdb`.FEEDBACK(CATEGORY, TITLE, CONTENT, CITIZEN_ID, EMPLOYEE_ID, COMMENT, STATUS, CREATED_AT, UPDATED_AT)
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