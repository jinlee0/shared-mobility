create table bike
(
    id         varchar(40) not null
        primary key,
    station_id varchar(40) not null
);

create index bike_station_id_index
    on bike (station_id);

create table customer
(
    id         varchar(40)  not null
        primary key,
    email      varchar(40)  not null,
    password   varchar(255) not null,
    created_at timestamp    not null,
    constraint customer_unique_email
        unique (email)
);

create index customer__email_index
    on customer (email);

create table rent
(
    id               varchar(40) not null
        primary key,
    ticket_id        varchar(40) not null,
    bike_id          varchar(40) not null,
    start_station_id varchar(40) not null,
    end_station_id   varchar(40) null,
    rent_at          timestamp   not null,
    returned_at      timestamp   null
);

create index rent_bike_id_index
    on rent (bike_id);

create index rent_cust_id_returned_at_index
    on rent (returned_at);

create index rent_end_station_id_index
    on rent (end_station_id);

create index rent_start_station_id_index
    on rent (start_station_id);

create index rent_ticket_id_fk
    on rent (ticket_id);

create table rent_req
(
    id        varchar(40) not null
        primary key,
    bike_id   varchar(40) not null,
    ticket_id varchar(40) not null
)
    comment '대여 실행 시 삭제된다.';

create index rent_req_ticket_id_index
    on rent_req (ticket_id);

create table station
(
    id       varchar(40)  not null
        primary key,
    name     varchar(255) not null,
    lat      double       not null,
    lon      double       not null,
    address1 varchar(255) not null,
    address2 varchar(255) null,
    n_hold   int          not null comment '대여소에 있는 거치대 개수'
);

create table station_fav
(
    id          varchar(40) not null
        primary key,
    customer_id varchar(40) not null,
    station_id  varchar(40) not null,
    constraint station_fav_unique__cust_id_station_id
        unique (customer_id, station_id)
);

create index station_fav_customer_id_index
    on station_fav (customer_id);

create index station_fav_station_id_index
    on station_fav (station_id);

create table ticket
(
    id              varchar(40) not null
        primary key,
    customer_id     varchar(40) not null,
    type            varchar(40) not null comment '이용권 타입 enum을 varchar로 저장한다.',
    start_at        timestamp   null comment '이용권을 처음 사용한 시점',
    usable_duration int         null comment '이용권 정책이 변경될 수 있으므로 이용권 구매 당시의 정책(기간)을 보장한다. 단위는 일이다.',
    last_used_at    timestamp   null comment '이용권을 마지막으로 사용한 시점.',
    created_at      timestamp   not null comment '이용권을 생성한 시점'
);

create index ticket_customer_id_index
    on ticket (customer_id);

create index ticket_usable_duration_index
    on ticket (usable_duration)
    comment '유저가 사용가능한 이용권을 조회하는 경우 (it=null or now() < it)';

