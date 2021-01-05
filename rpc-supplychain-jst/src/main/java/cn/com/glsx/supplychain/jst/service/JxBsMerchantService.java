package cn.com.glsx.supplychain.jst.service;

import cn.com.glsx.supplychain.jst.common.Constants;
import cn.com.glsx.supplychain.jst.dto.*;
import cn.com.glsx.supplychain.jst.enums.JstShopStatusEnum;
import cn.com.glsx.supplychain.jst.enums.LogisticsEnum;
import cn.com.glsx.supplychain.jst.enums.MaterialTypeEnum;
import cn.com.glsx.supplychain.jst.mapper.*;
import cn.com.glsx.supplychain.jst.model.*;
import cn.com.glsx.supplychain.jst.util.JstUtils;
import cn.com.glsx.supplychain.jst.util.StringUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.oreframework.boot.autoconfigure.snowflake.algorithm.SnowflakeWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName JxBsMerchantService
 * @Description
 * @Author xiex
 * @Date 2020/2/13 21:59
 * @Version 1.0
 */
@Service
public class JxBsMerchantService {

    private static final Logger logger = LoggerFactory.getLogger(JxBsMerchantService.class);

    @Autowired
    private BsMerchantStockDeviceMapper bsMerchantStockDeviceMapper;

    @Autowired
    private JstShopOrderMapper jstShopOrderMapper;

    @Autowired
    private JstShopOrderDetailMapper jstShopOrderDetailMapper;

    @Autowired
    private BsLogisticsMapper bsLogisticsMapper;

    @Autowired
    private BsAddressMapper bsAddressMapper;

    @Autowired
    private JstShopMapper jstShopMapper;

    @Autowired
    private JstShopAgentRelationMapper jstShopAgentRelationMapper;

    @Autowired
    private SnowflakeWorker snowflakeWorker;

    @Autowired
    private BsLogisticsService logisticsService;

    @Autowired
    private BsDealerUserInfoMapper bsDealerUserInfoMapper;

    /**
     * 获取经销存系统设备发货分页列表
     *
     * @param jstShopOrderDTO
     * @return
     */
    public Page<JstShopOrderDTO> pageJstShopOrder(int pageNum, int pageSize, JstShopOrderDTO jstShopOrderDTO) {
        logger.info("pageNum为：{}, pageSize为：{}, 条件查询参数为 ：{}。", pageNum, pageSize, jstShopOrderDTO);
        Page<JstShopOrderDTO> result = new Page<>();
        JstShopOrderDTO dto;
        PageHelper.startPage(pageNum, pageSize);
        JstShopOrder jstShopOrder = new JstShopOrder();
        if (!StringUtil.isEmpty(jstShopOrderDTO)) {
            BeanUtils.copyProperties(jstShopOrderDTO, jstShopOrder);
        }
        Page<JstShopOrder> jstShopOrderPage = jstShopOrderMapper.pageJstShopOrder(jstShopOrder);
        BeanUtils.copyProperties(jstShopOrderPage, result);
        JstShopOrderDetail jstShopOrderDetail;
        List<BsLogistics> logisticsList;
        int sum = 0;
        if (jstShopOrderPage != null && jstShopOrderPage.size() > 0) {
            for (JstShopOrder model : jstShopOrderPage) {
            	 dto = new JstShopOrderDTO();
                 BeanUtils.copyProperties(model, dto);
                 sum = 0;
            	if(model.getScanType().equals(Constants.NO_SCAN_CODE)){
            		logisticsList = logisticsService.getLogisticsListByShopOrderCode(dto.getShopOrderCode());
            		for (BsLogistics bsLogistics : logisticsList) {
                        if (bsLogistics.getShipmentsQuantity() != null && bsLogistics.getShipmentsQuantity() > 0) {
                            sum = sum + bsLogistics.getShipmentsQuantity();
                        }
                    }
            		dto.setSendCount(sum);
                    dto.setWaitCount(dto.getTotal() - sum);
            	}else{
            		jstShopOrderDetail = new JstShopOrderDetail();
                    jstShopOrderDetail.setShopOrderCode(dto.getShopOrderCode());
                    sum = jstShopOrderDetailMapper.selectCount(jstShopOrderDetail);
                    dto.setSendCount(sum);
                    dto.setWaitCount(dto.getTotal() - sum);
            	}
                result.add(dto);
            }
        }
        return result;
    }

