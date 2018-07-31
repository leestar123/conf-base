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

-- 导出  表 demo.conf_flow_info 结构
DROP TABLE IF EXISTS `conf_flow_info`;
CREATE TABLE IF NOT EXISTS `conf_flow_info` (
  `flow_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '流程ID',
  `step_id` int(11) NOT NULL COMMENT '阶段ID',
  `flow_name` varchar(50) NOT NULL COMMENT '流程名称',
  `flow_path` varchar(50) NOT NULL COMMENT '流程路径',
  `remark` varchar(2000) DEFAULT NULL COMMENT '流程说明',
  `teller` varchar(50) NOT NULL COMMENT '添加柜员',
  `org` varchar(50) NOT NULL COMMENT '添加机构',
  `delete_flag` int(2) NOT NULL DEFAULT 0 COMMENT '删除标识 0-未删除 1-删除',  
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`flow_id`),
  UNIQUE KEY `FLOW_INFO_IDX1` (`step_id`,`flow_name`)
) ENGINE=InnoDB AUTO_INCREMENT=50000000 DEFAULT CHARSET=utf8 MAX_ROWS=59999999 CHARSET=utf8;

-- 正在导出表  demo.conf_flow_info 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `conf_flow_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `conf_flow_info` ENABLE KEYS */;


-- 导出  表 demo.conf_invok_info 结构
DROP TABLE IF EXISTS `conf_invok_info`;
CREATE TABLE IF NOT EXISTS `conf_invok_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `service` varchar(50) NOT NULL COMMENT '服务名称',
  `request` varchar(2000) NOT NULL COMMENT '请求报文',
  `success` int(2) NOT NULL DEFAULT '1' COMMENT '是否成功 0-成功 1-失败',
  `detail` varchar(2000) DEFAULT NULL COMMENT '调用明细',
  `teller` varchar(50) NOT NULL COMMENT '调用柜员',
  `org` varchar(50) NOT NULL COMMENT '调用机构',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '调用时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '结束时间',
  PRIMARY KEY (`id`),
  KEY `INVOK_INFO_IDX1` (`service`)
) ENGINE=InnoDB AUTO_INCREMENT=60000000 DEFAULT CHARSET=utf8 MAX_ROWS=69999999 CHARSET=utf8;

-- 正在导出表  demo.conf_invok_info 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `conf_invok_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `conf_invok_info` ENABLE KEYS */;


-- 导出  表 demo.conf_operate_info 结构
DROP TABLE IF EXISTS `conf_operate_info`;
CREATE TABLE IF NOT EXISTS `conf_operate_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `serial_no` varchar(50) NOT NULL COMMENT '操作流水',
  `remark` varchar(200) NOT NULL COMMENT '操作描述',
  `operate_type` int(2) NOT NULL COMMENT '操作类型 1-增加 2-删除 3-修改',
  `operate_module` int(2) NOT NULL COMMENT '操作模块 1-组件 2-节点 3-阶段 4-流程 5-产品',
  `module_id` int(11) DEFAULT NULL COMMENT '模块ID',
  `module_name` varchar(50) DEFAULT NULL COMMENT '模块名称',
  `request` varchar(2000) NOT NULL COMMENT '请求报文',
  `success` int(2) NOT NULL DEFAULT '1' COMMENT '是否成功 0-成功 1-失败',
  `teller` varchar(50) NOT NULL COMMENT '调用柜员',
  `org` varchar(50) NOT NULL COMMENT '调用机构', 
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `OPERATE_INFO_IDX1` (`operate_type`,`operate_module`,`module_id`)
) ENGINE=InnoDB AUTO_INCREMENT=70000000 DEFAULT CHARSET=utf8 MAX_ROWS=79999999 CHARSET=utf8;

-- 正在导出表  demo.conf_operate_info 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `conf_operate_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `conf_operate_info` ENABLE KEYS */;


-- 导出  表 demo.conf_product_step 结构
DROP TABLE IF EXISTS `conf_product_step`;
CREATE TABLE IF NOT EXISTS `conf_product_step` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `product_id` int(11) DEFAULT NULL COMMENT '产品ID',
  `product_name` varchar(50) DEFAULT NULL COMMENT '产品名称',
  `business_type` varchar(50) DEFAULT NULL COMMENT '业务类型',
  `step_id` int(11) NOT NULL COMMENT '阶段ID',
  `flow_id` int(11) NOT NULL COMMENT '流程ID',
  `teller` varchar(50) NOT NULL COMMENT '操作柜员',
  `org` varchar(50) NOT NULL COMMENT '操作机构',
  `delete_flag` int(2) NOT NULL DEFAULT 0 COMMENT '删除标识 0-未删除 1-删除',  
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `PRODUCT_STEP_IDX1` (`product_id`,`business_type`,`step_id`,`flow_id`)
) ENGINE=InnoDB AUTO_INCREMENT=80000000 DEFAULT CHARSET=utf8 MAX_ROWS=89999999 CHARSET=utf8;

