package cn.com.glsx.supplychain.controller.bs;

/**
 * @ClassName AfterSalesController
 * @Author admin
 * @Param
 * @Date 2019/3/4 14:57
 * @Version
 **/

import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.config.SupplyadminProperty;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.model.*;
import cn.com.glsx.supplychain.model.bs.*;
import cn.com.glsx.supplychain.remote.DeviceManagerAdminRemote;
import cn.com.glsx.supplychain.remote.SupplyChainAdminRemote;
import cn.com.glsx.supplychain.remote.bs.*;
import cn.com.glsx.supplychain.util.ExcelReads;
import cn.com.glsx.supplychain.util.ExcelUtils;
import cn.com.glsx.supplychain.util.ImportedResult;
import cn.com.glsx.supplychain.vo.CheckImportDataVo;
import com.glsx.biz.common.base.entity.Area;
import com.glsx.biz.common.base.entity.City;
import com.glsx.biz.common.base.entity.Province;
import com.glsx.biz.common.base.service.AreaService;
import com.glsx.biz.common.base.service.CityService;
import com.glsx.biz.common.base.service.ProvinceService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.oreframework.boot.security.UserInfoHolder;
import org.oreframework.boot.security.cas.User;
import org.oreframework.commons.office.poi.zslin.utils.ExcelUtil;
import org.oreframework.web.ui.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 广联供应链管理
 *
 * @author leiming
 */
