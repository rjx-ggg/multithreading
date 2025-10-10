/*
Navicat MySQL Data Transfer

Source Server         : student
Source Server Version : 50635
Source Host           : localhost:3306
Source Database       : seckill

Target Server Type    : MYSQL
Target Server Version : 50635
File Encoding         : 65001

Date: 2020-06-03 21:19:27
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
                         `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
                         `goods_name` VARCHAR(16) DEFAULT NULL COMMENT '商品名称',
                         `goods_title` VARCHAR(64) DEFAULT NULL COMMENT '商品标题',
                         `goods_img` VARCHAR(64) DEFAULT NULL COMMENT '商品图片',
                         `goods_detail` LONGTEXT COMMENT '商品介绍详情',
                         `goods_price` DECIMAL(10,2) DEFAULT '0.00' COMMENT '商品单价',
                         `goods_stock` INT(11) DEFAULT '0' COMMENT '商品库存，-1表示没有限制',
                         `create_date` DATETIME DEFAULT NULL,
                         `update_date` DATETIME DEFAULT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES ('1', '蜗牛java高并发实战课程', '专注实战', '/img/huawei.jpg', '华为 HUAWEI Mate 40 Pro麒麟9000 SoC芯片', '7268.00', '1000', '2022-09-12 19:06:20', '2022-09-12 19:06:20');

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info` (
                              `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
                              `user_id` BIGINT(20) DEFAULT NULL COMMENT '用户id',
                              `goods_id` BIGINT(20) DEFAULT NULL COMMENT '商品id',
                              `addr_id` BIGINT(20) DEFAULT NULL COMMENT '收货地址id',
                              `goods_name` VARCHAR(16) DEFAULT NULL COMMENT '冗余过来的商品名称',
                              `goods_count` INT(11) DEFAULT NULL COMMENT '商品数量',
                              `goods_price` DECIMAL(10,2) DEFAULT NULL COMMENT '商品价格',
                              `order_channel` INT(2) DEFAULT '0' COMMENT '支付通道：1 PC、2 Android、3 ios',
                              `status` INT(2) DEFAULT NULL COMMENT '订单状态：0 未支付，1已支付，2 已发货，3 已收货，4 已退款，5 已完成',
                              `create_date` DATETIME DEFAULT NULL,
                              `pay_date` DATETIME DEFAULT NULL COMMENT '支付时间',
                              PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=7021 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for seckill_goods
-- ----------------------------
DROP TABLE IF EXISTS `seckill_goods`;
CREATE TABLE `seckill_goods` (
                                 `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
                                 `goods_id` BIGINT(20) DEFAULT NULL COMMENT '商品id',
                                 `seckill_price` DECIMAL(10,2) DEFAULT NULL COMMENT '秒杀价',
                                 `stock_count` INT(11) DEFAULT NULL COMMENT '秒杀数量',
                                 `start_date` DATETIME DEFAULT NULL,
                                 `end_date` DATETIME DEFAULT NULL,
                                 PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of seckill_goods
-- ----------------------------
INSERT INTO `seckill_goods` VALUES ('1', '1', '59', '1', '2024-02-27 19:06:20', '2024-03-27 19:06:20');

-- ----------------------------
-- Table structure for seckill_order
-- ----------------------------
DROP TABLE IF EXISTS `seckill_order`;
CREATE TABLE `seckill_order` (
                                 `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
                                 `user_id` BIGINT(20) DEFAULT NULL,
                                 `order_id` BIGINT(20) DEFAULT NULL,
                                 `goods_id` BIGINT(20) DEFAULT NULL,
                                 `create_date` DATETIME DEFAULT NULL,
                                 PRIMARY KEY (`id`),
                                 UNIQUE KEY `u_userid_goodsid` (`user_id`,`goods_id`)
) ENGINE=INNODB AUTO_INCREMENT=7021 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
                        `id` INT(11) NOT NULL AUTO_INCREMENT,
                        `user_name` VARCHAR(45) DEFAULT NULL,
                        `phone` VARCHAR(11) DEFAULT NULL,
                        `password` VARCHAR(65) DEFAULT NULL,
                        `salt` VARCHAR(45) DEFAULT NULL,
                        `head` VARCHAR(45) DEFAULT NULL,
                        `login_count` INT(11) DEFAULT NULL,
                        `register_date` DATETIME DEFAULT NULL,
                        `last_login_date` DATETIME DEFAULT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1410085408 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user  账号：15898981000  密码 123456 直接登录
-- ----------------------------
INSERT INTO `user` VALUES ('1000', 'user0', '15898981000', '83a1b4fcb2f8d3af5fabfe6562020e10', '1l2j3g', NULL, '1', '2020-06-01 15:50:59', NULL);

