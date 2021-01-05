package cn.com.glsx.supplychain.remote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.framework.core.util.RpcAssert;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.model.AttribInfo;
import cn.com.glsx.supplychain.model.TaskRecordExport;
import cn.com.glsx.supplychain.service.AttribInfoService;
import cn.com.glsx.supplychain.service.TaskRecordReportService;

import com.alibaba.dubbo.config.annotation.Service;

@Component("TaskExportRemote")
@Service(version = "1.0.0", protocol = "dubbo", timeout = 10000 )
public class TaskExportRemoteImpl implements TaskExportRemote{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private TaskRecordReportService taskService;
	@Autowired
	private AttribInfoService attribInfoService;

	@Override
	public RpcResponse<RpcPagination<TaskRecordExport>> pageTaskRecordReport(
			RpcPagination<TaskRecordExport> pagination) {
		
		logger.info("pageTaskRecordReport pagination:{}",pagination);
		try
		{
			RpcAssert.assertNotNull(pagination, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination must not be null");
			return RpcResponse.success(RpcPagination.copyPage(taskService.pageTaskRecordExport(pagination)));
		}
		catch(RpcServiceException e)
		{
			return RpcResponse.error(e);
		}
	}

	@Override
	public RpcResponse<RpcPagination<AttribInfo>> pageAttribInfo(
			RpcPagination<AttribInfo> pagination) {
		
		logger.info("pageAttribInfo pagination:{}",pagination);
		try
		{
			RpcAssert.assertNotNull(pagination, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination must not be null");
			return RpcResponse.success(RpcPagination.copyPage(attribInfoService.pageAttribInfo(pagination)));
		}
		catch(RpcServiceException e)
		{
			return RpcResponse.error(e);
		}
	}

	@Override
	public RpcResponse<Integer> setTaskRecordExport(TaskRecordExport record) {
		
		logger.info("setTaskRecordExport record:{}",record);
		try
		{
			RpcAssert.assertNotNull(record, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getOperatorFlag(), ErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getOperatorFlag() must not be null");
			return RpcResponse.success(taskService.setTaskRecordExport(record));
		}
		catch(RpcServiceException e)
		{
			return RpcResponse.error(e);
		}
	}

}
