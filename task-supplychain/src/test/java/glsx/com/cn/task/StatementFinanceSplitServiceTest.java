package glsx.com.cn.task;

import glsx.com.cn.task.service.StatementFinanceSplitService;

import org.springframework.beans.factory.annotation.Autowired;

public class StatementFinanceSplitServiceTest extends BaseTest{

	@Autowired
	StatementFinanceSplitService statementFinanceSplitService;
	
	@org.junit.Test
	public void testStatementFinanceSplit(){
		
		statementFinanceSplitService.statementFinanceSplit();
		
	}
}
