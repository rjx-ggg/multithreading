CREATE DATABASE IF NOT EXISTS db_msg default charset utf8mb4 COLLATE utf8mb4_unicode_ci;


CREATE TABLE `db_msg`.`msg_log` (
  `msg_id` varchar(255) NOT NULL DEFAULT '' COMMENT '消息唯一标识',
  `exchange` varchar(100) NOT NULL DEFAULT '' COMMENT '交换机',
  `route_key` varchar(100) NOT NULL DEFAULT '' COMMENT '路由键',
  `queue_name` varchar(100) NOT NULL DEFAULT '' COMMENT '队列名称',
  `msg` text COMMENT '消息体, json格式化',
  `result` varchar(255) DEFAULT NULL COMMENT '处理结果',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态，0：等待消费，1：消费成功，2：消费失败，9：重试失败',
  `try_count` int(11) NOT NULL DEFAULT '0' COMMENT '重试次数',
  `next_try_time` datetime DEFAULT NULL COMMENT '下一次重试时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`msg_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='mq消息日志';