package cn.com.glsx.merchant.supplychain.controller.bs;

import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.merchant.supplychain.config.Constants;
import cn.com.glsx.merchant.supplychain.config.UploadProperty;
import cn.com.glsx.merchant.supplychain.constants.MerchantConstants;
//import cn.com.glsx.merchant.supplychain.framework.common.Constants;
import cn.com.glsx.merchant.supplychain.util.*;
import cn.com.glsx.merchant.supplychain.web.session.Session;
import cn.com.glsx.supplychain.enums.DealerUserInfoChannelEnum;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.model.DeviceType;
import cn.com.glsx.supplychain.model.OrderInfoDetail;
import cn.com.glsx.supplychain.model.am.ProductSplit;
import cn.com.glsx.supplychain.model.am.ProductSplitDetail;
import cn.com.glsx.supplychain.model.bs.*;
import cn.com.glsx.supplychain.remote.DeviceManagerAdminRemote;
import cn.com.glsx.supplychain.remote.am.ProductSplitAdminRemote;
import cn.com.glsx.supplychain.remote.bs.*;
import cn.com.glsx.supplychain.vo.BsMerchantOrderSignVo;
import cn.com.glsx.supplychain.vo.FileUploadVO;
import cn.com.glsx.supplychain.vo.MerchantOrderExcelVo;
import cn.com.glsx.supplychain.vo.MerchantOrderSignVo;

import com.glsx.biz.common.base.entity.Area;
import com.glsx.biz.common.base.entity.City;
import com.glsx.biz.common.base.entity.Province;
import com.glsx.biz.common.base.service.AreaService;
import com.glsx.biz.common.base.service.CityService;
import com.glsx.biz.common.base.service.ProvinceService;
import com.glsx.oms.bigdata.service.fb.api.IMaterialDubboService;
import com.glsx.oms.bigdata.service.fb.model.Material;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.oreframework.boot.security.UserInfoHolder;
import org.oreframework.boot.security.cas.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName 我的订单Controller
 * @Author admin
 * @Param
 * @Date 2019/2/28 10:51
 * @Version
 **/
@RestController
@RequestMapping("/myOrder")
public class MyOrderController {

	private static final SimpleDateFormat FMT = new SimpleDateFormat("yyMMdd");

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //@Autowired
   // protected HttpSession session;

    @Autowired
    private UserInfoHolder userInfoHolder;

    @Autowired
    private ProductAdminRemote productAdminRemote;

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private CityService cityService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private AddressAdminRemote addressAdminRemote;

    @Autowired
    private MerchantOrderAdminRemote merchantOrderAdminRemote;

    @Autowired
    private ProductSplitAdminRemote productSplitAdminRemote;

    @Autowired
    private DeviceManagerAdminRemote deviceManagerAdminRemote;

    @Autowired
    private DealerUserInfoAdminRemote dealerUserInfoAdminRemote;

    @Autowired
    private SalesManagerRemote salesManagerRemote;

    @Autowired
    private ExcelXlsxStreamingViewUtil excelXlsxStreamingViewSalesMerchantOrder;

    @Autowired
    private ExcelXlsxViewNewMerchantOrderExportUtil excelXlsxViewNewMerchantOrderExportUtil;

    @Autowired
    private IMaterialDubboService iMaterialDubboService;

    @Autowired
	private UploadProperty uploadProperty;
    
    @Autowired
    private Environment env;

