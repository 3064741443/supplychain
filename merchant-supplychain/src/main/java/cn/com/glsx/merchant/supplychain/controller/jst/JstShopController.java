package cn.com.glsx.merchant.supplychain.controller.jst;

import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.merchant.facade.enums.SourceEnum;
import cn.com.glsx.merchant.facade.model.entity.MerchantDetailFacade;
import cn.com.glsx.merchant.facade.model.entity.MerchantFacade;
import cn.com.glsx.merchant.facade.model.response.MerchantFacadeResponse;
import cn.com.glsx.merchant.facade.model.response.ResultResponse;
import cn.com.glsx.merchant.facade.remote.MerchantFacadeRemote;
import cn.com.glsx.merchant.supplychain.config.SupplyadminProperty;
import cn.com.glsx.merchant.supplychain.util.ExcelReads;
import cn.com.glsx.merchant.supplychain.util.ExcelUtils;
import cn.com.glsx.merchant.supplychain.util.ImportedResult;
import cn.com.glsx.merchant.supplychain.vo.JstShopVO;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.jst.dto.CheckImportDataDTO;
import cn.com.glsx.supplychain.jst.dto.JstShopDTO;
import cn.com.glsx.supplychain.jst.dto.JstShopExportDTO;
import cn.com.glsx.supplychain.jst.dto.JstShopImportDTO;
import cn.com.glsx.supplychain.jst.remote.JxBsMerchantRemote;
import cn.com.glsx.supplychain.remote.bs.DealerUserInfoAdminRemote;
import com.glsx.cloudframework.core.util.StringUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.oreframework.commons.office.poi.zslin.utils.ExcelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName JstShopController
 * @Description 供货关系
 * @Author xiex
 * @Date 2020/2/14 16:58
 * @Version 1.0
 */
