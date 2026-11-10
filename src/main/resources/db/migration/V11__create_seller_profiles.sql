-- Employee record behind each seller, as collected by the "Información de Empleados" form:
-- personal data, emergency contact and the work file (hire date, position, salary). One profile
-- per seller, filled in by the seller herself after reading the privacy notice, or by an admin
-- for employees who already sent the form (registered_by says who). The INE pictures and the address proof are stored_files owned by the profile
-- (owner_type SELLER_PROFILE), told apart by the new document_type column.
CREATE TABLE `seller_profile` (
  `id` integer not null auto_increment,
  `seller_id` integer not null,
  `full_name` varchar(255) not null,
  `birth_date` date not null,
  `phone` varchar(20) not null,
  `rfc` varchar(13),
  `curp` varchar(18),
  `emergency_contact_name` varchar(255) not null,
  `emergency_contact_phone` varchar(20) not null,
  `hire_date` date not null,
  `position` varchar(255) not null,
  `salary` float(53) not null,
  `privacy_notice_accepted` bit not null,
  `privacy_notice_accepted_at` datetime(6) not null,
  `registered_by` varchar(255) not null,
  `created_at` datetime(6) not null,
  `updated_at` datetime(6) not null,
  PRIMARY KEY (`id`),
  CONSTRAINT `uk_seller_profile_seller` UNIQUE (`seller_id`),
  CONSTRAINT `fk_seller_profile_seller` FOREIGN KEY (`seller_id`) REFERENCES `seller` (`id`)
) ENGINE=InnoDB;

ALTER TABLE `stored_files` ADD COLUMN `document_type` varchar(255) AFTER `owner_id`;
