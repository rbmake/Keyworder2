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

-- Holds the ranking history for a domain
CREATE TABLE RANKING_HISTORY
(ID int(5) auto_increment,
DOMAIN_ID int(5),
SEARCH_ENGINE_ID int(5),
SEARCH_ENGINE_CONFIGURATION_ID int(5),
KEYPHRASE_ID int(5),
RANKING int(5),
TIMESTAMP timestamp,
PRIMARY KEY (ID));


-- Holds the URL results for each search resulting in a ranking
CREATE TABLE URL_RANKING
(ID int(10) auto_increment,
URL varchar(300),
RANKING int(5),
RANKING_HISTORY_ID int(5),
PRIMARY KEY (ID));

CREATE TABLE SEARCH_ENGINE_CONFIGURATION
(ID int(5) auto_increment,
COMMON_NAME varchar(50)
NAME varchar(50),
VALUE varchar(50),
PRIMARY KEY (ID));