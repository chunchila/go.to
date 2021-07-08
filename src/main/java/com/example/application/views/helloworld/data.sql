--DROP TABLE IF EXISTS site;
--
--CREATE TABLE IF NOT EXISTS site (
--  uid int AUTO_INCREMENT  PRIMARY KEY,
--  url VARCHAR(250) NOT NULL,
--  person VARCHAR(250) NOT NULL
--);

--CREATE TABLE IF NOT EXISTS site (
--  uid BINARY NOT NULL,
--  url VARCHAR(250) NOT NULL,
--  person VARCHAR(250) NOT NULL
--);
--DROP TABLE site;
--CREATE TABLE site (
--  uid BINARY NOT NULL,
--  url VARCHAR(250) NOT NULL,
--  person VARCHAR(250) NOT NULL,
--  hash VARCHAR(250) NOT NULL
--);


DROP TABLE site;
CREATE TABLE site (
hash VARCHAR(250) NOT NULL,
  url VARCHAR(250) NOT NULL,
  person VARCHAR(250) NOT NULL

);

--
--INSERT INTO site (url, person) VALUES
--  ('Aliko', 'Dangote'),
--  ('Bill', 'Gates'),
--  ('Folrunsho', 'Alakija');