mybatis学习计划


基本构成： FactoryBuilder  Factroy Session  Mapper
settings配置： property  typeAliases   typeHandler plugins environments  mappers
映射器：   select: 简单类型 自动映射  多个参数 resultMap
            insert  update delete  # $ sql
            resultMap :map pojo 级联
          缓存cache  : 一级 二级 缓存

动态sql   if  choose when otherwise
          trim where set  foreach  test build

运行原理 ： 反射 动态代理
             构建factroy ,session运行以及下面四大对象 ，发送拼接参数，转换结果。

插件 ： 分页插件

调用存储过程

mybatis-spring   : 配置template  mapper 事务



其他：

参数查询  string map  list   bean
级联查询 121 12n n2n

标签  isNotEmpty   isNull
原理与插件，分页插件
存储过程


问题：
1.批量更新



sql:

drop table t_user;
create table t_user(
  id int PRIMARY KEY  auto_increment,
  username VARCHAR(20),
  password VARCHAR(20),
  account DECIMAL (10,2),
  creator_erp VARCHAR(20),
  create_time timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
insert into t_user(username,password,account,creator_erp)values('中文','2',111,'abc');
select * from t_user;


