package cn.com.glsx.rpc.supplychain.rdn.service;

import cn.com.glsx.rpc.supplychain.rdn.model.pojo.Address;
import cn.com.glsx.rpc.supplychain.rdn.model.pojo.GoodsReceipt;
import cn.com.glsx.rpc.supplychain.rdn.model.pojo.ShipmentOrder;
import cn.com.glsx.rpc.supplychain.rdn.util.ExcelReadAndWriteUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author luoqiang
 * @version 1.0
 * @program: supplychain
 * @description:
 * @date 2020/9/27 17:43
 */
@RestController
@RequestMapping("api")
public class TestController {
    /**
     * @author: luoqiang
     * @description: 下载发货单
     * @date: 2020/9/27 17:52
     * @param null
     * @return:
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/exportGoodsReceipt", method = RequestMethod.GET)
    public void exportGoodsReceipt(HttpServletResponse response) {
        Map<String, Object> goodsReceiptMap = new HashMap<>();
        GoodsReceipt goodsReceipt=new GoodsReceipt();
        goodsReceipt.setDocumentNo("B202008060001");
        goodsReceipt.setConsignee("广联赛讯");
        List<ShipmentOrder> shipmentOrderList=new ArrayList<>();
        ShipmentOrder shipmentOrder=new ShipmentOrder();
        ShipmentOrder shipmentOrder2=new ShipmentOrder();
        shipmentOrder.setOrderNumber("GLSX1111111111");
        shipmentOrder.setProductCode("GLSX478");
        shipmentOrder.setProductName("车载导航");
        shipmentOrder.setSendCount(8);
        shipmentOrder.setSendDate("2020-09-27");
        shipmentOrder.setLogisticsCompany("顺丰物流");
        shipmentOrder.setRemark("物料颜色为黑");

        shipmentOrder2.setOrderNumber("GLSX2222222");
        shipmentOrder2.setProductCode("GLSX999");
        shipmentOrder2.setProductName("GPS");
        shipmentOrder2.setSendCount(7);
        shipmentOrder2.setSendDate("2020-09-26");
        shipmentOrder2.setLogisticsCompany("顺丰物流");
        shipmentOrder2.setRemark("物料颜色为白");
        shipmentOrderList.add(shipmentOrder);
        shipmentOrderList.add(shipmentOrder2);
        goodsReceipt.setShipmentOrderList(shipmentOrderList);
        Integer totalCount=0;
        for(ShipmentOrder shipmentOrderDto:shipmentOrderList){
            totalCount+=shipmentOrderDto.getSendCount();
        }
        goodsReceipt.setTotalCount(totalCount);
        Address address=new Address();
        StringBuffer sb=new StringBuffer();
        address.setProvinceName("广东省");
        address.setCityName("深圳市");
        address.setAreaName("龙华区");
        address.setAddress("大浪街道陶吓新村");
        address.setName("罗强");
        address.setMobile("18374768361");
        sb.append(address.getProvinceName());
        sb.append(address.getCityName());
        sb.append(address.getAreaName());
        sb.append(address.getAddress());
        address.setAddress(sb.toString());
        List<Address> addressList=new ArrayList<>();
        addressList.add(address);
        goodsReceiptMap.put("documentNo",goodsReceipt.getDocumentNo());
        goodsReceiptMap.put("consignee",goodsReceipt.getConsignee());
        goodsReceiptMap.put("totalCount",totalCount);
        goodsReceiptMap.put("addressList",addressList);
        goodsReceiptMap.put("shipmentOrderList",goodsReceipt.getShipmentOrderList());
        String goodsReceiptTemplateFileName = ExcelReadAndWriteUtil.getPath()+"templates" + File.separator + "templateGoodsReceipt.xlsx";
            try {
                ExcelReadAndWriteUtil.compositeFillGoodsReceipt(response, goodsReceiptMap, goodsReceiptTemplateFileName, null, null);
            } catch (Exception e) {
                logger.error("导出异常：" + e.getMessage(), e);
            }
        }
}
