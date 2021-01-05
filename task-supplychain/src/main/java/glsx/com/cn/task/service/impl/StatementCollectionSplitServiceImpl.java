package glsx.com.cn.task.service.impl;

import cn.com.glsx.supplychain.enums.*;
import cn.com.glsx.supplychain.model.am.*;

import com.glsx.oms.bigdata.service.fb.api.IMaterialDubboService;
import com.glsx.oms.bigdata.service.fb.model.Material;

import glsx.com.cn.task.mapper.DealerUserInfoMapper;
import glsx.com.cn.task.mapper.am.*;
import glsx.com.cn.task.model.AmSyncLastidRecord;
import glsx.com.cn.task.model.DealerUserInfo;
import glsx.com.cn.task.service.StatementCollectionSplitService;
import glsx.com.cn.task.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatementCollectionSplitServiceImpl implements StatementCollectionSplitService {

    @Autowired
    private StatementCollectionMapper statementCollectionMapper;

    @Autowired
    private StatementCollectionSplitMapper statementCollectionSplitMapper;

    @Autowired
    private ProductSplitMapper productSplitMapper;

    @Autowired
    private ProductSplitDetailMapper productSplitDetailMapper;

    @Autowired
    private ProductSplitHistoryPriceMapper productSplitHistoryPriceMapper;

    @Autowired
    private AmSyncLastidRecordMapper amSyncLastidRecordMapper;

    @Autowired
    private DealerUserInfoMapper dealerUserInfoMapper;

    @Autowired
    private IMaterialDubboService iMaterialDubboService;

    @Autowired
    private KcCustomerRelationMapper kcCustomerRelationMapper;
    
    @Autowired
    private StatementMaterialReplaceMapper materialReplaceMapper;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 广汇采集数据拆分
     */
    @Override
    public void statementCollectionSplit() {

        ProductSplit productSplit;
        AmSyncLastidRecord syncLastidRecord = amSyncLastidRecordMapper.getSyncLastidRecord();
        StatementCollection statementCollection = new StatementCollection();
        statementCollection.setId(syncLastidRecord.getLastStatementCollectionId());
        logger.info("广汇采集数据拆分的id：" + syncLastidRecord.getLastStatementCollectionId());
        List<StatementCollection> statementCollectionList = statementCollectionMapper.listStatementCollection(statementCollection);
        Map<String,StatementMaterialReplace> mapMaterialReplace = new HashMap<>();
        
        for (StatementCollection statementCollectionData : statementCollectionList) {
            //除硬件物料拆分
            Material material = new Material();
            material.setMaterialCode1(statementCollectionData.getMaterialCode());
            List<Material> materials = iMaterialDubboService.list(material);
            if (materials != null && materials.size() > 0) {
                if (materials.size() > 1) {
                    statementCollectionData.setStatus(StatementCollectionStatusEnum.STATEMENT_COLLECTION_SPLIT_FAIL.getCode());
                    statementCollectionData.setReasons("硬件物料编码格式错误");
                    statementCollectionMapper.updateById(statementCollectionData);
                    syncLastidRecord.setLastStatementCollectionId(statementCollectionData.getId());
                    amSyncLastidRecordMapper.updateSyncLastidRecord(syncLastidRecord);
                    continue;
                }
                if (!materials.get(0).getMaterialTypeId().equals(47)) {
                    ProductSplitHistoryPrice historyPrice = new ProductSplitHistoryPrice();
                    historyPrice.setPrice(statementCollectionData.getAfterRebatePrice());
                    if (statementCollectionData.getDeviceType().equals("激活卡")) {
                        historyPrice.setTaxRate(6.00);
                    } else {
                        historyPrice.setTaxRate(13.00);
                    }
                    this.add(statementCollectionData, historyPrice, true,mapMaterialReplace);
                    statementCollectionData.setStatus(StatementCollectionStatusEnum.STATEMENT_COLLECTION_SPLIT_SUCCESS.getCode());
                    statementCollectionMapper.updateById(statementCollectionData);
                    syncLastidRecord.setLastStatementCollectionId(statementCollectionData.getId());
                    amSyncLastidRecordMapper.updateSyncLastidRecord(syncLastidRecord);
                    continue;
                }
            } else {
                statementCollectionData.setStatus(StatementCollectionStatusEnum.STATEMENT_COLLECTION_SPLIT_FAIL.getCode());
                statementCollectionData.setReasons("硬件物料编码格式错误");
                statementCollectionMapper.updateById(statementCollectionData);
                syncLastidRecord.setLastStatementCollectionId(statementCollectionData.getId());
                amSyncLastidRecordMapper.updateSyncLastidRecord(syncLastidRecord);
                continue;
            }

            //硬件物料拆分
            productSplit = new ProductSplit();
            KcCustomerRelation kcCustomerRelation = new KcCustomerRelation();
            kcCustomerRelation.setCustomerCode(statementCollectionData.getCustomerCode());
            List<KcCustomerRelation> kcCustomerRelationList = kcCustomerRelationMapper.getKcCustomerRelationList(kcCustomerRelation);
            if (kcCustomerRelationList != null && kcCustomerRelationList.size() > 0) {
                if (kcCustomerRelationList.size() > 1) {
                    statementCollectionData.setStatus(StatementFinanceStatusEnum.STATEMENT_FINANCE_SPLIT_FAIL.getCode());
                    statementCollectionData.setReasons("客户对应的商户大于1");
                    statementCollectionMapper.updateById(statementCollectionData);
                    syncLastidRecord.setLastStatementCollectionId(statementCollectionData.getId());
                    amSyncLastidRecordMapper.updateSyncLastidRecord(syncLastidRecord);
                    continue;
                }
            } else {
                statementCollectionData.setStatus(StatementFinanceStatusEnum.STATEMENT_FINANCE_SPLIT_FAIL.getCode());
                statementCollectionData.setReasons("客户对应的商户不存在");
                statementCollectionMapper.updateById(statementCollectionData);
                syncLastidRecord.setLastStatementCollectionId(statementCollectionData.getId());
                amSyncLastidRecordMapper.updateSyncLastidRecord(syncLastidRecord);
                continue;
            }
            productSplit.setMerchantCode(kcCustomerRelationList.get(0).getMerchantCode());
            productSplit.setServiceType(statementCollectionData.getServiceType());
            productSplit.setDeletedFlag("N");
            if (statementCollectionData.getServiceType().equals(StatementCollectionServiceTypeEnum.STATEMENT_COLLECTION_SERVICE_TYPE_THREE.getCode())) {
                productSplit.setCarType(Byte.valueOf("1"));
            }
            List<ProductSplit> productSplitList = new ArrayList<>();
            //根据用户查询产品
            if (statementCollectionData.getServiceType().equals(StatementCollectionServiceTypeEnum.STATEMENT_COLLECTION_SERVICE_TYPE_FIVE.getCode())) {
                productSplitList = productSplitMapper.getProductSplitByServiceTypeThreeFour(productSplit);
            } else {
                productSplitList = productSplitMapper.select(productSplit);
            }

            if (productSplitList == null || productSplitList.size() < 1) {
                DealerUserInfo dealerUserInfo = new DealerUserInfo();
                dealerUserInfo.setMerchantCode(kcCustomerRelationList.get(0).getMerchantCode());
                //查询用户渠道
                dealerUserInfo = dealerUserInfoMapper.selectOne(dealerUserInfo);
                if (dealerUserInfo == null) {
                    statementCollectionData.setStatus(StatementCollectionStatusEnum.STATEMENT_COLLECTION_SPLIT_FAIL.getCode());
                    statementCollectionData.setReasons("商户不存在");
                    statementCollectionMapper.updateById(statementCollectionData);
                    continue;
                }

                productSplit = new ProductSplit();
                productSplit.setChannel(dealerUserInfo.getChannel());
                productSplit.setServiceType(statementCollectionData.getServiceType());
                productSplit.setDeletedFlag("N");
                if (statementCollectionData.getServiceType().equals(StatementCollectionServiceTypeEnum.STATEMENT_COLLECTION_SERVICE_TYPE_THREE.getCode())) {
                    productSplit.setCarType(Byte.valueOf("1"));
                }
                //根据用户渠道查询产品
                if (statementCollectionData.getServiceType().equals(StatementCollectionServiceTypeEnum.STATEMENT_COLLECTION_SERVICE_TYPE_FIVE.getCode())) {
                    productSplitList = productSplitMapper.getProductSplitByServiceTypeThreeFour(productSplit);
                } else {
                    productSplitList = productSplitMapper.select(productSplit);
                }
                if (productSplitList == null || productSplitList.size() < 1) {
                    statementCollectionData.setStatus(StatementCollectionStatusEnum.STATEMENT_COLLECTION_SPLIT_FAIL.getCode());
                    statementCollectionData.setReasons("没有当前用户的产品");
                    statementCollectionMapper.updateById(statementCollectionData);
                    continue;
                }
            }

            //根据物料匹配产品
            List<ProductSplit> productSplits = new ArrayList<>();
/*            for (ProductSplit product : productSplitList) {
                if (statementCollectionData.getServiceType() == ProductSplitServiceTypeEnum.PRODUCT_SPLIT_SERVICE_TYPE_THREE.getCode()) {
                    ProductSplitDetail productSplitDetail = new ProductSplitDetail();
                    productSplitDetail.setProductCode(product.getProductCode());
                    productSplitDetail.setServiceType(ProductSplitServiceTypeEnum.PRODUCT_SPLIT_SERVICE_TYPE_ONE.getCode());
                    productSplitDetail = productSplitDetailMapper.selectOne(productSplitDetail);
                    Material material = new Material();
                    material.setFirstLevelName(productSplitDetail.getMaterialName());
                    List<Material> materials = iMaterialDubboService.list(material);
                    for (Material materialData : materials) {
                        if (materialData.getMaterialCode() == statementCollectionData.getMaterialCode()) {
                            productSplits.add(product);
                            break;
                        }
                    }
                } else {
                    ProductSplitDetail productSplitDetail = new ProductSplitDetail();
                    productSplitDetail.setProductCode(product.getProductCode());
                    List<ProductSplitDetail> productSplitDetailList = productSplitDetailMapper.select(productSplitDetail);
                    for (ProductSplitDetail productSplitDetailData : productSplitDetailList) {
                        if (productSplitDetailData.getMaterialCode() == statementCollectionData.getMaterialCode()) {
                            productSplits.add(product);
                            break;
                        }
                    }
                }
            }*/
            //查询物料是否匹配
            String reasons = "";
            material = new Material();
            material.setMaterialCode1(statementCollectionData.getMaterialCode());
            materials = iMaterialDubboService.list(material);
            if (materials != null && materials.size() > 0) {
                if (materials.size() > 1) {
                    statementCollectionData.setStatus(StatementCollectionStatusEnum.STATEMENT_COLLECTION_SPLIT_FAIL.getCode());
                    statementCollectionData.setReasons("硬件物料编码格式错误");
                    statementCollectionMapper.updateById(statementCollectionData);
                    syncLastidRecord.setLastStatementCollectionId(statementCollectionData.getId());
                    amSyncLastidRecordMapper.updateSyncLastidRecord(syncLastidRecord);
                    continue;
                }
                if (materials.get(0).getFirstLevelName() != null) {
                    for (ProductSplit product : productSplitList) {
                        ProductSplitDetail productSplitDetail = new ProductSplitDetail();
                        productSplitDetail.setProductCode(product.getProductCode());
                        productSplitDetail.setProductType("0");
                        List<ProductSplitDetail> productSplitDetailList = productSplitDetailMapper.select(productSplitDetail);
                        for (ProductSplitDetail productSplitDetailData : productSplitDetailList) {
                            if (productSplitDetailData.getMaterialCode().equals(materials.get(0).getFirstLevelName())) {
                                productSplits.add(product);
                                break;
                            }
                        }
                        reasons = product.getProductCode() + ",";
                    }
                    if (productSplits == null || productSplits.size() < 1) {
                        for (ProductSplit product : productSplitList) {
                            ProductSplitDetail productSplitDetail = new ProductSplitDetail();
                            productSplitDetail.setProductCode(product.getProductCode());
                            productSplitDetail.setProductType("0");
                            List<ProductSplitDetail> productSplitDetailList = productSplitDetailMapper.select(productSplitDetail);
                            for (ProductSplitDetail productSplitDetailData : productSplitDetailList) {
                                if (productSplitDetailData.getMaterialCode().equals(materials.get(0).getMaterialCode())) {
                                    productSplits.add(product);
                                    break;
                                }
                            }
                            reasons = product.getProductCode() + ",";
                        }
                    }
                } else {
                    for (ProductSplit product : productSplitList) {
                        ProductSplitDetail productSplitDetail = new ProductSplitDetail();
                        productSplitDetail.setProductCode(product.getProductCode());
                        productSplitDetail.setProductType("0");
                        List<ProductSplitDetail> productSplitDetailList = productSplitDetailMapper.select(productSplitDetail);
                        for (ProductSplitDetail productSplitDetailData : productSplitDetailList) {
                            if (productSplitDetailData.getMaterialCode().equals(materials.get(0).getMaterialCode())) {
                                productSplits.add(product);
                                break;
                            }
                        }
                        reasons = product.getProductCode() + ",";
                    }
                }
            } else {
                statementCollectionData.setStatus(StatementCollectionStatusEnum.STATEMENT_COLLECTION_SPLIT_FAIL.getCode());
                statementCollectionData.setReasons("硬件物料编码格式错误");
                statementCollectionMapper.updateById(statementCollectionData);
                syncLastidRecord.setLastStatementCollectionId(statementCollectionData.getId());
                amSyncLastidRecordMapper.updateSyncLastidRecord(syncLastidRecord);
                continue;
            }

            if (productSplits == null || productSplits.size() < 1) {
                statementCollectionData.setStatus(StatementFinanceStatusEnum.STATEMENT_FINANCE_SPLIT_FAIL.getCode());
                statementCollectionData.setReasons("产品物料不匹配[" + reasons + "]");
                statementCollectionMapper.updateById(statementCollectionData);
                syncLastidRecord.setLastStatementCollectionId(statementCollectionData.getId());
                amSyncLastidRecordMapper.updateSyncLastidRecord(syncLastidRecord);
                continue;
            }


            reasons = "";
            //根据价格加5减5匹配产品
            Double priceSum;
            String priceSumString;
            ProductSplitHistoryPrice productSplitHistoryPrice = new ProductSplitHistoryPrice();
            List<ProductSplit> productSplitCorrect = new ArrayList<>();
            for (ProductSplit productData : productSplits) {
                productSplitHistoryPrice.setProductCode(productData.getProductCode());
                productSplitHistoryPrice.setType(Byte.valueOf("0"));
                List<ProductSplitHistoryPrice> productSplitHistoryPriceList = productSplitHistoryPriceMapper.selecrtProductSplitHistoryPriceByServiceTypeOneThreeFourProductTypeZero(productSplitHistoryPrice);
                priceSum = productSplitHistoryPriceList.get(0).getPrice();
                productSplitHistoryPriceList = productSplitHistoryPriceMapper.selecrtProductSplitHistoryPriceByServiceTypeOneThreeFour(productSplitHistoryPrice);
                for (ProductSplitHistoryPrice price : productSplitHistoryPriceList) {
                    priceSum = priceSum + price.getPrice();
                }
                priceSumString = priceSum + "";
                if (String.valueOf(statementCollectionData.getAfterRebatePrice()).equals(priceSumString)) {
                    productSplitCorrect.add(productData);
                    break;
                } else {
                    if (priceSum <= statementCollectionData.getAfterRebatePrice() + 5 || priceSum >= statementCollectionData.getAfterRebatePrice() - 5) {
                        productSplitCorrect.add(productData);
                    }
                }
                reasons = productData.getProductCode() + ",";
            }

            if (productSplitCorrect == null || productSplitCorrect.size() < 1) {
                statementCollectionData.setStatus(StatementFinanceStatusEnum.STATEMENT_FINANCE_SPLIT_FAIL.getCode());
                statementCollectionData.setReasons("产品价格不匹配[" + reasons + "]");
                statementCollectionMapper.updateById(statementCollectionData);
                syncLastidRecord.setLastStatementCollectionId(statementCollectionData.getId());
                amSyncLastidRecordMapper.updateSyncLastidRecord(syncLastidRecord);
                continue;
            }


            //进行拆分
            if (productSplitCorrect.size() > 0 && productSplitCorrect != null) {
                Double statementCollectionDataPrice = 0.0;
                productSplitHistoryPrice.setProductCode(productSplitCorrect.get(0).getProductCode());
                productSplitHistoryPrice.setType(Byte.valueOf("0"));
                //只查询服务和软件物料
                List<ProductSplitHistoryPrice> productSplitHistoryPriceList = productSplitHistoryPriceMapper.selecrtProductSplitHistoryPriceByServiceTypeOneThreeFour(productSplitHistoryPrice);
                for (ProductSplitHistoryPrice price : productSplitHistoryPriceList) {
                    this.add(statementCollectionData, price, false,mapMaterialReplace);
                    statementCollectionDataPrice = statementCollectionDataPrice + price.getPrice();
                }
                // 硬件价格=对账单中的销售价格-硬件之外所有物料价格之和
              //  statementCollectionDataPrice = statementCollectionData.getPrice() - statementCollectionDataPrice;
                statementCollectionDataPrice = statementCollectionData.getAfterRebatePrice() - statementCollectionDataPrice;
                //查询单个硬件物料
                productSplitHistoryPriceList = productSplitHistoryPriceMapper.selecrtProductSplitHistoryPriceByServiceTypeOneThreeFourProductTypeZero(productSplitHistoryPrice);
                productSplitHistoryPriceList.get(0).setPrice(statementCollectionDataPrice);
                this.add(statementCollectionData, productSplitHistoryPriceList.get(0), true,mapMaterialReplace);
                statementCollectionData.setProductCode(productSplitCorrect.get(0).getProductCode());
                statementCollectionData.setStatus(StatementCollectionStatusEnum.STATEMENT_COLLECTION_SPLIT_SUCCESS.getCode());
                statementCollectionMapper.updateById(statementCollectionData);
            }
            syncLastidRecord.setLastStatementCollectionId(statementCollectionData.getId());
            amSyncLastidRecordMapper.updateSyncLastidRecord(syncLastidRecord);
        }

    }
    
    private StatementMaterialReplace getStatementMaterialReplaceFromDB(String materialCode,Map<String,StatementMaterialReplace> mapMaterialReplace){
    	StatementMaterialReplace result = mapMaterialReplace.get(materialCode);
    	if(null != result){
    		return result;
    	}
    	StatementMaterialReplace condition = new StatementMaterialReplace();
    	condition.setMaterialCodeOrg(materialCode);
    	result = materialReplaceMapper.selectOne(condition);
    	if(null != result){
    		mapMaterialReplace.put(materialCode, result);
    	}
    	return result;
    }


    private void add(StatementCollection statementCollection, ProductSplitHistoryPrice productSplitHistoryPrice, Boolean materialkey,Map<String,StatementMaterialReplace> mapMaterialReplace) {

        StatementCollectionSplit statementCollectionSplit = new StatementCollectionSplit();
        statementCollectionSplit.setCollectionId(statementCollection.getId());
        statementCollectionSplit.setServiceType(statementCollection.getServiceType());
        statementCollectionSplit.setBillNumber("XX" + StringUtil.getOrderNo());
        String year = String.format("%tY", statementCollection.getTime());
        String mon = String.format("%tm", statementCollection.getTime());
        String time = year + "-" + mon + "-15";
        Date date = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = formatter.parse(time);
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
        }
        statementCollectionSplit.setTime(date);
        statementCollectionSplit.setWorkOrder(statementCollection.getWorkOrder());
        statementCollectionSplit.setCustomerCode(statementCollection.getCustomerCode());
        statementCollectionSplit.setCustomerName(statementCollection.getCustomerName());
        statementCollectionSplit.setSaleGroupCode(statementCollection.getSaleGroupCode());
        statementCollectionSplit.setSaleGroupName(statementCollection.getSaleGroupName());
        if(statementCollection.getServiceType().equals((byte)1))
        {
        	if(materialkey){
            	StatementMaterialReplace materialReplace = this.getStatementMaterialReplaceFromDB(statementCollection.getMaterialCode(), mapMaterialReplace);
            	if(null == materialReplace){
            		statementCollectionSplit.setMaterialCode(statementCollection.getMaterialCode());
            		statementCollectionSplit.setMaterialName(statementCollection.getMaterialName());
            	}else{
            		statementCollectionSplit.setMaterialCode(materialReplace.getMaterialCodeDst());
            		statementCollectionSplit.setMaterialName(materialReplace.getMaterialNameDst());
            	}
            }else{
            	StatementMaterialReplace materialReplace = this.getStatementMaterialReplaceFromDB(productSplitHistoryPrice.getMaterialCode(), mapMaterialReplace);
            	if(null == materialReplace){
            		statementCollectionSplit.setMaterialCode(productSplitHistoryPrice.getMaterialCode());
            		statementCollectionSplit.setMaterialName(productSplitHistoryPrice.getMaterialName());
            	}else{
            		statementCollectionSplit.setMaterialCode(materialReplace.getMaterialCodeDst());
            		statementCollectionSplit.setMaterialName(materialReplace.getMaterialNameDst());
            	}
            }
        }else{
        	statementCollectionSplit.setMaterialCode(materialkey ? statementCollection.getMaterialCode() : productSplitHistoryPrice.getMaterialCode());
            statementCollectionSplit.setMaterialName(materialkey ? statementCollection.getMaterialName() : productSplitHistoryPrice.getMaterialName());
        }
         
        statementCollectionSplit.setSalesQuantity(statementCollection.getSalesQuantity());
        statementCollectionSplit.setPrice(productSplitHistoryPrice.getPrice());
        statementCollectionSplit.setTakeGoodsDate(statementCollection.getTime());
        statementCollectionSplit.setWarehouseCode(statementCollection.getWarehouseCode());
        statementCollectionSplit.setWarehouseName(statementCollection.getWarehouseName());
        statementCollectionSplit.setQuantity(statementCollection.getSalesQuantity());
        statementCollectionSplit.setTaxRate(productSplitHistoryPrice.getTaxRate());
        statementCollectionSplit.setCreatedBy("admin");
        statementCollectionSplit.setCreatedDate(new Date());
        statementCollectionSplit.setUpdatedBy("admin");
        statementCollectionSplit.setUpdatedDate(new Date());
        statementCollectionSplit.setDeletedFlag("N");
        statementCollectionSplitMapper.add(statementCollectionSplit);
    }
}
