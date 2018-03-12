-- MySQL Script generated by MySQL Workbench
-- Mon 12 Mar 2018 03:46:02 PM CET
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema maestro
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema maestro
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `maestro` DEFAULT CHARACTER SET utf8 COLLATE utf8_lithuanian_ci ;
USE `maestro` ;

-- -----------------------------------------------------
-- Table `maestro`.`sut`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maestro`.`sut` (
  `sut_id` INT NOT NULL AUTO_INCREMENT,
  `sut_name` VARCHAR(64) NOT NULL,
  `sut_version` VARCHAR(45) NOT NULL,
  `sut_jvm_info` VARCHAR(45) NULL,
  `sut_other` VARCHAR(45) NULL,
  `sut_tags` VARCHAR(512) NULL,
  `client_name` VARCHAR(45) NULL,
  `client_version` VARCHAR(45) NULL,
  PRIMARY KEY (`sut_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `maestro`.`test`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maestro`.`test` (
  `test_id` INT NOT NULL AUTO_INCREMENT,
  `test_number` INT NOT NULL,
  `test_name` VARCHAR(45) NULL,
  `test_result` VARCHAR(45) NOT NULL,
  `sut_id` INT NULL,
  `test_report_link` VARCHAR(512) NULL,
  `test_data_storage_info` VARCHAR(512) NULL,
  `test_tags` VARCHAR(512) NULL,
  `test_date` DATETIME NULL,
  `test_duration` INT NULL,
  `test_target_rate` INT NULL DEFAULT 0,
  `test_valid` TINYINT NULL DEFAULT 1,
  PRIMARY KEY (`test_id`, `test_number`),
  INDEX `fk_test_sut1_idx` (`sut_id` ASC),
  CONSTRAINT `fk_test_sut1`
    FOREIGN KEY (`sut_id`)
    REFERENCES `maestro`.`sut` (`sut_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `maestro`.`test_fail_condition`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maestro`.`test_fail_condition` (
  `test_id` INT NOT NULL,
  `test_number` INT NOT NULL,
  `test_fail_condition_resource_name` VARCHAR(128) NOT NULL DEFAULT 'all',
  `test_fail_condition_name` VARCHAR(64) NOT NULL,
  `test_fail_condition_value` VARCHAR(128) NOT NULL,
  `test_fail_condition_description` VARCHAR(512) NULL,
  PRIMARY KEY (`test_id`, `test_fail_condition_name`, `test_fail_condition_resource_name`, `test_number`),
  INDEX `fk_test_fail_condition_test1_idx` (`test_id` ASC, `test_number` ASC),
  CONSTRAINT `fk_test_fail_condition_test1`
    FOREIGN KEY (`test_id` , `test_number`)
    REFERENCES `maestro`.`test` (`test_id` , `test_number`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `maestro`.`env_resource`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maestro`.`env_resource` (
  `env_resource_id` INT NOT NULL AUTO_INCREMENT,
  `env_resource_name` VARCHAR(128) NOT NULL,
  `env_resource_os_name` VARCHAR(64) NULL DEFAULT 'RHEL',
  `env_resource_os_arch` VARCHAR(45) NULL DEFAULT 'x86_64',
  `env_resource_os_version` VARCHAR(45) NULL,
  `env_resource_os_other` VARCHAR(512) NULL,
  `env_resource_hw_name` VARCHAR(64) NULL,
  `env_resource_hw_model` VARCHAR(64) NULL,
  `env_resource_hw_cpu` VARCHAR(45) NULL,
  `env_resource_hw_ram` INT NULL,
  `env_resource_hw_disk_type` VARCHAR(45) NULL DEFAULT 'HD',
  `env_resource_hw_other` VARCHAR(512) NULL,
  PRIMARY KEY (`env_resource_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `maestro`.`test_msg_property`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maestro`.`test_msg_property` (
  `test_id` INT NOT NULL,
  `test_number` INT NOT NULL,
  `test_msg_property_resource_name` VARCHAR(128) NOT NULL DEFAULT 'all',
  `test_msg_property_name` VARCHAR(64) NOT NULL,
  `test_msg_property_value` VARCHAR(128) NOT NULL,
  `test_msg_property_description` VARCHAR(512) NULL,
  PRIMARY KEY (`test_id`, `test_msg_property_resource_name`, `test_msg_property_name`, `test_number`),
  INDEX `fk_test_msg_property_test1_idx` (`test_id` ASC, `test_number` ASC),
  CONSTRAINT `fk_test_msg_property_test1`
    FOREIGN KEY (`test_id` , `test_number`)
    REFERENCES `maestro`.`test` (`test_id` , `test_number`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `maestro`.`env_results`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maestro`.`env_results` (
  `env_results_id` INT NOT NULL AUTO_INCREMENT,
  `env_resource_id` INT NOT NULL,
  `test_id` INT NOT NULL,
  `test_number` INT NOT NULL,
  `env_name` VARCHAR(128) NOT NULL,
  `env_resource_role` VARCHAR(64) NOT NULL,
  `test_rate_min` INT NULL,
  `test_rate_max` INT NULL,
  `test_rate_error_count` INT NULL,
  `test_rate_samples` INT NULL,
  `test_rate_geometric_mean` DOUBLE NULL,
  `test_rate_standard_deviation` DOUBLE NULL,
  `test_rate_skip_count` INT NULL,
  `connection_count` INT NULL,
  `lat_percentile_90` DOUBLE NULL,
  `lat_percentile_95` DOUBLE NULL,
  `lat_percentile_99` DOUBLE NULL,
  `error` TINYINT NULL,
  PRIMARY KEY (`env_results_id`),
  INDEX `fk_env_results_env_resource1_idx` (`env_resource_id` ASC),
  INDEX `fk_env_results_test1_idx` (`test_id` ASC, `test_number` ASC),
  CONSTRAINT `fk_env_results_env_resource1`
    FOREIGN KEY (`env_resource_id`)
    REFERENCES `maestro`.`env_resource` (`env_resource_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_env_results_test1`
    FOREIGN KEY (`test_id` , `test_number`)
    REFERENCES `maestro`.`test` (`test_id` , `test_number`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `maestro` ;

-- -----------------------------------------------------
-- Placeholder table for view `maestro`.`test_results`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maestro`.`test_results` (`test_id` INT, `test_number` INT, `sut_id` INT, `sut_tags` INT, `sut_name` INT, `sut_version` INT, `test_result` INT, `error` INT, `test_valid` INT, `env_resource_name` INT, `env_resource_role` INT, `test_rate_min` INT, `test_rate_max` INT, `test_rate_geometric_mean` INT, `test_rate_standard_deviation` INT, `test_rate_skip_count` INT, `connection_count` INT, `test_date` INT, `test_name` INT, `test_tags` INT, `test_report_link` INT);

-- -----------------------------------------------------
-- View `maestro`.`test_results`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `maestro`.`test_results`;
USE `maestro`;
CREATE OR REPLACE VIEW `test_results` AS
    SELECT 
        t.test_id,
        t.test_number,
        sut.sut_id,
        sut.sut_tags,
        sut.sut_name,
        sut.sut_version,
        t.test_result,
        er.error,
        t.test_valid,
        res.env_resource_name,
        er.env_resource_role,
        er.test_rate_min,
        er.test_rate_max,
        er.test_rate_geometric_mean,
        er.test_rate_standard_deviation,
        er.test_rate_skip_count,
        er.connection_count,
        t.test_date,
        t.test_name,
        t.test_tags,
        t.test_report_link
    FROM
        test t,
        env_results er,
        env_resource res,
        sut
    WHERE
        t.test_id = er.test_id
            AND er.env_resource_id = res.env_resource_id
            AND t.sut_id = sut.sut_id;
USE `maestro`;

DELIMITER $$
USE `maestro`$$
CREATE DEFINER = CURRENT_USER TRIGGER `maestro`.`test_BEFORE_INSERT` BEFORE INSERT ON `test` FOR EACH ROW
BEGIN

END
$$


DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
