package cn.com.glsx.merchant.supplychain.controller.jst;

import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.merchant.supplychain.config.Constants;
import cn.com.glsx.merchant.supplychain.config.SupplyadminProperty;
import cn.com.glsx.merchant.supplychain.util.JxcUtils;
import cn.com.glsx.merchant.supplychain.util.ThreadLocalCache;
import cn.com.glsx.merchant.supplychain.vo.BsMerchantStockDeviceVO;
import cn.com.glsx.merchant.supplychain.vo.DisMerchantStockDeviceVO;
import cn.com.glsx.merchant.supplychain.vo.JstShopOrderVO;
import cn.com.glsx.merchant.supplychain.web.session.Session;
import cn.com.glsx.supplychain.jst.dto.BsMerchantStockDeviceDTO;
import cn.com.glsx.supplychain.jst.dto.JstShopOrderDTO;
import cn.com.glsx.supplychain.jst.enums.JstErrorCodeEnum;
import cn.com.glsx.supplychain.jst.remote.JxBsMerchantRemote;
import cn.com.glsx.supplychain.model.bs.DealerUserInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

/**
 * @ClassName BsMerchantStockDeviceController
 * @Description 经销存设备明细
 * @Author xiex
 * @Date 2020/2/13 20:08
 * @Version 1.0
 */
@RestController
@RequestMapping("bsMerchantStockDevice")
public class BsMerchantStockDeviceController {


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JxBsMerchantRemote jxBsMerchantRemote;
    
   // @Autowired
   // protected HttpSession session;


