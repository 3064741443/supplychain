package cn.com.glsx.supplychain.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.manager.SupplyChainRedisService;
import cn.com.glsx.supplychain.mapper.DeviceFileVirtualMapper;
import cn.com.glsx.supplychain.model.DeviceFileVirtual;
import cn.com.glsx.supplychain.model.DeviceListImport;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;


@Service
@Transactional
public class DeviceFileVirtualService {

private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DeviceFileVirtualMapper fileVirtualMapper;
	
	@Autowired
	private SupplyChainRedisService redisService;
	
	/** 
	* @Title getDeviceFileVirtualByImsi
	* @Description 通过imsi获取虚拟设备
	* @param imsi
	* @return Integer
	* @throws RpcServiceException
	* @author QL.LiuQuan
	* @Time 2018-10-14 
	*/
	public DeviceFileVirtual getDeviceFileVirtualByImsi(String imsi) throws RpcServiceException
	{
		DeviceFileVirtual deviceFileVirtual = null;
		if(StringUtils.isEmpty(imsi))
		{	
			return deviceFileVirtual;
		}
		logger.info("DeviceFileVirtualService::getDeviceFileVirtualByImsi param=" + imsi);
		
		try
		{
			deviceFileVirtual = fileVirtualMapper.selectByImsi(imsi);
		}
		catch(Exception e)
		{
			logger.error("DeviceFileVirtualService::getDeviceFileVirtualByImsi 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		
		logger.info("DeviceFileVirtualService::getDeviceFileVirtualByImsi return deviceFileVirtual=" + (StringUtils.isEmpty(deviceFileVirtual)?"null":deviceFileVirtual.toString()));
		return deviceFileVirtual;
	}
	
	/** 
	* @Title addDeviceFileVirtual
	* @Description 添加虚拟设备
	* @param imsi
	* @return DeviceFileVirtual
	* @throws RpcServiceException
	* @author QL.LiuQuan
	* @Time 2018-10-14 
	*/
	public DeviceFileVirtual addDeviceFileVirtual(DeviceFileVirtual record) throws RpcServiceException
	{
		DeviceFileVirtual deviceFileVirtual = null;
		if(StringUtils.isEmpty(record) || StringUtils.isEmpty(record.getImsi()))
		{
			logger.error("DeviceFileVirtualService::addDeviceFileVirtual 参数错误");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		logger.info("DeviceFileVirtualService::addDeviceFileVirtual param=" + record.toString());
		
		try
		{
			deviceFileVirtual = fileVirtualMapper.selectByImsi(record.getImsi());
		}
		catch(Exception e)
		{
			logger.error("DeviceFileVirtualService::addDeviceFileVirtual 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		
		
		if(!StringUtils.isEmpty(deviceFileVirtual))
		{
			logger.error("DeviceFileVirtualService::addDeviceFileVirtual 流量卡:" + record.getImsi() + "已经存在虚拟表中!");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_DEVICEVIRTUAL_EXIST);
		}
		
		try
		{
			fileVirtualMapper.insertSelective(record);
			deviceFileVirtual = fileVirtualMapper.selectByPrimaryKey(record.getId());
		}
		catch(Exception e)
		{
			logger.error("DeviceFileVirtualService::addDeviceFileVirtual 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		
		logger.info("DeviceFileVirtualService::addDeviceFileVirtual return deviceFileVirtual=" + (StringUtils.isEmpty(deviceFileVirtual)?"null":deviceFileVirtual.toString()));
		return deviceFileVirtual;
	}
	
	
	/** 
	* @Title batchAddDeviceFileVirtual
	* @Description 批量添加虚拟设备不做验证 不返回数据id 有数据则跟新 无数据则插入
	* @param imsi
	* @return DeviceFileVirtual
	* @throws RpcServiceException
	* @author QL.LiuQuan
	* @Time 2018-10-14 
	*/
	public Integer batchAddDeviceFileVirtual(List<DeviceFileVirtual> virtualList) throws RpcServiceException
	{
		Integer ret = 0;
		if(StringUtils.isEmpty(virtualList))
		{
			return ret;
		}
		logger.info("DeviceFileVirtualService::batchAddDeviceFileVirtual param=virtualList{}" + virtualList + " virtualList.size=" + virtualList.size());
		
		try
		{
			fileVirtualMapper.batchInsertOnDuplicateKeyUpdate(virtualList);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("DeviceFileVirtualService::addDeviceFileVirtual 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("DeviceFileVirtualService::batchAddDeviceFileVirtual ok!");
		
		return ret;
	}
	
	
	/** 
	* @Title updateDeviceFileVirtualById
	* @Description 修改虚拟设备信息
	* @param imsi
	* @return DeviceFileVirtual
	* @throws RpcServiceException
	* @author QL.LiuQuan
	* @Time 2018-10-14 
	*/
	public DeviceFileVirtual updateDeviceFileVirtualById(DeviceFileVirtual record) throws RpcServiceException
	{
		DeviceFileVirtual deviceFileVirtual = null;
		if(StringUtils.isEmpty(record) || StringUtils.isEmpty(record.getId()))
		{
			logger.error("DeviceFileVirtualService::updateDeviceFileVirtualByImsi 参数错误");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		logger.info("DeviceFileVirtualService::updateDeviceFileVirtualByImsi param=" + record.toString());
		
		try
		{
			fileVirtualMapper.updateByPrimaryKeySelective(record);
			deviceFileVirtual = fileVirtualMapper.selectByPrimaryKey(record.getId());
		}
		catch(Exception e)
		{
			logger.error("DeviceFileVirtualService::updateDeviceFileVirtualByImsi 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		
		logger.info("DeviceFileVirtualService::updateDeviceFileVirtualByImsi return deviceFileVirtual=" + (StringUtils.isEmpty(deviceFileVirtual)?"null":deviceFileVirtual.toString()));
		return deviceFileVirtual;
	}
	
	/** 
	* @Title countDeviceFilesByDeviceCode  
	* @Description 统计deviceCode下的设备数
	* @param deviceCode
	* @return Integer
	* @throws RpcServiceException
	* @author QL.LiuQuan
	* @Time 2018-10-14 
	*/
	public Integer countDeviceFilesByDeviceCode(Integer deviceCode) throws RpcServiceException
	{
		Integer ret = 0;
		if(StringUtils.isEmpty(deviceCode))
		{
			return ret;
		}	
		
		logger.info("DeviceFileVirtualService::countDeviceFilesByDeviceCode param deviceCode=" + deviceCode);
		
		try
		{
			ret = fileVirtualMapper.countDeviceFilesByDeviceCode(deviceCode);
		}
		catch(Exception e)
		{
			logger.error("DeviceFileVirtualService::countDeviceFilesByDeviceCode 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		
		logger.info("DeviceFileVirtualService::countDeviceFilesByDeviceCode return ret=" + ret);
		return ret;
	}
	
	/** 
	* @Title countDeviceFilesByDeviceType  
	* @Description 统计deviceCode下的设备数
	* @param deviceCode
	* @return Integer
	* @throws RpcServiceException
	* @author QL.LiuQuan
	* @Time 2018-10-14 
	*/
	public Integer countDeviceFilesByDeviceType(Integer deviceTypeId) throws RpcServiceException
	{
		Integer ret = 0;
		if(StringUtils.isEmpty(deviceTypeId))
		{
			return ret;
		}
		logger.info("DeviceFileVirtualService::countDeviceFilesByDeviceType param deviceTypeId=" + deviceTypeId);
		
		try
		{
			ret = fileVirtualMapper.countDevicesByDeviceType(deviceTypeId);
		}
		catch(Exception e)
		{
			logger.error("DeviceFileVirtualService::countDeviceFilesByDeviceType 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		
		logger.info("DeviceFileVirtualService::countDeviceFilesByDeviceType return ret=" + ret);
		return ret;
	}
	
	/** 
	* @Title countDeviceFiles
	* @Description 统计设备数
	* @param 
	* @return Integer
	* @throws RpcServiceException
	* @author QL.LiuQuan
	* @Time 2018-10-14 
	*/
	public Integer countDeviceFiles() throws RpcServiceException
	{
		Integer ret = 0;
		logger.info("DeviceFileVirtualService::countDeviceFiles");
		
		try
		{
			ret = fileVirtualMapper.countDevices();
		}
		catch(Exception e)
		{
			logger.error("DeviceFileVirtualService::countDeviceFiles 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		
		logger.info("DeviceFileVirtualService::countDeviceFiles return ret=" + ret);
		return ret;
	}
	/**
	 * 
	 * @Title: getDeviceFileVirtual
	 * @Description: 获取设备外部卡虚拟导出信息
	 * @param @param
	 * @param @return
	 * @param @throws RpcServiceException
	 * @param pagination
	 * @return DeviceFileVirtual
	 * @throws
	 */
	public Page<DeviceFileVirtual> getDeviceFileVirtual(RpcPagination<DeviceFileVirtual> pagination, DeviceFileVirtual record) throws RpcServiceException {
		
		Page<DeviceFileVirtual> result = null;
		try {

			PageHelper.startPage(pagination.getPageNum(),pagination.getPageSize());
			result = fileVirtualMapper.selectFileVirtual(record);

		} catch (Exception e) {
			logger.error("查询设备外部卡虚拟信息数据库异常 错误信息:" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		return  result;
	}
	
	/**
	* 
	* @Title: getDeviceFileVirtualByIccid
	* @Description: 获取设备外部卡虚拟导出信息
	* @param @param
	* @param @return
	* @param @throws RpcServiceException
	* @param pagination
	* @return DeviceFileVirtual
	* @throws
	*/
	public DeviceFileVirtual getDeviceFileVirtualByIccid(String iccid) throws RpcServiceException
	{
		DeviceFileVirtual deviceFileVirtual = null;
		logger.info("DeviceFileVirtualService::getDeviceFileVirtualByIccid start iccid:" + iccid);
		try
		{
			deviceFileVirtual = fileVirtualMapper.getDeviceFileVirtualByIccid(iccid);
		}
		catch (Exception e)
		{
			logger.error("DeviceFileVirtualService::getDeviceFileVirtualByIccid 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("DeviceFileVirtualService::getDeviceFileVirtualByIccid end deviceFileVirtual:{}", deviceFileVirtual);
		return deviceFileVirtual;
	}
	
	 public List<DeviceFileVirtual> getDeviceFileVirtualByIccids(List<String> iccids) throws RpcServiceException
	 {
		 if(iccids == null || iccids.isEmpty()){
			 return null;
		 }
		 return fileVirtualMapper.getDeviceFileVirtualByIccids(iccids);
	 }
	 
	 public Map<String,DeviceFileVirtual> getDeviceFileVirtualByImsis(List<String> imsis){
		 Map<String,DeviceFileVirtual> mapResult = new HashMap<>();
		 if(null == imsis || imsis.isEmpty()){
			 return mapResult;
		 }
		 List<DeviceFileVirtual> listVirtuals = fileVirtualMapper.getDeviceFileVirtualByImsis(imsis);
		 if(null == listVirtuals || listVirtuals.isEmpty()){
			 return mapResult;
		 }
		 for(DeviceFileVirtual virtual:listVirtuals){
			 mapResult.put(virtual.getImsi(), virtual);
		 }
		 return mapResult;
	 }
}
