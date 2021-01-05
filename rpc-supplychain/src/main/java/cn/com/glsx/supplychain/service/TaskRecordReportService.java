package cn.com.glsx.supplychain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.mapper.TaskRecordExportMapper;
import cn.com.glsx.supplychain.model.TaskRecordExport;

@Service
public class TaskRecordReportService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	TaskRecordExportMapper taskMapper;
	
	public Page<TaskRecordExport> pageTaskRecordExport(RpcPagination<TaskRecordExport> pagination) throws RpcServiceException
	{
		try
		{
			PageHelper.startPage(pagination.getPageNum(), pagination.getPageSize());
			return taskMapper.pageTaskRecordExport(pagination.getCondition());
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(),e);
			throw new RpcServiceException(e.getMessage());
		}	
	}
	
	public Integer setTaskRecordExport(TaskRecordExport record) throws RpcServiceException
	{
		try
		{
			TaskRecordExport condition = new TaskRecordExport();
			condition.setOperatorFlag(record.getOperatorFlag());
			TaskRecordExport dbRecord = taskMapper.selectOne(condition);
			if(StringUtils.isEmpty(dbRecord))
			{
				return taskMapper.insertSelective(record);
			}
			record.setId(dbRecord.getId());
			return taskMapper.updateByPrimaryKeySelective(record);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(),e);
			throw new RpcServiceException(e.getMessage());
		}
	}
}
