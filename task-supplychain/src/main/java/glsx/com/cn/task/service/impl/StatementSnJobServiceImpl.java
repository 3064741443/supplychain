package glsx.com.cn.task.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.util.Comparators;
import org.oreframework.boot.autoconfigure.snowflake.algorithm.SnowflakeWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import tk.mybatis.mapper.entity.Example;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.merchant.facade.model.response.MerchantFacadeResponse;
import cn.com.glsx.merchant.facade.remote.MerchantFacadeRemote;
import cn.com.glsx.supplychain.model.am.MaterialInfo;
import cn.com.glsx.supplychain.model.am.ProductSplitHistoryPrice;
import glsx.com.cn.task.common.Constants;
import glsx.com.cn.task.mapper.LogisticsMapper;
import glsx.com.cn.task.mapper.MerchantOrderMapper;
import glsx.com.cn.task.mapper.OrderInfoDetailMapper;
import glsx.com.cn.task.mapper.ProductHistoryPriceMapper;
import glsx.com.cn.task.mapper.SysncMonitorRecordMapper;
import glsx.com.cn.task.mapper.am.MaterialInfoMapper;
import glsx.com.cn.task.mapper.am.ProductSplitHistoryPriceMapper;
import glsx.com.cn.task.mapper.am.StatementSellMapper;
import glsx.com.cn.task.model.Logistics;
import glsx.com.cn.task.model.MerchantOrder;
import glsx.com.cn.task.model.OrderInfoDetail;
import glsx.com.cn.task.model.ProductHistoryPrice;
import glsx.com.cn.task.model.StatementSell;
import glsx.com.cn.task.model.SysncMonitorRecord;
import glsx.com.cn.task.service.StatementSnJobService;
import glsx.com.cn.task.util.TaskUtils;
import glsx.com.cn.task.vo.MerchantOrderStatement;

@Service
public class StatementSnJobServiceImpl implements StatementSnJobService{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private SysncMonitorRecordMapper monitorRecordMapper;
	@Autowired
	private OrderInfoDetailMapper orderInfoDetailMapper;
	@Autowired
	private MerchantOrderMapper merchantOrderMapper;
	@Autowired
	private MaterialInfoMapper materialInfoMapper;
	@Autowired
	private LogisticsMapper logisticsMapper;
	@Autowired
	private ProductHistoryPriceMapper productPriceMapper;
	@Autowired
	private ProductSplitHistoryPriceMapper productSplitPriceMapper;
	@Autowired
	private StatementSellMapper statementSellMapper;
	@Autowired
	private SnowflakeWorker snowflakeWorker;
	@Autowired
	private MerchantFacadeRemote merchantFacadeRemote;
	
