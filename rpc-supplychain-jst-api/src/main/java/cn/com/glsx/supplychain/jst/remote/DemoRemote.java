package cn.com.glsx.supplychain.jst.remote;

import cn.com.glsx.supplychain.jst.model.DemoModel;
import cn.com.glsx.supplychain.jst.dto.DemoDTO;

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
