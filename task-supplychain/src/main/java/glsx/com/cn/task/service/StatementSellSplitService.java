package glsx.com.cn.task.service;

public interface StatementSellSplitService {

	//微信拆分逻辑
	void doStatementSellRenewSplit();
	//支付宝拆分逻辑
	void doStatementSellRzfbSplit();
	//广联无忧拆分逻辑
	void doStatementSellGlwySplit();
	//驾宝无忧拆分逻辑
	void doStatementSellJbwySplit();
}