	//硬件设备汇总
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { RpcServiceException.class })
	@Override
	public void doSyncStatementSnDispatch() throws RpcServiceException{
		try{
			SysncMonitorRecord monitorRecord=  this.getSysncMonitorRecord(Constants.smr_sn_statement_dispatch_id);
			handleSyncStatementSnDispatch(Constants.smr_sn_statement_step_count,monitorRecord);
			this.setSysncMonitorRecord(monitorRecord);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new RpcServiceException(e.getMessage());
		}
	}

	//配件汇总
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { RpcServiceException.class })
	@Override
	public void doSyncStatementSnDirect() throws RpcServiceException{
		try{
			SysncMonitorRecord monitorRecord = this.getSysncMonitorRecord(Constants.smr_sn_statement_direct_id);
			handleSyncStatementSnDirect(Constants.smr_sn_statement_step_count,monitorRecord);
			this.setSysncMonitorRecord(monitorRecord);
		}
		catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new RpcServiceException(e.getMessage());
		}	
	}
	
	private void handleSyncStatementSnDirect(Integer stepCount,SysncMonitorRecord monitorRecord){
		
		List<MerchantOrderStatement> listMerchantOrderStatement = null;
		Map<String,MerchantOrderStatement> mapMerchantOrderStatement = null;
		List<String> listDispatchOrderCodes = null;
		List<Logistics> listLogistics = null;
		List<StatementSell> listStatementSell = null;
		Map<String,String> mapMaterialInfo = new HashMap<>();
		Map<String,String> mapMerchantInfo = new HashMap<>();
		while(true){
			
			listMerchantOrderStatement = listMerchantOrderStatementForPeiJianFromDB(Constants.smr_sn_statement_step_count,TaskUtils.convertTimeStemp2Date(monitorRecord.getMonitorFlag()));
			if(null == listMerchantOrderStatement || listMerchantOrderStatement.isEmpty()){
				break;
			}
			listDispatchOrderCodes = listMerchantOrderStatement.stream().map(MerchantOrderStatement::getDispatchOrderNumber).collect(Collectors.toList());
			listLogistics = listLogisticsByDispatchOrderCodesFromDB(listDispatchOrderCodes);
			for(MerchantOrderStatement merchantOrderStatement:listMerchantOrderStatement){
				monitorRecord.setMonitorFlag(TaskUtils.convertDate2TimeStemp(merchantOrderStatement.getUpdateDate()));				
			}
			mapMerchantOrderStatement = listMerchantOrderStatement.stream().collect(Collectors.toMap(MerchantOrderStatement::getDispatchOrderNumber, a -> a,(k1, k2)->k1));
			listStatementSell = this.generatorListStatementSellFromPeiJian(listLogistics, mapMerchantOrderStatement, mapMaterialInfo,mapMerchantInfo);
			if(null == listStatementSell || listStatementSell.isEmpty()){
				continue;
			}
			saveListStatementSellToDB(listStatementSell);
			
			listMerchantOrderStatement = null;
			listDispatchOrderCodes = null;
			mapMerchantOrderStatement = null;
			listStatementSell = null;
			sleepCurThread((long) 1000);
			
		}
	}
	
	
	
	private void handleSyncStatementSnDispatch(Integer stepCount,SysncMonitorRecord monitorRecord){
		
		List<OrderInfoDetail> listOrderInfoDetail = null;
		Map<String,Integer> mapOrderCode = new HashMap<>();
		Map<Long,Integer> mapLogisticIds = new HashMap<>();
		List<String> listOrderCode = null;
		List<Long> listLogisticsIds = null;
		List<MerchantOrderStatement> listMerchantOrderStatement = null;
		List<Logistics> listLogistics = null;
		Map<String,MerchantOrderStatement> mapMerchantOrderStatement = null;
		Map<Long,Logistics> mapLogistics = null;
		List<StatementSell> listStatementSell = null;
		Map<String,String> mapMaterialInfo = new HashMap<>();
		Map<String,String> mapMerchantInfo = new HashMap<>();
		while(true){
			
			listOrderInfoDetail = listOrderInfoDetailFromDB(stepCount,monitorRecord.getMonitorFlag());
			if(null == listOrderInfoDetail || listOrderInfoDetail.isEmpty()){
				break;
			}
			for(OrderInfoDetail orderInfoDetail:listOrderInfoDetail){
				monitorRecord.setMonitorFlag(orderInfoDetail.getId());
				if(null == orderInfoDetail.getLogisticsId()){
					continue;
				}
				Integer flag = mapOrderCode.get(orderInfoDetail.getOrderCode());
				if(null == flag){
					mapOrderCode.put(orderInfoDetail.getOrderCode(), 1);
				}
				Integer logisticsFlag = mapLogisticIds.get(orderInfoDetail.getLogisticsId());
				if(null == logisticsFlag){
					mapLogisticIds.put(Long.valueOf(orderInfoDetail.getLogisticsId()), 1);
				}
			}
			if(mapOrderCode.isEmpty()){
				continue;
			}
			listOrderCode = new ArrayList<String>(mapOrderCode.keySet());
			if(listOrderCode.isEmpty()){
				continue;
			}
			listMerchantOrderStatement = this.listMerchantOrderStatementForSnFromDB(listOrderCode);
			if(null == listMerchantOrderStatement || listMerchantOrderStatement.isEmpty()){
				continue;
			}
			mapMerchantOrderStatement = listMerchantOrderStatement.stream().collect(Collectors.toMap(MerchantOrderStatement::getDispatchOrderNumber, a -> a,(k1, k2)->k1));
			
			listLogisticsIds = new ArrayList<Long>(mapLogisticIds.keySet());
			if(!listLogisticsIds.isEmpty()){
				listLogistics = this.listLogistics(listLogisticsIds);
				if(null != listLogistics && !listLogistics.isEmpty()){
					mapLogistics = listLogistics.stream().collect(Collectors.toMap(Logistics::getId, a -> a,(k1, k2)->k1));
				}
			}
			listStatementSell = this.generatorListStatementSell(listOrderInfoDetail, mapMerchantOrderStatement, mapLogistics, mapMaterialInfo,mapMerchantInfo);
			if(null == listStatementSell || listStatementSell.isEmpty()){
				continue;
			}			
			saveListStatementSellToDB(listStatementSell);
			
			mapOrderCode.clear();
			mapLogisticIds.clear();
			listOrderCode = null;
			listLogisticsIds = null;
			listMerchantOrderStatement = null;
			listLogistics = null;
			mapMerchantOrderStatement = null;
			mapLogistics = null;
			listStatementSell = null;
			listOrderInfoDetail = null;
			sleepCurThread((long) 1000);
		}
	}
	
	
	
	private Integer saveListStatementSellToDB(List<StatementSell> listStatementSell){
		if(null == listStatementSell || listStatementSell.isEmpty()){
			return 0;
		}
		return statementSellMapper.batchInsert(listStatementSell);
	}
	
	private List<StatementSell> generatorListStatementSellFromPeiJian(List<Logistics> listLogistics,
			Map<String,MerchantOrderStatement> mapMerchantOrderStatement,
			Map<String,String> mapMaterialInfo,
			Map<String,String> mapMerchantInfo){
		
		if(null == listLogistics || listLogistics.isEmpty()){
			return null;
		}
		
		MerchantOrderStatement merchantOrderStatement = null;
		List<StatementSell> listStatementSell = new ArrayList<>();
		for(Logistics logistics:listLogistics){
			if(logistics.getShipmentsQuantity() == null){
				continue;
			}
			merchantOrderStatement = mapMerchantOrderStatement.get(logistics.getServiceCode());
			if(null == merchantOrderStatement){
				continue;
			}
			if(StringUtils.isEmpty(merchantOrderStatement.getMaterialCode()) || StringUtils.isEmpty(merchantOrderStatement.getProductCode())){
				continue;
			}
			if(StringUtils.isEmpty(merchantOrderStatement.getMerchantCode())){
				continue;
			}
			if(StringUtils.isEmpty(merchantOrderStatement.getMerchantName())){
				merchantOrderStatement.setMerchantName(this.getMerchantName(merchantOrderStatement.getMerchantCode(), mapMerchantInfo));
			}
			if(StringUtils.isEmpty(merchantOrderStatement.getMaterialName())){
				merchantOrderStatement.setMaterialName(this.getMaterialName(merchantOrderStatement.getMaterialCode(), mapMaterialInfo));
			}
			for(int i=0; i<logistics.getShipmentsQuantity();i++){
				listStatementSell.add(generatorStatementSellFromPeiJian(logistics,merchantOrderStatement));
			}
		}
		return listStatementSell;	
	}
	
	private StatementSell generatorStatementSellFromPeiJian(Logistics logistics,MerchantOrderStatement merchantOrderStatement){
		StatementSell statementSell = new StatementSell();
		statementSell.setSn("PJ" + TaskUtils.getOrderNumber(snowflakeWorker));
		statementSell.setMerchantCode(merchantOrderStatement.getMerchantCode());
		statementSell.setMerchantName(merchantOrderStatement.getMerchantName());
		statementSell.setMerchantOrderCode(merchantOrderStatement.getMerchantOrderCode());
		statementSell.setDispatchOrderCode(merchantOrderStatement.getDispatchOrderNumber());
		statementSell.setProductCode(merchantOrderStatement.getProductCode());
		statementSell.setProductName(merchantOrderStatement.getProductName());
		statementSell.setProductType(merchantOrderStatement.getProductType());
		statementSell.setMaterialCode(merchantOrderStatement.getMaterialCode());
		statementSell.setMaterialName(merchantOrderStatement.getMaterialName());
		statementSell.setLogisticsNo(logistics.getOrderNumber());
		statementSell.setLogisticsCmp(StringUtils.isEmpty(logistics.getCompany())?"":logistics.getCompany());
		statementSell.setVtSn("Y");
		statementSell.setSendTime(logistics.getCreatedDate());
		statementSell.setTime(merchantOrderStatement.getProductPriceTime());
		statementSell.setCreatedBy("admin");
		statementSell.setCreatedDate(TaskUtils.getNowDate());
		statementSell.setUpdatedBy("admin");
		statementSell.setUpdatedDate(TaskUtils.getNowDate());
		statementSell.setDeletedFlag("N");
		return statementSell;
	}
	
	private List<StatementSell> generatorListStatementSell(List<OrderInfoDetail> listOrderInfoDetail,
			Map<String,MerchantOrderStatement> mapMerchantOrderStatement,
			Map<Long,Logistics> mapLogistics, 
			Map<String,String> mapMaterialInfo,
			Map<String,String> mapMerchantInfo){
		
		MerchantOrderStatement merchantOrderStatement = null;
		Logistics logistics = null;
		List<StatementSell> listStatementSell = new ArrayList<>();
		
		for(OrderInfoDetail orderInfoDetail:listOrderInfoDetail){
			if(null == orderInfoDetail.getLogisticsId()){
				continue;
			}
			merchantOrderStatement = mapMerchantOrderStatement.get(orderInfoDetail.getOrderCode());
			if(null == merchantOrderStatement){
				continue;
			}
			if(StringUtils.isEmpty(merchantOrderStatement.getMaterialCode()) || StringUtils.isEmpty(merchantOrderStatement.getProductCode())){
				continue;
			}
			if(StringUtils.isEmpty(merchantOrderStatement.getMerchantCode())){
				continue;
			}
			if(StringUtils.isEmpty(merchantOrderStatement.getMerchantName())){
				merchantOrderStatement.setMerchantName(this.getMerchantName(merchantOrderStatement.getMerchantCode(), mapMerchantInfo));
			}
			
			if(StringUtils.isEmpty(merchantOrderStatement.getMaterialName())){
				merchantOrderStatement.setMaterialName(this.getMaterialName(merchantOrderStatement.getMaterialCode(), mapMaterialInfo));
			}
			logistics = mapLogistics.get(orderInfoDetail.getLogisticsId().longValue());
			if(null == logistics){
				continue;
			}
			listStatementSell.add(genetorMerchantOrderStatementSell(orderInfoDetail,merchantOrderStatement,logistics));
		}
		return listStatementSell;
	}
	
	private StatementSell genetorMerchantOrderStatementSell(OrderInfoDetail orderInfoDetail,MerchantOrderStatement merchantOrderStatement,Logistics logistics){
		StatementSell statementSell = new StatementSell();
		statementSell.setSn(orderInfoDetail.getSn());
		statementSell.setMerchantCode(merchantOrderStatement.getMerchantCode());
		statementSell.setMerchantName(merchantOrderStatement.getMerchantName());
		statementSell.setMerchantOrderCode(merchantOrderStatement.getMerchantOrderCode());
		statementSell.setDispatchOrderCode(orderInfoDetail.getOrderCode());
		statementSell.setProductCode(merchantOrderStatement.getProductCode());
		statementSell.setProductName(merchantOrderStatement.getProductName());
		statementSell.setProductType(merchantOrderStatement.getProductType());
		statementSell.setMaterialCode(merchantOrderStatement.getMaterialCode());
		statementSell.setMaterialName(merchantOrderStatement.getMaterialName());
		statementSell.setLogisticsNo(logistics.getOrderNumber());
		statementSell.setLogisticsCmp(StringUtils.isEmpty(logistics.getCompany())?"":logistics.getCompany());
		statementSell.setVtSn("N");
		statementSell.setSendTime(orderInfoDetail.getUpdatedDate());
		statementSell.setTime(merchantOrderStatement.getProductPriceTime());
		statementSell.setCreatedBy("admin");
		statementSell.setCreatedDate(TaskUtils.getNowDate());
		statementSell.setUpdatedBy("admin");
		statementSell.setUpdatedDate(TaskUtils.getNowDate());
		statementSell.setDeletedFlag("N");
		return statementSell;
	}
	
	private String getMaterialName(String materialCode,Map<String,String> mapMaterialInfo){
		String result = "";
		String materialName = mapMaterialInfo.get(materialCode);
		if(!StringUtils.isEmpty(materialName)){
			return materialName;
		}
		MaterialInfo condition = new MaterialInfo();
		condition.setMaterialCode(materialCode);		
		MaterialInfo materialInfo = materialInfoMapper.selectOne(condition);
		if(null != materialInfo){
			result = materialInfo.getMaterialName();
			mapMaterialInfo.put(materialCode, result);
		}
		return result;
	}
	
	private void filtPriceTimeMerchantOrderStatement(List<MerchantOrderStatement> listMerchantOrderStatement){
		if(null == listMerchantOrderStatement || listMerchantOrderStatement.isEmpty()){
			return;
		}
		List<String> listProductCode = listMerchantOrderStatement.stream().map(MerchantOrderStatement::getProductCode).collect(Collectors.toList());
		Example example = new Example(ProductSplitHistoryPrice.class);
		example.createCriteria().andIn("productCode", listProductCode);							
		List<ProductSplitHistoryPrice> listProductPrices = productSplitPriceMapper.selectByExample(example);
		if(null == listProductPrices || listProductPrices.isEmpty()){
			return;
		}
		Map<String,List<ProductSplitHistoryPrice>> mapProductPrices = listProductPrices.stream().collect(Collectors.groupingBy(ProductSplitHistoryPrice::getProductCode));
		List<ProductSplitHistoryPrice> listPrice = null;
		for(MerchantOrderStatement merchantOrderStatement:listMerchantOrderStatement){
			listPrice = mapProductPrices.get(merchantOrderStatement.getProductCode());
			if(null == listPrice || listPrice.isEmpty()){
				continue;
			}
			listPrice.sort(Comparator.comparing(ProductSplitHistoryPrice::getTime).reversed());
			for(ProductSplitHistoryPrice price:listPrice){
				if(price.getTime().compareTo(merchantOrderStatement.getMerchantOrderTime())<=0){
					merchantOrderStatement.setProductPriceTime(price.getTime());
					break;
				}
			}
			//如果未找到价格 随机给当前价格时间
			if(merchantOrderStatement.getProductPriceTime() == null){
				merchantOrderStatement.setProductPriceTime(listPrice.get(0).getTime());
			}
		}
	}
	
	private List<MerchantOrderStatement> listMerchantOrderStatementForPeiJianFromDB(Integer stepCount,Date dataTime){
		MerchantOrder condition = new MerchantOrder();
		condition.setUpdatedDate(dataTime);
		condition.setTotalOrder(stepCount);
		List<MerchantOrderStatement> listMerchantOrderStatement = merchantOrderMapper.listMerchantOrderStatementForPeiJianFromDB(condition);
		filtPriceTimeMerchantOrderStatement(listMerchantOrderStatement);
		return listMerchantOrderStatement;
	}
	
	private List<MerchantOrderStatement> listMerchantOrderStatementForSnFromDB(List<String> listOrderCode){
		List<MerchantOrderStatement> listMerchantOrderStatement = merchantOrderMapper.listMerchantOrderStatementForSn(listOrderCode);
		filtPriceTimeMerchantOrderStatement(listMerchantOrderStatement);
		return listMerchantOrderStatement;	
	}
	
	private List<Logistics> listLogisticsByDispatchOrderCodesFromDB(List<String> listDispatchOrderCodes){
		Example example = new Example(Logistics.class);
		example.createCriteria().andIn("serviceCode", listDispatchOrderCodes)
								.andEqualTo("type", 5);
		return logisticsMapper.selectByExample(example);
	}
	
	private List<OrderInfoDetail> listOrderInfoDetailFromDB(Integer stepCount,Integer limitId){
		Example example = new Example(OrderInfoDetail.class);
		example.orderBy("id").asc();
		example.createCriteria().andGreaterThan("id", limitId);
		PageHelper.startPage(1, stepCount);
		return orderInfoDetailMapper.selectByExample(example);
	}
	
	private List<Logistics> listLogistics(List<Long> listLogisticsIds){
		Example example = new Example(Logistics.class);
		example.createCriteria().andIn("id", listLogisticsIds);
		return logisticsMapper.selectByExample(example);
	}
	
	private SysncMonitorRecord getSysncMonitorRecord(Integer id){	
		SysncMonitorRecord condition = new SysncMonitorRecord();
		condition.setId(id);
		return monitorRecordMapper.selectOne(condition);
	}
	
	private Integer setSysncMonitorRecord(SysncMonitorRecord record){
		if(null == record.getId()){
			return 0;
		}
		return monitorRecordMapper.updateByPrimaryKey(record);
	}
	
	private String getMerchantName(String merchantCode,Map<String,String> mapMerchantInfo){
		String result = mapMerchantInfo.get(merchantCode);
		if(!StringUtils.isEmpty(result)){
			return result;
		}
		result = "";
		try{
			RpcResponse<MerchantFacadeResponse> rsp = merchantFacadeRemote.getMerchantFacadeRemote(Integer.valueOf(merchantCode));
			if(null != rsp.getResult()){
				result = rsp.getResult().getMerchantName();
			}
		}catch(RpcServiceException e){
			result = "";
		}
		mapMerchantInfo.put(merchantCode, result);
		return result;
	}
	
	private void sleepCurThread(Long mic){
		try {
			Thread.currentThread().sleep(mic);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
