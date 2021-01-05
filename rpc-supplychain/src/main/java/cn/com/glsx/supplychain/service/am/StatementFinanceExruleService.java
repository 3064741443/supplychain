package cn.com.glsx.supplychain.service.am;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import cn.com.glsx.supplychain.mapper.am.StatementFinanceExruleMapper;
import cn.com.glsx.supplychain.model.am.StatementFinanceExrule;

@Service
public class StatementFinanceExruleService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
    private StatementFinanceExruleMapper statementFinanceExruleMapper;
	
	public Map<String,StatementFinanceExrule> getStatementFinanceExruleMap(StatementFinanceExrule record){
		Map<String,StatementFinanceExrule> mapResult = new HashMap<>();
		Example example = new Example(StatementFinanceExrule.class);
		example.createCriteria().andEqualTo("deletedFlag",record.getDeletedFlag());
		List<StatementFinanceExrule> listRules = statementFinanceExruleMapper.selectByExample(example);
		if(null != listRules){
			for(StatementFinanceExrule rule:listRules){
				mapResult.put(rule.getSnHeard(), rule);
			}
		}
		return mapResult;
	} 
}
