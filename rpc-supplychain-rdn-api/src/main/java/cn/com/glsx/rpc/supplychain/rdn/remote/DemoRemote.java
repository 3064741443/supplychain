package cn.com.glsx.rpc.supplychain.rdn.remote;

import cn.com.glsx.rpc.supplychain.rdn.model.DemoModel;
import cn.com.glsx.rpc.supplychain.rdn.dto.DemoDTO;

/**
 * @Title: DemoService.java
 * @Description:
 * @author ${userName}
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
public interface DemoRemote {

	DemoModel get(String name);

	DemoDTO convert(DemoModel model);
}
