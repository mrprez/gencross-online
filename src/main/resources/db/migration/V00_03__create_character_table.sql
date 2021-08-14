CREATE TABLE `rpg_character`
(
    id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name VARCHAR(100) NOT NULL,
    table_id INT NOT NULL,
    player_id INT NULL,
    data MEDIUMBLOB NOT NULL,
    creation_date DATETIME NOT NULL,
    FOREIGN KEY (table_id) REFERENCES rpg_table(id),
    FOREIGN KEY (player_id) REFERENCES user(id)
);