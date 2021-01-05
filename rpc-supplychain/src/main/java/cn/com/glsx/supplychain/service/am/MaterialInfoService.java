package cn.com.glsx.supplychain.service.am;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.glsx.supplychain.mapper.am.MaterialInfoMapper;
import cn.com.glsx.supplychain.model.am.MaterialInfo;

@Service
public class MaterialInfoService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
    private MaterialInfoMapper materialInfoMapper;
	
	public MaterialInfo getMaterialInfoByCode(String materialCode){
		MaterialInfo condition = new MaterialInfo();
		condition.setMaterialCode(materialCode);
		return materialInfoMapper.selectOne(condition);
	}
	
	public String getMaterialNameByCode(String materialCode){
		String result = "";
		if(StringUtils.isEmpty(materialCode)){
			return result;
		}
		MaterialInfo condition = new MaterialInfo();
		condition.setMaterialCode(materialCode);
		MaterialInfo material = materialInfoMapper.selectOne(condition);
		if(null != material){
			result = material.getMaterialName();
		}
		return result;
	}
}
