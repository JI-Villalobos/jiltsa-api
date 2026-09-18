-- branch_config.profile and check_list.check_type stored the Java enum ordinal
-- (tinyint), so reordering or inserting a constant would silently relabel rows.
-- Both now store the constant name, like app_users.role already does.
--
-- Each conversion fills a new column first and only drops the old one after the
-- new column has been made NOT NULL: an unexpected ordinal leaves a NULL, that
-- step fails ("Invalid use of NULL value"), and the original data is untouched.
-- Recovery in that case (MySQL DDL is not transactional): fix the offending
-- rows, DROP the helper column (profile_name / check_type_name), run
-- `flyway repair` (or delete the failed row from flyway_schema_history) and
-- restart.

-- Profile: 0 = PHARMACY, 1 = PURIFIED_WATER, 2 = NURSERY
ALTER TABLE `branch_config` ADD COLUMN `profile_name` varchar(255) NULL;
UPDATE `branch_config` SET `profile_name` = CASE `profile`
    WHEN 0 THEN 'PHARMACY'
    WHEN 1 THEN 'PURIFIED_WATER'
    WHEN 2 THEN 'NURSERY'
END;
ALTER TABLE `branch_config` MODIFY `profile_name` varchar(255) NOT NULL;
ALTER TABLE `branch_config` DROP COLUMN `profile`;
ALTER TABLE `branch_config` CHANGE `profile_name` `profile` varchar(255) NOT NULL;

-- CheckType: 0 = CHECK_IN, 1 = CHECK_OUT
ALTER TABLE `check_list` ADD COLUMN `check_type_name` varchar(255) NULL;
UPDATE `check_list` SET `check_type_name` = CASE `check_type`
    WHEN 0 THEN 'CHECK_IN'
    WHEN 1 THEN 'CHECK_OUT'
END;
ALTER TABLE `check_list` MODIFY `check_type_name` varchar(255) NOT NULL;
ALTER TABLE `check_list` DROP COLUMN `check_type`;
ALTER TABLE `check_list` CHANGE `check_type_name` `check_type` varchar(255) NOT NULL;
