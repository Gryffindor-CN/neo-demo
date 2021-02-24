drop table if exists person;

create table person
(
    id bigint(20) not null primary key comment '主键ID',
    first_name varchar(30),
    last_name varchar(20)
);
