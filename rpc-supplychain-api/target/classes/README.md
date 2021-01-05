# framework-archetype-rpc-api

RPC-API项目骨架

一、项目结构说明：

└─src
    ├─main													
      ├─java	
        ├─cn.com.glsx.supplychain.dto                                数据传输模型层            										
      	├─cn.com.glsx.supplychain.enums   			 				枚举定义
      	├─cn.com.glsx.supplychain.exception   						自定义异常
      	├─cn.com.glsx.supplychain.model                              数据模型层
      	└─cn.com.glsx.supplychain.remote                             RPC接口实现层
      └─resources										资源文件目录
        └─rpc                                        
          └─dubbo.xml                                   dubbo接口配置
└─pom.xml												pom文件

二、使用说明：
	1、API改动后，请及时deploy到Maven仓库