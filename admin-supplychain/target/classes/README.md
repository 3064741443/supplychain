# framework-archetype-admin

运营平台项目骨架

一、项目结构说明：

└─src
    ├─main													
      ├─java	
        ├─cn.com.glsx.supplychain.config                             配置层            										
      	├─cn.com.glsx.supplychain.controller			 				控制层
      	├─cn.com.glsx.supplychain.enums								枚举定义
      	├─cn.com.glsx.supplychain.mapper								持久层接口映射
      	├─cn.com.glsx.supplychain.model								数据模型定义
      	├─cn.com.glsx.supplychain.pojo								领域模型DOT、DO、VO、BO等
      	├─cn.com.glsx.supplychain.service							业务层
      	├─cn.com.glsx.supplychain.Main.java                          程序入口
      	└─cn.com.glsx.supplychain.WebMvcConfigurer.java              MVC拦截器配置类
      ├─resources										资源文件目录
        ├─config                                        
          ├─application.properties                      应用配置文件  
      	├─META-INF
      	  └─orm											SQL配置文件
      	├─templates      								模板目录
      	  └─*.flt                                       freemarker模板，（可用于邮件模板）
      	└─logback.xml    								日志配置文件
      └─webapp											web前端文件目录
      	├─static										静态文件
      	  ├─css											样式文件
      	  ├─images										图片文件
      	  └─js											JavaScript文件
      	  	└─common									angular框架文件
      	├─views
      	  ├─common                                      angular框架文件
      	  └─model
      	     ├─controller                               业务js
      	     ├─view                                     视图文件
      	     ├─route.js                                 路由配置
      	     └─services.js                              接口定义  
      	└─WEB-INF
      	  └─bin											shell脚本文件(包含启动、停止、重启等)
    └─test												测试目录
	  ├─java							
	  	└─cn.com.glsx.supplychain							                          测试代码
	  └─resources										测试资源目录
└─pom.xml												pom文件

二、使用说明：
	1、如无需访问数据库
	    1.1> cn.com.glsx.supplychain.mapper; cn.com.glsx.supplychain.model; META-INF/orm; 等目录可以删除
	    1.2> 删除pom中ore-boot-starter-datasource依赖或者添加 1.3 配置
	    1.3> 添加配置spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration或删除 1.2 依赖配置
	2、运行项目前需在配置中心创建配置信息，配置内容参考三配置说明：
		2.1> dataId  : application.properties --> ore.diamond.dataId + ore.diamond.env 
		2.2> group   : application.properties --> ore.diamond.group 
		2.3> content : 参考三配置说明 
		
三、配置说明:
#----------------------------------------------------------------------------------------------------------
# 运营后台静态资源CDN路径（必选配置）
oms.static-path=http://192.168.3.217:9430/static/

#----------------------------------------------------------------------------------------------------------
# cas配置（开发环境可不配置）
# CAS服务器地址
ore.cas.server-url=http://192.168.3.138:8089/cas
# 运营平台BOSS
ore.app.boss-url=http://192.168.3.238:18080/

#----------------------------------------------------------------------------------------------------------
# dubbo配置（调用dubbo服务需配置）
# dubbo应用名称
ore.dubbo.application.name=admin-supplychain
# dubbo应用所有者
ore.dubbo.application.owner=zhoudan
# dubbo服务注册地址
ore.dubbo.registry.address=zookeeper://192.168.3.206:2181
# dubbo服务注册超时时间
ore.dubbo.registry.timeout=30000
# dubbo服务扫描包路径
ore.dubbo.scan=cn.com.glsx.supplychain

#----------------------------------------------------------------------------------------------------------
# druid连接池及数据源配置（操作数据库需配置）
ore.druid.url=jdbc:mysql://host:port/数据库名?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull
ore.druid.driver-class=com.mysql.jdbc.Driver
ore.druid.username=用户名
ore.druid.password=密码
ore.druid.initial-size=1
ore.druid.min-idle=1
ore.druid.max-active=20
ore.druid.test-on-borrow=true

