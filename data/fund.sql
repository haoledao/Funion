DROP DATABASE IF EXISTS fund;
CREATE DATABASE fund;
USE fund;

SET NAMES utf8mb4;
-- SET FOREIGN_KEY_CHECKS = 0;

-- -----------------------------
-- Table structure for fundation
-- -----------------------------
DROP TABLE IF EXISTS `foundation`;
CREATE TABLE IF NOT EXISTS `foundation` (
	`id` int NOT NULL AUTO_INCREMENT COMMENT '自增主键',
	`fund_code` CHAR(6) NOT NULL COMMENT '基金代码',
	`fund_name` VARCHAR(32) NOT NULL COMMENT '基金名称',
	`type_id` int NOT NULL COMMENT '基金类型',
	`company_id` int NOT NULL COMMENT '基金公司',
	`risk_level` VARCHAR(16) NOT NULL COMMENT '风险等级：disk(risk_level)',
	`fund_net` decimal(10,4) NOT NULL DEFAULT 0.0000 COMMENT '基金净值',
	`fund_rise` decimal(10,4) NOT NULL DEFAULT 0.0000 COMMENT '基金涨幅',
	`share` decimal(10,4) NOT NULL DEFAULT 0.0000 COMMENT '持有份额',
	`amount` decimal(10,4) NOT NULL DEFAULT 0.0000 COMMENT '持有金额',
	`cost` decimal(10,4) NOT NULL DEFAULT 0.0000 COMMENT '持仓成本',
	`income_rate` decimal(10,4) NOT NULL DEFAULT 0.0000 COMMENT '收益率',
	`create_time` timestamp NOT NULL COMMENT '创建时间',
	`profit_status` char(16) NOT NULL COMMENT '盈利状态：stop-停，rise-涨，fall-跌',
	`status` char(16) NOT NULL DEFAULT 'on' COMMENT '基金状态：on-上架，off-下架',
	`order_no` int NULL COMMENT '排序号',
	PRIMARY KEY (`id`) USING BTREE,
	UNIQUE INDEX `uq_fund_code`(`fund_code`) USING BTREE,
	INDEX `idx_fund_type_id`(`type_id`) USING BTREE,
	INDEX `idx_fund_company_id`(`company_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100000 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '基金表' ROW_FORMAT = Dynamic;

-- -----------------------------
-- Table structure for fund_type
-- -----------------------------
DROP TABLE IF EXISTS `fund_type`;
CREATE TABLE IF NOT EXISTS `fund_type` (
	`id` int NOT NULL AUTO_INCREMENT COMMENT '自增主键',
	`type_name` VARCHAR(16) NOT NULL COMMENT '类型名称',
	`type_memo` VARCHAR(1024) NULL COMMENT '类型描述',
	PRIMARY KEY (`id`) USING BTREE,
	UNIQUE INDEX `uq_type_name`(`type_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100000 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '基金类型表' ROW_FORMAT = Dynamic;

INSERT INTO fund_type (`type_name`) VALUES ('混合型');
INSERT INTO fund_type (`type_name`) VALUES ('股票型');
INSERT INTO fund_type (`type_name`) VALUES ('指数型');
INSERT INTO fund_type (`type_name`) VALUES ('联接型');
INSERT INTO fund_type (`type_name`) VALUES ('债券型');
INSERT INTO fund_type (`type_name`) VALUES ('货币型');
INSERT INTO fund_type (`type_name`) VALUES ('QDII型');

-- -----------------------------
-- Table structure for fund_company
-- -----------------------------
DROP TABLE IF EXISTS `fund_company`;
CREATE TABLE IF NOT EXISTS `fund_company` (
	`id` int NOT NULL AUTO_INCREMENT COMMENT '自增主键',
	`short_name` VARCHAR(16) NOT NULL COMMENT '公司简称',
	`company_name` VARCHAR(32) NOT NULL COMMENT '公司名称',
	`company_logo` VARCHAR(128) NULL COMMENT '公司LOGO',
	`company_url` VARCHAR(128) NULL COMMENT '公司官网',
	`company_memo` TEXT NULL COMMENT '公司描述',
	`create_time` TIMESTAMP NOT NULL COMMENT '创建时间',
	PRIMARY KEY (`id`) USING BTREE,
	UNIQUE INDEX `uq_type_name`(`company_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100000 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '基金公司表' ROW_FORMAT = Dynamic;

-- -----------------------------
-- Table structure for income
-- -----------------------------
DROP  TABLE IF EXISTS `fund_income`;
CREATE TABLE IF NOT EXISTS `fund_income` (
	`id` int NOT NULL AUTO_INCREMENT COMMENT '自增主键',
	`fund_id` int NOT NULL COMMENT '基金ID',
	`income_date` TIMESTAMP NOT NULL COMMENT '收益日期',
	`amount` decimal(10,4) NOT NULL DEFAULT 0.0000 COMMENT '盈亏金额',
	`grow_value` decimal(10,4) NOT NULL DEFAULT 0.0000 COMMENT '日增长值',
	`grow_rate` decimal(10,4) NOT NULL DEFAULT 0.0000 COMMENT '日增长率',
	PRIMARY KEY (`id`) USING BTREE,
	INDEX `idx_income_date`(`income_date`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100000 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '收益明细表' ROW_FORMAT = Dynamic;

-- -----------------------------
-- Table structure for dict
-- -----------------------------
DROP TABLE IF EXISTS `dict`;
CREATE TABLE IF NOT EXISTS `dict` (
	`id` int NOT NULL AUTO_INCREMENT COMMENT '自增主键',
	`module` VARCHAR(32) NOT NULL COMMENT '模块',
	`dict_type` VARCHAR(32) NOT NULL COMMENT '字典类型',
	`dict_code` VARCHAR(32) NOT NULL COMMENT '字典代码',
	`dict_memo` VARCHAR(32) NULL COMMENT '描述',
	`active` tinyint NOT NULL DEFAULT 1 COMMENT '是否启用',
	`order` int NOT NULL COMMENT '排序号',
	PRIMARY KEY (`id`) USING BTREE,
	INDEX `idx_dict_type`(`dict_type`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 100000 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '字典表' ROW_FORMAT = Dynamic;

INSERT INTO `dict` (module, dict_type, dict_code, dict_memo, active, order)
VALUES ('system', 'risk_level', 'HIGH', '高风险', 1, 1);
INSERT INTO `dict` (module, dict_type, dict_code, dict_memo, active, order)
VALUES ('system', 'risk_level', 'MID_HIGH', '中高风险', 1, 2);
INSERT INTO `dict` (module, dict_type, dict_code, dict_memo, active, order)
VALUES ('system', 'risk_level', 'MIDDLE', '中风险', 1, 3);
INSERT INTO `dict` (module, dict_type, dict_code, dict_memo, active, order)
VALUES ('system', 'risk_level', 'MID_LOW', '中低风险', 1, 4);
INSERT INTO `dict` (module, dict_type, dict_code, dict_memo, active, order)
VALUES ('system', 'risk_level', 'LOW', '低风险', 1, 5);
