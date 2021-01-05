package cn.com.glsx.supplychain.jxc.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;

import tk.mybatis.mapper.entity.Example;
import cn.com.glsx.supplychain.jxc.dto.JXCMTBsMaterialInfoDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTBsMerchantMaterialCheckDTO;
import cn.com.glsx.supplychain.jxc.mapper.JXCMTBsMerchantMaterialCheckMapper;
import cn.com.glsx.supplychain.jxc.mapper.JXCMTMaterialInfoMapper;
import cn.com.glsx.supplychain.jxc.model.JXCMTBsMerchantMaterialCheck;
import cn.com.glsx.supplychain.jxc.model.JXCMTMaterialInfo;

@Service
public class JXCMTMaterialInfoService {
	private static final Logger logger = LoggerFactory.getLogger(JXCMTMaterialInfoService.class);
	@Autowired
	private JXCMTMaterialInfoMapper jxcmtMaterialInfoMapper;
	@Autowired
	private JXCMTBsMerchantMaterialCheckMapper jxcmtBsMerchantMaterialCheckMapper;
	
	public JXCMTMaterialInfo getMaterialInfoByMaterialCode(String materialCode){
		JXCMTMaterialInfo condition = new JXCMTMaterialInfo();
		condition.setMaterialCode(materialCode);
		return jxcmtMaterialInfoMapper.selectOne(condition);
	}
	
	public Page<JXCMTBsMaterialInfoDTO> pageMaterialInfo(JXCMTBsMaterialInfoDTO record){
		Page<JXCMTBsMaterialInfoDTO> pageResult = new Page<>();
		JXCMTMaterialInfo condition = new JXCMTMaterialInfo();
		condition.setDeviceTypeId(record.getDeviceTypeId());
		condition.setMaterialCode(record.getMaterialCode());
		condition.setMaterialName(record.getMaterialName());
		Page<JXCMTMaterialInfo> pageMaterialInfo = jxcmtMaterialInfoMapper.pageMaterialInfo(condition);
		if(null == pageMaterialInfo){
			return pageResult;
		}
		JXCMTBsMaterialInfoDTO dtoObject = null;
		for(JXCMTMaterialInfo material:pageMaterialInfo){
			dtoObject = new JXCMTBsMaterialInfoDTO();
			dtoObject.setMaterialCode(material.getMaterialCode());
			dtoObject.setMaterialName(material.getMaterialName());
			dtoObject.setDeviceTypeId(material.getDeviceTypeId());
			dtoObject.setDeviceTypeName(material.getDeviceType());
			pageResult.add(dtoObject);
		}
		pageResult.setPageNum(pageMaterialInfo.getPageNum());
		pageResult.setPages(pageMaterialInfo.getPages());
		pageResult.setPageSize(pageMaterialInfo.getPageSize());
		pageResult.setPageSizeZero(pageMaterialInfo.getPageSizeZero());
		pageResult.setTotal(pageMaterialInfo.getTotal());
		return pageResult;
	}
	
	public List<JXCMTBsMerchantMaterialCheckDTO> listMaterialCheck(JXCMTBsMerchantMaterialCheckDTO record){
		JXCMTBsMerchantMaterialCheckDTO dto = null;
		List<JXCMTBsMerchantMaterialCheckDTO> listMaterialCheckDtos = new ArrayList<>();
		Example example = new Example(JXCMTBsMerchantMaterialCheck.class);
		example.createCriteria().andEqualTo("orderMaterialCode",record.getOrderMaterialCode())
								.andEqualTo("deletedFlag", "N");
		List<JXCMTBsMerchantMaterialCheck> listMaterialCheck = jxcmtBsMerchantMaterialCheckMapper.selectByExample(example);
		if(null == listMaterialCheck || listMaterialCheck.isEmpty()){
			dto = new JXCMTBsMerchantMaterialCheckDTO();
			dto.setCheckMaterialCode(record.getOrderMaterialCode());
			dto.setOrderMaterialCode(record.getOrderMaterialCode());
			JXCMTMaterialInfo materialInfo = getMaterialInfoByMaterialCode(record.getOrderMaterialCode());
			if(null != materialInfo){
				dto.setCheckMaterialName(materialInfo.getMaterialName());
				dto.setOrderMaterialName(materialInfo.getMaterialName());
			}
			listMaterialCheckDtos.add(dto);
			return listMaterialCheckDtos;
		}
		for(JXCMTBsMerchantMaterialCheck check:listMaterialCheck){
			dto = new JXCMTBsMerchantMaterialCheckDTO();
			dto.setCheckMaterialCode(check.getCheckMaterialCode());
			dto.setCheckMaterialName(check.getCheckMaterialName());
			dto.setOrderMaterialCode(check.getOrderMaterialCode());
			dto.setOrderMaterialName(check.getOrderMaterialName());
			listMaterialCheckDtos.add(dto);
		}
		return listMaterialCheckDtos;
	}
	
	public List<JXCMTMaterialInfo> listMaterialByListMaterialCode(List<String> listMaterialCodes){
		if(null == listMaterialCodes || listMaterialCodes.isEmpty()){
			return null;
		}
		Example example = new Example(JXCMTMaterialInfo.class);
		example.createCriteria().andIn("materialCode", listMaterialCodes);
		return jxcmtMaterialInfoMapper.selectByExample(example);
	}
	
	public Map<String,JXCMTMaterialInfo> mapMaterialByListMaterialCode(List<String> listMaterialCodes){
		Map<String,JXCMTMaterialInfo> mapResult = new HashMap<>();
		if(null == listMaterialCodes || listMaterialCodes.isEmpty()){
			return mapResult;
		}
		Example example = new Example(JXCMTMaterialInfo.class);
		example.createCriteria().andIn("materialCode", listMaterialCodes);
		List<JXCMTMaterialInfo> listMaterialInfo = jxcmtMaterialInfoMapper.selectByExample(example);
		if(null == listMaterialInfo || listMaterialInfo.isEmpty()){
			return mapResult;
		}
		for(JXCMTMaterialInfo materialInfo:listMaterialInfo){
			mapResult.put(materialInfo.getMaterialCode(), materialInfo);
		}
		return mapResult;
	}

}
