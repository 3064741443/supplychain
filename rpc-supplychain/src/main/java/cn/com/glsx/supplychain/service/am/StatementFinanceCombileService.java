package cn.com.glsx.supplychain.service.am;

import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.common.Constants;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.mapper.am.StatementFinanceCombileMapper;
import cn.com.glsx.supplychain.model.am.StatementFinanceCombile;
import cn.com.glsx.supplychain.util.SupplychainUtils;
import cn.com.glsx.supplychain.vo.StatementFinanceSplitExcelVo;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class StatementFinanceCombileService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private StatementFinanceCombileMapper statementFinanceCombileMapper;
	
	/**
     * 获取对账单-金融风控(汇总)分页列表
     *
     * @param statementFinanceSplit 对账单-金融风控(汇总)对象
     * @return
     */
    public List<StatementFinanceCombile> listStatementFinanceCombile(int pageNum, int pageSize, StatementFinanceCombile statementFinanceCombile){
        logger.info("pageNum为：{}, pageSize为：{}, 条件查询参数为 ：{}。", pageNum, pageSize, statementFinanceCombile);
        List<StatementFinanceCombile> result;
        PageHelper.startPage(pageNum, pageSize);
        result = statementFinanceCombileMapper.listStatementFinanceCombile(statementFinanceCombile);
        return result;
    }
    
    /**
     * 导出对账单-金融风控(汇总)信息
     *
     * @param statementFinanceCombile 对账单-金融风控(拆分)对象
     * @return 返回成功条数
     */
    public List<StatementFinanceSplitExcelVo> exportStatementFinanceCombileExit(StatementFinanceCombile statementFinanceCombile){
        logger.info("导出对账单-金融风控(拆分)入参:{}", JSONObject.toJSON(statementFinanceCombile));
        if (StringUtils.isEmpty(statementFinanceCombile)) {
            logger.error("StatementFinanceSplitService::exportStatementFinanceCombileExit 参数不能为空");
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        List<StatementFinanceCombile> statementFinanceCombiles = null;
        List<StatementFinanceSplitExcelVo> statementFinanceSplitExcelVoList = new ArrayList<>();
        statementFinanceCombiles = statementFinanceCombileMapper.exportStatementFinanceCombileExit(statementFinanceCombile);
        if(null !=statementFinanceCombiles && statementFinanceCombiles.size() > 0){
            for (StatementFinanceCombile list: statementFinanceCombiles){
                StatementFinanceSplitExcelVo StatementFinanceSplitExcelVo = new StatementFinanceSplitExcelVo();
                StatementFinanceSplitExcelVo.setNumber(SupplychainUtils.getStatementNumber(Constants.NUMBER_HEAD_FINANCE_COMBILE, list.getId().intValue()));
                StatementFinanceSplitExcelVo.setBillTypeCode(list.getBillTypeCode());
                StatementFinanceSplitExcelVo.setBillTypeName(list.getBillTypeName());
                StatementFinanceSplitExcelVo.setBillNumber(list.getBillNumber());

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
        logger.info("exportStatementFinanceCombileExit return statementFinanceSplitExcelVoList.size()" + statementFinanceSplitExcelVoList.size());
        return statementFinanceSplitExcelVoList;
    }
}
