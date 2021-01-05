package cn.com.glsx.supplychain.remote;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import cn.com.glsx.framework.core.enums.DefaultErrorEnum;
import cn.com.glsx.framework.core.util.RpcAssert;
import cn.com.glsx.supplychain.service.*;
import cn.com.glsx.supplychain.util.ActiveDevicePackageThread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.enums.ExternalFlagEnum;
import cn.com.glsx.supplychain.enums.OutStorageTypeEnum;
import cn.com.glsx.supplychain.enums.PackageStatuEnum;
import cn.com.glsx.supplychain.enums.SystemPlatEnum;
import cn.com.glsx.supplychain.enums.UpdateRecordEnum;
import cn.com.glsx.supplychain.enums.UserFlagTypeEnum;
import cn.com.glsx.supplychain.enums.VehicleFlagTypeEnum;
import cn.com.glsx.supplychain.manager.SupplyChainExternalService;
import cn.com.glsx.supplychain.model.DeviceCardManager;
import cn.com.glsx.supplychain.model.DeviceCode;
import cn.com.glsx.supplychain.model.DeviceFile;
import cn.com.glsx.supplychain.model.DeviceFileSnapshot;
import cn.com.glsx.supplychain.model.DeviceFileSv;
import cn.com.glsx.supplychain.model.DeviceFileVirtual;
import cn.com.glsx.supplychain.model.DeviceImeiStock;
import cn.com.glsx.supplychain.model.DeviceType;
import cn.com.glsx.supplychain.model.DeviceUpdateRecord;
import cn.com.glsx.supplychain.model.DeviceUpdateRecordSv;
import cn.com.glsx.supplychain.model.DeviceUserManager;
import cn.com.glsx.supplychain.model.DeviceVehicleManager;
import cn.com.glsx.supplychain.model.FirmwareInfo;
import cn.com.glsx.supplychain.model.SupplyChainMerchantInfo;
import cn.com.glsx.supplychain.model.SupplyDeviceCodeRequest;
import cn.com.glsx.supplychain.model.SupplyDeviceFileRequest;
import cn.com.glsx.supplychain.model.SupplyDeviceTypeRequest;
import cn.com.glsx.supplychain.util.RequestVerifyService;
import cn.com.glsx.supplychain.util.StringUtil;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;

