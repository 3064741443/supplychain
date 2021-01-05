package cn.com.glsx.supplychain.controller.bs;

/**
 * @ClassName DealerUserInfoController
 * @Author admin
 * @Param
 * @Date 2019/3/6 9:51
 * @Version
 **/

import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.config.Constants;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.model.bs.DealerUserInfo;
import cn.com.glsx.supplychain.remote.bs.DealerUserInfoAdminRemote;
import com.glsx.biz.common.base.entity.Area;
import com.glsx.biz.common.base.entity.City;
import com.glsx.biz.common.base.entity.Province;
import com.glsx.biz.common.base.service.AreaService;
import com.glsx.biz.common.base.service.CityService;
import com.glsx.biz.common.base.service.ProvinceService;
import org.oreframework.boot.security.UserInfoHolder;
import org.oreframework.boot.security.cas.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * 经销商用户
 *
 * @author leiming
 */
@RestController
@RequestMapping("dealerUserInfo")
public class DealerUserInfoController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserInfoHolder userInfoHolder;

    @Autowired
    private DealerUserInfoAdminRemote dealerUserInfoAdminRemote;

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private CityService cityService;

    @Autowired
    private AreaService areaService;


    /**
     * 查询用户列表（分页）
     *
     * @param
     * @return
     */
    @RequestMapping("listUserInfo")
    public ResultEntity<RpcPagination<DealerUserInfo>> listDealerUserInfo(@RequestBody RpcPagination<DealerUserInfo> pagination) {
        RpcResponse<RpcPagination<DealerUserInfo>> response = dealerUserInfoAdminRemote.listDealerUserInfo(pagination);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 新增用户
     *
     * @param
     * @return
     */
    @RequestMapping("addBusUserInfo")
    public ResultEntity<Integer> add(DealerUserInfo dealerUserInfo) {
        @SuppressWarnings({"static-access", "deprecation"})
        User user = userInfoHolder.getUser();
        if(null != user){
            dealerUserInfo.setUpdatedBy(user.getRealname());
            dealerUserInfo.setCreatedBy(user.getRealname());
        }else {
            dealerUserInfo.setUpdatedBy("admin");
            dealerUserInfo.setCreatedBy("admin");
        }
        RpcResponse<Integer> response = dealerUserInfoAdminRemote.add(dealerUserInfo);
        if (response.getError() == null) {
            response.setMessage("添加成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 查询老商户名称
     *
     * @param
     * @return
     */
    @RequestMapping("getOldMerchantInfo")
    public ResultEntity<List> getOldMerchantInfo(HashMap<String, Object> hashMap, Integer integer, Integer integer1) {
        RpcResponse<List> response = dealerUserInfoAdminRemote.getOldMerchantInfo(hashMap, Constants.pageSizeNew, Constants.targetPageNew);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 查询用户名
     *
     * @param
     * @return
     */
    @RequestMapping("getDealerUserInfoByDealerUserName")
    public ResultEntity<DealerUserInfo> getDealerUserInfoByDealerUserName(String name) {
        RpcResponse<DealerUserInfo> response = dealerUserInfoAdminRemote.getDealerUserInfoByDealerUserName(name);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 修改密码
     *
     * @param name
     * @return
     */
    @RequestMapping("updatePasswordByName")
    public ResultEntity<Integer> updatePasswordByName(String name, String password) {
        @SuppressWarnings({"static-access", "deprecation"})
        User user = userInfoHolder.getUser();
        String updatedBy;
        if (null != user){
            updatedBy = user.getRealname();
        }else {
            updatedBy ="admin";
        }
        RpcResponse<Integer> response = dealerUserInfoAdminRemote.updatePasswordByName(name, password, updatedBy);
        if (response.getError() == null) {
            response.setMessage("修改成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 查询用户信息根据用户ID
     *
     * @param dealerUserInfo
     * @return
     */
    @RequestMapping("getDelerUseInfoById")
    public ResultEntity<DealerUserInfo>getDelerUseInfoById(@RequestBody DealerUserInfo dealerUserInfo){
        RpcResponse<DealerUserInfo>response = dealerUserInfoAdminRemote.getDelerUseInfoById(dealerUserInfo);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 修改用户信息根据用户ID
     *
     * @param dealerUserInfo
     * @return
     */
    @RequestMapping("updateByDealerUserId")
    public ResultEntity<Integer>updateByDealerUserId(@RequestBody DealerUserInfo dealerUserInfo){

        String merchantNo = dealerUserInfo.getMerchantCode();
        if(!StringUtils.isEmpty(merchantNo))
        {
            ResultEntity<Integer> result = new ResultEntity<Integer>();
            RpcResponse<DealerUserInfo> rsp = dealerUserInfoAdminRemote.getDealerUserInfoByMerchantCode(merchantNo);
            ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) rsp.getError();
            if (rsp.getError() == null)
            {
                DealerUserInfo tempUserInfo = rsp.getResult();
                if(!StringUtils.isEmpty(tempUserInfo))
                {
                    if(!dealerUserInfo.getId().equals(tempUserInfo.getId()))
                    {
                        result.setReturnCode("10177");
                        result.setMessage("商户号已经被占用");
                        return result;
                    }
                }
            }
            else
            {
                result.setReturnCode(errCodeEnum.getCode());
                result.setMessage(errCodeEnum.getDescrible());
                return result;
            }
        }

        RpcResponse<Integer>response = dealerUserInfoAdminRemote.updateByDealerUserId(dealerUserInfo);
        if (response.getError() == null) {
            response.setMessage("修改成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 修改用户信息删除标识根据用户ID
     *
     * @param dealerUserInfo
     * @return
     */
    @RequestMapping("deleteByDealerUserId")
    public ResultEntity<Integer>deleteByDealerUserId(@RequestBody DealerUserInfo dealerUserInfo){
        RpcResponse<Integer>response = dealerUserInfoAdminRemote.deleteByDealerUserId(dealerUserInfo);
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
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 查询商户信息根据merchantCode
     *
     * @param merchantCode
     * @return  用户信息对象
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

    /**
     * 动态获取省份地址信息
     *
     * @param
     * @return
     */
    @RequestMapping("getProvince")
    public List<Province> getAllList() {
        List<Province> response = provinceService.getAllList();
        return response;
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
}
