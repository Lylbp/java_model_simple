### 组织结构
``` lua
├── common -- 项目常用工具与基类包
    ├── annotation -- 自定义注解
        ├── ActionLog -- 操作日志注解
        ├── CheckPermission -- 操作权限注解
    ├── aspect -- AOP编程
        ├── ActionLogAspect -- 操作日志
        ├── LogAspect -- 控制台打印日志
    ├── constant -- 常量
        ├── ProjectConstant -- 项目常量
    ├── entity -- 项目中的自定义基类
        ├── LabelVO    -- 所有key-value对象可用
        ├── PageResResult -- 页面结果集
        ├── ResResult -- 结果集
        ├── DataPage  -- 数据分页对象
    ├── enums -- 枚举
        ├── ResResultEnum -- 结果集枚举
        ├── TrueOrFalseEnum -- 是否枚举
    ├── utils -- 工具类
    ├── exception -- 自定义异常
        ├── ResResultException -- 结果集异常
├── core -- 系统核心
    ├── configure -- 系统配置
        ├── CorsConfig -- 跨域配置
        ├── JacksonHttpMessageConverter Jackson转换器
        ├── ProjectWebMvcConfigurer -- 核心配置
    ├── filter -- 自定义过滤器
        ├── ApiFilter -- Api过滤器
    ├── handler -- 自定义处理器
        ├── ExceptionHandle -- 自定义异常处理器
    ├── interceptor -- 自定义拦截器
        ├── ApiInterceptor -- Api拦截器
        ├── NewCrossDomainInterceptor -- 跨域拦截器
    ├── properties -- 框架配置项
├── project -- 项目代码
├── manager -- 三方集成
### 代码生成器
https://github.com/Lylbp/mybatis-plus-generator

### 项目初始化
``` lua
1.进入src/main/resources根据你项目所用的数据库类型选择application-mysql.yml／application-oracle.yml
下面以mysql为例
    进入 src/main/resources/application.yml修改active为mysql
    cp application-mysql.yml.ref application-mysql.yml
注意application-mysql.yml不会提交到git中,请将文件中的配置项修改成自己的参数

2.示例使用mysql数据库, 表结构在src/main/resources/java_model.sql
```

### 后端RBAC接口权限管理
``` lua
1.权限分为登录权限验证以及接口访问权限验证
2.使用security必须实现MyUserDetailsService中的loadUserByUsername以及token2SecurityUser
3.默认接口都需要登录权限,若希望特定的接口或静态资源不需要登录权限,则需要在application-mysql.yml中配置allow-api以及allow-static
4.接口访问权限默认关闭,可在application-mysql.yml中通过enabled控制
5.若接口访问权限权限开启需要实现MyUserDetailsService中的getConfigAttributes函数
```