    public Integer dispatchJstShopOrderDetail(JstShopOrderDetailDTO jstShopOrderDetailDTO) {
        Date date = new Date();
        Integer result;
        BsLogistics bsLogistics = new BsLogistics();
        bsLogistics.setType((byte) 6);
        bsLogistics.setServiceCode(jstShopOrderDetailDTO.getShopOrderCode());
        bsLogisticsMapper.select(bsLogistics);
        bsLogistics.setId(null);
        bsLogistics.setType((byte) 7);
        bsLogistics.setCode(StringUtil.getOrderNo());
        bsLogistics.setCompany(jstShopOrderDetailDTO.getLogisticsCompany());
        bsLogistics.setOrderNumber(jstShopOrderDetailDTO.getLogisticsOrderNumber());
        bsLogistics.setCreatedDate(date);
        bsLogistics.setUpdatedDate(date);
        bsLogisticsMapper.insertSelective(bsLogistics);
        List<JstShopOrderDetail> jstShopOrderDetailList = new ArrayList<>();
        JstShopOrderDetail jstShopOrderDetail;
        for (String sn : jstShopOrderDetailDTO.getSnList()) {
            jstShopOrderDetail = new JstShopOrderDetail();
            jstShopOrderDetail.setLogisticsId(bsLogistics.getId().intValue());
            jstShopOrderDetail.setSn(sn);
            jstShopOrderDetail.setShopOrderCode(jstShopOrderDetailDTO.getShopOrderCode());
            jstShopOrderDetail.setAttribCode(jstShopOrderDetailDTO.getAttribCode());
            jstShopOrderDetail.setCreatedBy(bsLogistics.getCreatedBy());
            jstShopOrderDetail.setCreatedDate(date);
            jstShopOrderDetail.setUpdatedBy(bsLogistics.getUpdatedBy());
            jstShopOrderDetail.setUpdatedDate(date);
            jstShopOrderDetailList.add(jstShopOrderDetail);
        }
        result = jstShopOrderDetailMapper.insertList(jstShopOrderDetailList);
        return result;
    }

