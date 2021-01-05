package cn.com.glsx.supplychain.jxc.service;

import org.oreframework.boot.autoconfigure.snowflake.algorithm.SnowflakeWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.jxc.common.Constants;
import cn.com.glsx.supplychain.jxc.dto.STKWarningSetDTO;
import cn.com.glsx.supplychain.jxc.enums.JXCErrorCodeEnum;
import cn.com.glsx.supplychain.jxc.enums.WarningSetChannelTypeEnum;
import cn.com.glsx.supplychain.jxc.enums.WarnningSetTypeEnum;
import cn.com.glsx.supplychain.jxc.mapper.STKMerchantWarningSetMapper;
import cn.com.glsx.supplychain.jxc.model.STKMerchantWarningSet;
import cn.com.glsx.supplychain.jxc.util.JxcUtils;

@Service
public class STKWarningSetService {
	private static final Logger logger = LoggerFactory.getLogger(STKWarningSetService.class);
	@Autowired
	private STKMerchantWarningSetMapper merchantWarningSetMapper;
	@Autowired
	private JXCMTAttribInfoService jxcmtAttribInfoService;
	@Autowired
    private SnowflakeWorker snowflakeWorker;
	
	public Integer updateMerchantWarningSet(STKWarningSetDTO record){
		STKMerchantWarningSet dbRecord = new STKMerchantWarningSet();
		dbRecord.setThresholdHigh(record.getThresholdHigh());
		dbRecord.setThresholdLow(record.getThresholdLow());
		dbRecord.setUpdatedBy(record.getConsumer());
		dbRecord.setUpdatedDate(JxcUtils.getNowDate());
		dbRecord.setDeletedFlag(record.getDeletedFlag());
		Example example = new Example(STKMerchantWarningSet.class);
		example.createCriteria().andEqualTo("warningCode", record.getWarningCode());
		return merchantWarningSetMapper.updateByExampleSelective(dbRecord, example);
	}
	
	public Integer subMerchantWarningSet(STKWarningSetDTO record) throws RpcServiceException{
		if(!this.invalidWarningType(record.getWarningType())){
			 throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		if(!this.invalidWarningChannelType(record.getChannelType())){
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		if(record.getChannelType().equals(WarningSetChannelTypeEnum.WARNNING_CHANNEL_TYPE_CH.getCode())){
			if(StringUtils.isEmpty(record.getMerchantChannelId()) || StringUtils.isEmpty(record.getMerchantChannelName())){
				throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_INVALID_PARAM);
			}
		}else{
			if(StringUtils.isEmpty(record.getMerchantCode()) || StringUtils.isEmpty(record.getMerchantName())){
				throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_INVALID_PARAM);
			}
		}
		if(null == record.getThresholdLow() && null == record.getThresholdHigh()){
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		STKMerchantWarningSet condition = new STKMerchantWarningSet();
		condition.setWarningType(record.getWarningType());
		condition.setChannelType(record.getChannelType());
		if(!StringUtils.isEmpty(record.getMerchantChannelId())){
			Integer merchantChannelId = Integer.valueOf(record.getMerchantChannelId());
			condition.setMerchantChannelId(String.valueOf(jxcmtAttribInfoService.getDbMerchantChannelFromAttribInfo(merchantChannelId)));
		}
		if(!StringUtils.isEmpty(record.getMerchantChannelName())){
			condition.setMerchantChannelName(record.getMerchantChannelName());
		}
		if(!StringUtils.isEmpty(record.getMerchantCode())){
			condition.setMerchantCode(record.getMerchantCode());
		}
		if(!StringUtils.isEmpty(record.getMerchantName())){
			condition.setMerchantName(record.getMerchantName());
		}
		condition.setDeviceType(record.getDeviceType());
		condition.setDeletedFlag("N");
		STKMerchantWarningSet dbWarningSet = merchantWarningSetMapper.selectOne(condition);
		if(null != dbWarningSet){
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_WARNING_SET_REPEATED);
		}
		try{
			condition.setThresholdHigh(record.getThresholdHigh());
			condition.setThresholdLow(record.getThresholdLow());
			condition.setCreatedBy(record.getConsumer());
			condition.setUpdatedBy(record.getConsumer());
			condition.setCreatedDate(JxcUtils.getNowDate());
			condition.setUpdatedDate(JxcUtils.getNowDate());
			condition.setWarningCode(Constants.STKM_WARNING_PREFIX + JxcUtils.generatorOrderCode(snowflakeWorker));
			merchantWarningSetMapper.insertSelective(condition);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
		}
		return 0;
	}
	
