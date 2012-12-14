
    alter table DEGREECOURSE 
        drop 
        foreign key FK23B2EDE7FF225402

    alter table DEPARTMENT 
        drop 
        foreign key FK4F782F52D8E83A82

    alter table LECTURE_USER 
        drop 
        foreign key FKEAB22E0CE88F76AF

    alter table LECTURE_USER 
        drop 
        foreign key FKEAB22E0C91320A72

    alter table MODUL 
        drop 
        foreign key FK46204F9B7CCA62

    alter table MODUL_LECTURE 
        drop 
        foreign key FK852D3C18F91C1E92

    alter table MODUL_LECTURE 
        drop 
        foreign key FK852D3C1891320A72

    alter table PART 
        drop 
        foreign key FK255BF34F2AFA02

    alter table USER 
        drop 
        foreign key FK27E3CB4F2AFA02

    drop table if exists ADMIN

    drop table if exists DEGREECOURSE

    drop table if exists DEPARTMENT

    drop table if exists LECTURE

    drop table if exists LECTURE_USER

    drop table if exists MODUL

    drop table if exists MODUL_LECTURE

    drop table if exists PART

    drop table if exists UNIVERSITY

    drop table if exists USER

    create table ADMIN (
        ID bigint not null auto_increment,
        PASSWORD varchar(255),
        USERNAME varchar(255),
        primary key (ID),
        unique (USERNAME)
    )

    create table DEGREECOURSE (
        ID bigint not null auto_increment,
        CREDITPOINTS integer,
        NAME varchar(255),
        department_ID bigint,
        primary key (ID)
    )

    create table DEPARTMENT (
        ID bigint not null auto_increment,
        NAME varchar(255),
        university_ID bigint,
        primary key (ID)
    )

    create table LECTURE (
        ID bigint not null auto_increment,
        CREDITPOINTS integer,
        NAME varchar(255),
        primary key (ID)
    )

    create table LECTURE_USER (
        MARK float,
        USER_ID bigint,
        LECTURE_ID bigint,
        primary key (LECTURE_ID, USER_ID)
    )

    create table MODUL (
        ID bigint not null auto_increment,
        CREDITPOINTS integer,
        NAME varchar(255),
        part_ID bigint,
        primary key (ID)
    )

    create table MODUL_LECTURE (
        MODUL_ID bigint not null,
        LECTURE_ID bigint not null
    )

    create table PART (
        ID bigint not null auto_increment,
        CREDITPOINTS integer,
        NAME varchar(255),
        degreeCourse_ID bigint,
        primary key (ID)
    )

    create table UNIVERSITY (
        ID bigint not null auto_increment,
        NAME varchar(255),
        primary key (ID)
    )

    create table USER (
        ID bigint not null auto_increment,
        CREDITPOINTS integer,
        FIRSTNAME varchar(255),
        LASTNAME varchar(255),
        PASSWORD varchar(255),
        USERNAME varchar(255),
        degreeCourse_ID bigint,
        primary key (ID),
        unique (USERNAME)
    )

    alter table DEGREECOURSE 
        add index FK23B2EDE7FF225402 (department_ID), 
        add constraint FK23B2EDE7FF225402 
        foreign key (department_ID) 
        references DEPARTMENT (ID)

    alter table DEPARTMENT 
        add index FK4F782F52D8E83A82 (university_ID), 
        add constraint FK4F782F52D8E83A82 
        foreign key (university_ID) 
        references UNIVERSITY (ID)

    alter table LECTURE_USER 
        add index FKEAB22E0CE88F76AF (USER_ID), 
        add constraint FKEAB22E0CE88F76AF 
        foreign key (USER_ID) 
        references USER (ID)

    alter table LECTURE_USER 
        add index FKEAB22E0C91320A72 (LECTURE_ID), 
        add constraint FKEAB22E0C91320A72 
        foreign key (LECTURE_ID) 
        references LECTURE (ID)

    alter table MODUL 
        add index FK46204F9B7CCA62 (part_ID), 
        add constraint FK46204F9B7CCA62 
        foreign key (part_ID) 
        references PART (ID)

    alter table MODUL_LECTURE 
        add index FK852D3C18F91C1E92 (MODUL_ID), 
        add constraint FK852D3C18F91C1E92 
        foreign key (MODUL_ID) 
        references MODUL (ID)

    alter table MODUL_LECTURE 
        add index FK852D3C1891320A72 (LECTURE_ID), 
        add constraint FK852D3C1891320A72 
        foreign key (LECTURE_ID) 
        references LECTURE (ID)

    alter table PART 
        add index FK255BF34F2AFA02 (degreeCourse_ID), 
        add constraint FK255BF34F2AFA02 
        foreign key (degreeCourse_ID) 
        references DEGREECOURSE (ID)

    alter table USER 
        add index FK27E3CB4F2AFA02 (degreeCourse_ID), 
        add constraint FK27E3CB4F2AFA02 
        foreign key (degreeCourse_ID) 
        references DEGREECOURSE (ID)
