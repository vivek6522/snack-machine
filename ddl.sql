-- PostgreSQL
drop table if exists snack_machine;

create table snack_machine (
  id varchar(64) primary key,
  one_cent_count int default 0,
  ten_cent_count int default 0,
  quarter_count int default 0,
  dollar_count int default 0,
  five_dollar_count int default 0,
  twenty_dollar_count int default 0
);
