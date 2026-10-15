-- A ticket lists each product once, so (branch, ticket, key) identifies a sale
-- line; storing it twice means the same sales were uploaded again. NOT NULL keeps
-- the rule airtight (a unique index lets any row with a NULL in it through).
--
-- Single ALTER, so it either applies whole or not at all: existing duplicates
-- fail it with "Duplicate entry", NULLs with "Invalid use of NULL value", and the
-- table is left untouched. Find the offenders with
--   SELECT `branch_id`, `ticket`, `key`, COUNT(*) FROM `sales` GROUP BY `branch_id`, `ticket`, `key` HAVING COUNT(*) > 1;
--   SELECT * FROM `sales` WHERE `branch_id` IS NULL OR `ticket` IS NULL OR `key` IS NULL;
-- fix them, run `flyway repair` (or delete the failed row from
-- flyway_schema_history) and restart.
ALTER TABLE `sales`
    MODIFY `branch_id` integer NOT NULL,
    MODIFY `ticket` bigint NOT NULL,
    MODIFY `key` varchar(255) NOT NULL,
    ADD CONSTRAINT `uk_sales_branch_ticket_key` UNIQUE (`branch_id`, `ticket`, `key`);
