CREATE DATABASE  IF NOT EXISTS `bd_tarea4_ejercicio2` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `bd_tarea4_ejercicio2`;
-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: bd_tarea4_ejercicio2
-- ------------------------------------------------------
-- Server version	8.0.22

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tcuenta`
--

DROP TABLE IF EXISTS `tcuenta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tcuenta` (
  `id` int NOT NULL AUTO_INCREMENT,
  `numeroCuenta` varchar(7) NOT NULL,
  `tipoCuenta` varchar(45) NOT NULL,
  `fechaApertura` date NOT NULL,
  `saldo` decimal(10,2) NOT NULL,
  `tasaIntereses` decimal(4,4) NOT NULL,
  `idDuenno` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_duennoCuenta_idx` (`idDuenno`),
  CONSTRAINT `fk_duennoCuenta` FOREIGN KEY (`idDuenno`) REFERENCES `tcliente` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tcuenta`
--

LOCK TABLES `tcuenta` WRITE;
/*!40000 ALTER TABLE `tcuenta` DISABLE KEYS */;
INSERT INTO `tcuenta` VALUES (5,'1234567','CORRIENTE','2020-12-06',0.00,0.1400,2),(6,'1234568','CORRIENTE','2020-12-06',50000.00,0.1400,2),(7,'1234570','AHORRO','2020-12-06',45000.00,0.1400,3);
/*!40000 ALTER TABLE `tcuenta` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-12-06 21:58:28
