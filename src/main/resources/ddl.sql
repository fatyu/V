
DROP TABLE IF EXISTS `z_book_index`;

CREATE TABLE `z_book_index` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8 COMMENT='索引地址';

/*Table structure for table `z_book_info` */

DROP TABLE IF EXISTS `z_book_info`;

CREATE TABLE `z_book_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(256) NOT NULL COMMENT '标题',
  `remark` text COMMENT '介绍',
  `type` tinyint(4) DEFAULT NULL COMMENT '1直接下载 2微信获取下载码',
  `url` varchar(128) DEFAULT NULL COMMENT '下载地址',
  `source_site_id` varchar(32) DEFAULT NULL COMMENT '图书源站图书id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20240 DEFAULT CHARSET=utf8 COMMENT='书籍信息';

/*Table structure for table `z_book_url` */

DROP TABLE IF EXISTS `z_book_url`;

CREATE TABLE `z_book_url` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `book_id` bigint(20) NOT NULL COMMENT '书籍id',
  `url` varchar(1024) DEFAULT NULL COMMENT '下载地址',
  `wx_keyword` varchar(6) DEFAULT NULL COMMENT '微信关键字',
  `baidu_password` varchar(12) DEFAULT NULL COMMENT '百度下载码',
  `fatyu_baidu_url` varchar(512) DEFAULT NULL COMMENT '不需要密码的百度下载地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=297432 DEFAULT CHARSET=utf8 COMMENT='下载地址';

/*Table structure for table `z_site` */

DROP TABLE IF EXISTS `z_site`;

CREATE TABLE `z_site` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) NOT NULL COMMENT '网址名称',
  `img` varchar(256) DEFAULT NULL COMMENT '网址官方图片',
  `url` varchar(512) DEFAULT NULL COMMENT '网址',
  `category_id` bigint(20) DEFAULT NULL COMMENT '类别id',
  `recommend` tinyint(4) DEFAULT NULL COMMENT '0随便看看 1推荐 2强烈推荐 3必备',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13719 DEFAULT CHARSET=utf8 COMMENT='网址';

/*Table structure for table `z_site_category` */

DROP TABLE IF EXISTS `z_site_category`;

CREATE TABLE `z_site_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) NOT NULL COMMENT '类别名称',
  `pid` tinyint(4) DEFAULT NULL COMMENT '父类别id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='类别';
