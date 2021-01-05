package cn.com.glsx.supplychain.jst.remote;

import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.framework.core.enums.DefaultErrorEnum;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.framework.core.util.RpcAssert;
import cn.com.glsx.supplychain.jst.dto.*;
import cn.com.glsx.supplychain.jst.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.jst.enums.JstErrorCodeEnum;
import cn.com.glsx.supplychain.jst.mapper.BsDealerUserInfoMapper;
import cn.com.glsx.supplychain.jst.mapper.BsMerchantStockDeviceMapper;
import cn.com.glsx.supplychain.jst.mapper.JstShopMapper;
import cn.com.glsx.supplychain.jst.mapper.JstShopOrderDetailMapper;
import cn.com.glsx.supplychain.jst.model.BsDealerUserInfo;
import cn.com.glsx.supplychain.jst.model.BsMerchantStockDevice;
import cn.com.glsx.supplychain.jst.model.JstShop;
import cn.com.glsx.supplychain.jst.model.deviceMerchantReflect;
import cn.com.glsx.supplychain.jst.service.*;
import cn.com.glsx.supplychain.jst.util.StringUtil;
import com.alibaba.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @ClassName JxBsMerchantRemoteImpl
 * @Description 进销存系统后台rpc接口
 * @Author xiex
 * @Date 2020/2/13 21:55
 * @Version 1.0
 */
@Component("JxBsMerchantRemote")
@Service(version = "1.0.0", protocol = "dubbo", timeout = 10000)
public class JxBsMerchantRemoteImpl implements JxBsMerchantRemote {

    private static final Logger LOGGER = LoggerFactory.getLogger(JxBsMerchantRemoteImpl.class);

    @Autowired
    private RequestVerifyService verifyService;
    @Autowired
    private JxBsMerchantService jxBsMerchantService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private BsMerchantStockDeviceMapper bsMerchantStockDeviceMapper;
    @Autowired
    private BsMerchantStockDeviceService stockDeviceService;
    @Autowired
    private DeviceMerchantReflectService merchantReflectService;
    @Autowired
    private DeviceFileService deviceFileService;

    @Autowired
    private JstShopMapper jstShopMapper;

    @Autowired
    private JstShopOrderDetailMapper jstShopOrderDetailMapper;

    @Autowired
    private BsDealerUserInfoMapper bsDealerUserInfoMapper;

