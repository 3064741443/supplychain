package cn.com.glsx.supplychain.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.glsx.oms.bigdata.service.fb.api.IMaterialDubboService;
import com.glsx.oms.bigdata.service.fb.model.Material;
import com.glsx.oms.fcservice.api.core.FlowResponse;
import com.glsx.oms.fcservice.api.entity.Flowcard;
import com.glsx.oms.fcservice.api.manager.OpsMgrManager;
import com.glsx.oms.fcservice.api.request.FlowCardRequest;
import com.glsx.platform.goods.common.entity.ServicePackage;
import com.glsx.platform.goods.common.service.ServicePackageService;

import net.sf.json.JSONObject;

import org.oreframework.redis.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.merchant.facade.model.response.MerchantFacadeResponse;
import cn.com.glsx.merchant.facade.remote.MerchantFacadeRemote;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.enums.RedisEnum;
import cn.com.glsx.supplychain.model.DeviceCode;
import cn.com.glsx.supplychain.model.SupplyChainMerchantInfo;

import com.glsx.biz.common.base.entity.Configuration;
import com.glsx.biz.common.base.service.ConfigurationService;
import com.glsx.biz.common.user.entity.DeviceCategory;
import com.glsx.biz.common.user.service.DeviceCategoryService;
import com.glsx.biz.common.user.service.PackagesService;
import com.glsx.biz.merchant.common.entity.Merchant;
import com.glsx.biz.merchant.service.MerchantService;
import com.glsx.biz.user.common.entity.PhysicalDevice;
import com.glsx.biz.user.service.PhysicalDeviceService;
import com.glsx.cloudframework.core.datastructure.page.Pagination;
import com.glsx.cloudframework.exception.ServiceException;