    public List<JstShopOrderDetailDTO> getJstShopOrderDetailList(JstShopOrderDetailDTO jstShopOrderDetailDTO) {
        List<JstShopOrderDetailDTO> result = new ArrayList<>();
        JstShopOrderDetailDTO dto;
        JstShopOrderDetail jstShopOrderDetail = new JstShopOrderDetail();
        if (jstShopOrderDetailDTO != null) {
            BeanUtils.copyProperties(jstShopOrderDetailDTO, jstShopOrderDetail);
        }
        
        JstShopOrder shopOrder = new JstShopOrder();
        shopOrder.setShopOrderCode(jstShopOrderDetailDTO.getShopOrderCode());
        shopOrder = jstShopOrderMapper.selectOne(shopOrder);
        if(shopOrder.getScanType().equals(Constants.NO_SCAN_CODE)){
        	Example example = new Example(BsLogistics.class);
            Example.Criteria criteria=example.createCriteria();
            criteria.andEqualTo("serviceCode", jstShopOrderDetail.getShopOrderCode());
            criteria.andEqualTo("type", LogisticsEnum.LOGISTICS_TYPE_7.getCode());
            List<BsLogistics> bsLogistics = bsLogisticsMapper.selectByExample(example);
            if(null != bsLogistics){
            	for(BsLogistics logistics:bsLogistics){
            		if(logistics.getCompany().equals("线下配送")){
            			logistics.setCompany("");
            			logistics.setOrderNumber("");
            		}
            		dto = new JstShopOrderDetailDTO();
            		dto.setUpdatedDate(logistics.getUpdatedDate());
            		dto.setLogisticsOrderNumber(logistics.getOrderNumber());
            		dto.setLogisticsCompany(logistics.getCompany());
            		result.add(dto);
            	}
            }
        }else{
        	List<JstShopOrderDetail> list = jstShopOrderDetailMapper.getListByshopOrderCode(jstShopOrderDetail);
            Example example = new Example(BsLogistics.class);
            Example.Criteria criteria=example.createCriteria();
            criteria.andEqualTo("serviceCode", jstShopOrderDetail.getShopOrderCode());
            criteria.andEqualTo("type", LogisticsEnum.LOGISTICS_TYPE_7.getCode());
            List<BsLogistics> bsLogistics = bsLogisticsMapper.selectByExample(example);
           /* Example bsAddressExample = new Example(BsAddress.class);
            bsAddressExample.createCriteria().andEqualTo("id",bsLogistics.get(0).getReceiveId());*/
            BsAddress address=null;
            if(bsLogistics!=null&&!bsLogistics.isEmpty()) {
              address = bsAddressMapper.selectByPrimaryKey(bsLogistics.get(0).getReceiveId());
            }
            BsAddressDTO bsAddressDTO=new BsAddressDTO();
            if(address!=null) {
                BeanUtils.copyProperties(address, bsAddressDTO);
            }
            if (list != null && list.size() > 0) {
                for (JstShopOrderDetail model : list) {
                    dto = new JstShopOrderDetailDTO();
                    BeanUtils.copyProperties(model, dto);
                    dto.setBsAddressDTO(bsAddressDTO);
                    if(null != dto.getLogisticsCompany()){
                    	if(dto.getLogisticsCompany().equals("线下配送")){
                    		dto.setLogisticsCompany("");
                    		dto.setLogisticsOrderNumber("");
                    	}
                    }
                    result.add(dto);
                }
            }
        }
        return result;
    }

    /**
     * 获取经销存系统设备明细分页列表
     *
     * @param bsMerchantStockDeviceDTO
     * @return
     */
    public Page<BsMerchantStockDeviceDTO> pageBsMerchantStockDevice(int pageNum, int pageSize, BsMerchantStockDeviceDTO bsMerchantStockDeviceDTO) {
        logger.info("pageNum为：{}, pageSize为：{}, 条件查询参数为 ：{}。", pageNum, pageSize, bsMerchantStockDeviceDTO);
        Page<BsMerchantStockDeviceDTO> result = new Page<>();
        BsMerchantStockDeviceDTO dto;
        PageHelper.startPage(pageNum, pageSize);
        BsMerchantStockDevice bsMerchantStockDevice = new BsMerchantStockDevice();
        if (!StringUtil.isEmpty(bsMerchantStockDeviceDTO)) {
            BeanUtils.copyProperties(bsMerchantStockDeviceDTO, bsMerchantStockDevice);
        }
        Page<BsMerchantStockDevice> bsMerchantStockDevicePage = bsMerchantStockDeviceMapper.pageBsMerchantStockDevice(bsMerchantStockDevice);
        BeanUtils.copyProperties(bsMerchantStockDevicePage, result);
        if (bsMerchantStockDevicePage != null && bsMerchantStockDevicePage.size() > 0) {
            for (BsMerchantStockDevice model : bsMerchantStockDevicePage) {
                dto = new BsMerchantStockDeviceDTO();
                BeanUtils.copyProperties(model, dto);
                result.add(dto);
            }
        }
        return result;
    }

