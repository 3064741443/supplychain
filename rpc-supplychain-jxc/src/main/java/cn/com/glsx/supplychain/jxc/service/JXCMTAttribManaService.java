package cn.com.glsx.supplychain.jxc.service;

import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.jxc.dto.JXCMTAttribManaDTO;
import cn.com.glsx.supplychain.jxc.enums.JXCErrorCodeEnum;
import cn.com.glsx.supplychain.jxc.mapper.JXCMTAttribManaMapper;
import cn.com.glsx.supplychain.jxc.model.JXCMTAttribMana;
import cn.com.glsx.supplychain.jxc.model.JXCMTDeviceType;
import cn.com.glsx.supplychain.jxc.model.JXCMTMaterialInfo;
import cn.com.glsx.supplychain.jxc.util.JxcUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JXCMTAttribManaService {

	private static final Logger logger = LoggerFactory.getLogger(JXCMTAttribManaService.class);
	@Autowired
	private JXCMTAttribManaMapper jxcmtAttribManaMapper;
	@Autowired
	private JXCMTAttribInfoService jxcmtAttribInfoService;
	@Autowired
	private JXCMTDeviceTypeService jxcmtDeviceTypeService;
	@Autowired
	private JXCMTMaterialInfoService jxcmtMaterialInfoService;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
	public Integer saveAttribMana(JXCMTAttribManaDTO record) throws RpcServiceException {
		JXCMTAttribMana attribMana = new JXCMTAttribMana();
		attribMana.setAttribCode(record.getAttribCode());
		JXCMTAttribMana dbResult = jxcmtAttribManaMapper.selectOne(attribMana);
		if(null != dbResult){
			logger.info("attrib code is exists record:{}",record);
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_ATTRIB_CODE_EXISTS);
		}
		attribMana.setMaterialName(record.getAttribName());
		attribMana.setBoardVersion(record.getBoardVersion());
		attribMana.setCardSelfId(record.getCardSelfId());
		attribMana.setConfigure(record.getConfigureId());
		attribMana.setDeletedFlag("N");
		attribMana.setDevMnumId(record.getDevMnumId());
		attribMana.setDevTypeId(record.getDevTypeId());
		attribMana.setFastenerVersion(record.getFastenerVersion());
		attribMana.setMcuVersion(record.getMcuVersion());
		attribMana.setModel(record.getModelId());
		attribMana.setMsize(record.getMsizeId());
		attribMana.setOrNetId(record.getOrNetId());
		attribMana.setOrOpenId(record.getOrOpenId());
		attribMana.setScreenId(record.getScreenId());
		attribMana.setSoftVersion(record.getSoftVersion());
		attribMana.setSourceId(record.getSourceId());
		attribMana.setType(record.getTypeId());
		attribMana.setVerifyIccid(record.getVerifyIccid());
		attribMana.setCreatedBy(record.getConsumer());
		attribMana.setUpdatedBy(record.getConsumer());
		attribMana.setCreatedDate(JxcUtils.getNowDate());
		attribMana.setUpdatedDate(JxcUtils.getNowDate());
		try{
			return jxcmtAttribManaMapper.insertSelective(attribMana);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
            throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
	public Integer updateAttribMana(JXCMTAttribManaDTO record) throws RpcServiceException {
		JXCMTAttribMana attribMana = new JXCMTAttribMana();
		attribMana.setAttribCode(record.getAttribCode());
		JXCMTAttribMana dbResult = jxcmtAttribManaMapper.selectOne(attribMana);
		if(null == dbResult){
			logger.info("attrib code is exists record:{}",record);
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_ATTRIB_CODE_NOT_EXISTS);
		}
		dbResult.setMaterialName(record.getAttribName());
		dbResult.setBoardVersion(record.getBoardVersion());
		dbResult.setCardSelfId(record.getCardSelfId());
		dbResult.setConfigure(record.getConfigureId());
		dbResult.setDeletedFlag("N");
		dbResult.setDevMnumId(record.getDevMnumId());
		dbResult.setDevTypeId(record.getDevTypeId());
		dbResult.setFastenerVersion(record.getFastenerVersion());
		dbResult.setMcuVersion(record.getMcuVersion());
		dbResult.setModel(record.getModelId());
		dbResult.setMsize(record.getMsizeId());
		dbResult.setOrNetId(record.getOrNetId());
		dbResult.setOrOpenId(record.getOrOpenId());
		dbResult.setScreenId(record.getScreenId());
		dbResult.setSoftVersion(record.getSoftVersion());
		dbResult.setSourceId(record.getSourceId());
		dbResult.setType(record.getTypeId());
		dbResult.setVerifyIccid(record.getVerifyIccid());
		dbResult.setCreatedBy(record.getConsumer());
		dbResult.setUpdatedBy(record.getConsumer());
		dbResult.setCreatedDate(JxcUtils.getNowDate());
		dbResult.setUpdatedDate(JxcUtils.getNowDate());
		try{
			return jxcmtAttribManaMapper.updateByPrimaryKeySelective(dbResult);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
            throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
		}
	}
	
	public List<JXCMTAttribManaDTO> listAttribManaDto(JXCMTAttribManaDTO record){
		List<JXCMTAttribManaDTO>  listResult = new ArrayList<>();
		Example example = new Example(JXCMTAttribMana.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("devTypeId",record.getDevTypeId());
		if(!StringUtils.isEmpty(record.getAttribCode())){
			criteria.andLike("attribCode", "%"+record.getAttribCode()+"%");
		}
		List<JXCMTAttribMana> listAttribMana = jxcmtAttribManaMapper.selectByExample(example);
		if(null == listAttribMana || listAttribMana.isEmpty()){
			return listResult;
		}
		JXCMTAttribManaDTO dto = null;
		Map<Integer,String> mapCacheAttribInfo = new HashMap<>();
		Map<Integer,String> mapCacheDeviceType = new HashMap<>();
		for(JXCMTAttribMana mana:listAttribMana){
			listResult.add(convertAttribMana(mana,mapCacheAttribInfo,mapCacheDeviceType));
		}
		return listResult;
	}
	
	public Page<JXCMTAttribManaDTO> pageAttribMana(JXCMTAttribManaDTO record){
		Page<JXCMTAttribManaDTO>  listResult = new Page<>();
		JXCMTAttribMana condition = new JXCMTAttribMana();
		condition.setDevTypeId(record.getDevTypeId());
		condition.setMaterialName(record.getAttribName());
		condition.setAttribCode(record.getAttribCode());
		PageHelper.startPage(record.getPageNum(), record.getPageSize());
		Page<JXCMTAttribMana> listAttribMana = jxcmtAttribManaMapper.pageAttribMana(condition);
		if(null == listAttribMana || listAttribMana.isEmpty()){
			return listResult;
		}
		List<String> listMaterialCode = new ArrayList<>();
		for(JXCMTAttribMana mana:listAttribMana){
			listMaterialCode.add(mana.getAttribCode());
		}
		Map<String,JXCMTMaterialInfo> mapMaterialInfo = jxcmtMaterialInfoService.mapMaterialByListMaterialCode(listMaterialCode);
		JXCMTAttribMana updateAttribMana = null;
		JXCMTMaterialInfo materialInfo = null;
		JXCMTAttribManaDTO dto = null;
		Map<Integer,String> mapCacheAttribInfo = new HashMap<>();
		Map<Integer,String> mapCacheDeviceType = new HashMap<>();
		for(JXCMTAttribMana mana:listAttribMana){
			materialInfo = mapMaterialInfo.get(mana.getAttribCode());
			if(null != materialInfo && !materialInfo.getMaterialName().equals(mana.getMaterialName())){
				updateAttribMana = new JXCMTAttribMana();
				updateAttribMana.setId(mana.getId());
				updateAttribMana.setMaterialName(materialInfo.getMaterialName());
				this.updateAttribManaSelective(updateAttribMana);
				mana.setMaterialName(materialInfo.getMaterialName());
			}
			listResult.add(convertAttribMana(mana,mapCacheAttribInfo,mapCacheDeviceType));
		}
		listResult.setTotal(listAttribMana.getTotal());
		listResult.setPageNum(listAttribMana.getPageNum());
		listResult.setPages(listAttribMana.getPages());
		listResult.setPageSize(listAttribMana.getPageSize());
		listResult.setPageSizeZero(listAttribMana.getPageSizeZero());
		return listResult;
	}
	
	public Integer updateAttribManaSelective(JXCMTAttribMana mana){
		if(mana.getId() == null){
			return 0;
		}
		return jxcmtAttribManaMapper.updateByPrimaryKeySelective(mana);
	}
	
	public JXCMTAttribManaDTO getAttribManaDto(String attribCode){
		JXCMTAttribMana condition = new JXCMTAttribMana();
		condition.setAttribCode(attribCode);
		condition.setDeletedFlag("N");
		return convertAttribMana(jxcmtAttribManaMapper.selectOne(condition),null,null);
	}
	
	public JXCMTAttribManaDTO convertAttribMana(JXCMTAttribMana mana,
			Map<Integer,String> mapCacheAttribInfo,
			Map<Integer,String> mapCacheDeviceType){
		JXCMTAttribManaDTO dto = new JXCMTAttribManaDTO();
		dto.setAttribName(mana.getMaterialName());
		dto.setAttribCode(mana.getAttribCode());
		dto.setBoardVersion(mana.getBoardVersion());
		dto.setCardSelfName(jxcmtAttribInfoService.getAttribInfoNameById(mana.getCardSelfId(), mapCacheAttribInfo));
		dto.setConfigureName(jxcmtAttribInfoService.getAttribInfoNameById(mana.getConfigure(), mapCacheAttribInfo));
		dto.setDevMnumName(jxcmtAttribInfoService.getAttribInfoNameById(mana.getDevMnumId(), mapCacheAttribInfo));
		dto.setDevTypeId(mana.getDevTypeId());
		dto.setDevTypeName(jxcmtDeviceTypeService.getDeviceTypeNameById(mana.getDevTypeId(), mapCacheDeviceType));
		dto.setFastenerVersion(mana.getFastenerVersion());
		dto.setMcuVersion(mana.getMcuVersion());
		dto.setModelName(jxcmtAttribInfoService.getAttribInfoNameById(mana.getModel(), mapCacheAttribInfo));
		dto.setMsizeName(jxcmtAttribInfoService.getAttribInfoNameById(mana.getMsize(), mapCacheAttribInfo));
		dto.setOrNetName(jxcmtAttribInfoService.getAttribInfoNameById(mana.getOrNetId(), mapCacheAttribInfo));
		dto.setOrOpenName(jxcmtAttribInfoService.getAttribInfoNameById(mana.getOrNetId(), mapCacheAttribInfo));
		dto.setOrOpenName(jxcmtAttribInfoService.getAttribInfoNameById(mana.getOrOpenId(), mapCacheAttribInfo));
		dto.setScreenName(jxcmtAttribInfoService.getAttribInfoNameById(mana.getScreenId(), mapCacheAttribInfo));
		dto.setSoftVersion(mana.getSoftVersion());
		dto.setSourceName(jxcmtAttribInfoService.getAttribInfoNameById(mana.getSourceId(), mapCacheAttribInfo));
		dto.setTypeName(jxcmtAttribInfoService.getAttribInfoNameById(mana.getType(), mapCacheAttribInfo));
		dto.setVerifyIccid(mana.getVerifyIccid());
		return dto;
	}
	
	public JXCMTDeviceType getDeviceTypeByAttribCode(String attribCode,Map<String,JXCMTDeviceType> mapCache){
		JXCMTDeviceType result = null;
		if(StringUtils.isEmpty(attribCode)){
			return result;
		}
		if(mapCache != null){
			result = mapCache.get(attribCode);
		}
		if(null != result){
			return result;
		}
		JXCMTAttribMana attribMana = getAttribManaByAttribCode(attribCode);
		if(null == attribMana){
			return result;
		}
		if(attribMana.getDevTypeId() == null){
			return result;
		}
		JXCMTDeviceType deviceType = jxcmtDeviceTypeService.getDeviceTypeById(attribMana.getDevTypeId());
		if(null == deviceType){
			return result;
		}
		if(mapCache != null){
			mapCache.put(attribCode, deviceType);
		}
		return deviceType;
	}
	
	public JXCMTAttribMana getAttribManaByAttribCode(String attribCode){
		if(StringUtils.isEmpty(attribCode)){
			return null;
		}
		JXCMTAttribMana condition = new JXCMTAttribMana();
		condition.setAttribCode(attribCode);
		condition.setDeletedFlag("N");
		return jxcmtAttribManaMapper.selectOne(condition);
	}

	/**
	 * @author: luoqiang
	 * @description: 判断属性配置物料是否存在
	 * @date: 2020/9/10 15:44
	 * @param attribCode
	 * @return: cn.com.glsx.supplychain.jxc.model.JXCMTWarehouseInfo
	 *//*
	public JXCMTAttribMana getAttribManaByAttribCode(String attribCode) throws RpcServiceException {
		logger.info("获取属性配置物料编码：" + attribCode);
		try
		{
			Example example=new Example(JXCMTAttribMana.class);
			example.createCriteria().andEqualTo("attribCode",attribCode)
					.andEqualTo("deletedFlag","N");
			List<JXCMTAttribMana>  warehouseInfoList=jxcmtAttribManaMapper.selectByExample(example);
			return warehouseInfoList.get(0);
		}catch (Exception e) {
			logger.error("根据物料编码查询仓库信息异常," + e.getMessage(),e);
			throw new RpcServiceException(DefaultErrorEnum.DATABASE_ERROR,"根据物料编码查询仓库信息异常");
		}
	}*/
			
}
