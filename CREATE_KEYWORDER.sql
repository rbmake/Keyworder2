-- Holds a list of domains
CREATE TABLE DOMAIN
(ID int(5) auto_increment,
NAME varchar(150),
PRIMARY KEY (ID));

-- Holds keyphrases for a domain and whether they should be searched or not
CREATE TABLE KEYPHRASE
(ID int(5) auto_increment,
DOMAIN_ID int(5),
PHRASE varchar(100),
DO_SEARCH boolean,
PRIMARY KEY (ID));

-- Holds search engine list
CREATE TABLE SEARCH_ENGINE
(ID int(5) auto_increment,
NAME varchar(50),
PRIMARY KEY (ID));


CREATE TABLE RANKING
(ID int(5) auto_increment,
RANKING_HISTORY_ID int(10),
URL varchar(300),
KEYPHRASE_ID int(5),
RANKING int(5),
TIMESTAMP timestamp,
PRIMARY KEY (ID));

CREATE TABLE DOMAIN_RANKING
(ID int(5) auto_increment,
RANKING_HISTORY_ID int(10),
DOMAIN_ID int(5),
URL varchar(300),
KEYPHRASE_ID int(5),
RANKING int(5),
TIMESTAMP timestamp,
PRIMARY KEY (ID));

CREATE TABLE RANKING_HISTORY
(ID int(10) auto_increment,
SEARCH_ENGINE varchar(50),
NUM_RESULTS int(5),
LANGUAGE varchar(50),
CLIENT_BROWSER varchar(50),
TIMESTAMP timestamp,
PRIMARY KEY (ID));

CREATE TABLE USER_DOMAIN
(ID int(5) auto_increment,
DOMAIN_ID int(5),
USER_ID int(5),
PRIMARY KEY (ID));

CREATE TABLE USER
(ID int(5) auto_increment,
TITLE_ID int(3),
FIRST_NAME varchar(50),
SURNAME varchar(50),
EMAIL varchar(100), 
NUM_LOGON_FAILURES int(3),
IS_ACCOUNT_LOCKED boolean,
PASSWORD varchar(20),
TELEPHONE_NUMBER varchar(300),
RECEIVE_EMAIL_NEWSLETTER boolean,
PRIVILEGE_ID int(3),
COOKIE_CODE varchar(50),
PRIMARY KEY (ID));

CREATE TABLE TITLE
(ID int(3) auto_increment,
NAME varchar(50),
PRIMARY KEY (ID));


CREATE TABLE AREA (ID int(5), NAME varchar(30));

INSERT INTO AREA values (1, "UK"), (2, "Europe"), (3, "North America");

CREATE TABLE COUNTRY
(ID int(20),
NAME varchar(50),
CODE varchar(3),
PRIORITY int(5),
AREA_ID int(5),
TWO_LETTER_NAME varchar(2),
PRIMARY KEY (ID),
FOREIGN KEY (AREA_ID) REFERENCES AREA(ID));



CREATE TABLE REQUEST
(ID int(10) auto_increment,
TIMESTAMP timestamp,
PRIMARY KEY (ID));

ALTER TABLE DOMAIN_RANKING ADD COLUMN REQUEST_ID int(10);

ALTER TABLE RANKING ADD COLUMN REQUEST_ID int(10);

ALTER TABLE RANKING_HISTORY ADD COLUMN REQUEST_ID int(10);

alter table DOMAIN_RANKING add foreign key KEYPHRASE_ID (KEYPHRASE_ID ) references KEYPHRASE.ID;
alter table DOMAIN_RANKING add foreign key RANKING_HISTORY_ID (RANKING_HISTORY_ID) references RANKING_HISTORY.ID;
alter table DOMAIN_RANKING add foreign key REQUEST_ID(REQUEST_ID) references REQUEST.ID;
alter table DOMAIN_RANKING add foreign key DOMAIN_ID (DOMAIN_ID) references DOMAIN.ID;
alter table RANKING add foreign key KEYPHRASE_ID (KEYPHRASE_ID) references KEYPHRASE.ID;
alter table RANKING add foreign key RANKING_HISTORY_id (RANKING_history_id) references RANKING_HISTORY.ID;
alter table RANKING add foreign key REQUEST_ID (REQUEST_ID) references REQUEST.ID;
alter table RANKING_HISTORY add foreign key REQUEST_ID (REQUEST_ID) references REQUEST.id;
alter table USER_DOMAIN add foreign key DOMAIN_ID (DOMAIN_ID) references DOMAIN.ID;
alter table USER_DOMAIN add foreign key USER_ID (USER_ID) references USER.id;
alter table SEARCH_ENGINE_CONFIGURATION add foreign key SEARCH_ENGINE_ID (SEARCH_ENGINE_ID) references SEARCH_ENGINE.ID;

alter table REQUEST ADD COLUMN DOMAIN_ID int(10);
alter table REQUEST add foreign key DOMAIN_ID (DOMAIN_ID) references DOMAIN.ID;