#----------------------------------------------------------------------------------------------------------
# mybatis配置（操作数据库需配置）
# 实体模型包
mybatis.type-aliases-package=cn.com.glsx.supplychain.model
# SQL脚本文件路径
mybatis.mapper-locations=classpath:/META-INF/orm/*Mapper.xml
mapper.mappers=org.oreframework.datasource.mybatis.mapper.OreMapper
mapper.not-empty=false
mapper.identity=MYSQL

#----------------------------------------------------------------------------------------------------------
# 分页配置（操作数据库需配置）
pagehelper.helperDialect=mysql
pagehelper.reasonable=true
pagehelper.supportMethodsArguments=true
pagehelper.params=count=countSql

#----------------------------------------------------------------------------------------------------------
# 静态文件路径，默认：classpath:/META-INF/resources/,classpath:/resources/,classpath:/static/,classpath:/public/
spring.resources.static-locations:classpath:/META-INF/resources/,classpath:/resources/,classpath:/static/,classpath:/public/,/views/

#----------------------------------------------------------------------------------------------------------
# Freemarker模板配置
# 模板存放路径
spring.freemarker.template-loader-path=classpath:/templates/
# 是否开启模板缓存，默认是开启，开发时请关闭
spring.freemarker.cache=false
# 模板的编码设置，默认UTF-8
spring.freemarker.charset=UTF-8
# 是否检测模板已存在本地
spring.freemarker.check-template-location=true
# 模板的媒体类型设置，默认为text/html
spring.freemarker.content-type=text/html
spring.freemarker.expose-request-attributes=true
spring.freemarker.expose-session-attributes=true
spring.freemarker.request-context-attribute=request
# 后缀设置，默认为.html
spring.freemarker.suffix=.ftl

       ┌------------------------------------------------------------┐
                            注：如需使用freemarker需在pom中添加如下依赖                                                                                                 
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-freemarker</artifactId>
        </dependency>
       └------------------------------------------------------------┘

#----------------------------------------------------------------------------------------------------------
# Thymeleaf模板配置（必选配置）
# 是否开启模板缓存，默认是开启，开发时请关闭
spring.thymeleaf.cache=true
# 是否检测模板已存在本地
spring.thymeleaf.check-template-location=true
# 模板的媒体类型设置，默认为text/html
spring.thymeleaf.content-type=text/html
# 模板开关
spring.thymeleaf.enabled=true
# 模板的编码设置，默认UTF-8
spring.thymeleaf.encoding=UTF-8
# 排除的视图
spring.thymeleaf.excluded-view-names=
# 模板模式设置，默认为HTML5严格检查；LEGACYHTML5更友好的格式，适用于Vue.js和AngularJs等js库（必选配置）
spring.thymeleaf.mode=LEGACYHTML5
# 前缀设置，SpringBoot默认模板放置在classpath:/templates/目录下（必选配置）
spring.thymeleaf.prefix=/views/
# 后缀设置，默认为.html
spring.thymeleaf.suffix=.html

       ┌------------------------------------------------------------┐
                            注：如需使用thymeleaf需在pom中添加如下依赖                                                                                                    
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
                            注：如模板模式设置为LEGACYHTML5，则需在pom中添加如下依赖
        <dependency>
            <groupId>net.sourceforge.nekohtml</groupId>
            <artifactId>nekohtml</artifactId>
        </dependency>
       └------------------------------------------------------------┘
        
#----------------------------------------------------------------------------------------------------------
# kafka配置
spring.kafka.bootstrap-servers=192.168.3.207:9092
spring.kafka.template.default-topic=消息主题

#----------------------------------------------------------------------------------------------------------        
# 上传文件大小配置
spring.http.multipart.max-file-size=5MB
spring.http.multipart.max-request-size=5MB


