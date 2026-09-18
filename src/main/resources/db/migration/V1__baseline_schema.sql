-- Baseline schema, generated from the JPA entities by Hibernate (MySQL 5.7 dialect).
-- Existing databases are baselined at this version (spring.flyway.baseline-on-migrate),
-- so this script only runs on an empty database. Identifiers are quoted because
-- some column names (e.g. products.key) are MySQL reserved words.

CREATE TABLE `accounts` (
  `branch_id` integer not null,
  `id` integer not null auto_increment,
  `seller_id` integer not null,
  `date` datetime(6),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `active_accounting` (
  `accounting_id` integer not null,
  `branch_id` integer not null,
  `id` integer not null auto_increment,
  `is_active` bit not null,
  `seller_id` integer not null,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `app_users` (
  `branch_id` integer,
  `id` integer not null auto_increment,
  `email` varchar(255),
  `pass` varchar(255),
  `username` varchar(255),
  `role` enum ('ADMIN','DEVELOPER','USER'),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `bills` (
  `amount` float(53) not null,
  `branch_id` integer not null,
  `id` integer not null auto_increment,
  `is_active` bit not null,
  `is_paid` bit not null,
  `provider_id` integer not null,
  `date` datetime(6) not null,
  `limit_payment_date` datetime(6) not null,
  `reception_date` datetime(6),
  `branch` varchar(255) not null,
  `invoice` varchar(255) not null,
  `payment_ticket` varchar(255),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `branch_config` (
  `branch_id` integer,
  `can_edit_account` bit not null,
  `can_open_outdated_account` bit not null,
  `id` integer not null auto_increment,
  `initial_balance` float(53) not null,
  `profile` tinyint not null,
  `modified_date` datetime(6),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `branches` (
  `id` integer not null auto_increment,
  `is_active` bit not null,
  `name` varchar(255) not null,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `break_even_point` (
  `admin_payment` float(53) not null,
  `branch_id` integer not null,
  `electricity_service` float(53) not null,
  `fixed_expenses` float(53) not null,
  `gasoline_and_transport` float(53) not null,
  `id` integer not null auto_increment,
  `internet` float(53) not null,
  `operating_cost` float(53) not null,
  `other_expenses` float(53) not null,
  `other_services` float(53) not null,
  `paysheet` float(53) not null,
  `pe` float(53) not null,
  `rent` float(53) not null,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `cash_registry` (
  `accounting_id` integer not null,
  `amount` float(53) not null,
  `id` integer not null auto_increment,
  `income_type_id` integer not null,
  `modified_date` datetime(6),
  `time` datetime(6),
  `tag` varchar(255) not null,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `cash_sorting` (
  `accounting_id` integer not null,
  `bls_1` integer not null,
  `bls_10` integer not null,
  `bls_2` integer not null,
  `bls_5` integer not null,
  `branch_id` integer not null,
  `bt_100` integer not null,
  `bt_1000` integer not null,
  `bt_20` integer not null,
  `bt_200` integer not null,
  `bt_50` integer not null,
  `bt_500` integer not null,
  `id` integer not null auto_increment,
  `md_005` integer not null,
  `md_1` integer not null,
  `md_10` integer not null,
  `md_2` integer not null,
  `md_20` integer not null,
  `md_5` integer not null,
  `cash_date` datetime(6) not null,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `cash_withdrawals` (
  `amount` float(53) not null,
  `id` integer not null auto_increment,
  `date` datetime(6),
  `branch` varchar(255) not null,
  `concept` varchar(255) not null,
  `seller_name` varchar(255) not null,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `check_list` (
  `accounting_id` integer not null,
  `cash_balance` float(53) not null,
  `cellphone_charge` integer not null,
  `cellphone_condition` bit not null,
  `check_type` tinyint not null,
  `date` date not null,
  `furniture_clean_conditions` tinyint not null,
  `id` integer not null,
  `installation_state` bit not null,
  `seller_id` integer not null,
  `tranbox_balance` float(53) not null,
  `cellphone_observation` varchar(255),
  `installation_state_observation` varchar(255),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `check_list_seq` (
  `next_val` bigint
) ENGINE=InnoDB;

INSERT INTO `check_list_seq` values ( 1 );

CREATE TABLE `credit_sales` (
  `amount` float(53) not null,
  `branch_id` integer not null,
  `id` integer not null auto_increment,
  `is_paid` bit,
  `date` datetime(6) not null,
  `client` varchar(255) not null,
  `concept` varchar(255) not null,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `expense_registry` (
  `accounting_id` integer not null,
  `amount` float(53) not null,
  `expense_type_id` integer not null,
  `id` integer not null auto_increment,
  `modified_date` datetime(6),
  `time` datetime(6),
  `description` varchar(255) not null,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `expense_type` (
  `id` integer not null auto_increment,
  `type` varchar(255) not null,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `income_type` (
  `id` integer not null auto_increment,
  `type` varchar(255) not null,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `operative_expense` (
  `amount` float(53) not null,
  `branch_id` integer not null,
  `id` integer not null auto_increment,
  `provider_id` integer,
  `expense_date` datetime(6) not null,
  `category` varchar(255) not null,
  `concept` varchar(255) not null,
  `invoice` varchar(255),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `order_items` (
  `budgeted` float(53) not null,
  `final_price` float(53) not null,
  `order_id` integer not null,
  `price` float(53) not null,
  `requested` integer not null,
  `status` integer not null,
  `stocked` integer not null,
  `total` float(53) not null,
  `id` bigint not null auto_increment,
  `item` varchar(255) not null,
  `item_type` varchar(255) not null,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `orders` (
  `branch_id` integer not null,
  `creation_date` date not null,
  `estimated_cost` float(53) not null,
  `id` integer not null auto_increment,
  `is_open` bit not null,
  `provider_id` integer not null,
  `real_cost` float(53) not null,
  `status` integer not null,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `partial_payments` (
  `amount` float(53) not null,
  `credit_sale_id` integer not null,
  `id` integer not null auto_increment,
  `payment_date` datetime(6) not null,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `payments` (
  `amount` float(53) not null,
  `id` integer not null auto_increment,
  `date` datetime(6) not null,
  `ticket` varchar(255) not null,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `products` (
  `branch_id` integer,
  `id` bigint not null auto_increment,
  `category` varchar(255),
  `description` varchar(255),
  `key` varchar(255),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `providers` (
  `id` integer not null auto_increment,
  `name` varchar(255) not null,
  `rfc` varchar(255) not null,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `sale_result` (
  `branch_id` integer not null,
  `id` integer not null auto_increment,
  `pharmacy_amount` float(53) not null,
  `services_amount` float(53) not null,
  `week_number` integer not null,
  `year` integer not null,
  `final_date` datetime(6) not null,
  `initial_date` datetime(6) not null,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `sales` (
  `approximate_utility` float(53),
  `branch_id` integer,
  `price` float(53),
  `purchase_price` float(53),
  `quantity` integer,
  `total` float(53),
  `id` bigint not null auto_increment,
  `ticket` bigint,
  `timestamp` datetime(6),
  `category` varchar(255),
  `description` varchar(255),
  `key` varchar(255),
  `user` varchar(255),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `seller` (
  `branch_id` integer not null,
  `id` integer not null auto_increment,
  `is_active` bit not null,
  `full_name` varchar(255) not null,
  `password` varchar(255),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

ALTER TABLE `accounts` ADD CONSTRAINT `FK6s1ks79nqt6d16ub5ygm7nm7t` FOREIGN KEY (`branch_id`) REFERENCES `branches` (`id`);
ALTER TABLE `accounts` ADD CONSTRAINT `FKrcyt8m47knnc58g5qydts9nsx` FOREIGN KEY (`seller_id`) REFERENCES `seller` (`id`);
ALTER TABLE `bills` ADD CONSTRAINT `FKd67o7v1xa0wqcvxb7ltsof7cp` FOREIGN KEY (`provider_id`) REFERENCES `providers` (`id`);
ALTER TABLE `cash_registry` ADD CONSTRAINT `FKqf55xvbgvanfsom6b4i2m63wv` FOREIGN KEY (`accounting_id`) REFERENCES `accounts` (`id`);
ALTER TABLE `cash_registry` ADD CONSTRAINT `FKr28yscoy1mojftouldgy2f8og` FOREIGN KEY (`income_type_id`) REFERENCES `income_type` (`id`);
ALTER TABLE `expense_registry` ADD CONSTRAINT `FKcl7td42s3oqa3p9sy5lf4cn9k` FOREIGN KEY (`accounting_id`) REFERENCES `accounts` (`id`);
ALTER TABLE `expense_registry` ADD CONSTRAINT `FKf6vsnyj135jqn7eohv3n781bj` FOREIGN KEY (`expense_type_id`) REFERENCES `expense_type` (`id`);
ALTER TABLE `order_items` ADD CONSTRAINT `FKbioxgbv59vetrxe0ejfubep1w` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`);
ALTER TABLE `partial_payments` ADD CONSTRAINT `FKdgav0ybow2nh3l4oh9dty8car` FOREIGN KEY (`credit_sale_id`) REFERENCES `credit_sales` (`id`);
ALTER TABLE `seller` ADD CONSTRAINT `FKs925q9v1xf02iuqpjp0neepc2` FOREIGN KEY (`branch_id`) REFERENCES `branches` (`id`);