@Component("DeviceManagerService")
@Service(version = "1.0.0", protocol = "dubbo", timeout = 10000)
public class DeviceManagerServiceImpl implements DeviceManagerService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

   // private static ScheduledExecutorService executor = Executors.newScheduledThreadPool(256);
	
    @Autowired
    private SupplyChainExternalService externalService;
    
	@Autowired
	private DeviceImeiStockService deviceImeiStockService;

    @Autowired
    private DeviceManagerServiceService deviceManagerService;

    @Autowired
    private DeviceTypeService deviceTypeService;

    @Autowired
    private DeviceCodeService deviceCodeService;

    @Autowired
    private DeviceFileService deviceFileService;

    @Autowired
    private DeviceFileVirtualService deviceFileVirtualService;

    @Autowired
    private DeviceFileSnapshotService deviceFileSnapshotService;

    @Autowired
    private DeviceCardManagerService deviceCardManagerService;

    @Autowired
    private DeviceUserManagerService deviceUserManagerService;

    @Autowired
    private DeviceVehicleManagerService deviceVehicleManagerService;

    @Autowired
    private SupplyChainAdminRemoteService supplayChainAdminRemoteService;

    @Autowired
    private DeviceUpdateRecordService deviceUpdateRecordService;

    @Autowired
    private RequestVerifyService requestVerifyService;

    @Autowired
    private FirmwareInfoService firmwareInfoService;

    @Autowired
    private DeviceFileUnstockService deviceFileUnstockService;

    /**
     * @param ("必填")consumer,time,version
     * @param ("选填")pagination,request
     * @return RpcResponse<RpcPagination   <   DeviceType>>
     * @Title pageDeviceType
     * @Description 获取设备大类型列表(带分页)
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @Override
    public RpcResponse<RpcPagination<DeviceType>> pageDeviceType(
            RpcPagination<DeviceType> pagination,
            SupplyDeviceTypeRequest request) {

        if (!requestVerifyService.verifyRequest(request)) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        if (StringUtils.isEmpty(pagination) || StringUtils.isEmpty(request)) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        logger.info("DeviceManagerServiceImpl::pageDeviceType param pagination=" + pagination.toString() + " request=" + request.toString());
        try {
            DeviceType record = new DeviceType();
            record.setId(request.getId());
            record.setName(request.getName());
            Page<DeviceType> page = deviceTypeService.pageDeviceType(pagination, record);
            pagination = RpcPagination.copyPage(page);
            return RpcResponse.success(pagination);
        } catch (RpcServiceException e) {
            return RpcResponse.error(e.getError(), e.getMessage());
        }
    }

    /**
     * @param ("必填")consumer,time,version
     * @param ("选填")id,name
     * @return RpcResponse<RpcPagination   <   DeviceType>>
     * @Title listDeviceType
     * @Description 获取设备大类型列表
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @Override
    public RpcResponse<List<DeviceType>> listDeviceType(
            SupplyDeviceTypeRequest request) {

        if (!requestVerifyService.verifyRequest(request)) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        logger.info("DeviceManagerServiceImpl::listDeviceType param request=" + request.toString());
        try {
            DeviceType record = new DeviceType();
            record.setId(request.getId());
            record.setName(request.getName());
            List<DeviceType> oList = deviceTypeService.listDeviceType(record);
            return RpcResponse.success(oList);
        } catch (RpcServiceException e) {
            return RpcResponse.error(e.getError(), e.getMessage());
        }
    }

    /**
     * @param ("必填")consumer,time,version
     * @param ("选填")deviceCode,id,deviceName,merchantId,typeId
     * @return RpcResponse<RpcPagination   <   DeviceCode>>
     * @Title pageDeviceCode
     * @Description 获取设备编号列表(带分页)
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @Override
    public RpcResponse<RpcPagination<DeviceCode>> pageDeviceCode(
            RpcPagination<DeviceCode> pagination,
            SupplyDeviceCodeRequest request) {

        if (!requestVerifyService.verifyRequest(request)) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        if (StringUtils.isEmpty(pagination) || StringUtils.isEmpty(request)) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }

        logger.info("DeviceManagerServiceImpl::pageDeviceCode param pagination=" + pagination.toString() + " request=" + request.toString());

        try {
            DeviceCode record = new DeviceCode();
            record.setId(request.getId());
            record.setDeviceCode(request.getDeviceCode());
            record.setDeviceName(request.getDeviceName());
            record.setMerchantId(request.getMerchantId());
            record.setTypeId(request.getTypeId());
            Page<DeviceCode> page = deviceCodeService.pageDeviceCode(pagination, record);
            pagination = RpcPagination.copyPage(page);
            return RpcResponse.success(pagination);
        } catch (RpcServiceException e) {
            return RpcResponse.error(e.getError(), e.getMessage());
        }
    }

    /**
     * @param ("必填")consumer,time,version
     * @param ("选填")deviceCode,id,deviceName,merchantId,typeId
     * @return RpcResponse<List   <   DeviceCode>>
     * @Title listDeviceCode
     * @Description 获取设备编号列表
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @Override
    public RpcResponse<List<DeviceCode>> listDeviceCode(
            SupplyDeviceCodeRequest request) {

        if (!requestVerifyService.verifyRequest(request)) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }

        logger.info("DeviceManagerServiceImpl::listDeviceCode param request=" + request.toString());

        try {
            DeviceCode record = new DeviceCode();
            record.setId(request.getId());
            record.setDeviceCode(request.getDeviceCode());
            record.setDeviceName(request.getDeviceName());
            record.setMerchantId(request.getMerchantId());
            record.setTypeId(request.getTypeId());
            List<DeviceCode> oList = deviceCodeService.listDeviceCode(record);
            getMerchantNameForDeviceCodeList(oList);
            return RpcResponse.success(oList);
        } catch (RpcServiceException e) {
            return RpcResponse.error(e.getError(), e.getMessage());
        }
    }

    /**
     * @param ("必填")consumer,time,version
     * @param ("选填")listIds
     * @return RpcResponse<List   <   DeviceCode>>
     * @Title listDeviceCodeByIds
     * @Description 根据小类型id集合获取到code
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @Override
    public RpcResponse<List<DeviceCode>> listDeviceCodeByIds(
            SupplyDeviceCodeRequest request) {

        List<DeviceCode> oList = null;

        if (!requestVerifyService.verifyRequest(request)) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        logger.info("DeviceManagerServiceImpl::listDeviceCodeByIds param request=" + request.toString());
        if (StringUtils.isEmpty(request.getListIds())) {
            return RpcResponse.success(oList);
        }

        try {
            oList = deviceCodeService.listDeviceCodeByIds(request.getListIds());
            return RpcResponse.success(oList);
        } catch (RpcServiceException e) {
            return RpcResponse.error(e.getError(), e.getMessage());
        }
    }

    /**
     * @param ("必填")consumer,time,version"必填"
     * @param ("选填")id"必填"
     * @return RpcResponse<String>
     * @Title getDeviceTypeNameById
     * @Description 根据设备类型ID 获取设备类型名称
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @Override
    public RpcResponse<String> getDeviceTypeNameById(
            SupplyDeviceTypeRequest request) {

        String deviceTypeName = "";
        if (!requestVerifyService.verifyRequest(request)) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        logger.info("DeviceManagerServiceImpl::getDeviceTypeNameById param request=" + request.toString());
        if (StringUtils.isEmpty(request.getId())) {

            return RpcResponse.success(deviceTypeName);
        }

        try {
            deviceTypeName = deviceTypeService.getDeviceTypeNameById(request.getId());
            return RpcResponse.success(deviceTypeName);
        } catch (RpcServiceException e) {
            return RpcResponse.error(e.getError(), e.getMessage());
        }
    }

    /**
     * @param ("必填")consumer,time,version
     * @param ("选填")deviceCode,id
     * @return RpcResponse<DeviceCode>
     * @Title getDeviceCodeByIdOrDeviceCode
     * @Description 根据设备编码 或者设备ID 获取设备编码信息
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @Override
    public RpcResponse<DeviceCode> getDeviceCodeByIdOrDeviceCode(
            SupplyDeviceCodeRequest request) {

        if (!requestVerifyService.verifyRequest(request)) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        logger.info("DeviceManagerServiceImpl::getDeviceCodeByIdOrDeviceCode param request=" + request.toString());
        Integer code = (StringUtils.isEmpty(request.getDeviceCode()) ? request.getId() : request.getDeviceCode());

        try {
            DeviceCode deviceCode = deviceCodeService.getDeviceCodeByDeviceCode(code);
            return RpcResponse.success(deviceCode);
        } catch (RpcServiceException e) {
            return RpcResponse.error(e.getError(), e.getMessage());
        }
    }

    /**
     * @param ("必填")consumer,time,version
     * @param ("选填")manufacturerCode
     * @return RpcResponse<DeviceCode>
     * @Title getDeviceCodeByIdOrDeviceCode
     * @Description 根据厂商码 获取设备编码信息
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @Override
    public RpcResponse<DeviceCode> getDeviceCodeByManufacturerCode(
            SupplyDeviceCodeRequest request) {

        DeviceCode deviceCode = null;
        if (!requestVerifyService.verifyRequest(request)) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        logger.info("DeviceManagerServiceImpl::getDeviceCodeByManufacturerCode param request=" + request.toString());

        try {
            deviceCode = deviceCodeService.getDeviceCodeByManufacturerCode(request.getManufacturerCode());
            return RpcResponse.success(deviceCode);
        } catch (RpcServiceException e) {
            return RpcResponse.error(e.getError(), e.getMessage());
        }
    }

    /**
     * @param ("必填")consumer,time,version
     * @param ("选填")deviceCode,id
     * @return RpcResponse<String>
     * @Title getDeviceCodeNameByIdOrDeviceCode
     * @Description 根据设备编码 或者设备ID 获取设备名称
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @Override
    public RpcResponse<String> getDeviceCodeNameByIdOrDeviceCode(
            SupplyDeviceCodeRequest request) {

        String deviceCodeName = "";

        if (!requestVerifyService.verifyRequest(request)) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        logger.info("DeviceManagerServiceImpl::getDeviceCodeNameByIdOrDeviceCode param request=" + request.toString());
        Integer code = (StringUtils.isEmpty(request.getDeviceCode()) ? request.getId() : request.getDeviceCode());
        if (StringUtils.isEmpty(code)) {
            return RpcResponse.success(deviceCodeName);
        }

        try {
            DeviceCode deviceCode = deviceCodeService.getDeviceCodeByDeviceCode(code);
            if (!StringUtils.isEmpty(deviceCode)) {
                deviceCodeName = deviceCode.getDeviceName();
            }
            return RpcResponse.success(deviceCodeName);
        } catch (RpcServiceException e) {
            return RpcResponse.error(e.getError(), e.getMessage());
        }
    }

    /**
     * @param ("必填")consumer,time,version
     * @param ("选填")id
     * @return RpcResponse<Integer>
     * @Title countDevicesByDeviceType
     * @Description 统计设备大类型下设备数
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @Override
    public RpcResponse<Integer> countDevicesByDeviceType(
            SupplyDeviceTypeRequest request) {

        Integer ret = 0;
        if (!requestVerifyService.verifyRequest(request)) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        logger.info("DeviceManagerServiceImpl::countDevicesByDeviceType param request=" + request.toString());
        if (StringUtils.isEmpty(request.getId())) {
            return RpcResponse.success(ret);
        }

        try {
            ret = deviceFileService.countDeviceFilesByDeviceType(request.getId());
            ret += deviceFileVirtualService.countDeviceFilesByDeviceType(request.getId());
            return RpcResponse.success(ret);
        } catch (RpcServiceException e) {
            return RpcResponse.error(e.getError(), e.getMessage());
        }
    }

    /**
     * @param ("必填")consumer,time,version
     * @param ("选填")id,deviceCode
     * @return RpcResponse<Integer>
     * @Title countDevicesByDeviceCode
     * @Description 统计设备编码下设备数
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @Override
    public RpcResponse<Integer> countDevicesByDeviceCode(
            SupplyDeviceCodeRequest request) {

        int ret = 0;
        if (!requestVerifyService.verifyRequest(request)) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        logger.info("DeviceManagerServiceImpl::countDevicesByDeviceCode param request=" + request.toString());
        Integer deviceCode = (StringUtils.isEmpty(request.getDeviceCode()) ? request.getId() : request.getDeviceCode());
        if (StringUtils.isEmpty(deviceCode)) {
            return RpcResponse.success(ret);
        }

        try {
            ret = deviceFileService.countDeviceFilesByDeviceCode(deviceCode);
            ret += deviceFileVirtualService.countDeviceFilesByDeviceCode(deviceCode);
            return RpcResponse.success(ret);
        } catch (RpcServiceException e) {
            return RpcResponse.error(e.getError(), e.getMessage());
        }
    }

    /**
     * @param ("必填")consumer,time,version
     * @param ("选填")pkgStatus
     * @return RpcResponse<Integer>
     * @Title countDevicesByPackageStatus
     * @Description 统计设备激活或者未激活数
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @Override
    public RpcResponse<Integer> countDevicesByPackageStatus(
            SupplyDeviceFileRequest request) {

        int ret = 0;
        if (!requestVerifyService.verifyRequest(request)) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        logger.info("DeviceManagerServiceImpl::countDevicesByPackageStatus param request=" + request.toString());
        if (StringUtils.isEmpty(request.getPkgStatus())) {
            return RpcResponse.success(ret);
        }

        try {
            ret = deviceFileService.countDeviceFilesByPackageStatus(request.getPkgStatus());
            ret += deviceFileVirtualService.countDeviceFiles();
            return RpcResponse.success(ret);
        } catch (RpcServiceException e) {
            return RpcResponse.error(e.getError(), e.getMessage());
        }
    }

    /**
     * @param ("必填")consumer,time,version
     * @param ("选填")
     * @return RpcResponse<Integer>
     * @Title countDevices
     * @Description 统计设备总数
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @Override
    public RpcResponse<Integer> countDevices(SupplyDeviceFileRequest request) {

        int ret = 0;
        if (!requestVerifyService.verifyRequest(request)) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        logger.info("DeviceManagerServiceImpl::countDevicesByPackageStatus param request=" + request.toString());

        try {
            ret = deviceFileService.countDeviceFiles();
            ret += deviceFileVirtualService.countDeviceFiles();
            return RpcResponse.success(ret);
        } catch (RpcServiceException e) {
            return RpcResponse.error(e.getError(), e.getMessage());
        }
    }

    /**
     * @param ("必填")consumer,time,version
     * @param ("选填")sn(imei)
     * @return RpcResponse<DeviceFileSv>
     * @Title getDeviceByDeviceSn
     * @Description 根据设备sn获取设备信息
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @Override
    public RpcResponse<DeviceFileSv> getDeviceByDeviceSn(
            SupplyDeviceFileRequest request) {

        if (!requestVerifyService.verifyRequest(request)) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        logger.info("DeviceManagerServiceImpl::getDeviceByDeviceSn param request=" + request.toString());

        try {
            DeviceFile deviceFile = deviceFileService.getDeviceFileBySn(request.getSn());
            return RpcResponse.success(this.getDeviceFileSvFromDeviceFile(deviceFile));
        } catch (RpcServiceException e) {
            return RpcResponse.error(e.getError(), e.getMessage());
        }
    }

    /**
     * @Title getDeviceByDeviceId
     * @Description 根据设备id获取设备信息
     * @param ("必填")consumer,time,version
     * @param ("必填")id
     * @return RpcResponse<DeviceFileSv>
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
//	@Override
//	public RpcResponse<DeviceFileSv> getDeviceByDeviceId(
//			SupplyDeviceFileRequest request) {
//		
//		if(!requestVerifyService.verifyRequest(request))
//		{
//			return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
//		}
//		if(StringUtils.isEmpty(request) || StringUtils.isEmpty(request.getId()))
//		{
//			return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
//		}
//		logger.info("DeviceManagerServiceImpl::getDeviceByDeviceId param request=" + request.toString());
//		
//		try
//		{
//			DeviceFile deviceFile = deviceFileService.getDeviceFileById(request.getId());
//			return RpcResponse.success(this.getDeviceFileSvFromDeviceFile(deviceFile));
//		}
//		catch(RpcServiceException e)
//		{
//			return RpcResponse.error(e.getError(),e.getMessage());
//		}
//	}

    /**
     * @param ("必填")consumer,time,version
     * @param ("选填")imsi                  companyId
     * @return RpcResponse<DeviceFileSv>
     * @Title getDeviceByImsi
     * @Description 根据Imsi获取设备信息
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @Override
    public RpcResponse<DeviceFileSv> getDeviceByImsi(
            SupplyDeviceFileRequest request) {

        DeviceFileSv deviceFileSv = null;

        if (!requestVerifyService.verifyRequest(request)) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        logger.info("DeviceManagerServiceImpl::getDeviceByImsi param request=" + request.toString());
        if (StringUtils.isEmpty(request.getImsi())) {
            return RpcResponse.success(deviceFileSv);
        }

        try {
            //先在虚拟表中查询
            DeviceFileVirtual deviceFileVirtual = deviceFileVirtualService.getDeviceFileVirtualByImsi(request.getImsi());
            if (!StringUtils.isEmpty(deviceFileVirtual) && "N".equals(deviceFileVirtual.getDeletedFlag())) {
                return RpcResponse.success(this.getDeviceFileSvFromDeviceFileVirtual(deviceFileVirtual));
            }

            //获取卡在模块内部的id号
            DeviceCardManager record = new DeviceCardManager();
            record.setImsi(request.getImsi());
            record.setCompanyId(request.getCompanyId());
            DeviceCardManager deviceCardManager = deviceCardManagerService.getDeviceCardByUniqueKey(record);
            if (StringUtils.isEmpty(deviceCardManager)) {
                return RpcResponse.success(deviceFileSv);
            }

            //反向查询设备信息
            DeviceFileSnapshot deviceFileSnapshot = deviceFileSnapshotService.getDeviceFileSnapshotByCardId(deviceCardManager.getId());
            if (StringUtils.isEmpty(deviceFileSnapshot)) {
                return RpcResponse.success(deviceFileSv);
            }
            DeviceFile deviceFile = deviceFileService.getDeviceFileBySn(deviceFileSnapshot.getSn());
            if (StringUtils.isEmpty(deviceFile)) {
                return RpcResponse.success(deviceFileSv);
            }
            if ("Y".equals(deviceFile.getDeletedFlag())) {
                return RpcResponse.success(deviceFileSv);
            }
            return RpcResponse.success(this.getDeviceFileSvFromDeviceFile(deviceFile));
        } catch (RpcServiceException e) {
            return RpcResponse.error(e.getError(), e.getMessage());
        }
    }

    /**
     * @param ("必填")consumer,time,version
     * @param ("选填")sn
     * @return RpcResponse<SystemPlatEnum>
     * @Title getSystemPlatByDeviceSn
     * @Description 根据设备sn确定平台
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @Override
    public RpcResponse<SystemPlatEnum> getSystemPlatByDeviceSn(
            SupplyDeviceFileRequest request) {

        SystemPlatEnum systemPlat = SystemPlatEnum.SYSTEM_PLAT_PRIM_PERIOD;
        if (!requestVerifyService.verifyRequest(request)) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        logger.info("DeviceManagerServiceImpl::getSystemPlatByDeviceSn param request=" + request.toString());

        try {
            DeviceFile deviceFile = deviceFileService.getDeviceFileBySn(request.getSn());
            if (StringUtils.isEmpty(deviceFile)) {
                return RpcResponse.success(systemPlat);
            } else if ("Y".equals(deviceFile.getDeletedFlag())) {
                return RpcResponse.success(systemPlat);
            }

            systemPlat = SystemPlatEnum.SYSTEM_PLAT_MIDD_PERIOD;
            return RpcResponse.success(systemPlat);
        } catch (RpcServiceException e) {
            return RpcResponse.error(e.getError(), e.getMessage());
        }
    }

    /**
     * @param ("必填")consumer,time,version
     * @param ("选填")id
     * @return RpcResponse<SystemPlatEnum>
     * @Title getSystemPlatByDeviceSn
     * @Description 根据设备id确定平台(临时)
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
/*	@Override
	public RpcResponse<SystemPlatEnum> getSystemPlatByDeviceId(SupplyDeviceFileRequest request) {
		SystemPlatEnum systemPlat = SystemPlatEnum.SYSTEM_PLAT_PRIM_PERIOD;
		if(!requestVerifyService.verifyRequest(request))
		{
			return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		logger.info("DeviceManagerServiceImpl::getSystemPlatByDeviceSn param request=" + request.toString());

		try
		{
			DeviceFile deviceFile = deviceFileService.getDeviceFileById(request.getId());
			if(StringUtils.isEmpty(deviceFile))
			{
				return RpcResponse.success(systemPlat);
			}

			systemPlat = SystemPlatEnum.SYSTEM_PLAT_MIDD_PERIOD;
			return RpcResponse.success(systemPlat);
		}
		catch(RpcServiceException e)
		{
			return RpcResponse.error(e.getError(),e.getMessage());
		}
	}*/

    /**
     * @param ("必填")consumer,time,version
     * @param ("选填")imsi
     * @return RpcResponse<SystemPlatEnum>
     * @Title getSystemPlatByImsi
     * @Description 根据imsi确定平台
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @Override
    public RpcResponse<SystemPlatEnum> getSystemPlatByImsi(
            SupplyDeviceFileRequest request) {

        SystemPlatEnum systemPlat = SystemPlatEnum.SYSTEM_PLAT_MIDD_PERIOD;
        if (!requestVerifyService.verifyRequest(request)) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }

        logger.info("DeviceManagerServiceImpl::getSystemPlatByImsi param request=" + request.toString());

        try {
            DeviceFileVirtual deviceFileVirtual = deviceFileVirtualService.getDeviceFileVirtualByImsi(request.getImsi());

            if (!StringUtils.isEmpty(deviceFileVirtual) && "N".equals(deviceFileVirtual.getDeletedFlag())) {
                systemPlat = SystemPlatEnum.SYSTEM_PLAT_MIDD_PERIOD;
                return RpcResponse.success(systemPlat);

				/*if(OutStorageTypeEnum.OUT_STORAGE_TYPE_SYNC.equals(OutStorageTypeEnum.fromString(deviceFileVirtual.getOutStorageType())))
				{
					systemPlat = SystemPlatEnum.SYSTEM_PLAT_PRIM_PERIOD;
					return RpcResponse.success(systemPlat);
				}*/
            }


            DeviceCardManager record = new DeviceCardManager();
            record.setImsi(request.getImsi());
            record.setCompanyId(request.getCompanyId());
            DeviceCardManager deviceCardManager = deviceCardManagerService.getDeviceCardByUniqueKey(record);
            if (StringUtils.isEmpty(deviceCardManager)) {
                systemPlat = SystemPlatEnum.SYSTEM_PLAT_PRIM_PERIOD;
                return RpcResponse.success(systemPlat);
            }

            DeviceFileSnapshot deviceFileSnapshot = deviceFileSnapshotService.getDeviceFileSnapshotByCardId(deviceCardManager.getId());
            if (StringUtils.isEmpty(deviceFileSnapshot)) {
                systemPlat = SystemPlatEnum.SYSTEM_PLAT_PRIM_PERIOD;
                return RpcResponse.success(systemPlat);
                //return RpcResponse.success(systemPlat);
            } else if ("Y".equals(deviceFileSnapshot.getDeletedFlag())) {
                systemPlat = SystemPlatEnum.SYSTEM_PLAT_PRIM_PERIOD;
                return RpcResponse.success(systemPlat);
            }


            DeviceFile deviceFile = deviceFileService.getDeviceFileBySn(deviceFileSnapshot.getSn());
            if (StringUtils.isEmpty(deviceFile)) {
                systemPlat = SystemPlatEnum.SYSTEM_PLAT_PRIM_PERIOD;
                return RpcResponse.success(systemPlat);
                //return RpcResponse.success(systemPlat);
            } else if ("Y".equals(deviceFile.getDeletedFlag())) {
                systemPlat = SystemPlatEnum.SYSTEM_PLAT_PRIM_PERIOD;
                return RpcResponse.success(systemPlat);
            }

			/*if(OutStorageTypeEnum.OUT_STORAGE_TYPE_SYNC.equals(OutStorageTypeEnum.fromString(deviceFile.getOutStorageType())))
			{
				systemPlat = SystemPlatEnum.SYSTEM_PLAT_PRIM_PERIOD;
			}*/

            return RpcResponse.success(systemPlat);
        } catch (RpcServiceException e) {
            return RpcResponse.error(e.getError(), e.getMessage());
        }
    }

    /**
     * @param ("必填")consumer,time,version
     * @param ("选填")vehicleFlag和companyId
     * @return RpcResponse<DeviceFileSv>
     * @Title getDeviceFileByVehicleFlag
     * @Description 查看当前车辆所用设备信息
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @Override
    public RpcResponse<DeviceFileSv> getDeviceFileByVehicleFlag(
            SupplyDeviceFileRequest request) {

        DeviceFileSv deviceFileSv = null;
        if (!requestVerifyService.verifyRequest(request)) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        logger.info("DeviceManagerServiceImpl::getDeviceFileByVehicleFlag param request=" + request.toString());

        try {
            DeviceVehicleManager record = new DeviceVehicleManager();
            record.setVehicleFlag(request.getVehicleFlag());
            record.setCompanyId(request.getCompanyId());
            DeviceVehicleManager deviceVehicleManager = deviceVehicleManagerService.getDeviceVehicleByUniqueKey(record);
            if (StringUtils.isEmpty(deviceVehicleManager)) {
                return RpcResponse.success(deviceFileSv);
            }

            DeviceFileSnapshot deviceFileSnapshot = deviceFileSnapshotService.getDeviceFileSnapshotByVehicleId(deviceVehicleManager.getId());
            if (StringUtils.isEmpty(deviceFileSnapshot)) {
                return RpcResponse.success(deviceFileSv);
            }

            DeviceFile deviceFile = deviceFileService.getDeviceFileBySn(deviceFileSnapshot.getSn());
            if (StringUtils.isEmpty(deviceFile)) {
                return RpcResponse.success(deviceFileSv);
            }

            return RpcResponse.success(this.getDeviceFileSvFromDeviceFile(deviceFile));
        } catch (RpcServiceException e) {
            return RpcResponse.error(e.getError(), e.getMessage());
        }
    }

    /**
     * @param ("必填")consumer,time,version
     * @param ("必填")vehicleFlag,companyId,sn,flagType
     * @return RpcResponse<Integer> 0:成功 其他:失败
     * @Title bindDeviceFileToVehicleFlag
     * @Description 绑定设备到车辆
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @Override
    public RpcResponse<Integer> bindDeviceFileToVehicleFlag(
            SupplyDeviceFileRequest request) {
        Integer ret = 0;
        if (!requestVerifyService.verifyRequest(request)) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        if (StringUtils.isEmpty(request) || StringUtils.isEmpty(request.getVehicleFlag()) ||
                StringUtils.isEmpty(request.getCompanyId()) || StringUtils.isEmpty(request.getSn()) ||
                StringUtils.isEmpty(request.getFlagType())) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        logger.info("DeviceManagerServiceImpl::bindDeviceFileToVehicleFlag param request=" + request.toString());

        try {
            deviceManagerService.bindDeviceFileToVehicleFlag(request);
            logger.info("DeviceManagerServiceImpl::bindDeviceFileToVehicleFlag ok!");
            return RpcResponse.success(ret);
        } catch (RpcServiceException e) {
            return RpcResponse.error(e.getError(), e.getMessage());
        }
    }

    /**
     * @param ("必填")consumer,time,version
     * @param ("必填")vehicleFlag,companyId,sn,flagType
     * @return RpcResponse<Integer> 0:成功 其他:失败
     * @Title unBindDeviceFileToVehicleFlag
     * @Description 解绑设备到车辆
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @Override
    public RpcResponse<Integer> unBindDeviceFileToVehicleFlag(
            SupplyDeviceFileRequest request) {

        Integer ret = 0;
        if (!requestVerifyService.verifyRequest(request)) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        if (StringUtils.isEmpty(request) || StringUtils.isEmpty(request.getVehicleFlag()) ||
                StringUtils.isEmpty(request.getCompanyId()) || StringUtils.isEmpty(request.getSn()) ||
                StringUtils.isEmpty(request.getFlagType())) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        logger.info("DeviceManagerServiceImpl::unBindDeviceFileToVehicleFlag param request=" + request.toString());

        try {
            deviceManagerService.unBindDeviceFileToVehicleFlag(request);
            logger.info("DeviceManagerServiceImpl::unBindDeviceFileToVehicleFlag ok!");
            return RpcResponse.success(ret);
        } catch (RpcServiceException e) {
            return RpcResponse.error(e.getError(), e.getMessage());
        }
    }

    /**
     * @param ("必填")consumer,time,version
     * @param ("必填")imsi,companyId,sn
     * @return RpcResponse<Integer> 0:成功 其他:失败
     * @Title bindCardToDeviceFile
     * @Description 绑定卡到设备
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @Override
    public RpcResponse<Integer> bindCardToDeviceFile(
            SupplyDeviceFileRequest request) {

        Integer ret = 0;
        if (!requestVerifyService.verifyRequest(request)) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        if (StringUtils.isEmpty(request) || StringUtils.isEmpty(request.getImsi()) ||
                StringUtils.isEmpty(request.getCompanyId()) || StringUtils.isEmpty(request.getSn())) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        logger.info("DeviceManagerServiceImpl::bindCardToDeviceFile param request=" + request.toString());

        try {
            deviceManagerService.bindCardToDeviceFile(request);

            logger.info("DeviceManagerServiceImpl::bindCardToDeviceFile ok!");
            return RpcResponse.success(ret);
        } catch (RpcServiceException e) {
            return RpcResponse.error(e.getError(), e.getMessage());
        }
    }

    /**
     * @param ("必填")consumer,time,version
     * @param ("必填")imsi,companyId,sn
     * @return RpcResponse<Integer> 0:成功 其他:失败
     * @Title unBindCardToDeviceFile
     * @Description 解绑卡到设备
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @Override
    public RpcResponse<Integer> unBindCardToDeviceFile(
            SupplyDeviceFileRequest request) {

        Integer ret = 0;
        if (!requestVerifyService.verifyRequest(request)) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        if (StringUtils.isEmpty(request) || StringUtils.isEmpty(request.getImsi()) ||
                StringUtils.isEmpty(request.getCompanyId()) || StringUtils.isEmpty(request.getSn())) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        logger.info("DeviceManagerServiceImpl::unBindCardToDeviceFile param request=" + request.toString());

        try {
            deviceManagerService.unBindCardToDeviceFile(request);

            logger.info("DeviceManagerServiceImpl::unBindCardToDeviceFile ok!");
            return RpcResponse.success(ret);
        } catch (RpcServiceException e) {
            return RpcResponse.error(e.getError(), e.getMessage());
        }
    }

    /**
     * @param ("必填")consumer,time,version
     * @param ("必填")userFlag,companyId,userFlagType
     * @return RpcResponse<List   <   DeviceFileSv>>
     * @Title listDeviceFileByActivePackageUserFlag
     * @Description 查看用户当前所激活的设备信息列表
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @Override
    public RpcResponse<List<DeviceFileSv>> listDeviceFileByActivePackageUserFlag(
            SupplyDeviceFileRequest request) {

        List<DeviceFileSv> oList = new ArrayList<DeviceFileSv>();
        if (!requestVerifyService.verifyRequest(request)) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        if (StringUtils.isEmpty(request) || StringUtils.isEmpty(request.getUserFlag()) ||
                StringUtils.isEmpty(request.getCompanyId()) || StringUtils.isEmpty(request.getUserFlagType())) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        logger.info("DeviceManagerServiceImpl::listDeviceFileByActivePackageUserFlag param request=" + request.toString());

        try {
            DeviceUserManager record = new DeviceUserManager();
            record.setUserFlag(request.getUserFlag());
            record.setFlagType(request.getUserFlagType().getValue());
            record.setCompanyId(request.getCompanyId());
            DeviceUserManager deviceUserManager = deviceUserManagerService.getDeviceUserByUniqueKey(record);
            if (StringUtils.isEmpty(deviceUserManager)) {
                return RpcResponse.success(oList);
            }

            DeviceFileSnapshot deviceFileSnapshot = new DeviceFileSnapshot();
            deviceFileSnapshot.setPackageUserId(deviceUserManager.getId());
            deviceFileSnapshot.setPackageStatu(PackageStatuEnum.PACKAGE_STATU_ALACTIVE.getValue());
            List<DeviceFileSnapshot> snapshotList = deviceFileSnapshotService.getDeviceFileSnapshotList(deviceFileSnapshot);

            for (DeviceFileSnapshot item : snapshotList) {
                DeviceFile deviceFile = deviceFileService.getDeviceFileBySn(item.getSn());
                DeviceFileSv deviceFileSv = this.getDeviceFileSvFromDeviceFile(deviceFile);
                if (!StringUtils.isEmpty(deviceFileSv)) {
                    oList.add(deviceFileSv);
                }
            }
            return RpcResponse.success(oList);
        } catch (RpcServiceException e) {
            return RpcResponse.error(e.getError(), e.getMessage());
        }
    }

    /**
     * @param ("必填")consumer,time,version
     * @param ("必填")userFlag,companyId,userFlagType
     * @return RpcResponse<List   <   DeviceFileSv>>
     * @Title listDeviceFileByBindingUserFlag
     * @Description 查看用户当前所绑定的设备信息列表
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @Override
    public RpcResponse<List<DeviceFileSv>> listDeviceFileByBindingUserFlag(
            SupplyDeviceFileRequest request) {

        List<DeviceFileSv> oList = new ArrayList<DeviceFileSv>();
        if (!requestVerifyService.verifyRequest(request)) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        if (StringUtils.isEmpty(request) || StringUtils.isEmpty(request.getUserFlag()) ||
                StringUtils.isEmpty(request.getCompanyId()) || StringUtils.isEmpty(request.getUserFlagType())) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        logger.info("DeviceManagerServiceImpl::listDeviceFileByBindingUserFlag param request=" + request.toString());

        try {
            DeviceUserManager record = new DeviceUserManager();
            record.setUserFlag(request.getUserFlag());
            record.setFlagType(request.getUserFlagType().getValue());
            record.setCompanyId(request.getCompanyId());
            DeviceUserManager deviceUserManager = deviceUserManagerService.getDeviceUserByUniqueKey(record);
            if (StringUtils.isEmpty(deviceUserManager)) {
                return RpcResponse.success(oList);
            }

            DeviceFileSnapshot deviceFileSnapshot = new DeviceFileSnapshot();
            deviceFileSnapshot.setUserId(deviceUserManager.getId());
            List<DeviceFileSnapshot> snapshotList = deviceFileSnapshotService.getDeviceFileSnapshotList(deviceFileSnapshot);

            for (DeviceFileSnapshot item : snapshotList) {
                DeviceFile deviceFile = deviceFileService.getDeviceFileBySn(item.getSn());
                DeviceFileSv deviceFileSv = this.getDeviceFileSvFromDeviceFile(deviceFile);
                if (!StringUtils.isEmpty(deviceFileSv)) {
                    oList.add(deviceFileSv);
                }
            }
            return RpcResponse.success(oList);
        } catch (RpcServiceException e) {
            return RpcResponse.error(e.getError(), e.getMessage());
        }
    }

    /**
     * @param ("必填")consumer,time,version
     * @param ("必填")userFlag,companyId,userFlagType
     * @return RpcResponse<Integer>
     * @Title countDeviceFileByActivePackageUserFlag
     * @Description 统计用户所激活的设备数量
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @Override
    public RpcResponse<Integer> countDeviceFileByActivePackageUserFlag(
            SupplyDeviceFileRequest request) {

        Integer ret = 0;
        if (!requestVerifyService.verifyRequest(request)) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        if (StringUtils.isEmpty(request) || StringUtils.isEmpty(request.getUserFlag()) ||
                StringUtils.isEmpty(request.getCompanyId()) || StringUtils.isEmpty(request.getUserFlagType())) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        logger.info("DeviceManagerServiceImpl::countDeviceFileByActivePackageUserFlag param request=" + request.toString());

        try {
            DeviceUserManager record = new DeviceUserManager();
            record.setUserFlag(request.getUserFlag());
            record.setFlagType(request.getUserFlagType().getValue());
            record.setCompanyId(request.getCompanyId());
            DeviceUserManager deviceUserManager = deviceUserManagerService.getDeviceUserByUniqueKey(record);
            if (StringUtils.isEmpty(deviceUserManager)) {
                return RpcResponse.success(ret);
            }

            DeviceFileSnapshot deviceFileSnapshot = new DeviceFileSnapshot();
            deviceFileSnapshot.setPackageUserId(deviceUserManager.getId());
            deviceFileSnapshot.setPackageStatu(PackageStatuEnum.PACKAGE_STATU_ALACTIVE.getValue());
            ret = deviceFileSnapshotService.countDeviceFileSnapshot(deviceFileSnapshot);
            return RpcResponse.success(ret);
        } catch (RpcServiceException e) {
            return RpcResponse.error(e.getError(), e.getMessage());
        }
    }

    /**
     * @param ("必填")consumer,time,version
     * @param ("必填")userFlag,companyId,userFlagType
     * @return RpcResponse<Integer>
     * @Title countDeviceFileByBindingUserFlag
     * @Description 统计用户所绑定的设备数量
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @Override
    public RpcResponse<Integer> countDeviceFileByBindingUserFlag(
            SupplyDeviceFileRequest request) {

        Integer ret = 0;
        if (!requestVerifyService.verifyRequest(request)) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        if (StringUtils.isEmpty(request) || StringUtils.isEmpty(request.getUserFlag()) ||
                StringUtils.isEmpty(request.getCompanyId()) || StringUtils.isEmpty(request.getUserFlagType())) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        logger.info("DeviceManagerServiceImpl::countDeviceFileByBindingUserFlag param request=" + request.toString());

        try {
            DeviceUserManager record = new DeviceUserManager();
            record.setUserFlag(request.getUserFlag());
            record.setFlagType(request.getUserFlagType().getValue());
            record.setCompanyId(request.getCompanyId());
            DeviceUserManager deviceUserManager = deviceUserManagerService.getDeviceUserByUniqueKey(record);
            if (StringUtils.isEmpty(deviceUserManager)) {
                return RpcResponse.success(ret);
            }

            DeviceFileSnapshot deviceFileSnapshot = new DeviceFileSnapshot();
            deviceFileSnapshot.setUserId(deviceUserManager.getId());
            ret = deviceFileSnapshotService.countDeviceFileSnapshot(deviceFileSnapshot);
            return RpcResponse.success(ret);
        } catch (RpcServiceException e) {
            return RpcResponse.error(e.getError(), e.getMessage());
        }
    }

    /**
     * @param ("必填")consumer,time,version
     * @param ("必填")userFlag,companyId,userFlagType,sn,imsi ("选填"外部卡外部设备)deviceCode,packageId
     * @return RpcResponse<Integer> 0:成功 其他:失败
     * @Title activeDevicePackageByBaseLogic
     * @Description 用户激活设备(对应商品激活信息) 做必要的卡机绑定，激活状态等基本逻辑判断 
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
//	@Override
//	public RpcResponse<Integer> activeDevicePackageByBaseLogic(
//			SupplyDeviceFileRequest request) {
//		
//		Integer ret = 0;
//		if(!requestVerifyService.verifyRequest(request))
//		{
//			return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
//		}
//		if(StringUtils.isEmpty(request) || StringUtils.isEmpty(request.getUserFlag()) ||
//				StringUtils.isEmpty(request.getCompanyId()) || StringUtils.isEmpty(request.getUserFlagType()) ||
//				StringUtils.isEmpty(request.getSn()) || StringUtils.isEmpty(request.getImsi()))
//		{
//			return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
//		}
//		
//		logger.info("DeviceManagerServiceImpl::activeDevicePackage param request=" + request.toString());
//		
//		try
//		{
//			//获取用户信息
//			DeviceUserManager record = new DeviceUserManager();
//			record.setUserFlag(request.getUserFlag());
//			record.setFlagType(request.getUserFlagType().getValue());
//			record.setCompanyId(request.getCompanyId());
//			DeviceUserManager deviceUserManager = deviceUserManagerService.getDeviceUserByUniqueKey(record);
//			if(StringUtils.isEmpty(deviceUserManager))
//			{
//				record.setCreatedBy(request.getConsumer());
//				record.setUpdatedBy(request.getConsumer());
//				record.setCreatedDate(new Date());
//				record.setUpdatedDate(new Date());
//				deviceUserManager = deviceUserManagerService.addDeviceUser(record);
//			}
//			
//			//如果设备未入库或者被初始化返回失败
//			DeviceFile deviceFile = deviceFileService.getDeviceFileBySn(request.getSn());
//			DeviceFileVirtual deviceFileVirtual = deviceFileVirtualService.getDeviceFileVirtualByImsi(request.getImsi());
//			if(StringUtils.isEmpty(deviceFile) && StringUtils.isEmpty(deviceFileVirtual))
//			{
//				//设备未入库
//				logger.error("DeviceManagerServiceImpl::activeDevicePackage 设备sn:" + request.getSn() + "未入库!");
//				return RpcResponse.error(ErrorCodeEnum.ERRCODE_DEVICE_NOT_EXIST);
//			}
//			if(!StringUtils.isEmpty(deviceFile) && "Y".equals(deviceFile.getDeletedFlag()))
//			{
//				//设备已被初始化
//				logger.error("DeviceManagerServiceImpl::activeDevicePackage 设备sn:" + request.getSn() + "已被初始化!");
//				return RpcResponse.error(ErrorCodeEnum.ERRCODE_DEVICE_ALREAD_INITIAL);
//			}
//			
//			DeviceFileSnapshot deviceFileSnapshot = deviceFileSnapshotService.getDeviceFileSnapshotBySn(request.getSn());
//			if(!StringUtils.isEmpty(deviceFileSnapshot) && PackageStatuEnum.PACKAGE_STATU_ALACTIVE.getValue().equals(deviceFileSnapshot.getPackageStatu()))
//			{
//				//设备已经被激活
//				logger.error("DeviceManagerServiceImpl::activeDevicePackage 设备sn:" + request.getSn() + "已被激活!");
//				return RpcResponse.error(ErrorCodeEnum.ERRCODE_DEVICE_ALREAD_ACTIVE);
//			}
//			
//			
//			DeviceCardManager cardManager = new DeviceCardManager();
//			cardManager.setImsi(request.getImsi());
//			cardManager.setCompanyId(request.getCompanyId());
//			DeviceCardManager deviceCardManager = deviceCardManagerService.getDeviceCardByUniqueKey(cardManager);
//			if(!StringUtils.isEmpty(deviceCardManager))
//			{
//				DeviceFileSnapshot snapshot = deviceFileSnapshotService.getDeviceFileSnapshotByCardId(deviceCardManager.getId());
//				if(!StringUtils.isEmpty(snapshot) && !snapshot.getSn().equals(request.getSn()))
//				{
//					//卡已经绑定在设备上
//					logger.error("DeviceManagerServiceImpl::activeDevicePackage 流量卡:" + request.getImsi() + "已经被绑定在设备 sn" + snapshot.getSn() + "上");
//					return RpcResponse.error(ErrorCodeEnum.ERRCODE_CARD_ALREAD_BAND);
//				}
//			}
//			
//			DeviceFile deviceFileEx = new DeviceFile();
//			deviceFileEx.setDeviceCode(request.getDeviceCode());
//			deviceFileEx.setPackageId(request.getPackageId());
//			deviceManagerService.activeDevicePackage(request.getSn(), request.getImsi(), deviceUserManager, deviceFileEx);
//			return RpcResponse.success(ret);
//		}
//		catch(RpcServiceException e)
//		{
//			return RpcResponse.error(e.getError(),e.getMessage());
//		}
//	}

    /**
     * @param ("必填")consumer,time,version
     * @param ("必填")userFlag,companyId,userFlagType,sn,imsi ("选填"外部卡外部设备)deviceCode,packageId
     * @return RpcResponse<Integer> 0:成功 其他:失败
     * @Title activeDevicePackage
     * @Description 用户激活设备 同步接口(对应商品激活信息) 不做任何逻辑判断 所有逻辑依赖外部逻辑判断，这里只做记录和记录修改
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
/*    @Override
    public RpcResponse<Integer> activeDevicePackage(
            SupplyDeviceFileRequest request) {

        Integer ret = 0;
        if (!requestVerifyService.verifyRequest(request)) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        if (StringUtils.isEmpty(request) || StringUtils.isEmpty(request.getUserFlag()) ||
                StringUtils.isEmpty(request.getCompanyId()) || StringUtils.isEmpty(request.getUserFlagType()) ||
                StringUtils.isEmpty(request.getSn())) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }

        logger.info("DeviceManagerServiceImpl::activeDevicePackage param request=" + request.toString());

        try {
        	logger.info("aaaaaaaaaaaaaaaaaaaaaaa01:"+System.currentTimeMillis());
            deviceManagerService.activeDevicePackage(request);
            logger.info("aaaaaaaaaaaaaaaaaaaaaaa02:"+System.currentTimeMillis());
            logger.info("DeviceManagerServiceImpl::activeDevicePackage ok!");
            return RpcResponse.success(ret);
        } catch (RpcServiceException e) {
            return RpcResponse.error(e.getError(), e.getMessage());
        }
    }*/

    /**
     * @param ("必填")consumer,time,version
     * @param ("必填")userFlag,companyId,userFlagType,sn,imsi ("选填"外部卡外部设备)deviceCode,packageId
     * @return RpcResponse<Integer> 0:成功 其他:失败
     * @Title activeDevicePackage
     * @Description 用户激活设备 异步接口(对应商品激活信息) 不做任何逻辑判断 所有逻辑依赖外部逻辑判断，这里只做记录和记录修改
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @Override
    public RpcResponse<Integer> activeDevicePackage(
            SupplyDeviceFileRequest request) {

        Integer ret = 0;
        if (!requestVerifyService.verifyRequest(request)) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        if (StringUtils.isEmpty(request) || StringUtils.isEmpty(request.getUserFlag()) ||
                StringUtils.isEmpty(request.getCompanyId()) || StringUtils.isEmpty(request.getUserFlagType()) ||
                StringUtils.isEmpty(request.getSn())) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }

        logger.info("DeviceManagerServiceImpl::activeDevicePackage param request=" + request.toString());

        try {
         //   executor.execute(new ActiveDevicePackageThread(request));
        	logger.info("aaaaaaaaaaaaaaaaaaaaaaa01:"+System.currentTimeMillis());
            deviceManagerService.activeDevicePackage(request);
            logger.info("aaaaaaaaaaaaaaaaaaaaaaa02:"+System.currentTimeMillis());
            logger.info("DeviceManagerServiceImpl::activeDevicePackage ok!");
            return RpcResponse.success(ret);
        } catch (RpcServiceException e) {
            return RpcResponse.error(e.getError(), e.getMessage());
        }
    }


    /**
     * @param ("必填")consumer,time,version
     * @param ("必填")userFlag,companyId,userFlagType,sn
     * @return RpcResponse<Integer> 0:成功 其他:失败
     * @Title bindUserToDeviceFile
     * @Description 绑定用户到设备
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @Override
    public RpcResponse<Integer> bindUserToDeviceFile(
            SupplyDeviceFileRequest request) {

        Integer ret = 0;
        if (!requestVerifyService.verifyRequest(request)) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        if (StringUtils.isEmpty(request) || StringUtils.isEmpty(request.getUserFlag()) ||
                StringUtils.isEmpty(request.getCompanyId()) || StringUtils.isEmpty(request.getSn()) ||
                StringUtils.isEmpty(request.getUserFlagType())) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        logger.info("DeviceManagerServiceImpl::bindUserToDeviceFile param request=" + request.toString());

        try {
            deviceManagerService.bindUserToDeviceFile(request);

            logger.info("DeviceManagerServiceImpl::bindUserToDeviceFile ok!");
            return RpcResponse.success(ret);
        } catch (RpcServiceException e) {
            return RpcResponse.error(e.getError(), e.getMessage());
        }
    }

    /**
     * @param ("必填")consumer,time,version
     * @param ("必填")userFlag,companyId,flagType,sn
     * @return RpcResponse<Integer> 0:成功 其他:失败
     * @Title unBindUserToDeviceFile
     * @Description 解除绑定用户到设备
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @Override
    public RpcResponse<Integer> unBindUserToDeviceFile(
            SupplyDeviceFileRequest request) {

        Integer ret = 0;
        if (!requestVerifyService.verifyRequest(request)) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        if (StringUtils.isEmpty(request) || StringUtils.isEmpty(request.getUserFlag()) ||
                StringUtils.isEmpty(request.getCompanyId()) || StringUtils.isEmpty(request.getSn()) ||
                StringUtils.isEmpty(request.getUserFlagType())) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        logger.info("DeviceManagerServiceImpl::unBindUserToDeviceFile param request=" + request.toString());

        try {
            deviceManagerService.unBindUserToDeviceFile(request);

            logger.info("DeviceManagerServiceImpl::unBindUserToDeviceFile ok!");
            return RpcResponse.success(ret);
        } catch (RpcServiceException e) {
            return RpcResponse.error(e.getError(), e.getMessage());
        }
    }

    /**
     * @param ("必填")consumer,time,version
     * @param ("必填")sn,manuFactureCode
     * @return RpcResponse<Integer> 0:成功 其他:失败
     * @Title updateDeviceFileManuFactureCode
     * @Description 修改设备的厂商码
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @Override
    public RpcResponse<Integer> updateDeviceFileManuFactureCode(
            SupplyDeviceFileRequest request) {
        Integer ret = 0;
        if (!requestVerifyService.verifyRequest(request)) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        if (StringUtils.isEmpty(request.getSn())) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        logger.info("DeviceManagerServiceImpl::updateDeviceFileManuFactureCode param request=" + request.toString());

        DeviceFile deviceFile = null;

        try {
            deviceFile = deviceFileService.getDeviceFileBySn(request.getSn());

            if (StringUtils.isEmpty(deviceFile)) {
                return RpcResponse.error(ErrorCodeEnum.ERRCODE_DEVICE_NOT_EXIST);
            }
            if ("Y".equals(deviceFile.getDeletedFlag())) {
                return RpcResponse.error(ErrorCodeEnum.ERRCODE_DEVICE_ALREAD_INITIAL);
            }

            DeviceFile updateFile = new DeviceFile();
            updateFile.setId(deviceFile.getId());
            updateFile.setManufacturerCode(request.getManufactureCode());
            deviceFileService.updateDeviceFileById(updateFile);
            return RpcResponse.success(ret);
        } catch (RpcServiceException e) {
            return RpcResponse.error(e.getError(), e.getMessage());
        }
    }

    /**
     * @param ("必填")consumer,time,version
     * @param ("必填")sn,softVersion
     * @return RpcResponse<Integer> 0:成功 其他:失败
     * @Title updateDeviceFileSoftVersion
     * @Description 修改设备的软件版本号
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @Override
    public RpcResponse<Integer> updateDeviceFileSoftVersion(
            SupplyDeviceFileRequest request) {

        Integer ret = 0;
        if (!requestVerifyService.verifyRequest(request)) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        if (StringUtils.isEmpty(request.getSn())) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        logger.info("DeviceManagerServiceImpl::updateDeviceFileSoftVersion param request=" + request.toString());

        if (!StringUtils.isEmpty(request.getSoftVersion())) {
            return RpcResponse.success(ret);
        }

        try {
            deviceManagerService.updateDeviceFileSoftVersion(request);
            logger.info("DeviceManagerServiceImpl::updateDeviceFileSoftVersion ok!");
            return RpcResponse.success(ret);
        } catch (RpcServiceException e) {
            return RpcResponse.error(e.getError(), e.getMessage());
        }
    }

    /**
     * @param ("必填")consumer,time,version
     * @param ("必填")sn,
     * @return RpcResponse<List   <   DeviceUpdateRecordSv>>
     * @Title listDeviceUpdateRecordByVehicleFlag
     * @Description 查看车辆更换设备记录列表
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @Override
    public RpcResponse<List<DeviceUpdateRecordSv>> listDeviceUpdateRecordByVehicleFlag(
            SupplyDeviceFileRequest request) {

        List<DeviceUpdateRecordSv> oList = new ArrayList<DeviceUpdateRecordSv>();
        if (!requestVerifyService.verifyRequest(request)) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        if (StringUtils.isEmpty(request) || StringUtils.isEmpty(request.getSn())) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        logger.info("DeviceManagerServiceImpl::listDeviceUpdateRecordByVehicleFlag param request=" + request.toString());

        try {
            List<DeviceUpdateRecord> recordList = new ArrayList<DeviceUpdateRecord>();
            DeviceUpdateRecord record = new DeviceUpdateRecord();
            record.setSn(request.getSn());
            record.setFlagType(UpdateRecordEnum.UPDATE_RECORD_VEHI.getValue());
            recordList = deviceUpdateRecordService.listDeviceUpdateRecord(record);
            for (DeviceUpdateRecord item : recordList) {
                DeviceUpdateRecordSv recordSv = new DeviceUpdateRecordSv();
                recordSv.setFlagId(item.getFlagId());
                recordSv.setPreFlagId(item.getPreFlagId());
                DeviceVehicleManager deviceVehicleManager = deviceVehicleManagerService.getDeviceVehicleById(item.getFlagId());
                if (!StringUtils.isEmpty(deviceVehicleManager)) {
                    recordSv.setFlagName(deviceVehicleManager.getVehicleFlag());
                }
                deviceVehicleManager = deviceVehicleManagerService.getDeviceVehicleById(item.getPreFlagId());
                if (!StringUtils.isEmpty(deviceVehicleManager)) {
                    recordSv.setPreFlagName(deviceVehicleManager.getVehicleFlag());
                }
                recordSv.setFlagType(UpdateRecordEnum.fromString(item.getFlagType()));
                oList.add(recordSv);
            }
            return RpcResponse.success(oList);
        } catch (RpcServiceException e) {
            return RpcResponse.error(e.getError(), e.getMessage());
        }
    }

    /**
     * @param ("必填")consumer,time,version
     * @param "必填")sn,
     * @return RpcResponse<List   <   DeviceUpdateRecordSv>>
     * @Title listDeviceUpdateRecordByImsi
     * @Description 查看设备更换卡记录表
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @Override
    public RpcResponse<List<DeviceUpdateRecordSv>> listDeviceUpdateRecordByImsi(
            SupplyDeviceFileRequest request) {

        List<DeviceUpdateRecordSv> oList = new ArrayList<DeviceUpdateRecordSv>();
        if (!requestVerifyService.verifyRequest(request)) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        if (StringUtils.isEmpty(request) || StringUtils.isEmpty(request.getSn())) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        logger.info("DeviceManagerServiceImpl::listDeviceUpdateRecordByImsi param request=" + request.toString());

        try {
            List<DeviceUpdateRecord> recordList = new ArrayList<DeviceUpdateRecord>();
            DeviceUpdateRecord record = new DeviceUpdateRecord();
            record.setSn(request.getSn());
            record.setFlagType(UpdateRecordEnum.UPDATE_RECORD_CARD.getValue());
            recordList = deviceUpdateRecordService.listDeviceUpdateRecord(record);
            for (DeviceUpdateRecord item : recordList) {
                DeviceUpdateRecordSv recordSv = new DeviceUpdateRecordSv();
                recordSv.setFlagId(item.getFlagId());
                recordSv.setPreFlagId(item.getPreFlagId());
                DeviceCardManager deviceCardManager = deviceCardManagerService.getDeviceCardById(item.getFlagId());
                if (!StringUtils.isEmpty(deviceCardManager)) {
                    recordSv.setFlagName(deviceCardManager.getImsi());
                }
                deviceCardManager = deviceCardManagerService.getDeviceCardById(item.getPreFlagId());
                if (!StringUtils.isEmpty(deviceCardManager)) {
                    recordSv.setPreFlagName(deviceCardManager.getImsi());
                }
                recordSv.setFlagType(UpdateRecordEnum.fromString(item.getFlagType()));
                oList.add(recordSv);
            }
            return RpcResponse.success(oList);
        } catch (RpcServiceException e) {
            return RpcResponse.error(e.getError(), e.getMessage());
        }
    }

    /**
     * @param ("必填")consumer,time,version
     * @param ("必填")sn,
     * @return RpcResponse<List   <   DeviceUpdateRecordSv>>
     * @Title listDeviceUpdateRecordByActiveUserFlag
     * @Description 查看设备的用户激活记录表
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @Override
    public RpcResponse<List<DeviceUpdateRecordSv>> listDeviceUpdateRecordByActiveUserFlag(
            SupplyDeviceFileRequest request) {

        List<DeviceUpdateRecordSv> oList = new ArrayList<DeviceUpdateRecordSv>();
        if (!requestVerifyService.verifyRequest(request)) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        if (StringUtils.isEmpty(request) || StringUtils.isEmpty(request.getSn())) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        logger.info("DeviceManagerServiceImpl::listDeviceUpdateRecordByActiveUserFlag param request=" + request.toString());

        try {
            List<DeviceUpdateRecord> recordList = new ArrayList<DeviceUpdateRecord>();
            DeviceUpdateRecord record = new DeviceUpdateRecord();
            record.setSn(request.getSn());
            record.setFlagType(UpdateRecordEnum.UPDATE_RECORD_ACTI.getValue());
            recordList = deviceUpdateRecordService.listDeviceUpdateRecord(record);
            for (DeviceUpdateRecord item : recordList) {
                DeviceUpdateRecordSv recordSv = new DeviceUpdateRecordSv();
                recordSv.setFlagId(item.getFlagId());
                recordSv.setPreFlagId(item.getPreFlagId());
                DeviceUserManager deviceUserManager = deviceUserManagerService.getDeviceUserById(item.getFlagId());
                if (!StringUtils.isEmpty(deviceUserManager)) {
                    recordSv.setFlagName(deviceUserManager.getUserFlag());
                }
                deviceUserManager = deviceUserManagerService.getDeviceUserById(item.getPreFlagId());
                if (!StringUtils.isEmpty(deviceUserManager)) {
                    recordSv.setPreFlagName(deviceUserManager.getUserFlag());
                }
                recordSv.setFlagType(UpdateRecordEnum.fromString(item.getFlagType()));
                oList.add(recordSv);
            }
            return RpcResponse.success(oList);
        } catch (RpcServiceException e) {
            return RpcResponse.error(e.getError(), e.getMessage());
        }
    }

    /**
     * @param ("必填")consumer,time,version
     * @param ("必填")sn,
     * @return RpcResponse<List   <   DeviceUpdateRecordSv>>
     * @Title listDeviceUpdateRecordByBindUserFlag
     * @Description 查看设备的用户绑定记录表
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @Override
    public RpcResponse<List<DeviceUpdateRecordSv>> listDeviceUpdateRecordByBindUserFlag(
            SupplyDeviceFileRequest request) {

        List<DeviceUpdateRecordSv> oList = new ArrayList<DeviceUpdateRecordSv>();
        if (!requestVerifyService.verifyRequest(request)) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        if (StringUtils.isEmpty(request) || StringUtils.isEmpty(request.getSn())) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        logger.info("DeviceManagerServiceImpl::listDeviceUpdateRecordByBindUserFlag param request=" + request.toString());

        try {
            List<DeviceUpdateRecord> recordList = new ArrayList<DeviceUpdateRecord>();
            DeviceUpdateRecord record = new DeviceUpdateRecord();
            record.setSn(request.getSn());
            record.setFlagType(UpdateRecordEnum.UPDATE_RECORD_USER.getValue());
            recordList = deviceUpdateRecordService.listDeviceUpdateRecord(record);
            for (DeviceUpdateRecord item : recordList) {
                DeviceUpdateRecordSv recordSv = new DeviceUpdateRecordSv();
                recordSv.setFlagId(item.getFlagId());
                recordSv.setPreFlagId(item.getPreFlagId());
                DeviceUserManager deviceUserManager = deviceUserManagerService.getDeviceUserById(item.getFlagId());
                if (!StringUtils.isEmpty(deviceUserManager)) {
                    recordSv.setFlagName(deviceUserManager.getUserFlag());
                }
                deviceUserManager = deviceUserManagerService.getDeviceUserById(item.getPreFlagId());
                if (!StringUtils.isEmpty(deviceUserManager)) {
                    recordSv.setPreFlagName(deviceUserManager.getUserFlag());
                }
                recordSv.setFlagType(UpdateRecordEnum.fromString(item.getFlagType()));
                oList.add(recordSv);
            }
            return RpcResponse.success(oList);
        } catch (RpcServiceException e) {
            return RpcResponse.error(e.getError(), e.getMessage());
        }
    }

    private DeviceFileSv getDeviceFileSvFromDeviceFileVirtual(DeviceFileVirtual deviceFileVirtual) {
        if (StringUtils.isEmpty(deviceFileVirtual)) {
            return null;
        }
        DeviceCode deviceCode = deviceCodeService.getDeviceCodeByDeviceCode(deviceFileVirtual.getDeviceCode());
        DeviceFileSv deviceFileSv = new DeviceFileSv();
        deviceFileSv.setImsi(deviceFileVirtual.getImsi());
        deviceFileSv.setIccid(deviceFileVirtual.getIccid());
        deviceFileSv.setDeviceTypeId(deviceCode.getTypeId());
        deviceFileSv.setSn(deviceFileVirtual.getSn());
        deviceFileSv.setDeviceCode(deviceFileVirtual.getDeviceCode());
        deviceFileSv.setVerifCode(deviceFileVirtual.getVerifCode());
        deviceFileSv.setBatchNo(deviceFileVirtual.getBatchNo());
        deviceFileSv.setPackageId(deviceFileVirtual.getPackageId());
        deviceFileSv.setOperatorMerchantId(deviceFileVirtual.getOperatorMerchantNo());
        deviceFileSv.setSendMerchantId(deviceFileVirtual.getSendMerchantNo());
        deviceFileSv.setManufacturerCode(deviceFileVirtual.getManufacturerCode());
        deviceFileSv.setAndroidPackageId(deviceFileVirtual.getAndroidPackageId());
        deviceFileSv.setPackageStatu(PackageStatuEnum.PACKAGE_STATU_UNACTIVE);
        SystemPlatEnum systemPlat = SystemPlatEnum.SYSTEM_PLAT_MIDD_PERIOD;
		/*if(OutStorageTypeEnum.OUT_STORAGE_TYPE_SYNC.equals(OutStorageTypeEnum.valueOf(deviceFileVirtual.getOutStorageType())))
		{
			systemPlat = SystemPlatEnum.SYSTEM_PLAT_PRIM_PERIOD;
		}*/
        deviceFileSv.setSysPlat(systemPlat);
        deviceFileSv.setExternalFlag(ExternalFlagEnum.EXTERNALFLAG_EX);
        deviceFileSv.setDeletedFlag(deviceFileVirtual.getDeletedFlag());

        return deviceFileSv;
    }

    private DeviceFileSv getDeviceFileSvFromDeviceFile(DeviceFile deviceFile) {
        if (StringUtils.isEmpty(deviceFile)) {
            return null;
        }

        DeviceFileSv deviceFileSv = new DeviceFileSv();
        deviceFileSv.setSn(deviceFile.getSn());
        deviceFileSv.setId(deviceFile.getId());
        deviceFileSv.setDeviceCode(deviceFile.getDeviceCode());
        deviceFileSv.setOrderCode(deviceFile.getOrderCode());
        deviceFileSv.setVerifCode(deviceFile.getVerifCode());
        deviceFileSv.setBatchNo(deviceFile.getBatchNo());
        deviceFileSv.setPackageId(deviceFile.getPackageId());
        deviceFileSv.setOperatorMerchantId(deviceFile.getOperatorMerchantNo());
        deviceFileSv.setSendMerchantId(deviceFile.getSendMerchantNo());
        deviceFileSv.setInStorageTime(deviceFile.getInStorageTime());
        deviceFileSv.setOutStorageTime(deviceFile.getOutStorageTime());
        deviceFileSv.setOutStorageType(OutStorageTypeEnum.fromString(deviceFile.getOutStorageType()));
        deviceFileSv.setTerminalDiscode(deviceFile.getTerminalDiscode());
        deviceFileSv.setManufacturerCode(deviceFile.getManufacturerCode());
        deviceFileSv.setPackageStatu(PackageStatuEnum.PACKAGE_STATU_UNACTIVE); //默认后面查询覆盖
        //判断系统所在平台
        SystemPlatEnum systemPlat = SystemPlatEnum.SYSTEM_PLAT_MIDD_PERIOD;
		/*if(OutStorageTypeEnum.OUT_STORAGE_TYPE_SYNC.equals(OutStorageTypeEnum.fromString(deviceFile.getOutStorageType())))
		{
			systemPlat = SystemPlatEnum.SYSTEM_PLAT_PRIM_PERIOD;
		}*/
        deviceFileSv.setSysPlat(systemPlat);
        deviceFileSv.setExternalFlag(ExternalFlagEnum.fromString(deviceFile.getExternalFlag()));
        deviceFileSv.setDeletedFlag(deviceFile.getDeletedFlag());

        DeviceCode deviceCode = deviceCodeService.getDeviceCodeByDeviceCode(deviceFile.getDeviceCode());
        if (!StringUtils.isEmpty(deviceCode)) {
            deviceFileSv.setDeviceName(deviceCode.getDeviceName());
            deviceFileSv.setDeviceTypeId(deviceCode.getTypeId());
            deviceFileSv.setDeviceTypeName(deviceTypeService.getDeviceTypeNameById(deviceCode.getTypeId()));
            deviceFileSv.setMerchantId(deviceCode.getMerchantId());
            deviceFileSv.setManuCodesSet(deviceCode.getManufacturerCode());
        }

        DeviceFileSnapshot deviceFileSnapshot = null;
        try {
            deviceFileSnapshot = deviceFileSnapshotService.getDeviceFileSnapshotBySn(deviceFile.getSn());
        } catch (RpcServiceException e) {
            logger.info("DeviceManagerServiceImpl::getDeviceFileSvFromDeviceFile error=" + e.getError().getValue() + "message=" + e.getMessage());
            return deviceFileSv;
        }

        if (StringUtils.isEmpty(deviceFileSnapshot)) {
            return deviceFileSv;
        }

        deviceFileSv.setPackageStatu(PackageStatuEnum.fromString(deviceFileSnapshot.getPackageStatu()));
        deviceFileSv.setPackageUserTime(deviceFileSnapshot.getPackageUserTime());
        deviceFileSv.setUserTime(deviceFileSnapshot.getUserTime());
        deviceFileSv.setCardTime(deviceFileSnapshot.getCardTime());
        deviceFileSv.setCreatedBy(deviceFileSnapshot.getCreatedBy());
        deviceFileSv.setUpdatedBy(deviceFileSnapshot.getUpdatedBy());
        deviceFileSv.setCreatedDate(deviceFileSnapshot.getCreatedDate());
        deviceFileSv.setUpdatedDate(deviceFileSnapshot.getUpdatedDate());

        //当前激活用户
        try {
            DeviceUserManager deviceUserManager = deviceUserManagerService.getDeviceUserById(deviceFileSnapshot.getPackageUserId());
            if (!StringUtils.isEmpty(deviceUserManager)) {
                deviceFileSv.setPackageUserFlag(deviceUserManager.getUserFlag());
                deviceFileSv.setPackageUserFlagType(UserFlagTypeEnum.fromString(deviceUserManager.getFlagType()));
            }
        } catch (RpcServiceException e) {
            logger.info("DeviceManagerServiceImpl::getDeviceFileSvFromDeviceFile error=" + e.getError().getValue() + "message=" + e.getMessage());
        }

        //当前绑定用户
        try {
            DeviceUserManager deviceUserManager = deviceUserManagerService.getDeviceUserById(deviceFileSnapshot.getUserId());
            if (!StringUtils.isEmpty(deviceUserManager)) {
                deviceFileSv.setUserFlag(deviceUserManager.getUserFlag());
                deviceFileSv.setUserFlagType(UserFlagTypeEnum.fromString(deviceUserManager.getFlagType()));
            }
        } catch (RpcServiceException e) {
            logger.info("DeviceManagerServiceImpl::getDeviceFileSvFromDeviceFile error=" + e.getError().getValue() + "message=" + e.getMessage());
        }

        //当前绑定卡
        try {
            DeviceCardManager deviceCardManager = deviceCardManagerService.getDeviceCardById(deviceFileSnapshot.getCardId());
            if (!StringUtils.isEmpty(deviceCardManager)) {
                deviceFileSv.setIccid(deviceCardManager.getIccid());
                deviceFileSv.setImsi(deviceCardManager.getImsi());
            }
        } catch (RpcServiceException e) {
            logger.info("DeviceManagerServiceImpl::getDeviceFileSvFromDeviceFile error=" + e.getError().getValue() + "message=" + e.getMessage());
        }

        //获取当前版本号
        try {
            FirmwareInfo firmwareInfo = supplayChainAdminRemoteService.getFirmwareInfoById(deviceFileSnapshot.getFirmwareId());
            if (StringUtils.isEmpty(firmwareInfo)) {
                deviceFileSv.setSoftVersion(firmwareInfo.getSoftVersion());
            }
        } catch (RpcServiceException e) {
            logger.info("DeviceManagerServiceImpl::getDeviceFileSvFromDeviceFile error=" + e.getError().getValue() + "message=" + e.getMessage());
        }

        //获取当前车辆信息
        try {
            DeviceVehicleManager deviceVehicleManager = deviceVehicleManagerService.getDeviceVehicleById(deviceFileSnapshot.getVehicleId());
            if (StringUtils.isEmpty(deviceVehicleManager)) {
                deviceFileSv.setVehicleFlag(deviceVehicleManager.getVehicleFlag());
                deviceFileSv.setVehicleFlagType(VehicleFlagTypeEnum.fromString(deviceVehicleManager.getFlagType()));
            }
        } catch (RpcServiceException e) {
            logger.info("DeviceManagerServiceImpl::getDeviceFileSvFromDeviceFile error=" + e.getError().getValue() + "message=" + e.getMessage());
        }

        return deviceFileSv;

    }

    /**
     * @param ("必填")deviceCode
     * @return 0:成功 其他:失败
     * @Title addAndUpdateDeviceCode
     * @Description 设备分类添加和修改
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @Override
    public RpcResponse<Integer> addAndUpdateDeviceCode(DeviceCode deviceCode) {

        Integer ret = 0;

        if (StringUtils.isEmpty(deviceCode)) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }

        logger.info("DeviceManagerServiceImpl::addAndUpdateDeviceCode param deviceCode=" + deviceCode.toString());

        try {
            if (StringUtils.isEmpty(deviceCode.getId())) {
                deviceCodeService.addDeviceCode(deviceCode);
            } else {
                deviceCodeService.updateDeviceCode(deviceCode);
            }
            return RpcResponse.success(ret);
        } catch (RpcServiceException e) {
            return RpcResponse.error(e.getError(), e.getMessage());
        }
    }


	@Override
	public RpcResponse<DeviceImeiStock> getDeviceImeiStockBySn(
			SupplyDeviceFileRequest request) {
		
		if(!requestVerifyService.verifyRequest(request))
		{
			return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		if(StringUtils.isEmpty(request) || StringUtils.isEmpty(request.getSn()))
		{
			return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		logger.info("DeviceManagerServiceImpl::getDeviceImeiStockBySn param request=" + request.toString());
		
		try
		{
			DeviceImeiStock record = new DeviceImeiStock();
			record.setImei(request.getSn());
			return RpcResponse.success(deviceImeiStockService.getDeviceImeiStockByImei(record));
		}
		catch(RpcServiceException e)
		{
			return RpcResponse.error(e.getError(),e.getMessage());
		}
	}


    @Override
    public RpcResponse<Integer> saveDeviceFileUnstock(SupplyDeviceFileRequest request) {
        if (!requestVerifyService.verifyRequest(request)) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        if (StringUtils.isEmpty(request) || StringUtils.isEmpty(request.getImsi())) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        try {
            return RpcResponse.success(deviceFileUnstockService.saveDeviceFileUnstock(request));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }
    
    private void getMerchantNameForDeviceCodeList(List<DeviceCode> oList)
	{
		if(StringUtils.isEmpty(oList))
		{
			return;
		}
		
		for(DeviceCode deviceCode:oList)
		{
			if(StringUtils.isEmpty(deviceCode))
			{
				continue;
			}
			if(StringUtils.isEmpty(deviceCode.getMerchantId()))
			{
				continue;
			}
			
			try
			{
				SupplyChainMerchantInfo merchantInfo = externalService.findMerchantInfoByMerchantId(deviceCode.getMerchantId());
				if(!StringUtils.isEmpty(merchantInfo))
				{
					deviceCode.setMerchantName(merchantInfo.getName());
				}
			}
			catch(RpcServiceException e)
			{
				logger.error("DeviceManagerAdminRemoteImpl::getMerchantNameForDeviceCodeList: e.getError=" + e.getError() + " e.getError.getValue=" + e.getError().getValue());
			}
		}
	}

}
