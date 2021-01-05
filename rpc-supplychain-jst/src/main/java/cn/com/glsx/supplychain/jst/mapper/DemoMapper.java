package cn.com.glsx.supplychain.jst.mapper;

import cn.com.glsx.supplychain.jst.model.DemoModel;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

/**
 * @Title: DemoMapper.java
 * @Description:
 * @author deployer name
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@Mapper
public interface DemoMapper extends OreMapper<DemoModel> {
	
	DemoModel load();
}
