create table USER
(
    USER_ID  INT not null PRIMARY KEY,
    NAME     VARCHAR(256) not null,
    EMAIL    VARCHAR(256) not null,
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
