# framework-archetype-rpc

RPC服务项目骨架

一、项目结构说明：

└─src
    ├─main													
      ├─java	
        ├─cn.com.glsx.supplychain.jxc.config                             配置层            										
      	├─cn.com.glsx.supplychain.jxc.manager			 				通用处理层
      	├─cn.com.glsx.supplychain.jxc.mapper								Dao映射层
      	├─cn.com.glsx.supplychain.jxc.remote                             RPC接口实现层
      	├─cn.com.glsx.supplychain.jxc.service							业务层
      	├─cn.com.glsx.supplychain.jxc.util                               工具包
      	└─cn.com.glsx.supplychain.jxc.Main.java                          程序入口
      ├─resources										资源文件目录
        ├─config                                        
          ├─application.properties                      应用配置文件  
        ├─META-INF
          └─orm                                         SQL配置文件
      	├─templates      								模板目录
      	  └─*.flt                                       freemarker模板，（可用于邮件模板）
      	└─logback.xml    								日志配置文件
      └─webapp											web前端文件目录
      	└─WEB-INF
      	  └─bin											shell脚本文件(包含启动、停止、重启等)
    └─test												测试目录
	  ├─java							
	  	└─cn.com.glsx.supplychain.jxc/service							测试代码
	  └─resources										测试资源目录
└─pom.xml												pom文件

二、使用说明：
	1、运行项目前需在配置中心创建配置信息，配置内容参考三配置说明：
		1.1> dataId  : application.properties --> ore.diamond.dataId + ore.diamond.env 
		1.2> group   : application.properties --> ore.diamond.group 
		1.3> content : 参考三配置说明 
	2、生成mybatis文件的插件，使用说明：
	    1.1> 生成之后可以删掉插件
	    1.2> IDEA内：点击右侧Maven Projects > 项目 plugins > mybatis-generator 运行
	    1.3> ECLIPSE内：右键点击项目，选择RUN AS maven build > 输入maven命令：
                mybatis-generator:generate -e > 运行
	3、Bean转换器mapstruct使用说明：
	    1.1> 在converter包内建立Bean转换对应的转换器，传入source和target的类型。
	    1.2> 在remote内使用spring的方式引入bean。
	    1.3> Bean的属性名一样的情况下，不需要重写方法。
	4、自定义过滤器加入方式：
	    1.1> 在src.main.resources.META-INF.dubbo文件夹下新建文件com.alibaba.dubbo.rpc.Filter
	    1.2> 在该文件内写入自定义过滤器类的完整地址，以键值对的形式加入，左边为key名称，例如：
	         dubboAuthorizeFilter=cn.com.glsx.payment.roof.filter.DubboAuthorizeFilter
	    1.3> 在钻石配置中加入dubbo过滤器的配置，名字为key，例如：
	         ore.dubbo.provider.enabled=true
             ore.dubbo.provider.filter=dubboAuthorizeFilter
	    
三、配置说明:
#----------------------------------------------------------------------------------------------------------
# dubbo配置
# dubbo应用名称
ore.dubbo.application.name=rpc-supplychain-jxc
# dubbo应用所有者
ore.dubbo.application.owner=deployer name
# dubbo服务注册地址
ore.dubbo.registry.address=zookeeper://192.168.3.206:2181
# dubbo服务注册超时时间
ore.dubbo.registry.timeout=30000
# 默认协议：dubbo
ore.dubbo.protocol.name=dubbo
# dubbo服务扫描包路径
ore.dubbo.scan=cn.com.glsx.supplychain.jxc.remote
# dubbo自定义过滤器配置
ore.dubbo.provider.enabled=true
ore.dubbo.provider.filter=过滤器名称

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

# druid连接池监控配置
# 是否开启监控
ore.druid.monitor.enabled=true
# 监控后台用户名，默认：admin
ore.druid.monitor.login-username=admin
# 监控后台密码，默认：admin123
ore.druid.monitor.login-password=admin123
# 监控访问路径匹配规则，默认：/druid/*
ore.druid.monitor.url-mappings=/druid/*
# 监控访问IP白名单，多个以英文逗号“,”隔开，默认：127.0.0.1
ore.druid.monitor.allow=127.0.0.1
# 监控访问IP黑名单(存在共同时，deny优先于allow)，多个以英文逗号“,”隔开
ore.druid.monitor.deny=192.168.0.1
# 是否能够重置数据，默认：false，则隐藏页面重置按钮
ore.druid.monitor.reset-enable=false

#----------------------------------------------------------------------------------------------------------
# mybatis配置
# 实体模型包
mybatis.type-aliases-package=cn.com.glsx.supplychain.jxc.model
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
# 后缀设置，默认为.ftl
spring.freemarker.suffix=.ftl

       ┌------------------------------------------------------------┐
                            注：如需使用freemarker需在pom中添加如下依赖                                                                                                 
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-freemarker</artifactId>
            <exclusions>
                <exclusion>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>*</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
       └------------------------------------------------------------┘

#----------------------------------------------------------------------------------------------------------
# kafka生产者配置
spring.kafka.bootstrap-servers=192.168.3.207:9092
spring.kafka.template.default-topic=消息主题

       ┌------------------------------------------------------------┐
                            注：如需使用kafka发送消息需在pom中添加如下依赖                    
        <dependency>
            <groupId>org.springframework.kafka</groupId>
            <artifactId>spring-kafka</artifactId>
        </dependency>
       └------------------------------------------------------------┘
       
#----------------------------------------------------------------------------------------------------------
# kafka消费者配置
spring.kafka.consumer.group-id=rpc-supplychain-jxc-consumer
spring.kafka.consumer.auto-offset-reset=smallest
spring.kafka.properties.zookeeper.connect=192.168.3.207:2181
spring.kafka.properties.zookeeper.connection.timeout.ms=15000
spring.kafka.properties.zookeeper.session.timeout.ms=15000
spring.kafka.properties.nThreads=5
spring.kafka.properties.topic=消息主题
spring.kafka.properties.auto.commit.interval.ms=1000

       ┌------------------------------------------------------------┐
                            注：如需使用kafka接收消息需在pom中添加如下依赖                    
        <dependency>
            <groupId>org.oreframework.boot</groupId>
            <artifactId>ore-boot-starter-kafka</artifactId>
        </dependency>
       └------------------------------------------------------------┘