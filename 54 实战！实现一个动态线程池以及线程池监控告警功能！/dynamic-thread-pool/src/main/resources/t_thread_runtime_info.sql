DROP TABLE IF EXISTS `t_thread_runtime_info`;
CREATE TABLE `t_thread_runtime_info`
(
    `id`                       bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键ID',
    `item_id`                  varchar(80) DEFAULT NULL COMMENT '项目ID',
    `thread_pool_name`         varchar(150)  DEFAULT NULL COMMENT '线程池名字',
    `core_pool_size`           int(11) DEFAULT NULL COMMENT '核心线程数',
    `maximum_pool_size`        int(11) DEFAULT NULL COMMENT '最大线程数',
    `queue_capacity`           int(11) DEFAULT NULL COMMENT '队列容量',
    `keepalive_time`           int(11) DEFAULT NULL COMMENT '线程空闲超时时间',
    `pool_size`                int(11) DEFAULT NULL COMMENT '线程数',
    `active_size`              int(11) DEFAULT NULL COMMENT '活跃线程数',
    `queue_size`               int(11) DEFAULT NULL COMMENT '队列元素',
    `queue_remaining_capacity` int(11) DEFAULT NULL COMMENT '队列剩余容量',
    `largest_pool_size`        int(11) DEFAULT NULL COMMENT '线程池曾经创建过的最大线程数',
    `task_count`               bigint(20) DEFAULT NULL COMMENT '总任务计数',
    `completed_task_count`     bigint(20) DEFAULT NULL COMMENT '已完成任务计数',
    `current_load`             decimal(10,2) DEFAULT NULL COMMENT '当前负载',
    `maximum_load`             decimal(10,2) DEFAULT NULL COMMENT '峰值负载',
    `queue_type`               varchar(150)  DEFAULT NULL COMMENT '任务队列类型',
    `thread_factory_type`      varchar(150)  DEFAULT NULL COMMENT '线程工厂类型',
    `rejected_handler_type`    varchar(150)  DEFAULT NULL COMMENT '任务拒绝策略类型',
    `timestamp`                bigint(20) DEFAULT NULL COMMENT '索引时间戳',
    `create_time`              datetime     DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_timestamp` (`timestamp`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;