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

 Date: 01/12/2021 09:14:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `role_name` varchar(255) DEFAULT NULL COMMENT '角色名称',
  `role_code` varchar(32) DEFAULT NULL COMMENT '角色编码',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `modifiable` varchar(1) DEFAULT '1' COMMENT '是否可修改(0 否 1是)',
  `sub_system_id` varchar(32) DEFAULT NULL COMMENT '子系统id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES ('4091aaa5d15d4dc9b30a2b72a1ba4cba', '普通用户', '89757', '普通用户', 'e9ca23d68d884d4ebb19d07889727dae', '2020-03-10 14:56:25', NULL, NULL, '0', 'hsy');
INSERT INTO `sys_role` VALUES ('d44189943ab94b53a476f103e36aac91', '开发管理员', 'devadmin', '开发管理员', 'e9ca23d68d884d4ebb19d07889727dae', '2021-02-07 10:31:31', NULL, NULL, '1', 'hsy');
INSERT INTO `sys_role` VALUES ('e6adf7f35c4a4150b2260c159bc62290', '审计管理员', 'secadmin', '系统管理员', 'e9ca23d68d884d4ebb19d07889727dae', '2021-02-07 10:32:13', 'e9ca23d68d884d4ebb19d07889727dae', '2021-02-07 10:49:23', '1', 'hsy');
INSERT INTO `sys_role` VALUES ('f6817f48af4fb3af11b9e8bf182f618b', '系统管理员', 'sysadmin', '系统管理员', NULL, '2018-12-21 18:03:39', 'e9ca23d68d884d4ebb19d07889727dae', '2021-02-07 10:31:40', '0', 'hsy');
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `dep_id` varchar(32) DEFAULT NULL COMMENT '部门表ID',
  `username` varchar(100) NOT NULL COMMENT '登录账号',
  `realname` varchar(255) DEFAULT NULL COMMENT '真实姓名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `salt` varchar(45) DEFAULT NULL COMMENT 'md5密码盐',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `sex` varchar(1) DEFAULT NULL COMMENT '性别（1：男 2：女）',
  `email` varchar(45) DEFAULT NULL COMMENT '电子邮件',
  `phone` varchar(45) DEFAULT NULL COMMENT '电话',
  `status` varchar(1) NOT NULL COMMENT '状态(1：正常  0：不启用 )',
  `del_flag` varchar(1) NOT NULL COMMENT '删除状态（0，正常，1已删除）',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `id_card` varchar(18) DEFAULT NULL COMMENT '身份证号码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES ('20210908e5482f3c1b4340bcab902c8a', 'c75952f3fad04a30bfaa7be6a26e33d7', 'display', '展示用户', 'f83d2ddc4036a1bf9f6f1d6e66fa871f436c09b4cd9090273471d6a8b0e1b231', NULL, NULL, '1980-01-02 00:00:00', '1', '', '18500000000', '1', '0', 'e9ca23d68d884d4ebb19d07889727dae', '2021-09-08 11:21:08', '20210908e5482f3c1b4340bcab902c8a', '2021-09-08 11:37:52', '440102198001021230');
INSERT INTO `sys_user` VALUES ('31f6bd865bfc4d71bbea9bfe5837f1fd', 'c75952f3fad04a30bfaa7be6a26e33d7', 'devadmin', '开发管理员', '7166037f3ff23a50195e023c97aa307eb78fce92706e9b67a0bbdd792453ff8c', NULL, NULL, '1980-01-02 00:00:00', '1', 'hsy@hsy.net', '18566666666', '1', '0', 'e9ca23d68d884d4ebb19d07889727dae', '2021-02-07 10:44:57', NULL, NULL, '440102198001021230');
INSERT INTO `sys_user` VALUES ('e9ca23d68d884d4ebb19d07889727dae', 'c75952f3fad04a30bfaa7be6a26e33d7', 'sysadmin', '系统管理员', '076be41efc13672d31dfdd91f608ab0d882d2de592929249fea5c6ab5680b0a2', 'RCGTeGiH', 'user/20190119/logo-2_1547868176839.png', '1980-01-02 00:00:00', '1', '11@qq.com', '18566666666', '1', '0', 'e9ca23d68d884d4ebb19d07889727dae', '2018-12-21 17:54:10', 'e9ca23d68d884d4ebb19d07889727dae', '2021-02-07 11:35:37', '440102198001021230');
INSERT INTO `sys_user` VALUES ('ecc1b8809a674ff1910e2c3714ca6493', 'c75952f3fad04a30bfaa7be6a26e33d7', 'secadmin', '审计管理员', '86f355723ad96d69dac8c9f253977cc6aaf7ee8846a432451293712d52acc4f4', NULL, NULL, '1980-01-02 00:00:00', '1', 'hsy@hsy.net', '18566666666', '1', '0', 'e9ca23d68d884d4ebb19d07889727dae', '2021-02-07 10:43:27', 'ecc1b8809a674ff1910e2c3714ca6493', '2021-02-22 10:14:42', '440102198001021230');
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `user_id` varchar(32) NOT NULL COMMENT '用户id',
  `role_id` varchar(32) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='用户角色表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES ('09177178d1d96c8a56a593a3b3b357f4', '20210908e5482f3c1b4340bcab902c8a', '4091aaa5d15d4dc9b30a2b72a1ba4cba');
