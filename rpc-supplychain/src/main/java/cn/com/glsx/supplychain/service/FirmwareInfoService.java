package cn.com.glsx.supplychain.service;


import java.util.Date;
import java.util.List;

import net.sf.json.JSONSerializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.enums.DefaultErrorEnum;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.framework.core.util.RpcAssert;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.mapper.FirmwareInfoMapper;
import cn.com.glsx.supplychain.model.FirmwareInfo;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * @Title: DemoService.java
 * @Description:版本信息服务类
 * @author leiyj
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@Service
@Transactional
public class FirmwareInfoService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private FirmwareInfoMapper firmwareInfoMapper;
	
	/**
	 * 通过ID值查询固件信息
	 * @Title: findById 
	 * @Description: TODO
	 * @param @param id
	 * @param @return 
	 * @return FirmwareInfo
	 * @throws
	 */
	public FirmwareInfo getFirmwareInfoById(Integer id) throws RpcServiceException{
		return firmwareInfoMapper.getFirmwareInfoById(id);	
	}
	
	/**
	 * 查询固件信息
	 * @Title: load 
	 * @Description: TODO
	 * @param @param requestEntity
	 * @param @return 
	 * @return ResponseEntity<FirmwareInfo>
	 * @throws
	 */
	public Page<FirmwareInfo> listFirmwareInfo(RpcPagination<FirmwareInfo> pagination,FirmwareInfo firmwareInfo) throws RpcServiceException{	
		
		
		//判断条件是否为空
		RpcAssert.assertNotNull(pagination, DefaultErrorEnum.DATA_NULL, "pagination must not be null");	
		
		logger.info("查询固件信息列表传入参数："  + JSONSerializer.toJSON(pagination).toString());
		
		try {
			//设置分页数据
			PageHelper.startPage(pagination.getPageNum(), pagination.getPageSize());
			//返回结果集
			return firmwareInfoMapper.listFirmwareInfo(firmwareInfo);
		} catch (Exception e) {
			logger.debug(e.getMessage(),e);
			throw new RpcServiceException(DefaultErrorEnum.DATABASE_ERROR,e.getMessage());
		}
	}
	
	/**
	 * 插入固件信息
	 * @Title: insert 
	 * @Description: TODO
	 * @param @param firmwareInfo
	 * @param @return 
	 * @return int
	 * @throws
	 */
	public int insert(FirmwareInfo firmwareInfo) throws RpcServiceException{
		logger.info(" 插入固件信息: " + JSONSerializer.toJSON(firmwareInfo).toString());
		firmwareInfo.setCreatedDate(new Date());
		firmwareInfo.setUpdatedDate(new Date());
		return firmwareInfoMapper.insert(firmwareInfo);
	}
	
	/**
	 * 更新固件信息
	 * @Title: update 
	 * @Description: TODO
	 * @param @param firmwareInfo
	 * @param @return 
	 * @return int
	 * @throws
	 */
	public int update(FirmwareInfo firmwareInfo) throws RpcServiceException{
		firmwareInfo.setUpdatedDate(new Date());
		return firmwareInfoMapper.update(firmwareInfo);
	}
	
	/**
	 * 查询非分页数据
	 * @param firmwareInfo
	 * @return
	 * @throws RpcServiceException
	 */
	public List<FirmwareInfo> getVersion(FirmwareInfo firmwareInfo) throws RpcServiceException{	
		try {
			//返回结果集
			return firmwareInfoMapper.getVersion(firmwareInfo);
		} catch (Exception e) {
			logger.debug(e.getMessage(),e);
			throw new RpcServiceException(DefaultErrorEnum.DATABASE_ERROR,e.getMessage());
		}
	}
	
	/**
	 * 添加固件版本有则直接获取没有添加
	 * @param firmwareInfo
	 * @return
	 * @throws RpcServiceException
	 */
	public FirmwareInfo addFrimwareInfoByVersion(FirmwareInfo record)throws RpcServiceException
	{
		FirmwareInfo firmwareInfo = null;
		if(StringUtils.isEmpty(record))
		{
			logger.error("FirmwareInfoService::addFrimwareInfoByVersion param err!");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		
		logger.info("FirmwareInfoService::addFrimwareInfoByVersion firmwareInfo=" + record.toString());
		
		if(StringUtils.isEmpty(record.getSoftVersion()))
		{
			return firmwareInfo;
		}
		
		try
		{
			firmwareInfo = firmwareInfoMapper.getFirmwareInfoByVersion(record);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error("FirmwareInfoService::addFrimwareInfoByVersion 数据库读取失败" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		
		if(!StringUtils.isEmpty(firmwareInfo))
		{
			logger.info("FirmwareInfoService::addFrimwareInfoByVersion return firmwareInfo=" + firmwareInfo.toString());
			return firmwareInfo;
		}
		
		try
		{
			firmwareInfoMapper.insertSelective(record);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error("FirmwareInfoService::addFrimwareInfoByVersion 数据库读取失败" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		
		logger.info("FirmwareInfoService::addFrimwareInfoByVersion return record=" + record.toString());
		return record;
	}
	
}
