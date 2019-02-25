DROP TABLE users CASCADE CONSTRAINTS;
DROP TABLE activity CASCADE CONSTRAINTS;
DROP TABLE entry CASCADE CONSTRAINTS;
DROP SEQUENCE seqActivity;
DROP SEQUENCE seqEntry;

CREATE SEQUENCE seqActivity INCREMENT BY 1 START WITH 0;
CREATE SEQUENCE seqEntry INCREMENT BY 1 START WITH 0;


CREATE TABLE users(
    username VARCHAR2(30) PRIMARY KEY ,
    password VARCHAR2(256),
    salt VARCHAR2(24),
    isAdmin NUMBER(1)
);


CREATE TABLE activity(
  id INTEGER PRIMARY KEY,
  name VARCHAR2(30)
);

CREATE TABLE entry(
  id INTEGER PRIMARY KEY ,
  activity_id INTEGER,
  message VARCHAR2(200),
  user_id VARCHAR2(30),
  start_time TIMESTAMP,
  end_time TIMESTAMP,
  CONSTRAINT fk_entry_user FOREIGN KEY (user_id) REFERENCES users(username),
  CONSTRAINT fk_entry_activity FOREIGN KEY (activity_id) REFERENCES activity(id)
);

select * from users;