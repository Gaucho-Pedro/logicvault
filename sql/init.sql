-- Таблицы для хранения пользовательских данных
create table art_user
(
    id           bigint generated always as identity primary key,
    username     varchar(12) unique,
    password     varchar,
    email        varchar unique,
    phone_number varchar
);
create table art_natural_person
(
    id            bigint generated always as identity primary key,
    full_name     varchar,
    citizenship   varchar(50),
    passport_data text/*,
    contractor_id bigint unique references art_contractor (id) on delete cascade,
    customer_id   bigint unique references art_customer (id) on delete cascade,
    check ((contractor_id is not null and customer_id is null) or
           (contractor_id is null and customer_id is not null))*/
);
create table art_legal_person
(
    id                  bigint generated always as identity primary key,
    name                varchar,
    inn                 varchar,
    registration_number varchar(15),
    legal_address       text/*,
    contractor_id       bigint unique references art_contractor (id) on delete cascade,
    customer_id         bigint unique references art_customer (id) on delete cascade,
    check ((contractor_id is not null and customer_id is null) or
           (contractor_id is null and customer_id is not null))*/
);
create table art_contractor
(
    id                bigint generated always as identity primary key,
    user_id           bigint references art_user (id) not null,
    natural_person_id bigint references art_natural_person (id),
    legal_person_id   bigint references art_legal_person (id)
);
create table art_customer
(
    id                bigint generated always as identity primary key,
    user_id           bigint references art_user (id) not null,
    natural_person_id bigint references art_natural_person (id),
    legal_person_id   bigint references art_legal_person (id)
);
---
create table art_portfolio
(
    id          bigint generated always as identity primary key,
    customer_id bigint references art_customer (id)
    -- TODO Добавить параметры
);


--- Справочники
create table art_activity_type
(
    id   bigint generated always as identity primary key,
    name varchar(100)
);
create table art_portfolio_activity
(
    portfolio_id bigint references art_portfolio (id),
    activity_id  bigint references art_activity_type (id)
);
create table art_software
(
    id   bigint generated always as identity primary key,
    name varchar(100)
);
create table art_portfolio_software
(
    portfolio_id bigint references art_portfolio (id),
    software_id  bigint references art_software (id)
)
---
