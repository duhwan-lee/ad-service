create table IF NOT EXISTS ad_participation_history
(
    participation_id       int auto_increment
    primary key,
    ad_id                  int                                not null comment '광고 id',
    user_id                int                                not null,
    ad_title               varchar(100)                       not null,
    participation_datetime datetime                           not null comment '광고 참여 시각',
    reward_amount          int                                not null comment '광고 참여 적립액수',
    created_at             datetime default CURRENT_TIMESTAMP not null
    );

create index ad_participation_history_user_id_participation_datetime_index
    on ad_participation_history (user_id, participation_datetime);

create table IF NOT EXISTS ads
(
    ad_id                      int auto_increment
    primary key,
    ad_title                   varchar(100)                       not null comment '광고명',
    ad_reward                  int                                not null comment '참여 시 적립금',
    ad_max_participation       int                                not null comment '광고 참여 최대 횟수',
    ad_current_participation   int      default 0                 null,
    ad_description             text                               null comment '광고 문구',
    ad_image_url               varchar(200)                       not null,
    ad_start_datetime          datetime                           not null comment '광고 노출 시작날짜',
    ad_end_datetime            datetime                           not null comment '광고 노출 종료날짜',
    ad_participation_condition text                               null,
    created_at                 datetime default CURRENT_TIMESTAMP not null,
    updated_at                 datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    constraint ads_ad_title_uindex
    unique (ad_title)
    );

create index ads_ad_start_datetime_ad_end_datetime_index
    on ads (ad_start_datetime, ad_end_datetime);

