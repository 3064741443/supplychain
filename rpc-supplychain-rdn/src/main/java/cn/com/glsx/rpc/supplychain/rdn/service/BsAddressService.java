package cn.com.glsx.rpc.supplychain.rdn.service;

import cn.com.glsx.rpc.supplychain.rdn.converter.BsAddressRpcConvert;
import cn.com.glsx.rpc.supplychain.rdn.mapper.BsAddressMapper;
import cn.com.glsx.rpc.supplychain.rdn.model.pojo.Address;
import cn.com.glsx.rpc.supplychain.rdn.model.tmp.BsAddressVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BsAddressService {
	
	private static final Logger logger = LoggerFactory.getLogger(BsAddressService.class);
	
	@Autowired
	private BsAddressMapper bsAddressMapper;
	
	//根据地址id获取订单收货地址
	public List<BsAddressVo> listAddressByIds(List<Long> addressIds){
		if(null == addressIds || addressIds.isEmpty()){
			return null;
		}
		Example example = new Example(Address.class);
		example.createCriteria().andIn("id", addressIds);
		return BsAddressRpcConvert.convertListBean(bsAddressMapper.selectByExample(example));
	}
	
	//获取商户地址列表
	public List<BsAddressVo> listAddress(BsAddressVo record) {	
		
		Example example = new Example(Address.class);
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
		return BsAddressRpcConvert.convertListBean(bsAddressMapper.selectByExample(example));	
	}
	
	//根据id获取地址详情
	public BsAddressVo getAddress(BsAddressVo record){
		
		Address condition = new Address();
		condition.setId(record.getId().longValue());
		condition.setDeletedFlag("N");
		return BsAddressRpcConvert.convertBean(bsAddressMapper.selectOne(condition));
	}
	
	public Address getAddressByName(BsAddressVo record){
		Address condition = new Address();
		condition.setMerchantCode(record.getMerchantCode());
		condition.setName(record.getName());
		condition.setMobile(record.getMobile());
		condition.setAddress(record.getAddress());
		condition.setDeletedFlag("N");
		return bsAddressMapper.selectOne(condition);
	}
	
	public Address getAddressById(Integer id){
		Address condition = new Address();
		condition.setId(id.longValue());
		return bsAddressMapper.selectOne(condition);
	}

	public List<Address> listAddress(List<Long> listIds){
		if(null == listIds || listIds.isEmpty()){
			return null;
		}
		Example example=new Example(Address.class);
		example.createCriteria().andIn("id",listIds)
				.andEqualTo("deletedFlag","N");
		return bsAddressMapper.selectByExample(example);
	}

	public Map<Long,Address> listMapAddressByIds(List<Long> listIds){
		Map<Long,Address> mapResult = null;
		List<Address> listBsAddress = this.listAddress(listIds);
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
		mapResult = listBsAddress.stream().collect(Collectors.toMap(Address::getId, a->a));
		if(null == mapResult){
			mapResult = new HashMap<>();
		}
		return mapResult;
	}
	
}
