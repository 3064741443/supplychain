package cn.com.glsx.supplychain.manager;

import cn.com.glsx.supplychain.model.*;
import net.sf.json.JSON;
import net.sf.json.JSONObject;

import org.oreframework.redis.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.com.glsx.supplychain.enums.RedisEnum;

@Service
public class SupplyChainRedisService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RedisTemplate<String, String> redisClient;
	
	/**
	 * 
	 * @Title: getRequestVerifyByComsumer 
	 * @Description: 获取请求验证信息
	 * @param verify
	 * @return 
	 * @throws
	 */
	public RequestVerify getRequestVerify(RequestVerify verify)
	{
		String strValue = "";
		RequestVerify requestVerify= null;
		
		if(StringUtils.isEmpty(verify) || StringUtils.isEmpty(verify.getConsumer()) || StringUtils.isEmpty(verify.getVersion()))
		{
			logger.error("SupplyChainRedisService::getRequestVerifyByComsum 参数不能为空");
			return requestVerify;
		}
		
		try
		{
			strValue = redisClient.opsForValue().get(RedisEnum.REDIS_REQUEST_VERIFY_CONSUMER.getValue() + verify.getConsumer() + verify.getVersion());
		}
		catch(Exception e)
		{
			logger.error("SupplyChainRedisService::getRequestVerifyByComsum 缓存获取数据失败" + e.getMessage());
			return 	requestVerify;
		}

		if(StringUtils.isEmpty(strValue))
		{	
			return requestVerify;
		}
		
		try
		{
			JSONObject jsonObject = JSONObject.fromObject(strValue);
			requestVerify = (RequestVerify)JSONObject.toBean(jsonObject,RequestVerify.class);
		}
		catch(Exception e)
		{
			logger.error("SupplyChainRedisService::getRequestVerifyByComsum 缓存获取数据 json转对象失败" + e.getMessage());
		}
		
		return requestVerify;
	}
	
	/**
	 * 
	 * @Title: setRequestVerifyByComsumer 
	 * @Description: 添加请求验证信息到缓存
	 * @param verify
	 * @return 
	 * @throws
	 */
	public Integer setRequestVerifyByComsumer(RequestVerify verify)
	{
		Integer ret = 0;
		if(StringUtils.isEmpty(verify) || StringUtils.isEmpty(verify.getConsumer()) || StringUtils.isEmpty(verify.getVersion()))
		{
			logger.error("SupplyChainRedisService::setRequestVerifyByComsumer 参数不能为空");
			return ret;
		}
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("consumer", verify.getConsumer());
		jsonObject.put("version", verify.getVersion());
		
		String strValue = jsonObject.toString();
		
		try
		{
			redisClient.opsForValue().set(RedisEnum.REDIS_REQUEST_VERIFY_CONSUMER.getValue() + verify.getConsumer() + verify.getVersion(), strValue);
		}
		catch(Exception e)
		{
			logger.error("SupplyChainRedisService::setRequestVerifyByComsumer 缓存设置数据失败" + e.getMessage());
			return 	ret;
		}
		ret = 1;
		
		return ret;
	}
	
	
	
	
	 
	 
	 
	 /**
	 * 
	 * @Title: getDeviceCode 
	 * @Description: 从缓存中获取设备编码信息
	 * @param code
	 * @return 
	 * @throws
	 */
	public DeviceCode getDeviceCode(Integer code)
	{
		String strValue = "";
		DeviceCode deviceCode= null;
		
		if(StringUtils.isEmpty(code))
		{
			logger.error("SupplyChainRedisService::getDeviceCode 参数不能为空");
			return deviceCode;
		}
		
		logger.info("SupplyChainRedisService::getDeviceCode code=" + code);
		
		try
		{
			strValue = redisClient.opsForValue().get(RedisEnum.REDIS_DEVICE_CODE_INFO.getValue() + code);
		}
		catch(Exception e)
		{
			logger.error("SupplyChainRedisService::getDeviceCode 缓存获取数据失败" + e.getMessage());
			return 	deviceCode;
		}
		
		if(StringUtils.isEmpty(strValue))
		{	
			logger.info("SupplyChainRedisService::getDeviceCode return deviceCode=null");
			return deviceCode;
		}
		
		try
		{
			JSONObject jsonObject = JSONObject.fromObject(strValue);
			deviceCode = (DeviceCode)JSONObject.toBean(jsonObject,DeviceCode.class);
		}
		catch(Exception e)
		{
			logger.error("SupplyChainRedisService::getDeviceCode 缓存获取数据 json转对象失败" + e.getMessage());
		}
		
		logger.info("SupplyChainRedisService::getDeviceCode return deviceCode=" + (StringUtils.isEmpty(deviceCode)?"null":deviceCode.toString()));
		return deviceCode;
	}
		
		
	/**
	 * 
	 * @Title: setDeviceCode 
	 * @Description: 从缓存中设置设备编码信息
	 * @param record
	 * @return 
	 * @throws
	 */
	public Integer setDeviceCode(DeviceCode record)
	{
		Integer ret = 0;
		if(StringUtils.isEmpty(record) || StringUtils.isEmpty(record.getDeviceCode()) || StringUtils.isEmpty(record.getDeviceName()))
		{
			logger.error("SupplyChainRedisService::setDeviceCode 参数不能为空");
			return ret;
		}
		
		logger.info("SupplyChainRedisService::setDeviceCode record=" + record.toString());
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", record.getId());
		jsonObject.put("deviceCode", record.getDeviceCode());
		jsonObject.put("deviceName", record.getDeviceName());
		jsonObject.put("merchantId", record.getMerchantId());
		jsonObject.put("typeId", record.getTypeId());
		jsonObject.put("status", record.getStatus());
		
		String strValue = jsonObject.toString();
		
		try
		{
			redisClient.opsForValue().set(RedisEnum.REDIS_DEVICE_CODE_INFO.getValue() + record.getDeviceCode(), strValue);
			ret = 1;
		}
		catch(Exception e)
		{
			logger.error("SupplyChainRedisService::setDeviceCode 缓存设置数据失败" + e.getMessage());
		}
		
		logger.info("SupplyChainRedisService::setDeviceCode return ret=" + ret);
		return ret;
	}
	
	/**
	 * 
	 * @Title: delDeviceCode 
	 * @Description: 从缓存中删除设备编码信息
	 * @param 
	 * @return 
	 * @throws
	 */
	public Long delDeviceCode(Integer code)
	{
		Long ret = 0L;
		if(StringUtils.isEmpty(code))
		{
			logger.error("SupplyChainRedisService::delDeviceCode 参数不能为空");
			return ret;
		}
		 
		logger.info("SupplyChainRedisService::delDeviceCode code=" + code);
		 
		try
		{
			redisClient.delete(RedisEnum.REDIS_DEVICE_CODE_INFO.getValue() + code);
		}
		catch(Exception e)
		{
			logger.error("SupplyChainRedisService::delDeviceCode 缓存设置数据失败" + e.getMessage());
		} 
		logger.info("SupplyChainRedisService::delDeviceCode return ret=" + ret);
		return ret;
	}
	
	/**
	 * 
	 * @Title: getVehicleInfo 
	 * @Description: 从缓存中获取设备编码信息
	 * @param 
	 * @return 
	 * @throws
	 */
	public DeviceVehicleManager getVehicleInfo(Integer id)
	{
		String strValue = "";
		DeviceVehicleManager deviceVehicle= null;
		
		if(StringUtils.isEmpty(id))
		{
			logger.error("SupplyChainRedisService::getVehicleInfo 参数不能为空");
			return deviceVehicle;
		}
		
		logger.info("SupplyChainRedisService::getVehicleInfo id=" + id);
		
		try
		{
			strValue = redisClient.opsForValue().get(RedisEnum.REDIS_DEVICE_VEHICLE_INFO.getValue() + id);
		}
		catch(Exception e)
		{
			logger.error("SupplyChainRedisService::getVehicleInfo 缓存获取数据失败" + e.getMessage());
			return 	deviceVehicle;
		}
		
		if(StringUtils.isEmpty(strValue))
		{	
			logger.info("SupplyChainRedisService::getVehicleInfo return deviceVehicle=null");
			return deviceVehicle;
		}
		
		try
		{
			JSONObject jsonObject = JSONObject.fromObject(strValue);
			deviceVehicle = (DeviceVehicleManager)JSONObject.toBean(jsonObject,DeviceVehicleManager.class);
		}
		catch(Exception e)
		{
			logger.error("SupplyChainRedisService::getVehicleInfo 缓存获取数据 json转对象失败" + e.getMessage());
		}
		
		logger.info("SupplyChainRedisService::getVehicleInfo return deviceVehicle=" + (StringUtils.isEmpty(deviceVehicle)?"null":deviceVehicle.toString()));
		return deviceVehicle;
	}
		
		
	/**
	 * 
	 * @Title: setVehicleInfo 
	 * @Description: 从缓存中设置设备编码信息
	 * @param 
	 * @return 
	 * @throws
	 */
	public Integer setVehicleInfo(DeviceVehicleManager record)
	{
		Integer ret = 0;
		if(StringUtils.isEmpty(record) || StringUtils.isEmpty(record.getId()) || StringUtils.isEmpty(record.getVehicleFlag()))
		{
			logger.error("SupplyChainRedisService::setVehicleInfo 参数不能为空");
			return ret;
		}
		
		logger.info("SupplyChainRedisService::setVehicleInfo record=" + record.toString());
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", record.getId());
		jsonObject.put("vehicleFlag", record.getVehicleFlag());
		jsonObject.put("flagType", record.getFlagType());
		jsonObject.put("companyId", record.getCompanyId());
		jsonObject.put("deletedFlag", record.getDeletedFlag());
		
		String strValue = jsonObject.toString();
		
		try
		{
			redisClient.opsForValue().set(RedisEnum.REDIS_DEVICE_VEHICLE_INFO.getValue() + record.getId(), strValue);
			ret = 1;
		}
		catch(Exception e)
		{
			logger.error("SupplyChainRedisService::setVehicleInfo 缓存设置数据失败" + e.getMessage());
		}
		
		logger.info("SupplyChainRedisService::setVehicleInfo return ret=" + ret);
		return ret;
	}
	
	/**
	 * 
	 * @Title: delVehicleInfo 
	 * @Description: 从缓存中删除设备编码信息
	 * @param 
	 * @return 
	 * @throws
	 */
	public Long delVehicleInfo(Integer id)
	{
		Long ret = 0L;
		if(StringUtils.isEmpty(id))
		{
			logger.error("SupplyChainRedisService::delVehicleInfo 参数不能为空");
			return ret;
		}
		 
		logger.info("SupplyChainRedisService::delVehicleInfo id=" + id);
		 
		try
		{
			redisClient.delete(RedisEnum.REDIS_DEVICE_VEHICLE_INFO.getValue() + id);
		}
		catch(Exception e)
		{
			logger.error("SupplyChainRedisService::delVehicleInfo 缓存设置数据失败" + e.getMessage());
		} 
		logger.info("SupplyChainRedisService::delVehicleInfo return ret=" + ret);
		return ret;
	}
	
	/**
	 * 
	 * @Title: getDevUserInfo 
	 * @Description: 从缓存中获取设备用户信息
	 * @param 
	 * @return 
	 * @throws
	 */
	public DeviceUserManager getDevUserInfo(Integer id)
	{
		String strValue = "";
		DeviceUserManager deviceUser= null;
		
		if(StringUtils.isEmpty(id))
		{
			logger.error("SupplyChainRedisService::getDevUserInfo 参数不能为空");
			return deviceUser;
		}
		
		logger.info("SupplyChainRedisService::getDevUserInfo id=" + id);
		
		try
		{
			strValue = redisClient.opsForValue().get(RedisEnum.REDIS_DEVICE_USER_INFO.getValue() + id);
		}
		catch(Exception e)
		{
			logger.error("SupplyChainRedisService::getDevUserInfo 缓存获取数据失败" + e.getMessage());
			return 	deviceUser;
		}
		
		if(StringUtils.isEmpty(strValue))
		{	
			logger.info("SupplyChainRedisService::getDevUserInfo return deviceUser=null");
			return deviceUser;
		}
		
		try
		{
			JSONObject jsonObject = JSONObject.fromObject(strValue);
			deviceUser = (DeviceUserManager)JSONObject.toBean(jsonObject,DeviceUserManager.class);
		}
		catch(Exception e)
		{
			logger.error("SupplyChainRedisService::getDevUserInfo 缓存获取数据 json转对象失败" + e.getMessage());
		}
		
		logger.info("SupplyChainRedisService::getDevUserInfo return deviceUser=" + (StringUtils.isEmpty(deviceUser)?"null":deviceUser.toString()));
		return deviceUser;
	}
		
		
	/**
	 * 
	 * @Title: setDevUserInfo 
	 * @Description: 从缓存中设置设备用户信息
	 * @param 
	 * @return 
	 * @throws
	 */
	public Integer setDevUserInfo(DeviceUserManager record)
	{
		Integer ret = 0;
		if(StringUtils.isEmpty(record) || StringUtils.isEmpty(record.getId()) || StringUtils.isEmpty(record.getUserFlag()))
		{
			logger.error("SupplyChainRedisService::setDevUserInfo 参数不能为空");
			return ret;
		}
		
		logger.info("SupplyChainRedisService::setDevUserInfo record=" + record.toString());
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", record.getId());
		jsonObject.put("userFlag", record.getUserFlag());
		jsonObject.put("flagType", record.getFlagType());
		jsonObject.put("companyId", record.getCompanyId());
		jsonObject.put("deletedFlag", record.getDeletedFlag());
		
		String strValue = jsonObject.toString();
		
		try
		{
			redisClient.opsForValue().set(RedisEnum.REDIS_DEVICE_USER_INFO.getValue() + record.getId(), strValue);
			ret = 1;
		}
		catch(Exception e)
		{
			logger.error("SupplyChainRedisService::setDevUserInfo 缓存设置数据失败" + e.getMessage());
		}
		
		logger.info("SupplyChainRedisService::setDevUserInfo return ret=" + ret);
		return ret;
	}
	
	/**
	 * 
	 * @Title: delDevUserInfo 
	 * @Description: 从缓存中删除设备用户信息
	 * @param 
	 * @return 
	 * @throws
	 */
	public Long delDevUserInfo(Integer id)
	{
		Long ret = 0L;
		if(StringUtils.isEmpty(id))
		{
			logger.error("SupplyChainRedisService::delDevUserInfo 参数不能为空");
			return ret;
		}
		 
		logger.info("SupplyChainRedisService::delDevUserInfo id=" + id);
		 
		try
		{
			redisClient.delete(RedisEnum.REDIS_DEVICE_USER_INFO.getValue() + id);
		}
		catch(Exception e)
		{
			logger.error("SupplyChainRedisService::delDevUserInfo 缓存设置数据失败" + e.getMessage());
		} 
		logger.info("SupplyChainRedisService::delDevUserInfo return ret=" + ret);
		return ret;
	}
	
	/**
	 * 
	 * @Title: getDevCardInfo 
	 * @Description: 从缓存中获取设备卡信息
	 * @param 
	 * @return 
	 * @throws
	 */
	public DeviceCardManager getDevCardInfo(Integer id)
	{
		String strValue = "";
		DeviceCardManager deviceCard= null;
		
		if(StringUtils.isEmpty(id))
		{
			logger.error("SupplyChainRedisService::getDevCardInfo 参数不能为空");
			return deviceCard;
		}
		
		logger.info("SupplyChainRedisService::getDevCardInfo id=" + id);
		
		try
		{
			strValue = redisClient.opsForValue().get(RedisEnum.REDIS_DEVICE_CARD_INFO.getValue() + id);
		}
		catch(Exception e)
		{
			logger.error("SupplyChainRedisService::getDevCardInfo 缓存获取数据失败" + e.getMessage());
			return 	deviceCard;
		}
		
		if(StringUtils.isEmpty(strValue))
		{	
			logger.info("SupplyChainRedisService::getDevCardInfo return deviceUser=null");
			return deviceCard;
		}
		
		try
		{
			JSONObject jsonObject = JSONObject.fromObject(strValue);
			deviceCard = (DeviceCardManager)JSONObject.toBean(jsonObject,DeviceCardManager.class);
		}
		catch(Exception e)
		{
			logger.error("SupplyChainRedisService::getDevCardInfo 缓存获取数据 json转对象失败" + e.getMessage());
		}
		
		logger.info("SupplyChainRedisService::getDevCardInfo return deviceUser=" + (StringUtils.isEmpty(deviceCard)?"null":deviceCard.toString()));
		return deviceCard;
	}
		
		
	/**
	 * 
	 * @Title: setDevCardInfo 
	 * @Description: 从缓存中设置设备用户信息
	 * @param 
	 * @return 
	 * @throws
	 */
	public Integer setDevCardInfo(DeviceCardManager record)
	{
		Integer ret = 0;
		if(StringUtils.isEmpty(record) || StringUtils.isEmpty(record.getId()) || StringUtils.isEmpty(record.getImsi()))
		{
			logger.error("SupplyChainRedisService::setDevCardInfo 参数不能为空");
			return ret;
		}
		
		logger.info("SupplyChainRedisService::setDevCardInfo record=" + record.toString());
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", record.getId());
		jsonObject.put("iccid", record.getIccid());
		jsonObject.put("imsi", record.getImsi());
		jsonObject.put("companyId", record.getCompanyId());
		jsonObject.put("deletedFlag", record.getDeletedFlag());
		
		String strValue = jsonObject.toString();
		
		try
		{
			redisClient.opsForValue().set(RedisEnum.REDIS_DEVICE_CARD_INFO.getValue() + record.getId(), strValue);
			ret = 1;
		}
		catch(Exception e)
		{
			logger.error("SupplyChainRedisService::setDevCardInfo 缓存设置数据失败" + e.getMessage());
		}
		
		logger.info("SupplyChainRedisService::setDevCardInfo return ret=" + ret);
		return ret;
	}
	
	/**
	 * 
	 * @Title: delDevCardInfo 
	 * @Description: 从缓存中删除设备用户信息
	 * @param 
	 * @return 
	 * @throws
	 */
	public Long delDevCardInfo(Integer id)
	{
		Long ret = 0L;
		if(StringUtils.isEmpty(id))
		{
			logger.error("SupplyChainRedisService::delDevCardInfo 参数不能为空");
			return ret;
		}
		 
		logger.info("SupplyChainRedisService::delDevCardInfo id=" + id);
		 
		try
		{
			redisClient.delete(RedisEnum.REDIS_DEVICE_CARD_INFO.getValue() + id);
		}
		catch(Exception e)
		{
			logger.error("SupplyChainRedisService::delDevCardInfo 缓存设置数据失败" + e.getMessage());
		} 
		logger.info("SupplyChainRedisService::delDevCardInfo return ret=" + ret);
		return ret;
	}
	
	
	/**
	 * 
	 * @Title: getDevSoftVersion(firmware_info表)
	 * @Description: 从缓存中获取设备软件版本信息
	 * @param 
	 * @return 
	 * @throws
	 */
	public String getDevSoftVersion(Integer id)
	{
		String softVersion = "";
		
		if(StringUtils.isEmpty(id))
		{
			logger.error("SupplyChainRedisService::getDevSoftVersion 参数不能为空");
			return softVersion;
		}
		
		logger.info("SupplyChainRedisService::getDevSoftVersion id=" + id);
		
		try
		{
			softVersion = redisClient.opsForValue().get(RedisEnum.REDIS_DEVICE_SOFT_VERSION.getValue() + id);
		}
		catch(Exception e)
		{
			logger.error("SupplyChainRedisService::getDevSoftVersion 缓存获取数据失败" + e.getMessage());	
		}
		return softVersion;
	}
		
		
	/**
	 * 
	 * @Title: setDevSoftVersion 
	 * @Description: 从缓存中设置设备软件版本信息
	 * @param 
	 * @return 
	 * @throws
	 */
	public Integer setDevSoftVersion(Integer id,String softVersion)
	{
		Integer ret = 0;
		if(StringUtils.isEmpty(id) || StringUtils.isEmpty(softVersion))
		{
			logger.error("SupplyChainRedisService::setDevSoftVersion 参数不能为空");
			return ret;
		}
		
		logger.info("SupplyChainRedisService::setDevSoftVersion id=" + id + " softversion=" + softVersion);
		
		try
		{
			redisClient.opsForValue().set(RedisEnum.REDIS_DEVICE_SOFT_VERSION.getValue() + id, softVersion);
			ret = 1;
		}
		catch(Exception e)
		{
			logger.error("SupplyChainRedisService::setDevSoftVersion 缓存设置数据失败" + e.getMessage());
		}
		
		logger.info("SupplyChainRedisService::setDevSoftVersion return ret=" + ret);
		return ret;
	}
	
	/**
	 * 
	 * @Title: delDevSoftVersion 
	 * @Description: 从缓存中删除设备用户信息
	 * @param 
	 * @return 
	 * @throws
	 */
	public Long delDevSoftVersion(Integer id)
	{
		Long ret = 0L;
		if(StringUtils.isEmpty(id))
		{
			logger.error("SupplyChainRedisService::delDevSoftVersion 参数不能为空");
			return ret;
		}
		 
		logger.info("SupplyChainRedisService::delDevSoftVersion id=" + id);
		 
		try
		{
			redisClient.delete(RedisEnum.REDIS_DEVICE_SOFT_VERSION.getValue() + id);
		}
		catch(Exception e)
		{
			logger.error("SupplyChainRedisService::delDevSoftVersion 缓存设置数据失败" + e.getMessage());
		} 
		logger.info("SupplyChainRedisService::delDevSoftVersion return ret=" + ret);
		return ret;
	}
	
	/**
	 * 
	 * @Title: getDevCompany
	 * @Description: 从缓存中获取合作公司信息
	 * @param 
	 * @return 
	 * @throws
	 */
	public String getDevCompany(Integer id)
	{
		String companyName = "";
		
		if(StringUtils.isEmpty(id))
		{
			logger.error("SupplyChainRedisService::getDevCompany 参数不能为空");
			return companyName;
		}
		
		logger.info("SupplyChainRedisService::getDevCompany id=" + id);
		
		try
		{
			companyName = redisClient.opsForValue().get(RedisEnum.REDIS_DEVICE_COMPANY_INFO.getValue() + id);
		}
		catch(Exception e)
		{
			logger.error("SupplyChainRedisService::getDevCompany 缓存获取数据失败" + e.getMessage());	
		}
		return companyName;
	}
		
		
	/**
	 * 
	 * @Title: setDevCompany 
	 * @Description: 从缓存中设置合作公司信息
	 * @param 
	 * @return 
	 * @throws
	 */
	public Integer setDevCompany(Integer id,String companyName)
	{
		Integer ret = 0;
		if(StringUtils.isEmpty(id) || StringUtils.isEmpty(companyName))
		{
			logger.error("SupplyChainRedisService::setDevCompany 参数不能为空");
			return ret;
		}
		
		logger.info("SupplyChainRedisService::setDevCompany id=" + id + " companyName=" + companyName);
		
		try
		{
			redisClient.opsForValue().set(RedisEnum.REDIS_DEVICE_COMPANY_INFO.getValue() + id, companyName);
			ret = 1;
		}
		catch(Exception e)
		{
			logger.error("SupplyChainRedisService::setDevCompany 缓存设置数据失败" + e.getMessage());
		}
		
		logger.info("SupplyChainRedisService::setDevCompany return ret=" + ret);
		return ret;
	}
	
	/**
	 * 
	 * @Title: delDevCompany 
	 * @Description: 从缓存中删除设备用户信息
	 * @param 
	 * @return 
	 * @throws
	 */
	public Long delDevCompany(Integer id)
	{
		Long ret = 0L;
		if(StringUtils.isEmpty(id))
		{
			logger.error("SupplyChainRedisService::delDevCompany 参数不能为空");
			return ret;
		}
		 
		logger.info("SupplyChainRedisService::delDevCompany id=" + id);
		 
		try
		{
			redisClient.delete(RedisEnum.REDIS_DEVICE_COMPANY_INFO.getValue() + id);
		}
		catch(Exception e)
		{
			logger.error("SupplyChainRedisService::delDevCompany 缓存设置数据失败" + e.getMessage());
		} 
		logger.info("SupplyChainRedisService::delDevCompany return ret=" + ret);
		return ret;
	}


	/**
	 *
	 * @Title: getDeviceFileBySn
	 * @Description: 获取设备信息
	 * @param sn
	 * @return
	 * @throws
	 */
	public DeviceFile getDeviceFileBySn(String sn)
	{
		String strSn = "";
		DeviceFile deviceFile= null;
		if(StringUtils.isEmpty(sn))
		{
			logger.error("SupplyChainRedisService::getDeviceFileBySn 参数不能为空");
			return deviceFile;
		}
		try
		{
			strSn = redisClient.opsForValue().get(RedisEnum.REDIS_DEVICE_FILE.getValue() + sn);
		}
		catch(Exception e)
		{
			logger.error("SupplyChainRedisService::getDeviceFileBySn 缓存获取数据失败" + e.getMessage());
			return 	deviceFile;
		}
		if(StringUtils.isEmpty(strSn))
		{
			return deviceFile;
		}
		try
		{
			JSONObject jsonObject = JSONObject.fromObject(strSn);
			deviceFile = (DeviceFile)JSONObject.toBean(jsonObject,DeviceFile.class);
		}
		catch(Exception e)
		{
			logger.error("SupplyChainRedisService::getDeviceFileBySn 缓存获取数据 json转对象失败" + e.getMessage());
		}
		return deviceFile;
	}

	/**
	 *
	 * @Title: setDeviceFileBySn
	 * @Description: 添加设备基础信息到缓存
	 * @param deviceFile
	 * @return
	 * @throws
	 */
	public Integer setDeviceFileBySn(DeviceFile deviceFile)
	{
		Integer ret = 0;
		if(StringUtils.isEmpty(deviceFile))
		{
			logger.error("SupplyChainRedisService::setDeviceFileBySn 参数不能为空");
			return ret;
		}
		JSONObject jsonObject = JSONObject.fromObject(deviceFile);
		String strValue = jsonObject.toString();
		try
		{
			redisClient.opsForValue().set(RedisEnum.REDIS_DEVICE_FILE.getValue() + deviceFile.getSn(), strValue);
		}
		catch(Exception e)
		{
			logger.error("SupplyChainRedisService::setDeviceFileBySn 缓存设置数据失败" + e.getMessage());
			return 	ret;
		}
		ret = 1;
		return ret;
	}

	/**
	 *
	 * @Title: delDeviceFile
	 * @Description: 从缓存中删除设备基础信息到缓存
	 * @param sn
	 * @return
	 * @throws
	 */
	public Long delDeviceFile(String sn)
	{
		Long ret = 0L;
		if(StringUtils.isEmpty(sn))
		{
			logger.error("SupplyChainRedisService::delDeviceFile 参数不能为空");
			return ret;
		}

		logger.info("SupplyChainRedisService::delDeviceFile id=" + sn);

		try
		{
			redisClient.delete(RedisEnum.REDIS_DEVICE_FILE.getValue() + sn);
		}
		catch(Exception e)
		{
			logger.error("SupplyChainRedisService::delDeviceFile 缓存设置数据失败" + e.getMessage());
		}
		logger.info("SupplyChainRedisService::delDeviceFile return ret=" + ret);
		return ret;
	}
	
	
		
}