@Service
public class SupplyChainExternalService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DeviceCategoryService deviceCategoryService;  //老运营平台接口后面改用供应链提供的接口
    @Autowired
    private MerchantService merchantService;  //老运营平台接口后面改用商户新平台接口
    @Autowired
    private OpsMgrManager opsMgrManager;
    @Autowired
    private ServicePackageService servicePackageService; //新商品服务接口
    @Autowired
    private PhysicalDeviceService physicalDeviceService;
    @Autowired
	private RedisTemplate<String, String> redisClient;
    @Autowired
    private ConfigurationService configureService;  
    @Autowired
	private IMaterialDubboService materialService;
    @Autowired
    private MerchantFacadeRemote merchantFacadeRemote;


    //获取设备类型列表
    /*
    @SuppressWarnings("rawtypes")
    public List listDeviceTypeInfo(Integer targetPage, Integer pageSize) throws RpcServiceException {

        targetPage = (targetPage == null) ? new Integer(1) : targetPage;
        pageSize = (pageSize == null) ? new Integer(40) : pageSize;
        logger.info("获取设备类型列表输入参数: targetPage:" + targetPage + "pageSize:" + pageSize);
        Pagination pageVo = null;

        try {
            pageVo = deviceCategoryService.getDeviceCategories(targetPage, pageSize);
            if (pageVo == null) {
                return null;
            }
            logger.info("获取设备类型列:列表长度:" + pageVo.getList().size());
            return pageVo.getList();

        } catch (ServiceException e) {
            logger.error("获取设备类型列:调用外部服务失败 错误信息:" + e.getMessage());
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_EXTERNAL_INTERFACE);
        }
    }*/

    //根据硬件设备类型获取相应套餐
    @SuppressWarnings({"rawtypes", "deprecation"})
    public List listPackageInfoByDeviceCode(Integer code) throws RpcServiceException {
    	
        if (code == null) {
            logger.error("根据硬件设备类型获取相应套餐:id不能为空");
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }

        logger.info("根据硬件设备类型获取相应套餐输入参数:id:" + code);
        try {
        	List<ServicePackage> packageList = servicePackageService.findActiveUpShelvePackageByDeviceId(code);
        	//logger.info("根据硬件设备类型获取相应套餐返回 : packageList.size()=" + (StringUtils.isEmpty(packageList)?0:packageList.size()));
        	if(!StringUtils.isEmpty(packageList))
        	{
        		logger.info("根据硬件设备类型获取相应套餐返回 :packageList:{}",packageList);
        	}
        	return packageList;
         //   return ((PackagesService) packagesService).findPackgeByDeviceId(code);
        } catch (ServiceException e) {
            logger.error("根据硬件设备类型获取相应套餐:调用外部服务失败 错误信息:" + e.getMessage());
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_EXTERNAL_INTERFACE);
        }
    }

    //根据套餐ID获取套餐名称
    public String findPackageNameByPackageId(Integer id) throws RpcServiceException {

        if (StringUtils.isEmpty(id)) {

            logger.error("根据套餐ID获取套餐名称:id不能为空");
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }

        String strPackageName = redisClient.opsForValue().get(RedisEnum.REDIS_DEVICE_PACKAGE_INFO.getValue() + id.toString());

        if (!StringUtils.isEmpty(strPackageName)) {

            return strPackageName;
        }

        try {
        
            ServicePackage pkg = servicePackageService.findById(id, true);

            if (null == pkg) {
                logger.error("根据套餐ID获取套餐名称:未找到该id对应套餐");
                throw new RpcServiceException(ErrorCodeEnum.ERRCODE_PACKAGE_NOT_EXIST);
            } else {
                strPackageName = pkg.getName();
            }

        } catch (ServiceException e) {
            logger.error("根据套餐ID获取套餐名称:调用外部服务失败 错误信息:" + e.getMessage());
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_EXTERNAL_INTERFACE);
        }

        if (!StringUtils.isEmpty(strPackageName)) {

            redisClient.opsForValue().set(RedisEnum.REDIS_DEVICE_PACKAGE_INFO.getValue() + id.toString(), strPackageName);
        }

        return strPackageName;
    }

    //根据套餐ID获取套餐对应的设备编码
    /*
    public Boolean isRightPackageByDeviceCode(Integer deviceCode, Integer packageId) throws RpcServiceException {

        if (StringUtils.isEmpty(deviceCode) || StringUtils.isEmpty(packageId)) {

            logger.error("根据套餐ID获取套餐对应的设备编码:id不能为空");
            return false;
        }

        try {
            ServicePackage pkg = servicePackageService.findById(packageId, true);
            if(StringUtils.isEmpty(pkg))
            {
            	logger.error("根据套餐ID获取套餐对应的设备编码：套餐查询不到 ");
            	return false;
            }
            if(StringUtils.isEmpty(pkg.getAssociateDevices()))
            {
            	logger.error("根据套餐ID获取套餐对应的设备编码：无数据");
            	return false;
            }
            if(pkg.getAssociateDevices().size() < 1)
            {
            	logger.error("根据套餐ID获取套餐对应的设备编码：无数据");
            	return false;
            }

            for (int i = 0; i < pkg.getAssociateDevices().size(); i++) {
                if(deviceCode.equals(pkg.getAssociateDevices().get(i).getDeviceId())){
                    logger.error("根据套餐ID获取套餐对应的设备编码：成功");
                    return true;
                }else if (pkg.getAssociateDevices().size() == i+1){
                    logger.error("根据套餐ID获取套餐对应的设备编码：无匹配字段");
                    return false;
                }
            }

            //判断商品是否激活
            //if(pkg.getStatus() == 0){

            //	return false;
            //}

        } catch (ServiceException e) {

            logger.error("根据套餐ID获取套餐对应的设备编码:调用外部服务失败 错误信息:" + e.getMessage());
            return false;
        }

        return true;
    }
	*/

    //查询商户列表
    @SuppressWarnings("rawtypes")
    public List listMerchantInfo(Integer targetPage, Integer pageSize) throws RpcServiceException {
        targetPage = (targetPage == null) ? new Integer(1) : targetPage;
        pageSize = (pageSize == null) ? new Integer(40) : pageSize;

        logger.info("查询商户列表: targetPage:" + targetPage + "pageSize:" + pageSize);
        Pagination pageVo = null;
        try {
            pageVo = merchantService.find(targetPage, pageSize);

            if (pageVo == null) {
                return null;
            }
            logger.info("查询商户列表:列表长度:" + pageVo.getList().size());
            return pageVo.getList();

        } catch (ServiceException e) {
            logger.error("查询商户列表:调用外部服务失败 错误信息:" + e.getMessage());
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_EXTERNAL_INTERFACE);
        }
    }

    @SuppressWarnings("rawtypes")
    public List listMerchantInfo(HashMap<String, Object> condition, Integer targetPage, Integer pageSize) throws RpcServiceException {

        logger.info("查询商户列表: targetPage:" + targetPage + "pageSize:" + pageSize);
        Pagination pageVo = null;

        try {
            pageVo = merchantService.find(condition, targetPage, pageSize);
           
            if (pageVo == null) {
                return null;
            }
            if(pageVo.getList() == null){
            	return null;
            }
            logger.info("查询商户列表:列表长度:" + pageVo.getList().size());
            return pageVo.getList();

        } catch (ServiceException e) {
            logger.error("查询商户列表:调用外部服务失败 错误信息:" + e.getMessage());
         //   return null;
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_EXTERNAL_INTERFACE);
        }
    }
    
    

    //根据商户id获取商户信息
    public SupplyChainMerchantInfo findMerchantInfoByMerchantId(Integer id) throws RpcServiceException {

        if (StringUtils.isEmpty(id)) {
            logger.error("根据商户id获取商户信息:id不能为空");
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }

        String strMerchantName = redisClient.opsForValue().get(RedisEnum.REDIS_DEVICE_MERCHANT_NAME.getValue() + id.toString());
        String strMerchantType = redisClient.opsForValue().get(RedisEnum.REDIS_DEVICE_MERCHANT_TYPE.getValue() + id.toString());

        if (!StringUtils.isEmpty(strMerchantName) && !StringUtils.isEmpty(strMerchantType)) {
            SupplyChainMerchantInfo oInfo = new SupplyChainMerchantInfo();
            oInfo.setId(id);
            oInfo.setType(strMerchantType);
            oInfo.setName(strMerchantName);
            return oInfo;
        }

        Merchant merchant = null;

        try {
            merchant = merchantService.get(id);
            if (StringUtils.isEmpty(merchant)) {
                logger.error("根据商户id获取商户信息:根据id获取商户为空 id=" + id);
                throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_DEVICE_MERCHANT_NO);
            }
        } catch (Exception e) {
            logger.error("根据商户id获取商户信息:调用外部服务失败 错误信息:" + e.getMessage());
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_EXTERNAL_INTERFACE);
        }

        Configuration cfg = null;

        try {
            if (StringUtils.isEmpty(merchant.getMerchantType())) {
                logger.error("根据商户id获取商户信息:根据商户类型获取配置 商户类型为空 merchant.getMerchantType()" + merchant.getMerchantType());
                throw new RpcServiceException(ErrorCodeEnum.ERRCODE_EXTERNAL_INTERFACE);
            }
            cfg = configureService.getConfigById(merchant.getMerchantType());
            if (StringUtils.isEmpty(cfg)) {
                logger.error("根据商户id获取商户信息:根据商户类型获取配置 配置为空");
                throw new RpcServiceException(ErrorCodeEnum.ERRCODE_EXTERNAL_INTERFACE);
            }
        } catch (Exception e) {
            logger.error("根据商户id获取商户信息:调用外部服务失败 错误信息:" + e.getMessage());
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_EXTERNAL_INTERFACE);
        }

        SupplyChainMerchantInfo oInfo = new SupplyChainMerchantInfo();

        oInfo.setId(id);
        oInfo.setType(cfg.getConfValue());
        oInfo.setName(merchant.getMerchantName());

        redisClient.opsForValue().set(RedisEnum.REDIS_DEVICE_MERCHANT_NAME.getValue() + id.toString(), merchant.getMerchantName());
        redisClient.opsForValue().set(RedisEnum.REDIS_DEVICE_MERCHANT_TYPE.getValue() + id.toString(), cfg.getConfValue());

        return oInfo;
    }

    /**
     * @param @param  request
     * @param @return
     * @param @throws RpcServiceException
     * @return FlowResponse<FlowCard>
     * @throws
     * @Title: getFlowCardByIccidOrImsi
     * @Description: 获取流量卡信息
     */
    public FlowResponse<Flowcard> getFlowCardByIccidOrImsi(FlowCardRequest request) throws RpcServiceException {
        try {
            if (null == request) {
                logger.error("请求参数对象不能为空:id不能为空");
                throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
            }
                 
           // return flowCardService.getFlowCard(request);
            return opsMgrManager.getCardIccid(request);
        } catch (Exception e) {
            logger.error("请求外部接口异常【FlowCardService--->getFlowCardByIccidOrImsi】：" + e.getMessage(), e);
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_EXTERNAL_INTERFACE, e.getMessage());
        }
    }

    /**
     * @param @param  deviceId
     * @param @param  physicalDeviceList
     * @param @return
     * @param @throws RpcServiceException
     * @return Map
     * @throws
     * @Title: batchAddPhysicalDevice
     * @Description: 同步设备信息
     */
    @SuppressWarnings("rawtypes")
    public Map batchAddPhysicalDevice(Integer deviceId, List<PhysicalDevice> physicalDeviceList) throws RpcServiceException {
        try {
            //判断设备ID不为空
            if (null == deviceId) {
                logger.error("请求设备ID不能为空");
                throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
            }

            //判断同步数据不为空
            if (null == physicalDeviceList) {
                logger.error("同步设备信息physicalDeviceList不能为空");
                throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
            }

            return physicalDeviceService.batchAddPhysicalDevice(deviceId, physicalDeviceList);
        } catch (ServiceException e) {
            logger.error("请求外部接口异常【PhysicalDeviceService--->batchAddPhysicalDevice】：" + e.getMessage(), e);
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_EXTERNAL_INTERFACE, e.getMessage());
        }
    }

    /**
     * @param @param deviceCode
     * @return void
     * @throws
     * @Title: saveDeviceCategory
     * @Description: 保存设备类型到老平台
     */
    public DeviceCategory saveDeviceCategory(DeviceCode deviceCode) {

        if (deviceCode == null) {
            logger.error("数据为空");
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        short status = 2;
        DeviceCategory deviceCategory = new DeviceCategory();
        deviceCategory.setDeviceId(deviceCode.getDeviceCode());
        deviceCategory.setDeviceName(deviceCode.getDeviceName());
        deviceCategory.setStatus(status);
        deviceCategory.setTypeId(deviceCode.getTypeId());
        deviceCategory.setMerchantId(deviceCode.getMerchantId());
        deviceCategory.setMerchantName(deviceCode.getMerchantName());
        logger.error("==========同步老平台数据参数：" + JSONObject.fromObject(deviceCategory));
        try {
            DeviceCategory result = deviceCategoryService.saveDeviceCategory(deviceCategory);
            return result;
        } catch (ServiceException e) {
            logger.error("请求外部接口异常【deviceCategoryService--->saveDeviceCategory】：" + e.getMessage(), e);
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_EXTERNAL_INTERFACE, e.getMessage());
        }
    }
    
    public Material getMaterialInfoByMaterialCode(String materialCode)
	{
    	Material material = null;
    	try
    	{	
    		Material condition = new Material();
    		condition.setMaterialCode(materialCode);
    		List<Material> materialList = materialService.list(condition);
    		if (materialList != null && materialList.size() > 0)
    		{
    			material = materialList.get(0);
    		}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	return material;
	}
    
    public MerchantFacadeResponse getMerchantFacadeRemote(String merchantCode){
    	
    	try{
    		RpcResponse<MerchantFacadeResponse> response = merchantFacadeRemote.getMerchantFacadeRemote(Integer.valueOf(merchantCode));
    		if(null == response){
    			return null;
    		}
    		return response.getResult();
    	}catch(RpcServiceException e){
    		logger.error(e.getMessage(),e);
    		return null;
    	}
    }
}
