-- MySQL dump 10.13  Distrib 8.0.35, for Win64 (x86_64)
--
-- Host: localhost    Database: the_horror_database
-- ------------------------------------------------------
-- Server version	8.0.35

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
-- Current Database: `the_horror_database`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `the_horror_database` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `the_horror_database`;

--
-- Table structure for table `favorite`
--

DROP TABLE IF EXISTS `favorite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `favorite` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` date NOT NULL,
  `movie_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK728jcsp8okxo8km8tly6q8lku` (`movie_id`),
  KEY `FKh3f2dg11ibnht4fvnmx60jcif` (`user_id`),
  CONSTRAINT `FK728jcsp8okxo8km8tly6q8lku` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`id`),
  CONSTRAINT `FKh3f2dg11ibnht4fvnmx60jcif` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `favorite`
--

LOCK TABLES `favorite` WRITE;
/*!40000 ALTER TABLE `favorite` DISABLE KEYS */;
INSERT INTO `favorite` VALUES (1,'2024-09-22',1,5),(2,'2025-01-05',2,5),(3,'2025-01-05',3,5),(4,'2025-01-05',4,5),(5,'2025-01-05',4,5),(6,'2025-01-05',5,5);
/*!40000 ALTER TABLE `favorite` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `genre`
--

DROP TABLE IF EXISTS `genre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `genre` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `genre`
--

LOCK TABLES `genre` WRITE;
/*!40000 ALTER TABLE `genre` DISABLE KEYS */;
INSERT INTO `genre` VALUES (1,'Gore'),(2,'Monstres'),(3,'Possession'),(4,'Psychologique'),(5,'Slasher'),(6,'Sorcellerie'),(7,'Surnaturel'),(8,'Survival'),(9,'Zombie'),(10,'Slasher'),(11,'Slasher'),(12,'Slasher'),(13,'Slasher'),(14,'Monstres'),(15,'Monstres'),(16,'Zombie'),(17,'Gore'),(18,'Gore'),(19,'Monstres'),(20,'Gore'),(21,'Psychologique');
/*!40000 ALTER TABLE `genre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movie`
--

DROP TABLE IF EXISTS `movie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movie` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `country` varchar(255) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `created_by` int NOT NULL,
  `director` varchar(100) NOT NULL,
  `poster_url` varchar(500) DEFAULT NULL,
  `release_year` int NOT NULL,
  `status` enum('APPROVED','PENDING','REFUSED') NOT NULL,
  `synopsis` text,
  `title` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie`
--

LOCK TABLES `movie` WRITE;
/*!40000 ALTER TABLE `movie` DISABLE KEYS */;
INSERT INTO `movie` VALUES (1,'├ëtats-Unis','2024-09-22 11:25:00.000000',1,'John Carpenter','https://fr.web.img4.acsta.net/pictures/18/09/24/16/19/3060808.jpg',1978,'APPROVED','Fifteen years after murdering his sister on Halloween night, Michael Myers escapes from a mental hospital and returns to terrorize his hometown.','Halloween'),(2,'├ëtats-Unis','2024-09-22 11:10:00.000000',1,'Scott Derrickson','https://fr.web.img5.acsta.net/medias/nmedia/18/91/03/05/20267340.jpg',2012,'APPROVED','A true-crime writer discovers a cache of disturbing home videos that hint at the presence of a deadly supernatural force.','Sinister'),(3,'├ëtats-Unis','2024-09-22 11:05:00.000000',5,'Damien Leone','https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcQ5k0W9MOPwc-RMsmCer-vjbx2kE_RgRwT1NR_edRP-kpm1QlnV',2016,'APPROVED','La nuit d\'Halloween, deux femmes croisent la route de Art the Clown, un tueur sadique.','Terrifier'),(4,'├ëtats-Unis','2024-09-22 11:15:00.000000',1,'Stanley Kubrick','https://upload.wikimedia.org/wikipedia/en/1/1d/The_Shining_%281980%29_U.K._release_poster_-_The_tide_of_terror_that_swept_America_IS_HERE.jpg',1980,'APPROVED','A family heads to an isolated hotel for the winter where a sinister presence influences the father into violence, while his psychic son sees horrific forebodings.','The Shining'),(5,'United Kingdom','2024-09-22 11:20:00.000000',1,'Richard Donner','https://upload.wikimedia.org/wikipedia/en/1/1d/Omen_ver4.jpg',1976,'APPROVED','An American ambassador adopts a mysterious child, who may be the Antichrist.','The Omen'),(6,'├ëtats-Unis','2024-08-24 11:54:40.477870',1,'William Friedkin','https://m.media-amazon.com/images/I/81g32srM67L._AC_UF894,1000_QL80_.jpg',1973,'APPROVED','A young girl becomes possessed by a malevolent entity, leading her desperate mother to seek the help of two priests. Together, they confront the darkest forces in a battle for her soul.','The Exorcist'),(7,'├ëtats-Unis','2024-08-22 16:07:02.000000',1,'Clive Barker','https://fr.web.img4.acsta.net/c_310_420/medias/nmedia/18/63/14/44/18946269.jpg',1987,'APPROVED','Frank Cotton, a man in search of unknown pleasures, acquires a mysterious box that leads him to a hell filled with suffering and pleasure.','Hellraiser'),(8,'├ëtats-Unis','2024-09-22 11:00:00.000000',5,'Ari Aster','https://fr.web.img5.acsta.net/c_310_420/pictures/19/06/21/16/39/3513823.jpg',2019,'APPROVED','A grieving woman joins her boyfriend on a trip to a remote Swedish village for a rare midsummer festival, only to become part of disturbing rituals.','Midsommar'),(9,'├ëtats-Unis','2024-09-22 11:00:00.000000',1,'Wes Craven','https://m.media-amazon.com/images/I/41-lNiXQrHS._AC_UY218_.jpg',1996,'APPROVED','A teenage girl is targeted by a masked killer who plays a deadly game of horror movie trivia, leading to a series of gruesome murders.','Scream'),(10,'├ëtats-Unis','2024-09-22 11:00:00.000000',1,'Daniel Myrick, Eduardo S├ínchez','https://m.media-amazon.com/images/I/21Ztc9NOvwL._AC_UY218_.jpg',1999,'APPROVED','Three film students disappear in the woods while investigating a local legend, leaving behind only their footage.','Le projet Blair Witch'),(11,'├ëtats-Unis','2024-09-22 11:00:00.000000',1,'James DeMonaco','https://www.ecranlarge.com/content/uploads/2020/08/tjsezdkkw3kirewb9fxtlzjvqgy-867.jpg',2013,'APPROVED','In a dystopian future, one night a year, all crime is legal, leading to chaos and violence as a family fights to survive.','American Nightmare'),(12,'Japan','2024-09-22 11:00:00.000000',5,'Stephen Biro','https://m.media-amazon.com/images/M/MV5BNDAzNTIzMDQ5OF5BMl5BanBnXkFtZTgwOTk1NDE0MzE@._V1_FMjpg_UX1000_.jpg',2014,'APPROVED','A disturbing portrayal of human suffering and degradation, exploring the limits of endurance in a brutal setting.','American Guinea Pig'),(13,'├ëtats-Unis','2024-09-22 11:00:00.000000',1,'Roman Polanski','https://m.media-amazon.com/images/I/710Lc76py+L._AC_UY218_.jpg',1968,'APPROVED','A young couple becomes pregnant after moving into a gothic apartment building, where strange occurrences lead to a chilling revelation.','Rosemary\'s Baby'),(14,'United States','2024-09-22 11:00:00.000000',1,'Jonathan Demme','https://m.media-amazon.com/images/I/71QvfbMENEL._AC_UY218_.jpg',1991,'APPROVED','A young FBI trainee must seek the help of an imprisoned cannibalistic serial killer to catch another serial killer at large.','Le silence des agneaux'),(15,'United States','2024-09-22 11:00:00.000000',1,'Rob Zombie','https://m.media-amazon.com/images/I/710MrJ5lIGS._AC_UF894,1000_QL80_.jpg',2003,'APPROVED','A group of friends set out to find a legendary figure and end up in a horrific encounter with a family of sadistic killers.','La maison des 1000 morts'),(16,'Denmark','2024-09-22 11:00:00.000000',1,'Lars von Trier','https://m.media-amazon.com/images/I/61ngzH38DNL._AC_UY218_.jpg',2009,'APPROVED','A grieving couple retreats to their cabin in the woods, where they confront their deepest fears and despair in disturbing ways.','Antichrist'),(32,'Afghanistan','2024-12-21 17:24:59.977146',5,'ccc','https://upload.wikimedia.org/wikipedia/en/1/1d/The_Shining_%281980%29_U.K._release_poster_-_The_tide_of_terror_that_swept_America_IS_HERE.jpg',1989,'APPROVED','ssss','666'),(33,'Afghanistan','2025-01-06 12:42:35.738008',5,'caca','https://fr.web.img4.acsta.net/pictures/18/09/24/16/19/3060808.jpg',1989,'APPROVED','test','aca'),(34,'Albanie','2025-01-07 14:26:45.443315',5,'ccc','https://fr.web.img4.acsta.net/c_310_420/medias/nmedia/18/96/22/27/20453889.jpg',1989,'APPROVED','alert','Testalert'),(35,'├ëtats-Unis','2025-01-07 14:36:32.065532',5,'Damien Leone','https://fr.web.img4.acsta.net/c_310_420/img/59/21/5921ca09d32d7ebfeeceaf2ad1352edc.jpg',2024,'APPROVED','Apr├¿s avoir surv├®cu au massacre d\'Halloween perp├®tr├® par Art Le Clown, Sienna et son fr├¿re tentent de reconstruire leur vie. Alors que les f├¬tes de fin d\'ann├®e approchent, ils s\'efforcent de laisser derri├¿re eux les horreurs pass├®es. Mais au moment o├╣ ils se croyaient enfin ├á l\'abri, Art refait surface, bien d├®cid├® ├á transformer No├½l en un v├®ritable cauchemar.','Terrifier 3'),(36,'├ëtats-Unis','2025-01-08 14:27:24.217463',5,'Damien Leone','https://fr.web.img6.acsta.net/c_310_420/pictures/19/09/20/09/13/3220140.jpg',2024,'APPROVED','Apr├¿s avoir surv├®cu au massacre d\'Halloween perp├®tr├® par Art Le Clown, Sienna et son fr├¿re tentent de reconstruire leur vie. Alors que les f├¬tes de fin d\'ann├®e approchent, ils s\'efforcent de laisser derri├¿re eux les horreurs pass├®es. Mais au moment o├╣ ils se croyaient enfin ├á l\'abri, Art refait surface, bien d├®cid├® ├á transformer No├½l en un v├®ritable cauchemar.','Terrifier 3');
/*!40000 ALTER TABLE `movie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movie_genre`
--

DROP TABLE IF EXISTS `movie_genre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movie_genre` (
  `movie_id` bigint NOT NULL,
  `genre_id` bigint NOT NULL,
  KEY `FK86p3roa187k12avqfl28klp1q` (`genre_id`),
  KEY `FKp6vjabv2e2435at1hnuxg64yv` (`movie_id`),
  CONSTRAINT `FK86p3roa187k12avqfl28klp1q` FOREIGN KEY (`genre_id`) REFERENCES `genre` (`id`),
  CONSTRAINT `FKp6vjabv2e2435at1hnuxg64yv` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie_genre`
--

LOCK TABLES `movie_genre` WRITE;
/*!40000 ALTER TABLE `movie_genre` DISABLE KEYS */;
INSERT INTO `movie_genre` VALUES (3,1),(12,1),(7,2),(4,3),(5,3),(6,3),(8,4),(13,4),(14,4),(16,4),(1,5),(2,7),(9,5),(10,6),(11,8),(15,1);
/*!40000 ALTER TABLE `movie_genre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `upload`
--

DROP TABLE IF EXISTS `upload`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `upload` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `category` varchar(50) NOT NULL,
  `uploaded_at` datetime(6) NOT NULL,
  `file_name` varchar(255) NOT NULL,
  `file_path` varchar(500) NOT NULL,
  `movie_id` bigint DEFAULT NULL,
  `uploaded_by` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKljkhk6g547r769jd0tk67enu8` (`movie_id`),
  KEY `FKl8k42ff3hhv8qu20v551w0et0` (`uploaded_by`),
  CONSTRAINT `FKl8k42ff3hhv8qu20v551w0et0` FOREIGN KEY (`uploaded_by`) REFERENCES `user` (`id`),
  CONSTRAINT `FKljkhk6g547r769jd0tk67enu8` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `upload`
--

LOCK TABLES `upload` WRITE;
/*!40000 ALTER TABLE `upload` DISABLE KEYS */;
/*!40000 ALTER TABLE `upload` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `avatar_url` varchar(500) DEFAULT NULL,
  `birthdate` date DEFAULT NULL,
  `created_at` date DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,NULL,'1990-01-01',NULL,'prisonmike@mifflin.com','Schrut','Michael','$2a$10$DZ/CaiaHCprHF.Z.PjB3LeNyOuFp2vLN7HzZYNPH4H/ts9EZk9lHe',NULL),(2,NULL,'1990-01-01',NULL,'sad@stan.fr','Schrut','Dwight','$2a$10$ffHWAJSfNr.kqz7s9bjNSeJu.xG./OZ1/RlcrBE5V1L/iHRzkjRfm','Tom'),(4,'https://i.scdn.co/image/ab67616d0000b27377ad9aa382901937bac6108b','2024-12-17','2024-12-23','sad@satan.fr','Read','James','$2a$10$b6cw.uwRVNzPWqVJ1hfNAeSXhzNK1vVZuTFDPitZxLaCNwVYu3Yga',NULL),(5,'https://www.metal-archives.com/images/1/2/9/5/1295477.jpg?5031','1990-01-01','2024-12-23','revenge@war.com','Reed','J','$2a$10$4rpeclOD42z35I/qwHPavOYyQ.djEl1jDJPGDAQZvGPUc6GHutu4m',NULL),(9,'https://www.metal-archives.com/images/8/0/4/6/80461_artist.jpg?364','1989-12-23','2024-07-21','deviant@infaustus.com','Von Blakk','Deviant','$2a$10$l3asWENxM5RbjcQx4mzriuVKcFEu0Ie8scxYXpSpCnRoDdOXoj9ea',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_review`
--

DROP TABLE IF EXISTS `user_review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_review` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` date DEFAULT NULL,
  `rating` smallint NOT NULL,
  `review` varchar(1000) NOT NULL,
  `movie_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKneiwcr8bijvdhunu580ns8crh` (`movie_id`),
  KEY `FKk4378yigvs29qpwh8ughgs4gk` (`user_id`),
  CONSTRAINT `FKk4378yigvs29qpwh8ughgs4gk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKneiwcr8bijvdhunu580ns8crh` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_review`
--

LOCK TABLES `user_review` WRITE;
/*!40000 ALTER TABLE `user_review` DISABLE KEYS */;
INSERT INTO `user_review` VALUES (1,NULL,5,'Un tr├¿s bon film',1,5),(2,NULL,1,'Bof',2,5),(3,NULL,4,'456789',3,5),(4,NULL,4,'Un r├®gal pour les passion├®s de cin├®ma gore. Vivement le deux !',3,4),(6,NULL,5,'Le meilleur film de tout les temps !!',7,5),(7,NULL,5,'Insane !!',8,5),(9,NULL,5,'Top !',36,5),(11,NULL,5,'Un classique',6,5);
/*!40000 ALTER TABLE `user_review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles` (
  `user_id` bigint NOT NULL,
  `roles` varchar(255) DEFAULT NULL,
  KEY `FK55itppkw3i07do3h7qoclqd4k` (`user_id`),
  CONSTRAINT `FK55itppkw3i07do3h7qoclqd4k` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (1,'ROLE_USER'),(2,'ROLE_USER'),(4,'ROLE_ADMIN'),(5,'ROLE_USER'),(6,'ROLE_USER'),(7,'ROLE_USER'),(8,'ROLE_USER'),(9,'ROLE_ADMIN');
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-02-06 15:45:33
