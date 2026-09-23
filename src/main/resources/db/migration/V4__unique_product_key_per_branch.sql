-- A product key identifies one product within a branch; the same key may exist
-- in other branches. NOT NULL keeps the rule airtight (a unique index lets any
-- number of rows with a NULL key or branch through).
--
-- Single ALTER, so it either applies whole or not at all: existing duplicates
-- fail it with "Duplicate entry", NULLs with "Invalid use of NULL value", and the
-- table is left untouched. Find the offenders with
--   SELECT `branch_id`, `key`, COUNT(*) FROM `products` GROUP BY `branch_id`, `key` HAVING COUNT(*) > 1;
--   SELECT * FROM `products` WHERE `branch_id` IS NULL OR `key` IS NULL;
-- fix them, run `flyway repair` (or delete the failed row from
-- flyway_schema_history) and restart.
ALTER TABLE `products`
    MODIFY `branch_id` integer NOT NULL,
    MODIFY `key` varchar(255) NOT NULL,
    ADD CONSTRAINT `uk_products_branch_key` UNIQUE (`branch_id`, `key`);
