drop table if exists `users_roles`;
drop table if exists `roles`;
drop table if exists `users`;


create table roles
(
    id   bigint auto_increment primary key,
    name varchar(255) null
);

create table users
(
    id       bigint auto_increment primary key,
    password varchar(255) null,
    surname  varchar(255) null,
    username varchar(255) null
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

-- user:user
insert into user_crud.users(username, surname, password)
values ('user', 'userov', '$2a$12$KHvuNMEIduFHp/XP7WwR4eMDZHiwuD6CQiFCu8HtNZCwUDe5LJysi');
-- admin:admin
insert into user_crud.users(username, surname, password)
values ('admin', 'adminov', '$2a$12$lku3N9cSWWN5NClRl1ok.eu.0.hWr100i2uTZFGSA0wA2SpcskIRK');

insert into users_roles(user_id, roles_id)
values (1, 1);
insert into users_roles(user_id, roles_id)
values (2, 1);
insert into users_roles(user_id, roles_id)
values (2, 2);
