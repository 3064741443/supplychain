package cn.com.glsx.merchant.supplychain.controller.jxc;

import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.merchant.supplychain.config.Constants;
import cn.com.glsx.merchant.supplychain.util.JxcUtils;
import cn.com.glsx.merchant.supplychain.util.ThreadLocalCache;
import cn.com.glsx.merchant.supplychain.vo.jxc.*;
import cn.com.glsx.merchant.supplychain.web.session.Session;
import cn.com.glsx.supplychain.jxc.dto.JXCMTBsAddressDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTBsAddressDelDTO;
import cn.com.glsx.supplychain.jxc.enums.JXCErrorCodeEnum;
import cn.com.glsx.supplychain.jxc.remote.JxcCommonRemote;
import cn.com.glsx.supplychain.model.bs.DealerUserInfo;
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
 * @Title: JxcProductController.java
 * @Description: 820改版 经销存系统小程序 PC端-地址簿信息
 * @author liuquan
 * @version V1.0
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@Api(value="/jxcAddress",tags="820改版 经销存系统小程序 PC端-地址簿信息")
@RestController
@RequestMapping("jxcAddress")
public class JxcAddressController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private JxcCommonRemote jxcCommonRemote;
	@Autowired
    private ProvinceService provinceService;
    @Autowired
    private CityService cityService;
    @Autowired
    private AreaService areaService;
	
	@ApiOperation(value="获取商户地址列表",notes="获取商户地址列表")
	@PostMapping("listAdddress")
	public ResultEntity<List<JXCMTBsAddressDTO>> listAddress(@RequestBody JXCMTBsAddressListQueryVO queryVo){
		Session session = ThreadLocalCache.getSession();
        DealerUserInfo dealerUserInfo = (DealerUserInfo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
        logger.info("JxcAddressController::listAddress start dealerUserInfo:{},queryVo:{}",dealerUserInfo,queryVo);
    	if(StringUtils.isEmpty(dealerUserInfo) || StringUtils.isEmpty(dealerUserInfo.getMerchantCode()))
    	{
    		logger.info("JxcAddressController::listAddress not login or session time out");
			return ResultEntity.error(JXCErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JXCErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(),null);
    	}
    	JXCMTBsAddressDTO dtoCondition = new JXCMTBsAddressDTO();
    	dtoCondition.setProvinceId(queryVo.getProvinceId());
    	dtoCondition.setCityId(queryVo.getCityId());
    	dtoCondition.setAreaId(queryVo.getAreaId());
    	if(!StringUtils.isEmpty(queryVo.getMerchantCode())){
    		dtoCondition.setMerchantCode(queryVo.getMerchantCode());
    	}else{
    		dtoCondition.setMerchantCode(dealerUserInfo.getMerchantCode());
    	}
  
    	JxcUtils.setJXCBaseDTO(dtoCondition);
    	RpcResponse<List<JXCMTBsAddressDTO>> rpcResponse = jxcCommonRemote.listAddress(dtoCondition);
    	JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (!StringUtils.isEmpty(errCodeEnum)) {
            logger.info("JxcAddressController::listAddress return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        logger.info("JxcAddressController::listAddress end result:{}", rpcResponse.getResult());
        return ResultEntity.success(rpcResponse.getResult());
	}

	@ApiOperation(value="pc端获取服务商地址列表",notes="pc端获取服务商地址列表")
	@PostMapping("listServiceProviderAddress")
	public ResultEntity<List<JXCMTBsAddressDTO>> listServiceProviderAddress(@RequestBody JXCMTBsAddressListQueryVO queryVo){
		Session session = ThreadLocalCache.getSession();
		DealerUserInfo dealerUserInfo = (DealerUserInfo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
		logger.info("JxcAddressController::listAddress start dealerUserInfo:{},queryVo:{}",dealerUserInfo,queryVo);
		if(StringUtils.isEmpty(dealerUserInfo) || StringUtils.isEmpty(dealerUserInfo.getMerchantCode()))
		{
			logger.info("JxcAddressController::listAddress not login or session time out");
			return ResultEntity.error(JXCErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JXCErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(),null);
		}
		JXCMTBsAddressDTO dtoCondition = new JXCMTBsAddressDTO();
		dtoCondition.setProvinceId(queryVo.getProvinceId());
		dtoCondition.setCityId(queryVo.getCityId());
		dtoCondition.setAreaId(queryVo.getAreaId());
		if(!StringUtils.isEmpty(queryVo.getMerchantCode())){
			dtoCondition.setMerchantCode(queryVo.getMerchantCode());
		}else{
			dtoCondition.setMerchantCode(dealerUserInfo.getMerchantCode());
		}

		JxcUtils.setJXCBaseDTO(dtoCondition);
		RpcResponse<List<JXCMTBsAddressDTO>> rpcResponse = jxcCommonRemote.listServiceProviderAddress(dtoCondition);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
		if (!StringUtils.isEmpty(errCodeEnum)) {
			logger.info("JxcAddressController::listAddress return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		logger.info("JxcAddressController::listAddress end result:{}", rpcResponse.getResult());
		return ResultEntity.success(rpcResponse.getResult());
	}
	
 
	@ApiOperation(value="根据id获取地址详情",notes="根据id获取地址详情")
	@PostMapping("getAddress")
	public ResultEntity<JXCMTBsAddressDTO> getAddress(@RequestBody JXCMTBsAddressGetVO getVo){
		Session session = ThreadLocalCache.getSession();
        DealerUserInfo dealerUserInfo = (DealerUserInfo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
        logger.info("JxcAddressController::getAddress start dealerUserInfo:{},getVo:{}",dealerUserInfo,getVo);
    	if(StringUtils.isEmpty(dealerUserInfo) || StringUtils.isEmpty(dealerUserInfo.getMerchantCode()))
    	{
    		logger.info("JxcAddressController::getAddress not login or session time out");
			return ResultEntity.error(JXCErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JXCErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(),null);
    	} 
    	JXCMTBsAddressDTO dtoCondition = new JXCMTBsAddressDTO();
    	dtoCondition.setId(getVo.getId());
    	JxcUtils.setJXCBaseDTO(dtoCondition);
    	RpcResponse<JXCMTBsAddressDTO> rpcResponse = jxcCommonRemote.getAddress(dtoCondition);
    	JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (!StringUtils.isEmpty(errCodeEnum)) {
            logger.info("JxcAddressController::getAddress return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        logger.info("JxcAddressController::getAddress end result:{}", rpcResponse.getResult());
        return ResultEntity.success(rpcResponse.getResult());
	}
	
	@ApiOperation(value="添加商户地址",notes="添加商户地址")
	@PostMapping("addAddress")
	public ResultEntity<Integer> addAddress(@RequestBody JXCMTBsAddressDTO addressDto){
		
		return null;
	}
	 
	@ApiOperation(value="根据id修改地址信息",notes="根据id修改地址信息")
	@PostMapping("updateAddress")
	public ResultEntity<Integer> updateAddress(@RequestBody JXCMTBsAddressDTO addressDto){
		Session session = ThreadLocalCache.getSession();
        DealerUserInfo dealerUserInfo = (DealerUserInfo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
        logger.info("JxcAddressController::updateAddress start dealerUserInfo:{},addressDto:{}",dealerUserInfo,addressDto);
    	if(StringUtils.isEmpty(dealerUserInfo) || StringUtils.isEmpty(dealerUserInfo.getMerchantCode()))
    	{
    		logger.info("JxcAddressController::updateAddress not login or session time out");
			return ResultEntity.error(JXCErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JXCErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(),null);
    	} 
    	JxcUtils.setJXCBaseDTO(addressDto);
    	RpcResponse<Integer> rpcResponse = jxcCommonRemote.updateAddress(addressDto);
    	JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (!StringUtils.isEmpty(errCodeEnum)) {
            logger.info("JxcAddressController::updateAddress return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        logger.info("JxcAddressController::updateAddress end result:{}", rpcResponse.getResult());
		return ResultEntity.success(rpcResponse.getResult());
	}
	
	@ApiOperation(value="删除商户地址信息",notes="删除商户地址信息")
	@PostMapping("delAddress")
	public ResultEntity<Integer> delAddress(@RequestBody JXCMTBsAddressGetVO getVo){
		Session session = ThreadLocalCache.getSession();
        DealerUserInfo dealerUserInfo = (DealerUserInfo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
        logger.info("JxcAddressController::delAddress start dealerUserInfo:{},getVo:{}",dealerUserInfo,getVo);
    	if(StringUtils.isEmpty(dealerUserInfo) || StringUtils.isEmpty(dealerUserInfo.getMerchantCode()))
    	{
    		logger.info("JxcAddressController::delAddress not login or session time out");
			return ResultEntity.error(JXCErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JXCErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(),null);
    	}  
    	JXCMTBsAddressDTO dtoCondition = new JXCMTBsAddressDTO();
    	dtoCondition.setId(getVo.getId());
    	JxcUtils.setJXCBaseDTO(dtoCondition);
    	RpcResponse<Integer> rpcResponse = jxcCommonRemote.delAddress(dtoCondition);
    	JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
    	if (!StringUtils.isEmpty(errCodeEnum)) {
            logger.info("JxcAddressController::delAddress return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
    	logger.info("JxcAddressController::delAddress end result:{}", rpcResponse.getResult());
		return ResultEntity.success(rpcResponse.getResult());
	}
	 
	@ApiOperation(value="批量删除商户地址信息",notes="批量删除商户地址信息")
	@PostMapping("batchDelAddress")
	public ResultEntity<Integer> batchDelAddress(@RequestBody JXCMTBsAddressDelVO delVo){
		Session session = ThreadLocalCache.getSession();
        DealerUserInfo dealerUserInfo = (DealerUserInfo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
        logger.info("JxcAddressController::batchDelAddress start dealerUserInfo:{},delVo:{}",dealerUserInfo,delVo);
    	if(StringUtils.isEmpty(dealerUserInfo) || StringUtils.isEmpty(dealerUserInfo.getMerchantCode()))
    	{
    		logger.info("JxcAddressController::batchDelAddress not login or session time out");
			return ResultEntity.error(JXCErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JXCErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(),null);
    	}  
    	JXCMTBsAddressDelDTO dtoCondition = new JXCMTBsAddressDelDTO();
    	dtoCondition.setListIds(delVo.getListIds());
    	JxcUtils.setJXCBaseDTO(dtoCondition);
    	RpcResponse<Integer> rpcResponse = jxcCommonRemote.batchDelAddress(dtoCondition);
    	JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
    	if (!StringUtils.isEmpty(errCodeEnum)) {
            logger.info("JxcAddressController::batchDelAddress return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
    	logger.info("JxcAddressController::batchDelAddress end result:{}", rpcResponse.getResult());
		return ResultEntity.success(rpcResponse.getResult());
	}
	
	
	
	@ApiOperation(value="获取省份列表",notes="获取省份列表")
	@PostMapping("listProvinces")
	public ResultEntity<List<JXCMTBsProviceVO>> listProvinces(){
		Session session = ThreadLocalCache.getSession();
        DealerUserInfo dealerUserInfo = (DealerUserInfo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
        logger.info("JxcAddressController::listProvinces start dealerUserInfo:{}",dealerUserInfo);
    	if(StringUtils.isEmpty(dealerUserInfo) || StringUtils.isEmpty(dealerUserInfo.getMerchantCode()))
    	{
    		logger.info("JxcAddressController::listProvinces not login or session time out");
			return ResultEntity.error(JXCErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JXCErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(),null);
    	}  
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
		Session session = ThreadLocalCache.getSession();
        DealerUserInfo dealerUserInfo = (DealerUserInfo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
        logger.info("JxcAddressController::listCities start dealerUserInfo:{}",dealerUserInfo);
    	if(StringUtils.isEmpty(dealerUserInfo) || StringUtils.isEmpty(dealerUserInfo.getMerchantCode()))
    	{
    		logger.info("JxcAddressController::listCities not login or session time out");
			return ResultEntity.error(JXCErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JXCErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(),null);
    	}
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
		Session session = ThreadLocalCache.getSession();
        DealerUserInfo dealerUserInfo = (DealerUserInfo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
        logger.info("JxcAddressController::listAreas start dealerUserInfo:{}",dealerUserInfo);
    	if(StringUtils.isEmpty(dealerUserInfo) || StringUtils.isEmpty(dealerUserInfo.getMerchantCode()))
    	{
    		logger.info("JxcAddressController::listAreas not login or session time out");
			return ResultEntity.error(JXCErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JXCErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(),null);
    	}
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
