-- Таблицы для хранения пользовательских данных
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
    contractor_id bigint unique references art_contractor (id) on delete cascade,
    customer_id   bigint unique references art_customer (id) on delete cascade,
    full_name     varchar,
    citizenship   varchar(50),
    passport_data text,
    check ((contractor_id is not null and customer_id is null) or
           (contractor_id is null and customer_id is not null))
);
create table art_legal_person
(
    id                  bigint generated always as identity primary key,
    contractor_id       bigint unique references art_contractor (id) on delete cascade,
    customer_id         bigint unique references art_customer (id) on delete cascade,
    name                varchar,
    inn                 varchar,
    registration_number varchar(15),
    legal_address       text,
    check ((contractor_id is not null and customer_id is null) or
           (contractor_id is null and customer_id is not null))
)