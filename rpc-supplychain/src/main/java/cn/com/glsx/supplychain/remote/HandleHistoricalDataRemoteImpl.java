package cn.com.glsx.supplychain.remote;

import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.enums.MerchantOrderStatusEnum;
import cn.com.glsx.supplychain.mapper.bs.*;
import cn.com.glsx.supplychain.model.bs.*;
import cn.com.glsx.supplychain.service.SplitOrderService;
import cn.com.glsx.supplychain.service.bs.*;
import cn.com.glsx.supplychain.util.StringUtil;
import com.alibaba.dubbo.config.annotation.Service;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.QueryAnnotation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName HandleHistoricalDataRemoteImpl
 * @Description TODO
 * @Author yangbin
 * @Date 2019/8/12 14:58
 * @Version 1.0
 */
@Component("HandleHistoricalDataRemote")
@Service(version = "1.0.0", protocol = "dubbo", timeout = 10000)
public class HandleHistoricalDataRemoteImpl implements HandleHistoricalDataRemote {

    private static final Logger LOGGER = LoggerFactory.getLogger(HandleHistoricalDataRemoteImpl.class);

    @Autowired
    SplitOrderService splitOrderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductDetailMapper productDetailMapper;

    @Autowired
    private AfterSaleOrderMapper afterSaleOrderMapper;

    @Autowired
    private MerchantOrderDetailMapper merchantOrderDetailMapper;

    @Autowired
    private ProductHistoryPriceMapper productHistoryPriceMapper;

    @Autowired
    private SalesManagerMapper salesManagerMapper;


