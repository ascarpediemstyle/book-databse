# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table t_book (
  book_id                   SERIAL not null,
  book_name                 varchar(255),
  rank                      integer,
  introduced                timestamp,
  discontinued              timestamp,
  constraint pk_t_book primary key (book_id))
;

create table company (
  id                        bigint not null,
  name                      varchar(255),
  constraint pk_company primary key (id))
;

create table computer (
  id                        bigint not null,
  name                      varchar(255),
  introduced                timestamp,
  discontinued              timestamp,
  company_id                bigint,
  constraint pk_computer primary key (id))
;

create table account (
  email                     varchar(255) not null,
  name                      varchar(255),
  password                  varchar(255),
  constraint pk_account primary key (email))
;

create sequence t_book_seq;

create sequence company_seq;

create sequence computer_seq;

create sequence account_seq;

alter table computer add constraint fk_computer_company_1 foreign key (company_id) references company (id);
create index ix_computer_company_1 on computer (company_id);



# --- !Downs

drop table if exists t_book cascade;

drop table if exists company cascade;

drop table if exists computer cascade;

drop table if exists account cascade;

drop sequence if exists t_book_seq;

drop sequence if exists company_seq;

drop sequence if exists computer_seq;

drop sequence if exists account_seq;

