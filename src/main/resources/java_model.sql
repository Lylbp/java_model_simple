-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- 主机： localhost
-- 生成日期： 2020-11-27 14:37:18
-- 服务器版本： 5.7.22-log
-- PHP 版本： 7.1.29

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 数据库： `lylbp_oa`
--

-- --------------------------------------------------------

--
-- 表的结构 `QRTZ_BLOB_TRIGGERS`
--

CREATE TABLE `QRTZ_BLOB_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `BLOB_DATA` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `QRTZ_CALENDARS`
--

CREATE TABLE `QRTZ_CALENDARS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(190) NOT NULL,
  `CALENDAR` blob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `QRTZ_CRON_TRIGGERS`
--

CREATE TABLE `QRTZ_CRON_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `QRTZ_FIRED_TRIGGERS`
--

CREATE TABLE `QRTZ_FIRED_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `INSTANCE_NAME` varchar(190) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(190) DEFAULT NULL,
  `JOB_GROUP` varchar(190) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `QRTZ_JOB_DETAILS`
--

CREATE TABLE `QRTZ_JOB_DETAILS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(190) NOT NULL,
  `JOB_GROUP` varchar(190) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `QRTZ_LOCKS`
--

CREATE TABLE `QRTZ_LOCKS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `QRTZ_LOCKS`
--

INSERT INTO `QRTZ_LOCKS` (`SCHED_NAME`, `LOCK_NAME`) VALUES
('quartzScheduler', 'STATE_ACCESS');

-- --------------------------------------------------------

--
-- 表的结构 `QRTZ_PAUSED_TRIGGER_GRPS`
--

CREATE TABLE `QRTZ_PAUSED_TRIGGER_GRPS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `QRTZ_SCHEDULER_STATE`
--

CREATE TABLE `QRTZ_SCHEDULER_STATE` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(190) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `QRTZ_SCHEDULER_STATE`
--

INSERT INTO `QRTZ_SCHEDULER_STATE` (`SCHED_NAME`, `INSTANCE_NAME`, `LAST_CHECKIN_TIME`, `CHECKIN_INTERVAL`) VALUES
('quartzScheduler', 'Alex.local1606459011506', 1606459035853, 10000);

-- --------------------------------------------------------

--
-- 表的结构 `QRTZ_SIMPLE_TRIGGERS`
--

CREATE TABLE `QRTZ_SIMPLE_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `QRTZ_SIMPROP_TRIGGERS`
--

CREATE TABLE `QRTZ_SIMPROP_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `QRTZ_TRIGGERS`
--

CREATE TABLE `QRTZ_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `JOB_NAME` varchar(190) NOT NULL,
  `JOB_GROUP` varchar(190) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(190) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `t_rbac_admin_role`
--