-- 正在导出表  demo.conf_product_step 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `conf_product_step` DISABLE KEYS */;
/*!40000 ALTER TABLE `conf_product_step` ENABLE KEYS */;


-- 导出  表 demo.conf_step_info 结构
DROP TABLE IF EXISTS `conf_step_info`;
CREATE TABLE IF NOT EXISTS `conf_step_info` (
  `step_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '阶段ID',
  `node_id` int(11) NOT NULL COMMENT '节点ID',
  `step_name` varchar(50) NOT NULL COMMENT '阶段名称',
  `remark` varchar(50) DEFAULT NULL COMMENT '阶段说明',
  `teller` varchar(50) NOT NULL COMMENT '操作柜员',
  `org` varchar(50) NOT NULL COMMENT '操作机构',
  `delete_flag` int(2) NOT NULL DEFAULT 0 COMMENT '删除标识 0-未删除 1-删除',  
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`step_id`),
  UNIQUE KEY `NODE_ID_IDX1` (`step_id`,`node_id`)
) ENGINE=InnoDB AUTO_INCREMENT=90000000 DEFAULT CHARSET=utf8 MAX_ROWS=99999999 CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `qualification_review_info` (
  `QUALIFICATION_REVIEW_ID` int(12) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `TRAN_SEQ_NO` varchar(32) DEFAULT NULL COMMENT '业务流水号',
  `PROCESS_ID` varchar(20) DEFAULT NULL COMMENT '流程编号',
  `CUST_NO` varchar(100) DEFAULT NULL COMMENT '客户号',
  `CUST_NAME` varchar(32) DEFAULT NULL COMMENT '客户名称',
  `CARD_NO` varchar(20) DEFAULT NULL COMMENT '证件号',
  `CARD_TYPE` varchar(2) DEFAULT NULL COMMENT '证件类型',
  `TELEPHONE_NO` varchar(11) DEFAULT NULL COMMENT '手机号',
  `IS_CREDIT_AUTHORIZED` varchar(2) DEFAULT NULL COMMENT '是否征信授权',
  `STAGE_TYPE` varchar(2) DEFAULT NULL COMMENT '阶段类型',
  `CREDIT_PRODUCT` varchar(100) DEFAULT NULL COMMENT '授信产品',
  `CREDIT_QUOTA` decimal(19,2) DEFAULT NULL COMMENT '授信额度',
  `CREDIT_TERM` varchar(20) DEFAULT NULL COMMENT '授信期限',
  `GUARANTEE_QUOTA` decimal(19,2) DEFAULT NULL COMMENT '担保额度',
  `GUARANTEE_TYPE` varchar(2) DEFAULT NULL COMMENT '担保方式',
  `SOURCE_SYSTEM` varchar(10) DEFAULT NULL COMMENT '来源系统',
  `TELLER_NO` varchar(20) DEFAULT NULL COMMENT '经办人',
  `TELLER_ORG` varchar(20) DEFAULT NULL COMMENT '经办机构',
  `QUALIFICATION_REVIEW_RESULT` varchar(2) DEFAULT NULL COMMENT '资质审查结果',
  `QUALIFICATION_REVIEW_FAIL_CODE` varchar(32) DEFAULT NULL COMMENT '资质审查失败节点',
  `QUALIFICATION_REVIEW_REASON` varchar(255) DEFAULT NULL COMMENT '资质审查失败原因',
  `CREIDT_VALUE` varchar(10) DEFAULT NULL COMMENT '信用评分',
  `CREIDT_LEVEL` varchar(10) DEFAULT NULL COMMENT '信用评级',
  `PASS_FLAG` varchar(2) DEFAULT NULL COMMENT '评分评级通过标识',
  `PASS_RATE` varchar(50) DEFAULT NULL COMMENT '建议通过率',
  `OVER_PERCENT` varchar(50) DEFAULT NULL COMMENT '可能逾期率',
  `LAST_UPDATED_STAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `LAST_UPDATED_TX_STAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最新更新时间戳',
  `CREATED_STAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `CREATED_TX_STAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最新创建时间戳',
  PRIMARY KEY (`QUALIFICATION_REVIEW_ID`),
  KEY `QUALIF_IDX1` (`TRAN_SEQ_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资质审查结果登记表';
-- 正在导出表  demo.conf_step_info 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `conf_step_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `conf_step_info` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
