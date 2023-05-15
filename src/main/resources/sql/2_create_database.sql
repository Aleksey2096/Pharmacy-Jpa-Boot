CREATE DATABASE pharmacy_db DEFAULT CHARACTER SET utf8;

CREATE USER 'pharmacy_application'@'localhost' IDENTIFIED BY 'admin';
CREATE USER 'pharmacy_application'@'%' IDENTIFIED BY 'admin';

GRANT SELECT, INSERT, UPDATE, DELETE
    ON pharmacy_db.*
    TO pharmacy_application@localhost;

GRANT SELECT, INSERT, UPDATE, DELETE
    ON pharmacy_db.*
    TO pharmacy_application@'%';

USE pharmacy_db;
