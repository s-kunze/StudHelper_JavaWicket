
    alter table DEGREECOURSE 
        drop 
        foreign key FK23B2EDE7A79531D5

    alter table DEPARTMENT 
        drop 
        foreign key FK4F782F52BE526691

    alter table LECTURE 
        drop 
        foreign key FK2DEF995EB1A5FEF8

    alter table LECTURE_USER 
        drop 
        foreign key FKEAB22E0CE88F76AF

    alter table LECTURE_USER 
        drop 
        foreign key FKEAB22E0C91320A72

    alter table MODUL 
        drop 
        foreign key FK46204F9107DA7AA

    drop table if exists DEGREECOURSE

    drop table if exists DEPARTMENT

    drop table if exists LECTURE

    drop table if exists LECTURE_USER

    drop table if exists MODUL

    drop table if exists UNIVERSITY

    drop table if exists USER

    create table DEGREECOURSE (
        DEGREECOURSE_ID bigint not null auto_increment,
        DEGREECOURSE_CREDITPOINTS integer,
        DEGREECOURSE_NAME varchar(255),
        department_DEPARTMENT_ID bigint,
        primary key (DEGREECOURSE_ID)
    )

    create table DEPARTMENT (
        DEPARTMENT_ID bigint not null auto_increment,
        DEPARTMENT_NAME varchar(255),
        university_UNIVERSITY_ID bigint,
        primary key (DEPARTMENT_ID)
    )

    create table LECTURE (
        LECTURE_ID bigint not null auto_increment,
        LECTURE_CREDITPOINTS integer,
        LECTURE_NAME varchar(255),
        modul_MODUL_ID bigint,
        primary key (LECTURE_ID)
    )

    create table LECTURE_USER (
        MARK float,
        USER_ID bigint,
        LECTURE_ID bigint,
        primary key (LECTURE_ID, USER_ID)
    )

    create table MODUL (
        MODUL_ID bigint not null auto_increment,
        MODUL_CREDITPOINTS integer,
        MODUL_NAME varchar(255),
        degreeCourse_DEGREECOURSE_ID bigint,
        primary key (MODUL_ID)
    )

    create table UNIVERSITY (
        UNIVERSITY_ID bigint not null auto_increment,
        UNIVERSITY_NAME varchar(255),
        primary key (UNIVERSITY_ID)
    )

    create table USER (
        USER_ID bigint not null auto_increment,
        USER_CREDITPOINTS integer,
        USER_FIRSTNAME varchar(255),
        USER_LASTNAME varchar(255),
        USER_PASSWORD varchar(255),
        USER_USERNAME varchar(255),
        primary key (USER_ID)
    )

    alter table DEGREECOURSE 
        add index FK23B2EDE7A79531D5 (department_DEPARTMENT_ID), 
        add constraint FK23B2EDE7A79531D5 
        foreign key (department_DEPARTMENT_ID) 
        references DEPARTMENT (DEPARTMENT_ID)

    alter table DEPARTMENT 
        add index FK4F782F52BE526691 (university_UNIVERSITY_ID), 
        add constraint FK4F782F52BE526691 
        foreign key (university_UNIVERSITY_ID) 
        references UNIVERSITY (UNIVERSITY_ID)

    alter table LECTURE 
        add index FK2DEF995EB1A5FEF8 (modul_MODUL_ID), 
        add constraint FK2DEF995EB1A5FEF8 
        foreign key (modul_MODUL_ID) 
        references MODUL (MODUL_ID)

    alter table LECTURE_USER 
        add index FKEAB22E0CE88F76AF (USER_ID), 
        add constraint FKEAB22E0CE88F76AF 
        foreign key (USER_ID) 
        references USER (USER_ID)

    alter table LECTURE_USER 
        add index FKEAB22E0C91320A72 (LECTURE_ID), 
        add constraint FKEAB22E0C91320A72 
        foreign key (LECTURE_ID) 
        references LECTURE (LECTURE_ID)

    alter table MODUL 
        add index FK46204F9107DA7AA (degreeCourse_DEGREECOURSE_ID), 
        add constraint FK46204F9107DA7AA 
        foreign key (degreeCourse_DEGREECOURSE_ID) 
        references DEGREECOURSE (DEGREECOURSE_ID)
