package cn.com.glsx.supplychain.jxc.service;

import cn.com.glsx.supplychain.jxc.common.Constants;
import cn.com.glsx.supplychain.jxc.dto.JXCLogisticsDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTOrderInfoDetailDTO;
import cn.com.glsx.supplychain.jxc.mapper.JXCMTBsLogisticsMapper;
import cn.com.glsx.supplychain.jxc.model.JXCMTBsLogistics;
import cn.com.glsx.supplychain.jxc.util.JxcUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.oreframework.boot.autoconfigure.snowflake.algorithm.SnowflakeWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JXCMTBsLogisticsService {
	private static final Logger logger = LoggerFactory.getLogger(JXCMTBsLogisticsService.class);
	@Autowired
	private JXCMTBsLogisticsMapper jxcmtBsLogisticsMapper;
	@Autowired
	private SnowflakeWorker snowflakeWorker;
	
	public Integer insertListJxcmtBsLogistics(List<JXCMTBsLogistics> listJxcmtBsLogistics){
		if(null == listJxcmtBsLogistics || listJxcmtBsLogistics.isEmpty()){
			return 0;
		}
		return jxcmtBsLogisticsMapper.insertList(listJxcmtBsLogistics);
	}
	
	public List<JXCMTBsLogistics> listBsLogisticsByBsMerchantOrderCodes(List<String> listMerchantOrderCodes){
		if(null == listMerchantOrderCodes || listMerchantOrderCodes.isEmpty()){
			return null;
		}
		Example example = new Example(JXCMTBsLogistics.class);
		example.createCriteria().andIn("serviceCode", listMerchantOrderCodes);				
		return jxcmtBsLogisticsMapper.selectByExample(example);
	}
	
	public JXCMTBsLogistics getBsLogisticsByBsMerchantOrderCode(String merchantOrderCode){
		JXCMTBsLogistics condition = new JXCMTBsLogistics();
		condition.setServiceCode(merchantOrderCode);
	//	condition.setType((byte)1);
		return jxcmtBsLogisticsMapper.selectOne(condition);
	}
	
	public List<JXCMTBsLogistics> listBsLogisticsByDispatchOrderCodes(List<String> listDispatchOrderCodes,byte logisticsType){
		if(null == listDispatchOrderCodes || listDispatchOrderCodes.isEmpty()){
			return null;
		}
		Example example = new Example(JXCMTBsLogistics.class);
		example.createCriteria().andIn("serviceCode", listDispatchOrderCodes)
								.andEqualTo("type", logisticsType);
		return jxcmtBsLogisticsMapper.selectByExample(example);
	}


	public List<JXCMTBsLogistics> listBsLogisticsByOrderCodes(List<String> listOrderCodes){
		if(null == listOrderCodes || listOrderCodes.isEmpty()){
			return null;
		}
		Example example = new Example(JXCMTBsLogistics.class);
		example.createCriteria().andIn("serviceCode", listOrderCodes);
		return jxcmtBsLogisticsMapper.selectByExample(example);
	}
	
	public List<JXCMTBsLogistics> getBsLogisticsByDispatchOrderCode(String dispatchOrderCode,byte logisticsType){
		Example example = new Example(JXCMTBsLogistics.class);
		example.createCriteria().andEqualTo("serviceCode", dispatchOrderCode)
								.andEqualTo("type",5)
								.andEqualTo("deletedFlag", "N");
		return jxcmtBsLogisticsMapper.selectByExample(example);
	}
	
	public Integer updateBsLogisticsSeletive(JXCMTBsLogistics logistics){
		if(null == logistics){
			return 0;
		}
		return jxcmtBsLogisticsMapper.updateByPrimaryKeySelective(logistics);
	}
	
	public JXCMTBsLogistics getBsLogisticsById(Long id){
		JXCMTBsLogistics condition = new JXCMTBsLogistics();
		condition.setId(id);
		return jxcmtBsLogisticsMapper.selectOne(condition);
	}
	
	public Integer delBsLogisticsById(Long id){
		if(id == null){
			return 0;
		}
		return jxcmtBsLogisticsMapper.deleteByPrimaryKey(id);
	}
	
	public JXCMTBsLogistics addIfNotExist(JXCMTBsLogistics record){
		JXCMTBsLogistics condition = new JXCMTBsLogistics();
		condition.setServiceCode(record.getServiceCode());
		condition.setType(record.getType());
		condition.setOrderNumber(record.getOrderNumber());
		condition.setReceiveId(record.getReceiveId());
		JXCMTBsLogistics logistics = jxcmtBsLogisticsMapper.selectOne(condition);
		if(null != logistics){
			return logistics;
		}
		jxcmtBsLogisticsMapper.insertLogistics(record);
		return record;
	}
	
	public Page<JXCMTOrderInfoDetailDTO> pageOrderInfoDetail(String dispatchOrderCode,Integer pageNo,Integer pageSize){
		JXCMTBsLogistics condition = new JXCMTBsLogistics();
		condition.setServiceCode(dispatchOrderCode);
		condition.setType((byte)5);
		condition.setDeletedFlag("N");
		PageHelper.startPage(pageNo,pageSize);
		return jxcmtBsLogisticsMapper.pageOrderInfoDetail(condition);
	}


	public List<JXCMTBsLogistics> listLogisticsById(List<String> tranOrderCodes){
		if(null == tranOrderCodes || tranOrderCodes.isEmpty()){
			return null;
		}
		Example example=new Example(JXCMTBsLogistics.class);
		example.createCriteria().andIn("serviceCode",tranOrderCodes)
				.andEqualTo("type",8)
				.andEqualTo("deletedFlag","N");
		return jxcmtBsLogisticsMapper.selectByExample(example);
	}


	public Map<String,JXCMTBsLogistics> listMapLogisticsById(List<String> tranOrderCodes){
		Map<String,JXCMTBsLogistics> mapResult = null;
		List<JXCMTBsLogistics> listLogistics = listLogisticsById(tranOrderCodes);
		if(listLogistics==null || listLogistics.size()==0){
			mapResult= new HashMap<>();
			return  mapResult;
		}
		mapResult = listLogistics.stream().collect(Collectors.toMap(JXCMTBsLogistics::getServiceCode, a->a));
		if(null == mapResult){
			mapResult = new HashMap<>();
		}
		return mapResult;
	}


	/**
	 * @author: luoqiang
	 * @description: 调拨物流配送-生成发货物流信息
	 * @date: 2020/11/4 10:31
	 * @param addressId
	 * @param tranOrderCode
	 * @param consumer
	 * @return: cn.com.glsx.supplychain.jxc.model.JXCMTBsLogistics
	 */
	public JXCMTBsLogistics generatorSendBsLogistics(Long addressId, String tranOrderCode, JXCLogisticsDTO jxcLogisticsDTO,String consumer){
		JXCMTBsLogistics logistics = new JXCMTBsLogistics();
		String logiCode = Constants.LOGI_ORDER_PREFIX + JxcUtils.generatorOrderCode(snowflakeWorker);
		logistics.setReceiveId(addressId.longValue());
		logistics.setCode(logiCode);
		logistics.setType(Byte.valueOf("9"));
		logistics.setServiceCode(tranOrderCode);
		logistics.setOrderNumber(jxcLogisticsDTO.getOrderNumber());
		logistics.setCompany(jxcLogisticsDTO.getCompany());
		logistics.setShipmentsQuantity(jxcLogisticsDTO.getShipmentsQuantity());
		logistics.setCreatedBy(consumer);
		logistics.setUpdatedBy(consumer);
		logistics.setCreatedDate(JxcUtils.getNowDate());
		logistics.setUpdatedDate(JxcUtils.getNowDate());
		logistics.setSendTime(JxcUtils.getNowDateString());
		logistics.setDeletedFlag("N");
		return logistics;
	}


	/**
	 * @author: luoqiang
	 * @description: 调拨线下配送
	 * @date: 2020/11/4 10:31  Offline delivery
	 * @param addressId
	 * @param tranOrderCode
	 * @param consumer
	 * @return: cn.com.glsx.supplychain.jxc.model.JXCMTBsLogistics
	 */
	public JXCMTBsLogistics generatorOfflineDeliveryBsLogistics(Long addressId,String tranOrderCode,Integer sendCount,String consumer){
		JXCMTBsLogistics logistics = new JXCMTBsLogistics();
		String logiCode = Constants.OFFLINE_ORDER_PREFIX + JxcUtils.generatorOrderCode(snowflakeWorker);
		logistics.setReceiveId(addressId.longValue());
		logistics.setCode(logiCode);
		logistics.setType(Byte.valueOf("9"));
		logistics.setServiceCode(tranOrderCode);
		logistics.setShipmentsQuantity(sendCount);
		logistics.setCreatedBy(consumer);
		logistics.setUpdatedBy(consumer);
		logistics.setCreatedDate(JxcUtils.getNowDate());
		logistics.setUpdatedDate(JxcUtils.getNowDate());
		logistics.setSendTime(JxcUtils.getNowDateString());
		logistics.setDeletedFlag("N");
		return logistics;
	}
}
