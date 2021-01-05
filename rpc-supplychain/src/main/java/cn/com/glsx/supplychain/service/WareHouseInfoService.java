package cn.com.glsx.supplychain.service;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONSerializer;

import org.oreframework.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.enums.DefaultErrorEnum;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.framework.core.util.RpcAssert;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.enums.RedisEnum;
import cn.com.glsx.supplychain.mapper.WareHouseInfoMapper;
import cn.com.glsx.supplychain.model.WareHouseInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;


/**
 * 
 * @Title: WareHouseInfoService
 * @Description: 仓库信息服务类
 * @author Leiyj  
 * @date 2018年1月11日 下午2:43:04
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2015
 */
@Service
@Transactional
public class WareHouseInfoService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private WareHouseInfoMapper warehouseInfoMapper;
	
	@Autowired
	private RedisTemplate<String, String> redisClient;
	
	/**
	 * 
	 * @Title: pageWareHouseInfo 
	 * @Description: 查询仓库列表
	 * @param @param requestEntity
	 * @param @return
	 * @param @throws ServiceException 
	 * @return ResponseEntity<WarehouseInfo>
	 * @throws
	 */
	public  Page<WareHouseInfo> pageWareHouseInfo(RpcPagination<WareHouseInfo> pagination,WareHouseInfo wareHouseInfo) throws RpcServiceException {
		//判断条件是否为空
		RpcAssert.assertNotNull(pagination, DefaultErrorEnum.DATA_NULL, "pagination must not be null");		
		logger.info("查询订单列表传入参数："  + JSONSerializer.toJSON(pagination).toString());
		
		try {
			//设置分页数据
			PageHelper.startPage(pagination.getPageNum(), pagination.getPageSize());
			//返回结果集
			return warehouseInfoMapper.listWareHouseInfo(wareHouseInfo);
		} catch (Exception e) {
			logger.debug(e.getMessage(),e);
			throw new RpcServiceException(DefaultErrorEnum.DATABASE_ERROR,e.getMessage());
		}
		
	}
	
	/**
	 * 
	 * @Title: pageWareHouseInfo 
	 * @Description: 查询仓库列表
	 * @param @param requestEntity
	 * @param @return
	 * @param @throws ServiceException 
	 * @return ResponseEntity<WarehouseInfo>
	 * @throws
	 */
	public List<WareHouseInfo> listWareHouseInfo(WareHouseInfo record) throws RpcServiceException
	{
		RpcAssert.assertNotNull(record, DefaultErrorEnum.DATA_NULL, "pagination must not be null");
		try
		{
			return warehouseInfoMapper.getWareHouseInfoList(record);
		}
		catch (Exception e) {
			logger.debug(e.getMessage(),e);
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED,e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: deleteWareHouseInfo 
	 * @Description: 删除仓库数据
	 * @param @param id
	 * @param @return
	 * @param @throws ServiceException 
	 * @return int
	 * @throws
	 */
	public int deleteWareHouseInfo(Integer id) throws RpcServiceException {
		logger.info("删除条件ID为:" + id);
		try {
			return warehouseInfoMapper.deleteWareHouseInfo(id);
		} catch (Exception e) {
			logger.error("删除仓库数据异常" + e.getMessage(),e);
			throw new RpcServiceException(DefaultErrorEnum.DATABASE_ERROR,e.getMessage());
		}
	}

	/**
	 * 
	 * @Title: insert 
	 * @Description: 插入仓库数据
	 * @param @param record
	 * @param @return
	 * @param @throws ServiceException 
	 * @return int
	 * @throws
	 */
	public int insert(WareHouseInfo record) throws RpcServiceException {
		logger.info("仓库信息新增/修改传入参数："  + JSONSerializer.toJSON(record).toString());

		if(StringUtils.isEmpty(record.getId())){
			logger.info("新增仓库信息");
			
			WareHouseInfo warehouseInfo = warehouseInfoMapper.getWareHouseByName(record.getName());
			if(null != warehouseInfo){
				logger.info("仓库名称：" + record.getName() + ",已经存在");
				throw new RpcServiceException(DefaultErrorEnum.DATABASE_ERROR,"仓库名称已经存在");
			}
			
			try {
				record.setCreatedDate(new Date());
				record.setUpdatedDate(new Date());
				return warehouseInfoMapper.insert(record);
			} catch (Exception e) {
				logger.error("新增仓库信息异常," + e.getMessage(),e);
				throw new RpcServiceException(DefaultErrorEnum.DATABASE_ERROR,"新增仓库信息异常");
			}
			
		}else{
			logger.info("修改仓库信息");
			WareHouseInfo warehouseInfo = warehouseInfoMapper.getWareHouseByUpdate(record);
			if(null != warehouseInfo){
				logger.info("仓库名称：" + record.getName() + ",已经存在");
				throw new RpcServiceException(DefaultErrorEnum.DATABASE_ERROR,"仓库名称已经存在");
			}
			return update(record);
		}
	}

	/**
	 * 
	 * @Title: getWarehouseById 
	 * @Description: 根据仓库ID查询仓库信息
	 * @param @param id
	 * @param @return
	 * @param @throws ServiceException 
	 * @return WareHouseInfo
	 * @throws
	 */
	public WareHouseInfo getWarehouseById(Integer id) throws RpcServiceException {
		logger.info("获取仓库信息参数ID为：" + id);
		try {
			return warehouseInfoMapper.getWareHouseById(id);
		} catch (Exception e) {
			logger.error("根据仓库ID查询仓库信息异常," + e.getMessage(),e);
			throw new RpcServiceException(DefaultErrorEnum.DATABASE_ERROR,"根据仓库ID查询仓库信息异常");
		}
	}
	
	/**
	 * 
	 * @Title: getWarehouseByName
	 * @Description: 根据仓库名字查询仓库信息
	 * @param @param id
	 * @param @return
	 * @param @throws ServiceException 
	 * @return WareHouseInfo
	 * @throws
	 */
	public WareHouseInfo getWarehouseByName(String name) throws RpcServiceException {
		logger.info("获取仓库信息参数为：" + name);
		try
		{
			return warehouseInfoMapper.getWareHouseByName(name);
		}catch (Exception e) {
			logger.error("根据仓库名字查询仓库信息异常," + e.getMessage(),e);
			throw new RpcServiceException(DefaultErrorEnum.DATABASE_ERROR,"根据仓库名字查询仓库信息异常");
		}
	}

	/**
	 * 
	 * @Title: update 
	 * @Description: 修改仓库信息
	 * @param @param record
	 * @param @return
	 * @param @throws ServiceException 
	 * @return int
	 * @throws
	 */
	public int update(WareHouseInfo record) throws RpcServiceException {
		logger.info("修改仓库信息参数：" + JSONSerializer.toJSON(record).toString());

		try {
			record.setUpdatedDate(new Date());
			return warehouseInfoMapper.update(record);
		} catch (Exception e) {
			logger.error("修改仓库信息异常," + e.getMessage(),e);
			throw new RpcServiceException(DefaultErrorEnum.DATABASE_ERROR,"修改仓库信息异常");
		}
	}
	
	/**
	 * 
	 * @Title: getWareHouseInfo 
	 * @Description: 获取仓库信息List 
	 * @param @return
	 * @param @throws ServiceException 
	 * @return List<WareHouseInfo>
	 * @throws
	 */
	public List<WareHouseInfo> getWareHouseInfo() throws RpcServiceException {
		logger.info("获取仓库信息List");
		try {
			return warehouseInfoMapper.getWareHouseInfo();
		} catch (Exception e) {
			logger.error("获取仓库信息异常," + e.getMessage(),e);
			throw new RpcServiceException(DefaultErrorEnum.DATABASE_ERROR,"获取仓库信息异常");
		}
	}
	
	/**
	 * 
	 * @Title: queryWareHouseByRedis 
	 * @Description: 通过ID查询对应name值(优选读取redis缓存,不存在则数据库查询)
	 * @param @param key
	 * @param @return 
	 * @return String
	 * @throws
	 */
	public String queryWareHouseByRedis(Integer key){		
		String value = redisClient.opsForValue().get(RedisEnum.REDIS_WAREHOUSE_INFO.getValue() + key.toString());
		if(null == value){
			WareHouseInfo wareHouseInfo = warehouseInfoMapper.getWareHouseById(key);
			value = wareHouseInfo.getName();
			redisClient.opsForValue().set(RedisEnum.REDIS_WAREHOUSE_INFO.getValue() + key.toString(), value);
		}
		return value;
	}
}
