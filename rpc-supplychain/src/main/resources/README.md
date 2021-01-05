# framework-archetype-rpc

RPC服务项目骨架

一、项目结构说明：

└─src
    ├─main													
      ├─java	
        ├─cn.com.glsx.supplychain.config                             配置层            										
      	├─cn.com.glsx.supplychain.manager			 				通用处理层
      	├─cn.com.glsx.supplychain.mapper								Dao映射层
      	├─cn.com.glsx.supplychain.remote                             RPC接口实现层
      	├─cn.com.glsx.supplychain.service							业务层
      	├─cn.com.glsx.supplychain.util                               工具包
      	└─cn.com.glsx.supplychain.Main.java                          程序入口
      ├─resources										资源文件目录
        ├─config                                        
          ├─application.properties                      应用配置文件  
      	├─templates      								模板目录
      	  └─*.flt                                       freemarker模板，（可用于邮件模板）
      	└─logback.xml    								日志配置文件
      └─webapp											web前端文件目录
      	└─WEB-INF
      	  └─bin											shell脚本文件(包含启动、停止、重启等)
    └─test												测试目录
	  ├─java							
	  	└─cn.com.glsx.supplychain/service							测试代码
	  └─resources										测试资源目录
└─pom.xml												pom文件

二、使用说明：
	1、运行项目前需在配置中心创建配置信息，配置内容参考三配置说明：
		1.1> dataId  : application.properties --> ore.diamond.dataId + ore.diamond.env 
		1.2> group   : application.properties --> ore.diamond.group 
		1.3> content : 参考三配置说明 
		
三、配置说明:
#----------------------------------------------------------------------------------------------------------
# dubbo配置
# dubbo应用名称
ore.dubbo.application.name=rpc-supplychain
# dubbo应用所有者
ore.dubbo.application.owner=leiyj
# dubbo服务注册地址
ore.dubbo.registry.address=zookeeper://192.168.3.206:2181
# dubbo服务注册超时时间
ore.dubbo.registry.timeout=30000
# dubbo服务扫描包路径
ore.dubbo.scan=cn.com.glsx.supplychain

#----------------------------------------------------------------------------------------------------------
# druid连接池及数据源配置
ore.druid.url=jdbc:mysql://host:port/数据库名?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull
ore.druid.driver-class=com.mysql.jdbc.Driver
ore.druid.username=用户名
ore.druid.password=密码
ore.druid.initial-size=1
ore.druid.min-idle=1
ore.druid.max-active=20
ore.druid.test-on-borrow=true

#----------------------------------------------------------------------------------------------------------
# mybatis配置
# 实体模型包
mybatis.type-aliases-package=cn.com.glsx.supplychain.model
# SQL脚本文件路径
mybatis.mapper-locations=classpath:/META-INF/orm/*Mapper.xml
mapper.mappers=org.oreframework.datasource.mybatis.mapper.OreMapper
mapper.not-empty=false
mapper.identity=MYSQL

#----------------------------------------------------------------------------------------------------------
# 分页配置
pagehelper.helperDialect=mysql
pagehelper.reasonable=true
pagehelper.supportMethodsArguments=true
pagehelper.params=count=countSql

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
# kafka配置
spring.kafka.bootstrap-servers=192.168.3.207:9092
spring.kafka.template.default-topic=消息主题