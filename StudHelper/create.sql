
    drop table if exists PERSON

    create table PERSON (
        PERSON_ID integer not null auto_increment,
        PERSON_NAME varchar(255),
        primary key (PERSON_ID)
    )
