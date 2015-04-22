DROP TABLE chat_history IF EXISTS;

CREATE TABLE chat_history (
  id         INTEGER IDENTITY PRIMARY KEY,
  createDT TIMESTAMP,
  user VARCHAR(30),
  message VARCHAR(500)
);

CREATE INDEX idx_user ON chat_history (user);
