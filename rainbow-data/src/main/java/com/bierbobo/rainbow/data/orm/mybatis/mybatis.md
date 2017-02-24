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
