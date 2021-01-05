package cn.com.glsx.supplychain.controller;

import org.oreframework.boot.security.UserInfoHolder;
import org.oreframework.boot.security.cas.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.model.AttribInfo;
import cn.com.glsx.supplychain.model.TaskRecordExport;
import cn.com.glsx.supplychain.remote.TaskExportRemote;

/**
 * 导出任务
 * 
 * @author 
 */
@RestController
@RequestMapping("taskReport")
public class TaskRecordReportController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private TaskExportRemote taskExportRemote;
	@Autowired
	private UserInfoHolder userInfoHolder;
	
	@RequestMapping("pageTaskRecordReport")
	public ResultEntity<RpcPagination<TaskRecordExport>> pageTaskRecordReport(@RequestBody RpcPagination<TaskRecordExport> pagination)
	{
		@SuppressWarnings({ "static-access", "deprecation" })
		User user = userInfoHolder.getUser();
		String userName = "44187846";
		if(user != null)
		{
			userName = user.getRealname();
		}
		logger.info("pageTaskRecordReport pagination:{}",pagination);
		if(!StringUtils.isEmpty(pagination))
		{
			if(pagination.getCondition() == null)
			{
				pagination.setCondition(new TaskRecordExport());
			}
			pagination.getCondition().setCreatedBy(userName);
		}
		return ResultEntity.result(taskExportRemote.pageTaskRecordReport(pagination));
	}
	
	@RequestMapping("pageAttribInfo")
	public ResultEntity<RpcPagination<AttribInfo>> pageAttribInfo(RpcPagination<AttribInfo> pagination)
	{
		logger.info("pageAttribInfo pagination:{}",pagination);
		if(pagination.getCondition() == null)
		{
			pagination.setCondition(new AttribInfo());
		}
		pagination.getCondition().setType(12);
		pagination.setPageSize(1000);
		RpcResponse<RpcPagination<AttribInfo>> rsp = taskExportRemote.pageAttribInfo(pagination);
		return ResultEntity.result(taskExportRemote.pageAttribInfo(pagination));
	}
	
}
