CREATE DATABASE  IF NOT EXISTS `tankbattle` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `tankbattle`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: localhost    Database: tankbattle
-- ------------------------------------------------------
-- Server version	5.6.21

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
-- Table structure for table `partita`
--

DROP TABLE IF EXISTS `partita`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `partita` (
  `idPartita` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `score` int(11) DEFAULT NULL,
  `dataPartita` date DEFAULT NULL,
  PRIMARY KEY (`idPartita`)
) ENGINE=InnoDB AUTO_INCREMENT=306 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `partita`
--

LOCK TABLES `partita` WRITE;
/*!40000 ALTER TABLE `partita` DISABLE KEYS */;
INSERT INTO `partita` VALUES (60,'dark_warrior',11,'2020-01-30'),(61,'aixel15',8,'2020-01-30'),(62,'aixel15',3,'2020-01-30'),(63,'gerti98',18,'2020-01-30'),(64,'jack56',22,'2020-01-30'),(65,'jack56',1,'2020-01-30'),(66,'dark_warrior',2,'2020-01-30'),(67,'optimus',7,'2020-01-30'),(68,'heavyrain88',1,'2020-01-30'),(69,'heavyrain88',13,'2020-01-30'),(70,'paoly75',14,'2020-01-30'),(71,'paoly75',15,'2020-01-30'),(72,'eriksen55',2,'2020-01-30'),(73,'eriksen55',3,'2020-01-30'),(74,'gerti98',0,'2020-01-30'),(75,'gerti98',11,'2020-01-30'),(76,'dark_warrior',13,'2020-01-30'),(77,'aixel15',14,'2020-01-30'),(78,'gerti98',26,'2020-01-20'),(79,'gerti98',4,'2020-01-30'),(80,'gerti98',3,'2020-01-30'),(81,'gerti98',1,'2020-01-30'),(82,'gerti98',0,'2020-01-30'),(83,'gerti98',0,'2020-01-30'),(84,'gerti98',1,'2020-01-30'),(85,'gerti98',1,'2020-01-30'),(86,'gerti98',0,'2020-01-30'),(87,'gerti98',0,'2020-01-30'),(88,'gerti98',3,'2020-01-30'),(89,'gerti98',13,'2020-01-30'),(90,'gerti98',4,'2020-01-30'),(91,'gerti98',6,'2020-01-30'),(92,'gerti98',13,'2020-01-30'),(93,'gerti98',4,'2020-01-30'),(94,'gerti98',15,'2020-01-30'),(95,'gerti98',4,'2020-01-30'),(96,'gerti98',3,'2020-01-30'),(97,'gerti98',5,'2020-01-30'),(98,'gerti98',8,'2020-01-30'),(99,'ciao',7,'2020-01-30'),(100,'ciao',9,'2020-01-30'),(101,'ciao',10,'2020-01-30'),(102,'ciao',0,'2020-01-30'),(103,'ciao',0,'2020-01-30'),(104,'ciao',2,'2020-01-30'),(105,'ciao',13,'2020-01-30'),(106,'ciao',8,'2020-01-30'),(107,'ciao',0,'2020-01-30'),(108,'paoly75',11,'2020-01-30'),(109,'paoly75',1,'2020-01-30'),(110,'paoly75',4,'2020-01-30'),(111,'paoly75',0,'2020-01-30'),(112,'paoly75',3,'2020-01-30'),(113,'paoly75',3,'2020-01-30'),(114,'paoly75',2,'2020-01-30'),(115,'paoly75',6,'2020-01-30'),(116,'paoly75',15,'2020-01-30'),(117,'paoly75',7,'2020-01-30'),(118,'paoly75',4,'2020-01-30'),(119,'paoly75',10,'2020-01-30'),(120,'paoly75',7,'2020-01-30'),(121,'paoly75',2,'2020-01-30'),(122,'paoly75',0,'2020-01-30'),(123,'paoly75',0,'2020-01-30'),(124,'paoly75',8,'2020-01-30'),(125,'paoly75',3,'2020-01-30'),(126,'paoly75',17,'2020-01-30'),(127,'paoly75',0,'2020-01-30'),(128,'paoly75',0,'2020-01-30'),(129,'paoly75',2,'2020-01-30'),(130,'paoly75',0,'2020-01-29'),(131,'paoly75',0,'2020-01-30'),(132,'paoly75',13,'2020-01-30'),(133,'paoly75',5,'2020-01-30'),(134,'paoly75',39,'2020-01-30'),(135,'paoly75',0,'2020-01-30'),(136,'paoly75',0,'2020-01-30'),(137,'paoly75',1,'2020-01-30'),(138,'paoly75',0,'2020-01-30'),(139,'paoly75',1,'2020-01-30'),(140,'paoly75',0,'2020-01-30'),(141,'paoly75',6,'2020-01-30'),(142,'paoly75',2,'2020-01-30'),(143,'paoly75',3,'2020-01-30'),(144,'paoly75',6,'2020-01-30'),(145,'paoly75',0,'2020-01-30'),(146,'paoly75',9,'2020-01-30'),(147,'paoly75',4,'2020-01-30'),(300,'darkwarrior888',4,'2020-02-05'),(301,'darkwarrior888',1,'2020-02-05'),(302,'darkwarrior888',3,'2020-02-05'),(303,'darkwarrior888',1,'2020-02-05'),(304,'darkwarrior888',1,'2020-02-05'),(305,'darkwarrior888',5,'2020-02-05');
/*!40000 ALTER TABLE `partita` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-02-05 21:59:08
