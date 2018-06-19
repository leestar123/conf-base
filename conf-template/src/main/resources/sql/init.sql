-- --------------------------------------------------------
-- 主机:                           192.168.30.168
-- 服务器版本:                        10.1.28-MariaDB - MariaDB Server
-- 服务器操作系统:                      Linux
-- HeidiSQL 版本:                  9.3.0.4984
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出 conf 的数据库结构
CREATE DATABASE IF NOT EXISTS `conf` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `conf`;


-- 导出  表 conf.conf_node_info 结构
CREATE TABLE IF NOT EXISTS `conf_node_info` (
  `node_id` int(12) NOT NULL COMMENT '组件ID',
  `node_name` varchar(50) NOT NULL COMMENT '组件名称',
  `remark` varchar(200) DEFAULT NULL COMMENT '组件说明',
  `node_type` varchar(2) NOT NULL COMMENT '组件类型 0-规则组件',
  `version` varchar(12) NOT NULL COMMENT '版本号',
  `teller` varchar(50) DEFAULT NULL COMMENT '操作柜员',
  `org` varchar(50) DEFAULT NULL COMMENT '操作机构',
  PRIMARY KEY (`node_id`),
  UNIQUE KEY `NODE_INFO_IDX1` (`node_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组件基础信息表';

-- 正在导出表  conf.conf_node_info 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `conf_node_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `conf_node_info` ENABLE KEYS */;


-- 导出  表 conf.conf_node_template 结构
CREATE TABLE IF NOT EXISTS `conf_node_template` (
  `id` int(12) NOT NULL COMMENT '模板ID',
  `node_id` int(12) NOT NULL COMMENT '组件ID',
  `uid` int(12) NOT NULL COMMENT '属性/规则id',
  `teller` varchar(50) DEFAULT NULL COMMENT '操作柜员',
  `org` varchar(50) DEFAULT NULL COMMENT '操作机构',
  PRIMARY KEY (`id`),
  UNIQUE KEY `NODE_TEMPLATE_IDX1` (`node_id`,`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组件模板配置';

-- 正在导出表  conf.conf_node_template 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `conf_node_template` DISABLE KEYS */;
/*!40000 ALTER TABLE `conf_node_template` ENABLE KEYS */;


-- 导出  表 conf.conf_product_node 结构
CREATE TABLE IF NOT EXISTS `conf_product_node` (
  `id` int(12) NOT NULL COMMENT '主键ID',
  `product_id` int(12) NOT NULL COMMENT '产品ID',
  `node_id` int(12) NOT NULL COMMENT '组件ID',
  `uid` int(12) NOT NULL COMMENT '属性/规则ID',
  `teller` int(12) DEFAULT NULL COMMENT '操作柜员',
  `org` int(12) DEFAULT NULL COMMENT '操作机构',
  PRIMARY KEY (`id`),
  UNIQUE KEY `PRODUCT__NODE_IDX` (`product_id`,`node_id`,`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品关联组件配置';

-- 正在导出表  conf.conf_product_node 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `conf_product_node` DISABLE KEYS */;
/*!40000 ALTER TABLE `conf_product_node` ENABLE KEYS */;


-- 导出  表 conf.conf_rule_info 结构
CREATE TABLE IF NOT EXISTS `conf_rule_info` (
  `uid` int(12) NOT NULL COMMENT '规则ID',
  `name` varchar(20) NOT NULL COMMENT '规则名称',
  `remark` varchar(200) DEFAULT NULL COMMENT '规则说明',
  `clazz` varchar(200) NOT NULL COMMENT '执行类',
  `method` varchar(50) NOT NULL COMMENT '执行方法',
  `param` varchar(200) DEFAULT NULL COMMENT '执行参数',
  `version` varchar(12) NOT NULL COMMENT '版本号',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `RULE_INFO_IDX1` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='规则配置表';

-- 正在导出表  conf.conf_rule_info 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `conf_rule_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `conf_rule_info` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
