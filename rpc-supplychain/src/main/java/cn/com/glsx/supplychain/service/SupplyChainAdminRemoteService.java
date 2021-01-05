package cn.com.glsx.supplychain.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.com.glsx.supplychain.enums.MerchantOrderStatusEnum;
import cn.com.glsx.supplychain.mapper.bs.MerchantOrderDetailMapper;
import cn.com.glsx.supplychain.mapper.bs.MerchantOrderMapper;
import cn.com.glsx.supplychain.model.am.EcMerchantOrder;
import cn.com.glsx.supplychain.model.bs.MerchantOrder;
import cn.com.glsx.supplychain.model.bs.MerchantOrderDetail;
import cn.com.glsx.supplychain.service.am.MaterialInfoService;
import cn.com.glsx.supplychain.service.bs.EcMerchantOrderService;
import cn.com.glsx.supplychain.service.bs.MerchantOrderDetailService;
import cn.com.glsx.supplychain.service.bs.MerchantOrderService;
import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

import org.oreframework.util.encrypt.Md5Encrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.dubbo.rpc.RpcException;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.glsx.biz.user.common.entity.PhysicalDevice;
import com.glsx.biz.user.common.vo.ResponseResult;
import com.glsx.oms.fcservice.api.core.FlowResponse;
import com.glsx.oms.fcservice.api.entity.Flowcard;
import com.glsx.oms.fcservice.api.request.FlowCardRequest;

import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.enums.DefaultErrorEnum;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.framework.core.util.RpcAssert;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.enums.OrderStatusEnum;
import cn.com.glsx.supplychain.manager.SupplyChainExternalService;
import cn.com.glsx.supplychain.mapper.AttribInfoMapper;
import cn.com.glsx.supplychain.mapper.AttribManaMapper;
import cn.com.glsx.supplychain.mapper.DeviceInfoMapper;
import cn.com.glsx.supplychain.mapper.FirmwareInfoMapper;
import cn.com.glsx.supplychain.mapper.OrderInfoDetailMapper;
import cn.com.glsx.supplychain.mapper.OrderInfoMapper;
import cn.com.glsx.supplychain.mapper.RequestLogMapper;
import cn.com.glsx.supplychain.mapper.UserInfoMapper;
import cn.com.glsx.supplychain.mapper.WareHouseInfoMapper;
import cn.com.glsx.supplychain.model.AttribInfo;
import cn.com.glsx.supplychain.model.AttribMana;
import cn.com.glsx.supplychain.model.DeviceInfo;
import cn.com.glsx.supplychain.model.FirmwareInfo;
import cn.com.glsx.supplychain.model.OrderInfo;
import cn.com.glsx.supplychain.model.OrderInfoDetail;
import cn.com.glsx.supplychain.model.RequestLog;
import cn.com.glsx.supplychain.model.UserInfo;
import cn.com.glsx.supplychain.model.WareHouseInfo;
import cn.com.glsx.supplychain.util.StringUtil;


