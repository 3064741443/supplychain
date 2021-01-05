package cn.com.glsx.supplychain.service.bs;

import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.enums.SalesReconciliationStatus;
import cn.com.glsx.supplychain.enums.SalesSummarizingStatusEnum;
import cn.com.glsx.supplychain.mapper.OrderInfoDetailMapper;
import cn.com.glsx.supplychain.mapper.OrderInfoMapper;
import cn.com.glsx.supplychain.mapper.SettlementInfoMapper;
import cn.com.glsx.supplychain.mapper.SettlementMapper;
import cn.com.glsx.supplychain.mapper.bs.*;
import cn.com.glsx.supplychain.model.OrderInfoDetail;
import cn.com.glsx.supplychain.model.SettlementInfo;
import cn.com.glsx.supplychain.model.bs.*;
import cn.com.glsx.supplychain.util.StringUtil;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SalesSummarizingService {

    private final static Logger LOGGER = LoggerFactory.getLogger(SalesSummarizingService.class);

    @Autowired
    private SalesSummarizingMapper salesSummarizingMapper;

    @Autowired
    private SalesSummarizingDetailMapper salesSummarizingDetailMapper;

    @Autowired
    private SalesSummarizingHistoryMapper salesSummarizingHistoryMapper;

    @Autowired
    private SalesManagerMapper salesManagerMapper;

    @Autowired
    private ProductDetailMapper productDetailMapper;

    @Autowired
    private SalesRelationMapper salesRelationMapper;

    @Autowired
    private SalesSummarizingMaterialDetailMapper salesSummarizingMaterialDetailMapper;

    @Autowired
    private MerchantOrderDetailMapper merchantOrderDetailMapper;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private OrderInfoDetailMapper orderInfoDetailMapper;

    @Autowired
    private SettlementMapper settlementMapper;

    @Autowired
    private SettlementInfoMapper settlementInfoMapper;

    @Autowired
    private LogisticsMapper logisticsMapper;


    /**
     * 分页查询汇总列表
     *
     * @param pageNum
     * @param pageSize
     * @param salesSummarizing
     * @return
     */
    public List<SalesSummarizing> listSalesSummarizing(int pageNum, int pageSize, SalesSummarizing salesSummarizing) {

        LOGGER.info("pageNum为：{}, pageSize为：{}, 条件查询参数为 ：{}。", pageNum, pageSize, salesSummarizing);
        List<SalesSummarizing> result;
        PageHelper.startPage(pageNum, pageSize);
        result = salesSummarizingMapper.listSalesSummarizing(salesSummarizing);
        if (result != null && result.size() > 0) {
            for (int i = 0; i < result.size(); i++) {
                SalesSummarizingDetail salesSummarizingDetail = new SalesSummarizingDetail();
                salesSummarizingDetail.setSalesId(result.get(i).getId());
                List<SalesSummarizingDetail> salesSummarizingDetails = salesSummarizingDetailMapper.select(salesSummarizingDetail);
                Integer salesQuantity = 0;
                Double salesAmount = 0.0;
                for (SalesSummarizingDetail ssd : salesSummarizingDetails) {
                    salesQuantity = salesQuantity + ssd.getSalesQuantity();
                    salesAmount = salesAmount + ssd.getSalesAmount() * ssd.getSalesQuantity();
                }
                result.get(i).setSalesQuantity(salesQuantity);
                result.get(i).setTotalPrice(salesAmount);
            }
        }
        return result;
    }

    /**
     * 查询汇总
     *
     * @param salesSummarizing
     * @return
     */
    public SalesSummarizing getMaxTimeSalesSummarizing(SalesSummarizing salesSummarizing) {

        LOGGER.info("查询汇总详情入参:{}", salesSummarizing);
        SalesSummarizing result = new SalesSummarizing();
        try {
            result = salesSummarizingMapper.selectMaxTimeSalesSummarizing(salesSummarizing);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        if (result != null) {
            SalesSummarizingDetail salesSummarizingDetail = new SalesSummarizingDetail();
            salesSummarizingDetail.setSalesId(result.getId());
            result.setSalesSummarizingDetailList(salesSummarizingDetailMapper.select(salesSummarizingDetail));
        }
        return result;
    }

    public List<SalesSummarizingDetail> getSalesSummarizingDetailBySalesSummarizingId(Long salesSummarizingId) {
        LOGGER.info("查询汇总详情入参:{}", salesSummarizingId);
        SalesSummarizingDetail salesSummarizingDetail = new SalesSummarizingDetail();
        salesSummarizingDetail.setSalesId(salesSummarizingId);
        List<SalesSummarizingDetail> result = salesSummarizingDetailMapper.select(salesSummarizingDetail);
        //填充物流信息
        SalesRelation salesRelation;
        for (int i =0;i<result.size();i++){
            //查询销售关系id表
            salesRelation = new SalesRelation();
            salesRelation.setSummarizingId(result.get(i).getSalesId());
            List<SalesRelation> salesRelations = salesRelationMapper.select(salesRelation);
            if(salesRelations != null && salesRelations.size() > 0){
                for(int j = 0;j<salesRelations.size();j++){
                    Sales salesInfo = salesManagerMapper.selectByPrimaryKey(salesRelations.get(j).getSalesId());
                    if(!StringUtil.isEmpty(salesInfo)){
                        //填充物流信息
                        Logistics logistics = new Logistics();
                        logistics.setId(salesInfo.getLogisticsId());
                        result.get(i).setLogisticsList(logisticsMapper.select(logistics));
                    }
                }
            }
        }
        return result;
    }

    /**
     * 新增
     *
     * @param salesSummarizing
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
    public Integer add(SalesSummarizing salesSummarizing) {
        LOGGER.info("新增汇总表入参:{}", salesSummarizing);
        Integer result;
        salesSummarizing.setSalesTime(new Date());
        salesSummarizing.setMerchantCode(salesSummarizing.getMerchantCode());
        salesSummarizing.setCreatedDate(new Date());
        salesSummarizing.setUpdatedDate(new Date());
        salesSummarizing.setDeletedFlag("N");
        result = salesSummarizingMapper.add(salesSummarizing);
        for (int i = 0; i < salesSummarizing.getSalesSummarizingDetailList().size(); i++) {
            salesSummarizing.getSalesSummarizingDetailList().get(i).setSalesId(salesSummarizing.getId());
            salesSummarizing.getSalesSummarizingDetailList().get(i).setCreatedBy(salesSummarizing.getCreatedBy());
            salesSummarizing.getSalesSummarizingDetailList().get(i).setCreatedDate(new Date());
            salesSummarizing.getSalesSummarizingDetailList().get(i).setUpdatedDate(new Date());
            salesSummarizing.getSalesSummarizingDetailList().get(i).setUpdatedBy(salesSummarizing.getUpdatedBy());
            salesSummarizing.getSalesSummarizingDetailList().get(i).setDeletedFlag("N");
        }
        salesSummarizingDetailMapper.insertList(salesSummarizing.getSalesSummarizingDetailList());
        return result;
    }

    /**
     * 修改
     *
     * @param salesSummarizing
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
    public Integer updateById(SalesSummarizing salesSummarizing) {
        LOGGER.info("修改汇总表入参:{}", salesSummarizing);
        Integer result;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        salesSummarizing.setUpdatedDate(new Date());
        result = salesSummarizingMapper.updateById(salesSummarizing);
        if (salesSummarizing.getSalesSummarizingDetailList() != null && salesSummarizing.getSalesSummarizingDetailList().size() > 0) {
            List<SalesSummarizingDetail> salesSummarizingDetails = salesSummarizing.getSalesSummarizingDetailList();
            for (SalesSummarizingDetail salesSummarizingDetail : salesSummarizingDetails) {
                salesSummarizingDetail.setUpdatedDate(new Date());
                salesSummarizingDetailMapper.updateById(salesSummarizingDetail);
            }
        }
        //修改销售管理状态为以完成
        if (salesSummarizing.getStatus() == SalesSummarizingStatusEnum.SALES_SUMMARIZING_STATUS_ACCOMPLISH.getCode()) {
            SalesRelation salesRelation = new SalesRelation();
            salesRelation.setSummarizingId(salesSummarizing.getId());
            LOGGER.info("查询销售管理与销售汇总关系参数:{}", salesRelation);
            List<SalesRelation> salesRelationList = salesRelationMapper.select(salesRelation);
            if (salesRelationList != null && salesRelationList.size() > 0) {
                for(SalesRelation salesRelationData : salesRelationList){
                    Sales sales = new Sales();
                    sales.setId(salesRelationData.getSalesId());
                    sales.setStatus(SalesReconciliationStatus.SALES_STATUS_ALREADY_FINISH.getCode());
                    salesManagerMapper.updateByPrimaryKeySelective(sales);

                    //查询状态已完成的数据，同步到财务系统
                    Sales salesInfo = salesManagerMapper.selectByPrimaryKey(salesRelationData.getSalesId());

                    MerchantOrderDetail merchantOrderDetail = new MerchantOrderDetail();
                    merchantOrderDetail.setMerchantOrderNumber(salesInfo.getOrderNumber());
                    merchantOrderDetail.setProductCode(salesInfo.getProductCode());
                    merchantOrderDetail = merchantOrderDetailMapper.selectOne(merchantOrderDetail);

                    SettlementInfo settlementInfo = new SettlementInfo();
                    settlementInfo.setProductCode(salesInfo.getProductCode());
                    settlementInfo.setCustomerOrderNum(salesInfo.getOrderNumber());
                    settlementInfo.setLogisticsId(salesInfo.getLogisticsId());
                    if (!StringUtil.isEmpty(merchantOrderDetail.getDispatchOrderNumber())) {
                        settlementInfo.setDeliveryOrderNum(merchantOrderDetail.getDispatchOrderNumber());
                    }
                    settlementInfo.setSalesId(salesRelationData.getSummarizingId());
                    settlementInfo.setCreatedBy("admin");
                    settlementInfo.setCreatedDate(new Date());
                    settlementInfo.setUpdatedBy("admin");
                    settlementInfo.setUpdatedDate(new Date());
                    settlementInfo.setDeletedFlag("N");
                    settlementInfoMapper.insert(settlementInfo);
                }

                }
        }
        if (salesSummarizing.getStatus() == SalesSummarizingStatusEnum.SALES_SUMMARIZING_STATUS_SUBMIT.getCode()) {
            SalesSummarizing ss = new SalesSummarizing();
            ss.setId(salesSummarizing.getId());
            ss = salesSummarizingMapper.selectOne(ss);
            SalesSummarizingDetail salesSummarizingDetail = new SalesSummarizingDetail();
            salesSummarizingDetail.setSalesId(salesSummarizing.getId());
            List<SalesSummarizingDetail> salesSummarizingDetails = salesSummarizingDetailMapper.select(salesSummarizingDetail);
            for (SalesSummarizingDetail ssd : salesSummarizingDetails) {
                SalesSummarizingHistory salesSummarizingHistory = new SalesSummarizingHistory();
                salesSummarizingHistory.setSalesId(ss.getId());
                salesSummarizingHistory.setProductCode(ssd.getProductCode());
                salesSummarizingHistory.setSalesAmount(ssd.getSalesAmount());
                salesSummarizingHistory.setSalesQuantity(ssd.getSalesQuantity());
                salesSummarizingHistory.setSalesTime(ss.getSalesTime());
                salesSummarizingHistory.setCreatedBy(ss.getCreatedBy());
                salesSummarizingHistory.setCreatedDate(ss.getCreatedDate());
                salesSummarizingHistory.setUpdatedBy(ss.getUpdatedBy());
                salesSummarizingHistory.setUpdatedDate(ss.getUpdatedDate());
                salesSummarizingHistory.setDeletedFlag("N");
                salesSummarizingHistoryMapper.insert(salesSummarizingHistory);
            }
        }
        return result;
    }

    /**
     * 获取订单时间
     *
     * @return
     */
    public List<Date> getDate() {
        List<Date> result;
        result = salesSummarizingMapper.getDate();
        return result;
    }

    /**
     * 销售汇总列表导出
     * @param salesSummarizing
     * @return
     * @throws RpcServiceException
     */
    public List<SalesSummarizing> exportSalesSummarizingExit(SalesSummarizing salesSummarizing) throws RpcServiceException {
        if (StringUtil.isEmpty(salesSummarizing)) {
            LOGGER.error("SalesSummarizingService::exportSalesSummarizingExit 参数不能为空");
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        LOGGER.info("SalesSummarizingService::exportSalesSummarizingExit param salesSummarizing=" + salesSummarizing.toString());
        List<SalesSummarizing> salesList = null;
        List<Long>ids = new ArrayList<>();
        Long id;
        Byte status = 5;
        try {
            salesList = salesSummarizingMapper.exportSalesSummarizingExit(salesSummarizing);
            if (salesList != null && salesList.size()>0){
                for (int i= 0;i<salesList.size();i++){
                    id = new Long(0);
                    SalesSummarizingDetail salesSummarizingDetail = new SalesSummarizingDetail();
                    salesSummarizingDetail.setSalesId(salesList.get(i).getId());
                    //根据销售汇总id查询销售汇总详情
                    List<SalesSummarizingDetail>salesSummarizingDetailList = salesSummarizingDetailMapper.select(salesSummarizingDetail);
                    Integer salesQuantity = 0;
                    Double salesAmount = 0.0;
                    String materialCode = "";
                    String materialName = "";
                    for (SalesSummarizingDetail salesd : salesSummarizingDetailList) {
                        salesQuantity = salesQuantity + salesd.getSalesQuantity();
                        salesAmount = salesAmount + salesd.getSalesAmount();
                        //查询物料信息
                        SalesSummarizingMaterialDetail ssmd = new SalesSummarizingMaterialDetail();
                        ssmd.setProductCode(salesd.getProductCode());
                        List<SalesSummarizingMaterialDetail> salesSummarizingMaterialDetails = salesSummarizingMaterialDetailMapper.select(ssmd);
                            for (int j = 0; j < salesSummarizingMaterialDetails.size(); j++) {
                                materialCode = salesSummarizingMaterialDetails.get(j).getMaterialCode();
                                materialName = salesSummarizingMaterialDetails.get(j).getMaterialName();
                                salesList.get(i).setBillTypeID("XSDD01_SYS");
                                salesList.get(i).setBillTypeName("标准销售订单");
                                salesList.get(i).setBillNo("LHLH");
                                salesList.get(i).setDate(salesList.get(i).getCreatedDate());
                                salesList.get(i).setSaleOrgId(101);
                                salesList.get(i).setSaleOrgName("深圳广联赛讯有限公司");
                                salesList.get(i).setCustId(salesList.get(i).getMerchantCode());
                                salesList.get(i).setCustName(salesList.get(i).getMerchantName());
                                salesList.get(i).setSettleCurrId("PRE001");
                                salesList.get(i).setSettleCurrName("人民币");
                                salesList.get(i).setMaterialId(materialCode);
                                salesList.get(i).setMaterialName(materialName);
                                salesList.get(i).setUnitName("PCS");
                                salesList.get(i).setSalesQuantity(salesQuantity);
                                salesList.get(i).setTaxPrice(salesAmount);
                                if (salesAmount == 0.0) {
                                    salesList.get(i).setOnlyFree(true);
                                } else {
                                    salesList.get(i).setOnlyFree(false);
                                }
                                salesList.get(i).setEntryTaxRate(17);
                                salesList.get(i).setDeliveryDate(null);
                                salesList.get(i).setSettleOrgIds(101);
                                salesList.get(i).setSettleOrgIdsName("深圳广联赛讯有限公司");
                                salesList.get(i).setReserveType("弱预留");
                                salesList.get(i).setWarehouseCode("");
                                salesList.get(i).setWarehouseName("");
                                salesList.get(i).setPlanQuantity(salesQuantity);
                            }

                        }
                    //导出后对应的导出状态变更为已导出
                    id = salesList.get(i).getId();
                    ids.add(id);
                }
            }
            if(ids.size()>0){
                salesSummarizingMapper.batchUpdateByid(ids,status);
            }
        } catch (Exception e) {
            LOGGER.error("SalesSummarizingService::exportSalesSummarizingExit 数据库获取数据异常" + e.getMessage());
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
        }
        LOGGER.info("exportSalesSummarizingExit return salesList.size()" + salesList.size());
        return salesList;

    }


    /**
     * 批量添加销售汇总
     * @param salesSummsasrizingList
     * @return
     * @throws RpcServiceException
     */
    public Integer addSalesSummarizingList(List<SalesSummarizing> salesSummsasrizingList){
        LOGGER.info("批量添加销售汇总入参:{}", salesSummsasrizingList);
        Integer result=null;
        Byte status = 2;
        SalesRelation salesRelation;
        for(SalesSummarizing salesSummarizing:salesSummsasrizingList) {
            //填充销售管理与销售对账汇总关系表
            salesRelation = new SalesRelation();
            salesRelation.setCreatedBy(salesSummarizing.getCreatedBy());
            salesRelation.setCreatedDate(new Date());
            salesRelation.setUpdatedDate(new Date());
            salesRelation.setDeletedFlag("N");

            salesSummarizing.setSalesTime(new Date());
            salesSummarizing.setCreatedDate(new Date());
            salesSummarizing.setUpdatedDate(new Date());
            salesSummarizing.setDeletedFlag("N");
            salesSummarizingMapper.add(salesSummarizing);
            salesRelation.setSummarizingId(salesSummarizing.getId());
            if(salesSummarizing.getSalesList() != null && salesSummarizing.getSalesList().size() > 0){
                for(Sales sales : salesSummarizing.getSalesList() ){
                    salesRelation.setSalesId(sales.getId());
                    salesRelationMapper.add(salesRelation);
                }
            }
            for(SalesSummarizingDetail salesSummarizingDetail : salesSummarizing.getSalesSummarizingDetailList()){

                salesSummarizingDetail.setSalesId(salesSummarizing.getId());
                salesSummarizingDetail.setCreatedBy(salesSummarizing.getCreatedBy());
                salesSummarizingDetail.setCreatedDate(new Date());
                salesSummarizingDetail.setUpdatedDate(new Date());
                salesSummarizingDetail.setUpdatedBy(salesSummarizing.getUpdatedBy());
                salesSummarizingDetail.setDeletedFlag("N");
                salesSummarizingDetailMapper.insert(salesSummarizingDetail);
            }
            //填充销售汇总物料详情
            List<SalesSummarizingMaterialDetail>salesSummarizingMaterialDetails = salesSummarizing.getSalesSummarizingMaterialDetailList();
            List<SalesSummarizingMaterialDetail> s2=new ArrayList<>();
            for(SalesSummarizingMaterialDetail salesSummarizingMaterialDetail : salesSummarizingMaterialDetails){
                salesSummarizingMaterialDetail.setSalesId(salesSummarizing.getId());
                salesSummarizingMaterialDetail.setCreatedBy(salesSummarizing.getCreatedBy());
                salesSummarizingMaterialDetail.setCreatedDate(new Date());
                salesSummarizingMaterialDetail.setUpdatedBy(salesSummarizing.getUpdatedBy());
                salesSummarizingMaterialDetail.setUpdatedDate(new Date());
                salesSummarizingMaterialDetail.setDeletedFlag("N");
                s2.add(salesSummarizingMaterialDetail);
            }
            salesSummarizingMaterialDetailMapper.insertList(s2);
        }
        return result;
    }
}
