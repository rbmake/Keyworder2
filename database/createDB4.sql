CREATE TABLE REQUEST
(ID int(10) auto_increment,
TIMESTAMP timestamp,
PRIMARY KEY (ID));

ALTER TABLE DOMAIN_RANKING ADD COLUMN REQUEST_ID int(10);

ALTER TABLE RANKING ADD COLUMN REQUEST_ID int(10);

ALTER TABLE RANKING_HISTORY ADD COLUMN REQUEST_ID int(10);

alter table domain_ranking add foreign key keyphrase_id (keyphrase_id) references keyphrase.id;
alter table domain_ranking add foreign key ranking_history_id (ranking_history_id) references ranking_history.id;
alter table domain_ranking add foreign key request_id (request_id) references request.id;
alter table domain_ranking add foreign key domain_id (domain_id) references domain.id;
alter table ranking add foreign key keyphrase_id (keyphrase_id) references keyphrase.id;
alter table ranking add foreign key ranking_history_id (ranking_history_id) references ranking_history.id;
alter table ranking add foreign key request_id (request_id) references request.id;
alter table ranking_history add foreign key request_id (request_id) references request.id;
alter table user_domain add foreign key domain_id (domain_id) references domain.id;
alter table user_domain add foreign key user_id (user_id) references user.id;
alter table search_engine_configuration add foreign key search_engine_id (search_engine_id) references search_engine.id;


