package cn.com.glsx.supplychain.jst.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.com.glsx.supplychain.jst.mapper.DemoMapper;
import cn.com.glsx.supplychain.jst.model.DemoModel;

/**
 * @Title: DemoService.java
 * @Description:
 * @author deployer name
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@Service
public class DemoService {
	
	@Autowired
	private DemoMapper demoMapper;
	
	public DemoModel load(){
		return demoMapper.load();
	}
}
