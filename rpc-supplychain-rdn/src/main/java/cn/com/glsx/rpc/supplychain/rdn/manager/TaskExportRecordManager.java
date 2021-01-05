package cn.com.glsx.rpc.supplychain.rdn.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.jxc.dto.JXCMTBsMerchantOrderSignSuplyDTO;
import cn.com.glsx.supplychain.jxc.remote.JxcOrderRemote;
import cn.com.glsx.supplychain.model.TaskRecordExport;
import cn.com.glsx.supplychain.remote.TaskExportRemote;

@Service
public class TaskExportRecordManager {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private TaskExportRemote taskExportRemote;
	@Autowired
	private JxcOrderRemote jxcOrderRemote;
	
	public Integer setTaskExportRecord(TaskRecordExport record)
	{
		RpcResponse<Integer> rsp = taskExportRemote.setTaskRecordExport(record);
		return rsp.getResult();
	}
	
	public Integer writeOrderSignRecord(JXCMTBsMerchantOrderSignSuplyDTO record){
		
		RpcResponse<Integer> rsp = jxcOrderRemote.genBillNumberRecord(record);
		return rsp.getResult();
	}
}
