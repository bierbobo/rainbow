read.md


1. 依赖问题
orm  mybatis-spring
ibatis 与mybatis


两个注解研究
context:component-scan
context:annotation-config

beans
default-autowire="byName"

连接池 线程池  数据源BoneCP






3. 数据库sql

create table t_user(
  id int PRIMARY KEY  auto_increment,
  username VARCHAR(20),
  password VARCHAR(20),
  account DECIMAL (10,2)
)