    @Override
    public RpcResponse<RpcPagination<JstShopOrderDTO>> pageJstShopOrder(RpcPagination<JstShopOrderDTO> jstShopOrderDTO) {
        RpcResponse<RpcPagination<JstShopOrderDTO>> result;
        LOGGER.info("JxBsMerchantRemoteImpl :pageJstShopOrder {};" + jstShopOrderDTO.getCondition());
        try {
            RpcAssert.assertNotNull(jstShopOrderDTO, DefaultErrorEnum.PARAMETER_NULL, "jstShopOrderDTO must not be null");
            RpcAssert.assertNotNull(jstShopOrderDTO.getPageNum(), DefaultErrorEnum.PARAMETER_NULL, "pageNum must not be null");
            RpcAssert.assertNotNull(jstShopOrderDTO.getPageSize(), DefaultErrorEnum.PARAMETER_NULL, "pageSize must not be null");
            result = RpcResponse.success(RpcPagination.copyPage(jxBsMerchantService.pageJstShopOrder(jstShopOrderDTO.getPageNum(),
                    jstShopOrderDTO.getPageSize(),
                    jstShopOrderDTO.getCondition())));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<CheckImportShopOrderDetailDTO> checkImportDispatchDetail(CheckImportShopOrderDetailDTO record) {
        RpcResponse<CheckImportShopOrderDetailDTO> result;
        LOGGER.info("WxBsMerchantRemoteImpl::checkImportDispatchDetail start record:{}", record);
        try {
            RpcAssert.assertNotNull(record, DefaultErrorEnum.PARAMETER_NULL, "record must not be null");
            RpcAssert.assertNotNull(record.getShopOrderCode(), DefaultErrorEnum.PARAMETER_NULL, "record.getShopOrderCode must not be null");
            RpcAssert.assertNotNull(record.getMerchantCode(), DefaultErrorEnum.PARAMETER_NULL, "record.getMerchantCode must not be null");
            RpcAssert.assertNotNull(record.getListShopOrderDetailDto(), DefaultErrorEnum.PARAMETER_NULL, "record.getListShopOrderDetailDto must not be null");
            RpcAssert.assertIsTrue(verifyService.verifyRequest(record), JstErrorCodeEnum.ERRCODE_RPC_FORBIDDEN, "rpc frobidden");
            result = RpcResponse.success(orderService.batchCheckShopOrderDetail(record));
        } catch (RpcServiceException e) {
            return RpcResponse.error(e.getError(), e.getMessage());
        }
        LOGGER.info("WxBsMerchantRemoteImpl::checkImportDispatchDetail end result:{}", result);
        return result;
    }

  /*  @Override
    public RpcResponse<CheckImportDataDTO> checkImportDispatchDetail(List<JstShopOrderDetailImportDTO> jstShopOrderDetailImportDTOList) {
        CheckImportDataDTO checkImportDataDTO = new CheckImportDataDTO();
        try {
            if (StringUtil.isEmpty(jstShopOrderDetailImportDTOList) || jstShopOrderDetailImportDTOList.size() == 0) {
                return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
            }
            if (jstShopOrderDetailImportDTOList.size() > 5000) {
                return RpcResponse.error(ErrorCodeEnum.ERRCODE_MAX_IMPORT_SIZE);
            }
            //导入成功的数据返回
            List<JstShopOrderDetailImportDTO> successList = new ArrayList<>();
            //导入失败的 数据
            List<JstShopOrderDetailExportDTO> failList = new ArrayList<>();

            LOGGER.info("JxBsMerchantRemoteImpl :checkImportDispatchDetail 检验开始" + new Date());
            for (JstShopOrderDetailImportDTO item : jstShopOrderDetailImportDTOList) {
                LOGGER.info("JxBsMerchantRemoteImpl::checkImportDispatchDetail handle data item=" + item.toString());
                JstShopOrderDetailExportDTO fail = new JstShopOrderDetailExportDTO();
                boolean add = true;
                //验证所有字段是否为空
                if (!StringUtil.isEmpty(item) && !StringUtil.isEmpty(item.getSn())) {
                    BeanUtils.copyProperties(fail, item);
                    fail.setFailDesc(ErrorCodeEnum.INVALID_DEVICE_DATA_FORMAT.getDescrible());
                    failList.add(fail);
                    add = false;
                    continue;
                }

                //验证SN是否在库存
                BsMerchantStockDevice bsMerchantStockDevice = new BsMerchantStockDevice();
                bsMerchantStockDevice.setSn(item.getSn());
                bsMerchantStockDevice.setDeletedFlag("N");
                List<BsMerchantStockDevice> bsMerchantStockDeviceList = bsMerchantStockDeviceMapper.select(bsMerchantStockDevice);
                if (bsMerchantStockDeviceList != null && bsMerchantStockDeviceList.size() > 0) {
                    if (bsMerchantStockDeviceList.size() > 1) {
                        BeanUtils.copyProperties(fail, item);
                        fail.setFailDesc(ErrorCodeEnum.ERRCODE_SN.getDescrible());
                        failList.add(fail);
                        add = false;
                        continue;
                    }
                    if (!BsMerchantStockDeviceStatusEnum.IN.getCode().equals(bsMerchantStockDeviceList.get(0).getStatu())) {
                        BeanUtils.copyProperties(fail, item);
                        fail.setFailDesc(ErrorCodeEnum.ERRCODE_NO_SN.getDescrible());
                        failList.add(fail);
                        add = false;
                        continue;
                    }
                } else {
                    BeanUtils.copyProperties(fail, item);
                    fail.setFailDesc(ErrorCodeEnum.ERRCODE_NO_SN.getDescrible());
                    failList.add(fail);
                    add = false;
                    continue;
                }
                if (add) {
                    successList.add(item);
                }
            }
            LOGGER.info("JxBsMerchantRemoteImpl :checkImportDispatchDetail 检验结束" + new Date());
            LOGGER.info("JxBsMerchantRemoteImpl::checkImportDispatchDetail: check ok !");
            checkImportDataDTO.setJstShopOrderDetailImportDTOList(successList);
            checkImportDataDTO.setJstShopOrderDetailExportDTOList(failList);
            return RpcResponse.success(checkImportDataDTO);
        } catch (RpcServiceException e) {
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e.getError(), e.getMessage());
        }
    }*/

    @Override
    public RpcResponse<CheckImportShopOrderDetailDTO> dispatchJstShopOrderDetail(CheckImportShopOrderDetailDTO record) {
        RpcResponse<CheckImportShopOrderDetailDTO> result;
        LOGGER.info("WxBsMerchantRemoteImpl::dispatchJstShopOrderDetail start record:{}", record);
        try {
            RpcAssert.assertNotNull(record, DefaultErrorEnum.PARAMETER_NULL, "record must not be null");
            RpcAssert.assertNotNull(record.getLogisticsDto(), DefaultErrorEnum.PARAMETER_NULL, "record.getLogisticsDto must not be null");
            RpcAssert.assertNotNull(record.getLogisticsDto().getCompany(), DefaultErrorEnum.PARAMETER_NULL, "record.getCompany must not be null");
            RpcAssert.assertNotNull(record.getLogisticsDto().getOrderNumber(), DefaultErrorEnum.PARAMETER_NULL, "record.getLogisticsDto must not be null");
            RpcAssert.assertNotNull(record.getShopOrderCode(), DefaultErrorEnum.PARAMETER_NULL, "record.getShopOrderCode must not be null");
            RpcAssert.assertNotNull(record.getMerchantCode(), DefaultErrorEnum.PARAMETER_NULL, "record.getMerchantCode must not be null");
            //RpcAssert.assertNotNull(record.getListShopOrderDetailDto(), DefaultErrorEnum.PARAMETER_NULL, "record.getListShopOrderDetailDto must not be null");
            RpcAssert.assertIsTrue(verifyService.verifyRequest(record), JstErrorCodeEnum.ERRCODE_RPC_FORBIDDEN, "rpc frobidden");
            result = RpcResponse.success(orderService.batchSubmitShopOrderDetail(record));
        } catch (RpcServiceException e) {
            return RpcResponse.error(e.getError(), e.getMessage());
        }
        LOGGER.info("WxBsMerchantRemoteImpl::dispatchJstShopOrderDetail end result:{}", result);
        return result;
    }
    
  /*  public RpcResponse<Integer> dispatchJstShopOrderDetail(JstShopOrderDetailDTO jstShopOrderDetailDTO) {
        RpcResponse<Integer> result;
        try {
            RpcAssert.assertNotNull(jstShopOrderDetailDTO, DefaultErrorEnum.PARAMETER_NULL, "jstShopOrderDetailDTO must not be null");
            RpcAssert.assertNotNull(jstShopOrderDetailDTO.getShopOrderCode(), DefaultErrorEnum.PARAMETER_NULL, "ShopOrderCode must not be null");
            RpcAssert.assertNotNull(jstShopOrderDetailDTO.getLogisticsCompany(), DefaultErrorEnum.PARAMETER_NULL, "LogisticsCompany must not be null");
            RpcAssert.assertNotNull(jstShopOrderDetailDTO.getLogisticsOrderNumber(), DefaultErrorEnum.PARAMETER_NULL, "LogisticsOrderNumber must not be null");
            RpcAssert.assertNotNull(jstShopOrderDetailDTO.getSnList(), DefaultErrorEnum.PARAMETER_NULL, "SnList must not be null");
            RpcAssert.assertNotNull(jstShopOrderDetailDTO.getCreatedBy(), DefaultErrorEnum.PARAMETER_NULL, "CreatedBy must not be null");
            result = RpcResponse.success(jxBsMerchantService.dispatchJstShopOrderDetail(jstShopOrderDetailDTO));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }*/


    @Override
    public RpcResponse<List<JstShopOrderDetailDTO>> getJstShopOrderDetailList(JstShopOrderDetailDTO jstShopOrderDetailDTO) {
        RpcResponse<List<JstShopOrderDetailDTO>> result;
        try {
            RpcAssert.assertNotNull(jstShopOrderDetailDTO, DefaultErrorEnum.PARAMETER_NULL, "jstShopOrderDetailDTO must not be null");
            RpcAssert.assertNotNull(jstShopOrderDetailDTO.getShopOrderCode(), DefaultErrorEnum.PARAMETER_NULL, "ShopOrderCode must not be null");
            RpcAssert.assertIsTrue(verifyService.verifyRequest(jstShopOrderDetailDTO), JstErrorCodeEnum.ERRCODE_RPC_FORBIDDEN, "rpc frobidden");
            result = RpcResponse.success(jxBsMerchantService.getJstShopOrderDetailList(jstShopOrderDetailDTO));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<RpcPagination<BsMerchantStockDeviceDTO>> pageBsMerchantStockDevice(RpcPagination<BsMerchantStockDeviceDTO> bsMerchantStockDeviceDTO) {
        RpcResponse<RpcPagination<BsMerchantStockDeviceDTO>> result;
        try {
            RpcAssert.assertNotNull(bsMerchantStockDeviceDTO, DefaultErrorEnum.PARAMETER_NULL, "bsMerchantStockDeviceDTO must not be null");
            RpcAssert.assertNotNull(bsMerchantStockDeviceDTO.getPageNum(), DefaultErrorEnum.PARAMETER_NULL, "pageNum must not be null");
            RpcAssert.assertNotNull(bsMerchantStockDeviceDTO.getPageSize(), DefaultErrorEnum.PARAMETER_NULL, "pageSize must not be null");
            result = RpcResponse.success(RpcPagination.copyPage(jxBsMerchantService.pageBsMerchantStockDevice(bsMerchantStockDeviceDTO.getPageNum(),
                    bsMerchantStockDeviceDTO.getPageSize(),
                    bsMerchantStockDeviceDTO.getCondition())));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<BsMerchantStockDeviceDTO> getSumBsMerchantStockDeviceList(BsMerchantStockDeviceDTO bsMerchantStockDeviceDTO) {
        RpcResponse<BsMerchantStockDeviceDTO> result;
        try {
            RpcAssert.assertNotNull(bsMerchantStockDeviceDTO, DefaultErrorEnum.PARAMETER_NULL, "bsMerchantStockDeviceDTO must not be null");
            //RpcAssert.assertNotNull(bsMerchantStockDeviceDTO.getMerchantCode(), DefaultErrorEnum.PARAMETER_NULL, "bsMerchantStockDeviceDTO must not be null");
            result = RpcResponse.success(jxBsMerchantService.getSumBsMerchantStockDeviceList(bsMerchantStockDeviceDTO));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<RpcPagination<JstShopDTO>> pageMyJstShop(RpcPagination<JstShopDTO> JstShopDTO) {
        RpcResponse<RpcPagination<JstShopDTO>> result;
        try {
            RpcAssert.assertNotNull(JstShopDTO, DefaultErrorEnum.PARAMETER_NULL, "pagination must not be null");
            RpcAssert.assertNotNull(JstShopDTO.getPageNum(), DefaultErrorEnum.PARAMETER_NULL, "pageNum must not be null");
            RpcAssert.assertNotNull(JstShopDTO.getPageSize(), DefaultErrorEnum.PARAMETER_NULL, "pageSize must not be null");
            result = RpcResponse.success(RpcPagination.copyPage(jxBsMerchantService.pageMyJstShop(JstShopDTO.getPageNum(),
                    JstShopDTO.getPageSize(),
                    JstShopDTO.getCondition())));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<CheckImportDataDTO> checkImportMyJstShop(List<MyJstShopImportDTO> myJstShopImportDTOList) {
        CheckImportDataDTO checkImportDataDTO = new CheckImportDataDTO();
        try {
        	Map<String,Integer> mapShopName = new HashMap<>();
            if (StringUtil.isEmpty(myJstShopImportDTOList) || myJstShopImportDTOList.size() == 0) {
                return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
            }
            if (myJstShopImportDTOList.size() > 5000) {
                return RpcResponse.error(ErrorCodeEnum.ERRCODE_MAX_IMPORT_SIZE);
            }
            Integer mapValue = null;
            for(MyJstShopImportDTO dto:myJstShopImportDTOList){
            	mapValue = mapShopName.get(dto.getShopName()+dto.getAgentMerchantName());
            	if(null == mapValue){
            		mapShopName.put(dto.getShopName()+dto.getAgentMerchantName(), 1);
            	}else{
            		mapShopName.put(dto.getShopName()+dto.getAgentMerchantName(), 2);
            	}
            }
            
            //导入成功的数据返回
            List<MyJstShopImportDTO> successList = new ArrayList<>();
            //导入失败的 数据
            List<MyJstShopExportDTO> failList = new ArrayList<>();


            LOGGER.info("JxBsMerchantRemoteImpl :checkImportMyJstShop 检验开始" + new Date());
            for (MyJstShopImportDTO item : myJstShopImportDTOList) {
                LOGGER.info("JxBsMerchantRemoteImpl::checkImportMyJstShop handle data item=" + item.toString());
                MyJstShopExportDTO fail = new MyJstShopExportDTO();
                boolean add = true;
                mapValue = mapShopName.get(item.getShopName()+item.getAgentMerchantName());
                if(mapValue!=null&&mapValue == 2){
                	BeanUtils.copyProperties(item, fail);
                    fail.setFailDesc(ErrorCodeEnum.ERRCODE_SHOP_NAME.getDescrible());
                    failList.add(fail);
                    add = false;
                    continue;
                }
                //验证所有字段是否为空
                if (!StringUtil.isEmpty(item) && this.objCheckMyIsNull(item)) {
                    BeanUtils.copyProperties(item, fail);
                    fail.setFailDesc(ErrorCodeEnum.INVALID_DEVICE_DATA_FORMAT.getDescrible());
                    failList.add(fail);
                    add = false;
                    continue;
                }

                if (item.getAddr().length() > 200) {
                    BeanUtils.copyProperties(item, fail);
                    fail.setFailDesc(ErrorCodeEnum.ERRCODE_ADDR_TOO_LONG.getDescrible());
                    failList.add(fail);
                    add = false;
                    continue;
                }

                if (item.getPhone().length() > 16) {
                    BeanUtils.copyProperties(item, fail);
                    fail.setFailDesc(ErrorCodeEnum.ERRCODE_PHONE_TOO_LONG.getDescrible());
                    failList.add(fail);
                    add = false;
                    continue;
                }

                if (item.getContact().length() > 100) {
                    BeanUtils.copyProperties(item, fail);
                    fail.setFailDesc(ErrorCodeEnum.ERRCODE_CONTACT_TOO_LONG.getDescrible());
                    failList.add(fail);
                    add = false;
                    continue;
                }

                if (item.getShopName().length() > 100) {
                    BeanUtils.copyProperties(item, fail);
                    fail.setFailDesc(ErrorCodeEnum.ERRCODE_SHOP_NAME_TOO_LONG.getDescrible());
                    failList.add(fail);
                    add = false;
                    continue;
                }

                List<JstShop> jstShopList = new ArrayList<>();
                jstShopList = jstShopMapper.listJstShopByShopName(item.getShopName(),null,myJstShopImportDTOList.get(0).getAgentMerchantCode());
                if (jstShopList != null && jstShopList.size() > 0) {
                    BeanUtils.copyProperties(item, fail);
                    fail.setFailDesc(ErrorCodeEnum.ERRCODE_NAME_REPEAT.getDescrible());
                    failList.add(fail);
                    add = false;
                    continue;
                }
                //查询商户名称是否存在
                List<BsDealerUserInfo> bsDealerUserInfoList = new ArrayList<>();
                BsDealerUserInfo bsDealerUserInfo=new BsDealerUserInfo();
                bsDealerUserInfo.setMerchantName(item.getAgentMerchantName());
                bsDealerUserInfoList=bsDealerUserInfoMapper.select(bsDealerUserInfo);
                if (bsDealerUserInfoList.size() == 0) {
                    BeanUtils.copyProperties(item, fail);
                    fail.setFailDesc(ErrorCodeEnum.ERRCODE_MERCHANT_NAME_REPEAT.getDescrible());
                    failList.add(fail);
                    add = false;
                    continue;
                }

                //查询电话是否存在
                /*jstShop = new JstShop();
                jstShop.setPhone(item.getPhone());
                jstShopList = jstShopMapper.select(jstShop);
                if (jstShopList != null && jstShopList.size() > 0) {
                    BeanUtils.copyProperties(item, fail);
                    fail.setFailDesc(ErrorCodeEnum.ERRCODE_NAME_PHONE.getDescrible());
                    failList.add(fail);
                    add = false;
                    continue;
                }*/

                if (add) {
                    successList.add(item);
                }
            }
            if (successList != null & successList.size() > 0) {
                successList.get(0).setAgentMerchantCode(myJstShopImportDTOList.get(0).getAgentMerchantCode());
                jxBsMerchantService.importMyJstShop(successList);
            }
            LOGGER.info("JxBsMerchantRemoteImpl :checkImportMyJstShop 检验结束" + new Date());
            LOGGER.info("JxBsMerchantRemoteImpl::checkImportMyJstShop: check ok !");
            checkImportDataDTO.setMyJstShopImportDTOList(successList);
            checkImportDataDTO.setMyJstShopExportDTOList(failList);
            return RpcResponse.success(checkImportDataDTO);
        } catch (RpcServiceException e) {
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e.getError(), e.getMessage());
        }
    }

    @Override
    public RpcResponse<Integer> importMyJstShop(List<MyJstShopImportDTO> jstShopImportDTOList) {
        RpcResponse<Integer> result;
        try {
            RpcAssert.assertNotNull(jstShopImportDTOList, DefaultErrorEnum.PARAMETER_NULL, "jstShopImportDTO must not be null");
            result = RpcResponse.success(jxBsMerchantService.importMyJstShop(jstShopImportDTOList));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<RpcPagination<JstShopDTO>> pageJstShop(RpcPagination<JstShopDTO> JstShopDTO) {
        RpcResponse<RpcPagination<JstShopDTO>> result;
        try {
            RpcAssert.assertNotNull(JstShopDTO, DefaultErrorEnum.PARAMETER_NULL, "pagination must not be null");
            RpcAssert.assertNotNull(JstShopDTO.getPageNum(), DefaultErrorEnum.PARAMETER_NULL, "pageNum must not be null");
            RpcAssert.assertNotNull(JstShopDTO.getPageSize(), DefaultErrorEnum.PARAMETER_NULL, "pageSize must not be null");
            result = RpcResponse.success(RpcPagination.copyPage(jxBsMerchantService.pageJstShop(JstShopDTO.getPageNum(),
                    JstShopDTO.getPageSize(),
                    JstShopDTO.getCondition())));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<CheckImportDataDTO> checkImportJstShop(List<JstShopImportDTO> jstShopImportDTOList) {
        CheckImportDataDTO checkImportDataDTO = new CheckImportDataDTO();
        try {
        	Map<String,Integer> mapShopName = new HashMap<>();
            if (StringUtil.isEmpty(jstShopImportDTOList) || jstShopImportDTOList.size() == 0) {
                return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
            }
            if (jstShopImportDTOList.size() > 5000) {
                return RpcResponse.error(ErrorCodeEnum.ERRCODE_MAX_IMPORT_SIZE);
            }
            //导入成功的数据返回
            List<JstShopImportDTO> successList = new ArrayList<>();
            //导入失败的 数据
            List<JstShopExportDTO> failList = new ArrayList<>();
            Integer mapValue = null;
            for(JstShopImportDTO dto:jstShopImportDTOList){
            	mapValue = mapShopName.get(dto.getShopName()+dto.getAgentMerchantName());
            	if(null == mapValue){
            		mapShopName.put(dto.getShopName()+dto.getAgentMerchantName(), 1);
            	}else{
            		mapShopName.put(dto.getShopName()+dto.getAgentMerchantName(), 2);
            	}
            }
            
            


            LOGGER.info("JxBsMerchantRemoteImpl :checkImportJstShop 检验开始" + new Date());
            for (JstShopImportDTO item : jstShopImportDTOList) {
                LOGGER.info("JxBsMerchantRemoteImpl::checkImportJstShop handle data item=" + item.toString());
                JstShopExportDTO fail = new JstShopExportDTO();
                boolean add = true;
                //验证所有字段是否为空
                mapValue = mapShopName.get(item.getShopName()+item.getAgentMerchantName());
                if(mapValue!=null&&mapValue == 2){
                	 BeanUtils.copyProperties(item, fail);
                     fail.setFailDesc(ErrorCodeEnum.ERRCODE_SHOP_NAME.getDescrible());
                     failList.add(fail);
                     add = false;
                     continue;
                }
                if (!StringUtil.isEmpty(item) && this.objCheckIsNull(item)) {
                    BeanUtils.copyProperties(item, fail);
                    fail.setFailDesc(ErrorCodeEnum.INVALID_DEVICE_DATA_FORMAT.getDescrible());
                    failList.add(fail);
                    add = false;
                    continue;
                }
                if (item.getAgentMerchantCode().length() > 32) {
                    BeanUtils.copyProperties(item, fail);
                    fail.setFailDesc(ErrorCodeEnum.ERRCODE_AGENT_MERCHANT_CODE_TOO_LONG.getDescrible());
                    failList.add(fail);
                    add = false;
                    continue;
                }
                if (item.getAgentMerchantName().length() > 200) {
                    BeanUtils.copyProperties(item, fail);
                    fail.setFailDesc(ErrorCodeEnum.ERRCODE_AGENT_MERCHANT_NAME_TOO_LONG.getDescrible());
                    failList.add(fail);
                    add = false;
                    continue;
                }
                if (item.getAddr().length() > 200) {
                    BeanUtils.copyProperties(item, fail);
                    fail.setFailDesc(ErrorCodeEnum.ERRCODE_ADDR_TOO_LONG.getDescrible());
                    failList.add(fail);
                    add = false;
                    continue;
                }
                if (item.getAddr().length() > 200) {
                    BeanUtils.copyProperties(item, fail);
                    fail.setFailDesc(ErrorCodeEnum.ERRCODE_ADDR_TOO_LONG.getDescrible());
                    failList.add(fail);
                    add = false;
                    continue;
                }

                if (item.getPhone().length() > 16) {
                    BeanUtils.copyProperties(item, fail);
                    fail.setFailDesc(ErrorCodeEnum.ERRCODE_PHONE_TOO_LONG.getDescrible());
                    failList.add(fail);
                    add = false;
                    continue;
                }

                if (item.getContact().length() > 100) {
                    BeanUtils.copyProperties(item, fail);
                    fail.setFailDesc(ErrorCodeEnum.ERRCODE_CONTACT_TOO_LONG.getDescrible());
                    failList.add(fail);
                    add = false;
                    continue;
                }

                if (item.getShopName().length() > 100) {
                    BeanUtils.copyProperties(item, fail);
                    fail.setFailDesc(ErrorCodeEnum.ERRCODE_SHOP_NAME_TOO_LONG.getDescrible());
                    failList.add(fail);
                    add = false;
                    continue;
                }

                //查询门店名称是否存在
              /*  JstShop jstShop = new JstShop();
                jstShop.setShopName(item.getShopName());*/
                List<JstShop> jstShopList = new ArrayList<>();
                jstShopList = jstShopMapper.listJstShopByShopName(item.getShopName(),item.getAgentMerchantName(),null);
                if (jstShopList != null && jstShopList.size() > 0) {
                    BeanUtils.copyProperties(item, fail);
                    fail.setFailDesc(ErrorCodeEnum.ERRCODE_NAME_REPEAT.getDescrible());
                    failList.add(fail);
                    add = false;
                    continue;
                }
                //查询商户名称是否存在
                List<BsDealerUserInfo> bsDealerUserInfoList = new ArrayList<>();
                BsDealerUserInfo bsDealerUserInfo=new BsDealerUserInfo();
                bsDealerUserInfo.setMerchantName(item.getAgentMerchantName());
                bsDealerUserInfo.setDeletedFlag("N");
                bsDealerUserInfoList=bsDealerUserInfoMapper.select(bsDealerUserInfo);
                if (bsDealerUserInfoList.size() == 0) {
                    BeanUtils.copyProperties(item, fail);
                    fail.setFailDesc(ErrorCodeEnum.ERRCODE_MERCHANT_NAME_REPEAT.getDescrible());
                    failList.add(fail);
                    add = false;
                    continue;
                }
                //查询电话是否存在
             /*   jstShop = new JstShop();
                jstShop.setPhone(item.getPhone());
                jstShopList = jstShopMapper.select(jstShop);
                if (jstShopList != null && jstShopList.size() > 0) {
                    BeanUtils.copyProperties(item, fail);
                    fail.setFailDesc(ErrorCodeEnum.ERRCODE_NAME_PHONE.getDescrible());
                    failList.add(fail);
                    add = false;
                    continue;
                }*/

                if (add) {
                    successList.add(item);
                }
            }
            if (successList != null & successList.size() > 0) {
                jxBsMerchantService.importJstShop(successList);
            }
            LOGGER.info("JxBsMerchantRemoteImpl :checkImportJstShop 检验结束" + new Date());
            LOGGER.info("JxBsMerchantRemoteImpl::checkImportJstShop: check ok !");
            checkImportDataDTO.setJstShopImportDTOList(successList);
            checkImportDataDTO.setJstShopExportDTOList(failList);
            return RpcResponse.success(checkImportDataDTO);
        } catch (RpcServiceException e) {
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e.getError(), e.getMessage());
        }
    }

    private Boolean objCheckIsNull(JstShopImportDTO jstShopImportDTO) {
        int i = 0;
        Boolean result = false;
        if (StringUtil.isEmpty(jstShopImportDTO.getAgentMerchantName())) {
            i++;
        }
        if (StringUtil.isEmpty(jstShopImportDTO.getPhone())) {
            i++;
        }
        if (StringUtil.isEmpty(jstShopImportDTO.getShopName())) {
            i++;
        }
        if (StringUtil.isEmpty(jstShopImportDTO.getAddr())) {
            i++;
        }
        if (StringUtil.isEmpty(jstShopImportDTO.getAgentMerchantCode())) {
            i++;
        }
        if (StringUtil.isEmpty(jstShopImportDTO.getContact())) {
            i++;
        }
        if (i > 0) {
            result = true;
        }
        return result;
    }

    private Boolean objCheckMyIsNull(MyJstShopImportDTO myJstShopImportDTO) {
        int i = 0;
        Boolean result = false;
        if (StringUtil.isEmpty(myJstShopImportDTO.getPhone())) {
            i++;
        }
        if (StringUtil.isEmpty(myJstShopImportDTO.getShopName())) {
            i++;
        }
        if (StringUtil.isEmpty(myJstShopImportDTO.getAddr())) {
            i++;
        }
        if (StringUtil.isEmpty(myJstShopImportDTO.getContact())) {
            i++;
        }
        if (i > 0) {
            result = true;
        }
        return result;
    }

    private Boolean objCheckMyIsNull(NoOrderDetailImportDTO noOrderDetailImportDTO) {
        int i = 0;
        Boolean result = false;
        if (StringUtil.isEmpty(noOrderDetailImportDTO.getSn())) {
            i++;
        }
        if (i > 0) {
            result = true;
        }
        return result;
    }


    @Override
    public RpcResponse<Integer> importJstShop(List<JstShopImportDTO> jstShopImportDTOList) {
        RpcResponse<Integer> result;
        try {
            RpcAssert.assertNotNull(jstShopImportDTOList, DefaultErrorEnum.PARAMETER_NULL, "jstShopImportDTO must not be null");
            result = RpcResponse.success(jxBsMerchantService.importJstShop(jstShopImportDTOList));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<Integer> checkJstShop(JstShopDTO jstShopDTO) {
        RpcResponse<Integer> result;
        try {
            RpcAssert.assertNotNull(jstShopDTO, DefaultErrorEnum.PARAMETER_NULL, "jstShopDTO must not be null");
            result = RpcResponse.success(jxBsMerchantService.checkJstShop(jstShopDTO));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<CheckImportDataDTO> checkImportNoOrderDetail(List<NoOrderDetailImportDTO> noOrderDetailImportDTOList) {
        CheckImportDataDTO checkImportDataDTO = new CheckImportDataDTO();
        try {
            if (StringUtil.isEmpty(noOrderDetailImportDTOList) || noOrderDetailImportDTOList.size() == 0) {
                return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
            }
            if (noOrderDetailImportDTOList.size() > 5000) {
                return RpcResponse.error(ErrorCodeEnum.ERRCODE_MAX_IMPORT_SIZE);
            }

            //导入成功的数据返回
            List<NoOrderDetailImportDTO> successList = new ArrayList<>();
            //导入失败的 数据
            List<NoOrderDetailExportDTO> failList = new ArrayList<>();   
            BsMerchantStockDevice deviceSn = null;
            LOGGER.info("JxBsMerchantRemoteImpl :checkImportNoOrderDetail 检验开始" + new Date());
            for (NoOrderDetailImportDTO item : noOrderDetailImportDTOList) {
                LOGGER.info("JxBsMerchantRemoteImpl::checkImportNoOrderDetail handle data item=" + item.toString());
                NoOrderDetailExportDTO fail = new NoOrderDetailExportDTO();
                //NoOrderDetailImportDTO item2= item.getValue();
                boolean add = true;
                //验证所有字段是否为空
                if (!StringUtil.isEmpty(item) && this.objCheckMyIsNull(item)) {
                    BeanUtils.copyProperties(item, fail);
                    fail.setFailDesc(ErrorCodeEnum.INVALID_DEVICE_DATA_FORMAT.getDescrible());
                    failList.add(fail);
                    add = false;
                    continue;
                }
                if (item.getSn().length() > 20) {
                    BeanUtils.copyProperties(item, fail);
                    fail.setFailDesc(ErrorCodeEnum.ERRCODE_SN_TOO_LONG.getDescrible());
                    failList.add(fail);
                    add = false;
                    continue;
                }

                if (add) {
                    successList.add(item);
                }
            }

            checkImportDataDTO.setNoOrderDetailImportDTOList(successList);
            checkImportDataDTO.setNoOrderDetailExportDTOList(failList);
            return RpcResponse.success(checkImportDataDTO);
        } catch (RpcServiceException e) {
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e.getError(), e.getMessage());
        }
    }
    
    private deviceMerchantReflect getDeviceMerchantReflect(String sendMerchantCode,Map<String,deviceMerchantReflect> mapMerchantReflect){
    	deviceMerchantReflect result = mapMerchantReflect.get(sendMerchantCode);
    	if(null != result){
    		return result;
    	}
    	result = merchantReflectService.getMerchantReflectBySendToMerchant(sendMerchantCode);
    	if(null != result){
    		mapMerchantReflect.put(result.getSendToMerchantCode(), result);
    	}
    	return result;
    }

    /**
     * 批量验证sn-无订单发货
     */
    @Override
    public RpcResponse<CheckImportNoOrderDetailDTO> batchCheckShopOrderDetailNoOrder(CheckImportNoOrderDetailDTO record) {
        RpcResponse<CheckImportNoOrderDetailDTO> result;
        LOGGER.info("WxBsMerchantRemoteImpl::checkShopOrderDetailNoOrder record::{}", record);
        try {
            RpcAssert.assertNotNull(record, JstErrorCodeEnum.ERRCODE_INVALID_PARAM, "record must not be null");
            RpcAssert.assertNotNull(record.getMerchantCode(), JstErrorCodeEnum.ERRCODE_INVALID_PARAM, "record.getAgentMerchantCode() must not be null");
            //RpcAssert.assertNotNull(record.getNoOrderDetailImportDTOList(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getListDetailDto must not be null");
            RpcAssert.assertIsTrue(verifyService.verifyRequest(record), JstErrorCodeEnum.ERRCODE_RPC_FORBIDDEN, "rpc frobidden");
            result = RpcResponse.success(orderService.batchCheckShopOrderDetailNoOrder(record));
        } catch (RpcServiceException e) {
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e.getError(), e.getMessage());
        }
        LOGGER.info("WxBsMerchantRemoteImpl::checkShopOrderDetail end result:{}", result);
        return result;
    }

}
