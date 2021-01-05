package cn.com.glsx.supplychain.jst.service;

import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.jst.common.Constants;
import cn.com.glsx.supplychain.jst.config.SystemProperty;
import cn.com.glsx.supplychain.jst.convert.BsAddressRpcConvert;
import cn.com.glsx.supplychain.jst.dto.BsAddressDTO;
import cn.com.glsx.supplychain.jst.dto.BsLogisticsDTO;
import cn.com.glsx.supplychain.jst.dto.gh.*;
import cn.com.glsx.supplychain.jst.enums.*;
import cn.com.glsx.supplychain.jst.mapper.*;
import cn.com.glsx.supplychain.jst.model.*;
import cn.com.glsx.supplychain.jst.util.JstUtils;
import cn.com.glsx.supplychain.jst.util.MailUtils;
import cn.com.glsx.supplychain.jst.util.StringUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.session.RowBounds;
import org.oreframework.boot.autoconfigure.snowflake.algorithm.SnowflakeWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GhMerchantOrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GhMerchantOrderService.class);
    @Autowired
    private GhMerchantOrderMapper merchantOrderMapper;
    @Autowired
    private GhMerchantOrderConfigMapper merchantOrderConfigMapper;
    @Autowired
    private GhMerchantOrderReflectMapper merchantOrderReflectMapper;
    @Autowired
    private AttribInfoService attribInfoService;
    @Autowired
    private GhShoppingCartService shoppingCartService;
    @Autowired
    private SnowflakeWorker snowflakeWorker;
    @Autowired
    private BsAddressService addressService;
    @Autowired
    private BsMerchantOrderMapper bsMerchantOrderMapper;
    @Autowired
    private BsMerchantOrderDetailMapper bsMerchantOrderDetailMapper;
    @Autowired
    private BsMerchantOrderVehicleMapper bsMerchantOrderVehicleMapper;
    @Autowired
    private EcMerchantOrderMapper ecMerchantOrderMapper;
    @Autowired
    private GhMerchantOrderReflectMcodeMapper ghMerchantOrderReflectMcodeMapper;
    @Autowired
    private BsLogisticsService bsLogisticsService;
    @Autowired
    private BsProductService bsProductService;
    @Autowired
    private BsDealerUserInfoService bsDealerUserInfoService;
    @Autowired
    private ProductService productService;
    @Autowired
    private SystemProperty systemProperty;
    @Autowired
    private GhMerchantOrderReminderMapper ghMerchantOrderReminderMapper;
    @Autowired
    private OrderInfoMapper orderInfoMapper;


    public BsAddressDTO getLastMerchantOrderAddress(BsAddressDTO record) {
        GhMerchantOrder merchantOrder = getLastMerchantOrder(record.getMerchantCode());
        if (null == merchantOrder) {
            return null;
        }
        if (null == merchantOrder.getAddressId()) {
            return null;
        }
        return BsAddressRpcConvert.convertBean(addressService.getAddressById(merchantOrder.getAddressId().longValue(), null));
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
    public Integer addShoppingCartFromMerchantOrder(GhMerchantOrderDTO record) throws RpcServiceException {
        String cartCode = Constants.GH_SHOP_CART_PREFIX + JstUtils.getDispatchOrderNumber(snowflakeWorker);
        GhMerchantOrder merchantOrder = this.getMerchantOrder(record.getGhMerchantOrderCode());
        List<String> listMerchantOrderCodes = new ArrayList<>();
        listMerchantOrderCodes.add(record.getGhMerchantOrderCode());
        List<GhMerchantOrderConfig> listMerchantOrderConfig = this.listMerchantOrderConfig(listMerchantOrderCodes);
        GhShoppingCart shoppingCart = new GhShoppingCart();
        List<GhShoppingCartConfig> listShoppingCartConfig = null;
        GhShoppingCartConfig shoppingCartConfig = null;
        shoppingCart.setCartCode(cartCode);
        shoppingCart.setMerchantCode(record.getMerchantCode());
        shoppingCart.setProductConfigCode(merchantOrder.getProductConfigCode());
        shoppingCart.setParentBrandId(merchantOrder.getParentBrandId());
        shoppingCart.setSubBrandId(merchantOrder.getSubBrandId());
        shoppingCart.setAudiId(merchantOrder.getAudiId());
        shoppingCart.setMotorcycle(merchantOrder.getMotorcycle());
        shoppingCart.setCategoryId(merchantOrder.getCategoryId());
        shoppingCart.setSpaProductCode(merchantOrder.getSpaProductCode());
        shoppingCart.setSpaProductName(merchantOrder.getSpaProductName());
        shoppingCart.setGlsxProductCode(merchantOrder.getGlsxProductCode());
        shoppingCart.setGlsxProductName(merchantOrder.getGlsxProductName());
        shoppingCart.setTotal(record.getTotal());
        shoppingCart.setRemark(record.getRemark());
        shoppingCart.setCreatedBy(record.getMerchantCode());
        shoppingCart.setCreatedDate(JstUtils.getNowDate());
        shoppingCart.setUpdatedBy(record.getMerchantCode());
        shoppingCart.setUpdatedDate(JstUtils.getNowDate());
        shoppingCart.setDeletedFlag("N");
        if (null != listMerchantOrderConfig) {
            for (GhMerchantOrderConfig config : listMerchantOrderConfig) {
                shoppingCartConfig = new GhShoppingCartConfig();
                shoppingCartConfig.setAttribInfoId(config.getAttribInfoId());
                shoppingCartConfig.setCartCode(cartCode);
                shoppingCartConfig.setOption(config.getOption());
                shoppingCartConfig.setCreatedBy(record.getMerchantCode());
                shoppingCartConfig.setCreatedDate(JstUtils.getNowDate());
                shoppingCartConfig.setUpdatedBy(record.getMerchantCode());
                shoppingCartConfig.setUpdatedDate(JstUtils.getNowDate());
                shoppingCartConfig.setDeletedFlag("N");
                if (null == listShoppingCartConfig) {
                    listShoppingCartConfig = new ArrayList<>();
                }
                listShoppingCartConfig.add(shoppingCartConfig);
            }
        }
        try {
            shoppingCartService.insertShoppingCart(shoppingCart);
            shoppingCartService.insertShoppingCartConfig(listShoppingCartConfig);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new RpcServiceException(e.getMessage());
        }
        return 0;
    }

    public List<GhMerchantOrderDTO> exportGhMerchantOrder(GhMerchantOrderDTO dtoRecord) {
        List<GhMerchantOrderDTO> listResultDto = new ArrayList<>();
        Map<Integer, AttribInfo> mapAttribInfo = new HashMap<>();
        NewGhMerchantOrderDTO merchantOrderDto = null;
        GhMerchantOrderConfigDTO merchantOrderConfigDto = null;
        List<GhMerchantOrderConfigDTO> listMerchantOrderConfigDto = null;
        List<GhMerchantOrderConfig> listMerchantOrderConfig = null;
        List<BsLogisticsDTO> listLogistics = null;
        BsLogisticsDTO bsLogisticsDto = null;
        List<GhMerchantOrderReflectDispatch> listReflectDispatch = null;
        Map<String, List<GhMerchantOrderConfig>> mapMerchantOrderConfig = null;
        Map<String, List<GhMerchantOrderReflectDispatch>> mapMerchantOrderReflect = null;
        List<String> listMerchantOrderCode = new ArrayList<>();
        GhMerchantOrder condition = new GhMerchantOrder();
        condition.setDtoStatus(dtoRecord.getStatus());
        condition.setCategoryId(dtoRecord.getCategoryId());
        condition.setMerchantName(dtoRecord.getMerchantName());
        condition.setMerchantCode(dtoRecord.getMerchantCode());
        condition.setStartDate(JstUtils.getDateFromStrYMDHMS(dtoRecord.getStartDate()));
        condition.setEndDate(JstUtils.getDateFromStrYMDHMS(dtoRecord.getEndDate()));
        condition.setGlsxProductCode(dtoRecord.getGlsxProductCode());
        condition.setGlsxProductName(dtoRecord.getGlsxProductName());
        condition.setSpaProductName(dtoRecord.getSpaProductName());
        condition.setGhMerchantOrderCode(dtoRecord.getGhMerchantOrderCode());
        List<GhMerchantOrder> listMerchantOrder = merchantOrderMapper.listGhMerchantOrder(condition);
        List<String> ghMerchantOrderCodes = new ArrayList<>();
        for(GhMerchantOrder ghMerchantOrder:listMerchantOrder){
            ghMerchantOrderCodes.add(ghMerchantOrder.getGhMerchantOrderCode());
        }
        Map<String, GhMerchantOrderReminder> map=listMapGhmerchantOrderReminderByGhMerchantOrderCodes(ghMerchantOrderCodes);
        List<Long> adressIds = new ArrayList<>();
        for (GhMerchantOrder ghMerchantOrder : listMerchantOrder) {
            adressIds.add(ghMerchantOrder.getAddressId().longValue());
        }
        Map<Long, BsAddress> mapBsAddress = addressService.listMapAddressByIds(adressIds);

        for (int i = 0; i < listMerchantOrder.size(); i++) {
            if (null == listMerchantOrder.get(0).getAddressId()) {
                continue;
            }
            listMerchantOrder.get(i).setBsAddress(mapBsAddress.get(listMerchantOrder.get(i).getAddressId().longValue()));
        }
        if (null == listMerchantOrder || listMerchantOrder.isEmpty()) {
            return listResultDto;
        }
        for (GhMerchantOrder order : listMerchantOrder) {
            listMerchantOrderCode.add(order.getGhMerchantOrderCode());
        }
        listMerchantOrderConfig = listMerchantOrderConfig(listMerchantOrderCode);
        if (null != listMerchantOrderConfig) {
            mapMerchantOrderConfig = listMerchantOrderConfig.stream().collect(Collectors.groupingBy(GhMerchantOrderConfig::getGhMerchantOrderCode));
        }
        if (null == mapMerchantOrderConfig) {
            mapMerchantOrderConfig = new HashMap<>();
        }
        List<GhMerchantOrderReflectDispatch> listRefect = merchantOrderReflectMapper.listReflectDispatch(listMerchantOrderCode);
        if (null != listRefect && !listRefect.isEmpty()) {
            mapMerchantOrderReflect = listRefect.stream().collect(Collectors.groupingBy(GhMerchantOrderReflectDispatch::getGhMerchantOrderCode));
        }
        if (null == mapMerchantOrderReflect) {
            mapMerchantOrderReflect = new HashMap<>();
        }

        for (GhMerchantOrder order : listMerchantOrder) {
            merchantOrderDto = new NewGhMerchantOrderDTO();
            merchantOrderDto.setId(order.getId());
            merchantOrderDto.setGhMerchantOrderCode(order.getGhMerchantOrderCode());
            merchantOrderDto.setOrderTime(order.getOrderTime());
            merchantOrderDto.setMerchantCode(order.getMerchantCode());
            merchantOrderDto.setProductConfigCode(order.getProductConfigCode());
            merchantOrderDto.setParentBrandId(order.getParentBrandId());
            merchantOrderDto.setParentBrandName(getAttribInfoName(order.getParentBrandId(), mapAttribInfo));
            merchantOrderDto.setSubBrandId(order.getSubBrandId());
            merchantOrderDto.setSubBrandName(getAttribInfoName(order.getSubBrandId(), mapAttribInfo));
            merchantOrderDto.setAudiId(order.getAudiId());
            merchantOrderDto.setAudiName(getAttribInfoName(order.getAudiId(), mapAttribInfo));
            merchantOrderDto.setMotorcycle(order.getMotorcycle());
            merchantOrderDto.setCategoryId(order.getCategoryId());
            merchantOrderDto.setCategoryName(getAttribInfoName(order.getCategoryId(), mapAttribInfo));
            merchantOrderDto.setSpaProductCode(order.getSpaProductCode());
            merchantOrderDto.setSpaProductName(order.getSpaProductName());
            merchantOrderDto.setGlsxProductCode(order.getGlsxProductCode());
            merchantOrderDto.setGlsxProductName(order.getGlsxProductName());
            merchantOrderDto.setTotal(order.getTotal());
            merchantOrderDto.setRemark(order.getRemark());
            merchantOrderDto.setStatus(order.getStatus().toString());
            merchantOrderDto.setMerchantName(order.getMerchantName());
            merchantOrderDto.setSpaPurchaseCode(order.getSpaPurchaseCode());
            merchantOrderDto.setBsAddress(BsAddressRpcConvert.convertBean(order.getBsAddress()));

            if(order.getStatus()==0||order.getStatus()==9){
                merchantOrderDto.setConversionStatus(GhMerchantOrderStatuEnum.ORDER_FB.getName());
            }else if(order.getStatus()==1 || order.getStatus()==2 || order.getStatus()==8){
                merchantOrderDto.setConversionStatus(GhMerchantOrderStatuEnum.ORDER_WS.getName());
            }else if(order.getStatus()==3 || order.getStatus()==4){
                merchantOrderDto.setConversionStatus(GhMerchantOrderStatuEnum.ORDER_SP.getName());
            }else if(order.getStatus()==5 || order.getStatus()==6 || order.getStatus()==7){
                merchantOrderDto.setConversionStatus(GhMerchantOrderStatuEnum.ORDER_FI.getName());
            }
            if(StringUtils.isEmpty(order.getGhMerchantOrderCode())){
                continue;
            }
            if(StringUtils.isEmpty(map.get(order.getGhMerchantOrderCode()))){
                merchantOrderDto.setReminderTotal(0);
            }else {
                merchantOrderDto.setReminderTotal(map.get(order.getGhMerchantOrderCode()).getReminderTotal());
            }
            List<BsLogisticsDTO> bsLogisticsDTOList= bsLogisticsService.listLogisticsByGhMerchantOrderCode(order.getGhMerchantOrderCode());
            Integer shipmentsQuantity=0;
            for(BsLogisticsDTO logisticsDTO:bsLogisticsDTOList){
                shipmentsQuantity+=(StringUtils.isEmpty(logisticsDTO.getShipmentsQuantity())?0:logisticsDTO.getShipmentsQuantity());
            }
            merchantOrderDto.setShipmentsQuantity(shipmentsQuantity);
            merchantOrderDto.setOwerSend(merchantOrderDto.getTotal()-shipmentsQuantity);
            listMerchantOrderConfig = mapMerchantOrderConfig.get(order.getGhMerchantOrderCode());
            if (null != listMerchantOrderConfig) {
                listMerchantOrderConfigDto = new ArrayList<>();
                for (GhMerchantOrderConfig config : listMerchantOrderConfig) {
                    merchantOrderConfigDto = new GhMerchantOrderConfigDTO();
                    merchantOrderConfigDto.setAttribInfoId(config.getAttribInfoId());
                    merchantOrderConfigDto.setAttribInfoName(getAttribInfoName(config.getAttribInfoId(), mapAttribInfo));
                    merchantOrderConfigDto.setAttribTypeId(getAttribTypeId(config.getAttribInfoId(), mapAttribInfo));
                    merchantOrderConfigDto.setAttribTypeName(getAttribTypeName(config.getAttribInfoId(), mapAttribInfo));
                    merchantOrderConfigDto.setGhMerchantOrderCode(config.getGhMerchantOrderCode());
                    merchantOrderConfigDto.setId(config.getId());
                    merchantOrderConfigDto.setOption(config.getOption());
                    listMerchantOrderConfigDto.add(merchantOrderConfigDto);
                }
                merchantOrderDto.setListMerchantOrderConfig(listMerchantOrderConfigDto);
            }
            listReflectDispatch = mapMerchantOrderReflect.get(order.getGhMerchantOrderCode());
            if (null != listReflectDispatch) {
                listLogistics = new ArrayList<>();
                for (GhMerchantOrderReflectDispatch dispatch : listReflectDispatch) {
                    if (StringUtils.isEmpty(dispatch.getLogisticsDesc())) {
                        continue;
                    }
                    String logisticsDesc = dispatch.getLogisticsDesc();
                    try {
                        int j = 0;
                        while (true) {
                            j = logisticsDesc.indexOf(":");
                            String logisticsNum = logisticsDesc.substring(0, j);
                            logisticsDesc = logisticsDesc.substring(j + 1, logisticsDesc.length());
                            j = logisticsDesc.indexOf("\n");
                            String sendNum = logisticsDesc.substring(0, j);
                            logisticsDesc = logisticsDesc.substring(j + 1, logisticsDesc.length());
                            j = logisticsDesc.indexOf("\n");
                            String logisticsCmp = logisticsDesc.substring(0, j);
                            logisticsDesc = logisticsDesc.substring(j + 1, logisticsDesc.length());

                            bsLogisticsDto = new BsLogisticsDTO();
                            bsLogisticsDto.setCompany(logisticsCmp);
                            bsLogisticsDto.setOrderNumber(logisticsNum);
                            bsLogisticsDto.setShipmentsQuantity(Integer.valueOf(sendNum));
                            listLogistics.add(bsLogisticsDto);
                            if (StringUtils.isEmpty(logisticsDesc)) {
                                break;
                            }
                        }
                    } catch (Exception e) {
                        LOGGER.info("Failed get listLogistics logisticsDesc" + dispatch.getLogisticsDesc());
                    }
                }
                merchantOrderDto.setListLogistics(listLogistics);
            }
            listResultDto.add(merchantOrderDto);
        }
        return listResultDto;
    }

    /**
     * @author: luoqiang
     * @description: 获取催单次数
     * @date: 2020/8/31 17:36
     * @param ghMerchantOrderCodes
     * @return: java.util.Map<java.lang.String,cn.com.glsx.supplychain.jst.model.GhMerchantOrderReminder>
     */
    private  Map<String,GhMerchantOrderReminder> listMapGhmerchantOrderReminderByGhMerchantOrderCodes(List<String> ghMerchantOrderCodes){
        Map<String,GhMerchantOrderReminder> mapResult = null;
        if(StringUtils.isEmpty(ghMerchantOrderCodes)){
            return  null;
        }
        Example example=new Example(GhMerchantOrderReminder.class);
        example.createCriteria().andIn("ghMerchantOrderCode",ghMerchantOrderCodes);
        List<GhMerchantOrderReminder> ghMerchantOrderList=ghMerchantOrderReminderMapper.selectByExample(example);
        if(StringUtils.isEmpty(ghMerchantOrderList)){
            return new HashMap<>();
        }

        mapResult = ghMerchantOrderList.stream().collect(Collectors.toMap(GhMerchantOrderReminder::getGhMerchantOrderCode, a->a));
        if(null == mapResult){
            mapResult = new HashMap<>();
        }
        return mapResult;
    }


    public Page<GhMerchantOrderDTO> pageGhMerchantOrder(Integer pageNo, Integer pageSize, GhMerchantOrderDTO dtoRecord) {
        Page<GhMerchantOrderDTO> pageResultDto = new Page<GhMerchantOrderDTO>();
        Map<Integer, AttribInfo> mapAttribInfo = new HashMap<>();
        NewGhMerchantOrderDTO merchantOrderDto = null;
        GhMerchantOrderConfigDTO merchantOrderConfigDto = null;
        List<GhMerchantOrderConfigDTO> listMerchantOrderConfigDto = null;
        List<GhMerchantOrderConfig> listMerchantOrderConfig = null;
        List<BsLogisticsDTO> listLogistics = null;
        BsLogisticsDTO bsLogisticsDto = null;
        List<GhMerchantOrderReflectDispatch> listReflectDispatch = null;
        Map<String, List<GhMerchantOrderConfig>> mapMerchantOrderConfig = null;
        Map<String, List<GhMerchantOrderReflectDispatch>> mapMerchantOrderReflect = null;
        List<String> listMerchantOrderCode = new ArrayList<>();
        PageHelper.startPage(pageNo, pageSize);
        GhMerchantOrder condition = new GhMerchantOrder();
        condition.setDtoStatus(dtoRecord.getStatus());
        condition.setCategoryId(dtoRecord.getCategoryId());
        condition.setMerchantName(dtoRecord.getMerchantName());
        condition.setMerchantCode(dtoRecord.getMerchantCode());
        condition.setStartDate(JstUtils.getDateFromStrYMDHMS(dtoRecord.getStartDate()));
        condition.setEndDate(JstUtils.getDateFromStrYMDHMS(dtoRecord.getEndDate()));
        condition.setGlsxProductCode(dtoRecord.getGlsxProductCode());
        condition.setGlsxProductName(dtoRecord.getGlsxProductName());
        condition.setSpaProductName(dtoRecord.getSpaProductName());
        condition.setGhMerchantOrderCode(dtoRecord.getGhMerchantOrderCode());
        Page<GhMerchantOrder> pageMerchantOrder = merchantOrderMapper.pageGhMerchantOrder(condition);
        List<String> ghMerchantOrderCodes = new ArrayList<>();
        for(GhMerchantOrder ghMerchantOrder:pageMerchantOrder.getResult()){
            ghMerchantOrderCodes.add(ghMerchantOrder.getGhMerchantOrderCode());
        }
        Map<String, GhMerchantOrderReminder> map=listMapGhmerchantOrderReminderByGhMerchantOrderCodes(ghMerchantOrderCodes);
        for(GhMerchantOrder ghMerchantOrder:pageMerchantOrder.getResult()){
            if(StringUtils.isEmpty(ghMerchantOrder.getGhMerchantOrderCode())){
                continue;
            }
            if(StringUtils.isEmpty(map.get(ghMerchantOrder.getGhMerchantOrderCode()))){
                ghMerchantOrder.setReminderTotal(0);
            }else {
                ghMerchantOrder.setReminderTotal(map.get(ghMerchantOrder.getGhMerchantOrderCode()).getReminderTotal());
            }
        }
        List<Long> adressIds = new ArrayList<>();
        for (GhMerchantOrder ghMerchantOrder : pageMerchantOrder.getResult()) {
            adressIds.add(ghMerchantOrder.getAddressId().longValue());
        }
        Map<Long, BsAddress> mapBsAddress = addressService.listMapAddressByIds(adressIds);
        for (int i = 0; i < pageMerchantOrder.getResult().size(); i++) {
            if (null == pageMerchantOrder.getResult().get(i).getAddressId()) {
                continue;
            }
            pageMerchantOrder.getResult().get(i).setBsAddress(mapBsAddress.get(pageMerchantOrder.getResult().get(i).getAddressId().longValue()));
        }

        if (null == pageMerchantOrder || null == pageMerchantOrder.getResult() || pageMerchantOrder.getResult().isEmpty()) {
            return pageResultDto;
        }
        for (GhMerchantOrder order : pageMerchantOrder.getResult()) {
            listMerchantOrderCode.add(order.getGhMerchantOrderCode());
        }
        listMerchantOrderConfig = listMerchantOrderConfig(listMerchantOrderCode);
        if (null != listMerchantOrderConfig) {
            mapMerchantOrderConfig = listMerchantOrderConfig.stream().collect(Collectors.groupingBy(GhMerchantOrderConfig::getGhMerchantOrderCode));
        }
        if (null == mapMerchantOrderConfig) {
            mapMerchantOrderConfig = new HashMap<>();
        }
        List<GhMerchantOrderReflectDispatch> listRefect = merchantOrderReflectMapper.listReflectDispatch(listMerchantOrderCode);
        if (null != listRefect && !listRefect.isEmpty()) {
            mapMerchantOrderReflect = listRefect.stream().collect(Collectors.groupingBy(GhMerchantOrderReflectDispatch::getGhMerchantOrderCode));
        }
        if (null == mapMerchantOrderReflect) {
            mapMerchantOrderReflect = new HashMap<>();
        }
        for (GhMerchantOrder order : pageMerchantOrder.getResult()) {
            merchantOrderDto = new NewGhMerchantOrderDTO();
            if(order.getStatus()==0||order.getStatus()==9){
                merchantOrderDto.setConversionStatus(GhMerchantOrderStatuEnum.ORDER_FB.getName());
            }else if(order.getStatus()==1 || order.getStatus()==2 || order.getStatus()==8){
                merchantOrderDto.setConversionStatus(GhMerchantOrderStatuEnum.ORDER_WS.getName());
            }else if(order.getStatus()==3 || order.getStatus()==4){
                merchantOrderDto.setConversionStatus(GhMerchantOrderStatuEnum.ORDER_SP.getName());
            }else if(order.getStatus()==5 || order.getStatus()==6 || order.getStatus()==7){
                merchantOrderDto.setConversionStatus(GhMerchantOrderStatuEnum.ORDER_FI.getName());
            }
            merchantOrderDto.setId(order.getId());
            merchantOrderDto.setGhMerchantOrderCode(order.getGhMerchantOrderCode());
            merchantOrderDto.setOrderTime(order.getOrderTime());
            merchantOrderDto.setMerchantCode(order.getMerchantCode());
            merchantOrderDto.setMerchantName(order.getMerchantName());
            merchantOrderDto.setSpaPurchaseCode(order.getSpaPurchaseCode());
            merchantOrderDto.setProductConfigCode(order.getProductConfigCode());
            merchantOrderDto.setParentBrandId(order.getParentBrandId());
            merchantOrderDto.setParentBrandName(getAttribInfoName(order.getParentBrandId(), mapAttribInfo));
            merchantOrderDto.setSubBrandId(order.getSubBrandId());
            merchantOrderDto.setSubBrandName(getAttribInfoName(order.getSubBrandId(), mapAttribInfo));
            merchantOrderDto.setAudiId(order.getAudiId());
            merchantOrderDto.setAudiName(getAttribInfoName(order.getAudiId(), mapAttribInfo));
            merchantOrderDto.setMotorcycle(order.getMotorcycle());
            merchantOrderDto.setCategoryId(order.getCategoryId());
            merchantOrderDto.setCategoryName(getAttribInfoName(order.getCategoryId(), mapAttribInfo));
            merchantOrderDto.setSpaProductCode(order.getSpaProductCode());
            merchantOrderDto.setSpaProductName(order.getSpaProductName());
            merchantOrderDto.setGlsxProductCode(order.getGlsxProductCode());
            merchantOrderDto.setGlsxProductName(order.getGlsxProductName());
            merchantOrderDto.setTotal(order.getTotal());
            merchantOrderDto.setRemark(order.getRemark());
            merchantOrderDto.setStatus(order.getStatus().toString());
            merchantOrderDto.setBsAddress(BsAddressRpcConvert.convertBean(order.getBsAddress()));
            merchantOrderDto.setReminderTotal(order.getReminderTotal());
            List<BsLogisticsDTO> bsLogisticsDTOList= bsLogisticsService.listLogisticsByGhMerchantOrderCode(order.getGhMerchantOrderCode());
            Integer shipmentsQuantity=0;
            for(BsLogisticsDTO logisticsDTO:bsLogisticsDTOList){
                shipmentsQuantity+=(StringUtils.isEmpty(logisticsDTO.getShipmentsQuantity())?0:logisticsDTO.getShipmentsQuantity());
            }
            merchantOrderDto.setShipmentsQuantity(shipmentsQuantity);
            merchantOrderDto.setOwerSend(merchantOrderDto.getTotal()-shipmentsQuantity);
            listMerchantOrderConfig = mapMerchantOrderConfig.get(order.getGhMerchantOrderCode());
            if (null != listMerchantOrderConfig) {
                listMerchantOrderConfigDto = new ArrayList<>();
                for (GhMerchantOrderConfig config : listMerchantOrderConfig) {
                    merchantOrderConfigDto = new GhMerchantOrderConfigDTO();
                    merchantOrderConfigDto.setAttribInfoId(config.getAttribInfoId());
                    merchantOrderConfigDto.setAttribInfoName(getAttribInfoName(config.getAttribInfoId(), mapAttribInfo));
                    merchantOrderConfigDto.setAttribTypeId(getAttribTypeId(config.getAttribInfoId(), mapAttribInfo));
                    merchantOrderConfigDto.setAttribTypeName(getAttribTypeName(config.getAttribInfoId(), mapAttribInfo));
                    merchantOrderConfigDto.setGhMerchantOrderCode(config.getGhMerchantOrderCode());
                    merchantOrderConfigDto.setId(config.getId());
                    merchantOrderConfigDto.setOption(config.getOption());
                    listMerchantOrderConfigDto.add(merchantOrderConfigDto);
                }
                merchantOrderDto.setListMerchantOrderConfig(listMerchantOrderConfigDto);
            }
            listReflectDispatch = mapMerchantOrderReflect.get(order.getGhMerchantOrderCode());
            if (null != listReflectDispatch) {
                listLogistics = new ArrayList<>();
                for (GhMerchantOrderReflectDispatch dispatch : listReflectDispatch) {
                    if (StringUtils.isEmpty(dispatch.getLogisticsDesc())) {
                        continue;
                    }
                    String logisticsDesc = dispatch.getLogisticsDesc();
                    try {
                        int j = 0;
                        while (true) {
                            j = logisticsDesc.indexOf(":");
                            String logisticsNum = logisticsDesc.substring(0, j);
                            logisticsDesc = logisticsDesc.substring(j + 1, logisticsDesc.length());
                            j = logisticsDesc.indexOf("\n");
                            String sendNum = logisticsDesc.substring(0, j);
                            logisticsDesc = logisticsDesc.substring(j + 1, logisticsDesc.length());
                            j = logisticsDesc.indexOf("\n");
                            String logisticsCmp = logisticsDesc.substring(0, j);
                            logisticsDesc = logisticsDesc.substring(j + 1, logisticsDesc.length());

                            bsLogisticsDto = new BsLogisticsDTO();
                            bsLogisticsDto.setCompany(logisticsCmp);
                            bsLogisticsDto.setOrderNumber(logisticsNum);
                            bsLogisticsDto.setShipmentsQuantity(Integer.valueOf(sendNum));
                            listLogistics.add(bsLogisticsDto);
                            if (StringUtils.isEmpty(logisticsDesc)) {
                                break;
                            }
                        }
                    } catch (Exception e) {
                        LOGGER.info("Failed get listLogistics logisticsDesc" + dispatch.getLogisticsDesc());
                    }
                }
                merchantOrderDto.setListLogistics(listLogistics);
            }
            pageResultDto.add(merchantOrderDto);
        }
        pageResultDto.setPageNum(pageMerchantOrder.getPageNum());
        pageResultDto.setPageSize(pageMerchantOrder.getPageSize());
        pageResultDto.setPages(pageMerchantOrder.getPages());
        pageResultDto.setTotal(pageMerchantOrder.getTotal());
        return pageResultDto;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
    public Integer subGhMerchantOrder(GhSubMerchantOrderDTO record) throws RpcServiceException {
        Integer result = 0;
        GhShoppingCartDTO shoppingCartDto = null;
        Map<String, List<GhShoppingCartConfig>> mapShoppingCartConfig = null;
        List<GhMerchantOrder> listMerchantOrder = new ArrayList<>();
        List<GhMerchantOrderConfig> listMerchantOrderConfig = new ArrayList<>();
        List<GhShoppingCartConfig> listCartConfig = null;
        GhMerchantOrder merchantOrder = null;
        GhMerchantOrderConfig merchantOrderConfig = null;
        List<String> listDtoCartCodes = new ArrayList<>();
        Map<String, GhShoppingCartDTO> mapDtoCart = new HashMap<>();
        for (GhShoppingCartDTO cartDto : record.getListShoppingCartCode()) {
            listDtoCartCodes.add(cartDto.getCartCode());
            mapDtoCart.put(cartDto.getCartCode(), cartDto);
        }
        List<String> listCartCodes = new ArrayList<>();
        List<GhShoppingCart> listShoppingCart = shoppingCartService.listShoppingCart(listDtoCartCodes);
        List<GhShoppingCartConfig> listShoppingCartConfig = shoppingCartService.listShoppingCartConfig(listDtoCartCodes);
        if (null != listShoppingCartConfig) {
            mapShoppingCartConfig = listShoppingCartConfig.stream().collect(Collectors.groupingBy(GhShoppingCartConfig::getCartCode));
        }
        if (null == mapShoppingCartConfig) {
            mapShoppingCartConfig = new HashMap<>();
        }
        if (null == listShoppingCart || listShoppingCart.isEmpty()) {
            LOGGER.info("GhShoppingCartService::subGhMerchantOrder valid gh shopping cart code!");
            throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_INVALID_GH_CART_CODE);
        }
        for (GhShoppingCart cart : listShoppingCart) {
            String cartCode = Constants.GH_ORDER_PREFIX + JstUtils.getDispatchOrderNumber(snowflakeWorker);
            shoppingCartDto = mapDtoCart.get(cart.getCartCode());
            merchantOrder = new GhMerchantOrder();
            merchantOrder.setGhMerchantOrderCode(cartCode);
            merchantOrder.setOrderTime(JstUtils.getNowDate());
            merchantOrder.setMerchantCode(record.getMerchantCode());
            merchantOrder.setMerchantName(record.getMerchantName());
            merchantOrder.setProductConfigCode(cart.getProductConfigCode());
            merchantOrder.setParentBrandId(cart.getParentBrandId());
            merchantOrder.setSubBrandId(cart.getSubBrandId());
            merchantOrder.setAudiId(cart.getAudiId());
            merchantOrder.setMotorcycle(cart.getMotorcycle());
            merchantOrder.setCategoryId(cart.getCategoryId());
            merchantOrder.setSpaProductCode(cart.getSpaProductCode());
            merchantOrder.setSpaProductName(cart.getSpaProductName());
            merchantOrder.setGlsxProductCode(cart.getGlsxProductCode());
            merchantOrder.setGlsxProductName(cart.getGlsxProductName());
            merchantOrder.setTotal(shoppingCartDto.getTotal());
            merchantOrder.setRemark(shoppingCartDto.getRemark());
            merchantOrder.setStatus(MerchantOrderStatuEnum.ORDER_WC.getCode());
            merchantOrder.setCreatedBy(record.getMerchantCode());
            merchantOrder.setCreatedDate(JstUtils.getNowDate());
            merchantOrder.setUpdatedBy(record.getMerchantCode());
            merchantOrder.setUpdatedDate(JstUtils.getNowDate());
            merchantOrder.setDeletedFlag("N");
            merchantOrder.setAddressId(record.getAddressId());
            merchantOrder.setSpaPurchaseCode(record.getSpaPurchaseCode());
            listMerchantOrder.add(merchantOrder);
            listCartConfig = mapShoppingCartConfig.get(cart.getCartCode());
            listCartCodes.add(cart.getCartCode());
            if (null == listCartConfig || listCartConfig.isEmpty()) {
                continue;
            }
            for (GhShoppingCartConfig cartConfig : listCartConfig) {
                merchantOrderConfig = new GhMerchantOrderConfig();
                merchantOrderConfig.setAttribInfoId(cartConfig.getAttribInfoId());
                merchantOrderConfig.setGhMerchantOrderCode(cartCode);
                merchantOrderConfig.setOption(cartConfig.getOption());
                merchantOrderConfig.setCreatedBy(record.getMerchantCode());
                merchantOrderConfig.setCreatedDate(JstUtils.getNowDate());
                merchantOrderConfig.setUpdatedBy(record.getMerchantCode());
                merchantOrderConfig.setUpdatedDate(JstUtils.getNowDate());
                merchantOrderConfig.setDeletedFlag("N");
                listMerchantOrderConfig.add(merchantOrderConfig);
            }
        }
        try {
            merchantOrderMapper.insertList(listMerchantOrder);
            if (!listMerchantOrderConfig.isEmpty()) {

                merchantOrderConfigMapper.insertMerchantOrderConfigList(listMerchantOrderConfig);
                //	merchantOrderConfigMapper.insertList(listMerchantOrderConfig);
            }
            shoppingCartService.delGhShoppingCartByCartCodes(listCartCodes);
            shoppingCartService.delGhShoppingCartConfigByCartCodes(listCartCodes);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new RpcServiceException(e.getMessage());
        }
        return result;
    }


    private String getDtoStatus(Byte status) {
        if (status.equals('1') || status.equals('2') || status.equals('3') || status.equals('4')) {
            return "U";
        } else if (status.equals('5')) {
            return "F";
        }
        return "U";
    }

    private GhMerchantOrder getMerchantOrder(String merchantOrderCode) {
        GhMerchantOrder condition = new GhMerchantOrder();
        condition.setGhMerchantOrderCode(merchantOrderCode);
        return merchantOrderMapper.selectOne(condition);
    }

    private GhMerchantOrder getLastMerchantOrder(String merchantOrderCode) {
        Example example = new Example(GhMerchantOrder.class);
        example.createCriteria().andEqualTo("merchantCode", merchantOrderCode);
        example.orderBy("id").desc();
        RowBounds rowBounds = new RowBounds(0, 1);
        List<GhMerchantOrder> listMerchantOrder = merchantOrderMapper.selectByExampleAndRowBounds(example, rowBounds);
        if (null != listMerchantOrder && !listMerchantOrder.isEmpty()) {
            return listMerchantOrder.get(0);
        }
        return null;
    }

    private List<GhMerchantOrderConfig> listMerchantOrderConfig(List<String> listMerchantOrderCodes) {
        if (null == listMerchantOrderCodes || listMerchantOrderCodes.isEmpty()) {
            return null;
        }
        GhMerchantOrderConfig condition = new GhMerchantOrderConfig();
        condition.setListGhMerchantCodes(listMerchantOrderCodes);
        return merchantOrderConfigMapper.selectMerchantOrderConfig(condition);
	/*	Example example = new Example(GhMerchantOrderConfig.class);
		example.createCriteria().andIn("ghMerchantOrderCode", listMerchantOrderCodes);
		return merchantOrderConfigMapper.selectByExample(example);*/
    }

    private String getAttribInfoName(Integer attribInfoId, Map<Integer, AttribInfo> mapAttribInfo) {
        String result = "";
        if (null == attribInfoId) {
            return result;
        }
        AttribInfo attribInfo = attribInfoService.getAttribInfoWithLocalCache(attribInfoId, mapAttribInfo);
        if (null != attribInfo) {
            result = attribInfo.getName();
        }
        return result;
    }

    private Integer getAttribTypeId(Integer attribInfoId, Map<Integer, AttribInfo> mapAttribInfo) {
        Integer result = null;
        if (null == attribInfoId) {
            return null;
        }
        AttribInfo attribInfo = attribInfoService.getAttribInfoWithLocalCache(attribInfoId, mapAttribInfo);
        if (null != attribInfo) {
            result = attribInfo.getType();
        }
        return result;
    }

    private String getAttribTypeName(Integer attribInfoId, Map<Integer, AttribInfo> mapAttribInfo) {
        String result = "";
        if (null == attribInfoId) {
            return result;
        }
        AttribInfo attribInfo = attribInfoService.getAttribInfoWithLocalCache(attribInfoId, mapAttribInfo);
        if (null != attribInfo) {
            result = attribInfo.getComment();
        }
        return result;
    }

    public List<GhMerchantOrderDTO> listGhMerchantOrderNoMerchantOrder() {
        List<GhMerchantOrderDTO> listResultDto = new ArrayList<>();
        Map<Integer, AttribInfo> mapAttribInfo = new HashMap<>();
        GhMerchantOrderDTO merchantOrderDto = null;
        GhMerchantOrderConfigDTO merchantOrderConfigDto = null;
        List<GhMerchantOrderConfigDTO> listMerchantOrderConfigDto = null;
        List<GhMerchantOrderConfig> listMerchantOrderConfig = null;
        List<BsLogisticsDTO> listLogistics = null;
        BsLogisticsDTO bsLogisticsDto = null;
        List<GhMerchantOrderReflectDispatch> listReflectDispatch = null;
        Map<String, List<GhMerchantOrderConfig>> mapMerchantOrderConfig = null;
        Map<String, List<GhMerchantOrderReflectDispatch>> mapMerchantOrderReflect = null;
        List<String> listMerchantOrderCode = new ArrayList<>();
        GhMerchantOrder condition = new GhMerchantOrder();
        List<GhMerchantOrder> listMerchantOrder = merchantOrderMapper.listGhMerchantOrderNoMerchantOrder(condition);
        List<Long> adressIds = new ArrayList<>();
        for (GhMerchantOrder ghMerchantOrder : listMerchantOrder) {
            adressIds.add(ghMerchantOrder.getAddressId().longValue());
        }
        Map<Long, BsAddress> mapBsAddress = addressService.listMapAddressByIds(adressIds);

        for (int i = 0; i < listMerchantOrder.size(); i++) {
            if (null == listMerchantOrder.get(0).getAddressId()) {
                continue;
            }
            listMerchantOrder.get(i).setBsAddress(mapBsAddress.get(listMerchantOrder.get(i).getAddressId().longValue()));
        }
        if (null == listMerchantOrder || listMerchantOrder.isEmpty()) {
            return listResultDto;
        }
        for (GhMerchantOrder order : listMerchantOrder) {
            listMerchantOrderCode.add(order.getGhMerchantOrderCode());
        }
        listMerchantOrderConfig = listMerchantOrderConfig(listMerchantOrderCode);
        if (null != listMerchantOrderConfig) {
            mapMerchantOrderConfig = listMerchantOrderConfig.stream().collect(Collectors.groupingBy(GhMerchantOrderConfig::getGhMerchantOrderCode));
        }
        if (null == mapMerchantOrderConfig) {
            mapMerchantOrderConfig = new HashMap<>();
        }
        List<GhMerchantOrderReflectDispatch> listRefect = merchantOrderReflectMapper.listReflectDispatch(listMerchantOrderCode);
        if (null != listRefect && !listRefect.isEmpty()) {
            mapMerchantOrderReflect = listRefect.stream().collect(Collectors.groupingBy(GhMerchantOrderReflectDispatch::getGhMerchantOrderCode));
        }
        if (null == mapMerchantOrderReflect) {
            mapMerchantOrderReflect = new HashMap<>();
        }
        for (GhMerchantOrder order : listMerchantOrder) {
            merchantOrderDto = new GhMerchantOrderDTO();
            merchantOrderDto.setId(order.getId());
            merchantOrderDto.setGhMerchantOrderCode(order.getGhMerchantOrderCode());
            merchantOrderDto.setOrderTime(order.getOrderTime());
            merchantOrderDto.setMerchantCode(order.getMerchantCode());
            merchantOrderDto.setProductConfigCode(order.getProductConfigCode());
            merchantOrderDto.setParentBrandId(order.getParentBrandId());
            merchantOrderDto.setParentBrandName(getAttribInfoName(order.getParentBrandId(), mapAttribInfo));
            merchantOrderDto.setSubBrandId(order.getSubBrandId());
            merchantOrderDto.setSubBrandName(getAttribInfoName(order.getSubBrandId(), mapAttribInfo));
            merchantOrderDto.setAudiId(order.getAudiId());
            merchantOrderDto.setAudiName(getAttribInfoName(order.getAudiId(), mapAttribInfo));
            merchantOrderDto.setMotorcycle(order.getMotorcycle());
            merchantOrderDto.setCategoryId(order.getCategoryId());
            merchantOrderDto.setCategoryName(getAttribInfoName(order.getCategoryId(), mapAttribInfo));
            merchantOrderDto.setSpaProductCode(order.getSpaProductCode());
            merchantOrderDto.setSpaProductName(order.getSpaProductName());
            merchantOrderDto.setGlsxProductCode(order.getGlsxProductCode());
            merchantOrderDto.setGlsxProductName(order.getGlsxProductName());
            merchantOrderDto.setTotal(order.getTotal());
            merchantOrderDto.setRemark(order.getRemark());
            merchantOrderDto.setStatus(getDtoStatus(order.getStatus()));
            merchantOrderDto.setMerchantName(order.getMerchantName());
            merchantOrderDto.setSpaPurchaseCode(order.getSpaPurchaseCode());
            merchantOrderDto.setBsAddress(BsAddressRpcConvert.convertBean(order.getBsAddress()));
            listMerchantOrderConfig = mapMerchantOrderConfig.get(order.getGhMerchantOrderCode());
            if (null != listMerchantOrderConfig) {
                listMerchantOrderConfigDto = new ArrayList<>();
                for (GhMerchantOrderConfig config : listMerchantOrderConfig) {
                    merchantOrderConfigDto = new GhMerchantOrderConfigDTO();
                    merchantOrderConfigDto.setAttribInfoId(config.getAttribInfoId());
                    merchantOrderConfigDto.setAttribInfoName(getAttribInfoName(config.getAttribInfoId(), mapAttribInfo));
                    merchantOrderConfigDto.setAttribTypeId(getAttribTypeId(config.getAttribInfoId(), mapAttribInfo));
                    merchantOrderConfigDto.setAttribTypeName(getAttribTypeName(config.getAttribInfoId(), mapAttribInfo));
                    merchantOrderConfigDto.setGhMerchantOrderCode(config.getGhMerchantOrderCode());
                    merchantOrderConfigDto.setId(config.getId());
                    merchantOrderConfigDto.setOption(config.getOption());
                    listMerchantOrderConfigDto.add(merchantOrderConfigDto);
                }
                merchantOrderDto.setListMerchantOrderConfig(listMerchantOrderConfigDto);
            }
            listReflectDispatch = mapMerchantOrderReflect.get(order.getGhMerchantOrderCode());
            if (null != listReflectDispatch) {
                listLogistics = new ArrayList<>();
                for (GhMerchantOrderReflectDispatch dispatch : listReflectDispatch) {
                    if (StringUtils.isEmpty(dispatch.getLogisticsDesc())) {
                        continue;
                    }
                    String logisticsDesc = dispatch.getLogisticsDesc();
                    try {
                        int j = 0;
                        while (true) {
                            j = logisticsDesc.indexOf(":");
                            String logisticsNum = logisticsDesc.substring(0, j);
                            logisticsDesc = logisticsDesc.substring(j + 1, logisticsDesc.length());
                            j = logisticsDesc.indexOf("\n");
                            String sendNum = logisticsDesc.substring(0, j);
                            logisticsDesc = logisticsDesc.substring(j + 1, logisticsDesc.length());
                            j = logisticsDesc.indexOf("\n");
                            String logisticsCmp = logisticsDesc.substring(0, j);
                            logisticsDesc = logisticsDesc.substring(j + 1, logisticsDesc.length());

                            bsLogisticsDto = new BsLogisticsDTO();
                            bsLogisticsDto.setCompany(logisticsCmp);
                            bsLogisticsDto.setOrderNumber(logisticsNum);
                            bsLogisticsDto.setShipmentsQuantity(Integer.valueOf(sendNum));
                            listLogistics.add(bsLogisticsDto);
                            if (StringUtils.isEmpty(logisticsDesc)) {
                                break;
                            }
                        }
                    } catch (Exception e) {
                        LOGGER.info("Failed get listLogistics logisticsDesc" + dispatch.getLogisticsDesc());
                    }
                }
                merchantOrderDto.setListLogistics(listLogistics);
            }
            listResultDto.add(merchantOrderDto);
        }
        return listResultDto;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
    public Integer createMerchantOrderByGhOrder() {
        List<GhMerchantOrderDTO> ghMerchantOrderDTOList = listGhMerchantOrderNoMerchantOrder();
        LOGGER.info("待生成的广汇订单总数 {}", ghMerchantOrderDTOList == null ? 0 : ghMerchantOrderDTOList.size());
        Integer result = 0;
        if (ghMerchantOrderDTOList == null || ghMerchantOrderDTOList.size() == 0) {
            return result;
        }

        List<BsMerchantOrder> listBsMerchantOrder = new ArrayList<>();
        List<BsMerchantOrderDetail> listBsMerchantOrderDetail = new ArrayList<>();
        List<BsMerchantOrderVehicle> listBsMerchantOrderVehicle = new ArrayList<>();
        List<GhMerchantOrderReflectMcode> ghMerchantOrderReflectMcodeList = new ArrayList<>();
        List<BsProduct> listBsProduct = new ArrayList<>();
        List<BsProductDetail> listBsProductDetail = new ArrayList<>();
        List<BsLogistics> listBsLogistics = new ArrayList<>();
        List<EcMerchantOrder> listEcMerchantOrder = new ArrayList<>();
        List<BsProductHistoryPrice> listProductPrice = new ArrayList<>();
        Map<String, AmMaterialInfo> mapCacheMaterialInfo = new HashMap<>();
        Map<Integer, String> mapCacheMerchantChannel = new HashMap<>();
        Map<Integer, String> mapCacheProductType = new HashMap<>();
        AmMaterialInfo materialInfo = null;
        BsMerchantOrder bsMerchantOrder = null;
        BsDealerUserInfo dealerUserInfo = null;
        GhMerchantOrderReflectMcode ghMerchantOrderReflectMcode = null;
        for (GhMerchantOrderDTO dto : ghMerchantOrderDTOList) {
            try {
                LOGGER.info("待生成的广汇订单信息 {}", dto.toString());
                dealerUserInfo = bsDealerUserInfoService.getBsDealerUserInfoByMerchantCode(dto.getMerchantCode());
                LOGGER.info("根据 {} 查询到的 dealerUserInfo信息 {}", dto.getMerchantCode(), dealerUserInfo.toString());
                BsAddressDTO address = dto.getBsAddress();

                List<String> listProductCodes = Arrays.asList(dto.getGlsxProductCode());
                Map<String, AmProductSplit> mapProductSplit = this.getProductSplitByListProductCodes(listProductCodes);
                if (mapProductSplit == null) {
                    LOGGER.warn("根据产品编码{} 查询产品拆分表数据为null", dto.getGlsxProductCode());
                    continue;
                }
                Map<String, List<AmProductSplitDetail>> mapProductSplitDetail = this.getProductSplitDetailByListProductCodes(listProductCodes);
                if (mapProductSplitDetail == null) {
                    LOGGER.warn("根据产品编码{} 查询产品拆分详情表数据为null", dto.getGlsxProductCode());
                    continue;
                }
                String bsMerchantOrderCode = Constants.BS_ORDER_PREFIX + JstUtils.generatorOrderCode(snowflakeWorker);
                String bsProductCode = Constants.BS_PRODUCT_CODE_PREFIX + JstUtils.generatorOrderCode(snowflakeWorker);

                List<AmProductSplitDetail> list = mapProductSplitDetail.get(dto.getGlsxProductCode());
                materialInfo = getMaterialInfoByMaterialCode(list.get(0).getMaterialCode(), mapCacheMaterialInfo);
                bsMerchantOrder = generatorBsMerchantOrder(bsMerchantOrderCode, dto, dealerUserInfo);
                ghMerchantOrderReflectMcode = generatorGhMerchantOrderReflectMcode(bsMerchantOrder, dto);
                ghMerchantOrderReflectMcodeList.add(ghMerchantOrderReflectMcode);
                listBsMerchantOrder.add(bsMerchantOrder);
                listBsMerchantOrderDetail.add(generatorBsMerchantOrderDetail(bsMerchantOrderCode, bsProductCode, dto, dealerUserInfo));
                listBsProduct.add(generatorBsProduct(bsProductCode, dto.getConsumer(), mapProductSplit.get(dto.getGlsxProductCode()), materialInfo));
                listBsProductDetail.add(generatorBsProductDetail(bsProductCode, dto.getConsumer(), mapProductSplit.get(dto.getGlsxProductCode()), materialInfo, mapProductSplitDetail));
                listBsLogistics.add(generatorBsLogistics(address.getId().intValue(), bsMerchantOrderCode, dto.getConsumer()));
                listEcMerchantOrder.add(generatorEcMerchantOrder(dealerUserInfo, address, bsMerchantOrderCode, mapProductSplit.get(dto.getGlsxProductCode()), materialInfo, bsMerchantOrder, mapCacheMerchantChannel, mapCacheProductType));
                listBsMerchantOrderVehicle.add(generatorBsMerchantOrderVehicle(bsMerchantOrderCode, dto));

                //订单价格
                List<AmProductSplitHistoryPrice> productPriceList = productService.getAmProductSplitPrice(dto.getGlsxProductCode());
                if (null != productPriceList && productPriceList.size() > 0) {
                    for (AmProductSplitHistoryPrice amProductSplitHistoryPrice : productPriceList) {
                        LOGGER.info("根据GlsxProductCode {} 查询到的 amProductSplitHistoryPrice信息 {}", dto.getGlsxProductCode(), amProductSplitHistoryPrice.toString());
                        listProductPrice.add(generatorBsProductPrice(amProductSplitHistoryPrice, dto, bsProductCode));
                    }
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                continue;
            }
        }

        try {
            if (listBsMerchantOrder.size() > 0) {
                this.insertListBsMerchantOrder(listBsMerchantOrder);
                this.insertListGhMerchantOrderReflectMcode(ghMerchantOrderReflectMcodeList);
                this.insertListBsMerchantOrderDetail(listBsMerchantOrderDetail);
                this.insertListBsMerchantOrderVehicle(listBsMerchantOrderVehicle);
                this.insertListEcMerchantOrder(listEcMerchantOrder);
                bsLogisticsService.insertLogisticsList(listBsLogistics);
                bsProductService.BatchSubmitBsProduct(listBsProduct);
                bsProductService.BatchSubmitBsProductDetail(listBsProductDetail);
                bsProductService.BatchSubmitBsProductPrice(listProductPrice);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new RpcServiceException(e.getMessage());
        }
        return result;
    }
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
    public Integer synchronizeMerchantOrderStatus(){
        Integer result=0;
        List<GhMerchantOrderReflectMcode> ghMerchantOrderReflectMcodeList=bsMerchantOrderMapper.listGhMerchantOrderReflectMcodeAndBsMerchantOrder();
        if(StringUtils.isEmpty(ghMerchantOrderReflectMcodeList)||ghMerchantOrderReflectMcodeList.size()==0){
            LOGGER.info("同步商户订单到广汇订单的总数为0,不进行同步");
            JSON.toJSONString(ghMerchantOrderReflectMcodeList);
            return result;
        }
        LOGGER.info("同步商户订单到广汇订单的总数为{}", ghMerchantOrderReflectMcodeList.size());
        for(GhMerchantOrderReflectMcode ghMerchantOrderReflectMcode:ghMerchantOrderReflectMcodeList){
            LOGGER.info("商户单号: {}, 状态: {}, 广汇单号: {}, 状态: {}",ghMerchantOrderReflectMcode.getMerchantOrder(), ghMerchantOrderReflectMcode.getBsStatus(),
                    ghMerchantOrderReflectMcode.getGhMerchantOrderCode(), ghMerchantOrderReflectMcode.getGhStatus());
        }
        result=bsMerchantOrderMapper.batchUpdateGhMerchantOrder(ghMerchantOrderReflectMcodeList);
        LOGGER.info("同步商户订单到广汇订单的总数",result);
        return result;

    }

    private Map<String, AmProductSplit> getProductSplitByListProductCodes(List<String> listProductCodes) {
        List<AmProductSplit> listProductSplit = productService.listProductSplitByProductCodes(listProductCodes);
        if (null == listProductSplit || listProductSplit.isEmpty()) {
            return null;
        }
        return listProductSplit.stream().collect(Collectors.toMap(e -> e.getProductCode(), e -> e));
    }

    private Map<String, List<AmProductSplitDetail>> getProductSplitDetailByListProductCodes(List<String> listProductCodes) {
        List<AmProductSplitDetail> listProductSplitDetail = productService.listProductSplitDetailsByProductCodes(listProductCodes);
        if (null == listProductSplitDetail || listProductSplitDetail.isEmpty()) {
            return null;
        }
        return listProductSplitDetail.stream().collect(Collectors.groupingBy(AmProductSplitDetail::getProductCode));
    }

    private AmMaterialInfo getMaterialInfoByMaterialCode(String materialCode, Map<String, AmMaterialInfo> mapCacheMaterialInfo) {
        AmMaterialInfo result = null;
        if (null != mapCacheMaterialInfo) {
            result = mapCacheMaterialInfo.get(materialCode);
        }
        if (null != result) {
            return result;
        }
        result = productService.getMaterialInfoByMaterialCode(materialCode);
        if (null == result) {
            return result;
        }
        if (null != mapCacheMaterialInfo) {
            mapCacheMaterialInfo.put(materialCode, result);
        }
        return result;
    }

    private BsMerchantOrder generatorBsMerchantOrder(String merchantOrderCode, GhMerchantOrderDTO ghMerchantOrderDTO, BsDealerUserInfo bsDealerUserInfo) {
        BsMerchantOrder bsMerchantOrder = new BsMerchantOrder();
        bsMerchantOrder.setOrderNumber(merchantOrderCode);
        bsMerchantOrder.setOrderTime(ghMerchantOrderDTO.getOrderTime());
        bsMerchantOrder.setHopeTime(JstUtils.getFetureDate(1));
        bsMerchantOrder.setMerchantCode(ghMerchantOrderDTO.getMerchantCode());
        bsMerchantOrder.setTotalOrder(ghMerchantOrderDTO.getTotal());
        bsMerchantOrder.setTotalCheck(0);
        bsMerchantOrder.setTotalAmount(0.0);
        bsMerchantOrder.setStatus(MerchantOrderStatuEnum.ORDER_WC.getCode());
        bsMerchantOrder.setRemarks(ghMerchantOrderDTO.getRemark());
        bsMerchantOrder.setSignStatus(MerchantOrderSignStatusEnum.ORDER_SIGN_STATUS_UNSEND.getCode());
        bsMerchantOrder.setProductTotal(ghMerchantOrderDTO.getTotal());
        bsMerchantOrder.setCreatedBy(bsDealerUserInfo.getName());
        bsMerchantOrder.setUpdatedBy(bsDealerUserInfo.getName());
        bsMerchantOrder.setCreatedDate(JstUtils.getNowDate());
        bsMerchantOrder.setUpdatedDate(JstUtils.getNowDate());
        bsMerchantOrder.setDeletedFlag("N");
        return bsMerchantOrder;
    }

    private GhMerchantOrderReflectMcode generatorGhMerchantOrderReflectMcode(BsMerchantOrder bsMerchantOrder, GhMerchantOrderDTO dto) {
        GhMerchantOrderReflectMcode ghMerchantOrderReflectMcode = new GhMerchantOrderReflectMcode();
        ghMerchantOrderReflectMcode.setMerchantOrder(bsMerchantOrder.getOrderNumber());
        ghMerchantOrderReflectMcode.setGhMerchantOrderCode(dto.getGhMerchantOrderCode());
        ghMerchantOrderReflectMcode.setCreatedBy(dto.getMerchantCode());
        ghMerchantOrderReflectMcode.setUpdatedBy(dto.getMerchantCode());
        ghMerchantOrderReflectMcode.setCreatedDate(new Date());
        ghMerchantOrderReflectMcode.setUpdatedDate(new Date());
        ghMerchantOrderReflectMcode.setDeletedFlag("N");
        return ghMerchantOrderReflectMcode;
    }

    private BsMerchantOrderDetail generatorBsMerchantOrderDetail(String merchantOrderCode, String bsProductCode, GhMerchantOrderDTO ghMerchantOrderDTO, BsDealerUserInfo bsDealerUserInfo) {
        BsMerchantOrderDetail bsMerchantOrderDetail = new BsMerchantOrderDetail();
        bsMerchantOrderDetail.setMerchantOrderNumber(merchantOrderCode);
        bsMerchantOrderDetail.setProductCode(bsProductCode);
        bsMerchantOrderDetail.setOrderQuantity(ghMerchantOrderDTO.getTotal());
        bsMerchantOrderDetail.setProductRemarks(ghMerchantOrderDTO.getRemark());
        bsMerchantOrderDetail.setCreatedBy(bsDealerUserInfo.getName());
        bsMerchantOrderDetail.setUpdatedBy(bsDealerUserInfo.getName());
        bsMerchantOrderDetail.setCreatedDate(JstUtils.getNowDate());
        bsMerchantOrderDetail.setUpdatedDate(JstUtils.getNowDate());
        bsMerchantOrderDetail.setDeletedFlag("N");
        return bsMerchantOrderDetail;
    }

    private BsProduct generatorBsProduct(String bsProductCode, String consumer, AmProductSplit productSplit, AmMaterialInfo materialInfo) {
        BsProduct bsProduct = new BsProduct();
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
        bsProduct.setCreatedDate(JstUtils.getNowDate());
        bsProduct.setUpdatedDate(JstUtils.getNowDate());
        bsProduct.setDeletedFlag("N");
        return bsProduct;
    }

    private BsProductDetail generatorBsProductDetail(String bsProductCode, String consumer, AmProductSplit productSplit, AmMaterialInfo materialInfo, Map<String, List<AmProductSplitDetail>> mapProductSplitDetail) {
        List<AmProductSplitDetail> listSplitDetail = mapProductSplitDetail.get(productSplit.getProductCode());
        AmProductSplitDetail splitDetail = null;
        for (AmProductSplitDetail detail : listSplitDetail) {
            if (detail.getMaterialCode().equals(materialInfo.getMaterialCode())) {
                splitDetail = detail;
                break;
            }
        }
        BsProductDetail bsProductDetail = new BsProductDetail();
        bsProductDetail.setServiceType(productSplit.getServiceType());
        bsProductDetail.setMaterialName(materialInfo.getMaterialName());
        bsProductDetail.setProductCode(bsProductCode);
        bsProductDetail.setMaterialCode(materialInfo.getMaterialCode());
        bsProductDetail.setType((null != splitDetail) ? Byte.valueOf(splitDetail.getProductType()) : null);
        bsProductDetail.setPropQuantity((null != splitDetail) ? splitDetail.getPropQuantity() : null);
        bsProductDetail.setOrderMaterialCode(materialInfo.getMaterialCode());
        bsProductDetail.setOrderMaterialName(materialInfo.getMaterialName());
        bsProductDetail.setCreatedBy(consumer);
        bsProductDetail.setUpdatedBy(consumer);
        bsProductDetail.setCreatedDate(JstUtils.getNowDate());
        bsProductDetail.setUpdatedDate(JstUtils.getNowDate());
        bsProductDetail.setDeletedFlag("N");
        return bsProductDetail;
    }

    private BsLogistics generatorBsLogistics(Integer addressId, String bsMerchantCode, String consumer) {
        BsLogistics logistics = new BsLogistics();
        String logiCode = Constants.LOGI_ORDER_PREFIX + JstUtils.generatorOrderCode(snowflakeWorker);
        logistics.setReceiveId(addressId.longValue());
        logistics.setCode(logiCode);
        logistics.setType(Byte.valueOf("1"));
        logistics.setServiceCode(bsMerchantCode);
        logistics.setCreatedBy(consumer);
        logistics.setUpdatedBy(consumer);
        logistics.setCreatedDate(JstUtils.getNowDate());
        logistics.setUpdatedDate(JstUtils.getNowDate());
        logistics.setDeletedFlag("N");
        return logistics;
    }


    private EcMerchantOrder generatorEcMerchantOrder(BsDealerUserInfo dealerUserInfo, BsAddressDTO address, String bsMerchantCode,
                                                     AmProductSplit productSplit, AmMaterialInfo materialInfo,
                                                     BsMerchantOrder jxcMerchantOrder, Map<Integer, String> mapCacheMerchantChannel,
                                                     Map<Integer, String> mapCacheProductType) {
        EcMerchantOrder ecMerchantOrder = new EcMerchantOrder();
        ecMerchantOrder.setChannel(getChannelName(dealerUserInfo.getChannel()));
        ecMerchantOrder.setMerchantCode(dealerUserInfo.getMerchantCode());
        ecMerchantOrder.setMerchantName(dealerUserInfo.getMerchantName());
        ecMerchantOrder.setOrderNumber(bsMerchantCode);
        ecMerchantOrder.setProductCode(productSplit.getProductCode());
        ecMerchantOrder.setProductName(productSplit.getProductName());
        ecMerchantOrder.setMaterialCode(materialInfo.getMaterialCode());
        ecMerchantOrder.setMaterialName(materialInfo.getMaterialName());
        ecMerchantOrder.setDeviceType(materialInfo.getDeviceType());
        ecMerchantOrder.setPrice(jxcMerchantOrder.getBsPrice() == null ? 0.0 : jxcMerchantOrder.getBsPrice());
        ecMerchantOrder.setOrderQuantity(jxcMerchantOrder.getTotalOrder());
        ecMerchantOrder.setCheckQuantity(0);
        ecMerchantOrder.setDispatchOrderNumber("");
        ecMerchantOrder.setAlreadyShipmentQuantity(0);
        ecMerchantOrder.setShipmentTime("");
        ecMerchantOrder.setShipmentQuantity("");
        ecMerchantOrder.setSignQuantity(0);
        ecMerchantOrder.setOweQuantity(0);
        ecMerchantOrder.setTotalAmount(jxcMerchantOrder.getTotalAmount() * ecMerchantOrder.getPrice());
        ecMerchantOrder.setOrderTime(JstUtils.getStringFromDate(jxcMerchantOrder.getCreatedDate()));
        ecMerchantOrder.setProductRemarks("");
        ecMerchantOrder.setCheckBy("");
        ecMerchantOrder.setCheckTime("");
        ecMerchantOrder.setStatus(MerchantOrderStatuEnum.ORDER_WC.getName());
        ecMerchantOrder.setAddressee(address.getName());
        ecMerchantOrder.setMobile(address.getMobile());
        ecMerchantOrder.setAddressDetail((StringUtils.isEmpty(address.getProvinceName()) ? "" : address.getProvinceName())
                + (StringUtils.isEmpty(address.getCityName()) ? "" : address.getCityName()) + (StringUtils.isEmpty(address.getAreaName()) ? "" : address.getAreaName())
                + address.getAddress());
        ecMerchantOrder.setCreatedBy(dealerUserInfo.getName());
        ecMerchantOrder.setUpdatedBy(dealerUserInfo.getName());
        ecMerchantOrder.setCreatedDate(JstUtils.getNowDate());
        ecMerchantOrder.setUpdatedDate(JstUtils.getNowDate());
        ecMerchantOrder.setDeletedFlag("N");
        ecMerchantOrder.setMerchantCode(dealerUserInfo.getMerchantCode());
        ecMerchantOrder.setLogisticsDesc("");
        ecMerchantOrder.setProductTotal(jxcMerchantOrder.getTotalOrder());
        ecMerchantOrder.setSignStatus(MerchantOrderSignStatusEnum.ORDER_SIGN_STATUS_UNSIGN.getName());
        ecMerchantOrder.setMaterialCode(materialInfo.getMaterialCode());
        ecMerchantOrder.setMaterialName(materialInfo.getMaterialName());
        ecMerchantOrder.setProductRemarks(jxcMerchantOrder.getRemarks());
        ecMerchantOrder.setOrderRemark(jxcMerchantOrder.getRemarks());
        return ecMerchantOrder;
    }

    private String getChannelName(Byte channel) {
        if (channel.equals(DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_GHUI.getCode())) {
            return DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_GHUI.getName();
        } else if (channel.equals(DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_OTHER.getCode())) {
            return DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_OTHER.getName();
        } else if (channel.equals(DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_TMHUI.getCode())) {
            return DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_TMHUI.getName();
        } else if (channel.equals(DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_CHANNEL.getCode())) {
            return DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_CHANNEL.getName();
        } else if (channel.equals(DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_FIVE.getCode())) {
            return DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_FIVE.getName();
        } else if (channel.equals(DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_SIX.getCode())) {
            return DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_SIX.getName();
        } else if (channel.equals(DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_SEVER.getCode())) {
            return DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_SEVER.getName();
        } else if (channel.equals(DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_EIGHT.getCode())) {
            return DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_EIGHT.getName();
        }
        return "";
    }

    private BsMerchantOrderVehicle generatorBsMerchantOrderVehicle(String bsMerchantOrder, GhMerchantOrderDTO ghMerchantOrderDTO) {
        BsMerchantOrderVehicle vehicle = new BsMerchantOrderVehicle();
        vehicle.setMerchantOrder(bsMerchantOrder);
        vehicle.setBsParentBrandId(ghMerchantOrderDTO.getParentBrandId());
        vehicle.setBsParentBrandName(ghMerchantOrderDTO.getParentBrandName());
        vehicle.setBsSubBrandId(ghMerchantOrderDTO.getSubBrandId());
        vehicle.setBsSubBrandName(ghMerchantOrderDTO.getSubBrandName());
        vehicle.setBsAudiId(ghMerchantOrderDTO.getAudiId());
        vehicle.setBsAudiName(ghMerchantOrderDTO.getAudiName());
        vehicle.setBsMotorcycle(ghMerchantOrderDTO.getMotorcycle());
        // vehicle.setBsRemark(ghMerchantOrderDTO.getRemark());
        vehicle.setBsTotal(ghMerchantOrderDTO.getTotal());
        vehicle.setBsCheckQuantity(ghMerchantOrderDTO.getTotal());
        vehicle.setBsTotal(ghMerchantOrderDTO.getTotal());
        vehicle.setBsCheckQuantity(ghMerchantOrderDTO.getTotal());
        vehicle.setCreatedBy(ghMerchantOrderDTO.getConsumer());
        vehicle.setUpdatedBy(ghMerchantOrderDTO.getConsumer());
        vehicle.setCreatedDate(JstUtils.getNowDate());
        vehicle.setUpdatedDate(JstUtils.getNowDate());
        vehicle.setDeletedFlag("N");
        return vehicle;
    }

    private BsProductHistoryPrice generatorBsProductPrice(AmProductSplitHistoryPrice amProductSplitHistoryPrice, GhMerchantOrderDTO dto, String bsProductCode) {
        BsProductHistoryPrice bsProductPrice = new BsProductHistoryPrice();
        bsProductPrice.setProductCode(bsProductCode);
        bsProductPrice.setTime(amProductSplitHistoryPrice.getTime());
        bsProductPrice.setType(amProductSplitHistoryPrice.getType());
        bsProductPrice.setPrice(amProductSplitHistoryPrice.getPrice());
        bsProductPrice.setCreatedBy(dto.getConsumer());
        bsProductPrice.setUpdatedBy(dto.getConsumer());
        bsProductPrice.setCreatedDate(JstUtils.getNowDate());
        bsProductPrice.setUpdatedDate(JstUtils.getNowDate());
        bsProductPrice.setDeletedFlag("N");
        bsProductPrice.setProductType(amProductSplitHistoryPrice.getProductType());
        bsProductPrice.setServiceType(amProductSplitHistoryPrice.getServiceType());
        bsProductPrice.setTaxRate(amProductSplitHistoryPrice.getTaxRate());
        bsProductPrice.setMaterialCode(amProductSplitHistoryPrice.getMaterialCode());
        bsProductPrice.setMaterialName(amProductSplitHistoryPrice.getMaterialName());
        return bsProductPrice;
    }

    private Integer insertListBsMerchantOrder(List<BsMerchantOrder> listJxcmtBsMerchantOrder) {
        if (null == listJxcmtBsMerchantOrder || listJxcmtBsMerchantOrder.isEmpty()) {
            return 0;
        }
        return bsMerchantOrderMapper.insertList(listJxcmtBsMerchantOrder);
    }

    private Integer insertListBsMerchantOrderDetail(List<BsMerchantOrderDetail> listJxcmtBsMerchantOrderDetail) {
        if (null == listJxcmtBsMerchantOrderDetail || listJxcmtBsMerchantOrderDetail.isEmpty()) {
            return 0;
        }
        return bsMerchantOrderDetailMapper.insertList(listJxcmtBsMerchantOrderDetail);
    }


    private Integer insertListBsMerchantOrderVehicle(List<BsMerchantOrderVehicle> listJxcmtBsMerchantOrderVehicle) {
        if (null == listJxcmtBsMerchantOrderVehicle || listJxcmtBsMerchantOrderVehicle.isEmpty()) {
            return 0;
        }
        return bsMerchantOrderVehicleMapper.insertList(listJxcmtBsMerchantOrderVehicle);
    }

    private Integer insertListEcMerchantOrder(List<EcMerchantOrder> listEcMerchantOrder) {
        if (null == listEcMerchantOrder || listEcMerchantOrder.isEmpty()) {
            return 0;
        }
        return ecMerchantOrderMapper.insertList(listEcMerchantOrder);
    }

    private Integer insertListGhMerchantOrderReflectMcode(List<GhMerchantOrderReflectMcode> ghMerchantOrderReflectMcodeList) {
        if (null == ghMerchantOrderReflectMcodeList || ghMerchantOrderReflectMcodeList.isEmpty()) {
            return 0;
        }
        return ghMerchantOrderReflectMcodeMapper.insertList(ghMerchantOrderReflectMcodeList);
    }

    public Page<NewGhMerchantOrderDTO> wxlistMerchantOrders(RpcPagination<NewGhMerchantOrderDTO> pagination) {
        Page<NewGhMerchantOrderDTO> pageResultDto = new Page<NewGhMerchantOrderDTO>();
        Map<Integer, AttribInfo> mapAttribInfo = new HashMap<>();
        NewGhMerchantOrderDTO merchantOrderDto = null;
        GhMerchantOrderConfigDTO merchantOrderConfigDto = null;
        List<GhMerchantOrderConfigDTO> listMerchantOrderConfigDto = null;
        List<GhMerchantOrderConfig> listMerchantOrderConfig = null;
        List<BsLogisticsDTO> listLogistics = null;
        BsLogisticsDTO bsLogisticsDto = null;
        List<GhMerchantOrderReflectDispatch> listReflectDispatch = null;
        Map<String, List<GhMerchantOrderConfig>> mapMerchantOrderConfig = null;
        Map<String, List<GhMerchantOrderReflectDispatch>> mapMerchantOrderReflect = null;
        List<String> listMerchantOrderCode = new ArrayList<>();
        NewGhMerchantOrderDTO dtoRecord=pagination.getCondition();
        PageHelper.startPage(pagination.getPageNum(), pagination.getPageSize());
        GhMerchantOrder condition = new GhMerchantOrder();
        condition.setDtoStatus(dtoRecord.getDtoStatus());
        condition.setCategoryId(dtoRecord.getCategoryId());
        condition.setSpaProductCode(dtoRecord.getSpaProductCode());
        condition.setSpaProductName(dtoRecord.getSpaProductName());
        condition.setSpaPurchaseCode(dtoRecord.getSpaPurchaseCode());
        condition.setMerchantCode(dtoRecord.getMerchantCode());
        Page<GhMerchantOrder> pageMerchantOrder = merchantOrderMapper.wxlistMerchantOrders(condition);
        if (null == pageMerchantOrder || null == pageMerchantOrder.getResult() || pageMerchantOrder.getResult().isEmpty()) {
            return pageResultDto;
        }
        for (GhMerchantOrder order : pageMerchantOrder.getResult()) {
            listMerchantOrderCode.add(order.getGhMerchantOrderCode());
        }
        listMerchantOrderConfig = listMerchantOrderConfig(listMerchantOrderCode);
        if (null != listMerchantOrderConfig) {
            mapMerchantOrderConfig = listMerchantOrderConfig.stream().collect(Collectors.groupingBy(GhMerchantOrderConfig::getGhMerchantOrderCode));
        }
        if (null == mapMerchantOrderConfig) {
            mapMerchantOrderConfig = new HashMap<>();
        }
        List<GhMerchantOrderReflectDispatch> listRefect = merchantOrderReflectMapper.listReflectDispatch(listMerchantOrderCode);
        if (null != listRefect && !listRefect.isEmpty()) {
            mapMerchantOrderReflect = listRefect.stream().collect(Collectors.groupingBy(GhMerchantOrderReflectDispatch::getGhMerchantOrderCode));
        }
        if (null == mapMerchantOrderReflect) {
            mapMerchantOrderReflect = new HashMap<>();
        }
        for (GhMerchantOrder order : pageMerchantOrder.getResult()) {
            merchantOrderDto = new NewGhMerchantOrderDTO();
            if(order.getStatus()==0||order.getStatus()==9){

                merchantOrderDto.setConversionStatus(GhMerchantOrderStatuEnum.ORDER_FB.getName());
            }else if(order.getStatus()==1 || order.getStatus()==2 || order.getStatus()==8){
                merchantOrderDto.setConversionStatus(GhMerchantOrderStatuEnum.ORDER_WS.getName());
            }else if(order.getStatus()==3 || order.getStatus()==4){
                merchantOrderDto.setConversionStatus(GhMerchantOrderStatuEnum.ORDER_SP.getName());
            }else if(order.getStatus()==5 || order.getStatus()==6 || order.getStatus()==7){
                merchantOrderDto.setConversionStatus(GhMerchantOrderStatuEnum.ORDER_FI.getName());
            }
            merchantOrderDto.setId(order.getId());
            merchantOrderDto.setGhMerchantOrderCode(order.getGhMerchantOrderCode());
            merchantOrderDto.setOrderTime(order.getOrderTime());
            merchantOrderDto.setMerchantCode(order.getMerchantCode());
            merchantOrderDto.setMerchantName(order.getMerchantName());
            merchantOrderDto.setSpaPurchaseCode(order.getSpaPurchaseCode());
            merchantOrderDto.setProductConfigCode(order.getProductConfigCode());
            merchantOrderDto.setParentBrandId(order.getParentBrandId());
            merchantOrderDto.setParentBrandName(getAttribInfoName(order.getParentBrandId(), mapAttribInfo));
            merchantOrderDto.setSubBrandId(order.getSubBrandId());
            merchantOrderDto.setSubBrandName(getAttribInfoName(order.getSubBrandId(), mapAttribInfo));
            merchantOrderDto.setAudiId(order.getAudiId());
            merchantOrderDto.setAudiName(getAttribInfoName(order.getAudiId(), mapAttribInfo));
            merchantOrderDto.setMotorcycle(order.getMotorcycle());
            merchantOrderDto.setCategoryId(order.getCategoryId());
            merchantOrderDto.setCategoryName(getAttribInfoName(order.getCategoryId(), mapAttribInfo));
            merchantOrderDto.setSpaProductCode(order.getSpaProductCode());
            merchantOrderDto.setSpaProductName(order.getSpaProductName());
            merchantOrderDto.setGlsxProductCode(order.getGlsxProductCode());
            merchantOrderDto.setGlsxProductName(order.getGlsxProductName());
            merchantOrderDto.setTotal(StringUtils.isEmpty(order.getTotal())?0:order.getTotal());
            merchantOrderDto.setRemark(order.getRemark());
            merchantOrderDto.setStatus(order.getStatus().toString());
            merchantOrderDto.setBsAddress(BsAddressRpcConvert.convertBean(order.getBsAddress()));
            Integer shipmentsQuantity=0;
            List<BsLogisticsDTO> bsLogisticsDTOList= bsLogisticsService.listLogisticsByGhMerchantOrderCode(order.getGhMerchantOrderCode());
            for(BsLogisticsDTO logisticsDTO:bsLogisticsDTOList){
                shipmentsQuantity+=(StringUtils.isEmpty(logisticsDTO.getShipmentsQuantity())?0:logisticsDTO.getShipmentsQuantity());
            }
            merchantOrderDto.setShipmentsQuantity(shipmentsQuantity);
            merchantOrderDto.setOwerSend(merchantOrderDto.getTotal()-shipmentsQuantity);
            if(merchantOrderDto.getTotal()==0) {
                merchantOrderDto.setPercentage("0%");
            }else{
                double percent =merchantOrderDto.getShipmentsQuantity()/merchantOrderDto.getTotal();
                BigDecimal b=new BigDecimal(percent);
                double percentage=b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                NumberFormat nt = NumberFormat.getPercentInstance();
                //nt.setMinimumFractionDigits(2);
                merchantOrderDto.setPercentage(nt.format(percentage));
            }
            listMerchantOrderConfig = mapMerchantOrderConfig.get(order.getGhMerchantOrderCode());
            if (null != listMerchantOrderConfig) {
                listMerchantOrderConfigDto = new ArrayList<>();
                for (GhMerchantOrderConfig config : listMerchantOrderConfig) {
                    merchantOrderConfigDto = new GhMerchantOrderConfigDTO();
                    merchantOrderConfigDto.setAttribInfoId(config.getAttribInfoId());
                    merchantOrderConfigDto.setAttribInfoName(getAttribInfoName(config.getAttribInfoId(), mapAttribInfo));
                    merchantOrderConfigDto.setAttribTypeId(getAttribTypeId(config.getAttribInfoId(), mapAttribInfo));
                    merchantOrderConfigDto.setAttribTypeName(getAttribTypeName(config.getAttribInfoId(), mapAttribInfo));
                    merchantOrderConfigDto.setGhMerchantOrderCode(config.getGhMerchantOrderCode());
                    merchantOrderConfigDto.setId(config.getId());
                    merchantOrderConfigDto.setOption(config.getOption());
                    listMerchantOrderConfigDto.add(merchantOrderConfigDto);
                }
                merchantOrderDto.setListMerchantOrderConfig(listMerchantOrderConfigDto);
            }
            pageResultDto.add(merchantOrderDto);
        }
        pageResultDto.setPageNum(pageMerchantOrder.getPageNum());
        pageResultDto.setPageSize(pageMerchantOrder.getPageSize());
        pageResultDto.setPages(pageMerchantOrder.getPages());
        pageResultDto.setTotal(pageMerchantOrder.getTotal());
        return pageResultDto;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
    public Integer wxReminderOrder(GhMerchantOrderDTO ghMerchantOrderDTO) {
        LOGGER.info("广汇采购订单-催单请求 {}", ghMerchantOrderDTO.toString());

        Integer result = 0;
        Integer total = 0;
        GhMerchantOrderReminder reminder = new GhMerchantOrderReminder();
        reminder.setSpaPurchaseCode(ghMerchantOrderDTO.getSpaPurchaseCode());
        reminder.setGhMerchantOrderCode(ghMerchantOrderDTO.getGhMerchantOrderCode());
        GhMerchantOrderReminder exitReminder = ghMerchantOrderReminderMapper.selectOne(reminder);
        if (exitReminder != null){
            exitReminder.setReminderTotal(exitReminder.getReminderTotal() + 1);
            exitReminder.setUpdatedDate(JstUtils.getNowDate());
            ghMerchantOrderReminderMapper.updateByPrimaryKey(exitReminder);
            total = exitReminder.getReminderTotal();
        }else {
            reminder.setReminderTotal(1);
            reminder.setCreatedDate(JstUtils.getNowDate());
            reminder.setUpdatedDate(JstUtils.getNowDate());
            ghMerchantOrderReminderMapper.insert(reminder);
            total = reminder.getReminderTotal();
        }

        if (total == 1 || total == 5){
            MailUtils cn = new MailUtils();
            // 设置发件人地址、收件人地址和邮件标题
            String sendAddr = systemProperty.getAddress();
            String sendName = systemProperty.getSendName();
            String sendPwd = systemProperty.getSendPwd();
            String sendSmtp = systemProperty.getSendSmtp();
            String recieveAddr = systemProperty.getSupplyReceiver();
            String mailHead = Constants.ORDER_REMINDER_MAIL_HEAD;
            String mailContent = total < 5 ? Constants.ORDER_REMINDER_MAIL_CONTENT : Constants.ORDER_REMINDER_MAIL_CONTENT_REPEAT;
            mailContent = MessageFormat.format(mailContent, ghMerchantOrderDTO.getMerchantName(),
                    ghMerchantOrderDTO.getGhMerchantOrderCode(), String.valueOf(total));
            // 获取多个收件人
            String[] str = recieveAddr.split(",");
            List<String> toList = Arrays.asList(str);
            cn.setAddress(sendAddr, toList, mailHead);
            cn.send(sendSmtp, sendName, sendPwd, mailContent);
        }

        return total;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
    public Integer wxCancelOrder(GhMerchantOrderDTO ghMerchantOrderDTO) {
        LOGGER.info("广汇采购订单-取消订单请求SpaPurchaseCode = {}, ghMerchantOrderCode = {}",
                ghMerchantOrderDTO.getSpaPurchaseCode(), ghMerchantOrderDTO.getGhMerchantOrderCode());
        Integer result = 0;

        try{
            GhMerchantOrder queryGhOrder = new GhMerchantOrder();
//            queryGhOrder.setSpaPurchaseCode(ghMerchantOrderDTO.getSpaPurchaseCode());
            queryGhOrder.setGhMerchantOrderCode(ghMerchantOrderDTO.getGhMerchantOrderCode());
            GhMerchantOrder ghMerchantOrder = merchantOrderMapper.selectOne(queryGhOrder);
            LOGGER.info("待取消的广汇订单 {}, 状态 {}", ghMerchantOrder.getGhMerchantOrderCode(), ghMerchantOrder.getStatus());
            if (ghMerchantOrder != null && ghMerchantOrder.getStatus() != MerchantOrderStatuEnum.ORDER_WC.getCode()
                    && ghMerchantOrder.getStatus() != MerchantOrderStatuEnum.ORDER_WS.getCode()){
                LOGGER.warn("广汇订单 {} 为 {} 状态，取消订单失败", ghMerchantOrderDTO.getGhMerchantOrderCode(),
                        MerchantOrderStatuEnum.getName(ghMerchantOrder.getStatus()));
                throw new RpcServiceException("订单" + MerchantOrderStatuEnum.getName(ghMerchantOrder.getStatus()));
            }

            Example example = new Example(GhMerchantOrder.class);
            example.createCriteria().andEqualTo("ghMerchantOrderCode", ghMerchantOrderDTO.getGhMerchantOrderCode());
            ghMerchantOrder.setStatus(MerchantOrderStatuEnum.ORDER_ALREADY_CANCEL.getCode());
            merchantOrderMapper.updateByExampleSelective(ghMerchantOrder, example);

            GhMerchantOrderReflect queryOrderReflect = new GhMerchantOrderReflect();
            queryOrderReflect.setGhMerchantOrderCode(ghMerchantOrderDTO.getGhMerchantOrderCode());
            GhMerchantOrderReflect ghMerchantOrderReflect = merchantOrderReflectMapper.selectOne(queryOrderReflect);
            if (ghMerchantOrderReflect != null){
                BsMerchantOrder queryBsMerchantOrder = new BsMerchantOrder();
                queryBsMerchantOrder.setOrderNumber(ghMerchantOrderReflect.getMerchantOrder());
                BsMerchantOrder bsMerchantOrder = bsMerchantOrderMapper.selectOne(queryBsMerchantOrder);
                LOGGER.info("{} 对应的商户订单 {}, 状态 {}", ghMerchantOrder.getGhMerchantOrderCode(),
                        bsMerchantOrder.getOrderNumber(),bsMerchantOrder.getStatus());
                if (bsMerchantOrder != null && bsMerchantOrder.getStatus() != MerchantOrderStatuEnum.ORDER_WC.getCode()
                        && bsMerchantOrder.getStatus() != MerchantOrderStatuEnum.ORDER_WS.getCode()){
                    LOGGER.warn("商户订单 {} 为 {} 状态，取消订单失败", ghMerchantOrderReflect.getMerchantOrder(),
                            MerchantOrderStatuEnum.getName(bsMerchantOrder.getStatus()));
                    throw new RpcServiceException("订单" + MerchantOrderStatuEnum.getName(bsMerchantOrder.getStatus()));
                }

                LOGGER.info("更新商户订单 {} 的状态为取消（已驳回）", bsMerchantOrder.getOrderNumber());
                example = new Example(BsMerchantOrder.class);
                example.createCriteria().andEqualTo("orderNumber", bsMerchantOrder.getOrderNumber());
                bsMerchantOrder.setStatus(MerchantOrderStatuEnum.ORDER_ALREADY_CANCEL.getCode());
                bsMerchantOrder.setRebackReason("用户取消订单");
                bsMerchantOrderMapper.updateByExampleSelective(bsMerchantOrder, example);

                BsMerchantOrderVehicle orderVehicle = new BsMerchantOrderVehicle();
                orderVehicle.setMerchantOrder(ghMerchantOrderReflect.getMerchantOrder());
                BsMerchantOrderVehicle bsOrderVehicle = bsMerchantOrderVehicleMapper.selectOne(orderVehicle);
                if (bsOrderVehicle != null && !StringUtil.isEmpty(bsOrderVehicle.getDispatchOrderCode())){
                    LOGGER.info("更新发货单 {} 的状态为取消", bsOrderVehicle.getDispatchOrderCode());
                    OrderInfo orderInfo = new OrderInfo();
                    Example orderExample = new Example(OrderInfo.class);
                    orderExample.createCriteria().andEqualTo("orderCode", bsOrderVehicle.getDispatchOrderCode());
                    orderInfo.setStatus(ShopOrderStatuEnum.ORDER_CL.getValue());
                    orderInfoMapper.updateByExampleSelective(orderInfo, orderExample);
                }
            }

        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            throw new RpcServiceException(e.getMessage());
        }

        return result;
    }

    public List<BsLogisticsDTO> wxGetLogisticsInfoListByServiceCode(BsLogisticsDTO logistics) {
        List<BsLogisticsDTO> logisticsDTOList=bsLogisticsService.listLogisticsByGhMerchantOrderCode(logistics.getGhMerchantOrderCode());
        List<Long> bsAddressIds=new ArrayList<>();
        for(BsLogisticsDTO logisticsDTO:logisticsDTOList){
            bsAddressIds.add(logisticsDTO.getReceiveId());
        }
        Map<Long,BsAddress> mapAddress=addressService.listMapAddressByIds(bsAddressIds);
        for(BsLogisticsDTO logisticsDTO:logisticsDTOList){
            logisticsDTO.setBsAddress(mapAddress.get(logisticsDTO.getReceiveId()));
        }
        return logisticsDTOList;
    }
}
