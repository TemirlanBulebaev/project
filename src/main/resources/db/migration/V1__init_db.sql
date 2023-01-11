create table delivery_unit
(
    delivery_unit_id bigint not null,
    date_of_created  datetime,
    date_of_updated  datetime,
    amount_items     varchar(255),
    delivery_id      bigint,
    item_id          bigint,
    primary key (delivery_unit_id)
) engine=InnoDB;;
create table delivery_unit_seq
(
    next_val bigint
) engine=InnoDB;
insert into delivery_unit_seq values ( 1 );
create table green_coffee
(
    green_coffee_id     bigint       not null,
    date_of_created     datetime,
    date_of_updated     datetime,
    density             integer,
    humidity            integer,
    green_coffee_name   varchar(255) not null,
    water_loss          integer,
    green_coffee_weight bigint,
    primary key (green_coffee_id)
) engine=InnoDB;
create table green_coffee_seq
(
    next_val bigint
) engine=InnoDB;
insert into green_coffee_seq values ( 1 );
create table inventory
(
    inventory_id    bigint not null,
    date_of_created datetime,
    date_of_updated datetime,
    primary key (inventory_id)
) engine=InnoDB;
create table inventory_seq
(
    next_val bigint
) engine=InnoDB;
insert into inventory_seq values ( 1 );
create table inventory_unit
(
    unit_id         bigint not null,
    date_of_created datetime,
    date_of_updated datetime,
    amount_items    varchar(255),
    item_id         bigint,
    inventory_id    bigint,
    primary key (unit_id)
) engine=InnoDB;
create table item
(
    item_id         bigint       not null,
    date_of_created datetime,
    date_of_updated datetime,
    is_active       bit          not null,
    amount          bigint,
    coffee_name     varchar(255) not null,
    description     text,
    name            varchar(255) not null,
    package         varchar(255),
    price           bigint,
    sticker_type    varchar(255),
    weight          integer,
    primary key (item_id)
) engine=InnoDB;
create table item_seq
(
    next_val bigint
) engine=InnoDB;
insert into item_seq values ( 1 );
create table package
(
    package_id bigint not null,
    amount     bigint,
    name       varchar(255),
    primary key (package_id)
) engine=InnoDB;
create table package_seq
(
    next_val bigint
) engine=InnoDB;
insert into package_seq values ( 1 );
create table refresh_token
(
    token_id        bigint       not null,
    date_of_created datetime,
    date_of_updated datetime,
    expiry_dt       datetime     not null,
    refresh_count   bigint,
    token           varchar(255) not null,
    user_device_id  bigint       not null,
    primary key (token_id)
) engine=InnoDB;
create table refresh_token_seq
(
    next_val bigint
) engine=InnoDB;
insert into refresh_token_seq values ( 1 );
create table roasted_coffee
(
    item_id bigint not null,
    name    varchar(255),
    weight  bigint,
    primary key (item_id)
) engine=InnoDB;
create table role
(
    role_id   bigint not null auto_increment,
    role_name varchar(255),
    primary key (role_id)
) engine=InnoDB;
create table sticker
(
    sticker_id bigint not null,
    amount     bigint,
    name       varchar(255),
    primary key (sticker_id)
) engine=InnoDB;
create table sticker_seq
(
    next_val bigint
) engine=InnoDB;
insert into sticker_seq values ( 1 );
create table unit_seq
(
    next_val bigint
) engine=InnoDB;
insert into unit_seq values ( 1 );
create table user
(
    user_id           bigint not null,
    date_of_created   datetime,
    date_of_updated   datetime,
    is_active         bit    not null,
    email             varchar(255),
    is_email_verified bit    not null,
    password          varchar(255),
    username          varchar(255),
    inventory_id      bigint not null,
    primary key (user_id)
) engine=InnoDB;
create table user_authority
(
    user_id bigint not null,
    role_id bigint not null,
    primary key (user_id, role_id)
) engine=InnoDB;
create table user_delivery
(
    delivery_id       bigint not null,
    date_of_created   datetime,
    date_of_updated   datetime,
    address           varchar(255),
    comment           text,
    is_payment        bit,
    user_inventory_id bigint,
    primary key (delivery_id)
) engine=InnoDB;
create table user_delivery_seq
(
    next_val bigint
) engine=InnoDB;
insert into user_delivery_seq values ( 1 );
create table user_device
(
    user_device_id     bigint       not null,
    date_of_created    datetime,
    date_of_updated    datetime,
    device_id          varchar(255) not null,
    device_type        varchar(255),
    is_refresh_active  bit,
    notification_token varchar(255),
    user_id            bigint       not null,
    primary key (user_device_id)
) engine=InnoDB;
create table user_device_seq
(
    next_val bigint
) engine=InnoDB;
insert into user_device_seq values ( 1 );
create table user_seq
(
    next_val bigint
) engine=InnoDB;
insert into user_seq values ( 1 );
create table verification_token
(
    token_id     bigint       not null auto_increment,
    expiry_dt    datetime     not null,
    token        varchar(255) not null,
    token_status varchar(255),
    user_id      bigint       not null,
    primary key (token_id)
) engine=InnoDB;

alter table refresh_token
    add constraint UK_8ogx3ejsbfbf2xsgl4758otrm unique (user_device_id);

alter table refresh_token
    add constraint UK_r4k4edos30bx9neoq81mdvwph unique (token);

alter table role
    add constraint UK_epk9im9l9q67xmwi4hbed25do unique (role_name);

alter table user
    add constraint UK_2its1oh0ku235njjgsxsgc64m unique (inventory_id);

alter table user
    add constraint UK_ob8kqyqqgmefl0aco34akdtpe unique (email);

alter table user
    add constraint UK_sb8bbouer5wak8vyiiy4pf2bx unique (username);

alter table verification_token
    add constraint UK_p678btf3r9yu6u8aevyb4ff0m unique (token);
alter table delivery_unit
    add constraint FKpv6gl8vt4xkoiq6uqy5kegvk foreign key (item_id) references item (item_id);
alter table delivery_unit
    add constraint FKklkhkywf3insgbk61gmuaxpov foreign key (delivery_id) references user_delivery (delivery_id);
alter table inventory_unit
    add constraint FKg63n1jatlwrrdjh69sqw4qimd foreign key (item_id) references item (item_id);
alter table inventory_unit
    add constraint FKaaxbal8bbo4bq638oj8kb83ir foreign key (inventory_id) references inventory (inventory_id);
alter table refresh_token
    add constraint FKr92opronarwe7pn1c41621grv foreign key (user_device_id) references user_device (user_device_id);
alter table user
    add constraint FKalo4ugar5xpb802kfh7lqlcj4 foreign key (inventory_id) references inventory (inventory_id);
alter table user_authority
    add constraint FKash3fy9hdayq3o73fir11n3th foreign key (role_id) references role (role_id);
alter table user_authority
    add constraint FKpqlsjpkybgos9w2svcri7j8xy foreign key (user_id) references user (user_id);
alter table user_device
    add constraint FKd2lb0k09c4nnfpvku8r61g92n foreign key (user_id) references user (user_id);
alter table verification_token
    add constraint FKrdn0mss276m9jdobfhhn2qogw foreign key (user_id) references user (user_id);