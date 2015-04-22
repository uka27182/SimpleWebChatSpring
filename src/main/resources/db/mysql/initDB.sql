CREATE DATABASE IF NOT EXISTS chatdb;
-- GRANT ALL PRIVILEGES ON chatdb.* TO chatuser@localhost IDENTIFIED BY 'chatuser';

USE chatdb;

CREATE TABLE IF NOT EXISTS chat_history (
  id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  createDT DATETIME,
  user VARCHAR(30),
  message VARCHAR(500),
  INDEX(user)
) engine=InnoDB;
