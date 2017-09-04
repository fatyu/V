/*
SQLyog Ultimate v11.13 (64 bit)
MySQL - 5.6.27-log : Database - adm
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`adm` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `adm`;

/*Table structure for table `z_books` */

DROP TABLE IF EXISTS `z_books`;

CREATE TABLE `z_books` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(256) NOT NULL COMMENT '标题',
  `remark` text COMMENT '介绍',
  `type` tinyint(4) DEFAULT NULL COMMENT '1直接下载 2微信获取下载码',
  `url` varchar(512) DEFAULT NULL COMMENT '下载地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20157 DEFAULT CHARSET=utf8 COMMENT='书籍信息';

/*Table structure for table `z_download_url` */

DROP TABLE IF EXISTS `z_download_url`;

CREATE TABLE `z_download_url` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `book_id` bigint(20) NOT NULL COMMENT '书籍id',
  `url` varchar(1024) DEFAULT NULL COMMENT '下载地址',
  `wx_keyword` varchar(6) DEFAULT NULL COMMENT '微信关键字',
  `baidu_password` varchar(12) DEFAULT NULL COMMENT '百度下载码',
  `fatyu_baidu_url` varchar(512) DEFAULT NULL COMMENT '不需要密码的百度下载地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=297432 DEFAULT CHARSET=utf8 COMMENT='下载地址';

/*Table structure for table `z_url_index` */

DROP TABLE IF EXISTS `z_url_index`;

CREATE TABLE `z_url_index` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8 COMMENT='索引地址';

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
