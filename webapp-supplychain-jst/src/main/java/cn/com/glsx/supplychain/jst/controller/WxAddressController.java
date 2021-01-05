package cn.com.glsx.supplychain.jst.controller;

import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.jst.common.Constants;
import cn.com.glsx.supplychain.jst.convert.BsAddressHttpConvert;
import cn.com.glsx.supplychain.jst.dto.BsAddressDTO;
import cn.com.glsx.supplychain.jst.dto.JstShopDTO;
import cn.com.glsx.supplychain.jst.enums.JstErrorCodeEnum;
import cn.com.glsx.supplychain.jst.remote.WxBsMerchantRemote;
import cn.com.glsx.supplychain.jst.utils.JstUtils;
import cn.com.glsx.supplychain.jst.utils.JxcUtils;
import cn.com.glsx.supplychain.jst.utils.ThreadLocalCache;
import cn.com.glsx.supplychain.jst.vo.*;
import cn.com.glsx.supplychain.jst.web.session.Session;
import cn.com.glsx.supplychain.jxc.dto.JXCMTBsAddressDTO;
import cn.com.glsx.supplychain.jxc.enums.JXCErrorCodeEnum;
import cn.com.glsx.supplychain.jxc.remote.JxcCommonRemote;
import com.glsx.biz.common.base.entity.Area;
import com.glsx.biz.common.base.entity.City;
import com.glsx.biz.common.base.entity.Province;
import com.glsx.biz.common.base.service.AreaService;
import com.glsx.biz.common.base.service.CityService;
import com.glsx.biz.common.base.service.ProvinceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title: WxCartController.java
 * @Description: 微信小程序地址
 * @author liuquan
 * @version V1.0
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@Api(value="/address",tags="微信小程序地址")
@RestController
@RequestMapping("address")
public class WxAddressController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
//	@Autowired
//	protected HttpSession session;

	@Autowired
	private WxBsMerchantRemote wxBsMerchantRemote;
	@Autowired
	private JxcCommonRemote jxcCommonRemote;
	@Autowired
    private ProvinceService provinceService;
    @Autowired
    private CityService cityService;
    @Autowired
    private AreaService areaService;
	
	/**
	 * 获取登陆方商户地址列表
	 */
	@RequestMapping("wxListAddress")
	public ResultEntity<List<AddressVO>> wxListAddress()
	{
		Session session = ThreadLocalCache.getSession();
		UserInfoVo userInfoVo = (UserInfoVo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
	//	UserInfoVo userInfoVo = (UserInfoVo)session.getAttribute(cn.com.glsx.supplychain.jst.common.Constants.SESSION_LOGIN_USER);
		logger.info("WxAddressController::wxListAddress start userInfoVo:{}", userInfoVo);
		if(StringUtils.isEmpty(userInfoVo))
		{
			logger.info("WxAddressController::wxListAddress account not login or session time out");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(),null);
		}
		BsAddressDTO condition = new BsAddressDTO();
		condition.setMerchantCode(userInfoVo.getMerchantCode());
		JstUtils.setBaseDTO(condition);
		RpcResponse<List<BsAddressDTO>> rsp = wxBsMerchantRemote.listBsAddressByMerchantCode(condition);
		JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
		if (!StringUtils.isEmpty(errCodeEnum)) {
			logger.info("WxLoginController::wxListAddress return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		List<BsAddressDTO> result = rsp.getResult();
		List<AddressVO> listBsAddressVO = BsAddressHttpConvert.convertList(result);
		logger.info("WxAddressController::wxListAddress end listBsAddressVO:{}", listBsAddressVO);
		return ResultEntity.success(listBsAddressVO);
	}

	@ApiOperation(value="小程序获取服务商地址列表",notes="小程序获取服务商地址列表")
	@PostMapping("listServiceProviderAddress")
	public ResultEntity<List<JXCMTBsAddressDTO>> listServiceProviderAddress(@RequestBody JXCMTBsAddressListQueryVO queryVo){
		Session session = ThreadLocalCache.getSession();
		UserInfoVo userInfoVo = (UserInfoVo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
		//	UserInfoVo userInfoVo = (UserInfoVo)session.getAttribute(cn.com.glsx.supplychain.jst.common.Constants.SESSION_LOGIN_USER);
		logger.info("WxAddressController::listServiceProviderAddress start userInfoVo:{}", userInfoVo);
		if(StringUtils.isEmpty(userInfoVo))
		{
			logger.info("WxAddressController::listServiceProviderAddress account not login or session time out");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(),null);
		}
		JXCMTBsAddressDTO dtoCondition = new JXCMTBsAddressDTO();
		dtoCondition.setProvinceId(queryVo.getProvinceId());
		dtoCondition.setCityId(queryVo.getCityId());
		dtoCondition.setAreaId(queryVo.getAreaId());
		if(!StringUtils.isEmpty(queryVo.getMerchantCode())){
			dtoCondition.setMerchantCode(queryVo.getMerchantCode());
		}else{
			dtoCondition.setMerchantCode(userInfoVo.getMerchantCode());
		}

		JxcUtils.setJXCBaseDTO(dtoCondition);
		RpcResponse<List<JXCMTBsAddressDTO>> rpcResponse = jxcCommonRemote.listServiceProviderAddress(dtoCondition);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
		if (!StringUtils.isEmpty(errCodeEnum)) {
			logger.info("WxAddressController::listServiceProviderAddress return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		logger.info("WxAddressController::listServiceProviderAddress end result:{}", rpcResponse.getResult());
		return ResultEntity.success(rpcResponse.getResult());
	}
	
	/**
	 * 获取指定门店收获地址接口
	 */
	@RequestMapping("wxListShopAddress")
	public ResultEntity<List<AddressVO>> wxListShopAddress(@RequestBody JstShopVO jstShopVo){
		Session session = ThreadLocalCache.getSession();
		UserInfoVo userInfoVo = (UserInfoVo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
		logger.info("WxAddressController::wxListShopAddress start userInfoVo:{},jstShopVo:{}", userInfoVo,jstShopVo);
		if(StringUtils.isEmpty(userInfoVo))
		{
			logger.info("WxAddressController::wxListShopAddress account not login or session time out");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(),null);
		}
		if(StringUtils.isEmpty(jstShopVo)){
			logger.info("WxAddressController::wxListShopAddress param err");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getCode(), JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible(),null);
		}
		if(StringUtils.isEmpty(jstShopVo.getShopCode())){
			logger.info("WxAddressController::wxListShopAddress param err");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getCode(), JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible(),null);
		}
		JstShopDTO jstShopDto = new JstShopDTO();
		jstShopDto.setShopCode(jstShopVo.getShopCode());
		JstUtils.setBaseDTO(jstShopDto);
		RpcResponse<JstShopDTO> rpcResponse = wxBsMerchantRemote.getJspShopByShopCode(jstShopDto);
		JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rpcResponse.getError();
		if (!StringUtils.isEmpty(errCodeEnum)) {
			logger.info("WxLoginController::wxListShopAddress return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		jstShopDto = rpcResponse.getResult();
		if(null == jstShopDto){
			logger.info("WxAddressController::wxListShopAddress param err");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_NULL_SHOP.getCode(), JstErrorCodeEnum.ERRCODE_NULL_SHOP.getDescrible(),null);
		}
		BsAddressDTO addressDto = new BsAddressDTO();
		addressDto.setMerchantCode(jstShopDto.getShopMerchantCode());
		JstUtils.setBaseDTO(addressDto);
		RpcResponse<List<BsAddressDTO>> rsp = wxBsMerchantRemote.listBsAddressByMerchantCode(addressDto);
		JstErrorCodeEnum errEnum = (JstErrorCodeEnum) rsp.getError();
		if (!StringUtils.isEmpty(errEnum)) {
			logger.info("WxLoginController::wxListShopAddress return"
					+ errEnum.getDescrible());
			return ResultEntity.error(errEnum.getCode(),
					errEnum.getDescrible());
		}
		List<BsAddressDTO> result = rsp.getResult();
		List<AddressVO> listBsAddressVO = BsAddressHttpConvert.convertList(result);
		logger.info("WxAddressController::wxListShopAddress end listBsAddressVO:{}", listBsAddressVO);
		return ResultEntity.success(listBsAddressVO);
	}
	
	
	/**
	 * 删除登陆方商户地址
	 */
	@RequestMapping("wxRemoveAddress")
	public ResultEntity<Integer> wxRemoveAddress(@RequestBody AddressVO address)
	{
		Session session = ThreadLocalCache.getSession();
		UserInfoVo userInfoVo = (UserInfoVo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
		logger.info("WxAddressController::wxRemoveAddress start userInfoVo:{},address:{}", userInfoVo,address);
		if(StringUtils.isEmpty(userInfoVo))
		{
			logger.info("WxAddressController::wxRemoveAddress account not login or session time out");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(),null);
		}
		BsAddressDTO condition = new BsAddressDTO();
		condition.setMerchantCode(userInfoVo.getMerchantCode());
		condition.setId(address.getId());
		JstUtils.setBaseDTO(condition);
		RpcResponse<Integer> rsp = wxBsMerchantRemote.removeBsAddress(condition);
		JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
		if (!StringUtils.isEmpty(errCodeEnum)) {
			logger.info("WxLoginController::wxRemoveAddress return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		Integer result = rsp.getResult();
		logger.info("WxAddressController::wxRemoveAddress result:{}", result);
		return ResultEntity.success(result);	
	}
	
	
	/**
	 * 添加登陆方商户地址
	 */
	@RequestMapping("wxAddAddress")
	public ResultEntity<AddressVO> wxAddAddress(@RequestBody AddressVO address)
	{
		Session session = ThreadLocalCache.getSession();
		UserInfoVo userInfoVo = (UserInfoVo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
//		UserInfoVo userInfoVo = (UserInfoVo)session.getAttribute(cn.com.glsx.supplychain.jst.common.Constants.SESSION_LOGIN_USER);
		logger.info("WxAddressController::wxAddAddress start userInfoVo:{},address:{}", userInfoVo,address);
		if(StringUtils.isEmpty(userInfoVo))
		{
			logger.info("WxAddressController::wxAddAddress account not login or session time out");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(),null);
		}
		BsAddressDTO dtoConditon = BsAddressHttpConvert.convertVO(address);
		JstUtils.setBaseDTO(dtoConditon);
		RpcResponse<BsAddressDTO> rsp = wxBsMerchantRemote.saveBsAddress(dtoConditon);
		JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
		if (!StringUtils.isEmpty(errCodeEnum)) {
			logger.info("WxLoginController::wxListAddress return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		BsAddressDTO result = rsp.getResult();
		AddressVO vo = BsAddressHttpConvert.convertDTO(result);
		logger.info("WxAddressController::wxAddAddress end vo:{}", vo);
		return ResultEntity.success(vo);
	}
	
	@ApiOperation(value="获取省份列表",notes="获取省份列表")
	@PostMapping("listProvinces")
	public ResultEntity<List<JXCMTBsProviceVO>> listProvinces(){
		List<JXCMTBsProviceVO> listResult = new ArrayList<>();
		List<Province> rpcResponse = provinceService.getAllList();
		if(null == rpcResponse){
			return null;
		}
		JXCMTBsProviceVO vo =null;
		for(Province province:rpcResponse){
			vo=new JXCMTBsProviceVO();
			vo.setProvinceId(province.getPid());
			vo.setProvinceName(province.getProvince());
			listResult.add(vo);
		}
		logger.info("JxcAddressController::listProvinces end listResult:{}", listResult);
		return ResultEntity.success(listResult);
	}
	
	@ApiOperation(value="获取省份下城市列表",notes="获取省份下城市列表")
	@PostMapping("listCities")
	public ResultEntity<List<JXCMTBsCityVO>> listCities(@RequestBody JXCMTBsProviceVO proviceVo){
    	List<JXCMTBsCityVO> listResult = new ArrayList<>();
    	List<City> rpcResponse = cityService.getListByProvinceId(proviceVo.getProvinceId());
    	if(null == rpcResponse){
			return null;
		}
    	JXCMTBsCityVO vo = null;
    	for(City city:rpcResponse){
    		vo=new JXCMTBsCityVO();
    		vo.setCityId(city.getCid());
    		vo.setCityName(city.getCity());
    		listResult.add(vo);
    	}
    	logger.info("JxcAddressController::listCities end listResult:{}", listResult);
		return ResultEntity.success(listResult);
	}
	
	@ApiOperation(value="获取城市下区域列表",notes="获取城市下区域列表")
	@PostMapping("listAreas")
	public ResultEntity<List<JXCMTBsAreaVO>> listAreas(@RequestBody JXCMTBsCityVO cityVo){
    	List<JXCMTBsAreaVO> listResult = new ArrayList<>();
    	List<Area> rpcResponse = areaService.getListByCityId(cityVo.getCityId());
    	if(null == rpcResponse){
			return null;
		}
    	JXCMTBsAreaVO vo = null;
    	for(Area area:rpcResponse){
			vo=new JXCMTBsAreaVO();
    		vo.setAreaId(area.getAid());
    		vo.setAreaName(area.getArea());
    		listResult.add(vo);
    	}
    	logger.info("JxcAddressController::listAreas end listResult:{}", listResult);
		return ResultEntity.success(listResult);
	}

}
