CREATE TABLE if not exists `salaries` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `emp_no` bigint DEFAULT NULL,
                            `salary` bigint DEFAULT NULL,
                            `from_date` date DEFAULT NULL,
                            `to_date` date DEFAULT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3100001 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='员工薪资表';


