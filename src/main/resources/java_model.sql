/*
 Navicat Premium Data Transfer

 Source Server         : java_model
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : localhost:3306
 Source Schema         : java_model

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : 65001

 Date: 05/06/2020 09:26:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `user_id` varchar(32) COLLATE utf8_unicode_ci NOT NULL COMMENT '管理员id',
  `phone` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '手机号码',
  `user_name` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户名',
  `pwd` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '密码',
  `gender` tinyint(4) DEFAULT '1' COMMENT '性别 1:男 2:女',
  `account_status` int(11) DEFAULT '1' COMMENT '账号状态 是否启用。0 ：禁用；1：启用',
  `is_valid` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否有效',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='后台管理员';

-- ----------------------------
-- Records of admin
-- ----------------------------
BEGIN;
INSERT INTO `admin` VALUES ('0A4DE1B8785B478190218C9C91721F73', '18956925656', '王玉', 'fcea920f7412b5da7be0cf42b8c93759', 1, 1, 0, '2020-05-25 14:57:59');
INSERT INTO `admin` VALUES ('0F0881A929B240C8B659F44BC8050604', '13170221865', '测试', 'e10adc3949ba59abbe56e057f20f883e', 1, 1, 1, '2020-05-25 14:58:02');
INSERT INTO `admin` VALUES ('15005ef77de943e390abf15a5ade078a', '13339036821', '韦文彬1', '09d3ed919e48e2db01a7392692a727b5', 1, 1, 1, '2020-05-29 15:45:44');
INSERT INTO `admin` VALUES ('26C0CC8785F54E259F444B1AA6D11EDD', '157698525222', '啦啦1', 'e10adc3949ba59abbe56e057f20f883e', 1, 1, 1, '2020-05-25 14:58:02');
INSERT INTO `admin` VALUES ('475B24EB704145D3B858AD8F76D98BBE', '18956925655', '王伟', 'e10adc3949ba59abbe56e057f20f883e', 1, 1, 1, '2020-05-25 14:58:04');
INSERT INTO `admin` VALUES ('57D4F34B89614581B01BFFA0309283EA', '15769852521', '徐菲', 'e10adc3949ba59abbe56e057f20f883e', 1, 1, 1, '2020-05-15 14:55:46');
INSERT INTO `admin` VALUES ('605bb5b9f2fc4b8b84bcec6737c317b4', '18855976979', '韦文彬', '09d3ed919e48e2db01a7392692a727b5', 1, 1, 0, '2020-05-29 14:55:41');
INSERT INTO `admin` VALUES ('64046c19d4b3f61409345f6cb1c757e1', NULL, NULL, NULL, NULL, NULL, 1, NULL);
INSERT INTO `admin` VALUES ('64046c19d4b3f61409345f6cb1c757ea', '15056469253', '韦文彬', '0e85bf8527a37d47592bd386f9c142a2', 1, 1, 1, '2020-06-04 11:01:04');
INSERT INTO `admin` VALUES ('7DE972215FC6427E8C21A15F5531AE15', '18656150658', '陶明', 'e10adc3949ba59abbe56e057f20f883e', 1, 1, 1, '2020-05-25 14:57:59');
INSERT INTO `admin` VALUES ('9A7DC523998F41B89259E1048DBCF18F', '13093658608', '陈庆', 'e10adc3949ba59abbe56e057f20f883e', 1, 1, 1, '2020-05-25 14:58:04');
INSERT INTO `admin` VALUES ('B1BF6C7776B94617A106CAB82F0580F2', '18855976978', '崔闪', 'e10adc3949ba59abbe56e057f20f883e', 1, 1, 1, '2020-05-18 16:32:52');
INSERT INTO `admin` VALUES ('B2AEB65004E348D192B08F7DA87EB102', '18905661987', '李秋凡', 'e10adc3949ba59abbe56e057f20f883e', 1, 1, 1, '2020-05-18 16:32:52');
INSERT INTO `admin` VALUES ('bfa805f2161f45128e834fe63a8ccb1f', '18855976979', '韦文彬', '2e04f5777509adbca55efaee8854e780', 1, 1, 1, '2020-05-29 15:45:57');
INSERT INTO `admin` VALUES ('E5389C56967811EAA1920242AC110002', '18955301684', '程礼娜', 'e10adc3949ba59abbe56e057f20f883e', 1, 1, 1, '2020-05-15 14:55:46');
COMMIT;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `menu_id` varchar(32) COLLATE utf8_unicode_ci NOT NULL COMMENT '菜单ID',
  `menu_name` varchar(32) COLLATE utf8_unicode_ci NOT NULL COMMENT '路由名称',
  `menu_pid` varchar(32) COLLATE utf8_unicode_ci DEFAULT '0' COMMENT '上级ID',
  `menu_url` varchar(1024) COLLATE utf8_unicode_ci NOT NULL COMMENT '前端url',
  `is_valid` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否有效',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='菜单';

-- ----------------------------
-- Records of menu
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for menu_role
-- ----------------------------
DROP TABLE IF EXISTS `menu_role`;
CREATE TABLE `menu_role` (
  `menu_permission_id` varchar(32) COLLATE utf8_unicode_ci NOT NULL COMMENT '菜单与权限id',
  `menu_id` varchar(32) COLLATE utf8_unicode_ci NOT NULL COMMENT '菜单id',
  `role_id` varchar(32) COLLATE utf8_unicode_ci NOT NULL COMMENT '权限id',
  `is_valid` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否有效',
  PRIMARY KEY (`menu_permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='菜单与角色关系';

-- ----------------------------
-- Records of menu_role
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `permission_id` varchar(32) COLLATE utf8_unicode_ci NOT NULL COMMENT '权限id',
  `permission_name` varchar(32) COLLATE utf8_unicode_ci NOT NULL COMMENT '名称',
  `permission_url` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT 'url',
  `is_valid` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否有效',
  PRIMARY KEY (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='权限';

-- ----------------------------
-- Records of permission
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `role_id` varchar(32) COLLATE utf8_unicode_ci NOT NULL COMMENT '角色id',
  `role_name` varchar(32) COLLATE utf8_unicode_ci NOT NULL COMMENT '角色名称',
  `role_description` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT '角色描述',
  `is_valid` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='角色';

-- ----------------------------
-- Records of role
-- ----------------------------
BEGIN;
INSERT INTO `role` VALUES ('0828f2783b244f7dadaa59b293acd337', '角色名称', '描述', 0);
INSERT INTO `role` VALUES ('ce6bcde15aad80993fc1d5f583b4c5ea', '角色名称2', '描述2', 1);
COMMIT;

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `role_permission_id` varchar(32) COLLATE utf8_unicode_ci NOT NULL COMMENT '角色权限id',
  `role_id` varchar(32) COLLATE utf8_unicode_ci NOT NULL COMMENT '角色id',
  `permission_id` varchar(32) COLLATE utf8_unicode_ci NOT NULL COMMENT '权限id',
  `is_valid` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否有效',
  PRIMARY KEY (`role_permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='角色与权限关系';

-- ----------------------------
-- Records of role_permission
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_role_id` varchar(32) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户角色Id',
  `user_id` varchar(32) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户id',
  `role_id` varchar(32) COLLATE utf8_unicode_ci NOT NULL COMMENT '角色id',
  `is_valid` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否有效',
  PRIMARY KEY (`user_role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='用户与角色关系';

-- ----------------------------
-- Records of user_role
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
