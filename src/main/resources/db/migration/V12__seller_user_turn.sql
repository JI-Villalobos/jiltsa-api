-- Optional shift ("turno") of the seller. Free text, nullable: existing rows and clients that do
-- not send it keep working.
ALTER TABLE `seller` ADD COLUMN `user_turn` varchar(255) AFTER `full_name`;
