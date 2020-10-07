CREATE DATABASE  IF NOT EXISTS `bookstore` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;
USE `bookstore`;
-- MySQL dump 10.13  Distrib 8.0.13, for Win64 (x86_64)
--
-- Host: localhost    Database: bookstore
-- ------------------------------------------------------
-- Server version	8.0.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `auth_role`
--

DROP TABLE IF EXISTS `auth_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `auth_role` (
  `id` int(11) NOT NULL,
  `role_desc` varchar(255) DEFAULT NULL,
  `role_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_7nny9jtiql7w5waa79gwppyhe` (`role_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_role`
--

LOCK TABLES `auth_role` WRITE;
/*!40000 ALTER TABLE `auth_role` DISABLE KEYS */;
INSERT INTO `auth_role` VALUES (1,'Admin','ROLE_ADMIN'),(2,'User','ROLE_USER'),(3,'Editor','ROLE_EDITOR');
/*!40000 ALTER TABLE `auth_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `books`
--

DROP TABLE IF EXISTS `books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `books` (
  `id` varchar(128) NOT NULL,
  `author` varchar(255) NOT NULL,
  `catalog_id` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `dimensions` varchar(255) DEFAULT NULL,
  `format` varchar(255) DEFAULT NULL,
  `isbn` varchar(255) NOT NULL,
  `language` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `original_name` varchar(255) NOT NULL,
  `original_publication` varchar(255) DEFAULT NULL,
  `page_count` int(11) DEFAULT NULL,
  `publication_year` varchar(255) DEFAULT NULL,
  `publisher` varchar(255) DEFAULT NULL,
  `weight` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ccrtv77cbx5yku2fi62u6nwm` (`catalog_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `books`
--

LOCK TABLES `books` WRITE;
/*!40000 ALTER TABLE `books` DISABLE KEYS */;
INSERT INTO `books` VALUES ('_gJmqy','J.K. Rowling','303820','','129×198 mm','PAPERBACK','9783551354068','DE','Harry Potter und der Halbblutprinz','Harry Potter and the Half-Blood Prince','2005',656,'2010','Carlsen Verlag',522),('5BMdH_','J.K. Rowling','202150','','135×210 mm','HARDCOVER','9788055143880','SK','Harry Potter a Polovičný princ (Kniha 6)','Harry Potter and the Half-Blood Prince','2005',544,'2015','Ikar',488),('A50LyG','J.K. Rowling','441259','','155×220 mm','HARDCOVER','9783551557469','DE','Harry Potter und der Halbblutprinz','Harry Potter and the Half-Blood Prince','2005',640,'2018','Carlsen Verlag',799),('efwjVo','J.K. Rowling','19320','','128×196 mm','PAPERBACK','9780747584681','EN','Harry Potter and the Half-Blood Prince','Harry Potter and the Half-Blood Prince','2005',607,'2005','Bloomsbury',739),('fpCjjl','J.K. Rowling','326617','','135×205 mm','PAPERBACK','9781338299199','EN','Harry Potter and the Half-Blood Prince','Harry Potter and the Half-Blood Prince','2005',688,'2018','Scholastic',582),('HaLWGp','J.K. Rowling','214975','','129×198 mm','HARDCOVER','9781408865446','EN','Harry Potter and the Half-Blood Prince','Harry Potter and the Half-Blood Prince','2005',608,'2015','Bloomsbury',548),('HgfAbZ','J.K. Rowling','157822','','128×198 mm','PAPERBACK','9781408835012','EN','Harry Potter and the Half-Blood Prince','Harry Potter and the Half-Blood Prince','2005',608,'2013','Bloomsbury',410),('ho--Y-','J.K. Rowling','202149','','125×200 mm','PAPERBACK','9788055143125','SK','Harry Potter a Polovičný princ (Kniha 6)','Harry Potter and the Half-Blood Prince','2005',544,'2015','Ikar',402),('m8TfzY','J.K. Rowling','186271','','129×198 mm','PAPERBACK','9781408855706','EN','Harry Potter and the Half-Blood Prince','Harry Potter and the Half-Blood Prince','2005',560,'2014','Bloomsbury',456),('O5pESl','J.K. Rowling','22867','','125×200 mm','HARDCOVER','8055112207','SK','Harry Potter a Polovičný princ (Kniha 6)','Harry Potter and the Half-Blood Prince','2005',544,'2005','Ikar',501),('UnU0cw','J.K. Rowling','179246','','134×203 mm','PAPERBACK','9780545582995','EN','Harry Potter and the Half-Blood Prince','Harry Potter and the Half-Blood Prince','2005',652,'2013','Scholastic',574);
/*!40000 ALTER TABLE `books` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `books_categories`
--

DROP TABLE IF EXISTS `books_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `books_categories` (
  `book_id` varchar(128) NOT NULL,
  `category_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_exgrjpua85xbymkj36wom3dms` (`category_id`),
  KEY `FKmsuoucvyyccli3f6u59co41cq` (`book_id`),
  CONSTRAINT `FKkksgsy7k6sy1hr55se8mpktvb` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
  CONSTRAINT `FKmsuoucvyyccli3f6u59co41cq` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `books_categories`
--

LOCK TABLES `books_categories` WRITE;
/*!40000 ALTER TABLE `books_categories` DISABLE KEYS */;
/*!40000 ALTER TABLE `books_categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `category` (
  `id` bigint(20) NOT NULL,
  `caption` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2y94svpmqttx80mshyny85wqr` (`parent_id`),
  CONSTRAINT `FK2y94svpmqttx80mshyny85wqr` FOREIGN KEY (`parent_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (19055,'Mysteriózna, dobrodružná','mystery_adventure',96329),(26038,'Anglický jazyk','english',61498),(32965,'Pre deti a mládež','kids',70643),(37478,'Romány','novel',39558),(39558,'Beletria','fiction',52503),(42079,'Sci-fi, fantasy','scify_fantasy',32965),(44152,'Sci-fi, fantasy, horory','scify_fantasy',39558),(49623,'Fantasy','fantasy',44152),(52503,'Knihy','books',NULL),(55181,'Beletria - anglicky','fiction',26038),(61498,'Cudzojazyčná literatúra','foreign',52503),(65877,'Pre deti a mládež','kids',55181),(70643,'Beletria - nemecky','fiction',94751),(83801,'Sci-fi, fantasy','scify_fantasy',65877),(94751,'Nemecký jazyk','german',61498),(96229,'Deti a mládež','kids',52503),(96329,'Beletria','fiction',96229);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (39);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `active` bit(1) NOT NULL,
  `email` varchar(255) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`),
  UNIQUE KEY `UK_r43af9ap4edm43mmtq01oddj6` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (4119596900129935781,_binary '','test@test.sk',_binary '','$2a$10$TAw7f5I/2b1ksHY5Og43s.zw7bvNyrqWLI/GKIcwU7ft2ZDOZhhXa','user');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_roles`
--

DROP TABLE IF EXISTS `users_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `users_roles` (
  `user_id` bigint(20) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKltn3jbibvbsrrtudxdiv60l5e` (`role_id`),
  CONSTRAINT `FK2o0jvgh89lemvvo17cbqvdxaa` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKltn3jbibvbsrrtudxdiv60l5e` FOREIGN KEY (`role_id`) REFERENCES `auth_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_roles`
--

LOCK TABLES `users_roles` WRITE;
/*!40000 ALTER TABLE `users_roles` DISABLE KEYS */;
INSERT INTO `users_roles` VALUES (4119596900129935781,1);
/*!40000 ALTER TABLE `users_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-10-08  0:21:30
