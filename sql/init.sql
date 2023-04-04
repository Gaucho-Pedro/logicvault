-- Таблицы для хранения пользовательских данных

create table if not exists art_user
(
    id           bigint generated always as identity primary key,
    username     varchar(12) unique,
    password     varchar,
    email        varchar unique,
    phone_number varchar
);
create table if not exists art_natural_person
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
create table if not exists art_legal_person
(
    id                  bigint generated always as identity primary key,
    name                varchar,
    inn                 numeric(12),
    registration_number numeric(15),
    legal_address       text/*,
    contractor_id       bigint unique references art_contractor (id) on delete cascade,
    customer_id         bigint unique references art_customer (id) on delete cascade,
    check ((contractor_id is not null and customer_id is null) or
           (contractor_id is null and customer_id is not null))*/
);
create table if not exists art_contractor
(
    id                bigint generated always as identity primary key,
    user_id           bigint references art_user (id) on delete cascade not null,
    natural_person_id bigint references art_natural_person (id),
    legal_person_id   bigint references art_legal_person (id)
);
create table if not exists art_customer
(
    id                bigint generated always as identity primary key,
    user_id           bigint references art_user (id) on delete cascade not null,
    natural_person_id bigint references art_natural_person (id),
    legal_person_id   bigint references art_legal_person (id)
);
--- Справочники
create table if not exists art_activity_type
(
    id   bigint generated always as identity primary key,
    name varchar(100)
);
/*create table if not exists art_portfolio_activity
(
    portfolio_id bigint references art_portfolio (id),
    activity_id  bigint references art_activity_type (id)
);*/
create table if not exists art_software
(
    id   bigint generated always as identity primary key,
    name varchar(100)
);
---
create table if not exists art_portfolio
(
    id               bigint generated always as identity primary key,
    customer_id      bigint references art_customer (id),
    activity_type_id bigint references art_activity_type (id),
    description      text,
    score1           integer default 0,
    score2           integer default 0,
    score3           integer default 0,
    showreel_tags    text
);

create table if not exists art_order
(
    id               bigint generated always as identity primary key,
    contractor_id    bigint references art_contractor (id),
    activity_type_id bigint references art_activity_type (id),
    description      text,
    target_date      date
);
---
create table if not exists art_portfolio_software
(
    portfolio_id bigint references art_portfolio (id),
    software_id  bigint references art_software (id)
);
