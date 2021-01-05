package cn.com.glsx.supplychain.service.bs;

import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.common.Constants;
import cn.com.glsx.supplychain.enums.*;
import cn.com.glsx.supplychain.kafka.ExportMerchantOrder;
import cn.com.glsx.supplychain.kafka.MerchantOrderKafkaMessage;
import cn.com.glsx.supplychain.kafka.SendKafkaService;
import cn.com.glsx.supplychain.kafka.SignMerchantOrder;
import cn.com.glsx.supplychain.mapper.DeviceTypeMapper;
import cn.com.glsx.supplychain.mapper.OrderInfoDetailMapper;
import cn.com.glsx.supplychain.mapper.OrderInfoMapper;
import cn.com.glsx.supplychain.mapper.am.MaterialInfoMapper;
import cn.com.glsx.supplychain.mapper.am.ProductSplitDetailMapper;
import cn.com.glsx.supplychain.mapper.am.ProductSplitHistoryPriceMapper;
import cn.com.glsx.supplychain.mapper.am.ProductSplitMapper;
import cn.com.glsx.supplychain.mapper.bs.*;
import cn.com.glsx.supplychain.model.DeviceType;
import cn.com.glsx.supplychain.model.OrderInfo;
import cn.com.glsx.supplychain.model.OrderInfoDetail;
import cn.com.glsx.supplychain.model.am.*;
import cn.com.glsx.supplychain.model.bs.*;
import cn.com.glsx.supplychain.service.am.ProductSplitService;
import cn.com.glsx.supplychain.util.StringUtil;
import cn.com.glsx.supplychain.util.SupplychainUtils;
import cn.com.glsx.supplychain.vo.MerchantOrderExcelVo;
import cn.com.glsx.supplychain.vo.MerchantOrderSignVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.glsx.cloudframework.core.util.BeanUtils;
import com.glsx.oms.bigdata.service.fb.api.IMaterialDubboService;
import org.oreframework.boot.autoconfigure.snowflake.algorithm.SnowflakeWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class MerchantOrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MerchantOrderService.class);
    @Autowired
    private SendKafkaService sendKafkaService;
    @Autowired
    private MerchantOrderMapper merchantOrderMapper;

    @Autowired
    private MerchantOrderDetailMapper merchantOrderDetailMapper;

    @Autowired
    private ProductHistoryPriceService productHistoryPriceService;

    @Autowired
    private SalesSummarizingService salesSummarizingService;

    @Autowired
    private SalesSummarizingDetailMapper salesSummarizingDetailMapper;

    @Autowired
    private ProductSplitHistoryPriceMapper productSplitHistoryPriceMapper;

    @Autowired
    private LogisticsService logisticsService;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private AddressService addressService;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private DealerUserInfoService dealerUserInfoService;

    @Autowired
    private ProductService productService;
    
    @Autowired
    private ProductSplitService productSplitService;

    @Autowired
    private SalesManagerService salesManagerService;

    @Autowired
    private OrderInfoDetailMapper orderInfoDetailMapper;

    @Autowired
    private LogisticsMapper logisticsMapper;

    @Autowired
    private ProductTypeMapper productTypeMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private DeviceTypeMapper deviceTypeMapper;

    @Autowired
    private ProductDetailMapper productDetailMapper;

    @Autowired
    private ProductHistoryPriceMapper productHistoryPriceMapper;

    @Autowired
    private ProductSplitDetailMapper productSplitDetailMapper;

    @Autowired
    private ProductSplitMapper productSplitMapper;

    @Autowired
    private IMaterialDubboService iMaterialDubboService;

    @Autowired
    private EcMerchantOrderMapper ecMerchantOrderMapper;

    @Autowired
    private MaterialInfoMapper materialInfoMapper;

    @Autowired
    private MerchantOrderSignService orderSignService;

    @Autowired
    private SnowflakeWorker snowflakeWorker;
    
    @Autowired
    private EcMerchantOrderService ecMerchantOrderService;


    /**
     * 分页查询订单列表
     *
     * @param pageNum
     * @param pageSize
     * @param merchantOrder
     * @return
     */
    public Page<MerchantOrder> listMerchantOrder(int pageNum, int pageSize, MerchantOrder merchantOrder) {
        LOGGER.info("pageNum为：{}, pageSize为：{}, 条件查询参数为 ：{}。", pageNum, pageSize, merchantOrder);
        Page<MerchantOrder> result;
        PageHelper.startPage(pageNum, pageSize);
        result = merchantOrderMapper.listMerchantOrder(merchantOrder);
        if (!StringUtil.isEmpty(result)) {
            for (int i = 0; i < result.getResult().size(); i++) {
                //填充产品信息
                Product product = new Product();
                product.setCode(result.getResult().get(i).getCode());
                product = productMapper.select(product).get(0);
                ProductDetail productDetail = new ProductDetail();
                productDetail.setProductCode(product.getCode());
                List<ProductDetail> productDetailList = productDetailMapper.getProductDetail(productDetail);
                List<ProductDetail> productDetailListResult = new ArrayList<>();
                for (ProductDetail productDetailData : productDetailList) {
                    productDetailListResult.add(productDetailData);
                }
                product.setProductDetailList(productDetailListResult);
                result.getResult().get(i).setProductInfo(product);

                Double price = 0.0;
                ProductHistoryPrice productHistoryPrice = new ProductHistoryPrice();
                productHistoryPrice.setProductCode(result.getResult().get(i).getCode());
                productHistoryPrice.setType(ProductHistoryPriceEnum.PRODUCT_HISTORY_PRICE_NOW.getCode());
                List<ProductHistoryPrice> productHistoryPriceList = productHistoryPriceMapper.select(productHistoryPrice);
                if (productHistoryPriceList != null && productHistoryPriceList.size() > 0) {
                    for (ProductHistoryPrice HistoryPrice : productHistoryPriceList) {
                        if (HistoryPrice.getPrice() != null) {
                            price = price + HistoryPrice.getPrice();
                        }
                    }
                    result.getResult().get(i).setAmount(price);
                }
                //填充商户订单详情信息
                if (result.getResult().get(i).getMerchantOrderDetailInfo() != null) {
                    MerchantOrderDetail merchantOrderDetail = new MerchantOrderDetail();
                    merchantOrderDetail.setMerchantOrderNumber(result.getResult().get(i).getMerchantOrderDetailInfo().getMerchantOrderNumber());
                    if (result.getResult().get(i).getMerchantOrderDetailInfo().getMerchantOrderNumber() != null) {
                        result.getResult().get(i).setMerchantOrderDetailInfo(merchantOrderDetailMapper.selectOne(merchantOrderDetail));
                        //填充发货数量
                        Integer shipmentsQuantity = orderInfoMapper.getShipmentsQuantityByOrderCode(result.getResult().get(i).getMerchantOrderDetailInfo().getDispatchOrderNumber());
                        if (shipmentsQuantity == 0) {
                            LOGGER.info("直接发货情况取物流表的发货数 物流单号:{}", result.getResult().get(i).getMerchantOrderDetailInfo().getDispatchOrderNumber());
                            Logistics logistics = new Logistics();
                            if (result.getResult().get(i).getMerchantOrderDetailInfo().getDispatchOrderNumber() != null) {
                                Integer deliverShipmentQuantity = 0;
                                logistics.setServiceCode(result.getResult().get(i).getMerchantOrderDetailInfo().getDispatchOrderNumber());
                                List<Logistics> logisticsList = logisticsMapper.select(logistics);
                                if (logisticsList != null && logisticsList.size() > 0) {
                                    for (Logistics list : logisticsList) {
                                        if (list.getShipmentsQuantity() != null) {
                                            deliverShipmentQuantity += list.getShipmentsQuantity();
                                        }
                                    }
                                }
                                result.getResult().get(i).getMerchantOrderDetailInfo().setShipmentsQuantity(deliverShipmentQuantity);
                                //查询物流List
                                result.getResult().get(i).setLogisticsList(logisticsList);
                            }
                        } else {
                            result.getResult().get(i).getMerchantOrderDetailInfo().setShipmentsQuantity(shipmentsQuantity);
                        }

                    }
                }
                //获取单价
                /*if(!StringUtil.isEmpty(result.get(i).getProductSplitId())){
                    ProductSplit productSplit = new ProductSplit();
                    productSplit.setId(result.get(i).getProductSplitId());
                    productSplit = productSplitMapper.selectOne(productSplit);
                    ProductSplitHistoryPrice productSplitHistoryPrice = new ProductSplitHistoryPrice();
                    productSplitHistoryPrice.setType(ProductHistoryPriceEnum.PRODUCT_HISTORY_PRICE_NOW.getCode());
                    productSplitHistoryPrice.setProductCode(productSplit.getProductCode());
                    List<ProductSplitHistoryPrice> productSplitHistoryPrices = productSplitHistoryPriceMapper.select(productSplitHistoryPrice);
                    Double price = 0.0;
                    for(ProductSplitHistoryPrice splitHistoryPrice : productSplitHistoryPrices){
                        if(splitHistoryPrice.getPrice() != null){
                            price = price + splitHistoryPrice.getPrice();
                        }
                    }
                }*/

                //产品名称
                if (!StringUtil.isEmpty(product.getProductSplitId())) {
                    ProductSplit productSplitInfo = productSplitMapper.getProductSplitByid(product.getProductSplitId());
                    if (!StringUtil.isEmpty(productSplitInfo) && !StringUtil.isEmpty(productSplitInfo.getProductName())) {
                        result.getResult().get(i).setProductName(productSplitInfo.getProductName());
                    } else if (!StringUtil.isEmpty(productSplitInfo) && !StringUtil.isEmpty(productSplitInfo.getAlias())) {
                        result.getResult().get(i).setProductName(productSplitInfo.getAlias());
                    }else if(!StringUtil.isEmpty(product.getName())){
                        result.getResult().get(i).setProductName(product.getName());
                    }
                }
            }
        }

        List<MerchantOrder> merchantOrderList = result.getResult();
        List<String> orderNumList = new ArrayList<String>();
        List<BsMerchantOrderSign> orderSignList = null;
        for (MerchantOrder order : merchantOrderList) {
            orderNumList.add(order.getOrderNumber());
        }
        if (orderNumList.size() > 0) {
            orderSignList = orderSignService.listMerchantOrderSignByMerchantOrder(orderNumList);
        }
        for(MerchantOrder order:merchantOrderList)
        {
        	for(BsMerchantOrderSign sign:orderSignList)
        	{
        		if(sign.getMerchantOrderNumber().equals(order.getOrderNumber()))
        		{
        			order.setJsonSignUrl(sign.getSignUrl());
        			order.setSignNumberCode(sign.getMerchantSignNumber());
        		}
        	}

        }
        return result;
    }

    /**
     * 根据订单编码生成签收单信息
     *
     * @param listMerchantOrder
     * @return
     */
    public List<MerchantOrderSignVo> genSignOrderByMerchantOrderNum(String receiveOrderNo, List<MerchantOrder> listMerchantOrder) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<String> listDispatchOrderCodes = new ArrayList<String>();
        List<String> listDirectOrderCodes = new ArrayList<String>();
        List<MerchantOrderSignVo> listSignOrderVo = new ArrayList<MerchantOrderSignVo>();
        List<OrderInfo> listSendOrderInfo = null;
        List<Logistics> listLogistics = null;

        List<MerchantOrder> listOrder = merchantOrderMapper.getMerchantOrderListForSignOrder(listMerchantOrder);
        //去重
        for (int i = 0; i < listOrder.size(); i++) {
            for (int j = 0; j < listOrder.size(); j++) {
                if (i != j && listOrder.get(i).getOrderNumber().equals(listOrder.get(j).getOrderNumber())) {
                    listOrder.remove(listOrder.get(j));
                }
            }
        }
        for (MerchantOrder merchantOrder : listOrder) {
            if (merchantOrder.getMetrialType() == (byte) 0) //硬件
            {
                listDispatchOrderCodes.add(merchantOrder.getDispatchOrderNumber());
            } else //配件直接发货
            {
                listDirectOrderCodes.add(merchantOrder.getDispatchOrderNumber());
            }
        }
        if (listDispatchOrderCodes.size() > 0) {
            listSendOrderInfo = orderInfoMapper.getOrderInfoForSignOrder(listDispatchOrderCodes);
        }
        if (listDirectOrderCodes.size() > 0) {
            listLogistics = logisticsMapper.getLogisticsForSignOrder(listDirectOrderCodes);
        }

        for (MerchantOrder merchantOrder : listOrder) {
            MerchantOrderSignVo signVo = new MerchantOrderSignVo();
            signVo.setOrderNumber(merchantOrder.getOrderNumber());
            signVo.setMerchantCode(merchantOrder.getMerchantCode());
            signVo.setMerchantName(merchantOrder.getMerchantName());
            signVo.setMaterialCode(merchantOrder.getMaterialCode());
            signVo.setMaterialName(merchantOrder.getMaterialName());
            signVo.setRemark(merchantOrder.getRemarks());
            if (merchantOrder.getMetrialType() == (byte) 0) //硬件分配发货
            {
                if (listSendOrderInfo != null) {
                    for (OrderInfo o : listSendOrderInfo) {
                        if (o.getOrderCode().equals(merchantOrder.getDispatchOrderNumber())) {
                            signVo.setFactoryName(o.getWarehouseName());
                            signVo.setShipmentQuantity(String.valueOf(o.getCountLogistiss() == null ? 0 : o.getCountLogistiss()));
                            signVo.setShipmentTime(df.format(o.getCreatedDate()));
                            signVo.setLogisticsNo(o.getLogistisNo());
                            signVo.setLogisticsCpy(o.getLogistisCpy());
                            signVo.setAddress(o.getAddress());
                            signVo.setContacts(o.getContacts());
                            signVo.setMobile(o.getMobile());
                        }

                    }
                }

            } else {
                if (listLogistics != null) {
                    for (Logistics l : listLogistics) {
                        if (l.getServiceCode().equals(merchantOrder.getDispatchOrderNumber())) {
                            signVo.setFactoryName("");
                            signVo.setShipmentQuantity(String.valueOf(l.getShipmentsQuantity() == null ? 0 : l.getShipmentsQuantity()));
                            signVo.setShipmentTime(df.format(l.getCreatedDate()));
                            signVo.setLogisticsNo(l.getOrderNumber());
                            signVo.setLogisticsCpy(l.getCompany());
                            signVo.setAddress(l.getAddress());
                            signVo.setContacts(l.getName());
                            signVo.setMobile(l.getMobile());
                        }
                    }
                }

            }

            listSignOrderVo.add(signVo);
        }

        //插入bs_merchant_order_sign表
        List<BsMerchantOrderSign> orderSignList = new ArrayList<BsMerchantOrderSign>();
        for (MerchantOrderSignVo signVo : listSignOrderVo) {
            BsMerchantOrderSign orderSign = new BsMerchantOrderSign();
            orderSign.setMerchantOrderNumber(signVo.getOrderNumber());
            orderSign.setMerchantSignNumber(receiveOrderNo);
            orderSign.setCreatedBy(signVo.getMerchantCode());
            orderSign.setUpdatedBy(signVo.getMerchantCode());
            orderSign.setCreatedDate(new Date());
            orderSign.setUpdatedDate(new Date());
            orderSign.setSignUrl("");
            orderSign.setDeletedFlag("N");
            orderSignList.add(orderSign);
        }
        orderSignService.batchAddMerchantOrderSign(orderSignList);

        return listSignOrderVo;

    }


    /**
     * 查询商户订单列表
     *
     * @param merchantOrder
     * @return
     */
    public List<MerchantOrder> getMerchantOrderList(MerchantOrder merchantOrder) {
        LOGGER.info("查询订单列表入参:", merchantOrder);
        List<MerchantOrder> result;
        result = merchantOrderMapper.select(merchantOrder);
        return result;
    }


    /**
     * 根据订单号获取订单详情
     *
     * @param merchantOrderNumber
     * @return
     */
    public MerchantOrder getMerchantOrderByMerchantOrderNumber(String merchantOrderNumber) {
        LOGGER.info("根据订单号查询订单。", merchantOrderNumber);
        MerchantOrder result;
        MerchantOrder merchantOrder = new MerchantOrder();
        merchantOrder.setOrderNumber(merchantOrderNumber);
        merchantOrder.setDeletedFlag("N");
        result = merchantOrderMapper.selectOne(merchantOrder);
        if (StringUtil.isEmpty(result)) {
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_RECORD_UNUSUAL);
        }
        DealerUserInfo dealerUserInfo = dealerUserInfoService.getDealerUserInfoByMerchantCode(result.getMerchantCode());
        result.setMerchantName(dealerUserInfo.getMerchantName());
        //填充订单详情
        MerchantOrderDetail merchantOrderDetail = new MerchantOrderDetail();
        merchantOrderDetail.setMerchantOrderNumber(result.getOrderNumber());
        List<MerchantOrderDetail> merchantOrderDetailList;
        List<Logistics> logisticsList = new ArrayList<>();
        Logistics logistics;
        merchantOrderDetailList = merchantOrderDetailMapper.select(merchantOrderDetail);
        //merchantOrder.setMerchantOrderDetailInfo(merchantOrderDetailMapper.selqianectOne(merchantOrderDetail));
        if (!StringUtil.isEmpty(merchantOrderDetailList) && merchantOrderDetailList.size() > 0) {
            //填充产品当前价格
            for (int j = 0; j < merchantOrderDetailList.size(); j++) {
                Double productAmount = 0.0;
                List<ProductHistoryPrice> productHistoryPriceList = productHistoryPriceService.getProductHistoryPriceByProductCode(merchantOrderDetailList.get(j).getProductCode());
                for (ProductHistoryPrice price : productHistoryPriceList) {
                    productAmount = productAmount + price.getPrice();
                }
                merchantOrderDetailList.get(j).setProductAmount(productAmount);
                Product product = productService.getProductByProductCode(merchantOrderDetailList.get(j).getProductCode());
                merchantOrderDetailList.get(j).setProductName(product.getName());
                merchantOrderDetailList.get(j).setProductSpecification(product.getSpecification());
            }

            //填充发货数量
            for (int i = 0; i < merchantOrderDetailList.size(); i++) {
                if (merchantOrderDetailList.get(i).getDispatchOrderNumber() != null) {
                    Integer shipmentsQuantity = orderInfoMapper.getShipmentsQuantityByOrderCode(merchantOrderDetailList.get(i).getDispatchOrderNumber());
                    merchantOrderDetailList.get(i).setShipmentsQuantity(shipmentsQuantity);
                    if (shipmentsQuantity == 0) {
                        Logistics logistics1 = new Logistics();
                        logistics1.setServiceCode(merchantOrderDetailList.get(i).getDispatchOrderNumber());
                        List<Logistics> logisticsList1 = logisticsMapper.select(logistics1);
                        if (logisticsList1.size() > 0) {
                            Integer ShipmentsQuantity = 0;
                            for (int j = 0; j < logisticsList1.size(); j++) {
                                if (logisticsList1.get(j).getShipmentsQuantity() != null) {
                                    ShipmentsQuantity = ShipmentsQuantity + logisticsList1.get(j).getShipmentsQuantity();
                                }
                            }
                            merchantOrderDetailList.get(i).setShipmentsQuantity(ShipmentsQuantity);
                        }
                    }                                      
                }
            }
            result.setMerchantOrderDetailList(merchantOrderDetailList);
            //查询物流信息List
            logistics = new Logistics();
            logistics.setServiceCode(merchantOrderDetailList.get(0).getDispatchOrderNumber());
            logisticsList.add(logistics);
            List<Logistics> listLogistics = logisticsService.getLogisticsInfoList(logisticsList);
            if(null != listLogistics && !listLogistics.isEmpty()){
            	OrderInfoDetail detailCondition = new OrderInfoDetail();
            	for(Logistics item:listLogistics){
            		if(null == item.getShipmentsQuantity() || item.getShipmentsQuantity() == 0){
            			detailCondition.setLogisticsId(item.getId().intValue());
                		detailCondition.setOrderCode(merchantOrderDetailList.get(0).getDispatchOrderNumber());
                		item.setShipmentsQuantity(orderInfoDetailMapper.getCountOrderInfoDetailByLogistics(detailCondition));
            		}      
            	}
            }  
            result.setLogisticsList(listLogistics);
            
            //填充订单物流信息
            Logistics logistics1 = new Logistics();
            logistics1.setServiceCode(result.getOrderNumber());
            result.setLogistics(logisticsService.getLogistics(logistics1));
        } else {
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_RECORD_UNUSUAL);
        }

        return result;
    }

    /**
     * 新增商户订单
     *
     * @param merchantOrder
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
    public Integer add(MerchantOrder merchantOrder) {
        LOGGER.info("新增商户订单入参:{}", merchantOrder);
        Integer result = 0;
        String orderCode;
        for (int i = 0; i < merchantOrder.getMerchantOrderDetailList().size(); i++) {
            orderCode = StringUtil.getOrderNo(); // 生成订单号
            merchantOrder.setOrderNumber(orderCode);
            merchantOrder.setStatus(MerchantOrderStatusEnum.ORDER_WAIT_CHECK.getCode());
            merchantOrder.setCreatedDate(new Date());
            merchantOrder.setOrderTime(new Date());
            merchantOrder.setUpdatedDate(new Date());
            merchantOrder.setDeletedFlag("N");
            merchantOrder.getMerchantOrderDetailList().get(i).setMerchantOrderNumber(orderCode);
            merchantOrder.getMerchantOrderDetailList().get(i).setCreatedBy(merchantOrder.getCreatedBy());
            merchantOrder.getMerchantOrderDetailList().get(i).setCreatedDate(new Date());
            merchantOrder.getMerchantOrderDetailList().get(i).setUpdatedBy(merchantOrder.getUpdatedBy());
            merchantOrder.getMerchantOrderDetailList().get(i).setUpdatedDate(new Date());
            merchantOrder.getMerchantOrderDetailList().get(i).setDeletedFlag("N");
            merchantOrder.setTotalOrder(merchantOrder.getMerchantOrderDetailList().get(i).getOrderQuantity());
            merchantOrder.setTotalAmount(merchantOrder.getMerchantOrderDetailList().get(i).getProductAmount() * merchantOrder.getMerchantOrderDetailList().get(i).getOrderQuantity());
            result = merchantOrderMapper.insert(merchantOrder);
            merchantOrderDetailMapper.insert(merchantOrder.getMerchantOrderDetailList().get(i));
            //查询地址是否存在
            List<Address> addressesList = addressService.getAddressList(merchantOrder.getLogistics().getReceiveAddress());
            if (!StringUtil.isEmpty(addressesList) && addressesList.size() < 1) {
                merchantOrder.getLogistics().getReceiveAddress().setMerchantCode(merchantOrder.getMerchantCode());
                addressService.add(merchantOrder.getLogistics().getReceiveAddress());
                merchantOrder.getLogistics().setReceiveId(merchantOrder.getLogistics().getReceiveAddress().getId());
            } else {
                merchantOrder.getLogistics().setReceiveId(addressesList.get(0).getId());
            }
            //物流信息添加
            String logisticsCode = StringUtil.getOrderNo();
            merchantOrder.getLogistics().setCode(logisticsCode);
            merchantOrder.getLogistics().setType(LogisticsTypeEnum.MERCHANT_ORDER.getCode());
            merchantOrder.getLogistics().setServiceCode(merchantOrder.getOrderNumber());
            logisticsService.add(merchantOrder.getLogistics());
        }
        return result;
    }
    
    /**
     * 新增商户订单(填补老产品)
     *
     * @param merchantOrder
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
    public Integer addSplit(MerchantOrder merchantOrder) throws RpcServiceException{
    	
    	LOGGER.info("addSplit merchantOrder:{}", merchantOrder);
    	Integer result = 0;
    	
    	DealerUserInfo dealerUserInfo = dealerUserInfoService.getDealerUserInfoByMerchantCode(merchantOrder.getMerchantCode());
    	if(StringUtils.isEmpty(dealerUserInfo))
    	{
    		LOGGER.error("addSplit merchant code has been deleted!");
    		throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_DEVICE_MERCHANT_NO);
    	}
    	
    	Address bsAddress = merchantOrder.getLogistics().getReceiveAddress();
    	bsAddress.setMerchantCode(merchantOrder.getMerchantCode());
		bsAddress = addressService.addIfNotExist(bsAddress);
		if(StringUtils.isEmpty(bsAddress))
		{
			LOGGER.error("addSplit invalide address");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_ADDRESS);
		}
		
		List<String> listProductCode = new ArrayList<String>();
		List<String> listMaterialCode = new ArrayList<String>();
		Map<String,ProductSplit> mapProduct = new HashMap<String,ProductSplit>();
		Map<String,List<ProductSplitDetail>> mapProductDetail = new HashMap<String,List<ProductSplitDetail>>();
		Map<String,List<ProductSplitHistoryPrice>> mapProductPrice = new HashMap<String,List<ProductSplitHistoryPrice>>();
		Map<String,MaterialInfo> mapMaterialInfo = new HashMap<String,MaterialInfo>();
		
		for(MerchantOrderDetail detail:merchantOrder.getMerchantOrderDetailList())
		{
			//验证产品是否下架
			ProductSplit productSplit = getProductSplitBaseInfo(detail.getProductCode());
			if(StringUtils.isEmpty(productSplit))
			{
				LOGGER.error("addSplit invalid product");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PRODUCT);
			}
			//验证物料和产品的关系
			if(!isMaterialBelongProduct(detail.getProductCode(),detail.getMaterialCode(),null))
			{
				LOGGER.error("addSplit matrial of product has been deleted!");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_MATERIAL);
			}
			mapProduct.put(productSplit.getProductCode(), productSplit);
			listProductCode.add(detail.getProductCode());
			listMaterialCode.add(detail.getMaterialCode());
		}
    	
		mapProductDetail = getProductSplitDetail(listProductCode, mapProductDetail);
		mapProductPrice	 = getProductSplitPrice(listProductCode, mapProductPrice);
		mapMaterialInfo	 = getMaterialInfoList(listMaterialCode, mapMaterialInfo);
		
		try
		{
			//构造订单
			List<Logistics> listLogistics = new ArrayList<Logistics>();
			List<MerchantOrder> listMerchantOrder = new ArrayList<MerchantOrder>();
			List<MerchantOrderDetail> listMerchantOrderDetail = new ArrayList<MerchantOrderDetail>();
			List<EcMerchantOrder> listEcMerchantOrder = new ArrayList<EcMerchantOrder>();
			List<Product> listBsProduct = new ArrayList<Product>();
			List<ProductDetail> listBsProductDetail = new ArrayList<ProductDetail>();
			Map<Byte,ProductDetail> mapBsProductDetail = new HashMap<Byte,ProductDetail>();
			List<ProductHistoryPrice> listBsProductPrice = new ArrayList<ProductHistoryPrice>();
			Map<String,ProductHistoryPrice> mapBsProductPrice = new HashMap<String,ProductHistoryPrice>();
			for(MerchantOrderDetail detail:merchantOrder.getMerchantOrderDetailList())
			{
				String bsProductCode = Constants.BS_PRODUCT_CODE_PREFIX + SupplychainUtils.getDispatchOrderNumber(snowflakeWorker);
				String bsOrderCode = Constants.BS_ORDER_PREFIX + SupplychainUtils.getDispatchOrderNumber(snowflakeWorker);
				String logiCode = Constants.LOGI_ORDER_PREFIX + SupplychainUtils.getDispatchOrderNumber(snowflakeWorker);
				
				ProductSplit productSplit = mapProduct.get(detail.getProductCode());
				List<ProductSplitDetail> productDetail = mapProductDetail.get(detail.getProductCode());
				List<ProductSplitHistoryPrice> productPrice = mapProductPrice.get(detail.getProductCode());
				MaterialInfo materialInfo = mapMaterialInfo.get(detail.getMaterialCode());
				
				listLogistics.add(generatorLogistics(bsAddress.getId(),logiCode,bsOrderCode,dealerUserInfo));
				listMerchantOrderDetail.add(generatorMerchantOrderDetail(bsOrderCode,bsProductCode,detail,dealerUserInfo));
				listMerchantOrder.add(generatorMerchantOrder(bsOrderCode,merchantOrder.getHopeTime(),merchantOrder.getRemarks(),detail.getOrderQuantity(),productSplit.getPrice(),dealerUserInfo));
				listBsProduct.add(generatorProduct(bsProductCode,materialInfo,productSplit,dealerUserInfo));
				
				listBsProductDetail = generatorBsProductDetail(bsProductCode,materialInfo,detail,mapBsProductDetail,productDetail,dealerUserInfo,listBsProductDetail);
				listBsProductPrice	= generatorBsProductHistoryPrice(bsProductCode,materialInfo,detail,mapBsProductPrice,productPrice,dealerUserInfo,listBsProductPrice);
				listEcMerchantOrder.add(generatorEcMerchantOrder(bsOrderCode,detail.getOrderQuantity(),merchantOrder.getHopeTime(),merchantOrder.getRemarks(),
						productSplit,materialInfo,detail,bsAddress,dealerUserInfo));
			}
			logisticsService.batchAddLogistics(listLogistics);
			productService.batchSubmitBsProduct(listBsProduct);
			productService.batchSubmitBsProductDetail(listBsProductDetail);
			productService.batchSubmitBsProductPrice(listBsProductPrice);
			ecMerchantOrderService.batchAddEcMerchantOrder(listEcMerchantOrder);
			merchantOrderMapper.insertList(listMerchantOrder);
			merchantOrderDetailMapper.insertList(listMerchantOrderDetail);
			listProductCode = null;
			listMaterialCode = null;
			mapProduct = null;
			mapProductDetail = null;
			mapProductPrice = null;
			mapMaterialInfo = null;
			listLogistics = null;
			listMerchantOrder = null;
			listMerchantOrderDetail = null;
			listEcMerchantOrder = null;
			listBsProduct = null;
			listBsProductDetail = null;
			mapBsProductDetail = null;
			listBsProductPrice = null;
			mapBsProductPrice = null;
		}
		catch(Exception e)
		{
    		LOGGER.error(e.getMessage(),e);
			throw new RpcServiceException(e.getMessage());
		}	
        return result;
    }
    
    private EcMerchantOrder generatorEcMerchantOrder(String bsOrderCode,Integer OrderQuantity,Date hopeTime,String orderRemarks,
    		ProductSplit productSplit,MaterialInfo materialInfo,MerchantOrderDetail merchantOrderDetail,
    		Address bsAddress,DealerUserInfo userInfo)
    {
    	EcMerchantOrder ecMerchantOrder = new EcMerchantOrder();
    	ecMerchantOrder.setChannel(getChannelName(userInfo.getChannel()));
    	ecMerchantOrder.setMerchantCode(userInfo.getMerchantCode());
    	ecMerchantOrder.setMerchantName(userInfo.getMerchantName());
    	ecMerchantOrder.setOrderNumber(bsOrderCode);   	
    	ecMerchantOrder.setProductCode(productSplit.getProductCode());
    	ecMerchantOrder.setProductName(productSplit.getProductName());
    	ecMerchantOrder.setMaterialCode(merchantOrderDetail.getMaterialCode());
    	ecMerchantOrder.setMaterialName(merchantOrderDetail.getMaterialName());
    	ecMerchantOrder.setDeviceType(materialInfo.getDeviceType());
    	ecMerchantOrder.setPrice(productSplit.getPrice());
    	ecMerchantOrder.setOrderQuantity(OrderQuantity);
    	ecMerchantOrder.setCheckQuantity(0);
    	ecMerchantOrder.setDispatchOrderNumber("");
    	ecMerchantOrder.setAlreadyShipmentQuantity(0);
    	ecMerchantOrder.setShipmentTime("");
    	ecMerchantOrder.setShipmentQuantity("");
    	ecMerchantOrder.setSignQuantity(0);
    	ecMerchantOrder.setOweQuantity(0);
    	ecMerchantOrder.setTotalAmount(OrderQuantity*productSplit.getPrice());
    	ecMerchantOrder.setOrderTime(SupplychainUtils.getNowDateStringYMD(SupplychainUtils.getNowDate()));
    	ecMerchantOrder.setProductRemarks(merchantOrderDetail.getProductRemarks());
    	ecMerchantOrder.setOrderRemark(orderRemarks);
    	ecMerchantOrder.setCheckRemark("");
    	ecMerchantOrder.setCheckBy("");
    	ecMerchantOrder.setCheckTime("");
    	ecMerchantOrder.setStatus(MerchantOrderStatusEnum.ORDER_WAIT_CHECK.getName());
    	ecMerchantOrder.setAddressee(bsAddress.getName());
    	ecMerchantOrder.setMobile(bsAddress.getMobile());
    	ecMerchantOrder.setAddressDetail((StringUtils.isEmpty(bsAddress.getProvinceName())?"":bsAddress.getProvinceName())
    			+ (StringUtils.isEmpty(bsAddress.getCityName())?"":bsAddress.getCityName()) + (StringUtils.isEmpty(bsAddress.getAreaName())?"":bsAddress.getAreaName())
    					+ bsAddress.getAddress());
    	ecMerchantOrder.setCreatedBy(userInfo.getName());
    	ecMerchantOrder.setUpdatedBy(userInfo.getName());
    	ecMerchantOrder.setCreatedDate(SupplychainUtils.getNowDate());
    	ecMerchantOrder.setUpdatedDate(SupplychainUtils.getNowDate());
    	ecMerchantOrder.setDeletedFlag("N");
    	ecMerchantOrder.setMerchantCode(userInfo.getMerchantCode());
    	ecMerchantOrder.setLogisticsDesc("");
    	return ecMerchantOrder;	
    }
    
    private String getChannelName(Byte channel)
    {
    	if(channel.equals(DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_GHUI.getCode())){
    		return DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_GHUI.getName();
    	}else if(channel.equals(DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_OTHER.getCode())){
    		return DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_OTHER.getName();
    	}else if(channel.equals(DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_TMHUI.getCode())){
    		return DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_TMHUI.getName();
    	}else if(channel.equals(DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_CHANNEL.getCode())){
    		return DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_CHANNEL.getName();
    	}else if(channel.equals(DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_FIVE.getCode())){
    		return DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_FIVE.getName();
    	}else if(channel.equals(DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_SIX.getCode())){
    		return DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_SIX.getName();
    	}else if(channel.equals(DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_SEVER.getCode())){
    		return DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_SEVER.getName();
    	}else if(channel.equals(DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_EIGHT.getCode())){
    		return DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_EIGHT.getName();
    	}
    	return "";
    }
    
    private List<ProductHistoryPrice> generatorBsProductHistoryPrice(String bsProdoctCode,MaterialInfo materialInfo,MerchantOrderDetail merchantOrderDetail,Map<String,ProductHistoryPrice> mapBsProductPrice,List<ProductSplitHistoryPrice> ListProductSplitPrice,DealerUserInfo userInfo,List<ProductHistoryPrice> listBsProductPrice)
    {
    	mapBsProductPrice.clear();
    	ProductHistoryPrice optSplitPrice = new ProductHistoryPrice();
    	for(ProductSplitHistoryPrice splitPrice:ListProductSplitPrice)
    	{
    		ProductHistoryPrice bsProductPrice = new ProductHistoryPrice();
			bsProductPrice.setProductCode(bsProdoctCode);
			bsProductPrice.setTime(splitPrice.getTime());
			bsProductPrice.setType(splitPrice.getType());
			bsProductPrice.setPrice(splitPrice.getPrice());
			bsProductPrice.setCreatedBy(userInfo.getName());
			bsProductPrice.setUpdatedBy(userInfo.getName());
			bsProductPrice.setCreatedDate(SupplychainUtils.getNowDate());
			bsProductPrice.setUpdatedDate(SupplychainUtils.getNowDate());
			bsProductPrice.setDeletedFlag("N");
			bsProductPrice.setProductType(splitPrice.getProductType());
			bsProductPrice.setServiceType(splitPrice.getServiceType());
			bsProductPrice.setTaxRate(splitPrice.getTaxRate());
			bsProductPrice.setMaterialCode(splitPrice.getMaterialCode());
			bsProductPrice.setMaterialName(splitPrice.getMaterialName());
			if(splitPrice.getProductType().equals(String.valueOf(MaterialTypeEnum.MATERIAL_TYPE_HARD.getCode())))
			{
				if(materialInfo.getMaterialTypeId().equals(47))
				{
					bsProductPrice.setMaterialCode(merchantOrderDetail.getMaterialCode());
					bsProductPrice.setMaterialName(merchantOrderDetail.getMaterialName());
				}
			}
			if(splitPrice.getProductType().equals(String.valueOf(MaterialTypeEnum.MATERIAL_TYPE_PART.getCode())))
			{
				if(materialInfo.getMaterialTypeId().equals(46))
				{
					bsProductPrice.setMaterialCode(merchantOrderDetail.getMaterialCode());
					bsProductPrice.setMaterialName(merchantOrderDetail.getMaterialName());
				}
			}
			if(materialInfo.getMaterialCode().equals(splitPrice.getMaterialCode()))
			{
				BeanUtils.copyProperties(optSplitPrice, bsProductPrice);
			}
			mapBsProductPrice.put(bsProductPrice.getProductType(), bsProductPrice);
    	}
    	
    	for(Map.Entry<String,ProductHistoryPrice> entry:mapBsProductPrice.entrySet())
		{
    		if(entry.getValue().getProductType().equals(optSplitPrice.getProductType()))
    		{
    			listBsProductPrice.add(optSplitPrice);
    		}
    		else
    		{
    			listBsProductPrice.add(entry.getValue());
    		}	
		}
    	return listBsProductPrice;
    }
    
    private List<ProductDetail> generatorBsProductDetail(String bsProdoctCode,MaterialInfo materialInfo,MerchantOrderDetail merchantOrderDetail,Map<Byte,ProductDetail> mapBsProductDetail,List<ProductSplitDetail> ListProductSplitDetail,DealerUserInfo userInfo,List<ProductDetail> listBsProductDetail)
    {
    	mapBsProductDetail.clear();
    	Boolean isAddHaredOrPart = false;
    	for(ProductSplitDetail splitDetail:ListProductSplitDetail)
    	{
    		ProductDetail bsProductDetail = new ProductDetail();
    		bsProductDetail.setProductCode(bsProdoctCode);
			bsProductDetail.setCreatedBy(userInfo.getName());
			bsProductDetail.setUpdatedBy(userInfo.getName());
			bsProductDetail.setCreatedDate(SupplychainUtils.getNowDate());
			bsProductDetail.setUpdatedDate(SupplychainUtils.getNowDate());
			bsProductDetail.setDeletedFlag("N");
			bsProductDetail.setServiceType(splitDetail.getServiceType());
			bsProductDetail.setType(Byte.valueOf(splitDetail.getProductType()));
			bsProductDetail.setMaterialCode(splitDetail.getMaterialCode());
			bsProductDetail.setMaterialName(splitDetail.getMaterialName());
			if(splitDetail.getProductType().equals(String.valueOf(MaterialTypeEnum.MATERIAL_TYPE_HARD.getCode())))
			{
				if(materialInfo.getMaterialTypeId().equals(47) || materialInfo.getMaterialTypeId().equals(46))
				{
					bsProductDetail.setMaterialCode(merchantOrderDetail.getMaterialCode());
					bsProductDetail.setMaterialName(merchantOrderDetail.getMaterialName());
					//配件和硬件只能选择一个添加
					if(isAddHaredOrPart)
					{
						continue;
					}
					isAddHaredOrPart = true;
					//sb产品  物料定义配件  产品拆分中定义为硬件 这里按照物料类别转化下
					Byte redifineType = (materialInfo.getMaterialTypeId().equals(47))?Byte.valueOf(MaterialTypeEnum.MATERIAL_TYPE_HARD.getCode()):Byte.valueOf(MaterialTypeEnum.MATERIAL_TYPE_PART.getCode());
					bsProductDetail.setType(redifineType);
					mapBsProductDetail.put(bsProductDetail.getType(), bsProductDetail);	
				}
			}
			else if(splitDetail.getProductType().equals(String.valueOf(MaterialTypeEnum.MATERIAL_TYPE_PART.getCode())))
			{
				if(materialInfo.getMaterialTypeId().equals(46) || materialInfo.getMaterialTypeId().equals(47))
				{
					bsProductDetail.setMaterialCode(merchantOrderDetail.getMaterialCode());
					bsProductDetail.setMaterialName(merchantOrderDetail.getMaterialName());
					//配件和硬件只能选择一个添加
					if(isAddHaredOrPart)
					{
						continue;
					}
					isAddHaredOrPart = true;
					//sb产品  物料定义配件  产品拆分中定义为硬件 这里按照物料类别转化下
					Byte redifineType = (materialInfo.getMaterialTypeId().equals(47))?Byte.valueOf(MaterialTypeEnum.MATERIAL_TYPE_HARD.getCode()):Byte.valueOf(MaterialTypeEnum.MATERIAL_TYPE_PART.getCode());
					bsProductDetail.setType(redifineType);
					mapBsProductDetail.put(bsProductDetail.getType(), bsProductDetail);	
				}
			}
			else
			{
				mapBsProductDetail.put(bsProductDetail.getType(), bsProductDetail);
			}
    	}
    	
    	for(Map.Entry<Byte,ProductDetail> entry:mapBsProductDetail.entrySet())
		{
			listBsProductDetail.add(entry.getValue());
		}
    	
    	return listBsProductDetail;
    }
    
    private Logistics generatorLogistics(Long addressId,String logisticsCode,String orderCode,DealerUserInfo userInfo)
    {
    	Logistics logistics = new Logistics();
    	logistics.setReceiveId(addressId);
    	logistics.setCode(logisticsCode);
    	logistics.setType(Byte.valueOf("6"));
    	logistics.setServiceCode(orderCode);
    	logistics.setCreatedBy(userInfo.getMerchantCode());
    	logistics.setUpdatedBy(userInfo.getMerchantCode());
    	logistics.setCreatedDate(SupplychainUtils.getNowDate());
    	logistics.setUpdatedDate(SupplychainUtils.getNowDate());
    	logistics.setDeletedFlag("N");
    	return logistics;
    }
    
    private MerchantOrderDetail generatorMerchantOrderDetail(String orderCode, String productCode,MerchantOrderDetail merchantOrderDetail,DealerUserInfo userInfo)
    {
    	MerchantOrderDetail orderDetail = new MerchantOrderDetail();
    	orderDetail.setMerchantOrderNumber(orderCode);
		orderDetail.setProductCode(productCode);
		orderDetail.setOrderQuantity(merchantOrderDetail.getOrderQuantity());
		orderDetail.setCheckQuantity(null);
		orderDetail.setDispatchOrderNumber(null);
		orderDetail.setCreatedBy(userInfo.getName());
		orderDetail.setUpdatedBy(userInfo.getName());
		orderDetail.setCreatedDate(SupplychainUtils.getNowDate());
		orderDetail.setUpdatedDate(SupplychainUtils.getNowDate());
		orderDetail.setDeletedFlag("N");
		orderDetail.setCheckBy("");
		orderDetail.setCheckTime(null);
		orderDetail.setSubjectId(null);
		orderDetail.setInsure(null);
		orderDetail.setProductRemarks(merchantOrderDetail.getProductRemarks());
		return orderDetail;
    }
    
    private MerchantOrder generatorMerchantOrder(String orderCode,Date hopeTime,String remark,Integer OrderQuantity,Double productPrice,DealerUserInfo userInfo)
    {
    	MerchantOrder order = new MerchantOrder();
    	order.setOrderNumber(orderCode);
		order.setOrderTime(SupplychainUtils.getNowDate());
		order.setMerchantCode(userInfo.getMerchantCode());
		order.setHopeTime(hopeTime);
		order.setTotalOrder(OrderQuantity);
		order.setTotalCheck(null);
		order.setTotalAmount(OrderQuantity*productPrice);
		order.setStatus(MerchantOrderStatusEnum.ORDER_WAIT_CHECK.getCode());
		order.setRemarks(remark);
		order.setCreatedBy(userInfo.getName());
		order.setUpdatedBy(userInfo.getName());
		order.setCreatedDate(SupplychainUtils.getNowDate());
		order.setUpdatedDate(SupplychainUtils.getNowDate());
		order.setDeletedFlag("N");
		return order;
    }
    
    private Product generatorProduct(String productCode,MaterialInfo materialInfo,ProductSplit productSplit,DealerUserInfo userInfo)
    {
    	Product bsProduct = new Product();
    	bsProduct.setCode(productCode);
		bsProduct.setName(materialInfo.getMaterialName());
		bsProduct.setSpecification(null);
		bsProduct.setPackageOne(productSplit.getPackageOne());
		bsProduct.setType(String.valueOf(materialInfo.getDeviceTypeId()));
		bsProduct.setChannel(productSplit.getChannel());
		bsProduct.setStatus(Byte.valueOf("1"));
		bsProduct.setCreatedBy(userInfo.getName());
		bsProduct.setUpdatedBy(userInfo.getName());
		bsProduct.setCreatedDate(SupplychainUtils.getNowDate());
		bsProduct.setUpdatedDate(SupplychainUtils.getNowDate());
		bsProduct.setDeletedFlag("N");
		bsProduct.setProductSplitId(productSplit.getId().longValue());
		bsProduct.setServiceType(productSplit.getServiceType());
		bsProduct.setServiceTime(productSplit.getServiceTime());
		bsProduct.setMerchantCode(productSplit.getMerchantCode());
		bsProduct.setMerchantName(productSplit.getMerchantName());
		bsProduct.setAlias(null);
		bsProduct.setDeviceQuantity(productSplit.getDeviceQuantity());
		bsProduct.setHardwareContainSource(productSplit.getHardwareContainSource());
		bsProduct.setSourceProportion(productSplit.getSourceProportion());
		bsProduct.setNotSourceProportion(productSplit.getNotSourceProportion());
		bsProduct.setCarType(productSplit.getCarType());
		return bsProduct;
    }
    
    private ProductSplit getProductSplitBaseInfo(String productCode)
    {
    	ProductSplit productSplit = productSplitService.getProductSplitBaseInfo(productCode);
    	productSplitService.getProductSplitPrice(productSplit);
    	return productSplit;
    }
    
    private boolean isMaterialBelongProduct(String productCode,String materialCode,Map<String,Byte> mapMaterialType)
    {
    	return productSplitService.isMaterialBelongProduct(productCode, materialCode, mapMaterialType);
    }
    
    private Map<String,List<ProductSplitDetail>> getProductSplitDetail(List<String> listProductCode,Map<String,List<ProductSplitDetail>>  mapProductDetail)
    {
    	return productSplitService.listProductSplitDetail(listProductCode, mapProductDetail);
    }
    
    private Map<String,List<ProductSplitHistoryPrice>> getProductSplitPrice(List<String> listProductCode,Map<String,List<ProductSplitHistoryPrice>> mapProductPrice)
    {
    	return productSplitService.listProductSplitPrice(listProductCode, mapProductPrice);
    }
    
    private Map<String,MaterialInfo> getMaterialInfoList(List<String> listMaterialCode,Map<String,MaterialInfo> mapMaterialInfo)
    {
    	return productSplitService.listMaterialInfo(listMaterialCode, mapMaterialInfo);
    }
   
    /**
     * 新增商户订单(填补老产品)
     *
     * @param merchantOrder
     * @return
     */
   /* @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
    public Integer addSplit(MerchantOrder merchantOrder) throws RpcServiceException{
    	
    	LOGGER.info("新增商户订单入参:{}", merchantOrder);
    	Integer result = 0;
    	try
    	{
	        String orderCode;
	        String productCode;
	        Integer orderNumber = 0;
	        Date dateNew = new Date();
	        //查询地址是否存在
	        merchantOrder.getLogistics().getReceiveAddress().setMerchantCode(merchantOrder.getMerchantCode());
	        List<Address> addressesList = addressService.getAddressList(merchantOrder.getLogistics().getReceiveAddress());
	        if (!StringUtil.isEmpty(addressesList) && addressesList.size() > 0) {
	            merchantOrder.getLogistics().setReceiveId(addressesList.get(0).getId());
	        } else {
	            addressService.add(merchantOrder.getLogistics().getReceiveAddress());
	            merchantOrder.getLogistics().setReceiveId(merchantOrder.getLogistics().getReceiveAddress().getId());
	        }
	        
	        for (int i = 0; i < merchantOrder.getMerchantOrderDetailList().size(); i++) {
	            orderNumber++;
	            //查询产品拆分详情填充服务物料
	            ProductSplitDetail productSplitDetail = new ProductSplitDetail();
	            productSplitDetail.setProductCode(merchantOrder.getMerchantOrderDetailList().get(i).getProductCode());
	            productSplitDetail.setDeletedFlag("N");
	            List<ProductSplitDetail> productSplitDetailList = productSplitDetailMapper.select(productSplitDetail);
	            ProductSplitHistoryPrice historyPriceDate = new ProductSplitHistoryPrice();
	            historyPriceDate.setProductCode(merchantOrder.getMerchantOrderDetailList().get(i).getProductCode());
	            historyPriceDate.setDeletedFlag("N");
	            historyPriceDate.setType((byte)0);
	            List<ProductSplitHistoryPrice> splitHistoryPriceList = productSplitHistoryPriceMapper.select(historyPriceDate);
	            //查询产品拆分表信息
	            ProductSplit productSplit = new ProductSplit();
	            productSplit.setProductCode(merchantOrder.getMerchantOrderDetailList().get(i).getProductCode());
	            ProductSplit productSplitInfo = productSplitMapper.selectOne(productSplit);
	
	           // productCode = "GLSX" + StringUtil.getOrderNo() + orderNumber;//生成产品编号
	           // productCode = "GLSX" + snowflakeWorker.nextId();
	           // orderCode = StringUtil.getOrderNo() + orderNumber; // 生成订单号
	           // orderCode = snowflakeWorker.nextId()+"";
	            productCode = Constants.BS_PRODUCT_CODE_PREFIX + SupplychainUtils.getDispatchOrderNumber(snowflakeWorker);
	            orderCode = Constants.BS_ORDER_PREFIX + SupplychainUtils.getDispatchOrderNumber(snowflakeWorker);
	            merchantOrder.setOrderNumber(orderCode);
	            merchantOrder.setStatus(MerchantOrderStatusEnum.ORDER_WAIT_CHECK.getCode());
	            merchantOrder.setCreatedDate(dateNew);
	            merchantOrder.setOrderTime(dateNew);
	            merchantOrder.setUpdatedDate(dateNew);
	            merchantOrder.setDeletedFlag("N");
	            merchantOrder.getMerchantOrderDetailList().get(i).setProductCode(productCode);
	            merchantOrder.getMerchantOrderDetailList().get(i).setMerchantOrderNumber(orderCode);
	            merchantOrder.getMerchantOrderDetailList().get(i).setCreatedBy(merchantOrder.getCreatedBy());
	            merchantOrder.getMerchantOrderDetailList().get(i).setCreatedDate(dateNew);
	            merchantOrder.getMerchantOrderDetailList().get(i).setUpdatedBy(merchantOrder.getUpdatedBy());
	            merchantOrder.getMerchantOrderDetailList().get(i).setUpdatedDate(dateNew);
	            merchantOrder.getMerchantOrderDetailList().get(i).setDeletedFlag("N");
	            merchantOrder.setTotalOrder(merchantOrder.getMerchantOrderDetailList().get(i).getOrderQuantity());
	            merchantOrder.setTotalAmount(merchantOrder.getMerchantOrderDetailList().get(i).getProductAmount() * merchantOrder.getMerchantOrderDetailList().get(i).getOrderQuantity());
	            result = merchantOrderMapper.insert(merchantOrder);
	            merchantOrderDetailMapper.insert(merchantOrder.getMerchantOrderDetailList().get(i));
	
	            //物流信息添加
	            String logisticsCode = StringUtil.getOrderNo();
	            merchantOrder.getLogistics().setCode(logisticsCode);
	            merchantOrder.getLogistics().setType(LogisticsTypeEnum.MERCHANT_ORDER.getCode());
	            merchantOrder.getLogistics().setServiceCode(merchantOrder.getOrderNumber());
	            logisticsService.add(merchantOrder.getLogistics());
	
	
	            //根据物料编号获取对应的设备类型
	            Integer deviceTypeId = null;
	            Material material = new Material();
	            material.setMaterialCode1(merchantOrder.getMerchantOrderDetailList().get(i).getMaterialCode());
	            List<Material> materialList = iMaterialDubboService.list(material);
	            if (materialList != null && materialList.size() > 0) {
	                deviceTypeId = materialList.get(0).getDeviceTypeId();
	            }
	
	            //填补产品信息
	            Product product = new Product();
	            product.setServiceType(productSplitInfo.getServiceType());
	            if(productSplitInfo.getMerchantCode() != null && productSplitInfo.getMerchantCode() != ""){//填充商户信息
	                product.setMerchantCode(productSplitInfo.getMerchantCode());
	                product.setMerchantName(productSplitInfo.getMerchantName());
	            }
	            product.setAlias(productSplitInfo.getAlias());
	            product.setDeviceQuantity(productSplitInfo.getDeviceQuantity());
	            product.setServiceTime(productSplitInfo.getServiceTime());
	            product.setPackageOne(productSplitInfo.getPackageOne());
	            //填充硬件是否有缘无源
	            if(productSplitInfo.getHardwareContainSource() != null && productSplitInfo.getHardwareContainSource() != ""){
	                product.setHardwareContainSource(productSplitInfo.getHardwareContainSource());
	                product.setSourceProportion(productSplitInfo.getSourceProportion());
	                product.setNotSourceProportion(productSplitInfo.getNotSourceProportion());
	            }
	            if(productSplitInfo.getCarType() != null){//填充车机类型
	                product.setCarType(productSplitInfo.getCarType());
	            }
	            product.setCode(productCode);
	            product.setName(merchantOrder.getMerchantOrderDetailList().get(i).getMaterialName());
	            product.setType(String.valueOf(deviceTypeId));
	            product.setChannel(productSplitInfo.getChannel());
	            product.setStatus(ProductStatusEnum.PRODUCT_STATUS_PUTAWAY.getCode());
	            if (!StringUtil.isEmpty(productSplitInfo)) {
	                product.setProductSplitId(productSplitInfo.getId());
	            }
	            product.setCreatedBy(merchantOrder.getCreatedBy());
	            product.setCreatedDate(dateNew);
	            product.setUpdatedBy(merchantOrder.getUpdatedBy());
	            product.setUpdatedDate(dateNew);
	            product.setDeletedFlag("N");
	            productMapper.insertProduct(product);
	
	            if (productSplitDetailList != null && productSplitDetailList.size() > 0) {
	                Integer sumKey = 0;
	                for (int j = 0; j < productSplitDetailList.size(); j++) {
	                    //填补产品详情信息
	                    ProductDetail productDetail = new ProductDetail();
	                    productDetail.setServiceType(productSplitDetailList.get(j).getServiceType());
	                    productDetail.setProductCode(productCode);
	                    productDetail.setMaterialCode(productSplitDetailList.get(j).getMaterialCode());
	                    productDetail.setMaterialName(productSplitDetailList.get(j).getMaterialName());
	                    if ("0".equals(productSplitDetailList.get(j).getProductType()) &&
	                            merchantOrder.getMerchantOrderDetailList().get(i).getMaterialCode().equals(productSplitDetailList.get(j).getMaterialCode())) {
	                        sumKey++;
	                        productDetail.setType((byte) 0);
	                    } else if ("1".equals(productSplitDetailList.get(j).getProductType())) {
	                        productDetail.setType((byte) 1);
	                    } else if ("2".equals(productSplitDetailList.get(j).getProductType())) {
	                        productDetail.setType((byte) 2);
	                    } else if ("3".equals(productSplitDetailList.get(j).getProductType())) {
	                        productDetail.setType((byte) 3);
	                    } else if ("4".equals(productSplitDetailList.get(j).getProductType())) {
	                        productDetail.setType((byte) 4);
	                    } else if ("5".equals(productSplitDetailList.get(j).getProductType())) {
	                        productDetail.setType((byte) 5);
	                    } else if ("6".equals(productSplitDetailList.get(j).getProductType())) {
	                        productDetail.setType((byte) 6);
	                    } else if ("7".equals(productSplitDetailList.get(j).getProductType()) &&
	                            merchantOrder.getMerchantOrderDetailList().get(i).getMaterialCode().equals(productSplitDetailList.get(j).getMaterialCode())) {
	                        sumKey++;
	                        productDetail.setType((byte) 7);
	                    }
	
	                    if (sumKey == 0) {
	                        int sum = 0;
	                        for (int o = 0; o < productSplitDetailList.size(); o++) {
	                            if (merchantOrder.getMerchantOrderDetailList().get(i).getMaterialCode().equals(productSplitDetailList.get(o).getMaterialCode())) {
	                                sum++;
	                            }
	                            if(productSplitDetailList.size() - o == 1 && sum == 0){
	                                material = new Material();
	                                material.setMaterialCode1(merchantOrder.getMerchantOrderDetailList().get(i).getMaterialCode());
	                                materialList = iMaterialDubboService.list(material);
	                                if (materialList.get(0).getMaterialTypeId().equals(46)) {
	                                    productDetail.setType((byte) 7);
	                                } else if (materialList.get(0).getMaterialTypeId().equals(47)) {
	                                    productDetail.setType((byte) 0);
	                                }
	                                productDetail.setMaterialCode(merchantOrder.getMerchantOrderDetailList().get(i).getMaterialCode());
	                                productDetail.setMaterialName(merchantOrder.getMerchantOrderDetailList().get(i).getMaterialName());
	                                sumKey++;
	                            }
	                        }
	                    }
	
	                    if (productDetail.getType() != null) {
	                        productDetail.setCreatedBy(merchantOrder.getCreatedBy());
	                        productDetail.setCreatedDate(dateNew);
	                        productDetail.setUpdatedBy(merchantOrder.getUpdatedBy());
	                        productDetail.setUpdatedDate(dateNew);
	                        productDetail.setDeletedFlag("N");
	                        productDetailMapper.insert(productDetail);
	                        //填充产品历史价格信息
	                        ProductHistoryPrice productHistoryPrice = new ProductHistoryPrice();
	                        productHistoryPrice.setServiceType(productSplitDetailList.get(j).getServiceType());
	                        productHistoryPrice.setProductType(productSplitDetailList.get(j).getProductType());
	                        productHistoryPrice.setProductCode(productCode);
	                        productHistoryPrice.setMaterialCode(productDetail.getMaterialCode());
	                        productHistoryPrice.setMaterialName(productDetail.getMaterialName());
	                        if(splitHistoryPriceList != null && splitHistoryPriceList.size() > 0){
	                            productHistoryPrice.setTime(splitHistoryPriceList.get(0).getTime());
	                        }else{
	                            productHistoryPrice.setTime(merchantOrder.getOrderTime());
	                        }
	                        productHistoryPrice.setType(ProductHistoryPriceEnum.PRODUCT_HISTORY_PRICE_NOW.getCode());
	                        ProductSplitHistoryPrice productSplitHistoryPrice = new ProductSplitHistoryPrice();
	                        productSplitHistoryPrice.setProductCode(productSplitInfo.getProductCode());
	                        productSplitHistoryPrice.setType(ProductHistoryPriceEnum.PRODUCT_HISTORY_PRICE_NOW.getCode());
	                        if (productDetail.getType() == (byte) 0 || productDetail.getType() == (byte) 7) {
	                            productSplitHistoryPrice.setProductType(productDetail.getType() + "");
	                            List<ProductSplitHistoryPrice> productSplitHistoryPriceList = productSplitHistoryPriceMapper.select(productSplitHistoryPrice);
	                            if (productSplitHistoryPriceList != null && productSplitHistoryPriceList.size() > 0) {
	                                productHistoryPrice.setPrice(productSplitHistoryPriceList.get(0).getPrice());
	                                productHistoryPrice.setTaxRate(productSplitHistoryPriceList.get(0).getTaxRate());
	                            } else {
	                                productHistoryPrice.setPrice(0.0);
	                            }
	                        } else {
	                            productSplitHistoryPrice.setMaterialCode(productDetail.getMaterialCode());
	                            productSplitHistoryPrice.setProductType(productDetail.getType()+"");
	                            productSplitHistoryPrice.setDeletedFlag("N");
	                            productSplitHistoryPrice = productSplitHistoryPriceMapper.selectOne(productSplitHistoryPrice);
	                            productHistoryPrice.setPrice(productSplitHistoryPrice.getPrice());
	                            productHistoryPrice.setTaxRate(productSplitHistoryPrice.getTaxRate());
	                        }
	                        productHistoryPrice.setCreatedBy(merchantOrder.getCreatedBy());
	                        productHistoryPrice.setCreatedDate(dateNew);
	                        productHistoryPrice.setUpdatedBy(merchantOrder.getUpdatedBy());
	                        productHistoryPrice.setUpdatedDate(dateNew);
	                        productHistoryPrice.setDeletedFlag("N");
	                        productHistoryPriceMapper.insert(productHistoryPrice);
	                    }
	                }
	            }
	        }
    	}
    	catch(Exception e)
		{
    		LOGGER.error(e.getMessage(),e);
    		LOGGER.info("StatMerchantStockServiceImpl::setMerchantStock handle failed");
			throw new RpcServiceException(e.getMessage());
		}	
        return result;
    }*/


    /**
     * 修改订单状态
     *
     * @param merchantOrder
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
    public Integer updateOrderStatus(MerchantOrder merchantOrder) throws RpcServiceException {
        LOGGER.info("修改商户订单的编号:{}", merchantOrder.getOrderNumber());
        Integer result = 0;
        try
        {
        	EcMerchantOrder ecMerchantOrder = new EcMerchantOrder();
            merchantOrder.setUpdatedDate(new Date());
            result = merchantOrderMapper.updateByOrderNumber(merchantOrder);
            MerchantOrderDetail merchantOrderDetail = new MerchantOrderDetail();
            merchantOrderDetail.setMerchantOrderNumber(merchantOrder.getOrderNumber());
            MerchantOrderDetail merchantOrderDetailInfo = merchantOrderDetailMapper.selectOne(merchantOrderDetail);
            if (!StringUtil.isEmpty(merchantOrderDetailInfo)) {
                MerchantOrderDetail mod = new MerchantOrderDetail();
                mod.setId(merchantOrderDetailInfo.getId());
                if (merchantOrder.getStatus().equals(MerchantOrderStatusEnum.ORDER_ALREADY_COUNTERMAND.getCode())) {
                    mod.setCheckBy(merchantOrder.getUpdatedBy());
                    ecMerchantOrder.setCheckBy(merchantOrder.getUpdatedBy());
                }
                ecMerchantOrder.setCheckTime(SupplychainUtils.getNowDateStringYMD(new Date()));
                mod.setCheckTime(new Date());
                merchantOrderDetailMapper.updateById(mod);
            }
             
            ecMerchantOrder.setOrderNumber(merchantOrder.getOrderNumber());
            ecMerchantOrder.setStatus(getMerchantOrderStatusName(merchantOrder.getStatus()));
            ecMerchantOrderService.updateEcMerchantOrderByOrderNumber(ecMerchantOrder);
        }
        catch(Exception e)
        {
        	LOGGER.error(e.getMessage(),e);
			throw new RpcServiceException(e.getMessage());
        } 
        return result;
    }
    
    private String getMerchantOrderStatusName(Byte statusCode)
    {
    	if(statusCode.equals(MerchantOrderStatusEnum.ORDER_ALREADY_COUNTERMAND.getCode())){
    		return MerchantOrderStatusEnum.ORDER_ALREADY_COUNTERMAND.getName();
    	}else if(statusCode.equals(MerchantOrderStatusEnum.ORDER_WAIT_CHECK.getCode())){
    		return MerchantOrderStatusEnum.ORDER_WAIT_CHECK.getName();
    	}else if(statusCode.equals(MerchantOrderStatusEnum.ORDER_WAIT_SHIPMENTS.getCode())){
    		return MerchantOrderStatusEnum.ORDER_WAIT_SHIPMENTS.getName();
    	}else if(statusCode.equals(MerchantOrderStatusEnum.ORDER_WAIT_RECEIVE.getCode())){
    		return MerchantOrderStatusEnum.ORDER_WAIT_RECEIVE.getName();
    	}else if(statusCode.equals(MerchantOrderStatusEnum.ORDER_PORTION_RECEIVE.getCode())){
    		return MerchantOrderStatusEnum.ORDER_PORTION_RECEIVE.getName();
    	}else if(statusCode.equals(MerchantOrderStatusEnum.ORDER_ALREADY_FINISH.getCode())){
    		return MerchantOrderStatusEnum.ORDER_ALREADY_FINISH.getName();
    	}else if(statusCode.equals(MerchantOrderStatusEnum.ORDER_ALREADY_DRAW_A_BILL.getCode())){
    		return MerchantOrderStatusEnum.ORDER_ALREADY_DRAW_A_BILL.getName();
    	}else if(statusCode.equals(MerchantOrderStatusEnum.ORDER_ALREADY_INVALID.getCode())){
    		return MerchantOrderStatusEnum.ORDER_ALREADY_INVALID.getName();
    	}
    	return "";
    }

    /**
     * 根据备注获取订单信息（主要用于保活干系统同步上来的订单号存在remark字段中）
     *
     * @param remark
     * @return
     */
    public MerchantOrder getMerchantOrderByRemarkForExsystem(String remark) {
        LOGGER.info("MerchantOrderService::getMerchantOrderByRemark start remark:" + remark);
        MerchantOrder result = null;
        MerchantOrder condition = new MerchantOrder();
        condition.setRemarks(remark);
        condition.setDeletedFlag("N");
        result = merchantOrderMapper.selectOne(condition);
        LOGGER.info("MerchantOrderService::getMerchantOrderByRemark end result:{}", result);
        return result;
    }

    /**
     * 修改订单信息（主要用于保活干系统同步上来的订单信息）
     *
     * @param merchantOrder
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
    public Integer updateMerchantOrderStatusForExsystem(MerchantOrder merchantOrder) {
        LOGGER.info("MerchantOrderService::updateMerchantOrderStatusForExsystem start merchantOrder:{}", merchantOrder);
        if (StringUtils.isEmpty(merchantOrder.getId())) {
            return 0;
        }
        //判断如果订单作废 要找到对应的发货单 取消发货单  如果已经发货则不能作废
        if (merchantOrder.getStatus().equals(Byte.valueOf(MerchantOrderStatusEnum.ORDER_ALREADY_INVALID.getCode()))) {
            LOGGER.info("MerchantOrderService::updateMerchantOrderStatusForExsystem come here");
            MerchantOrderDetail detailCondition = new MerchantOrderDetail();
            detailCondition.setMerchantOrderNumber(merchantOrder.getOrderNumber());
            detailCondition.setDeletedFlag("N");
            MerchantOrderDetail merchantOrderDetail = merchantOrderDetailMapper.selectOne(detailCondition);
            if (!StringUtils.isEmpty(merchantOrderDetail)) {
                if (!StringUtils.isEmpty(merchantOrderDetail.getDispatchOrderNumber())) {
                    //查询发货单是否已经发货
                    OrderInfo orderInfo = orderInfoMapper.getOrderInfoByOrderCode(merchantOrderDetail.getDispatchOrderNumber());
                    if (!StringUtils.isEmpty(orderInfo)) {
                        int uiCount = orderInfoDetailMapper.getDeviceCountByOrderCode(orderInfo.getOrderCode());
                        if (uiCount == 0) {
                            orderInfo.setStatus(OrderStatusEnum.STATUS_CL.getValue());
                            orderInfo.setUpdatedDate(new Date());
                            orderInfoMapper.update(orderInfo);
                            merchantOrderMapper.updateByOrderNumber(merchantOrder);
                            LOGGER.info("MerchantOrderService::updateMerchantOrderStatusForExsystem come here 2 merchantOrder:{}", merchantOrder);
                        } else {
                            LOGGER.info("MerchantOrderService::updateMerchantOrderStatusForExsystem 发货单已经发货不允许修改");
                            return 1;
                        }

                    } else {
                        merchantOrderMapper.updateByOrderNumber(merchantOrder);
                        LOGGER.info("MerchantOrderService::updateMerchantOrderStatusForExsystem come here 3 merchantOrder:{}", merchantOrder);
                    }
                } else {
                    //merchantOrderMapper.updateByPrimaryKeySelective(merchantOrder);
                    merchantOrderMapper.updateByOrderNumber(merchantOrder);
                    LOGGER.info("MerchantOrderService::updateMerchantOrderStatusForExsystem come here 4 merchantOrder:{}", merchantOrder);
                }
            }
        } else if (merchantOrder.getStatus().equals(Byte.valueOf(MerchantOrderStatusEnum.ORDER_ALREADY_FINISH.getCode()))) {
            //如果订单已经完成 则要生成销售订单等信息
            LOGGER.info("MerchantOrderService::updateMerchantOrderStatusForExsystem come here 99");
            MerchantOrderDetail detailCondition = new MerchantOrderDetail();
            detailCondition.setMerchantOrderNumber(merchantOrder.getOrderNumber());
            detailCondition.setDeletedFlag("N");
            MerchantOrderDetail merchantOrderDetail = merchantOrderDetailMapper.selectOne(detailCondition);
            merchantOrderDetail.setAcceptQuantity(merchantOrderDetail.getCheckQuantity());
            //找到发货单号以及发货单下面的物流列表
            if (!StringUtils.isEmpty(merchantOrderDetail.getDispatchOrderNumber())) {
                Logistics logisticsCondition = new Logistics();
                List<OrderInfoDetail> listLogisticids = orderInfoDetailMapper.getLogisticIdsByOrderCode(merchantOrderDetail.getDispatchOrderNumber());
                for (OrderInfoDetail detail : listLogisticids) {
                    if (StringUtils.isEmpty(detail.getLogisticsId())) {
                        LOGGER.info("MerchantOrderService::updateMerchantOrderStatusForExsystem 物流id为空");
                        continue;
                    }
                    logisticsCondition.setId(detail.getLogisticsId().longValue());
                    Logistics logistics = logisticsService.getLogistics(logisticsCondition);
                    logistics.setLogisticsAcceptQuantity(detail.getLogisticsCount());
                    if (StringUtils.isEmpty(logistics)) {
                        LOGGER.info("MerchantOrderService::updateMerchantOrderStatusForExsystem 根据物流id找不到物流");
                        continue;
                    }
                    merchantOrderDetail.setLogistics(logistics);
                    this.acceptMerchantOrder(merchantOrderDetail);
                }
            } else {
                LOGGER.info("MerchantOrderService::updateMerchantOrderStatusForExsystem 未找到发货单");
            }
        } else {
            merchantOrderMapper.updateByOrderNumber(merchantOrder);
            LOGGER.info("MerchantOrderService::updateMerchantOrderStatusForExsystem come here 5 merchantOrder:{}", merchantOrder);
        }

        LOGGER.info("MerchantOrderService::updateMerchantOrderStatusForExsystem end");
        return 0;
    }


    /**
     * 已完成的订单增加到汇总表
     *
     * @param orderNumber
     * @return
     */
    /*public void addSalesSummarizing(String orderNumber) throws ParseException {
        LOGGER.info("已完成的商户订单增加到汇总表入参订单号:{}", orderNumber);
        Integer result = 0;
        MerchantOrder merchantOrder = this.getMerchantOrderByMerchantOrderNumber(orderNumber);
        List<MerchantOrderDetail> merchantOrderDetailList = merchantOrder.getMerchafntOrderDetailList();
        //查询汇总
        SalesSummarizing salesSummarizing = new SalesSummarizing();
        salesSummarizing.setMerchantCode(merchantOrder.getMerchantCode());
        salesSummarizing = salesSummarizingService.getMaxTimeSalesSummarizing(salesSummarizing);
        List<SalesSummarizingDetail> salesSummarizingDetailList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        SalesSummarizingDetail insertSalesSummarizingDetail = new SalesSummarizingDetail();
        SalesSummarizingDetail updateSalesSummarizingDetail = new SalesSummarizingDetail();
        if (salesSummarizing != null && sdf.format(salesSummarizing.getSalesTime()).equals(sdf.format(new Date()))) {
            //汇总产品详情
            salesSummarizingDetailList = salesSummarizing.getSalesSummarizingDetailList();
            for (int j = 0; j < merchantOrderDetailList.size(); j++) {
                for (int i = 0; i < salesSummarizingDetailList.size(); i++) {
                    updateSalesSummarizingDetail = new SalesSummarizingDetail();
                    if (salesSummarizingDetailList.get(i).getProductCode().equals(merchantOrderDetailList.get(j).getProductCode())) {
                        updateSalesSummarizingDetail.setSalesAmount(salesSummarizingDetailList.get(i).getSalesAmount() + merchantOrderDetailList.get(j).getProductAmount());
                        updateSalesSummarizingDetail.setSalesQuantity(salesSummarizingDetailList.get(i).getSalesQuantity() + merchantOrderDetailList.get(j).getCheckQuantity());
                        updateSalesSummarizingDetail.setId(salesSummarizingDetailList.get(i).getId());
                        updateSalesSummarizingDetail.setUpdatedDate(new Date());
                        salesSummarizingDetailMapper.updateById(updateSalesSummarizingDetail);
                    }else{
                        insertSalesSummarizingDetail = new SalesSummarizingDetail();
                        insertSalesSummarizingDetail.setSalesId(salesSummarizingDetailList.get(i).getSalesId());
                        insertSalesSummarizingDetail.setProductCode(merchantOrderDetailList.get(j).getProductCode());
                        List<SalesSummarizingDetail> list = salesSummarizingDetailMapper.select(insertSalesSummarizingDetail);
                        if(list != null && list.size() > 0){
                            continue;
                        }else{
                            insertSalesSummarizingDetail.setSalesQuantity(merchantOrderDetailList.get(j).getAcceptQuantity());
                            insertSalesSummarizingDetail.setSalesAmount(merchantOrderDetailList.get(j).getProductAmount());
                            insertSalesSummarizingDetail.setCreatedBy(merchantOrderDetailList.get(j).getCreatedBy());
                            insertSalesSummarizingDetail.setCreatedDate(new Date());
                            insertSalesSummarizingDetail.setUpdatedBy(merchantOrderDetailList.get(j).getUpdatedBy());
                            insertSalesSummarizingDetail.setUpdatedDate(new Date());
                            insertSalesSummarizingDetail.setDeletedFlag("N");
                            salesSummarizingDetailMapper.insert(insertSalesSummarizingDetail);
                        }
                    }
                }
            }
        } else {
            salesSummarizing = new SalesSummarizing();
            salesSummarizing.setMerchantCode(merchantOrder.getMerchantCode());
            salesSummarizing.setStatus(SalesSummarizingStatusEnum.SALES_SUMMARIZING_STATUS_RECONCILATION.getCode());
            for (int i = 0; i < merchantOrderDetailList.size(); i++) {
                salesSummarizingDetailList.add(this.toSalesSummarizingDetail(null, merchantOrderDetailList.get(i)));
            }
            salesSummarizing.setSalesSummarizingDetailList(salesSummarizingDetailList);
            salesSummarizing.setUpdatedBy("admin");
            salesSummarizing.setCreatedBy("admin");
            salesSummarizingService.add(salesSummarizing);
        }
    }

    private SalesSummarizingDetail toSalesSummarizingDetail(Long SalesId, MerchantOrderDetail merchantOrderDetail) {
        SalesSummarizingDetail salesSummarizingDetail = new SalesSummarizingDetail();
        salesSummarizingDetail.setCreatedBy("admin");
        salesSummarizingDetail.setUpdatedBy("admin");
        salesSummarizingDetail.setSalesId(SalesId);
        salesSummarizingDetail.setProductCode(merchantOrderDetail.getProductCode());
        salesSummarizingDetail.setSalesQuantity(merchantOrderDetail.getCheckQuantity());
        salesSummarizingDetail.setSalesAmount(merchantOrderDetail.getProductAmount() * merchantOrderDetail.getCheckQuantity());
        return salesSummarizingDetail;
    }*/


    
    /**
     * 签收订单
     *
     * @param merchantOrderDetail
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
    public Integer acceptMerchantOrder(MerchantOrderDetail merchantOrderDetail) throws RpcServiceException {
        //修改订单详情签收状态
        LOGGER.info("签收商户订单详情ID:{}", merchantOrderDetail.getId());
        Integer result = 0;
        try
        {
        	//新的前端页面没有把签收总数传过来 后台算一下
        	MerchantOrderDetail detailCondition = new MerchantOrderDetail();
        	detailCondition.setId(merchantOrderDetail.getId());
        	MerchantOrderDetail conditionResult = merchantOrderDetailMapper.selectOne(detailCondition);
        	Integer acceptQuantity = 0;
        	if(conditionResult != null){
        		if(conditionResult.getAcceptQuantity() != null){
        			acceptQuantity += conditionResult.getAcceptQuantity();
        		}
        	}
        	if(merchantOrderDetail.getLogistics() != null){
        		if(merchantOrderDetail.getLogistics().getLogisticsAcceptQuantity() != null){
        			acceptQuantity += merchantOrderDetail.getLogistics().getLogisticsAcceptQuantity();
        		}
        	}
        	if(null == merchantOrderDetail.getAcceptQuantity()){
        		merchantOrderDetail.setAcceptQuantity(acceptQuantity);
        	}
        	
        	EcMerchantOrder ecMerchantOrder = new EcMerchantOrder();
        	ecMerchantOrder.setOrderNumber(merchantOrderDetail.getMerchantOrderNumber());
        	merchantOrderDetail.setUpdatedDate(new Date());
            result = merchantOrderDetailMapper.updateById(merchantOrderDetail);
            //修改订单签收状态
            MerchantOrderDetail mod = new MerchantOrderDetail();
            mod.setMerchantOrderNumber(merchantOrderDetail.getMerchantOrderNumber());
            List<MerchantOrderDetail> merchantOrderDetailList = merchantOrderDetailMapper.select(mod);
            logisticsService.updateById(merchantOrderDetail.getLogistics());    
            if (merchantOrderDetailList != null && merchantOrderDetailList.size() > 0) {
                Integer sum = 0;
                Integer checkSum = 0;
                Integer shipmentsSum = 0;
                MerchantOrder merchantOrder = new MerchantOrder();
                for (MerchantOrderDetail model : merchantOrderDetailList) {
                    if (model.getAcceptQuantity() != null) {
                        sum += model.getAcceptQuantity();
                    }
                    if(model.getCheckQuantity() != null)
                    {
                    	checkSum += model.getCheckQuantity();
                    }  
                    //查询发货总数
                    Integer shipmentsQuantity = orderInfoMapper.getShipmentsQuantityByOrderCode(model.getDispatchOrderNumber());
                    if (shipmentsQuantity.equals(0)) {
                        Logistics logistics = new Logistics();
                        logistics.setServiceCode(model.getDispatchOrderNumber());
                        List<Logistics> logisticsList = logisticsMapper.select(logistics);
                        if (logisticsList != null && logisticsList.size() > 0) {
                            for (Logistics list : logisticsList) {
                                shipmentsSum += list.getShipmentsQuantity();
                            }
                        }
                    } else {
                        shipmentsSum += shipmentsQuantity;
                    }
                    
                    if(!StringUtils.isEmpty(model.getDispatchOrderNumber()) && !StringUtils.isEmpty(merchantOrderDetail.getLogistics()))
                    {
                    	MerchantOrderKafkaMessage kafkaMessage = new MerchantOrderKafkaMessage();
                    	SignMerchantOrder signMerchantOrder = new SignMerchantOrder();
                    	signMerchantOrder.setOrderCode(model.getDispatchOrderNumber());
                    	signMerchantOrder.setLogiticsId(merchantOrderDetail.getLogistics().getId());
                    	kafkaMessage.setSignMerchantOrder(signMerchantOrder);
                    	kafkaMessage.setMessageType(MerchantOrderKafkaMessageTypeEnum.MERCHANT_ORDER_SIGN.getCode());
                    	sendKafkaService.sendSignMerchantOrderKafkaMessage(kafkaMessage);
                    }
                    
                }
                merchantOrder.setOrderNumber(merchantOrderDetail.getMerchantOrderNumber());
                merchantOrder.setUpdatedDate(new Date());
                merchantOrder.setUpdatedBy(merchantOrderDetail.getUpdatedBy());
                if (shipmentsSum.equals(checkSum)  && shipmentsSum.equals(sum)) {
                    merchantOrder.setStatus(MerchantOrderStatusEnum.ORDER_ALREADY_FINISH.getCode());
                    //this.addSalesSummarizing(merchantOrderDetail.getMerchantOrderNumber());
                } else {
                    merchantOrder.setStatus(MerchantOrderStatusEnum.ORDER_PORTION_RECEIVE.getCode());
                }
                //更新导出订单
                ecMerchantOrder.setStatus(this.getMerchantOrderStatusName(merchantOrder.getStatus()));
                ecMerchantOrder.setSignQuantity(sum);
                ecMerchantOrder.setUpdatedDate(new Date());
                ecMerchantOrder.setUpdatedBy(merchantOrderDetail.getUpdatedBy());
                ecMerchantOrderService.updateEcMerchantOrderByOrderNumber(ecMerchantOrder);
                //将签收的信息添加到销售管理信息
                LOGGER.info("商户销售模式为经销，开始添加对账数据");
                MerchantOrder merchantOrderNew = new MerchantOrder();
                merchantOrderNew.setOrderNumber(merchantOrderDetail.getMerchantOrderNumber());
                merchantOrderNew = merchantOrderMapper.selectOne(merchantOrderNew);
                Sales sales = new Sales();
                List<Sales> salesList = new ArrayList<>();
                sales.setLogisticsId(merchantOrderDetail.getLogistics().getId());
                sales.setMerchantCode(merchantOrderNew.getMerchantCode());
                sales.setProductCode(merchantOrderDetail.getProductCode());
                sales.setOrderNumber(merchantOrderDetail.getMerchantOrderNumber());
                sales.setQuantity(merchantOrderDetail.getLogistics().getLogisticsAcceptQuantity());
                //根据订单编号获取订单信息
                if(merchantOrderDetail.getDispatchOrderNumber() != null){
                    OrderInfoDetail orderInfoDetail = orderInfoDetailMapper.getMaxUpdateOrderInfoDetailByOrderCode(merchantOrderDetail.getDispatchOrderNumber());
                    if (orderInfoDetail != null) {
                        sales.setDispatchTime(orderInfoDetail.getUpdatedDate());
                    }
                }else{
                    Logistics logistics = new Logistics();
                    logistics.setServiceCode(merchantOrderDetailList.get(0).getDispatchOrderNumber());
                    Logistics logisticsList = logisticsMapper.getMaxUpdateLogisticsByserviceCode(logistics);
                    sales.setDispatchTime(logisticsList.getUpdatedDate());
                }
                sales.setStatus(SalesReconciliationStatus.SALES_STATUS_NOT_RECONCILATION.getCode());
                sales.setUpdatedDate(new Date());
                sales.setCreatedDate(new Date());

                sales.setCreatedBy(merchantOrderDetail.getUpdatedBy());
                sales.setUpdatedBy(merchantOrderDetail.getUpdatedBy());
                sales.setDeletedFlag("N");
                salesList.add(sales);
                salesManagerService.add(salesList);
                LOGGER.info("insert销售对账数据end");
                
                merchantOrderMapper.updateByOrderNumber(merchantOrder);
            }
        }
        catch(Exception e)
        {
        	LOGGER.error(e.getMessage(),e);
			throw new RpcServiceException(e.getMessage());
        } 
        return result;
    }

    /**
     * 审核订单
     *
     * @param merchantOrder
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
    public Integer checkMerchantOrder(MerchantOrder merchantOrder) {
        LOGGER.info("审核订单入参:{}", merchantOrder);
        Integer result;
        try
        {
        	merchantOrder.setUpdatedDate(new Date());
            Integer totalCheck = 0;
            Double totalAmount = 0.0;
            for (MerchantOrderDetail merchantOrderDetail : merchantOrder.getMerchantOrderDetailList()) {
                totalCheck = totalCheck + merchantOrderDetail.getCheckQuantity();
                merchantOrderDetail.setUpdatedDate(new Date());
                merchantOrderDetail.setUpdatedBy(merchantOrder.getUpdatedBy());
                merchantOrderDetail.setCheckBy(merchantOrder.getCreatedBy());
                merchantOrderDetail.setCheckTime(new Date());
                merchantOrderDetailMapper.updateById(merchantOrderDetail);
                MerchantOrderDetail mod = new MerchantOrderDetail();
                mod.setId(merchantOrderDetail.getId());
                mod = merchantOrderDetailMapper.selectOne(mod);
                List<ProductHistoryPrice> productHistoryPrice = productHistoryPriceService.getProductHistoryPrice(mod.getProductCode(), merchantOrder.getOrderTime());
                Double price = 0.0;
                for(ProductHistoryPrice historyPrice : productHistoryPrice){
                    price = price + historyPrice.getPrice();
                }
                totalAmount = totalAmount + (price * merchantOrderDetail.getCheckQuantity());
            }
            merchantOrder.setTotalAmount(totalAmount);
            merchantOrder.setTotalCheck(totalCheck);
            result = merchantOrderMapper.updateByOrderNumber(merchantOrder);
            
            EcMerchantOrder ecMerchantOrder = new EcMerchantOrder();
            ecMerchantOrder.setOrderNumber(merchantOrder.getOrderNumber());
            ecMerchantOrder.setOweQuantity(totalCheck);
            ecMerchantOrder.setCheckQuantity(totalCheck);
            ecMerchantOrder.setTotalAmount(totalAmount);
            ecMerchantOrder.setCheckRemark(merchantOrder.getCheckRemark());           
            ecMerchantOrder.setCheckBy(merchantOrder.getCreatedBy());
            ecMerchantOrder.setCheckTime(SupplychainUtils.getNowDateStringYMD(new Date()));
            ecMerchantOrder.setStatus(MerchantOrderStatusEnum.ORDER_WAIT_SHIPMENTS.getName());   
            ecMerchantOrder.setUpdatedBy(merchantOrder.getUpdatedBy());
            ecMerchantOrder.setUpdatedDate(new Date());
            ecMerchantOrderService.updateEcMerchantOrderByOrderNumber(ecMerchantOrder);
        }
        catch(Exception e)
		{
    		LOGGER.error(e.getMessage(),e);
			throw new RpcServiceException(e.getMessage());
		}	
        return result;
    }

    /**
     * 分配发货
     *
     * @param merchantOrderDetailId
     * @param dispatchOrderNumber
     * @param updatedBy
     * @return
     */
    public Integer addMerchantOrderDispatch(Long merchantOrderDetailId, String dispatchOrderNumber, String updatedBy) {
        LOGGER.info("分配发货订单详情ID:{},发货单ID:{}", merchantOrderDetailId, dispatchOrderNumber);
        Integer result = 0;
        MerchantOrderDetail merchantOrderDetail = new MerchantOrderDetail();
        merchantOrderDetail.setId(merchantOrderDetailId);
        merchantOrderDetail.setDispatchOrderNumber(dispatchOrderNumber);
        merchantOrderDetail.setUpdatedBy(updatedBy);
        merchantOrderDetail.setUpdatedDate(new Date());
        merchantOrderDetailMapper.updateById(merchantOrderDetail);
        return result;
    }

    /**
     * 直接发货(不通过扫码工具)
     *
     * @param merchantOrder
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
    public Integer sendGoodsUpdateMerchantOrderStatu(MerchantOrder merchantOrder) throws RpcServiceException {
        LOGGER.info("直接发货修改商户订单的状态:{}", merchantOrder);
        Integer result = 0;
        //修改详情信息
        try
        {
        	String dispatchOrderNumber = StringUtil.getDispatchOrderNumber();
            MerchantOrderDetail mo = new MerchantOrderDetail();
            for (int i = 0; i < merchantOrder.getMerchantOrderDetailList().size(); i++) {
                mo.setId(merchantOrder.getMerchantOrderDetailList().get(i).getId());
            }
            MerchantOrderDetail merchantOrderDetail = merchantOrderDetailMapper.selectOne(mo);
            if (merchantOrderDetail != null && merchantOrderDetail.getDispatchOrderNumber() == null) {
                merchantOrderDetail.setDispatchOrderNumber(dispatchOrderNumber);
                merchantOrderDetailMapper.updateById(merchantOrderDetail);
            }

            //添加物流信息
            String LogisCode = StringUtil.getOrderNo();
            Logistics logistics = new Logistics();
            logistics.setCode(LogisCode);
            logistics.setOrderNumber(merchantOrder.getLogistics().getOrderNumber());
            logistics.setCompany(merchantOrder.getLogistics().getCompany());
            logistics.setType(LogisticsTypeEnum.SEND_ORDER_DELIVER_SHIMENT.getCode());
            logistics.setReceiveId(merchantOrder.getLogistics().getReceiveId());
            logistics.setShipmentsQuantity(merchantOrder.getLogistics().getShipmentsQuantity());
            logistics.setCreatedBy(merchantOrder.getCreatedBy());
            logistics.setCreatedDate(new Date());
            logistics.setUpdatedBy(merchantOrder.getUpdatedBy());
            logistics.setUpdatedDate(new Date());
            logistics.setDeletedFlag("N");
            //根据id查询商户详情
            MerchantOrderDetail md = new MerchantOrderDetail();
            for (int i = 0; i < merchantOrder.getMerchantOrderDetailList().size(); i++) {
                md.setId(merchantOrder.getMerchantOrderDetailList().get(i).getId());
            }
            MerchantOrderDetail merchantOrderDetail1 = merchantOrderDetailMapper.selectOne(md);
            if (merchantOrderDetail1 != null) {
                logistics.setServiceCode(merchantOrderDetail1.getDispatchOrderNumber());
            }
            result = logisticsMapper.insert(logistics);

            //发货完改状态
            MerchantOrder newMerchantOrder = new MerchantOrder();
            newMerchantOrder.setOrderNumber(merchantOrder.getOrderNumber());
            MerchantOrder merchantOrder1 = merchantOrderMapper.selectOne(newMerchantOrder);
            if (merchantOrder1.getStatus() != MerchantOrderStatusEnum.ORDER_PORTION_RECEIVE.getCode()) {
                merchantOrder.setStatus(MerchantOrderStatusEnum.ORDER_WAIT_RECEIVE.getCode());
                merchantOrder.setUpdatedDate(new Date());
                merchantOrderMapper.updateByOrderNumber(merchantOrder);
            }
            
            EcMerchantOrder ecMerchantOrder = ecMerchantOrderService.getEcMerchantOrderByOrderNumber(merchantOrder.getOrderNumber());
            ecMerchantOrder.setDispatchOrderNumber(dispatchOrderNumber);
            ecMerchantOrder.setAlreadyShipmentQuantity(ecMerchantOrder.getAlreadyShipmentQuantity() + merchantOrder.getLogistics().getShipmentsQuantity());
            String shipmentTime = "";
            if(!StringUtils.isEmpty(ecMerchantOrder.getShipmentTime()))
            {
            	shipmentTime += ecMerchantOrder.getShipmentTime();
            	shipmentTime += ",";
            }
            shipmentTime += SupplychainUtils.getNowDateStringYMD(new Date());
            ecMerchantOrder.setShipmentTime(shipmentTime);
            String shipmentQuantity = "";
            if(!StringUtils.isEmpty(ecMerchantOrder.getShipmentQuantity()))
            {
            	shipmentQuantity += ecMerchantOrder.getShipmentQuantity();
            	shipmentQuantity += ",";
            }
            shipmentQuantity += merchantOrder.getLogistics().getShipmentsQuantity();
            ecMerchantOrder.setShipmentQuantity(shipmentQuantity);
            ecMerchantOrder.setOweQuantity(ecMerchantOrder.getOrderQuantity() - ecMerchantOrder.getAlreadyShipmentQuantity());
            String logisticsDesc = "";
            if(!StringUtils.isEmpty(ecMerchantOrder.getLogisticsDesc()))
            {
            	logisticsDesc += ecMerchantOrder.getLogisticsDesc();
            	logisticsDesc += "\n";
            }
            logisticsDesc += merchantOrder.getLogistics().getOrderNumber();
            logisticsDesc += ":";
            logisticsDesc += merchantOrder.getLogistics().getShipmentsQuantity();
            logisticsDesc += "\n";
            logisticsDesc += merchantOrder.getLogistics().getCompany();
            ecMerchantOrder.setLogisticsDesc(logisticsDesc);
            ecMerchantOrder.setUpdatedBy(merchantOrder.getUpdatedBy());
            ecMerchantOrder.setUpdatedDate(new Date());
            ecMerchantOrder.setStatus(MerchantOrderStatusEnum.ORDER_WAIT_RECEIVE.getName());
            ecMerchantOrderService.updateEcMerchantOrderById(ecMerchantOrder);
        }
        catch(Exception e)
		{
    		LOGGER.error(e.getMessage(),e);
			throw new RpcServiceException(e.getMessage());
		}	
        return result;
    }

    /**
     * 根据物流ID查询发货
     *
     * @param orderInfoDetail
     * @return
     */
    public List<OrderInfoDetail> listOrderInfoDetail(List<OrderInfoDetail> orderInfoDetail) {
        List<OrderInfoDetail> result;
        result = orderInfoDetailMapper.listOrderInfoDetail(orderInfoDetail);
        return result;
    }
    
    /**
     * 取消发货单 检查是否要更变订单状态
     *
     * @param orderInfoDetail
     * @return
     */
    public Integer updateMerchantOrderByDispatchOrderNumber(String dispatchOrderNum){
    	
    	MerchantOrderDetail detailCondition = new MerchantOrderDetail();
    	detailCondition.setDispatchOrderNumber(dispatchOrderNum);
    	detailCondition.setDeletedFlag("N");
    	MerchantOrderDetail merchantOrderDetail = merchantOrderDetailMapper.selectOne(detailCondition);
    	if(null == merchantOrderDetail){
    		return 0;
    	}
    	//查看订单明细列表
    	Example example = new Example(MerchantOrderDetail.class);
    	example.createCriteria().andEqualTo("merchantOrderNumber", merchantOrderDetail.getMerchantOrderNumber())
    							.andEqualTo("deletedFlag","N");
    	List<MerchantOrderDetail> listMerchantOrderDetail = merchantOrderDetailMapper.selectByExample(example);
    	if(null != listMerchantOrderDetail && listMerchantOrderDetail.size() == 1){
    		MerchantOrder condition = new MerchantOrder();
    		condition.setOrderNumber(merchantOrderDetail.getMerchantOrderNumber());
    		condition.setDeletedFlag("N");
    		MerchantOrder merchantOrder = merchantOrderMapper.selectOne(condition);
    		if(null != merchantOrder){
    			if(merchantOrder.getStatus().equals(MerchantOrderStatusEnum.ORDER_WAIT_RECEIVE.getCode())){
        			merchantOrder.setStatus(MerchantOrderStatusEnum.ORDER_WAIT_SHIPMENTS.getCode());
        		}
    		}
    		merchantOrderMapper.updateByPrimaryKeySelective(merchantOrder);	
    		
    		EcMerchantOrder ecMerchantOrderCondition = new EcMerchantOrder();
    		ecMerchantOrderCondition.setOrderNumber(merchantOrderDetail.getMerchantOrderNumber());
    		ecMerchantOrderCondition.setDeletedFlag("N");
    		EcMerchantOrder ecMerchantOrder = ecMerchantOrderMapper.selectOne(ecMerchantOrderCondition);
    		if(null != ecMerchantOrder){
    			ecMerchantOrder.setStatus(MerchantOrderStatusEnum.ORDER_WAIT_SHIPMENTS.getName());
    		}
    		ecMerchantOrderMapper.updateByPrimaryKeySelective(ecMerchantOrder);
    		
    		merchantOrderDetail.setDispatchOrderNumber(null);
    		merchantOrderDetailMapper.updateByPrimaryKey(merchantOrderDetail);
    	}
    	return 0;
    }

    /**
     * 导出商户订单列表
     *
     * @param merchantOrder
     * @return
     */
    public List<MerchantOrderExcelVo> exportMerchantOrderExit(MerchantOrder merchantOrder) {
    	
    	ExportMerchantOrder exportMerchantOrder = generatorExportMerchantOrder(merchantOrder);
    	sendKafkaService.notifyMerchantOrderExport(merchantOrder.getUserName(),merchantOrder.getPlatName(),exportMerchantOrder);
    	
        if (StringUtils.isEmpty(merchantOrder)) {
            LOGGER.error("MerchantOrderService::exportMerchantOrderExit 参数不能为空");
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        EcMerchantOrder ecMerchantOrder = new EcMerchantOrder();
        if (!StringUtil.isEmpty(merchantOrder.getOrderNumber())) {//订单号
            ecMerchantOrder.setOrderNumber(merchantOrder.getOrderNumber());
        }
        if (!StringUtil.isEmpty(merchantOrder.getMaterialCode())) {//物料编号
         //   ecMerchantOrder.setProductCode(merchantOrder.getMaterialCode());
        	ecMerchantOrder.setMaterialCode(merchantOrder.getMaterialCode());
        }
        if (!StringUtil.isEmpty(merchantOrder.getType())) {//设备类型
            DeviceType deviceTypeInfo = deviceTypeMapper.selectByPrimaryKey(Integer.valueOf(merchantOrder.getType()));
            if (!StringUtil.isEmpty(deviceTypeInfo)) {
                ecMerchantOrder.setDeviceType(deviceTypeInfo.getName());
            }
        }
        if (!StringUtil.isEmpty(merchantOrder.getChannel())) {//商户渠道
            if (merchantOrder.getChannel() == DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_GHUI.getCode()) {
                ecMerchantOrder.setChannel("广汇代销");
            } else if (merchantOrder.getChannel() == DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_OTHER.getCode()) {
                ecMerchantOrder.setChannel("金融风控代销");
            } else if (merchantOrder.getChannel() == DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_TMHUI.getCode()) {
                ecMerchantOrder.setChannel("同盟会渠道");
            } else if (merchantOrder.getChannel() == DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_CHANNEL.getCode()) {
                ecMerchantOrder.setChannel("金融渠道");
            } else if (merchantOrder.getChannel() == DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_FIVE.getCode()) {
                ecMerchantOrder.setChannel("亿咖通");
            } else if (merchantOrder.getChannel() == DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_SIX.getCode()) {
                ecMerchantOrder.setChannel("同盟会特定品");
            }
        }

        if (!StringUtil.isEmpty(merchantOrder.getMerchantCode())) {
            ecMerchantOrder.setMerchantCode(merchantOrder.getMerchantCode());
        }
        if (!StringUtil.isEmpty(merchantOrder.getMerchantName())) {//商户名称
            ecMerchantOrder.setMerchantName(merchantOrder.getMerchantName());
        }

        if (!StringUtil.isEmpty(merchantOrder.getStatus())) {//订单状态
            if (merchantOrder.getStatus() == MerchantOrderStatusEnum.ORDER_WAIT_CHECK.getCode()) {
                ecMerchantOrder.setStatus("待审核");
            } else if (merchantOrder.getStatus() == MerchantOrderStatusEnum.ORDER_WAIT_SHIPMENTS.getCode()) {
                ecMerchantOrder.setStatus("待发货");
            } else if (merchantOrder.getStatus() == MerchantOrderStatusEnum.ORDER_WAIT_RECEIVE.getCode()) {
                ecMerchantOrder.setStatus("待签收");
            } else if (merchantOrder.getStatus() == MerchantOrderStatusEnum.ORDER_PORTION_RECEIVE.getCode()) {
                ecMerchantOrder.setStatus("部分签收");
            } else if (merchantOrder.getStatus() == MerchantOrderStatusEnum.ORDER_ALREADY_FINISH.getCode()) {
                ecMerchantOrder.setStatus("已完成");
            } else if (merchantOrder.getStatus() == MerchantOrderStatusEnum.ORDER_ALREADY_DRAW_A_BILL.getCode()) {
                ecMerchantOrder.setStatus("已开票");
            } else if (merchantOrder.getStatus() == MerchantOrderStatusEnum.ORDER_ALREADY_INVALID.getCode()) {
                ecMerchantOrder.setStatus("已作废");
            } else if (merchantOrder.getStatus() == MerchantOrderStatusEnum.ORDER_ALREADY_COUNTERMAND.getCode()) {
                ecMerchantOrder.setStatus("已驳回");
            }
        }

        if (!StringUtil.isEmpty(merchantOrder.getStartDate())) {
            ecMerchantOrder.setStartDate(merchantOrder.getStartDate());
        }
        if (!StringUtil.isEmpty(merchantOrder.getEndDate())) {
            ecMerchantOrder.setEndDate(merchantOrder.getEndDate());
        }
        if (!StringUtil.isEmpty(merchantOrder.getCheckStartDate())) {
            ecMerchantOrder.setCheckStartDate(merchantOrder.getCheckStartDate());
        }
        if (!StringUtil.isEmpty(merchantOrder.getCheckEndDate())) {
            ecMerchantOrder.setCheckEndDate(merchantOrder.getCheckEndDate());
        }

        List<MerchantOrderExcelVo> merchantOrderVoList = new ArrayList<>();
        List<EcMerchantOrder> ecMerchantOrderList = null;
        ecMerchantOrderList = ecMerchantOrderMapper.exportEcMerchantOrderExit(ecMerchantOrder);

        if (ecMerchantOrderList != null && ecMerchantOrderList.size() > 0) {
            for (EcMerchantOrder list : ecMerchantOrderList) {
                MerchantOrderExcelVo merchantOrderExcelVo = new MerchantOrderExcelVo();
                merchantOrderExcelVo.setChannel(list.getChannel());
                merchantOrderExcelVo.setMerchantName(list.getMerchantName());
                merchantOrderExcelVo.setOrderNumber(list.getOrderNumber());
                merchantOrderExcelVo.setProductName(list.getProductName());
                merchantOrderExcelVo.setProductCode(list.getProductCode());
                merchantOrderExcelVo.setDeviceType(list.getDeviceType());
                merchantOrderExcelVo.setPrice(list.getPrice());
                merchantOrderExcelVo.setOrderQuantity(list.getOrderQuantity());
                merchantOrderExcelVo.setCheckQuantity(list.getCheckQuantity());
                merchantOrderExcelVo.setDispatchOrderNumber(list.getDispatchOrderNumber());
                merchantOrderExcelVo.setAlreadyShipmentQuantity(list.getAlreadyShipmentQuantity());
                merchantOrderExcelVo.setShipmentTime(list.getShipmentTime());
                merchantOrderExcelVo.setShipmentQuantity(list.getShipmentQuantity());
                merchantOrderExcelVo.setSignQuantity(list.getSignQuantity());
                merchantOrderExcelVo.setOweQuantity(list.getOweQuantity());
                if(list.getCheckQuantity() != null){
                    merchantOrderExcelVo.setTotalAmount(list.getPrice() * list.getCheckQuantity());
                }
                merchantOrderExcelVo.setOrderTime(list.getOrderTime());
                merchantOrderExcelVo.setProductRemarks(list.getProductRemarks());
                merchantOrderExcelVo.setOrderRemark(list.getOrderRemark());
                merchantOrderExcelVo.setCheckRemark(list.getCheckRemark());
                merchantOrderExcelVo.setCheckBy(list.getCheckBy());
                merchantOrderExcelVo.setCheckTime(list.getCheckTime());
                merchantOrderExcelVo.setStatus(list.getStatus());
                merchantOrderExcelVo.setAddressee(list.getAddressee());
                merchantOrderExcelVo.setMobile(list.getMobile());
                merchantOrderExcelVo.setAddressDetail(list.getAddressDetail());
                merchantOrderExcelVo.setMaterialCode(list.getMaterialCode());
                merchantOrderExcelVo.setMaterialName(list.getMaterialName());
                merchantOrderExcelVo.setLogisticsDesc(list.getLogisticsDesc());
                merchantOrderExcelVo.setDealerUserName(list.getDealerUserName());

                merchantOrderVoList.add(merchantOrderExcelVo);
            }
        }
        LOGGER.info("exportMerchantOrderExit return merchantOrderVoList.size()" + merchantOrderVoList.size());
        return merchantOrderVoList;
    }
    
    private ExportMerchantOrder generatorExportMerchantOrder(MerchantOrder merchantOrder)
    {
    	ExportMerchantOrder exportMerchantOrder = new ExportMerchantOrder();
    	exportMerchantOrder.setOrderNumber(merchantOrder.getOrderNumber());
    	exportMerchantOrder.setMaterialCode(merchantOrder.getMaterialCode());
    	exportMerchantOrder.setType(merchantOrder.getType());
    	exportMerchantOrder.setChannel(merchantOrder.getChannel());
    	exportMerchantOrder.setMerchantCode(merchantOrder.getMerchantCode());
    	exportMerchantOrder.setMerchantName(merchantOrder.getMerchantName());
    	exportMerchantOrder.setStatus(merchantOrder.getStatus());
    	exportMerchantOrder.setStartDate(merchantOrder.getStartDate());
    	exportMerchantOrder.setEndDate(merchantOrder.getEndDate());
    	exportMerchantOrder.setCheckStartDate(merchantOrder.getCheckStartDate());
    	exportMerchantOrder.setCheckEndDate(merchantOrder.getCheckEndDate());
    	return exportMerchantOrder;
    }


    /**
     * 商户订单列表导入
     * @param merchantOrderImportList
     * @return
     */
    /*public Integer importMerchantOrderList(List<MerchantOrderImport> merchantOrderImportList){
        LOGGER.info("商户订单列表导入参数:{}", merchantOrderImportList);
        Integer result = 0;
        for(MerchantOrderImport list: merchantOrderImportList){
            String dispatchOrderNumber = StringUtil.getDispatchOrderNumber();
            MerchantOrderDetail merchantOrderDetail = new MerchantOrderDetail();
            merchantOrderDetail.setMerchantOrderNumber(list.getOrderNumber());
            merchantOrderDetail.setProductCode(list.getProductCode());
            MerchantOrderDetail merchantOrderDetail1Info = merchantOrderDetailMapper.selectOne(merchantOrderDetail);
            if(merchantOrderDetail1Info.getDispatchOrderNumber() == null){
                merchantOrderDetail1Info.setDispatchOrderNumber(dispatchOrderNumber);
                merchantOrderDetailMapper.updateById(merchantOrderDetail1Info);
            }

            //查询已出货数量
            Logistics logistics1 = new Logistics();
            logistics1.setServiceCode(merchantOrderDetail1Info.getDispatchOrderNumber());
            List<Logistics> logisticsList = logisticsMapper.select(logistics1);
            Integer shipmentQuantitySum = 0;
            for(int i =0;i<logisticsList.size();i++){
                shipmentQuantitySum = shipmentQuantitySum + logisticsList.get(i).getShipmentsQuantity();
            }
            if(list.getShipmentQuantity()+shipmentQuantitySum <= merchantOrderDetail1Info.getCheckQuantity()){
                MerchantOrder merchantOrderInfo = this.getMerchantOrderByMerchantOrderNumber(list.getOrderNumber());
                    //添加对应的物流信息
                    String logisticsCode = StringUtil.getOrderNo();
                    Logistics logistics = new Logistics();
                    logistics.setCode(logisticsCode);
                    logistics.setOrderNumber(list.getDispatchOrderNumber());
                    logistics.setCompany(list.getCompany());
                    logistics.setType(LogisticsTypeEnum.MERCHANT_ORDER.getCode());

                    //发货单
                    MerchantOrderDetail merchantOrderDetailOne = new MerchantOrderDetail();
                    merchantOrderDetailOne.setMerchantOrderNumber(list.getOrderNumber());
                    merchantOrderDetailOne.setProductCode(list.getProductCode());
                    MerchantOrderDetail merchantDetail = merchantOrderDetailMapper.selectOne(merchantOrderDetailOne);
                    if(merchantDetail != null){
                        logistics.setServiceCode(merchantDetail.getDispatchOrderNumber());
                    }
                    logistics.setReceiveId(merchantOrderInfo.getLogistics().getReceiveId());
                    logistics.setShipmentsQuantity(list.getShipmentQuantity());
                    logistics.setCreatedBy(merchantOrderDetail1Info.getCreatedBy());
                    logistics.setCreatedDate(new Date());
                    logistics.setUpdatedBy(merchantOrderDetail1Info.getUpdatedBy());
                    logistics.setUpdatedDate(new Date());
                    logistics.setDeletedFlag("N");
                    logisticsMapper.insert(logistics);

                    //完成后订单改状态
                    MerchantOrder merchantOrder = new MerchantOrder();
                    merchantOrder.setOrderNumber(list.getOrderNumber());
                    MerchantOrder merchantOrder1 = merchantOrderMapper.selectOne(merchantOrder);
                    if(merchantOrder1.getStatus() != MerchantOrderStatusEnum.ORDER_PORTION_RECEIVE.getCode()){
                        merchantOrder.setStatus(MerchantOrderStatusEnum.ORDER_WAIT_RECEIVE.getCode());
                        merchantOrder.setUpdatedDate(new Date());
                        merchantOrderMapper.updateByOrderNumber(merchantOrder);
                    }
            }

        }
        return result;
    }*/
    
    
    
    public MerchantOrder getMerchantOrderDbObject(String merchantOrder){
    	MerchantOrder condition = new MerchantOrder();
    	condition.setOrderNumber(merchantOrder);
    	condition.setDeletedFlag("N");
    	return merchantOrderMapper.selectOne(condition);	
    }
    
    public Integer updateMerchantOrderDbObject(MerchantOrder merchantOrder){
    	if(null == merchantOrder){
    		return 0;
    	}
    	if(merchantOrder.getId() == null){
    		return 0;
    	}
    	return merchantOrderMapper.updateByPrimaryKeySelective(merchantOrder);
    }
}
