DROP TABLE users CASCADE CONSTRAINTS;
DROP TABLE activities CASCADE CONSTRAINTS;
DROP TABLE entries CASCADE CONSTRAINTS;
DROP SEQUENCE seqActivity;
DROP SEQUENCE seqEntry;

CREATE SEQUENCE seqActivity INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE seqEntry INCREMENT BY 1 START WITH 1;


CREATE TABLE users(
    username VARCHAR2(30) PRIMARY KEY ,
    password VARCHAR2(256),
    salt VARCHAR2(24),
    isAdmin NUMBER(1)
);


CREATE TABLE activities(
  id INTEGER PRIMARY KEY,
  name VARCHAR2(30)
);

CREATE TABLE entries(
  id INTEGER PRIMARY KEY ,
  activity_id INTEGER,
  title VARCHAR2(30),
  message VARCHAR2(200),
  user_id VARCHAR2(30),
  start_time TIMESTAMP,
  end_time TIMESTAMP,
  CONSTRAINT fk_entry_user FOREIGN KEY (user_id) REFERENCES users(username),
  CONSTRAINT fk_entry_activity FOREIGN KEY (activity_id) REFERENCES activities(id)
);

select * from users;

SELECT id, name from activities;
SELECT id, name from activities;

SELECT id, activity_id,message, start_time, end_time, title, username, password, salt, isadmin FROM entries INNER JOIN users ON entries.user_id = users.username;


insert into activities values(seqActivity.nextval, 'Programming');

commit;

select * from entries;