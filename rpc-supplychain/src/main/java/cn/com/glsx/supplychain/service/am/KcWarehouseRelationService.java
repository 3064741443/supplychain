package cn.com.glsx.supplychain.service.am;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.com.glsx.supplychain.mapper.am.KcCustomerRelationMapper;
import cn.com.glsx.supplychain.mapper.am.KcWarehouseRelationMapper;
import cn.com.glsx.supplychain.model.am.KcCustomerRelation;
import cn.com.glsx.supplychain.model.am.KcWarehouseRelation;

@Service
public class KcWarehouseRelationService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private KcWarehouseRelationMapper WarehouseRelationMapper;
	
	@Autowired
	private KcCustomerRelationMapper customerRelationMapper;
	
	public KcWarehouseRelation getWarehouseRelationByCode(String warehouseCode){
		KcWarehouseRelation condition = new KcWarehouseRelation();
		condition.setWarehouseCode(warehouseCode);
		condition.setDeletedFlag("N");
		return WarehouseRelationMapper.selectOne(condition);
	}
	
	public KcCustomerRelation getCustomerRelationByCode(String customCode){
		KcCustomerRelation condition = new KcCustomerRelation();
		condition.setCustomerCode(customCode);
		condition.setDeletedFlag("N");
		return customerRelationMapper.selectOne(condition);
	}
	
	public Page<KcCustomerRelation> pageCustomerRelations(int pageNum, int pageSize,KcCustomerRelation record){
		PageHelper.startPage(pageNum,pageSize);
		return customerRelationMapper.pageKcCustomerRelation(record);
	}
	
	public KcWarehouseRelation getWarehouseRelationByMerchantCode(String merchantCode){
		Example example = new Example(KcWarehouseRelation.class);
		example.createCriteria().andEqualTo("merchantCode",merchantCode)
								.andEqualTo("deletedFlag","N");
		List<KcWarehouseRelation> listRelation = WarehouseRelationMapper.selectByExample(example);
		if(null == listRelation || listRelation.isEmpty()){
			return null;
		}
		return listRelation.get(0);
	}
	
	public KcCustomerRelation getCustomerRelationByMerchantCode(String merchantCode){
		Example example = new Example(KcCustomerRelation.class);
		example.createCriteria().andEqualTo("merchantCode",merchantCode)
								.andEqualTo("deletedFlag","N");
		List<KcCustomerRelation> listRelation = customerRelationMapper.selectByExample(example);
		if(null == listRelation || listRelation.isEmpty()){
			return null;
		}
		return listRelation.get(0);
	}
}
