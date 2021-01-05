package cn.com.glsx.supplychain.service;

import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.mapper.DeviceImeiStockMapper;
import cn.com.glsx.supplychain.model.DeviceImeiStock;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;


/**
 * @ClassName deviceImeiStockService
 * @Author admin
 * @Param
 * @Date 2018/11/15 11:24
 * @Version
 **/

@Service
@Transactional
public class DeviceImeiStockService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DeviceImeiStockMapper deviceImeiStockMapper;


    /**
     *
     * @Title: getDeviceImeiStock
     * @Description: 获取IMEI设备库存信息
     * @param @param deviceImeiStock
     * @param @return
     * @param @throws RpcServiceException
     * @return DeviceImeiStock
     * @throws
     */
    public Page<DeviceImeiStock> getDeviceImeiStock(RpcPagination<DeviceImeiStock>pagination,DeviceImeiStock record ) throws RpcServiceException {

        Page<DeviceImeiStock> result = null;
        try {

            PageHelper.startPage(pagination.getPageNum(),pagination.getPageSize());
            result = deviceImeiStockMapper.selectdeviceImeiStock(record);

        } catch (Exception e) {
            logger.error("查询MEI设备库存信息数据库异常 错误信息:" + e.getMessage());
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
        }
        return  result;
    }
    
    /**
    *
    * @Title: getDeviceImeiStockByImei
    * @Description: 根据imei获取IMEI设备库存信息
    * @param @param deviceImeiStock
    * @param @return
    * @param @throws RpcServiceException
    * @return DeviceImeiStock
    * @throws
    */
    public DeviceImeiStock getDeviceImeiStockByImei(DeviceImeiStock record) throws RpcServiceException
    {
    	if(StringUtils.isEmpty(record) || StringUtils.isEmpty(record.getImei()))
    	{
    		logger.error("DeviceImeiStockService::getDeviceImeiStockByImei 参数错误");
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
    	}
    	logger.info("DeviceImeiStockService::getDeviceImeiStockByImei param=" + record.toString());
    	try
        {
            return deviceImeiStockMapper.selectDeviceImeiStockByUniqueKey(record);
        }
        catch(Exception e)
        {
            logger.error("DeviceImeiStockService::getDeviceImeiStockByImei 数据库获取数据异常" + e.getMessage());
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
        }
    }

    /**
     * @Title batAddDeviceImeiStock
     * @Description 添加imei库存设备
     * @param record
     * @return DeviceImeiStock
     * @throws RpcServiceException
     * @author
     * @Time
     */
    public void batchAddDeviceImeiStock(List<DeviceImeiStock> stockList)  throws RpcServiceException
    {
        if(StringUtils.isEmpty(stockList))
        {
            return ;
        }

        try
        {
            deviceImeiStockMapper.batchInsertOnDuplicateKeyUpdate(stockList);
        }
        catch(Exception e)
        {
            logger.error("DeviceImeiStockService::addDeviceImeiStock 数据库获取数据异常" + e.getMessage());
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
        }
    }

    /**
     * @Title addDeviceImeiStock
     * @Description 添加imei库存设备
     * @param record
     * @return DeviceImeiStock
     * @throws RpcServiceException
     * @author
     * @Time
     */
    public DeviceImeiStock addDeviceImeiStock(DeviceImeiStock record) throws RpcServiceException
    {
    	DeviceImeiStock deviceImeiStock = null;
        if(StringUtils.isEmpty(record) || StringUtils.isEmpty(record.getImei()))
        {
            logger.error("DeviceImeiStockService::addDeviceImeiStock 参数错误");
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        logger.info("DeviceImeiStockService::addDeviceImeiStock param=" + record.toString());
        try
        {
            deviceImeiStockMapper.insertSelective(record);
            deviceImeiStock = deviceImeiStockMapper.selectByPrimaryKey(record.getId());
        }
        catch(Exception e)
        {
            logger.error("DeviceImeiStockService::addDeviceImeiStock 数据库获取数据异常" + e.getMessage());
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
        }
        return deviceImeiStock;
    }

    /**
     * @Title removeDeviceImeiStock
     * @Description 删除imei库存设备
     * @param id
     * @return DeviceImeiStock
     * @throws RpcServiceException
     * @author
     * @Time
     */
    public void removeDeviceImeiStock(Integer id) throws RpcServiceException
    {
        if(StringUtils.isEmpty(id))
        {
            logger.error("DeviceImeiStockService::addDeviceImeiStock 参数错误");
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        logger.info("DeviceImeiStockService::removeDeviceImeiStock param=" + id.toString());
        try
        {
             deviceImeiStockMapper.deleteByPrimaryKey(id);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


}
