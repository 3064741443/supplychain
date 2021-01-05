package cn.com.glsx.supplychain.service.am;

import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.common.Constants;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.mapper.am.StatementFinanceSplitMapper;
import cn.com.glsx.supplychain.model.am.StatementFinanceSplit;
import cn.com.glsx.supplychain.util.SupplychainUtils;
import cn.com.glsx.supplychain.vo.StatementFinanceSplitExcelVo;

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
 * @ClassName StatementFinanceSplitService
 * @Author leiming
 * @Param
 * @Date 2019/9/17 10:03
 * @Version 1.0
 **/
@Service
@Transactional
public class StatementFinanceSplitService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StatementFinanceSplitMapper statementFinanceSplitMapper;

    /**
     * 获取对账单-金融风控(拆分)分页列表
     *
     * @param statementFinanceSplit 对账单-金融风控(拆分)对象
     * @return
     */
    public List<StatementFinanceSplit> listStatementFinanceSplit(int pageNum, int pageSize, StatementFinanceSplit statementFinanceSplit){
        logger.info("pageNum为：{}, pageSize为：{}, 条件查询参数为 ：{}。", pageNum, pageSize, statementFinanceSplit);
        List<StatementFinanceSplit> result;
        PageHelper.startPage(pageNum, pageSize);
        result = statementFinanceSplitMapper.listStatementFinanceSplit(statementFinanceSplit);
        return result;
    }

    /**
     * 获取对账单-金融风控(拆分)Info
     *
     * @param statementFinanceSplit 对账单-金融风控(拆分)对象
     * @return
     */
    public StatementFinanceSplit getStatementFinanceSplitInfo(StatementFinanceSplit statementFinanceSplit) {
        logger.info("查询对账单-金融风控(拆分)列表入参:{}", statementFinanceSplit);
        StatementFinanceSplit result;
        result = statementFinanceSplitMapper.selectOne(statementFinanceSplit);
        return result;
    }

    /**
     * 获取对账单-金融风控(拆分)List
     *
     * @param statementFinanceSplit 对账单-金融风控(拆分)对象
     * @return
     */
    public List<StatementFinanceSplit> getStatementFinanceSplitList(StatementFinanceSplit statementFinanceSplit) {
        logger.info("查询对账单-金融风控(拆分)列表List入参:{}", statementFinanceSplit);
        List<StatementFinanceSplit> result;
        result = statementFinanceSplitMapper.select(statementFinanceSplit);
        return result;
    }

    /**
     * 新增对账单-金融风控(拆分)信息
     *
     * @param statementFinanceSplit 对账单-金融风控(拆分)对象
     * @return 返回成功条数
     */
    public Integer add(StatementFinanceSplit statementFinanceSplit){
        logger.info("新增对账单-金融风控(拆分)入参:{}", statementFinanceSplit);
        Integer result;
        result = statementFinanceSplitMapper.add(statementFinanceSplit);
        return result;
    }

    /**
     * 修改对账单-金融风控(拆分)信息
     *
     * @param statementFinanceSplit 对账单-金融风控(拆分)对象
     * @return 返回成功条数
     */
    public Integer updateById(StatementFinanceSplit statementFinanceSplit){
        logger.info("修改对账单-金融风控(拆分)入参:{}", statementFinanceSplit);
        Integer result;
        result = statementFinanceSplitMapper.updateById(statementFinanceSplit);
        return result;
    }

    /**
     * 导出对账单-金融风控(拆分)信息
     *
     * @param statementFinanceSplit 对账单-金融风控(拆分)对象
     * @return 返回成功条数
     */
    public List<StatementFinanceSplitExcelVo> exportStatementFinanceSplitExit(StatementFinanceSplit statementFinanceSplit){
        logger.info("导出对账单-金融风控(拆分)入参:{}", statementFinanceSplit);
        if (StringUtils.isEmpty(statementFinanceSplit)) {
            logger.error("StatementFinanceSplitService::exportStatementFinanceSplitExit 参数不能为空");
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        List<StatementFinanceSplit> statementFinanceSplits = null;
        List<StatementFinanceSplitExcelVo> statementFinanceSplitExcelVoList = new ArrayList<>();
        statementFinanceSplits = statementFinanceSplitMapper.exportStatementFinanceSplitExit(statementFinanceSplit);
        if(statementFinanceSplits.size() > 0){
            for (StatementFinanceSplit list: statementFinanceSplits){
                StatementFinanceSplitExcelVo StatementFinanceSplitExcelVo = new StatementFinanceSplitExcelVo();
                StatementFinanceSplitExcelVo.setBillTypeCode(list.getBillTypeCode());
                StatementFinanceSplitExcelVo.setBillTypeName(list.getBillTypeName());
                StatementFinanceSplitExcelVo.setBillNumber(list.getBillNumber());
                StatementFinanceSplitExcelVo.setNumber(SupplychainUtils.getStatementNumber(Constants.NUMBER_HEAD_FINANCE_SPLIT, list.getId().intValue()));

                Date newTime = list.getTime();//转换日期
                String strTime = formatter.format(newTime);
                StatementFinanceSplitExcelVo.setTime(strTime);
                StatementFinanceSplitExcelVo.setSalesCode(list.getSalesCode());
                StatementFinanceSplitExcelVo.setSalesName(list.getSalesName());
                StatementFinanceSplitExcelVo.setCustomerCode(list.getCustomerCode());
                StatementFinanceSplitExcelVo.setCustomerName(list.getCustomerName());
                StatementFinanceSplitExcelVo.setSaleGroupCode(list.getSaleGroupCode());
                StatementFinanceSplitExcelVo.setSaleGroupName(list.getSaleGroupName());
                StatementFinanceSplitExcelVo.setStatementCurrencyCode(list.getStatementCurrencyCode());
                StatementFinanceSplitExcelVo.setStatementCurrencyName(list.getStatementCurrencyName());
                StatementFinanceSplitExcelVo.setMaterialCode(list.getMaterialCode());
                StatementFinanceSplitExcelVo.setMaterialName(list.getMaterialName());
                StatementFinanceSplitExcelVo.setSalesUnitCode(list.getSalesUnitCode());
                StatementFinanceSplitExcelVo.setSalesUnitName(list.getSalesUnitName());
                StatementFinanceSplitExcelVo.setSalesQuantity(list.getSalesQuantity());
                StatementFinanceSplitExcelVo.setPrice(list.getPrice());
                StatementFinanceSplitExcelVo.setGift(list.getGift());
                StatementFinanceSplitExcelVo.setTaxRate(list.getTaxRate());
                StatementFinanceSplitExcelVo.setSaleOrderFinance(StatementFinanceSplitExcelVo.getNumber());
                StatementFinanceSplitExcelVo.setSaleOrderEntry(StatementFinanceSplitExcelVo.getNumber());
                Date newTakeGoods = list.getTakeGoodsDate();//转换要货日期
                if(newTakeGoods != null){
                    String strTakeGoods = formatter.format(newTakeGoods);
                    StatementFinanceSplitExcelVo.setTakeGoodsDate(strTakeGoods);
                }
                StatementFinanceSplitExcelVo.setStatementOrganizeCode(list.getStatementOrganizeCode());
                StatementFinanceSplitExcelVo.setStatementOrganizeName(list.getStatementOrganizeName());
                StatementFinanceSplitExcelVo.setReserveType(list.getReserveType());
                StatementFinanceSplitExcelVo.setWarehouseCode(list.getWarehouseCode());
                StatementFinanceSplitExcelVo.setWarehouseName(list.getWarehouseName());
                StatementFinanceSplitExcelVo.setQuantity(list.getQuantity());
                StatementFinanceSplitExcelVo.setSaleOrderFinance(StatementFinanceSplitExcelVo.getNumber());
                StatementFinanceSplitExcelVo.setSaleOrderEntry(StatementFinanceSplitExcelVo.getNumber());
                statementFinanceSplitExcelVoList.add(StatementFinanceSplitExcelVo);
            }
        }
        logger.info("exportStatementFinanceSplitExit return statementFinanceSplitExcelVoList.size()" + statementFinanceSplitExcelVoList.size());
        return statementFinanceSplitExcelVoList;
    }

}
