-- -----------------------------------------------------
-- Table `maestro`.`report`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maestro`.`report` (
  `report_id` INT NOT NULL AUTO_INCREMENT,
  `test_id` INT NOT NULL,
  `test_number` INT NULL,
  `test_name` VARCHAR(45) NULL,
  `test_script` VARCHAR(128) NULL,
  `test_host` VARCHAR(128) NULL,
  `test_host_role` VARCHAR(64) NULL,
  `test_result` VARCHAR(45) NULL,
  `test_result_message` VARCHAR(2048) NULL,
  `location` VARCHAR(1024) NULL,
  `aggregated` TINYINT NULL DEFAULT 0,
  `test_date` DATETIME NULL,
  `test_description` VARCHAR(2048) NULL,
  `test_comments` VARCHAR(2048) NULL,
  `valid` TINYINT NULL,
  `retired` TINYINT NULL,
  `retired_date` DATETIME NULL,
  PRIMARY KEY (`report_id`, `test_id`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `maestro`.`sut_node_info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maestro`.`sut_node_info` (
  `sut_node_id` INT NOT NULL AUTO_INCREMENT,
  `sut_node_name` VARCHAR(128) NULL,
  `sut_node_os_name` VARCHAR(64) NULL,
  `sut_node_os_arch` VARCHAR(45) NULL,
  `sut_node_os_version` VARCHAR(45) NULL,
  `sut_node_os_other` VARCHAR(512) NULL,
  `sut_node_hw_name` VARCHAR(64) NULL,
  `sut_node_hw_model` VARCHAR(64) NULL,
  `sut_node_hw_cpu` VARCHAR(45) NULL,
  `sut_node_hw_cpu_count` INT NULL,
  `sut_node_hw_ram` INT NULL,
  `sut_node_hw_disk_type` VARCHAR(45) NULL,
  `sut_node_hw_other` VARCHAR(512) NULL,
  PRIMARY KEY (`sut_node_id`))
ENGINE = InnoDB;



DROP TABLE test_sut_node_link;
-- -----------------------------------------------------
-- Table `maestro`.`test_sut_node_link`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maestro`.`test_sut_node_link` (
  `test_id` INT NOT NULL,
  `sut_node_id` INT NOT NULL,
  PRIMARY KEY (`test_id`, `sut_node_id`),
  INDEX `fk_report_sut_node_link_sut_node_info1_idx` (`sut_node_id` ASC),
  CONSTRAINT `fk_report_sut_node_link_sut_node_info1`
    FOREIGN KEY (`sut_node_id`)
    REFERENCES `maestro`.`sut_node_info` (`sut_node_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Note that it does not enforce the reference to test_id on report table on MariaDB because MariaDB cannot support referencing only one of the indexes.';



-- -----------------------------------------------------
-- Table `maestro`.`report_history`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maestro`.`report_history` (
  `report_id` INT NOT NULL,
  `test_id` INT NOT NULL,
  `test_number` INT NULL,
  `test_name` VARCHAR(45) NULL,
  `test_script` VARCHAR(128) NULL,
  `test_host` VARCHAR(128) NULL,
  `test_host_role` VARCHAR(64) NULL,
  `test_result` VARCHAR(45) NULL,
  `test_result_message` VARCHAR(2048) NULL,
  `location` VARCHAR(1024) NULL,
  `aggregated` TINYINT NULL DEFAULT 0,
  `test_date` DATETIME NULL,
  `test_description` VARCHAR(2048) NULL,
  `test_comments` VARCHAR(2048) NULL,
  `valid` TINYINT NULL,
  `retired` TINYINT NULL,
  `retired_date` DATETIME NULL,
  `report_update_history` DATETIME NULL)
ENGINE = InnoDB;

USE `maestro` ;

DELIMITER $$

USE `maestro`$$
CREATE DEFINER = CURRENT_USER TRIGGER `maestro`.`report_BEFORE_UPDATE` BEFORE UPDATE ON `report` FOR EACH ROW
BEGIN
	INSERT INTO report_history select *,now() as report_update_history from report where report_id = NEW.report_id;
END$$

DELIMITER ;