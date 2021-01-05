# framework-archetype-webapp

运营平台项目骨架

一、项目结构说明：

└─src
    ├─main													
      ├─java	
        ├─cn.com.glsx.supplychain.jst.config                             配置层            										
      	├─cn.com.glsx.supplychain.jst.controller			 				控制层
      	├─cn.com.glsx.supplychain.jst.vo								             视图对象
      	├─cn.com.glsx.supplychain.jst.service							业务层
      	├─cn.com.glsx.supplychain.jst.Main.java                          程序入口
      	└─cn.com.glsx.supplychain.jst.WebMvcConfigurer.java              MVC拦截器配置类
      ├─resources										资源文件目录
        ├─config                                        
          ├─application.properties                      应用配置文件  
      	├─templates      								模板目录
      	  └─*.flt                                       freemarker模板，（可用于邮件模板）
      	└─logback.xml    								日志配置文件
      └─webapp											web前端文件目录
      	├─static										静态文件
      	  ├─css											样式文件
      	  ├─images										图片文件
      	  └─js											JavaScript文件
      	  	└─common									公共js文件
      	├─views
      	  └─model                                       模块文件夹
      	     └─*.html  	                                                                                                          视图文件
      	└─WEB-INF
      	  └─bin											shell脚本文件(包含启动、停止、重启等)
    └─test												测试目录
	  ├─java							
	  	└─cn.com.glsx.supplychain.jst/service							测试代码
	  └─resources										测试资源目录
└─pom.xml												pom文件

二、使用说明：
	1、运行项目前需在配置中心创建配置信息，配置内容参考三配置说明：
		1.1> dataId  : application.properties --> ore.diamond.dataId + ore.diamond.env 
		1.2> group   : application.properties --> ore.diamond.group 
		1.3> content : 参考三配置说明 
		
三、配置说明:
#----------------------------------------------------------------------------------------------------------
# 适配器配置
ore.adapter.enabled=true
# 是否开启请求日志拦截器
ore.adapter.request-log-interceptor=enabled
# 是否开启跨域访问
ore.adapter.cross-domain=enabled

#----------------------------------------------------------------------------------------------------------
# dubbo配置
# dubbo应用名称
ore.dubbo.application.name=webapp-supplychain-jst
# dubbo应用所有者
ore.dubbo.application.owner=deployer name
# dubbo服务注册地址
ore.dubbo.registry.address=zookeeper://192.168.3.206:2181
# dubbo服务注册超时时间
ore.dubbo.registry.timeout=30000
# 默认协议：dubbo
ore.dubbo.protocol.name=dubbo
# dubbo服务扫描包路径
ore.dubbo.scan=cn.com.glsx.supplychain.jst

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
# Thymeleaf模板配置
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
# 模板模式设置，默认为HTML5严格检查；LEGACYHTML5更友好的格式，适用于Vue.js和AngularJs等js库
spring.thymeleaf.mode=HTML5
# 前缀设置，SpringBoot默认模板放置在classpath:/templates/目录下
spring.thymeleaf.prefix=/views/
# 后缀设置，默认为.html
spring.thymeleaf.suffix=.html

       ┌----------------------------------------------------------------------------------------┐
                            注：如需使用thymeleaf需在pom中添加如下依赖，且需排除thymelear对springboot的依赖，否则引起包冲突，导致diamond配置不加载                                                                                          
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
            <exclusions>
                <exclusion>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>*</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
                            注：如模板模式设置为LEGACYHTML5，则需在pom中添加如下依赖
        <dependency>
            <groupId>net.sourceforge.nekohtml</groupId>
            <artifactId>nekohtml</artifactId>
        </dependency>
       └----------------------------------------------------------------------------------------┘

#----------------------------------------------------------------------------------------------------------        
# 上传文件大小配置
spring.http.multipart.max-file-size=5MB
spring.http.multipart.max-request-size=5MB
