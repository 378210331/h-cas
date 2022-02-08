/*
 Navicat Premium Data Transfer

 Source Server         : 172.16.11.162
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : 172.16.11.162:3306
 Source Schema         : cloud

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 11/11/2021 10:16:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cas_service
-- ----------------------------
DROP TABLE IF EXISTS `cas_service`;
CREATE TABLE `cas_service` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `evaluation_order` int DEFAULT NULL COMMENT '排序',
  `logout_type` int DEFAULT NULL COMMENT '登出类型',
  `logout_url` varchar(255) DEFAULT NULL COMMENT '登出URL',
  `name` varchar(255) DEFAULT NULL COMMENT '服务名称',
  `serviceid` varchar(255) DEFAULT NULL COMMENT '服务id',
  `status` char(1) DEFAULT '1' COMMENT '(1 开启 0 关闭)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT='单点登录服务信息';

-- ----------------------------
-- Records of cas_service
-- ----------------------------
BEGIN;
INSERT INTO `cas_service` VALUES ('47', 'This service definition authorizes all application urls that support HTTPS and IMAPS protocols.', 10000, 0, NULL, 'HTTPS and IMAPS', '^(https|imaps|http)://.*', '1');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
