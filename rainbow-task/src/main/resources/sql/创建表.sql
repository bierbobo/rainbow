CREATE TABLE task (
 business_key varchar(100) NOT NULL COMMENT '任务id',
 business_type varchar(255) NOT NULL COMMENT '任务类型',
 uuid varchar(255) DEFAULT NULL COMMENT '任务uuid值，用于关联任务的其他信息表',
 name varchar(1000) DEFAULT NULL COMMENT '任务名称',
 state int(11) DEFAULT NULL COMMENT '任务状态',

 server_ip varchar(20) DEFAULT NULL COMMENT '处理任务的服务器ip',
 create_time datetime DEFAULT NULL COMMENT '创建时间',
 update_time timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
 message varchar(2000) DEFAULT NULL COMMENT '任务信息',

 PRIMARY KEY (business_type,business_key),
 KEY uuid_index (uuid),
 KEY idx_createtime (create_time),
 KEY idx_create_type (business_type,state,create_time)
 )
 --ENGINE=InnoDB DEFAULT CHARSET=utf8
 COMMENT='任务表'







