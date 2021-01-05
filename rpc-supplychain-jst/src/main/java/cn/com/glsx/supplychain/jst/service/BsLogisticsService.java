package cn.com.glsx.supplychain.jst.service;

import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.jst.dto.BsLogisticsDTO;
import cn.com.glsx.supplychain.jst.enums.JstErrorCodeEnum;
import cn.com.glsx.supplychain.jst.enums.LogisticsEnum;
import cn.com.glsx.supplychain.jst.mapper.BsLogisticsMapper;
import cn.com.glsx.supplychain.jst.model.BsLogistics;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BsLogisticsService {
	
	private static final Logger logger = LoggerFactory.getLogger(BsLogisticsService.class);
	
	@Autowired
	private BsLogisticsMapper logisticsMapper;
	
	public Integer batchAddLogistics(List<BsLogistics> listLogistics) throws RpcServiceException
	{
		Integer result = 0;
		logger.info("BsLogisticsService::batchAddLogistics start listLogistics:{}",listLogistics);
		try
		{
			logisticsMapper.insertList(listLogistics);
		}
		catch(Exception e)
		{
			logger.error("BsLogisticsService::batchAddLogistics 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("BsLogisticsService::batchAddLogistics end result:{}",result);
		return result;
	}
	
	public Integer batchAddLogisticsNoCatch(List<BsLogistics> listLogistics) throws RpcServiceException
	{
		return logisticsMapper.insertList(listLogistics);
	}
	
	public BsLogistics getLogisticsByShopOrderCode(String shopOrderCode,Byte logisticsType)
	{
		BsLogistics condition = new BsLogistics();
		condition.setServiceCode(shopOrderCode);
		condition.setType(logisticsType);
		return logisticsMapper.selectOne(condition);
	}

	public List<BsLogistics> getLogisticsListByShopOrderCode(String shopOrderCode)
	{
		BsLogistics condition = new BsLogistics();
		condition.setServiceCode(shopOrderCode);
		condition.setType(LogisticsEnum.LOGISTICS_TYPE_7.getCode());
		return logisticsMapper.select(condition);
	}
	
	public BsLogistics getLogisticsByOrderNumber(BsLogistics record)
	{
		BsLogistics logistics = logisticsMapper.selectOne(record);
		if(!StringUtils.isEmpty(logistics))
		{
			return logistics;
		}
		logisticsMapper.insertUseGeneratedKeys(record);
		return record;
	}
	
	public Integer insertLogisticsList(List<BsLogistics> list){
		return logisticsMapper.insertList(list);
	}

	public List<BsLogistics> listLogisticsByServiceCode(List<String> merchantOrderIds){
		if(StringUtils.isEmpty(merchantOrderIds)){
			return null;
		}
		Example example=new Example(BsLogistics.class);
		example.createCriteria().andIn("serviceCode",merchantOrderIds)
				.andEqualTo("type",1)
				.andEqualTo("deletedFlag","N");
		return logisticsMapper.selectByExample(example);
	}

	public Map<String,BsLogistics> listMapLogisticsByServiceCode(List<String> merchantOrderIds){
		Map<String,BsLogistics> mapResult = null;
		List<BsLogistics> bsLogisticsList = this.listLogisticsByServiceCode(merchantOrderIds);
		if(StringUtils.isEmpty(bsLogisticsList)){
			return null;
		}
		mapResult = bsLogisticsList.stream().collect(Collectors.toMap(BsLogistics::getServiceCode, a->a));
		if(null == mapResult){
			mapResult = new HashMap<>();
		}
		return mapResult;
	}

	public List<BsLogisticsDTO> listLogisticsByGhMerchantOrderCode(String ghMerchantOrderCode){
		List<BsLogisticsDTO> bsLogisticsList =logisticsMapper.listLogisticsByGhMerchantOrderCode(ghMerchantOrderCode);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for(BsLogisticsDTO bsLogisticsDTO:bsLogisticsList){
			Date date = null;
			try {
				if(!StringUtils.isEmpty(bsLogisticsDTO.getSendTime())) {
					date = sdf.parse(bsLogisticsDTO.getSendTime());
					String dateString=sdf.format(date);
					bsLogisticsDTO.setSendTime(dateString);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		logger.info("查询采购订单物流信息{} ", JSON.toJSON(bsLogisticsList).toString());
		if(StringUtils.isEmpty(bsLogisticsList)){
			return new ArrayList<>();
		}
      return  bsLogisticsList;
	}
}
