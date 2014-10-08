# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table t_book (
  book_id                   bigint not null,
  book_name                 varchar(255),
  rank                      integer,
  categcory_id              bigint,
  comment                   varchar(255),
  amazon_uri                varchar(255),
  recorded_on               timestamp not null,
  updated_on                timestamp not null,
  constraint pk_t_book primary key (book_id))
;

create table t_category (
  category_id               bigint not null,
  subject                   varchar(255),
  sort_order                integer,
  constraint pk_t_category primary key (category_id))
;

create table account (
  email                     varchar(255) not null,
  name                      varchar(255),
  password                  varchar(255),
  constraint pk_account primary key (email))
;

create sequence book_id_seq;

create sequence category_id_seq;

create sequence account_seq;

alter table t_book add constraint fk_t_book_category_1 foreign key (categcory_id) references t_category (category_id);
create index ix_t_book_category_1 on t_book (categcory_id);



# --- !Downs

drop table if exists t_book cascade;

drop table if exists t_category cascade;

drop table if exists account cascade;

drop sequence if exists book_id_seq;

drop sequence if exists category_id_seq;

drop sequence if exists account_seq;

