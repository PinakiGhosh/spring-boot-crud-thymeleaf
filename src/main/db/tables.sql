# CREATE DATABASE test;
USE test;

DROP TABLE IF EXISTS `inventory`;
DROP TABLE IF EXISTS `item`;


CREATE TABLE `item` (
  `item_id` int(11) NOT NULL AUTO_INCREMENT,
  `item_name` varchar(256) UNIQUE NOT NULL,
  `description` varchar(512) NOT NULL,
  `added_on` datetime NOT NULL,
  PRIMARY KEY (`item_id`)
);

CREATE TABLE `test`.`inventory` (
  `item_id` INT NOT NULL,
  `count` INT NULL,
  `updated_on` DATETIME NOT NULL,
  PRIMARY KEY (`item_id`),
  CONSTRAINT `fk_inventory_1`
  FOREIGN KEY (`item_id`)
  REFERENCES `test`.`item` (`item_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
