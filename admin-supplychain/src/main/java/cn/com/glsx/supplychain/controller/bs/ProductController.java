package cn.com.glsx.supplychain.controller.bs;

/**
 * @ClassName ProductController
 * @Author leiming
 * @Param
 * @Date 2019/3/5 13:58
 * @Version
 **/

import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.enums.ProductStatusEnum;
import cn.com.glsx.supplychain.model.DeviceType;
import cn.com.glsx.supplychain.model.bs.Product;
import cn.com.glsx.supplychain.model.bs.ProductDetail;
import cn.com.glsx.supplychain.model.bs.ProductHistoryPrice;
import cn.com.glsx.supplychain.model.bs.ProductType;
import cn.com.glsx.supplychain.remote.DeviceManagerAdminRemote;
import cn.com.glsx.supplychain.remote.bs.*;
import com.glsx.oms.bigdata.service.fb.api.IMaterialDubboService;
import com.glsx.oms.bigdata.service.fb.model.Material;
import org.oreframework.boot.security.UserInfoHolder;
import org.oreframework.boot.security.cas.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 产品管理
 *
 * @author leiming
 */
@RestController
@RequestMapping("product")
public class ProductController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserInfoHolder userInfoHolder;

    @Autowired
    private DeviceManagerAdminRemote deviceManagerAdminRemote;

    @Autowired
    private ProductAdminRemote productAdminRemote;

    @Autowired
    private IMaterialDubboService iMaterialDubboService;
    /**
     * 查询产品列表（分页）
     *
     * @param pagination
     * @return
     */
    @RequestMapping("listProduct")
    public ResultEntity<RpcPagination<Product>> listProduct(@RequestBody RpcPagination<Product> pagination) {
        RpcResponse<RpcPagination<Product>> response = productAdminRemote.listProduct(pagination);
        ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (errCodeEnum == null) {
            response.setMessage("查询成功");
        } else {
            response.setMessage(errCodeEnum.getDescrible());
        }
        return ResultEntity.result(response);
    }

    /**
     * 查询产品列表（）
     *
     * @param pagination
     * @return
     */
    @RequestMapping("getListProduct")
    public ResultEntity<List<Product>> getListProduct(@RequestBody Product pagination) {
        RpcResponse<List<Product>> response = productAdminRemote.getProductList(pagination);
        ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (errCodeEnum == null) {
            response.setMessage("查询成功");
        } else {
            response.setMessage(errCodeEnum.getDescrible());
        }
        return ResultEntity.result(response);
    }



    /**
     * 新增产品
     *
     * @param product
     * @return
     */
    @RequestMapping(value = "addProdcut")
    @ResponseBody
    public ResultEntity<Integer> addProdcut(@RequestBody Product product) {
        @SuppressWarnings({"static-access", "deprecation"})
        User user = userInfoHolder.getUser();
        if (null != user) {
            product.setCreatedBy(user.getRealname());
            product.setUpdatedBy(user.getRealname());
        }else {
            product.setCreatedBy("admin");
            product.setUpdatedBy("admin");
        }
        product.setCreatedDate(new Date());
        product.setStatus(ProductStatusEnum.PRODUCT_STATUS_PUTAWAY.getCode());
        RpcResponse<Integer> response = productAdminRemote.add(product);
        ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (errCodeEnum == null) {
            response.setMessage("添加成功");
        } else {
            response.setMessage(errCodeEnum.getDescrible());
        }
        return ResultEntity.result(response);
    }


    /**
     * 获取全部设备类型(DeviceType)
     *
     * @return
     */
    @RequestMapping("listDeviceType")
    public ResultEntity<List<DeviceType>> listDeviceType(Integer id) {
        DeviceType deviceType = new DeviceType();
        deviceType.setId(id);
        RpcResponse<List<DeviceType>> response = deviceManagerAdminRemote.listDeviceType(deviceType);
        ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (errCodeEnum == null) {
            response.setMessage("查询成功");
        } else {
            response.setMessage(errCodeEnum.getDescrible());
        }
        return ResultEntity.result(response);
    }

    /**
     * 获取全部产品设备类型(Product_type)
     *
     * @return
     */
    @RequestMapping("getProductTypeList")
    public ResultEntity<List<ProductType>> getProductTypeList() {
        ProductType productType = new ProductType();
        RpcResponse<List<ProductType>> response = productAdminRemote.getProductTypeList(productType);
        ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (errCodeEnum == null) {
            response.setMessage("查询成功");
        } else {
            response.setMessage(errCodeEnum.getDescrible());
        }
        return ResultEntity.result(response);
    }


    /**
     * 产品上架与下架
     *
     * @param product
     * @return
     */
    @RequestMapping("updateProductStatus")
    public ResultEntity<Integer> update(Product product) {
        RpcResponse<Integer> response = productAdminRemote.updateById(product);
        ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (errCodeEnum == null) {
            response.setMessage("修改成功");
        } else {
            response.setMessage(errCodeEnum.getDescrible());
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
    public ResultEntity<Product> getProductByProductCode(@RequestBody String code) {
        RpcResponse<Product> response = productAdminRemote.getProductByProductCode(code);
        ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (errCodeEnum == null) {
            response.setMessage("查询成功");
        } else {
            response.setMessage(errCodeEnum.getDescrible());
        }
        return ResultEntity.result(response);
    }

    /**
     * 修改含税单价
     *
     * @param code
     * @return
     */
    @RequestMapping("updateAmount")
    public ResultEntity<Integer> updateAmount(String code, Double amount,@RequestParam("time") @DateTimeFormat(pattern="yyyy-MM-dd") Date time) {
        @SuppressWarnings({"static-access", "deprecation"})
        User user = userInfoHolder.getUser();
        String updatedBy;
        if(null != user){
             updatedBy = user.getRealname();
        }else {
             updatedBy = "admin";
        }
        RpcResponse<Integer> response = productAdminRemote.updateProductPriceByCode(code, amount, updatedBy, time);
        ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (errCodeEnum == null) {
            response.setMessage("修改成功");
        } else {
            response.setMessage(errCodeEnum.getDescrible());
        }
        return ResultEntity.result(response);
    }

    /**
     * 根据产品编号查询历史价格
     *
     * @param code
     * @return
     */
    @RequestMapping("getProductHistoryPrice")
    public ResultEntity<List<ProductHistoryPrice>> getProductHistoryPriceByProductCode(String code) {
        RpcResponse<List<ProductHistoryPrice>> response = productAdminRemote.getProductHistoryPriceByProductCode(code);
        ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (errCodeEnum == null) {
            response.setMessage("查询成功");
        } else {
            response.setMessage(errCodeEnum.getDescrible());
        }
        return ResultEntity.result(response);
    }

    /**
     * 查询物料信息
     *
     * @param materialTypeIdStr
     * @return
     */
    @RequestMapping("getMaterialInfo")
    public ResultEntity<List<Material>>getMaterialInfo(String materialTypeIdStr){
        List<Integer> materialTypeIdArr = new ArrayList<>();
        if(materialTypeIdStr != null && materialTypeIdStr != ""){
            String[] strings = materialTypeIdStr.split(",");
            for(String list : strings){
                materialTypeIdArr.add(Integer.valueOf(list));
            }
        }
        Material material = new Material();
        material.setMaterialTypeIdList(materialTypeIdArr);
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
     * 根据产品编号查询产品详情
     *
     * @param productCode
     * @return
     */
    @RequestMapping("getProductDetailByProductCode")
    public ResultEntity<List<ProductDetail>> getProductDetailByProductCode(String productCode) {
        RpcResponse<List<ProductDetail>> response = productAdminRemote.getProductDetailByProductCode(productCode);
        ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (errCodeEnum == null) {
            response.setMessage("查询成功");
        } else {
            response.setMessage(errCodeEnum.getDescrible());
        }
        return ResultEntity.result(response);
    }
}
