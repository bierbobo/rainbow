spring data elsaticsearch提供了三种构建查询模块的方式：
1. 基本的增删改查：继承spring data提供的接口就默认提供
2. 接口中声明方法：无需实现类，spring data根据方法名，自动生成实现类，方法名必须符合一定的规则（这里还扩展出一种忽略方法名，根据注解的方式查询）
3. 自定义repository：在实现类中注入elasticsearchTemplate，实现上面两种方式不易实现的查询（例如：聚合、分组、深度翻页等）