INSERT INTO `sys_user_role` VALUES ('13b9e50b1c7e1b84fc923d78b90a06cc', 'f3c5afa21ecc43f185e010a06374925f', '4091aaa5d15d4dc9b30a2b72a1ba4cba');
INSERT INTO `sys_user_role` VALUES ('1a2865cd3b14eb469ac1cd460881b43f', 'e9c6b87ad4b8476195fb3f1e7c179294', 'f6817f48af4fb3af11b9e8bf182f618b');
INSERT INTO `sys_user_role` VALUES ('2dc2b798c3a95befffe63b33d6268fc9', '31f6bd865bfc4d71bbea9bfe5837f1fd', 'd44189943ab94b53a476f103e36aac91');
INSERT INTO `sys_user_role` VALUES ('309e6229b34807423e0f23d3a7b00dcb', '4d9f6ba22485477493d3d660991ba9c4', 'f6817f48af4fb3af11b9e8bf182f618b');
INSERT INTO `sys_user_role` VALUES ('355a07377b3ccd08f00966cffe1f35cc', 'e9ca23d68d884d4ebb19d07889727dag', 'f6817f48af4fb3af11b9e8bf182f618b');
INSERT INTO `sys_user_role` VALUES ('3c869de549fed4b833ff0731f705d7fe', 'e75c264228cf4a3b9cb642a2ee3c2837', '4091aaa5d15d4dc9b30a2b72a1ba4cba');
INSERT INTO `sys_user_role` VALUES ('48f015f83883e7220b98d081b7fa2bd5', '20210908e5482f3c1b4340bcab902c8a', '633add00638646b28ba2c2e01ede3b85');
INSERT INTO `sys_user_role` VALUES ('4edddac97f4745fd3a4ea4d57aef645c', 'ecc1b8809a674ff1910e2c3714ca6493', 'e6adf7f35c4a4150b2260c159bc62290');
INSERT INTO `sys_user_role` VALUES ('613237a1f1d9ca41e44b403520c3cd20', 'cd9d18feae614710871e0bc4b5229425', '4091aaa5d15d4dc9b30a2b72a1ba4cba');
INSERT INTO `sys_user_role` VALUES ('68d67b5c321c431f5758d1997427b5bf', '38f18d3936ec4ed182d02d6ae7517509', '4091aaa5d15d4dc9b30a2b72a1ba4cba');
INSERT INTO `sys_user_role` VALUES ('6e3e89244bdefb7f6f470b44f76f512c', '7ed10a70fe58445c9e4e0e2c55e48da7', 'f6817f48af4fb3af11b9e8bf182f618b');
INSERT INTO `sys_user_role` VALUES ('71ba9cc907363a3ddbd470284a6bad6f', 'b01ca5bf03774149a5465a03c6110a58', '4091aaa5d15d4dc9b30a2b72a1ba4cba');
INSERT INTO `sys_user_role` VALUES ('85d874ca46ded12e5c51f9f9091ca149', 'e9ca23d68d884d4ebb19d07889727daf', 'f6817f48af4fb3af11b9e8bf182f618b');
INSERT INTO `sys_user_role` VALUES ('8ac3896b2b0cdc9fc774a2c609a4d44f', 'e1fda083da9642a49eff81e6e98b72f3', 'f6817f48af4fb3af11b9e8bf182f618b');
INSERT INTO `sys_user_role` VALUES ('8bc03369b682e0c6364c06126a73446b', '3812e1f5fa0440a1a4889323b67605dd', 'f6817f48af4fb3af11b9e8bf182f618b');
INSERT INTO `sys_user_role` VALUES ('99dabfd0deb721c724af794486daba2b', 'e9ca23d68d884d4ebb19d07889727dae', 'f6817f48af4fb3af11b9e8bf182f618b');
INSERT INTO `sys_user_role` VALUES ('a3ccef7a966c676d892e91ce1a48d477', '20210908e5482f3c1b4340bcab902c8a', '2021090842d32fc056784112b2aaf2e4');
INSERT INTO `sys_user_role` VALUES ('ba696b713649e60e71ef24e742e1fda0', '4d9f6ba22485477493d3d660991ba9c4', '4091aaa5d15d4dc9b30a2b72a1ba4cba');
INSERT INTO `sys_user_role` VALUES ('bc1188a286a8d028f88bbcabb0e5e8e9', 'ea478b624b6045ec829822aaf476cefe', 'f6817f48af4fb3af11b9e8bf182f618b');
INSERT INTO `sys_user_role` VALUES ('c5ecc7a142201d3b9c9cd51a157ef753', '85fa7320464f40249810767c334d7e6d', 'f6817f48af4fb3af11b9e8bf182f618b');
INSERT INTO `sys_user_role` VALUES ('ead365418d65063a34409cbb00d7b57b', 'efb1216ad4564be89546e80a32d02911', '4091aaa5d15d4dc9b30a2b72a1ba4cba');
INSERT INTO `sys_user_role` VALUES ('ecada56b34406fcbb70a9143a6402728', 'bfce67612fdb4019a74ab30ff96e8bc0', '4091aaa5d15d4dc9b30a2b72a1ba4cba');
INSERT INTO `sys_user_role` VALUES ('ece9e2734ad8e2a208170c92f2bbe64e', '20210823818dbc0411f1801f', '4091aaa5d15d4dc9b30a2b72a1ba4cba');
INSERT INTO `sys_user_role` VALUES ('ee490ecac43209fe9b5b1f227519c853', 'a217973387414841a65a308486d43ee9', 'f6817f48af4fb3af11b9e8bf182f618b');
INSERT INTO `sys_user_role` VALUES ('ff98c330e6da5f71568987586029c195', '5bd97cdd7e5b44c2affc2991328e7469', '4091aaa5d15d4dc9b30a2b72a1ba4cba');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
