-- MySQL Script generated by MySQL Workbench
-- Fri 05 Jan 2018 02:39:54 PM CET
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
CREATE SCHEMA IF NOT EXISTS `maestro` DEFAULT CHARACTER SET utf8 ;
USE `maestro` ;

-- -----------------------------------------------------
-- Table `maestro`.`test_parameter`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maestro`.`test_parameter` (
  `test_parameter_id` INT NOT NULL AUTO_INCREMENT,
  `test_target_rate` INT NULL DEFAULT 0,
  `test_sender_count` INT NULL DEFAULT 1,
  `test_receiver_count` INT NULL DEFAULT 1,
  PRIMARY KEY (`test_parameter_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `maestro`.`test_fail_condition`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maestro`.`test_fail_condition` (
  `test_parameter_id` INT NOT NULL,
  `test_fail_condition_name` VARCHAR(64) NOT NULL,
  `test_fail_condition_value` VARCHAR(128) NOT NULL,
  `test_fail_condition_description` VARCHAR(512) NULL,
  PRIMARY KEY (`test_parameter_id`, `test_fail_condition_name`),
  INDEX `fk_test_fail_condition_test_parameter1_idx` (`test_parameter_id` ASC),
  CONSTRAINT `fk_test_fail_condition_test_parameter1`
    FOREIGN KEY (`test_parameter_id`)
    REFERENCES `maestro`.`test_parameter` (`test_parameter_id`)
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
  `env_resource_os_arch` VARCHAR(45) NULL,
  `env_resource_os_version` VARCHAR(45) NULL,
  `env_resource_os_other` VARCHAR(512) NULL,
  `env_resource_hw_name` VARCHAR(64) NULL,
  `env_resource_hw_model` VARCHAR(64) NULL,
  `env_resource_hw_cpu` VARCHAR(45) NULL,
  `env_resource_hw_ram` INT NULL,
  `env_resource_hw_disk_type` VARCHAR(45) NULL,
  `env_resource_hw_other` VARCHAR(512) NULL,
  PRIMARY KEY (`env_resource_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `maestro`.`test_msg_property`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maestro`.`test_msg_property` (
  `test_parameter_id` INT NOT NULL,
  `test_msg_property_name` VARCHAR(64) NOT NULL,
  `test_msg_property_value` VARCHAR(128) NOT NULL,
  `test_msg_property_description` VARCHAR(512) NULL,
  PRIMARY KEY (`test_parameter_id`, `test_msg_property_name`),
  INDEX `fk_test_msg_properties_test_parameter1_idx` (`test_parameter_id` ASC),
  CONSTRAINT `fk_test_msg_properties_test_parameter1`
    FOREIGN KEY (`test_parameter_id`)
    REFERENCES `maestro`.`test_parameter` (`test_parameter_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `maestro`.`env_results`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maestro`.`env_results` (
  `env_results_id` INT NOT NULL,
  `env_name` VARCHAR(128) NOT NULL,
  `env_resource_id` INT NOT NULL,
  `env_resource_role` ENUM('sender', 'receiver', 'inspector', 'other') NOT NULL,
  `test_rate_min` INT NULL,
  `test_rate_max` INT NULL,
  `test_rate_error_count` INT NULL,
  `test_rate_samples` INT NULL,
  `test_rate_geometric_mean` DOUBLE NULL,
  `test_rate_standard_deviation` DOUBLE NULL,
  `test_rate_skip_count` INT NULL,
  `error` TINYINT NULL,
  PRIMARY KEY (`env_results_id`),
  INDEX `fk_env_results_env_resource1_idx` (`env_resource_id` ASC),
  CONSTRAINT `fk_env_results_env_resource1`
    FOREIGN KEY (`env_resource_id`)
    REFERENCES `maestro`.`env_resource` (`env_resource_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


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
  PRIMARY KEY (`sut_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `maestro`.`test`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maestro`.`test` (
  `test_id` INT NOT NULL AUTO_INCREMENT,
  `test_name` VARCHAR(45) NULL,
  `test_result` ENUM('failed', 'success', 'error') NOT NULL,
  `env_results_id` INT NOT NULL,
  `test_parameter_id` INT NOT NULL,
  `sut_id` INT NULL,
  `test_report_link` VARCHAR(512) NULL,
  `test_data_storage_info` VARCHAR(512) NULL,
  `test_tags` VARCHAR(512) NULL,
  `test_date` DATETIME NULL,
  PRIMARY KEY (`test_id`),
  INDEX `fk_test_test_parameter1_idx` (`test_parameter_id` ASC),
  INDEX `fk_test_env_results1_idx` (`env_results_id` ASC),
  INDEX `fk_test_sut1_idx` (`sut_id` ASC),
  CONSTRAINT `fk_test_test_parameter1`
    FOREIGN KEY (`test_parameter_id`)
    REFERENCES `maestro`.`test_parameter` (`test_parameter_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_test_env_results1`
    FOREIGN KEY (`env_results_id`)
    REFERENCES `maestro`.`env_results` (`env_results_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_test_sut1`
    FOREIGN KEY (`sut_id`)
    REFERENCES `maestro`.`sut` (`sut_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
