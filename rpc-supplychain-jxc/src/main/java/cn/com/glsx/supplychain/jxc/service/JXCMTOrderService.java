package cn.com.glsx.supplychain.jxc.service;

import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.jxc.common.Constants;
import cn.com.glsx.supplychain.jxc.converter.*;
import cn.com.glsx.supplychain.jxc.dto.*;
import cn.com.glsx.supplychain.jxc.enums.DispatchOrderStatusEnum;
import cn.com.glsx.supplychain.jxc.enums.JXCErrorCodeEnum;
import cn.com.glsx.supplychain.jxc.enums.MerchantOrderSignStatusEnum;
import cn.com.glsx.supplychain.jxc.enums.MerchantOrderStatusEnum;
import cn.com.glsx.supplychain.jxc.kafka.ExportMerchantOrder;
import cn.com.glsx.supplychain.jxc.kafka.SendKafkaService;
import cn.com.glsx.supplychain.jxc.manager.JXCCarBrandManager;
import cn.com.glsx.supplychain.jxc.mapper.*;
import cn.com.glsx.supplychain.jxc.model.*;
import cn.com.glsx.supplychain.jxc.util.JxcUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.oreframework.boot.autoconfigure.snowflake.algorithm.SnowflakeWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class JXCMTOrderService {
	private static final Logger logger = LoggerFactory.getLogger(JXCMTOrderService.class);
	@Autowired
	private JXCMTMerchantOrderMapper jxcmtMerchantOrderMapper;
	@Autowired
	private JXCMTMerchantOrderMaterialMapper jxcmtMerchantOrderMaterialMapper;
	@Autowired
	private JXCMTMerchantOrderReflectMcodeMapper jxcmtMerchantOrderReflectMcodeMapper;
	@Autowired
	private JXCMTMerchantOrderVehicleMapper jxcmtMerchantOrderVehicleMapper;
	@Autowired
	private JXCMTBsMerchantOrderMapper jxcmtBsMerchantOrderMapper;
	@Autowired
	private JXCMTBsMerchantOrderDetailMapper jxcmtBsMerchantOrderDetailMapper;
	@Autowired
	private JXCMTBsMerchantOrderVehicleMapper jxcmtBsMerchantOrderVehicleMapper;
	@Autowired
	private JXCMTBsMerchantOrderSignMapper jxcmtBsMerchantOrderSignMapper;
	@Autowired
	private JXCMTEcMerchantOrderMapper jxcmtEcMerchantOrderMapper;
	@Autowired
	private JXCMTAttribInfoService jxcmtAttribInfoService;
	@Autowired
	private JXCMTBsDealerUserInfoService jxcmtUserInfoService;
	@Autowired
    private SnowflakeWorker snowflakeWorker;
	@Autowired
	private JXCMTProductSplitService jxcmtProductSplitService;
	@Autowired
	private JXCMTMaterialInfoService jxcmtMaterialInfoService;
	@Autowired
	private JXCMTBsAddressService jxcmtBsAddressService;
	@Autowired
	private JXCMTBsLogisticsService jxcmtBsLogisticsService;
	@Autowired
	private JXCMTProductService jxcmtProductService;
	@Autowired
	private JXCMTOrderDispatchService jxcmtOrderDispatchService;
	@Autowired
	private JXCMTDeviceTypeService jxcmtDeviceTypeService;
	@Autowired
	private JXCMTBsSubjectService jxcmtBsSubjectService;
	@Autowired
	private JXCMTGhMerchantOrderService jxcmtGhMerchantOrderService;
	@Autowired
    private SendKafkaService sendKafkaService;
	@Autowired
	private JXCCarBrandManager jxcCarBrandManager;
	
	public List<JXCMTBsMerchantOrderVehicleDTO> listSpOrderVehicle(JXCMTSpOrderVehicleQueryDTO record){
		List<JXCMTBsMerchantOrderVehicleDTO> listResult = new ArrayList<>();
		Example example = new Example(JXCMTBsMerchantOrderVehicle.class);
		example.createCriteria().andIn("merchantOrder", record.getListMerchantOrder());
		Map<String,List<JXCMTGhMerchantOrderConfig>> mapGhMerchantOrderConfigs = jxcmtGhMerchantOrderService.mapListGhMerchantOrderConfigByMerchantOrders( record.getListMerchantOrder());
		List<JXCMTBsMerchantOrderVehicle> listVehicle = jxcmtBsMerchantOrderVehicleMapper.selectByExample(example);
		for(JXCMTBsMerchantOrderVehicle vehicle:listVehicle){
			listResult.add(generatorBsMerhantOrderVehicleDto(vehicle,getBsDealerUserInfoByMerchantOrder(vehicle.getMerchantOrder()),null,null,mapGhMerchantOrderConfigs));
		}
		return listResult;
	}
	
	public List<JXCMTBsMerchantOrderSignDTO> generatorMerchantOrderSign(JXCMTBsMerchantOrderGenSignDTO record){
		List<JXCMTBsMerchantOrderSignDTO> listOrderSignDto = new ArrayList<>();
		List<JXCMTBsMerchantOrderSign> listOrderSign = new ArrayList<>();
		List<JXCMTBsMerchantOrder> listBsMerchantOrder = this.listBsMerchantOrderByBsMerchantOrder(record.getListMerchantOrders());
		if(null == listBsMerchantOrder || listBsMerchantOrder.isEmpty()){
			return listOrderSignDto;
		}
		Map<String,JXCMTBsMerchantOrder> mapBsMerchantOrder = null;
		mapBsMerchantOrder = listBsMerchantOrder.stream().collect(Collectors.toMap(JXCMTBsMerchantOrder::getOrderNumber, a->a));
		if(null == mapBsMerchantOrder){
			mapBsMerchantOrder = new HashMap<>();
		}
		List<JXCMTBsMerchantOrderVehicle> listOrderVehicle = this.listBsMerchantOrderVehicleByBsMerchantOrderCodes(record.getListMerchantOrders(),false);
		if(null == listOrderVehicle || listOrderVehicle.isEmpty()){
			return listOrderSignDto;
		}
		List<String> listDispatchOrderCodes = new ArrayList<>();
		for(JXCMTBsMerchantOrderVehicle vehicle:listOrderVehicle){
			if(!StringUtils.isEmpty(vehicle.getDispatchOrderCode())){
				listDispatchOrderCodes.add(vehicle.getDispatchOrderCode());
			}
		}
		List<JXCMTDispatchOrderNumCount> listDispatchOrderNumCount = jxcmtOrderDispatchService.selectOrderDetailCount(listDispatchOrderCodes);
		Map<String,JXCMTDispatchOrderNumCount> mapDispatchOrderNumCount = listDispatchOrderNumCount.stream().collect(Collectors.toMap(JXCMTDispatchOrderNumCount::getDispatchOrderCode, a->a));
		if(null == mapDispatchOrderNumCount){
			mapDispatchOrderNumCount = new HashMap<>();
		}
		Map<String,JXCMTMaterialInfo> mapMaterialInfo = this.listMaterialInfoByMerchantOrder(record.getListMerchantOrders());
		Map<String,List<JXCMTBsLogistics>> mapBsLogistics = this.listBsLogisticsByDispatchOrderCodes(listDispatchOrderCodes);
		Map<String,JXCMTWarehouseInfo> mapWarehouseInfo = jxcmtOrderDispatchService.listOrderWarehouseInfos(listDispatchOrderCodes);
		List<Long> listReceiveIds = new ArrayList<>();
		for(List<JXCMTBsLogistics> listLogistics:mapBsLogistics.values()){
			if(null == listLogistics || listLogistics.isEmpty()){
				continue;
			}
			for(JXCMTBsLogistics loigistics:listLogistics){
				if(null == loigistics){
					continue;
				}
				listReceiveIds.add(loigistics.getReceiveId());
			}
		}
	//	List<Long> listReceiveIds = mapBsLogistics.values().stream().map(JXCMTBsLogistics::getReceiveId).collect(Collectors.toList());
		List<JXCMTBsAddressDTO> listBsAddress = jxcmtBsAddressService.listAddressByIds(listReceiveIds);
		Map<Integer, JXCMTBsAddressDTO> mapBsAddress = listBsAddress.stream().collect(Collectors.toMap(JXCMTBsAddressDTO::getId, a->a));
		JXCMTBsMerchantOrder bsMerchantOrder = null;
		JXCMTDispatchOrderNumCount dispatchOrderNumCount = null;
		JXCMTBsLogistics bsLogistics = null;
		Map<String,JXCMTBsDealerUserInfo> mapCacheUserInfo = new HashMap<>();
		for(JXCMTBsMerchantOrderVehicle vehicle:listOrderVehicle){
			bsMerchantOrder = mapBsMerchantOrder.get(vehicle.getMerchantOrder());
			if(null == bsMerchantOrder){
				continue;
			}
			if(StringUtils.isEmpty(vehicle.getDispatchOrderCode())){
				continue;
			}
			listOrderSignDto.add(this.generatorBsMerchantOrderSignDto(vehicle, bsMerchantOrder, mapMaterialInfo, dispatchOrderNumCount, bsLogistics, mapBsAddress, mapWarehouseInfo, mapCacheUserInfo));
		}
		for(String merchantOrder:record.getListMerchantOrders()){
			listOrderSign.add(this.generatorBsMerchantOrderSign(merchantOrder, record.getDocumentNo(), "", merchantOrder));
		}
		if(!listOrderSign.isEmpty()){
			jxcmtBsMerchantOrderSignMapper.insertBsMerchantOrderSignOnDupliteKey(listOrderSign);
		}
		return listOrderSignDto;
	}
	
	public List<JXCMTBsMerchantOrderVehicleDTO> listBsMerchantOrderDetail(JXCMTBsMerchantOrderDTO record){
		List<JXCMTBsMerchantOrderVehicleDTO> listVehicleDto = new ArrayList<>();
		List<JXCMTBsMerchantOrderVehicle> listVehicle = this.listBsMerchantOrderVehicleByBsMerchantOrderCode(record.getMerchantOrder());
		if(null == listVehicle || listVehicle.isEmpty()){
			return listVehicleDto;
		}
		JXCMTBsDealerUserInfo bsDealerUserInfo = getBsDealerUserInfoByBsMerchantOrder(listVehicle.get(0).getMerchantOrder());
		List<String> listDispatchOrderCodes = new ArrayList<>();
		for(JXCMTBsMerchantOrderVehicle vehicle:listVehicle){
			if(!StringUtils.isEmpty(vehicle.getDispatchOrderCode())){
				listDispatchOrderCodes.add(vehicle.getDispatchOrderCode());
			}
		}
		Map<String,Integer> mapDispatchCount = listDispatchOrderSendNums(listDispatchOrderCodes);
		JXCMTOrderInfo orderInfo = null;
		for(JXCMTBsMerchantOrderVehicle vehicle:listVehicle){
			orderInfo = jxcmtOrderDispatchService.getOrderInfoByDispatchOrderCode(vehicle.getDispatchOrderCode());
			listVehicleDto.add(this.generatorBsMerhantOrderVehicleDto(vehicle, bsDealerUserInfo, mapDispatchCount,orderInfo));
		}
		return listVehicleDto;
	}

	public List<JXCMTBsMerchantOrderVehicleDTO> listVehicleInformation(){
		List<JXCMTBsMerchantOrderVehicleDTO> listVehicleDto = new ArrayList<>();
		JXCMTBsMerchantOrderVehicleDTO jxcmtBsMerchantOrderVehicleDTO=new JXCMTBsMerchantOrderVehicleDTO();
		List<JXCMTBsMerchantOrderVehicle> jxcmtBsMerchantOrderVehicleList= jxcmtBsMerchantOrderVehicleMapper.listVehicleInformation();
		for(JXCMTBsMerchantOrderVehicle jxcmtBsMerchantOrderVehicle:jxcmtBsMerchantOrderVehicleList){
			BeanUtils.copyProperties(jxcmtBsMerchantOrderVehicle, jxcmtBsMerchantOrderVehicleDTO);
			listVehicleDto.add(jxcmtBsMerchantOrderVehicleDTO);
		}
		return listVehicleDto;
   }
	
	public JXCMTBsMerchantOrderDetailDTO getBsOrder(JXCMTBsMerchantOrderDTO record){
		List<JXCMTBsMerchantOrderVehicleDTO> listOrderVehicleDto = new ArrayList<>();
		List<JXCMTOrderInfoDTO> listDispatchOrders = new ArrayList<>();
		JXCMTBsMerchantOrderDetailDTO dtoResult = new JXCMTBsMerchantOrderDetailDTO();
		List<String> listMerchantOrder=new ArrayList<>();
		listMerchantOrder.add(record.getMerchantOrder());
		Map<String,List<JXCMTGhMerchantOrderConfig>> mapGhMerchantOrderConfigs = jxcmtGhMerchantOrderService.mapListGhMerchantOrderConfigByMerchantOrders(listMerchantOrder);
		List<JXCMTGhMerchantOrderConfig> listGhMerchantOrderConfig = null;
		JXCMTAttribInfo attribInfo = null;
		JXCMTBsMerchantOrder bsMerchantOrder = this.getBsMerchantOrderByBsMerchantOrder(record.getMerchantOrder());
		if(null == bsMerchantOrder){
			return dtoResult;
		}
		JXCMTBsDealerUserInfo bsDealerUserInfo = jxcmtUserInfoService.getBsDealerUserInfo(bsMerchantOrder.getMerchantCode());
		JXCMTBsMerchantOrderDetail bsMerchantOrderDetail = this.getBsMerchantOrderDetailByMerchantOrder(record.getMerchantOrder());
		JXCMTProduct product = jxcmtProductService.getBsProductByProductCode(bsMerchantOrderDetail.getProductCode());
		JXCMTProductDetail productDetail = jxcmtProductService.getBsProductDetailByProductCode(bsMerchantOrderDetail.getProductCode());
		JXCMTProductSplit productSplit = jxcmtProductSplitService.getProductSplitById(product.getProductSplitId().intValue());
		JXCMTMaterialInfo orderMaterialInfo = jxcmtMaterialInfoService.getMaterialInfoByMaterialCode(productDetail.getOrderMaterialCode());
		JXCMTMaterialInfo materialInfo = jxcmtMaterialInfoService.getMaterialInfoByMaterialCode(productDetail.getMaterialCode());
		List<JXCMTBsMerchantOrderVehicle>listVehicle = this.listBsMerchantOrderVehicleByBsMerchantOrderCode(record.getMerchantOrder());
		List<String> listDispatchOrderCode = new ArrayList<>();
		for(JXCMTBsMerchantOrderVehicle vehicle:listVehicle){
			if(StringUtils.isEmpty(vehicle.getDispatchOrderCode())){
				continue;
			}
			listDispatchOrderCode.add(vehicle.getDispatchOrderCode());
		}
		List<JXCMTBsLogistics> listLogisticsDirect = jxcmtBsLogisticsService.listBsLogisticsByDispatchOrderCodes(listDispatchOrderCode,Constants.LOGISTICS_TYPE_FIVE);
		List<JXCMTBsLogistics> listLogisticsDispatch = jxcmtBsLogisticsService.listBsLogisticsByDispatchOrderCodes(listDispatchOrderCode,Constants.LOGISTICS_TYPE_FOUR);
		Map<String,JXCMTWarehouseInfo> mapWarehouseInfo = jxcmtOrderDispatchService.listOrderWarehouseInfos(listDispatchOrderCode);
		Map<String,Integer> mapDispatchCount = listDispatchOrderSendNums(listDispatchOrderCode);
		Map<String,JXCMTOrderInfo> mapOrderInfo = jxcmtOrderDispatchService.mapOrderInfo(listDispatchOrderCode);
		JXCMTOrderInfoDTO orderInfoDto = null;
		JXCMTWarehouseInfo warehouseInfo = null;
		Integer sendCount = null;
		JXCMTOrderInfo orderInfo = null;
		for(JXCMTBsMerchantOrderVehicle vehicle:listVehicle){
			listOrderVehicleDto.add(this.generatorBsMerhantOrderVehicleDto(vehicle, bsDealerUserInfo, mapDispatchCount,null,mapGhMerchantOrderConfigs));
			if(StringUtils.isEmpty(vehicle.getDispatchOrderCode())){
				continue;
			}
			orderInfoDto = new JXCMTOrderInfoDTO();
			orderInfoDto.setDispatchOrderCode(vehicle.getDispatchOrderCode());
			warehouseInfo = mapWarehouseInfo.get(vehicle.getDispatchOrderCode());
			if(null != warehouseInfo){
				orderInfoDto.setWarehouseId(warehouseInfo.getId());
				orderInfoDto.setWarehouseName(warehouseInfo.getName());
			}
			orderInfoDto.setTotal(vehicle.getBsCheckQuantity());
			sendCount = mapDispatchCount.get(vehicle.getDispatchOrderCode());
			if(null == sendCount){
				sendCount = 0;
			}
			orderInfoDto.setSendQuantities(sendCount);
			orderInfo = mapOrderInfo.get(vehicle.getDispatchOrderCode());
			if(null != orderInfo){
				orderInfoDto.setStatus(orderInfo.getStatus());
			}
			if(StringUtils.isEmpty(orderInfoDto.getStatus())){
				if(orderInfoDto.getSendQuantities().equals(orderInfoDto.getSendQuantities())){
					orderInfoDto.setStatus("OV");
				}else{
					orderInfoDto.setStatus("UF");
				}
			}
			
			Map<String,JXCMTBsLogisticsDTO> mapBsLogisticsDto = new HashMap<>();
			JXCMTBsLogisticsDTO lsdto = null;
			if(null != listLogisticsDirect){
				for(JXCMTBsLogistics item:listLogisticsDirect){
					if(item.getServiceCode().equals(vehicle.getDispatchOrderCode())){
						String strKey = "od:" + item.getOrderNumber() + "cp:" + item.getCompany();
						lsdto = mapBsLogisticsDto.get(strKey);
						if(null == lsdto){
							lsdto = new JXCMTBsLogisticsDTO();
							lsdto.setAccept(item.getAccept());
							lsdto.setCompany(item.getCompany());
							lsdto.setOrderNumber(item.getOrderNumber());
							lsdto.setShipmentsQuantity(item.getShipmentsQuantity());
							mapBsLogisticsDto.put(strKey, lsdto);							
						}else{
							lsdto.setShipmentsQuantity(lsdto.getShipmentsQuantity() + item.getShipmentsQuantity());
							mapBsLogisticsDto.put(strKey, lsdto);
						}	
					}
				}
			}
			if(null != listLogisticsDispatch){
				for(JXCMTBsLogistics item:listLogisticsDispatch){
					if(item.getServiceCode().equals(vehicle.getDispatchOrderCode())){
						String strKey = "od:" + item.getOrderNumber() + "cp:" + item.getCompany();
						lsdto = mapBsLogisticsDto.get(strKey);
						if(null == lsdto){
							lsdto = new JXCMTBsLogisticsDTO();
							lsdto.setAccept(item.getAccept());
							lsdto.setCompany(item.getCompany());
							lsdto.setOrderNumber(item.getOrderNumber());
							lsdto.setShipmentsQuantity(jxcmtOrderDispatchService.countOrderDetailByLogisticsId(item.getId().intValue(), vehicle.getDispatchOrderCode()));
							mapBsLogisticsDto.put(strKey, lsdto);		
						}else{
							lsdto.setShipmentsQuantity(lsdto.getShipmentsQuantity() + jxcmtOrderDispatchService.countOrderDetailByLogisticsId(item.getId().intValue(), vehicle.getDispatchOrderCode()));
							mapBsLogisticsDto.put(strKey, lsdto);
						}	
					}
				}
			}
			List<JXCMTBsLogisticsDTO> logisticsDto = new ArrayList(mapBsLogisticsDto.values());			
			orderInfoDto.setLogisticsDto(logisticsDto);	
			listDispatchOrders.add(orderInfoDto);
		}
		dtoResult.setProductTypeId(jxcmtAttribInfoService.getProductTypeFromDbProduct(productSplit.getServiceType()));
		dtoResult.setProductTypeName(jxcmtAttribInfoService.getProductTypeNameById(dtoResult.getProductTypeId(), null));
		dtoResult.setListDispatchOrders(listDispatchOrders);
		dtoResult.setListOrderVehicleDto(listOrderVehicleDto);
		dtoResult.setChannelId(jxcmtAttribInfoService.getMerchantChannelFromDbMerchantChannel(bsDealerUserInfo.getChannel()));
		dtoResult.setChannelName(jxcmtAttribInfoService.getMerchantChannelNameById(dtoResult.getChannelId(),null));
		dtoResult.setMerchantCode(bsDealerUserInfo.getMerchantCode());
		dtoResult.setMerchantName(bsDealerUserInfo.getMerchantName());
		dtoResult.setProductCode(productSplit.getProductCode());
		dtoResult.setProductName(productSplit.getProductName());
		dtoResult.setPackageOne(productSplit.getPackageOne());
		dtoResult.setServiceTime(productSplit.getServiceTime());
		JXCMTBsMaterialInfoDTO orderMaterialInfoDto = new JXCMTBsMaterialInfoDTO();
		JXCMTBsMaterialInfoDTO materialInfoDto = new JXCMTBsMaterialInfoDTO();
		orderMaterialInfoDto.setMaterialCode(productDetail.getOrderMaterialCode());
		orderMaterialInfoDto.setMaterialName(productDetail.getOrderMaterialName());
		orderMaterialInfoDto.setMaterialTypeId(orderMaterialInfo.getMaterialTypeId());
		orderMaterialInfoDto.setMaterialTypeName(orderMaterialInfo.getMaterialTypeName());
		orderMaterialInfoDto.setDeviceTypeId(orderMaterialInfo.getDeviceTypeId());
		orderMaterialInfoDto.setPropQuantity(productDetail.getPropQuantity());
		orderMaterialInfoDto.setMaterialTotal(bsMerchantOrderDetail.getOrderQuantity());
		JXCMTDeviceTypeDispatchSurpport dispatchSurpport = jxcmtDeviceTypeService.getDeviceTypeDispatchSurpport(orderMaterialInfo.getDeviceTypeId());
		orderMaterialInfoDto.setDeviceTypeDispatch((dispatchSurpport==null)?"N":"Y");
		orderMaterialInfoDto.setDeviceTypeName(jxcmtDeviceTypeService.getDeviceTypeNameById(orderMaterialInfo.getDeviceTypeId(), null));
		orderMaterialInfoDto.setPropQuantity(productDetail.getPropQuantity());
		materialInfoDto.setMaterialCode(productDetail.getMaterialCode());
		materialInfoDto.setMaterialName(productDetail.getMaterialName());
		if(null != materialInfo){
			materialInfoDto.setMaterialTypeId(materialInfo.getMaterialTypeId());
			materialInfoDto.setMaterialTypeName(materialInfo.getMaterialTypeName());
			materialInfoDto.setDeviceTypeId(materialInfo.getDeviceTypeId());
			materialInfoDto.setDeviceTypeName(jxcmtDeviceTypeService.getDeviceTypeNameById(materialInfo.getDeviceTypeId(), null));
			dispatchSurpport = jxcmtDeviceTypeService.getDeviceTypeDispatchSurpport(materialInfo.getDeviceTypeId());
			materialInfoDto.setDeviceTypeDispatch((dispatchSurpport==null)?"N":"Y");
		}		
		materialInfoDto.setPropQuantity(productDetail.getPropQuantity());
		materialInfoDto.setMaterialTotal(bsMerchantOrderDetail.getCheckQuantity());
		dtoResult.setOrderMaterialInfoDto(orderMaterialInfoDto);
		dtoResult.setCheckMaterialInfoDto(materialInfoDto);
		dtoResult.setCheckQuantity(bsMerchantOrderDetail.getCheckQuantity());
		if(productDetail.getPropQuantity() != null){
			dtoResult.setProductTotal(bsMerchantOrderDetail.getOrderQuantity()/productDetail.getPropQuantity());
		}else{
			dtoResult.setProductTotal(bsMerchantOrderDetail.getOrderQuantity());
		}
		dtoResult.setRemarks(bsMerchantOrder.getRemarks());
		dtoResult.setCheckRemark(bsMerchantOrder.getCheckRemark());
		dtoResult.setSubjectId(bsMerchantOrderDetail.getSubjectId());
		dtoResult.setSubjectName(jxcmtBsSubjectService.getBsSubjectNameById(bsMerchantOrderDetail.getSubjectId()));
		dtoResult.setInsure(bsMerchantOrderDetail.getInsure());
		dtoResult.setModelDevice(bsMerchantOrder.getModelDevice());
		//生成固定配置和选项配置
		listGhMerchantOrderConfig = mapGhMerchantOrderConfigs.get(record.getMerchantOrder());
		if(null == listGhMerchantOrderConfig || listGhMerchantOrderConfig.isEmpty()){
			return dtoResult;
		}
		String optionConfigDesc = "";
		String fastenConfigDesc = "";
		for(JXCMTGhMerchantOrderConfig oConfig:listGhMerchantOrderConfig){
			attribInfo = jxcmtAttribInfoService.getAttribInfoById(oConfig.getAttribInfoId());
			if(null == attribInfo){
				continue;
			}
			if(oConfig.getStrOption().equals("O")){
				optionConfigDesc += attribInfo.getComment();
				optionConfigDesc += ":";
				optionConfigDesc += attribInfo.getName();
				optionConfigDesc += "/";
			}else if(oConfig.getStrOption().equals("F")){
				fastenConfigDesc += attribInfo.getComment();
				fastenConfigDesc += ":";
				fastenConfigDesc += attribInfo.getName();
				fastenConfigDesc += "/";
			}
		}
		dtoResult.setFastenConfigDesc(fastenConfigDesc);
		dtoResult.setOptionConfigDesc(optionConfigDesc);
		return dtoResult;
	}
	
	public Page<JXCMTBsMerchantOrderDTO> pageBsMerchantOrderBSS(RpcPagination<JXCMTBsMerchantOrderDTO> pagination){
		Page<JXCMTBsMerchantOrderDTO> pageResult = new Page<>();
		JXCMTBsMerchantOrder condition = new JXCMTBsMerchantOrder();
		condition.setMerchantCode(pagination.getCondition().getMerchantCode());
		condition.setMerchantName(pagination.getCondition().getMerchantName());
		condition.setOrderNumber(pagination.getCondition().getMerchantOrder());
		condition.setMoOrderCode(pagination.getCondition().getMoOrderCode());
		if(!StringUtils.isEmpty(pagination.getCondition().getStatus())){
			condition.setStatus(Byte.valueOf(pagination.getCondition().getStatus()));
		}
		if(!StringUtils.isEmpty(pagination.getCondition().getSignStatus())){
			condition.setSignStatus(Byte.valueOf(pagination.getCondition().getSignStatus()));
		}
		condition.setMerchantChannelId(jxcmtAttribInfoService.getDbMerchantChannelFromAttribInfo(pagination.getCondition().getChannelId()));
		condition.setProductTypeId(jxcmtAttribInfoService.getDbProductTypeFromAttribInfo(pagination.getCondition().getProductTypeId()));
		condition.setProductCode(pagination.getCondition().getProductCode());
		condition.setProductName(pagination.getCondition().getProductName());
		condition.setMaterialCode(pagination.getCondition().getMaterialCode());
		condition.setMaterialName(pagination.getCondition().getMaterialName());
		condition.setOrderTimeStart(JxcUtils.getDateFromString(pagination.getCondition().getOrderTimeStart()));
		condition.setOrderTimeEnd(JxcUtils.getDateFromString(pagination.getCondition().getOrderTimeEnd()));
		condition.setCheckTimeStart(JxcUtils.getDateFromString(pagination.getCondition().getCheckTimeStart()));
		condition.setCheckTimeEnd(JxcUtils.getDateFromString(pagination.getCondition().getCheckTimeEnd()));
		//PageHelper.startPage(pagination.getPageNum(), pagination.getPageSize());
		condition.setPageStart((pagination.getPageNum()-1)*pagination.getPageSize());
		condition.setPageSize(pagination.getPageSize());
		logger.info("bss1111111111111111111111");
		Integer totalCount = jxcmtBsMerchantOrderMapper.countBsMerchantOrderBSS(condition);
		logger.info("bss2222222222222222222222");
		Page<JXCMTBsMerchantOrderDTO> pageMerchantOrder = jxcmtBsMerchantOrderMapper.pageBsMerchantOrderBSS(condition);
		logger.info("bss33333333333333333333333");
		if(null == pageMerchantOrder || pageMerchantOrder.isEmpty()){
			return pageResult;
		}

		List<JXCMTBsMerchantOrderDTO> listMerchantOrder = pageMerchantOrder.getResult();
		if(null == listMerchantOrder || listMerchantOrder.isEmpty()){
			return pageMerchantOrder;
		}
		List<String> listBserMerchantOrderCode = listMerchantOrder.stream().map(JXCMTBsMerchantOrderDTO::getMerchantOrder).collect(Collectors.toList());
		List<Integer> listChannelIds = listMerchantOrder.stream().map(JXCMTBsMerchantOrderDTO::getChannelId).collect(Collectors.toList());
		Map<String,JXCMTBsAddressDTO> mapBsAddress = listBsAddressByBsMerchantOrderCodes(listBserMerchantOrderCode);
		Map<Integer,String> mapMerchantChannels = listBsMerchantChannelName(listChannelIds);
		int totalCheck = 0;
		int totalSend = 0;
		for(JXCMTBsMerchantOrderDTO dto:listMerchantOrder){
			dto.setUpdateFlag("N");
			dto.setBsAddressDto(mapBsAddress.get(dto.getMerchantOrder()));
			dto.setChannelName(mapMerchantChannels.get(jxcmtAttribInfoService.getMerchantChannelFromDbMerchantChannel(dto.getChannelId().byteValue())));
			if(!StringUtils.isEmpty(dto.getStatus())){
				//商户审核页面将待分配转成待发货状态
				if(dto.getStatus().equals(String.valueOf(MerchantOrderStatusEnum.ORDER_WAIT_DISPATCH.getCode()))){
					dto.setUpdateFlag("Y");
					dto.setStatus(String.valueOf(MerchantOrderStatusEnum.ORDER_WAIT_SHIPMENTS.getCode()));
				}
			}	
			totalCheck = (dto.getTotalCheck()==null?0:dto.getTotalCheck());
			totalSend = (dto.getTotalSends()==null?0:dto.getTotalSends());
			if(dto.getStatus().equals(String.valueOf(MerchantOrderStatusEnum.ORDER_ALREADY_INVALID.getCode()))){
				dto.setTotalOwes(0);
			}else{
				dto.setTotalOwes(totalCheck - totalSend);
			}
			pageResult.add(dto);
		}
		pageResult.setTotal(totalCount);
		pageResult.setPageNum(pagination.getPageNum());
		pageResult.setPageSize(pagination.getPageSize());
		if(totalCount%pagination.getPageSize() == 0){
			pageResult.setPages(totalCount/pagination.getPageSize());
		}else{
			pageResult.setPages(totalCount/pagination.getPageSize() + 1);
		}
		return pageResult;
	}
	
	public List<JXCMTBsMerchantOrderExportDTO> exportBsMerchantOrderBSS(JXCMTBsMerchantOrderDTO record){
		//发送kafka异步执行
		ExportMerchantOrder exportMerchantOrder = generatorExportMerchantOrder(record);
		sendKafkaService.notifyMerchantOrderExport(record.getConsumer(), Constants.TASK_CFG_ID_JXC_ORDER_BUSSCHECK, exportMerchantOrder);
		//同步执行
		JXCMTBsMerchantOrder condition = new JXCMTBsMerchantOrder();
		condition.setMerchantCode(record.getMerchantCode());
		condition.setMerchantName(record.getMerchantName());
		condition.setOrderNumber(record.getMerchantOrder());
		condition.setMoOrderCode(record.getMoOrderCode());
		if(!StringUtils.isEmpty(record.getStatus())){
			condition.setStatus(Byte.valueOf(record.getStatus()));
		}
		if(!StringUtils.isEmpty(record.getSignStatus())){
			condition.setSignStatus(Byte.valueOf(record.getSignStatus()));
		}
		condition.setMerchantChannelId(jxcmtAttribInfoService.getDbMerchantChannelFromAttribInfo(record.getChannelId()));
		condition.setProductTypeId(jxcmtAttribInfoService.getDbProductTypeFromAttribInfo(record.getProductTypeId()));
		condition.setProductCode(record.getProductCode());
		condition.setProductName(record.getProductName());
		condition.setMaterialCode(record.getMaterialCode());
		condition.setMaterialName(record.getMaterialName());
		condition.setOrderTimeStart(JxcUtils.getDateFromString(record.getOrderTimeStart()));
		condition.setOrderTimeEnd(JxcUtils.getDateFromString(record.getOrderTimeEnd()));
		condition.setCheckTimeStart(JxcUtils.getDateFromString(record.getCheckTimeStart()));
		condition.setCheckTimeEnd(JxcUtils.getDateFromString(record.getCheckTimeEnd()));
		List<JXCMTBsMerchantOrderExportDTO> ListMerchantOrder = jxcmtBsMerchantOrderMapper.exportBsMerchantOrderBSS(condition);
		if(null == ListMerchantOrder || ListMerchantOrder.isEmpty()){
			return ListMerchantOrder;
		}
		List<String> listMerchantOrder = ListMerchantOrder.stream().map(JXCMTBsMerchantOrderExportDTO::getMerchantOrder).collect(Collectors.toList());
		Map<String,List<JXCMTGhMerchantOrderConfig>> mapGhMerchantOrderConfigs = jxcmtGhMerchantOrderService.mapListGhMerchantOrderConfigByMerchantOrders(listMerchantOrder);
		List<JXCMTGhMerchantOrderConfig> listGhMerchantOrderConfig = null;
		JXCMTAttribInfo attribInfo = null;
		List<String> listDispatchOrders = new ArrayList<>();
		for(JXCMTBsMerchantOrderExportDTO dto:ListMerchantOrder){
			if(!StringUtils.isEmpty(dto.getDispatchOrderCode())){
				listDispatchOrders.add(dto.getDispatchOrderCode());
			}
		}
		List<Integer> listChannelIds = ListMerchantOrder.stream().map(JXCMTBsMerchantOrderExportDTO::getChannelId).collect(Collectors.toList());
		Map<String,List<JXCMTBsLogistics>> mapLogistics = this.listBsLogisticsByDispatchOrderCodes(listDispatchOrders);
		Map<Integer,String> mapMerchantChannels = listBsMerchantChannelName(listChannelIds);
		Map<String,JXCMTBsAddressDTO> mapBsAddress = listBsAddressByBsMerchantOrderCodes(listMerchantOrder);
		JXCMTBsAddressDTO bsAddress = null;
		String strAddress = "";
		int bsTotal = 0;
		int bsProp = 1;
		for(JXCMTBsMerchantOrderExportDTO dto:ListMerchantOrder){
			//更新地址
			bsAddress = mapBsAddress.get(dto.getMerchantOrder());
			if(null != bsAddress){
			    strAddress = "";
			    if(!StringUtils.isEmpty(bsAddress.getProvinceName())){
			     strAddress += bsAddress.getProvinceName();
			    }
			    if(!StringUtils.isEmpty(bsAddress.getCityName())){
			     strAddress += bsAddress.getCityName();
			    }
			    if(!StringUtils.isEmpty(bsAddress.getAreaName())){
			     strAddress += bsAddress.getAreaName();
			    }
			    strAddress += bsAddress.getAddress();
			    dto.setAddress(strAddress);
			    dto.setContacts(bsAddress.getName());
			    dto.setMobile(bsAddress.getMobile());
			}
			if(dto.getChannelId() != null){
				dto.setChannelName(mapMerchantChannels.get(jxcmtAttribInfoService.getMerchantChannelFromDbMerchantChannel(dto.getChannelId().byteValue())));
			}
			if(!StringUtils.isEmpty(dto.getDispatchOrderCode())){
				dto.setListLogistics(JXCMTBsLogisticsRpcConvert.convertListBean(mapLogistics.get(dto.getDispatchOrderCode())));
			}
			//生成固定配置和选项配置
			listGhMerchantOrderConfig = mapGhMerchantOrderConfigs.get(dto.getMerchantOrder());
			if(null == listGhMerchantOrderConfig || listGhMerchantOrderConfig.isEmpty()){
				continue;
			}
			String optionConfigDesc = "";
			String fastenConfigDesc = "";
			for(JXCMTGhMerchantOrderConfig oConfig:listGhMerchantOrderConfig){
				attribInfo = jxcmtAttribInfoService.getAttribInfoById(oConfig.getAttribInfoId());
				if(null == attribInfo){
					continue;
				}
				if(oConfig.getStrOption().equals("O")){
					optionConfigDesc += attribInfo.getComment();
					optionConfigDesc += ":";
					optionConfigDesc += attribInfo.getName();
					optionConfigDesc += "/";
				}else if(oConfig.getStrOption().equals("F")){
					fastenConfigDesc += attribInfo.getComment();
					fastenConfigDesc += ":";
					fastenConfigDesc += attribInfo.getName();
					fastenConfigDesc += "/";
				}
			}
			dto.setFastenConfigDesc(fastenConfigDesc);
			dto.setSetOptionConfigDesc(optionConfigDesc);
		}
		return ListMerchantOrder;
	}
		
	public Page<JXCMTSpMerchantOrderDTO> pageBsMerchantOrderBSP(RpcPagination<JXCMTSpMerchantOrderDTO> pagination){
		Page<JXCMTSpMerchantOrderDTO> pageResult = new Page<>();
		logger.info("pageBsMerchantOrderBSP come1111");
		JXCMTBsMerchantOrder conditon = new JXCMTBsMerchantOrder();
		conditon.setOrderNumber(pagination.getCondition().getMerchantOrder());
		List<String> listOrderNums = listBsMerchantOrderByDispatchOrderLike(pagination.getCondition().getDispatchOrderCode());
		if(null != listOrderNums && !listOrderNums.isEmpty()){
			conditon.setListOrderNums(listOrderNums);
		}
		conditon.setMerchantCode(pagination.getCondition().getMerchantCode());
		conditon.setMerchantName(pagination.getCondition().getMerchantName());
		conditon.setMaterialCode(pagination.getCondition().getMaterialCode());
		conditon.setMaterialName(pagination.getCondition().getMaterialName());
		conditon.setUrlDispatchBills(pagination.getCondition().getUrlDispatchBills());
		if(!StringUtils.isEmpty(pagination.getCondition().getStatus())){
			conditon.setStatus(Byte.valueOf(pagination.getCondition().getStatus()));
		}
		logger.info("pageBsMerchantOrderBSP come2222");
		if(!StringUtils.isEmpty(pagination.getCondition().getChannelId())){
			conditon.setMerchantChannelId(jxcmtAttribInfoService.getDbMerchantChannelFromAttribInfo(pagination.getCondition().getChannelId()));
		}
		
		conditon.setOrderTimeStart(JxcUtils.getDateFromString(pagination.getCondition().getOrderTimeStart()));
		conditon.setOrderTimeEnd(JxcUtils.getDateFromString(pagination.getCondition().getOrderTimeEnd()));
		
	//	PageHelper.startPage(pagination.getPageNum(), pagination.getPageSize());
		conditon.setPageStart((pagination.getPageNum()-1)*pagination.getPageSize());
		conditon.setPageSize(pagination.getPageSize());
		logger.info("pageBsMerchantOrderBSP come99999999");
		Integer totalCount = jxcmtBsMerchantOrderMapper.countBsMerchantOrderBSP(conditon);
		List<JXCMTSpMerchantOrderDTO> listMerchantOrder = jxcmtBsMerchantOrderMapper.pageBsMerchantOrderBSP(conditon);
		logger.info("pageBsMerchantOrderBSP come8888888");
		if(null == listMerchantOrder || listMerchantOrder.isEmpty()){
			return pageResult;
		}
		logger.info("pageBsMerchantOrderBSP come3333");
		List<String> listBserMerchantOrderCode = listMerchantOrder.stream().map(JXCMTSpMerchantOrderDTO::getMerchantOrder).collect(Collectors.toList());
		List<Integer> listChannelIds = listMerchantOrder.stream().map(JXCMTSpMerchantOrderDTO::getChannelId).collect(Collectors.toList());
		Map<String,JXCMTBsAddressDTO> mapBsAddress = listBsAddressByBsMerchantOrderCodes(listBserMerchantOrderCode);
		Map<Integer,String> mapMerchantChannels = listBsMerchantChannelName(listChannelIds);
		int totalCheck = 0;
		int totalSend = 0;
		logger.info("pageBsMerchantOrderBSP come4444");
		for(JXCMTSpMerchantOrderDTO dto:listMerchantOrder){
			dto.setBsAddressDto(mapBsAddress.get(dto.getMerchantOrder()));
			if(null != dto.getChannelId()){
				dto.setChannelName(mapMerchantChannels.get(jxcmtAttribInfoService.getMerchantChannelFromDbMerchantChannel(dto.getChannelId().byteValue())));
			}			
			totalCheck = (dto.getTotalCheck()==null?0:dto.getTotalCheck());
			totalSend = (dto.getTotalSends()==null?0:dto.getTotalSends());
			if(dto.getStatus().equals(String.valueOf(MerchantOrderStatusEnum.ORDER_ALREADY_INVALID.getCode()))){
				dto.setTotalOwes(0);
			}else{
				dto.setTotalOwes(totalCheck - totalSend);
			}
			pageResult.add(dto);
		}
		pageResult.setTotal(totalCount);
		pageResult.setPageNum(pagination.getPageNum());
		pageResult.setPageSize(pagination.getPageSize());
		if(totalCount%pagination.getPageSize() == 0){
			pageResult.setPages(totalCount/pagination.getPageSize());
		}else{
			pageResult.setPages(totalCount/pagination.getPageSize() + 1);
		}
		logger.info("pageBsMerchantOrderBSP come5555");
		return pageResult;
	}
	
	public List<JXCMTBsMerchantOrderExportBspDTO> exportBsMerchantOrderExportBSP(JXCMTSpMerchantOrderDTO record){
		
		//发送kafka异步执行
		ExportMerchantOrder exportMerchantOrder = generatorExportMerchantOrderBsp(record);
		sendKafkaService.notifyMerchantOrderExport(record.getConsumer(), Constants.TASK_CFG_ID_JXC_ORDER_BUSPDISP, exportMerchantOrder);
		//同步
		JXCMTBsMerchantOrder conditon = new JXCMTBsMerchantOrder();
		conditon.setOrderNumber(record.getMerchantOrder());
		List<String> listOrderNums = listBsMerchantOrderByDispatchOrderLike(record.getDispatchOrderCode());
		if(null != listOrderNums && !listOrderNums.isEmpty()){
			conditon.setListOrderNums(listOrderNums);
		}
		conditon.setMerchantCode(record.getMerchantCode());
		conditon.setMerchantName(record.getMerchantName());
		conditon.setMaterialCode(record.getMaterialCode());
		conditon.setMaterialName(record.getMaterialName());
		if(!StringUtils.isEmpty(record.getStatus())){
			conditon.setStatus(Byte.valueOf(record.getStatus()));
		}
		if(!StringUtils.isEmpty(record.getChannelId())){	
			conditon.setMerchantChannelId(jxcmtAttribInfoService.getDbMerchantChannelFromAttribInfo(record.getChannelId()));
		}
		conditon.setOrderTimeStart(JxcUtils.getDateFromString(record.getOrderTimeStart()));
		conditon.setOrderTimeEnd(JxcUtils.getDateFromString(record.getOrderTimeEnd()));
		List<JXCMTBsMerchantOrderExportBspDTO> listMerchantOrder = jxcmtBsMerchantOrderMapper.exportBsMerchantOrderExportBSP(conditon);
		List<String> orderNumberList = listMerchantOrder.stream().map(JXCMTBsMerchantOrderExportBspDTO::getMerchantOrderNum).collect(Collectors.toList());
		Map<String,List<JXCMTGhMerchantOrderConfig>> mapGhMerchantOrderConfigs = jxcmtGhMerchantOrderService.mapListGhMerchantOrderConfigByMerchantOrders(orderNumberList);
		List<JXCMTGhMerchantOrderConfig> listGhMerchantOrderConfig = null;
		JXCMTAttribInfo attribInfo = null;
		if(null == listMerchantOrder || listMerchantOrder.isEmpty()){
			return listMerchantOrder;
		}
		List<String> listBserMerchantOrderCode = listMerchantOrder.stream().map(JXCMTBsMerchantOrderExportBspDTO::getMerchantOrderNum).collect(Collectors.toList());
		
		List<Integer> listChannelIds = listMerchantOrder.stream().map(JXCMTBsMerchantOrderExportBspDTO::getChannelId).collect(Collectors.toList());
		Map<Integer,String> mapMerchantChannels = listBsMerchantChannelName(listChannelIds);
		Map<String,JXCMTBsAddressDTO> mapBsAddress = listBsAddressByBsMerchantOrderCodes(listBserMerchantOrderCode);
		JXCMTBsAddressDTO bsAddress = null;
		String strAddress = "";
		for(JXCMTBsMerchantOrderExportBspDTO dto:listMerchantOrder){
			bsAddress = mapBsAddress.get(dto.getMerchantOrderNum());
			if(null != bsAddress){
				strAddress = "";
				if(!StringUtils.isEmpty(bsAddress.getProvinceName())){
					strAddress += bsAddress.getProvinceName();
				}
				if(!StringUtils.isEmpty(bsAddress.getCityName())){
					strAddress += bsAddress.getCityName();
				}
				if(!StringUtils.isEmpty(bsAddress.getAreaName())){
					strAddress += bsAddress.getAreaName();
				}
				strAddress += bsAddress.getAddress();
				dto.setAddress(strAddress);
				dto.setContacts(bsAddress.getName());
				dto.setMobile(bsAddress.getMobile());
			}
			dto.setChannelName(mapMerchantChannels.get(jxcmtAttribInfoService.getMerchantChannelFromDbMerchantChannel(dto.getChannelId().byteValue())));
			if(!StringUtils.isEmpty(dto.getDispatchOrderStatus())){
				dto.setDispatchOrderStatus(convertMerchantOrderStatus(Byte.valueOf(dto.getDispatchOrderStatus())));
			}else{
				dto.setDispatchOrderStatus("");
			}
			//生成固定配置和选项配置
			listGhMerchantOrderConfig = mapGhMerchantOrderConfigs.get(dto.getMerchantOrderNum());
			if(null == listGhMerchantOrderConfig || listGhMerchantOrderConfig.isEmpty()){
				continue;
			}
			String optionConfigDesc = "";
			String fastenConfigDesc = "";
			for(JXCMTGhMerchantOrderConfig oConfig:listGhMerchantOrderConfig){
				attribInfo = jxcmtAttribInfoService.getAttribInfoById(oConfig.getAttribInfoId());
				if(null == attribInfo){
					continue;
				}
				if(oConfig.getStrOption().equals("O")){
					optionConfigDesc += attribInfo.getComment();
					optionConfigDesc += ":";
					optionConfigDesc += attribInfo.getName();
					optionConfigDesc += "/";
				}else if(oConfig.getStrOption().equals("F")){
					fastenConfigDesc += attribInfo.getComment();
					fastenConfigDesc += ":";
					fastenConfigDesc += attribInfo.getName();
					fastenConfigDesc += "/";
				}
			}
			dto.setFastenConfigDesc(fastenConfigDesc);
			dto.setOptionConfigDesc(optionConfigDesc);
		}
		return listMerchantOrder;
	}
	
	public String convertMerchantOrderStatus(Byte merchantOrderStatus){
		String result = "";
		if(merchantOrderStatus.equals(MerchantOrderStatusEnum.ORDER_ALREADY_COUNTERMAND.getCode())){
			result = MerchantOrderStatusEnum.ORDER_ALREADY_COUNTERMAND.getName();
		}else if(merchantOrderStatus.equals(MerchantOrderStatusEnum.ORDER_WAIT_CHECK.getCode())){
			result = MerchantOrderStatusEnum.ORDER_WAIT_CHECK.getName();
		}else if(merchantOrderStatus.equals(MerchantOrderStatusEnum.ORDER_WAIT_SHIPMENTS.getCode())){
			result = MerchantOrderStatusEnum.ORDER_WAIT_SHIPMENTS.getName();
		}else if(merchantOrderStatus.equals(MerchantOrderStatusEnum.ORDER_WAIT_RECEIVE.getCode())){
			result = MerchantOrderStatusEnum.ORDER_WAIT_RECEIVE.getName();
		}else if(merchantOrderStatus.equals(MerchantOrderStatusEnum.ORDER_ALREADY_FINISH.getCode())){
			result = MerchantOrderStatusEnum.ORDER_ALREADY_FINISH.getName();
		}else if(merchantOrderStatus.equals(MerchantOrderStatusEnum.ORDER_ALREADY_INVALID.getCode())){
			result = MerchantOrderStatusEnum.ORDER_ALREADY_INVALID.getName();
		}else if(merchantOrderStatus.equals(MerchantOrderStatusEnum.ORDER_WAIT_DISPATCH.getCode())){
			result = MerchantOrderStatusEnum.ORDER_WAIT_DISPATCH.getName();
		}
		return result;
	}
	
	
	
	public Page<JXCMTBsMerchantOrderDTO> pageBsMerchantOrderJXC(RpcPagination<JXCMTBsMerchantOrderDTO> pagination){
		JXCMTBsMerchantOrder condition = new JXCMTBsMerchantOrder();
		condition.setMerchantCode(pagination.getCondition().getMerchantCode());
		if(!StringUtils.isEmpty(pagination.getCondition().getStatus())){
			condition.setStatus(Byte.valueOf(pagination.getCondition().getStatus()));
		}
		if(!StringUtils.isEmpty(pagination.getCondition().getSignStatus())){
			condition.setSignStatus(Byte.valueOf(pagination.getCondition().getSignStatus()));
		}
		condition.setOrderTimeStart(JxcUtils.getDateFromString(pagination.getCondition().getOrderTimeStart()));
		condition.setOrderTimeEnd(JxcUtils.getDateFromString(pagination.getCondition().getOrderTimeEnd()));
		condition.setMoOrderCode(pagination.getCondition().getMoOrderCode());
		condition.setOrderNumber(pagination.getCondition().getMerchantOrder());
		condition.setProductName(pagination.getCondition().getProductName());
		condition.setMaterialCode(pagination.getCondition().getOrderMaterialName());
		condition.setMaterialName(pagination.getCondition().getOrderMaterialName());
		PageHelper.startPage(pagination.getPageNum(), pagination.getPageSize());
		Page<JXCMTBsMerchantOrderDTO> pageMerchantOrder = jxcmtBsMerchantOrderMapper.pageBsMerchantOrderJXC(condition);
		if(null == pageMerchantOrder){
			return null;
		}
		for(JXCMTBsMerchantOrderDTO merchantOrder:pageMerchantOrder){
			if(StringUtils.isEmpty(merchantOrder.getMoOrderCode())){
				merchantOrder.setMoOrderCode(merchantOrder.getMerchantOrder());
			}
		}
		List<JXCMTBsMerchantOrderDTO> listMerchantOrder = pageMerchantOrder.getResult();
		if(null == listMerchantOrder || listMerchantOrder.isEmpty()){
			return pageMerchantOrder;
		}
		List<String> listBserMerchantOrderCode = listMerchantOrder.stream().map(JXCMTBsMerchantOrderDTO::getMerchantOrder).collect(Collectors.toList());
		Map<String,JXCMTBsAddressDTO> mapBsAddress = listBsAddressByBsMerchantOrderCodes(listBserMerchantOrderCode);
		Map<String,String> mapMotocyle = listMotorcyleFlagByBsMerchantOrderCodes(listBserMerchantOrderCode);
		int totalCheck = 0;
		int totalSend = 0;
		for(JXCMTBsMerchantOrderDTO dto:listMerchantOrder){
			dto.setBsAddressDto(mapBsAddress.get(dto.getMerchantOrder()));
			dto.setMotorcyleFlag(mapMotocyle.get(dto.getMerchantOrder()) == null?"N":mapMotocyle.get(dto.getMerchantOrder()));
			//进销存将待分配转成待发货状态
			if(!StringUtils.isEmpty(dto.getStatus())){
				if(dto.getStatus().equals(String.valueOf(MerchantOrderStatusEnum.ORDER_WAIT_DISPATCH.getCode()))){
					dto.setStatus(String.valueOf(MerchantOrderStatusEnum.ORDER_WAIT_SHIPMENTS.getCode()));
				}
			}
			totalCheck = (dto.getTotalCheck()==null?0:dto.getTotalCheck());
			totalSend = (dto.getTotalSends()==null?0:dto.getTotalSends());
			if(dto.getStatus().equals(String.valueOf(MerchantOrderStatusEnum.ORDER_ALREADY_INVALID.getCode()))){
				dto.setTotalOwes(0);
			}else{
				dto.setTotalOwes(totalCheck - totalSend);
			}
			//如果总订单号为空 补上子订单号
			if(StringUtils.isEmpty(dto.getMoOrderCode())){
				dto.setMoOrderCode(dto.getMerchantCode());
			}
		}
		return pageMerchantOrder;
	}
	
	private ExportMerchantOrder generatorExportMerchantOrderBsp(JXCMTSpMerchantOrderDTO merchantOrder)
	{
    	ExportMerchantOrder exportMerchantOrder = new ExportMerchantOrder();
    	exportMerchantOrder.setMerchantCode(merchantOrder.getMerchantCode());
    	exportMerchantOrder.setDispatchOrderCode(merchantOrder.getDispatchOrderCode());
    	exportMerchantOrder.setMerchantName(merchantOrder.getMerchantName());
    	exportMerchantOrder.setOrderNumber(merchantOrder.getMerchantOrder());
    	exportMerchantOrder.setMaterialCode(merchantOrder.getMaterialCode());
    	exportMerchantOrder.setMaterialName(merchantOrder.getMaterialName());
    	if(!StringUtils.isEmpty(merchantOrder.getStatus())){
    		exportMerchantOrder.setStatus(Byte.valueOf(merchantOrder.getStatus()));
    	}
    	exportMerchantOrder.setChannel(jxcmtAttribInfoService.getDbMerchantChannelFromAttribInfo(merchantOrder.getChannelId()));
    	exportMerchantOrder.setStartDate(JxcUtils.getDateFromString(merchantOrder.getOrderTimeStart()));
    	exportMerchantOrder.setEndDate(JxcUtils.getDateFromString(merchantOrder.getOrderTimeEnd()));
    	return exportMerchantOrder;
    } 

	
	private ExportMerchantOrder generatorExportMerchantOrder(JXCMTBsMerchantOrderDTO merchantOrder)
    {
    	ExportMerchantOrder exportMerchantOrder = new ExportMerchantOrder();
    	exportMerchantOrder.setMerchantCode(merchantOrder.getMerchantCode());
    	exportMerchantOrder.setMerchantName(merchantOrder.getMerchantName());
    	exportMerchantOrder.setMoMerchantOrder(merchantOrder.getMoOrderCode());
    	exportMerchantOrder.setOrderNumber(merchantOrder.getMerchantOrder());
    	exportMerchantOrder.setMaterialCode(merchantOrder.getMaterialCode());
    	if(!StringUtils.isEmpty(merchantOrder.getStatus())){
    		exportMerchantOrder.setStatus(Byte.valueOf(merchantOrder.getStatus()));
    	}
    	if(!StringUtils.isEmpty(merchantOrder.getSignStatus())){
    		exportMerchantOrder.setSignStatus(Byte.valueOf(merchantOrder.getSignStatus()));
    	}
    	exportMerchantOrder.setChannel(jxcmtAttribInfoService.getDbMerchantChannelFromAttribInfo(merchantOrder.getChannelId()));
    	exportMerchantOrder.setProductTypeId(jxcmtAttribInfoService.getDbProductTypeFromAttribInfo(merchantOrder.getProductTypeId()));
    	exportMerchantOrder.setProductCode(merchantOrder.getProductCode());
    	exportMerchantOrder.setProductName(merchantOrder.getProductName());
    	exportMerchantOrder.setMaterialCode(merchantOrder.getMaterialCode());
    	exportMerchantOrder.setMaterialName(merchantOrder.getMaterialName());
    	exportMerchantOrder.setStartDate(JxcUtils.getDateFromString(merchantOrder.getOrderTimeStart()));
    	exportMerchantOrder.setEndDate(JxcUtils.getDateFromString(merchantOrder.getOrderTimeEnd()));
    	exportMerchantOrder.setCheckStartDate(JxcUtils.getDateFromString(merchantOrder.getCheckTimeStart()));
    	exportMerchantOrder.setCheckEndDate(JxcUtils.getDateFromString(merchantOrder.getCheckTimeEnd()));
    	return exportMerchantOrder;
    } 
	
	public List<JXCMTBsMerchantOrderDTO> exportPurchaseOrder(JXCMTBsMerchantOrderDTO record){
		
		//发送kafka异步执行
		ExportMerchantOrder exportMerchantOrder = generatorExportMerchantOrder(record);
		sendKafkaService.notifyMerchantOrderExport(record.getConsumer(), Constants.TASK_CFG_ID_JXC_ORDER_PURCHASE, exportMerchantOrder);
		
		//同步执行
		JXCMTBsMerchantOrder condition = new JXCMTBsMerchantOrder();
		condition.setMerchantCode(record.getMerchantCode());
		if(record.getStatus()!=null) {
			condition.setStatus(Byte.valueOf(record.getStatus()));
		}
		if(record.getSignStatus()!=null) {
			condition.setSignStatus(Byte.valueOf(record.getSignStatus()));
		}
		condition.setOrderTimeStart(JxcUtils.getDateFromString(record.getOrderTimeStart()));
		condition.setOrderTimeEnd(JxcUtils.getDateFromString(record.getOrderTimeEnd()));
		condition.setMoOrderCode(record.getMoOrderCode());
		condition.setOrderNumber(record.getMerchantOrder());
		condition.setProductCode(record.getProductCode());
		condition.setProductName(record.getProductName());
		condition.setMaterialCode(record.getMaterialCode());
		condition.setMaterialName(record.getMaterialName());
		List<JXCMTBsMerchantOrderDTO> jxcmtBsMerchantOrderDTOList = jxcmtBsMerchantOrderMapper.exportBsMerchantOrderJXC(condition);
		if(null == jxcmtBsMerchantOrderDTOList|| jxcmtBsMerchantOrderDTOList.isEmpty()){
			return null;
		}

		for(JXCMTBsMerchantOrderDTO merchantOrder:jxcmtBsMerchantOrderDTOList){
			if(StringUtils.isEmpty(merchantOrder.getMoOrderCode())){
				merchantOrder.setMoOrderCode(merchantOrder.getSpaPurchaseCode());
			}
		}

		List<String> listBserMerchantOrderCode = jxcmtBsMerchantOrderDTOList.stream().map(JXCMTBsMerchantOrderDTO::getMerchantOrder).collect(Collectors.toList());
		Map<String,JXCMTBsAddressDTO> mapBsAddress = listBsAddressByBsMerchantOrderCodes(listBserMerchantOrderCode);
		Map<String,String> mapMotocyle = listMotorcyleFlagByBsMerchantOrderCodes(listBserMerchantOrderCode);
		for(JXCMTBsMerchantOrderDTO dto:jxcmtBsMerchantOrderDTOList){
			dto.setBsAddressDto(mapBsAddress.get(dto.getMerchantOrder()));
			dto.setMotorcyleFlag(mapMotocyle.get(dto.getMerchantOrder()) == null?"N":mapMotocyle.get(dto.getMerchantOrder()));
			//进销存页面将待分配转成待发货状态
			if(dto.getStatus().equals(String.valueOf(MerchantOrderStatusEnum.ORDER_WAIT_DISPATCH.getCode()))){
				dto.setStatus(String.valueOf(MerchantOrderStatusEnum.ORDER_WAIT_SHIPMENTS.getCode()));
			}
		}
		return jxcmtBsMerchantOrderDTOList;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
	public Integer rebackBsOrder(JXCMTBsMerchantOrderDTO record) throws RpcServiceException{
		Integer result = 0;
		JXCMTBsMerchantOrder bsMerchantOrder = this.getBsMerchantOrderByBsMerchantOrder(record.getMerchantOrder());
		if(null == bsMerchantOrder){
			return result;
		}
		JXCMTEcMerchantOrder ecMerchantOrder = this.getEcMerchantOrderByBsMerchantOrder(record.getMerchantOrder());
		if(null == ecMerchantOrder){
			return result;
		}
		bsMerchantOrder.setStatus(MerchantOrderStatusEnum.ORDER_ALREADY_COUNTERMAND.getCode());
		bsMerchantOrder.setRebackReason(record.getRebackReason());
		ecMerchantOrder.setRebackReason(record.getRebackReason());
		bsMerchantOrder.setUpdatedBy(record.getConsumer());
		bsMerchantOrder.setUpdatedDate(JxcUtils.getNowDate());
		ecMerchantOrder.setStatus(MerchantOrderStatusEnum.ORDER_ALREADY_COUNTERMAND.getName());
		ecMerchantOrder.setUpdatedBy(record.getConsumer());
		ecMerchantOrder.setUpdatedDate(JxcUtils.getNowDate());
		try{
			this.updateBsMerchantOrder(bsMerchantOrder);
			this.updateEcMerchantOrder(ecMerchantOrder);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
            throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR.getDescrible());
		}
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
	public Integer rebackSpOrder(JXCMTBsMerchantOrderRebackDTO record) throws RpcServiceException{
		Integer result = 0;
		JXCMTBsMerchantOrder bsMerchantOrder = this.getBsMerchantOrderByBsMerchantOrder(record.getMerchantOrder());
		if(null == bsMerchantOrder){
			return result;
		}
		JXCMTEcMerchantOrder ecMerchantOrder = this.getEcMerchantOrderByBsMerchantOrder(record.getMerchantOrder());
		if(null == ecMerchantOrder){
			return result;
		}
		if(!bsMerchantOrder.getStatus().equals(MerchantOrderStatusEnum.ORDER_WAIT_DISPATCH.getCode())){
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_MERCHANT_ORDER_NOT_WAIT_DISPATCH);
		}
		bsMerchantOrder.setStatus(MerchantOrderStatusEnum.ORDER_WAIT_CHECK.getCode());
		bsMerchantOrder.setRebackReason(record.getRebackReason());
		ecMerchantOrder.setRebackReason(record.getRebackReason());
		bsMerchantOrder.setUpdatedBy(record.getConsumer());
		bsMerchantOrder.setUpdatedDate(JxcUtils.getNowDate());
		ecMerchantOrder.setStatus(MerchantOrderStatusEnum.ORDER_WAIT_CHECK.getName());
		ecMerchantOrder.setUpdatedBy(record.getConsumer());
		ecMerchantOrder.setUpdatedDate(JxcUtils.getNowDate());
		try{
			this.updateBsMerchantOrder(bsMerchantOrder);
			this.updateEcMerchantOrder(ecMerchantOrder);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
            throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR.getDescrible());
		}
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
	public Integer recallSpOrder(JXCMTBsMerchantOrderRecallDTO record) throws RpcServiceException{
		Integer result = 0;
		JXCMTBsMerchantOrder bsMerchantOrder = this.getBsMerchantOrderByBsMerchantOrder(record.getMerchantOrder());
		if(null == bsMerchantOrder){
			return result;
		}
		JXCMTEcMerchantOrder ecMerchantOrder = this.getEcMerchantOrderByBsMerchantOrder(record.getMerchantOrder());
		if(null == ecMerchantOrder){
			return result;
		}
		if(!bsMerchantOrder.getStatus().equals(MerchantOrderStatusEnum.ORDER_WAIT_SHIPMENTS.getCode())){
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_MERCHANT_ORDER_NOT_WAIT_SHIPMENTS);
		}
		bsMerchantOrder.setStatus(MerchantOrderStatusEnum.ORDER_WAIT_DISPATCH.getCode());
		bsMerchantOrder.setRecallReason(record.getRecallReason());
		ecMerchantOrder.setRecallReason(record.getRecallReason());
		bsMerchantOrder.setUpdatedBy(record.getConsumer());
		bsMerchantOrder.setUpdatedDate(JxcUtils.getNowDate());
		ecMerchantOrder.setStatus(MerchantOrderStatusEnum.ORDER_WAIT_DISPATCH.getName());
		ecMerchantOrder.setUpdatedBy(record.getConsumer());
		ecMerchantOrder.setUpdatedDate(JxcUtils.getNowDate());
		List<JXCMTBsMerchantOrderVehicle> listVehicles = this.listBsMerchantOrderVehicleByBsMerchantOrderCode(record.getMerchantOrder());
		
		try{
			for(JXCMTBsMerchantOrderVehicle vehicle:listVehicles){
				if(!StringUtils.isEmpty(vehicle.getDispatchOrderCode())){
					jxcmtOrderDispatchService.deleteDispatchOrderByOrderCode(vehicle.getDispatchOrderCode());
					vehicle.setDispatchOrderCode(null);
				}
				this.updateBsMerchantOrderVehicle(vehicle);
			}
			this.updateBsMerchantOrder(bsMerchantOrder);
			this.updateEcMerchantOrder(ecMerchantOrder);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
            throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR.getDescrible());
		}
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
	public Integer finishSpOrder(JXCMTBsMerchantOrderFinishDTO record) throws RpcServiceException{
		Integer result = 0;
		JXCMTBsMerchantOrder bsMerchantOrder = this.getBsMerchantOrderByBsMerchantOrder(record.getMerchantOrder());
		if(null == bsMerchantOrder){
			return result;
		}
		JXCMTEcMerchantOrder ecMerchantOrder = this.getEcMerchantOrderByBsMerchantOrder(record.getMerchantOrder());
		if(null == ecMerchantOrder){
			return result;
		}
		if(!bsMerchantOrder.getStatus().equals(MerchantOrderStatusEnum.ORDER_WAIT_SHIPMENTS.getCode()) &&
				!bsMerchantOrder.getStatus().equals(MerchantOrderStatusEnum.ORDER_WAIT_RECEIVE.getCode())){
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_MERCHANT_ORDER_NOT_WAIT_SHIPMENTSORPART);
		}
		bsMerchantOrder.setStatus(MerchantOrderStatusEnum.ORDER_ALREADY_INVALID.getCode());
		bsMerchantOrder.setFinishReason(record.getFinishReason());
		ecMerchantOrder.setFinishReason(record.getFinishReason());
		bsMerchantOrder.setUpdatedBy(record.getConsumer());
		bsMerchantOrder.setUpdatedDate(JxcUtils.getNowDate());
		ecMerchantOrder.setStatus(MerchantOrderStatusEnum.ORDER_ALREADY_INVALID.getName());
		ecMerchantOrder.setUpdatedBy(record.getConsumer());
		ecMerchantOrder.setUpdatedDate(JxcUtils.getNowDate());
		List<JXCMTBsMerchantOrderVehicle> listVehicles = this.listBsMerchantOrderVehicleByBsMerchantOrderCode(record.getMerchantOrder());
		try{
			for(JXCMTBsMerchantOrderVehicle vehicle:listVehicles){
				if(!StringUtils.isEmpty(vehicle.getDispatchOrderCode())){
					jxcmtOrderDispatchService.updateOrderInfoStatu(DispatchOrderStatusEnum.STATUS_OV.getValue(), vehicle.getDispatchOrderCode());
				}
			}
			this.updateBsMerchantOrder(bsMerchantOrder);
			this.updateEcMerchantOrder(ecMerchantOrder);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
            throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR.getDescrible());
		}
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
	public Integer updateBsOrder(JXCMTBsOrderCheckDTO record) throws RpcServiceException{
		Integer result = 0;
		JXCMTBsMerchantOrder bsMerchantOrder = this.getBsMerchantOrderByBsMerchantOrder(record.getMerchantOrder());
		if(null == bsMerchantOrder){
			return result;
		}
		if(!bsMerchantOrder.getStatus().equals(MerchantOrderStatusEnum.ORDER_WAIT_DISPATCH.getCode())){
			logger.info("JXCMTOrderService::updateBsOrder 订单为非待分配状态不能修改");
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_MERCHANT_ORDER_NOT_WAIT_DISPATCH);
		}
		JXCMTEcMerchantOrder ecMerchantOrder = this.getEcMerchantOrderByBsMerchantOrder(record.getMerchantOrder());
		JXCMTBsMerchantOrderDetail bsMerchantOrderDetail = this.getBsMerchantOrderDetailByMerchantOrder(record.getMerchantOrder());
		JXCMTProductDetail bsProductDetail = jxcmtProductService.getBsProductDetailByProductCode(bsMerchantOrderDetail.getProductCode());
		bsMerchantOrder.setStatus(MerchantOrderStatusEnum.ORDER_WAIT_DISPATCH.getCode());
		bsMerchantOrder.setModelDevice(record.getModelDevice());
		bsMerchantOrder.setCheckRemark(record.getCheckRemark());
		bsMerchantOrder.setUrlDispatchBills(record.getUrlDispatchBills());
		bsMerchantOrder.setTotalCheck(record.getTotalCheck());
		bsMerchantOrder.setUpdatedBy(record.getConsumer());
		bsMerchantOrder.setUpdatedDate(JxcUtils.getNowDate());
		bsProductDetail.setMaterialCode(record.getCheckMaterialCode());
		bsProductDetail.setMaterialName(record.getCheckMaterialName());
		bsProductDetail.setUpdatedBy(record.getConsumer());
		bsProductDetail.setUpdatedDate(JxcUtils.getNowDate());
		bsMerchantOrderDetail.setInsure(record.getInsure());
		bsMerchantOrderDetail.setSubjectId(record.getSubjectId());
		bsMerchantOrderDetail.setCheckBy(record.getConsumer());
		bsMerchantOrderDetail.setCheckQuantity(record.getTotalCheck());
		bsMerchantOrderDetail.setCheckTime(JxcUtils.getNowDate());
		bsMerchantOrderDetail.setUpdatedBy(record.getConsumer());
		bsMerchantOrderDetail.setUpdatedDate(JxcUtils.getNowDate());
		ecMerchantOrder.setStatus(MerchantOrderStatusEnum.ORDER_WAIT_DISPATCH.getName());
		ecMerchantOrder.setCheckRemark(record.getCheckRemark());
		ecMerchantOrder.setCheckQuantity(record.getTotalCheck());
		ecMerchantOrder.setMaterialCode(record.getCheckMaterialCode());
		ecMerchantOrder.setMaterialName(record.getCheckMaterialName());
		ecMerchantOrder.setCheckTime(JxcUtils.getStringFromDate(JxcUtils.getNowDate()));
		ecMerchantOrder.setCheckRemark(record.getCheckRemark());
		ecMerchantOrder.setUpdatedBy(record.getConsumer());
		ecMerchantOrder.setUpdatedDate(JxcUtils.getNowDate());
		try{
			this.updateBsMerchantOrder(bsMerchantOrder);
			this.updateEcMerchantOrder(ecMerchantOrder);
			this.updateBsMerchantOrderDetail(bsMerchantOrderDetail);
			jxcmtProductService.updateProductDetail(bsProductDetail);
			if(null != record.getListOrderVehicleDto() && !record.getListOrderVehicleDto().isEmpty()){
				
				if(record.getListOrderVehicleDto().size() == 1){
					JXCMTBsMerchantOrderVehicle orderVehicle = jxcmtBsMerchantOrderVehicleMapper.selectByPrimaryKey(record.getListOrderVehicleDto().get(0).getId());
					if(orderVehicle.getBsParentBrandId() == null){
						record.getListOrderVehicleDto().get(0).setBsCheckQuantity(record.getTotalCheck());
					}
				}
				
				JXCMTBsMerchantOrderVehicle vehicle = null;
				for(JXCMTBsMerchantOrderVehicleDTO dto:record.getListOrderVehicleDto()){
					vehicle = new JXCMTBsMerchantOrderVehicle();
					vehicle.setId(dto.getId());
					vehicle.setBsCheckQuantity(dto.getBsCheckQuantity());
					vehicle.setUpdatedBy(record.getConsumer());
					vehicle.setUpdatedDate(JxcUtils.getNowDate());
					this.updateBsMerchantOrderVehicleSelective(vehicle);
				}
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
            throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR.getDescrible());
		}
		return result;
		
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
	public Integer checkBsOrder(JXCMTBsOrderCheckDTO record) throws RpcServiceException{
		Integer result = 0;
		JXCMTBsMerchantOrder bsMerchantOrder = this.getBsMerchantOrderByBsMerchantOrder(record.getMerchantOrder());
		if(null == bsMerchantOrder){
			return result;
		}
		JXCMTEcMerchantOrder ecMerchantOrder = this.getEcMerchantOrderByBsMerchantOrder(record.getMerchantOrder());
		JXCMTBsMerchantOrderDetail bsMerchantOrderDetail = this.getBsMerchantOrderDetailByMerchantOrder(record.getMerchantOrder());
		JXCMTProductDetail bsProductDetail = jxcmtProductService.getBsProductDetailByProductCode(bsMerchantOrderDetail.getProductCode());
		bsMerchantOrder.setStatus(MerchantOrderStatusEnum.ORDER_WAIT_DISPATCH.getCode());
		bsMerchantOrder.setModelDevice(record.getModelDevice());
		bsMerchantOrder.setCheckRemark(record.getCheckRemark());
		bsMerchantOrder.setUrlDispatchBills(record.getUrlDispatchBills());
		bsMerchantOrder.setTotalCheck(record.getTotalCheck());
		bsMerchantOrder.setUpdatedBy(record.getConsumer());
		bsMerchantOrder.setUpdatedDate(JxcUtils.getNowDate());
		bsProductDetail.setMaterialCode(record.getCheckMaterialCode());
		bsProductDetail.setMaterialName(record.getCheckMaterialName());
		bsProductDetail.setUpdatedBy(record.getConsumer());
		bsProductDetail.setUpdatedDate(JxcUtils.getNowDate());
		bsMerchantOrderDetail.setInsure(record.getInsure());
		bsMerchantOrderDetail.setSubjectId(record.getSubjectId());
		bsMerchantOrderDetail.setCheckBy(record.getConsumer());
		bsMerchantOrderDetail.setCheckQuantity(record.getTotalCheck());
		bsMerchantOrderDetail.setCheckTime(JxcUtils.getNowDate());
		bsMerchantOrderDetail.setUpdatedBy(record.getConsumer());
		bsMerchantOrderDetail.setUpdatedDate(JxcUtils.getNowDate());
		ecMerchantOrder.setStatus(MerchantOrderStatusEnum.ORDER_WAIT_DISPATCH.getName());
		ecMerchantOrder.setCheckRemark(record.getCheckRemark());
		ecMerchantOrder.setCheckQuantity(record.getTotalCheck());
		ecMerchantOrder.setMaterialCode(record.getCheckMaterialCode());
		ecMerchantOrder.setMaterialName(record.getCheckMaterialName());
		ecMerchantOrder.setCheckTime(JxcUtils.getStringFromDate(JxcUtils.getNowDate()));
		ecMerchantOrder.setCheckRemark(record.getCheckRemark());
		ecMerchantOrder.setUpdatedBy(record.getConsumer());
		ecMerchantOrder.setUpdatedDate(JxcUtils.getNowDate());
		try{
			this.updateBsMerchantOrder(bsMerchantOrder);
			this.updateEcMerchantOrder(ecMerchantOrder);
			this.updateBsMerchantOrderDetail(bsMerchantOrderDetail);
			jxcmtProductService.updateProductDetail(bsProductDetail);
			if(null != record.getListOrderVehicleDto() && !record.getListOrderVehicleDto().isEmpty()){	
				if(record.getListOrderVehicleDto().size() == 1){
					JXCMTBsMerchantOrderVehicle orderVehicle = jxcmtBsMerchantOrderVehicleMapper.selectByPrimaryKey(record.getListOrderVehicleDto().get(0).getId());
					if(orderVehicle.getBsParentBrandId() == null){
						record.getListOrderVehicleDto().get(0).setBsCheckQuantity(record.getTotalCheck());
					}
				}
				
				JXCMTBsMerchantOrderVehicle vehicle = null;
				for(JXCMTBsMerchantOrderVehicleDTO dto:record.getListOrderVehicleDto()){
					vehicle = new JXCMTBsMerchantOrderVehicle();
					vehicle.setId(dto.getId());
					vehicle.setBsCheckQuantity(dto.getBsCheckQuantity());
					vehicle.setUpdatedBy(record.getConsumer());
					vehicle.setUpdatedDate(JxcUtils.getNowDate());
					this.updateBsMerchantOrderVehicleSelective(vehicle);
				}
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
            throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR.getDescrible());
		}
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
	public JXCMTMerchantOrderDTO getJxcMerchantOrder(JXCMTMerchantOrderDTO record){
		JXCMTMerchantOrderDTO dtoResult = null;
		JXCMTMerchantOrder merchantOrder = getJxcmtMerchantOrderByMoMerchantOrder(record.getMoOrderCode());
		if(null == merchantOrder){
			return dtoResult;
		}
		JXCMTBsAddressDTO bsAddressDto = JXCMTBsAddressRpcConvert.convertBean(jxcmtBsAddressService.getAddressById(merchantOrder.getMoAddressId()));
		List<JXCMTMerchantOrderMaterialDTO>  listMerchantOrderMaterials = JXCMTMerchantOrderMaterialRpcConvert.convertListBean(this.listJxcmtMerchantOrderMaterialByMoOrderCode(record.getMoOrderCode()));
		List<JXCMTMerchantOrderVehicleDTO> listMerchantOrderVehicles = JXCMTMerchantOrderVehicleRpcConvert.convertListBean(this.listJxcmtMerchantOrderVehicleByMoOrderCode(record.getMoOrderCode()));
		dtoResult = JXCMTMerchantOrderRpcConvert.convertBean(merchantOrder);
		dtoResult.setBsAddress(bsAddressDto);
		dtoResult.setListMerchantOrderMaterials(listMerchantOrderMaterials);
		dtoResult.setListMerchantOrderVehicles(listMerchantOrderVehicles);
		dtoResult.setSupportUpdate("Y");
		List<JXCMTBsMerchantOrder> listBsMerchantOrders = this.listBsMerchantOrderByMoOrderCode(record.getMoOrderCode());
		for(JXCMTBsMerchantOrder bsMerchantOrder:listBsMerchantOrders){
			if(bsMerchantOrder.getStatus().equals(MerchantOrderStatusEnum.ORDER_WAIT_CHECK.getCode())){
				continue;
			}
			if(bsMerchantOrder.getStatus().equals(MerchantOrderStatusEnum.ORDER_ALREADY_COUNTERMAND.getCode())){
				continue;
			}
			dtoResult.setSupportUpdate("N");
			break;
		}
		return dtoResult;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
	public Integer cancelJxcMerchantOrder(JXCMTMerchantOrderDTO record) throws RpcServiceException{
		List<JXCMTBsMerchantOrder> listBsMerchantOrders = this.listBsMerchantOrderByMoOrderCode(record.getMoOrderCode());
		if(null == listBsMerchantOrders || listBsMerchantOrders.isEmpty()){
			return 0;
		}
		for(JXCMTBsMerchantOrder bsMerchantOrder:listBsMerchantOrders){
			if(bsMerchantOrder.getStatus().equals(MerchantOrderStatusEnum.ORDER_WAIT_CHECK.getCode())){
				continue;
			}
			if(bsMerchantOrder.getStatus().equals(MerchantOrderStatusEnum.ORDER_ALREADY_COUNTERMAND.getCode())){
				continue;
			}
			logger.info("JXCMTOrderDispatchService::cancelJxcMerchantOrder not wait check");
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_MERCHANT_ORDER_NOT_WAIT_CHECK);
		}
		if(!record.getMoOrderCode().startsWith("JXC")){
			logger.info("JXCMTOrderDispatchService::cancelJxcMerchantOrder not jxc");
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_MERCHANT_ORDER_NOT_JXC);
		}
		try{
			JXCMTEcMerchantOrder ecMerchantOrder = null;
			for(JXCMTBsMerchantOrder order:listBsMerchantOrders){
				ecMerchantOrder = this.getEcMerchantOrderByBsMerchantOrder(order.getOrderNumber());
				order.setStatus(MerchantOrderStatusEnum.ORDER_ALREADY_CANCEL.getCode());
				order.setUpdatedBy(record.getConsumer());
				order.setUpdatedDate(JxcUtils.getNowDate());
				ecMerchantOrder.setStatus("已取消");
				ecMerchantOrder.setUpdatedBy(record.getConsumer());
				ecMerchantOrder.setUpdatedDate(JxcUtils.getNowDate());
				this.updateBsMerchantOrder(order);
				this.updateEcMerchantOrder(ecMerchantOrder);
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
            throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR.getDescrible());
		}
		return 0;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
	public Integer updateJxcMerchantOrder(JXCMTMerchantOrderDTO record) throws RpcServiceException{
		List<JXCMTBsMerchantOrder> listBsMerchantOrders = this.listBsMerchantOrderByMoOrderCode(record.getMoOrderCode());
		if(null == listBsMerchantOrders || listBsMerchantOrders.isEmpty()){
			return 0;
		}
		for(JXCMTBsMerchantOrder bsMerchantOrder:listBsMerchantOrders){
			if(bsMerchantOrder.getStatus().equals(MerchantOrderStatusEnum.ORDER_WAIT_CHECK.getCode())){
				continue;
			}
			if(bsMerchantOrder.getStatus().equals(MerchantOrderStatusEnum.ORDER_ALREADY_COUNTERMAND.getCode())){
				continue;
			}
			logger.info("JXCMTOrderDispatchService::cancelJxcMerchantOrder not wait check");
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_MERCHANT_ORDER_NOT_WAIT_CHECK);
		}
		
		JXCMTMerchantOrder merchantOrder = getJxcmtMerchantOrderByMoMerchantOrder(record.getMoOrderCode());
		
		List<JXCMTMerchantOrderMaterial> listMerchantOrderMaterial = this.listJxcmtMerchantOrderMaterialByMoOrderCode(record.getMoOrderCode());
		List<JXCMTMerchantOrderVehicle>	listMerchantOrderVehicle = this.listJxcmtMerchantOrderVehicleByMoOrderCode(record.getMoOrderCode());
		JXCMTBsDealerUserInfo dealerUserInfo = jxcmtUserInfoService.getBsDealerUserInfo(record.getMoMerchantCode());
		JXCMTBsAddress address = null;
		if(record.getBsAddress().getId() != null){
			jxcmtBsAddressService.updateAddressById(record.getBsAddress());
			address = jxcmtBsAddressService.getAddressById(record.getBsAddress().getId());
		}else{
			jxcmtBsAddressService.addAddress(record.getBsAddress());
			address = jxcmtBsAddressService.getAddressByName(record.getBsAddress());
		}
		merchantOrder.setMoAddressId(address.getId().intValue());
		merchantOrder.setMoHopeTime(JxcUtils.getDateFromString(record.getMoHopeTime()));
		merchantOrder.setMoPrice(record.getMoPrice());
		merchantOrder.setMoTotal(record.getMoTotal());
		merchantOrder.setMoRemark(record.getMoRemark());
		try{
			//修改订单订购数量 以及订单物料订购数量
			JXCMTBsMerchantOrderDetail bsMerchantOrderDetail = null;
			JXCMTEcMerchantOrder ecMerchantOrder = null;
			JXCMTProduct bsProduct = null;
			JXCMTProductDetail bsProductDetail = null;	
			JXCMTBsLogistics jxcmtBsLogistics = null;
			List<JXCMTBsMerchantOrderVehicle> listBsMerchantOrderVehicles = null;
			for(JXCMTBsMerchantOrder bsMerchantOrder:listBsMerchantOrders){
				jxcmtBsLogistics = jxcmtBsLogisticsService.getBsLogisticsByBsMerchantOrderCode(bsMerchantOrder.getOrderNumber());
				if(null != jxcmtBsLogistics){
					jxcmtBsLogistics.setReceiveId(address.getId());
					jxcmtBsLogisticsService.updateBsLogisticsSeletive(jxcmtBsLogistics);
				}
				bsMerchantOrderDetail = this.getBsMerchantOrderDetailByMerchantOrder(bsMerchantOrder.getOrderNumber());
				ecMerchantOrder = this.getEcMerchantOrderByBsMerchantOrder(bsMerchantOrder.getOrderNumber());
				bsProductDetail = jxcmtProductService.getBsProductDetailByProductCode(bsMerchantOrderDetail.getProductCode());
				listBsMerchantOrderVehicles =this.listBsMerchantOrderVehicleByBsMerchantOrderCode(bsMerchantOrder.getOrderNumber());
				for(JXCMTBsMerchantOrderVehicle vehicle:listBsMerchantOrderVehicles){
					this.deleteBsMerchantOrderVehicleById(vehicle.getId());
				}
				
				for(JXCMTMerchantOrderMaterialDTO orderMaterial:record.getListMerchantOrderMaterials()){
					if(orderMaterial.getMoMaterialCode().equals(bsProductDetail.getOrderMaterialCode())){
						bsMerchantOrder.setHopeTime(JxcUtils.getDateFromString(record.getMoHopeTime()));
						bsMerchantOrder.setTotalOrder(orderMaterial.getMoTotal());
						bsMerchantOrder.setTotalAmount(record.getMoPrice()*orderMaterial.getMoTotal()/orderMaterial.getMoPropQuantity());
						bsMerchantOrder.setProductTotal(record.getMoTotal());
						bsMerchantOrder.setRemarks(record.getMoRemark());
						bsMerchantOrder.setStatus(MerchantOrderStatusEnum.ORDER_WAIT_CHECK.getCode());
						bsMerchantOrderDetail.setOrderQuantity(orderMaterial.getMoTotal());
						ecMerchantOrder.setOrderQuantity(orderMaterial.getMoTotal());
						ecMerchantOrder.setStatus(MerchantOrderStatusEnum.ORDER_WAIT_CHECK.getName());
						ecMerchantOrder.setTotalAmount(record.getMoPrice()*orderMaterial.getMoTotal()/orderMaterial.getMoPropQuantity());
						this.updateBsMerchantOrder(bsMerchantOrder);
						this.updateBsMerchantOrderDetail(bsMerchantOrderDetail);
						this.updateEcMerchantOrder(ecMerchantOrder);
						
						//重新分配BsMerchantOrderVehicle
						listBsMerchantOrderVehicles = new ArrayList<>();
						if(null != record.getListMerchantOrderVehicles() && !record.getListMerchantOrderVehicles().isEmpty()){
							for(JXCMTMerchantOrderVehicleDTO vehicleDto:record.getListMerchantOrderVehicles()){
								listBsMerchantOrderVehicles.add(generatorBsMerchantOrderVehicle(bsMerchantOrder.getOrderNumber(), record.getConsumer(), vehicleDto));
							}
						}else{
							JXCMTMerchantOrderVehicleDTO vehicleDto = new JXCMTMerchantOrderVehicleDTO();
							if(null != orderMaterial.getMoPropQuantity() && orderMaterial.getMoPropQuantity() > 1){
								vehicleDto.setMoTotal(orderMaterial.getMoTotal()/orderMaterial.getMoPropQuantity());
							}else{
								vehicleDto.setMoTotal(orderMaterial.getMoTotal());
							}
							listBsMerchantOrderVehicles.add(generatorBsMerchantOrderVehicle(bsMerchantOrder.getOrderNumber(),record.getConsumer(),vehicleDto));
						}
						this.insertListJxcmtBsMerchantOrderVehicle(listBsMerchantOrderVehicles);
					}
				}
			}
			//删除车辆信息表 重新分配			
			if(null != listMerchantOrderVehicle && !listMerchantOrderVehicle.isEmpty()){
				for(JXCMTMerchantOrderVehicle vehicle:listMerchantOrderVehicle){
					this.deleteMtMerchantOrderVehicleById(vehicle.getId());
				}	
			}			
			List<JXCMTMerchantOrderVehicle> listMerchantOrderVehicleNew = JXCMTMerchantOrderVehicleRpcConvert.convertListDto(record.getListMerchantOrderVehicles());			
			if(null != listMerchantOrderVehicleNew && !listMerchantOrderVehicleNew.isEmpty()){
				for(JXCMTMerchantOrderVehicle vehicle:listMerchantOrderVehicleNew){
					if(vehicle.getMoParentBrandId() == null){
						continue;
					}
					vehicle.setMoOrderCode(record.getMoOrderCode());
					vehicle.setDeletedFlag("N");
					vehicle.setCreatedBy(record.getConsumer());
					vehicle.setCreatedDate(JxcUtils.getNowDate());
					vehicle.setUpdatedBy(record.getConsumer());
					vehicle.setUpdatedDate(JxcUtils.getNowDate());
				}
				this.insertListJxcmtMerchantOrderVehicle(listMerchantOrderVehicleNew);
			}
			//修改订单物料
			for(JXCMTMerchantOrderMaterial material:listMerchantOrderMaterial){
				for(JXCMTMerchantOrderMaterialDTO materialDto:record.getListMerchantOrderMaterials()){
					if(materialDto.getMoMaterialCode().equals(material.getMoMaterialCode())){
						material.setMoTotal(materialDto.getMoTotal());
						material.setUpdatedBy(record.getConsumer());
						material.setUpdatedDate(JxcUtils.getNowDate());
						jxcmtMerchantOrderMaterialMapper.updateByPrimaryKeySelective(material);
					}
				}
			}
			jxcmtMerchantOrderMapper.updateByPrimaryKeySelective(merchantOrder);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
            throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR.getDescrible());
		}
		return 0;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
	public Integer submitJxcMerchantOrder(JXCMTMerchantOrderSubmitDTO record)throws RpcServiceException {
		Integer result = 0;
		JXCMTBsDealerUserInfo dealerUserInfo = jxcmtUserInfoService.getBsDealerUserInfo(record.getMerchantCode());
		JXCMTBsAddress address = null;
		if(record.getAddressDto().getId() != null){
			jxcmtBsAddressService.updateAddressById(record.getAddressDto());
			address = jxcmtBsAddressService.getAddressById(record.getAddressDto().getId());
		}else{
			jxcmtBsAddressService.addAddress(record.getAddressDto());
			address = jxcmtBsAddressService.getAddressByName(record.getAddressDto());
		}
		List<JXCMTProductSplitDTO> listProductSplitDto = record.getListProductSplitDto();
		List<JXCMTMerchantOrder> listMerchantOrder = new ArrayList<>();
		List<JXCMTMerchantOrderMaterial> listMerchantOrderMaterial = new ArrayList<>();
		List<JXCMTMerchantOrderReflectMcode> listMerchantOrderReflectMcode = new ArrayList<>();
		List<JXCMTMerchantOrderVehicle> listMerchantOrderVehicle = new ArrayList<>();
		List<JXCMTBsMerchantOrder> listBsMerchantOrder = new ArrayList<>();
		List<JXCMTBsMerchantOrderDetail> listBsMerchantOrderDetail = new ArrayList<>();
		List<JXCMTBsMerchantOrderVehicle> listBsMerchantOrderVehicle = new ArrayList<>();
		List<JXCMTProduct> listBsProduct = new ArrayList<>();
		List<JXCMTProductDetail> listBsProductDetail = new ArrayList<>();
		List<JXCMTBsLogistics> listBsLogistics = new ArrayList<>();
		List<JXCMTEcMerchantOrder> listEcMerchantOrder = new ArrayList<>();
		JXCMTMerchantOrder jxcMerchantOrder = null;
		List<String> listProductCodes = listProductSplitDto.stream().map(JXCMTProductSplitDTO::getProductCode).collect(Collectors.toList());
		Map<String,JXCMTProductSplit> mapProductSplit = this.getProductSplitByListProductCodes(listProductCodes);
		Map<String,List<JXCMTProductSplitDetail>> mapProductSplitDetail = this.getProductSplitDetailByListProductCodes(listProductCodes);
	//	Map<String,List<JXCMTProductSplitPrice>> mapProductSplitPrice = this.getProductSplitPriceByListProductCodes(listProductCodes);
		Map<String,JXCMTMaterialInfo> mapCacheMaterialInfo = new HashMap<>();
		Map<Integer,String> mapCacheMerchantChannel = new HashMap<>();
		Map<Integer,String> mapCacheProductType = new HashMap<>();
		JXCMTMaterialInfo materialInfo = null;
		for(JXCMTProductSplitDTO dto:listProductSplitDto){
			jxcMerchantOrder = generatorJxcMerchantOrder(dealerUserInfo,JxcUtils.getDateYmdFromString(record.getHopeTime()),address.getId().intValue(),record.getConsumer(),dto);
			if(null == dto.getListProductSplitDetailDto() || dto.getListProductSplitDetailDto().isEmpty()){
				logger.info("JXCMTOrderService::submitJxcMerchantOrder !");
			}
			for(JXCMTProductSplitDetailDTO detailDto:dto.getListProductSplitDetailDto()){
				String bsMerchantOrderCode = Constants.BS_ORDER_PREFIX + JxcUtils.generatorOrderCode(snowflakeWorker);
				String bsProductCode = Constants.BS_PRODUCT_CODE_PREFIX + JxcUtils.generatorOrderCode(snowflakeWorker);
				materialInfo = getMaterialInfoByMaterialCode(detailDto.getMaterialCode(),mapCacheMaterialInfo);
				//总订单物料明细
				listMerchantOrderMaterial.add(generatorJxcMerchantOrderMaterial(jxcMerchantOrder.getMoOrderCode(),jxcMerchantOrder.getMoProductCode(),record.getConsumer(),detailDto));
				//构造商户子订单数据
				listBsMerchantOrder.add(generatorBsMerchantOrder(bsMerchantOrderCode, jxcMerchantOrder, detailDto));
				//构造商户订单详情数据
				listBsMerchantOrderDetail.add(generatorBsMerchantOrderDetail(bsMerchantOrderCode, bsProductCode, jxcMerchantOrder, detailDto));
				//生成商务订单产品快照信息
				listBsProduct.add(generatorBsProduct(bsProductCode, record.getConsumer(), mapProductSplit.get(detailDto.getProductCode()), materialInfo));
				//生成商务订单产品详情快照信息
				listBsProductDetail.add(generatorBsProductDetail(bsProductCode, record.getConsumer(), mapProductSplit.get(detailDto.getProductCode()), materialInfo, mapProductSplitDetail));
				//总订单和商务子订单的关系
				listMerchantOrderReflectMcode.add(generatorJxcmtMerchantOrderReflectMcode(jxcMerchantOrder.getMoOrderCode(), bsMerchantOrderCode, record.getConsumer()));
				//生成物流信息
				listBsLogistics.add(generatorBsLogistics(address.getId().intValue(), bsMerchantOrderCode, record.getConsumer()));
				listEcMerchantOrder.add(generatorEcMerchantOrder(dealerUserInfo, address, bsMerchantOrderCode, mapProductSplit.get(detailDto.getProductCode()), materialInfo, jxcMerchantOrder, detailDto, mapCacheMerchantChannel, mapCacheProductType));
				if(null != dto.getListVehicleDto() && !dto.getListVehicleDto().isEmpty()){
					for(JXCMTMerchantOrderVehicleDTO vehicleDto:dto.getListVehicleDto()){
						listBsMerchantOrderVehicle.add(generatorBsMerchantOrderVehicle(bsMerchantOrderCode, record.getConsumer(), vehicleDto));
					}
				}else{
					JXCMTMerchantOrderVehicleDTO vehicleDto = new JXCMTMerchantOrderVehicleDTO();
					if(null != detailDto.getPropQuantity() && detailDto.getPropQuantity() > 1){
						vehicleDto.setMoTotal(detailDto.getOrderTotal()/detailDto.getPropQuantity());
					}else{
						vehicleDto.setMoTotal(detailDto.getOrderTotal());
					}
					listBsMerchantOrderVehicle.add(generatorBsMerchantOrderVehicle(bsMerchantOrderCode,record.getConsumer(),vehicleDto));
				}
			}
			listMerchantOrder.add(jxcMerchantOrder);		
			if(null == dto.getListVehicleDto() || dto.getListVehicleDto().isEmpty()){
				continue;
			}
			//生成商户下单总订单与车辆信息关系的数据
			for(JXCMTMerchantOrderVehicleDTO vehicleDto:dto.getListVehicleDto()){
				listMerchantOrderVehicle.add(generatorJxcMerchantOrderVehicle(jxcMerchantOrder.getMoOrderCode(),record.getConsumer(),vehicleDto));
			}
			
		}
		try{
			//生成总订单
			this.insertListJxcmtMerchantOrder(listMerchantOrder);
			//生成总订单物料明细
			this.insertListJxcmtMerchantOrderMaterial(listMerchantOrderMaterial);
			//生成总订单和商务子订单的关系
			this.insertListJxcmtMerchantOrderReflectMcode(listMerchantOrderReflectMcode);
			//生成商户下单总订单与车辆信息关系
			this.insertListJxcmtMerchantOrderVehicle(listMerchantOrderVehicle);
			//生成商户子订单
			this.insertListJxcmtBsMerchantOrder(listBsMerchantOrder);
			//生成商户子订单详情
			this.insertListJxcmtBsMerchantOrderDetail(listBsMerchantOrderDetail);
			//生成商户子订单和车辆信息的关系
			this.insertListJxcmtBsMerchantOrderVehicle(listBsMerchantOrderVehicle);
			//生成订单导出的信息
			this.insertListJxcmtEcMerchantOrder(listEcMerchantOrder);
			//生成物流信息
			jxcmtBsLogisticsService.insertListJxcmtBsLogistics(listBsLogistics);
			//生成产品快照信息
			jxcmtProductService.insertListJxcmtProduct(listBsProduct);
			//生成产品快照详情信息
			jxcmtProductService.insertListJxcmtProductDetail(listBsProductDetail);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
            throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR.getDescrible());
		}
		return result;
	}
	
	
	
	private Map<String,Integer> listDispatchOrderSendNums(List<String> listDispatchOrderCodes){
		Map<String,Integer> mapResult = new HashMap<>();
		if(null == listDispatchOrderCodes || listDispatchOrderCodes.isEmpty()){
			return mapResult;
		}
		Map<String,Integer> mapDispatch = listDispatchOrderSendNumsDispatch(listDispatchOrderCodes);
		Map<String,Integer> mapDirect = listDispatchOrderSendNumsDirect(listDispatchOrderCodes);
		Integer count = null;
		for(String code:listDispatchOrderCodes){
			count = mapDispatch.get(code);
			if(null != count){
				mapResult.put(code, count);
				continue;
			}
			count = mapDirect.get(code);
			if(null != count){
				mapResult.put(code, count);
				continue;
			}
		}
		return mapResult;
	}
	
	private Map<String,List<JXCMTBsLogistics>> listBsLogisticsByDispatchOrderCodes(List<String> listDispatchOrderCodes){
		Map<String,List<JXCMTBsLogistics>> mapBsLogistics = new HashMap<>();
		List<JXCMTBsLogistics> listLogistics = null;
		JXCMTBsLogistics bsLogistics = null;
		List<JXCMTBsLogistics> listLogisticsDirect = jxcmtBsLogisticsService.listBsLogisticsByDispatchOrderCodes(listDispatchOrderCodes,Constants.LOGISTICS_TYPE_FIVE);
		List<JXCMTBsLogistics> listLogisticsDispatch = jxcmtBsLogisticsService.listBsLogisticsByDispatchOrderCodes(listDispatchOrderCodes,Constants.LOGISTICS_TYPE_FOUR);
		if(null != listLogisticsDirect){
			for(JXCMTBsLogistics logistics:listLogisticsDirect){
				listLogistics = mapBsLogistics.get(logistics.getServiceCode());
				if(null == listLogistics){
					listLogistics = new ArrayList<>();
					mapBsLogistics.put(logistics.getServiceCode(), listLogistics);
				}
				listLogistics.add(logistics);	
			}
		}
		if(null != listLogisticsDispatch){
			for(JXCMTBsLogistics logistics:listLogisticsDispatch){
				listLogistics = mapBsLogistics.get(logistics.getServiceCode());
				if(null == listLogistics){
					listLogistics = new ArrayList<>();
					mapBsLogistics.put(logistics.getServiceCode(), listLogistics);
				}
				listLogistics.add(logistics);	
			}
		}
		return mapBsLogistics;
	}
	
	private Map<String,Integer> listDispatchOrderSendNumsDispatch(List<String> listDispatchOrderCodes){
		Map<String,Integer> mapDispatch = new HashMap<>();
		if(null == listDispatchOrderCodes || listDispatchOrderCodes.isEmpty()){
			return mapDispatch;
		}
		List<JXCMTDispatchOrderNumCount> listDispatchOrderNumCount = jxcmtOrderDispatchService.selectOrderDetailCount(listDispatchOrderCodes);
		if(null == listDispatchOrderNumCount || listDispatchOrderNumCount.isEmpty()){
			return mapDispatch;
		}
		return listDispatchOrderNumCount.stream().collect(Collectors.toMap(JXCMTDispatchOrderNumCount::getDispatchOrderCode, JXCMTDispatchOrderNumCount::getSendNums));
	}
	
	private Map<String,Integer> listDispatchOrderSendNumsDirect(List<String> listDispatchOrderCodes){
		Map<String,Integer> mapDispatch = new HashMap<>();
		List<JXCMTBsLogistics> listLogistics = jxcmtBsLogisticsService.listBsLogisticsByDispatchOrderCodes(listDispatchOrderCodes,(byte)5);
		if(null == listLogistics || listLogistics.isEmpty()){
			return mapDispatch;
		}
		Integer mapValue = null;
		int logisticsNum = 0;
		for(JXCMTBsLogistics logistics:listLogistics){
			logisticsNum = ((logistics.getShipmentsQuantity()==null)?0:logistics.getShipmentsQuantity());
			mapValue = mapDispatch.get(logistics.getServiceCode());
			if(null != mapValue){
				logisticsNum += mapValue.intValue();
			}
			mapDispatch.put(logistics.getServiceCode(), logisticsNum);
		}
		return mapDispatch;	
	}
	
	public Map<Integer,String> listBsMerchantChannelName(List<Integer> listChannelIds){
		List<Integer> listIds = new ArrayList<>();
		for(Integer item:listChannelIds){
			if(null == item){
				continue;
			}
			listIds.add(jxcmtAttribInfoService.getMerchantChannelFromDbMerchantChannel(item.byteValue()));
		}
		return jxcmtAttribInfoService.listAttribNameByIds(listIds);
	}
	
	private Map<String,JXCMTBsAddressDTO> listBsAddressByBsMerchantOrderCodes(List<String> listBsMerchantOrderCodes){
		Map<String,JXCMTBsAddressDTO> mapResult = new HashMap<>();
		List<JXCMTBsLogistics> listLogistics = jxcmtBsLogisticsService.listBsLogisticsByBsMerchantOrderCodes(listBsMerchantOrderCodes);
		if(null == listLogistics || listLogistics.isEmpty()){
			return mapResult;
		}
		List<Long> listReceiveIds = listLogistics.stream().map(JXCMTBsLogistics::getReceiveId).collect(Collectors.toList());
		List<JXCMTBsAddressDTO> listBsAddress = jxcmtBsAddressService.listAddressByIds(listReceiveIds);
		Map<Integer, JXCMTBsAddressDTO> mapBsAddress = listBsAddress.stream().collect(Collectors.toMap(JXCMTBsAddressDTO::getId, a->a));
		for(JXCMTBsLogistics logistics:listLogistics){
			mapResult.put(logistics.getServiceCode(), mapBsAddress.get(logistics.getReceiveId().intValue()));
		}
		return mapResult;
	}
	
	private Map<String,String> listMotorcyleFlagByBsMerchantOrderCodes(List<String> listBsMerchantOrderCodes){
		Map<String,String> mapMotocyle = new HashMap<>();
		List<JXCMTBsMerchantOrderVehicle> listVehicles = listBsMerchantOrderVehicleByBsMerchantOrderCodes(listBsMerchantOrderCodes,true);
		if(null == listVehicles || listVehicles.isEmpty()){
			return mapMotocyle;
		}
		for(JXCMTBsMerchantOrderVehicle vehicle:listVehicles){
			mapMotocyle.put(vehicle.getMerchantOrder(), "Y");
		}
		return mapMotocyle;
	}
	
	public List<JXCMTBsMerchantOrderVehicle> listBsMerchantOrderVehicleByBsMerchantOrderCodes(List<String> listBsMerchantOrderCodes,boolean motorcyleFlag){
		Example example = new Example(JXCMTBsMerchantOrderVehicle.class);
		Criteria criteria = example.createCriteria();
		criteria.andIn("merchantOrder", listBsMerchantOrderCodes);
		if(motorcyleFlag){
			criteria.andNotEqualTo("bsParentBrandId", 0)
			.andNotEqualTo("bsSubBrandId", 0);
		}
		return jxcmtBsMerchantOrderVehicleMapper.selectByExample(example);
	}
	
	public List<JXCMTBsMerchantOrderVehicle> listBsMerchantOrderVehicleByBsMerchantOrderCode(String bsMerchantOrderCode){
		Example example = new Example(JXCMTBsMerchantOrderVehicle.class);
		example.createCriteria().andEqualTo("merchantOrder", bsMerchantOrderCode);
		return jxcmtBsMerchantOrderVehicleMapper.selectByExample(example);
	}
	
	public Integer deleteBsMerchantOrderVehicleById(Integer id){
		return jxcmtBsMerchantOrderVehicleMapper.deleteByPrimaryKey(id);
	}
	
	public Integer deleteMtMerchantOrderVehicleById(Integer id){
		return jxcmtMerchantOrderVehicleMapper.deleteByPrimaryKey(id);
	}
	
	public Integer updateBsMerchantOrderVehicleSelective(JXCMTBsMerchantOrderVehicle vehicle){
		return jxcmtBsMerchantOrderVehicleMapper.updateByPrimaryKeySelective(vehicle);
	}
	
	public Integer updateBsMerchantOrderVehicle(JXCMTBsMerchantOrderVehicle vehicle){
		return jxcmtBsMerchantOrderVehicleMapper.updateByPrimaryKey(vehicle);
	}
	
	
	public JXCMTBsMerchantOrderVehicle getBsMerchantOrderVehicleByDispatchOrder(String dispatchOrder){
		JXCMTBsMerchantOrderVehicle condition = new JXCMTBsMerchantOrderVehicle();
		condition.setDispatchOrderCode(dispatchOrder);
		condition.setDeletedFlag("N");
		return jxcmtBsMerchantOrderVehicleMapper.selectOne(condition);
	}
	
	public String getBsMerchantOrderCodeByDispatchOrder(String dispatchOrder){
		JXCMTBsMerchantOrderVehicle condition = new JXCMTBsMerchantOrderVehicle();
		condition.setDispatchOrderCode(dispatchOrder);
		condition.setDeletedFlag("N");
		condition = jxcmtBsMerchantOrderVehicleMapper.selectOne(condition);
		if(null == condition){
			return "";
		}
		return condition.getMerchantOrder();
	}
	
	//获取子订单审核后的物料信息
	private Map<String,JXCMTMaterialInfo> listMaterialInfoByMerchantOrder(List<String> listMerchantOrders){
		Map<String,JXCMTMaterialInfo> mapResult = new HashMap<>();
		List<JXCMTBsMerchantOrderDTO> listMerchantOrder = jxcmtBsMerchantOrderMapper.listMaterialInfoByMerchantOrder(listMerchantOrders);
		if(null == listMerchantOrder){
			return mapResult;
		}
		JXCMTMaterialInfo materialInfo = null;
		for(JXCMTBsMerchantOrderDTO dto:listMerchantOrder){
			materialInfo = new JXCMTMaterialInfo();
			materialInfo.setMaterialCode(dto.getMaterialCode());
			materialInfo.setMaterialName(dto.getMaterialName());
			mapResult.put(dto.getMerchantOrder(), materialInfo);
		}
		return mapResult;
	}
	
	private JXCMTMaterialInfo getMaterialInfoByMaterialCode(String materialCode,Map<String,JXCMTMaterialInfo> mapCacheMaterialInfo){
		JXCMTMaterialInfo result=null;
		if(null != mapCacheMaterialInfo){
			result = mapCacheMaterialInfo.get(materialCode);
		}
		if(null != result){
			return result;
		}
		result = jxcmtMaterialInfoService.getMaterialInfoByMaterialCode(materialCode);
		if(null == result){
			return result;
		}
		if(null != mapCacheMaterialInfo){
			mapCacheMaterialInfo.put(materialCode, result);
		}
		return result;
	}
	
	private Map<String,JXCMTProductSplit> getProductSplitByListProductCodes(List<String> listProductCodes){
		List<JXCMTProductSplit> listProductSplit = jxcmtProductSplitService.listProductSplitByProductCodes(listProductCodes);
		if(null == listProductSplit || listProductSplit.isEmpty()){
			return null;
		}
		return listProductSplit.stream().collect(Collectors.toMap(e->e.getProductCode(), e->e));
	}
	
	private Map<String,List<JXCMTProductSplitDetail>> getProductSplitDetailByListProductCodes(List<String> listProductCodes){
		List<JXCMTProductSplitDetail> listProductSplitDetail = jxcmtProductSplitService.listProductSplitDetailsByProductCodes(listProductCodes);
		if(null == listProductSplitDetail || listProductSplitDetail.isEmpty()){
			return null;
		}
		return listProductSplitDetail.stream().collect(Collectors.groupingBy(JXCMTProductSplitDetail::getProductCode));
	}
	
	private Map<String,List<JXCMTProductSplitPrice>> getProductSplitPriceByListProductCodes(List<String> listProductCodes){
		List<JXCMTProductSplitPrice> listProductSplitPrice = jxcmtProductSplitService.listProductSplitPriceByProductCodes(listProductCodes);
		if(null == listProductSplitPrice || listProductSplitPrice.isEmpty()){
			return null;
		}
		return listProductSplitPrice.stream().collect(Collectors.groupingBy(JXCMTProductSplitPrice::getProductCode));
	}
	
	private JXCMTMerchantOrder generatorJxcMerchantOrder(JXCMTBsDealerUserInfo dealerUserInfo,Date moHopeTime,Integer addressId,String consumer,JXCMTProductSplitDTO productSplitDto){
		String moOrderCode = Constants.JXC_ORDER_PREFIX + JxcUtils.generatorOrderCode(snowflakeWorker);
		JXCMTMerchantOrder jxcMerchantOrder = new JXCMTMerchantOrder();
		jxcMerchantOrder.setMoOrderCode(moOrderCode);
		jxcMerchantOrder.setMoMerchantCode(dealerUserInfo.getMerchantCode());
		jxcMerchantOrder.setMoMerchantName(dealerUserInfo.getMerchantName());
		jxcMerchantOrder.setMoProductCode(productSplitDto.getProductCode());
		jxcMerchantOrder.setMoProductName(productSplitDto.getProductName());
		jxcMerchantOrder.setMoProductPackage(productSplitDto.getPackageOne());
		jxcMerchantOrder.setMoProductServiceTime(productSplitDto.getServiceTime());
		jxcMerchantOrder.setMoTotal(productSplitDto.getTotal());
		jxcMerchantOrder.setMoPrice(productSplitDto.getUnitPrice());
		jxcMerchantOrder.setMoRemark(productSplitDto.getRemark());
		jxcMerchantOrder.setMoHopeTime(moHopeTime);
		jxcMerchantOrder.setMoAddressId(addressId);
		jxcMerchantOrder.setCreatedBy(consumer);
		jxcMerchantOrder.setUpdatedBy(consumer);
		jxcMerchantOrder.setCreatedDate(JxcUtils.getNowDate());
		jxcMerchantOrder.setUpdatedDate(JxcUtils.getNowDate());
		jxcMerchantOrder.setDeletedFlag("N");
		return jxcMerchantOrder;
	}
	
	private JXCMTMerchantOrderMaterial generatorJxcMerchantOrderMaterial(String moMerchantOrder,String moProductCode,String consumer,JXCMTProductSplitDetailDTO productSplitDetailDto){
		JXCMTMerchantOrderMaterial merchantOrderMaterial = new JXCMTMerchantOrderMaterial();
		merchantOrderMaterial.setMoOrderCode(moMerchantOrder);
		merchantOrderMaterial.setMoProductCode(moProductCode);
		merchantOrderMaterial.setMoMaterialCode(productSplitDetailDto.getMaterialCode());
		merchantOrderMaterial.setMoMaterialName(productSplitDetailDto.getMaterialName());
		merchantOrderMaterial.setMoPropQuantity(productSplitDetailDto.getPropQuantity());
		merchantOrderMaterial.setMoTotal(productSplitDetailDto.getOrderTotal());
		merchantOrderMaterial.setCreatedBy(consumer);
		merchantOrderMaterial.setUpdatedBy(consumer);
		merchantOrderMaterial.setCreatedDate(JxcUtils.getNowDate());
		merchantOrderMaterial.setUpdatedDate(JxcUtils.getNowDate());
		merchantOrderMaterial.setDeletedFlag("N");
		return merchantOrderMaterial;
	}
	
	private JXCMTMerchantOrderVehicle generatorJxcMerchantOrderVehicle(String moMerchantOrder,String consumer,JXCMTMerchantOrderVehicleDTO vehicleDto){
		JXCMTMerchantOrderVehicle vehicle = new JXCMTMerchantOrderVehicle();
		vehicle.setMoOrderCode(moMerchantOrder);
		vehicle.setMoParentBrandId(vehicleDto.getMoParentBrandId());
		vehicle.setMoParentBrandName(vehicleDto.getMoParentBrandName());
		vehicle.setMoSubBrandId(vehicleDto.getMoSubBrandId());
		vehicle.setMoSubBrandName(vehicleDto.getMoSubBrandName());
		vehicle.setMoAudiId(vehicleDto.getMoAudiId());
		vehicle.setMoAudiName(vehicleDto.getMoAudiName());
		vehicle.setMoMotorcycle(vehicleDto.getMoMotorcycle());
		vehicle.setMoRemark(vehicleDto.getMoRemark());
		vehicle.setMoTotal(vehicleDto.getMoTotal());
		vehicle.setCreatedBy(consumer);
		vehicle.setUpdatedBy(consumer);
		vehicle.setCreatedDate(JxcUtils.getNowDate());
		vehicle.setUpdatedDate(JxcUtils.getNowDate());
		vehicle.setDeletedFlag("N");
		return vehicle;
	}
	
	private JXCMTBsMerchantOrder generatorBsMerchantOrder(String merchantOrderCode,JXCMTMerchantOrder jxcMerchantOrder,JXCMTProductSplitDetailDTO detailDto){
		JXCMTBsMerchantOrder bsMerchantOrder = new JXCMTBsMerchantOrder();				
		bsMerchantOrder.setOrderNumber(merchantOrderCode);
		bsMerchantOrder.setOrderTime(JxcUtils.getNowDate());
		bsMerchantOrder.setHopeTime(jxcMerchantOrder.getMoHopeTime());
		bsMerchantOrder.setMerchantCode(jxcMerchantOrder.getMoMerchantCode());
		bsMerchantOrder.setTotalOrder(detailDto.getOrderTotal());
		bsMerchantOrder.setTotalCheck(0);
		bsMerchantOrder.setTotalAmount(0.0);
		bsMerchantOrder.setStatus(MerchantOrderStatusEnum.ORDER_WAIT_CHECK.getCode());
		bsMerchantOrder.setRemarks(jxcMerchantOrder.getMoRemark());
		bsMerchantOrder.setSignStatus(MerchantOrderSignStatusEnum.ORDER_SIGN_STATUS_UNSEND.getCode());
		bsMerchantOrder.setProductTotal(jxcMerchantOrder.getMoTotal());
		bsMerchantOrder.setCreatedBy(jxcMerchantOrder.getCreatedBy());
		bsMerchantOrder.setUpdatedBy(jxcMerchantOrder.getUpdatedBy());
		bsMerchantOrder.setCreatedDate(JxcUtils.getNowDate());
		bsMerchantOrder.setUpdatedDate(JxcUtils.getNowDate());
		bsMerchantOrder.setDeletedFlag("N");
		bsMerchantOrder.setUrlDispatchBills("");
		return bsMerchantOrder;
	}
	
	private JXCMTBsMerchantOrderDetail generatorBsMerchantOrderDetail(String merchantOrderCode,String bsProductCode,JXCMTMerchantOrder jxcMerchantOrder,JXCMTProductSplitDetailDTO detailDto){
		JXCMTBsMerchantOrderDetail bsMerchantOrderDetail = new JXCMTBsMerchantOrderDetail();
		bsMerchantOrderDetail.setMerchantOrderNumber(merchantOrderCode);
		bsMerchantOrderDetail.setProductCode(bsProductCode);
		bsMerchantOrderDetail.setOrderQuantity(detailDto.getOrderTotal());
		bsMerchantOrderDetail.setProductRemarks(jxcMerchantOrder.getMoRemark());
		bsMerchantOrderDetail.setCreatedBy(jxcMerchantOrder.getCreatedBy());
		bsMerchantOrderDetail.setUpdatedBy(jxcMerchantOrder.getUpdatedBy());
		bsMerchantOrderDetail.setCreatedDate(JxcUtils.getNowDate());
		bsMerchantOrderDetail.setUpdatedDate(JxcUtils.getNowDate());
		bsMerchantOrderDetail.setDeletedFlag("N");
		return bsMerchantOrderDetail;
	}
	
	private JXCMTProduct generatorBsProduct(String bsProductCode,String consumer,JXCMTProductSplit productSplit,JXCMTMaterialInfo materialInfo){
		JXCMTProduct bsProduct = new JXCMTProduct();
		bsProduct.setCode(bsProductCode);
		bsProduct.setName(materialInfo.getMaterialName());
		bsProduct.setServiceType(productSplit.getServiceType());
		bsProduct.setType(String.valueOf(materialInfo.getDeviceTypeId()));
		bsProduct.setChannel(productSplit.getChannel());
		bsProduct.setDeviceQuantity(productSplit.getDeviceQuantity());
		bsProduct.setServiceTime(productSplit.getServiceTime());
		bsProduct.setPackageOne(productSplit.getPackageOne());
		bsProduct.setHardwareContainSource(productSplit.getHardwareContainSource());
		bsProduct.setSourceProportion(productSplit.getSourceProportion());
		bsProduct.setNotSourceProportion(productSplit.getNotSourceProportion());
		bsProduct.setCarType(productSplit.getCarType());
		bsProduct.setChannel(productSplit.getChannel());
		bsProduct.setProductSplitId(productSplit.getId().longValue());
		bsProduct.setCreatedBy(consumer);
		bsProduct.setUpdatedBy(consumer);
		bsProduct.setCreatedDate(JxcUtils.getNowDate());
		bsProduct.setUpdatedDate(JxcUtils.getNowDate());
		bsProduct.setDeletedFlag("N");
		return bsProduct;
	}
	
	private JXCMTProductDetail generatorBsProductDetail(String bsProductCode,String consumer,JXCMTProductSplit productSplit,JXCMTMaterialInfo materialInfo,Map<String,List<JXCMTProductSplitDetail>> mapProductSplitDetail){
		List<JXCMTProductSplitDetail> listSplitDetail = mapProductSplitDetail.get(productSplit.getProductCode());
		JXCMTProductSplitDetail splitDetail = null;
		for(JXCMTProductSplitDetail detail:listSplitDetail){
			if(detail.getMaterialCode().equals(materialInfo.getMaterialCode())){
				splitDetail = detail;
				break;
			}
		}
		JXCMTProductDetail bsProductDetail = new JXCMTProductDetail();
		bsProductDetail.setServiceType(productSplit.getServiceType());
		bsProductDetail.setProductCode(bsProductCode);
		bsProductDetail.setMaterialCode("");
		bsProductDetail.setMaterialName("");
		bsProductDetail.setType((null!=splitDetail)?Byte.valueOf(splitDetail.getProductType()):null);
		bsProductDetail.setPropQuantity((null!=splitDetail)?splitDetail.getPropQuantity():null);
		bsProductDetail.setOrderMaterialCode(materialInfo.getMaterialCode());
		bsProductDetail.setOrderMaterialName(materialInfo.getMaterialName());
		bsProductDetail.setCreatedBy(consumer);
		bsProductDetail.setUpdatedBy(consumer);
		bsProductDetail.setCreatedDate(JxcUtils.getNowDate());
		bsProductDetail.setUpdatedDate(JxcUtils.getNowDate());
		bsProductDetail.setDeletedFlag("N");
		return bsProductDetail;
	}
	
	private JXCMTMerchantOrderReflectMcode generatorJxcmtMerchantOrderReflectMcode(String moMerchantOrder,String bsMerchantOrder,String consumer){
		JXCMTMerchantOrderReflectMcode mcode = new JXCMTMerchantOrderReflectMcode();
		mcode.setMoOrderCode(moMerchantOrder);
		mcode.setMerchantOrder(bsMerchantOrder);
		mcode.setCreatedBy(consumer);
		mcode.setUpdatedBy(consumer);
		mcode.setCreatedDate(JxcUtils.getNowDate());
		mcode.setUpdatedDate(JxcUtils.getNowDate());
		mcode.setDeletedFlag("N");
		return mcode;
	}
	
	private JXCMTBsMerchantOrderVehicle generatorBsMerchantOrderVehicle(String bsMerchantOrder,String consumer,JXCMTMerchantOrderVehicleDTO vehicleDto){
		JXCMTBsMerchantOrderVehicle vehicle = new JXCMTBsMerchantOrderVehicle();
		vehicle.setMerchantOrder(bsMerchantOrder);
		vehicle.setBsParentBrandId(vehicleDto.getMoParentBrandId());
		vehicle.setBsParentBrandName(vehicleDto.getMoParentBrandName());
		vehicle.setBsSubBrandId(vehicleDto.getMoSubBrandId());
		vehicle.setBsSubBrandName(vehicleDto.getMoSubBrandName());
		vehicle.setBsAudiId(vehicleDto.getMoAudiId());
		vehicle.setBsAudiName(vehicleDto.getMoAudiName());
		vehicle.setBsMotorcycle(vehicleDto.getMoMotorcycle());
		vehicle.setBsRemark(vehicleDto.getMoRemark());
		vehicle.setBsTotal(vehicleDto.getMoTotal());
		vehicle.setBsCheckQuantity(0);
		vehicle.setBsTotal(vehicleDto.getMoTotal());
		vehicle.setCreatedBy(consumer);
		vehicle.setUpdatedBy(consumer);
		vehicle.setCreatedDate(JxcUtils.getNowDate());
		vehicle.setUpdatedDate(JxcUtils.getNowDate());
		vehicle.setDeletedFlag("N");
		return vehicle;
	}
	
	private JXCMTBsLogistics generatorBsLogistics(Integer addressId,String bsMerchantCode,String consumer){
		JXCMTBsLogistics logistics = new JXCMTBsLogistics();
		String logiCode = Constants.LOGI_ORDER_PREFIX + JxcUtils.generatorOrderCode(snowflakeWorker);
		logistics.setReceiveId(addressId.longValue());
		logistics.setCode(logiCode);
		logistics.setType(Byte.valueOf("1"));
		logistics.setServiceCode(bsMerchantCode);
		logistics.setCreatedBy(consumer);
		logistics.setUpdatedBy(consumer);
		logistics.setCreatedDate(JxcUtils.getNowDate());
		logistics.setUpdatedDate(JxcUtils.getNowDate());
		logistics.setDeletedFlag("N");
		return logistics;
	}
	
	private JXCMTEcMerchantOrder generatorEcMerchantOrder(JXCMTBsDealerUserInfo dealerUserInfo,JXCMTBsAddress address,String bsMerchantCode,
			JXCMTProductSplit productSplit,JXCMTMaterialInfo materialInfo,
			JXCMTMerchantOrder jxcMerchantOrder,JXCMTProductSplitDetailDTO detailDto,
			Map<Integer,String> mapCacheMerchantChannel,
			Map<Integer,String> mapCacheProductType){
		JXCMTEcMerchantOrder ecMerchantOrder = new JXCMTEcMerchantOrder();
		ecMerchantOrder.setChannel(jxcmtAttribInfoService.getMerchantChannelNameById(jxcmtAttribInfoService.getMerchantChannelFromDbMerchantChannel(dealerUserInfo.getChannel()), mapCacheMerchantChannel));
        ecMerchantOrder.setMerchantCode(dealerUserInfo.getMerchantCode());
        ecMerchantOrder.setMerchantName(dealerUserInfo.getMerchantName());
        ecMerchantOrder.setOrderNumber(bsMerchantCode);
        ecMerchantOrder.setProductCode(productSplit.getProductCode());
        ecMerchantOrder.setProductName(productSplit.getProductName());
        ecMerchantOrder.setMaterialCode(materialInfo.getMaterialCode());
        ecMerchantOrder.setMaterialName(materialInfo.getMaterialName());
        ecMerchantOrder.setDeviceType(materialInfo.getDeviceType());
        ecMerchantOrder.setPrice(jxcMerchantOrder.getMoPrice());
        ecMerchantOrder.setOrderQuantity(detailDto.getOrderTotal());
        ecMerchantOrder.setCheckQuantity(0);
        ecMerchantOrder.setDispatchOrderNumber("");
        ecMerchantOrder.setAlreadyShipmentQuantity(0);
        ecMerchantOrder.setShipmentTime("");
        ecMerchantOrder.setShipmentQuantity("");
        ecMerchantOrder.setSignQuantity(0);
        ecMerchantOrder.setOweQuantity(0);
        ecMerchantOrder.setTotalAmount(jxcMerchantOrder.getMoTotal() * ecMerchantOrder.getPrice());
        ecMerchantOrder.setOrderTime(JxcUtils.getStringFromDate(jxcMerchantOrder.getCreatedDate()));
        ecMerchantOrder.setProductRemarks("");
        ecMerchantOrder.setCheckBy("");
        ecMerchantOrder.setCheckTime("");
        ecMerchantOrder.setStatus(MerchantOrderStatusEnum.ORDER_WAIT_CHECK.getName());
        ecMerchantOrder.setAddressee(address.getName());
        ecMerchantOrder.setMobile(address.getMobile());
        ecMerchantOrder.setAddressDetail((StringUtils.isEmpty(address.getProvinceName()) ? "" : address.getProvinceName())
                + (StringUtils.isEmpty(address.getCityName()) ? "" : address.getCityName()) + (StringUtils.isEmpty(address.getAreaName()) ? "" : address.getAreaName())
                + address.getAddress());
        ecMerchantOrder.setCreatedBy(dealerUserInfo.getName());
        ecMerchantOrder.setUpdatedBy(dealerUserInfo.getName());
        ecMerchantOrder.setCreatedDate(JxcUtils.getNowDate());
        ecMerchantOrder.setUpdatedDate(JxcUtils.getNowDate());
        ecMerchantOrder.setDeletedFlag("N");
        ecMerchantOrder.setMerchantCode(dealerUserInfo.getMerchantCode());
        ecMerchantOrder.setLogisticsDesc("");
        ecMerchantOrder.setProductTotal(jxcMerchantOrder.getMoTotal());
        ecMerchantOrder.setSignStatus(MerchantOrderSignStatusEnum.ORDER_SIGN_STATUS_UNSEND.getName());
        ecMerchantOrder.setOrderMaterialCode(materialInfo.getMaterialCode());
        ecMerchantOrder.setOrderMaterialName(materialInfo.getMaterialName());
        return ecMerchantOrder;
	}

	private JXCMTBsMerchantOrderVehicleDTO generatorBsMerhantOrderVehicleDto(JXCMTBsMerchantOrderVehicle vehicle,JXCMTBsDealerUserInfo bsDealerUserInfo,Map<String,Integer> mapDispatchCount,JXCMTOrderInfo orderInfo){
		Integer count = null;
		JXCMTBsMerchantOrderVehicleDTO dto = new JXCMTBsMerchantOrderVehicleDTO();
		dto.setId(vehicle.getId());
		if(null != bsDealerUserInfo){
			dto.setMerchantCode(bsDealerUserInfo.getMerchantCode());
			dto.setMerchantName(bsDealerUserInfo.getMerchantName());
		}
		if(null == orderInfo){
			dto.setDispatchOrderStatus("UF");
		}else{
			dto.setDispatchOrderStatus(orderInfo.getStatus());
		}
		dto.setMerchantOrder(vehicle.getMerchantOrder());
		dto.setDispatchOrderCode(vehicle.getDispatchOrderCode());
		dto.setBsParentBrandId(vehicle.getBsParentBrandId());
		dto.setBsParentBrandName(vehicle.getBsParentBrandName());
		dto.setBsSubBrandId(vehicle.getBsSubBrandId());
		dto.setBsSubBrandName(vehicle.getBsSubBrandName());
		dto.setBsAudiId(vehicle.getBsAudiId());
		dto.setBsAudiName(vehicle.getBsAudiName());
		dto.setBsMotorcycle(vehicle.getBsMotorcycle());
		dto.setBsRemark(vehicle.getBsRemark());
		dto.setBsTotal(vehicle.getBsTotal());
		dto.setBsCheckQuantity(vehicle.getBsCheckQuantity());
		if(null != mapDispatchCount){
			if(!StringUtils.isEmpty(vehicle.getDispatchOrderCode())){
				count = mapDispatchCount.get(vehicle.getDispatchOrderCode());
				if(count == null){
					count = 0;
				}
			}else{
				count = 0;
			}
			dto.setBsSendQuantity(count);
			if(null != vehicle.getBsCheckQuantity()){
				dto.setBsOweQuantity(vehicle.getBsCheckQuantity() - dto.getBsSendQuantity());
			}

		}
		return dto;
	}
	
	private JXCMTBsMerchantOrderVehicleDTO generatorBsMerhantOrderVehicleDto(JXCMTBsMerchantOrderVehicle vehicle,JXCMTBsDealerUserInfo bsDealerUserInfo,Map<String,Integer> mapDispatchCount,JXCMTOrderInfo orderInfo,Map<String,List<JXCMTGhMerchantOrderConfig>> mapGhMerchantOrderConfigs){
		List<JXCMTGhMerchantOrderConfig> listGhMerchantOrderConfig = null;
		JXCMTAttribInfo attribInfo = null;
		Integer count = null;
		JXCMTBsMerchantOrderVehicleDTO dto = new JXCMTBsMerchantOrderVehicleDTO();
		dto.setId(vehicle.getId());
		if(null != bsDealerUserInfo){
			dto.setMerchantCode(bsDealerUserInfo.getMerchantCode());
			dto.setMerchantName(bsDealerUserInfo.getMerchantName());
		}
		if(null == orderInfo){
			dto.setDispatchOrderStatus("UF");
		}else{
			dto.setDispatchOrderStatus(orderInfo.getStatus());
		}
		dto.setMerchantOrder(vehicle.getMerchantOrder());
		dto.setDispatchOrderCode(vehicle.getDispatchOrderCode());
		dto.setBsParentBrandId(vehicle.getBsParentBrandId());
		dto.setBsParentBrandName(vehicle.getBsParentBrandName());
		dto.setBsSubBrandId(vehicle.getBsSubBrandId());
		dto.setBsSubBrandName(vehicle.getBsSubBrandName());
		dto.setBsAudiId(vehicle.getBsAudiId());
		dto.setBsAudiName(vehicle.getBsAudiName());
		dto.setBsMotorcycle(vehicle.getBsMotorcycle());
		dto.setBsRemark(vehicle.getBsRemark());
		dto.setBsTotal(vehicle.getBsTotal());
		dto.setBsCheckQuantity(vehicle.getBsCheckQuantity());
		if(null != mapDispatchCount){
			if(!StringUtils.isEmpty(vehicle.getDispatchOrderCode())){
				count = mapDispatchCount.get(vehicle.getDispatchOrderCode());
				if(count == null){
					count = 0;
				}
			}else{
				count = 0;
			}
			dto.setBsSendQuantity(count);
			if(null != vehicle.getBsCheckQuantity()){
				dto.setBsOweQuantity(vehicle.getBsCheckQuantity() - dto.getBsSendQuantity());
			}
			
		}
		//生成固定配置和选项配置
		listGhMerchantOrderConfig = mapGhMerchantOrderConfigs.get(dto.getMerchantOrder());
		if(null == listGhMerchantOrderConfig || listGhMerchantOrderConfig.isEmpty()){
			return dto;
		}

		String optionConfigDesc = "";
		String fastenConfigDesc = "";

		List<JXCMTAttribInfoDTO> fastenConfigList = new ArrayList<>();
		List<JXCMTAttribInfoDTO> optionConfigList = new ArrayList<>();
		JXCMTAttribInfoDTO jxcmtAttribInfoDTO=null;
		for(JXCMTGhMerchantOrderConfig oConfig:listGhMerchantOrderConfig){
			attribInfo = jxcmtAttribInfoService.getAttribInfoById(oConfig.getAttribInfoId());

			if(null == attribInfo){
				continue;
			}
			if(oConfig.getStrOption().equals("O")){
				optionConfigDesc += attribInfo.getComment();
				optionConfigDesc += ":";
				optionConfigDesc += attribInfo.getName();
				optionConfigDesc += "\n";
				jxcmtAttribInfoDTO=new JXCMTAttribInfoDTO();
				BeanUtils.copyProperties(attribInfo,jxcmtAttribInfoDTO);
				optionConfigList.add(jxcmtAttribInfoDTO);
			}else if(oConfig.getStrOption().equals("F")){
				fastenConfigDesc += attribInfo.getComment();
				fastenConfigDesc += ":";
				fastenConfigDesc += attribInfo.getName();
				fastenConfigDesc += "\n";
				jxcmtAttribInfoDTO=new JXCMTAttribInfoDTO();
				BeanUtils.copyProperties(attribInfo,jxcmtAttribInfoDTO);
				fastenConfigList.add(jxcmtAttribInfoDTO);
			}
		}
		dto.setFastenConfigDesc(fastenConfigDesc);
		dto.setOptionConfigDesc(optionConfigDesc);
		dto.setFastenConfigList(fastenConfigList);
		dto.setOptionConfigList(optionConfigList);
		return dto;
	}
	
	private JXCMTBsMerchantOrderSignDTO generatorBsMerchantOrderSignDto(JXCMTBsMerchantOrderVehicle vehicle,
			JXCMTBsMerchantOrder bsMerchantOrder,
			Map<String,JXCMTMaterialInfo> mapMaterialInfo,
			JXCMTDispatchOrderNumCount dispatchOrderNumCount,
			JXCMTBsLogistics bsLogistics,
			Map<Integer, JXCMTBsAddressDTO> mapBsAddress,
			Map<String,JXCMTWarehouseInfo> mapWarehouseInfo,
			Map<String,JXCMTBsDealerUserInfo> mapCacheUserInfo){
		JXCMTBsMerchantOrderSignDTO dtoResult = new JXCMTBsMerchantOrderSignDTO();
		dtoResult.setMerchantCode(bsMerchantOrder.getMerchantCode());
		dtoResult.setMerchantName(mapCacheUserInfo.get(bsMerchantOrder.getMerchantCode()).getMerchantName());
		dtoResult.setMerchantOrder(bsMerchantOrder.getOrderNumber());
		JXCMTWarehouseInfo warehouseInfo = mapWarehouseInfo.get(vehicle.getDispatchOrderCode());
		if(null != warehouseInfo){
			dtoResult.setFactoryName(warehouseInfo.getName());
		}
		JXCMTMaterialInfo material = mapMaterialInfo.get(vehicle.getMerchantOrder());
		if(null != material){
			dtoResult.setMaterialCode(material.getMaterialCode());
			dtoResult.setMaterialName(material.getMaterialName());
		}
		if(null != dispatchOrderNumCount){
			dtoResult.setShipmentQuantity(dispatchOrderNumCount.getSendNums());
			dtoResult.setShipmentTime(JxcUtils.getStringFromDate(dispatchOrderNumCount.getSendTime()));
		}
		if(null != bsLogistics){
			if(null == dtoResult.getShipmentQuantity()){
				dtoResult.setShipmentQuantity(dispatchOrderNumCount.getSendNums());
				dtoResult.setShipmentTime(JxcUtils.getStringFromDate(dispatchOrderNumCount.getSendTime()));
			}
			dtoResult.setLogisticsCpy(bsLogistics.getCompany());
			dtoResult.setLogisticsNo(bsLogistics.getOrderNumber());
			
			JXCMTBsAddressDTO bsAddress = mapBsAddress.get(bsLogistics.getReceiveId());
			if(null != bsAddress){
				dtoResult.setContacts(bsAddress.getName());
				dtoResult.setMobile(bsAddress.getMobile());
				dtoResult.setAddress(bsAddress.getAddress());
			}
		}
		dtoResult.setRemark(bsMerchantOrder.getRemarks());
		return dtoResult;
	}
	
	private JXCMTBsMerchantOrderSign generatorBsMerchantOrderSign(String merchantOrder,String documentNo,String documentUrl,String operator){
		JXCMTBsMerchantOrderSign result = new JXCMTBsMerchantOrderSign();
		result.setMerchantOrderNumber(merchantOrder);
		result.setMerchantSignNumber(documentNo);
		result.setSignUrl(documentUrl);
		result.setCreatedBy(operator);
		result.setCreatedDate(JxcUtils.getNowDate());
		result.setUpdatedBy(operator);
		result.setUpdatedDate(JxcUtils.getNowDate());
		result.setDeletedFlag("N");
		return result;
	}
	
	private Integer insertListJxcmtMerchantOrder(List<JXCMTMerchantOrder> listJxcmtMerchantOrders){
		if(null == listJxcmtMerchantOrders || listJxcmtMerchantOrders.isEmpty()){
			return 0;
		}
		return jxcmtMerchantOrderMapper.insertList(listJxcmtMerchantOrders);
	}
	
	private Integer insertListJxcmtMerchantOrderMaterial(List<JXCMTMerchantOrderMaterial> listJxcmtMerchantOrderMaterial){
		if(null == listJxcmtMerchantOrderMaterial || listJxcmtMerchantOrderMaterial.isEmpty()){
			return 0;
		}
		return jxcmtMerchantOrderMaterialMapper.insertList(listJxcmtMerchantOrderMaterial);
	}
	
	private List<JXCMTMerchantOrderReflectMcode> listMerchantReflectModesByMerchantOrders(List<String> merchantOrders){
		if(null == merchantOrders || merchantOrders.isEmpty()){
			return null;
		}
		Example example = new Example(JXCMTMerchantOrderReflectMcode.class);
		example.createCriteria().andIn("merchantOrder", merchantOrders);
		return jxcmtMerchantOrderReflectMcodeMapper.selectByExample(example);
	}
	
	private Integer insertListJxcmtMerchantOrderReflectMcode(List<JXCMTMerchantOrderReflectMcode> listJxcmtMerchantOrderReflectMcode){
		if(null == listJxcmtMerchantOrderReflectMcode || listJxcmtMerchantOrderReflectMcode.isEmpty()){
			return 0;
		}
		return jxcmtMerchantOrderReflectMcodeMapper.insertList(listJxcmtMerchantOrderReflectMcode);
	}
	
	private Integer insertListJxcmtMerchantOrderVehicle(List<JXCMTMerchantOrderVehicle> listJxcmtMerchantOrderVehicle){
		if(null == listJxcmtMerchantOrderVehicle || listJxcmtMerchantOrderVehicle.isEmpty()){
			return 0;
		}
		return jxcmtMerchantOrderVehicleMapper.insertList(listJxcmtMerchantOrderVehicle);
	}
	
	private Integer insertListJxcmtBsMerchantOrder(List<JXCMTBsMerchantOrder> listJxcmtBsMerchantOrder){
		if(null == listJxcmtBsMerchantOrder || listJxcmtBsMerchantOrder.isEmpty()){
			return 0;
		}
		return jxcmtBsMerchantOrderMapper.insertList(listJxcmtBsMerchantOrder);
	}
	
	private Integer insertListJxcmtBsMerchantOrderDetail(List<JXCMTBsMerchantOrderDetail> listJxcmtBsMerchantOrderDetail){
		if(null == listJxcmtBsMerchantOrderDetail || listJxcmtBsMerchantOrderDetail.isEmpty()){
			return 0;
		}
		return jxcmtBsMerchantOrderDetailMapper.insertList(listJxcmtBsMerchantOrderDetail);
	}
	
	private Integer insertListJxcmtBsMerchantOrderVehicle(List<JXCMTBsMerchantOrderVehicle> listJxcmtBsMerchantOrderVehicle){
		if(null == listJxcmtBsMerchantOrderVehicle || listJxcmtBsMerchantOrderVehicle.isEmpty()){
			return 0;
		}
		return jxcmtBsMerchantOrderVehicleMapper.insertList(listJxcmtBsMerchantOrderVehicle);
	}
	
	private Integer insertListJxcmtEcMerchantOrder(List<JXCMTEcMerchantOrder> listJxcmtEcMerchantOrder){
		if(null == listJxcmtEcMerchantOrder || listJxcmtEcMerchantOrder.isEmpty()){
			return 0;
		}
		return jxcmtEcMerchantOrderMapper.insertList(listJxcmtEcMerchantOrder);
	}
	
	private JXCMTBsDealerUserInfo getBsDealerUserInfoByBsMerchantOrder(String merchantOrder){
		JXCMTBsMerchantOrder bsMerchantOrder = getBsMerchantOrderByBsMerchantOrder(merchantOrder);
		if(null == bsMerchantOrder){
			return null;
		}
		return jxcmtUserInfoService.getBsDealerUserInfo(bsMerchantOrder.getMerchantCode());
	}
	
	private JXCMTBsDealerUserInfo getBsDealerUserInfoByMerchantCode(String merchantCode,Map<String,JXCMTBsDealerUserInfo> mapCacheUserInfo){
		JXCMTBsDealerUserInfo userInfo = null;
		if(null == mapCacheUserInfo){
			userInfo = mapCacheUserInfo.get(merchantCode);
		}
		if(null != userInfo){
			return userInfo;
		}
		userInfo = jxcmtUserInfoService.getBsDealerUserInfo(merchantCode);
		if(null != userInfo){
			mapCacheUserInfo.put(merchantCode, userInfo);
		}
		return userInfo;
	}
	
	public JXCMTEcMerchantOrder getEcMerchantOrderByBsMerchantOrder(String merchantOrder){
		JXCMTEcMerchantOrder condition = new JXCMTEcMerchantOrder();
		condition.setOrderNumber(merchantOrder);
		return jxcmtEcMerchantOrderMapper.selectOne(condition);
	}
	
	public List<JXCMTEcMerchantOrder> listEcMerchantOrderByBsMerchantOrders(List<String> listMerchantOrders){
		Example example = new Example(JXCMTEcMerchantOrder.class);
		example.createCriteria().andIn("orderNumber", listMerchantOrders);
		return jxcmtEcMerchantOrderMapper.selectByExample(example);
	}
	
	public Integer updateEcMerchantOrderStatus(String status,List<String> listMerchantOrders){
		JXCMTEcMerchantOrder record = new JXCMTEcMerchantOrder();
		record.setStatus(status);
		Example example = new Example(JXCMTEcMerchantOrder.class);
		example.createCriteria().andIn("orderNumber", listMerchantOrders);
		return jxcmtEcMerchantOrderMapper.updateByExampleSelective(record, example);				
	}
	
	
	public Integer updateEcMerchantOrder(JXCMTEcMerchantOrder ecMerchantOrder){
		if(null == ecMerchantOrder){
			return 0;
		}
		return jxcmtEcMerchantOrderMapper.updateByPrimaryKeySelective(ecMerchantOrder);
	}
	
	private Integer updateBsMerchantOrderDetail(JXCMTBsMerchantOrderDetail bsMerchantOrderDetail){
		return jxcmtBsMerchantOrderDetailMapper.updateByPrimaryKey(bsMerchantOrderDetail);
	}
	
	public JXCMTBsMerchantOrder getBsMerchantOrderByBsMerchantOrder(String merchantOrder){
		JXCMTBsMerchantOrder condition = new JXCMTBsMerchantOrder();
		condition.setOrderNumber(merchantOrder);
		return jxcmtBsMerchantOrderMapper.selectOne(condition);
	}
	
	public Integer updateBsMerchantOrder(JXCMTBsMerchantOrder bsMerchantOrder){
		if(null == bsMerchantOrder){
			return 0;
		}
		return jxcmtBsMerchantOrderMapper.updateByPrimaryKeySelective(bsMerchantOrder);	
	}
	
	public List<JXCMTBsMerchantOrder> listBsMerchantOrderByMoOrderCode(String moOrderCode){
		List<JXCMTMerchantOrderReflectMcode> listReflectMcodes = this.listMtMerchantOrderReflectMcodesByMoOrderCode(moOrderCode);
		if(null == listReflectMcodes || listReflectMcodes.isEmpty()){
			return null;
		}
		List<String> listBsMerchantOrderCode = listReflectMcodes.stream().map(JXCMTMerchantOrderReflectMcode::getMerchantOrder).collect(Collectors.toList());
		return this.listBsMerchantOrderByBsMerchantOrder(listBsMerchantOrderCode);
	}
	
	public List<JXCMTMerchantOrderReflectMcode> listMtMerchantOrderReflectMcodesByMoOrderCode(String moOrderCode){
		Example example = new Example(JXCMTMerchantOrderReflectMcode.class);
		example.createCriteria().andEqualTo("moOrderCode", moOrderCode)
								.andEqualTo("deletedFlag", "N");
		return jxcmtMerchantOrderReflectMcodeMapper.selectByExample(example);
	}
	
	public List<JXCMTBsMerchantOrder> listBsMerchantOrderByBsMerchantOrder(List<String> listMerchantOrders){
		Example example = new Example(JXCMTBsMerchantOrder.class);
		example.createCriteria().andIn("orderNumber", listMerchantOrders);
		return jxcmtBsMerchantOrderMapper.selectByExample(example);
	}
	
	public JXCMTBsMerchantOrder getBsMerchantOrderByMerchantOrder(String merchantOrder){
		JXCMTBsMerchantOrder condition = new JXCMTBsMerchantOrder();
		condition.setOrderNumber(merchantOrder);
		return jxcmtBsMerchantOrderMapper.selectOne(condition);
	}
	
	public Integer updateBsMerchantOrderStatu(Byte statu,List<String> listMerchantOrders){
		JXCMTBsMerchantOrder record = new JXCMTBsMerchantOrder();
		record.setStatus(statu);
		Example example = new Example(JXCMTBsMerchantOrder.class);
		example.createCriteria().andIn("orderNumber", listMerchantOrders);
		return jxcmtBsMerchantOrderMapper.updateByExampleSelective(record, example);
	}
	
	
	public JXCMTBsMerchantOrderDetail getBsMerchantOrderDetailByMerchantOrder(String merchantOrder){
		JXCMTBsMerchantOrderDetail condition = new JXCMTBsMerchantOrderDetail();
		condition.setMerchantOrderNumber(merchantOrder);
		return jxcmtBsMerchantOrderDetailMapper.selectOne(condition);
	}
	
	public List<JXCMTBsMerchantOrderDetail> listBsMerchantOrderDetailsByMerchantOrders(List<String> listMerchantOrders){
		Example example = new Example(JXCMTBsMerchantOrderDetail.class);
		example.createCriteria().andIn("merchantOrderNumber", listMerchantOrders);
		return jxcmtBsMerchantOrderDetailMapper.selectByExample(example);
	}
	
	private List<String> listBsMerchantOrderByDispatchOrderLike(String dispatchOrderLike){
		if(StringUtils.isEmpty(dispatchOrderLike)){
			return null;
		}
		Example example = new Example(JXCMTBsMerchantOrderVehicle.class);
		example.createCriteria().andLike("dispatchOrderCode", "%"+dispatchOrderLike+"%")			
								.andEqualTo("deletedFlag", "N");
		List<JXCMTBsMerchantOrderVehicle> listVehicle = jxcmtBsMerchantOrderVehicleMapper.selectByExample(example);
		if(null == listVehicle || listVehicle.isEmpty()){
			return null;
		}
		return listVehicle.stream().map(JXCMTBsMerchantOrderVehicle::getMerchantOrder).collect(Collectors.toList());	
	}
	
	private JXCMTBsDealerUserInfo getBsDealerUserInfoByMerchantOrder(String merchantOrder){
		JXCMTBsMerchantOrder bsMerchantOrder = this.getBsMerchantOrderByBsMerchantOrder(merchantOrder);
		if(null == bsMerchantOrder){
			return null;
		}
		return jxcmtUserInfoService.getBsDealerUserInfo(bsMerchantOrder.getMerchantCode());
	}
	
	private JXCMTMerchantOrder getJxcmtMerchantOrderByMoMerchantOrderFromDB(String moMerchantOrder){
		JXCMTMerchantOrder condition = new JXCMTMerchantOrder();
		condition.setMoOrderCode(moMerchantOrder);
		condition.setDeletedFlag("N");
		return jxcmtMerchantOrderMapper.selectOne(condition);
	}  
	
	private JXCMTMerchantOrder getJxcmtMerchantOrderByMoMerchantOrder(String moMerchantOrder){
		JXCMTMerchantOrder jxcmtMerchantOrder = this.getJxcmtMerchantOrderByMoMerchantOrderFromDB(moMerchantOrder);
		if(null != jxcmtMerchantOrder){
			return jxcmtMerchantOrder;
		}
		//如果按照总订单号获取不到  根据商户子订单反补到商户总订单
		JXCMTBsMerchantOrder jxcmtBsMerchantOrder = this.getBsMerchantOrderByBsMerchantOrder(moMerchantOrder);
		if(null == jxcmtBsMerchantOrder){
			return jxcmtMerchantOrder;
		}
		JXCMTBsDealerUserInfo dealerUserInfo = jxcmtUserInfoService.getBsDealerUserInfo(jxcmtBsMerchantOrder.getMerchantCode());
		if(null == dealerUserInfo){
			return jxcmtMerchantOrder;
		}
		JXCMTBsLogistics logistics = jxcmtBsLogisticsService.getBsLogisticsByBsMerchantOrderCode(jxcmtBsMerchantOrder.getOrderNumber());
		if(null == logistics){
			return jxcmtMerchantOrder;
		}
		if(null == logistics.getReceiveId()){
			return jxcmtMerchantOrder;
		}
		JXCMTBsMerchantOrderDetail jxcmtBsMerchantOrderDetail = this.getBsMerchantOrderDetailByMerchantOrder(jxcmtBsMerchantOrder.getOrderNumber());
		if(null == jxcmtBsMerchantOrderDetail){
			return jxcmtMerchantOrder;
		}
		JXCMTProduct jxcmtProduct = jxcmtProductService.getBsProductByProductCode(jxcmtBsMerchantOrderDetail.getProductCode());
		JXCMTProductDetail jxcmtProductDetail = jxcmtProductService.getBsProductDetailByProductCode(jxcmtBsMerchantOrderDetail.getProductCode());
		JXCMTProductSplit jxcmtProductSplit = jxcmtProductSplitService.getProductSplitById(jxcmtProduct.getProductSplitId().intValue());
		JXCMTProductSplitDTO productSplitDto = jxcmtProductSplitService.convertProductSplitDto(jxcmtProductSplit);
		if(null == jxcmtBsMerchantOrder.getHopeTime()){
			jxcmtBsMerchantOrder.setHopeTime(jxcmtBsMerchantOrder.getOrderTime());
		}
		jxcmtMerchantOrder = this.generatorJxcMerchantOrder(dealerUserInfo, jxcmtBsMerchantOrder.getHopeTime(), logistics.getReceiveId().intValue(), jxcmtBsMerchantOrder.getCreatedBy(), productSplitDto);
		jxcmtMerchantOrder.setMoOrderCode(moMerchantOrder);
		jxcmtMerchantOrder.setMoTotal(jxcmtBsMerchantOrder.getTotalOrder()/jxcmtProductDetail.getPropQuantity());
		JXCMTProductSplitDetailDTO productSplitDetailDto = new JXCMTProductSplitDetailDTO();
		productSplitDetailDto.setMaterialCode(jxcmtProductDetail.getMaterialCode());
		productSplitDetailDto.setMaterialName(jxcmtProductDetail.getMaterialName());
		productSplitDetailDto.setPropQuantity(jxcmtProductDetail.getPropQuantity());
		productSplitDetailDto.setOrderTotal(jxcmtBsMerchantOrder.getTotalOrder());
		JXCMTMerchantOrderMaterial jxcmtMerchantOrderMaterial = this.generatorJxcMerchantOrderMaterial(moMerchantOrder, jxcmtMerchantOrder.getMoProductCode(), jxcmtBsMerchantOrder.getCreatedBy(), productSplitDetailDto);
		JXCMTMerchantOrderReflectMcode jxcmtMerchantOrderReflectMcode = this.generatorJxcmtMerchantOrderReflectMcode(moMerchantOrder,moMerchantOrder,jxcmtBsMerchantOrder.getCreatedBy());
		List<JXCMTBsMerchantOrderVehicle> listBsMerchantOrderVehicle = this.listBsMerchantOrderVehicleByBsMerchantOrderCode(jxcmtBsMerchantOrder.getOrderNumber());
		List<JXCMTMerchantOrderVehicle> listMtMerchantOrderVehicle = null;
		if(null != listBsMerchantOrderVehicle && !listBsMerchantOrderVehicle.isEmpty()){
			listMtMerchantOrderVehicle = new ArrayList<>();
			JXCMTMerchantOrderVehicle MTVehicle = null;
			for(JXCMTBsMerchantOrderVehicle vehicle:listBsMerchantOrderVehicle){
				if(vehicle.getBsParentBrandId() == null){
					continue;
				}
				MTVehicle = new JXCMTMerchantOrderVehicle();
				MTVehicle.setMoOrderCode(moMerchantOrder);
				MTVehicle.setMoTotal(vehicle.getBsTotal());
				if(!StringUtils.isEmpty(vehicle.getBsParentBrandName())){
					MTVehicle.setMoParentBrandId(jxcCarBrandManager.getParentBrandIdByName(vehicle.getBsParentBrandName()));
					MTVehicle.setMoParentBrandName(vehicle.getBsParentBrandName());
				}
				if(!StringUtils.isEmpty(vehicle.getBsSubBrandName())){
					MTVehicle.setMoSubBrandId(jxcCarBrandManager.getSubBrandIdByName(MTVehicle.getMoParentBrandId(), vehicle.getBsSubBrandName()));
					MTVehicle.setMoSubBrandName(vehicle.getBsSubBrandName());
				}
				if(!StringUtils.isEmpty(vehicle.getBsAudiName())){
					MTVehicle.setMoAudiId(jxcCarBrandManager.getAudiIdByName(MTVehicle.getMoSubBrandId(), vehicle.getBsAudiName()));
					MTVehicle.setMoAudiName(vehicle.getBsAudiName());
				}
				MTVehicle.setMoMotorcycle(vehicle.getBsMotorcycle());
				MTVehicle.setCreatedBy(vehicle.getCreatedBy());
				MTVehicle.setUpdatedBy(vehicle.getUpdatedBy());
				MTVehicle.setCreatedDate(vehicle.getCreatedDate());
				MTVehicle.setUpdatedDate(vehicle.getUpdatedDate());
				MTVehicle.setDeletedFlag(vehicle.getDeletedFlag());
				listMtMerchantOrderVehicle.add(MTVehicle);
			}
		}
		
		jxcmtMerchantOrderMapper.insert(jxcmtMerchantOrder);
		jxcmtMerchantOrderMaterialMapper.insert(jxcmtMerchantOrderMaterial);
		jxcmtMerchantOrderReflectMcodeMapper.insert(jxcmtMerchantOrderReflectMcode);
		if(null != listMtMerchantOrderVehicle && !listMtMerchantOrderVehicle.isEmpty()){
			jxcmtMerchantOrderVehicleMapper.insertList(listMtMerchantOrderVehicle);
		}	
		return this.getJxcmtMerchantOrderByMoMerchantOrderFromDB(moMerchantOrder);	
	}
	
	List<JXCMTMerchantOrderMaterial> listJxcmtMerchantOrderMaterialByMoOrderCode(String moOrderCode){
		Example example = new Example(JXCMTMerchantOrderMaterial.class);
		example.createCriteria().andEqualTo("moOrderCode",moOrderCode)
								.andEqualTo("deletedFlag", "N");
		return jxcmtMerchantOrderMaterialMapper.selectByExample(example);
	}
	
	List<JXCMTMerchantOrderVehicle> listJxcmtMerchantOrderVehicleByMoOrderCode(String moOrderCode){
		Example example = new Example(JXCMTMerchantOrderVehicle.class);
		example.createCriteria().andEqualTo("moOrderCode", moOrderCode)
								.andEqualTo("deletedFlag", "N");
		return jxcmtMerchantOrderVehicleMapper.selectByExample(example);
	}
	
}
