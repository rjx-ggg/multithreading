

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `member`  (
                         `id` int(5) NOT NULL AUTO_INCREMENT,
                         `nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                         `tenant_id` int(11) NULL DEFAULT 0 COMMENT '租户id',
                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `member` VALUES (1,  '蜗牛侠', 1);
INSERT INTO `member` VALUES (2,  '蝙蝠侠', 2);
INSERT INTO `member` VALUES (3,  '猪猪侠', 3);

SET FOREIGN_KEY_CHECKS = 1;