package cn.com.glsx.supplychain.service.bs;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.glsx.oms.bigdata.service.fb.model.Material;

import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.enums.MerchantStockCainouStatuEnum;
import cn.com.glsx.supplychain.manager.SupplyChainExternalService;
import cn.com.glsx.supplychain.mapper.bs.BsMerchantStockCainouMapper;
import cn.com.glsx.supplychain.mapper.bs.BsMerchantStockMapper;
import cn.com.glsx.supplychain.model.bs.BsMerchantStock;
import cn.com.glsx.supplychain.model.bs.BsMerchantStockCainou;

@Service
public class BsMerchantStockCainouService {
	private static final Logger logger = LoggerFactory.getLogger(BsMerchantStockCainouService.class);
	
	@Autowired
	private BsMerchantStockCainouMapper merchantStockCainouMapper;
	@Autowired
	private BsMerchantStockMapper merchantStockMapper;
	@Autowired
	private SupplyChainExternalService externalService;
	
	
	public Page<BsMerchantStockCainou> pageMerchantStockCainou(RpcPagination<BsMerchantStockCainou> pagination) throws RpcServiceException
	{
		Page<BsMerchantStockCainou> result;
		logger.info("BsMerchantStockCainouService::pageMerchantStockCainou start pagination:{}",pagination);
		try
		{
			BsMerchantStockCainou condition = pagination.getCondition();
			condition.setDeletedFlag("N");
			PageHelper.startPage(pagination.getPageNum(),pagination.getPageSize());  
			result = merchantStockCainouMapper.pageMerchantStockCainou(condition);
		}
		catch(Exception e)     
		{
			e.printStackTrace();                                                                                                                 
			logger.error("BsMerchantStockCainouService::pageMerchantStockCainou 数据库获取数据异常" + e.getMessage());                                  
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);        
		}
		logger.info("BsMerchantStockCainouService::pageMerchantStockCainou return result.size=" + (StringUtils.isEmpty(result)?"null":result.size()));
		return result;
	}
	
	public BsMerchantStockCainou addMerchantStockCainou(BsMerchantStockCainou record) throws RpcServiceException
	{
		logger.info("BsMerchantStockCainouService::addMerchantStockCainou start record:{}",record);
		
		if (StringUtils.isEmpty(record.getCreatedBy())) {
			record.setCreatedBy("admin");
		}
		if (StringUtils.isEmpty(record.getUpdatedBy())) {
			record.setUpdatedBy("admin");
		}
		if (StringUtils.isEmpty(record.getCreatedDate())) {
			record.setCreatedDate(new Date());
		}
		if (StringUtils.isEmpty(record.getUpdatedDate())) {
			record.setUpdatedDate(new Date());
		}

		Material material = externalService
				.getMaterialInfoByMaterialCode(record.getMaterialCode());
		if (!StringUtils.isEmpty(material)) {
			record.setDeviceTypeName(material.getDeviceType());
			record.setDeviceType(material.getDeviceTypeId());
		}

		// 判断库存表有没有这个纪录存在 如果没有 不允许调出 如果有则查看库存数量是否大于调出数量
		BsMerchantStock stockCondition = new BsMerchantStock();
		stockCondition.setMerchantCode(record.getFromMerchantCode());
		stockCondition.setMerchantCode(record.getMaterialCode());
		stockCondition.setDeletedFlag("N");
		BsMerchantStock merchantStock = merchantStockMapper
				.selectOne(stockCondition);
		if (StringUtils.isEmpty(merchantStock)
				|| merchantStock.getStatStckNum() < record.getDeliNum()) {
			logger.error("BsMerchantStockCainouService::addMerchantStockCainou 库存数小于调出数 无法调出!");
			throw new RpcServiceException(
					ErrorCodeEnum.ERRCODE_MATERIIAL_SHORTAGE_IN_NUMBER);
		}

		merchantStock.setStatCaouNum(merchantStock.getStatCaouNum()
				+ record.getDeliNum());
		merchantStock.setStatStckNum(merchantStock.getStatStckNum()
				- record.getDeliNum());
		merchantStock.setUpdatedBy(record.getUpdatedBy());
		merchantStock.setUpdatedDate(record.getUpdatedDate());
		try {
			merchantStockMapper.updateByPrimaryKeySelective(merchantStock);
			// 插入出入库纪录表
			merchantStockCainouMapper.insertSelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("BsMerchantStockCainouService::addMerchantStockCainou 数据库获取数据异常"
					+ e.getMessage());
			throw new RpcServiceException(
					ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("BsMerchantStockCainouService::addMerchantStockCainou return record:{}",record);
		return record;
	}
	
	public BsMerchantStockCainou updateMerchantStock(BsMerchantStockCainou record) throws RpcServiceException
	{
		BsMerchantStockCainou result;
		logger.info(
				"BsMerchantStockCainouService::updateMerchantStock start record:{}",
				record);

		if (StringUtils.isEmpty(record.getUpdatedBy())) {
			record.setUpdatedBy("admin");
		}
		if (StringUtils.isEmpty(record.getUpdatedDate())) {
			record.setUpdatedDate(new Date());
		}

		BsMerchantStockCainou cainouCondition = new BsMerchantStockCainou();
		cainouCondition.setId(record.getId());
		BsMerchantStockCainou dbStockCainou = merchantStockCainouMapper
				.selectOne(cainouCondition);
		if (StringUtils.isEmpty(dbStockCainou)) {
			logger.error("BsMerchantStockCainouService::updateMerchantStock 数据库获取数据异常!");
			throw new RpcServiceException(
					ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		Integer differencesDeliNum = record.getDeliNum()
				- dbStockCainou.getDeliNum();
		Integer differencesSignNum = record.getSignNum()
				- dbStockCainou.getSignNum();

		if (differencesDeliNum != 0) {
			if (record.getStatus() == MerchantStockCainouStatuEnum.MSTOCK_CAINOU_WAIT_FINI
					.getCode()
					|| dbStockCainou.getStatus() == MerchantStockCainouStatuEnum.MSTOCK_CAINOU_WAIT_FINI
							.getCode()) {
				logger.error("BsMerchantStockCainouService::updateMerchantStock 已经完成的调拨单不能修改发货数量!");
				throw new RpcServiceException(
						ErrorCodeEnum.ERRCODE_FORBID_UPDATE_DELINUM);
			}
		}

		if (differencesSignNum != 0) {
			if (record.getStatus() != MerchantStockCainouStatuEnum.MSTOCK_CAINOU_WAIT_FINI
					.getCode()) {
				logger.error("BsMerchantStockCainouService::updateMerchantStock 必须是已经完成状态下 修改签约数量!");
				throw new RpcServiceException(
						ErrorCodeEnum.ERRCODE_FORBID_UPDATE_SIGNNUM);
			}
		}

		try {
			// 修改发货数量
			if (differencesDeliNum != 0) {
				BsMerchantStock stockCondition = new BsMerchantStock();
				stockCondition.setMerchantCode(record.getFromMerchantCode());
				stockCondition.setMerchantCode(record.getMaterialCode());
				stockCondition.setDeletedFlag("N");
				BsMerchantStock merchantStock = merchantStockMapper
						.selectOne(stockCondition);
				if (StringUtils.isEmpty(merchantStock)
						|| merchantStock.getStatStckNum() < differencesDeliNum) {
					logger.error("BsMerchantStockCainouService::updateMerchantStock 库存数小于调出数 无法调出!");
					throw new RpcServiceException(
							ErrorCodeEnum.ERRCODE_MATERIIAL_SHORTAGE_IN_NUMBER);
				}

				merchantStock.setStatCaouNum(merchantStock.getStatCaouNum()
						+ differencesDeliNum);
				merchantStock.setStatStckNum(merchantStock.getStatStckNum()
						- differencesDeliNum);
				merchantStock.setUpdatedBy(record.getUpdatedBy());
				merchantStock.setUpdatedDate(record.getUpdatedDate());
				merchantStockMapper.updateByPrimaryKeySelective(merchantStock);
			}

			// 修改签收信息
			if (differencesSignNum != 0) {
				BsMerchantStock stockCondition = new BsMerchantStock();
				stockCondition.setMerchantCode(record.getToMerchantCode());
				stockCondition.setMaterialCode(record.getMaterialCode());
				stockCondition.setDeletedFlag("N");
				BsMerchantStock merchantStock = merchantStockMapper
						.selectOne(stockCondition);
				if (StringUtils.isEmpty(merchantStock)) {
					merchantStock = new BsMerchantStock();
					merchantStock.setMerchantCode(record.getToMerchantCode());
					merchantStock.setMerchantName(record.getToMerchantName());
					merchantStock.setMaterialCode(record.getMaterialCode());
					merchantStock.setMaterialName(record.getMaterialName());
					Material material = externalService
							.getMaterialInfoByMaterialCode(record
									.getMaterialCode());
					if (!StringUtils.isEmpty(material)) {
						merchantStock.setMaterialTypeId(material
								.getMaterialTypeId());
						merchantStock.setMaterialTypeName(material
								.getMaterialTypeName());
						merchantStock.setMaterialDeviceTypeId(material
								.getDeviceTypeId());
						merchantStock.setMaterialDeviceTypeName(material
								.getDeviceType());
					}
					merchantStock.setStatCainNum(differencesSignNum);
					merchantStock.setStatStckNum(differencesSignNum);
					merchantStock.setCreatedBy(record.getUpdatedBy());
					merchantStock.setUpdatedBy(record.getUpdatedBy());
					merchantStock.setCreatedDate(record.getCreatedDate());
					merchantStock.setUpdatedDate(record.getUpdatedDate());
					merchantStock.setDeletedFlag("N");
					merchantStockMapper.insertSelective(merchantStock);
				} else {
					merchantStock.setStatCainNum(merchantStock.getStatCainNum()
							+ differencesSignNum);
					merchantStock.setStatStckNum(merchantStock.getStatStckNum()
							+ differencesSignNum);
					merchantStock.setUpdatedBy(record.getUpdatedBy());
					merchantStock.setUpdatedDate(record.getUpdatedDate());
					merchantStockMapper
							.updateByPrimaryKeySelective(merchantStock);
				}
			}

			merchantStockCainouMapper.updateByPrimaryKeySelective(record);
			result = merchantStockCainouMapper.selectOne(cainouCondition);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("BsMerchantStockCainouService::updateMerchantStock 数据库获取数据异常"
					+ e.getMessage());
			throw new RpcServiceException(
					ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info(
				"BsMerchantStockCainouService::updateMerchantStock return result:{}",
				result);
		return result;
	}
	
	
}
