package cn.com.glsx.supplychain.service.am;

import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.common.Constants;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.enums.StatementCollectionServiceTypeEnum;
import cn.com.glsx.supplychain.mapper.am.StatementCollectionSplitMapper;
import cn.com.glsx.supplychain.model.am.StatementCollectionSplit;
import cn.com.glsx.supplychain.util.SupplychainUtils;
import cn.com.glsx.supplychain.vo.StatementCollectSplitExcelVo;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName StatementCollectionSplitService
 * @Author leiming
 * @Param
 * @Date 2019/9/17 10:00
 * @Version 1.0
 **/
@Service
@Transactional
public class StatementCollectionSplitService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StatementCollectionSplitMapper statementCollectionSplitMapper;

    /**
     * 获取对账单-广联采集(拆分)分页列表
     *
     * @param statementCollectionSplit 对账单-广联采集(拆分)对象
     * @return
     */
    public Page<StatementCollectionSplit> listStatementCollectionSplit(int pageNum, int pageSize, StatementCollectionSplit statementCollectionSplit){
        logger.info("pageNum为：{}, pageSize为：{}, 条件查询参数为 ：{}。", pageNum, pageSize, statementCollectionSplit);
        Page<StatementCollectionSplit> result;
        PageHelper.startPage(pageNum, pageSize);
        result = statementCollectionSplitMapper.listStatementCollectionSplit(statementCollectionSplit);
        return result;
    }

    /**
     * 获取对账单-广联采集(拆分)Info
     *
     * @param statementCollectionSplit 对账单-广联采集(拆分)对象
     * @return
     */
    public StatementCollectionSplit getStatementCollectionSplitInfo(StatementCollectionSplit statementCollectionSplit) {
        logger.info("查询对账单-广联采集(拆分)列表入参:{}", statementCollectionSplit);
        StatementCollectionSplit result;
        result = statementCollectionSplitMapper.selectOne(statementCollectionSplit);
        return result;
    }

    /**
     * 获取对账单-广联采集(拆分)List
     *
     * @param statementCollectionSplit 对账单-广联采集(拆分)对象
     * @return
     */
    public List<StatementCollectionSplit> getStatementCollectionSplitList(StatementCollectionSplit statementCollectionSplit) {
        logger.info("查询对账单-广联采集(拆分)列表List入参:{}", statementCollectionSplit);
        List<StatementCollectionSplit> result;
        result = statementCollectionSplitMapper.select(statementCollectionSplit);
        return result;
    }

    /**
     * 新增对账单-广联采集(拆分)信息
     *
     * @param statementCollectionSplit 对账单-广联采集(拆分)对象
     * @return 返回成功条数
     */
    public Integer add(StatementCollectionSplit statementCollectionSplit){
        logger.info("新增对账单-广联采集(拆分)入参:{}", statementCollectionSplit);
        Integer result;
        result = statementCollectionSplitMapper.add(statementCollectionSplit);
        return result;
    }

    /**
     * 修改对账单-广联采集(拆分)信息
     *
     * @param statementCollectionSplit 对账单-广联采集(拆分)对象
     * @return 返回成功条数
     */
    public Integer updateById(StatementCollectionSplit statementCollectionSplit){
        logger.info("修改对账单-广联采集(拆分)入参:{}", statementCollectionSplit);
        Integer result;
        result = statementCollectionSplitMapper.updateById(statementCollectionSplit);
        return result;
    }


    /**
     * 导出对账单-广联采集(拆分)信息
     *
     * @param statementCollectionSplit 对账单-广联采集(拆分)对象
     * @return 返回成功条数
     */
    public List<StatementCollectSplitExcelVo> exportStatementCollectionSplitExit(StatementCollectionSplit statementCollectionSplit){
        logger.info("导出对账单-广联采集(拆分)入参:{}", statementCollectionSplit);
        if (StringUtils.isEmpty(statementCollectionSplit)) {
            logger.error("StatementCollectionSplitService::exportStatementCollectionSplitExit 参数不能为空");
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        List<StatementCollectionSplit> statementCollectionSplits = null;
        List<StatementCollectSplitExcelVo> statementCollectSplitExcelVoList = new ArrayList<>();
        statementCollectionSplits = statementCollectionSplitMapper.exportStatementCollectionSplitExit(statementCollectionSplit);
        if(statementCollectionSplits.size() > 0){
            for (StatementCollectionSplit list: statementCollectionSplits){
                StatementCollectSplitExcelVo statementCollectSplitExcelVo = new StatementCollectSplitExcelVo();
                statementCollectSplitExcelVo.setBillTypeCode(list.getBillTypeCode());
                statementCollectSplitExcelVo.setBillTypeName(list.getBillTypeName());
                statementCollectSplitExcelVo.setBillNumber(list.getBillNumber());
                statementCollectSplitExcelVo.setNumber(SupplychainUtils.getStatementNumber(Constants.NUMBER_HEAD_COLLECTION_SPLIT, list.getId().intValue()));

                Date newTime = list.getTime();//转换日期
                String strTime = formatter.format(newTime);
                statementCollectSplitExcelVo.setTime(strTime);
                statementCollectSplitExcelVo.setSalesCode(list.getSalesCode());
                statementCollectSplitExcelVo.setSalesName(list.getSalesName());
                statementCollectSplitExcelVo.setCustomerCode(list.getCustomerCode());
                statementCollectSplitExcelVo.setCustomerName(list.getCustomerName());
                statementCollectSplitExcelVo.setSaleGroupCode(list.getSaleGroupCode());
                statementCollectSplitExcelVo.setSaleGroupName(list.getSaleGroupName());
                statementCollectSplitExcelVo.setStatementCurrencyCode(list.getStatementCurrencyCode());
                statementCollectSplitExcelVo.setStatementCurrencyName(list.getStatementCurrencyName());
                statementCollectSplitExcelVo.setMaterialCode(list.getMaterialCode());
                statementCollectSplitExcelVo.setMaterialName(list.getMaterialName());
                statementCollectSplitExcelVo.setSalesUnitCode(list.getSalesUnitCode());
                statementCollectSplitExcelVo.setSalesUnitName(list.getSalesUnitName());
                statementCollectSplitExcelVo.setSalesQuantity(list.getSalesQuantity());
                statementCollectSplitExcelVo.setPrice(list.getPrice());
                statementCollectSplitExcelVo.setGift(list.getGift());
                statementCollectSplitExcelVo.setTaxRate(list.getTaxRate());

                Date newTakeGoods = list.getTakeGoodsDate();//转换要货日期
                if(newTakeGoods != null){
                    String strTakeGoods = formatter.format(newTakeGoods);
                    statementCollectSplitExcelVo.setTakeGoodsDate(strTakeGoods);
                }
                statementCollectSplitExcelVo.setStatementOrganizeCode(list.getStatementOrganizeCode());
                statementCollectSplitExcelVo.setStatementOrganizeName(list.getStatementOrganizeName());
                statementCollectSplitExcelVo.setReserveType(list.getReserveType());
                statementCollectSplitExcelVo.setWarehouseCode(list.getWarehouseCode());
                statementCollectSplitExcelVo.setWarehouseName(list.getWarehouseName());
                statementCollectSplitExcelVo.setQuantity(list.getQuantity());

                statementCollectSplitExcelVoList.add(statementCollectSplitExcelVo);
            }
        }
        logger.info("exportStatementCollectionSplitExit return statementCollectSplitExcelVoList.size()" + statementCollectSplitExcelVoList.size());
        return statementCollectSplitExcelVoList;
    }

}
