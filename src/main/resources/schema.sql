DROP TABLE IF EXISTS DEPT;
CREATE TABLE DEPT (
DNO INT AUTO_INCREMENT  PRIMARY KEY,
DNAME VARCHAR(50) NOT NULL
);
DROP TABLE IF EXISTS EMP;
CREATE TABLE EMP (
  ENO INT AUTO_INCREMENT  PRIMARY KEY,
  ENAME VARCHAR(50) NOT NULL,
  DNO INT,
  SALARY INT
);

