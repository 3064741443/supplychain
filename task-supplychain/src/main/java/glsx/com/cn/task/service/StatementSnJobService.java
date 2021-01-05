package glsx.com.cn.task.service;

public interface StatementSnJobService {
	
	//硬件设备汇总
	void doSyncStatementSnDispatch();
	//配件或者服务汇总
	void doSyncStatementSnDirect();
}
