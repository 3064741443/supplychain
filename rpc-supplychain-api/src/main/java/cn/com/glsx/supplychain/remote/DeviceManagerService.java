package cn.com.glsx.supplychain.remote;

import java.util.List;
import java.util.Map;

import cn.com.glsx.framework.core.annotation.ApiMethod;
import cn.com.glsx.framework.core.annotation.ApiParam;
import cn.com.glsx.framework.core.annotation.ApiResponse;
import cn.com.glsx.framework.core.annotation.ApiService;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.enums.SystemPlatEnum;
import cn.com.glsx.supplychain.model.DeviceCode;
import cn.com.glsx.supplychain.model.DeviceFileSnapshot;
import cn.com.glsx.supplychain.model.DeviceFileSv;
import cn.com.glsx.supplychain.model.DeviceImeiStock;
import cn.com.glsx.supplychain.model.DeviceListImport;
import cn.com.glsx.supplychain.model.DeviceType;
import cn.com.glsx.supplychain.model.DeviceUpdateRecordSv;
import cn.com.glsx.supplychain.model.SupplyDeviceCodeRequest;
import cn.com.glsx.supplychain.model.SupplyDeviceFileRequest;
import cn.com.glsx.supplychain.model.SupplyDeviceTypeRequest;


/**
 * @author QL.LiuQuan
 * @version V1.0
 * @Title DeviceManagerService.java
 * @Description 设备管理RPC接口(对外接口)
 * @Company Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@ApiService(value = "设备管理RPC接口(对外接口)", owner = "supplychain", version = "1.0.0")
public interface DeviceManagerService {

    /**
     * @param ("必填")consumer,time,version
     * @param ("选填")pagination,request
     * @return RpcResponse<RpcPagination   <   DeviceType>>
     * @Title pageDeviceType
     * @Description 获取设备大类型列表(带分页)
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @ApiMethod("获取设备大类型列表(带分页)")
    @ApiResponse(value = "返回设备大类型列表")
    @ApiParam(name = "request", notes = "请求参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<RpcPagination<DeviceType>> pageDeviceType(RpcPagination<DeviceType> pagination, SupplyDeviceTypeRequest request);

    /**
     * @param ("必填")consumer,time,version
     * @param ("选填")id,name
     * @return RpcResponse<RpcPagination   <   DeviceType>>
     * @Title listDeviceType
     * @Description 获取设备大类型列表
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @ApiMethod("获取设备大类型列表")
    @ApiResponse(value = "返回设备大类型列表")
    @ApiParam(name = "request", notes = "请求参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<List<DeviceType>> listDeviceType(SupplyDeviceTypeRequest request);

    /**
     * @param ("必填")consumer,time,version
     * @param ("选填")deviceCode,id,deviceName,merchantId,typeId
     * @return RpcResponse<RpcPagination   <   DeviceCode>>
     * @Title pageDeviceCode
     * @Description 获取设备编码信息列表(带分页)
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @ApiMethod("获取设备编码信息列表(带分页)")
    @ApiResponse(value = "返回获取设备编号列表")
    @ApiParam(name = "request", notes = "获取设备设备请求参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<RpcPagination<DeviceCode>> pageDeviceCode(RpcPagination<DeviceCode> pagination, SupplyDeviceCodeRequest request);

    /**
     * @param ("必填")consumer,time,version
     * @param ("选填")deviceCode,id,deviceName,merchantId,typeId
     * @return RpcResponse<List   <   DeviceCode>>
     * @Title listDeviceCode
     * @Description 获取设备编码信息列表
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @ApiMethod("获取设备编码信息列表")
    @ApiResponse(value = "返回获取设备编号列表")
    @ApiParam(name = "request", notes = "获取设备设备请求参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<List<DeviceCode>> listDeviceCode(SupplyDeviceCodeRequest request);

    /**
     * @param ("必填")consumer,time,version
     * @param ("选填")listIds
     * @return RpcResponse<List   <   DeviceCode>>
     * @Title listDeviceCodeByIds
     * @Description 根据设备编码id集合获取到code集合
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @ApiMethod("根据设备编码id集合获取到code集合")
    @ApiResponse(value = "返回设备编码信息集合")
    @ApiParam(name = "request", notes = "获取设备设备请求参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<List<DeviceCode>> listDeviceCodeByIds(SupplyDeviceCodeRequest request);


    /**
     * @param ("必填")consumer,time,version
     * @param ("选填")id
     * @return RpcResponse<String>
     * @Title getDeviceTypeNameById
     * @Description 根据设备类型ID 获取设备类型名称
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @ApiMethod("根据设备类型ID 获取设备类型名称")
    @ApiResponse(value = "返回设备类型名称")
    @ApiParam(name = "request", notes = "请求参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<String> getDeviceTypeNameById(SupplyDeviceTypeRequest request);

    /**
     * @param ("必填")consumer,time,version
     * @param ("选填")deviceCode,id
     * @return RpcResponse<DeviceCode>
     * @Title getDeviceCodeByIdOrDeviceCode
     * @Description 根据设备编码 或者设备ID 获取设备编码信息
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @ApiMethod("根据设备编码 或者设备ID 获取设备编码信息 ")
    @ApiResponse(value = "返回设备编码对象")
    @ApiParam(name = "request", notes = "请求参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<DeviceCode> getDeviceCodeByIdOrDeviceCode(SupplyDeviceCodeRequest request);

    /**
     * @param ("必填")consumer,time,version
     * @param ("选填")manufacturerCode
     * @return RpcResponse<DeviceCode>
     * @Title getDeviceCodeByIdOrDeviceCode
     * @Description 根据厂商码 获取设备编码信息
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @ApiMethod("根据厂商码 获取设备编码信息 ")
    @ApiResponse(value = "返回设备编码对象")
    @ApiParam(name = "request", notes = "请求参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<DeviceCode> getDeviceCodeByManufacturerCode(SupplyDeviceCodeRequest request);

    /**
     * @param ("必填")consumer,time,version
     * @param ("选填")deviceCode,id
     * @return RpcResponse<String>
     * @Title getDeviceCodeNameByIdOrDeviceCode
     * @Description 根据设备编码 或者设备ID 获取设备名称
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @ApiMethod("根据设备编码 或者设备ID 获取设备名称")
    @ApiResponse(value = "返回设备设备名称")
    @ApiParam(name = "request", notes = "请求参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<String> getDeviceCodeNameByIdOrDeviceCode(SupplyDeviceCodeRequest request);

    /**
     * @param ("必填")consumer,time,version
     * @param ("选填")id
     * @return RpcResponse<Integer>
     * @Title countDevicesByDeviceType
     * @Description 统计设备大类型下设备数
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @ApiMethod("统计设备大类型下设备数")
    @ApiResponse(value = "返回设备大类型下设备数")
    @ApiParam(name = "request", notes = "请求参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> countDevicesByDeviceType(SupplyDeviceTypeRequest request);

    /**
     * @param ("必填")consumer,time,version
     * @param ("选填")id,deviceCode
     * @return RpcResponse<Integer>
     * @Title countDevicesByDeviceCode
     * @Description 统计设备编码下设备数
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @ApiMethod("统计设备编码下设备数")
    @ApiResponse(value = "返回统计设备编码下设备数")
    @ApiParam(name = "request", notes = "请求参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> countDevicesByDeviceCode(SupplyDeviceCodeRequest request);

    /**
     * @param ("必填")consumer,time,version
     * @param ("选填")pkgStatus
     * @return RpcResponse<Integer>
     * @Title countDevicesByPackageStatus
     * @Description 统计设备激活或者未激活数
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @ApiMethod("统计设备激活或者未激活数")
    @ApiResponse(value = "返回设备激活或者未激活数")
    @ApiParam(name = "request", notes = "请求参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> countDevicesByPackageStatus(SupplyDeviceFileRequest request);

    /**
     * @param ("必填")consumer,time,version
     * @param ("选填")
     * @return RpcResponse<Integer>
     * @Title countDevices
     * @Description 统计设备总数
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @ApiMethod("统计设备总数")
    @ApiResponse(value = "返回统计设备总数")
    @ApiParam(name = "request", notes = "请求参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> countDevices(SupplyDeviceFileRequest request);

    /**
     * @param ("必填")consumer,time,version
     * @param ("选填")sn(imei)
     * @return RpcResponse<DeviceFileSv>
     * @Title getDeviceByDeviceSn
     * @Description 根据设备sn获取设备信息
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @ApiMethod("根据设备sn获取设备信息")
    @ApiResponse(value = "返回设备清单对象")
    @ApiParam(name = "request", notes = "请求参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<DeviceFileSv> getDeviceByDeviceSn(SupplyDeviceFileRequest request);


    /**
     * @param ("必填")consumer,time,version
     * @param ("必填")id
     * @return RpcResponse<DeviceFileSv>
     * @Title getDeviceByDeviceId
     * @Description 根据设备id获取设备信息
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
//    @ApiMethod("根据设备id获取设备信息")
//    @ApiResponse(value = "返回设备清单对象")
//    @ApiParam(name = "request", notes = "请求参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
//    RpcResponse<DeviceFileSv> getDeviceByDeviceId(SupplyDeviceFileRequest request);


    /**
     * @param ("必填")consumer,time,version
     * @param ("选填")imsi和companyId
     * @return RpcResponse<DeviceFileSv>
     * @Title getDeviceByImsi
     * @Description 根据Imsi获取设备信息
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @ApiMethod("根据Imsi获取设备信息")
    @ApiResponse(value = "返回设备清单对象")
    @ApiParam(name = "request", notes = "请求参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<DeviceFileSv> getDeviceByImsi(SupplyDeviceFileRequest request);


    /**
     * @param ("必填")consumer,time,version
     * @param ("选填")sn
     * @return RpcResponse<SystemPlatEnum>
     * @Title getSystemPlatByDeviceSn
     * @Description 根据设备sn确定平台
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @ApiMethod("根据设备sn确定平台")
    @ApiResponse(value = "返回设备所在系统平台枚举")
    @ApiParam(name = "request", notes = "请求参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<SystemPlatEnum> getSystemPlatByDeviceSn(SupplyDeviceFileRequest request);


    /**
     * @param ("必填")consumer,time,version
     * @param ("选填")id
     * @return RpcResponse<SystemPlatEnum>
     * @Title getSystemPlatByDeviceId
     * @Description 根据设备ID确定平台(临时)
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
/*    @ApiMethod("根据设备sn确定平台")
    @ApiResponse(value = "返回设备所在系统平台枚举")
    @ApiParam(name = "request", notes = "请求参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<SystemPlatEnum> getSystemPlatByDeviceId(SupplyDeviceFileRequest request);*/

    /**
     * @param ("必填")consumer,time,version
     * @param ("选填")imsi和companyId
     * @return RpcResponse<SystemPlatEnum>
     * @Title getSystemPlatByImsi
     * @Description 根据imsi确定设备所在平台
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @ApiMethod("根据imsi确定设备所在平台")
    @ApiResponse(value = "返回设备所在系统平台枚举")
    @ApiParam(name = "request", notes = "请求参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<SystemPlatEnum> getSystemPlatByImsi(SupplyDeviceFileRequest request);

    /**
     * @param ("必填")consumer,time,version
     * @param ("选填")vehicleFlag和companyId
     * @return RpcResponse<DeviceFileSv>
     * @Title getDeviceFileByVehicleFlag
     * @Description 查看当前车辆所用设备信息
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @ApiMethod("查看当前车辆所用设备信息")
    @ApiResponse(value = "返回当前车辆所用设备信息")
    @ApiParam(name = "request", notes = "请求参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<DeviceFileSv> getDeviceFileByVehicleFlag(SupplyDeviceFileRequest request);

    /**
     * @param ("必填")consumer,time,version
     * @param ("必填")vehicleFlag,companyId,sn,flagType
     * @return RpcResponse<Integer> 0:成功 其他:失败
     * @Title bindDeviceFileToVehicleFlag
     * @Description 绑定设备到车辆
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @ApiMethod("绑定设备到车辆")
    @ApiResponse(value = "返回绑定设备到车辆结果")
    @ApiParam(name = "request", notes = "请求参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> bindDeviceFileToVehicleFlag(SupplyDeviceFileRequest request);

    /**
     * @param ("必填")consumer,time,version
     * @param ("必填")vehicleFlag,companyId,sn,flagType
     * @return RpcResponse<Integer> 0:成功 其他:失败
     * @Title unBindDeviceFileToVehicleFlag
     * @Description 解绑设备到车辆
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @ApiMethod("解绑设备到车辆")
    @ApiResponse(value = "返回绑解绑设备到车辆结果")
    @ApiParam(name = "request", notes = "请求参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> unBindDeviceFileToVehicleFlag(SupplyDeviceFileRequest request);

    /**
     * @param ("必填")consumer,time,version
     * @param ("必填")imsi,companyId,sn
     * @return RpcResponse<Integer> 0:成功 其他:失败
     * @Title bindCardToDeviceFile
     * @Description 绑定卡到设备
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @ApiMethod("绑定卡到设备")
    @ApiResponse(value = "返回绑定卡到设备结果")
    @ApiParam(name = "request", notes = "请求参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> bindCardToDeviceFile(SupplyDeviceFileRequest request);

    /**
     * @param ("必填")consumer,time,version
     * @param ("必填")imsi,companyId,sn
     * @return RpcResponse<Integer> 0:成功 其他:失败
     * @Title unBindCardToDeviceFile
     * @Description 解绑卡到设备
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @ApiMethod("解绑卡到设备")
    @ApiResponse(value = "返回解绑卡到设备结果")
    @ApiParam(name = "request", notes = "请求参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> unBindCardToDeviceFile(SupplyDeviceFileRequest request);

    /**
     * @param ("必填")consumer,time,version
     * @param ("必填")userFlag,companyId,userFlagType
     * @return RpcResponse<List   <   DeviceFileSv>>
     * @Title listDeviceFileByActivePackageUserFlag
     * @Description 查看用户当前所激活的设备信息列表
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @ApiMethod("查看用户当前所激活的设备信息列表")
    @ApiResponse(value = "返回户当前所激活的设备信息列表")
    @ApiParam(name = "request", notes = "请求参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<List<DeviceFileSv>> listDeviceFileByActivePackageUserFlag(SupplyDeviceFileRequest request);

    /**
     * @param ("必填")consumer,time,version
     * @param ("必填")userFlag,companyId,userFlagType
     * @return RpcResponse<List   <   DeviceFileSv>>
     * @Title listDeviceFileByBindingUserFlag
     * @Description 查看用户当前所绑定的设备信息列表
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @ApiMethod("查看用户当前所绑定的设备信息列表")
    @ApiResponse(value = "返回用户当前所绑定的设备信息列表")
    @ApiParam(name = "request", notes = "请求参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<List<DeviceFileSv>> listDeviceFileByBindingUserFlag(SupplyDeviceFileRequest request);

    /**
     * @param ("必填")consumer,time,version
     * @param ("必填")userFlag,companyId,userFlagType
     * @return RpcResponse<Integer>
     * @Title countDeviceFileByActivePackageUserFlag
     * @Description 统计用户所激活的设备数量
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @ApiMethod("统计用户所激活的设备数量")
    @ApiResponse(value = "返回统计用户所激活的设备数量")
    @ApiParam(name = "request", notes = "请求参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> countDeviceFileByActivePackageUserFlag(SupplyDeviceFileRequest request);

    /**
     * @param ("必填")consumer,time,version
     * @param ("必填")userFlag,companyId,userFlagType
     * @return RpcResponse<Integer>
     * @Title countDeviceFileByBindingUserFlag
     * @Description 统计用户所绑定的设备数量
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @ApiMethod("统计用户所绑定的设备数量")
    @ApiResponse(value = "返回统计用户所绑定的设备数量")
    @ApiParam(name = "request", notes = "请求参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> countDeviceFileByBindingUserFlag(SupplyDeviceFileRequest request);

    /**
     * @param ("必填")consumer,time,version
     * @param ("必填")userFlag,companyId,userFlagType,sn,imsi ("选填"外部卡外部设备)deviceCode,packageId
     * @return RpcResponse<Integer> 0:成功 其他:失败
     * @Title activeDevicePackage
     * @Description 用户激活设备(对应商品激活信息) 不做任何逻辑判断 所有逻辑依赖外部逻辑判断，这里只做记录和记录修改
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @ApiMethod("用户激活设备(对应商品激活信息)不带逻辑判断")
    @ApiResponse(value = "返回用户激活设备(对应商品激活信息)结果")
    @ApiParam(name = "request", notes = "请求参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> activeDevicePackage(SupplyDeviceFileRequest request);
 
    /**
     * @param ("必填")consumer,time,version
     * @param ("必填")userFlag,companyId,userFlagType,sn
     * @return RpcResponse<Integer> 0:成功 其他:失败
     * @Title bindUserToDeviceFile
     * @Description 绑定用户到设备
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @ApiMethod("绑定用户到设备")
    @ApiResponse(value = "返回用户绑定设备结果")
    @ApiParam(name = "request", notes = "请求参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> bindUserToDeviceFile(SupplyDeviceFileRequest request);

    /**
     * @param ("必填")consumer,time,version
     * @param ("必填")userFlag,companyId,userFlagType,sn
     * @return RpcResponse<Integer> 0:成功 其他:失败
     * @Title unBindUserToDeviceFile
     * @Description 解除绑定用户到设备
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @ApiMethod("解除绑定用户到设备")
    @ApiResponse(value = "返回用户解绑定设备结果")
    @ApiParam(name = "request", notes = "请求参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> unBindUserToDeviceFile(SupplyDeviceFileRequest request);

    /**
     * @param ("必填")consumer,time,version
     * @param ("必填")sn或者id,manuFactureCode
     * @return RpcResponse<Integer> 0:成功 其他:失败
     * @Title updateDeviceFileManuFactureCode
     * @Description 修改设备的厂商码
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @ApiMethod("修改设备的厂商码")
    @ApiResponse(value = "返回修改设备的厂商码结果")
    @ApiParam(name = "request", notes = "请求参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> updateDeviceFileManuFactureCode(SupplyDeviceFileRequest request);

    /**
     * @param ("必填")consumer,time,version
     * @param ("必填")sn或者id,softVersion
     * @return RpcResponse<Integer> 0:成功 其他:失败
     * @Title updateDeviceFileSoftVersion
     * @Description 修改设备的软件版本号
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @ApiMethod("修改设备的软件版本号")
    @ApiResponse(value = "返回修改设备的软件版本号结果")
    @ApiParam(name = "request", notes = "请求参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> updateDeviceFileSoftVersion(SupplyDeviceFileRequest request);


    /**
     * @param ("必填")consumer,time,version
     * @param ("必填")sn,
     * @return RpcResponse<List   <   DeviceUpdateRecordSv>>
     * @Title listDeviceUpdateRecordByVehicleFlag
     * @Description 查看设备更换车辆记录表
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @ApiMethod("查看车辆更换设备记录列表")
    @ApiResponse(value = "返回查看车辆更换设备记录列表")
    @ApiParam(name = "request", notes = "请求参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<List<DeviceUpdateRecordSv>> listDeviceUpdateRecordByVehicleFlag(SupplyDeviceFileRequest request);

    /**
     * @param ("必填")consumer,time,version
     * @param "必填")sn,
     * @return RpcResponse<List   <   DeviceUpdateRecordSv>>
     * @Title listDeviceUpdateRecordByImsi
     * @Description 查看设备更换卡记录表
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @ApiMethod("查看设备更换卡记录表")
    @ApiResponse(value = "返回查看卡更换设备记录列表")
    @ApiParam(name = "request", notes = "请求参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<List<DeviceUpdateRecordSv>> listDeviceUpdateRecordByImsi(SupplyDeviceFileRequest request);

    /**
     * @param ("必填")consumer,time,version
     * @param ("必填")sn,
     * @return RpcResponse<List   <   DeviceUpdateRecordSv>>
     * @Title listDeviceUpdateRecordByActiveUserFlag
     * @Description 查看设备的用户激活记录表
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @ApiMethod("查看用户绑定设备记录列表")
    @ApiResponse(value = "返回查看用户绑定设备记录列表")
    @ApiParam(name = "request", notes = "请求参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<List<DeviceUpdateRecordSv>> listDeviceUpdateRecordByActiveUserFlag(SupplyDeviceFileRequest request);

    /**
     * @param ("必填")consumer,time,version
     * @param ("必填")sn,
     * @return RpcResponse<List   <   DeviceUpdateRecordSv>>
     * @Title listDeviceUpdateRecordByBindUserFlag
     * @Description 查看设备的用户绑定记录表
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @ApiMethod("查看用户激活设备记录列表")
    @ApiResponse(value = "返回查看用户激活设备记录列表")
    @ApiParam(name = "request", notes = "请求参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<List<DeviceUpdateRecordSv>> listDeviceUpdateRecordByBindUserFlag(SupplyDeviceFileRequest request);


    /**
     * @param ("必填")deviceCode
     * @return 0:成功 其他:失败
     * @Title addAndUpdateDeviceCode
     * @Description 设备分类添加和修改
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
    @ApiMethod("设备分类添加和修改")
    @ApiResponse(value = "返回设备分类添加和修改结果")
    @ApiParam(name = "request", notes = "请求参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> addAndUpdateDeviceCode(DeviceCode deviceCode);
    
    /**
     * @param ("必填")consumer,time,version
     * @param ("必填")sn,
     * @return RpcResponse<DeviceImeiStock>
     * @Title getDeviceImeiStockBySn
     * @Description 查看imei库存信息(提供给流量卡平台调用)
     * @author QL.LiuQuan
     * @Time 2018-10-14
     */
     RpcResponse<DeviceImeiStock> getDeviceImeiStockBySn(SupplyDeviceFileRequest request);

    /**
     *
     * @Title: saveDeviceFileUnstock
     * @Description: 保存未入库的设备信息
     * @param @param deviceCode
     * @return RpcResponse<Integer>
     */
    @ApiMethod("保存未入库的设备信息")
    @ApiResponse(value = "返回保存结果")
    @ApiParam(name = "deviceCode",notes = "保存未入库的设备信息",required = true,dataType = ApiParam.DataTypeEnum.STRING)
    RpcResponse<Integer> saveDeviceFileUnstock(SupplyDeviceFileRequest request);
       
}