@Service
@Transactional
public class SupplyChainAdminRemoteService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private WareHouseInfoMapper warehouseInfoMapper;
	
	@Autowired
	private UserInfoMapper userInfoMapper;

	@Autowired
	private FirmwareInfoMapper firmwareInfoMapper;

	@Autowired
	private DeviceInfoMapper deviceInfoMapper;
	
	@Autowired
	private OrderInfoDetailMapper orderInfoDetailMapper;

	@Autowired
	private OrderInfoMapper orderInfoMapper;

	@Autowired
	private AttribInfoMapper attribInfoMapper;
	
	@Autowired
	private RequestLogMapper requestLogMapper;
	
	@Autowired
	private SupplyChainExternalService externalService;
	
	@Autowired
	private AttribManaMapper attribManaMapper;

	@Autowired
	private AttribInfoService attribInfoService;
	
	@Autowired
	private MaterialInfoService materialInfoService;
	
	@Autowired
	private WareHouseInfoService wareHouseInfoService;

	@Autowired
	private MerchantOrderDetailMapper merchantOrderDetailMapper;

	@Autowired
	private MerchantOrderMapper merchantOrderMapper;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private EcMerchantOrderService ecMerchantOrderService;
	
	@Autowired
	private MerchantOrderService merchantOrderService;
	/**
	 * 
	 * @Title: listWareHouseInfo 
	 * @Description: 获取仓库信息列表
	 * @param @param pagination
	 * @param @param wareHouseInfo
	 * @param @return
	 * @param @throws RpcServiceException 
	 * @return Page<WareHouseInfo>
	 * @throws
	 */
	public Page<WareHouseInfo> listWareHouseInfo(
			RpcPagination<WareHouseInfo> pagination, WareHouseInfo wareHouseInfo)
			throws RpcServiceException {

		if (pagination == null || wareHouseInfo == null) {
			logger.error("查询仓库信息列表:参数pagination and wareHouseInfo 不能为null值");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		logger.info("查询仓库信息列表传入参数:" + JSONSerializer.toJSON(pagination) + " "
				+ wareHouseInfo.toString());

		try {
			PageHelper.startPage(pagination.getPageNum(),
					pagination.getPageSize());
			return warehouseInfoMapper.listWareHouseInfo(wareHouseInfo);
		} catch (Exception e) {
			logger.error("查询仓库信息列表获取数据库异常 错误信息:" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
	}

	/**
	 * 
	 * @Title: addAndUpdateWareHouseInfo 
	 * @Description: 添加或修改仓库信息
	 * @param @param wareHouseInfo
	 * @param @return
	 * @param @throws RpcServiceException 
	 * @return Integer
	 * @throws
	 */
	public Integer addAndUpdateWareHouseInfo(WareHouseInfo wareHouseInfo)
			throws RpcServiceException {

		if (wareHouseInfo == null) {
			logger.error("添加或修改仓库信息:参数 wareHouseInfo 不能为null值");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		logger.info("添加或修改仓库信息传入参数:" + wareHouseInfo.toString());

		if(wareHouseInfo.getId() == null){
			
			wareHouseInfo.setCreatedDate(new Date());
			wareHouseInfo.setUpdatedDate(new Date());

			WareHouseInfo tempInfo = warehouseInfoMapper.getWareHouseByName(wareHouseInfo.getName());
			if(tempInfo != null){	
				logger.error("添加或修改仓库信息:仓库名已被占用");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_WAREHOUSE_NAME_ALREADY_USED);
			}
			
			try{
				return warehouseInfoMapper.insert(wareHouseInfo);	
			}catch (Exception e) {
				logger.error("添加或修改仓库信息数据库异常 错误信息:" + e.getMessage());
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
			}
			
		}else{
			wareHouseInfo.setUpdatedDate(new Date());
			return warehouseInfoMapper.update(wareHouseInfo);
		}
		
	}

	/**
	 *  
	 * @Title: getWareHouseInfoById 
	 * @Description: 根据id获取仓库信息
	 * @param @param id
	 * @param @return
	 * @param @throws RpcServiceException 
	 * @return WareHouseInfo
	 * @throws
	 */
	public WareHouseInfo getWareHouseInfoById(Integer id)
			throws RpcServiceException {

		if (id == null) {
			logger.error("根据id获取仓库信息:参数 id 不能为null值");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		logger.info("根据id获取仓库信息传入参数:" + id);

		try {
			return warehouseInfoMapper.getWareHouseById(id);
		} catch (Exception e) {
			logger.error("根据id获取仓库信息数据库异常 错误信息:" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
	}

	/**
	 * 
	 * @Title: listUserInfo 
	 * @Description:  获取仓库操作员信息列表
	 * @param @param pagination
	 * @param @param userInfo
	 * @param @return
	 * @param @throws RpcServiceException 
	 * @return Page<UserInfo>
	 * @throws
	 */
	public Page<UserInfo> listUserInfo(RpcPagination<UserInfo> pagination,
			UserInfo userInfo) throws RpcServiceException {
		if (pagination == null || userInfo == null) {
			logger.error("获取仓库操作员信息列表:参数pagination and userInfo 不能为null值");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		logger.info("获取仓库操作员信息列表传入参数:" + JSONSerializer.toJSON(pagination)
				+ " " + userInfo.toString());

		try {
			PageHelper.startPage(pagination.getPageNum(),
					pagination.getPageSize());
			return userInfoMapper.listUserInfo(userInfo);
		} catch (Exception e) {
			logger.error("获取仓库操作员信息列表数据库异常 错误信息:" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
	}

	/**
	 *  
	 * @Title: addAndUpdateUserInfo 
	 * @Description: 添加或者修改操作员信息
	 * @param @param userInfo
	 * @param @return
	 * @param @throws RpcServiceException 
	 * @return Integer
	 * @throws
	 */
	public Integer addAndUpdateUserInfo(UserInfo userInfo) throws RpcServiceException {

		if (userInfo == null) {
			logger.error("添加或者修改操作员信息:userInfo 不能为null值");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		logger.info("添加或者修改操作员信息入参数:" + userInfo.toString());
		
		if(StringUtils.isEmpty(userInfo.getId())){
			
			logger.info("新增用户入口");
			
			//查询新增用户的名称是否存在
			//UserInfo user = userInfoMapper.getUserInfoByUserName(userInfo);
			
			UserInfo user = userInfoService.getUserInfoByUserName(userInfo.getUserName());
			
			if(null != user){
				logger.info("新增的用户:" + user.getUserName() + ",已经存在");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_ACCOUNT_USERNAME_ALREADY_USED);
			}
			
			userInfo.setCreatedDate(new Date());
			userInfo.setUpdatedDate(new Date());
			//密码MD5加密
			userInfo.setPassword(Md5Encrypt.md5(userInfo.getPassword()));
		
			try {
				return userInfoMapper.insertUserInfo(userInfo);
			} catch (Exception e) {
				logger.error("新增用户信息异常," + e.getMessage(),e);
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
			}
			
		}else{
			
			logger.info("更新用户入口");
			
			//查询除当前用户信息外,是否存在相同用户名称
			UserInfo user = userInfoMapper.getUserInfoByByUpdate(userInfo);
			
			if(null != user){
				logger.info("修改的用户名字:"+ userInfo.getUserName()  +"已经存在");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_ACCOUNT_USERNAME_ALREADY_USED);
			}
			
			userInfo.setUpdatedDate(new Date());
			userInfo.setPassword(Md5Encrypt.md5(userInfo.getPassword()));
			//userInfoMapper.update(userInfo);
			//更新缓存
			return userInfoService.update(userInfo);
			
		}
	}

	/**
	 * 
	 * @Title: getUserInfoById 
	 * @Description: 根据id获取操作员信息
	 * @param @param id
	 * @param @return
	 * @param @throws RpcServiceException 
	 * @return UserInfo
	 * @throws
	 */
	public UserInfo getUserInfoById(Integer id) throws RpcServiceException {
		if (id == null) {
			logger.error("根据id获取操作员信息:参数 id 不能为null值");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		logger.info("根据id获取操作员信息传入参数:" + id);
		try {
			return userInfoMapper.getUserInfoById(id);
		} catch (Exception e) {
			logger.error("根据id获取操作员信息数据库异常 错误信息:" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
	}

	/**
	 * 
	 * @Title: listFirmwareInfo 
	 * @Description: 获取固件信息列表
	 * @param @param pagination
	 * @param @param firmwareInfo
	 * @param @return
	 * @param @throws RpcServiceException 
	 * @return Page<FirmwareInfo>
	 * @throws
	 */
	public Page<FirmwareInfo> listFirmwareInfo(RpcPagination<FirmwareInfo> pagination, FirmwareInfo firmwareInfo)
			throws RpcServiceException {
		if (pagination == null || firmwareInfo == null) {
			logger.error("获取固件信息列表:参数pagination and firmwareInfo 不能为null值");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		logger.info("获取固件信息列表传入参数:" + JSONSerializer.toJSON(pagination) + " "
				+ firmwareInfo.toString());

		try {
			PageHelper.startPage(pagination.getPageNum(),pagination.getPageSize());
			
			Page<FirmwareInfo> firmwareList = firmwareInfoMapper.listFirmwareInfo(firmwareInfo);
			Page<FirmwareInfo> newFirmwareList = new Page<FirmwareInfo>();
			return returnFirmwareList(firmwareList, newFirmwareList);

		} catch (Exception e) {
			logger.error("获取固件信息列表数据库异常 错误信息:" + e.getMessage() + JSONArray.fromObject(e.getStackTrace()));
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
	}
	
	/**
	 * 
	 * @Title: returnFirmwareList 
	 * @Description: 固件版本属性转换
	 * @param @param firmwareList
	 * @param @param newFirmwareList
	 * @param @return 
	 * @return Page<FirmwareInfo>
	 * @throws
	 */
	public Page<FirmwareInfo> returnFirmwareList(Page<FirmwareInfo> firmwareList,Page<FirmwareInfo> newFirmwareList){
		newFirmwareList.setTotal(firmwareList.getTotal());
		newFirmwareList.setPageSize(firmwareList.getPageSize());
		newFirmwareList.setPageNum(firmwareList.getPageNum());
		
		for (int i = 0; i < firmwareList.size(); i++) {
			AttribMana attribMana = firmwareList.get(i).getAttribMana();
			FirmwareInfo f = firmwareList.get(i);
			
			//获取属性配置type
			Integer type = f.getAttribMana().getType();
			//获取到值
			String typeName = attribInfoService.getAttribInfoNameById(type);
			attribMana.setTypeName(typeName);
			
			//获取属性配置mode
			Integer model = f.getAttribMana().getModel();
			//获取到值
			String modelName = attribInfoService.getAttribInfoNameById(model);
			attribMana.setModelName(modelName);
			
			//获取属性配置configure
			Integer configure = f.getAttribMana().getConfigure();
			//获取到值
			String configureName = attribInfoService.getAttribInfoNameById(configure);
			attribMana.setConfigureName(configureName);
			f.setAttribMana(attribMana);
			newFirmwareList.add(f);
		}
		return newFirmwareList;	
	}
	

	/**
	 * 
	 * @Title: addAndUpdateFirmwareInfo 
	 * @Description: 添加或修改固件信息
	 * @param @param firmwareInfo
	 * @param @return
	 * @param @throws RpcServiceException 
	 * @return Integer
	 * @throws
	 */
	public Integer addAndUpdateFirmwareInfo(FirmwareInfo firmwareInfo)
			throws RpcServiceException {

		if (firmwareInfo == null) {
			logger.error("添加或修改固件信息:firmwareInfo 不能为null值");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		logger.info("添加或修改固件信息输入参数:" + firmwareInfo.toString());

		if(firmwareInfo.getId() == null){
			firmwareInfo.setUpdatedDate(new Date());
			firmwareInfo.setCreatedDate(new Date());
			try{
				return firmwareInfoMapper.insert(firmwareInfo);
			}catch(Exception e){	
				logger.error("添加或修改固件信息数据库异常 错误信息:" + e.getMessage());
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
			}
		}else{	
			firmwareInfo.setUpdatedDate(new Date());		
			return firmwareInfoMapper.update(firmwareInfo);
		}

	}

	/**
	 *  
	 * @Title: getFirmwareInfoById 
	 * @Description: 根据id获取固件信息
	 * @param @param id
	 * @param @return
	 * @param @throws RpcServiceException 
	 * @return FirmwareInfo
	 * @throws
	 */
	public FirmwareInfo getFirmwareInfoById(Integer id)
			throws RpcServiceException {

		if (id == null) {
			logger.error("根据id获取固件信息:参数 id 不能为null值");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		logger.info("根据id获取固件信息传入参数:" + id);

		try {
			return firmwareInfoMapper.getFirmwareInfoById(id);
		} catch (Exception e) {
			logger.error("根据id获取固件信息数据库异常 错误信息:" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
	}
	
	/**
	 *  
	 * @Title: getFirmwareInfoBySoftversion 
	 * @Description: 根据固件和版本信息获取firmwareInfo
	 * @param @param id
	 * @param @return
	 * @param @throws RpcServiceException 
	 * @return FirmwareInfo
	 * @throws
	 */
	public FirmwareInfo getFirmwareInfoBySoftversion(FirmwareInfo record)
			throws RpcServiceException{
		
		if(record.getModel() == null || record.getConfigure()==null || record.getType() == null || record.getSoftVersion() == null)
		{
			logger.error("根据固件和版本信息获取firmwareInfo 参数错误");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		
		logger.info("根据固件和版本信息获取firmwareInfo参数:" + record.toString());
		
		try
		{
			return firmwareInfoMapper.getFirmwareInfoByVersion(record);
		}catch (Exception e) {
			logger.error("根据固件和版本信息获取firmwareInfo数据库异常 错误信息:" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
	}
	
	/**
	 * 
	 * @Title: ExportDeviceInfo 
	 * @Description: 导出硬件设备信息
	 * @param @param pagination
	 * @param @param deviceInfo
	 * @param @return
	 * @param @throws RpcServiceException 
	 * @return Page<DeviceInfo>
	 * @throws
	 */
	public List<DeviceInfo> ExportDeviceInfo(DeviceInfo deviceInfo)throws RpcServiceException{

		if (deviceInfo == null) {
			logger.error("获取硬件设备列表:参数deviceInfo 不能为null值");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		
		logger.info("获取硬件设备列表传入参数:" +  deviceInfo.toString());
		
		try{
			
			if((StringUtil.isEmpty(deviceInfo.getIccid()) || StringUtil.isEmpty(deviceInfo.getImei())) && !StringUtil.isEmpty(deviceInfo.getOrderCode())){
				//单独按订单号导出
				RpcAssert.assertNotNull(deviceInfo.getOrderCode(), DefaultErrorEnum.DATA_NULL, "orderCode must not be null");
				return deviceInfoMapper.listDeviceInfoByOrderCode(deviceInfo.getOrderCode());

			}else{
				//按iccid、imei、orderCode组合条件导出
				//返回结果集
				
				Page<DeviceInfo> deviceList = deviceInfoMapper.listDeviceInfo(deviceInfo);
				
				Page<DeviceInfo> newDeviceList = new Page<DeviceInfo>();
				
				return returnDeviceList(deviceList, newDeviceList);
			}
		}catch(Exception e){
			
			logger.error("获取硬件设备列表数据库异常 错误信息:" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
	}


	/**
	 * 导出订单详情信息
	 * @param orderInfoDetail
	 * @return
	 * @throws RpcServiceException
	 */
	public List<OrderInfoDetail> ExportOrderInfoDetail(OrderInfoDetail orderInfoDetail)throws RpcServiceException{

		if (orderInfoDetail == null) {
			logger.error("获取订单详情列表:orderInfoDetail 不能为null值");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}

		logger.info("获取订单详情列表传入参数:" +  orderInfoDetail.toString());

		try{

			if((StringUtil.isEmpty(orderInfoDetail.getIccid()) || StringUtil.isEmpty(orderInfoDetail.getImei())) && !StringUtil.isEmpty(orderInfoDetail.getOrderCode())){
				//单独按订单号导出
				RpcAssert.assertNotNull(orderInfoDetail.getOrderCode(), DefaultErrorEnum.DATA_NULL, "orderCode must not be null");
				return orderInfoDetailMapper.getOrderInfoDetailListByOrderCode(orderInfoDetail.getOrderCode());
			}
		}catch(Exception e){

			logger.error("获取订单详情列表数据库异常 错误信息:" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		return null;
	}

	/**
	 * 
	 * @Title: listDeviceInfo 
	 * @Description: 获取硬件设备列表
	 * @param @param pagination
	 * @param @param deviceInfo
	 * @param @return
	 * @param @throws RpcServiceException 
	 * @return Page<DeviceInfo>
	 * @throws
	 */
	public Page<DeviceInfo> listDeviceInfo(RpcPagination<DeviceInfo> pagination, DeviceInfo deviceInfo)
			throws RpcServiceException {

		if (deviceInfo == null) {
			logger.error("获取硬件设备列表:参数pagination and firmwareInfo 不能为null值");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		logger.info("获取硬件设备列表传入参数:" + JSONSerializer.toJSON(pagination) + "," + deviceInfo.toString());

		try {
			PageHelper.startPage(pagination.getPageNum(),pagination.getPageSize());
			Page<DeviceInfo> deviceList = deviceInfoMapper.listDeviceInfo(deviceInfo);
			Page<DeviceInfo> newDeviceList = new Page<DeviceInfo>();
			
			return returnDeviceList(deviceList, newDeviceList);
		} catch (Exception e) {
			logger.error("获取硬件设备列表数据库异常 错误信息:" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
	}
	
	
	/**
	 * 
	 * @Title: returnDeviceList 
	 * @Description: 设备属性转换
	 * @param @param firmwareList
	 * @param @param newFirmwareList
	 * @param @return 
	 * @return Page<FirmwareInfo>
	 * @throws
	 */
	public Page<DeviceInfo> returnDeviceList(Page<DeviceInfo> deviceList,Page<DeviceInfo> newDeviceList){
		newDeviceList.setTotal(deviceList.getTotal());
		newDeviceList.setPageSize(deviceList.getPageSize());
		newDeviceList.setPageNum(deviceList.getPageNum());
		
		for (int i = 0; i < deviceList.size(); i++) {
			
			AttribMana attribMana = deviceList.get(i).getAttribMana();
			DeviceInfo d = deviceList.get(i);
			
			//获取属性配置type
			Integer type = d.getAttribMana().getType();
			//获取到值
			if(type != null && type !=0){
				String typeName = attribInfoService.getAttribInfoNameById(type);
				attribMana.setTypeName(typeName);
			}
			
			//获取属性配置mode
			Integer model = d.getAttribMana().getModel();
			//获取到值
			if(model != null && model !=0){
				String modelName = attribInfoService.getAttribInfoNameById(model);
				attribMana.setModelName(modelName);
			}

			//获取属性配置size
			Integer msize = d.getAttribMana().getMsize();
			//获取到值
			if(msize != null && msize != 0){
				String msizeName = attribInfoService.getAttribInfoNameById(msize);
				attribMana.setMsizeName(msizeName);
			}

			//获取属性配置configure
			Integer configure = d.getAttribMana().getConfigure();
			//获取到值
			if(configure != null && configure !=0){
				String configureName = attribInfoService.getAttribInfoNameById(configure);
				attribMana.setConfigureName(configureName);
			}

			//查询设备型号
			Integer devMnum = d.getAttribMana().getDevMnumId();
			if(devMnum != null && devMnum != 0){
				String devMnumName = attribInfoService.getAttribInfoNameById(devMnum);
				attribMana.setDevMnumName(devMnumName);
			}

			//查询是否联网
			Integer orNet = d.getAttribMana().getOrNetId();
			if(orNet != null && orNet != 0){
				String orNetName = attribInfoService.getAttribInfoNameById(orNet);
				attribMana.setOrNetName(orNetName);
			}

			//查询卡类型
			Integer cardType = d.getAttribMana().getCardSelfId();
			if(cardType != null && cardType != 0){
				String cardSelfName  = attribInfoService.getAttribInfoNameById(cardType);
				attribMana.setCardSelfName(cardSelfName);
			}

			//查询有源/无源
			Integer source = d.getAttribMana().getSourceId();
			if(source != null && source != 0){
				String sourceName = attribInfoService.getAttribInfoNameById(source);
				attribMana.setSourceName(sourceName);
			}

			//查询是否通用
			Integer orOpen = d.getAttribMana().getOrOpenId();
			if(orOpen != null && orOpen != 0){
				String orOpenName = attribInfoService.getAttribInfoNameById(orOpen);
				attribMana.setOrOpenName(orOpenName);
			}

			//查询是否带屏
			Integer screen = d.getAttribMana().getScreenId();
			if(screen != null && screen !=0){
				String screenName  = attribInfoService.getAttribInfoNameById(screen);
				attribMana.setScreenName(screenName);
			}
			
			d.setMaterialName(materialInfoService.getMaterialNameByCode(d.getAttribCode()));
			
			//获取仓库值（优先读取缓存）
			String wareHouseName = wareHouseInfoService.queryWareHouseByRedis(d.getWareHouseId());
			String wareHouseUpName = wareHouseInfoService.queryWareHouseByRedis(d.getWareHouseIdUp());
			WareHouseInfo w = new WareHouseInfo();
			w.setWareHouseName(wareHouseName);
			w.setWareHouseUpName(wareHouseUpName);
			d.setAttribMana(attribMana);
			d.setWareHouseInfo(w);
			newDeviceList.add(d);
		}
		return newDeviceList;	
	}
	
	
	/**
	 * 
	 * @Title: listDeviceInfoByOrderCode 
	 * @Description: 根据订单号获取硬件设备列表
	 * @param @param pagination
	 * @param @param deviceInfo
	 * @param @return
	 * @param @throws RpcServiceException 
	 * @return Page<DeviceInfo>
	 * @throws
	 */
	public Page<DeviceInfo> listDeviceInfoByOrderCode(
			RpcPagination<DeviceInfo> pagination, String strOrderCode)
			throws RpcServiceException{
		
		logger.info("根据订单号获取硬件设备列表传入参数:" + JSONSerializer.toJSON(pagination) + " "
				+ strOrderCode);
		
		try{
			PageHelper.startPage(pagination.getPageNum(),
					pagination.getPageSize());
			return deviceInfoMapper.listDeviceInfoByOrderCode(strOrderCode);
		}catch (Exception e) {
			logger.error("根据订单号获取硬件设备列表数据库异常 错误信息:" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
	} 
	
	/**
	 * 
	 * @Title: pageOrderInfoDetails 
	 * @Description: 获取订单详情列表
	 * @param @param pagination
	 * @param @param orderInfoDetail
	 * @param @return
	 * @param @throws RpcServiceException 
	 * @return Page<DeviceInfo>
	 * @throws
	 */
	public Page<OrderInfoDetail> pageOrderInfoDetails(
			RpcPagination<OrderInfoDetail> pagination, OrderInfoDetail record)
			throws RpcServiceException{
		
		logger.info("获取订单详情列表:" + JSONSerializer.toJSON(pagination) + " "
				+ record.toString());
		
		try{
			PageHelper.startPage(pagination.getPageNum(),
					pagination.getPageSize());
			return orderInfoDetailMapper.pageOrderInfoDetail(record);
		}catch (Exception e) {
			logger.error("获取订单详情列表数据库异常 错误信息:" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
	}

	/**
	 * 
	 * @Title: addAndUpdateDeviceInfo 
	 * @Description: 添加或修改硬件设备信息
	 * @param @param deviceInfo
	 * @param @return
	 * @param @throws RpcServiceException 
	 * @return Integer
	 * @throws
	 */
	public Integer addAndUpdateDeviceInfo(DeviceInfo deviceInfo)
			throws RpcServiceException {

		if (deviceInfo == null) {
			logger.error("添加或修改硬件设备信息:firmwareInfo 不能为null值");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		logger.info("添加或修改硬件设备信息输入参数:" + deviceInfo.toString());
		
		if(deviceInfo.getId() == null){
			
			deviceInfo.setCreatedDate(new Date());
			deviceInfo.setUpdatedDate(new Date());
			
			try{
				return deviceInfoMapper.insert(deviceInfo);
			}catch(Exception e){
				logger.error("添加或修改硬件设备信息数据库异常 错误信息:" + e.getMessage());
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
			}
		}else{
			
			deviceInfo.setUpdatedDate(new Date());
			return deviceInfoMapper.update(deviceInfo);
		}
	}

	/**
	 * 
	 * @Title: getDeviceInfoById 
	 * @Description: 根据id获取硬件设备信息
	 * @param @param id
	 * @param @return
	 * @param @throws RpcServiceException 
	 * @return DeviceInfo
	 * @throws
	 */
	public DeviceInfo getDeviceInfoById(Integer id) throws RpcServiceException {

		if (id == null) {
			logger.error("根据id获取硬件设备信息:参数 id 不能为null值");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		logger.info("根据id获取硬件设备传入参数:" + id);

		try {
			DeviceInfo deviceInfo = deviceInfoMapper.getDeviceInfoById(id);
			return returnDeviceInfoByid(deviceInfo);
		} catch (Exception e) {
			logger.error("根据id获取硬件设备数据库异常 错误信息:" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
	}

	/**
	 *
	 * @Title: getDeviceInfoById
	 * @Description: 根据id获取硬件设备信息(属性转换)
	 * @param @param id
	 * @param @return
	 * @param @throws RpcServiceException
	 * @return DeviceInfo
	 * @throws
	 */
	public DeviceInfo returnDeviceInfoByid(DeviceInfo deviceInfo){

		//获取属性配置type
		Integer type = deviceInfo.getAttribMana().getType();
		//获取到值
		if(type != null && type !=0){
			String typeName = attribInfoService.getAttribInfoNameById(type);
			deviceInfo.setTypeName(typeName);
		}

		//获取属性配置mode
		Integer model = deviceInfo.getAttribMana().getModel();
		//获取到值
		if(model != null && model !=0){
			String modelName = attribInfoService.getAttribInfoNameById(model);
			deviceInfo.setModelName(modelName);
		}

		//获取属性配置size
		Integer msize = deviceInfo.getAttribMana().getMsize();
		//获取到值
		if(msize != null && msize != 0){
			String msizeName = attribInfoService.getAttribInfoNameById(msize);
			deviceInfo.setMsizeName(msizeName);
		}

		//获取属性配置configure
		Integer configure = deviceInfo.getAttribMana().getConfigure();
		//获取到值
		if(configure != null && configure !=0){
			String configureName = attribInfoService.getAttribInfoNameById(configure);
			deviceInfo.setConfigureName(configureName);
		}

		//查询设备型号
		Integer devMnum = deviceInfo.getAttribMana().getDevMnumId();
		if(devMnum != null && devMnum != 0){
			String devMnumName = attribInfoService.getAttribInfoNameById(devMnum);
			deviceInfo.setDevMnumName(devMnumName);
		}

		//查询是否联网
		Integer orNet = deviceInfo.getAttribMana().getOrNetId();
		if(orNet != null && orNet != 0){
			String orNetName = attribInfoService.getAttribInfoNameById(orNet);
			deviceInfo.setOrNetName(orNetName);
		}

		//查询卡类型
		Integer cardType = deviceInfo.getAttribMana().getCardSelfId();
		if(cardType != null && cardType != 0){
			String cardSelfName  = attribInfoService.getAttribInfoNameById(cardType);
			deviceInfo.setCardSelfName(cardSelfName);
		}

		//查询有源/无源
		Integer source = deviceInfo.getAttribMana().getSourceId();
		if(source != null && source != 0){
			String sourceName = attribInfoService.getAttribInfoNameById(source);
			deviceInfo.setSourceName(sourceName);
		}

		//查询是否通用
		Integer orOpen = deviceInfo.getAttribMana().getOrOpenId();
		if(orOpen != null && orOpen != 0){
			String orOpenName = attribInfoService.getAttribInfoNameById(orOpen);
			deviceInfo.setOrOpenName(orOpenName);
		}

		//查询是否带屏
		Integer screen = deviceInfo.getAttribMana().getScreenId();
		if(screen != null && screen !=0){
			String screenName  = attribInfoService.getAttribInfoNameById(screen);
			deviceInfo.setScreenName(screenName);
		}
		return deviceInfo;
	}
	
	/**
	 * 
	 * @Title: clearDeviceInfoByImei 
	 * @Description: 清除设备库存管理中的数据 使其能够再次扫码入库
	 * @param @param imei
	 * @param @return
	 * @param @throws RpcServiceException 
	 * @return DeviceInfo
	 * @throws
	 */
	public Integer clearDeviceInfoByImei(String imei) throws RpcServiceException
	{
		Integer ret = 1;
		if(StringUtils.isEmpty(imei))
		{
			logger.error("SupplyChainAdminRemoteService::clearDeviceInfoByImei 参数错误");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		
		logger.info("SupplyChainAdminRemoteService::clearDeviceInfoByImei param imei=" + imei);
		
		try
		{
			DeviceInfo record = deviceInfoMapper.getDeviceInfoByImei(imei);
			if(!StringUtils.isEmpty(record))
			{
				record.setDeletedFlag("Y");
				deviceInfoMapper.updateByPrimaryKeySelective(record);
			}
		}
		catch (Exception e) 
		{
			logger.error("SupplyChainAdminRemoteService::clearDeviceInfoByImei 数据库异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		
		return ret;
		
	}

	/**
	 * 
	 * @Title: listOrderInfo 
	 * @Description: 获取订单列表
	 * @param @param pagination
	 * @param @param orderInfo
	 * @param @return
	 * @param @throws RpcServiceException 
	 * @return Page<OrderInfo>
	 * @throws
	 */
	public Page<OrderInfo> listOrderInfo(RpcPagination<OrderInfo> pagination,OrderInfo orderInfo) throws RpcServiceException {

		if (pagination == null || orderInfo == null) {
			logger.error("获取订单列表:参数pagination and firmwareInfo 不能为null值");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		logger.info("获取订单列表传入参数:" + JSONSerializer.toJSON(pagination) + " "
				+ orderInfo.toString());

		try {
			PageHelper.startPage(pagination.getPageNum(),pagination.getPageSize());
			Page<OrderInfo> orderList = orderInfoMapper.listOrderInfo(orderInfo);
			Page<OrderInfo> newOrderList = new Page<OrderInfo>();
			
			return returnOrderList(orderList, newOrderList);
		} catch (Exception e) {
			logger.error("获取订单列表数据库异常 错误信息:" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
	}
	
	/**
	 * @Title: countOrderInfosByDeviceCode 
	 * @Description: 根据deviceCode获取订单数
	 * @param @param Integer 
	 * @param @return
	 * @param @throws RpcServiceElistMerchantInfoxception
	 * @return Integer
	 * @throws
	 */
	public Integer countOrderInfosByDeviceCode(Integer deviceCode) throws RpcServiceException
	{
		Integer count = 0;
		logger.info("根据deviceCode获取订单数:" + deviceCode);
		
		try
		{
			OrderInfo record = new OrderInfo();
			record.setDeviceId(deviceCode);
			record.setDeletedFlag("N");
			count = orderInfoMapper.count(record);
		}
		catch (Exception e)
		{
			logger.error("根据deviceCode获取订单数据库异常 错误信息:" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		
		return count;
	}
	
	
	/**
	 * 
	 * @Title: returnOrderList 
	 * @Description: 订单属性转换
	 * @param @param firmwareList
	 * @param @param newFirmwareList
	 * @param @return 
	 * @return Page<FirmwareInfo>
	 * @throws
	 */
	public Page<OrderInfo> returnOrderList(Page<OrderInfo> orderList,Page<OrderInfo> newOrderList){
		
		newOrderList.setTotal(orderList.getTotal());
		newOrderList.setPageSize(orderList.getPageSize());
		newOrderList.setPageNum(orderList.getPageNum());
		for (int i = 0; i < orderList.size(); i++) {
			AttribMana attribMana = orderList.get(i).getAttribMana();
			OrderInfo o = orderList.get(i);
			
			//获取属性配置type
			Integer type = o.getAttribMana().getType();
			//获取到值
			if(type != null && type !=0){
				String typeName = attribInfoService.getAttribInfoNameById(type);
				attribMana.setTypeName(typeName);
			}

			//获取尺寸
			Integer msize = o.getAttribMana().getMsize();
			if(msize != null && msize !=0){
				String msizeName = attribInfoService.getAttribInfoNameById(msize);
				attribMana.setMsizeName(msizeName);
			}
			
			//获取属性配置mode
			Integer model = o.getAttribMana().getModel();
			//获取到值
			if(model != null && model !=0){
				String modelName = attribInfoService.getAttribInfoNameById(model);
				attribMana.setModelName(modelName);
			}

			//获取属性配置configure
			Integer configure = o.getAttribMana().getConfigure();
			//获取到值
			if(configure != null && configure != 0){
				String configureName = attribInfoService.getAttribInfoNameById(configure);
				attribMana.setConfigureName(configureName);
			}

			//查询设备型号
			Integer devMnum = o.getAttribMana().getDevMnumId();
			if(devMnum != null && devMnum != 0){
				String devMnumName = attribInfoService.getAttribInfoNameById(devMnum);
				attribMana.setDevMnumName(devMnumName);
			}

			//查询是否联网
			Integer orNet = o.getAttribMana().getOrNetId();
			if(orNet != null && orNet != 0){
				String orNetName = attribInfoService.getAttribInfoNameById(orNet);
				attribMana.setOrNetName(orNetName);
			}

			//查询卡类型
			Integer cardType = o.getAttribMana().getCardSelfId();
			if(cardType != null && cardType != 0){
				String cardSelfName  = attribInfoService.getAttribInfoNameById(cardType);
				attribMana.setCardSelfName(cardSelfName);
			}

			//查询有源/无源
			Integer source = o.getAttribMana().getSourceId();
			if(source != null && source != 0){
				String sourceName = attribInfoService.getAttribInfoNameById(source);
				attribMana.setSourceName(sourceName);
			}

			//查询是否通用
			Integer orOpen = o.getAttribMana().getOrOpenId();
			if(orOpen != null && orOpen != 0){
				String orOpenName = attribInfoService.getAttribInfoNameById(orOpen);
				attribMana.setOrOpenName(orOpenName);
			}

			//查询是否带屏
			Integer screen = o.getAttribMana().getScreenId();
			if(screen != null && screen !=0){
				String screenName  = attribInfoService.getAttribInfoNameById(screen);
				attribMana.setScreenName(screenName);
			}
			
			//获取仓库值（优先读取缓存）
			String wareHouseName = wareHouseInfoService.queryWareHouseByRedis(o.getWarehouseId());
			WareHouseInfo w = new WareHouseInfo();
			w.setWareHouseName(wareHouseName);
			o.setAttribMana(attribMana);
			o.setWareHouseInfo(w);
			newOrderList.add(o);
		}
		return newOrderList;	
	}

	/**
	 * 
	 * @Title: addAndUpdateOrderInfo 
	 * @Description: 添加或修改订单信息
	 * @param @param orderInfo
	 * @param @return
	 * @param @throws RpcServiceException 
	 * @return Integer
	 * @throws
	 */
	public Integer addAndUpdateOrderInfo(OrderInfo orderInfo)throws RpcServiceException {

		if (orderInfo == null) {
			logger.error("添加或修改订单信息:firmwareInfo 不能为null值");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		logger.info("添加或修改订单信息输入参数:" + orderInfo.toString());
		
		if(StringUtils.isEmpty(orderInfo.getId())){
			
			String orderCode = StringUtil.getOrderNo(); // 生成订单号
			orderInfo.setOrderCode(orderCode);
			logger.info("生成订单号：" + orderCode);
			logger.info("order insert....");
			orderInfo.setCreatedDate(new Date());
			orderInfo.setUpdatedDate(new Date());
			orderInfo.setPackageTwo(orderInfo.getPackageOne());
			orderInfo.setStatus(OrderStatusEnum.STATUS_UF.getValue());

			try{
				//商户订单新增发货单号
				if(orderInfo.getMerchantOrderDetailId() != null){
					EcMerchantOrder ecMerchantOrder = new EcMerchantOrder();
					MerchantOrderDetail merchantOrderDetail = new MerchantOrderDetail();
					merchantOrderDetail.setId(orderInfo.getMerchantOrderDetailId());
					merchantOrderDetail = merchantOrderDetailMapper.selectOne(merchantOrderDetail);
					merchantOrderDetail.setDispatchOrderNumber(orderCode);
					merchantOrderDetailMapper.updateById(merchantOrderDetail);
					
					MerchantOrder merchantOrder = new MerchantOrder();
					merchantOrder.setOrderNumber(merchantOrderDetail.getMerchantOrderNumber());
					merchantOrder = merchantOrderMapper.selectOne(merchantOrder);
					if(merchantOrder.getStatus() != MerchantOrderStatusEnum.ORDER_PORTION_RECEIVE.getCode()){
						merchantOrder.setStatus(MerchantOrderStatusEnum.ORDER_WAIT_RECEIVE.getCode());
						merchantOrder.setUpdatedDate(new Date());
						merchantOrderMapper.updateByOrderNumber(merchantOrder);	
						ecMerchantOrder.setStatus(MerchantOrderStatusEnum.ORDER_WAIT_RECEIVE.getName());
					}
					else
					{
						ecMerchantOrder.setStatus(MerchantOrderStatusEnum.ORDER_PORTION_RECEIVE.getName());
					}
					
					ecMerchantOrder.setUpdatedDate(new Date());
					ecMerchantOrder.setOrderNumber(merchantOrder.getOrderNumber());
					ecMerchantOrder.setDispatchOrderNumber(orderCode);
					ecMerchantOrderService.updateEcMerchantOrderByOrderNumber(ecMerchantOrder);
				}
				
				return orderInfoMapper.insertOrderInfo(orderInfo);
			}catch(Exception e){
				logger.error("添加或修改订单信息数据库异常 错误信息:" + e.getMessage());
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
			}
		}else{
			
			OrderInfo orderInfoTemp = orderInfoMapper.getOrderInfoByOrderCode(orderInfo.getOrderCode());
			
			if(orderInfo.getDeletedFlag().equals("Y") && orderInfo.getStatus().equals("CL")){
				
				orderInfoTemp = orderInfoMapper.getOrderInfoByOrderCode(orderInfo.getOrderCode());
				
				if(orderInfoTemp.getAlreadyShipped() != 0){
					logger.error("添加或修改订单信息:已出货 不能取消");
					throw new RpcServiceException(ErrorCodeEnum.ERRCODE_ORDER_ALREADY_OUTGOING);
				}
				
				merchantOrderService.updateMerchantOrderByDispatchOrderNumber(orderInfo.getOrderCode());
				
			}else{
				if(orderInfo.getTotal() < orderInfoTemp.getAlreadyShipped()){
					logger.error("添加或修改订单信息:修改订单总数无效");
					throw new RpcServiceException(ErrorCodeEnum.ERRCODE_ORDER_INVALID_OPERATOR);
				}
			}
			logger.info("order update....");
			try{
				return orderInfoMapper.update(orderInfo);
			}catch(Exception e){
				
				logger.error("添加或修改订单信息数据库异常 错误信息:" + e.getMessage());
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
			}
		}
	}

	/**
	 * 
	 * @Title: getOrderInfoById 
	 * @Description: 根据id获取订单信息
	 * @param @param id
	 * @param @return
	 * @param @throws RpcServiceException 
	 * @return OrderInfo
	 * @throws
	 */
	public OrderInfo getOrderInfoById(Integer id) throws RpcServiceException {
		if (id == null) {
			logger.error("根据id获取订单信息:参数 id 不能为null值");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		logger.info("根据id获取订单信息传入参数:" + id);

		try {
			return orderInfoMapper.getOrderInfoById(id);
		} catch (Exception e) {
			logger.error("根据id获取订单信息数据库异常 错误信息:" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
	}
	
	/** 
	 * @Title: getOrderInfoByOrderCode 
	 * @Description: 根据orderCode获取订单信息
	 * @param @param orderCode
	 * @param @return
	 * @param @throws RpcServiceException 
	 * @return OrderInfo
	 * @throws
	 */
	 public OrderInfo getOrderInfoByOrderCode(String orderCode) throws RpcServiceException
	 {
		 if (orderCode == null) {
			logger.error("根据订单号获取订单信息:参数 orderCode 不能为null值");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		logger.info("根据订单号获取订单信息传入参数:" + orderCode);

		try {
			return orderInfoMapper.getOrderInfoByOrderCode(orderCode);
		} catch (Exception e) {
			logger.error("根据订单号获取订单信息数据库异常 错误信息:" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
	 }

	/**
	 *  
	 * @Title: listAttribInfo 
	 * @Description: 获取设备属性配置信息列表
	 * @param @param attribInfo
	 * @param @return
	 * @param @throws RpcServiceException 
	 * @return List<AttribInfo>
	 * @throws
	 */
	public List<AttribInfo> listAttribInfo(AttribInfo attribInfo)
			throws RpcServiceException {
		if (null == attribInfo) {
			logger.error("查询设备属性配置信息:AttribInfo 不能为null值");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		try {
			return attribInfoMapper.getAttribInfoListByCondition(attribInfo);
		} catch (Exception e) {
			logger.error("根据设备属性配置信息条件查询异常， 错误信息:" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
	}
	
	/**
	 * 
	 * @Title: addAndUpdateAttribMana 
	 * @Description: 添加或修改设备配置信息
	 * @param @param attribMana
	 * @param @return
	 * @param @throws RpcServiceException 
	 * @return Integer
	 * @throws
	 */
	public Integer addAndUpdateAttribMana(AttribMana attribMana)
			throws RpcServiceException {

		//判断对象不为空
		if (attribMana == null) {
			logger.error("添加或修改设备配置信息:attribMana 不能为null值");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		
		//判断配置编码是否存在
		AttribMana attribManaInfo = attribManaMapper.getAttribManaByCode(attribMana.getAttribCode());
		if(null != attribManaInfo){
			logger.error("增加的配置编码已经存在");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_ATTRIB_MANA_CODE);
		}
		
		logger.info("添加或修改设备配置信息输入参数:" + attribMana.toString());
		
		if(StringUtils.isEmpty(attribMana.getId())){
			
			logger.info("attribMana add....");
			attribMana.setCreatedBy("admin");
			attribMana.setUpdatedBy("admin");
			attribMana.setCreatedDate(new Date());
			attribMana.setUpdatedDate(new Date());
			
			try{
				//return attribManaMapper.insert(attribMana);
                return attribManaMapper.insertAttribMana(attribMana);
			}catch(Exception e){
				logger.error("添加或修改设备配置信息数据库异常 错误信息:" + e.getMessage());
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
			}
		}else{
			logger.info("attribMana update....");
			attribMana.setUpdatedDate(new Date());
			return attribManaMapper.update(attribMana);
		}
	}
	
	/**
	 * 
	 * @Title: listAttribMana 
	 * @Description: 获取设备配置信息
	 * @param @param pagination
	 * @param @param attribMana
	 * @param @return
	 * @param @throws RpcServiceException 
	 * @return Page<OrderInfo>
	 * @throws
	 */
	public Page<AttribMana> listAttribMana(RpcPagination<AttribMana> pagination,
			AttribMana attribMana) throws RpcServiceException {

		if (pagination == null || attribMana == null) {
			logger.error("获取设备配置信息列表:参数pagination and firmwareInfo 不能为null值");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		logger.info("获取设备配置信息传入参数:" + JSONSerializer.toJSON(pagination) + ","+ attribMana.toString());

		try {
			PageHelper.startPage(pagination.getPageNum(),pagination.getPageSize());
			Page<AttribMana> attribManaList = attribManaMapper.listAttribMana(attribMana);
			Page<AttribMana> newAttribManaList = new Page<AttribMana>();

			return returnAttribManaList(attribManaList, newAttribManaList);
		} catch (Exception e) {
			logger.error("获取设备配置信息数据库异常 错误信息:" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
	}
	
	/**
	 * 
	 * @Title: getAttribManaInfo 
	 * @Description: 通过配置code获取到配置信息
	 * @param @param manaCode
	 * @param @return 
	 * @return AttribMana
	 * @throws
	 */
	public AttribMana getAttribManaInfo(String manaCode){
		try {
			return attribManaMapper.getAttribManaByCode(manaCode);
		} catch (Exception e) {
			logger.error("获取设备配置信息数据库异常 错误信息:" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
	}
	
	
	/**
	 * 
	 * @Title: returnAttribManaList 
	 * @Description: 设备配置转换
	 * @param @param firmwareList
	 * @param @param newFirmwareList
	 * @param @return 
	 * @return Page<FirmwareInfo>
	 * @throws
	 */
	public Page<AttribMana> returnAttribManaList(Page<AttribMana> attribManaList,Page<AttribMana> newAttribManaList){
		
		newAttribManaList.setTotal(attribManaList.getTotal());
		newAttribManaList.setPageSize(attribManaList.getPageSize());
		newAttribManaList.setPageNum(attribManaList.getPageNum());
		
		for (int i = 0; i < attribManaList.size(); i++) {
			AttribMana a = attribManaList.get(i);
			
			//获取属性配置type
			Integer type = a.getType();
			//获取到值
			if(type != null && type != 0){
				String typeName = attribInfoService.getAttribInfoNameById(type);
				a.setTypeName(typeName);
			}

			//获取属性配置mode
			Integer model = a.getModel();
			//获取到值
			if(model != null && model != 0){
				String modelName = attribInfoService.getAttribInfoNameById(model);
				a.setModelName(modelName);
			}

			//获取属性配置configure
			Integer configure = a.getConfigure();
			//获取到值
			if(configure != null && configure != 0){
				String configureName = attribInfoService.getAttribInfoNameById(configure);
				a.setConfigureName(configureName);
			}
			
			//获取属性配置msize
			Integer msize = a.getMsize();
			if(msize != null && msize != 0){
				String msizeName = attribInfoService.getAttribInfoNameById(msize);
				a.setMsizeName(msizeName);
			}

			//查询设备类型
			Integer devType = a.getDevTypeId();
			if(devType != null && devType != 0){
				String devTypeName = attribInfoService.getAttribInfoNameById(devType);
				a.setDevTypeName(devTypeName);
			}

			//查询设备型号
			Integer devMnum = a.getDevMnumId();
			if(devMnum != null && devMnum != 0){
				String devMnumName = attribInfoService.getAttribInfoNameById(devMnum);
				a.setDevMnumName(devMnumName);
			}

			//查询是否联网
			Integer orNet = a.getOrNetId();
			if(orNet != null && orNet != 0){
				String orNetName = attribInfoService.getAttribInfoNameById(orNet);
				a.setOrNetName(orNetName);
			}

			//查询卡类型
			Integer cardType = a.getCardSelfId();
			if(cardType != null && cardType != 0){
				String cardSelfName  = attribInfoService.getAttribInfoNameById(cardType);
				a.setCardSelfName(cardSelfName);
			}

			//查询有源/无源
			Integer source = a.getSourceId();
			if(source != null && source != 0){
				String sourceName = attribInfoService.getAttribInfoNameById(source);
				a.setSourceName(sourceName);
			}

			//查询是否通用
			Integer orOpen = a.getOrOpenId();
			if(orOpen != null && orOpen != 0){
				String orOpenName = attribInfoService.getAttribInfoNameById(orOpen);
				a.setOrOpenName(orOpenName);
			}

			//查询是否带屏
			Integer screen = a.getScreenId();
			if(screen != null && screen !=0){
				String screenName  = attribInfoService.getAttribInfoNameById(screen);
				a.setScreenName(screenName);
			}

			newAttribManaList.add(a);
		}
		return newAttribManaList;	
	}
	
	
	/**
	 * 
	 * @Title: syncDeviceInfo 
	 * @Description: 手动同步设备信息
	 * @param @param orderCode
	 * @param @return
	 * @param @throws RpcServiceException 
	 * @return int
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	public int syncDeviceInfo(String orderCode) throws RpcServiceException{
		try {
			logger.info("手动同步设备信息,同步对应的订单号为：" + orderCode);
			if(StringUtil.isEmpty(orderCode)){
				logger.error("订单号为空...");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
			}
			
			//通过orderCode查询订单信息
			OrderInfo order = orderInfoMapper.getOrderInfoByOrderCode(orderCode);
			if(null == order){
				logger.error("查询到的订单对象为空.");
				throw new RpcServiceException(ErrorCodeEnum.ERRCODE_ORDER_NOT_EXIST);
			}
			
			//循环订单列表同步数据到老设备平台中心
			Page<DeviceInfo> deviceList = deviceInfoMapper.listDeviceInfoByOrderCode(orderCode);
			List<PhysicalDevice> physicalDeviceList = new ArrayList<PhysicalDevice>();
			
			//取数据信息
			for (DeviceInfo deviceInfo : deviceList) {
				RequestLog log = new RequestLog();
				log.setImei(deviceInfo.getImei());
				RequestLog logRep = requestLogMapper.getRequestLog(log);
				
				if(logRep == null || logRep.getResult() == null){
					//流量管理平台参数对象
					FlowCardRequest request = new FlowCardRequest();
					request.setKeyWord(deviceInfo.getIccid().toUpperCase());		
					
					//获取到模块手机号码
					String cardNo = "";
					String imsi = "";
					try {
						String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()); 
						request.setTime(time);  //时间
						request.setVersion("2.2.0"); //版本号
						request.setConsumer("task-supplychain"); //项目名称
						request.setInvoker("glsx");
						//请求接口
						FlowResponse<Flowcard> response = externalService.getFlowCardByIccidOrImsi(request);
						if(response.getCode().equals("1000")){
							if(null == response.getEntiy()){
								logger.info("调用[FlowCardService ------》 getFlowCardByIccidOrImsi]查询不到数据");
								continue; //请求接口异常直接跳出此次循环
							} 
							 cardNo = response.getEntiy().getCardNo();
							 imsi = response.getEntiy().getImsi();
							 cardNo = cardNo.replace("86", "");
						 }
					} catch (Exception e) {
						logger.error("FlowCardService ------》 getFlowCardByIccidOrImsi,查询模块手机号码异常：" +e.getMessage(),e);
						continue; //请求接口异常直接跳出此次循环
					} 
					
					PhysicalDevice physicalDevice = new PhysicalDevice();
					physicalDevice.setModulePhoneNumber(cardNo);
					physicalDevice.setIccid(deviceInfo.getIccid());
					physicalDevice.setImsi(imsi);
					physicalDevice.setImei(deviceInfo.getImei());
					physicalDevice.setMerchantId(Integer.parseInt(order.getOperatorMerchantNo()));
					physicalDevice.setToMerchantId(Integer.parseInt(order.getSendMerchantNo()));
					physicalDevice.setPackageId(Integer.parseInt(order.getPackageOne()));
					physicalDevice.setAndroidPackageId(Integer.parseInt(order.getPackageTwo()));
					physicalDevice.setBatchNo(order.getBatch());
					physicalDeviceList.add(physicalDevice);
				}
			}
			
			//记录同步老设备平台的结果
			if(physicalDeviceList.size() > 0){
				Map map = externalService.batchAddPhysicalDevice(order.getDeviceId(), physicalDeviceList);
				insertResultLog(map);
			}
		} catch (RpcServiceException e) {
			logger.error("更新设备数据异常," + e.getMessage(),e);
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_EXTERNAL_INTERFACE);
		}
		return 0; 
	}
	
	/**
	 * 
	 * @Title: insertResultLog 
	 * @Description: 插入同步日志信息
	 * @param @param map 
	 * @return void
	 * @throws
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void insertResultLog(Map map){
		//返回成功结果集
		List<PhysicalDevice> sucessList = (List<PhysicalDevice>) map.get("success");

		for (PhysicalDevice physicalDevice : sucessList) {
			RequestLog log = new RequestLog();
			log.setDeviceId(physicalDevice.getDeviceId());
			log.setIccid(physicalDevice.getIccid());
			log.setImei(physicalDevice.getImei());
			log.setErrorCode(0);
			log.setErrorMsg("成功");
			log.setResult(0);
			log.setDeletedFlag("N");
			log.setCreatedBy("admin");
			log.setUpdatedBy("admin");
			requestLogMapper.insert(log);
		}
		
		//返回失败结果集
		List<ResponseResult<PhysicalDevice>> result = (List<ResponseResult<PhysicalDevice>>) map.get("fail");
		for (ResponseResult<PhysicalDevice> responseResult : result) {
			RequestLog log = new RequestLog();
			log.setDeviceId(responseResult.getResult().getDeviceId());  //设备ID
			log.setIccid(responseResult.getResult().getIccid());     //iccid
			log.setImei(responseResult.getResult().getImei());      //imei
			log.setErrorCode(Integer.parseInt(responseResult.getErrorCode())); //错误编码
			log.setErrorMsg(responseResult.getErrorMsg());  //错误原因
			log.setResult(0);
			log.setDeletedFlag("N");
			log.setCreatedBy("admin");
			log.setUpdatedBy("admin");
			requestLogMapper.insert(log);
		}
	}

	public OrderInfoDetail getOrderInfoDetailByImei(String imei){
		logger.info("根据IMEI查询订单详情:{}",imei);
		OrderInfoDetail result;
		result = orderInfoDetailMapper.getOrderInfoDetailByImei(imei);
		return result;
	}
}
