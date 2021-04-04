CREATE TABLE `user`
(
    id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    username VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    password_hash CHAR(60) NOT NULL,
    CONSTRAINT u_user UNIQUE (username)
);