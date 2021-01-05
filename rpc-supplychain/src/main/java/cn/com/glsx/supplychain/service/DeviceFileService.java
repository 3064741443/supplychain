package cn.com.glsx.supplychain.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import cn.com.glsx.supplychain.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.enums.PackageStatuEnum;
import cn.com.glsx.supplychain.manager.SupplyChainRedisService;
import cn.com.glsx.supplychain.mapper.DeviceFileMapper;
import cn.com.glsx.supplychain.model.DeviceFile;

@Service
@Transactional
public class DeviceFileService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DeviceFileMapper deviceFileMapper;

    @Autowired
    private SupplyChainRedisService redisService;


    /**
     * @param deviceCode
     * @return Integer
     * @throws RpcServiceException
     * @Title countDeviceFilesByCondition
     * @Description 统计deviceCode下的设备数
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    public Integer countDeviceFilesByCondition(DeviceFile record) throws RpcServiceException {
        Integer ret = 0;
        if (StringUtils.isEmpty(record)) {
            return ret;
        }
        logger.info("DeviceFileService::countDeviceFilesByCondition param record=" + record.toString());

        try {
            ret = deviceFileMapper.countDevicesByCondition(record);
        } catch (Exception e) {
            logger.error("DeviceFileService::countDeviceFilesByCondition 数据库获取数据异常" + e.getMessage());
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
        }

        logger.info("DeviceFileService::countDeviceFilesByCondition return ret=" + ret);
        return ret;
    }

    /**
     * @param deviceCode
     * @return Integer
     * @throws RpcServiceException
     * @Title countDeviceFilesByDeviceCode
     * @Description 统计deviceCode下的设备数
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    public Integer countDeviceFilesByDeviceCode(Integer deviceCode) throws RpcServiceException {
        Integer ret = 0;
        if (StringUtils.isEmpty(deviceCode)) {
            return ret;
        }

        logger.info("DeviceFileService::countDeviceFilesByDeviceCode param deviceCode=" + deviceCode);

        try {
            ret = deviceFileMapper.countDeviceFilesByDeviceCode(deviceCode);
        } catch (Exception e) {
            logger.error("DeviceFileService::countDeviceFilesByDeviceCode 数据库获取数据异常" + e.getMessage());
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
        }

        logger.info("DeviceFileService::countDeviceFilesByDeviceCode return ret=" + ret);
        return ret;
    }

    /**
     * @param deviceTypeId
     * @return Integer
     * @throws RpcServiceException
     * @Title countDeviceFilesByDeviceType
     * @Description 统计deviceType下的设备数
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    public Integer countDeviceFilesByDeviceType(Integer deviceTypeId) throws RpcServiceException {
        Integer ret = 0;
        if (StringUtils.isEmpty(deviceTypeId)) {
            return ret;
        }
        logger.info("DeviceFileService::countDeviceFilesByDeviceType param deviceTypeId=" + deviceTypeId);

        try {
            ret = deviceFileMapper.countDevicesByDeviceType(deviceTypeId);
        } catch (Exception e) {
            logger.error("DeviceFileService::countDeviceFilesByDeviceType 数据库获取数据异常" + e.getMessage());
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
        }

        logger.info("DeviceFileService::countDeviceFilesByDeviceType return ret=" + ret);
        return ret;
    }

    /**
     * @param pkgStatus
     * @return Integer
     * @throws RpcServiceException
     * @Title countDeviceFilesByPackageStatus
     * @Description 统计套餐激活数或者未激活数
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    public Integer countDeviceFilesByPackageStatus(PackageStatuEnum pkgStatus) throws RpcServiceException {
        Integer ret = 0;
        if (StringUtils.isEmpty(pkgStatus)) {
            return ret;
        }

        logger.info("DeviceFileService::countDeviceFilesByPackageStatus param pkgStatus=" + pkgStatus.getValue());

        try {
            ret = deviceFileMapper.countDevicesByPackageStatus(pkgStatus.getValue());
        } catch (Exception e) {
            logger.error("DeviceFileService::countDeviceFilesByPackageStatus 数据库获取数据异常" + e.getMessage());
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
        }

        logger.info("DeviceFileService::countDeviceFilesByPackageStatus return ret=" + ret);
        return ret;
    }

    /**
     * @param
     * @return Integer
     * @throws RpcServiceException
     * @Title countDeviceFiles
     * @Description 统计设备数
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    public Integer countDeviceFiles() throws RpcServiceException {
        Integer ret = 0;
        logger.info("DeviceFileService::countDeviceFiles");

        try {
            ret = deviceFileMapper.countDevices();
        } catch (Exception e) {
            logger.error("DeviceFileService::countDeviceFiles 数据库获取数据异常" + e.getMessage());
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
        }

        logger.info("DeviceFileService::countDeviceFiles return ret=" + ret);
        return ret;
    }

    /**
     * @param pagination,deviceCode
     * @return Page<DeviceFile>
     * @throws RpcServiceException
     * @Title pageDeviceFile
     * @Description 设备关系明细列表
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
/*    public Page<DeviceFile> pageDeviceFile(RpcPagination<DeviceFile> pagination, DeviceFile record) throws RpcServiceException {
        if (StringUtils.isEmpty(pagination) || StringUtils.isEmpty(record)) {
            logger.error("DeviceFileService::pageDeviceFile 参数不能为空");
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        logger.info("DeviceFileService::pageDeviceFile param pagination=" + pagination.toString() + " record:{}" + record + "deviceSnapshot:{}" + record.getSnapshot());

        Page<DeviceFile> oPage = null;
        Page<DeviceFile> resultPage = new Page<DeviceFile>();

        try {
            //PageHelper.startPage(pagination.getPageNum(),pagination.getPageSize());
            logger.info("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm01:" + System.currentTimeMillis());
            record.setCheckNumber(record.getPageSize());
            oPage = deviceFileMapper.nexPageDeviceFile(record);
            record.setCheckNumber(record.getPageSize() * 10);
            DeviceFile resultFile = null;
            if (!StringUtils.isEmpty(oPage)) {
                for (int i = 0; i < oPage.size(); i++) {
                    DeviceFile item = oPage.get(i);
                    if (i == 0) {
                        resultFile = item;
                        record.setPageFirstId(item.getId());
                        resultFile.setAgoIdList(deviceFileMapper.agoListDeviceFileIds(record));
                    }
                    record.setPageFirstId(item.getId());
                }
                if (!StringUtils.isEmpty(resultFile)) {
                    List<Integer> nexIdList = deviceFileMapper.nexListDeviceFileIds(record);
                    if (nexIdList.size() % record.getPageSize() != 0) {
                        resultFile.setNexPages(nexIdList.size() / record.getPageSize() + 1);
                    } else {
                        resultFile.setNexPages(nexIdList.size() / record.getPageSize());
                    }
                    resultFile.setNexIdList(nexIdList);
                }
                //oPage.setTotal(oPage.get(0).getTotalSum());
            }
            //oPage = deviceFileMapper.pageDeviceFile(record);
            logger.info("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm02:" + System.currentTimeMillis());
        } catch (Exception e) {
            logger.error("DeviceFileService::pageDeviceFile 数据库获取数据异常" + e.getMessage());
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
        }

        logger.info("DeviceFileService::pageDeviceFile return oPage=" + (StringUtils.isEmpty(oPage) ? "null" : oPage.toString()));
        return oPage;
    }*/
    public Page<DeviceFile> pageDeviceFile(RpcPagination<DeviceFile> pagination,DeviceFile record) throws RpcServiceException
    {
        if(StringUtils.isEmpty(pagination) || StringUtils.isEmpty(record))
        {
            logger.error("DeviceFileService::pageDeviceFile 参数不能为空");
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        logger.info("DeviceFileService::pageDeviceFile param pagination=" + pagination.toString() + " record:{}" + record + "deviceSnapshot:{}" + record.getSnapshot());

        Page<DeviceFile> oPage = null;

        try
        {
            PageHelper.startPage(record.getPageStart(),record.getPageSize());
            logger.error("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm01:"+System.currentTimeMillis());
            oPage = deviceFileMapper.pageDeviceFile(record);
            logger.error("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm02:"+System.currentTimeMillis());
        }
        catch(Exception e)
        {
            logger.error("DeviceFileService::pageDeviceFile 数据库获取数据异常" + e.getMessage(),e);
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
        }

        logger.info("DeviceFileService::pageDeviceFile return oPage=" + (StringUtils.isEmpty(oPage)?"null":oPage.toString()));
        return oPage;
    }


    /**
     * @param deviceCode
     * @return List<DeviceFile>
     * @throws RpcServiceException
     * @Title exportDeviceFile
     * @Description 设备关系明细导出
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    public List<DeviceFile> exportDeviceFile(DeviceFile record) throws RpcServiceException {
        if (StringUtils.isEmpty(record)) {
            logger.error("DeviceFileService::exportDeviceFile 参数不能为空");
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }

        logger.info("DeviceFileService::exportDeviceFile param record=" + record.toString());

        List<DeviceFile> oList;

        try {
            oList = deviceFileMapper.exportDeviceFile(record);
        } catch (Exception e) {
            logger.error("DeviceFileService::exportDeviceFile 数据库获取数据异常" + e.getMessage());
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
        }

        logger.info("DeviceFileService::pageDeviceFile return oList.size=" + (StringUtils.isEmpty(oList) ? "0" : oList.size()));
        return oList;
    }

    /**
     * @param id
     * @return DeviceFile
     * @throws RpcServiceException
     * @Title getDeviceFileById
     * @Description 根据ID获取设备基本信息
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    public DeviceFile getDeviceFileById(Integer id) throws RpcServiceException {
        DeviceFile deviceFile = null;
        logger.info("DeviceFileService::getDeviceFileById param id=" + id);

        try {
            deviceFile = deviceFileMapper.selectByPrimaryKey(id);
        } catch (Exception e) {
            logger.error("DeviceFileService::exportDeviceFile 数据库获取数据异常" + e.getMessage());
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
        }

        logger.info("DeviceFileService::getDeviceFileById return deviceFile=" + (StringUtils.isEmpty(deviceFile) ? "null" : deviceFile.toString()));
        return deviceFile;
    }

    /**
     * @param sn
     * @return DeviceFile
     * @throws RpcServiceException
     * @Title getDeviceFileBySn
     * @Description 根据sn获取设备基本信息
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    public DeviceFile getDeviceFileBySn(String sn) throws RpcServiceException {
        DeviceFile deviceFile = null;
        logger.info("DeviceFileService::getDeviceFileBySn param sn=" + sn);

        try {
            deviceFile = redisService.getDeviceFileBySn(sn);
            if (StringUtil.isEmpty(deviceFile)) {
                DeviceFile record = new DeviceFile();
                record.setSn(sn);
                deviceFile = deviceFileMapper.selectByUniqueKey(record);
            }

        } catch (Exception e) {
            logger.error("DeviceFileService::exportDeviceFile 数据库获取数据异常" + e.getMessage());
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
        }

        logger.info("DeviceFileService::getDeviceFileBySn return deviceFile=" + (StringUtils.isEmpty(deviceFile) ? "null" : deviceFile.toString()));
        return deviceFile;
    }

    /**
     * @param sn
     * @return DeviceFile
     * @throws RpcServiceException
     * @Title checkDeviceSnsExistInDeviceFileTable
     * @Description 批量检查sn是否在数据库中
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    public List<String> checkDeviceSnsExistInDeviceFileTable(List<String> deviceSns) throws RpcServiceException {
        List<String> oList = null;
        if (StringUtils.isEmpty(deviceSns)) {
            logger.info("DeviceFileService::checkDeviceSnsExistInDeviceFileTable param sn is null and return null");
            return oList;
        }
        logger.info("DeviceFileService::checkDeviceSnsExistInDeviceFileTable param deviceSns=" + deviceSns.toString());

        try {
            oList = deviceFileMapper.findDeviceFileByDeviceSns(deviceSns);
        } catch (Exception e) {
            logger.error("DeviceFileService::checkDeviceSnsExistInDeviceFileTable 数据库获取数据异常" + e.getMessage());
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
        }

        logger.info("DeviceFileService::checkDeviceSnsExistInDeviceFileTable return=" + (StringUtils.isEmpty(oList) ? "null" : oList.toString()));
        return oList;
    }

    /**
     * @param sn
     * @return DeviceFile
     * @throws RpcServiceException
     * @Title addDeviceFileByDuplicateKey
     * @Description 插入设备如果数据库中存在则修改字段
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    public DeviceFile addDeviceFileByDuplicateKey(DeviceFile record) throws RpcServiceException {
        if (StringUtils.isEmpty(record) || StringUtils.isEmpty(record.getSn()) || StringUtils.isEmpty(record.getDeviceCode()) ||
                StringUtils.isEmpty(record.getPackageId()) || /*StringUtils.isEmpty(record.getOperatorMerchantNo()) ||*/
                StringUtils.isEmpty(record.getSendMerchantNo()) || StringUtils.isEmpty(record.getOutStorageType()) ||
                StringUtils.isEmpty(record.getExternalFlag())) {
            logger.error("DeviceFileService::addDeviceFileByDuplicateKey 参数错误");
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }

        logger.info("DeviceFileService::addDeviceFileByDuplicateKey record=" + record.toString());

        try {
            deviceFileMapper.insertOnDuplicateKeyUpdate(record);
        } catch (Exception e) {
            logger.error("DeviceFileService::addDeviceFileByDuplicateKey 数据库获取数据异常" + e.getMessage());
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
        }

        logger.info("DeviceFileService::addDeviceFileByDuplicateKey deviceFile=" + record.toString());
        return record;


    }

    /**
     * @param sn
     * @return DeviceFile
     * @throws RpcServiceException
     * @Title addDeviceFile
     * @Description 插入设备
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    public DeviceFile addDeviceFile(DeviceFile record) throws RpcServiceException {
        DeviceFile deviceFile = null;
        if (StringUtils.isEmpty(record) || StringUtils.isEmpty(record.getSn()) || StringUtils.isEmpty(record.getDeviceCode()) ||
                StringUtils.isEmpty(record.getPackageId()) || /*StringUtils.isEmpty(record.getOperatorMerchantNo()) || */
                StringUtils.isEmpty(record.getSendMerchantNo()) || StringUtils.isEmpty(record.getOutStorageType()) ||
                StringUtils.isEmpty(record.getExternalFlag())) {
            logger.error("DeviceFileService::addDeviceFile 参数错误");
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        logger.info("DeviceFileService::addDeviceFile record=" + record.toString());

        try {
            deviceFile = this.getDeviceFileBySn(record.getSn());
        } catch (RpcServiceException e) {
            logger.error("DeviceFileService::addDeviceFile 方法调用出错" + e.getMessage());
            throw e;
        }

        if (!StringUtils.isEmpty(deviceFile)) {
            logger.info("DeviceFileService::addDeviceFile 新增设备:" + record.getSn() + " 已经存在");
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_DEVICEFILE_EXIST);
        }

        try {
            deviceFileMapper.insertSelective(record);
            deviceFile = deviceFileMapper.selectByPrimaryKey(record.getId());
        } catch (Exception e) {
            logger.error("DeviceFileService::addDeviceFile 数据库获取数据异常" + e.getMessage());
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
        }

        logger.info("DeviceFileService::addDeviceFile deviceFile {}" + deviceFile);
        return deviceFile;
    }

    /**
     * @param record
     * @return DeviceFile
     * @throws RpcServiceException
     * @Title updateDeviceFileById
     * @Description 根据sn修改设备信息(条件更新)
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    public DeviceFile updateDeviceFileById(DeviceFile record) throws RpcServiceException {
        DeviceFile deviceFile = null;
        if (StringUtils.isEmpty(record) || StringUtils.isEmpty(record.getId())) {
            logger.error("DeviceFileService::updateDeviceFileBySn 参数错误");
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        logger.info("DeviceFileService::updateDeviceFileBySn param=" + record.toString());

        try {
            deviceFileMapper.updateByPrimaryKeySelective(record);
            deviceFile = deviceFileMapper.selectByUniqueKey(record);
        } catch (Exception e) {
            logger.error("DeviceFileService::updateDeviceFileBySn 数据库获取数据异常" + e.getMessage());
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
        }

        logger.info("DeviceFileService::updateDeviceFileBySn return deviceFile=" + (StringUtils.isEmpty(deviceFile) ? "null" : deviceFile.toString()));
        return deviceFile;
    }

    /**
     * @param record
     * @return DeviceFile
     * @throws RpcServiceException
     * @Title updateAllColDeviceFileById
     * @Description 根据sn修改设备信息(全更新)
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    public DeviceFile updateAllColDeviceFileById(DeviceFile record) throws RpcServiceException {
        DeviceFile deviceFile = null;
        if (StringUtils.isEmpty(record) || StringUtils.isEmpty(record.getId())) {
            logger.error("DeviceFileService::updateAllColDeviceFileBySn 参数错误");
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        logger.info("DeviceFileService::updateAllColDeviceFileBySn param=" + record.toString());

        try {
            deviceFileMapper.updateByPrimaryKey(record);
            deviceFile = deviceFileMapper.selectByPrimaryKey(record.getId());
        } catch (Exception e) {
            logger.error("DeviceFileService::updateAllColDeviceFileBySn 数据库获取数据异常" + e.getMessage());
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
        }

        logger.info("DeviceFileService::updateAllColDeviceFileBySn return deviceFile=" + (StringUtils.isEmpty(deviceFile) ? "null" : deviceFile.toString()));
        return deviceFile;
    }

    /**
     * @param deviceFileList
     * @return oList
     * @throws RpcServiceException
     * @Title batchAddDeviceFile
     * @Description 批量插入物理表
     * @author LeiM
     * @Time 2018-10-14
     */
    public void batchAddDeviceFile(List<DeviceFile> deviceFileList) throws RpcServiceException {
        if (StringUtils.isEmpty(deviceFileList) || deviceFileList.size() == 0) {
            logger.error("DeviceFileService::batchAddDeviceFile 参数错误");
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        logger.info("DeviceFileService::batchAddDeviceFile deviceFileList=" + deviceFileList.size());
        try {
            deviceFileMapper.batchInsertOnDuplicateKeyUpdate(deviceFileList);
        } catch (Exception e) {
            logger.error("DeviceFileService::batchAddDeviceFile 数据库获取数据异常" + e.getMessage());
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
        }
        logger.info("DeviceFileService::batchAddDeviceFile deviceFileList.size()=" + deviceFileList.size());
    }
    
    public Map<String,DeviceFile> getDeviceFilesBySns(List<String> listSn){
    	Map<String,DeviceFile> mapResult = new HashMap<>();
    	if(null == listSn || listSn.isEmpty()){
    		return mapResult;
    	}
    	List<DeviceFile> listDeviceFiles = deviceFileMapper.listDeviceFilesBySns(listSn);
    	if(null == listDeviceFiles){
    		return mapResult;
    	}
    	for(DeviceFile deviceFile:listDeviceFiles){
    		mapResult.put(deviceFile.getSn(), deviceFile);
    	}
    	return mapResult;
    }

}
