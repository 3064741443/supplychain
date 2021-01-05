package cn.com.glsx.supplychain.jxc.service;

import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.jxc.converter.JXCMTBsAddressRpcConvert;
import cn.com.glsx.supplychain.jxc.dto.JXCMTBsAddressDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTBsAddressDelDTO;
import cn.com.glsx.supplychain.jxc.enums.JXCErrorCodeEnum;
import cn.com.glsx.supplychain.jxc.mapper.JXCMTBsAddressMapper;
import cn.com.glsx.supplychain.jxc.model.JXCMTBsAddress;
import cn.com.glsx.supplychain.jxc.util.JxcUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JXCMTBsAddressService {
	
	private static final Logger logger = LoggerFactory.getLogger(JXCMTBsAddressService.class);
	
	@Autowired
	private JXCMTBsAddressMapper JxcmtBsAddressMapper;
	
	//根据地址id获取订单收货地址
	public List<JXCMTBsAddressDTO> listAddressByIds(List<Long> addressIds){
		if(null == addressIds || addressIds.isEmpty()){
			return null;
		}
		Example example = new Example(JXCMTBsAddress.class);
		example.createCriteria().andIn("id", addressIds);
		return JXCMTBsAddressRpcConvert.convertListBean(JxcmtBsAddressMapper.selectByExample(example));
	}

	//获取商户地址列表
	public List<JXCMTBsAddressDTO> listAddress(JXCMTBsAddressDTO record) {	
		
		JXCMTBsAddress condition = new JXCMTBsAddress();
		condition.setProvinceId(record.getProvinceId());
		condition.setCityId(record.getCityId());
		condition.setAreaId(record.getAreaId());
		condition.setMerchantCode(record.getMerchantCode());
		condition.setDeletedFlag("N");
		return JXCMTBsAddressRpcConvert.convertListBean(JxcmtBsAddressMapper.listMerchantOrderAddress(condition));
		
		
		/*Example example = new Example(JXCMTBsAddress.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("merchantCode", record.getMerchantCode());
		if(null != record.getProvinceId()){
			criteria.andEqualTo("provinceId", record.getProvinceId());
		}
		if(null != record.getCityId()){
			criteria.andEqualTo("cityId", record.getCityId());
		}
		if(null != record.getAreaId()){
			criteria.andEqualTo("areaId",record.getAreaId());
		}
		criteria.andEqualTo("deletedFlag", "N");
		example.orderBy("updatedDate").desc();
		return JXCMTBsAddressRpcConvert.convertListBean(JxcmtBsAddressMapper.selectByExample(example));	*/
	}


	//获取服务商地址列表
	public List<JXCMTBsAddressDTO> 	listServiceProviderAddress(JXCMTBsAddressDTO record) {

		JXCMTBsAddress condition = new JXCMTBsAddress();
		condition.setProvinceId(record.getProvinceId());
		condition.setCityId(record.getCityId());
		condition.setAreaId(record.getAreaId());
		condition.setMerchantCode(record.getMerchantCode());
		condition.setDeletedFlag("N");
		return JXCMTBsAddressRpcConvert.convertListBean(JxcmtBsAddressMapper.listServiceProviderAddress(condition));
	}
	
	//根据id获取地址详情
	public JXCMTBsAddressDTO getAddress(JXCMTBsAddressDTO record){
		
		JXCMTBsAddress condition = new JXCMTBsAddress();
		condition.setId(record.getId().longValue());
		condition.setDeletedFlag("N");
		return JXCMTBsAddressRpcConvert.convertBean(JxcmtBsAddressMapper.selectOne(condition));
	}
	

	//根据id修改地址信息
	public Integer updateAddress(JXCMTBsAddressDTO record) throws RpcServiceException{
		
		JXCMTBsAddress condition = new JXCMTBsAddress();
		condition.setMerchantCode(record.getMerchantCode());
		condition.setName(record.getName());
		condition.setMobile(record.getMobile());
		condition.setAddress(record.getAddress());
		condition.setDeletedFlag("N");
		JXCMTBsAddress dbObject = JxcmtBsAddressMapper.selectOne(condition);
		if(null != dbObject && dbObject.getId().longValue() != record.getId().longValue()){
			logger.error("JXCMTBsAddressService::updateAddress 数据库已经存在相同信息的地址");
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_REPEAT_ADDRESS.getDescrible());
		}	
		try{
			JXCMTBsAddress bsAddress = JXCMTBsAddressRpcConvert.converDto(record);
			bsAddress.setUpdatedBy(record.getConsumer());
			bsAddress.setUpdatedDate(JxcUtils.getNowDate());
			return JxcmtBsAddressMapper.updateByPrimaryKeySelective(bsAddress);	
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_REPEAT_ADDRESS.getDescrible());
		}
	}
	
	//根据id修改地址
	public Integer updateAddressById(JXCMTBsAddressDTO record) throws RpcServiceException{
		JXCMTBsAddress bsAddress = JXCMTBsAddressRpcConvert.converDto(record);
		if(record.getId() == null){
			return 0;
		}
		bsAddress.setUpdatedBy(record.getConsumer());
		bsAddress.setUpdatedDate(JxcUtils.getNowDate());
		return JxcmtBsAddressMapper.updateByPrimaryKeySelective(bsAddress);
	}
	
	//删除商户地址信息
	public Integer delAddress(JXCMTBsAddressDTO record){
		JXCMTBsAddress condition = new JXCMTBsAddress();
		condition.setId(record.getId().longValue());
		condition.setUpdatedBy(record.getConsumer());
		condition.setUpdatedDate(JxcUtils.getNowDate());
		condition.setDeletedFlag("Y");
		return JxcmtBsAddressMapper.updateByPrimaryKeySelective(condition);
	}
	
	//批量删除商户地址信息
	public Integer batchDelAddress(JXCMTBsAddressDelDTO record){
		Example example = new Example(JXCMTBsAddress.class);
		example.createCriteria().andIn("id", record.getListIds());
		JXCMTBsAddress condition = new JXCMTBsAddress();
		condition.setDeletedFlag("Y");
		condition.setUpdatedBy(record.getConsumer());
		condition.setUpdatedDate(JxcUtils.getNowDate());
		return JxcmtBsAddressMapper.updateByExampleSelective(condition, example);
	}
	
	//添加商户地址信息
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
	public Integer addAddress(JXCMTBsAddressDTO record) throws RpcServiceException{	
		try{
			Example example = new Example(JXCMTBsAddress.class);
			example.createCriteria().andEqualTo("name",record.getName())
									.andEqualTo("mobile", record.getMobile())
									.andEqualTo("address",record.getAddress())
									.andEqualTo("merchantCode",record.getMerchantCode())
									.andEqualTo("deletedFlag", "N");
			List<JXCMTBsAddress> listBsAddress = JxcmtBsAddressMapper.selectByExample(example);
			JXCMTBsAddress dbObject = null;
			if(null != listBsAddress && !listBsAddress.isEmpty()){
				dbObject = listBsAddress.get(0);
			}
			
//			JXCMTBsAddress condition = new JXCMTBsAddress();
//			condition.setMerchantCode(record.getMerchantCode());
//			condition.setName(record.getName());
//			condition.setMobile(record.getMobile());
//			condition.setAddress(record.getAddress());
//			condition.setDeletedFlag("N");
//			JXCMTBsAddress dbObject = JxcmtBsAddressMapper.selectOne(condition);
			JXCMTBsAddress bsAddress = JXCMTBsAddressRpcConvert.converDto(record);
			if(null != dbObject){
				bsAddress.setId(dbObject.getId());
				bsAddress.setUpdatedBy(record.getConsumer());
				bsAddress.setUpdatedDate(JxcUtils.getNowDate());
				JxcmtBsAddressMapper.updateByPrimaryKeySelective(bsAddress);
			}else{
				bsAddress.setCreatedBy(record.getConsumer());
				bsAddress.setCreatedDate(JxcUtils.getNowDate());
				bsAddress.setUpdatedBy(record.getConsumer());
				bsAddress.setUpdatedDate(JxcUtils.getNowDate());
				bsAddress.setDeletedFlag("N");
				JxcmtBsAddressMapper.insertSelective(bsAddress);
			}
		}catch(RpcServiceException e){
			logger.error(e.getMessage(),e);
		}
		return 0;
	}
	
	public JXCMTBsAddress getAddressByName(JXCMTBsAddressDTO record){
		
		Example example = new Example(JXCMTBsAddress.class);
		example.createCriteria().andEqualTo("name",record.getName())
								.andEqualTo("mobile", record.getMobile())
								.andEqualTo("address",record.getAddress())
								.andEqualTo("merchantCode",record.getMerchantCode())
								.andEqualTo("deletedFlag", "N");
		List<JXCMTBsAddress> listBsAddress = JxcmtBsAddressMapper.selectByExample(example);
		JXCMTBsAddress dbObject = null;
		if(null != listBsAddress && !listBsAddress.isEmpty()){
			dbObject = listBsAddress.get(0);
		}
		return dbObject;
//		JXCMTBsAddress condition = new JXCMTBsAddress();
//		condition.setMerchantCode(record.getMerchantCode());
//		condition.setName(record.getName());
//		condition.setMobile(record.getMobile());
//		condition.setAddress(record.getAddress());
//		condition.setDeletedFlag("N");
//		return JxcmtBsAddressMapper.selectOne(condition);
	}
	
	public JXCMTBsAddress getAddressById(Integer id){
		JXCMTBsAddress condition = new JXCMTBsAddress();
		condition.setId(id.longValue());
		return JxcmtBsAddressMapper.selectOne(condition);
	}
	
	public JXCMTBsAddress addIfNotExist(JXCMTBsAddress record){
		Example example = new Example(JXCMTBsAddress.class);
		example.createCriteria().andEqualTo("name",record.getName())
								.andEqualTo("mobile", record.getMobile())
								.andEqualTo("address",record.getAddress())
								.andEqualTo("merchantCode",record.getMerchantCode())
								.andEqualTo("deletedFlag", "N");
		List<JXCMTBsAddress> listBsAddress = JxcmtBsAddressMapper.selectByExample(example);
		JXCMTBsAddress bsAddress = null;
		if(null != listBsAddress && !listBsAddress.isEmpty()){
			bsAddress = listBsAddress.get(0);
		}
//		JXCMTBsAddress condition = new JXCMTBsAddress();
//		condition.setName(record.getName());
//		condition.setMobile(record.getMobile());
//		condition.setAddress(record.getAddress());
//		condition.setMerchantCode(record.getMerchantCode());
//		JXCMTBsAddress bsAddress = JxcmtBsAddressMapper.selectOne(record);
		if(null != bsAddress){
			return bsAddress;
		}
		JxcmtBsAddressMapper.insertAddress(record);
		return record;
	}

	public List<JXCMTBsAddress> listAddress(List<Long> listIds){
		if(null == listIds || listIds.isEmpty()){
			return null;
		}
		Example example=new Example(JXCMTBsAddress.class);
		example.createCriteria().andIn("id",listIds)
				.andEqualTo("deletedFlag","N");
		return JxcmtBsAddressMapper.selectByExample(example);
	}

	public Map<Long,JXCMTBsAddress> listMapAddressByIds(List<Long> listIds){
		Map<Long,JXCMTBsAddress> mapResult = null;
		List<JXCMTBsAddress> listBsAddress = this.listAddress(listIds);
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
		mapResult = listBsAddress.stream().collect(Collectors.toMap(JXCMTBsAddress::getId, a->a));
		if(null == mapResult){
			mapResult = new HashMap<>();
		}
		return mapResult;
	}
	
}
