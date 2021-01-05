package cn.com.glsx.supplychain.jxc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import tk.mybatis.mapper.entity.Example;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.supplychain.jxc.dto.JXCAmKcWarehouseRelationDTO;
import cn.com.glsx.supplychain.jxc.mapper.JXCAmKcCustomerRelationMapper;
import cn.com.glsx.supplychain.jxc.mapper.JXCAmKcWarehouseRelationMapper;
import cn.com.glsx.supplychain.jxc.model.JXCAmKcWarehouseRelation;

@Service
public class JXCAMKcRelationService {
	private static final Logger logger = LoggerFactory.getLogger(JXCAMKcRelationService.class);
	@Autowired
	private JXCAmKcCustomerRelationMapper customerRelationMapper;
	@Autowired
	private JXCAmKcWarehouseRelationMapper warehouseRelationMapper;
	
	public Page<JXCAmKcWarehouseRelationDTO> pageKcWarehouseRelation(RpcPagination<JXCAmKcWarehouseRelationDTO> pagination){
		JXCAmKcWarehouseRelation condition = new JXCAmKcWarehouseRelation();
		condition.setMerchantCode(pagination.getCondition().getMerchantCode());
		condition.setMerchantName(pagination.getCondition().getMerchantName());
		condition.setWarehouseCode(pagination.getCondition().getWarehouseCode());
		condition.setWarehouseName(pagination.getCondition().getWarehouseName());
		PageHelper.startPage(pagination.getPageNum(), pagination.getPageSize());
		return warehouseRelationMapper.pageKcWarehouseRelation(condition);
	}
	
	public Map<String,JXCAmKcWarehouseRelation> mapKcWarehouseRelationByWarehouseCodes(List<String> listWarehouseCodes){
		Map<String,JXCAmKcWarehouseRelation> mapResult = new HashMap<>();
		if(null == listWarehouseCodes || listWarehouseCodes.isEmpty()){
			return mapResult;
		}
		Example example = new Example(JXCAmKcWarehouseRelation.class);
		example.createCriteria().andIn("warehouseCode", listWarehouseCodes)
								.andEqualTo("deletedFlag", "N");
		List<JXCAmKcWarehouseRelation> listRelation = warehouseRelationMapper.selectByExample(example);
		if(null == listRelation || listRelation.isEmpty()){
			return mapResult;
		}
		for(JXCAmKcWarehouseRelation relation:listRelation){
			mapResult.put(relation.getWarehouseCode(), relation);
		}
		return mapResult;
	}
}