    public RpcResponse<Integer> splitOrder() {
        RpcResponse<Integer> result;
        try {
            List<SplitOrder> orderList = splitOrderService.getOrderList();
            List<SplitOrder> updateOrderList = splitOrderService.getOrderList();
            List<Logistics> logisticsList = splitOrderService.getLogisticsList();

            Map<String, List<String>> orderMap = new LinkedHashMap<>();
            List<SplitOrder> splitOrderList = new ArrayList<>();
            for (SplitOrder md : orderList) {
                List list;
                if (orderMap.containsKey(md.getOrderNumber())) {
                    //获取到新的ID
                    String id = StringUtil.getOrderNo();
                    //旧ID和新ID对应关系存到map
                    list = orderMap.get(md.getOrderNumber());
                    list.add(id);
                    //获取新增的订单和修改的订单详情对象
                    SplitOrder mod = new SplitOrder();
                    BeanUtils.copyProperties(mod, md);
                    mod.setOrderNumber(id);
                    mod.setMerchantOrderNumber(id);
                    mod.setTotalOrder(mod.getOrderQuantity());
                    mod.setTotalCheck(mod.getCheckQuantity());
                    if (null != mod.getCheckQuantity()) {
                        if (null == mod.getAcceptQuantity()) {
                            mod.setStatus(Integer.valueOf(MerchantOrderStatusEnum.ORDER_WAIT_RECEIVE.getCode()));
                        } else if (mod.getCheckQuantity() == mod.getAcceptQuantity()) {
                            mod.setStatus(Integer.valueOf(MerchantOrderStatusEnum.ORDER_ALREADY_FINISH.getCode()));
                        } else if (mod.getAcceptQuantity() < mod.getCheckQuantity()) {
                            mod.setStatus(Integer.valueOf(MerchantOrderStatusEnum.ORDER_PORTION_RECEIVE.getCode()));
                        }
                    }
                    if (null == mod.getDispatchOrderNumber()) {
                        mod.setStatus(Integer.valueOf(MerchantOrderStatusEnum.ORDER_WAIT_SHIPMENTS.getCode()));
                    }
                    splitOrderList.add(mod);
                } else {
                    list = new ArrayList();
                    orderMap.put(md.getOrderNumber(), list);
                    updateOrderList.add(md);
                }
            }

            List<Logistics> splitLogisticsList = new ArrayList<>();
            for (Logistics logistics : logisticsList) {
                String orderNumber = logistics.getServiceCode();
                if (orderMap.containsKey(orderNumber)) {
                    List<String> list = orderMap.get(orderNumber);
                    for (String id : list) {
                        Logistics logistics1 = new Logistics();
                        BeanUtils.copyProperties(logistics1, logistics);
                        logistics1.setServiceCode(id);
                        splitLogisticsList.add(logistics1);
                    }
                }
            }
            Map orderDetailMap = new LinkedHashMap();
            orderDetailMap.put("detailList", splitOrderList);
            Map updateOrderMap = new LinkedHashMap();
            updateOrderMap.put("orderList", updateOrderList);
            splitOrderService.saveOrderAndLogistics(splitOrderList, splitLogisticsList, updateOrderMap,
                    orderDetailMap);
            return RpcResponse.success(1);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }


    public RpcResponse<Integer> updateProductCode() {
        RpcResponse<Integer> result;
        try {
            Product product = new Product();
            String materialCode = "";
            List<Product> productList = productService.getProductList(product);
            for (Product productData : productList) {

                //产品明细表表编号修改
                ProductDetail productDetail = new ProductDetail();
                productDetail.setProductCode(productData.getCode());
                List<ProductDetail> productDetailList = productDetailMapper.select(productDetail);
                if (productDetailList.size() > 1) {
                    for (ProductDetail productDetailData : productDetailList) {
                        if (productDetailData.getMaterialCode().contains("GLXS")||productDetailData.getMaterialCode().contains("GLSX")) {
                            materialCode = productDetailData.getMaterialCode();
                            productDetailData.setProductCode(materialCode);
                            productDetailMapper.updateById(productDetailData);
                        }
                    }
                } else {
                    materialCode = productDetailList.get(0).getMaterialCode();
                    productDetailList.get(0).setProductCode(materialCode);
                    productDetailMapper.updateById(productDetailList.get(0));
                }

                //售后产品编号修改
                AfterSaleOrder afterSaleOrder = new AfterSaleOrder();
                afterSaleOrder.setProductCode(productData.getCode());
                List<AfterSaleOrder> afterSaleOrderList = afterSaleOrderMapper.select(afterSaleOrder);
                for (AfterSaleOrder afterSaleOrderData : afterSaleOrderList) {
                    afterSaleOrderData.setProductCode(materialCode);
                    afterSaleOrderMapper.updateById(afterSaleOrderData);
                }

                //订单详情修改
                MerchantOrderDetail merchantOrderDetail = new MerchantOrderDetail();
                merchantOrderDetail.setProductCode(productData.getCode());
                List<MerchantOrderDetail> merchantOrderDetailList = merchantOrderDetailMapper.select(merchantOrderDetail);
                for (MerchantOrderDetail merchantOrderDetailData : merchantOrderDetailList) {
                    merchantOrderDetailData.setProductCode(materialCode);
                    merchantOrderDetailMapper.updateById(merchantOrderDetailData);
                }

                //产品历史价格表修改
                ProductHistoryPrice productHistoryPrice = new ProductHistoryPrice();
                productHistoryPrice.setProductCode(productData.getCode());
                List<ProductHistoryPrice> productHistoryPriceList = productHistoryPriceMapper.select(productHistoryPrice);
                for(ProductHistoryPrice productHistoryPriceData : productHistoryPriceList){
                    productHistoryPriceData.setProductCode(materialCode);
                    productHistoryPriceMapper.updateById(productHistoryPriceData);
                }

                //销售表产品编号修改
                Sales sales = new Sales();
                sales.setProductCode(productData.getCode());
                List<Sales> salesList = salesManagerMapper.select(sales);
                for(Sales salesData : salesList){
                    salesData.setProductCode(materialCode);
                    salesManagerMapper.updateById(salesData);
                }

                //产品表编号修改
                productData.setCode(materialCode);
                productService.updateById(productData);
            }
            return RpcResponse.success(1);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }
}