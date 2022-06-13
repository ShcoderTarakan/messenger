CREATE DATABASE chat;
USE chat;

CREATE TABLE user (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  login VARCHAR(255),
  name varchar(45) NOT NULL
);

CREATE TABLE direct_message (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  sender_id INT NOT NULL,
  recipient_id INT NOT NULL,
  text TEXT,
  created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE global_message (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  text TEXT,
  created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE direct_message
    ADD CONSTRAINT fk_dm_sender
    FOREIGN KEY (sender_id)
    REFERENCES user (id);

ALTER TABLE direct_message
    ADD CONSTRAINT fk_dm_recipient
    FOREIGN KEY (recipient_id)
    REFERENCES user (id);

ALTER TABLE global_message
    ADD CONSTRAINT fk_gm_user
    FOREIGN KEY (user_id)
    REFERENCES user (id);
