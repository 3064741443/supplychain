package cn.com.glsx.supplychain.service.am;

import cn.com.glsx.supplychain.mapper.am.StatementCollectionMapper;
import cn.com.glsx.supplychain.mapper.am.StatementCollectionSplitMapper;
import cn.com.glsx.supplychain.model.am.StatementCollection;
import cn.com.glsx.supplychain.model.am.StatementCollectionSplit;
import cn.com.glsx.supplychain.util.StringUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @ClassName StatementCollectionService
 * @Author admin
 * @Param
 * @Date 2019/9/17 9:59
 * @Version 1.0
 **/
@Service
@Transactional
public class StatementCollectionService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StatementCollectionMapper statementCollectionMapper;

    @Autowired
    private StatementCollectionSplitMapper statementCollectionSplitMapper;

    /**
     * 获取对账单-广汇采集分页列表
     *
     * @param statementCollection 对账单-广汇采集对象
     * @return
     */
    public Page<StatementCollection> listStatementCollection(int pageNum, int pageSize, StatementCollection statementCollection){
        logger.info("pageNum为：{}, pageSize为：{}, 条件查询参数为 ：{}。", pageNum, pageSize, statementCollection);
        Page<StatementCollection> result;
        PageHelper.startPage(pageNum, pageSize);
        result = statementCollectionMapper.listStatementCollection(statementCollection);
        return result;
    }

    /**
     * 获取对账单-广汇采集Info
     *
     * @param statementCollection 对账单-广汇采集对象
     * @return
     */
    public StatementCollection getStatementCollectionInfo(StatementCollection statementCollection) {
        logger.info("查询对账单-广汇采集列表入参:{}", statementCollection);
        StatementCollection result;
        result = statementCollectionMapper.selectOne(statementCollection);
        return result;
    }

    /**
     * 获取对账单-广汇采集List
     *
     * @param statementCollection 对账单-广汇采集对象
     * @return
     */
    public List<StatementCollection> getStatementCollectionList(StatementCollection statementCollection) {
        logger.info("查询对账单-广汇采集列表List入参:{}", statementCollection);
        List<StatementCollection> result;
        result = statementCollectionMapper.select(statementCollection);
        return result;
    }

    /**
     * 新增对账单-广汇采集信息
     *
     * @param statementCollection 对账单-广汇采集对象
     * @return 返回成功条数
     */
    public Integer add(StatementCollection statementCollection){
        logger.info("新增对账单-广汇采集入参:{}", statementCollection);
        Integer result;
        result = statementCollectionMapper.add(statementCollection);
        return result;
    }

    /**
     * 查询对账单-广汇采集info
     *
     * @return 返回对账单-广汇采集info
     */
    public StatementCollection getStatementCollectionByid(Long id){
        logger.info("查询对账单-广汇采集入参:{}", id);
        StatementCollection result;
        result = statementCollectionMapper.getStatementCollectionByid(id);
        if(!StringUtil.isEmpty(result)){
            StatementCollectionSplit statementCollectionSplit = new StatementCollectionSplit();
            statementCollectionSplit.setCollectionId(result.getId());
            List<StatementCollectionSplit>statementCollectionSplitList = statementCollectionSplitMapper.select(statementCollectionSplit);
            if(statementCollectionSplitList != null && statementCollectionSplitList.size() > 0){
                result.setStatementCollectionSplitList(statementCollectionSplitList);
            }
        }
        return result;
    }

    /**
     * 修改对账单-广汇采集信息
     *
     * @param statementCollection 对账单-广汇采集对象
     * @return 返回成功条数
     */
    public Integer updateById(StatementCollection statementCollection){
        logger.info("修改对账单-广汇采集入参:{}", statementCollection);
        Integer result;
        result = statementCollectionMapper.updateById(statementCollection);
        return result;
    }

    /**
     * 查询对账单-广汇采集最大月份的一条数据
     *
     * @return 返回最大月份的一条数据
     */
    public StatementCollection getMaxDate(){
        logger.info("查询对账单-广汇采集最大月份的一条数据");
        StatementCollection result;
        result = statementCollectionMapper.getMaxDate();
        return result;
    }

    /**
     * 批量新增对账单-广汇采集信息
     *
     * @param statementCollectionList 对账单-广汇采集对象
     * @return 返回成功条数
     */
    public Integer batchAddStatementCollection(List<StatementCollection> statementCollectionList){
        Integer result;
        logger.info("批量插入对账单-广汇采集入参:{}", statementCollectionList);
        result = statementCollectionMapper.batchAddStatementCollection(statementCollectionList);
        return result;
    }

    /**
     * 删除对账单-广汇采集信息
     *
     * @param statementCollection 对账单-广汇采集对象(创建时间)
     * @return 返回成功条数
     */
    public Integer deleteStatementCollectionByDate(StatementCollection statementCollection){
        Integer result;
        statementCollection.setUpdatedDate(new Date());
        result = statementCollectionMapper.deleteStatementCollectionByDate(statementCollection);
        logger.info("连带删除对账单-广汇采集拆分数据入参:{}", statementCollection);
        StatementCollectionSplit statementCollectionSplit = new StatementCollectionSplit();
        statementCollectionSplit.setTime(statementCollection.getTime());
        statementCollectionSplit.setCustomerCode(statementCollection.getCustomerCode());
        statementCollectionSplit.setServiceType(statementCollection.getServiceType());
        statementCollectionSplit.setUpdatedDate(new Date());
        statementCollectionSplit.setUpdatedBy(statementCollection.getUpdatedBy());
        statementCollectionSplitMapper.deleteStatementCollectionSplitByDate(statementCollectionSplit);
        return  result;
    }
}