    /**
     * 经销存系统设备明细总数查询
     */
    public BsMerchantStockDeviceDTO getSumBsMerchantStockDeviceList(BsMerchantStockDeviceDTO bsMerchantStockDeviceDTO) {
        logger.info("经销存系统设备明细总数查询查询参数商户号为 ：{}。", bsMerchantStockDeviceDTO.getMerchantCode());
        BsMerchantStockDeviceDTO result = new BsMerchantStockDeviceDTO();
        Integer inSum = 0;
        Integer odSum = 0;
        Integer osSum = 0;
        BsMerchantStockDevice bsMerchantStockDevice = new BsMerchantStockDevice();
        bsMerchantStockDevice.setMerchantCode(bsMerchantStockDeviceDTO.getMerchantCode());
        bsMerchantStockDevice.setStatu("IN");
        inSum = bsMerchantStockDeviceMapper.getSum(bsMerchantStockDevice);
        if (StringUtil.isEmpty(inSum)) {
            inSum = 0;
        }
        result.setInSum(inSum);
        bsMerchantStockDevice.setStatu("OD");
        odSum = bsMerchantStockDeviceMapper.getSum(bsMerchantStockDevice);
        if (StringUtil.isEmpty(odSum)) {
            odSum = 0;
        }
        result.setOdSum(odSum);
        bsMerchantStockDevice.setStatu("OS");
        osSum = bsMerchantStockDeviceMapper.getSum(bsMerchantStockDevice);
        if (StringUtil.isEmpty(osSum)) {
            osSum = 0;
        }
        result.setOsSum(osSum);
        result.setSum(inSum + odSum + osSum);
        return result;
    }

    /**
     * 获取经销存系统我的门店分页列表
     *
     * @param jstShopDTO
     * @return
     */
    public Page<JstShopDTO> pageMyJstShop(int pageNum, int pageSize, JstShopDTO jstShopDTO) {
        logger.info("pageNum为：{}, pageSize为：{}, 条件查询参数为 ：{}。", pageNum, pageSize, jstShopDTO);
        Page<JstShopDTO> result = new Page<>();
        JstShopDTO dto;
        PageHelper.startPage(pageNum, pageSize);
        JstShop jstShop = new JstShop();
        if (!StringUtil.isEmpty(jstShopDTO)) {
            BeanUtils.copyProperties(jstShopDTO, jstShop);
        }
        Page<JstShop> jstShopPage = jstShopMapper.pageMyJstShop(jstShop);
        BeanUtils.copyProperties(jstShopPage, result);
        if (jstShopPage != null && jstShopPage.size() > 0) {
            for (JstShop model : jstShopPage) {
                dto = new JstShopDTO();
                BeanUtils.copyProperties(model, dto);
                result.add(dto);
            }
        }
        return result;
    }

