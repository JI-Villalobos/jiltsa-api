-- Cash withdrawals referenced their branch by name, so renaming a branch detached
-- its withdrawals from it and from its balance. They now reference branches.id.
--
-- MySQL can't roll back DDL, so the guard runs first and changes nothing: if any
-- withdrawal's name matches no branch, or more than one (branches.name is not
-- unique), the backfill would be wrong, and the migration stops with
-- "Subquery returns more than 1 row" (a plain script can't raise its own error).
-- Find the offenders with
--   SELECT cw.`branch`, COUNT(*) FROM `cash_withdrawals` cw
--   WHERE (SELECT COUNT(*) FROM `branches` b WHERE b.`name` = cw.`branch`) <> 1
--   GROUP BY cw.`branch`;
-- fix them, run `flyway repair` (or delete the failed row from
-- flyway_schema_history) and restart.
DO (
    SELECT 1 FROM (SELECT 1 AS `x` UNION ALL SELECT 1) AS `two_rows`
    WHERE EXISTS (
        SELECT 1 FROM `cash_withdrawals` cw
        WHERE (SELECT COUNT(*) FROM `branches` b WHERE b.`name` = cw.`branch`) <> 1
    )
);

ALTER TABLE `cash_withdrawals` ADD COLUMN `branch_id` integer;

UPDATE `cash_withdrawals` cw
SET cw.`branch_id` = (SELECT b.`id` FROM `branches` b WHERE b.`name` = cw.`branch`);

ALTER TABLE `cash_withdrawals`
    MODIFY `branch_id` integer NOT NULL,
    DROP COLUMN `branch`,
    ADD INDEX `idx_cash_withdrawals_branch_date` (`branch_id`, `date`),
    ADD CONSTRAINT `fk_cash_withdrawals_branch` FOREIGN KEY (`branch_id`) REFERENCES `branches` (`id`);