    /**
     * 获取经销存系统设备明细分页列表
     * @param merchantStockDevice
     * @return
     */
    @RequestMapping("pageBsMerchantStockDevice")
    public ResultEntity<DisMerchantStockDeviceVO> pageBsMerchantStockDevice(@RequestBody DisMerchantStockDeviceVO merchantStockDevice)
    {
    	Session session = ThreadLocalCache.getSession();
        DealerUserInfo dealerUserInfo = (DealerUserInfo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
    	//DealerUserInfo dealerUserInfo = (DealerUserInfo) session.getAttribute(Constants.SESSION_LOGIN_USER);
    	logger.info("MerchantStockDeviceController::pageBsMerchantStockDevice start dealerUserInfo:{},merchantStockDevice:{}",dealerUserInfo,merchantStockDevice);
    	if(StringUtils.isEmpty(dealerUserInfo) || StringUtils.isEmpty(dealerUserInfo.getMerchantCode()))
    	{
    		logger.info("MerchantStockDeviceController::pageBsMerchantStockDevice not login or session time out");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(),null);
    	}
    	
    	RpcPagination<BsMerchantStockDeviceDTO> bsMerchantStockDeviceDTO = new RpcPagination<>();
    	bsMerchantStockDeviceDTO.setPageNum(merchantStockDevice.getPageNum());
    	bsMerchantStockDeviceDTO.setPageSize(merchantStockDevice.getPageSize());
    	BsMerchantStockDeviceDTO merchantStockDeviceDto = new BsMerchantStockDeviceDTO();
    	if(!StringUtils.isEmpty(merchantStockDevice.getInTimeStart()))
    	{
    		merchantStockDeviceDto.setInTimeStart(JxcUtils.getDateFromString(merchantStockDevice.getInTimeStart()));
    	}
    	if(!StringUtils.isEmpty(merchantStockDevice.getInTimeEnd()))
    	{
    		merchantStockDeviceDto.setInTimeEnd(JxcUtils.getDateFromString(merchantStockDevice.getInTimeEnd()));
    	}
    	if(!StringUtils.isEmpty(merchantStockDevice.getOutTimeStart()))
    	{
    		merchantStockDeviceDto.setOutTimeStart(JxcUtils.getDateFromString(merchantStockDevice.getOutTimeStart()));
    	}
    	if(!StringUtils.isEmpty(merchantStockDevice.getOutTimeEnd()))
    	{
    		merchantStockDeviceDto.setOutTimeEnd(JxcUtils.getDateFromString(merchantStockDevice.getOutTimeEnd()));
    	}
    	if(!StringUtils.isEmpty(merchantStockDevice.getSn()))
    	{
    		merchantStockDeviceDto.setSn(merchantStockDevice.getSn());
    	}
    	if(!StringUtils.isEmpty(merchantStockDevice.getMerchantName()))
    	{
    		merchantStockDeviceDto.setMerchantName(merchantStockDevice.getMerchantName());
    	}
    	if(!StringUtils.isEmpty(merchantStockDevice.getStatu()))
    	{
    		merchantStockDeviceDto.setStatu(merchantStockDevice.getStatu());
    	}
        if(!StringUtils.isEmpty(merchantStockDevice.getToMerchantName()))
        {
            merchantStockDeviceDto.setToMerchantName(merchantStockDevice.getToMerchantName());
        }
    	merchantStockDeviceDto.setMerchantCode(dealerUserInfo.getMerchantCode());
    	bsMerchantStockDeviceDTO.setCondition(merchantStockDeviceDto);
    	RpcResponse<RpcPagination<BsMerchantStockDeviceDTO>> rpcPaginationRpcResponse = jxBsMerchantRemote.pageBsMerchantStockDevice(bsMerchantStockDeviceDTO);
    	JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rpcPaginationRpcResponse.getError();
		if (!StringUtils.isEmpty(errCodeEnum)) {
			logger.info("MerchantStockDeviceController::pageBsMerchantStockDevice return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		RpcPagination<BsMerchantStockDeviceDTO> dtoResult = rpcPaginationRpcResponse.getResult();
		if(!StringUtils.isEmpty(dtoResult))
		{
			List<BsMerchantStockDeviceVO> voListResult = new ArrayList<BsMerchantStockDeviceVO>();
			List<BsMerchantStockDeviceDTO> dtoListResult = dtoResult.getList();
			if(!StringUtils.isEmpty(dtoListResult))
			{
				for(BsMerchantStockDeviceDTO dto:dtoListResult)
				{
					BsMerchantStockDeviceVO vo = new BsMerchantStockDeviceVO();
					BeanUtils.copyProperties(dto, vo);
					voListResult.add(vo);
				}
			}
			merchantStockDevice.setListStockDevice(voListResult);
			merchantStockDevice.setTotal((int)dtoResult.getTotal());
			merchantStockDevice.setPages(dtoResult.getPages());
			merchantStockDevice.setPageNum(dtoResult.getPageNum());
			merchantStockDevice.setPageSize(dtoResult.getPageSize());
		}
		logger.info("MerchantStockDeviceController::pageBsMerchantStockDevice end merchantStockDevice:{}",merchantStockDevice);
		return ResultEntity.success(merchantStockDevice);
    }
    
  /*  @RequestMapping("pageBsMerchantStockDevice")
    public ResultEntity<RpcPagination<BsMerchantStockDeviceVO>> pageBsMerchantStockDevice(@RequestBody RpcPagination<BsMerchantStockDeviceVO> bsMerchantStockDeviceVO) {
        
    	logger.info("BsMerchantStockDeviceController::pageJstShopOrder bsMerchantStockDeviceVO:{}",bsMerchantStockDeviceVO.getCondition());
        RpcResponse<RpcPagination<BsMerchantStockDeviceVO>> result = RpcResponse.success(new RpcPagination<>());
        RpcPagination<BsMerchantStockDeviceDTO> bsMerchantStockDeviceDTO = new RpcPagination<>();
        BeanUtils.copyProperties(bsMerchantStockDeviceVO, bsMerchantStockDeviceDTO);
        BsMerchantStockDeviceVO vo = bsMerchantStockDeviceVO.getCondition();
        if(vo != null){
            BsMerchantStockDeviceDTO dto = new BsMerchantStockDeviceDTO();
            BeanUtils.copyProperties(vo, dto);
            bsMerchantStockDeviceDTO.setCondition(dto);
        }
        RpcResponse<RpcPagination<BsMerchantStockDeviceDTO>> rpcPaginationRpcResponse = jxBsMerchantRemote.pageBsMerchantStockDevice(bsMerchantStockDeviceDTO);
        BeanUtils.copyProperties(rpcPaginationRpcResponse, result);
        List<BsMerchantStockDeviceDTO> dtoList = rpcPaginationRpcResponse.getResult().getList();
        List<BsMerchantStockDeviceVO> voList = new ArrayList<>();
        for(BsMerchantStockDeviceDTO  bmsd: dtoList){
            vo = new BsMerchantStockDeviceVO();
            BeanUtils.copyProperties(bmsd, vo);
            voList.add(vo);
        }
        result.getResult().setList(voList);
        return ResultEntity.result(result);
    }*/

    /**
     * 获取查询总数接口
     * @param
     * @return
     */
    @RequestMapping("getSumBsMerchantStockDeviceList")
    public ResultEntity<BsMerchantStockDeviceVO> getSumBsMerchantStockDeviceList(){
    	Session session = ThreadLocalCache.getSession();
        DealerUserInfo dealerUserInfo = (DealerUserInfo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
    	//DealerUserInfo dealerUserInfo = (DealerUserInfo) session.getAttribute(Constants.SESSION_LOGIN_USER);
    	logger.info("MerchantStockDeviceController::getSumBsMerchantStockDeviceList start dealerUserInfo:{}",dealerUserInfo);
    	if(StringUtils.isEmpty(dealerUserInfo) || StringUtils.isEmpty(dealerUserInfo.getMerchantCode()))
    	{
    		logger.info("MerchantStockDeviceController::getSumBsMerchantStockDeviceList not login or session time out");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(),null);
    	}
    	BsMerchantStockDeviceVO result = new BsMerchantStockDeviceVO();
    	BsMerchantStockDeviceDTO bsMerchantStockDeviceDTO = new BsMerchantStockDeviceDTO();
    	bsMerchantStockDeviceDTO.setMerchantCode(dealerUserInfo.getMerchantCode());
    	RpcResponse<BsMerchantStockDeviceDTO> rpcResponse = jxBsMerchantRemote.getSumBsMerchantStockDeviceList(bsMerchantStockDeviceDTO);
    	if(rpcResponse != null && rpcResponse.getResult() != null){
            BeanUtils.copyProperties(rpcResponse.getResult(), result);
        }
    	return ResultEntity.result(RpcResponse.success(result));
    }
}