    /**
     * 经销存系统我的门店导入
     */
    public Integer importMyJstShop(List<MyJstShopImportDTO> myJstShopImportDTOList) {
        BsDealerUserInfo bsDealerUserInfo = new BsDealerUserInfo();
        bsDealerUserInfo.setMerchantCode(myJstShopImportDTOList.get(0).getAgentMerchantCode());
        bsDealerUserInfo.setDeletedFlag("N");
        bsDealerUserInfo = bsDealerUserInfoMapper.selectOne(bsDealerUserInfo);
        Date date = new Date();
        Integer result = 0;
        List<JstShop> jstShopList = new ArrayList<>();
        List<JstShop> jstShops = new ArrayList<>();
        List<JstShopAgentRelation> jstShopAgentRelationList = new ArrayList<>();
        JstShopAgentRelation jstShopAgentRelation;
        JstShop jstShop;
        for (MyJstShopImportDTO myJstShopImportDTO : myJstShopImportDTOList) {
            jstShop = new JstShop();
            jstShop.setShopName(myJstShopImportDTO.getShopName());
            jstShops = jstShopMapper.select(jstShop);
            if (jstShops == null || jstShops.size() == 0) {
                jstShop.setShopCode(Constants.JST_SHOP + JstUtils.getDispatchOrderNumber(snowflakeWorker));
                jstShop.setContact(myJstShopImportDTO.getContact());
                jstShop.setAddr(myJstShopImportDTO.getAddr());
                jstShop.setPhone(myJstShopImportDTO.getPhone());
                jstShop.setStatus(JstShopStatusEnum.WC.getCode());
                jstShop.setCreatedBy(bsDealerUserInfo.getMerchantName());
                jstShop.setCreatedDate(date);
                jstShop.setUpdatedBy(bsDealerUserInfo.getMerchantName());
                jstShop.setUpdatedDate(date);
                jstShop.setDeletedFlag("N");
                jstShopList.add(jstShop);
            }
            jstShopAgentRelation = new JstShopAgentRelation();
            if (jstShops != null && jstShops.size() > 0) {
                jstShopAgentRelation.setShopCode(jstShops.get(0).getShopCode());
            } else {
                jstShopAgentRelation.setShopCode(Constants.JST_SHOP + JstUtils.getDispatchOrderNumber(snowflakeWorker));
            }
            jstShopAgentRelation.setAgentMerchantCode(myJstShopImportDTOList.get(0).getAgentMerchantCode());
            jstShopAgentRelation.setAgentMerchantName(bsDealerUserInfo.getMerchantName());
            jstShopAgentRelation.setStatus(JstShopStatusEnum.WC.getCode());
            jstShopAgentRelation.setCreatedBy(bsDealerUserInfo.getMerchantName());
            jstShopAgentRelation.setCreatedDate(date);
            jstShopAgentRelation.setUpdatedBy(bsDealerUserInfo.getMerchantName());
            jstShopAgentRelation.setUpdatedDate(date);
            jstShopAgentRelation.setDeletedFlag("N");
            jstShopAgentRelationList.add(jstShopAgentRelation);
        }
        if (jstShopList != null && jstShopList.size() > 0) {
            jstShopMapper.insertList(jstShopList);
        }
        if (jstShopAgentRelationList != null && jstShopAgentRelationList.size() > 0) {
            jstShopAgentRelationMapper.insertList(jstShopAgentRelationList);
        }
        return result;
    }

    /**
     * 供货关系分页列表
     *
     * @param jstShopDTO
     * @return
     */
    public Page<JstShopDTO> pageJstShop(int pageNum, int pageSize, JstShopDTO jstShopDTO) {
        logger.info("pageNum为：{}, pageSize为：{}, 条件查询参数为 ：{}。", pageNum, pageSize, jstShopDTO);
        Page<JstShopDTO> result = new Page<>();
        JstShopDTO dto;
        PageHelper.startPage(pageNum, pageSize);
        JstShop jstShop = new JstShop();
        if (!StringUtil.isEmpty(jstShopDTO)) {
            BeanUtils.copyProperties(jstShopDTO, jstShop);
        }
        Page<JstShop> jstShopPage = jstShopMapper.pageJstShop(jstShop);
        BeanUtils.copyProperties(jstShopPage, result);
        if (jstShopPage != null && jstShopPage.size() > 0) {
            for (JstShop model : jstShopPage) {
                dto = new JstShopDTO();
                BeanUtils.copyProperties(model, dto);
                result.add(dto);
            }
        }
        return result;
    }

