package cn.com.glsx.supplychain.jxc.remote;

import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.framework.core.util.RpcAssert;
import cn.com.glsx.supplychain.jxc.dto.*;
import cn.com.glsx.supplychain.jxc.enums.JXCErrorCodeEnum;
import cn.com.glsx.supplychain.jxc.manager.JXCServicePackageService;
import cn.com.glsx.supplychain.jxc.model.JXCMTAttribInfo;
import cn.com.glsx.supplychain.jxc.service.*;
import com.alibaba.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ql
 * @version V1.0
 * @Title: JxcCommonRemote.java
 * @Description: 进销存重构地址 类型等公共服务接口
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@Component("JxcCommonRemote")
@Service(version = "1.0.0", protocol = "dubbo", timeout = 10000)
public class JxcCommonRemoteImpl implements JxcCommonRemote {
	private static final Logger logger = LoggerFactory.getLogger(JxcCommonRemoteImpl.class);
	@Autowired
	private JXCMTBsAddressService JxcmtBsAddressService;
	@Autowired
	private JXCMTAttribInfoService JxcmtAttribInfoService;
	@Autowired
	private JXCMTBsSubjectService JxcmtBsSubjectService;
	@Autowired
	private JXCMTDeviceTypeService JxcmtDeviceTypeService;
	@Autowired
	private JXCServicePackageService JxcmtServicePackageService;
	@Autowired
	private JXCMTWarehouseService JxcmtWarehouseService;
	@Autowired
	private JXCMTAttribManaService JxcmtAttribManaService;
	@Autowired
	private JXCMTMaterialInfoService JxcmtMaterialInfoService;
	@Autowired
	private JXCMTBsDealerUserInfoService JxcmtBsDealerUserInfoService;
	@Autowired
	private JXCAMKcRelationService JxcamkcRelationService;
	
	
	@Override
	public RpcResponse<List<JXCMTBsAddressDTO>> listAddress(
			JXCMTBsAddressDTO record) {
		logger.info("JxcCommonRemoteImpl::listAddress record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getMerchantCode(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getMerchantCode() must not be null");
			return RpcResponse.success(JxcmtBsAddressService.listAddress(record));
		}catch (Exception e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
		}
	}

    @Override
    public RpcResponse<List<JXCMTBsAddressDTO>> listServiceProviderAddress(
            JXCMTBsAddressDTO record) {
        logger.info("JxcCommonRemoteImpl::listAddress record::{}", record);
        try {
            RpcAssert.assertNotNull(record, JXCErrorCodeEnum.ERRCODE_INVALID_PARAM, "record must not be null");
            RpcAssert.assertNotNull(record.getMerchantCode(), JXCErrorCodeEnum.ERRCODE_INVALID_PARAM, "record.getMerchantCode() must not be null");
            return RpcResponse.success(JxcmtBsAddressService.listServiceProviderAddress(record));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
        }
    }

    @Override
    public RpcResponse<JXCMTBsAddressDTO> getAddress(JXCMTBsAddressDTO record) {
        logger.info("JxcCommonRemoteImpl::getAddress record::{}", record);
        try {
            RpcAssert.assertNotNull(record, JXCErrorCodeEnum.ERRCODE_INVALID_PARAM, "record must not be null");
            RpcAssert.assertNotNull(record.getId(), JXCErrorCodeEnum.ERRCODE_INVALID_PARAM, "record.getId() must not be null");
            return RpcResponse.success(JxcmtBsAddressService.getAddress(record));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
        }
    }

    @Override
    public RpcResponse<Integer> updateAddress(JXCMTBsAddressDTO record) {
        logger.info("JxcCommonRemoteImpl::updateAddress record::{}", record);
        try {
            RpcAssert.assertNotNull(record, JXCErrorCodeEnum.ERRCODE_INVALID_PARAM, "record must not be null");
            RpcAssert.assertNotNull(record.getId(), JXCErrorCodeEnum.ERRCODE_INVALID_PARAM, "record.getId() must not be null");
            RpcAssert.assertNotNull(record.getName(), JXCErrorCodeEnum.ERRCODE_INVALID_PARAM, "record.getName() must not be null");
            RpcAssert.assertNotNull(record.getAddress(), JXCErrorCodeEnum.ERRCODE_INVALID_PARAM, "record.getAddress() must not be null");
            RpcAssert.assertNotNull(record.getMobile(), JXCErrorCodeEnum.ERRCODE_INVALID_PARAM, "record.getMobile() must not be null");
            return RpcResponse.success(JxcmtBsAddressService.updateAddress(record));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
        }
    }

    @Override
    public RpcResponse<Integer> delAddress(JXCMTBsAddressDTO record) {
        logger.info("JxcCommonRemoteImpl::delAddress record::{}", record);
        try {
            RpcAssert.assertNotNull(record, JXCErrorCodeEnum.ERRCODE_INVALID_PARAM, "record must not be null");
            RpcAssert.assertNotNull(record.getId(), JXCErrorCodeEnum.ERRCODE_INVALID_PARAM, "record.getId() must not be null");
            return RpcResponse.success(JxcmtBsAddressService.delAddress(record));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
        }
    }

    @Override
    public RpcResponse<Integer> addAddress(JXCMTBsAddressDTO record) {
        logger.info("JxcCommonRemoteImpl::addAddress record::{}", record);
        try {
            RpcAssert.assertNotNull(record, JXCErrorCodeEnum.ERRCODE_INVALID_PARAM, "record must not be null");
            RpcAssert.assertNotNull(record.getMerchantCode(), JXCErrorCodeEnum.ERRCODE_INVALID_PARAM, "record.getMerchantCode() must not be null");
            RpcAssert.assertNotNull(record.getAddress(), JXCErrorCodeEnum.ERRCODE_INVALID_PARAM, "record.getAddress() must not be null");
            RpcAssert.assertNotNull(record.getName(), JXCErrorCodeEnum.ERRCODE_INVALID_PARAM, "record.getName() must not be null");
            RpcAssert.assertNotNull(record.getMobile(), JXCErrorCodeEnum.ERRCODE_INVALID_PARAM, "record.getMobile() must not be null");
            return RpcResponse.success(JxcmtBsAddressService.addAddress(record));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
        }
    }

    @Override
    public RpcResponse<Integer> batchDelAddress(JXCMTBsAddressDelDTO record) {
        logger.info("JxcCommonRemoteImpl::batchDelAddress record::{}", record);
        try {
            RpcAssert.assertNotNull(record, JXCErrorCodeEnum.ERRCODE_INVALID_PARAM, "record must not be null");
            RpcAssert.assertNotNull(record.getListIds(), JXCErrorCodeEnum.ERRCODE_INVALID_PARAM, "record.getListIds() must not be null");
            RpcAssert.assertIsTrue(!record.getListIds().isEmpty(), JXCErrorCodeEnum.ERRCODE_INVALID_PARAM, "record.getListIds() must not empty");
            return RpcResponse.success(JxcmtBsAddressService.batchDelAddress(record));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
        }
    }

    @Override
    public RpcResponse<List<JXCMTProductTypeDTO>> listProductType(JXCMTProductTypeDTO record) {
        logger.info("JxcCommonRemoteImpl::listProductType record::{}", record);
        try {
            return RpcResponse.success(JxcmtAttribInfoService.listProductType(record));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
        }
    }

    @Override
    public RpcResponse<List<JXCMTMerchantChannelDTO>> listMerchantChannel(
            JXCMTMerchantChannelDTO record) {
        logger.info("JxcCommonRemoteImpl::listMerchantChannel record::{}", record);
        try {
            return RpcResponse.success(JxcmtAttribInfoService.listMerchantChannel(record));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
        }
    }

    @Override
    public RpcResponse<List<JXCMTBsSubjectDTO>> listSubject(
            JXCMTBsSubjectDTO record) {
        logger.info("JxcCommonRemoteImpl::listSubject record::{}", record);
        try {
            return RpcResponse.success(JxcmtBsSubjectService.listBsSubjectDto(record));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
        }
    }

    @Override
    public RpcResponse<List<JXCMTDeviceTypeDTO>> listDeviceType(
            JXCMTDeviceTypeDTO record) {
        logger.info("JxcCommonRemoteImpl::listDeviceType record::{}", record);
        try {
            return RpcResponse.success(JxcmtDeviceTypeService.listDeviceTypeDto(record));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
        }
    }

    @Override
    public RpcResponse<List<JXCMTDeviceTypeDTO>> listSupportDispatchDeviceType(
            JXCMTDeviceTypeDTO record) {
        logger.info("JxcCommonRemoteImpl::listSupportDispatchDeviceType record::{}", record);
        try {
            return RpcResponse.success(JxcmtDeviceTypeService.listDeviceTypeSupportDispatch(record));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
        }
    }

    @Override
    public RpcResponse<List<JXCMTDeviceCodeDTO>> listDeviceCode(
            JXCMTDeviceCodeDTO record) {
        logger.info("JxcCommonRemoteImpl::listDeviceCode record::{}", record);
        try {
            return RpcResponse.success(JxcmtDeviceTypeService.listDeviceCodeDto(record));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
        }
    }

    @Override
    public RpcResponse<List<JXCMTPackageDTO>> listDevicePackage(JXCMTPackageDTO record) {
        logger.info("JxcCommonRemoteImpl::listDevicePackage record::{}", record);
        try {
            RpcAssert.assertNotNull(record, JXCErrorCodeEnum.ERRCODE_INVALID_PARAM, "record must not be null");
            RpcAssert.assertNotNull(record.getDeviceCode(), JXCErrorCodeEnum.ERRCODE_INVALID_PARAM, "record.getDeviceCode() must not be null");
            return RpcResponse.success(JxcmtServicePackageService.listPackageByDeviceCode(record.getDeviceCode()));
        } catch (RpcServiceException e) {
            logger.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }

    @Override
    public RpcResponse<List<JXCMTAttribManaDTO>> listAttribMana(
            JXCMTAttribManaDTO record) {
        logger.info("JxcCommonRemoteImpl::listDevicePackage record::{}", record);
        try {
            RpcAssert.assertNotNull(record, JXCErrorCodeEnum.ERRCODE_INVALID_PARAM, "record must not be null");
            return RpcResponse.success(JxcmtAttribManaService.listAttribManaDto(record));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
        }
    }

    @Override
    public RpcResponse<RpcPagination<JXCMTAttribManaDTO>> pageAttribMana(
            JXCMTAttribManaDTO record) {
        logger.info("JxcCommonRemoteImpl::pageAttribMana record::{}", record);
        try {
            RpcAssert.assertNotNull(record, JXCErrorCodeEnum.ERRCODE_INVALID_PARAM, "record must not be null");
            RpcAssert.assertNotNull(record.getPageNum(), JXCErrorCodeEnum.ERRCODE_INVALID_PARAM, "record.getPageNum must not be null");
            RpcAssert.assertNotNull(record.getPageSize(), JXCErrorCodeEnum.ERRCODE_INVALID_PARAM, "record.getPageSize must not be null");
            return RpcResponse.success(RpcPagination.copyPage(JxcmtAttribManaService.pageAttribMana(record)));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
        }
    }

    @Override
    public RpcResponse<Integer> saveAttribMana(JXCMTAttribManaDTO record) {
        logger.info("JxcCommonRemoteImpl::saveAttribMana record::{}", record);
        try {
            RpcAssert.assertNotNull(record, JXCErrorCodeEnum.ERRCODE_INVALID_PARAM, "record must not be null");
            RpcAssert.assertNotNull(record.getAttribCode(), JXCErrorCodeEnum.ERRCODE_INVALID_PARAM, "record.getAttribCode() must not be null");
            RpcAssert.assertNotNull(record.getDevTypeId(), JXCErrorCodeEnum.ERRCODE_INVALID_PARAM, "record.getDevTypeId() must not be null");
            return RpcResponse.success(JxcmtAttribManaService.saveAttribMana(record));
        } catch (RpcServiceException e) {
            logger.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }

    @Override
    public RpcResponse<Integer> updateAttribMana(JXCMTAttribManaDTO record) {
        logger.info("JxcCommonRemoteImpl::updateAttribMana record::{}", record);
        try {
            RpcAssert.assertNotNull(record, JXCErrorCodeEnum.ERRCODE_INVALID_PARAM, "record must not be null");
            RpcAssert.assertNotNull(record.getAttribCode(), JXCErrorCodeEnum.ERRCODE_INVALID_PARAM, "record.getAttribCode() must not be null");
            RpcAssert.assertNotNull(record.getDevTypeId(), JXCErrorCodeEnum.ERRCODE_INVALID_PARAM, "record.getDevTypeId() must not be null");
            return RpcResponse.success(JxcmtAttribManaService.updateAttribMana(record));
        } catch (RpcServiceException e) {
            logger.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }

    @Override
    public RpcResponse<List<JXCMTWarehouseInfoDTO>> listWarehouseInfo(
            JXCMTWarehouseInfoDTO record) {
        logger.info("JxcCommonRemoteImpl::listWarehouseInfo record::{}", record);
        try {
            return RpcResponse.success(JxcmtWarehouseService.listWarehouseInfoDtoByName(record));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
        }
    }

    @Override
    public RpcResponse<JXCMTAttribManaDTO> getAttribManaByAttribCode(
            JXCMTAttribManaDTO record) {
        logger.info("JxcCommonRemoteImpl::getAttribManaByAttribCode record::{}", record);
        try {
            RpcAssert.assertNotNull(record, JXCErrorCodeEnum.ERRCODE_INVALID_PARAM, "record must not be null");
            RpcAssert.assertNotNull(record.getAttribCode(), JXCErrorCodeEnum.ERRCODE_INVALID_PARAM, "record.getAttribCode must not be null");
            return RpcResponse.success(JxcmtAttribManaService.getAttribManaDto(record.getAttribCode()));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
        }
    }

	@Override
	public RpcResponse<RpcPagination<JXCDealerUserInfoDTO>> pageServerMerchant(
			RpcPagination<JXCDealerUserInfoDTO> pagination) {
		logger.info("JxcCommonRemoteImpl::pageServerMerchant pagination::{}",pagination);
		try{
			RpcAssert.assertNotNull(pagination,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination must not be null");
			RpcAssert.assertNotNull(pagination.getCondition(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getCondition() must not be null");
			RpcAssert.assertNotNull(pagination.getPages(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getPages() must not be null");
			RpcAssert.assertNotNull(pagination.getPageSize(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getPages() must not be null");
			return RpcResponse.success(RpcPagination.copyPage(JxcmtBsDealerUserInfoService.pageServerMerchant(pagination)));
		}catch (Exception e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
		}	
	}

	@Override
	public RpcResponse<RpcPagination<JXCAmKcWarehouseRelationDTO>> pageAmKcWarehouseRelation(
			RpcPagination<JXCAmKcWarehouseRelationDTO> pagination) {
		logger.info("JxcCommonRemoteImpl::pageAmKcWarehouseRelation pagination::{}",pagination);
		try{
			RpcAssert.assertNotNull(pagination,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination must not be null");
			RpcAssert.assertNotNull(pagination.getCondition(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getCondition() must not be null");
			return RpcResponse.success(RpcPagination.copyPage(JxcamkcRelationService.pageKcWarehouseRelation(pagination)));
		}catch (Exception e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
		}	
	}

    @Override
    public RpcResponse<List<JXCMTAttribInfoDTO>> listAttribInfoByDeviceType(
            JXCMTDeviceTypeDTO record) {
        logger.info("JxcCommonRemoteImpl::listAttribInfoByDeviceType record::{}", record);
        try {
            RpcAssert.assertNotNull(record, JXCErrorCodeEnum.ERRCODE_INVALID_PARAM, "record must not be null");
            RpcAssert.assertNotNull(record.getId(), JXCErrorCodeEnum.ERRCODE_INVALID_PARAM, "record must not be null");
            return RpcResponse.success(JxcmtAttribInfoService.listAttribInfoByDeviceType(record));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
        }
    }

    @Override
    public RpcResponse<List<JXCMTAttribInfoDTO>> listAttrinInfoByAttribType(
            JXCMTAttribInfoDTO record) {
        logger.info("JxcCommonRemoteImpl::listAttribInfoByDeviceType record::{}", record);
        try {
            RpcAssert.assertNotNull(record, JXCErrorCodeEnum.ERRCODE_INVALID_PARAM, "record must not be null");
            RpcAssert.assertNotNull(record.getType(), JXCErrorCodeEnum.ERRCODE_INVALID_PARAM, "record must not be null");
            return RpcResponse.success(JxcmtAttribInfoService.listAttribInfoByAttribInfoType(record));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
        }
    }

    @Override
    public RpcResponse<Integer> saveAttribInfoByDeviceType(
            JXCMTDeviceTypeAttribInfoDTO record) {
        logger.info("JxcCommonRemoteImpl::saveAttribInfoByDeviceType record::{}", record);
        try {
            RpcAssert.assertNotNull(record, JXCErrorCodeEnum.ERRCODE_INVALID_PARAM, "record must not be null");
            RpcAssert.assertNotNull(record.getDeviceTypeId(), JXCErrorCodeEnum.ERRCODE_INVALID_PARAM, "record must not be null");
            RpcAssert.assertNotNull(record.getAttribName(), JXCErrorCodeEnum.ERRCODE_INVALID_PARAM, "record must not be null");
            JxcmtAttribInfoService.saveAttribInfoByDeviceType(record);
            JXCMTAttribInfo attribInfo = JxcmtAttribInfoService.getAttribInfoByDeviceType(record);
            return RpcResponse.success(attribInfo.getId());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
        }
    }

    @Override
    public RpcResponse<RpcPagination<JXCMTBsMaterialInfoDTO>> pageMaterialInfo(
            JXCMTBsMaterialInfoDTO record) {
        logger.info("JxcCommonRemoteImpl::pageMaterialInfo record::{}", record);
        try {
            RpcAssert.assertNotNull(record, JXCErrorCodeEnum.ERRCODE_INVALID_PARAM, "record must not be null");
            return RpcResponse.success(RpcPagination.copyPage(JxcmtMaterialInfoService.pageMaterialInfo(record)));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
        }
    }


}
