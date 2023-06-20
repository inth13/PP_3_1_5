drop table if exists `users_roles`;
drop table if exists `roles`;
drop table if exists `users`;


create table roles
(
    id   bigint auto_increment primary key,
    name varchar(255) not null
);

create table users
(
    id         bigint auto_increment primary key,
    age        tinyint      null,
    email      varchar(255) not null unique ,
    first_name varchar(255) null,
    last_name  varchar(255) null,
    password   varchar(255) null
);

create table users_roles
(
    user_id  bigint not null,
    roles_id bigint not null,
    primary key (user_id, roles_id),
    foreign key (user_id) references users (id),
    foreign key (roles_id) references roles (id)
);



insert into user_crud.roles (name)
values ('ROLE_USER');
insert into user_crud.roles (name)
values ('ROLE_ADMIN');

-- USER PASS!!! --
-- user@gmail.com:user
insert into user_crud.users(first_name, last_name, email, age, password)
values ('user', 'userov', 'user@gmail.com', 30, '$2a$12$KHvuNMEIduFHp/XP7WwR4eMDZHiwuD6CQiFCu8HtNZCwUDe5LJysi');
-- admin@gmail.com:admin
insert into user_crud.users(first_name, last_name, email, age, password)
values ('admin', 'adminov', 'admin@gmail.com', 35, '$2a$12$lku3N9cSWWN5NClRl1ok.eu.0.hWr100i2uTZFGSA0wA2SpcskIRK');
-- igor@gmail.com:123
insert into user_crud.users(first_name, last_name, email, age, password)
values ('igor', 'mikhailov', 'igor@gmail.com', 38, '$2a$12$c2CrjtoAAiMnSujIggSHreFqHlBFoxUDkAj4J7nQ/W4Jt8Mj.H9ay');


insert into users_roles(user_id, roles_id)
values (1, 1);
insert into users_roles(user_id, roles_id)
values (2, 1);
insert into users_roles(user_id, roles_id)
values (2, 2);
insert into users_roles(user_id, roles_id)
values (3, 1);

