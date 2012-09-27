StudHelper_JavaWicket
=====================

Notenübersicht in Java und Wicket

Install
=======

We need a MySQL-Database:
  + Database with name studhelper
  + a user for this database, named 'studhelper' and passwort 'studhelper'

With the following commands:

shell> mysql --user=root -p
mysql> CREATE DATABASE studhelper;
mysql> GRANT SELECT,INSERT,UPDATE,DELETE,CREATE,DROP
    ->     ON studhelper.*
    ->     TO 'studhelper'@'localhost'
    ->     IDENTIFIED BY 'studhelper';

We also need maven2 on the system.

Then just run: 
mvn jetty:run
