package cn.com.glsx.supplychain.remote;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.glsx.framework.core.annotation.ApiMethod;
import cn.com.glsx.framework.core.annotation.ApiParam;
import cn.com.glsx.framework.core.annotation.ApiResponse;
import cn.com.glsx.framework.core.annotation.ApiService;
import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.model.*;
import cn.com.glsx.supplychain.vo.CheckImportDataVo;


/**
 * @author ql
 * @version V1.0
 * @Title: DeviceManagerAdminRemote.java
 * @Description: 设备管理RPC接口(针对运营业务后台)
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@ApiService(value = "设备管理RPC接口(针对运营业务后台)", owner = "supplychain", version = "1.0.0")
public interface DeviceManagerAdminRemote {

    /**
     * 获取全部设备类型（大类型）
     *
     * @param targetPage:指定页 （NULL 默认1） pageSize:指定页大小(null 默认40)
     * @return
     * @throws
     */
    @ApiMethod("获取全部设备类型（大类型）")
    @ApiResponse(value = "返回设备类型（大类型）列表")
    @ApiParam(name = "DeviceType", notes = "设备大类", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<List<DeviceType>> listDeviceType(DeviceType record);

    /**
     * 获取商户列表
     *
     * @param targetPage:指定页 （NULL 默认1） pageSize:指定页大小(null 默认40)
     * @return
     * @throws RpcServiceException
     */
    @ApiMethod("获取商户列表")
    @ApiResponse(value = "返回商户列表")
    @ApiParam(name = "targetPage", notes = "当前页", required = true, dataType = ApiParam.DataTypeEnum.INTEGER)
    @SuppressWarnings("rawtypes")
    RpcResponse<List> listMerchantInfo(Integer targetPage, Integer pageSize);

    /**
     * 获取商户列表
     *
     * @param condition 查询条件 targetPage:指定页 （NULL 默认1） pageSize:指定页大小(null 默认40)
     * @return
     * @throws RpcServiceException
     */
    @ApiMethod("获取品牌定制商列表")
    @ApiResponse(value = "获取品牌定制商列表")
    @ApiParam(name = "condition", notes = "当前页", required = true, dataType = ApiParam.DataTypeEnum.INTEGER)
    @SuppressWarnings("rawtypes")
    RpcResponse<List> findlistMerchantInfo(HashMap<String, Object> condition, Integer targetPage, Integer pageSize);

    /**
     * 设备分类列表
     *
     * @param deviceCode DeviceCode条件查询参数
     * @return
     * @throws RpcServiceException
     */
    @ApiMethod("设备分类列表")
    @ApiResponse(value = "返回设备分类列表")
    @ApiParam(name = "pagination", notes = "分页对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<RpcPagination<DeviceCode>> pageDeviceCode(RpcPagination<DeviceCode> pagination, DeviceCode deviceCode);

    /**
     * 设备分类列表导出
     *
     * @param deviceCode 条件参数
     * @return
     * @throws RpcServiceException
     */
    @ApiMethod("设备分类列表导出")
    @ApiResponse(value = "返回设备分类导出列表")
    @ApiParam(name = "deviceCode", notes = "设备小类实体", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<List<DeviceCode>> exportDeviceCode(DeviceCode deviceCode);

    /**
     * 设备分类添加和修改
     *
     * @param deviceCode
     * @return
     * @throws RpcServiceException
     */
    @ApiMethod("设备分类添加和修改")
    @ApiResponse(value = "返回增加或者修改结果")
    @ApiParam(name = "deviceCode", notes = "设备小类实体", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> addAndUpdateDeviceCode(DeviceCode deviceCode);

    /**
     * 设备导入(excel)数据校验
     *
     * @param
     * @return
     * @throws RpcServiceException
     */
    @ApiMethod("设备导入(excel)数据校验")
    @ApiResponse(value = "返回设备导入(excel)数据校验结果")
    @ApiParam(name = "deviceCode", notes = "设备编码", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<CheckImportDataVo> checkImportDeviceList(String deviceCode, List<DeviceListImport> importList, Boolean isOnlyimsi,Boolean isAdditional);

    /**
     * gps出入库导入数据校验
     *
     * @param
     * @return
     * @throws RpcServiceException
     */
    @ApiMethod("gps出入库导入数据校验")
    @ApiResponse(value = "gps出入库导入数据校验")
    @ApiParam(name = "DeviceInfoGpsPreimport", notes = "设备编码", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<CheckImportDataVo> checkImportGpsDevceList(List<DeviceInfoGpsPreimport> importList);
    
    /**
     * gps出入库导入数据
     *
     * @param
     * @return
     * @throws RpcServiceException
     */
    @ApiMethod("gps出入库导入数据")
    @ApiResponse(value = "gps出入库导入数据")
    @ApiParam(name = "DeviceInfoGpsPreimport", notes = "设备编码", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> importGpsDeviceList(String userName,List<DeviceInfoGpsPreimport> importList);
    
    /**
     * gps出入库导入数据
     *
     * @param
     * @return
     * @throws RpcServiceException
     */
    @ApiMethod("gps出入库列表显示")
    @ApiResponse(value = "gps出入库列表显示")
    @ApiParam(name = "pageGpsDeviceList", notes = "设备编码", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<RpcPagination<DeviceInfoGpsPreimport>> pageGpsDeviceList(RpcPagination<DeviceInfoGpsPreimport> pagination);
    
    /**
     * 补录设备导入(excel)数据校验
     *
     * @param
     * @return
     * @throws RpcServiceException
     */
    @ApiMethod("补录设备导入(excel)数据校验")
    @ApiResponse(value = "返回补录设备导入(excel)数据校验结果")
    @ApiParam(name = "deviceCode", notes = "设备编码", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<CheckImportDataVo> checkImportDeviceFileUnstock(String deviceCode, List<DeviceListImport> importList, Boolean isOnlyimsi);


    /**
     * imei库存设备导入(excel)数据
     *
     * @param
     * @return
     * @throws RpcServiceException
     */
    @ApiMethod("imei库存设备导入(excel)数据")
    @ApiResponse(value = "返回设备导入(excel)数据结果")
    @ApiParam(name = "importList", notes = "导入集合", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<CheckImportDataVo> preImportDeviceImeiStokeList(List<DeviceImeiStokeListImport> imeiStokeList);

    /**
     * 设备导入(excel)数据导入
     *
     * @param
     * @return
     * @throws RpcServiceException
     */
    @ApiMethod("设备导入(excel)数据导入")
    @ApiResponse(value = "返回设备导入(excel)数据导入结果")
    @ApiParam(name = "operatorName", notes = "操作人", required = true, dataType = ApiParam.DataTypeEnum.STRING)
    RpcResponse<Integer> importDeviceListByDeviceCode(String operatorName, String strDeviceCode, List<DeviceListImport> importList, Boolean isOnlyimsi);

    /**
     * 补录设备导入(excel)数据导入
     *
     * @param
     * @return
     * @throws RpcServiceException
     */
    @ApiMethod("补录设备导入(excel)数据导入")
    @ApiResponse(value = "返回设备导入(excel)数据导入结果")
    @ApiParam(name = "operatorName", notes = "操作人", required = true, dataType = ApiParam.DataTypeEnum.STRING)
    RpcResponse<Integer> importDeviceFileUnstockListByDeviceCode(String operatorName, String strDeviceCode, List<DeviceListImport> importList, Boolean isOnlyimsi);

    /**
     * 设备导入(外部程序导入)数据导入(gps)(上线后找个时间 把接口移到DeviceManagerService文件中)
     *
     * @param
     * @return 返回导入失败的列表
     * @throws RpcServiceException
     */
    @ApiMethod("设备导入(外部程序导入)数据导入")
    @ApiResponse(value = "设备导入(外部程序导入)数据导入 返回失败列表")
    RpcResponse<Map<String, List<DeviceListImport>>> importDeviceListByExternalProgram(Integer companyId, String operatorName, String strDeviceCode, List<DeviceListImport> importList);

    
    /**
     * 根据sn获取设备当前状态(上线后找个时间 把接口移到DeviceManagerService文件中)
     *
     * @param
     * @return 根据sn获取设备当前状态
     * @throws RpcServiceException
     */
    @ApiMethod("根据sn获取设备当前状态")
    @ApiResponse(value = "根据sn获取设备当前状态")
    RpcResponse<DeviceFileSnapshot> getDeviceFileSnapshotByDeviceSn(String strSn);
    
    
    /**
     * 根据sn修改设备当前状态(上线后找个时间 把接口移到DeviceManagerService文件中)
     *
     * @param
     * @return 根据sn修改设备当前状态
     * @throws RpcServiceException
     */
    RpcResponse<Integer> updateDeviceFileSnapshotByDeviceSn(DeviceFileSnapshot snapshot);
    

    /**
     * imei库存导入(excel)数据导入
     *
     * @param
     * @return
     * @throws RpcServiceException
     */
    @ApiMethod("imei库存导入(excel)数据导入")
    @ApiResponse(value = "返回imei库存导入(excel)数据导入结果")
    @ApiParam(name = "operatorName", notes = "操作人", required = true, dataType = ApiParam.DataTypeEnum.STRING)
    RpcResponse<Integer> importDeviceimeiStokeList(String operatorName, List<DeviceImeiStokeListImport> importimeiStokeList);

    /**
     * 根据id删除imei库存信息
     *
     * @param
     * @return
     * @throws RpcServiceException
     */
    @ApiMethod("根据id删除imei库存信息")
    @ApiResponse(value = "根据id删除imei库存信息结果")
    @ApiParam(name = "id", notes = "imei库存设备id", required = true, dataType = ApiParam.DataTypeEnum.STRING)
    RpcResponse<Integer> reomveDeviceImeiStock(Integer id);

    /**
     * 设备关系明细列表
     *
     * @param deviceFile DeviceFile条件查询参数
     * @return
     * @throws RpcServiceException
     */
    @ApiMethod("设备关系明细列表")
    @ApiResponse(value = "返回设备关系明细列表")
    @ApiParam(name = "deviceFile", notes = "设备明细对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<RpcPagination<DeviceFile>> listDeviceFile(RpcPagination<DeviceFile> pagination, DeviceFile deviceFile);

    /**
     * 设备关系明细导出
     *
     * @param deviceFile:指定页 （NULL 默认1） pageSize:指定页大小(null 默认40) DeviceFile过滤条件
     * @return
     * @throws RpcServiceException
     */
    @ApiMethod("设备关系明细导出")
    @ApiResponse(value = "返回设备关系明细导出")
    @ApiParam(name = "deviceFile", notes = "设备明细对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<List<DeviceFile>> exportDeviceFile(DeviceFile deviceFile);

    /**
     * 设备关系明细详情
     *
     * @param deviceFile 设备sn / id
     * @return
     * @throws RpcServiceException
     */
    @ApiMethod("设备关系明细详情")
    @ApiResponse(value = "返回设备关系明细详情内容")
    @ApiParam(name = "deviceFile", notes = "设备明细对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<DeviceFile> getDeviceFileById(DeviceFile deviceFile);

    /**
     * 设备更换记录列表
     *
     * @param pagination DeviceUpdateRecord条件查询参数
     * @return
     * @throws RpcServiceException
     */
    @ApiMethod("设备更换记录列表")
    @ApiResponse(value = "返回设备更换记录信息")
    @ApiParam(name = "deviceUpdateRecord", notes = "设备更换记录", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<RpcPagination<DeviceUpdateRecord>> listDeviceUpdateRecord(RpcPagination<DeviceUpdateRecord> pagination, DeviceUpdateRecord deviceUpdateRecord);

    /**
     * 设备更换记录列表导出
     *
     * @return
     * @throws RpcServiceException
     */
    @ApiMethod("设备更换记录列表导出列表")
    @ApiResponse(value = "返回设备更换记录列表导出列表")
    @ApiParam(name = "deviceUpdateRecord", notes = "设备更换记录列表", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<List<DeviceUpdateRecord>> exportDeviceUpdateRecord(DeviceUpdateRecord deviceUpdateRecord);

    /**
     * 设备初始化记录列表
     *
     * @param pagination DeviceResetRecord条件查询参数
     * @return
     * @throws RpcServiceException
     */
    @ApiMethod("设备初始化记录列表")
    @ApiResponse(value = "返回设备初始化记录列表")
    @ApiParam(name = "deviceResetRecord", notes = "设备初始化记录对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<RpcPagination<DeviceResetRecord>> listDeviceResetRecord(RpcPagination<DeviceResetRecord> pagination, DeviceResetRecord deviceResetRecord);


    /**
     * 设备初始化记录列表导出
     *
     * @param deviceResetRecord:指定页 （NULL 默认1） pageSize:指定页大小(null 默认40) DeviceUpdateRecord过滤条件
     * @return
     * @throws RpcServiceException
     */
    @ApiMethod("设备初始化记录列表导出")
    @ApiResponse(value = "返回设备初始化记录列表导出")
    @ApiParam(name = "deviceResetRecord", notes = "设备初始化记录对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<List<DeviceResetRecord>> exportDeviceResetRecord(DeviceResetRecord deviceResetRecord);

    /**
     * 设备初始化记录添加和修改
     *
     * @param
     * @return
     * @throws RpcServiceException
     */
    @ApiMethod("设备初始化记录添加和修改")
    @ApiResponse(value = "返回设备初始化记录添加和修改结果")
    @ApiParam(name = "deviceResetRecord", notes = "设备初始化记录对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> addAndUpdateDeviceResetRecord(DeviceResetRecord deviceResetRecord);

    /**
     * 通过device_code获取到设备清单
     *
     * @return deviceFile
     */
    @ApiMethod("通过device_code获取到设备清单")
    @ApiResponse(value = "返回清单结果")
    @ApiParam(name = "deviceFile", notes = "获取设备清单数据参数", required = true, dataType = ApiParam.DataTypeEnum.STRING)
    RpcResponse<DeviceCode> getDeviceCode(DeviceCode deviceCode);

    /**
     * 查询设备导出信息列表
     *
     * @param record DeviceFileVirtual条件查询参数
     */
    @ApiMethod("获取设备导出信息列表")
    @ApiResponse(value = "返回获取设备导出信息列表")
    @ApiParam(name = "devicefilevirtual", notes = "请求设备导出信息参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<RpcPagination<DeviceFileVirtual>> listDeviceFileVirtual(RpcPagination<DeviceFileVirtual> pagination, DeviceFileVirtual record);

    /**
     * 查询IMEI设备库存信息列表
     *
     * @param record listDeviceImeiStock条件查询参数
     */
    @ApiMethod("查询IMEI设备库存信息列表")
    @ApiResponse(value = "返回IMEI设备库存信息列表")
    @ApiParam(name = "record", notes = "IMEI设备库存信息", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<RpcPagination<DeviceImeiStock>> listDeviceImeiStock(RpcPagination<DeviceImeiStock> pagination, DeviceImeiStock record);

    /**
     * 分页查询设备未入库表
     */
    @ApiMethod("分页查询设备未入库表")
    @ApiResponse(value = "返回分页对象")
    @ApiParam(name = "pagination", notes = "查询条件", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<RpcPagination<DeviceFileUnstock>> listDeviceFileUnstock(RpcPagination<DeviceFileUnstock> pagination);

    /**
     * 根据ID修改未录入的设备信息
     */
    @ApiMethod("根据ID修改未录入的设备信息")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "pagination", notes = "查询条件", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> updateById(DeviceFileUnstock pagination);

    /**
     * 获取最大的统计报表
     */
    @ApiMethod("获取最大的统计报表")
    @ApiResponse(value = "返回成功条数")
    RpcResponse<DeviceStatisReport> getMaxDeviceStatisReport();

}
