-- 初始化表格前先删除
drop table if exists `user`;

-- 创建表格
create table `user`
(
	`id`       int unsigned auto_increment,
	`username` varchar(16) not null,
	`password` varchar(32) not null,
	primary key (`id`)
) engine = InnoDB
  default charset = utf8mb4;