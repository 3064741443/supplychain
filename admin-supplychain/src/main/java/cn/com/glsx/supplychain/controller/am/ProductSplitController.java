package cn.com.glsx.supplychain.controller.am;

/**
 * @ClassName ProductSplitController
 * @Author admin
 * @Param
 * @Date 2019/9/19 17:47
 * @Version 1.0
 **/

import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.enums.ProductHistoryPriceEnum;
import cn.com.glsx.supplychain.enums.ProductSplitServiceTypeEnum;
import cn.com.glsx.supplychain.model.am.ProductSplit;
import cn.com.glsx.supplychain.model.am.ProductSplitDetail;
import cn.com.glsx.supplychain.model.am.ProductSplitHistoryPrice;
import cn.com.glsx.supplychain.model.bs.DealerUserInfo;
import cn.com.glsx.supplychain.remote.am.ProductSplitAdminRemote;
import cn.com.glsx.supplychain.remote.bs.DealerUserInfoAdminRemote;
import cn.com.glsx.supplychain.vo.ProductSplitHistoryPriceVo;

import org.oreframework.boot.security.UserInfoHolder;
import org.oreframework.boot.security.cas.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

/**
 * 产品拆分
 *
 * @author leiming
 */
@RestController
@RequestMapping("productSplitInfo")
public class ProductSplitController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserInfoHolder userInfoHolder;

    @Autowired
    private ProductSplitAdminRemote productSplitAdminRemote;

    @Autowired
    private DealerUserInfoAdminRemote dealerUserInfoAdminRemote;

    /**
     * 产品拆分列表（分页）
     *
     * @param
     * @return
     */
    @RequestMapping("listProductSplit")
    public ResultEntity<RpcPagination<ProductSplit>> listProductSplit(@RequestBody RpcPagination<ProductSplit> pagination) {
        RpcResponse<RpcPagination<ProductSplit>> response = productSplitAdminRemote.listProductSplit(pagination);
        //ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 添加产品拆分
     */
    @RequestMapping("addProductSplit")
    public ResultEntity<Integer>addProductSplit(@RequestBody ProductSplit productSplit){
        @SuppressWarnings({"static-access", "deprecation"})
        User user = userInfoHolder.getUser();
        if (null != user) {
            productSplit.setCreatedBy(user.getRealname());
            productSplit.setUpdatedBy(user.getRealname());
        }else {
            productSplit.setCreatedBy("admin");
            productSplit.setUpdatedBy("admin");
        }
        productSplit.setCreatedDate(new Date());
        productSplit.setUpdatedDate(new Date());
        RpcResponse<Integer> response = productSplitAdminRemote.add(productSplit);
        //ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (response.getError() == null) {
            response.setMessage("添加成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 查询产品拆分信息
     *
     * @param productSplit
     * @return  产品拆分信息
     */
    @RequestMapping("getProductSplitInfo")
    public ResultEntity<ProductSplit> getProductSplitInfo(@RequestBody ProductSplit productSplit){

    	List<ProductSplitHistoryPriceVo> returnList = new ArrayList<ProductSplitHistoryPriceVo>();
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        RpcResponse<ProductSplit> response = productSplitAdminRemote.getProductSplitInfo(productSplit);

        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }

        ProductSplit dbSplit = response.getResult();


        Map<String, ProductSplitHistoryPriceVo> voMap = new HashMap<String,ProductSplitHistoryPriceVo>();

        if(dbSplit == null)
        {
            dbSplit = new ProductSplit();
        	dbSplit.setProductSplitHistoryPriceVo(returnList);
        	return ResultEntity.success(dbSplit);
        }

        List<ProductSplitHistoryPrice> dbListPriceList = dbSplit.getProductSplitHistoryPriceList();

        if(dbListPriceList == null)
        {
        	dbSplit.setProductSplitHistoryPriceVo(returnList);
        	return ResultEntity.success(dbSplit);
        }

        for(ProductSplitHistoryPrice dbPrice:dbListPriceList)
		{
			String keyTime = df.format(dbPrice.getTime());
			String keyProductCode = dbPrice.getProductCode();
			String strKey = keyTime + keyProductCode;
			voMap = this.translateProductSplitHistoryPrivce2Vo(strKey, dbPrice, dbSplit, voMap);
		}

        Iterator<Entry<String, ProductSplitHistoryPriceVo>> entries = voMap.entrySet().iterator();
        while(entries.hasNext())
        {
        	Entry<String, ProductSplitHistoryPriceVo> entry = entries.next();
        	ProductSplitHistoryPriceVo priceVo = entry.getValue();
        	calcProductPrice(priceVo);
        	returnList.add(priceVo);
        }
        ListSort(returnList);
        dbSplit.setProductSplitHistoryPriceVo(returnList);
        return ResultEntity.success(dbSplit);
    }

    //根据时间排序
    private static void ListSort(List<ProductSplitHistoryPriceVo> list) {
        Collections.sort(list, new Comparator<ProductSplitHistoryPriceVo>() {
            @Override
            public int compare(ProductSplitHistoryPriceVo o1, ProductSplitHistoryPriceVo o2) {
                try {
                    Date dt1 = o1.getTime();
                    Date dt2 = o2.getTime();
                    if (dt1.getTime() > dt2.getTime()) {
                        return 1;
                    } else if (dt1.getTime() < dt2.getTime()) {
                        return -1;
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }
    
    private void calcProductPrice(ProductSplitHistoryPriceVo priceVo)
    {
    	double dPrice = 0.0;//销售价格
        double netHardPrice = 0.0;//网联智能硬件
    	if(priceVo.getAiNetCarMatribPrice() != null)
    	{
    		dPrice += priceVo.getAiNetCarMatribPrice();
    	}
    	if(priceVo.getDangerSoftPrice() != null)
    	{
    		dPrice += priceVo.getDangerSoftPrice();
            netHardPrice += priceVo.getDangerSoftPrice();
    	}
    	if(priceVo.getFlowControlPrice() != null)
    	{
    		dPrice += priceVo.getFlowControlPrice();
    	}
    	if(priceVo.getHardConfigPrice() != null)
    	{
    		dPrice += priceVo.getHardConfigPrice();
    	}
    	if(priceVo.getHardWarePrice() != null)
    	{
    		dPrice += priceVo.getHardWarePrice();
            netHardPrice += priceVo.getHardWarePrice();
    	}
    	if(priceVo.getInstallSvrPrice() != null)
    	{
    		dPrice += priceVo.getInstallSvrPrice();
    	}
    	/*if(priceVo.getNetHardPrice() != null)
    	{
    		dPrice += priceVo.getNetHardPrice();
    	}*/
    	if(priceVo.getNetSoftPrice() != null)
    	{
    		dPrice += priceVo.getNetSoftPrice();
            netHardPrice += priceVo.getNetSoftPrice();
    	}
    	if(priceVo.getServicePrice() != null)
    	{
    		dPrice += priceVo.getServicePrice();
    	}
    	if(priceVo.getSmartShopPrice() != null)
    	{
    		dPrice += priceVo.getSmartShopPrice();
    	}
    	priceVo.setSalesPrice(dPrice);
    	priceVo.setNetHardPrice(netHardPrice);
    }
    
    private Map<String, ProductSplitHistoryPriceVo> translateProductSplitHistoryPrivce2Vo(String strKey,ProductSplitHistoryPrice dbPrice,ProductSplit dbSplit,Map<String, ProductSplitHistoryPriceVo> mapVo)
    {
    	ProductSplitHistoryPriceVo voPrice = mapVo.get(strKey);
    	if(voPrice == null)
    	{
    		voPrice = new ProductSplitHistoryPriceVo();
    	}
    	if(dbPrice.getType() == ProductHistoryPriceEnum.PRODUCT_HISTORY_PRICE_NOW.getCode()){
            voPrice.setStatus("当前价格");
        }else if(dbPrice.getType() == ProductHistoryPriceEnum.PRODUCT_HISTORY_PRICE_TOMORROW.getCode()){
            voPrice.setStatus("未来价格");
        }else if(dbPrice.getType() == ProductHistoryPriceEnum.PRODUCT_HISTORY_PRICE_HISTORY.getCode()){
            voPrice.setStatus("历史价格");
        }
    	voPrice.setTime(dbPrice.getTime());
    	voPrice.setProductCode(dbPrice.getProductCode());
    	if(dbPrice.getProductType().equals("0"))
    	{
    		voPrice.setHardWareMatribCode(dbPrice.getMaterialCode());
    		voPrice.setHardWarePrice(dbPrice.getPrice());
    		voPrice.setHardWareTaxRate(dbPrice.getTaxRate());
    	}
    	else if(dbPrice.getProductType().equals("7"))
    	{
    		voPrice.setHardConfigMatribCode(dbPrice.getMaterialCode());
    		voPrice.setHardConfigPrice(dbPrice.getPrice());
    		voPrice.setHardConfigTaxRate(dbPrice.getTaxRate());
    	}
    	else if(dbPrice.getProductType().equals("1"))
    	{
    		voPrice.setNetSoftMatribCode(dbPrice.getMaterialCode());
    		voPrice.setNetSoftPrice(dbPrice.getPrice());
            voPrice.setNetSoftMaterialName(dbPrice.getMaterialName());
            voPrice.setNetSoftTaxRate(dbPrice.getTaxRate());
    	}
    	else if(dbPrice.getProductType().equals("2"))
    	{
    		voPrice.setDangerSoftMatribCode(dbPrice.getMaterialCode());
    		voPrice.setDangerSoftPrice(dbPrice.getPrice());
            voPrice.setDangerSoftMaterialName(dbPrice.getMaterialName());
            voPrice.setDangerSoftTaxRate(dbPrice.getTaxRate());
    	}
    	else if(dbPrice.getProductType().equals("3"))
    	{
    		voPrice.setFlowControlMatribCode(dbPrice.getMaterialCode());
    		voPrice.setFlowControlPrice(dbPrice.getPrice());
    		voPrice.setFlowContRolMaterialName(dbPrice.getMaterialName());
    		voPrice.setFlowContRolTaxRate(dbPrice.getTaxRate());
    	}
    	else if(dbPrice.getProductType().equals("4"))
    	{
    		voPrice.setInstallSvrMatribCode(dbPrice.getMaterialCode());
    		voPrice.setInstallSvrPrice(dbPrice.getPrice());
    		voPrice.setInstallSvrMaterialName(dbPrice.getMaterialName());
    		voPrice.setInstallSvrTaxRate(dbPrice.getTaxRate());
    	}
    	else if(dbPrice.getProductType().equals("6"))
    	{
    		voPrice.setAiNetCarMatribCode(dbPrice.getMaterialCode());
    		voPrice.setAiNetCarMatribPrice(dbPrice.getPrice());
    		voPrice.setAiNetCarMaterialName(dbPrice.getMaterialName());
    		voPrice.setAiNetCarTaxRate(dbPrice.getTaxRate());
    	}
    	else if(dbPrice.getProductType().equals("5"))
    	{
    		voPrice.setSmartShopMatribCode(dbPrice.getMaterialCode());
    		voPrice.setSmartShopPrice(dbPrice.getPrice());
    		voPrice.setSmartShopName(dbPrice.getMaterialName());
    		voPrice.setSmartShopTaxRate(dbPrice.getTaxRate());
    	}
    	
    	voPrice.setCreatedDate(dbSplit.getCreatedDate());
        voPrice.setServiceType(dbSplit.getServiceType());
    	mapVo.put(strKey, voPrice);
    	
    	return mapVo;
    }
    

    /**
     * 修改产品拆分信息删除标识
     *
     * @param productSplit
     * @return  成功条数
     */
    @RequestMapping("updateDeletedFlagById")
    public ResultEntity<Integer>updateDeletedFlagById(@RequestBody ProductSplit productSplit){
        RpcResponse<Integer> response = productSplitAdminRemote.updateDeletedFlagById(productSplit);
        //ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (response.getError() == null) {
            response.setMessage("修改成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }



    /**
     * 查询根据用户对象List
     *
     * @param dealerUserInfo
     * @return  用户信息对象List
     */
    @RequestMapping("getDealerUserInfoList")
    public ResultEntity<List<DealerUserInfo>>getDealerUserInfoList(DealerUserInfo dealerUserInfo){
        RpcResponse<List<DealerUserInfo>>response = dealerUserInfoAdminRemote.gteDealerUserInfoList(dealerUserInfo);
        //ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 修改拆分产品
     *
     * @param productSplit
     * @return  成功条数
     */
    @RequestMapping("updateById")
    public ResultEntity<Integer>updateById(@RequestBody ProductSplit productSplit){
        @SuppressWarnings({"static-access", "deprecation"})
        User user = userInfoHolder.getUser();
        if (null != user) {
            productSplit.setUpdatedBy(user.getRealname());
        }else {
            productSplit.setUpdatedBy("admin");
        }
        productSplit.setUpdatedDate(new Date());
        for(ProductSplitDetail productSplitDetail : productSplit.getProductSplitDetailList()){
            if (null != user) {
                productSplitDetail.setUpdatedBy(user.getRealname());
            }else {
                productSplitDetail.setUpdatedBy("admin");
            }
            productSplitDetail.setUpdatedDate(new Date());
        }
        RpcResponse<Integer> response = productSplitAdminRemote.updateById(productSplit);
        if (response.getError() == null) {
            response.setMessage("修改成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 修改拆分产品状态
     *
     * @param productSplit
     * @return  成功条数
     */
    @RequestMapping("updateProductSplitStatus")
    public ResultEntity<Integer>updateProductSplitStatus(@RequestBody ProductSplit productSplit){
        RpcResponse<Integer>response = productSplitAdminRemote.updateProductSplitStatus(productSplit);
        if (response.getError() == null) {
            response.setMessage("修改成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 修改拆分产品历史价格表删除标识
     *
     * @param productSplitHistoryPrice
     * @return  成功条数
     */
    @RequestMapping("deleteProductSplitHistoryPrice")
    public ResultEntity<Integer>deleteProductSplitHistoryPrice(@RequestBody ProductSplitHistoryPrice productSplitHistoryPrice){
        RpcResponse<Integer>response = productSplitAdminRemote.deleteProductSplitHistoryPrice(productSplitHistoryPrice);
        if (response.getError() == null) {
            response.setMessage("修改成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 修改新增产品拆分价格
     *
     * @param
     * @return
     */
    @RequestMapping("updateProductSplitHistoryPriceByTime")
    public ResultEntity<Integer> updateProductSplitHistoryPriceByTime(@RequestBody List<ProductSplitHistoryPrice> productSplitHistoryPriceList) {
        @SuppressWarnings({"static-access", "deprecation"})
        User user = userInfoHolder.getUser();
        if (null != user) {
            if(productSplitHistoryPriceList != null && productSplitHistoryPriceList.size() > 0){
                for(ProductSplitHistoryPrice list : productSplitHistoryPriceList){
                    list.setCreatedBy(user.getRealname());
                    list.setUpdatedBy(user.getRealname());
                    list.setCreatedDate(new Date());
                    list.setUpdatedDate(new Date());
                    if(list.getDeletedFlag() == null){
                        list.setDeletedFlag("N");
                    }
                }
            }
        }else {
            if(productSplitHistoryPriceList != null && productSplitHistoryPriceList.size() > 0){
                for(ProductSplitHistoryPrice list : productSplitHistoryPriceList){
                    list.setCreatedBy("admin");
                    list.setUpdatedBy("admin");
                    list.setCreatedDate(new Date());
                    list.setUpdatedDate(new Date());
                    if(list.getDeletedFlag() == null){
                        list.setDeletedFlag("N");
                    }
                }
            }

        }
        RpcResponse<Integer> response = productSplitAdminRemote.updateProductSplitHistoryPriceByTime(productSplitHistoryPriceList);
        if (response.getError() == null) {
            response.setMessage("修改成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 添加产品拆分
     */
    @RequestMapping("addProductSplitHistoryPriceList")
    public ResultEntity<Integer>addProductSplitHistoryPriceList(@RequestBody List<ProductSplitHistoryPrice> productSplitHistoryPriceList){
        @SuppressWarnings({"static-access", "deprecation"})
        User user = userInfoHolder.getUser();
        if (null != user) {
            if(productSplitHistoryPriceList != null && productSplitHistoryPriceList.size() > 0){
                for(ProductSplitHistoryPrice list : productSplitHistoryPriceList){
                    list.setCreatedBy(user.getRealname());
                    list.setUpdatedBy(user.getRealname());
                    list.setCreatedDate(new Date());
                    list.setUpdatedDate(new Date());
                    list.setDeletedFlag("N");
                }
            }
        }else {
            if(productSplitHistoryPriceList != null && productSplitHistoryPriceList.size() > 0){
                for(ProductSplitHistoryPrice list : productSplitHistoryPriceList){
                    list.setCreatedBy("admin");
                    list.setUpdatedBy("admin");
                    list.setCreatedDate(new Date());
                    list.setUpdatedDate(new Date());
                    list.setDeletedFlag("N");
                }
            }

        }
        RpcResponse<Integer> response = productSplitAdminRemote.addProductSplitHistoryPriceList(productSplitHistoryPriceList);
        //ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (response.getError() == null) {
            response.setMessage("添加成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }
}
