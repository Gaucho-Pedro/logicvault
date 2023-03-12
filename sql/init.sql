create table art_user_type
(
    id   smallint generated always as identity primary key,
    name varchar(50)
);
insert into art_user_type(name)
values ('Исполнитель');
insert into art_user_type(name)
values ('Заказчик');
create table art_person_type
(
    id   smallint generated always as identity primary key,
    name varchar(50)
);
insert into art_person_type(name)
values ('Физическое лицо');
insert into art_person_type(name)
values ('Юридическое лицо');

create table art_user
(
    id           bigint generated always as identity primary key,
    username     varchar(12) unique,
    password     varchar,
    email        varchar unique,
    phone_number varchar,
    user_type    smallint references art_user_type (id),
    person_type  smallint references art_person_type (id)
);

-- create table Customer
-- (
--     id           bigint generated always as identity primary key,
--     username     varchar(12) unique,
--     password     varchar,
--     email        varchar unique,
--     phone_number varchar,
--     is_individual boolean,
--     is_legal_entity boolean,
--     individual_name boolean
-- )
-- - is_individual (boolean)
-- - is_legal_entity (boolean)
-- - individual_name (if is_individual is true)
-- - individual_surname (if is_individual is true)
-- - legal_entity_name (if is_legal_entity is true)
-- - legal_entity_registration_number (if is_legal_entity is true)

create table art_user
(
    id           bigint generated always as identity primary key,
    username     varchar(12) unique,
    password     varchar,
    email        varchar unique,
    phone_number varchar
);
create table art_contractor
(
    id      bigint generated always as identity primary key,
    user_id bigint references art_user (id) not null
);
create table art_customer
(
    id      bigint generated always as identity primary key,
    user_id bigint references art_user (id)
);
create table art_natural_person
(
    id            bigint generated always as identity primary key,
    contractor_id bigint references art_contractor (id) on delete cascade,
    customer_id   bigint references art_customer (id) on delete cascade,
    full_name     varchar,
    citizenship   varchar(50),
    passport_data text
);
create table art_legal_person
(
    id                  bigint generated always as identity primary key,
    contractor_id       bigint references art_contractor (id) on delete cascade,
    customer_id         bigint references art_customer (id) on delete cascade,
    name                varchar,
    inn                 varchar,
    registration_number varchar(15),
    legal_address       text
)