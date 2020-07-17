/*
Navicat MySQL Data Transfer

Source Server         : o2o
Source Server Version : 50619
Source Host           : 172.16.21.6:3306
Source Database       : autozi_sps

Target Server Type    : MYSQL
Target Server Version : 50619
File Encoding         : 65001

Date: 2016-05-19 09:43:35
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `par_party`
-- ----------------------------
DROP TABLE IF EXISTS `par_party`;
CREATE TABLE `par_party` (
  `id` decimal(19,0) NOT NULL COMMENT '主键',
  `version` decimal(10,0) NOT NULL DEFAULT '10' COMMENT '乐观锁',
  `code` varchar(25) DEFAULT NULL COMMENT '编码',
  `name` varchar(255) DEFAULT NULL COMMENT '企业全称',
  `name_first_letter` varchar(255) DEFAULT NULL COMMENT '简称首字母',
  `short_name` varchar(64) DEFAULT NULL COMMENT '名称首字母',
  `short_name_first_letter` varchar(64) DEFAULT NULL COMMENT '简称首字母',
  `area_id` decimal(19,0) DEFAULT NULL,
  `email` varchar(64) DEFAULT NULL COMMENT '企业邮箱',
  `phone` varchar(64) DEFAULT NULL COMMENT '企业联系方式',
  `mobile` varchar(64) DEFAULT NULL,
  `connector` varchar(64) DEFAULT NULL COMMENT '企业联系人',
  `web` varchar(64) DEFAULT NULL COMMENT '企业网站',
  `fax` varchar(64) DEFAULT NULL COMMENT '企业传真',
  `post_code` varchar(16) DEFAULT NULL COMMENT '邮编',
  `address` varchar(128) DEFAULT NULL COMMENT '企业联系地址',
  `status` int(11) DEFAULT NULL COMMENT '状态',
  `party_type` int(11) NOT NULL COMMENT '类型',
  `party_class` int(11) NOT NULL COMMENT '类别',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_party_id` decimal(19,0) DEFAULT NULL,
  `create_user_id` decimal(19,0) DEFAULT NULL COMMENT '创建人',
  `business_area` varchar(128) DEFAULT NULL COMMENT '业务区域',
  `key_words` varchar(128) DEFAULT NULL COMMENT '助记码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='主体';

-- ----------------------------
-- Records of par_party
-- ----------------------------










-- ----------------------------
-- Table structure for `aut_user`
-- ----------------------------
DROP TABLE IF EXISTS `aut_user`;
CREATE TABLE `aut_user` (
  `id` decimal(19,0) NOT NULL COMMENT '主键',
  `version` decimal(19,0) NOT NULL DEFAULT '10' COMMENT '乐观锁',
  `name` varchar(32) DEFAULT NULL COMMENT '用户名',
  `login_name` varchar(32) DEFAULT NULL COMMENT '登录名-账号',
  `password` varchar(128) DEFAULT NULL COMMENT '密码',
  `party_id` decimal(19,0) NOT NULL COMMENT '所属主体',
  `user_type` int(11) DEFAULT NULL COMMENT '用户类型【管理员、普通用户、业务员】',
  `phone` varchar(32) DEFAULT NULL COMMENT '联系方式',
  `email` varchar(64) DEFAULT NULL COMMENT '电子邮箱',
  `image_url` varchar(255) DEFAULT NULL COMMENT '头像图片地址',
  `admin` tinyint(1) NOT NULL COMMENT '是否为管理员',
   `can_login` tinyint(1) NOT NULL COMMENT '是否为管理员',
  `status` int(11) NOT NULL COMMENT '用户状态【正常、锁定、不可用】',
  `login_time` timestamp NULL DEFAULT NULL COMMENT '最后一次登录时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` decimal(19,0) NOT NULL COMMENT '创建人',
  `last_login_time` timestamp NULL DEFAULT NULL COMMENT '最后一次登录时间',
  `department_id` decimal(19,0) DEFAULT NULL,
  `party_type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `I_aut_user_login_name` (`login_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表\r\n';

-- ----------------------------
-- Records of aut_user
-- ----------------------------