CREATE TABLE `t_rbac_admin_role` (
  `admin_role_id` varchar(32) NOT NULL COMMENT '管理员与角色关系id',
  `admin_id` varchar(32) NOT NULL COMMENT '管理员id',
  `role_id` varchar(32) NOT NULL COMMENT '角色id',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_valid` varchar(1) NOT NULL DEFAULT '1' COMMENT '是否有效数据'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限管理-管理员与角色关系表 ';

--
-- 转存表中的数据 `t_rbac_admin_role`
--

INSERT INTO `t_rbac_admin_role` (`admin_role_id`, `admin_id`, `role_id`, `create_by`, `create_time`, `update_by`, `update_time`, `is_valid`) VALUES
('6356cc1b790ca3e48fc4959693849755', '0fbb5571d64d1f627f694e3fb5b2cb6d', 'd1fbe54bd21162891fd5e3e9498a238e', '475B24EB704145D3B858AD8F76D98BBE', '2020-11-25 21:58:16', NULL, NULL, '1');

-- --------------------------------------------------------

--
-- 表的结构 `t_rbac_menu`
--

CREATE TABLE `t_rbac_menu` (
  `menu_id` varchar(32) NOT NULL COMMENT '菜单id',
  `menu_pid` varchar(32) NOT NULL COMMENT '菜单上级id',
  `menu_name` varchar(32) NOT NULL DEFAULT '0' COMMENT '菜单名称',
  `menu_url` varchar(32) NOT NULL COMMENT '前端url',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_valid` varchar(1) NOT NULL DEFAULT '1' COMMENT '是否有效'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限管理-菜单表 ';

-- --------------------------------------------------------

--
-- 表的结构 `t_rbac_menu_role`
--

CREATE TABLE `t_rbac_menu_role` (
  `menu_role_id` varchar(32) NOT NULL COMMENT '菜单与角色关系id',
  `menu_id` varchar(32) NOT NULL COMMENT '菜单id',
  `role_id` varchar(32) NOT NULL COMMENT '角色id',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_valid` varchar(1) NOT NULL DEFAULT '1' COMMENT '是否有效数据'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限管理-菜单与角色关系表 ';

-- --------------------------------------------------------

--
-- 表的结构 `t_rbac_permission`
--

CREATE TABLE `t_rbac_permission` (
  `permission_id` varchar(32) NOT NULL COMMENT '权限id',
  `permission_name` varchar(128) NOT NULL COMMENT '权限名称',
  `permission_url` varchar(128) NOT NULL COMMENT '权限url',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_valid` varchar(1) NOT NULL DEFAULT '1' COMMENT '是否有效数据'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限管理-权限表 ';

--
-- 转存表中的数据 `t_rbac_permission`
--

INSERT INTO `t_rbac_permission` (`permission_id`, `permission_name`, `permission_url`, `create_by`, `create_time`, `update_by`, `update_time`, `is_valid`) VALUES
('084fe46e0d6d6be92a8e34a054289309', '管理员-添加', '/bg/admin/add', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:48:50', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:52:14', '1'),
('1c88a42e9f1b04e3adeb82076900173e', '角色-获取角色分页列表', '/bg/role/getPageList', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:48:50', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:52:14', '1'),
('2a4c229da1bd74d5c0d99a3ccf03846a', '管理员与角色关系-批量添加管理员与角色关系', '/bg/adminRole/batchInsert', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:48:50', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:52:14', '1'),
('37b314086e00f8616621c2692b208da7', '管理员-通过管理员id获取管理员信息', '/bg/admin/info/{id}', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:48:50', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:52:14', '1'),
('3c2c18f53e5ccf787305fa49d18fe629', '菜单-编辑菜单', '/bg/menu/edit', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:48:50', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:52:14', '1'),
('3e25c1daf3493c49fb5661e502e41f05', '角色与权限关系-根据角色id获得已分配权限列表', '/bg/rolePermission/getRoleAssignData', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:48:50', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:52:14', '1'),
('63ccc96ebbc222d9ab2e5f24d56de824', '管理员与角色关系-批量删除管理员与角色关系', '/bg/adminRole/batchDelete', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:48:50', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:52:14', '1'),
('63ea7c040138bfeedd25b3f03fdb9db0', '菜单-添加菜单', '/bg/menu/add', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:48:50', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:52:14', '1'),
('64145468aed63bfe424db3d19b213439', '管理员-根据管理员id批量删除', '/bg/admin/batchDeleteByIds', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:48:50', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:52:14', '1'),
('7170c0e59681a2504980590468e94ea2', '角色-编辑', '/bg/role/edit', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:48:49', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:52:14', '1'),
('7b85682b8f8a632fcecf185e82b6b424', '权限相关-更新权限源数据', '/bg/permission/editPermissionData', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:48:50', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:52:14', '1'),
('7c6f674d7d331cb1f974836a5834dd2e', '角色与权限关系-批量添加角色与权限关系', '/bg/rolePermission/batchInsert', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:48:50', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:52:14', '1'),
('880cd38734d2c0077ddd4c04cebdcd8a', '管理员-根据管理员id批量禁用账号', '/bg/admin/batchUpdateDisableByIds', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:48:50', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:52:14', '1'),
('8aaf66c416c242192e595d9d4ed86c07', '角色与菜单关系-根据角色id获得未分配菜单列表', '/bg/menuRole/getRoleNoAssignMenuList', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:48:50', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:52:14', '1'),
('8d61aae05533e2529a2bfd28feb326ca', '菜单-根据菜单id获取菜单详情', '/bg/menu/info/{id}', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:48:50', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:52:14', '1'),
('9c6ddc480b08857b7e5c53c78f482cb4', '角色与权限关系-根据角色id获得未分配权限列表', '/bg/rolePermission/getUserNoAssignRoleList', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:48:50', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:52:14', '1'),
('9d8cc6ca31e60bbdc2a58d5e8561509b', '菜单-获取菜单分页列表', '/bg/menu/getPageList', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:48:50', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:52:14', '1'),
('9f669affb59f5a0ef69ed25c58c0d856', '角色-通过角色id批量删除', '/bg/role/batchDeleteByIds', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:48:50', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:52:14', '1'),
('ad287ab388191a7040cd4bf8ba0cb904', '角色与权限关系-批量删除角色与权限关系', '/bg/rolePermission/batchDelete', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:48:50', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:52:14', '1'),
('aeed7b8f89eef386ebe356ac5125db69', '角色-添加', '/bg/role/add', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:48:50', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:52:14', '1'),
('b28f95aefc23240ef88a7aa01e8ee3b0', '管理员-根据管理员id批量启用账号', '/bg/admin/batchUpdateIsEnableByIds', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:48:50', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:52:14', '1'),
('b2b8ed4009317883d64e82a5ea6eac71', '管理员与角色关系-获得对应用户未分配角色列表', '/bg/adminRole/getUserNoAssignRoleList', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:48:50', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:52:14', '1'),
('c11e7874f5ebaddff078d8132ef75e8b', '菜单-编辑菜单', '/bg/menu/batchDeleteByIds', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:48:50', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:52:14', '1'),
('ca4e3fd20f35c87b65d669fb8aadb8bb', '管理员-编辑', '/bg/admin/edit', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:48:50', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:52:14', '1'),
('da832f3ecb2795de57db74918e15ccda', '管理员-获取管理员分页列表', '/bg/admin/getPageList', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:48:50', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:52:14', '1'),
('e3b508e69fb1dead7489a44a9baf062e', '角色与菜单关系-批量添加角色与菜单关系', '/bg/menuRole/batchInsert', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:48:50', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:52:14', '1'),
('e4fcdbfb3c943d08c17cc915cb8c7234', '角色-获取角色id获取角色详情', '/bg/role/info/{id}', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:48:50', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:52:14', '1'),
('e70ef087aebe823bffd3c34071e55b26', '角色与菜单关系-批量删除角色与菜单关系', '/bg/menuRole/batchDelete', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:48:49', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:52:14', '1'),
('eae3fe43cc42784993bc3bcdfa0b807b', '角色与菜单关系-根据角色id获得已分配菜单列表', '/bg/menuRole/getRoleHasAssignMenuList', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:48:50', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:52:14', '1'),
('fb6b9b5aa54d20f0ffa5cd0eb4e620e6', '管理员与角色关系-获得对应用户已分配角色列表', '/bg/adminRole/getRoleAssignData', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:48:50', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-22 09:52:14', '1');

-- --------------------------------------------------------

--
-- 表的结构 `t_rbac_role`
--

CREATE TABLE `t_rbac_role` (
  `role_id` varchar(32) NOT NULL COMMENT '角色id',
  `role_name` varchar(128) NOT NULL COMMENT '角色名称',
  `role_description` varchar(512) NOT NULL COMMENT '角色描述',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_valid` varchar(1) NOT NULL DEFAULT '1' COMMENT '是否有效数据'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限管理-角色表 ';

--
-- 转存表中的数据 `t_rbac_role`
--

INSERT INTO `t_rbac_role` (`role_id`, `role_name`, `role_description`, `create_by`, `create_time`, `update_by`, `update_time`, `is_valid`) VALUES
('d1fbe54bd21162891fd5e3e9498a238e', '管理员列表权限', '管理员列表权限', '475B24EB704145D3B858AD8F76D98BBE', '2020-11-25 21:55:40', NULL, NULL, '1');

-- --------------------------------------------------------

--
-- 表的结构 `t_rbac_role_permission`
--

CREATE TABLE `t_rbac_role_permission` (
  `role_permission_id` varchar(32) NOT NULL COMMENT '角色与权限关系id',
  `role_id` varchar(32) NOT NULL COMMENT '角色id',
  `permission_id` varchar(32) NOT NULL COMMENT '权限id',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_valid` varchar(1) NOT NULL DEFAULT '1' COMMENT '是否有效数据'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限管理-角色与权限关系表 ';

--
-- 转存表中的数据 `t_rbac_role_permission`
--

INSERT INTO `t_rbac_role_permission` (`role_permission_id`, `role_id`, `permission_id`, `create_by`, `create_time`, `update_by`, `update_time`, `is_valid`) VALUES
('1377f520e0b91aad3dbd7e2b6b5b2c20', 'd1fbe54bd21162891fd5e3e9498a238e', 'da832f3ecb2795de57db74918e15ccda', '475B24EB704145D3B858AD8F76D98BBE', '2020-11-25 21:57:12', NULL, NULL, '1');

-- --------------------------------------------------------

--
-- 表的结构 `t_s_admin`
--

CREATE TABLE `t_s_admin` (
  `admin_id` varchar(32) NOT NULL COMMENT '管理员id',
  `login_account` varchar(32) NOT NULL COMMENT '登录账号',
  `real_name` varchar(128) NOT NULL COMMENT '真实名称',
  `pwd` varchar(128) NOT NULL COMMENT '密码',
  `is_enabled` varchar(1) NOT NULL DEFAULT '1' COMMENT '账号是否启用',
  `is_valid` varchar(1) NOT NULL DEFAULT '1' COMMENT '是否有效数据',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='后台管理员表';

--
-- 转存表中的数据 `t_s_admin`
--

INSERT INTO `t_s_admin` (`admin_id`, `login_account`, `real_name`, `pwd`, `is_enabled`, `is_valid`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES
('02b5b04aa98662d70ad1ef3f1bba72ef', 'test3', 'ceshi3', '$2a$10$L4LDeiLG7E1VBjCVR2vLh.IA9dunWvap0sh.MEcuCsg8XxcxrJuyq', '1', '1', '475B24EB704145D3B858AD8F76D98BBE', '2020-11-26 09:08:38', NULL, NULL),
('0fbb5571d64d1f627f694e3fb5b2cb6d', 'test', 'ceshi1', '$2a$10$BxlLkVVGiBB2a/EDEu0T5eW9dT2qGrxUSD9WJ/PDrefnl1FJ8fK8e', '1', '1', '475B24EB704145D3B858AD8F76D98BBE', '2020-11-25 21:54:09', NULL, NULL),
('475B24EB704145D3B858AD8F76D98BBE', 'admin', '超级管理员', '$2a$10$N3W53f17xblFR0k8EdCemewyHNlD3H0WmiMtU4Pd2y6gUd1WbjEwi', '1', '1', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-19 16:14:24', NULL, NULL),
('ee896c8bab822d1f9018c599087ca7b1', 'test2', 'ceshi2', '$2a$10$MfEw327dxEkDtoWN0n4jh.DUimuYNAPceZpmabr13zkwCXNPHd.6m', '1', '1', '475B24EB704145D3B858AD8F76D98BBE', '2020-11-25 22:00:02', NULL, NULL);

-- --------------------------------------------------------

--
-- 表的结构 `t_s_task`
--

CREATE TABLE `t_s_task` (
  `task_id` varchar(32) NOT NULL COMMENT '任务id',
  `name` varchar(64) NOT NULL COMMENT '任务名称',
  `description` varchar(128) NOT NULL COMMENT '任务描述',
  `remark` varchar(128) DEFAULT NULL COMMENT '任务备注',
  `execution_time` varchar(32) NOT NULL COMMENT '执行时间cron表达式',
  `execution_class_name` varchar(128) DEFAULT NULL COMMENT '执行类名称',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '任务状态(0: 暂停 1:启动)',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_valid` varchar(1) NOT NULL DEFAULT '1' COMMENT '是否有效数据'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统定时任务表 ';

--
-- 转存表中的数据 `t_s_task`
--

INSERT INTO `t_s_task` (`task_id`, `name`, `description`, `remark`, `execution_time`, `execution_class_name`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `is_valid`) VALUES
('404e9cad6f7c592abfd8db1460c3fead', '测试任务', '测试1', '测试备注', '0 */1 * * * ?', 'com.lylbp.oa.task.TestTask', 0, '475B24EB704145D3B858AD8F76D98BBE', '2020-10-20 08:48:23', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-20 09:03:48', '1'),
('92499f6683a3076f16dd023155bb2f8a', '测试任务', '测试2', '测试备注', '*/5 * * * * ?', 'com.lylbp.oa.task.TestTask', 1, '475B24EB704145D3B858AD8F76D98BBE', '2020-10-19 16:46:29', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-19 17:16:50', '0'),
('db4b0a1b6d18f56d6ab66241b5466cd2', '测试任务', '测试3', '测试备注', '*/5 * * * * ?', 'com.lylbp.oa.task.TestTask', 1, '475B24EB704145D3B858AD8F76D98BBE', '2020-10-19 16:15:35', '475B24EB704145D3B858AD8F76D98BBE', '2020-10-19 16:19:55', '0');

-- --------------------------------------------------------

--
-- 表的结构 `t_s_type`
--

CREATE TABLE `t_s_type` (
  `type_id` varchar(32) NOT NULL COMMENT '字典表id',
  `type_code` varchar(32) NOT NULL COMMENT '字典代码',
  `type_name` varchar(32) NOT NULL COMMENT '字典名称',
  `type_description` varchar(32) NOT NULL COMMENT '字典描述',
  `type_group_code` varchar(32) NOT NULL COMMENT '字典项代码',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_valid` varchar(1) NOT NULL DEFAULT '1' COMMENT '是否有效数据'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典表 ';

-- --------------------------------------------------------

--
-- 表的结构 `t_s_type_group`
--

CREATE TABLE `t_s_type_group` (
  `type_group_id` varchar(32) NOT NULL COMMENT '字典项id',
  `type_group_code` varchar(32) NOT NULL COMMENT '字典项代码',
  `type_group_name` varchar(32) NOT NULL COMMENT '字典项名称',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_valid` varchar(1) NOT NULL DEFAULT '1' COMMENT '是否有效数据'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典项表 ';

--
-- 转储表的索引
--

--
-- 表的索引 `QRTZ_BLOB_TRIGGERS`
--
ALTER TABLE `QRTZ_BLOB_TRIGGERS`
  ADD PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  ADD KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`);

--
-- 表的索引 `QRTZ_CALENDARS`
--
ALTER TABLE `QRTZ_CALENDARS`
  ADD PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`);

--
-- 表的索引 `QRTZ_CRON_TRIGGERS`
--
ALTER TABLE `QRTZ_CRON_TRIGGERS`
  ADD PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`);

--
-- 表的索引 `QRTZ_FIRED_TRIGGERS`
--
ALTER TABLE `QRTZ_FIRED_TRIGGERS`
  ADD PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  ADD KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
  ADD KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
  ADD KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  ADD KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`),
  ADD KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  ADD KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`);

--
-- 表的索引 `QRTZ_JOB_DETAILS`
--
ALTER TABLE `QRTZ_JOB_DETAILS`
  ADD PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  ADD KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  ADD KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`);

--
-- 表的索引 `QRTZ_LOCKS`
--
ALTER TABLE `QRTZ_LOCKS`
  ADD PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`);

--
-- 表的索引 `QRTZ_PAUSED_TRIGGER_GRPS`
--
ALTER TABLE `QRTZ_PAUSED_TRIGGER_GRPS`
  ADD PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`);

--
-- 表的索引 `QRTZ_SCHEDULER_STATE`
--
ALTER TABLE `QRTZ_SCHEDULER_STATE`
  ADD PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`);

--
-- 表的索引 `QRTZ_SIMPLE_TRIGGERS`
--
ALTER TABLE `QRTZ_SIMPLE_TRIGGERS`
  ADD PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`);

--
-- 表的索引 `QRTZ_SIMPROP_TRIGGERS`
--
ALTER TABLE `QRTZ_SIMPROP_TRIGGERS`
  ADD PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`);

--
-- 表的索引 `QRTZ_TRIGGERS`
--
ALTER TABLE `QRTZ_TRIGGERS`
  ADD PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  ADD KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  ADD KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
  ADD KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
  ADD KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
  ADD KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
  ADD KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  ADD KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  ADD KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
  ADD KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
  ADD KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
  ADD KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  ADD KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`);

--
-- 表的索引 `t_rbac_admin_role`
--
ALTER TABLE `t_rbac_admin_role`
  ADD PRIMARY KEY (`admin_role_id`);

--
-- 表的索引 `t_rbac_menu`
--
ALTER TABLE `t_rbac_menu`
  ADD PRIMARY KEY (`menu_id`);

--
-- 表的索引 `t_rbac_menu_role`
--
ALTER TABLE `t_rbac_menu_role`
  ADD PRIMARY KEY (`menu_role_id`);

--
-- 表的索引 `t_rbac_permission`
--
ALTER TABLE `t_rbac_permission`
  ADD PRIMARY KEY (`permission_id`);

--
-- 表的索引 `t_rbac_role`
--
ALTER TABLE `t_rbac_role`
  ADD PRIMARY KEY (`role_id`);

--
-- 表的索引 `t_rbac_role_permission`
--
ALTER TABLE `t_rbac_role_permission`
  ADD PRIMARY KEY (`role_permission_id`);

--
-- 表的索引 `t_s_admin`
--
ALTER TABLE `t_s_admin`
  ADD PRIMARY KEY (`admin_id`);

--
-- 表的索引 `t_s_task`
--
ALTER TABLE `t_s_task`
  ADD PRIMARY KEY (`task_id`);

--
-- 表的索引 `t_s_type`
--
ALTER TABLE `t_s_type`
  ADD PRIMARY KEY (`type_id`);

--
-- 表的索引 `t_s_type_group`
--
ALTER TABLE `t_s_type_group`
  ADD PRIMARY KEY (`type_group_id`);

--
-- 限制导出的表
--

--
-- 限制表 `QRTZ_BLOB_TRIGGERS`
--
ALTER TABLE `QRTZ_BLOB_TRIGGERS`
  ADD CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`);

--
-- 限制表 `QRTZ_CRON_TRIGGERS`
--
ALTER TABLE `QRTZ_CRON_TRIGGERS`
  ADD CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`);

--
-- 限制表 `QRTZ_SIMPLE_TRIGGERS`
--
ALTER TABLE `QRTZ_SIMPLE_TRIGGERS`
  ADD CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`);

--
-- 限制表 `QRTZ_SIMPROP_TRIGGERS`
--
ALTER TABLE `QRTZ_SIMPROP_TRIGGERS`
  ADD CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`);

--
-- 限制表 `QRTZ_TRIGGERS`
--
ALTER TABLE `QRTZ_TRIGGERS`
  ADD CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`) REFERENCES `QRTZ_JOB_DETAILS` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
