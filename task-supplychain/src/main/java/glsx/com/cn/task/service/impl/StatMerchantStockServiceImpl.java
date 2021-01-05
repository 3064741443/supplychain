package glsx.com.cn.task.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import glsx.com.cn.task.manager.MaterialServiceManager;
import glsx.com.cn.task.mapper.BsAfterSaleOrderMapper;
import glsx.com.cn.task.mapper.BsMerchantStockMapper;
import glsx.com.cn.task.mapper.BsMerchantStockRetnMapper;
import glsx.com.cn.task.mapper.BsMerchantStockSellMapper;
import glsx.com.cn.task.mapper.BsMerchantStockSettMapper;
import glsx.com.cn.task.mapper.BsSalesMapper;
import glsx.com.cn.task.mapper.BsSalesSummarizingMapper;
import glsx.com.cn.task.mapper.SyncLastidRecordMerChantStockMapper;
import glsx.com.cn.task.mapper.am.StatementCollectionMapper;
import glsx.com.cn.task.mapper.am.StatementCollectionSplitMapper;
import glsx.com.cn.task.mapper.am.StatementFinanceSplitMapper;
import glsx.com.cn.task.model.BsAfterSaleOrder;
import glsx.com.cn.task.model.BsMerchantStock;
import glsx.com.cn.task.model.BsMerchantStockRetn;
import glsx.com.cn.task.model.BsMerchantStockSell;
import glsx.com.cn.task.model.BsMerchantStockSett;
import glsx.com.cn.task.model.BsSales;
import glsx.com.cn.task.model.BsSalesSummarizing;
import glsx.com.cn.task.model.SyncLastidRecordMerChantStock;
import glsx.com.cn.task.service.StatMerchantStockService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.model.am.StatementCollection;
import cn.com.glsx.supplychain.model.am.StatementCollectionSplit;
import cn.com.glsx.supplychain.model.am.StatementFinanceSplit;

import com.glsx.oms.bigdata.service.fb.model.Material;

