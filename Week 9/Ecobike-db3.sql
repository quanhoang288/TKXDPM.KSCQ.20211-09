-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema Ecobike-logic-data-model
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema Ecobike-logic-data-model
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `Ecobike-logic-data-model` DEFAULT CHARACTER SET utf8 ;
USE `Ecobike-logic-data-model` ;

-- -----------------------------------------------------
-- Table `Ecobike-logic-data-model`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Ecobike-logic-data-model`.`User` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NULL,
  `phoneNumber` VARCHAR(45) NULL,
  `province` VARCHAR(45) NULL,
  `address` VARCHAR(100) NULL,
  `dob` DATETIME NULL,
  `email` VARCHAR(45) NULL,
  `password` VARCHAR(20) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Ecobike-logic-data-model`.`Dock`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Ecobike-logic-data-model`.`Dock` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NULL,
  `address` VARCHAR(100) NULL,
  `area` VARCHAR(45) NULL,
  `capacity` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Ecobike-logic-data-model`.`Bike`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Ecobike-logic-data-model`.`Bike` (
  `id` INT NOT NULL,
  `type` VARCHAR(45) NULL,
  `licensePlate` VARCHAR(45) NULL,
  `batteryPercent` VARCHAR(5) NULL,
  `value` VARCHAR(20) NULL,
  `dockID` INT NOT NULL,
  PRIMARY KEY (`id`, `dockID`),
  INDEX `fk_Bike_Dock1_idx` (`dockID` ASC) VISIBLE,
  CONSTRAINT `fk_Bike_Dock1`
    FOREIGN KEY (`dockID`)
    REFERENCES `Ecobike-logic-data-model`.`Dock` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Ecobike-logic-data-model`.`BikeRentalInfor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Ecobike-logic-data-model`.`BikeRentalInfor` (
  `startAt` DATETIME NULL,
  `endAt` DATETIME NULL,
  `userID` INT NOT NULL,
  `bikeID` INT NOT NULL,
  PRIMARY KEY (`userID`, `bikeID`),
  INDEX `fk_BikeRentalInfor_Bike1_idx` (`bikeID` ASC) VISIBLE,
  CONSTRAINT `fk_BikeRentalInfor_User1`
    FOREIGN KEY (`userID`)
    REFERENCES `Ecobike-logic-data-model`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_BikeRentalInfor_Bike1`
    FOREIGN KEY (`bikeID`)
    REFERENCES `Ecobike-logic-data-model`.`Bike` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Ecobike-logic-data-model`.`PaymentTransection`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Ecobike-logic-data-model`.`PaymentTransection` (
  `id` INT NOT NULL,
  `content` VARCHAR(45) NULL,
  `method` VARCHAR(45) NULL,
  `createAt` DATETIME NULL,
  `bikeRentalInforID` INT NOT NULL,
  PRIMARY KEY (`id`, `bikeRentalInforID`),
  INDEX `fk_PaymentTransection_BikeRentalInfor1_idx` (`bikeRentalInforID` ASC) VISIBLE,
  CONSTRAINT `fk_PaymentTransection_BikeRentalInfor1`
    FOREIGN KEY (`bikeRentalInforID`)
    REFERENCES `Ecobike-logic-data-model`.`BikeRentalInfor` (`userID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
