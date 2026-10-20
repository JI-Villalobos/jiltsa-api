-- Categories that classify the product catalog. A name may be stored once; the
-- default (case-insensitive) collation also makes 'Medicamento' and 'MEDICAMENTO'
-- the same name.
CREATE TABLE `categories` (
  `id` integer not null auto_increment,
  `category_name` varchar(255) not null,
  PRIMARY KEY (`id`),
  CONSTRAINT `uk_categories_category_name` UNIQUE (`category_name`)
) ENGINE=InnoDB;
