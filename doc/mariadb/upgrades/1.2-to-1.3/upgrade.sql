ALTER TABLE test ADD COLUMN maestro_version VARCHAR(32) default '1.3.0';

CREATE TABLE IF NOT EXISTS `maestro`.`test_history` (
  `test_id` INT NOT NULL,
  `test_number` INT NULL,
  `test_name` VARCHAR(45) NULL,
  `test_result` VARCHAR(45) NULL,
  `sut_id` INT NULL,
  `test_report_link` VARCHAR(512) NULL,
  `test_data_storage_info` VARCHAR(512) NULL,
  `test_tags` VARCHAR(512) NULL,
  `test_date` DATETIME NULL,
  `test_duration` INT NULL,
  `test_target_rate` INT NULL DEFAULT 0,
  `test_valid` TINYINT NULL DEFAULT 1,
  `maestro_version` VARCHAR(32) DEFAULT '1.3.0',
  `test_update_date` DATETIME NOT NULL)
ENGINE = InnoDB;

CREATE DEFINER = CURRENT_USER TRIGGER `maestro`.`test_BEFORE_UPDATE` BEFORE UPDATE ON `test` FOR EACH ROW
BEGIN
	INSERT INTO test_history select *,now() as test_update_date from test where test_id = NEW.test_id and test_number = NEW.test_number;
END;

ALTER TABLE test ADD COLUMN maestro_version VARCHAR(32) default '1.3.0';

update test set maestro_version = '1.2.0';

commit;


