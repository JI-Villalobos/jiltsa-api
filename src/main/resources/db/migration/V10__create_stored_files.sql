-- Record of every object uploaded to the bucket through a presigned URL. A row is PENDING from
-- the moment the upload URL is issued until the client confirms the upload (UPLOADED). The owner
-- is the entity the file belongs to (owner_type names it, owner_id is its id).
CREATE TABLE `stored_files` (
  `id` bigint not null auto_increment,
  `object_key` varchar(512) not null,
  `bucket` varchar(255) not null,
  `file_name` varchar(255) not null,
  `content_type` varchar(255) not null,
  `size_bytes` bigint,
  `etag` varchar(255),
  `status` varchar(255) not null,
  `owner_type` varchar(255) not null,
  `owner_id` bigint not null,
  `uploaded_by` varchar(255) not null,
  `created_at` datetime(6) not null,
  `uploaded_at` datetime(6),
  PRIMARY KEY (`id`),
  CONSTRAINT `uk_stored_files_object_key` UNIQUE (`object_key`),
  INDEX `ix_stored_files_owner` (`owner_type`, `owner_id`)
) ENGINE=InnoDB;
