# framework-archetype-rpc-api

RPC-API项目骨架

一、项目结构说明：

└─src
    ├─main													
      ├─java	
        ├─cn.com.glsx.supplychain.jxc.dto                                数据传输模型层            										
      	├─cn.com.glsx.supplychain.jxc.enums   			 				枚举定义
      	├─cn.com.glsx.supplychain.jxc.model                              数据模型层
      	└─cn.com.glsx.supplychain.jxc.remote                             RPC暴露服务层
      └─resources										资源文件目录
        └─rpc                                        
          └─dubbo.xml                                   dubbo接口配置
└─pom.xml												pom文件

二、使用说明：
	1、API改动后，请及时deploy到Maven仓库
三、注意事项：
    1、rpc-api-deploy-maven-plugin api上报插件需配置当前api的jar包的引用。