@RestController
@RequestMapping("jstShop")
public class JstShopController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JxBsMerchantRemote jxBsMerchantRemote;

    @Autowired
    private SupplyadminProperty supplyadminProperty;

    //  @Autowired
    //  protected HttpSession session;

    @Autowired
    private DealerUserInfoAdminRemote dealerUserInfoAdminRemote;

    @Autowired
    private MerchantFacadeRemote merchantFacadeRemote;

    /**
     * 供货关系分页列表
     *
     * @param jstShopVO
     * @return
     */
    @RequestMapping("pageJstShop")
    public ResultEntity<RpcPagination<JstShopVO>> pageJstShop(@RequestBody RpcPagination<JstShopVO> jstShopVO) {
        logger.info("jstShop::pageJstShop jstShopVO:{}", jstShopVO.getCondition());
        RpcResponse<RpcPagination<JstShopVO>> result = RpcResponse.success(new RpcPagination<>());
        RpcPagination<JstShopDTO> jstShopDTO = new RpcPagination<>();
        BeanUtils.copyProperties(jstShopVO, jstShopDTO);
        JstShopVO vo = jstShopVO.getCondition();
        if (vo != null) {
            JstShopDTO dto = new JstShopDTO();
            BeanUtils.copyProperties(vo, dto);
            jstShopDTO.setCondition(dto);
        }
        RpcResponse<RpcPagination<JstShopDTO>> rpcPaginationRpcResponse = jxBsMerchantRemote.pageJstShop(jstShopDTO);
        BeanUtils.copyProperties(rpcPaginationRpcResponse, result);
        List<JstShopDTO> dtoList = rpcPaginationRpcResponse.getResult().getList();
        List<JstShopVO> voList = new ArrayList<>();
        for (JstShopDTO js : dtoList) {
            vo = new JstShopVO();
            BeanUtils.copyProperties(js, vo);
            voList.add(vo);
        }
        result.getResult().setList(voList);
        return ResultEntity.result(result);
    }

    /**
     * 供货关系导入门店模板下载接口
     *
     * @param response
     */
    @RequestMapping(value = "/jstShopDownloadTemplate", method = RequestMethod.GET)
    public void jstShopDownloadTemplate(HttpServletResponse response) {
        try {
            OutputStream os = response.getOutputStream();
            String str = "";
            String name = "";
            str = "供货关系导入门店模板.xlsx";
            name = "templates/templateJstShop.xlsx";

            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=\""
                    + new String(str.getBytes("GBK"), "iso8859-1") + "\"");
            response.setContentType("application/octet-stream;charset=UTF-8");
            ExcelUtils.downloadExcelTemplate(os, name);

        } catch (Exception e) {
            logger.error("下载Excle模版出错：", e);
        }
        return;
    }

    /**
     * 供货关系门店上传效验接口
     */
    @ResponseBody
    @RequestMapping(value = "checkImportJstShop")
    public ResultEntity<ImportedResult> checkImportJstShop(@RequestParam(value = "file") MultipartFile files) {
        ResultEntity<ImportedResult> responseEntity = new ResultEntity<ImportedResult>();
        ImportedResult importedResult = new ImportedResult();

        long totalCount = 0;
        long failCount = 0;
        long successCount = 0;
        String allowNum = supplyadminProperty.getOpAllowNum();
        Integer opAllowNum = 0;
        if (!StringUtils.isEmpty(allowNum)) {
            opAllowNum = Integer.parseInt(allowNum);
        }
        // 这里可以支持多文件上传
        if (files != null) {
            BufferedOutputStream bw = null;
            try {
                String fileName = files.getOriginalFilename();
                // 判断是否有文件且是否为图片文件
                if (fileName != null && !"".equalsIgnoreCase(fileName.trim())
                        && (FilenameUtils.getExtension(fileName.trim()).equals("xls")
                        || FilenameUtils.getExtension(fileName.trim()).equals("xlsx"))) {
                    // 可以选择把文件保存到服务器,创建输出文件对象
                    String strUploadFile = supplyadminProperty.getUploadPath() + UUID.randomUUID().toString() + "."
                            + FilenameUtils.getExtension(fileName);

                    File outFile = new File(strUploadFile);

                    // 文件到输出文件对象
                    FileUtils.copyInputStreamToFile(files.getInputStream(), outFile);

                    List<JstShopImportDTO> jstShopImportDTOList = new ArrayList<JstShopImportDTO>();


                    // 获得输入流
                    InputStream input = files.getInputStream();
                    List<Object> list = null;
                    try {
                        list = ExcelReads.getInstance().readExcel2Objs(input, JstShopImportDTO.class, 0, 0);
                    } catch (Exception e) {
                        responseEntity.setReturnCode("1");
                        responseEntity.setMessage("请使用有效模板导入数据");
                        logger.warn("导入异常....");
                        return responseEntity;
                    }
                    if (list == null || list.size() == 0) {
                        responseEntity.setReturnCode("1");
                        responseEntity.setMessage("请使用有效模板导入数据");
                        logger.warn("导入异常..");
                        return responseEntity;
                    }

                    if (opAllowNum == 0) {
                        opAllowNum = 5000;
                    }
                    totalCount = list.size();
                    if (totalCount > opAllowNum) {
                        responseEntity.setReturnCode("1");
                        responseEntity.setMessage("模版中最大允许导入" + opAllowNum + "条,请修改数据重新再导!");
                        logger.warn("导入异常..");
                        return responseEntity;
                    }

                    for (Object statement : list) {
                        JstShopImportDTO bean = (JstShopImportDTO) statement;
                        jstShopImportDTOList.add(bean);
                    }
                    RpcResponse<CheckImportDataDTO> response = jxBsMerchantRemote.checkImportJstShop(jstShopImportDTOList);
                    List<JstShopImportDTO> sucessList = new ArrayList<JstShopImportDTO>();
                    List<JstShopExportDTO> failList = new ArrayList<JstShopExportDTO>();
                    sucessList = response.getResult().getJstShopImportDTOList();
                    failList = response.getResult().getJstShopExportDTOList();

                    ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
                    if (errCodeEnum != null) {
                        responseEntity.setReturnCode("2");
                        responseEntity.setMessage(errCodeEnum.getDescrible());
                        return responseEntity;
                    }
                    failCount = failList.size();
                    successCount = sucessList.size();

                    importedResult.setSuccessCount(successCount);
                    importedResult.setFailCount(failCount);
                    importedResult.setTotalCount(totalCount);
                    // 保存数据
                    if (sucessList != null && sucessList.size() > 0) {
                        String json = jstShopImportToJson(sucessList);
                        importedResult.setIsImported(1);
                        importedResult.setMsg(json);
                    }

                    if (failList != null && failList.size() > 0) {
                        String name = UUID.randomUUID().toString() + "."
                                + FilenameUtils.getExtension(fileName);
                        String url = supplyadminProperty.getDownloadPath() + name;
                        ExcelUtil.getInstance().exportObj2Excel(url, failList, JstShopExportDTO.class);
                        String reUrl = supplyadminProperty.getDomain() + name;
                        importedResult.setUrl(reUrl);
                        logger.warn("导入结束.....,总数：" + totalCount + " 成功：" + successCount + " 失败：" + failCount);
                    }
                } else {
                    responseEntity.setReturnCode("1");
                    responseEntity.setMessage("上传文件只支持.xls与.xlsx格式，请另存为兼容格式Excel再上传!");
                    logger.warn("导出结束....");
                    return responseEntity;
                }
            } catch (IOException e) {
                logger.error("导入操作异常" + e);
            } finally {
                try {
                    if (bw != null) {
                        bw.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        responseEntity.setData(importedResult);
        return responseEntity;
    }

    /**
     * 转json
     *
     * @param list
     * @return
     */
    public String jstShopImportToJson(List<JstShopImportDTO> list) {
        if (null == list || list.size() < 0) {
            return null;
        }
        StringBuilder json = new StringBuilder();
        json.append("[");
        for (int i = 0; i < list.size(); i++) {
            if (i != 0) {
                json.append(",");
            }
            json.append("{");
            json.append("\"agentMerchantName\":\"" + list.get(i).getAgentMerchantName() + "\",");
            json.append("\"agentMerchantCode\":\"" + list.get(i).getAgentMerchantCode() + "\",");
            json.append("\"shopName\":\"" + list.get(i).getShopName() + "\",");
            json.append("\"addr\":\"" + list.get(i).getAddr() + "\",");
            json.append("\"contact\":\"" + list.get(i).getContact() + "\",");
            json.append("\"phone\":\"" + list.get(i).getPhone() + "\"");
            json.append("}");
        }
        json.append("]");
        return json.toString();
    }

    /**
     * 供货关系门店导入a
     *
     * @param jstShopImportDTOList
     * @return
     */
    @RequestMapping("importMyJstShop")
    public ResultEntity<Integer> importMyJstShop(@RequestBody List<JstShopImportDTO> jstShopImportDTOList) {
        logger.info("jstShop::importMyJstShop jstShopImportDTOList:{}", jstShopImportDTOList);
        RpcResponse<Integer> rpcnRpcResponse = jxBsMerchantRemote.importJstShop(jstShopImportDTOList);
        return ResultEntity.result(rpcnRpcResponse);
    }

    /**
     * 供货关系审核门店
     *
     * @param jstShopVO
     * @return
     */
    @RequestMapping("checkJstShop")
    public ResultEntity<Integer> checkJstShop(@RequestBody JstShopVO jstShopVO) {
        logger.info("jstShop::checkJstShop checkJstShop:{}", jstShopVO);
        JstShopDTO jstShopDTO = new JstShopDTO();
        jstShopVO.setShopMerchantCode(jstShopVO.getMerchantId());
        jstShopVO.setShopMerchantName(jstShopVO.getMerchantName());
        if (jstShopVO != null) {
            BeanUtils.copyProperties(jstShopVO, jstShopDTO);
        }
        RpcResponse<Integer> rpcnRpcResponse = jxBsMerchantRemote.checkJstShop(jstShopDTO);
        return ResultEntity.result(rpcnRpcResponse);
    }


    /**
     * 供货关系根据手机号查询门店
     * @param phone
     * @return
     */
/*    @RequestMapping("getByPhone")
    public ResultEntity<MerchantFacadeResponse> getByPhone(@RequestBody String phone) {
        logger.info("jstShop::getByPhone getByPhone:{}",phone);
        RpcResponse<MerchantFacadeResponse> rpcnRpcResponse = merchantFacadeRemote.getMerchantFacadeByPhoneRemote(phone);
        return ResultEntity.result(rpcnRpcResponse);
    }*/

    /**
     * 供货关系根据商户名称和手机号查询门店
     *
     * @param jstShopVO
     * @return
     */
    @RequestMapping("getMerchantByVagueNameAndPhoneFacade")
    public ResultEntity<List<MerchantFacadeResponse>> getMerchantByVagueNameAndPhoneFacade(@RequestBody JstShopVO jstShopVO) {
        List<MerchantFacadeResponse> list = new ArrayList<>();
        RpcResponse<List<MerchantFacadeResponse>> rpcnRpcResponse = RpcResponse.success(list);
        String merchantName = jstShopVO.getMerchantName();
        String phone = jstShopVO.getPhone();
        logger.info("jstShop::getByMerchantName getMerchantByVagueNameAndPhoneFacade:shopName={},phone={}", merchantName, phone);
        rpcnRpcResponse = merchantFacadeRemote.getMerchantByVagueNameAndPhoneFacade(merchantName, null);
        //logger.info("jstShop::getByMerchantName getMerchantByVagueNameAndPhoneFacade  res：{}", JSONObject.toJSONString(rpcnRpcResponse.getResult()));
       /* if (!StringUtils.isEmpty(merchantName) && !StringUtils.isEmpty(merchantName)) {
            rpcnRpcResponse = merchantFacadeRemote.getMerchantByVagueNameAndPhoneFacade(merchantName, phone);
            if (!(rpcnRpcResponse != null && rpcnRpcResponse.getResult() != null && rpcnRpcResponse.getResult().size() > 0)) {
                rpcnRpcResponse = merchantFacadeRemote.getMerchantByVagueNameAndPhoneFacade(merchantName, phone);
                if (!(rpcnRpcResponse != null && rpcnRpcResponse.getResult() != null && rpcnRpcResponse.getResult().size() > 0)) {
                    rpcnRpcResponse = merchantFacadeRemote.getMerchantByVagueNameAndPhoneFacade(null, phone);
                    if (!(rpcnRpcResponse != null && rpcnRpcResponse.getResult() != null && rpcnRpcResponse.getResult().size() > 0)) {
                        rpcnRpcResponse = merchantFacadeRemote.getMerchantByVagueNameAndPhoneFacade(merchantName, null);
                    }
                }
            }
        } else if (StringUtils.isEmpty(merchantName) && !StringUtils.isEmpty(phone)) {
            rpcnRpcResponse = merchantFacadeRemote.getMerchantByVagueNameAndPhoneFacade(null, phone);
        } else if (!StringUtils.isEmpty(merchantName) && StringUtils.isEmpty(phone)) {
            rpcnRpcResponse = merchantFacadeRemote.getMerchantByVagueNameAndPhoneFacade(merchantName, null);
        }*/

        return ResultEntity.result(rpcnRpcResponse);
    }

    /**
     * 供货关系根据商户名称创建门店
     *
     * @param jstShopVO
     * @return
     */
    @RequestMapping("saveMerchantFacadeRemote")
    public ResultEntity<ResultResponse> saveMerchantFacadeRemote(@RequestBody JstShopVO jstShopVO) {
        logger.info("jstShop::getByMerchantName saveMerchantFacadeRemote:{}", jstShopVO);
        MerchantFacade merchantFacade = new MerchantFacade();
        merchantFacade.setMerchantName(jstShopVO.getShopName());
        merchantFacade.setGpsAddress(jstShopVO.getAddr());
        merchantFacade.setContactor(jstShopVO.getContact());
        merchantFacade.setContactPhone(jstShopVO.getPhone());
        MerchantDetailFacade merchantDetailFacade = new MerchantDetailFacade();
        try {
            RpcResponse<ResultResponse> rpcnRpcResponse = merchantFacadeRemote.saveMerchantFacadeRemote(merchantFacade, merchantDetailFacade, SourceEnum.SOURCE_SUPPLY);
            return ResultEntity.result(rpcnRpcResponse);
        } catch (RpcServiceException e) {
            return ResultEntity.error(e.getErrId(), e.getMessage());
        }
    }

}
