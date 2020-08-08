create sequence hibernate_sequence start 1 increment 1;

create table history_entry (
                               id int8 not null,
                               date date,
                               exchange_rate float8,
                               original_amount float8,
                               total_amount float8,
                               original_valute_id varchar(255),
                               target_valute_id varchar(255),
                               primary key (id)
);

create table usr (
                     id int8 not null,
                     active boolean not null,
                     email varchar(255),
                     password varchar(255),
                     username varchar(255),
                     primary key (id)
);

create table usr_role (
                          usr_id int8 not null,
                          roles varchar(255)
);

create table value (
                       id int8 not null,
                       date date,
                       value float8,
                       valute_id varchar(255),
                       primary key (id)
);

create table valute (
                        id varchar(255) not null,
                        char_code varchar(255),
                        name varchar(255),
                        nominal int4,
                        num_code int4,
                        primary key (id)
);

alter table if exists history_entry
    add constraint history_original_valute_id
        foreign key (original_valute_id)
            references valute;

alter table if exists history_entry
    add constraint history_target_valute_id
        foreign key (target_valute_id)
            references valute;

alter table if exists usr_role
    add constraint usr_role_user_id
        foreign key (usr_id)
            references usr;

alter table if exists value
    add constraint value_valute_id
        foreign key (valute_id)
            references valute;