	public Page<STKWarningSetDTO> pageWarningSet(RpcPagination<STKWarningSetDTO> pagination){
		STKMerchantWarningSet condition = new STKMerchantWarningSet();
		condition.setChannelType(pagination.getCondition().getChannelType());
		if(!StringUtils.isEmpty(pagination.getCondition().getMerchantChannelId())){
			Integer merchantChannelId = Integer.valueOf(pagination.getCondition().getMerchantChannelId());
			condition.setMerchantChannelId(String.valueOf(jxcmtAttribInfoService.getDbMerchantChannelFromAttribInfo(merchantChannelId)));
		}
		condition.setWarningType(pagination.getCondition().getWarningType());
		condition.setMerchantCode(pagination.getCondition().getMerchantCode());
		condition.setMerchantName(pagination.getCondition().getMerchantName());
		condition.setDeviceType(pagination.getCondition().getDeviceType());
		condition.setDeletedFlag("N");
		PageHelper.startPage(pagination.getPageNum(), pagination.getPageSize());
		Page<STKWarningSetDTO> pageWarningSet = merchantWarningSetMapper.pageWarningSet(condition);
		if(null == pageWarningSet){
			return null;
		}
		if(null == pageWarningSet.getResult()){
			return null;
		}
		for(STKWarningSetDTO setdto:pageWarningSet.getResult()){
			if(!StringUtils.isEmpty(setdto.getMerchantChannelId())){
				Integer merchantChannelId = Integer.valueOf(setdto.getMerchantChannelId());
				setdto.setMerchantChannelId(jxcmtAttribInfoService.getMerchantChannelFromDbMerchantChannel(merchantChannelId.byteValue()).toString());
			}
			setdto.setChannelTypeName(getWarningChannelTypeName(setdto.getChannelType()));
			setdto.setWarningTypeName(getWarningTypeName(setdto.getWarningType()));
		}
		return pageWarningSet;
	}
	
	private boolean invalidWarningType(String warningType){
		if(StringUtils.isEmpty(warningType)){
			return false;
		}
		if(warningType.equals(WarnningSetTypeEnum.WARNNING_TYPE_KUXIAO.getCode())){
			return true;
		}
		if(warningType.equals(WarnningSetTypeEnum.WARNNING_TYPE_DAIZHI.getCode())){
			return true;
		}
		return false;
	}
	
	private boolean invalidWarningChannelType(String warningChannelType){
		if(StringUtils.isEmpty(warningChannelType)){
			return false;
		}
		if(warningChannelType.equals(WarningSetChannelTypeEnum.WARNNING_CHANNEL_TYPE_CH.getCode())){
			return true;
		}
		if(warningChannelType.equals(WarningSetChannelTypeEnum.WARNNING_CHANNEL_TYPE_ME.getCode())){
			return true;
		}
		return false;
	}
	
	private String getWarningTypeName(String warningType){
		String result = "";
		if(StringUtils.isEmpty(warningType)){
			return result;
		}
		if(warningType.equals(WarnningSetTypeEnum.WARNNING_TYPE_KUXIAO.getCode())){
			return WarnningSetTypeEnum.WARNNING_TYPE_KUXIAO.getName();
		}
		if(warningType.equals(WarnningSetTypeEnum.WARNNING_TYPE_DAIZHI.getCode())){
			return WarnningSetTypeEnum.WARNNING_TYPE_DAIZHI.getName();
		}
		return result;
	}
	
	private String getWarningChannelTypeName(String warningChannelType){
		String result = "";
		if(StringUtils.isEmpty(warningChannelType)){
			return result;
		}
		if(warningChannelType.equals(WarningSetChannelTypeEnum.WARNNING_CHANNEL_TYPE_CH.getCode())){
			return WarningSetChannelTypeEnum.WARNNING_CHANNEL_TYPE_CH.getName();
		}
		if(warningChannelType.equals(WarningSetChannelTypeEnum.WARNNING_CHANNEL_TYPE_ME.getCode())){
			return WarningSetChannelTypeEnum.WARNNING_CHANNEL_TYPE_ME.getName();
		}
		return result;
	}
}
