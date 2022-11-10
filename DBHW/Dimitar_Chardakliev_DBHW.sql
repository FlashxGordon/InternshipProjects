CREATE TABLE roles (
  role_id INT PRIMARY KEY,
  role_name VARCHAR(20) NOT NULL
);

CREATE TABLE users (
user_id INT PRIMARY KEY,
user_name VARCHAR(30) NOT NULL,
user_role_id INT NOT NULL,
FOREIGN KEY (user_role_id) REFERENCES roles (role_id)
);

------------------------------------------------------------

ALTER TABLE users ADD COLUMN last_modified_date;
Alter TABLE users ADD COLUMN IsActive int DEFAULT 1;

------------------------------------------------------------

INSERT INTO roles VALUES (1, 'Admin');
INSERT INTO roles VALUES (2, 'User');

INSERT INTO users VALUES (1,'Sam Smith',1,'2010-02-01',1);
INSERT INTO users VALUES (2,'John Willy',2,'2019-12-12',0);
INSERT INTO users VALUES (3,'Sara Lyn',2,'2022-01-01',0);
INSERT INTO users VALUES (4,'Dora Max',2,'2012-10-02',1);
INSERT INTO users VALUES (5,'Terry Cruise',1,'1999-02-09',1);

------------------------------------------------------------

UPDATE users set user_name = CONCAT(user_name, '_inactive') WHERE isActive = 0;

------------------------------------------------------------

DELETE FROM users WHERE EXTRACT(YEAR FROM last_modified_date) < 2000;
------------------------------------------------------------

SELECT role_name, COUNT(U.user_id) FROM users U INNER JOIN roles R ON R.role_id=U.user_role_id
WHERE isActive = 1
GROUP BY role_name;

------------------------------------------------------------

SELECT role_name, COUNT(U.user_id) FROM users U INNER JOIN roles R on R.role_id=U.user_role_id
WHERE isActive = 1
GROUP BY role_name
HAVING COUNT(U.user_id) > 1;

------------------------------------------------------------

SELECT user_name, role_name FROM users U INNER JOIN roles R on R.role_id=U.user_role_id 
WHERE isActive = 1;

------------------------------------------------------------

CREATE VIEW active_user_roles AS
SELECT user_name, role_name FROM users U INNER JOIN roles R on R.role_id=U.user_role_id 
WHERE isActive = 1;

------------------------------------------------------------

CREATE TABLE log_record (
  ID SERIAL PRIMARY KEY,
  name_of_table VARCHAR(20),
  type_of_operation VARCHAR(20),
  previous_value VARCHAR(30),
  new_value VARCHAR(30),
  date_of_operation DATE 
);

------------------------------------------------------------

CREATE FUNCTION insert_new ()
RETURNS TRIGGER
LANGUAGE plpgsql  
  AS  
$$  
BEGIN  
    IF NEW.user_name <> null THEN  
         INSERT INTO log_records(name_of_table, new_value, date_of_operation)  
         VALUES('users',NEW.user_name,now());  
    END IF;  
RETURN NEW;  
END;  
$$  

------------------------------------------------------------

CREATE TRIGGER new_trig 
AFTER INSERT ON users 
FOR EACH ROW 
EXECUTE PROCEDURE insert_new();

------------------------------------------------------------

CREATE OR REPLACE FUNCTION user_name (u_name VARCHAR)
RETURNS VARCHAR AS $u_roles$
DECLARE 
	u_roles VARCHAR(20);
BEGIN
	SELECT role_name INTO u_roles FROM users U INNER JOIN roles R ON R.role_id=U.user_role_id 
	WHERE U.user_name = u_name;
END;
$u_roles$ LANGUAGE plpgsql;

------------------------------------------------------------


CREATE OR REPLACE PROCEDURE insert_new_user (
  new_id INT, 
  new_name VARCHAR, 
  new_role_id INT, 
  new_date DATE, 
  new_account INT)
  LANGUAGE plpgsql
  AS $$
  BEGIN 
  INSERT INTO users VALUES (new_id, new_name, new_role_id, new_date, new_account);
  COMMIT;
  END;
  $$