@Service
@Transactional
public class StatMerchantStockServiceImpl implements StatMerchantStockService{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SyncLastidRecordMerChantStockMapper syncLastidRecordMapper;
	@Autowired
	private BsSalesMapper salesMapper;
	@Autowired
	private BsAfterSaleOrderMapper afterSalesMapper;
	@Autowired
	private BsSalesSummarizingMapper salesSummarizingMapper;
	@Autowired
	private StatementCollectionSplitMapper collectionSplitMapper;
	@Autowired
	private StatementFinanceSplitMapper financeSplitMapper;
	@Autowired
	private BsMerchantStockMapper stockMapper;
	@Autowired
	private BsMerchantStockSellMapper sellMapper;
	@Autowired
	private BsMerchantStockRetnMapper retnMapper;
	@Autowired
	private BsMerchantStockSettMapper settMapper;
	@Autowired
	private MaterialServiceManager materialManager;
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RpcServiceException.class})
	@Override
	public void doStatMerchantStock() throws RpcServiceException{
		
		logger.info("StatMerchantStockServiceImpl::setMerchantStock handle start");
		try
		{
			List<BsMerchantStockSell> listStockSell = new ArrayList<BsMerchantStockSell>();
			List<BsMerchantStockRetn> listStockRetn = new ArrayList<BsMerchantStockRetn>();
			List<BsMerchantStockSett> listStockSett = new ArrayList<BsMerchantStockSett>();
			Map<String,Material> mapMaterial = new HashMap<String,Material>();
			SyncLastidRecordMerChantStock sysLastidRecord = this.getSyncLastidRecordMerchantStock();
			
			listStockSell = this.getListStockSell(sysLastidRecord, listStockSell, mapMaterial);
			listStockRetn = this.getListStockRetn(sysLastidRecord, listStockRetn, mapMaterial);
			listStockSett = this.getListStockSett(sysLastidRecord, listStockSett, mapMaterial);
			
			//商户-物料-数量 0:sell 1:retn 2:sett
			Map<String,Map<String,List<Integer>>> mapStat = new HashMap<String,Map<String,List<Integer>>>();
			Map<String,String> mapMechant = new HashMap<String,String>();
			
			mapStat = this.getStat(listStockSell, listStockRetn, listStockSett, mapStat,mapMechant);
			
			this.setMerchantStock(mapStat, mapMechant, mapMaterial);
			
			this.setListStockSell(listStockSell);
			this.setListStockRetn(listStockRetn);
			this.setListStockSett(listStockSett);
			
			this.setSyncLastidRecordMerchantStock(sysLastidRecord);
		}
		catch(Exception e)
		{
			logger.error(e.getMessage(),e);
			logger.info("StatMerchantStockServiceImpl::setMerchantStock handle failed");
			throw new RpcServiceException(e.getMessage());
		}	
		logger.info("StatMerchantStockServiceImpl::setMerchantStock handle ok!");
	}
	
	
	private void setListStockSell(List<BsMerchantStockSell> listStockSell)
	{
		logger.info("StatMerchantStockServiceImpl::setMerchantStock listStockSell.size=" + listStockSell.size());
		if(listStockSell.size() == 0)
		{
			return;
		}
		sellMapper.batchAddMerchantStockSell(listStockSell);
	}
	
	private void setListStockRetn(List<BsMerchantStockRetn> listStockRetn)
	{
		logger.info("StatMerchantStockServiceImpl::setListStockRetn listStockRetn.size=" + listStockRetn.size());
		if(listStockRetn.size() == 0)
		{
			return;
		}
		retnMapper.batchAddMerchantStockRetn(listStockRetn);
	}
	
	private void setListStockSett(List<BsMerchantStockSett> listStockSett)
	{
		logger.info("StatMerchantStockServiceImpl::setListStockSett listStockSett.size=" + listStockSett.size());
		if(listStockSett.size() == 0)
		{
			return;
		}
		settMapper.batchAddAndUpdateMerchantStockSett(listStockSett);
	}
	
	@SuppressWarnings({ "rawtypes", "null", "unchecked" })
	private void setMerchantStock(Map<String,Map<String,List<Integer>>> mapStat,Map<String,String> mapMechant,Map<String,Material> mapMaterial)
	{
		List<BsMerchantStock> listMerchantStock = new ArrayList<BsMerchantStock>();
		if(mapStat == null)
		{
			return;
		}
		Date nowTime = new Date();
		Iterator iterStat = mapStat.entrySet().iterator();
		while(iterStat.hasNext())
		{
			Map.Entry entry = (Map.Entry) iterStat.next();
			String merchantCode = (String)entry.getKey();
			Map<String,List<Integer>> mapMaterielNum = (Map<String,List<Integer>>)entry.getValue();
			if(mapMaterielNum == null)
			{
				continue;
			}
			Iterator iterMateriel = mapMaterielNum.entrySet().iterator();
			while(iterMateriel.hasNext())
			{
				Map.Entry entryMaterial = (Map.Entry)iterMateriel.next();
				String materialCode = (String)entryMaterial.getKey();
				List<Integer> numList = (List<Integer>)entryMaterial.getValue();
				if(numList == null)
				{
					continue;
				}
				BsMerchantStock condition = new BsMerchantStock();
				condition.setMerchantCode(merchantCode);
				condition.setMaterialCode(materialCode);
				BsMerchantStock merchantStock = stockMapper.selectOne(condition);
				if(merchantStock == null)
				{
					merchantStock = new BsMerchantStock();
					Material material = this.getMaterialByMaterialCode(materialCode, mapMaterial);
					merchantStock.setMaterialCode(materialCode);
					merchantStock.setMaterialDeviceTypeId(material.getDeviceTypeId());
					merchantStock.setMaterialDeviceTypeName(material.getDeviceType());
					merchantStock.setMaterialName(material.getMaterialName());
					merchantStock.setMaterialTypeId(material.getMaterialTypeId());
					merchantStock.setMaterialTypeName(material.getMaterialTypeName());
					merchantStock.setMerchantCode(merchantCode);
					String merchantName = mapMechant.get(merchantCode);
					if(StringUtils.isEmpty(merchantName))
					{
						merchantName = "";
					}
					merchantStock.setMerchantName(merchantName);
					merchantStock.setCreatedBy("adming");
					merchantStock.setUpdatedBy("admin");
					merchantStock.setCreatedDate(nowTime);
					merchantStock.setUpdatedDate(nowTime);
					merchantStock.setStatSellNum(numList.get(0));
					merchantStock.setStatRetnNum(numList.get(1));
					merchantStock.setStatSettNum(numList.get(2));
					merchantStock.setStatCainNum(0);
					merchantStock.setStatCaouNum(0);
					merchantStock.setStatStckNum(merchantStock.getStatSellNum() - merchantStock.getStatRetnNum() -  merchantStock.getStatSettNum() + merchantStock.getStatCainNum() - merchantStock.getStatCaouNum());
					merchantStock.setDeletedFlag("N");
					listMerchantStock.add(merchantStock);
				//	stockMapper.insertSelective(merchantStock);
					logger.info("StatMerchantStockServiceImpl::setMerchantStock add merchantStock:{}", merchantStock);
				}
				else
				{
					merchantStock.setUpdatedBy("admin");
					merchantStock.setUpdatedDate(nowTime);
					merchantStock.setStatSellNum(merchantStock.getStatSellNum() + numList.get(0));
					merchantStock.setStatRetnNum(merchantStock.getStatRetnNum() + numList.get(1));
					merchantStock.setStatSettNum(merchantStock.getStatSettNum() + numList.get(2));
					merchantStock.setStatStckNum(merchantStock.getStatSellNum() - merchantStock.getStatRetnNum() -  merchantStock.getStatSettNum() + merchantStock.getStatCainNum() - merchantStock.getStatCaouNum());
					merchantStock.setDeletedFlag("N");
					//stockMapper.updateByPrimaryKeySelective(merchantStock);
					listMerchantStock.add(merchantStock);
					logger.info("StatMerchantStockServiceImpl::setMerchantStock update merchantStock:{}", merchantStock);
				}			
			}
		}
		
		logger.info("StatMerchantStockServiceImpl::setMerchantStock listMerchantStock.size=" + listMerchantStock.size());
		if(listMerchantStock.size() > 0)
		{
			stockMapper.batchAddAndUpdateMerchantStock(listMerchantStock);
		}
	}
	
	
	private Map<String,Map<String,List<Integer>>> getStat(List<BsMerchantStockSell> listStockSell,List<BsMerchantStockRetn> listStockRetn,List<BsMerchantStockSett> listStockSett,
			Map<String,Map<String,List<Integer>>> mapStat,Map<String,String> mapMechant)
	{
		for(BsMerchantStockSell sell:listStockSell)
		{
			String merchantCode = sell.getMerchantCode();
			String materielCode = sell.getMaterialCode();
			Map<String,List<Integer>> mapMaterielNum = mapStat.get(merchantCode);
			if(mapMaterielNum == null)
			{
				mapMaterielNum = new HashMap<String,List<Integer>>();
			}
			List<Integer> listNum = mapMaterielNum.get(materielCode);
			if(listNum == null)
			{
				listNum = new ArrayList<Integer>();
				listNum.add(0);
				listNum.add(0);
				listNum.add(0);
			}
			Integer sellNum = (sell.getSellNum() == null)?0:sell.getSellNum();
			listNum.set(0, listNum.get(0)+sellNum);
			mapMaterielNum.put(materielCode, listNum);
			mapStat.put(merchantCode, mapMaterielNum);
			mapMechant.put(merchantCode, sell.getMerchantName());
		}
		
		for(BsMerchantStockRetn retn:listStockRetn)
		{
			String merchantCode = retn.getMerchantCode();
			String materielCode = retn.getMaterialCode();
			Map<String,List<Integer>> mapMaterielNum = mapStat.get(merchantCode);
			if(mapMaterielNum == null)
			{
				mapMaterielNum = new HashMap<String,List<Integer>>();
			}
			List<Integer> listNum = mapMaterielNum.get(materielCode);
			if(listNum == null)
			{
				listNum = new ArrayList<Integer>();
				listNum.add(0);
				listNum.add(0);
				listNum.add(0);
			}
			Integer retnNum = (retn.getRetnNum() == null)?0:retn.getRetnNum();
			listNum.set(1, listNum.get(1)+retnNum);
			mapMaterielNum.put(materielCode, listNum);
			mapStat.put(merchantCode, mapMaterielNum);
			mapMechant.put(merchantCode, retn.getMerchantName());
		}
		
		for(BsMerchantStockSett sett:listStockSett)
		{
			String merchantCode = sett.getMerchantCode();
			String materielCode = sett.getMaterialCode();
			Map<String,List<Integer>> mapMaterielNum = mapStat.get(merchantCode);
			if(mapMaterielNum == null)
			{
				mapMaterielNum = new HashMap<String,List<Integer>>();
			}
			List<Integer> listNum = mapMaterielNum.get(materielCode);
			if(listNum == null)
			{
				listNum = new ArrayList<Integer>();
				listNum.add(0);
				listNum.add(0);
				listNum.add(0);
			}
			Integer settNum = (sett.getSettNum() == null)?0:sett.getSettNum();
			if(sett.getDeletedFlag().equals("Y"))
			{			
				listNum.set(2, listNum.get(2)-settNum);
			}
			else
			{			
				listNum.set(2, listNum.get(2)+settNum);
			}	
			mapMaterielNum.put(materielCode, listNum);
			mapStat.put(merchantCode, mapMaterielNum);
			mapMechant.put(merchantCode, sett.getMerchantName());
		}
		
		return mapStat;
	}
	
	private List<BsMerchantStockSett> getListStockSett(SyncLastidRecordMerChantStock sysLastidRecord,
			List<BsMerchantStockSett> listStockSett,Map<String,Material> mapMaterial)
	{
		//经销结算
		List<BsSalesSummarizing> listSalesSummarizing = this.listBsSalesSummarizing(sysLastidRecord);
		if(listSalesSummarizing != null)
		{
			for(BsSalesSummarizing salesSummarizing:listSalesSummarizing)
			{
				logger.info("StatMerchantStockServiceImpl::getListStockSett salesSummarizing:{}",salesSummarizing);
				if(salesSummarizing.getQuantity() == null || salesSummarizing.getQuantity() == 0)
				{
					continue;
				}
				BsMerchantStockSett Sett = new BsMerchantStockSett();
				Sett.setId(null);
				Sett.setMerchantCode(salesSummarizing.getMerchantCode());
				Sett.setMerchantName(StringUtils.isEmpty(salesSummarizing.getMerchantName())?"":salesSummarizing.getMerchantName());
				Sett.setMaterialCode(salesSummarizing.getMaterialCode());
				Material material = this.getMaterialByMaterialCode(salesSummarizing.getMaterialCode(), mapMaterial);
				if(material != null)
				{
					Sett.setMaterialName(material.getMaterialName() == null?"":material.getMaterialName());
				}
				else
				{
					logger.info("StatMerchantStockServiceImpl::getListStockSett  物料表查不到物料信息");
					continue;
				}
				Sett.setSettNum(salesSummarizing.getQuantity());
				Sett.setSetterType(1);
				Sett.setSetterNo(String.valueOf(salesSummarizing.getId()));
				Sett.setCreatedBy(salesSummarizing.getUpdatedBy());
				Sett.setUpdatedBy(salesSummarizing.getUpdatedBy());
				Sett.setCreatedDate(salesSummarizing.getSalesTime());
				Sett.setUpdatedDate(salesSummarizing.getSalesTime());
				Sett.setDeletedFlag("N");
				listStockSett.add(Sett);
			}
		}
		
		//金融风控代销结算
		List<StatementCollectionSplit> listCollectionSplit = this.listStatementCollectionSplit(sysLastidRecord);
		if(listCollectionSplit != null)
		{
			for(StatementCollectionSplit collectionSplit:listCollectionSplit)
			{
				logger.info("StatMerchantStockServiceImpl::getListStockSett collectionSplit:{}",collectionSplit);
				if(collectionSplit.getQuantity() == null || collectionSplit.getQuantity() == 0)
				{
					continue;
				}
				BsMerchantStockSett Sett = new BsMerchantStockSett();
				Sett.setId(null);
				Sett.setMerchantCode(collectionSplit.getMerchantCode());
				Sett.setMerchantName(StringUtils.isEmpty(collectionSplit.getMerchantName())?"":collectionSplit.getMerchantName());
				Sett.setMaterialCode(collectionSplit.getMaterialCode());
				Material material = this.getMaterialByMaterialCode(collectionSplit.getMaterialCode(), mapMaterial);
				if(material != null)
				{
					Sett.setMaterialName(material.getMaterialName() == null?"":material.getMaterialName());
				}
				else
				{
					logger.info("StatMerchantStockServiceImpl::getListStockSett  物料表查不到物料信息");
					continue;
				}
				Sett.setSettNum(collectionSplit.getQuantity());
				Sett.setSetterType(2);
				Sett.setSetterNo(String.valueOf(collectionSplit.getCollectionId()));
				Sett.setCreatedBy(collectionSplit.getCreatedBy());
				Sett.setCreatedDate(collectionSplit.getTime());
				Sett.setUpdatedBy(collectionSplit.getUpdatedBy());
				Sett.setUpdatedDate(collectionSplit.getUpdatedDate());
				Sett.setDeletedFlag(collectionSplit.getDeletedFlag());
				listStockSett.add(Sett);
			}
		}
		
		//广汇集采代销结算
		List<StatementFinanceSplit> listFinanceSplit = this.listStatementFinanceSplit(sysLastidRecord);
		if(listFinanceSplit != null)
		{
			for(StatementFinanceSplit financeSplit:listFinanceSplit)
			{
				logger.info("StatMerchantStockServiceImpl::getListStockSett financeSplit:{}",financeSplit);
				if(financeSplit.getQuantity() == null || financeSplit.getQuantity() == 0)
				{
					continue;
				}
				BsMerchantStockSett Sett = new BsMerchantStockSett();
				Sett.setId(null);
				Sett.setMerchantCode(financeSplit.getMerchantCode());
				Sett.setMerchantName(StringUtils.isEmpty(financeSplit.getMerchantName())?"":financeSplit.getMerchantName());
				Sett.setMaterialCode(financeSplit.getMaterialCode());
				Material material = this.getMaterialByMaterialCode(financeSplit.getMaterialCode(), mapMaterial);
				if(material != null)
				{
					Sett.setMaterialName(material.getMaterialName() == null?"":material.getMaterialName());
				}
				else
				{
					logger.info("StatMerchantStockServiceImpl::getListStockRetn  物料表查不到物料信息");
					continue;
				}
				Sett.setSettNum(financeSplit.getQuantity());
				Sett.setSetterType(3);
				Sett.setSetterNo(String.valueOf(financeSplit.getFinanceId()));
				Sett.setCreatedBy(financeSplit.getCreatedBy());
				Sett.setCreatedDate(financeSplit.getTime());
				Sett.setUpdatedBy(financeSplit.getUpdatedBy());
				Sett.setUpdatedDate(financeSplit.getUpdatedDate());
				Sett.setDeletedFlag(financeSplit.getDeletedFlag());
				listStockSett.add(Sett);
			}
		}
		
		return listStockSett;
	}
	
	private List<BsMerchantStockRetn> getListStockRetn(SyncLastidRecordMerChantStock sysLastidRecord,
			List<BsMerchantStockRetn> listStockRetn,Map<String,Material> mapMaterial)
	{
		List<BsAfterSaleOrder> listAfterSales = this.listBsAfterSaleOrder(sysLastidRecord);
		if(listAfterSales == null)
		{
			return listStockRetn;
		}
		for(BsAfterSaleOrder afterSale:listAfterSales)
		{
			logger.info("StatMerchantStockServiceImpl::getListStockRetn afterSale:{}",afterSale);
			if(afterSale.getQuality() == null || afterSale.getQuality() == 0)
			{
				continue;
			}
			BsMerchantStockRetn retn = new BsMerchantStockRetn();
			retn.setId(null);
			retn.setMerchantCode(afterSale.getMerchantCode());
			retn.setMerchantName(StringUtils.isEmpty(afterSale.getMerchantName())?"":afterSale.getMerchantName());
			retn.setMaterialCode(afterSale.getMaterialCode());
			Material material = this.getMaterialByMaterialCode(afterSale.getMaterialCode(), mapMaterial);
			if(material != null)
			{
				retn.setMaterialName(material.getMaterialName() == null? "":material.getMaterialName());
			}
			else
			{
				logger.info("StatMerchantStockServiceImpl::getListStockRetn  物料表查不到物料信息");
				continue;
			}
			retn.setMerchantAfterSaleNo(afterSale.getOrderNumber());
			retn.setRetnNum(afterSale.getQuality());
			retn.setCreatedBy(afterSale.getCreatedBy());
			retn.setUpdatedBy(afterSale.getUpdatedBy());
			retn.setCreatedDate(afterSale.getUpdatedDate());
			retn.setUpdatedDate(afterSale.getUpdatedDate());
			retn.setDeletedFlag("N");
			listStockRetn.add(retn);
		}
		return listStockRetn;	
	}
	
	private List<BsMerchantStockSell> getListStockSell(SyncLastidRecordMerChantStock sysLastidRecord,
			List<BsMerchantStockSell> listStockSell,Map<String,Material> mapMaterial)
	{
		List<BsSales> listSales = this.listBsSales(sysLastidRecord);
		if(listSales == null)
		{
			return listStockSell;
		}
		for(BsSales bsSales:listSales)
		{
			logger.info("StatMerchantStockServiceImpl::getListStockSell bsSales:{}",bsSales);
			if(bsSales.getQuantity() == null || bsSales.getQuantity() == 0)
			{
				continue;
			}
			BsMerchantStockSell sell = new BsMerchantStockSell();
			sell.setId(null);
			sell.setMerchantCode(bsSales.getMerchantCode());		
			sell.setMerchantName(StringUtils.isEmpty(bsSales.getMerchantName())?"":bsSales.getMerchantName());
			sell.setMaterialCode(bsSales.getMaterialCode());
			Material material = this.getMaterialByMaterialCode(bsSales.getMaterialCode(), mapMaterial);
			if(material != null)
			{
				sell.setMaterialName(material.getMaterialName() == null? "":material.getMaterialName());
			}
			else
			{
				logger.info("StatMerchantStockServiceImpl::getListStockSell  物料表查不到物料信息");
				continue;
			}
			sell.setMerchantOrderCode(bsSales.getOrderNumber());
			sell.setSellNum(bsSales.getQuantity());
			sell.setCreatedBy(bsSales.getCreatedBy());
			sell.setUpdatedBy(bsSales.getCreatedBy());
			sell.setCreatedDate(bsSales.getCreatedDate());
			sell.setUpdatedDate(bsSales.getCreatedDate());
			sell.setDeletedFlag("N");
			listStockSell.add(sell);
		}
		return listStockSell;
	}
	
	private Material getMaterialByMaterialCode(String materialCode,Map<String,Material> mapMaterial)
	{
		Material material = mapMaterial.get(materialCode);
		if(material != null)
		{
			return material;
		}
		material = materialManager.getMaterialByCode(materialCode);
		if(material != null)
		{
			mapMaterial.put(materialCode, material);
		}
		return material;
	}
	
	private SyncLastidRecordMerChantStock getSyncLastidRecordMerchantStock()
	{
		return syncLastidRecordMapper.selectByPrimaryKey(1);
	}
	
	private void setSyncLastidRecordMerchantStock(SyncLastidRecordMerChantStock sysLastidRecord)
	{
		syncLastidRecordMapper.updateByPrimaryKeySelective(sysLastidRecord);
	}
	
	private List<BsSales> listBsSales(SyncLastidRecordMerChantStock sysLastidRecord)
	{
		List<BsSales> listSales = salesMapper.listBsSalesByCondition(sysLastidRecord.getLastBsSalesId().longValue());
		if(listSales == null)
		{
			return listSales;
		}
		if(listSales.size() == 0)
		{
			return listSales;
		}
		Long lastId = sysLastidRecord.getLastBsSalesId().longValue();
		for(BsSales sales:listSales)
		{
			if(sales.getId() > lastId)
			{
				lastId = sales.getId();
			}
		}
		logger.info("StatMerchantStockServiceImpl::listBsSales lastId:" + lastId);
		sysLastidRecord.setLastBsSalesId(lastId.intValue());
		return listSales;
	}
	
	//经销结算
	private List<BsSalesSummarizing> listBsSalesSummarizing(SyncLastidRecordMerChantStock sysLastidRecord)
	{
		List<BsSalesSummarizing> listSalesSummarizing = null;	
		Integer lastTimeStamp = sysLastidRecord.getLastBsSalesTimestamp();
		String strLastDate = TimeStamp2Date(lastTimeStamp.toString(), "yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date lastDate = null;
		try {
			lastDate = sdf.parse(strLastDate);
		} catch (ParseException e) {
			logger.error(e.getMessage(),e);
			e.printStackTrace();
		}
		BsSalesSummarizing condition = new BsSalesSummarizing();
		condition.setSalesTime(lastDate);
		listSalesSummarizing = salesSummarizingMapper.listSalesSummarizingByCondition(condition);
		if(StringUtils.isEmpty(listSalesSummarizing))
		{
			return listSalesSummarizing;
		}
		if(listSalesSummarizing.size() == 0)
		{
			return listSalesSummarizing;
		}
		
		for(BsSalesSummarizing summarizing:listSalesSummarizing)
		{
			if(lastDate.compareTo(summarizing.getSalesTime()) < 0)
			{
				lastDate = summarizing.getSalesTime();
			}
		}
		logger.info("StatMerchantStockServiceImpl::listBsSalesSummarizing lastDate:" + lastDate);
		lastTimeStamp = (int)(lastDate.getTime()/1000);
		sysLastidRecord.setLastBsSalesTimestamp(lastTimeStamp);	
		return listSalesSummarizing;
	}
	
	//代销结算-广汇集采
	private List<StatementCollectionSplit> listStatementCollectionSplit(SyncLastidRecordMerChantStock sysLastidRecord)
	{
		List<StatementCollectionSplit> listStatementCollection = null;
		Integer lastTimeStamp = sysLastidRecord.getLastAmStatementCollectionTimestamp();
		String strLastDate = TimeStamp2Date(lastTimeStamp.toString(), "yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date lastDate = null;
		try {
			lastDate = sdf.parse(strLastDate);
		} catch (ParseException e) {
			logger.error(e.getMessage(),e);
			e.printStackTrace();
		}
		StatementCollectionSplit condition = new StatementCollectionSplit();
		condition.setUpdatedDate(lastDate);
		listStatementCollection = collectionSplitMapper.listStatementCollectionSplitForMerchantStock(condition);
		if(listStatementCollection == null)
		{
			return listStatementCollection;
		}
		if(listStatementCollection.size() == 0)
		{
			return listStatementCollection;
		}
		for(StatementCollectionSplit split:listStatementCollection)
		{
			if(lastDate.compareTo(split.getUpdatedDate()) < 0)
			{
				lastDate = split.getUpdatedDate();
			}
		}
		logger.info("StatMerchantStockServiceImpl::listStatementCollectionSplit lastDate:" + lastDate);
		lastTimeStamp = (int)(lastDate.getTime()/1000);
		sysLastidRecord.setLastAmStatementCollectionTimestamp(lastTimeStamp);	
		return listStatementCollection;
	}
	
	private List<StatementFinanceSplit> listStatementFinanceSplit(SyncLastidRecordMerChantStock sysLastidRecord)
	{
		List<StatementFinanceSplit> listFinanceSplit = null;		
		Integer lastTimeStamp = sysLastidRecord.getLastAmStatementFinanceTimestamp();
		String strLastDate = TimeStamp2Date(lastTimeStamp.toString(), "yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date lastDate = null;
		try {
			lastDate = sdf.parse(strLastDate);
		} catch (ParseException e) {
			logger.error(e.getMessage(),e);
			e.printStackTrace();
		}
		StatementFinanceSplit condition = new StatementFinanceSplit();
		condition.setUpdatedDate(lastDate);
		listFinanceSplit = financeSplitMapper.listStatementFinanceSplitForMerchantStock(condition);
		if(listFinanceSplit == null)
		{
			return listFinanceSplit;
		}
		if(listFinanceSplit.size() == 0)
		{
			return listFinanceSplit;
		}
		for(StatementFinanceSplit split:listFinanceSplit)
		{
			if(lastDate.compareTo(split.getUpdatedDate()) < 0)
			{
				lastDate = split.getUpdatedDate();
			}
		}
		logger.info("StatMerchantStockServiceImpl::listStatementCollectionSplit lastDate:" + lastDate);
		lastTimeStamp = (int)(lastDate.getTime()/1000);
		sysLastidRecord.setLastAmStatementFinanceTimestamp(lastTimeStamp);
		return listFinanceSplit;
	}
	
	private List<BsAfterSaleOrder> listBsAfterSaleOrder(SyncLastidRecordMerChantStock sysLastidRecord)
	{
		List<BsAfterSaleOrder> listAfterSales = null;
		
		Integer lastTimeStamp = sysLastidRecord.getLastBsAfterSaleOrderTimestamp();
		String strLastDate = TimeStamp2Date(lastTimeStamp.toString(), "yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date lastDate = null;
		try {
			lastDate = sdf.parse(strLastDate);
		} catch (ParseException e) {
			logger.error(e.getMessage(),e);
			e.printStackTrace();
		}
		BsAfterSaleOrder condition = new BsAfterSaleOrder();
		condition.setUpdatedDate(lastDate);
		listAfterSales = afterSalesMapper.listBsAfterSaleOrderByConditon(condition);
		if(listAfterSales == null)
		{
			return listAfterSales;
		}
		if(listAfterSales.size() == 0)
		{
			return listAfterSales;
		}
		for(BsAfterSaleOrder saleOrder:listAfterSales)
		{
			if(lastDate.compareTo(saleOrder.getUpdatedDate()) < 0)
			{
				lastDate = saleOrder.getUpdatedDate();
			}
		}
		logger.info("StatMerchantStockServiceImpl::listBsAfterSaleOrder lastDate:" + lastDate);
		lastTimeStamp =(int)(lastDate.getTime()/1000);
		sysLastidRecord.setLastBsAfterSaleOrderTimestamp(lastTimeStamp);
		return listAfterSales;
	}
	
	
	static public String TimeStamp2Date(String timestampString, String formats) {

        if (StringUtils.isEmpty(formats)) 
        {
            formats = "yyyy-MM-dd HH:mm:ss";
        }

        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date = new SimpleDateFormat(formats, Locale.CHINA).format(new Date(timestamp));
        return date;
    }

}
