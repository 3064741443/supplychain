package cn.com.glsx.supplychain.service.bs;

import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.mapper.bs.DealerUserInfoMapper;
import cn.com.glsx.supplychain.model.bs.DealerUserInfo;
import cn.com.glsx.supplychain.util.CreateMD5;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

@Service
public class DealerUserInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DealerUserInfoService.class);

    @Autowired
    private DealerUserInfoMapper dealerUserInfoMapper;

    /**
     * 获取经销商用户分页列表
     *
     * @param dealerUserInfo 经销商用户对象
     * @return
     */
    public List<DealerUserInfo> listDealerUserInfo(int pageNum, int pageSize, DealerUserInfo dealerUserInfo) {
        LOGGER.info("pageNum为:{}, pageSize为:{}, 条件查询参数为:{}。", pageNum, pageSize, dealerUserInfo);
        List<DealerUserInfo> result;
        PageHelper.startPage(pageNum, pageSize);
        result = dealerUserInfoMapper.listDealerUserInfo(dealerUserInfo);
        return result;
    }

    /**
     * 获取经销商用户
     *
     * @param dealerUserName 经销商用户名
     * @return
     */
    public DealerUserInfo getDealerUserInfoByDealerUserName(String dealerUserName) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        LOGGER.info("根据用户名获取经销商用户查询参数为:{}。", dealerUserName);
        DealerUserInfo result;
        DealerUserInfo dealerUserInfo = new DealerUserInfo();
        dealerUserInfo.setName(dealerUserName);
        dealerUserInfo.setDeletedFlag("N");
        result = dealerUserInfoMapper.selectOne(dealerUserInfo);
        result.setPassword(CreateMD5.md5(result.getPassword()));
        return result;
    }

    /**
     * 获取经销商用户
     *
     * @param merchantCode 经销商code
     * @return
     */
    public DealerUserInfo getDealerUserInfoByMerchantCode(String merchantCode) {
        LOGGER.info("根据商户Code获取经销商用户查询参数为:{}。", merchantCode);
        DealerUserInfo result;
        DealerUserInfo dealerUserInfo = new DealerUserInfo();
        dealerUserInfo.setMerchantCode(merchantCode);
        dealerUserInfo.setDeletedFlag("N");
        result = dealerUserInfoMapper.selectOne(dealerUserInfo);
        return result;
    }

    /**
     * 获取经销商用户信息
     *
     * @param dealerUserInfo
     * @return
     */
    public DealerUserInfo getDelerUseInfoById(DealerUserInfo dealerUserInfo) {
        LOGGER.info("根据商户ID获取经销商用户查询参数为:{}。", dealerUserInfo);
        DealerUserInfo result;
        result = dealerUserInfoMapper.getDelerUseInfoById(dealerUserInfo);
        return result;
    }


    /**
     * 新增经销商用户
     *
     * @param dealerUserInfo 经销商用户对象
     * @return 成功条数
     */
    public Integer add(DealerUserInfo dealerUserInfo) {
        LOGGER.info("新增用户参数为:{}。", dealerUserInfo);
        Integer result;
        DealerUserInfo userInfo = new DealerUserInfo();
        userInfo.setName(dealerUserInfo.getName());
        userInfo.setDeletedFlag("N");
        userInfo = dealerUserInfoMapper.selectOne(userInfo);
        if (userInfo != null) {
            LOGGER.error("添加经销商用户:用户名已被占用");
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_DEALER_USER_INFO_NAME_ALREADY_USED);
        }
        userInfo = new DealerUserInfo();
        userInfo.setMerchantCode(dealerUserInfo.getMerchantCode());
        userInfo.setDeletedFlag("N");
        userInfo = dealerUserInfoMapper.selectOne(userInfo);
        if (userInfo != null) {
            LOGGER.error("添加经销商用户:商户已经创建用户");
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_DEALER_USER_INFO_NAME_MERCHANT_USED);
        }
        dealerUserInfo.setUpdatedDate(new Date());
        dealerUserInfo.setCreatedDate(new Date());
        dealerUserInfo.setDeletedFlag("N");
        result = dealerUserInfoMapper.insert(dealerUserInfo);
        return result;
    }

    /**
     * 修改经销商用户
     *
     * @param dealerUserInfo 经销商用户对象
     * @return 成功条数
     */
    public Integer update(DealerUserInfo dealerUserInfo) {
        LOGGER.info("修改用户查询参数为:{}。", dealerUserInfo);
        Integer result;
        dealerUserInfo.setUpdatedDate(new Date());
        result = dealerUserInfoMapper.updateByPrimaryKey(dealerUserInfo);
        return result;
    }

    /**
     * 根据用户名修改经销商用户密码
     *
     * @param name
     * @param password
     * @param updatedBy
     * @return
     */
    public Integer updatePasswordByName(String name, String password, String updatedBy) {
        LOGGER.info("修改的用户:{},修改后的密码:{}", name, password);
        Integer result;
        DealerUserInfo dealerUserInfo = new DealerUserInfo();
        dealerUserInfo.setName(name);
        dealerUserInfo.setPassword(password);
        dealerUserInfo.setUpdatedBy(updatedBy);
        dealerUserInfo.setUpdatedDate(new Date());
        result = dealerUserInfoMapper.updateByDealerUserName(dealerUserInfo);
        return result;
    }

    /**
     * 修改经销商用户根据用户ID
     *
     * @param dealerUserInfo 经销商用户对象
     * @return 成功条数
     */
    public Integer updateByDealerUserId(DealerUserInfo dealerUserInfo) {
        LOGGER.info("修改用户查询参数为:{}。", dealerUserInfo);
        Integer result;
        dealerUserInfo.setUpdatedDate(new Date());
        result = dealerUserInfoMapper.updateByDealerUserId(dealerUserInfo);
        return result;
    }

    /**
     * 修改删除标识根据用户ID
     *
     * @param dealerUserInfo 经销商用户对象
     * @return 成功条数
     */
    public Integer deleteByDealerUserId(DealerUserInfo dealerUserInfo) {
        LOGGER.info("修改用户查询参数为:{}。", dealerUserInfo);
        Integer result;
        dealerUserInfo.setUpdatedDate(new Date());
        result = dealerUserInfoMapper.deleteByDealerUserId(dealerUserInfo);
        return result;
    }

    /**
     * 获取经销商用户信息List
     *
     * @param dealerUserInfo
     * @return
     */
    public List<DealerUserInfo>gteDealerUserInfoList(DealerUserInfo dealerUserInfo){
        LOGGER.info("查询用户参数为:{}。", dealerUserInfo);
        List<DealerUserInfo>result;
        result = dealerUserInfoMapper.gteDealerUserInfoList(dealerUserInfo);
        return result;
    }
}
