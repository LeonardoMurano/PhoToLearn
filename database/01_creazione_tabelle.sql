-- DB PhoToLearn: 01_creazione_tabelle

DROP DATABASE IF EXISTS `PhoToLearn`;

CREATE DATABASE `PhoToLearn`;

USE `PhoToLearn`;

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema PhoToLearn
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema PhoToLearn
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `PhoToLearn` DEFAULT CHARACTER SET utf8 ;
USE `PhoToLearn` ;

-- -----------------------------------------------------
-- Table `PhoToLearn`.`Credenziali`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `PhoToLearn`.`Credenziali` (
  `NomeUtente` VARCHAR(20) NOT NULL,
  `Password` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`NomeUtente`),
  
  CONSTRAINT chk_Credenziali_NomeUtente
    CHECK (CHAR_LENGTH(TRIM(NomeUtente)) > 0),
  CONSTRAINT chk_Credenziali_Password
    CHECK (CHAR_LENGTH(TRIM(Password)) > 0))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `PhoToLearn`.`FotografoAmatoriale`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `PhoToLearn`.`FotografoAmatoriale` (
  `Email` VARCHAR(254) NOT NULL,
  `Nome` VARCHAR(20) NOT NULL,
  `Cognome` VARCHAR(20) NOT NULL,
  `DataNascita` DATE NOT NULL,
  `NomeUtente` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`Email`),
  INDEX `fk_FotografoAmatoriale_Credenziali1_idx` (`NomeUtente` ASC) VISIBLE,
  UNIQUE INDEX `NomeUtente_UNIQUE` (`NomeUtente` ASC) VISIBLE,
  CONSTRAINT `fk_FotografoAmatoriale_Credenziali1`
    FOREIGN KEY (`NomeUtente`)
    REFERENCES `PhoToLearn`.`Credenziali` (`NomeUtente`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    
  CONSTRAINT chk_FotAm_Email
    CHECK (CHAR_LENGTH(TRIM(Email)) > 0))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `PhoToLearn`.`FotografoProfessionista`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `PhoToLearn`.`FotografoProfessionista` (
  `Email` VARCHAR(254) NOT NULL,
  `Nome` VARCHAR(20) NOT NULL,
  `Cognome` VARCHAR(20) NOT NULL,
  `DataNascita` DATE NOT NULL,
  `DescrizioneProfilo` VARCHAR(250) NULL,
  `NomeUtente` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`Email`),
  INDEX `fk_FotografoProfessionista_Credenziali1_idx` (`NomeUtente` ASC) VISIBLE,
  UNIQUE INDEX `NomeUtente_UNIQUE` (`NomeUtente` ASC) VISIBLE,
  CONSTRAINT `fk_FotografoProfessionista_Credenziali1`
    FOREIGN KEY (`NomeUtente`)
    REFERENCES `PhoToLearn`.`Credenziali` (`NomeUtente`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    
  CONSTRAINT chk_FotPro_Email
    CHECK (CHAR_LENGTH(TRIM(Email)) > 0))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `PhoToLearn`.`Tematica`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `PhoToLearn`.`Tematica` (
  `NomeTematica` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`NomeTematica`),
  
  CONSTRAINT chk_Tematica_Nome
    CHECK (CHAR_LENGTH(TRIM(NomeTematica)) > 0))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `PhoToLearn`.`Tratta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `PhoToLearn`.`Tratta` (
  `Tematica` VARCHAR(100) NOT NULL,
  `FotografoProfessionista` VARCHAR(254) NOT NULL,
  PRIMARY KEY (`Tematica`, `FotografoProfessionista`),
  INDEX `fk_Tratta_Tematica1_idx` (`Tematica` ASC) VISIBLE,
  INDEX `fk_Tratta_FotografoProfessionista1_idx` (`FotografoProfessionista` ASC) VISIBLE,
  CONSTRAINT `fk_Tratta_Tematica1`
    FOREIGN KEY (`Tematica`)
    REFERENCES `PhoToLearn`.`Tematica` (`NomeTematica`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Tratta_FotografoProfessionista1`
    FOREIGN KEY (`FotografoProfessionista`)
    REFERENCES `PhoToLearn`.`FotografoProfessionista` (`Email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `PhoToLearn`.`Fotografia`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `PhoToLearn`.`Fotografia` (
  `IdFotografia` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `FileFotografia` MEDIUMBLOB NOT NULL,
  `Data` DATE NULL,
  `Orario` TIME NULL,
  `Luogo` VARCHAR(100) NULL,
  `Latitudine` DECIMAL(10,8) NULL,
  `Longitudine` DECIMAL(11,8) NULL,
  `ModelloFotocamera` VARCHAR(100) NULL,
  `ModelloObiettivo` VARCHAR(100) NULL,
  `LunghezzaFocale` DECIMAL(6,2) NULL,
  `Diaframma` DECIMAL(4,1) NULL,
  `TempoPosa` VARCHAR(30) NULL,
  `Iso` INT UNSIGNED NULL,
  `ModoMessaFuoco` VARCHAR(30) NULL,
  `ModoAreaAF` VARCHAR(30) NULL,
  `DescrizioneFoto` VARCHAR(250) NULL,
  `DescrizionePostFoto` VARCHAR(250) NULL,
  `FotografoProfessionista` VARCHAR(254) NOT NULL,
  PRIMARY KEY (`IdFotografia`),
  INDEX `fk_Fotografia_FotografoProfessionista1_idx` (`FotografoProfessionista` ASC) VISIBLE,
  CONSTRAINT `fk_Fotografia_FotografoProfessionista1`
    FOREIGN KEY (`FotografoProfessionista`)
    REFERENCES `PhoToLearn`.`FotografoProfessionista` (`Email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    
  CONSTRAINT chk_Fotografia_Latitudine
    CHECK (Latitudine IS NULL OR Latitudine BETWEEN -90 AND 90),
  CONSTRAINT chk_Fotografia_Longitudine
    CHECK (Longitudine IS NULL OR Longitudine BETWEEN -180 AND 180),
  CONSTRAINT chk_Fotografia_Coordinate
    CHECK (
        (Latitudine IS NULL AND Longitudine IS NULL)
        OR
        (Latitudine IS NOT NULL AND Longitudine IS NOT NULL)
    ),
  CONSTRAINT chk_Fotografia_LunghezzaFocale
    CHECK (LunghezzaFocale IS NULL OR LunghezzaFocale > 0),
  CONSTRAINT chk_Fotografia_Diaframma
    CHECK (Diaframma IS NULL OR Diaframma > 0),
  CONSTRAINT chk_Fotografia_Iso
    CHECK (Iso IS NULL OR Iso > 0),
  CONSTRAINT chk_Fotografia_File
    CHECK (OCTET_LENGTH(FileFotografia) > 0))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `PhoToLearn`.`RadunoFotografico`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `PhoToLearn`.`RadunoFotografico` (
  `idRadunoFotografico` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `Luogo` VARCHAR(100) NOT NULL,
  `Latitudine` DECIMAL(10,8) NOT NULL,
  `Longitudine` DECIMAL(11,8) NOT NULL,
  `Data` DATE NOT NULL,
  `OrarioInizio` TIME NOT NULL,
  `DescrizioneRadunoFotografico` VARCHAR(250) NULL,
  `Tematica` VARCHAR(100) NOT NULL,
  `Organizzatore` VARCHAR(254) NOT NULL,
  PRIMARY KEY (`idRadunoFotografico`),
  INDEX `fk_RadunoFotografico_Tematica1_idx` (`Tematica` ASC) VISIBLE,
  INDEX `fk_RadunoFotografico_FotografoProfessionista1_idx` (`Organizzatore` ASC) VISIBLE,
  CONSTRAINT `fk_RadunoFotografico_Tematica1`
    FOREIGN KEY (`Tematica`)
    REFERENCES `PhoToLearn`.`Tematica` (`NomeTematica`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_RadunoFotografico_FotografoProfessionista1`
    FOREIGN KEY (`Organizzatore`)
    REFERENCES `PhoToLearn`.`FotografoProfessionista` (`Email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    
  CONSTRAINT chk_Raduno_Latitudine
    CHECK (Latitudine BETWEEN -90 AND 90),
  CONSTRAINT chk_Raduno_Longitudine
    CHECK (Longitudine BETWEEN -180 AND 180),
  CONSTRAINT chk_Raduno_Orario
    CHECK (
        OrarioInizio >= MAKETIME(0, 0, 0)
        AND OrarioInizio <= MAKETIME(23, 59, 59)
    ),
    CONSTRAINT chk_Raduno_Luogo
    CHECK (CHAR_LENGTH(TRIM(Luogo)) > 0))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `PhoToLearn`.`Richiesta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `PhoToLearn`.`Richiesta` (
  `IdRichiesta` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `Contenuto` VARCHAR(250) NOT NULL,
  `DataInvio` DATE NOT NULL,
  `OrarioInvio` TIME NOT NULL,
  `Stato` ENUM('pending', 'satisfied', 'rejected') NOT NULL,
  `FotografiaDiscussa` INT UNSIGNED NOT NULL,
  `Mittente` VARCHAR(254) NOT NULL,
  `Destinatario` VARCHAR(254) NOT NULL,
  PRIMARY KEY (`IdRichiesta`),
  INDEX `fk_Richiesta_Fotografia1_idx` (`FotografiaDiscussa` ASC) VISIBLE,
  INDEX `fk_Richiesta_FotografoAmatoriale1_idx` (`Mittente` ASC) VISIBLE,
  INDEX `fk_Richiesta_FotografoProfessionista1_idx` (`Destinatario` ASC) VISIBLE,
  CONSTRAINT `fk_Richiesta_Fotografia1`
    FOREIGN KEY (`FotografiaDiscussa`)
    REFERENCES `PhoToLearn`.`Fotografia` (`IdFotografia`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Richiesta_FotografoAmatoriale1`
    FOREIGN KEY (`Mittente`)
    REFERENCES `PhoToLearn`.`FotografoAmatoriale` (`Email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Richiesta_FotografoProfessionista1`
    FOREIGN KEY (`Destinatario`)
    REFERENCES `PhoToLearn`.`FotografoProfessionista` (`Email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    
  CONSTRAINT chk_Richiesta_Contenuto
    CHECK (CHAR_LENGTH(TRIM(Contenuto)) > 0),
  CONSTRAINT chk_Richiesta_Orario
    CHECK (
        OrarioInvio >= MAKETIME(0, 0, 0)
        AND OrarioInvio <= MAKETIME(23, 59, 59)
    ))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `PhoToLearn`.`Risposta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `PhoToLearn`.`Risposta` (
  `IdRisposta` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `Contenuto` VARCHAR(250) NOT NULL,
  `DataInvio` DATE NOT NULL,
  `OrarioInvio` TIME NOT NULL,
  `Mittente` VARCHAR(254) NOT NULL,
  `Destinatario` VARCHAR(254) NOT NULL,
  `RichiestaAssociata` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`IdRisposta`),
  INDEX `fk_Risposta_FotografoProfessionista1_idx` (`Mittente` ASC) VISIBLE,
  INDEX `fk_Risposta_FotografoAmatoriale1_idx` (`Destinatario` ASC) VISIBLE,
  INDEX `fk_Risposta_Richiesta1_idx` (`RichiestaAssociata` ASC) VISIBLE,
  UNIQUE INDEX `RichiestaAssociata_UNIQUE` (`RichiestaAssociata` ASC) VISIBLE,
  CONSTRAINT `fk_Risposta_FotografoProfessionista1`
    FOREIGN KEY (`Mittente`)
    REFERENCES `PhoToLearn`.`FotografoProfessionista` (`Email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Risposta_FotografoAmatoriale1`
    FOREIGN KEY (`Destinatario`)
    REFERENCES `PhoToLearn`.`FotografoAmatoriale` (`Email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Risposta_Richiesta1`
    FOREIGN KEY (`RichiestaAssociata`)
    REFERENCES `PhoToLearn`.`Richiesta` (`IdRichiesta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    
  CONSTRAINT chk_Risposta_Contenuto
    CHECK (CHAR_LENGTH(TRIM(Contenuto)) > 0),
  CONSTRAINT chk_Risposta_Orario
    CHECK (
        OrarioInvio >= MAKETIME(0, 0, 0)
        AND OrarioInvio <= MAKETIME(23, 59, 59)
    ))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `PhoToLearn`.`ConNotificheAttive`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `PhoToLearn`.`ConNotificheAttive` (
  `FotografoAmatoriale` VARCHAR(254) NOT NULL,
  `FotografoProfessionista` VARCHAR(254) NOT NULL,
  PRIMARY KEY (`FotografoAmatoriale`, `FotografoProfessionista`),
  INDEX `fk_ConNotificheAttive_FotografoProfessionista1_idx` (`FotografoProfessionista` ASC) VISIBLE,
  CONSTRAINT `fk_ConNotificheAttive_FotografoAmatoriale1`
    FOREIGN KEY (`FotografoAmatoriale`)
    REFERENCES `PhoToLearn`.`FotografoAmatoriale` (`Email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ConNotificheAttive_FotografoProfessionista1`
    FOREIGN KEY (`FotografoProfessionista`)
    REFERENCES `PhoToLearn`.`FotografoProfessionista` (`Email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `PhoToLearn`.`Partecipa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `PhoToLearn`.`Partecipa` (
  `Partecipante` VARCHAR(254) NOT NULL,
  `RadunoFotografico` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`Partecipante`, `RadunoFotografico`),
  INDEX `fk_Partecipa_RadunoFotografico1_idx` (`RadunoFotografico` ASC) VISIBLE,
  CONSTRAINT `fk_Partecipa_FotografoAmatoriale1`
    FOREIGN KEY (`Partecipante`)
    REFERENCES `PhoToLearn`.`FotografoAmatoriale` (`Email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Partecipa_RadunoFotografico1`
    FOREIGN KEY (`RadunoFotografico`)
    REFERENCES `PhoToLearn`.`RadunoFotografico` (`idRadunoFotografico`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
