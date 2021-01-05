package cn.com.glsx.supplychain.jxc.service;

import cn.com.glsx.framework.core.enums.DefaultErrorEnum;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.jxc.mapper.JXCMTDeviceInfoMapper;
import cn.com.glsx.supplychain.jxc.model.JXCMTDeviceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author luoqiang
 * @version 1.0
 * @program: supplychain
 * @description:
 * @date 2020/9/11 2:07
 */
@Service
public class JXCMTDeviceInfoService {
    private static final Logger logger = LoggerFactory.getLogger(JXCMTDeviceInfoService.class);
    @Autowired
    private JXCMTDeviceInfoMapper jxcmtDeviceInfoMapper;
    public List<JXCMTDeviceInfo> listDeviceInfoBySn(List<String> sns) throws RpcServiceException {
        try {
            Example example = new Example(JXCMTDeviceInfo.class);
            example.createCriteria().andIn("sn", sns)
                    .andEqualTo("deletedFlag", "N");
            return jxcmtDeviceInfoMapper.selectByExample(example);
        } catch (Exception e) {
            logger.error("JXCMTDeviceService::listDeviceInfoBySn 数据库获取数据异常" + e.getMessage());
            throw new RpcServiceException(DefaultErrorEnum.DATABASE_ERROR);
        }
    }

    public List<JXCMTDeviceInfo> listDeviceInfoByIccids(List<String> iccids) throws RpcServiceException {
        try {
            Example example = new Example(JXCMTDeviceInfo.class);
            example.createCriteria().andIn("iccid", iccids)
                    .andEqualTo("deletedFlag", "N");
            return jxcmtDeviceInfoMapper.selectByExample(example);
        } catch (Exception e) {
            logger.error("JXCMTDeviceService::listDeviceInfoByIccids 数据库获取数据异常" + e.getMessage());
            throw new RpcServiceException(DefaultErrorEnum.DATABASE_ERROR);
        }
    }

    public List<JXCMTDeviceInfo> listDeviceInfoByImei(List<String> imeis) throws RpcServiceException {
        try {
            Example example = new Example(JXCMTDeviceInfo.class);
            example.createCriteria().andIn("imei", imeis)
                    .andEqualTo("deletedFlag", "N");
            return jxcmtDeviceInfoMapper.selectByExample(example);
        } catch (Exception e) {
            logger.error("JXCMTDeviceService::listDeviceInfoByIccids 数据库获取数据异常" + e.getMessage());
            throw new RpcServiceException(DefaultErrorEnum.DATABASE_ERROR);
        }
    }



    public Map<String,JXCMTDeviceInfo> listMapDeviceInfoBySn(List<String> sns) throws RpcServiceException {
        try {
            Map<String,JXCMTDeviceInfo> mapResult=null;
            List<JXCMTDeviceInfo> deviceInfoList=listDeviceInfoBySn(sns);
            if(StringUtils.isEmpty(deviceInfoList) || deviceInfoList.size()==0){
                return new HashMap<>();
            }
            mapResult=deviceInfoList.stream().collect(Collectors.toMap(JXCMTDeviceInfo::getSn,deviceInfo->deviceInfo));
            if(null == mapResult){
                mapResult = new HashMap<>();
            }
            return mapResult;
        } catch (Exception e) {
            logger.error("JXCMTDeviceInfoService::listMapDeviceInfoBySn 数据库获取数据异常" + e.getMessage());
            throw new RpcServiceException(DefaultErrorEnum.DATABASE_ERROR);
        }
    }

    public Map<String,JXCMTDeviceInfo> listMapDeviceInfoByIccids(List<String> iccids) throws RpcServiceException {
        try {
            Map<String,JXCMTDeviceInfo> mapResult=null;
            List<JXCMTDeviceInfo> deviceInfoList=listDeviceInfoByIccids(iccids);
            if(StringUtils.isEmpty(deviceInfoList) || deviceInfoList.size()==0){
                return new HashMap<>();
            }
            mapResult=deviceInfoList.stream().collect(Collectors.toMap(JXCMTDeviceInfo::getIccid, Function.identity(), (key1, key2) -> key2));
            if(null == mapResult){
                mapResult = new HashMap<>();
            }
            return mapResult;
        } catch (Exception e) {
            logger.error("JXCMTDeviceInfoService::listDeviceInfoByIccids 数据库获取数据异常" + e.getMessage());
            throw new RpcServiceException(DefaultErrorEnum.DATABASE_ERROR);
        }
    }

    public Map<String,JXCMTDeviceInfo> listMapDeviceInfoByImei(List<String> imeis) throws RpcServiceException {
        try {
            Map<String,JXCMTDeviceInfo> mapResult=null;
            List<JXCMTDeviceInfo> deviceInfoList=listDeviceInfoByImei(imeis);
            if(StringUtils.isEmpty(deviceInfoList) || deviceInfoList.size()==0){
                return new HashMap<>();
            }
            mapResult=deviceInfoList.stream().collect(Collectors.toMap(JXCMTDeviceInfo::getImei,Function.identity(), (key1, key2) -> key2));
            if(null == mapResult){
                mapResult = new HashMap<>();
            }
            return mapResult;
        } catch (Exception e) {
            logger.error("JXCMTDeviceInfoService::listMapDeviceInfoByImei 数据库获取数据异常" + e.getMessage());
            throw new RpcServiceException(DefaultErrorEnum.DATABASE_ERROR);
        }
    }


}
