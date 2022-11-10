/*Script to create database*/

CREATE DATABASE trippy_db;

/* Script to create user_table. user_name and user_city have an UNIQUE constraint so that no duplicate email adresses and user names can be entered.*/

CREATE TABLE user_table (
user_id BIGSERIAL PRIMARY KEY NOT NULL,
user_name CHARACTER VARYING(50) NOT NULL UNIQUE,
user_city CHARACTER VARYING(50) NOT NULL,
user_email CHARACTER VARYING(70) NOT NULL UNIQUE,
date_joined DATE NOT NULL);

--
/* Script to populate user_table with an initial dataset */

INSERT INTO user_table (user_name, user_city, user_email, date_joined)
VALUES
('charlie02', 'Plovdiv','charlie@gmail.com','2022-08-10'),
('hello12', 'Sofia','hello@gmail.com','2022-08-11'),
('world420', 'Varna','world@yahoo.com','2022-08-11'),
('dimitarCh', 'Sofia','dimitar@gmail.com','2022-08-12'),
('user69', 'Skopje','user69@yahoo.com','2022-08-1');

--

/* Query to create venue_table */

CREATE TABLE venue_table (
venue_id BIGSERIAL PRIMARY KEY NOT NULL,
venue_name CHARACTER VARYING(70) NOT NULL UNIQUE,
venue_type CHARACTER VARYING(70) NOT NULL,
venue_city CHARACTER VARYING(50) NOT NULL,
venue_address CHARACTER VARYING(255) NOT NULL,
venue_email CHARACTER VARYING(70) NOT NULL UNIQUE,
venue_phone CHARACTER VARYING(255)NOT NULL UNIQUE);

--

/* Script to populate venue_table with an initial dataset */

INSERT INTO venue_table (venue_name, venue_type, venue_city, venue_email, venue_address, venue_phone)
VALUES
('Restaurant1','Restaurant','Plovdiv','hello.restaurant@gmail.com','Some Address 12','088121201'),
('Bar1','Bar','Varna','hello.bar@gmail.com','Some Street 14','083523212'),
('Hotel1','Hotel','Sofia','hello.hotel@gmail.com','Hello Boulevard 420','0881252353'),
('Restaurant2','Restaurant','Burgas','finedining@gmail.com','Restaurant street 21','0825621215'),
('Restaurant3','Restaurant','Sofia','dontcontactus@gmail.com','Bad review street 21','091551217'),
('Bar2','Bar','Skopje','cheers@gmail.com','Piss drunk stret 21','084206915');

--
/* Script to create review_table */

CREATE TABLE review_table (
review_id BIGSERIAL PRIMARY KEY NOT NULL,
user_id INTEGER NOT NULL,
user_name CHARACTER VARYING(50) NOT NULL,
venue_id INTEGER NOT NULL,
venue_name CHARACTER VARYING(70) NOT NULL,
review_text CHARACTER VARYING(255),
rating INTEGER NOT NULL CHECK (rating < 6 AND rating > 0),
date_created DATE NOT NULL,
FOREIGN KEY (venue_id) REFERENCES venue_table(venue_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
FOREIGN KEY (user_id) REFERENCES user_table(user_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);
--

/* Script to populate venue_table with an initial dataset */

INSERT INTO review_table (user_id, user_name, venue_id, venue_name, review_text, rating, date_created)
VALUES
(1,'charlie02',2,'Bar World','Not bad, would visit again',4,'2022-8-15'),
(1,'charlie02',1,'Hello Restaurant','Great! Defintiely recommend.',5,'2022-8-17'),
(2,'hello12',2,'Bar World','Terrible service.',2,'2022-08-16'),
(2,'hello12',5,'Dont eat here diner','Abosolute dumpster fire.',1,'2022-08-20'),
(4,'dimitarCh',4,'Fine Establishment','Exquisite dining experience!',5,'2022-08-20');

--

