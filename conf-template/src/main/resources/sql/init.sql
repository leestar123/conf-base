-- --------------------------------------------------------
-- 主机:                           192.168.30.168
-- 服务器版本:                        10.1.28-MariaDB - MariaDB Server
-- 服务器操作系统:                      Linux
-- HeidiSQL 版本:                  9.3.0.4984
-- --------------------------------------------------------

-- 导出 conf 的数据库结构
CREATE DATABASE IF NOT EXISTS `conf`;
USE `conf`;


-- 导出  表 conf.conf_node_info 结构
CREATE TABLE IF NOT EXISTS `conf_node_info` (
  `node_id` int(12) NOT NULL AUTO_INCREMENT COMMENT '组件ID',
  `node_name` varchar(50) NOT NULL COMMENT '组件名称',
  `remark` varchar(200) DEFAULT NULL COMMENT '组件说明',
  `node_type` varchar(2) NOT NULL COMMENT '组件类型 0-规则组件',
  `version` varchar(12) NOT NULL COMMENT '版本号',
  `teller` varchar(50) DEFAULT NULL COMMENT '操作柜员',
  `org` varchar(50) DEFAULT NULL COMMENT '操作机构',
  `delete_flag` int(2) NOT NULL DEFAULT 0 COMMENT '删除标识 0-未删除 1-删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`node_id`),
  UNIQUE KEY `NODE_INFO_IDX1` (`node_name`)
) ENGINE=InnoDB AUTO_INCREMENT=10000000 DEFAULT CHARSET=utf8 MAX_ROWS=19999999 COMMENT='组件基础信息表';


-- 导出  表 conf.conf_node_template 结构
CREATE TABLE IF NOT EXISTS `conf_node_template` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '模板ID',
  `node_id` int(12) NOT NULL COMMENT '组件ID',
  `uid` int(12) NOT NULL COMMENT '属性/规则id',
  `teller` varchar(50) DEFAULT NULL COMMENT '操作柜员',
  `org` varchar(50) DEFAULT NULL COMMENT '操作机构',
  `delete_flag` int(2) NOT NULL DEFAULT 0 COMMENT '删除标识 0-未删除 1-删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',  
  PRIMARY KEY (`id`),
  UNIQUE KEY `NODE_TEMPLATE_IDX1` (`node_id`,`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=20000000 DEFAULT CHARSET=utf8 MAX_ROWS=29999999 COMMENT='组件模板配置';


-- 导出  表 conf.conf_product_node 结构
CREATE TABLE IF NOT EXISTS `conf_product_node` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `product_id` int(12) NOT NULL COMMENT '产品ID',
  `node_id` int(12) NOT NULL COMMENT '组件ID',
  `uid` int(12) NOT NULL COMMENT '属性/规则ID',
  `effect` varchar(12) NOT NULL COMMENT '是否生效 0-生效 1-不生效',
  `teller` varchar(2) DEFAULT NULL COMMENT '操作柜员',
  `org` varchar(50) DEFAULT NULL COMMENT '操作机构',
  `product_name` varchar(50) DEFAULT NULL COMMENT '产品名称',
  `delete_flag` int(2) NOT NULL DEFAULT 0 COMMENT '删除标识 0-未删除 1-删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',  
  PRIMARY KEY (`id`),
  UNIQUE KEY `PRODUCT_NODE_IDX` (`product_id`,`node_id`,`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=30000000 DEFAULT CHARSET=utf8 MAX_ROWS=39999999 COMMENT='产品关联组件配置';


-- 导出  表 conf.conf_rule_info 结构
CREATE TABLE IF NOT EXISTS `conf_rule_info` (
  `uid` int(12) NOT NULL AUTO_INCREMENT COMMENT '规则ID',
  `rule_name` varchar(20) NOT NULL COMMENT '规则名称',
  `rule_type` varchar(200) NOT NULL COMMENT '规则类型',
  `rule_path` varchar(200) DEFAULT NULL COMMENT '规则路径，如/aa/bb.rs.xml',
  `remark` varchar(200) DEFAULT NULL COMMENT '规则说明',
  `package_name` varchar(200) DEFAULT NULL COMMENT '执行类包名',
  `clazz` varchar(200) DEFAULT NULL COMMENT '执行类',
  `method` varchar(50) DEFAULT NULL COMMENT '执行方法',
  `param` varchar(200) DEFAULT NULL COMMENT '执行参数',
  `version` varchar(12) DEFAULT NULL COMMENT '版本号',
  `teller` varchar(12) DEFAULT NULL COMMENT '柜员号',
  `org` varchar(12) DEFAULT NULL COMMENT '机构号',
  `useable` varchar(2) NOT NULL DEFAULT 0 COMMENT '是否启用,0-启用 1-停用',
  `delete_flag` int(2) NOT NULL DEFAULT 0 COMMENT '删除标识 0-未删除 1-删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',  
  PRIMARY KEY (`uid`),
  UNIQUE KEY `RULE_INFO_IDX1` (`rule_name`)
) ENGINE=InnoDB AUTO_INCREMENT=40000000 DEFAULT CHARSET=utf8 MAX_ROWS=49999999 COMMENT='规则配置表';
