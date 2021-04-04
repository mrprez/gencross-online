CREATE TABLE `rpg_table`
(
    id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name VARCHAR(100) NOT NULL,
    game VARCHAR(50) NOT NULL,
    gm_id INT NOT NULL,
    creation_date DATETIME NOT NULL,
    FOREIGN KEY (gm_id) REFERENCES user(id)
);