    /**
     * 查询商户订单列表（分页）
     *
     * @param pagination
     * @return
     */
    @RequestMapping("listMerchantOrder")
    public ResultEntity<RpcPagination<MerchantOrder>> listMerchantOrder(@RequestBody RpcPagination<MerchantOrder> pagination) {
    	Session session = ThreadLocalCache.getSession();
        DealerUserInfo dealerUserInfo = (DealerUserInfo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
    	//  DealerUserInfo dealerUserInfo = (DealerUserInfo) session.getAttribute(Constants.SESSION_LOGIN_USER);
        RpcResponse<DealerUserInfo> dul = dealerUserInfoAdminRemote.getDealerUserInfoByDealerUserName(dealerUserInfo.getName());
        if (pagination.getCondition() != null) {
            pagination.getCondition().setMerchantCode(dul.getResult().getMerchantCode());
        } else {
            MerchantOrder merchantOrder = new MerchantOrder();
            merchantOrder.setMerchantCode(dul.getResult().getMerchantCode());
            pagination.setCondition(merchantOrder);
        }
        RpcResponse<RpcPagination<MerchantOrder>> response = merchantOrderAdminRemote.listMerchantOrder(pagination);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 查询产品拆分详情（分页）
     *
     * @param pagination
     * @return
     */
    @RequestMapping("listProductSplitDetail")
    public ResultEntity<RpcPagination<ProductSplitDetail>> listProductSplitDetail(@RequestBody RpcPagination<ProductSplitDetail> pagination) {
    	Session session = ThreadLocalCache.getSession();
        DealerUserInfo dealerUserInfo = (DealerUserInfo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
    	//    DealerUserInfo dealerUserInfo = (DealerUserInfo) session.getAttribute(Constants.SESSION_LOGIN_USER);
        RpcResponse<DealerUserInfo> dul = dealerUserInfoAdminRemote.getDealerUserInfoByDealerUserName(dealerUserInfo.getName());
        if (pagination.getCondition() != null && pagination.getCondition().getChannel() != null) {
            if (pagination.getCondition().getChannel() == 1) {
                pagination.getCondition().setChannel(dul.getResult().getChannel());
                pagination.getCondition().setMerchantCode("TESTTEST123321test");
            } else if (pagination.getCondition().getChannel() == 2) {
                pagination.getCondition().setMerchantCode(dul.getResult().getMerchantCode());
                pagination.getCondition().setChannel((byte)10);
            }
        } else {
            if(pagination.getCondition() == null)
            {
                pagination.setCondition(new ProductSplitDetail());
            }
            pagination.getCondition().setChannel(dul.getResult().getChannel());
            pagination.getCondition().setMerchantCode(dul.getResult().getMerchantCode());
        }
        RpcResponse<RpcPagination<ProductSplitDetail>> response = productSplitAdminRemote.listProductSplitDetail(pagination);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }


    /**
     * 修改订单状态
     *
     * @param merchantOrder
     * @return
     */
    @RequestMapping("updateOrderStatus")
    public ResultEntity<Integer> updateOrderStatus(@RequestBody MerchantOrder merchantOrder) {
    	return ResultEntity.error("-1", "请到V3新系统操作");
//    	Session session = ThreadLocalCache.getSession();
//        DealerUserInfo dealerUserInfo = (DealerUserInfo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
//    	
//        //   DealerUserInfo dealerUserInfo = (DealerUserInfo) session.getAttribute(Constants.SESSION_LOGIN_USER);
//        merchantOrder.setUpdatedBy(dealerUserInfo.getName());
//        RpcResponse<Integer> response = merchantOrderAdminRemote.updateOrderStatus(merchantOrder);
//        if (response.getError() == null) {
//            response.setMessage("开票成功");
//        } else {
//            logger.error(response.getMessage());
//        }
//        return ResultEntity.result(response);
    }


    /**
     * (我的订单)获取产品信息
     *
     * @return
     */
    @RequestMapping("getProductList")
    public ResultEntity<List<Product>> getProductList(@RequestBody Product product) {
    	Session session = ThreadLocalCache.getSession();
        DealerUserInfo dealerUserInfo = (DealerUserInfo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
    	//   DealerUserInfo dealerUserInfo = (DealerUserInfo) session.getAttribute(Constants.SESSION_LOGIN_USER);
        RpcResponse<DealerUserInfo> dul = dealerUserInfoAdminRemote.getDealerUserInfoByDealerUserName(dealerUserInfo.getName());
        product.setChannel(dul.getResult().getChannel());
        RpcResponse<List<Product>> response = productAdminRemote.getProductList(product);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 获取(新)产品拆分信息
     *
     * @return
     */
    @RequestMapping("getProductSplitList")
    public ResultEntity<List<ProductSplit>> getProductSplitList(@RequestBody ProductSplit productSplit) {
    	Session session = ThreadLocalCache.getSession();
        DealerUserInfo dealerUserInfo = (DealerUserInfo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
    	//     DealerUserInfo dealerUserInfo = (DealerUserInfo) session.getAttribute(Constants.SESSION_LOGIN_USER);
        RpcResponse<DealerUserInfo> dul = dealerUserInfoAdminRemote.getDealerUserInfoByDealerUserName(dealerUserInfo.getName());
        productSplit.setChannel(dul.getResult().getChannel());
        productSplit.setMerchantCode(dul.getResult().getMerchantCode());
        RpcResponse<List<ProductSplit>> response = productSplitAdminRemote.getProductSplitList(productSplit);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        //过滤 根据产品名称 业务类型 过滤掉渠道的同名产品
        List<ProductSplit> listResult = new ArrayList<ProductSplit>();
        Map<String,List<ProductSplit>> mapProductSplit = new HashMap<String,List<ProductSplit>>();
        List<ProductSplit> listProductSplit = response.getResult();
        if(!StringUtils.isEmpty(listProductSplit))
        {
        	logger.info("getProductSplitList listProductSplit:{}",listProductSplit);
        	for(ProductSplit split:listProductSplit)
        	{
        		String strkey = "" + split.getServiceType() + split.getProductName() + split.getPackageOne();
        		List<ProductSplit> listSplit = mapProductSplit.get(strkey);
        		if(StringUtils.isEmpty(listSplit))
        		{
        			listSplit = new ArrayList<ProductSplit>();
        			mapProductSplit.put(strkey, listSplit);
        		}
        		listSplit.add(split);
        	}
        }
        
        for(Map.Entry<String,List<ProductSplit>> entry:mapProductSplit.entrySet())
        {
        	List<ProductSplit> listSplit = entry.getValue();
        	if(StringUtils.isEmpty(listSplit))
        	{
        		continue;
        	}
        	if(listSplit.size() >= 2)
        	{
        		for(ProductSplit split:listSplit)
        		{
        			if(!StringUtils.isEmpty(split.getMerchantCode()))
        			{
        				listResult.add(split);
        			}	
        		}
        	}
        	else
        	{
        		for(ProductSplit split:listSplit)
        		{
        			listResult.add(split);
        		}
        	}
        }
        logger.info("getProductSplitList listResult:{}",listResult);
        return ResultEntity.success(listResult);
    }

    /**
     * 获取(新)产品拆分详情信息List
     *
     * @return
     */
    @RequestMapping("getProductSplitDetailByProductTypeZeroList")
    public ResultEntity<List<ProductSplitDetail>> getProductSplitDetailByProductTypeZeroList(@RequestBody List<ProductSplitDetail> productSplitDetail) {
        RpcResponse<List<ProductSplitDetail>> response = productSplitAdminRemote.getProductSplitDetailByProductTypeZeroList(productSplitDetail);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 查询物料信息
     *
     * @param firstLevelName
     * @return
     */
    @RequestMapping("getMaterialInfo")
    public ResultEntity<List<Material>> getMaterialInfo(String firstLevelName) {
        Material material = new Material();
        material.setFirstLevelName(firstLevelName);
        List<Material> list = iMaterialDubboService.list(material);
        RpcResponse<List<Material>> response = RpcResponse.success(list);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 新增商户订单
     *
     * @param merchantOrder
     * @return
     */
    @RequestMapping("addMyOrder")
    public ResultEntity<Integer> addMyOrder(@RequestBody MerchantOrder merchantOrder) {
/*        DealerUserInfo dealerUserInfo = (DealerUserInfo) session.getAttribute(Constants.SESSION_LOGIN_USER);
        merchantOrder.setMerchantCode(dealerUserInfo.getMerchantCode());
        merchantOrder.setCreatedBy(dealerUserInfo.getName());
        merchantOrder.setUpdatedBy(dealerUserInfo.getName());
        merchantOrder.getLogistics().setCreatedBy(dealerUserInfo.getName());
        merchantOrder.getLogistics().setUpdatedBy(dealerUserInfo.getName());
        merchantOrder.getLogistics().getReceiveAddress().setCreatedBy(dealerUserInfo.getName());
        merchantOrder.getLogistics().getReceiveAddress().setUpdatedBy(dealerUserInfo.getName());
        RpcResponse<Integer> response = merchantOrderAdminRemote.add(merchantOrder);
        ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (errCodeEnum == null) {
            response.setMessage("新增成功");
        } else {
            response.setMessage(errCodeEnum.getDescrible());
        }*/
        RpcResponse<Integer> response = RpcResponse.success(1);

        response.setMessage("houtai");

        return ResultEntity.result(response);
    }

    /**
     * 新增商户订单(填补老产品)
     *
     * @param merchantOrder
     * @return
     */
    @RequestMapping("addSplitMyOrder")
    public ResultEntity<Integer> addSplitMyOrder(@RequestBody MerchantOrder merchantOrder) {
    	
    	return ResultEntity.error("-1", "请到V3新系统操作");
    	
//    	Session session = ThreadLocalCache.getSession();
//        DealerUserInfo dealerUserInfo = (DealerUserInfo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
//    	//    DealerUserInfo dealerUserInfo = (DealerUserInfo) session.getAttribute(Constants.SESSION_LOGIN_USER);
//        merchantOrder.setMerchantCode(dealerUserInfo.getMerchantCode());
//        merchantOrder.setCreatedBy(dealerUserInfo.getName());
//        merchantOrder.setUpdatedBy(dealerUserInfo.getName());
//        merchantOrder.getLogistics().setCreatedBy(dealerUserInfo.getName());
//        merchantOrder.getLogistics().setUpdatedBy(dealerUserInfo.getName());
//        merchantOrder.getLogistics().getReceiveAddress().setCreatedBy(dealerUserInfo.getName());
//        merchantOrder.getLogistics().getReceiveAddress().setUpdatedBy(dealerUserInfo.getName());
//        merchantOrder.getLogistics().getReceiveAddress().setAreaName((merchantOrder.getLogistics().getReceiveAddress().getAreaId()==null)?"":this.getAreaName(Integer.valueOf(merchantOrder.getLogistics().getReceiveAddress().getAreaId())));
//        merchantOrder.getLogistics().getReceiveAddress().setCityName((merchantOrder.getLogistics().getReceiveAddress().getCityId()==null)?"":this.getCityName(merchantOrder.getLogistics().getReceiveAddress().getCityId()));
//        merchantOrder.getLogistics().getReceiveAddress().setProvinceName((merchantOrder.getLogistics().getReceiveAddress().getProvinceId()==null)?"":this.getProviceName(merchantOrder.getLogistics().getReceiveAddress().getProvinceId()));
//        RpcResponse<Integer> response = merchantOrderAdminRemote.addSplit(merchantOrder);
//        if (response.getError() == null) {
//            response.setMessage("新增成功");
//        } else {
//            logger.error(response.getMessage());
//        }
//        return ResultEntity.result(response);
    }

    /**
     * 获取全部设备类型
     *
     * @return
     */
    @RequestMapping("getDeviceType")
    public ResultEntity<List<DeviceType>> listDeviceType(Integer id) {
        DeviceType record = new DeviceType();
        record.setId(id);
        RpcResponse<List<DeviceType>> response = deviceManagerAdminRemote.listDeviceType(record);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 获取全部产品类型
     *
     * @return
     */
    @RequestMapping("getProductTypeList")
    public ResultEntity<List<ProductType>> getProductTypeList(Long id) {
        ProductType productType = new ProductType();
        productType.setId(id);
        RpcResponse<List<ProductType>> response = productAdminRemote.getProductTypeList(productType);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }


    /**
     * 动态获取省份地址信息
     *
     * @param
     * @return
     */
    @RequestMapping("getProvince")
    public List<Province> getAllList() {
        List<Province> response = provinceService.getAllList();
        /*ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if(errCodeEnum == null){
            response.setMessage("查询成功");
        }else{
            response.setMessage(errCodeEnum.getDescrible());
        }*/
        return response;
    }
    
    private String getProviceName(Integer proviceId){
    	String result = "";
    	Province province = provinceService.getByProvinceId(proviceId);
    	if(null != province){
    		result = province.getProvince();
    	}
    	return result;
    }

    /**
     * 动态获取城市地址信息
     *
     * @param
     * @return
     */
    @RequestMapping("getCity")
    public List<City> getcityList(Integer pid) {
        List<City> response = cityService.getListByProvinceId(pid);
        return response;

    }
    
    private String getCityName(Integer cityId){
    	String result = "";
    	City city = cityService.findById(cityId);
    	if(null != city){
    		result = city.getCity();
    	}
    	return result;
    }

    /**
     * 动态获取地区地址信息
     *
     * @param
     * @return
     */
    @RequestMapping("getAreaList")
    public List<Area> getAreaList(Integer cid) {
        List<Area> response = areaService.getListByCityId(cid);
        return response;

    }
    
    private String getAreaName(Integer areaId){
    	String result = "";
    	Area area = areaService.get(areaId);
    	if(null != area){
    		result = area.getArea();
    	}
    	return result;
    }

    /**
     * 获取地址簿信息
     *
     * @param
     * @return
     */
    @RequestMapping("getAddressListByMerchantCode")
    public ResultEntity<List<Address>> getAddressListByMerchantCode() {
    	Session session = ThreadLocalCache.getSession();
        DealerUserInfo dealerUserInfo = (DealerUserInfo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
    	//   DealerUserInfo dealerUserInfo = (DealerUserInfo) session.getAttribute(Constants.SESSION_LOGIN_USER);
        String merchantCode = dealerUserInfo.getMerchantCode();
        RpcResponse<List<Address>> response = addressAdminRemote.getAddressListByMerchantCode(merchantCode);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 查询商户订单列表（根据商户订单号）
     *
     * @param orderNumber
     * @return
     */
    @RequestMapping("getMerchantOrderByMerchantOrderNumber")
    public ResultEntity<MerchantOrder> getMerchantOrderByMerchantOrderNumber(String orderNumber) {
        RpcResponse<MerchantOrder> response = merchantOrderAdminRemote.getMerchantOrderByMerchantOrderNumber(orderNumber);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 签收商户订单
     *
     * @param merchantOrderDetail
     * @return
     */
    @RequestMapping("acceptMerchantOrder")
    public ResultEntity<Integer> acceptMerchantOrder(@RequestBody MerchantOrderDetail merchantOrderDetail) {
    	Session session = ThreadLocalCache.getSession();
        DealerUserInfo dealerUserInfo = (DealerUserInfo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
    	//     DealerUserInfo dealerUserInfo = (DealerUserInfo) session.getAttribute(Constants.SESSION_LOGIN_USER);
        merchantOrderDetail.setUpdatedBy(dealerUserInfo.getName());
        
        logger.info("acceptMerchantOrder merchantorderid:" + merchantOrderDetail.getId());
        RpcResponse<Integer> response = merchantOrderAdminRemote.acceptMerchantOrder(merchantOrderDetail);
        if (response.getError() == null) {
            response.setMessage("取消成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }


    /**
     * 添加销售管理信息
     *
     * @param salesList
     * @return
     */
    @RequestMapping("addSalesManaList")
    public ResultEntity<Integer> addSalesManaList(@RequestBody List<Sales> salesList) {
        @SuppressWarnings({"static-access", "deprecation"})
        User user = userInfoHolder.getUser();
        List<Sales> addSalesList = new ArrayList<>();
        if (null != user) {
            for (int i = 0; i < salesList.size(); i++) {
                salesList.get(i).setCreatedBy(user.getRealname());
                salesList.get(i).setUpdatedBy(user.getRealname());
                addSalesList.add(salesList.get(i));
            }
        } else {
            for (int i = 0; i < salesList.size(); i++) {
                salesList.get(i).setCreatedBy("admin");
                salesList.get(i).setUpdatedBy("admin");
                addSalesList.add(salesList.get(i));
            }
        }
        RpcResponse<Integer> response = salesManagerRemote.add(salesList);
        if (response.getError() == null) {
            response.setMessage("取消成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 查询物流单号
     *
     * @param orderInfoDetail
     * @return
     */
    @RequestMapping("listOrderInfoDetail")
    public ResultEntity<List<OrderInfoDetail>> listOrderInfoDetail(@RequestBody List<OrderInfoDetail> orderInfoDetail) {
        RpcResponse<List<OrderInfoDetail>> response = merchantOrderAdminRemote.listOrderInfoDetail(orderInfoDetail);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 查询物流信息List根据业务Code
     *
     * @param serviceCode
     * @return
     */
    @RequestMapping("getLogisticsInfoListByServiceCode")
    public ResultEntity<List<Logistics>> getLogisticsInfoListByServiceCode(String serviceCode) {
        Logistics logistics = new Logistics();
        logistics.setServiceCode(serviceCode);
        RpcResponse<List<Logistics>> response = merchantOrderAdminRemote.getLogisticsInfoListByServiceCode(logistics);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 导出商户订单
     *
     * @param response
     * @param response
     * @return
     */
    @RequestMapping("exportMerchantOrderExit")
    public ModelAndView exportMerchantOrderExit(HttpServletRequest request, HttpServletResponse response) {
        //转化时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        MerchantOrder merchantOrder = new MerchantOrder();
        if (request.getParameter("status") != null) {
            Byte status = Byte.valueOf(request.getParameter("status"));
            merchantOrder.setStatus(status);
        }
        if (request.getParameter("orderNumber") != null) {
            merchantOrder.setOrderNumber(request.getParameter("orderNumber"));
        }
        if (request.getParameter("materialCode") != null) {
            merchantOrder.setMaterialCode(request.getParameter("materialCode"));
        }
        try {
            if (request.getParameter("startDate") != null) {
                merchantOrder.setStartDate(df.parse(request.getParameter("startDate")));
            }
            if (request.getParameter("endDate") != null) {
                merchantOrder.setEndDate(df.parse(request.getParameter("endDate")));
            }
        } catch (Exception e) {
            logger.error("日期格式转换异常：", e);
        }
        /*String orderNumber = merchantOrder;
        List<String> result = Arrays.asList(orderNumber.split(","));
        List<MerchantOrder>merchantOrders = new ArrayList<>();
        for (int i =0;i<result.size();i++){
            MerchantOrder merchantOrder1 = new MerchantOrder();
            merchantOrder1.setOrderNumber(result.get(i));
            merchantOrders.add(merchantOrder1);
        }*/
        Session session = ThreadLocalCache.getSession();
        DealerUserInfo dealerUserInfo = (DealerUserInfo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
        //    DealerUserInfo dealerUserInfo = (DealerUserInfo) session.getAttribute(Constants.SESSION_LOGIN_USER);
        merchantOrder.setMerchantCode(dealerUserInfo.getMerchantCode());
        Boolean priceFlag;//价格显示标识
        if (dealerUserInfo.getChannel() == DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_GHUI.getCode() ||
                dealerUserInfo.getChannel() == DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_OTHER.getCode() ||
                dealerUserInfo.getChannel() == DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_FIVE.getCode() ||
                dealerUserInfo.getChannel() == DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_SIX.getCode()) {
            priceFlag = false;
        } else {
            priceFlag = true;
        }
        merchantOrder.setUserName(dealerUserInfo.getMerchantCode());
        merchantOrder.setPlatName("JXC");
        RpcResponse<List<MerchantOrderExcelVo>> responseMerchantOrder = merchantOrderAdminRemote.exportMerchantOrderExit(merchantOrder);
        List<MerchantOrderExcelVo> merchantOrderExportList = responseMerchantOrder.getResult();
        List<MerchantOrderExcelVo> merchantOrderExcelVos = merchantOrderExportList.stream().map(merchantOrderExcelVoOne -> convertTo(merchantOrderExcelVoOne)).collect(Collectors.toList());
        List<Object> merchantOrderList = new ArrayList<Object>();
        merchantOrderList.addAll(merchantOrderExcelVos);
        Map<String, Object> map = new HashMap<>();
        map.put("objs", merchantOrderList);
        if (priceFlag) {//priceFlag 为true，单价可见
            map.put(ExcelXlsxStreamingViewUtil.EXCEL_TEMPLATE_FILE_PATH_KEY, cn.com.glsx.merchant.supplychain.config.Constants.EXPORT_FOLDER_MERCHANT);
            map.put(ExcelXlsxStreamingViewUtil.EXCEL_FILE_NAME_KEY, cn.com.glsx.merchant.supplychain.config.Constants.EXPORT_FORMAT_MERCHANT);
            map.put(ExcelXlsxStreamingViewUtil.EXCEL_SHEET_NAME_KEY, cn.com.glsx.merchant.supplychain.config.Constants.EXPORT_NAME_MERCHANT);
           /* try {
                excelXlsxStreamingViewSalesMerchantOrder.buildExcelDocument(map, null, null, response);
            } catch (Exception e) {
                logger.error("导出异常：" + e.getMessage(), e);
            }*/
            return new ModelAndView(excelXlsxStreamingViewSalesMerchantOrder, map);
        } else {
            //priceFlag 为false，单价不可见
            map.put(ExcelXlsxStreamingViewUtil.EXCEL_TEMPLATE_FILE_PATH_KEY, cn.com.glsx.merchant.supplychain.config.Constants.EXPORT_FOLDER_NEW_MERCHANT);
            map.put(ExcelXlsxStreamingViewUtil.EXCEL_FILE_NAME_KEY, cn.com.glsx.merchant.supplychain.config.Constants.EXPORT_FORMAT_NEW_MERCHANT);
            map.put(ExcelXlsxStreamingViewUtil.EXCEL_SHEET_NAME_KEY, cn.com.glsx.merchant.supplychain.config.Constants.EXPORT_NAME_NEW_MERCHANT);
            /*try {
                excelXlsxViewNewMerchantOrderExportUtil.buildExcelDocument(map, null, null, response);
            } catch (Exception e) {
                logger.error("导出异常：" + e.getMessage(), e);
            }*/
            return new ModelAndView(excelXlsxViewNewMerchantOrderExportUtil, map);
        }

    }
    
    /**
     * 生成签收单
     *
     *
     * @param listMerchantOrder
     * @return
     */
    @RequestMapping("generateSignOrder")
    private FileUploadServerData generateSignOrder(@RequestBody List<MerchantOrder> listMerchantOrder)
    {
    	FileUploadServerData result = null;
    	if(listMerchantOrder == null)
    	{
    		return result;
    	}
    	if(listMerchantOrder.size() == 0)
    	{
    		return result;
    	}
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月");
    	String strDate = formatter.format(new Date());
    	PdfCreateUtil pdfCreateUtil = PdfCreateUtil.getInstance();
    	pdfCreateUtil.setEnv(env);
    	Map params = new HashMap<>();
    	String receiveOrderNo = MerchantStringUtil.getReceiveOrderNo();
    	params.put(MerchantConstants.PdfKey.SIGN_ORDER_NO, receiveOrderNo);
    	params.put(MerchantConstants.PdfKey.SIGN_ORDER_DATE, strDate);
    	
    	RpcResponse<List<MerchantOrderSignVo>> rsp = merchantOrderAdminRemote.genSignOrderByMerchantOrderNum(receiveOrderNo,listMerchantOrder);
    	if (rsp.getError() == null) {
    		rsp.setMessage("查询成功");
        } else {
            logger.error(rsp.getMessage());
        }
    	List<MerchantOrderSignVo> listSignVo = rsp.getResult();
    	
    	StringBuffer orderDetails = new StringBuffer(); 
    	Integer count = 0;
    	Integer intNo = 1;
    	String strRecvCompany = "";
    	String strRecvContact = "";
    	String strRecvMobile = "";
    	String strRecvAddr = "";
    	
    	for(MerchantOrderSignVo vo:listSignVo)
    	{
    		String orderDetail = MessageFormat.format(MerchantConstants.PdfKey.SIGN_ORDER_DETAIL_TEMPLATE, 
    				intNo,vo.getOrderNumber(),vo.getFactoryName(),vo.getMaterialCode()+"/"+vo.getMaterialName(),
    				vo.getShipmentQuantity(),vo.getShipmentTime(),vo.getLogisticsCpy()+":"+vo.getLogisticsNo(),vo.getRemark()==null?"":vo.getRemark());
    		orderDetails.append(orderDetail);
    		count += Integer.valueOf(vo.getShipmentQuantity());
    		intNo++;
    		strRecvCompany = vo.getMerchantName();
    		strRecvContact = vo.getContacts();
    		strRecvMobile = vo.getMobile();
    		strRecvAddr = vo.getAddress();
    	}
    	params.put(MerchantConstants.PdfKey.SIGN_RECV_COMPANY, strRecvCompany);
    	params.put(MerchantConstants.PdfKey.SIGN_RECV_CONTACT, strRecvContact);
    	params.put(MerchantConstants.PdfKey.SIGN_RECV_PHONE, strRecvMobile);
    	params.put(MerchantConstants.PdfKey.SIGN_RECV_ADDR, strRecvAddr);
    	params.put(MerchantConstants.PdfKey.SIGN_ORDER_DETAIL, orderDetails);
    	params.put(MerchantConstants.PdfKey.SIGN_TOTAL, String.valueOf(count));
    	
    	logger.info("创建订单PDF文件的请求参数 {}", params.toString());
    	
    	byte[] pdfFileByte = pdfCreateUtil.createPDF(MerchantConstants.PdfKey.HTML_TEMPLATE_APPLY, params);
    	FileUploadServerData fileUploadServerData = pdfCreateUtil.uploadToFileSystem(receiveOrderNo,
               pdfFileByte, MerchantConstants.PdfKey.FILE_TYPE); 
    	return fileUploadServerData;
    }

	/**
	 * 上传签收单图片
	 * @param fileData  fileUpload
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/uploadSignPic")
	@ResponseBody
	public String uploadSignPic(
			@RequestParam(value = "file") MultipartFile fileData,
			@ModelAttribute FileUploadVO fileUpload) throws Exception {
		JSONObject json = new JSONObject();

		String preFileName = fileUpload.getPreFileName();
		logger.info("UploadController::uploadSignPic preFileName:"
				+ preFileName);
		double size = fileData.getSize();
		int fileSize = (int) Math.ceil(size / FileUtils.ONE_MB);
		if (uploadProperty.getMaxSize() != null
				&& fileSize > uploadProperty.getMaxSize()) {
			json.put("ret", ErrorCodeEnum.ERRCODE_FILE_OUT_OF_RANGE.getCode());
			json.put("err",
					"文件超出大小限制(".concat(uploadProperty.getMaxSize().toString())
							.concat("MB"));
			logger.info("UploadController::upload err" + json.toString());
			return json.toString();
		}
		String savePath = uploadProperty.getDirs().get(preFileName);
		StringBuffer tempFileName = new StringBuffer(preFileName);
		tempFileName.append("-");
		tempFileName.append(FMT.format(new Date()));
		tempFileName.append(UUID.randomUUID().toString().replace("-", "")
				.substring(16));
		fileUpload.setOutputPreFileName(tempFileName.toString());
		String originalFilename = fileData.getOriginalFilename();
		int i = originalFilename.lastIndexOf(".");
		if (i > -1 && i < originalFilename.length())
			tempFileName.append(originalFilename.substring(i).toUpperCase());
		File tempPackage = new File(savePath);
		if (!tempPackage.exists()) {
			tempPackage.mkdirs();
		}
		File tempFile = new File(savePath, tempFileName.toString());
		byte[] tempbytes = new byte[1024];
		int byteread = 0;
		InputStream fis = fileData.getInputStream();
		FileOutputStream fos = new FileOutputStream(tempFile);
		while ((byteread = fis.read(tempbytes)) != -1) {
			fos.write(tempbytes, 0, byteread);
			fos.flush();
		}
		fos.close();
		String serverSavePath = uploadProperty.getUrls().get(preFileName)
				+ tempFileName.toString();
		fileUpload.setSavePath(serverSavePath);
		fileUpload.setOutputFileName(tempFileName.toString());
		fileUpload.setFileUploadName(originalFilename);

		json.put("ret", "0");
		json.put("err", "");
		json.put("url", serverSavePath);
		return json.toString();
	}

	/**
     * 签收上传确认
	 * @param signVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("confirmUploadSignPic")
	public ResultEntity<Integer> confirmUploadSignPic(@RequestBody BsMerchantOrderSignVo signVo)
	{
		Session session = ThreadLocalCache.getSession();
        DealerUserInfo dealerUserInfo = (DealerUserInfo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
		//		DealerUserInfo dealerUserInfo = (DealerUserInfo) session.getAttribute(Constants.SESSION_LOGIN_USER);
		BsMerchantOrderSign orderSign = new BsMerchantOrderSign();
		orderSign.setMerchantSignNumber(signVo.getMerchantSignNumber());
		orderSign.setSignUrl(signVo.getSignUrl());
		orderSign.setCreatedBy(dealerUserInfo.getMerchantCode());
		orderSign.setUpdatedBy(dealerUserInfo.getMerchantCode());
		orderSign.setCreatedDate(new Date());
		orderSign.setUpdatedDate(new Date());
		orderSign.setDeletedFlag("N");
		
		RpcResponse<Integer> response = merchantOrderAdminRemote.updateMerchantOrderSignBySignCode(orderSign);
		if (response.getError() == null) {
			response.setMessage("查询成功");
		} else {
			logger.error(response.getMessage());
		}
		return ResultEntity.result(response);
	}



    /**
     * 导出商户列表（转换对象）
     */
    private MerchantOrderExcelVo convertTo(MerchantOrderExcelVo merchantOrderExcelVoOne) {
        Date date = new Date();
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");

        MerchantOrderExcelVo merchantOrderVo = new MerchantOrderExcelVo();
        merchantOrderVo.setOrderNumber(StringUtils.isEmpty(merchantOrderExcelVoOne.getOrderNumber()) ? "" : merchantOrderExcelVoOne.getOrderNumber());
        merchantOrderVo.setDeviceType(StringUtils.isEmpty(merchantOrderExcelVoOne.getDeviceType()) ? "" : merchantOrderExcelVoOne.getDeviceType());
        merchantOrderVo.setProductCode(StringUtils.isEmpty(merchantOrderExcelVoOne.getProductCode()) ? "" : merchantOrderExcelVoOne.getProductCode());
        merchantOrderVo.setProductName(StringUtils.isEmpty(merchantOrderExcelVoOne.getProductName()) ? "" : merchantOrderExcelVoOne.getProductName());

        merchantOrderVo.setPrice(merchantOrderExcelVoOne.getPrice() == null ? 0 : merchantOrderExcelVoOne.getPrice());
        merchantOrderVo.setOrderQuantity(merchantOrderExcelVoOne.getOrderQuantity() == null ? 0 : merchantOrderExcelVoOne.getOrderQuantity());
        merchantOrderVo.setCheckQuantity(merchantOrderExcelVoOne.getCheckQuantity() == null ? 0 : merchantOrderExcelVoOne.getCheckQuantity());
        merchantOrderVo.setAlreadyShipmentQuantity(merchantOrderExcelVoOne.getAlreadyShipmentQuantity() == null ? 0 : merchantOrderExcelVoOne.getAlreadyShipmentQuantity());
        merchantOrderVo.setShipmentTime(StringUtils.isEmpty(merchantOrderExcelVoOne.getShipmentTime()) ? "" : merchantOrderExcelVoOne.getShipmentTime());
        merchantOrderVo.setShipmentQuantity(merchantOrderExcelVoOne.getShipmentQuantity() == null ? "0" : merchantOrderExcelVoOne.getShipmentQuantity());

        merchantOrderVo.setSignQuantity(merchantOrderExcelVoOne.getSignQuantity() == null ? 0 : merchantOrderExcelVoOne.getSignQuantity());
        merchantOrderVo.setTotalAmount(merchantOrderExcelVoOne.getTotalAmount() == null ? 0.0 : merchantOrderExcelVoOne.getTotalAmount());
        merchantOrderVo.setOrderTime(StringUtils.isEmpty(merchantOrderExcelVoOne.getOrderTime()) ? "" : merchantOrderExcelVoOne.getOrderTime());
        merchantOrderVo.setStatus(StringUtils.isEmpty(merchantOrderExcelVoOne.getStatus()) ? "" : merchantOrderExcelVoOne.getStatus());
        merchantOrderVo.setMaterialCode(StringUtils.isEmpty(merchantOrderExcelVoOne.getMaterialCode())?"":merchantOrderExcelVoOne.getMaterialCode());
        merchantOrderVo.setMaterialName(StringUtils.isEmpty(merchantOrderExcelVoOne.getMaterialName())?"":merchantOrderExcelVoOne.getMaterialName());
        merchantOrderVo.setLogisticsDesc(StringUtils.isEmpty(merchantOrderExcelVoOne.getLogisticsDesc())?"":merchantOrderExcelVoOne.getLogisticsDesc());
        return merchantOrderVo;
    }
}

