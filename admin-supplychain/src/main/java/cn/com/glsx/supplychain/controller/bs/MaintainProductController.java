package cn.com.glsx.supplychain.controller.bs;

/**
 * @ClassName MainTainProductController
 * @Author leiming
 * @Param
 * @Date 2019/4/11 13:58
 * @Version
 **/

import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.model.DeviceInfo;
import cn.com.glsx.supplychain.model.bs.MaintainProduct;
import cn.com.glsx.supplychain.model.bs.MaintainProductDetail;
import cn.com.glsx.supplychain.remote.SupplyChainAdminRemote;
import cn.com.glsx.supplychain.remote.bs.MaintainProductRemote;
import io.swagger.models.auth.In;
import org.oreframework.boot.security.UserInfoHolder;
import org.oreframework.boot.security.cas.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 维修产品库管理
 *
 * @author leiming
 */
@RestController
@RequestMapping("mainTainProduct")
public class MaintainProductController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserInfoHolder userInfoHolder;

    @Autowired
    private MaintainProductRemote maintainProductRemote;

    @Autowired
    private SupplyChainAdminRemote supplyChainAdminRemote;


    /**
     * 查询维修产品库列表（分页）
     *
     * @param pagination
     * @return
     */
    @RequestMapping("listMainTainProduct")
    public ResultEntity<RpcPagination<MaintainProduct>> listMaintainProduct(@RequestBody RpcPagination<MaintainProduct> pagination) {
        RpcResponse<RpcPagination<MaintainProduct>> response = maintainProductRemote.listMaintainProduct(pagination);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }


    /**
     * 修改维修产品库状态
     *
     * @param maintainProduct
     * @return
     */
    @RequestMapping("updateById")
    public ResultEntity<Integer>updateById(@RequestBody MaintainProduct maintainProduct){
        @SuppressWarnings({"static-access", "deprecation"})
        User user = userInfoHolder.getUser();
        if (null != user) {
            maintainProduct.setUpdatedBy(user.getRealname());
        }else {
            maintainProduct.setUpdatedBy("admin");
        }
        RpcResponse<Integer>response = maintainProductRemote.updateById(maintainProduct);
        if (response.getError() == null) {
            response.setMessage("修改成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 根据id查询产品维修库信息
     * @param maintainProduct
     * @return
     *
     */
    @RequestMapping("getMaintainProductInfo")
    public ResultEntity<MaintainProduct>getMaintainProductInfo(@RequestBody MaintainProduct maintainProduct){
        RpcResponse<MaintainProduct>response =maintainProductRemote.getMaintainProductInfo(maintainProduct);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 修改维修管理详情信息
     * @param maintainProductDetail
     * @return
     *
     */
    @RequestMapping("updateByMaintainProductDetail")
    public ResultEntity<Integer>getMaintainProductInfo(@RequestBody MaintainProductDetail maintainProductDetail){
        RpcResponse<Integer>response =maintainProductRemote.updateByMaintainProductDetail(maintainProductDetail);
        if (response.getError() == null) {
            response.setMessage("修改成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 批量修改维修管理详情信息
     * @param maintainProductDetailList
     * @return
     *
     */
    @RequestMapping("batchUpdateMaintainProductDetail")
    public ResultEntity<Integer>batchUpdateMaintainProductDetail(@RequestBody List<MaintainProductDetail> maintainProductDetailList){
        RpcResponse<Integer>response =maintainProductRemote.batchUpdateMaintainProductDetail(maintainProductDetailList);
        if (response.getError() == null) {
            response.setMessage("修改成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 根据ICCID查询设备库存信息
     * @param iccid
     * @return
     *
     */
    @RequestMapping("getDeviceInfoByIccid")
    public ResultEntity<DeviceInfo>getDeviceInfoByIccid(String iccid){
        RpcResponse<DeviceInfo>response =supplyChainAdminRemote.getDeviceInfoByIccid(iccid);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 查询设备库存信息根据SN
     *
     * @param sn
     * @return
     */
    @RequestMapping("getDeviceInfoBySn")
    public ResultEntity<DeviceInfo> getDeviceInfoBySn(String sn) {
        RpcResponse<DeviceInfo> response = supplyChainAdminRemote.getDeviceInfoBySn(sn);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 查询设备库存信息根据IMEI
     *
     * @param imei
     * @return
     */
    @RequestMapping("getDeviceInfoByImei")
    public ResultEntity<DeviceInfo> getDeviceInfoByImei(String imei) {
        RpcResponse<DeviceInfo> response = supplyChainAdminRemote.getDeviceInfoByImei(imei);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 校验ICCID是否为GLSX的卡
     *
     * @param deviceInfo
     * @return
     */
    @RequestMapping("checkIccid")
    public ResultEntity<DeviceInfo>checkIccid(@RequestBody DeviceInfo deviceInfo){
        RpcResponse<DeviceInfo> response = maintainProductRemote.checkIccid(deviceInfo);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

}
