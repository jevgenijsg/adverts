drop table if exists ad;
drop table if exists hibernate_sequence;
create table ad (
    id bigint not null,
    category varchar(255),
    expiry_date date,
    is_active bit,
    price decimal(19,2),
    text varchar(255),
    primary key (id));
create table hibernate_sequence (next_val bigint);
insert into hibernate_sequence values ( 1 );