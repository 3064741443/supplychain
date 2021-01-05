package cn.com.glsx.supplychain.service.am;

import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.mapper.am.StatementFinanceCombileMapper;
import cn.com.glsx.supplychain.mapper.am.StatementFinanceMapper;
import cn.com.glsx.supplychain.mapper.am.StatementFinanceSplitMapper;
import cn.com.glsx.supplychain.mapper.am.StatementSnMapper;
import cn.com.glsx.supplychain.model.am.StatementCollection;
import cn.com.glsx.supplychain.model.am.StatementFinance;
import cn.com.glsx.supplychain.model.am.StatementFinanceCombile;
import cn.com.glsx.supplychain.model.am.StatementFinanceSplit;
import cn.com.glsx.supplychain.model.am.StatementSn;
import cn.com.glsx.supplychain.util.StringUtil;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @ClassName StatementFinanceService
 * @Author leiming
 * @Param
 * @Date 2019/9/17 10:02
 * @Version 1.0
 **/
@Service
@Transactional
public class StatementFinanceService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StatementFinanceMapper statementFinanceMapper;
    @Autowired
    private StatementFinanceSplitMapper statementFinanceSplitMapper;   
    @Autowired
    private StatementFinanceCombileMapper statementFinanceCombileMapper;
    @Autowired
    private StatementSnMapper statementSnMapper;

    /**
     * 获取对账单-金融风控分页列表
     *
     * @param statementFinance 对账单-金融风控对象
     * @return
     */
    public Page<StatementFinance> listStatementFinance(int pageNum, int pageSize, StatementFinance statementFinance){
        logger.info("pageNum为：{}, pageSize为：{}, 条件查询参数为 ：{}。", pageNum, pageSize, statementFinance);
        Page<StatementFinance> result;
        PageHelper.startPage(pageNum, pageSize);
        result = statementFinanceMapper.listStatementFinance(statementFinance);       
        return result;
    }

    /**
     * 获取对账单-金融风控Info
     *
     * @param statementFinance 对账单-金融风控对象
     * @return
     */
    public StatementFinance getStatementFinanceInfo(StatementFinance statementFinance) {
        logger.info("查询对账单-金融风控列表入参:{}", statementFinance);
        StatementFinance result;
        result = statementFinanceMapper.selectOne(statementFinance);
        return result;
    }

    /**
     * 获取对账单-金融风控List
     *
     * @param statementFinance 对账单-金融风控对象
     * @return
     */
    public List<StatementFinance> getStatementFinanceList(StatementFinance statementFinance) {
        logger.info("查询对账单-金融风控列表List入参:{}", statementFinance);
        List<StatementFinance> result;
        result = statementFinanceMapper.select(statementFinance);
        return result;
    }

    /**
     * 查询对账单-金融风控info
     *
     * @return 返回对账单-金融风控info
     */
    public StatementFinance getStatementFinanceByid(Long id){
        logger.info("查询对账单-金融风控入参:{}", id);
        StatementFinance result;
        result = statementFinanceMapper.getStatementFinanceByid(id);
        if(!StringUtil.isEmpty(result)){
            StatementFinanceSplit statementFinanceSplit = new StatementFinanceSplit();
            statementFinanceSplit.setFinanceId(result.getId());
            List<StatementFinanceSplit>statementFinanceSplits = statementFinanceSplitMapper.select(statementFinanceSplit);
            if(statementFinanceSplits != null && statementFinanceSplits.size() > 0){
                result.setStatementFinanceSplitList(statementFinanceSplits);
            }
        }
        return result;
    }

    /**
     * 新增对账单-金融风控信息
     *
     * @param statementFinance 对账单-金融风控对象
     * @return 返回成功条数
     */
    public Integer add(StatementFinance statementFinance){
        logger.info("新增对账单-金融风控入参:{}", statementFinance);
        Integer result;
        result = statementFinanceMapper.add(statementFinance);
        return result;
    }

    /**
     * 修改对账单-金融风控信息
     *
     * @param statementFinance 对账单-金融风控对象
     * @return 返回成功条数
     */
    public Integer updateById(StatementFinance statementFinance){
        logger.info("修改对账单-金融风控入参:{}", statementFinance);
        Integer result;
        result = statementFinanceMapper.updateById(statementFinance);
        return result;
    }

    /**
     * 查询对账单-金融风控最大月份的一条数据
     *
     * @return 返回最大月份的一条数据
     */
    public StatementFinance getMaxDate(){
        logger.info("查询对账单-金融风控最大月份的一条数据");
        StatementFinance result;
        result = statementFinanceMapper.getMaxDate();
        return result;
    }

    /**
     * 批量新增对账单-金融风控(拆分)信息
     *
     * @param statementFinanceList 对账单-金融风控(拆分)对象
     * @return 返回成功条数
     */
    public Integer batchAddStatementFinance(List<StatementFinance> statementFinanceList){
        Integer result;
        logger.info("批量插入对账单-金融风控(拆分)入参:{}", statementFinanceList);
        result = statementFinanceMapper.batchAddStatementFinance(statementFinanceList);
        return result;
    }

    /**
     * 删除对账单-金融风控信息
     *
     * @param statementFinance 对账单-金融风控对象(创建时间)
     * @return 返回成功条数
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
    public Integer deleteStatementFinanceByDate(StatementFinance statementFinance) throws RpcServiceException{
        Integer result;
        try
        {
        	List<StatementFinance> listFinances = statementFinanceMapper.selectStatementFinanceByDate(statementFinance);
        	deleteStatementSn(listFinances);
        	
        	statementFinance.setUpdatedDate(new Date());
            result = statementFinanceMapper.deleteStatementFinanceByDate(statementFinance);
            logger.info("连带删除对账单-金融风控拆分数据入参:{}");
            StatementFinanceSplit statementFinanceSplit = new StatementFinanceSplit();
            statementFinanceSplit.setTime(statementFinance.getTime());
            statementFinanceSplit.setFinanceCustomerCode(statementFinance.getCustomerCode());
            statementFinanceSplit.setUpdatedDate(new Date());
            statementFinanceSplit.setUpdatedBy(statementFinance.getUpdatedBy());  
            statementFinanceSplit.setWorkType(statementFinance.getWorkType());
            statementFinanceSplitMapper.deleteStatementFinanceSplitByDate(statementFinanceSplit);
            StatementFinanceCombile statementFinanceCombile = new StatementFinanceCombile();
            statementFinanceCombile.setFinanceCustomerCode(statementFinance.getCustomerCode());
            statementFinanceCombile.setTime(statementFinance.getTime());
            statementFinanceCombile.setUpdatedDate(new Date());
            statementFinanceCombile.setUpdatedBy(statementFinance.getUpdatedBy());
            statementFinanceCombile.setWorkType(statementFinance.getWorkType());
            statementFinanceCombileMapper.deleteStatementFinanceCombileByDate(statementFinanceCombile);
        }catch(Exception e){
        	logger.error(e.getMessage(),e);
        	throw new RpcServiceException(e.getMessage());
        }
        return  result;
    }
    
    
    private void deleteStatementSn(List<StatementFinance> listFinances){
    	if(null == listFinances || listFinances.isEmpty()){
    		return;
    	}
    	List<String> listWorkOrders = listFinances.stream().map(StatementFinance::getWorkOrder).distinct().collect(Collectors.toList());
    	if(null == listWorkOrders || listWorkOrders.isEmpty()){
    		return;
    	}
    	Example example = new Example(StatementSn.class);
    	example.createCriteria().andIn("workOrder", listWorkOrders);
    	statementSnMapper.deleteByExample(example);
    }
}
