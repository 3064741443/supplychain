package cn.com.glsx.supplychain.remote;

import cn.com.glsx.framework.core.annotation.ApiMethod;
import cn.com.glsx.framework.core.annotation.ApiParam;
import cn.com.glsx.framework.core.annotation.ApiResponse;
import cn.com.glsx.framework.core.annotation.ApiService;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.model.AttribInfo;
import cn.com.glsx.supplychain.model.TaskRecordExport;

/**
 * @Title: TaskExportRemote.java
 * @Description: 供应链导出任务接口
 * @author ql
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@ApiService(value = "供应链导出任务接口",owner = "supplychain",version = "1.0.0")
public interface TaskExportRemote {

	@ApiMethod("获取导出任务列表")
	@ApiResponse(value = "返回列表")
	@ApiParam(name = "userInfo",notes = "条件查询",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<RpcPagination<TaskRecordExport>> pageTaskRecordReport(RpcPagination<TaskRecordExport> pagination);
	
	@ApiMethod("获取配置列表")
	@ApiResponse(value = "返回列表")
	@ApiParam(name = "userInfo",notes = "条件查询",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<RpcPagination<AttribInfo>> pageAttribInfo(RpcPagination<AttribInfo> pagination);
	
	@ApiMethod("添加修改配置表")
	@ApiResponse(value = "返回列表")
	@ApiParam(name = "userInfo",notes = "条件查询",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<Integer> setTaskRecordExport(TaskRecordExport record);
}
