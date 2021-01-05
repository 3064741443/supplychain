package cn.com.glsx.supplychain.controller.gh;

import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.config.Constants;
import cn.com.glsx.supplychain.jst.dto.BsLogisticsDTO;
import cn.com.glsx.supplychain.jst.dto.gh.*;
import cn.com.glsx.supplychain.jst.enums.JstErrorCodeEnum;
import cn.com.glsx.supplychain.jst.remote.MotorcycleRemote;
import cn.com.glsx.supplychain.util.ExcelReadAndWriteUtil;
import cn.com.glsx.supplychain.util.SupplychainUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.oreframework.boot.security.UserInfoHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 广汇18家产品配置表
 *
 * @author
 */
@RestController
@RequestMapping("gh")
public class GhMerchantProductConfigController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserInfoHolder userInfoHolder;

    @Autowired
    private MotorcycleRemote motocycleRemote;

    /**
     * 产品配置获取父品牌列表
     *
     * @param
     * @return
     */
    @RequestMapping("listParentBrands")
    public ResultEntity<List<AttribInfoDTO>> listParentBrands(@RequestBody AttribInfoDTO attribInfoDto) {

        attribInfoDto.setType(Constants.GH_ATTRIB_INFO_TYPE_PARENT_BRAND);
        RpcResponse<List<AttribInfoDTO>> rsp = motocycleRemote.listParentBrands(attribInfoDto);
        if (rsp.getError() == null) {
            rsp.setMessage("查询成功");
        } else {
            logger.error(rsp.getMessage());
        }
        return ResultEntity.success(rsp.getResult());
    }


    /**
     * 产品配置获取子品牌列表
     *
     * @param
     * @return
     */
    @RequestMapping("listSubBrands")
    public ResultEntity<List<AttribInfoDTO>> listSubBrands(@RequestBody GhProductConfigDTO productConfigDto) {

        RpcResponse<List<AttribInfoDTO>> rsp = motocycleRemote.listSubBrands(productConfigDto);
        if (rsp.getError() == null) {
            rsp.setMessage("查询成功");
        } else {
            logger.error(rsp.getMessage());
        }
        return ResultEntity.success(rsp.getResult());
    }

    /**
     * 产品配置获取车系列表
     *
     * @param
     * @return
     */
    @RequestMapping("listAudis")
    public ResultEntity<List<AttribInfoDTO>> listAudis(@RequestBody GhProductConfigDTO productConfigDto) {
        RpcResponse<List<AttribInfoDTO>> rsp = motocycleRemote.listAudis(productConfigDto);
        if (rsp.getError() == null) {
            rsp.setMessage("查询成功");
        } else {
            logger.error(rsp.getMessage());
        }
        return ResultEntity.success(rsp.getResult());
    }

    /**
     * 产品配置获取车型列表
     *
     * @param
     * @return
     */
    @RequestMapping("listMotorcyle")
    public ResultEntity<List<AttribInfoDTO>> listMotorcyle(@RequestBody GhProductConfigDTO productConfigDto) {
        RpcResponse<List<AttribInfoDTO>> rsp = motocycleRemote.listMotorcyle(productConfigDto);
        if (rsp.getError() == null) {
            rsp.setMessage("查询成功");
        } else {
            logger.error(rsp.getMessage());
        }
        return ResultEntity.success(rsp.getResult());
    }

    /**
     * 产品配置获取产品类型列表
     *
     * @param
     * @return
     */
    @RequestMapping("listCategorys")
    public ResultEntity<List<AttribInfoDTO>> listCategorys(@RequestBody AttribInfoDTO attribInfoDto) {

        attribInfoDto.setType(Constants.GH_ATTRIB_INFO_TYPE_CATEGORY);
        RpcResponse<List<AttribInfoDTO>> rsp = motocycleRemote.listAttribInfo(attribInfoDto);
        if (rsp.getError() == null) {
            rsp.setMessage("查询成功");
        } else {
            logger.error(rsp.getMessage());
        }
        return ResultEntity.success(rsp.getResult());
    }

    /**
     * 产品配置获取产品类型列表
     *
     * @param
     * @return
     */
    @RequestMapping("listProductConfigs")
    public ResultEntity<RpcPagination<GhProductConfigDTO>> listProductConfigs(@RequestBody RpcPagination<GhProductConfigDTO> pagination) {
        if (null == pagination.getCondition()) {
            pagination.setCondition(new GhProductConfigDTO());
        }
        RpcResponse<RpcPagination<GhProductConfigDTO>> rsp = motocycleRemote.pageGhProductConfigMotorcycle(pagination);
        if (rsp.getError() == null) {
            rsp.setMessage("查询成功");
        } else {
            logger.error(rsp.getMessage());
        }
        return ResultEntity.success(rsp.getResult());
    }


    private GhProductConfigExportDTO exportProductConfigsConvertTo(GhProductConfigDTO ghProductConfigDTO) {
        GhProductConfigExportDTO ghProductConfigExportDTO = new GhProductConfigExportDTO();
        ghProductConfigExportDTO.setParentBrandName(StringUtils.isEmpty(ghProductConfigDTO.getParentBrandName()) ? "" : ghProductConfigDTO.getParentBrandName());
        ghProductConfigExportDTO.setSubBrandName(StringUtils.isEmpty(ghProductConfigDTO.getSubBrandName()) ? "" : ghProductConfigDTO.getSubBrandName());
        ghProductConfigExportDTO.setAudiName(StringUtils.isEmpty(ghProductConfigDTO.getAudiName()) ? "" : ghProductConfigDTO.getAudiName());
        ghProductConfigExportDTO.setMotorcycle(StringUtils.isEmpty(ghProductConfigDTO.getMotorcycle()) ? "" : ghProductConfigDTO.getMotorcycle());
        ghProductConfigExportDTO.setCategoryName(StringUtils.isEmpty(ghProductConfigDTO.getCategoryName()) ? "" : ghProductConfigDTO.getCategoryName());
        ghProductConfigExportDTO.setSpaProductCode(StringUtils.isEmpty(ghProductConfigDTO.getSpaProductCode()) ? "" : ghProductConfigDTO.getSpaProductCode());
        ghProductConfigExportDTO.setSpaProductName(StringUtils.isEmpty(ghProductConfigDTO.getSpaProductName()) ? "" : ghProductConfigDTO.getSpaProductName());
        ghProductConfigExportDTO.setGlsxProductCode(StringUtils.isEmpty(ghProductConfigDTO.getGlsxProductCode()) ? "" : ghProductConfigDTO.getGlsxProductCode());
        ghProductConfigExportDTO.setGlsxProductName(StringUtils.isEmpty(ghProductConfigDTO.getGlsxProductName()) ? "" : ghProductConfigDTO.getGlsxProductName());
        StringBuffer fixedOption = new StringBuffer();
        StringBuffer selectOption = new StringBuffer();
        if (ghProductConfigDTO.getListConfigOther() != null && ghProductConfigDTO.getListConfigOther().size() > 0) {
            for (GhProductConfigOtherDTO ghProductConfigDTO2 : ghProductConfigDTO.getListConfigOther()) {
                if (ghProductConfigDTO2.getOption() != null && ghProductConfigDTO2.getOption().equals("F")) {
                    fixedOption.append(ghProductConfigDTO2.getAttribTypeName() + ":" + ghProductConfigDTO2.getAttribInfoName() + "\n");
                } else if (ghProductConfigDTO2.getOption() != null && ghProductConfigDTO2.getOption().equals("O")) {
                    selectOption.append(ghProductConfigDTO2.getAttribTypeName() + ":" + ghProductConfigDTO2.getAttribInfoName() + "\n");
                }
            }
        }
        ghProductConfigExportDTO.setFasternConfig(fixedOption.toString());
        ghProductConfigExportDTO.setOptionConfig(selectOption.toString());
        return ghProductConfigExportDTO;
    }

    /**
     * 导出产品配置获取产品类型列表
     *
     * @param
     * @return
     */
    @RequestMapping("exportProductConfigs")
    public void exportProductConfigs(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String glsxProductName = request.getParameter("glsxProductName");
        String categoryId = request.getParameter("categoryId");
        String parentBrandId = request.getParameter("parentBrandId");
        String subBrandId = request.getParameter("subBrandId");
        String audiId=request.getParameter("audiId");
        String motorcycle=request.getParameter("motorcycle");
        GhProductConfigDTO ghProductConfigDTO = new GhProductConfigDTO();
        ghProductConfigDTO.setGlsxProductName(glsxProductName);
        if (StringUtils.isNotBlank(categoryId)) {
            ghProductConfigDTO.setCategoryId(Integer.valueOf(categoryId));
        }
        if (StringUtils.isNotBlank(parentBrandId)) {
            ghProductConfigDTO.setParentBrandId(Integer.valueOf(parentBrandId));
        }
        if (StringUtils.isNotBlank(subBrandId)) {
            ghProductConfigDTO.setSubBrandId(Integer.valueOf(subBrandId));
        }
        if(StringUtils.isNotBlank(audiId)){
            ghProductConfigDTO.setAudiId(Integer.valueOf(audiId));
        }
        ghProductConfigDTO.setMotorcycle(motorcycle);
        RpcResponse<List<GhProductConfigDTO>> rsp = motocycleRemote.exportGhProductConfigMotorcycle(ghProductConfigDTO);
        List<GhProductConfigDTO> ghProductConfigDTOS=rsp.getResult();
        List<GhProductConfigExportDTO> ghProductConfigExportDTOS=ghProductConfigDTOS.stream().map(ghProductConfigDTO1 -> exportProductConfigsConvertTo(ghProductConfigDTO1)).collect(Collectors.toList());
        ExcelReadAndWriteUtil.writeExcel(response,ghProductConfigExportDTOS,"productConfigList","productConfigList",GhProductConfigExportDTO.class);

        JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
        if (errCodeEnum == null) {
            rsp.setMessage("查询成功");
        } else {
            ResultEntity.error(errCodeEnum.getCode(), errCodeEnum.getDescrible());
            logger.error(rsp.getMessage());
        }

    }

    /**
     * 产品配置获取订单列表
     *
     * @param
     * @return
     */
    @RequestMapping("listMerchantOrders")
    public ResultEntity<RpcPagination<GhMerchantOrderDTO>> listMerchantOrders(@RequestBody RpcPagination<GhMerchantOrderDTO> pagination) {
        if (null == pagination.getCondition()) {
            pagination.setCondition(new GhMerchantOrderDTO());
        }
        RpcResponse<RpcPagination<GhMerchantOrderDTO>> rsp = motocycleRemote.pageGhMerchantOrder(pagination);

        if (rsp.getError() == null) {
            rsp.setMessage("查询成功");
        } else {
            logger.error(rsp.getMessage());
        }
        return ResultEntity.success(rsp.getResult());
    }

   /**
    * @author: luoqiang
    * @description: 查询广汇订单出货详情
    * @date: 2020/9/6 15:35
    * @param ghMerchantOrderCode
    * @return: cn.com.glsx.framework.core.beans.ResultEntity<java.util.List<cn.com.glsx.supplychain.jst.dto.BsLogisticsDTO>>
    */
    @ApiOperation(value = "查询广汇订单出货详情",notes = "查询广汇订单出货详情")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "query", dataType = "String", name = "ghMerchantOrderCode", value = "广汇订单ID", required = true) })
    @GetMapping("shippingDetails")
    public ResultEntity<List<BsLogisticsDTO>> listMerchantOrders(@RequestParam(value = "ghMerchantOrderCode")String ghMerchantOrderCode) {
        BsLogisticsDTO bsLogisticsDTO=new BsLogisticsDTO();
        bsLogisticsDTO.setGhMerchantOrderCode(ghMerchantOrderCode);
        RpcResponse<List<BsLogisticsDTO>> rsp = motocycleRemote.wxGetLogisticsInfoListByServiceCode(bsLogisticsDTO);
        if (rsp.getError() == null) {
            rsp.setMessage("查询成功");
        } else {
            logger.error(rsp.getMessage());
        }
        return ResultEntity.success(rsp.getResult());
    }

    /**
     * description:订单导出对象转换
     * version: 1.0
     * date 2020/7/22 17:06
     * author luoqiang
     *
     * @param ghMerchantOrderDTO
     * @return cn.com.glsx.supplychain.jst.dto.gh.GhMerchantOrderExportDTO
     */
    private GhMerchantOrderExportDTO ghMerchantOrdersConvertTo(NewGhMerchantOrderDTO ghMerchantOrderDTO) {
        GhMerchantOrderExportDTO vo = new GhMerchantOrderExportDTO();
        vo.setGhMerchantOrderCode(StringUtils.isEmpty(ghMerchantOrderDTO.getGhMerchantOrderCode()) ? "" : ghMerchantOrderDTO.getGhMerchantOrderCode());
        vo.setMerchantName(StringUtils.isEmpty(ghMerchantOrderDTO.getMerchantName()) ? "" : ghMerchantOrderDTO.getMerchantName());
        vo.setSpaProductCode(StringUtils.isEmpty(ghMerchantOrderDTO.getSpaProductCode()) ? "" : ghMerchantOrderDTO.getSpaProductCode());
        vo.setSpaProductName(StringUtils.isEmpty(ghMerchantOrderDTO.getSpaProductName()) ? "" : ghMerchantOrderDTO.getSpaProductName());
        vo.setCategoryName(StringUtils.isEmpty(ghMerchantOrderDTO.getCategoryName()) ? "" : ghMerchantOrderDTO.getCategoryName());
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("品牌：" + ghMerchantOrderDTO.getParentBrandName() + "\n");
        stringBuffer.append("子品牌：" + ghMerchantOrderDTO.getSubBrandName() + "\n");
        stringBuffer.append("车系：" + ghMerchantOrderDTO.getAudiName() + "\n");
        stringBuffer.append("车型：" + ghMerchantOrderDTO.getMotorcycle() + "\n");
        vo.setVechoAttrib(stringBuffer.toString());
//		Map<String,Object> map=new HashMap<>();
//		map.toString();
        StringBuffer fixedOption = new StringBuffer();
        StringBuffer selectOption = new StringBuffer();
        if (ghMerchantOrderDTO.getListMerchantOrderConfig() != null && ghMerchantOrderDTO.getListMerchantOrderConfig().size() > 0) {
            for (GhMerchantOrderConfigDTO ghMerchantOrderConfigDTO : ghMerchantOrderDTO.getListMerchantOrderConfig()) {
                if (ghMerchantOrderConfigDTO.getOption() != null && ghMerchantOrderConfigDTO.getOption().equals("F")) {
                    fixedOption.append(ghMerchantOrderConfigDTO.getAttribTypeName() + ":" + ghMerchantOrderConfigDTO.getAttribInfoName() + "\n");
                } else if (ghMerchantOrderConfigDTO.getOption() != null && ghMerchantOrderConfigDTO.getOption().equals("O")) {
                    selectOption.append(ghMerchantOrderConfigDTO.getAttribTypeName() + ":" + ghMerchantOrderConfigDTO.getAttribInfoName() + "\n");
                }
            }
        }
        vo.setFasternConfig(fixedOption.toString());
        vo.setOptionConfig(selectOption.toString());
        vo.setRemark(StringUtils.isEmpty(ghMerchantOrderDTO.getRemark()) ? "" : ghMerchantOrderDTO.getRemark());
        if (StringUtils.isNotBlank(ghMerchantOrderDTO.getStatus()) && "1".equals(ghMerchantOrderDTO.getStatus())) {
            vo.setStatus("待审核");
        } else if (StringUtils.isNotBlank(ghMerchantOrderDTO.getStatus()) && "2".equals(ghMerchantOrderDTO.getStatus())||"8".equals(ghMerchantOrderDTO.getStatus())){
            vo.setStatus("待发货");
        } else if (StringUtils.isNotBlank(ghMerchantOrderDTO.getStatus()) && "3".equals(ghMerchantOrderDTO.getStatus())||"4".equals(ghMerchantOrderDTO.getStatus())){
            vo.setStatus("部分发货");
        }else if (StringUtils.isNotBlank(ghMerchantOrderDTO.getStatus()) && "5".equals(ghMerchantOrderDTO.getStatus())){
            vo.setStatus("完成发货");
        }else if (StringUtils.isNotBlank(ghMerchantOrderDTO.getStatus()) && "7".equals(ghMerchantOrderDTO.getStatus())) {
            vo.setStatus("提前结束");
        }else if (StringUtils.isNotBlank(ghMerchantOrderDTO.getStatus()) && "0".equals(ghMerchantOrderDTO.getStatus())) {
            vo.setStatus("已驳回");
        }else if (StringUtils.isNotBlank(ghMerchantOrderDTO.getStatus()) && "9".equals(ghMerchantOrderDTO.getStatus())) {
            vo.setStatus("已取消");
        }
        vo.setTotal(ghMerchantOrderDTO.getTotal() == null ? 0 : ghMerchantOrderDTO.getTotal());
        vo.setAlreadySend(ghMerchantOrderDTO.getShipmentsQuantity());
        vo.setOwerSend(ghMerchantOrderDTO.getTotal() - vo.getAlreadySend());
        vo.setOrderTime(SupplychainUtils.getStrYMDHMSFromDate(ghMerchantOrderDTO.getOrderTime()));
        vo.setSpaPurchaseCode(StringUtils.isEmpty(ghMerchantOrderDTO.getSpaPurchaseCode()) ? "" : ghMerchantOrderDTO.getSpaPurchaseCode());
        if(ghMerchantOrderDTO.getBsAddress()!=null) {
            vo.setName(StringUtils.isEmpty(ghMerchantOrderDTO.getBsAddress().getName()) ? "" : ghMerchantOrderDTO.getBsAddress().getName());
            vo.setMobile(StringUtils.isEmpty(ghMerchantOrderDTO.getBsAddress().getMobile())?"":ghMerchantOrderDTO.getBsAddress().getMobile());
            vo.setAddress(StringUtils.isEmpty(ghMerchantOrderDTO.getBsAddress().getAddress())?"":ghMerchantOrderDTO.getBsAddress().getAddress());
        }
        vo.setConversionStatus(ghMerchantOrderDTO.getConversionStatus());
        vo.setReminderTotal(ghMerchantOrderDTO.getReminderTotal());
        return vo;
    }


    /**
     * 产品配置获取订单列表导出
     *
     * @param
     * @return
     */
    @RequestMapping("exportGhMerchantOrders")
    public void exportGhMerchantOrders(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String strMerchantName = request.getParameter("merchantName");
        String strOrderCode = request.getParameter("ghMerchantOrderCode");
        String strProductName = request.getParameter("spaProductName");
        String strCategoryId = request.getParameter("categoryId");
        String strStartDate = request.getParameter("startDate");
        String strEndDate = request.getParameter("endDate");
        Integer categoryId = null;
        GhMerchantOrderDTO dtoCondition = new GhMerchantOrderDTO();
        if (!StringUtils.isEmpty(strCategoryId)) {
            categoryId = Integer.valueOf(strCategoryId);
        }
        dtoCondition.setMerchantName(strMerchantName);
        dtoCondition.setGhMerchantOrderCode(strOrderCode);
        dtoCondition.setSpaProductName(strProductName);
        dtoCondition.setCategoryId(categoryId);
        dtoCondition.setStartDate(strStartDate);
        dtoCondition.setEndDate(strEndDate);
        RpcResponse<List<GhMerchantOrderDTO>> rsp = motocycleRemote.exportGhMerchantOrder(dtoCondition);
        List<GhMerchantOrderDTO> ghMerchantOrderDTOS = rsp.getResult();
        List<GhMerchantOrderExportDTO> ghMerchantOrderExportDTOS = ghMerchantOrderDTOS.stream().map(ghMerchantOrderDTO -> ghMerchantOrdersConvertTo((NewGhMerchantOrderDTO) ghMerchantOrderDTO)).collect(Collectors.toList());
        ExcelReadAndWriteUtil.writeExcel(response, ghMerchantOrderExportDTOS, "order", "order", GhMerchantOrderExportDTO.class);
        JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
        if (errCodeEnum == null) {
            rsp.setMessage("查询成功");
        } else {
            ResultEntity.error(errCodeEnum.getCode(), errCodeEnum.getDescrible());
            logger.error(rsp.getMessage());
        }


    }


}
