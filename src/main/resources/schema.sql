DROP TABLE IF EXISTS `order_table`;

CREATE TABLE `order_table` (
`order_id` INT NOT NULL AUTO_INCREMENT,
`customer_name` VARCHAR(30) NOT NULL,
`shipping_address` VARCHAR(100) NOT NULL,
`order_date` VARCHAR(50) NOT NULL,
`total` INT NOT NULL,
PRIMARY KEY (`order_id`)
);

CREATE TABLE `order_item_mapping` (
`order_id` INT NOT NULL,
`item_id` INT NOT NULL
);