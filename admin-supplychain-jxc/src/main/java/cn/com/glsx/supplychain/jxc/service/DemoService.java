package cn.com.glsx.supplychain.jxc.service;

import cn.com.glsx.supplychain.jxc.mapper.DemoMapper;
import cn.com.glsx.supplychain.jxc.model.DemoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	private DemoMapper mapper;
	
	public DemoModel load(){
		return mapper.load();
	}
}
