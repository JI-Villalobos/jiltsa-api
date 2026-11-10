-- Promotions of medicine close to its expiration date, the items each one offers, and the
-- loss recorded once a promotion is closed (what was left is written off at purchase price).
CREATE TABLE `promotion` (
  `id` integer not null auto_increment,
  `promotion_name` varchar(255) not null,
  `branch_id` integer not null,
  `creation_date` date not null,
  `promotion_end_date` date not null,
  `active` bit not null,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_promotion_branch` FOREIGN KEY (`branch_id`) REFERENCES `branches` (`id`),
  INDEX `ix_promotion_branch_creation_date` (`branch_id`, `creation_date`)
) ENGINE=InnoDB;

CREATE TABLE `promotion_item` (
  `id` bigint not null auto_increment,
  `key` varchar(255) not null,
  `description` varchar(255),
  `quantity` integer not null,
  `normal_price` float(53) not null,
  `promotion_price` float(53) not null,
  `purchase_price` float(53) not null,
  `promotion_id` integer not null,
  `expiration_date` date not null,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_promotion_item_promotion` FOREIGN KEY (`promotion_id`) REFERENCES `promotion` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB;

-- One record per promotion. The promotion cannot be deleted once its loss is recorded.
CREATE TABLE `recording_loss_expiration` (
  `id` integer not null auto_increment,
  `promotion_id` integer not null,
  `branch_id` integer not null,
  `total_loss` float(53) not null,
  `creation_date` date not null,
  PRIMARY KEY (`id`),
  CONSTRAINT `uk_recording_loss_expiration_promotion` UNIQUE (`promotion_id`),
  CONSTRAINT `fk_recording_loss_expiration_promotion` FOREIGN KEY (`promotion_id`) REFERENCES `promotion` (`id`),
  CONSTRAINT `fk_recording_loss_expiration_branch` FOREIGN KEY (`branch_id`) REFERENCES `branches` (`id`),
  INDEX `ix_recording_loss_expiration_branch_creation_date` (`branch_id`, `creation_date`)
) ENGINE=InnoDB;
