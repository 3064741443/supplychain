package cn.com.glsx.supplychain.jst.service;

import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.jst.convert.BsAddressRpcConvert;
import cn.com.glsx.supplychain.jst.dto.BsAddressDTO;
import cn.com.glsx.supplychain.jst.enums.JstErrorCodeEnum;
import cn.com.glsx.supplychain.jst.mapper.BsAddressMapper;
import cn.com.glsx.supplychain.jst.model.BsAddress;
import cn.com.glsx.supplychain.jst.util.JstUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BsAddressService {
	private static final Logger logger = LoggerFactory.getLogger(BsAddressService.class);
	
	@Autowired
	private BsAddressMapper bsAddressMapper;
	
	public List<BsAddressDTO> listBsAddressByMerchantCode(String merchantCode) throws RpcServiceException
	{
		List<BsAddressDTO> result;
		logger.info("BsAddressService::listBsAddress start merchantCode:{}",merchantCode);
		try
		{
			List<BsAddress> listBsAddress = null;
			/*Example example = new Example(BsAddress.class);
			example.createCriteria().andEqualTo("merchantCode",merchantCode)
									.andEqualTo("deletedFlag","N");
			example.orderBy("updatedDate").desc();
			listBsAddress = bsAddressMapper.selectByExample(example);*/
			BsAddress condition = new BsAddress();
			condition.setMerchantCode(merchantCode);
			condition.setDeletedFlag("N");
			listBsAddress = bsAddressMapper.listMerchantOrderAddress(condition);
			result = BsAddressRpcConvert.convertList(listBsAddress);	
		}
		catch(Exception e)
		{
			logger.error("BsAddressService::listBsAddress 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("BsAddressService::listBsAddress end result:{}",result);
		return result;	
	}
	
	public Integer removeBsAddress(BsAddressDTO record) throws RpcServiceException
	{
		Integer result = 0;
		logger.info("BsAddressService::removeBsAddress start record:{}",record);
		try
		{
			BsAddress condition = new BsAddress();
			condition.setId(record.getId());
			BsAddress bsAddress = bsAddressMapper.selectOne(condition);
			if(!StringUtils.isEmpty(bsAddress))
			{
				bsAddress.setDeletedFlag("Y");
				bsAddress.setUpdatedBy(bsAddress.getMerchantCode());
				bsAddress.setUpdatedDate(JstUtils.getNowDate());
				bsAddressMapper.updateByPrimaryKeySelective(bsAddress);	
			}
		}
		catch(Exception e)
		{
			logger.error("BsAddressService::removeBsAddress 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("BsAddressService::removeBsAddress end result:{}",result);
		return result;	
	}
	
	public BsAddressDTO addBsAddress(BsAddressDTO record) throws RpcServiceException
	{
		BsAddressDTO result;
		logger.info("BsAddressService::addBsAddress start record:{}",record);
		try
		{	
			BsAddress bsAddress = null;
			if(!StringUtils.isEmpty(record.getId()))
			{
				BsAddress model = BsAddressRpcConvert.convertDTO(record);
				model.setUpdatedDate(JstUtils.getNowDate());
				model.setDeletedFlag("N");
				bsAddressMapper.updateByPrimaryKeySelective(model);
				bsAddress = bsAddressMapper.selectOne(model);	
			}
			else
			{
				BsAddress condition = new BsAddress();
				condition.setAddress(record.getAddress());
				condition.setName(record.getName());
				condition.setMobile(record.getMobile());
				condition.setMerchantCode(record.getMerchantCode());
				bsAddress = bsAddressMapper.selectOne(condition);
				if(StringUtils.isEmpty(bsAddress))
				{
					BsAddress model = BsAddressRpcConvert.convertDTO(record);
					model.setCreatedDate(JstUtils.getNowDate());
					model.setUpdatedDate(JstUtils.getNowDate());
					model.setDeletedFlag("N");
					bsAddressMapper.insertSelective(model);
					bsAddress = bsAddressMapper.selectOne(condition);
				}
			}	
			result =  BsAddressRpcConvert.convertBean(bsAddress);
		}
		catch(Exception e)
		{
			logger.error("BsAddressService::addBsAddress 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("BsAddressService::addBsAddress end result:{}",result);
		return result;	
	}
	
	public BsAddressDTO getBsAddressById(BsAddressDTO record,String deleteFlag){
		logger.info("BsAddressService::getBsAddressById start record:{}",record);
		return BsAddressRpcConvert.convertBean(getAddressById(record.getId(),deleteFlag));
	}
	
	public BsAddressDTO updateBsAddressById(BsAddressDTO record){
		BsAddressDTO result;
		logger.info("BsAddressService::updateBsAddressById start record:{}",record);
		BsAddress model = BsAddressRpcConvert.convertDTO(record);
		model.setUpdatedDate(JstUtils.getNowDate());
		model.setDeletedFlag("N");
		bsAddressMapper.updateByPrimaryKeySelective(model);
		BsAddress bsAddress = bsAddressMapper.selectOne(model);	
		result =  BsAddressRpcConvert.convertBean(bsAddress);
		return result;
	}
	
	public BsAddress getAddressById(Long id, String deleteFlag)
	{
		BsAddress condition = new BsAddress();
		condition.setId(id);
		if(null != deleteFlag){
			condition.setDeletedFlag(deleteFlag);
		}
		return bsAddressMapper.selectOne(condition);
	}
	
	public BsAddress getAddressIfExist(BsAddressDTO record){
		
		BsAddress condition = new BsAddress();
		condition.setName(record.getName());
		condition.setMobile(record.getMobile());
		condition.setAddress(record.getAddress());
		condition.setMerchantCode(record.getMerchantCode());
		condition.setDeletedFlag("N");
		BsAddress result = bsAddressMapper.selectOne(condition);
		
		condition.setProvinceId(record.getProvinceId());
		condition.setProvinceName(record.getProvinceName());
		condition.setCityId(record.getCityId());
		condition.setCityName(record.getCityName());
		condition.setAreaId(record.getAreaId());
		condition.setAreaName(record.getAreaName());
		if(null != result){
			condition.setId(result.getId());
			condition.setUpdatedBy(record.getConsumer());
			condition.setUpdatedDate(JstUtils.getNowDate());
			bsAddressMapper.updateByPrimaryKeySelective(condition);
			
//			String recordString = "pi:" + ((record.getProvinceId()==null)?"":record.getProvinceId()) + 
//					"ci"+ ((record.getCityId()==null)?"":record.getCityId()) + 
//					"ai:"+((record.getAreaId()==null)?"":record.getAreaId()) +
//					"pn:"+((record.getProvinceName()==null)?"":record.getProvinceName()) + 
//					"cn:"+((record.getCityName()==null)?"":record.getCityName()) +
//					"an:"+((record.getAreaName()==null)?"":record.getAreaName());
//			String resultString = "pi:" + ((result.getProvinceId()==null)?"":result.getProvinceId()) + 
//					"ci"+ ((result.getCityId()==null)?"":result.getCityId()) + 
//					"ai:"+((result.getAreaId()==null)?"":result.getAreaId()) +
//					"pn:"+((result.getProvinceName()==null)?"":result.getProvinceName()) + 
//					"cn:"+((result.getCityName()==null)?"":result.getCityName()) +
//					"an:"+((result.getAreaName()==null)?"":result.getAreaName());
//			if(!recordString.equals(resultString)){
//				condition.setDeletedFlag("N");
//				condition.setId(result.getId());
//				condition.setUpdatedBy(record.getConsumer());
//				bsAddressMapper.updateByPrimaryKeySelective(condition);
//			}
			return result;
		}
		condition.setCreatedDate(JstUtils.getNowDate());
		condition.setUpdatedDate(JstUtils.getNowDate());
		condition.setCreatedBy(record.getConsumer());
		condition.setUpdatedBy(record.getConsumer());
		bsAddressMapper.insertSelective(condition);	
		return condition;
	}
	
	public List<BsAddress> listAddressByIds(List<Long> listIds){
		if(null == listIds || listIds.isEmpty()){
			return null;
		}
		Example example=new Example(BsAddress.class);
		example.createCriteria().andIn("id",listIds)
				                .andEqualTo("deletedFlag","N");
		return bsAddressMapper.selectByExample(example);
	}
	
	public Map<Long,BsAddress> listMapAddressByIds(List<Long> listIds){
		Map<Long,BsAddress> mapResult = null;
		List<BsAddress> listBsAddress = this.listAddressByIds(listIds);
		if(listBsAddress==null || listBsAddress.size()==0){
			mapResult= new HashMap<>();
			return  mapResult;
		}
		StringBuffer sb=null;
		for(int i=0;i<listBsAddress.size();i++){
			sb=new StringBuffer();
			if(!StringUtils.isEmpty(listBsAddress.get(i).getProvinceName())){
				sb.append(listBsAddress.get(i).getProvinceName());
			}
			if(!StringUtils.isEmpty(listBsAddress.get(i).getCityName())){
				sb.append(listBsAddress.get(i).getCityName());
			}
			if(!StringUtils.isEmpty(listBsAddress.get(i).getAreaName())){
				sb.append(listBsAddress.get(i).getAreaName());
			}
			if(!StringUtils.isEmpty(listBsAddress.get(i).getAddress())){
				sb.append(listBsAddress.get(i).getAddress());
			}
			listBsAddress.get(i).setAddress(sb.toString());
		}
		if(null == listBsAddress || listBsAddress.isEmpty()){
			return null;
		}
		mapResult = listBsAddress.stream().collect(Collectors.toMap(BsAddress::getId, a->a));
		if(null == mapResult){
			mapResult = new HashMap<>();
		}
		return mapResult;
	}
	
}
