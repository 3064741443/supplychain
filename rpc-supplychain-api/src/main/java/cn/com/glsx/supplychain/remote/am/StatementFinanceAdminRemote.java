package cn.com.glsx.supplychain.remote.am;

import cn.com.glsx.framework.core.annotation.ApiMethod;
import cn.com.glsx.framework.core.annotation.ApiParam;
import cn.com.glsx.framework.core.annotation.ApiResponse;
import cn.com.glsx.framework.core.annotation.ApiService;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.model.DeviceListImport;
import cn.com.glsx.supplychain.model.am.KcCustomerRelation;
import cn.com.glsx.supplychain.model.am.KcWarehouseRelation;
import cn.com.glsx.supplychain.model.am.StatementCollectionSplit;
import cn.com.glsx.supplychain.model.am.StatementFinance;
import cn.com.glsx.supplychain.model.am.StatementFinanceCombile;
import cn.com.glsx.supplychain.model.am.StatementFinanceExrule;
import cn.com.glsx.supplychain.model.am.StatementFinanceImport;
import cn.com.glsx.supplychain.model.am.StatementFinanceSplit;
import cn.com.glsx.supplychain.vo.CheckImportDataVo;
import cn.com.glsx.supplychain.vo.StatementCollectSplitExcelVo;
import cn.com.glsx.supplychain.vo.StatementFinanceSplitExcelVo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author leiming
 * @version V1.0
 * @Title: StatementFinanceAdminRemote.java
 * @Description: 对账单-金融风控管理(针对运营业务后台)
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2019
 */
@ApiService(value = "对账单-金融风控管理(针对运营业务后台)", owner = "supplychain_bs", version = "1.0.0")
public interface StatementFinanceAdminRemote {
	
	@ApiMethod("获取客户关系列表")
    @ApiResponse(value = "返回客户关系表")
    @ApiParam(name = "statementFinance", notes = "对账单-客户关系表", required = true, dataType = ApiParam.DataTypeEnum.LONG)
	RpcResponse<RpcPagination<KcCustomerRelation>> listKcCustomerRelation(RpcPagination<KcCustomerRelation> pagination);
	
	@ApiMethod("根据发货仓库编码获取发货仓库")
    @ApiResponse(value = "返回发货仓库")
    @ApiParam(name = "statementFinance", notes = "对账单-查询仓库", required = true, dataType = ApiParam.DataTypeEnum.LONG)
	RpcResponse<KcWarehouseRelation> getWarehouseRelationByCode(KcWarehouseRelation record);
	
	@ApiMethod("根据客户编码获取客户")
    @ApiResponse(value = "返回客户")
    @ApiParam(name = "statementFinance", notes = "对账单-查询客户", required = true, dataType = ApiParam.DataTypeEnum.LONG)
	RpcResponse<KcCustomerRelation> getCustomerRelationByCode(KcCustomerRelation record);
	
	@ApiMethod("获取excel导入转换规则map列表")
    @ApiResponse(value = "返回对账单-金融风控列表")
    @ApiParam(name = "statementFinance", notes = "对账单-金融风控对象", required = true, dataType = ApiParam.DataTypeEnum.LONG)
	RpcResponse<Map<String,StatementFinanceExrule>> getStatementFinanceExruleMap(StatementFinanceExrule record);
	
    @ApiMethod("分页获取对账单-金融风控列表")
    @ApiResponse(value = "返回对账单-金融风控列表")
    @ApiParam(name = "statementFinance", notes = "对账单-金融风控对象", required = true, dataType = ApiParam.DataTypeEnum.LONG)
    RpcResponse<RpcPagination<StatementFinance>> listStatementFinance(RpcPagination<StatementFinance> pagination);

    @ApiMethod("获取对账单-金融风控列表根据ID")
    @ApiResponse(value = "返回对账单-金融风控列表")
    @ApiParam(name = "statementFinance", notes = "对账单-金融风控对象", required = true, dataType = ApiParam.DataTypeEnum.LONG)
    RpcResponse<StatementFinance> getStatementFinanceByid(Long id);

    @ApiMethod("新增对账单-金融风控")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "statementFinance", notes = "对账单-金融风控对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> add(StatementFinance statementFinance);

    @ApiMethod("修改对账单-金融风控")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "statementFinance", notes = "对账单-金融风控对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> updateById(StatementFinance statementFinance);

    @ApiMethod("删除对账单-金融风控以及拆分结果数据")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "statementCollection", notes = "对账单-金融风控对象以及拆分结果数据", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> deleteStatementFinanceByDate(StatementFinance statementFinance);

    @ApiMethod("对账单-金融风控导入检验")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "statementFinance", notes = "对账单-金融风控对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<CheckImportDataVo> checkImportStatementFinance(List<StatementFinanceImport> statementFinanceImportList);

    @ApiMethod("分页获取对账单-金融风控(拆分)列表")
    @ApiResponse(value = "返回对账单-金融风控(拆分)列表")
    @ApiParam(name = "statementFinanceSplit", notes = "对账单-金融风控(拆分)对象", required = true, dataType = ApiParam.DataTypeEnum.LONG)
    RpcResponse<RpcPagination<StatementFinanceSplit>> listStatementFinanceSplit(RpcPagination<StatementFinanceSplit> pagination);

    @ApiMethod("分页获取对账单-金融风控(汇总)列表")
    @ApiResponse(value = "返回对账单-金融风控(汇总)列表")
    @ApiParam(name = "statementFinanceSplit", notes = "对账单-金融风控(汇总)对象", required = true, dataType = ApiParam.DataTypeEnum.LONG)
    RpcResponse<RpcPagination<StatementFinanceCombile>> listStatementFinanceCombile(RpcPagination<StatementFinanceCombile> pagination);
    
    @ApiMethod("新增对账单-金融风控(拆分)")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "statementFinanceSplit", notes = "对账单-金融风控(拆分)对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> add(StatementFinanceSplit statementFinanceSplit);

    @ApiMethod("修改对账单-金融风控(拆分)")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "statementFinanceSplit", notes = "对账单-金融风控(拆分)对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> updateById(StatementFinanceSplit statementFinanceSplit);

    @ApiMethod("对账单-金融风控(拆分)导出")
    @ApiResponse(value = "对账单-金融风控(拆分)列表导出")
    @ApiParam(name = "statementCollectionSplit", notes = "对账单-金融风控(拆分)列表导出", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<List<StatementFinanceSplitExcelVo>> exportStatementFinanceSplitExit(StatementFinanceSplit statementFinanceSplit);

    @ApiMethod("对账单-金融风控(汇总)导出")
    @ApiResponse(value = "对账单-金融风控(汇总)列表导出")
    @ApiParam(name = "statementCollectionSplit", notes = "对账单-金融风控(汇总)列表导出", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<List<StatementFinanceSplitExcelVo>> exportStatementFinanceCombileExit(StatementFinanceCombile statementFinanceCombile);
    /**
     * 对账单-金融风控(excel)数据导入
     *
     * @param
     * @return
     * @throws RpcServiceException
     */
    @ApiMethod("对账单-金融风控(excel)数据导入")
    @ApiResponse(value = "返回对账单-金融风控(excel)数据导入结果")
    @ApiParam(name = "operatorName", notes = "操作人", required = true, dataType = ApiParam.DataTypeEnum.STRING)
    RpcResponse<Integer> importStatementFinanceImport(String operatorName,List<StatementFinanceImport> statementFinanceImportList);
}
