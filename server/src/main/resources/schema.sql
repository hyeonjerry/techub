create table publisher
(
    id          bigint auto_increment primary key,
    name        varchar(255) not null,
    logo        blob         null,
    rss_url     varchar(255) not null,
    website_url varchar(255) not null,
    created_at  datetime(6)  null,
    updated_at  datetime(6)  null
);

create table feed
(
    id            bigint auto_increment primary key,
    publisher_id  bigint       not null,
    title         varchar(255) not null,
    url           varchar(255) not null,
    description   varchar(255) null,
    thumbnail_url varchar(255) null,
    created_at    datetime(6)  null,
    updated_at    datetime(6)  null
);
