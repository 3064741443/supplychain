package cn.com.glsx.merchant.supplychain.controller.jst;

import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.merchant.supplychain.config.Constants;
import cn.com.glsx.merchant.supplychain.config.SupplyadminProperty;
import cn.com.glsx.merchant.supplychain.convert.JstShopHttpConvert;
import cn.com.glsx.merchant.supplychain.util.*;
import cn.com.glsx.merchant.supplychain.vo.DisJspShopVO;
import cn.com.glsx.merchant.supplychain.vo.JstShopVO;
import cn.com.glsx.merchant.supplychain.web.session.Session;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.jst.dto.*;
import cn.com.glsx.supplychain.jst.enums.JstErrorCodeEnum;
import cn.com.glsx.supplychain.jst.remote.JxBsMerchantRemote;
import cn.com.glsx.supplychain.jst.remote.WxBsMerchantRemote;
import cn.com.glsx.supplychain.model.bs.DealerUserInfo;
import cn.com.glsx.supplychain.remote.bs.DealerUserInfoAdminRemote;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.oreframework.commons.office.poi.zslin.utils.ExcelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName JstShopController
 * @Description 经销存系统我的门店
 * @Author xiex
 * @Date 2020/2/14 16:58
 * @Version 1.0
 */
@RestController
@RequestMapping("myJstShop")
public class MyJstShopController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JxBsMerchantRemote jxBsMerchantRemote;

    @Autowired
    private SupplyadminProperty supplyadminProperty;

    @Autowired
    private WxBsMerchantRemote wxBsMerchantRemote;

  //  @Autowired
  //  protected HttpSession session;

    @Autowired
    private DealerUserInfoAdminRemote dealerUserInfoAdminRemote;

    /**
     * 获取经销存系统我的门店分页列表
     * @param disJspShop
     * @return
     */
    @RequestMapping("pageMyJstShop")
    public ResultEntity<DisJspShopVO> pageMyJstShop(@RequestBody DisJspShopVO disJspShop)
    {
    	Session session = ThreadLocalCache.getSession();
        DealerUserInfo dealerUserInfo = (DealerUserInfo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
 //   	DealerUserInfo dealerUserInfo = (DealerUserInfo) session.getAttribute(Constants.SESSION_LOGIN_USER);
    	logger.info("MerchantStockDeviceController::pageMyJstShop start dealerUserInfo:{},disJspShop:{}",dealerUserInfo,disJspShop);
    	if(StringUtils.isEmpty(dealerUserInfo) || StringUtils.isEmpty(dealerUserInfo.getMerchantCode()))
    	{
    		logger.info("MerchantStockDeviceController::pageMyJstShop not login or session time out");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(),null);
    	}
    	RpcPagination<JstShopDTO> rpcJstShopDTO = new RpcPagination<>();
    	rpcJstShopDTO.setPageNum(disJspShop.getPageNum());
    	rpcJstShopDTO.setPageSize(disJspShop.getPageSize());
    	JstShopDTO jstShopDto = new JstShopDTO();
    	if(!StringUtils.isEmpty(disJspShop.getShopName()))
    	{
    		jstShopDto.setShopName(disJspShop.getShopName());
    	}
    	if(!StringUtils.isEmpty(disJspShop.getStatus()))
    	{
    		jstShopDto.setStatus(disJspShop.getStatus());
    	}
    	jstShopDto.setAgentMerchantCode(dealerUserInfo.getMerchantCode());
    	rpcJstShopDTO.setCondition(jstShopDto);
    	RpcResponse<RpcPagination<JstShopDTO>> rpcPaginationRpcResponse = jxBsMerchantRemote.pageMyJstShop(rpcJstShopDTO);
    	JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rpcPaginationRpcResponse.getError();
		if (!StringUtils.isEmpty(errCodeEnum)) {
			logger.info("WxProductController::pageMyJstShop return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		RpcPagination<JstShopDTO> rpcDtoResult = rpcPaginationRpcResponse.getResult();
		disJspShop.setPageNum(rpcDtoResult.getPageNum());
		disJspShop.setPages(rpcDtoResult.getPages());
		disJspShop.setPageSize(rpcDtoResult.getPageSize());
		disJspShop.setTotal((int)rpcDtoResult.getTotal());
		disJspShop.setListJstShop(JstShopHttpConvert.convertListDto(rpcDtoResult.getList()));
		logger.info("MerchantStockDeviceController::pageMyJstShop end disJspShop:{}",disJspShop);
    	return ResultEntity.success(disJspShop);
    }
 /*   public ResultEntity<RpcPagination<JstShopVO>> pageMyJstShop(@RequestBody RpcPagination<JstShopVO> jstShopVO) {
        logger.info("myJstShop::pageMyJstShop jstShopVO:{}",jstShopVO.getCondition());
        RpcResponse<RpcPagination<JstShopVO>> result = RpcResponse.success(new RpcPagination<>());
        RpcPagination<JstShopDTO> jstShopDTO = new RpcPagination<>();
        BeanUtils.copyProperties(jstShopVO, jstShopDTO);
        JstShopVO vo = jstShopVO.getCondition();
        if(vo != null){
            JstShopDTO dto = new JstShopDTO();
            BeanUtils.copyProperties(vo, dto);
            jstShopDTO.setCondition(dto);
        }
        logger.info("myJstShop::pageMyJstShop jstShopDTO:{}",jstShopDTO.getCondition());
        RpcResponse<RpcPagination<JstShopDTO>> rpcPaginationRpcResponse = jxBsMerchantRemote.pageMyJstShop(jstShopDTO);
        BeanUtils.copyProperties(rpcPaginationRpcResponse, result);
        List<JstShopDTO> dtoList = rpcPaginationRpcResponse.getResult().getList();
        List<JstShopVO> voList = new ArrayList<>();
        for(JstShopDTO  js: dtoList){
            vo = new JstShopVO();
            BeanUtils.copyProperties(js, vo);
            voList.add(vo);
        }
        result.getResult().setList(voList);
        return ResultEntity.result(result);
    }*/

    /**
     * 我的门店导入门店模板下载接口
     * @param response
     */
    @RequestMapping(value = "/myJstShopDownloadTemplate", method = RequestMethod.GET)
    public void myJstShopDownloadTemplate(HttpServletResponse response) {
        try {
            OutputStream os = response.getOutputStream();
            String str = "";
            String name = "";
            str = "我的门店导入门店模板.xlsx";
            name = "templates/myTemplateJstShop.xlsx";

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
     * 我的门店上传效验接口
     */
    @ResponseBody
    @RequestMapping(value = "/checkImportMyJstShop")
    public ResultEntity<ImportedResult> checkImportMyJstShop(@RequestParam(value = "file") MultipartFile files, HttpServletRequest request) {
        ResultEntity<ImportedResult> responseEntity = new ResultEntity<ImportedResult>();
        ImportedResult importedResult = new ImportedResult();
        String merchantCode =  request.getParameter("merchantCode");
        long totalCount = 0;
        long failCount = 0;
        long successCount = 0;
        String allowNum = supplyadminProperty.getOpAllowNum();
        Integer opAllowNum = 0;
        if (!com.glsx.cloudframework.core.util.StringUtils.isEmpty(allowNum)) {
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

                    List<MyJstShopImportDTO> myJstShopImportDTOList = new ArrayList<MyJstShopImportDTO>();


                    // 获得输入流
                    InputStream input = files.getInputStream();
                    List<Object> list = null;
                    try {
                        list = ExcelReads.getInstance().readExcel2Objs(input, MyJstShopImportDTO.class, 0, 0);
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
                        MyJstShopImportDTO bean = (MyJstShopImportDTO) statement;
                        myJstShopImportDTOList.add(bean);
                    }
                    myJstShopImportDTOList.get(0).setAgentMerchantCode(merchantCode);
                    RpcResponse<CheckImportDataDTO> response = jxBsMerchantRemote.checkImportMyJstShop(myJstShopImportDTOList);
                    List<MyJstShopImportDTO> sucessList = new ArrayList<MyJstShopImportDTO>();
                    List<MyJstShopExportDTO> failList = new ArrayList<MyJstShopExportDTO>();
                    sucessList = response.getResult().getMyJstShopImportDTOList();
                    failList = response.getResult().getMyJstShopExportDTOList();

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
                        String json = myJstShopImportToJson(sucessList);
                        importedResult.setIsImported(1);
                        importedResult.setMsg(json);
                    }

                    if (failList != null && failList.size() > 0) {
                        String name = UUID.randomUUID().toString() + "."
                                + FilenameUtils.getExtension(fileName);
                        String url = supplyadminProperty.getDownloadPath() + name;
                        ExcelUtil.getInstance().exportObj2Excel(url, failList, MyJstShopExportDTO.class);
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
     * @param list
     * @return
     */
    public String myJstShopImportToJson(List<MyJstShopImportDTO> list){
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
     * 经销存系统我的门店导入
     * @param jstShopImportDTOList
     * @return
     */
    @RequestMapping("importMyJstShop")
    public ResultEntity<Integer> importMyJstShop(@RequestBody List<MyJstShopImportDTO> jstShopImportDTOList) {
        logger.info("myJstShop::importMyJstShop jstShopImportDTOList:{}",jstShopImportDTOList);
/*        DealerUserInfo dealerUserInfo = (DealerUserInfo) session.getAttribute(Constants.SESSION_LOGIN_USER);
        RpcResponse<DealerUserInfo> dul = dealerUserInfoAdminRemote.getDealerUserInfoByDealerUserName(dealerUserInfo.getName());
        if (jstShopImportDTOList != null && jstShopImportDTOList.size() > 0) {
            jstShopImportDTOList.get(0).setAgentMerchantCode(dul.getResult().getMerchantCode());
            jstShopImportDTOList.get(0).setAgentMerchantName(dul.getResult().getMerchantName());
        }*/
        RpcResponse<Integer> rpcnRpcResponse = jxBsMerchantRemote.importMyJstShop(jstShopImportDTOList);
        return ResultEntity.result(rpcnRpcResponse);
    }

    /**
     * 获取供应商下门店
     */
    @RequestMapping("wxListAgentJstShops")
    public ResultEntity<List<JstShopVO>> wxListAgentJstShops()
    {
        Session session = ThreadLocalCache.getSession();
        DealerUserInfo userInfoVo = (DealerUserInfo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
        if(StringUtils.isEmpty(userInfoVo) || StringUtils.isEmpty(userInfoVo.getMerchantCode()))
        {
            logger.info("WxProductController::wxListAgentJstShops not login or session time out");
            return ResultEntity.error(JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(),null);
        }
        BsDealerUserInfoDTO dtoCondition = new BsDealerUserInfoDTO();
        dtoCondition.setMerchantCode(userInfoVo.getMerchantCode());
        JxcUtils.setBaseDTO(dtoCondition);
        RpcResponse<List<JstShopDTO>> rsp = wxBsMerchantRemote.listJstShopByAgentMerchant(dtoCondition);
        JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
        if (!StringUtils.isEmpty(errCodeEnum)) {
            logger.info("WxProductController::wxListAgentJstShops return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        List<JstShopDTO> listDtoReturn = rsp.getResult();
        List<JstShopVO> listReturn = JstShopHttpConvert.convertListDto(listDtoReturn);
        logger.info("WxProductController::wxListAgentJstShops end listReturn:{}",listReturn);
        return ResultEntity.success(listReturn);
    }
}
