package cn.com.glsx.merchant.supplychain.controller.bs;

import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.merchant.supplychain.config.Constants;
import cn.com.glsx.merchant.supplychain.util.ThreadLocalCache;
import cn.com.glsx.merchant.supplychain.web.session.Session;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.model.DeviceType;
import cn.com.glsx.supplychain.model.SupplyRequest;
import cn.com.glsx.supplychain.model.bs.*;
import cn.com.glsx.supplychain.remote.bs.*;

import com.glsx.cloudframework.core.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

/**
 * @ClassName 我的库存Controller
 * @Author xiaowy
 * @Param
 * @Date 2019/11/18 10:51
 * @Version
 **/
@RestController
@RequestMapping("materailAllot")
public class MaterialAllotController {

  //  @Autowired
  //  protected HttpSession session;

    @Autowired
    private BsMerchantStockAdminRemote bsMerchantStockAdminRemote;

    @Autowired
    private DealerUserInfoAdminRemote dealerUserInfoAdminRemote;
    
    DateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 查询商户库存列表（分页）
     * @param pagination
     * @return
     */
    @RequestMapping("pageMerchantStockCainou")
    public ResultEntity<RpcPagination<BsMerchantStockCainou>> pageMerchantStockCainou(BsMerchantStockCainou bsMerchantStockCainou, RpcPagination<BsMerchantStockCainou> pagination) {
    	Session session = ThreadLocalCache.getSession();
        DealerUserInfo dealerUserInfo = (DealerUserInfo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
    	//   DealerUserInfo dealerUserInfo = (DealerUserInfo) session.getAttribute(Constants.SESSION_LOGIN_USER);
        RpcResponse<DealerUserInfo> dul = dealerUserInfoAdminRemote.getDealerUserInfoByDealerUserName(dealerUserInfo.getName());
        if(null == bsMerchantStockCainou.getId() || 1 == bsMerchantStockCainou.getId()){
        	bsMerchantStockCainou.setFromMerchantCode(dul.getResult().getMerchantCode());
        }else{
        	bsMerchantStockCainou.setToMerchantCode(dul.getResult().getMerchantCode());
        }
     
        pagination.setCondition(bsMerchantStockCainou);
        Date date = new Date();
		SupplyRequest supplyRequest = new SupplyRequest();
		supplyRequest.setConsumer("merchant-supplychain");
		supplyRequest.setTime(formater.format(date));
		supplyRequest.setVersion("v1.0");
        RpcResponse<RpcPagination<BsMerchantStockCainou>> response = bsMerchantStockAdminRemote.pageMerchantStockCainou(supplyRequest, pagination);
        ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (errCodeEnum == null) {
            response.setMessage("查询成功");
        } else {
            response.setMessage(errCodeEnum.getDescrible());
        }
        return ResultEntity.result(response);
    }
    
    /**
     * 物料调出
     * @param
     * @return
     */
    @RequestMapping("materailCallout")
    public ResultEntity<BsMerchantStockCainou> addMerchantStockCainou(@RequestBody BsMerchantStockCainou bsMerchantStockCainou) {
    	Session session = ThreadLocalCache.getSession();
        DealerUserInfo dealerUserInfo = (DealerUserInfo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
    	//	DealerUserInfo dealerUserInfo = (DealerUserInfo) session.getAttribute(Constants.SESSION_LOGIN_USER);
        RpcResponse<DealerUserInfo> dul = dealerUserInfoAdminRemote.getDealerUserInfoByDealerUserName(dealerUserInfo.getName());
        bsMerchantStockCainou.setFromMerchantCode(dul.getResult().getMerchantCode());
        bsMerchantStockCainou.setFromMerchantName(dul.getResult().getMerchantName());
    	Date date = new Date();
		SupplyRequest supplyRequest = new SupplyRequest();
		supplyRequest.setConsumer("merchant-supplychain");
		supplyRequest.setTime(formater.format(date));
		supplyRequest.setVersion("v1.0");
    	RpcResponse<BsMerchantStockCainou> response = bsMerchantStockAdminRemote.addMerchantStockCainou(supplyRequest, bsMerchantStockCainou);
        ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (errCodeEnum == null) {
            response.setMessage("物料调出成功");
        } else {
            response.setMessage(errCodeEnum.getDescrible());
        }
        return ResultEntity.result(response);
    }
    
    /**
     * 物料签收
     * @param
     * @return
     */
    @RequestMapping("materailSign")
    public ResultEntity<BsMerchantStockCainou> updateMerchantStockCainou(@RequestBody BsMerchantStockCainou bsMerchantStockCainou) {
    	Date date = new Date();
		SupplyRequest supplyRequest = new SupplyRequest();
		supplyRequest.setConsumer("merchant-supplychain");
		supplyRequest.setTime(formater.format(date));
		supplyRequest.setVersion("v1.0");
		bsMerchantStockCainou.setStatus(2);
		RpcResponse<BsMerchantStockCainou> response = bsMerchantStockAdminRemote.updateMerchantStockCainou(supplyRequest, bsMerchantStockCainou);
        ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (errCodeEnum == null) {
            response.setMessage("更改成功");
        } else {
            response.setMessage(errCodeEnum.getDescrible());
        }
        return ResultEntity.result(response);
    }
    
    /**
     * 获取全部设备类型
     *
     * @return
     */
    @RequestMapping("getDeviceType")
    public ResultEntity<List<DeviceType>> listDeviceType(Integer id) {
    	Date date = new Date();
		SupplyRequest supplyRequest = new SupplyRequest();
		supplyRequest.setConsumer("merchant-supplychain");
		supplyRequest.setTime(formater.format(date));
		supplyRequest.setVersion("v1.0");
        DeviceType record = new DeviceType();
        record.setId(id);
        RpcResponse<List<DeviceType>> response = bsMerchantStockAdminRemote.listMerchantStockDeviceType(supplyRequest, record);
        ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (errCodeEnum == null) {
            response.setMessage("查询成功");
        } else {
            response.setMessage(errCodeEnum.getDescrible());
        }
        return ResultEntity.result(response);
    }
    
    /**
     * 获取商户
     *
     * @return
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping("getMerchantList")
    public ResultEntity<List> listMerchantStockMerchantInfo(BsMerchantStockCainou bsMerchantStockCainou) {
    	HashMap<String, Object> condition = new HashMap<String, Object>();
		if(bsMerchantStockCainou.getToMerchantCode() != null){
			condition.put("merchantId", bsMerchantStockCainou.getToMerchantCode());
		}
		if(bsMerchantStockCainou.getToMerchantName() != null){
			condition.put("merchantName", bsMerchantStockCainou.getToMerchantName());
		}
        RpcResponse<List> response = bsMerchantStockAdminRemote.listMerchantStockMerchantInfo(condition, Constants.targetPageNew, Constants.pageSizeMer);
        ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (errCodeEnum == null) {
            response.setMessage("查询成功");
        } else {
            response.setMessage(errCodeEnum.getDescrible());
        }
        return ResultEntity.result(response);
    }
    
    /**
     * 获取物料编码列表
     * @param bsMerchantStock
     * @param pagination
     * @return
     */
    @RequestMapping("getMaterialList")
    public ResultEntity<List<BsMerchantStock>> getMaterialList(BsMerchantStock bsMerchantStock, RpcPagination<BsMerchantStock> pagination) {
    	ResultEntity<List<BsMerchantStock>> response = new ResultEntity<List<BsMerchantStock>>();
    	Session session = ThreadLocalCache.getSession();
        DealerUserInfo dealerUserInfo = (DealerUserInfo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
    	//  DealerUserInfo dealerUserInfo = (DealerUserInfo) session.getAttribute(Constants.SESSION_LOGIN_USER);
        RpcResponse<DealerUserInfo> dul = dealerUserInfoAdminRemote.getDealerUserInfoByDealerUserName(dealerUserInfo.getName());
        bsMerchantStock.setMerchantCode(dul.getResult().getMerchantCode());
        pagination.setCondition(bsMerchantStock);
        Date date = new Date();
		SupplyRequest supplyRequest = new SupplyRequest();
		supplyRequest.setConsumer("merchant-supplychain");
		supplyRequest.setTime(formater.format(date));
		supplyRequest.setVersion("v1.0");
        RpcResponse<RpcPagination<BsMerchantStock>> result = bsMerchantStockAdminRemote.pageMerchantStocks(supplyRequest, pagination);
        response.setData(result.getResult().getList());
        ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) result.getError();
        if (errCodeEnum == null) {
            response.setMessage("查询成功");
        } else {
            response.setMessage(errCodeEnum.getDescrible());
        }
        return response;
    }
}