    /**
     * 供货关系导入
     */
    public Integer importJstShop(List<JstShopImportDTO> jstShopImportDTOList) {
        Date date = new Date();
        Integer result = 0;
        List<JstShop> jstShopList = new ArrayList<>();
        List<JstShop> jstShops = new ArrayList<>();
        List<JstShopAgentRelation> jstShopAgentRelationList = new ArrayList<>();
        JstShopAgentRelation jstShopAgentRelation;
        JstShop jstShop;
        for (JstShopImportDTO jstShopImportDTO : jstShopImportDTOList) {
            jstShop = new JstShop();
            jstShop.setShopName(jstShopImportDTO.getShopName());
            jstShops = jstShopMapper.select(jstShop);
            if (jstShops == null || jstShops.size() == 0) {
                jstShop.setContact(jstShopImportDTO.getContact());
                jstShop.setShopCode(Constants.JST_SHOP + JstUtils.getDispatchOrderNumber(snowflakeWorker));
                jstShop.setAddr(jstShopImportDTO.getAddr());
                jstShop.setPhone(jstShopImportDTO.getPhone());
                jstShop.setStatus(JstShopStatusEnum.WC.getCode());
                jstShop.setCreatedBy("admin");
                jstShop.setCreatedDate(date);
                jstShop.setUpdatedBy("admin");
                jstShop.setUpdatedDate(date);
                jstShop.setDeletedFlag("N");
                jstShopList.add(jstShop);
            }

            jstShopAgentRelation = new JstShopAgentRelation();
            if (jstShops != null && jstShops.size() > 0) {
                jstShopAgentRelation.setShopCode(jstShops.get(0).getShopCode());
            } else {
                jstShopAgentRelation.setShopCode(Constants.JST_SHOP + JstUtils.getDispatchOrderNumber(snowflakeWorker));
            }
            jstShopAgentRelation.setAgentMerchantCode(jstShopImportDTO.getAgentMerchantCode());
            jstShopAgentRelation.setAgentMerchantName(jstShopImportDTO.getAgentMerchantName());
            jstShopAgentRelation.setStatus(JstShopStatusEnum.WC.getCode());
            jstShopAgentRelation.setCreatedBy("admin");
            jstShopAgentRelation.setCreatedDate(date);
            jstShopAgentRelation.setUpdatedBy("admin");
            jstShopAgentRelation.setUpdatedDate(date);
            jstShopAgentRelation.setDeletedFlag("N");
            jstShopAgentRelationList.add(jstShopAgentRelation);
        }
        if (jstShopList != null && jstShopList.size() > 0) {
            jstShopMapper.insertList(jstShopList);
        }
        if (jstShopAgentRelationList != null && jstShopAgentRelationList.size() > 0) {
            jstShopAgentRelationMapper.insertList(jstShopAgentRelationList);
        }
        return result;
    }

    /**
     * 审核门店
     *
     * @param jstShopDTO
     * @return
     */
    public Integer checkJstShop(JstShopDTO jstShopDTO) {
        logger.info("审核门店的参数：{}。", jstShopDTO);
        Date date = new Date();
        JstShopAgentRelation jstShopAgentRelation = new JstShopAgentRelation();
        JstShop jstShop = new JstShop();

        jstShopAgentRelation.setShopCode(jstShopDTO.getShopCode());
        jstShopAgentRelation.setAgentMerchantCode(jstShopDTO.getAgentMerchantCode());
        jstShopAgentRelation.setStatus(jstShopDTO.getStatus());
        if (jstShopDTO != null && jstShopDTO.getStatus() != null && jstShopDTO.getStatus().equals(JstShopStatusEnum.FI.getCode())) {
            jstShopAgentRelation.setCheckFailResult(jstShopDTO.getCheckFailResult());
            jstShop.setCheckFailResult(jstShopDTO.getCheckFailResult());
        } else {
            jstShop.setShopMerchantCode(jstShopDTO.getShopMerchantCode());
            jstShop.setShopMerchantName(jstShopDTO.getShopMerchantName());
        }
        jstShop.setShopCode(jstShopDTO.getShopCode());
        jstShop.setStatus(jstShopDTO.getStatus());

        jstShopAgentRelation.setUpdatedDate(date);
        jstShop.setUpdatedDate(date);
        jstShopAgentRelationMapper.updateByShopCodeAndAgentMerchantCode(jstShopAgentRelation);
        return jstShopMapper.updateByShopCode(jstShop);
    }
}
