-- seller.password was never read or exposed by the API; the application only
-- filled it with a plaintext default on insert.
ALTER TABLE `seller` DROP COLUMN `password`;
