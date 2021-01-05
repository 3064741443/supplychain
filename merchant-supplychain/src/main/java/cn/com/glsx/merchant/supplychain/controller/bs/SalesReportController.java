package cn.com.glsx.merchant.supplychain.controller.bs;

import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.merchant.supplychain.config.Constants;
import cn.com.glsx.merchant.supplychain.util.ThreadLocalCache;
import cn.com.glsx.merchant.supplychain.web.session.Session;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.model.bs.*;
import cn.com.glsx.supplychain.remote.bs.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import java.util.List;

/**
 * @ClassName 销售对账Controller
 * @Author admin
 * @Param
 * @Date 2019/2/28 10:51
 * @Version
 **/
@RestController
@RequestMapping("salesReport")
public class SalesReportController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

   // @Autowired
  //  protected HttpSession session;

    @Autowired
    private SalesSummarizingRemote salesSummarizingRemote;

    @Autowired
    private ProductAdminRemote productAdminRemote;

    @Autowired
    private DealerUserInfoAdminRemote dealerUserInfoAdminRemote;

    @Autowired
    private SalesRelationAdminRemote salesRelationAdminRemote;

    @Autowired
    private SalesManagerRemote salesManagerRemote;

    /**
     * 销售对账列表（分页）
     * @param pagination
     * @return
     */
    @RequestMapping("getAllReportList")
    public ResultEntity<RpcPagination<SalesSummarizing>> getAllReportList(@RequestBody RpcPagination<SalesSummarizing> pagination) {
    	Session session = ThreadLocalCache.getSession();
        DealerUserInfo dealerUserInfo = (DealerUserInfo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
    	//    DealerUserInfo dealerUserInfo = (DealerUserInfo) session.getAttribute(Constants.SESSION_LOGIN_USER);
        RpcResponse<DealerUserInfo> dul = dealerUserInfoAdminRemote.getDealerUserInfoByDealerUserName(dealerUserInfo.getName());
        if(pagination.getCondition() != null){
            pagination.getCondition().setMerchantCode(dul.getResult().getMerchantCode());
        }else {
            SalesSummarizing salesSummarizing = new SalesSummarizing();
            salesSummarizing.setMerchantCode(dul.getResult().getMerchantCode());
            pagination.setCondition(salesSummarizing);
        }
        RpcResponse<RpcPagination<SalesSummarizing>> response = salesSummarizingRemote.listSalesSummarizing(pagination);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * (销售汇总)获取产品信息
     *
     * @return
     */
    @RequestMapping("getSalesProductList")
    public ResultEntity<List<Product>> getSalesProductList(@RequestBody Product product) {
        RpcResponse<List<Product>> response = productAdminRemote.getProductList(product);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 根据id获取销售管理
     */

    @RequestMapping("getSalesManaInfo")
    public ResultEntity<List<SalesSummarizingDetail>> getSalesSummarizingDetailBySalesSummarizingId(@RequestBody Long id) {
        RpcResponse<List<SalesSummarizingDetail>> response = salesSummarizingRemote.getSalesSummarizingDetailBySalesSummarizingId(id);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 修改销售汇总(提交对账)
     */

    @RequestMapping("updateSalesByid")
    public ResultEntity<Integer> updateById(@RequestBody SalesSummarizing salesSummarizing) {
        RpcResponse<Integer> response = salesSummarizingRemote.updateById(salesSummarizing);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 查询销售管理与销售汇总关系信息
     */
    @RequestMapping("getSalesRelation")
    public ResultEntity<List<SalesRelation>>getSalesRelation(@RequestBody SalesRelation salesRelation){
        RpcResponse<List<SalesRelation>> response = salesRelationAdminRemote.getSalesRelation(salesRelation);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }


    /**
     * 查询销售管理(根据销售id)
     *
     * @param id
     * @return Sales
     */
    @RequestMapping("getSalesInfoById")
    public ResultEntity<Sales>getSalesInfoById(Long id){
        RpcResponse<Sales>response = salesManagerRemote.getSalesInfoByid(id);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 查询商户用户
     *
     * @param
     * @return Sales
     */
    @RequestMapping("getDealerUserInfoByMerchantCode")
    public ResultEntity<DealerUserInfo>getDealerUserInfoByMerchantCode(String merchantCode){
        RpcResponse<DealerUserInfo>response = dealerUserInfoAdminRemote.getDealerUserInfoByMerchantCode(merchantCode);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }
}

