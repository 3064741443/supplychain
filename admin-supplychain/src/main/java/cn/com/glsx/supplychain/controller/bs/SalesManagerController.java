package cn.com.glsx.supplychain.controller.bs;

import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.model.DeviceType;
import cn.com.glsx.supplychain.model.bs.*;
import cn.com.glsx.supplychain.remote.DeviceManagerAdminRemote;
import cn.com.glsx.supplychain.remote.bs.ProductAdminRemote;
import cn.com.glsx.supplychain.remote.bs.SalesManagerRemote;
import cn.com.glsx.supplychain.remote.bs.SalesSummarizingRemote;
import com.glsx.oms.bigdata.service.fb.api.IMaterialDubboService;
import com.glsx.oms.bigdata.service.fb.model.Material;
import org.oreframework.boot.security.UserInfoHolder;
import org.oreframework.boot.security.cas.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName SalesManagerController
 * @Author leiming
 * @Param
 * @Date 2019/4/16 16:13
 * @Version
 **/
@RestController
@RequestMapping("salesManager")
public class SalesManagerController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SalesManagerRemote salesManagerRemote;

    @Autowired
    private IMaterialDubboService iMaterialDubboService;

    @Autowired
    private ProductAdminRemote productAdminRemote;

    @Autowired
    private UserInfoHolder userInfoHolder;

    @Autowired
    private SalesSummarizingRemote salesSummarizingRemote;

    @Autowired
    private DeviceManagerAdminRemote deviceManagerAdminRemote;
    /**
     * 查询销售管理列表（分页）
     *
     * @param pagination
     * @return
     */
    @RequestMapping("listSalesManager")
    public ResultEntity<RpcPagination<Sales>>listSalesManager(@RequestBody RpcPagination<Sales>pagination){
        RpcResponse<RpcPagination<Sales>> response = salesManagerRemote.listSalesManager(pagination);
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
     * 查询物料信息
     *
     * @param material
     * @return
     */
    @RequestMapping("getMaterialInfoList")
    public ResultEntity<List<Material>>getMaterialInfoList(@RequestBody Material material){
        List<Material> list = iMaterialDubboService.list(material);
        RpcResponse<List<Material>> response =  RpcResponse.success(list);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 根据产品编号获取产品信息
     *
     * @param
     * @return
     */
    @RequestMapping("getProductByProductCode")
    public ResultEntity<Product> getProductByProductCode(String code) {
        RpcResponse<Product> response = productAdminRemote.getProductByProductCode(code);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 根据产品编号获取产品明细价格信息
     *
     * @param
     * @return
     */
    @RequestMapping("getProductHistoryPrice")
    public ResultEntity<List<ProductHistoryPrice>> getProductHistoryPrice(String productCode) {
        RpcResponse<List<ProductHistoryPrice>> response = productAdminRemote.getProductHistoryPriceByProductCode(productCode);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     *  新增对账管理信息
     *
     * @param
     * @return
     */
    @RequestMapping("addSalesSummarizingList")
    public ResultEntity<Integer>addSalesSummarizingList(@RequestBody List<SalesSummarizing> salesSummarizingList){
        @SuppressWarnings({"static-access", "deprecation"})
        User user = userInfoHolder.getUser();
        for(int i=0;i<salesSummarizingList.size();i++){
            if (null != user){
                salesSummarizingList.get(i).setCreatedBy(user.getRealname());
                salesSummarizingList.get(i).setUpdatedBy(user.getRealname());
            }else {
                salesSummarizingList.get(i).setCreatedBy("admin");
                salesSummarizingList.get(i).setUpdatedBy("admin");
            }
        }
        RpcResponse<Integer> response = salesSummarizingRemote.addSalesSummarizingList(salesSummarizingList);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     *  修改销售管理状态
     *
     * @param
     * @return
     */
    @RequestMapping("updateById")
    public ResultEntity<Integer>updateById(@RequestBody Sales sales){
        @SuppressWarnings({"static-access", "deprecation"})
        User user = userInfoHolder.getUser();
        if (null != user){
            sales.setUpdatedBy(user.getRealname());
        }else {
            sales.setUpdatedBy("admin");
        }
        RpcResponse<Integer>response = salesManagerRemote.updateById(sales);
        if (response.getError() == null) {
            response.setMessage("修改成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     *  批量修改销售管理状态
     *
     * @param
     * @return
     */
    @RequestMapping("updateSalesInfoByid")
    public ResultEntity<Integer>updateSalesInfoByid(@RequestParam("ids") List<Long> ids, @RequestParam("status") Byte status){
        RpcResponse<Integer>response = salesManagerRemote.updateSalesInfoByid(ids,status);
        if (response.getError() == null) {
            response.setMessage("批量修改成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     *  获取销售汇总物料详情列表
     *
     * @param
     * @return
     */
    @RequestMapping("getSalesSummarizingMaterialDetailList")
    public ResultEntity<List<SalesSummarizingMaterialDetail>> getSalesSummarizingMaterialDetailList(@RequestBody SalesSummarizingMaterialDetail salesSummarizingMaterialDetail) {
        RpcResponse<List<SalesSummarizingMaterialDetail>> response = salesSummarizingRemote.getSalesSummarizingMaterialDetailList(salesSummarizingMaterialDetail);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 获取全部产品类型名称(Device_Type)
     *
     * @param
     * @return
     */
    @RequestMapping("listDeviceType")
    public ResultEntity<List<DeviceType>> listDeviceType(Integer id) {
        DeviceType deviceType = new DeviceType();
        deviceType.setId(id);
        RpcResponse<List<DeviceType>> response = deviceManagerAdminRemote.listDeviceType(deviceType);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }
}
