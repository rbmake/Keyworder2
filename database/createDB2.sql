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
KEYPHRASE_ID int(5),
RANKING int(5),
TIMESTAMP timestamp,
PRIMARY KEY (ID));

CREATE TABLE RANKING_HISTORY
(ID int(10) auto_increment,
SEARCH_ENGINE_CONFIGURATION_ID int(5),
TIMESTAMP timestamp,
PRIMARY KEY (ID));

CREATE TABLE SEARCH_ENGINE_CONFIGURATION
(ID int(5) auto_increment,
SEARCH_ENGINE_ID int(5),
NUM_RESULTS int(5),
LANGUAGE varchar(50),
CLIENT_BROWSER varchar(50),
PRIMARY KEY (ID));