@RestController
@RequestMapping("afterSales")
public class AfterSalesController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private CityService cityService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private UserInfoHolder userInfoHolder;

    @Autowired
    private AddressAdminRemote addressAdminRemote;

    @Autowired
    private ProductAdminRemote productAdminRemote;

    @Autowired
    private AfterSaleOrderAdminRemote afterSaleOrderAdminRemote;

    @Autowired
    private MaintainProductRemote maintainProductRemote;

    @Autowired
    private SupplyChainAdminRemote supplyChainAdminRemote;

    @Autowired
    private SupplyadminProperty supplyadminProperty;

    @Autowired
    private DeviceManagerAdminRemote deviceManagerAdminRemote;

    /**
     * 查询售后订单列表（分页）
     *
     * @param pagination
     * @return
     */
    @RequestMapping("listAfterSaleOrder")
    public ResultEntity<RpcPagination<AfterSaleOrder>> listAfterSaleOrder(@RequestBody RpcPagination<AfterSaleOrder> pagination) {
        RpcResponse<RpcPagination<AfterSaleOrder>> response = afterSaleOrderAdminRemote.listAfterSaleOrder(pagination);
        //ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 售后订单详情（根据订单号获取订售后单对象）
     *
     * @param
     * @return
     */
    @RequestMapping("getAfterSaleOrderByOrderNumber")
    public ResultEntity<AfterSaleOrder> getAfterSaleOrderByOrderNumber(String orderNumber) {
        RpcResponse<AfterSaleOrder> response = afterSaleOrderAdminRemote.getAfterSaleOrderByOrderNumber(orderNumber);
        //ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 动态获取省份地址信息
     *
     * @param
     * @return
     */
    @RequestMapping("getProvince")
    public List<Province> getAllList() {
        List<Province> response = provinceService.getAllList();
        return response;
    }

    /**
     * 动态获取城市地址信息
     *
     * @param
     * @return
     */
    @RequestMapping("getCity")
    public List<City> getCityList(Integer pid) {

        List<City> response = cityService.getListByProvinceId(pid);
        return response;

    }

    /**
     * 动态获取地区地址信息
     *
     * @param
     * @return
     */
    @RequestMapping("getAreaList")
    public List<Area> getAreaList(Integer cid) {
        List<Area> response = areaService.getListByCityId(cid);
        return response;

    }

    /**
     * 获取地址簿信息
     *
     * @param
     * @return
     */
    @RequestMapping("getAddressListByName")
    public ResultEntity<List<Address>> getAddressListByName() {
        RpcResponse<List<Address>> response = addressAdminRemote.getAddressListByName(null);
        //ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }




    /**
     * 新增地址簿信息
     *
     * @param
     * @return
     */
    @RequestMapping("addAddressBook")
    public ResultEntity<Integer> add(@RequestBody Address address) {
        @SuppressWarnings({"static-access", "deprecation"})
        User user = userInfoHolder.getUser();
        if(null != user){
            address.setUpdatedBy(user.getRealname());
            address.setCreatedBy(user.getRealname());
        }else {
            address.setUpdatedBy("admin");
            address.setCreatedBy("admin");
        }
        RpcResponse<Integer> response = addressAdminRemote.add(address);
        //ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (response.getError() == null) {
            response.setMessage("新增成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 新增维修物流信息
     *
     * @param
     * @return
     */
    @RequestMapping("addAfterSaleOrderLogistics")
    public ResultEntity<Integer> addAfterSaleOrderLogistics(@RequestBody Logistics logistics) {
        @SuppressWarnings({"static-access", "deprecation"})
        User user = userInfoHolder.getUser();
        if(null != user){
            logistics.getReceiveAddress().setUpdatedBy(user.getRealname());
            logistics.setUpdatedBy(user.getRealname());
            logistics.getReceiveAddress().setCreatedBy(user.getRealname());
            logistics.setCreatedBy(user.getRealname());
        }else {
            logistics.getReceiveAddress().setUpdatedBy("admin");
            logistics.setUpdatedBy("admin");
            logistics.getReceiveAddress().setCreatedBy("admin");
            logistics.setCreatedBy("admin");
        }
        RpcResponse<Integer> response = afterSaleOrderAdminRemote.addAfterSaleOrderLogistics(logistics);
        //ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (response.getError() == null) {
            response.setMessage("新增成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 修改维修物流信息
     *
     * @param
     * @return
     */
    @RequestMapping("updateAfterSaleOrderLogisticsByServiceCodeAndType")
    public ResultEntity<Integer> updateAfterSaleOrderLogisticsByServiceCodeAndType(@RequestBody Logistics logistics) {
        @SuppressWarnings({"static-access", "deprecation"})
        User user = userInfoHolder.getUser();
        if(null != user){
            logistics.setUpdatedBy(user.getRealname());
        }else {
            logistics.setUpdatedBy("admin");
        }
        RpcResponse<Integer> response = afterSaleOrderAdminRemote.updateAfterSaleOrderLogisticsByServiceCodeAndType(logistics);
        //ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (response.getError() == null) {
            response.setMessage("新增成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }


    /**
     * 售后订单修改状态
     *
     * @param
     * @return
     */
    @RequestMapping("updateByOrderNumber")
    public ResultEntity<Integer> updateByOrderNumber(@RequestBody AfterSaleOrder afterSaleOrder) {
        RpcResponse<Integer> response = afterSaleOrderAdminRemote.updateByOrderNumber(afterSaleOrder);
        //ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (response.getError() == null) {
            response.setMessage("更改成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 根据产品编号获取产品信息
     *
     * @param
     * @return
     */
    @RequestMapping("getProductByProductCode")
    public ResultEntity<Product> getProductByProductCode(String code) {
        RpcResponse<Product> response = productAdminRemote.getProductByProductCode(code);
        //ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 获取全部产品类型名称(Device_Type)
     *
     * @param
     * @return
     */
    @RequestMapping("listDeviceType")
    public ResultEntity<List<DeviceType>> listDeviceType(Integer id) {
        DeviceType deviceType = new DeviceType();
        deviceType.setId(id);
        RpcResponse<List<DeviceType>> response = deviceManagerAdminRemote.listDeviceType(deviceType);
        //ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 获取全部产品类型名称(Product_Type)
     *
     * @param
     * @return
     */
    @RequestMapping("getProductTypeList")
    public ResultEntity<List<ProductType>> getProductTypeList(@RequestBody ProductType productType) {
        RpcResponse<List<ProductType>> response = productAdminRemote.getProductTypeList(productType);
        //ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 修改售后订单（更具售后订单对象）
     *
     * @param afterSaleOrder
     * @return
     */
    @RequestMapping("updateReject")
    public ResultEntity<Integer> updateReject(AfterSaleOrder afterSaleOrder) {
        RpcResponse<Integer> response = afterSaleOrderAdminRemote.updateByOrderNumber(afterSaleOrder);
        //ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (response.getError() == null) {
            response.setMessage("更改成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }


    /**
     * 售后订单(获取商品sn与工厂信息)
     *
     * @param imei
     * @return
     */
    @RequestMapping("getOrderInfoDetailByImei")
    public ResultEntity<OrderInfoDetail> getOrderInfoDetailByImei(String imei) {
        RpcResponse<OrderInfoDetail> response = supplyChainAdminRemote.getOrderInfoDetailByImei(imei);
        //ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     *  根据仓库ID查询仓库名称
     *
     * @param id
     * @return
     */
    @RequestMapping("getWareHouseInfo")
    public ResultEntity<WareHouseInfo> getWareHouseInfoById(Integer id) {
        RpcResponse<WareHouseInfo> response = supplyChainAdminRemote.getWareHouseInfoById(id);
        //ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     *  添加维修产品库
     *
     * @param maintainProduct
     * @return
     */
    @RequestMapping("addMainTainProduct")
    public ResultEntity<Integer>addMainTainProduct(@RequestBody MaintainProduct maintainProduct){
        @SuppressWarnings({"static-access", "deprecation"})
        User user = userInfoHolder.getUser();
        if (null != user){
                maintainProduct.setCreatedBy(user.getRealname());
                maintainProduct.setUpdatedBy(user.getRealname());
                maintainProduct.setCreatedDate(new Date());
                maintainProduct.setUpdatedDate(new Date());
                maintainProduct.setDeletedFlag("N");
        }else {
                maintainProduct.setCreatedBy("admin");
                maintainProduct.setUpdatedBy("admin");
            }
        RpcResponse<Integer>response = maintainProductRemote.add(maintainProduct);
        //ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (response.getError() == null) {
            response.setMessage("添加成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     *  添加售后SN签收记录
     *
     * @param afterSaleOrderDetailList
     * @return
     */
    @RequestMapping("insertAfterSaleOrderDetailList")
    public ResultEntity<Integer>insertAfterSaleOrderDetailList(@RequestBody List<AfterSaleOrderDetail> afterSaleOrderDetailList){
        RpcResponse<Integer>response = afterSaleOrderAdminRemote.insertAfterSaleOrderDetailList(afterSaleOrderDetailList);
        //ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (response.getError() == null) {
            response.setMessage("添加成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 获取售后SN发货记录信息
     *
     * @param afterSaleOrderDetail
     * @return
     */
    @RequestMapping("getAfterSalesSnList")
    public ResultEntity<List<AfterSaleOrderDetail>> getAfterSalesSnList(@RequestBody AfterSaleOrderDetail afterSaleOrderDetail) {
        RpcResponse<List<AfterSaleOrderDetail>> response = afterSaleOrderAdminRemote.getAfterSalesSnList(afterSaleOrderDetail);
        //ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 查询售后订单实际发货的SN信息
     *
     * @param sn
     * @return
     */
    @RequestMapping("getDeviceInfoBySn")
    public ResultEntity<DeviceInfo> getDeviceInfoBySn(String sn) {
        RpcResponse<DeviceInfo> response = supplyChainAdminRemote.getDeviceInfoBySn(sn);
        //ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 根据SN查询设备库存信息
     *
     * @param maintainProductDetail
     * @return
     */
    @RequestMapping("getMainTainSnChangeList")
    public ResultEntity<List<MaintainSnChange>> getMainTainSnChangeList(@RequestBody MaintainProductDetail maintainProductDetail) {
        RpcResponse<List<MaintainSnChange>> response = afterSaleOrderAdminRemote.getMainTainSnChangeList(maintainProductDetail);
        //ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 根据SN查询DeviceFile信息
     *
     * @param sn
     * @return
     */
    @RequestMapping("getDeviceFileBySn")
    public ResultEntity<DeviceFile> getDeviceFileBySn(String sn) {
        RpcResponse<DeviceFile> response = afterSaleOrderAdminRemote.getDeviceFileBySn(sn);
        //ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * <br>
     * <b>功能：</b>Sn导入模板下载<br>
     * <b>日期：2019-2-25
     *
     * @param response
     */
    @RequestMapping(value = "/SndownloadTemplate", method = RequestMethod.GET)
    public void SndownloadTemplate(HttpServletResponse response) {
        try {
            OutputStream os = response.getOutputStream();
            String str = "";
            String name = "";
            str = "Sn导入模板.xlsx";
            name = "templates/templateSn.xlsx";

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
     * @param list 拼接成json 返回
     * @return String
     */
    public String snconstrJson(List<SnChangeImport> list) {

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
            json.append("\"sn\":\"" + list.get(i).getSn() + "\",");
            json.append("}");
        }
        json.append("]");
        return json.toString();
    }

    /**
     * SN导入返回数据
     *
     * @param files
     * @param
     */
    @ResponseBody
    @RequestMapping(value = "/snImportData")
    public ResponseEntity<ImportedResult> snImportData(@RequestParam(value = "file") MultipartFile files) {
        ResponseEntity<ImportedResult> responseEntity = new ResponseEntity<ImportedResult>();
        List<ImportedResult> importList = new ArrayList<ImportedResult>();
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

                    List<SnChangeImport> SnChangeList = new ArrayList<SnChangeImport>();


                    // 获得输入流
                    InputStream input = files.getInputStream();
                    List<Object> list = null;
                    try {
                        list = ExcelReads.getInstance().readExcel2Objs(input, SnChangeImport.class, 0, 0);
                    } catch (Exception e) {
                        importedResult.setIsImported(4);
                        importedResult.setCause("请使用有效模板导入数据");
                        importList.add(importedResult);
                        responseEntity.setData(importList);
                        logger.warn("导入异常....");
                        return responseEntity;
                    }
                    if (list == null || list.size() == 0) {
                        importedResult.setIsImported(4);
                        importedResult.setCause("请使用有效模板导入数据");
                        importList.add(importedResult);
                        responseEntity.setData(importList);
                        logger.warn("导入异常..");
                        return responseEntity;
                    }

                    if (opAllowNum == 0) {
                        opAllowNum = 2000;
                    }
                    totalCount = list.size();
                    if (totalCount > opAllowNum) {
                        importedResult.setIsImported(3);
                        importedResult.setCause("模版中最大允许导入" + opAllowNum + "条,请修改数据重新再导!");
                        importList.add(importedResult);
                        responseEntity.setData(importList);
                        logger.warn("导入异常..");
                        return responseEntity;
                    }

                    for (Object device : list) {
                        SnChangeImport bean = (SnChangeImport) device;
                        SnChangeList.add(bean);
                    }
                    RpcResponse<CheckImportDataVo> response = afterSaleOrderAdminRemote.checkImportSnChangeList(SnChangeList);
                    List<SnChangeImport> SucessList = new ArrayList<SnChangeImport>();
                    List<SnChangeExport> failList = new ArrayList<SnChangeExport>();
                    SucessList = response.getResult().getSnChangeImports();
                    failList = response.getResult().getSnChangeExportList();

                    ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
                    if (errCodeEnum != null) {

                        importedResult.setIsImported(4);
                        importedResult.setCause(errCodeEnum.getDescrible());
                        importList.add(importedResult);
                        responseEntity.setData(importList);
                        return responseEntity;
                    }
                    failCount = failList.size();
                    successCount = SucessList.size();

                    importedResult.setSuccessCount(successCount);
                    importedResult.setFailCount(failCount);
                    importedResult.setTotalCount(totalCount);
                    // 保存数据
                    if (SucessList != null && SucessList.size() > 0) {
                        String json = snconstrJson(SucessList);
                        importedResult.setIsImported(1);
                        importedResult.setMsg(json);
                    }

                    if (failList != null && failList.size() > 0) {
                        String name = UUID.randomUUID().toString() + "."
                                + FilenameUtils.getExtension(fileName);
                        String url = supplyadminProperty.getDownloadPath() + name;
                        ExcelUtil.getInstance().exportObj2Excel(url, failList, SnChangeExport.class);
                        String reUrl = supplyadminProperty.getDomain() + name;
                        importedResult.setUrl(reUrl);
                        logger.warn("导入结束.....,总数：" + totalCount + " 成功：" + successCount + " 失败：" + failCount);
                    }
                } else {
                    importedResult.setCause("上传文件只支持.xls与.xlsx格式，请另存为兼容格式Excel再上传");
                    importedResult.setIsImported(2);
                    importList.add(importedResult);
                    responseEntity.setData(importList);
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
        importList.add(importedResult);
        responseEntity.setData(importList);
        return responseEntity;
    }
}



