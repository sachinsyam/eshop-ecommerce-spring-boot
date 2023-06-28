-- MySQL dump 10.13  Distrib 8.0.33, for Linux (x86_64)
--
-- Host: localhost    Database: ecomm
-- ------------------------------------------------------
-- Server version	8.0.33-0ubuntu0.22.04.2

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `SPRING_SESSION`
--

DROP TABLE IF EXISTS `SPRING_SESSION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `SPRING_SESSION` (
  `PRIMARY_ID` char(36) NOT NULL,
  `SESSION_ID` char(36) NOT NULL,
  `CREATION_TIME` bigint NOT NULL,
  `LAST_ACCESS_TIME` bigint NOT NULL,
  `MAX_INACTIVE_INTERVAL` int NOT NULL,
  `EXPIRY_TIME` bigint NOT NULL,
  `PRINCIPAL_NAME` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`PRIMARY_ID`),
  UNIQUE KEY `SPRING_SESSION_IX1` (`SESSION_ID`),
  KEY `SPRING_SESSION_IX2` (`EXPIRY_TIME`),
  KEY `SPRING_SESSION_IX3` (`PRINCIPAL_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SPRING_SESSION`
--

LOCK TABLES `SPRING_SESSION` WRITE;
/*!40000 ALTER TABLE `SPRING_SESSION` DISABLE KEYS */;
INSERT INTO `SPRING_SESSION` VALUES ('3e4cfe17-7f1b-4d98-83d7-c43fd03cdaf7','49a68f85-3d06-4b99-8ae7-35248d9cdca5',1687875538865,1687875539791,1800,1687877339791,NULL);
/*!40000 ALTER TABLE `SPRING_SESSION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SPRING_SESSION_ATTRIBUTES`
--

DROP TABLE IF EXISTS `SPRING_SESSION_ATTRIBUTES`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `SPRING_SESSION_ATTRIBUTES` (
  `SESSION_PRIMARY_ID` char(36) NOT NULL,
  `ATTRIBUTE_NAME` varchar(200) NOT NULL,
  `ATTRIBUTE_BYTES` blob NOT NULL,
  PRIMARY KEY (`SESSION_PRIMARY_ID`,`ATTRIBUTE_NAME`),
  CONSTRAINT `SPRING_SESSION_ATTRIBUTES_FK` FOREIGN KEY (`SESSION_PRIMARY_ID`) REFERENCES `SPRING_SESSION` (`PRIMARY_ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SPRING_SESSION_ATTRIBUTES`
--

LOCK TABLES `SPRING_SESSION_ATTRIBUTES` WRITE;
/*!40000 ALTER TABLE `SPRING_SESSION_ATTRIBUTES` DISABLE KEYS */;
/*!40000 ALTER TABLE `SPRING_SESSION_ATTRIBUTES` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `audit_log`
--

DROP TABLE IF EXISTS `audit_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `audit_log` (
  `uuid` varchar(255) NOT NULL,
  `ip_address` varchar(255) DEFAULT NULL,
  `login_timestamp` datetime(6) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `deleted_by` binary(255) DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  `session_id` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `asn` varchar(255) DEFAULT NULL,
  `network` varchar(255) DEFAULT NULL,
  `organization` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  KEY `FK1i8nxbwi9dwitwm7o7epjleee` (`user_id`),
  CONSTRAINT `FK1i8nxbwi9dwitwm7o7epjleee` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audit_log`
--

LOCK TABLES `audit_log` WRITE;
/*!40000 ALTER TABLE `audit_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `audit_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `banner`
--

DROP TABLE IF EXISTS `banner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `banner` (
  `uuid` varchar(255) NOT NULL,
  `banner_file` varchar(255) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `product_id` varchar(255) DEFAULT NULL,
  `description1` varchar(255) DEFAULT NULL,
  `description2` varchar(255) DEFAULT NULL,
  `description3` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `deleted_by` binary(255) DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  KEY `FKlh871y5v0koigokacbud30bdi` (`product_id`),
  CONSTRAINT `FKlh871y5v0koigokacbud30bdi` FOREIGN KEY (`product_id`) REFERENCES `product` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `banner`
--

LOCK TABLES `banner` WRITE;
/*!40000 ALTER TABLE `banner` DISABLE KEYS */;
INSERT INTO `banner` VALUES ('845707a5-e637-4699-82fa-f74e3fe6818c','dfc1a0fd-60a0-4a86-88d2-683dc98781fe-banner4.jpg',_binary '','Sale','98250d35-fac9-43a3-9866-f160ecf4801d','End of Season Sale','2023 Lenovo Laptops','Order Now',NULL,_binary '\0',NULL,NULL,NULL),('c692945b-b227-4231-ba61-b3fc7a573cda','6f85c6c1-98e4-486b-a216-7c99d845ebd5-pexels-tima-miroshnichenko-5662847.jpg',_binary '','Nikon','d5438c62-e1f1-41fc-b6fb-2db3bf66c515','Nikon Z6 10% OFF','','Order Now!',NULL,_binary '\0',NULL,NULL,'2023-06-10 23:20:05.540000'),('f7118c13-694b-422c-9532-3d788d0234f9','3678ab34-bf17-4c22-817c-deb2a93ec994-pexels-pixabay-434346.jpg',_binary '','Mac Offer','3c1792bf-32dd-4d30-bd68-5a7ec817a968','Lowest Price of the Year!','Limited Stock!','Order Now!',NULL,_binary '\0',NULL,NULL,'2023-06-10 23:18:39.450000');
/*!40000 ALTER TABLE `banner` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `brand`
--

DROP TABLE IF EXISTS `brand`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `brand` (
  `uuid` varchar(255) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `website` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `deleted_by` binary(255) DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brand`
--

LOCK TABLES `brand` WRITE;
/*!40000 ALTER TABLE `brand` DISABLE KEYS */;
/*!40000 ALTER TABLE `brand` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `uuid` varchar(255) NOT NULL,
  `quantity` int NOT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `variant_id` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `deleted_by` binary(255) DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  KEY `FK3oexs31qtfpym0v38fc3o951i` (`user_id`),
  KEY `FKiooeaakvn6wca6h281k8m881g` (`variant_id`),
  CONSTRAINT `FK3oexs31qtfpym0v38fc3o951i` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`uuid`),
  CONSTRAINT `FKiooeaakvn6wca6h281k8m881g` FOREIGN KEY (`variant_id`) REFERENCES `variant` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `uuid` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `deleted_by` binary(255) DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `UK_46ccwnsi9409t36lurvtyljak` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES ('1280e445-3782-49bb-8137-b7f1e47caeef','Andoird & Apple','Phones',NULL,_binary '\0',NULL,NULL,'2023-06-09 12:32:41.137000'),('2141828a-f9a0-4054-887c-5c1eec40485c','DSLR, Mirrorless & Action','Cameras',NULL,_binary '\0',NULL,NULL,'2023-06-09 12:33:16.747000'),('7db03c36-a17b-4c5f-8e5b-cd721b350621','OLED, LED & LCD','TV',NULL,_binary '\0',NULL,NULL,'2023-06-09 12:33:39.157000'),('a2e9dce1-7af3-461f-a475-4d9643d1e5f5','Andoird & Apple','Tablets',NULL,_binary '\0',NULL,NULL,'2023-06-09 12:33:53.120000'),('c01f111a-29c2-4c4a-8f5a-2461ddf31428','Intel, AMD & Apple','Laptops',NULL,_binary '\0',NULL,NULL,'2023-06-09 12:34:16.999000'),('c115be17-806d-4cb9-a9b4-9e69de52fe71','Home Theater & Portable','Audio',NULL,_binary '\0',NULL,NULL,'2023-06-09 12:34:30.251000'),('dd940c42-b5ed-411e-bfa3-c0751b4e10f9','Gaming Accessories','Gaming',NULL,_binary '\0',NULL,NULL,'2023-06-09 12:34:57.503000'),('eafe34c9-03cd-4f46-941a-10aaa3fec8ad','Electronic Accessories','Accessories',NULL,_binary '\0',NULL,NULL,'2023-06-09 12:35:12.295000');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coupon`
--

DROP TABLE IF EXISTS `coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coupon` (
  `uuid` varchar(255) NOT NULL,
  `applicable_for` binary(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `count` int NOT NULL,
  `coupon_type` int NOT NULL,
  `enabled` bit(1) NOT NULL,
  `expiry_date` date DEFAULT NULL,
  `max_off` int NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `off_percentage` int NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `deleted_by` binary(255) DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coupon`
--

LOCK TABLES `coupon` WRITE;
/*!40000 ALTER TABLE `coupon` DISABLE KEYS */;
INSERT INTO `coupon` VALUES ('1db7495e-df4e-4391-b30a-4cac28ea9a69',_binary '<’¿2\ÝM0½hZ~\È©h\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0','MACRAZY85',1000,1,_binary '','2023-08-18',100000,'MACBOOK 85% OFF upto 100000',85,'2023-06-16 18:44:45.363000',_binary '\0',NULL,NULL,'2023-06-16 18:44:45.363000'),('2c4ae169-b4cd-42cd-bc87-c2ef002e67d5',_binary '!A‚Šù @Tˆ|\\\ì@H\\\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0','CAM65FEST',1000,2,_binary '','2023-10-20',50000,'Cameras 65% OFF upto 50000',65,'2023-06-16 18:43:42.499000',_binary '\0',NULL,NULL,'2023-06-16 18:43:42.500000'),('2fec49f4-5f00-4782-8499-57ce363fb03c',NULL,'5OFF',1000,0,_binary '\0','2023-12-07',500,'NEW5',5,NULL,_binary '\0',NULL,NULL,NULL),('58d67e40-cfd2-4660-bc16-fbd4a24a764c',_binary '!A‚Šù @Tˆ|\\\ì@H\\\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0','Quos tempora accusam',87,2,_binary '\0','2001-09-09',15,'Hope Long',50,'2023-06-16 14:06:41.724000',_binary '','2023-06-16 17:15:55.683000',_binary '\õ4\Û=dI\õ§O\è¤éŸ¶\÷\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0','2023-06-16 17:15:55.684000'),('6e6cb042-9d34-4d3e-a391-4b7166a4d2bf',_binary '€\äE7‚I»7·\ñ\ä|®\ï\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0','PHONE50',1000,2,_binary '','2023-09-14',25000,'Phones 50% Off upto 25000',50,'2023-06-16 18:41:53.053000',_binary '\0',NULL,NULL,'2023-06-16 18:41:53.057000'),('7be30b71-66ee-4c69-9c8b-35e929548201',_binary 'À\Z)\ÂLJZ$a\Ý\ó(\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0','LAP50OFF',999,2,_binary '','2023-09-01',50000,'Laptops 55% Off Upto 50000',55,'2023-06-16 18:42:28.156000',_binary '\0',NULL,NULL,'2023-06-16 18:50:29.919000'),('8f9dec84-e24d-47db-8df7-dad25c1529cd',NULL,'Voluptatem quibusdam',45,5,_binary '\0','2005-05-19',56,'Bert Barr',83,'2023-06-16 14:06:27.112000',_binary '','2023-06-16 17:15:53.107000',_binary '\õ4\Û=dI\õ§O\è¤éŸ¶\÷\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0','2023-06-16 17:15:53.108000'),('9681c166-57e8-435e-88a2-19603d4cf08e',_binary '\í_\×ø\â\ßEŒ•.S^U,¡\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0','REALME10',999,1,_binary '','2023-09-21',10000,'REALME 25% Off upto 10000',25,'2023-06-16 18:46:35.034000',_binary '\0',NULL,NULL,'2023-06-16 19:35:37.342000'),('9a776e31-f90d-46c5-abcc-2f9ab748981d',NULL,'APPLE15',26,0,_binary '','2023-05-31',15000,'Apple Offer',15,NULL,_binary '\0',NULL,NULL,NULL),('9c94dffc-a7ff-4a0a-b877-6b609724229c',NULL,'FREE90',998,4,_binary '','2023-08-31',1500000,'End of Season Sale. 90% OFF upto 150000 on all Products!',90,'2023-06-04 17:46:39.616000',_binary '\0',NULL,NULL,'2023-06-16 20:01:05.983000'),('abdef49c-1601-44c0-a908-61ec33a16b2d',NULL,'wew',0,0,_binary '\0','2023-05-26',4,'test',2,NULL,_binary '','2023-06-16 22:00:30.255000',_binary '\õ4\Û=dI\õ§O\è¤éŸ¶\÷\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0','2023-06-16 22:00:30.275000'),('bf3ece4a-0eee-40b9-90d1-9d24118ab31b',NULL,'25OFF',43,0,_binary '\0','2024-01-03',2500,'Galena Weiss',25,'2023-06-16 14:09:15.253000',_binary '','2023-06-16 18:40:33.091000',_binary '\õ4\Û=dI\õ§O\è¤éŸ¶\÷\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0','2023-06-16 18:40:33.092000'),('e78fb10d-eb34-4ac0-9180-86ec6a941531',_binary '(³Þ’C“ƒ‚g­c˜\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0','AUDIO30',100,1,_binary '','2024-08-23',4000,'30% Off upto 4000',25,'2023-06-16 17:19:27.580000',_binary '\0','2023-06-16 18:40:46.479000',_binary '\õ4\Û=dI\õ§O\è¤éŸ¶\÷\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0','2023-06-16 18:40:46.480000'),('e99f23bc-149f-425d-bad8-ecb9836458f1',NULL,'123',124,0,_binary '','2023-06-23',123,'123',123,'2023-06-02 15:09:44.822000',_binary '',NULL,NULL,'2023-06-16 13:20:20.921000');
/*!40000 ALTER TABLE `coupon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `image`
--

DROP TABLE IF EXISTS `image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `image` (
  `uuid` varchar(255) NOT NULL,
  `file_name` varchar(255) DEFAULT NULL,
  `product_id` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `deleted_by` binary(255) DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  KEY `FKgpextbyee3uk9u6o2381m7ft1` (`product_id`),
  CONSTRAINT `FKgpextbyee3uk9u6o2381m7ft1` FOREIGN KEY (`product_id`) REFERENCES `product` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `image`
--

LOCK TABLES `image` WRITE;
/*!40000 ALTER TABLE `image` DISABLE KEYS */;
INSERT INTO `image` VALUES ('0950b90a-6d62-48ca-8110-5f88440ce712','47610c74-cf40-4da1-aa3c-99c0d2cf5d31-71fYfa6l8SL._SL1500_.jpg','98250d35-fac9-43a3-9866-f160ecf4801d',NULL,_binary '\0',NULL,NULL,NULL),('09cedfb6-7a68-4a23-aa0d-1e58fcdeec9b','f46d841b-4edc-4f1b-945d-27331d32b2d3-51UOJo4hhkL._SY450_.jpg','3920eaa1-4560-4069-93ef-731df9604272',NULL,_binary '\0',NULL,NULL,NULL),('115c7e4a-8f2c-4fdc-9b8d-8c8d39e0efba','56784ea3-51d4-4efd-859b-2c70aa4dd76a-2e.webp','bc9b9ddc-03a6-4b4a-8561-42f4f3844f64','2023-06-09 14:05:04.016000',_binary '\0',NULL,NULL,'2023-06-09 14:05:04.017000'),('13850c16-8381-4788-aeb1-72c3c82f300e','3e7c5e94-a688-4b51-bc38-510012e61a3e-71z452bvrIL._SL1500_.jpg','04c664db-a15c-418a-9e12-0f0b179a83d0',NULL,_binary '\0',NULL,NULL,NULL),('140e3dd6-de69-4625-b47b-4cdcc1aa4bb1','91998aa3-d8e9-4060-af7e-5ddadf754df8-81-fFXQdPTL._SL1500_.jpg','77564ad4-59ae-4f57-9add-9fabd51e67e7',NULL,_binary '\0',NULL,NULL,NULL),('28abf5f7-3115-481b-939f-f38f59dd90c4','70abce99-ee4a-4523-996f-61ce528b8a74-91oUeawTcsL._SX679_.jpg','d5438c62-e1f1-41fc-b6fb-2db3bf66c515',NULL,_binary '\0',NULL,NULL,NULL),('32ffe33b-b75c-43c0-ab6c-4e4eb6e30cb7','b00f9ae5-26b2-4382-a752-b735b8ff81ae-71ZACtD0-oL._SL1500_.jpg','6e423d06-b255-46b4-88bc-18f78de7dbe0',NULL,_binary '\0',NULL,NULL,NULL),('33195a39-b845-471c-95f7-1e4270144e7a','458e2733-bb91-4114-800c-903a835bf422-51tBNIuUY7L._SL1500_.jpg','77564ad4-59ae-4f57-9add-9fabd51e67e7',NULL,_binary '\0',NULL,NULL,NULL),('37157f6f-3324-44a2-a7a7-9d759a8421a7','e5b172e3-84f0-4f40-b905-7ca2908ed31a-71Xq2PQIT1L._SL1500_.jpg','e2be5700-9c3d-4227-89e7-3dc8b9c36f8d',NULL,_binary '\0',NULL,NULL,NULL),('38f8bc44-f080-4cee-8bc2-2dca9075e893','8d15af01-a7f5-49ac-a135-511ad20f4089-718nVK4vxPL._SL1500_.jpg','04c664db-a15c-418a-9e12-0f0b179a83d0',NULL,_binary '\0',NULL,NULL,NULL),('4ea8d23f-0aa7-4da1-8c2f-f5d5378d0261','2b53e7e3-42f5-49a1-87af-fe249777f010-417wIj9y83L._SY450_.jpg','3920eaa1-4560-4069-93ef-731df9604272',NULL,_binary '\0',NULL,NULL,NULL),('583b62bf-2086-4e74-ad8a-85ee7068b658','47c5e300-d496-4d51-8e23-bb2c3efb2c12-81wxS8abrgL._SL1500_.jpg','734ac55c-dbb4-43f5-bfd9-0e2133313bb5',NULL,_binary '\0',NULL,NULL,NULL),('5a4c5ac8-cf30-47a4-8495-dcd0047644ee','57e02f1d-45bb-485e-bf4f-06c7c580e862-1.jpg','0e28b304-de92-4393-8382-678dad631598',NULL,_binary '\0',NULL,NULL,NULL),('5a7b75a0-084a-4ec2-864a-af9d2c97a820','d497d618-fada-42b7-ac36-f12ee56245d2-51rBj3KF-ZL._SL1500_.jpg','98250d35-fac9-43a3-9866-f160ecf4801d',NULL,_binary '\0',NULL,NULL,NULL),('609d2be0-7f5d-4078-971d-bac5f1cb696b','0b39bb2e-1a61-45df-9228-0422e21e7601-71AN8x5irQL._SL1500_.jpg','6e423d06-b255-46b4-88bc-18f78de7dbe0',NULL,_binary '\0',NULL,NULL,NULL),('61644847-a21b-4a59-92f7-ddc24b501310','c499beea-4f78-49fb-af80-de9ec41a1311-81CSWdv0mAL._SL1500_.jpg','d5438c62-e1f1-41fc-b6fb-2db3bf66c515',NULL,_binary '\0',NULL,NULL,NULL),('626b4de3-9779-4a54-9b60-478c19ea1e46','0abb4769-01d9-491f-9a40-0d283cfe5351-71f5Eu5lJSL._SL1500_.jpg','3c1792bf-32dd-4d30-bd68-5a7ec817a968',NULL,_binary '\0',NULL,NULL,NULL),('6307f9cf-5946-4be4-b885-3eec83d37fda','051e4478-e5d3-426d-abd4-9b5d5511fc93-61Li1i6AaXL._SL1500_.jpg','6e423d06-b255-46b4-88bc-18f78de7dbe0',NULL,_binary '\0',NULL,NULL,NULL),('664230c1-aaa5-459f-90db-3cd9a7b66d66','3ebb0766-39b4-41c5-9a1f-c5db740ed343-3.jpg','0e28b304-de92-4393-8382-678dad631598',NULL,_binary '\0',NULL,NULL,NULL),('6b63f87a-c667-402c-9a45-524c16dc27c8','c3fea742-733c-4c67-9c79-f87d22364c61-1e.webp','bc9b9ddc-03a6-4b4a-8561-42f4f3844f64','2023-06-09 14:04:57.609000',_binary '\0',NULL,NULL,'2023-06-09 14:04:57.609000'),('6c2db759-ebb3-4f1a-a5d2-f612a5ddb8f1','6b642df1-9d8c-4de5-830b-d9e3372c2f07-81UmTnrBDSL._SL1500_.jpg','669415e9-1b44-4f38-be08-fa31c162f1cf',NULL,_binary '\0',NULL,NULL,NULL),('6e350004-8828-4d37-95d7-161ff86abad4','9a8cac4d-ab8a-4c45-be65-479b65202024-41XukzvDg6L._SY450_.jpg','3920eaa1-4560-4069-93ef-731df9604272',NULL,_binary '\0',NULL,NULL,NULL),('712e4577-6284-4c1c-8fac-58db77f06efd','3e6724ac-5612-41bf-85b3-32e98d42bed6-81v5JNjZ4-L._SL1500_.jpg','3c1792bf-32dd-4d30-bd68-5a7ec817a968',NULL,_binary '\0',NULL,NULL,NULL),('77e2f638-43c9-43b4-b50a-bad72092e3ec','5c82f12a-b812-4a8f-877e-d3eefb50484c-81DXnElMfkL._SL1500_.jpg','e2be5700-9c3d-4227-89e7-3dc8b9c36f8d',NULL,_binary '\0',NULL,NULL,NULL),('7e7e8035-6f27-47a3-9449-b90f7f0e9cc7','16a808d8-4bc2-4b80-8bab-dcc850beb851-71B7sRIydwL._SL1500_.jpg','e9c371d1-296e-4d15-a6e8-2cdf4839e081',NULL,_binary '\0',NULL,NULL,NULL),('8c6879d5-d7ef-4436-8688-b972596b8503','8dc958aa-6b6a-49fb-84e1-ee2f80e764e9-81mawyKuJgL._SL1500_.jpg','e2be5700-9c3d-4227-89e7-3dc8b9c36f8d',NULL,_binary '\0',NULL,NULL,NULL),('8cb9949f-a859-4cd1-8be6-63ff640b55f3','c7ed0d28-deac-4145-bffb-6fd0feb733c6-51j-IJ-lSsL._SL1500_.jpg','6e423d06-b255-46b4-88bc-18f78de7dbe0',NULL,_binary '\0',NULL,NULL,NULL),('8e16f668-55bd-4652-8bdb-503195cf4088','a37d31d7-1e39-45e7-a8c6-8a6c279de888-81Np0yq-KFL._SL1500_.jpg','e9c371d1-296e-4d15-a6e8-2cdf4839e081',NULL,_binary '\0',NULL,NULL,NULL),('8e8319ce-9259-43b0-a614-d23aac06d44c','cb01f6ac-0ca6-4c7d-a7cd-c24a7910c3e7-71vtbZCZdAL._SL1500_.jpg','e2be5700-9c3d-4227-89e7-3dc8b9c36f8d',NULL,_binary '\0',NULL,NULL,NULL),('9252453d-0232-4f3a-a2e5-a8ee8600ac77','8e68a00c-7cd7-4035-9900-8589fcadb340-91pyswFh31L._SL1500_.jpg','b40c3f3f-5f66-461e-b2db-e0ec8a692f58',NULL,_binary '\0',NULL,NULL,NULL),('94e4e3a6-d129-4941-909a-c1348e7fc0c1','e0015717-f6d2-4251-820e-adda63dba1a4-2.jpg','0e28b304-de92-4393-8382-678dad631598',NULL,_binary '\0',NULL,NULL,NULL),('985f8271-5493-4230-b19b-13d971c57e3d','b9a223a5-a466-4c5a-aeb3-b8eca29adbed-61PSM1GsOFL._SL1500_.jpg','77564ad4-59ae-4f57-9add-9fabd51e67e7',NULL,_binary '\0',NULL,NULL,NULL),('995680b6-7cfd-436b-9e46-9fb5ef44900a','95a4904e-1b3d-4a6e-9837-318525ec22b6-71z2fFXZ8XL._SL1500_.jpg','04c664db-a15c-418a-9e12-0f0b179a83d0',NULL,_binary '\0',NULL,NULL,NULL),('9aff6824-a992-4b29-aa8c-b56b6cbec024','79b83285-7db3-40c1-ac38-2df096c0d38b-71Ftzmh3XWL._SL1500_.jpg','ed5fd7f8-e2df-458c-952e-90535e552ca1',NULL,_binary '\0',NULL,NULL,NULL),('9c6b2603-3db5-4c4a-87b8-c03defdfce33','7956defd-679a-4522-98a2-fccd5ad88e73-81kCLhvLM4L._SY879_.jpg','d5438c62-e1f1-41fc-b6fb-2db3bf66c515',NULL,_binary '\0',NULL,NULL,NULL),('9c8a3970-9739-4517-9a7a-e5804cc6bae4','7c804950-f1c0-45c5-a2ee-7580cfb23918-71wgU0winfL._SL1500_.jpg','e2be5700-9c3d-4227-89e7-3dc8b9c36f8d',NULL,_binary '\0',NULL,NULL,NULL),('a3f70db2-a737-4184-bbb5-7d9a10ee023d','97dc7a59-a7ab-4872-b513-6cb4881762c2-61iSbOfEsQL._SL1500_.jpg','98250d35-fac9-43a3-9866-f160ecf4801d',NULL,_binary '\0',NULL,NULL,NULL),('a4612a0f-f240-47d1-85f7-14c99300c1ed','6b74ff4f-38d3-48e7-b8b0-39cea5316947-4.jpg','0e28b304-de92-4393-8382-678dad631598',NULL,_binary '\0',NULL,NULL,NULL),('a858b13e-0545-47ad-8b79-5ef2d6e63148','3013065f-bc6f-44a9-bd78-fbc0ab4091aa-61QcrPWqvNL._SL1500_.jpg','6e423d06-b255-46b4-88bc-18f78de7dbe0',NULL,_binary '\0',NULL,NULL,NULL),('ab893ad2-b03f-425e-bc82-e4e41c2f4dbe','531da76f-a52c-4e60-9514-88c1760ee935-61XPhYGQOQL._SL1500_.jpg','3c1792bf-32dd-4d30-bd68-5a7ec817a968',NULL,_binary '\0',NULL,NULL,NULL),('b2cf0287-3888-4822-9730-956d549c4b00','1ccd72bc-894a-4efd-8ff0-41f8a375dea7-51p-vAu49aL._SL1500_.jpg','6e423d06-b255-46b4-88bc-18f78de7dbe0',NULL,_binary '\0',NULL,NULL,NULL),('b491b24c-947d-4e1f-8d7d-c487307b9026','63ef9e94-2e77-4401-81a6-7b77bc6fb1d2-41mMSinFp7L._SY450_.jpg','3920eaa1-4560-4069-93ef-731df9604272',NULL,_binary '\0',NULL,NULL,NULL),('c0743937-de03-4e5c-89c7-0cbd6bad332f','8dcdd1bb-877c-4972-ae8e-982c4c515668-81h4sLxAesL._SL1500_.jpg','734ac55c-dbb4-43f5-bfd9-0e2133313bb5',NULL,_binary '\0',NULL,NULL,NULL),('cff6e2cf-4437-408b-a7f2-f6f5e7b7f8a4','901b0e5c-9982-4178-8f3b-1c3752724a84-41yF+1f1S8L._SL1500_.jpg','669415e9-1b44-4f38-be08-fa31c162f1cf',NULL,_binary '\0',NULL,NULL,NULL),('d498cf5b-31b6-4ccc-99b5-46374aaf031c','4a507ad1-5eff-4986-92f9-745b45cb7972-71P+-2u8tpL._SL1500_.jpg','b40c3f3f-5f66-461e-b2db-e0ec8a692f58',NULL,_binary '\0',NULL,NULL,NULL),('d65f5af3-9697-4077-a191-9124920ee54d','1a46ceec-1e58-4906-a6e3-ff3528326e25-61+85vZqI-L._SL1500_.jpg','3c1792bf-32dd-4d30-bd68-5a7ec817a968',NULL,_binary '\0',NULL,NULL,NULL),('d9470eb5-5866-4bae-bf23-1748f7628508','c1d176ad-af6a-4500-b7e8-4cd8b045a739-81XIWeZComL._SL1500_.jpg','04c664db-a15c-418a-9e12-0f0b179a83d0',NULL,_binary '\0',NULL,NULL,NULL),('e788ab17-f02e-4377-aae0-4f683a681607','a08144ea-c353-49ed-b563-ff0566c93892-61c5LcysbIL._SL1500_.jpg','669415e9-1b44-4f38-be08-fa31c162f1cf',NULL,_binary '\0',NULL,NULL,NULL),('e79f0157-5d1a-444b-a39e-7184bd28aeef','a5983370-0a31-4e80-b46f-b9a7a6a2b206-414oId5agWL._SL1500_.jpg','669415e9-1b44-4f38-be08-fa31c162f1cf',NULL,_binary '\0',NULL,NULL,NULL),('e89edd73-8273-4e73-b055-5561beda119c','ed727eba-a3c2-4606-bfe0-ceb1f95ae941-715Pe4fJO2L._SL1500_.jpg','734ac55c-dbb4-43f5-bfd9-0e2133313bb5',NULL,_binary '\0',NULL,NULL,NULL),('ea5451b7-866e-44b0-bf21-acdcc80b2df0','aad4e47e-d225-4b63-8cb3-d0103794b42b-81SjldAN4ZL._SL1500_.jpg','3c1792bf-32dd-4d30-bd68-5a7ec817a968',NULL,_binary '\0',NULL,NULL,NULL),('ed16c828-b4a2-44e7-951d-6537ec0d3fb9','5d1764d8-a301-4b06-8a60-9cafd1e390e1-81jWlvWJFLL._SL1500_.jpg','734ac55c-dbb4-43f5-bfd9-0e2133313bb5',NULL,_binary '\0',NULL,NULL,NULL),('ed5eb946-4323-4960-b822-c9a0b88c56a8','b8af940b-21ec-48a1-8213-14f87d297457-61gMBqZrkAL._SL1500_.jpg','669415e9-1b44-4f38-be08-fa31c162f1cf',NULL,_binary '\0',NULL,NULL,NULL),('f79d498b-317e-490f-aa60-d44395a4c278','f7771506-3949-4f0e-b9c0-92cc08651618-71H6mqexb8L._SL1500_.jpg','ed5fd7f8-e2df-458c-952e-90535e552ca1',NULL,_binary '\0',NULL,NULL,NULL),('fba53316-3e09-4010-a9f1-64334def987b','9f2b01aa-a1b2-4907-809d-b11a4106d943-61og60CnGlL._SL1500_.jpg','ed5fd7f8-e2df-458c-952e-90535e552ca1',NULL,_binary '\0',NULL,NULL,NULL),('fdf1ba93-a221-4f59-93e6-b0451682c7f4','b4040b1a-58a9-4636-bb60-c0092e46f933-81tDwe9R1PL._SL1500_.jpg','734ac55c-dbb4-43f5-bfd9-0e2133313bb5',NULL,_binary '\0',NULL,NULL,NULL);
/*!40000 ALTER TABLE `image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `online_order_ref`
--

DROP TABLE IF EXISTS `online_order_ref`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `online_order_ref` (
  `uuid` varchar(255) NOT NULL,
  `payment_id` varchar(255) DEFAULT NULL,
  `razor_pay_order_id` varchar(255) DEFAULT NULL,
  `signature` varchar(255) DEFAULT NULL,
  `order_id` varchar(255) DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `status` bit(1) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `deleted_by` binary(255) DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  `error_code` varchar(255) DEFAULT NULL,
  `error_desc` varchar(255) DEFAULT NULL,
  `error_reason` varchar(255) DEFAULT NULL,
  `error_source` varchar(255) DEFAULT NULL,
  `error_step` varchar(255) DEFAULT NULL,
  `error_payment_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  KEY `FKb3b3fqewum8kgrsthtfl0dmqt` (`order_id`),
  CONSTRAINT `FKb3b3fqewum8kgrsthtfl0dmqt` FOREIGN KEY (`order_id`) REFERENCES `order_history` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `online_order_ref`
--

LOCK TABLES `online_order_ref` WRITE;
/*!40000 ALTER TABLE `online_order_ref` DISABLE KEYS */;
/*!40000 ALTER TABLE `online_order_ref` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_history`
--

DROP TABLE IF EXISTS `order_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_history` (
  `uuid` varchar(255) NOT NULL,
  `off_price` float DEFAULT NULL,
  `order_status` int DEFAULT NULL,
  `order_type` int DEFAULT NULL,
  `tax` float DEFAULT NULL,
  `total` float DEFAULT NULL,
  `coupon_id` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  `address_id` varchar(255) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `deleted_by` binary(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  KEY `FKrvvn3knjix1l5spexau1jwcih` (`coupon_id`),
  KEY `FKebt5nh0d50xnjqrmrlmdi5yb1` (`user_id`),
  KEY `FKa4hnljxmimpc41uglom9alty2` (`address_id`),
  CONSTRAINT `FKa4hnljxmimpc41uglom9alty2` FOREIGN KEY (`address_id`) REFERENCES `user_address` (`uuid`),
  CONSTRAINT `FKebt5nh0d50xnjqrmrlmdi5yb1` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`uuid`),
  CONSTRAINT `FKrvvn3knjix1l5spexau1jwcih` FOREIGN KEY (`coupon_id`) REFERENCES `coupon` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_history`
--

LOCK TABLES `order_history` WRITE;
/*!40000 ALTER TABLE `order_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_items`
--

DROP TABLE IF EXISTS `order_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_items` (
  `uuid` varchar(255) NOT NULL,
  `order_price` float NOT NULL,
  `quantity` int NOT NULL,
  `order_id` varchar(255) DEFAULT NULL,
  `variant_id` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `deleted_by` binary(255) DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  KEY `FKaukdf8u1nmdel9x9wt3r2a8dj` (`order_id`),
  KEY `FKqp1hpkosh4xx33fq14stv9l9n` (`variant_id`),
  CONSTRAINT `FKaukdf8u1nmdel9x9wt3r2a8dj` FOREIGN KEY (`order_id`) REFERENCES `order_history` (`uuid`),
  CONSTRAINT `FKqp1hpkosh4xx33fq14stv9l9n` FOREIGN KEY (`variant_id`) REFERENCES `variant` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_items`
--

LOCK TABLES `order_items` WRITE;
/*!40000 ALTER TABLE `order_items` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `otp`
--

DROP TABLE IF EXISTS `otp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `otp` (
  `uuid` varchar(255) NOT NULL,
  `otp` varchar(255) DEFAULT NULL,
  `otp_request_time` datetime(6) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  KEY `FKhdt5av2bkcc04x6n5o5o4ll5j` (`user_id`),
  CONSTRAINT `FKhdt5av2bkcc04x6n5o5o4ll5j` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `otp`
--

LOCK TABLES `otp` WRITE;
/*!40000 ALTER TABLE `otp` DISABLE KEYS */;
INSERT INTO `otp` VALUES ('552b679e-7646-4bdf-8bab-0897f9b329f9','450605','2023-06-27 19:39:12.881000','f50f34db-3d64-49f5-a74f-e8a4e99fb6f7'),('d403a7ae-5e7c-4c9e-9627-bea14acbf223','934700','2023-06-05 15:12:55.049000',NULL);
/*!40000 ALTER TABLE `otp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment_method`
--

DROP TABLE IF EXISTS `payment_method`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment_method` (
  `uuid` varchar(255) NOT NULL,
  `card_date` varchar(255) DEFAULT NULL,
  `card_name` varchar(255) DEFAULT NULL,
  `card_number` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  KEY `FK9ucd4h5hyj9htis3y7at6dnkl` (`user_id`),
  CONSTRAINT `FK9ucd4h5hyj9htis3y7at6dnkl` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment_method`
--

LOCK TABLES `payment_method` WRITE;
/*!40000 ALTER TABLE `payment_method` DISABLE KEYS */;
/*!40000 ALTER TABLE `payment_method` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `uuid` varchar(255) NOT NULL,
  `description` longtext,
  `enabled` bit(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `brand_id` varchar(255) DEFAULT NULL,
  `category_id` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `deleted_by` binary(255) DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  `average_rating` int NOT NULL,
  PRIMARY KEY (`uuid`),
  KEY `FKs6cydsualtsrprvlf2bb3lcam` (`brand_id`),
  KEY `FK1mtsbur82frn64de7balymq9s` (`category_id`),
  CONSTRAINT `FK1mtsbur82frn64de7balymq9s` FOREIGN KEY (`category_id`) REFERENCES `category` (`uuid`),
  CONSTRAINT `FKs6cydsualtsrprvlf2bb3lcam` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES ('04c664db-a15c-418a-9e12-0f0b179a83d0','61.0 MP 35 mm full-frame Exmor R CMOS sensor and BIONZ X image processing engine;Standard ISO 100â€“32000 range\r\nFast Hybrid AF with 567-point focal-plane phase-detection AF and 425-point contrast-detection AF\r\nHigh-speed continuous shooting of up to 10FPS27 with AF/AE tracking\r\nHardware Interface: Audio Video Port\r\nCompatible Mountings: Sony E mount',_binary '','Sony Alpha ILCE-7RM4A Camera',NULL,'2141828a-f9a0-4054-887c-5c1eec40485c',NULL,_binary '\0',NULL,NULL,'2023-06-09 19:20:25.033000',0),('0e28b304-de92-4393-8382-678dad631598','100W RMS DEEP BASS JBL ORIGINAL PRO SOUND: Experience the powerful 100Watt RMS sound output from the power packed JBL PartyBox Encore Essential. Nothing beats the amazing JBL Original Pro Sound with deep bass.\r\n\r\nDYNAMIC CUSTOMIZABLE STROBE EFFECT LIGHT SHOW: Change the lightings to your mood from the customizable dynamic light show with cool strobe effect that syncs to the beat of your music.\r\n\r\nCONTROL WITH JBL PARTYBOX APP: The JBL PartyBox app makes it easier than ever to control your music, update settings, and customize the colors and patterns of your light show of JBL PartyBox Encore Essential for the perfect party vibe.',_binary '','JBL Partybox',NULL,'c115be17-806d-4cb9-a9b4-9e69de52fe71','2023-06-03 13:25:23.727000',_binary '\0',NULL,NULL,'2023-06-23 09:40:33.283000',4),('3920eaa1-4560-4069-93ef-731df9604272','Approx. 45MP full-frame CMOS sensor\r\nUp to 8-stop In-Body Image Stabilizer X Optical Image Stabilizer\r\nUp to 20 fps + Animal Detection AF\r\n8K DCI movie + 4K DCI 120 fps video recording capability\r\nPhoto Sensor Technology: Size:[Value:Full Frame (35mm) ], Technology:[Value:Cmos ]; Compatible Mountings: Canon Rf',_binary '','Canon EOS R5 45MP Camera',NULL,'2141828a-f9a0-4054-887c-5c1eec40485c',NULL,_binary '\0',NULL,NULL,'2023-06-23 11:14:55.999000',5),('3c1792bf-32dd-4d30-bd68-5a7ec817a968','STRIKINGLY THIN DESIGN â€“ The redesigned MacBook Air is more portable than ever and weighs just 1.24 kg (2.7 pounds). Itâ€™s the ultra-capable laptop that lets you work, play or create just about anything â€” anywhere.\r\nSUPERCHARGED BY M2 â€“ Get more done faster with a next-generation 8-core CPU, up to 10-core GPU and up to 24GB of unified memory.\r\nUP TO 18 HOURS OF BATTERY LIFE â€“ Go all day and into the night, thanks to the power-efficient performance of the Apple M2 chip.\r\nBIG, BEAUTIFUL DISPLAY â€“ The 34.46-centimetre (13.6-inch) Liquid Retina display features over 500 nits of brightness, P3 wide colour and support for one billion colours for vibrant images and incredible detail.\r\nADVANCED CAMERA AND AUDIO â€“ Look sharp and sound great with a 1080p FaceTime HD camera, three-mic array and four-speaker sound system with Spatial Audio.\r\nVERSATILE CONNECTIVITY â€“ MacBook Air features a MagSafe charging port, two Thunderbolt ports and a headphone jack.\r\nEASY TO USE â€“ Your Mac feels familiar from the moment you turn it on and works seamlessly with all your Apple devices.',_binary '','Apple 2022 MacBook Air Laptop with M2',NULL,'c01f111a-29c2-4c4a-8f5a-2461ddf31428',NULL,_binary '\0',NULL,NULL,'2023-06-23 09:40:05.703000',3),('669415e9-1b44-4f38-be08-fa31c162f1cf','Display: 16.56cm HD+ Scratch resistant display\r\nProcessor: MediaTek Helio A22 processor; up to 2.0GHz\r\nCamera: 8MP Dual camera | 5MP Front camera\r\nMemory, Storage & SIM: 2GB LPDDR4x RAM | 32GB storage expandable up to 512GB with dedicated SD card slot | Dual SIM (nano+nano) dual standby (4G+4G)',_binary '','Redmi A1',NULL,'1280e445-3782-49bb-8137-b7f1e47caeef',NULL,_binary '\0',NULL,NULL,'2023-06-04 20:44:50.413000',0),('6e423d06-b255-46b4-88bc-18f78de7dbe0','The 5G enabled Dimensity 8100 SoC built on TSMCâ€™s advanced 5nm process technology has 25% better CPU power efficiency over previous Dimensity chips. Combined with LPDDR5 RAM and UFS 3.1 storage the device elevates the gaming experience to new heights. The device can maintain sustained peak performance owing to the better heat dissipation via a large vapor chamber cooling system with 7 layers of graphite\r\nThe 6.6\" FHD+ 144Hz Fluid, Dolby Vision display along with 7-stage dynamic refresh rate is capable of producing 1 billion colors. The display produces accurate, vibrant colors & is extremely smooth and responsive as well, keeping the users fully immersed in their gaming & multimedia. The device comes with a unique 20.5:9 aspect ratio, clean & sharp flat frame design making the device aesthetic and comfortable to hold even for a longer duration\r\nReimagine photography with 64MP ISOCELL primary sensor, 8MP Ultra-Wide, and 2MP Macro triple camera setup. The triple camera setup with the AI noise reduction algorithm helps capture stellar images effortlessly both during the day and night scenarios. The 4K video recording capability brings out movie-quality videos in a hassle-free manner\r\nThe 67W in-box turbocharger can charge the massive 5080mAh battery in a jiffy. The all-day battery helps your Redmi K50i 5G work non-stop and ensures you donâ€™t run out of power\r\nThe dual stereo speaker setup with support for Dolby Atmos brings out superior sound quality with rich & deep bass, accurate mids, and super crisp highs, providing for a consistent listening experience. The display and audio setup bring out an unparalleled experience keeping you engrossed in your games and multimedia',_binary '','Redmi K50i 5G',NULL,'1280e445-3782-49bb-8137-b7f1e47caeef',NULL,_binary '\0',NULL,NULL,'2023-06-10 20:55:18.107000',0),('734ac55c-dbb4-43f5-bfd9-0e2133313bb5','Resolution: 4K Ultra HD (3840 x 2160) | Refresh Rate: 60 Hertz | 178 Degree wide viewing angle\r\nConnectivity: 3 HDMI ports to connect set top box, Blu Ray players, gaming console | 2 USB ports to connect hard drives and other USB devices\r\nSound : 20 Watts Output | Open Baffle Speaker| Dolby Audio | Clear Phase\r\nSmart TV Features: Google TV | Watchlist | Voice Search | Google Play | Chromecast | Netflix | Amazon Prime Video | Additional Features: Apple Airplay | Apple Homekit |Alexa\r\nDisplay: X1 4K Processor | 4K HDR | Live Colour| 4K X Reality Pro | Motion Flow XR100\r\nWarranty Information: 1 year warranty provided by the manufacture from the date of purchase\r\nInstallation: Brand will contact for installation for this product once delivered. Contact Sony for assistance (Please visit brand website for toll free numbers) and provide product\'s model name and seller\'s details mentioned on your invoice. The service center will allot you a convenient slot for the service\r\nEasy returns: This product is eligible for replacement within 10 days of delivery in case of any product defects, damage or features not matching the description provided',_binary '','Sony Bravia 164 cm (65 inches) 4K Ultra HD Smart LED Google TV KD-65X74K (Black)',NULL,'7db03c36-a17b-4c5f-8e5b-cd721b350621',NULL,_binary '\0',NULL,NULL,'2023-06-05 23:48:17.313000',1),('77564ad4-59ae-4f57-9add-9fabd51e67e7','6000mAh lithium-ion battery, 1 year manufacturer warranty for device and 6 months manufacturer warranty for in-box accessories including batteries from the date of purchase\r\nUpto 12GB RAM with RAM Plus | 64GB internal memory expandable up to 1TB| Dual Sim (Nano)\r\n50MP+5MP+2MP Triple camera setup- True 50MP (F1.8) main camera +5MP(F2.2)+ 2MP (F2.4) | 8MP (F2.2) front cam\r\nAndroid 12,One UI Core 4 with a powerful Octa Core Processor\r\n16.72 centimeters (6.6-inch) FHD+ LCD - infinity O Display, FHD+ resolution with 1080 x 2408 pixels resolution, 401 PPI with 16M color',_binary '','Samsung Galaxy M13',NULL,'1280e445-3782-49bb-8137-b7f1e47caeef',NULL,_binary '\0',NULL,NULL,'2023-06-06 10:37:13.716000',0),('98250d35-fac9-43a3-9866-f160ecf4801d','Processor: 11th Gen Intel Core i7-11800H | Speed: 2.3 GHz (Base) - 4.6 GHz (Max) | 8 Cores | 16 Threads | 24MB Cache\r\nDisplay: 16\" QHD (2560x1600) Dolby Vision | Wide Quad with 16:10 Aspect Ratio| IPS Technology | 165 Hz Refresh Rate | 500Nits Brightness | 100% sRGB\r\nGraphics: NVIDIA GeForce RTX 3060 6GB GDDR6 Dedicated Graphics with max TGP 130W | Boost Clock 1425/1702MHz | Free-Sync and G-Sync || Memory and Storage: 32GB RAM DDR4 3200 | 1TB SSD\r\nAI Engine : Upto 15% more FPS and Best performance with Auto Detect Mode (Top 16 AAA Titles) and Auto Optimisation Mode | Q Control 4.0 to select between Quiet (Blue), Intelligent (White) and Performance (Red) Modes\r\nCooling: Legion Coldfront 3.0 with Quad Channel Exhaust System with Copper Fins || Battery Life: 80Wh | upto 6 hrs | Rapid Charge Pro (Up to 60% in 30 Minutes) | NVIDIA Advanced Optimus\r\nOS and Pre-Installed Softare: Pre-Loaded Windows 11 Home with Lifetime Validity | MS Office Home and Student 2021 | Xbox GamePass Ultimate 3-month subscription*\r\nAudio: 2x2W HD Stereo Speakers| Nahimic Audio with Surround Sound, Sound Tracker, Night Mode, Sound Sharing & Content Profiles || Camera: HD 720p with E-camera Shutter :Physical kill switch for 100% privacy protection| Integrated Dual Array Microphone',_binary '','Lenovo Legion5Pro Laptop',NULL,'c01f111a-29c2-4c4a-8f5a-2461ddf31428',NULL,_binary '\0',NULL,NULL,'2023-06-16 12:22:01.403000',4),('b40c3f3f-5f66-461e-b2db-e0ec8a692f58','Processor: 10th Gen Intel Core i5-10300H Processor, 2.5 GHz (8MB Cache, up to 4.5 GHz, 4 Cores, 8 Threads)\r\nAccess to over 100 high-quality PC games on Windows 11\r\nOne-month subscription to Xbox Game Pass thatâ€™s included with the purchase of your device\r\nOperating System: Pre-loaded Windows 11 Home with lifetime validity\r\nMemory & Storage: 8GB DDR4 2933MHz RAM, Support up to 32GB using 2x SO-DIMM Slot | Storage: 512GB M.2 NVMe PCIe 3.0 SSD with empty 1x 2.5-inch SATA Slot for Storage Expansion\r\nGraphics: Dedicated NVIDIA GeForce GTX 1650 GDDR6 4GB VRAM\r\nDisplay: 15.6-inch (16:9) LED-backlit FHD (1920x1080) 144Hz Refresh Rate, Anti-Glare IPS-level Panel with 45% NTSC',_binary '','ASUS TUF Gaming F15  Laptop',NULL,'c01f111a-29c2-4c4a-8f5a-2461ddf31428',NULL,_binary '\0',NULL,NULL,'2023-06-23 11:15:22.604000',2),('bc9b9ddc-03a6-4b4a-8561-42f4f3844f64','More light for your night - Get ready for a Gallery full of epic night shots everyone will want. Nightography\'s enhanced AI keeps details clear, so low light photos and videos will be bright and colorful from dusk to dawn and back again. ',_binary '','Samsung Galaxy S23 5G',NULL,'1280e445-3782-49bb-8137-b7f1e47caeef','2023-06-09 13:50:26.016000',_binary '\0',NULL,NULL,'2023-06-12 11:44:59.936000',4),('d5438c62-e1f1-41fc-b6fb-2db3bf66c515','24.5MP FX-Format BSI CMOS Sensor and Dual EXPEED 6 Processors\r\nUHD 4K Video Recording , UHD 4K video recording is possible with full pixel readout up to 30p and Full HD 1080p video recording is also supported at up to 120p for slow motion playback\r\n273-Point Phase-Detect AF System and Vibration Reduction with Sensor Shift;NIKKOR Z 24-70mm f/4 S Lens part of the kit\r\nA robust magnesium alloy chassis is both dust- and weather-resistant to benefit working in harsh climates and inclement conditions;Hardware Interface: Bluetooth 4 0\r\nWireless Communication Technology: Bluetooth 4.0\r\n',_binary '','Nikon Z6 II Mirrorless Camera Z',NULL,'2141828a-f9a0-4054-887c-5c1eec40485c',NULL,_binary '\0',NULL,NULL,'2023-06-12 23:38:31.872000',5),('e2be5700-9c3d-4227-89e7-3dc8b9c36f8d','Processor: Intel Core i5-13500H Processor 2.6 GHz (18MB Cache, up to 4.7 GHz, 12 cores, 16 Threads)\r\nMemory: 16GB (8GB on board + 8GB SO-DIMM) 3200MHz with | Storage: 512GB M.2 NVMe PCIe 3.0 SSD\r\nDisplay: 15.6-inch (39.62 cm) FHD (1920 x 1080) OLED 16:9 aspect ratio, 60Hz refresh rate Display, 0.2ms response time 600nits HDR peak brightness VESA CERTIFIED Display HDR True Black 600 100% DCI-P3 color gamut PANTONE Validated Glossy display\r\nGraphics: Integrated Intel Iris Xáµ‰ Graphics\r\nOperating System: Windows 11 Home with lifetime validity | Software Included: Pre-Installed Office Home and Student 2021 | 1-Year McAfee Anti-Virus\r\nDesign: 1.99 ~ 1.99 cm Thin | Thin & Light Laptop | 1.70 kg | 50WHrs Battery Capacity Up to 6 hours Battery life, Note: Battery life depends on conditions of usage\r\nKeyboard: Backlit Chiclet Keyboard with Num-key',_binary '','ASUS Vivobook 15 Laptop',NULL,'c01f111a-29c2-4c4a-8f5a-2461ddf31428',NULL,_binary '\0',NULL,NULL,'2023-06-23 09:40:55.310000',3),('e9c371d1-296e-4d15-a6e8-2cdf4839e081','Resolution : Crystal 4K Ultra HD (3840 x 2160) | Refresh Rate : 50 Hertz\r\nConnectivity: 3 HDMI ports to connect set top box, Blu-ray speakers or a gaming console | 1 USB ports to connect hard drives or other USB devices\r\nDisplay: Ultra HD (4k) LED Panel | One Billion Colors | New Bezel-less Design | Supports HDR 10+ | PurColor | Mega Contarst | UHD Dimming | Auto Game Mode\r\nSound: 20 Watts Output | Powerful Speakers with Dolby Digital Plus | Q Symphony\r\nSmart TV Features : Prime Video, Hotstar, Netflix, Zee5 and more | Voice Assistant - Bixby & Alexa | Tap View | PC Mode | Universal Guide | Web Browser | Screen Mirroring\r\nWarranty Information: 1 year comprehensive warranty plus additional 1 years on panel by brand from date of invoice\r\nInstallation: For requesting installation/wall mounting/demo of this product once delivered, please directly call Samsung support on (Visit Brand website for tollfree numbers) and provide product\'s model name as well as seller\'s details mentioned on the invoice\r\nEasy Returns: This product is eligible for replacement within 10 days of delivery in case of any product defects, damage or features not matching the description',_binary '','Samsung Crystal 4K Neo Series Smart LED TV (43 inches)',NULL,'7db03c36-a17b-4c5f-8e5b-cd721b350621',NULL,_binary '\0',NULL,NULL,'2023-06-12 16:25:15.032000',5),('ed5fd7f8-e2df-458c-952e-90535e552ca1','* With fast 33W SUPERVOOC charging, the realme narzo N55 charges up the massive 5000mAh battery from 0-50% in just 29 minutes, making your life super convenient and stress free.\r\n\r\n* Super High-res 64MP primary AI camera allows you to shoot crisp and clear shots in any scenario with incredible detail. The flagship grade ProLight Imaging technology brings segment leading low light performance to the N55.\r\n\r\n* Large 6.72â€ Full screen display on the narzo N55 provides a segment leading display with a centre punch hole display, 90Hz ultra smooth refresh rate, 680nits of peak brightness, and 91.4% screen-to-Body ratio.\r\n\r\n* Flagship grade premium two tone design makes the N55 stand out from the crowd. The 7.89mm thin beauty feels easy to hold and light weight to carry around easily.',_binary '','realme narzo N55',NULL,'1280e445-3782-49bb-8137-b7f1e47caeef',NULL,_binary '\0',NULL,NULL,'2023-06-12 14:00:44.239000',5);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_review`
--

DROP TABLE IF EXISTS `product_review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_review` (
  `uuid` varchar(255) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `deleted_by` binary(255) DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  `rating` int NOT NULL,
  `review` text,
  `product_id` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  KEY `FKkaqmhakwt05p3n0px81b9pdya` (`product_id`),
  KEY `FK8c2hq5ob9c4jk2mr1xufxiw3b` (`user_id`),
  CONSTRAINT `FK8c2hq5ob9c4jk2mr1xufxiw3b` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`uuid`),
  CONSTRAINT `FKkaqmhakwt05p3n0px81b9pdya` FOREIGN KEY (`product_id`) REFERENCES `product` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_review`
--

LOCK TABLES `product_review` WRITE;
/*!40000 ALTER TABLE `product_review` DISABLE KEYS */;
INSERT INTO `product_review` VALUES ('0caec042-3b70-4d0c-b912-08860a63d7e7','2023-06-17 22:45:42.848000',_binary '\0',NULL,NULL,'2023-06-17 22:45:42.860000',4,'good','98250d35-fac9-43a3-9866-f160ecf4801d','bef69830-cf21-404c-b833-381933a7ebb9'),('0d48b330-da33-4682-9c1f-6372bb960a9f','2023-06-12 14:00:44.216000',_binary '\0',NULL,NULL,'2023-06-12 14:00:44.217000',5,'','ed5fd7f8-e2df-458c-952e-90535e552ca1','71620638-4698-4d7e-b74a-7dd808b472f7'),('0fb5a0aa-1775-46ca-88f2-bc906a8f002d','2023-06-12 16:25:14.988000',_binary '\0',NULL,NULL,'2023-06-12 16:25:15.001000',5,'','e9c371d1-296e-4d15-a6e8-2cdf4839e081','5a72c8df-0268-4d58-a3b9-5294508998b2'),('19dd774b-4cf3-494e-a700-afd8fab3ab4a','2023-06-13 15:07:27.256000',_binary '\0',NULL,NULL,'2023-06-13 15:07:27.256000',3,'jgguyguyf','b40c3f3f-5f66-461e-b2db-e0ec8a692f58','f8ed1810-0915-491c-a61c-f07bc3e06493'),('1a9407f9-1da8-4451-b65f-3b27aef72b2a','2023-06-11 23:34:55.251000',_binary '\0',NULL,NULL,'2023-06-11 23:34:55.252000',5,'Est non enim molestias cupidatat hic anim cum molestiae quis maxime omnis quas ex sequi mollitia quis numquam fuga. Incididunt at qui molestias voluptatem soluta repudiandae aliquip tempor aliquam sit.','bc9b9ddc-03a6-4b4a-8561-42f4f3844f64','5a72c8df-0268-4d58-a3b9-5294508998b2'),('1cd2e988-694f-4b45-978f-2d45065145fd','2023-06-12 10:45:18.779000',_binary '\0',NULL,NULL,'2023-06-12 10:45:18.780000',1,'Very bad TV','734ac55c-dbb4-43f5-bfd9-0e2133313bb5','f50f34db-3d64-49f5-a74f-e8a4e99fb6f7'),('1d2ee20a-980c-485e-8f9d-bfbf8225d36d','2023-06-13 15:18:24.851000',_binary '\0',NULL,NULL,'2023-06-13 15:18:24.851000',4,'','b40c3f3f-5f66-461e-b2db-e0ec8a692f58','f50f34db-3d64-49f5-a74f-e8a4e99fb6f7'),('4d815f7a-c904-44a2-a06e-13c4938fb937','2023-06-11 23:21:53.713000',_binary '\0',NULL,NULL,'2023-06-11 23:21:53.720000',1,'Est elit, voluptatem anim eos qui non cumque eum repellendus. Sit non reiciendis in occaecat sit, tempora tempore, sit ut impedit, cillum a dignissimos est, dolores est eiusmod ut quaerat sed et exerc.','b40c3f3f-5f66-461e-b2db-e0ec8a692f58','5a72c8df-0268-4d58-a3b9-5294508998b2'),('4ee496d8-0e53-492e-9f29-ed2c4b671189','2023-06-09 20:35:18.096000',_binary '\0',NULL,NULL,'2023-06-09 20:35:18.096000',3,'Its durable construction ensures longevity, while the comfortable strap makes it suitable for prolonged wear. The watch face is clear and vibrant, with customizable watch faces and a variety of strap options, allowing you to personalize it to your liking. The overall design and build quality of the Noise Smart Watch are top-notch, making it an attractive accessory for any occasion.\r\n','b40c3f3f-5f66-461e-b2db-e0ec8a692f58','f8ed1810-0915-491c-a61c-f07bc3e06493'),('65d5c915-8f30-4a8f-9c8f-61be9f62ff12','2023-06-10 16:23:23.470000',_binary '\0',NULL,NULL,'2023-06-10 16:23:23.476000',4,'Corrupti, aut cumque commodi porro est sunt expedita quos pariatur? Et dolores aut cupiditate dolor qui cupidatat consequat. Quis est numquam facere earum non explicabo. Dicta non quibusdam elit, est.','0e28b304-de92-4393-8382-678dad631598','5a72c8df-0268-4d58-a3b9-5294508998b2'),('6756631a-e7b9-46f2-86fb-9532b1f4f52f','2023-06-12 10:41:40.136000',_binary '\0',NULL,NULL,'2023-06-12 10:41:40.136000',3,'Doloremque quis voluptatum culpa, sed exercitationem velit Nam dolores esse, natus ut itaque voluptatem, ullamco soluta adipisicing consequatur officia eos perspiciatis, possimus, elit, exercitationem.','0e28b304-de92-4393-8382-678dad631598','f50f34db-3d64-49f5-a74f-e8a4e99fb6f7'),('6b809134-510c-4b7c-986f-ed16e2c249c3','2023-06-12 11:39:39.315000',_binary '\0',NULL,NULL,'2023-06-12 11:39:39.326000',2,'','e2be5700-9c3d-4227-89e7-3dc8b9c36f8d','f50f34db-3d64-49f5-a74f-e8a4e99fb6f7'),('6d3ddefd-9367-4fc2-9ec9-35989e1053e0','2023-06-10 22:30:52.911000',_binary '\0',NULL,NULL,'2023-06-10 22:30:52.912000',4,'mac is good','3c1792bf-32dd-4d30-bd68-5a7ec817a968','5a72c8df-0268-4d58-a3b9-5294508998b2'),('70e87edf-decc-4c5b-8137-8ec8ff635620','2023-06-17 22:38:12.625000',_binary '\0',NULL,NULL,'2023-06-17 22:38:12.627000',5,'good','d5438c62-e1f1-41fc-b6fb-2db3bf66c515','bef69830-cf21-404c-b833-381933a7ebb9'),('71fbf384-f70c-4f1c-b02a-4b6b6bdf662c','2023-06-11 23:50:34.284000',_binary '\0',NULL,NULL,'2023-06-11 23:50:34.285000',3,'Architecto veniam, voluptates nostrud obcaecati asperiores quia consequatur, iusto corporis maiores lorem repellendus. Tempora dolorem illum, eos nihil minim voluptatem, nulla do officia qui ea vel fu.','3c1792bf-32dd-4d30-bd68-5a7ec817a968','5a72c8df-0268-4d58-a3b9-5294508998b2'),('79c2d419-461b-4b5e-9937-a2862210dd20','2023-06-16 12:22:01.370000',_binary '\0',NULL,NULL,'2023-06-16 12:22:01.377000',4,'good laptop','98250d35-fac9-43a3-9866-f160ecf4801d','ba2da78c-cbf1-4cf5-8aad-bd864e410568'),('a7ebdaa2-ae03-4c38-9580-73de521dbfa9','2023-06-13 15:21:36.943000',_binary '\0',NULL,NULL,'2023-06-13 15:21:36.943000',5,'','0e28b304-de92-4393-8382-678dad631598','5a72c8df-0268-4d58-a3b9-5294508998b2'),('b2a7f451-684b-408f-b02b-ae8ba3cb557f','2023-06-12 10:44:52.564000',_binary '\0',NULL,NULL,'2023-06-12 10:44:52.564000',5,'','3920eaa1-4560-4069-93ef-731df9604272','f50f34db-3d64-49f5-a74f-e8a4e99fb6f7'),('b41cd578-f608-4f12-9211-543e73cdb384','2023-06-12 10:42:02.915000',_binary '\0',NULL,NULL,'2023-06-12 10:42:02.915000',4,'','e2be5700-9c3d-4227-89e7-3dc8b9c36f8d','f50f34db-3d64-49f5-a74f-e8a4e99fb6f7'),('bfb1ed6e-f6aa-48f0-8657-18c801f3ac3d','2023-06-12 23:19:21.575000',_binary '\0',NULL,NULL,'2023-06-12 23:19:21.576000',5,'','3c1792bf-32dd-4d30-bd68-5a7ec817a968','d1f1060c-bc5a-471c-a55b-2ca32c8bf7dc'),('c2ce33f5-a3d5-4775-9d84-5b5faaadb2bc','2023-06-12 11:44:59.922000',_binary '\0',NULL,NULL,'2023-06-12 11:44:59.923000',4,'','bc9b9ddc-03a6-4b4a-8561-42f4f3844f64','f50f34db-3d64-49f5-a74f-e8a4e99fb6f7'),('c42d08fc-45e6-4493-8d5c-3116d8930d53','2023-06-11 23:59:10.662000',_binary '\0',NULL,NULL,'2023-06-11 23:59:10.663000',3,'Amet, sunt voluptatem. Doloribus aliquam minus adipisci natus id, saepe cum autem cupiditate libero possimus, non incididunt ex architecto non totam Nam odio ut rerum nisi commodi dolorum sunt in culp.','3c1792bf-32dd-4d30-bd68-5a7ec817a968','5a72c8df-0268-4d58-a3b9-5294508998b2'),('d96faeea-f34d-400c-ac7a-605d1b4096c8','2023-06-15 23:08:19.019000',_binary '\0',NULL,NULL,'2023-06-15 23:08:19.020000',4,'Good','3c1792bf-32dd-4d30-bd68-5a7ec817a968','0367b9e9-8789-4fb9-ad99-f6f9a4225c91'),('df996d05-3ad4-4ea5-a0aa-65a5d4aedeaf','2023-06-12 23:38:31.850000',_binary '\0',NULL,NULL,'2023-06-12 23:38:31.851000',5,'','d5438c62-e1f1-41fc-b6fb-2db3bf66c515','d1f1060c-bc5a-471c-a55b-2ca32c8bf7dc');
/*!40000 ALTER TABLE `product_review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `uuid` varchar(255) NOT NULL,
  `role_name` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `deleted_by` binary(255) DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES ('8b5495b2-0f12-493d-8527-820c885eebc3','ROLE_ADMIN',NULL,_binary '\0',NULL,NULL,NULL),('9cfbde23-0077-45bf-985a-7960273620ec','ROLE_USER',NULL,_binary '\0',NULL,NULL,NULL);
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_address`
--

DROP TABLE IF EXISTS `user_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_address` (
  `uuid` varchar(255) NOT NULL,
  `area` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `flat` varchar(255) DEFAULT NULL,
  `landmark` varchar(255) DEFAULT NULL,
  `pin` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `town` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `default_address` bit(1) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `deleted_by` binary(255) DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  PRIMARY KEY (`uuid`),
  KEY `FK1lswhlojwqbf548l7hyqnxryg` (`user_id`),
  CONSTRAINT `FK1lswhlojwqbf548l7hyqnxryg` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_address`
--

LOCK TABLES `user_address` WRITE;
/*!40000 ALTER TABLE `user_address` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_info`
--

DROP TABLE IF EXISTS `user_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_info` (
  `uuid` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `role_id` varchar(255) DEFAULT NULL,
  `verified` bit(1) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `deleted_by` binary(255) DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  `coupon_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  KEY `FKgatgpi3b28ljsw6bo16jrvykb` (`role_id`),
  KEY `FKk2gtmco8lyqrfgu34hd7ibam8` (`coupon_id`),
  CONSTRAINT `FKgatgpi3b28ljsw6bo16jrvykb` FOREIGN KEY (`role_id`) REFERENCES `role` (`uuid`),
  CONSTRAINT `FKk2gtmco8lyqrfgu34hd7ibam8` FOREIGN KEY (`coupon_id`) REFERENCES `coupon` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_info`
--

LOCK TABLES `user_info` WRITE;
/*!40000 ALTER TABLE `user_info` DISABLE KEYS */;
INSERT INTO `user_info` VALUES ('0367b9e9-8789-4fb9-ad99-f6f9a4225c91','blue@blue.blue',_binary '','blue','blueP','$2a$10$d5Q9rd7T0Eg/lO8IJm1J0.hj/oLa97X3UgRartXMzIXCQvupFoQJK','75675765','blue','9cfbde23-0077-45bf-985a-7960273620ec',_binary '','2023-05-29 19:04:51.031000',NULL,_binary '\0',NULL,NULL,'2023-06-05 12:28:11.030000',NULL),('06c5c9c3-e306-4665-8822-19866fceedab','trump@mail.com',_binary '','Donald','Trump','$2a$10$xLJyzISAyJi0UdDSNvBske.fHTpQMCdYqvaqvYEk6Oq4Dn/XLw75y','91191191191','trump','9cfbde23-0077-45bf-985a-7960273620ec',_binary '','2023-05-31 22:39:18.604000','2023-05-31 22:38:11.503000',_binary '\0',NULL,NULL,NULL,NULL),('08588b25-8ca0-4f81-a459-e564ba5994e6','jrobertuccig@github.io',_binary '\0','Jobina','Robertucci','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','233-275-6403','jrobertuccig','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('099da3cd-0b02-4243-b0de-977f2c87b4f4','black@black.black',_binary '\0','black','black','$2a$10$CLK7Bpt7fEXDdX/BnsARq.G1VJ7zMyy8MxluJseSS9/gkhTJM6W/S','65875464353','black','9cfbde23-0077-45bf-985a-7960273620ec',_binary '','2023-05-29 19:41:22.573000',NULL,_binary '','2023-06-16 13:51:21.928000',_binary '\õ4\Û=dI\õ§O\è¤éŸ¶\÷\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0','2023-06-16 13:51:21.998000',NULL),('0beb04af-33fc-495a-bf3c-b2fbf1bb80d1','thambrook18@merriam-webster.com',_binary '','Terrye','Hambrook','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','938-909-0407','thambrook18','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('0cf15671-fab8-4ae1-9358-c27bd581177f','cdeboyh@chronoengine.com',_binary '','Chere','Deboy','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','395-251-7566','cdeboyh','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('0f9e4a51-54c4-4136-9a28-f0301c0061e0','kakers29@engadget.com',_binary '','Kirk','Akers','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','159-296-5494','kakers29','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('11127526-bab5-44a9-88b1-ac8c1250bb45','hphythiena@etsy.com',_binary '','Harland','Phythien','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','414-116-9691','hphythiena','9cfbde23-0077-45bf-985a-7960273620ec',_binary '',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('11bced74-fe9e-4ab0-9410-d4d4941e99e6','ceasterbrookn@walmart.com',_binary '','Corliss','Easterbrook','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','349-503-1116','ceasterbrookn','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('12f2fce0-83e4-4a97-a857-95e329bee2e0','tkerss1g@nature.com',_binary '','Town','Kerss','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','122-463-5155','tkerss1g','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('16757d33-968f-4e26-856b-f2871129bec7','gguittej@reverbnation.com',_binary '','Gallagher','Guitte','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','160-832-4574','gguittej','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('16bd9007-f1b1-4566-bd53-7ddfddae6499','asandwick24@comsenz.com',_binary '\0','Aubrey','Sandwick','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','963-137-6813','asandwick24','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('17626097-bc6c-414d-9f10-05f866db81d3','djahndel4@oaic.gov.au',_binary '','Darill','Jahndel','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','716-850-5106','djahndel4','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('1a37c0ac-1552-46d2-8307-ce0cd3e66304','amaasze8@npr.org',_binary '\0','Allegra','Maasze','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','642-991-8877','amaasze8','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('1c659642-d34f-495c-83ad-8d55af03754a','bwhifenz@princeton.edu',_binary '','Byran','Whifen','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','300-315-1431','bwhifenz','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('1d360d8d-403f-4e7f-9caf-53d903a13f67','givanchikov1u@goo.gl',_binary '','Gage','Ivanchikov','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','700-403-5303','givanchikov1u','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('208a6ffb-8a09-4167-8c90-a59868e0951c','gobee7@artisteer.com',_binary '','Guthrie','O\' Bee','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','935-227-4924','gobee7','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('20fab30d-ce38-4064-aee8-23d8ffc9640e','mohanniganb@ycombinator.com',_binary '','Mellisent','O\'Hannigan','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','430-818-3483','mohanniganb','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('2571982e-206f-4203-8d08-8d631ad9a8d5','sdelacoste1v@tumblr.com',_binary '','Sapphire','De La Coste','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','158-791-0875','sdelacoste1v','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('28847afb-33fa-428f-a914-63dddcf1a86b','harry@mail.com',_binary '','Harry','Potter','$2a$10$Jo89POT0nWgJ3hrYV37fNuOTml5c.Qj6Wapov2cSpfejyk9UFJkDG','46846486','harry','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,'2023-06-19 13:26:30.549000',_binary '\0',NULL,NULL,'2023-06-19 13:26:30.581000',NULL),('2a580599-0d47-46de-b323-c7c76ee00066','gellor2b@storify.com',_binary '','Gaye','Ellor','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','829-136-0550','gellor2b','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('2e2dd22a-dbbe-4665-a711-a91acbd4f3a9','white@white.com',_binary '','white','white','$2a$10$qqmDLuNfrNR4NTloQsrYn.brFZoT7HgHNcGbfQlTNvzoakwURi1.W','3452544532','white','9cfbde23-0077-45bf-985a-7960273620ec',_binary '',NULL,'2023-06-15 13:28:13.625000',_binary '\0',NULL,NULL,'2023-06-15 19:32:12.739000',NULL),('2ee5abc0-6a00-4bc7-8acc-71c87bc914e3','chydesi@e-recht24.de',_binary '','Cleveland','Hydes','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','998-708-2352','chydesi','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('2f42c99f-11ac-4812-a633-8907a872cbe7','eloudon2n@google.de',_binary '','Evelina','Loudon','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','136-569-2850','eloudon2n','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('2fd16cd0-8f0d-4ce6-8b56-845689378185','gcrunden13@jugem.jp',_binary '','Griffin','Crunden','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','484-168-1588','gcrunden13','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('31167c7d-df9f-45ef-9388-dc9b6c8c8449','tez@tez.tez',_binary '','tez','tez','$2a$10$59NKrpbEiojhwUkzlS/3huJpAzqk0DYtAHtdrmLveTrRZcXcAczkm','141414','tez','9cfbde23-0077-45bf-985a-7960273620ec',_binary '','2023-05-29 19:10:13.883000',NULL,_binary '\0',NULL,NULL,NULL,NULL),('34ee9b6b-eb8b-4415-b30c-9a09a831e47e','dhamblett15@dropbox.com',_binary '','Dorry','Hamblett','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','693-128-0190','dhamblett15','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('378f1405-b9b2-4998-b1b2-a02a6b4d474a','rcauraht@ow.ly',_binary '','Randolf','Caurah','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','509-326-9559','rcauraht','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('3bf3dd04-bfa3-4515-a6cf-fc4b39a31f06','jpratchett10@mail.ru',_binary '\0','Jenica','Pratchett','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','487-630-0806','jpratchett10','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('3c5b2121-292c-4071-bdab-a024ee4fd4f5','laers1q@wordpress.com',_binary '','Liane','Aers','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','809-939-5292','laers1q','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('3fb682df-e7f9-48bd-8526-5aa4abb015a4','oelles1e@uiuc.edu',_binary '','Ozzie','Elles','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','365-329-3330','oelles1e','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('40a61380-778d-44fc-a823-151a5f9b377d','twarnes9@last.fm',_binary '','Tiertza','Warnes','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','686-867-6405','twarnes9','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('456e65c5-dcb4-49b8-b87d-43078a120592','ftuvey1f@godaddy.com',_binary '\0','Fitz','Tuvey','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','517-522-0725','ftuvey1f','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('4581b51d-07bd-48b2-9fc6-cfb7b5deef12','vstairmond1t@berkeley.edu',_binary '','Veronica','Stairmond','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','678-919-9320','vstairmond1t','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('496fb3f0-00ac-4500-8cf2-dae8d3ee78e4','kferandez28@elpais.com',_binary '','Kimberley','Ferandez','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','638-731-7920','kferandez28','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('4d8feae4-dcc7-4afe-b91a-7d16a6efe241','amarcm@washingtonpost.com',_binary '\0','Averil','Marc','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','722-837-0931','amarcm','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('4ff7549f-f744-4291-af1c-9e1e3bd18343','cblackham1@telegraph.co.uk',_binary '','Cornie','Blackham','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','655-249-7984','cblackham1','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('51c8c360-61a8-4b0a-bc8b-3b74d5ec4405','kcrunkhornx@flickr.com',_binary '','Killian','Crunkhorn','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','289-823-5390','kcrunkhornx','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('542dcb86-5fca-423c-adb5-130b361e5e0c','ewaddam2a@cloudflare.com',_binary '','Eustace','Waddam','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','989-903-7542','ewaddam2a','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('551945cc-0e9f-4ed2-9c4c-34ff1bb781e5','spiderman@spider.com',_binary '\0','Peter','Parker','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','1231234','spiderman','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('56b6ede6-7bed-4110-bdba-38ba10d3f578','ccraner@biblegateway.com',_binary '','Carl','Crane','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','909-359-9835','ccraner','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('5a317752-8df4-41eb-9f11-d01725196417','mmoniniu@gnu.org',_binary '','Maggi','Monini','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','793-805-3103','mmoniniu','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('5a6d6c79-f772-4bb6-9fe5-7ba36f5b1bd0','cbaskwell2m@indiegogo.com',_binary '','Carlos','Baskwell','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','138-334-2660','cbaskwell2m','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('5a72c8df-0268-4d58-a3b9-5294508998b2','gema@mail.com',_binary '','John','Wick','$2a$10$47xIkLrnmtbAXOsCJi26heMQnYQxRfyZCoRrD2JQVH7fWPauWwTVa','123123123','gema','9cfbde23-0077-45bf-985a-7960273620ec',_binary '',NULL,NULL,_binary '\0',NULL,NULL,'2023-06-16 14:45:25.321000',NULL),('5af92f88-f7ac-41c7-92a8-6d9cad3e9a9a','lsaile1b@mac.com',_binary '','Lura','Saile','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','990-425-5942','lsaile1b','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('62f48d94-04d8-4570-ab5a-4e4f9a70dca6','yalpes3@themeforest.net',_binary '','Yoko','Alpes','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','185-456-6915','yalpes3','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('66e54fb5-f296-4709-aee6-65888bf0ea8f','gwallworthc@github.com',_binary '','George','Wallworth','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','906-567-0469','gwallworthc','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('6a2e49d1-ef60-44da-af5b-a01ab32a06f8','wosanne1a@google.es',_binary '','Wilow','Osanne','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','552-139-7592','wosanne1a','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('71620638-4698-4d7e-b74a-7dd808b472f7','purple@purple.purple',_binary '','purple','purple','$2a$10$1af1RUovH.1fGbdud8kV4eBenqpjUyiMljC8SKyT0JmVUJPjyCCWe','4353534561','purple','9cfbde23-0077-45bf-985a-7960273620ec',_binary '','2023-05-30 11:34:42.600000','2023-05-30 11:34:42.600000',_binary '\0',NULL,NULL,'2023-06-12 14:10:39.404000','9c94dffc-a7ff-4a0a-b877-6b609724229c'),('71d2a953-56dc-4513-8b9d-1c65e2fb8100','dyakovitch2j@sohu.com',_binary '','Domenico','Yakovitch','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','731-253-8256','dyakovitch2j','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('725d18d1-4447-4518-bd97-0ad492d13aa2','lharnetty1z@wordpress.org',_binary '','Latia','Harnetty','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','916-423-0931','lharnetty1z','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('72db76f0-d0d7-406d-932a-6cbdd9512761','sygukogebo@mailinator.com',_binary '','Sigourney','David','$2a$10$sKiEccXwns4HKGwHl3XHqOdM3M.8uEyIFUTKHrXvYLX9B1mijI/16','+1 (365) 527-9754','ximodif','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,'2023-06-06 18:36:06.027000',_binary '\0',NULL,NULL,'2023-06-06 18:36:06.034000',NULL),('73299798-b14d-4eed-b586-317c6e2ff019','lvondra0@vk.com',_binary '','Lucias','Vondra','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','305-223-2750','lvondra0','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('7352dfd5-9619-44e3-9746-7aed738eaf6e','gdeveraux21@princeton.edu',_binary '','Godard','Deveraux','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','737-921-3716','gdeveraux21','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('75f14220-7f5b-4038-b9c5-b2711beb71b6','kmoulderq@sourceforge.net',_binary '','Kissie','Moulder','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','670-979-2546','kmoulderq','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('76292205-ded2-4291-9e99-a05b24555f59','hmaccaig1i@csmonitor.com',_binary '','Hillyer','MacCaig','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','573-704-3207','hmaccaig1i','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('76db1c94-3b3b-4f7f-8e9a-6b18dab1dafb','dbullocke11@upenn.edu',_binary '','Diena','Bullocke','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','235-677-6808','dbullocke11','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('790c5c1c-c454-4ca4-9259-69c0dc62b974','sheaney2p@skype.com',_binary '','Scarface','Heaney`','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','603-907-9561','sheaney2p','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('7b022758-95b7-4c91-844f-77737fb30c3f','pink@pink.com',_binary '','pink','pinl','$2a$10$A0aeZ0bRRwM1VyGglVwrV.NLwodvO3YeKYU7ErkKxr5JozGZT7/bK','94656544','pink','9cfbde23-0077-45bf-985a-7960273620ec',_binary '',NULL,'2023-06-15 10:16:53.865000',_binary '\0',NULL,NULL,'2023-06-15 12:24:16.537000','9c94dffc-a7ff-4a0a-b877-6b609724229c'),('7fecc3f5-5294-4bf6-a332-18f2f591664d','mleveretv@usnews.com',_binary '','Moll','Leveret','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','363-802-2023','mleveretv','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('82f1d06e-7e93-4b32-8f69-fc3efad3d49c','dbreissan1n@ebay.co.uk',_binary '','Dorolice','Breissan','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','251-184-4526','dbreissan1n','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('852d7251-f7ba-4d4c-af00-428e015acd58','ckillingsworthw@oakley.com',_binary '','Clarine','Killingsworth','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','749-890-2733','ckillingsworthw','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('875088aa-cdaf-4ac5-9129-8d2f9afc2155','red@red.bh',_binary '','red','red','$2a$10$wUcK/6t96Dzvs4TGclym0OO4u2lavJJ6sVoo1BGZqe//vzIyunoJe','123123','red','9cfbde23-0077-45bf-985a-7960273620ec',_binary '','2023-05-29 19:02:06.065000',NULL,_binary '\0',NULL,NULL,NULL,NULL),('887373f5-c9cd-4556-ae46-a8a1bf4cf858','dmorshead2e@psu.edu',_binary '','Darb','Morshead','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','893-457-7799','dmorshead2e','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('8eee08eb-6511-48a8-8841-789564f92cab','ccrokero@moonfruit.com',_binary '','Caspar','Croker','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','426-988-5904','ccrokero','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('8f18658c-2354-4147-b4a9-5d13646a3c00','fjedryka2@xing.com',_binary '\0','Faustine','Jedryka','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','686-260-4919','fjedryka2','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('8f2c3c89-d646-4cd9-8ac3-3bb4c0823d5d','siacobassi2q@jiathis.com',_binary '','Shurlock','Iacobassi','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','117-182-2892','siacobassi2q','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('90d4480e-7841-4a49-ab0c-cabcda1e0116','fed@mail.com',_binary '','Mary','Jane','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','12312313','fed','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('91a2e3ff-e189-460f-8b45-679b463a67af','walter@breakingbad.com',_binary '','Walter','White','$2a$10$Ml.WeHHYOX3kMWTFbExdLO7KRMXEYNOEKsCwOEKdlzu4wyWr4VzZu','456468889','walter','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,'2023-06-16 00:30:29.413000',_binary '\0',NULL,NULL,'2023-06-16 00:30:29.438000',NULL),('91e91050-133d-4f39-a38d-c75c76954021','ylambden1o@fc2.com',_binary '','Yoshiko','Lambden','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','501-536-5278','ylambden1o','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('932db805-ca80-4805-8069-55d7543c28bb','nwalkers@zimbio.com',_binary '','Ninnette','Walker','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','218-918-6294','nwalkers','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('93f9682c-78d1-4f56-ba83-9938ba128148','omungham14@ftc.gov',_binary '','Orion','Mungham','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','814-998-1283','omungham14','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('9824fe43-bf74-4aee-979e-bad75a07c68d','gmackibbon16@youku.com',_binary '','Gnni','MacKibbon','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','690-487-9361','gmackibbon16','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('99b93f3d-05ac-4287-9baa-e2989f699cef','lbartosek25@google.ca',_binary '','Lorie','Bartosek','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','715-724-2705','lbartosek25','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('9c98d73a-add5-4ab4-84ec-659f52d3e592','asisey26@umich.edu',_binary '\0','Alyce','Sisey','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','813-122-2575','asisey26','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('a115d1be-cd34-4bdf-b21b-4e06c8116e1e','asaundercockk@goo.ne.jp',_binary '\0','Aylmar','Saundercock','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','900-313-1131','asaundercockk','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('a87ad44b-ddcf-49fe-9f47-a4c810b46e36','vyakov1w@fc2.com',_binary '','Vassily','Yakov','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','749-418-7098','vyakov1w','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('af0bd35b-1088-4f2b-9102-b592e9a03643','moliva0@reddit.com',_binary '','Melisa','Oliva','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','233-538-8530','moliva0','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('b4968f9d-e0fd-48d7-ab7b-9bd2293109a8','whackwell2k@theatlantic.com',_binary '','Wayne','Hackwell','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','555-422-4776','whackwell2k','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('b5c2e009-3f42-44a6-ac7a-433a03b59d70','moswalp@google.nl',_binary '','Margot','Oswal','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','203-132-6206','moswalp','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('b5d122d3-27c3-42ca-a92b-b573b98cee73','drobinette19@mac.com',_binary '','Doralynne','Robinette','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','572-795-0753','drobinette19','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('b863a88a-fc49-44ea-ac84-15b82d1c4679','gsimoneau20@stumbleupon.com',_binary '','Guido','Simoneau','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','598-367-2429','gsimoneau20','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('b8add176-bb77-4d62-b824-58be8b1d1ce4','kbapty2g@icio.us',_binary '','Keriann','Bapty','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','231-248-5025','kbapty2g','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('b9db27da-c898-4860-a1ce-77bdcdec0e6c','mlergan5@nyu.edu',_binary '','Miriam','Lergan','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','496-320-8485','mlergan5','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('ba2da78c-cbf1-4cf5-8aad-bd864e410568','green@green.green',_binary '','green','green','$2a$10$YtSbnST4cMcpb1ug3dV7quq6MN20mykqLGMPnDJQgmRGWvFf9up5i','564563465464','green','9cfbde23-0077-45bf-985a-7960273620ec',_binary '','2023-05-29 19:00:05.324000',NULL,_binary '\0',NULL,NULL,'2023-06-16 20:00:54.269000','9c94dffc-a7ff-4a0a-b877-6b609724229c'),('bc637995-298f-469f-8fc7-b3b611101a02','cnickoll2f@php.net',_binary '','Cariotta','Nickoll','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','824-401-9925','cnickoll2f','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('bcc94f85-d38f-4b23-b516-6d2ce971a9a3','fdfddf01164@gmail.com',_binary '','Rashid','M','$2a$10$dpFTXGBrTFvoJoNarqhyruBqaBqSddZpo92omwwuuTVw7PNysiBsK','8585858','rashid','9cfbde23-0077-45bf-985a-7960273620ec',_binary '','2023-06-01 11:34:28.630000','2023-06-01 11:33:57.615000',_binary '\0',NULL,NULL,NULL,NULL),('bef69830-cf21-404c-b833-381933a7ebb9','johncena@gmail.com',_binary '','John','Cena','$2a$10$C.447mwnCiV8q3D0ffEmvOuEJKx46ihqIw9RyAOcEUsSYwadGMTGq','9877978979','johncena','9cfbde23-0077-45bf-985a-7960273620ec',_binary '',NULL,'2023-06-17 21:07:49.292000',_binary '\0',NULL,NULL,'2023-06-17 23:11:17.274000',NULL),('c4968d76-6b2c-4bde-a004-985c8d0a60c9','hbeagan2c@mediafire.com',_binary '','Hamel','Beagan','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','313-181-8971','hbeagan2c','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('c93e26d4-cc95-4b58-a09a-2cc8dd1a5875','hcrawshay2o@slate.com',_binary '','Holly','Crawshay','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','714-238-9209','hcrawshay2o','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('cbfb6777-68b3-458c-b8df-f12cb4b9fbe5','nfinnan2r@baidu.com',_binary '','Nessie','Finnan','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','874-709-6317','nfinnan2r','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('cf719e8a-7625-4d95-ae5b-9ca8a92e2f68','vgellyl@mapquest.com',_binary '','Valery','Gelly','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','653-555-0197','vgellyl','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('d1f1060c-bc5a-471c-a55b-2ca32c8bf7dc','brown@brown.brown',_binary '','brown','brown','$2a$10$sxt2Ld7.PXIlArbHEC0azeIrU24e9GsKKAi.AtyYO2ACklFlvp5zy','7456744546','brown','9cfbde23-0077-45bf-985a-7960273620ec',_binary '','2023-05-29 19:03:16.882000',NULL,_binary '\0',NULL,NULL,'2023-06-17 13:50:10.359000',NULL),('d593e062-af44-4f4f-a124-1e69405d8ba5','mrosendorf23@godaddy.com',_binary '','Mordy','Rosendorf','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','191-423-8620','mrosendorf23','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('d63b1fc0-a099-4652-88c4-36ea28518867','saki@saki.com',_binary '','Wretz','Munsi','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','465454656','saki','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('d7023e40-9ea6-4c8d-885e-6455ddc0521c','dfather1c@wix.com',_binary '','Derek','Father','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','936-200-2605','dfather1c','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('d7b28036-ef47-43aa-bf55-e0c171e9a364','roakenford1l@yellowbook.com',_binary '','Robyn','Oakenford','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','431-334-5377','roakenford1l','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('d9817456-5a4d-44f2-9cbd-071c4087df51','ehazeldene12@sohu.com',_binary '','Edythe','Hazeldene','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','520-713-9700','ehazeldene12','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('da0ace14-fb0d-4c29-85b8-64dcb35308bb','idrinkhall17@techcrunch.com',_binary '','Isac','Drinkhall','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','347-622-6849','idrinkhall17','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('da6318dc-6b44-427c-86d3-5ee97e1588e0','lmoodycliffe1m@pagesperso-orange.fr',_binary '','Linell','Moodycliffe','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','327-674-1296','lmoodycliffe1m','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('db894fa7-7041-4e3e-9d10-07f38adcd7a1','cctvhome2022@outlook.com',_binary '','Elon','Musk','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','43434787','joko','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('dbe66ce0-c6f4-4619-a009-1e9d0b9371cb','chexam1s@simplemachines.org',_binary '','Cornall','Hexam','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','905-764-7423','chexam1s','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('df117503-ffa5-49eb-9cb2-acf20748e10c','ggiffkins1y@fda.gov',_binary '','Gaylene','Giffkins','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','833-574-9586','ggiffkins1y','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('e153e06b-c5aa-469d-88aa-0a314987fbee','jessi@breakingbad.com',_binary '','Jessie','Pinkman','$2a$10$iv6KPWhcNy0ge5fB8Zo9geOVR1zrxOM5nTfWNgRvvfLKjxTHAkGaC','958664654','jessie','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,'2023-06-16 00:34:47.053000',_binary '\0',NULL,NULL,'2023-06-16 00:35:27.458000',NULL),('e2a2a315-72a2-4ee3-a7f5-82a4b677741a','mverrechia1j@friendfeed.com',_binary '','Maxwell','Verrechia','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','157-762-1627','mverrechia1j','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('e3357471-dadc-40fa-a5e6-90a54d242057','wtullett27@imdb.com',_binary '','Wynn','Tullett','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','363-529-9159','wtullett27','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('e3c98616-c1f4-42a5-a8b9-8dba1f1d1632','jewellsd@blogspot.com',_binary '\0','Jack','Ewells','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','369-155-9914','jewellsd','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('e60f48f9-a34c-4014-8076-a0ec5a93ab48','dkinnoch22@naver.com',_binary '','Denise','Kinnoch','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','216-368-0585','dkinnoch22','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('e6f14b4f-0e26-4f44-a1b0-9d6986514c1b','sstroder2d@columbia.edu',_binary '','Shannan','Stroder','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','549-344-5138','sstroder2d','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('e8bedaa9-db48-4447-8753-03b3d61b3560','cowyx@mailinator.com',_binary '','Julie','Sanford','$2a$10$iAItDf52HZ36fWGCv.3ts.0ykH.rHoER7gQ4gG0Ckdp2rPQXkGkxe','+1 (464) 347-6591','myzanojasi','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,'2023-06-06 18:09:44.567000',_binary '\0',NULL,NULL,'2023-06-06 18:09:44.575000',NULL),('e9cea808-4874-4170-a18c-5f9d4f196574','drenackowna2l@ning.com',_binary '','Darci','Renackowna','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','388-475-5829','drenackowna2l','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('ed067207-2ce6-417a-904a-eae6a90a94ed','gaspray1r@chicagotribune.com',_binary '','Georg','Aspray','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','111-252-6925','gaspray1r','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('ee7b6163-f357-48a9-8d34-13ead40cb3db','dnealande@elegantthemes.com',_binary '','Deirdre','Nealand','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','976-523-3600','dnealande','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('f29aecb4-e815-4e8d-8669-47c169bc1d46','uworton1p@fema.gov',_binary '','Ursulina','Worton','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','704-988-1099','uworton1p','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('f473ec87-decd-4c04-8747-2237b2f78c2d','lslyvestery@howstuffworks.com',_binary '','Louise','Slyvester','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','330-698-3003','lslyvestery','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('f4824e0b-0dea-4cf5-840a-0d4ea39f2325','jgwilt6@sphinn.com',_binary '\0','Jarrad','Gwilt','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','424-291-0305','jgwilt6','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('f50f34db-3d64-49f5-a74f-e8a4e99fb6f7','admin@adminmail.info',_binary '','admin','acc','$2a$10$a8jEx1vMKVjvdDkiDm.Xr.XfDWxKBrqv7dCEZuXGvDhfX86m6X0Bu','5858585','admin','8b5495b2-0f12-493d-8527-820c885eebc3',_binary '',NULL,NULL,_binary '\0',NULL,NULL,'2023-06-16 19:44:46.036000',NULL),('f5616bd7-b93b-4964-ae78-f3e2a4ef8849','lsedwick2h@sourceforge.net',_binary '','Lian','Sedwick','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','975-236-6610','lsedwick2h','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('f8cc4f4f-b77a-441d-b38b-b8c8c27b600d','scacacie2i@google.com.au',_binary '','Salim','Cacacie','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','177-375-9898','scacacie2i','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('f8ed1810-0915-491c-a61c-f07bc3e06493','ffgdfdfdfs@gmail.com',_binary '','abhijith','a','$2a$10$RmMHpikJg1RAMHppze84O.1o9ZcUa77swEgo8S0xQ7wKOa1ZrAFMi','75272727','abhijith000360','8b5495b2-0f12-493d-8527-820c885eebc3',_binary '','2023-05-31 19:51:01.249000','2023-05-31 19:50:35.632000',_binary '\0',NULL,NULL,'2023-06-13 15:44:33.976000',NULL),('f979b71b-5d67-4652-867e-2e7502263959','ibrodhead1x@cisco.com',_binary '','Inge','Brodhead','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','290-495-0798','ibrodhead1x','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL),('f980fcff-97b4-458c-b0dc-3f36f7b2946f','nowain1k@ow.ly',_binary '','Neron','Owain','$2a$10$VypwWekADGJeXB4BG3BM8upMKVBZINf3UsoC9bRDG/dBsHb9kR7jq','286-483-3128','nowain1k','9cfbde23-0077-45bf-985a-7960273620ec',_binary '\0',NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `user_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `variant`
--

DROP TABLE IF EXISTS `variant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `variant` (
  `uuid` varchar(255) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `selling_price` float DEFAULT NULL,
  `stock` int DEFAULT NULL,
  `product_id` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `deleted_by` binary(255) DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  `wholesale_price` float DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  KEY `FKjjpllnln6hk6hj98uesgxno00` (`product_id`),
  CONSTRAINT `FKjjpllnln6hk6hj98uesgxno00` FOREIGN KEY (`product_id`) REFERENCES `product` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `variant`
--

LOCK TABLES `variant` WRITE;
/*!40000 ALTER TABLE `variant` DISABLE KEYS */;
INSERT INTO `variant` VALUES ('018de3fb-ed6d-4f28-96a4-bcb64fa624ce',_binary '','Black',20000,10000,1918,'0e28b304-de92-4393-8382-678dad631598',NULL,_binary '\0',NULL,NULL,'2023-06-17 21:19:33.699000',5000),('0de77b25-515d-4188-b52c-04768d9e6a80',_binary '','Body Only',30000,20000,14,'d5438c62-e1f1-41fc-b6fb-2db3bf66c515',NULL,_binary '\0',NULL,NULL,'2023-06-17 21:19:33.698000',18000),('21c44f88-20f7-4989-bbb7-e82662b7cfbd',_binary '','White',50000,25000,0,'98250d35-fac9-43a3-9866-f160ecf4801d',NULL,_binary '\0',NULL,NULL,'2023-06-04 17:56:52.711000',22500),('299d91bd-612d-4966-a7dd-70791bf0c503',_binary '','Prime Blue, 4GB+64GB',13000,11000,172,'ed5fd7f8-e2df-458c-952e-90535e552ca1',NULL,_binary '\0',NULL,NULL,'2023-06-17 21:19:33.691000',9900),('2a662752-0cfc-4c98-bd3f-78be98ff3b42',_binary '','i7 12th Gen, 16GB RAM',90000,80000,68,'e2be5700-9c3d-4227-89e7-3dc8b9c36f8d',NULL,_binary '\0',NULL,NULL,'2023-06-17 22:18:51.811000',72000),('5aaab2b0-3835-444c-a5f6-77fcb4c3dea2',_binary '','8GB',120000,107000,84,'3c1792bf-32dd-4d30-bd68-5a7ec817a968',NULL,_binary '\0',NULL,NULL,'2023-06-17 21:19:33.689000',96300),('5eed5e2e-0cd5-4561-b443-ad6253ca3d78',_binary '','Black',250000,200000,434,'04c664db-a15c-418a-9e12-0f0b179a83d0',NULL,_binary '\0',NULL,NULL,'2023-06-17 21:19:33.700000',180000),('6092a864-5f79-4bf7-aa75-088acb6b3056',_binary '','Black',50000,40000,639,'b40c3f3f-5f66-461e-b2db-e0ec8a692f58',NULL,_binary '\0',NULL,NULL,'2023-06-23 11:15:49.849000',36000),('62c310da-a569-4169-8d78-fcfe910bbb51',_binary '','Green 64GB',50000,40000,83,'77564ad4-59ae-4f57-9add-9fabd51e67e7',NULL,_binary '\0',NULL,NULL,'2023-06-17 21:19:33.684000',36000),('85c8c3a3-a2b3-4329-9efe-d2b1ed958b6d',_binary '','8 GB',9000,8500,198,'669415e9-1b44-4f38-be08-fa31c162f1cf',NULL,_binary '\0',NULL,NULL,'2023-06-17 21:19:33.690000',7650),('8d6f03cb-df56-4e13-afdd-ffc6c5ddd4fd',_binary '','With Zoom Lens',120000,110000,0,'d5438c62-e1f1-41fc-b6fb-2db3bf66c515',NULL,_binary '\0',NULL,NULL,'2023-06-04 01:57:14.421000',99000),('93788e34-8b4c-4b0c-965e-7a3c6ba0c890',_binary '','16GB',10000,9000,124,'669415e9-1b44-4f38-be08-fa31c162f1cf','2023-06-06 14:38:15.972000',_binary '\0',NULL,NULL,'2023-06-08 11:55:03.350000',8100),('985e6127-afca-4d8d-9563-1dc1d38e1f7e',_binary '','Green, 8GB, 256GB',95999,79999,97,'bc9b9ddc-03a6-4b4a-8561-42f4f3844f64','2023-06-09 14:00:15.045000',_binary '\0',NULL,NULL,'2023-06-17 21:19:33.693000',60000),('a0a30968-3932-4080-9cf5-049eb55d84ad',_binary '','16GB',130000,115000,48,'3c1792bf-32dd-4d30-bd68-5a7ec817a968',NULL,_binary '\0',NULL,NULL,'2023-06-09 13:45:30.300000',103500),('a94cbe77-4061-4762-ba85-1699ff14c84e',_binary '','128GB',25000,20000,59,'6e423d06-b255-46b4-88bc-18f78de7dbe0',NULL,_binary '\0',NULL,NULL,'2023-06-17 21:19:33.695000',18000),('a9d5f85e-14cf-43bf-8b58-7ef45393b718',_binary '','Black',350000,320000,1,'3920eaa1-4560-4069-93ef-731df9604272',NULL,_binary '\0',NULL,NULL,'2023-06-17 21:19:33.687000',288000),('ac87ac90-eb99-4a26-a399-83eec72fab8a',_binary '','Green 128GB',18000,11000,78,'77564ad4-59ae-4f57-9add-9fabd51e67e7',NULL,_binary '\0',NULL,NULL,'2023-06-17 21:19:33.682000',9900),('b550fbde-3bee-450c-b787-be27e8ee3d7b',_binary '','Prime Blue, 4GB+128GB',90000,80000,547,'ed5fd7f8-e2df-458c-952e-90535e552ca1',NULL,_binary '\0',NULL,NULL,'2023-06-16 20:01:06.864000',72000),('cd961e36-33c0-49a2-ba29-5f317b08c97f',_binary '','65 Inches',90000,70000,666,'734ac55c-dbb4-43f5-bfd9-0e2133313bb5',NULL,_binary '\0',NULL,NULL,'2023-06-17 21:19:33.685000',63000),('d48d638a-72ce-49ee-a125-7965e185b89c',_binary '','32GB',150000,140000,121,'3c1792bf-32dd-4d30-bd68-5a7ec817a968',NULL,_binary '\0',NULL,NULL,'2023-06-09 13:41:11.733000',126000),('e54971bd-6d9f-4ae4-986e-13b67d2ac19d',_binary '','43 inches',50000,29999,551,'e9c371d1-296e-4d15-a6e8-2cdf4839e081',NULL,_binary '\0',NULL,NULL,'2023-06-17 21:19:33.692000',26999.1);
/*!40000 ALTER TABLE `variant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wallet`
--

DROP TABLE IF EXISTS `wallet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wallet` (
  `uuid` varchar(255) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `deleted_by` binary(255) DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  `balance` float DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  KEY `FKp6qygd2vwf84gdkb8w0wwm4f2` (`user_id`),
  CONSTRAINT `FKp6qygd2vwf84gdkb8w0wwm4f2` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wallet`
--

LOCK TABLES `wallet` WRITE;
/*!40000 ALTER TABLE `wallet` DISABLE KEYS */;
INSERT INTO `wallet` VALUES ('1b818796-4c5f-448a-9b1a-3e4e2a8e4416','2023-06-15 12:41:52.211000',_binary '\0',NULL,NULL,'2023-06-15 12:41:52.228000',0,'d1f1060c-bc5a-471c-a55b-2ca32c8bf7dc'),('32b11ac1-5993-4ece-9f1a-330d298b9f47','2023-06-16 18:13:00.019000',_binary '\0',NULL,NULL,'2023-06-16 18:13:00.025000',0,'1a37c0ac-1552-46d2-8307-ce0cd3e66304'),('3ba3192c-1579-435e-8ab3-f36d8cb51337','2023-06-15 19:34:16.460000',_binary '\0',NULL,NULL,'2023-06-15 19:34:16.461000',0,'2e2dd22a-dbbe-4665-a711-a91acbd4f3a9'),('535b6fa8-b139-426b-b5b8-7d7a7a571dbd','2023-06-15 10:17:30.418000',_binary '\0',NULL,NULL,'2023-06-15 10:17:30.419000',0,'7b022758-95b7-4c91-844f-77737fb30c3f'),('902b3cb8-d50a-4420-8aa1-d66002ea9f26','2023-06-19 13:26:32.056000',_binary '\0',NULL,NULL,'2023-06-19 13:26:32.056000',0,'28847afb-33fa-428f-a914-63dddcf1a86b'),('924d03e4-d24a-45d1-9c03-b122e3e65b7a','2023-06-13 14:58:52.144000',_binary '\0',NULL,NULL,'2023-06-13 15:04:23.406000',1000,'5a72c8df-0268-4d58-a3b9-5294508998b2'),('929d59db-071a-4544-8dfe-b6e8b73375ac','2023-06-16 20:15:32.165000',_binary '\0',NULL,NULL,'2023-06-16 20:15:41.831000',100,'1c659642-d34f-495c-83ad-8d55af03754a'),('974b0f8f-366f-4a94-a6a1-18db6ee84481','2023-06-16 00:32:01.733000',_binary '\0',NULL,NULL,'2023-06-16 00:32:01.734000',0,'91a2e3ff-e189-460f-8b45-679b463a67af'),('a1baf570-6a92-46c7-8348-6870b7e2b46d','2023-06-17 21:09:05.396000',_binary '\0',NULL,NULL,'2023-06-17 21:09:05.397000',0,'bef69830-cf21-404c-b833-381933a7ebb9'),('ce525122-1421-4c6e-aeb5-b3245d33b4f6','2023-06-16 00:34:50.685000',_binary '\0',NULL,NULL,'2023-06-16 00:34:50.687000',0,'e153e06b-c5aa-469d-88aa-0a314987fbee'),('d164a514-35ee-46b6-b5fd-dfe1fbe031c4','2023-06-15 22:37:45.350000',_binary '\0',NULL,NULL,'2023-06-15 22:37:45.351000',0,'0367b9e9-8789-4fb9-ad99-f6f9a4225c91'),('d6bfd8bf-b078-4c20-96a2-c6dc1ab5c947','2023-06-13 15:11:06.773000',_binary '\0',NULL,NULL,'2023-06-13 15:11:06.773000',0,'f8ed1810-0915-491c-a61c-f07bc3e06493'),('e762b475-4e00-4f4a-a9de-1360dac417ae','2023-06-16 13:51:26.623000',_binary '\0',NULL,NULL,'2023-06-16 13:51:26.630000',0,'099da3cd-0b02-4243-b0de-977f2c87b4f4'),('fe46ba86-4d40-4957-8931-0c12595377e7','2023-06-13 14:57:19.074000',_binary '\0',NULL,NULL,'2023-06-20 10:23:59.653000',40,'f50f34db-3d64-49f5-a74f-e8a4e99fb6f7'),('ff3de506-3914-4225-a792-9e33433ce6cb','2023-06-16 12:40:16.074000',_binary '\0',NULL,NULL,'2023-06-16 12:40:16.075000',0,'ba2da78c-cbf1-4cf5-8aad-bd864e410568');
/*!40000 ALTER TABLE `wallet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wallet_history`
--

DROP TABLE IF EXISTS `wallet_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wallet_history` (
  `uuid` varchar(255) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `deleted_by` binary(255) DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  `amount` float NOT NULL,
  `type` int DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  KEY `FK4xfpk0sh9b1703f62qa8ge1xe` (`user_id`),
  CONSTRAINT `FK4xfpk0sh9b1703f62qa8ge1xe` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wallet_history`
--

LOCK TABLES `wallet_history` WRITE;
/*!40000 ALTER TABLE `wallet_history` DISABLE KEYS */;
INSERT INTO `wallet_history` VALUES ('0915c157-dfca-463a-aeb3-2e538838fd84','2023-06-16 20:15:41.842000',_binary '\0',NULL,NULL,'2023-06-16 20:15:41.843000',100,0,'1c659642-d34f-495c-83ad-8d55af03754a'),('0dc0ce21-546d-4f31-84b0-a3449c166654','2023-06-20 10:23:32.797000',_binary '\0',NULL,NULL,'2023-06-20 10:23:32.797000',1,0,'f50f34db-3d64-49f5-a74f-e8a4e99fb6f7'),('1ad24e17-5a9c-485b-97e3-64f61cf82de1','2023-06-20 10:23:34.627000',_binary '\0',NULL,NULL,'2023-06-20 10:23:34.627000',1,0,'f50f34db-3d64-49f5-a74f-e8a4e99fb6f7'),('1de61fdf-4a56-4b3d-b260-b123d49ab073','2023-06-20 10:23:38.796000',_binary '\0',NULL,NULL,'2023-06-20 10:23:38.796000',1,0,'f50f34db-3d64-49f5-a74f-e8a4e99fb6f7'),('21d276b7-8d57-4c81-8f82-549d54b89717','2023-06-20 10:23:30.951000',_binary '\0',NULL,NULL,'2023-06-20 10:23:30.951000',1,0,'f50f34db-3d64-49f5-a74f-e8a4e99fb6f7'),('276b1110-c493-4b2b-b584-ab9d65d36cf4','2023-06-13 14:57:19.042000',_binary '\0',NULL,NULL,'2023-06-13 14:57:19.056000',0,0,'f50f34db-3d64-49f5-a74f-e8a4e99fb6f7'),('29a5ab90-b7c8-499a-9ed0-9fb0881b4bbc','2023-06-15 10:17:30.404000',_binary '\0',NULL,NULL,'2023-06-15 10:17:30.408000',0,0,'7b022758-95b7-4c91-844f-77737fb30c3f'),('2c2610ae-0e21-43ce-ac5a-72c66700831a','2023-06-20 10:23:37.649000',_binary '\0',NULL,NULL,'2023-06-20 10:23:37.649000',1,0,'f50f34db-3d64-49f5-a74f-e8a4e99fb6f7'),('5d51e38a-1378-49cb-9218-f1a521b2c67e','2023-06-16 00:34:50.672000',_binary '\0',NULL,NULL,'2023-06-16 00:34:50.674000',0,0,'e153e06b-c5aa-469d-88aa-0a314987fbee'),('5df25550-031f-4b4c-9fa3-143c890c019c','2023-06-20 10:23:36.700000',_binary '\0',NULL,NULL,'2023-06-20 10:23:36.700000',1,0,'f50f34db-3d64-49f5-a74f-e8a4e99fb6f7'),('66568831-5f5c-4960-90d5-5975e9a717a5','2023-06-19 13:26:32.044000',_binary '\0',NULL,NULL,'2023-06-19 13:26:32.045000',0,0,'28847afb-33fa-428f-a914-63dddcf1a86b'),('6c3fa77c-add5-4065-9689-8cfe7253125a','2023-06-20 10:23:46.137000',_binary '\0',NULL,NULL,'2023-06-20 10:23:46.137000',1,0,'f50f34db-3d64-49f5-a74f-e8a4e99fb6f7'),('735bc9d0-d394-42d3-acc9-bab046d56d75','2023-06-15 19:34:16.451000',_binary '\0',NULL,NULL,'2023-06-15 19:34:16.453000',0,0,'2e2dd22a-dbbe-4665-a711-a91acbd4f3a9'),('7e78d720-1632-4fa2-a638-3a431d2abe2f','2023-06-20 10:23:35.736000',_binary '\0',NULL,NULL,'2023-06-20 10:23:35.737000',1,0,'f50f34db-3d64-49f5-a74f-e8a4e99fb6f7'),('7ea5067e-b1c3-4b2f-b426-2d59b490378c','2023-06-20 10:23:42.114000',_binary '\0',NULL,NULL,'2023-06-20 10:23:42.115000',1,0,'f50f34db-3d64-49f5-a74f-e8a4e99fb6f7'),('8418ee88-dbec-4fd6-bccf-4e17dc4c6e43','2023-06-20 10:23:54.868000',_binary '\0',NULL,NULL,'2023-06-20 10:23:54.868000',1,0,'f50f34db-3d64-49f5-a74f-e8a4e99fb6f7'),('8aa7b8a0-5782-47e2-9afe-247f6a5e2312','2023-06-15 22:37:45.313000',_binary '\0',NULL,NULL,'2023-06-15 22:37:45.323000',0,0,'0367b9e9-8789-4fb9-ad99-f6f9a4225c91'),('92e394c8-a81e-47cb-8f34-dd3688916512','2023-06-16 12:40:16.063000',_binary '\0',NULL,NULL,'2023-06-16 12:40:16.064000',0,0,'ba2da78c-cbf1-4cf5-8aad-bd864e410568'),('9cd607be-cf2c-4005-9ffa-9c6f4f62666d','2023-06-13 15:04:23.403000',_binary '\0',NULL,NULL,'2023-06-13 15:04:23.404000',1000,0,'5a72c8df-0268-4d58-a3b9-5294508998b2'),('a43ba71e-189e-49ef-b4a3-bfd289c3520e','2023-06-20 10:23:57.077000',_binary '\0',NULL,NULL,'2023-06-20 10:23:57.078000',22,1,'f50f34db-3d64-49f5-a74f-e8a4e99fb6f7'),('ac939907-b215-41dc-8998-3c424eceb533','2023-06-20 10:23:59.658000',_binary '\0',NULL,NULL,'2023-06-20 10:23:59.658000',50,0,'f50f34db-3d64-49f5-a74f-e8a4e99fb6f7'),('b6499f2e-4e2f-4393-b9c2-2fad9b6bee7b','2023-06-16 00:32:01.724000',_binary '\0',NULL,NULL,'2023-06-16 00:32:01.726000',0,0,'91a2e3ff-e189-460f-8b45-679b463a67af'),('c364edb9-f1c9-456f-9a99-4eec2d5c4057','2023-06-20 10:23:41.058000',_binary '\0',NULL,NULL,'2023-06-20 10:23:41.058000',1,0,'f50f34db-3d64-49f5-a74f-e8a4e99fb6f7'),('fa2ae571-e352-4bbe-b3b3-7a06378860ad','2023-06-20 10:23:39.967000',_binary '\0',NULL,NULL,'2023-06-20 10:23:39.967000',1,0,'f50f34db-3d64-49f5-a74f-e8a4e99fb6f7');
/*!40000 ALTER TABLE `wallet_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wishlist`
--

DROP TABLE IF EXISTS `wishlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wishlist` (
  `uuid` varchar(255) NOT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `variant_id` varchar(255) DEFAULT NULL,
  `saved_time` datetime(6) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `deleted_by` binary(255) DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  `user_info` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  KEY `FKbrbunxhtojin0ona9ui421o0r` (`user_id`),
  KEY `FKjir42yy1o8x7goxr3xflw3dqj` (`variant_id`),
  KEY `FK9nh4mm8vbic2o4mv1x55jiwwf` (`user_info`),
  CONSTRAINT `FK9nh4mm8vbic2o4mv1x55jiwwf` FOREIGN KEY (`user_info`) REFERENCES `user_info` (`uuid`),
  CONSTRAINT `FKbrbunxhtojin0ona9ui421o0r` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`uuid`),
  CONSTRAINT `FKjir42yy1o8x7goxr3xflw3dqj` FOREIGN KEY (`variant_id`) REFERENCES `variant` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wishlist`
--

LOCK TABLES `wishlist` WRITE;
/*!40000 ALTER TABLE `wishlist` DISABLE KEYS */;
INSERT INTO `wishlist` VALUES ('2ff3d4f8-4fe3-46e5-9902-42420b7f855a','0367b9e9-8789-4fb9-ad99-f6f9a4225c91','6092a864-5f79-4bf7-aa75-088acb6b3056','2023-06-15 22:48:01.499000','2023-06-15 22:48:01.505000',_binary '\0',NULL,NULL,'2023-06-15 22:48:01.506000',NULL),('315a7583-e222-4955-8300-bd3ec4f862ec','5a72c8df-0268-4d58-a3b9-5294508998b2','6092a864-5f79-4bf7-aa75-088acb6b3056','2023-06-11 16:26:24.027000','2023-06-11 16:26:24.030000',_binary '\0',NULL,NULL,'2023-06-11 16:26:24.031000',NULL),('32a170d4-a3a3-4927-9971-6a6fa14aac3c','f8ed1810-0915-491c-a61c-f07bc3e06493','2a662752-0cfc-4c98-bd3f-78be98ff3b42','2023-06-13 15:44:21.658000','2023-06-13 15:44:21.662000',_binary '\0',NULL,NULL,'2023-06-13 15:44:21.662000',NULL),('334d034e-3ce2-464d-afe2-b6feec8e9e00','db894fa7-7041-4e3e-9d10-07f38adcd7a1','2a662752-0cfc-4c98-bd3f-78be98ff3b42','2023-05-26 23:14:10.260000',NULL,_binary '\0',NULL,NULL,NULL,NULL),('371caf88-e335-49e9-9c51-305517805234','0beb04af-33fc-495a-bf3c-b2fbf1bb80d1','a9d5f85e-14cf-43bf-8b58-7ef45393b718','2023-05-27 13:59:34.632000',NULL,_binary '\0',NULL,NULL,NULL,NULL),('3ec0d83e-910d-417b-a635-2081cae3bfad','0beb04af-33fc-495a-bf3c-b2fbf1bb80d1','5eed5e2e-0cd5-4561-b443-ad6253ca3d78','2023-05-27 14:03:32.289000',NULL,_binary '\0',NULL,NULL,NULL,NULL),('49207a03-67cd-4369-ad8b-b0c5fc3879cb','2e2dd22a-dbbe-4665-a711-a91acbd4f3a9','6092a864-5f79-4bf7-aa75-088acb6b3056','2023-06-15 20:15:22.387000','2023-06-15 20:15:22.399000',_binary '\0',NULL,NULL,'2023-06-15 20:15:22.411000',NULL),('5439c4c4-bd04-4a10-b864-20b97c7fc678','5a72c8df-0268-4d58-a3b9-5294508998b2','5aaab2b0-3835-444c-a5f6-77fcb4c3dea2','2023-06-11 23:56:00.118000','2023-06-11 23:56:00.118000',_binary '\0',NULL,NULL,'2023-06-11 23:56:00.119000',NULL),('a0d0c18d-dcdd-47bd-9916-d35ade0d77aa','f50f34db-3d64-49f5-a74f-e8a4e99fb6f7','018de3fb-ed6d-4f28-96a4-bcb64fa624ce','2023-06-09 13:33:38.784000','2023-06-09 13:33:38.788000',_binary '\0',NULL,NULL,'2023-06-09 13:33:38.788000',NULL),('a466cf1d-e6d0-45e1-8448-0eaa7734f2fc','f50f34db-3d64-49f5-a74f-e8a4e99fb6f7','6092a864-5f79-4bf7-aa75-088acb6b3056','2023-06-13 15:18:19.883000','2023-06-13 15:18:19.884000',_binary '\0',NULL,NULL,'2023-06-13 15:18:19.885000',NULL),('a98bb30c-8a42-4e9a-b82c-a1a3096b318f','5a72c8df-0268-4d58-a3b9-5294508998b2','a94cbe77-4061-4762-ba85-1699ff14c84e','2023-06-05 12:24:37.303000','2023-06-05 12:24:37.304000',_binary '\0',NULL,NULL,'2023-06-05 12:24:37.304000',NULL),('b5ee5ca4-0b95-4501-abca-3ddb54439900','f8ed1810-0915-491c-a61c-f07bc3e06493','6092a864-5f79-4bf7-aa75-088acb6b3056','2023-06-13 15:08:14.850000','2023-06-13 15:08:14.850000',_binary '\0',NULL,NULL,'2023-06-13 15:08:14.851000',NULL),('c888f7cc-eb5f-4ee5-badd-371f9181e34f','06c5c9c3-e306-4665-8822-19866fceedab','a9d5f85e-14cf-43bf-8b58-7ef45393b718','2023-06-01 00:13:49.225000',NULL,_binary '\0',NULL,NULL,NULL,NULL),('cc6b7eeb-5bd2-4e3e-bb2d-ee5f37812152','5a72c8df-0268-4d58-a3b9-5294508998b2','a0a30968-3932-4080-9cf5-049eb55d84ad','2023-06-05 19:27:27.982000','2023-06-05 19:27:27.982000',_binary '\0',NULL,NULL,'2023-06-05 19:27:27.983000',NULL),('cced56c1-235f-4273-84f0-3eb20bace827','db894fa7-7041-4e3e-9d10-07f38adcd7a1','018de3fb-ed6d-4f28-96a4-bcb64fa624ce','2023-05-26 23:13:49.111000',NULL,_binary '\0',NULL,NULL,NULL,NULL),('db3d482b-054b-4d78-95e6-079e72c668f1','ba2da78c-cbf1-4cf5-8aad-bd864e410568','a94cbe77-4061-4762-ba85-1699ff14c84e','2023-06-04 17:47:40.577000','2023-06-04 17:47:40.581000',_binary '\0',NULL,NULL,'2023-06-04 17:47:40.582000',NULL),('e4d06f08-dc2b-41fd-9483-51685e1260f5','5a72c8df-0268-4d58-a3b9-5294508998b2','018de3fb-ed6d-4f28-96a4-bcb64fa624ce','2023-06-13 15:21:22.439000','2023-06-13 15:21:22.439000',_binary '\0',NULL,NULL,'2023-06-13 15:21:22.440000',NULL),('ebc96438-fc93-4f0f-995d-f5dff3095e15','f8ed1810-0915-491c-a61c-f07bc3e06493','018de3fb-ed6d-4f28-96a4-bcb64fa624ce','2023-06-02 20:42:36.477000','2023-06-02 20:42:36.482000',_binary '\0',NULL,NULL,'2023-06-02 20:42:36.483000',NULL);
/*!40000 ALTER TABLE `wishlist` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-27 20:05:11
