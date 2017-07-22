-- MySQL dump 10.13  Distrib 5.7.13, for Linux (x86_64)
--
-- Host: localhost    Database: iptv_comm
-- ------------------------------------------------------
-- Server version	5.7.13-0ubuntu0.16.04.2

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `general_description`
--

DROP TABLE IF EXISTS `general_description`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `general_description` (
  `description_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `internal_description` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`description_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `general_description`
--

LOCK TABLES `general_description` WRITE;
/*!40000 ALTER TABLE `general_description` DISABLE KEYS */;
INSERT INTO `general_description` VALUES (1,'Discovery channel');
/*!40000 ALTER TABLE `general_description` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `general_name`
--

DROP TABLE IF EXISTS `general_name`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `general_name` (
  `name_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `internal_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`name_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `general_name`
--

LOCK TABLES `general_name` WRITE;
/*!40000 ALTER TABLE `general_name` DISABLE KEYS */;
INSERT INTO `general_name` VALUES (1,'Discovery'),(2,'NatGeoWild');
/*!40000 ALTER TABLE `general_name` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `language`
--

DROP TABLE IF EXISTS `language`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `language` (
  `language_id` int(11) NOT NULL AUTO_INCREMENT,
  `language_iso_code` varchar(5) NOT NULL,
  `language_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`language_id`),
  UNIQUE KEY `language_iso_code_UNIQUE` (`language_iso_code`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `language`
--

LOCK TABLES `language` WRITE;
/*!40000 ALTER TABLE `language` DISABLE KEYS */;
INSERT INTO `language` VALUES (1,'code','english language');
/*!40000 ALTER TABLE `language` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `localized_description`
--

DROP TABLE IF EXISTS `localized_description`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `localized_description` (
  `description_id` bigint(20) NOT NULL,
  `language_id` int(11) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`description_id`),
  KEY `fk_localized_description_2_idx` (`language_id`),
  CONSTRAINT `fk_localized_description_1` FOREIGN KEY (`description_id`) REFERENCES `general_description` (`description_id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_localized_description_2` FOREIGN KEY (`language_id`) REFERENCES `language` (`language_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `localized_description`
--

LOCK TABLES `localized_description` WRITE;
/*!40000 ALTER TABLE `localized_description` DISABLE KEYS */;
INSERT INTO `localized_description` VALUES (1,1,'English');
/*!40000 ALTER TABLE `localized_description` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `localized_name`
--

DROP TABLE IF EXISTS `localized_name`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `localized_name` (
  `name_id` bigint(20) NOT NULL,
  `language_id` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`name_id`,`language_id`),
  KEY `fk_localized_name_2_idx` (`language_id`),
  CONSTRAINT `fk_localized_name_1` FOREIGN KEY (`name_id`) REFERENCES `general_name` (`name_id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_localized_name_2` FOREIGN KEY (`language_id`) REFERENCES `language` (`language_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `localized_name`
--

LOCK TABLES `localized_name` WRITE;
/*!40000 ALTER TABLE `localized_name` DISABLE KEYS */;
INSERT INTO `localized_name` VALUES (1,1,'Descover'),(2,1,'Armenia');
/*!40000 ALTER TABLE `localized_name` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message` (
  `message_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sender` bigint(20) DEFAULT NULL,
  `receiver` bigint(20) DEFAULT NULL,
  `message_content_id` bigint(20) DEFAULT NULL,
  `parent_message_id` bigint(20) DEFAULT NULL,
  `tv_channel_id` bigint(20) DEFAULT NULL,
  `tv_program_id` bigint(20) DEFAULT NULL,
  `send_dt` datetime DEFAULT NULL,
  `receive_dt` datetime DEFAULT NULL,
  PRIMARY KEY (`message_id`),
  KEY `fk_message_1_idx` (`sender`),
  KEY `fk_message_2_idx` (`receiver`),
  KEY `fk_message_3_idx` (`message_content_id`),
  KEY `fk_message_4_idx` (`parent_message_id`),
  CONSTRAINT `fk_message_1` FOREIGN KEY (`sender`) REFERENCES `user` (`user_id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_message_2` FOREIGN KEY (`receiver`) REFERENCES `user` (`user_id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_message_3` FOREIGN KEY (`message_content_id`) REFERENCES `message_content` (`message_content_id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_message_4` FOREIGN KEY (`parent_message_id`) REFERENCES `message` (`message_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES (8,23,NULL,9,NULL,1,1,'2016-08-05 12:54:07',NULL);
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message_content`
--

DROP TABLE IF EXISTS `message_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message_content` (
  `message_content_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `message_type_id` int(11) DEFAULT NULL,
  `subject` varchar(45) DEFAULT NULL,
  `body` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`message_content_id`),
  KEY `fk_message_content_1_idx` (`message_type_id`),
  CONSTRAINT `fk_message_content_1` FOREIGN KEY (`message_type_id`) REFERENCES `message_type` (`message_type_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message_content`
--

LOCK TABLES `message_content` WRITE;
/*!40000 ALTER TABLE `message_content` DISABLE KEYS */;
INSERT INTO `message_content` VALUES (1,1,'privet','Hello'),(2,1,'barev','fffff'),(3,1,'barev','fbdfbnnbdfn'),(4,1,'barev','fbdfbnnbdfn'),(5,1,'barev',' vonseq'),(6,1,NULL,'Kakashka'),(7,1,'barev','fbdfbnnbdfn'),(8,2,NULL,'Hello'),(9,3,NULL,'nayeq'),(10,3,NULL,'nayum enq exbayr jan'),(11,1,'uggytgfg',' hghg ujghuj'),(12,2,NULL,'hhtrhbebe'),(13,1,'alo',' Serojh'),(14,3,NULL,'Gavnoprogram'),(15,3,NULL,'Gavnoprogram');
/*!40000 ALTER TABLE `message_content` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message_type`
--

DROP TABLE IF EXISTS `message_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message_type` (
  `message_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `message_type_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`message_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message_type`
--

LOCK TABLES `message_type` WRITE;
/*!40000 ALTER TABLE `message_type` DISABLE KEYS */;
INSERT INTO `message_type` VALUES (1,'SMS'),(2,'Sugestion'),(3,'Comment');
/*!40000 ALTER TABLE `message_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tv_channel`
--

DROP TABLE IF EXISTS `tv_channel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tv_channel` (
  `tv_channel_id` int(11) NOT NULL AUTO_INCREMENT,
  `provider_id` int(11) DEFAULT NULL,
  `channel_number` int(10) unsigned DEFAULT NULL,
  `channel_name` bigint(20) DEFAULT NULL,
  `channel_descr` bigint(20) DEFAULT NULL,
  `channel_url` varchar(200) DEFAULT NULL,
  `channel_logo` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`tv_channel_id`),
  KEY `fk_tv_channel_1_idx` (`provider_id`),
  KEY `fk_tv_channel_2_idx` (`channel_name`),
  KEY `fk_tv_channel_3_idx` (`channel_descr`),
  CONSTRAINT `fk_tv_channel_1` FOREIGN KEY (`provider_id`) REFERENCES `tv_provider` (`tv_provider_id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_tv_channel_2` FOREIGN KEY (`channel_name`) REFERENCES `general_name` (`name_id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_tv_channel_3` FOREIGN KEY (`channel_descr`) REFERENCES `general_description` (`description_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tv_channel`
--

LOCK TABLES `tv_channel` WRITE;
/*!40000 ALTER TABLE `tv_channel` DISABLE KEYS */;
INSERT INTO `tv_channel` VALUES (5,1,1,1,1,'asas','http://foto-t-v.narod.ru/olderfiles/1/Pervyi_kanal.jpg');
/*!40000 ALTER TABLE `tv_channel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tv_program`
--

DROP TABLE IF EXISTS `tv_program`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tv_program` (
  `tv_program_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tv_channel_id` int(11) DEFAULT NULL,
  `tv_program_name` bigint(20) DEFAULT NULL,
  `tv_program_descr` bigint(20) DEFAULT NULL,
  `start_dt` datetime DEFAULT NULL,
  `duration_in_minutes` int(11) DEFAULT NULL,
  PRIMARY KEY (`tv_program_id`),
  KEY `fk_tv_program_1_idx` (`tv_program_name`),
  KEY `fk_tv_program_3_idx` (`tv_program_descr`),
  CONSTRAINT `fk_tv_program_1` FOREIGN KEY (`tv_program_name`) REFERENCES `general_name` (`name_id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_tv_program_2` FOREIGN KEY (`tv_program_name`) REFERENCES `general_name` (`name_id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_tv_program_3` FOREIGN KEY (`tv_program_descr`) REFERENCES `general_description` (`description_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tv_program`
--

LOCK TABLES `tv_program` WRITE;
/*!40000 ALTER TABLE `tv_program` DISABLE KEYS */;
INSERT INTO `tv_program` VALUES (2,1,1,1,'2016-07-20 13:00:00',60);
/*!40000 ALTER TABLE `tv_program` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tv_provider`
--

DROP TABLE IF EXISTS `tv_provider`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tv_provider` (
  `tv_provider_id` int(11) NOT NULL AUTO_INCREMENT,
  `company_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`tv_provider_id`),
  UNIQUE KEY `company_name` (`company_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tv_provider`
--

LOCK TABLES `tv_provider` WRITE;
/*!40000 ALTER TABLE `tv_provider` DISABLE KEYS */;
INSERT INTO `tv_provider` VALUES (2,'MagicTV5'),(3,'Rostelecom'),(1,'UCom');
/*!40000 ALTER TABLE `tv_provider` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(10) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `dob` datetime DEFAULT NULL,
  `avatar` varchar(200) DEFAULT NULL,
  `token` varchar(50) DEFAULT NULL,
  `last_active` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (23,'2','2',NULL,'fjrjrtj','Serob','2016-08-04 00:00:00',NULL,NULL,NULL),(27,'3','3',NULL,NULL,NULL,NULL,NULL,'3EUkiiidoMBXWNvnjyKZKdZYDRKLYZrODrfEokyyqBjlWvDPof','2016-08-16 18:40:00'),(28,'misha','1',NULL,NULL,NULL,NULL,NULL,'mishabImhNRjBTmXwOAccufNYlhbRafGJTeThHsgjGjqLcdWcy','2016-08-18 12:03:34'),(29,'Vasya','23','poshyol otsyuda','Vasyok','Petrov',NULL,NULL,NULL,NULL),(31,'degenik','degenik','degenik@ya.ru','degenik','degenik',NULL,NULL,'degenikowwDagJjGYABwrUxfZeePsmgyJudKFerGIxCQYLnybQ','2016-08-26 12:55:58'),(32,'Mish','999','yerximnoc@gmail.com','Misha','Maghakyan',NULL,NULL,'MishHoHxWqzHdgGaKFKKNMEZzMidaAwUpkpjadmoiZQWnenKrq','2016-08-23 12:55:11'),(33,'Asya','53','jaha@.eu','Asya','Arzumanyan',NULL,NULL,NULL,NULL),(34,'kvasyok','kvas','vasik@tvasik.kvasik','Vasya','Vasyan',NULL,NULL,'kvasyokphUynKWYUPOwWfYBUjzUTFEUppSlRNylrviZDDjMbVE','2016-08-23 13:09:08'),(35,'Nika','nike','nik.am','Niko','Nikyan',NULL,NULL,'NikaeHEUojQHgKnlgrQGzwKRJBldhngSRYdHBccIMqeImWRbrM','2016-08-23 13:11:38'),(38,'j','j','','','',NULL,NULL,NULL,NULL),(43,'fd','f','f','f','f',NULL,NULL,NULL,NULL),(44,'qwerty','','q','fq','fq',NULL,NULL,NULL,NULL),(53,'m','m','m','m','m',NULL,NULL,NULL,NULL),(56,'e','e','e','e','e',NULL,NULL,NULL,NULL),(57,'pop','pop',NULL,NULL,NULL,NULL,NULL,'popOVtSiIYgkeSFTgOuePjVGwJGTcJZhgrcMjcBfGVnxFdOhWm','2016-08-26 12:27:20'),(58,'lalala','lalalal',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(59,'wefwe1','qdwd1',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(60,'rgfd',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(63,'dfagr','dfagr','dfagr','dfagr','dfagr',NULL,NULL,NULL,NULL),(64,'wefwef','dfagryt','fvwefw','wefw','dfagrywef',NULL,NULL,NULL,NULL),(68,'rth','rth','rth','hrt','rth',NULL,NULL,NULL,NULL),(69,'gbbg','gbbg','gbbg','gbbg','gbbg',NULL,NULL,NULL,NULL),(71,'wefwefw','wefwefwef','wefwef','wefwef','wefwef',NULL,NULL,NULL,NULL),(72,'fgbfgb','fgb','fgbfgbfgb','fgbfgb','fgbfgbfg',NULL,NULL,NULL,NULL),(73,'weefw','erg','wefvwe','erew','werwfew',NULL,NULL,NULL,NULL),(74,'ghgh','gh','','','',NULL,NULL,NULL,NULL),(75,'tyjtyjtyj','gh','tyjtyjtyjt','thtyjtyj','tyjtyjtyj',NULL,NULL,NULL,NULL),(76,'try','rty','','','',NULL,NULL,NULL,NULL),(77,'po','po','po','po','po',NULL,NULL,NULL,NULL),(78,'pot','t','pot','pot','pot',NULL,NULL,NULL,NULL),(79,'wegwer','wefggvbwe','webwer','rtwe','wefggb',NULL,NULL,NULL,NULL),(81,'hj','hj','','','',NULL,NULL,'hjBqPOAwEzpNFZfeviFbUqMuUekqGKfEPBxQHXrjaynbACuBqv','2016-08-23 17:53:31'),(82,'xcv','xcv','','','',NULL,NULL,'xcvXDyazDWEBbWPNrKibIBaqbNXGlZjZkxKtyJdDJXwSPMbAvh','2016-08-23 17:54:37'),(83,'ouighoi','hiohoih','','iuwefbgiu','iougou',NULL,NULL,'ouighoitwsVuyqmDVJiImkFUCMbqMywVWzjYgpHEaJOZQDLZzx','2016-08-23 17:55:17'),(84,'78','78','78','78','78',NULL,NULL,NULL,NULL),(85,'123','123','123','123','123',NULL,NULL,NULL,NULL),(86,'qwef','','55','55','55',NULL,NULL,'55VyhhcFynhavhBlzTxtlSGUiAOVUOtjiCZFqFPIvPycnIuQjj','2016-08-25 18:50:44'),(87,'89','89','89','89','89',NULL,NULL,'89yKkjoFktmAwTQKfkuKSpWkMWbyfDbuxbGvVCzdFRkDWMjVjs','2016-08-24 11:37:09'),(88,'73','73','73','73','73',NULL,NULL,'73dHWimhLPYNiLqRxEGbsBcqwuKvnvOVwefTSKBFStpPmkqVaR','2016-08-24 11:39:00'),(89,'666','666','666','666','666',NULL,NULL,NULL,NULL),(90,'46','46','46','46','46',NULL,NULL,NULL,NULL),(91,'258','258','258','258','258',NULL,NULL,'258TLTRQmDfZghCkQhllqmkrBdClQlWLccpsuTmuCCgWHnMvfB','2016-08-24 12:10:31'),(92,'05','06','04','02','03',NULL,NULL,NULL,NULL),(93,'456','456','456','456','456',NULL,NULL,NULL,NULL),(94,'csd','csd','csd','csd','cds',NULL,NULL,'csdPHLtGuAaKJxciKQWDYSdoiyeygrQVnVDhQgnuIbkSukxbvQ','2016-08-24 15:16:34'),(95,'popjm','wqd','','cb','',NULL,NULL,'popjmfvJCUlWgoePZZZXQgIyQpeUxORBXrhBaAbyCaSyvBtqUk','2016-08-25 15:17:41'),(96,'as','as','as','as','as',NULL,NULL,'asvoMIPcfepCwzCRZbaLbWmEgRePTFlikzyMOtsAztgJpnbHTA','2016-08-25 16:52:27'),(97,'iii','iii','iii','iii','iii',NULL,NULL,'iiiAEALIWYJghOXBNZHidNziciIOjSWwxUzNpeMCDkIZRcmznU','2016-08-25 16:56:53'),(98,'dhdhd','dhdfhd','hgdhd','ghhg','hgfdh',NULL,NULL,NULL,NULL),(99,'pixik','petros','poghos@z.ru','pux','afadfasf',NULL,NULL,'pixikDfZVDagJyXQjHAskkljuyBtuGyXMqtjyLxoNnSiqXzQzR','2016-08-26 14:45:52');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_assoc`
--

DROP TABLE IF EXISTS `user_assoc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_assoc` (
  `assoc_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `object_user_id` bigint(20) NOT NULL,
  `subject_user_id` bigint(20) DEFAULT NULL,
  `assoc_type_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`assoc_id`),
  KEY `fk_friendship_1_idx` (`object_user_id`),
  KEY `fk_friendship_2_idx` (`subject_user_id`),
  KEY `fk_user_assoc_1_idx` (`assoc_type_id`),
  CONSTRAINT `fk_friendship_1` FOREIGN KEY (`object_user_id`) REFERENCES `user` (`user_id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_friendship_2` FOREIGN KEY (`subject_user_id`) REFERENCES `user` (`user_id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_user_assoc_1` FOREIGN KEY (`assoc_type_id`) REFERENCES `user_assoc_type` (`user_assoc_type_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_assoc`
--

LOCK TABLES `user_assoc` WRITE;
/*!40000 ALTER TABLE `user_assoc` DISABLE KEYS */;
INSERT INTO `user_assoc` VALUES (16,28,23,2);
/*!40000 ALTER TABLE `user_assoc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_assoc_type`
--

DROP TABLE IF EXISTS `user_assoc_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_assoc_type` (
  `user_assoc_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `assoc_type_name` varchar(20) DEFAULT NULL,
  `assoc_type_descr` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`user_assoc_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_assoc_type`
--

LOCK TABLES `user_assoc_type` WRITE;
/*!40000 ALTER TABLE `user_assoc_type` DISABLE KEYS */;
INSERT INTO `user_assoc_type` VALUES (1,'sent,no answer','object_user sent friend request to the subject_user'),(2,'friends','object_user and subject_user are friends'),(3,'declined','subject_user declined object_user\'s friend request');
/*!40000 ALTER TABLE `user_assoc_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_tv_provider`
--

DROP TABLE IF EXISTS `user_tv_provider`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_tv_provider` (
  `user_id` bigint(20) NOT NULL,
  `tv_provider_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`tv_provider_id`),
  KEY `fk_user_tv_provider_2_idx` (`tv_provider_id`),
  CONSTRAINT `fk_user_tv_provider_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_tv_provider_2` FOREIGN KEY (`tv_provider_id`) REFERENCES `tv_provider` (`tv_provider_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_tv_provider`
--

LOCK TABLES `user_tv_provider` WRITE;
/*!40000 ALTER TABLE `user_tv_provider` DISABLE KEYS */;
INSERT INTO `user_tv_provider` VALUES (23,1);
/*!40000 ALTER TABLE `user_tv_provider` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-08-26 18:02:06
