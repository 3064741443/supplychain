package cn.com.glsx.supplychain.remote.am;

import java.util.List;

import cn.com.glsx.framework.core.annotation.ApiMethod;
import cn.com.glsx.framework.core.annotation.ApiParam;
import cn.com.glsx.framework.core.annotation.ApiResponse;
import cn.com.glsx.framework.core.annotation.ApiService;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.model.am.StatementSellCombile;
import cn.com.glsx.supplychain.model.am.StatementSellGlwy;
import cn.com.glsx.supplychain.model.am.StatementSellGlwyImport;
import cn.com.glsx.supplychain.model.am.StatementSellJbwy;
import cn.com.glsx.supplychain.model.am.StatementSellJbwyImport;
import cn.com.glsx.supplychain.model.am.StatementSellRecon;
import cn.com.glsx.supplychain.model.am.StatementSellRenew;
import cn.com.glsx.supplychain.model.am.StatementSellRenewImport;
import cn.com.glsx.supplychain.model.am.StatementSellRzfb;
import cn.com.glsx.supplychain.model.am.StatementSellRzfbImport;
import cn.com.glsx.supplychain.model.am.StatementSellSplit;
import cn.com.glsx.supplychain.vo.CheckImportDataVo;
import cn.com.glsx.supplychain.vo.ProductSplitDetailVo;
import cn.com.glsx.supplychain.vo.ProductSplitVo;
import cn.com.glsx.supplychain.vo.StatementSellSplitExcelVo;

/**
 * @author 
 * @version V1.0
 * @Title: StatementSellAdminRemote.java
 * @Description: 对账单-经销对账(针对运营业务后台)
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2019
 */
@ApiService(value = "对账单-经销对账(针对运营业务后台)", owner = "supplychain_bs", version = "1.0.0")
public interface StatementSellAdminRemote {

	@ApiMethod("生成对账单草稿")
    @ApiResponse(value = "返回对账单以及详情")
    @ApiParam(name = "statementSell", notes = "生成对账单", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<StatementSellRecon> generaterStatementSellRecon(StatementSellRecon record);
	
	@ApiMethod("经销对账单列表")
    @ApiResponse(value = "返回对账单列表")
    @ApiParam(name = "statementSell", notes = "经销对账单列表", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<RpcPagination<StatementSellRecon>> pageStatementSellRecon(RpcPagination<StatementSellRecon> pagination);
	
	@ApiMethod("获取对账单详情")
    @ApiResponse(value = "返回对账单详情")
    @ApiParam(name = "statementSell", notes = "对账单详情", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<StatementSellRecon> getStatementSellRecon(StatementSellRecon record);
	
	@ApiMethod("修改对账单信息")
    @ApiResponse(value = "返回对账单详情")
    @ApiParam(name = "statementSell", notes = "对账单详情", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<StatementSellRecon> saveStatementSellRecon(StatementSellRecon record);
	
	@ApiMethod("删除对账单")
    @ApiResponse(value = "返回对账单详情")
    @ApiParam(name = "statementSell", notes = "对账单详情", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<Integer> delStatementSellRecon(StatementSellRecon record);
	
	@ApiMethod("拆分对账单")
    @ApiResponse(value = "返回对账单详情")
    @ApiParam(name = "statementSell", notes = "对账单详情", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<Integer> splitStatementSellRecon(StatementSellRecon record);
	
	@ApiMethod("对账单拆分列表")
    @ApiResponse(value = "返回对账单拆分列表")
    @ApiParam(name = "statementSell", notes = "对账单详情", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<RpcPagination<StatementSellSplit>> pageStatementSellSplit(RpcPagination<StatementSellSplit> pagination);
	
	@ApiMethod("对账单拆分列表导出")
    @ApiResponse(value = "返回对账单拆分列表")
    @ApiParam(name = "statementSell", notes = "对账单详情", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<List<StatementSellSplitExcelVo>> listStatementSellSplit(StatementSellSplit record);
	
	@ApiMethod("对账单拆分汇总列表")
    @ApiResponse(value = "返回对账单拆分汇总列表")
    @ApiParam(name = "statementSell", notes = "对账单详情", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<RpcPagination<StatementSellCombile>> pageStatementSellCombile(RpcPagination<StatementSellCombile> pagination);
	
	@ApiMethod("对账单拆分汇总列表导出")
    @ApiResponse(value = "返回对账单拆分汇总列表导出")
    @ApiParam(name = "statementSell", notes = "对账单详情", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<List<StatementSellSplitExcelVo>> listStatementSellCombile(StatementSellCombile record);

	@ApiMethod("获取商户下产品列表用于对账单")
    @ApiResponse(value = "返回产品列表")
    @ApiParam(name = "statementSell", notes = "对账单详情", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<List<ProductSplitVo>> listStatementSellProductSplit(StatementSellRecon record);
	
	@ApiMethod("获取商户下产品列表详情列表")
    @ApiResponse(value = "返回产品详情")
    @ApiParam(name = "statementSell", notes = "对账单详情", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<List<ProductSplitDetailVo>> listStatementSellProductSplitDetail(ProductSplitVo record);
	
	@ApiMethod("经销对账广联无忧导入数据校验")
    @ApiResponse(value = "返回校验结果")
    @ApiParam(name = "statementSell", notes = "对账单详情", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<CheckImportDataVo> CheckStatementSellGlwyImportData(List<StatementSellGlwyImport> importList);
	
	@ApiMethod("经销对账广联无忧导入数据导入")
    @ApiResponse(value = "返回占位")
    @ApiParam(name = "statementSell", notes = "对账单详情", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<Integer> ImportStatementSellGlwyImportData(String operatorName,List<StatementSellGlwyImport> importList);
	
	@ApiMethod("经销对账广联无忧分页显示")
    @ApiResponse(value = "返回列表")
    @ApiParam(name = "statementSell", notes = "对账单详情", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<RpcPagination<StatementSellGlwy>> pageStatementSellGlwy(RpcPagination<StatementSellGlwy> pagination);
	
	@ApiMethod("经销对账广联无忧删除")
    @ApiResponse(value = "返回占位")
    @ApiParam(name = "statementSell", notes = "对账单详情", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<Integer> delStatementSellGlwy(StatementSellGlwy record);
	
	@ApiMethod("经销对账驾宝无忧导入数据校验")
    @ApiResponse(value = "返回校验结果")
    @ApiParam(name = "statementSell", notes = "对账单详情", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<CheckImportDataVo> CheckStatementSellJbwyImportData(List<StatementSellJbwyImport> importList);
	
	@ApiMethod("经销对账驾宝无忧导入数据导入")
    @ApiResponse(value = "返回占位")
    @ApiParam(name = "statementSell", notes = "对账单详情", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<Integer> ImportStatementSellJbwyImportData(String operatorName,List<StatementSellJbwyImport> importList);
	
	@ApiMethod("经销对账驾宝无忧分页显示")
    @ApiResponse(value = "返回列表")
    @ApiParam(name = "statementSell", notes = "对账单详情", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<RpcPagination<StatementSellJbwy>> pageStatementSellJbwy(RpcPagination<StatementSellJbwy> pagination);
	
	@ApiMethod("经销对账驾宝无忧删除")
    @ApiResponse(value = "返回占位")
    @ApiParam(name = "statementSell", notes = "对账单详情", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<Integer> delStatementSellJbwy(StatementSellJbwy record);
	
	@ApiMethod("经销对账续费(微信)导入数据校验")
    @ApiResponse(value = "返回校验结果")
    @ApiParam(name = "statementSell", notes = "对账单详情", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<CheckImportDataVo> CheckStatementSellRenewImportData(List<StatementSellRenewImport> importList);
	
	@ApiMethod("经销对账续费(微信)导入数据导入")
    @ApiResponse(value = "返回占位")
    @ApiParam(name = "statementSell", notes = "对账单详情", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<Integer> ImportStatementSellRenewImportData(String operatorName,List<StatementSellRenewImport> importList);
	
	@ApiMethod("经销对账续费(微信)分页显示")
    @ApiResponse(value = "返回对账列表")
    @ApiParam(name = "statementSell", notes = "对账单详情", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<RpcPagination<StatementSellRenew>> pageStatementSellRenew(RpcPagination<StatementSellRenew> pagination);

	@ApiMethod("经销对账续费(微信)删除")
    @ApiResponse(value = "返回占位")
    @ApiParam(name = "statementSell", notes = "对账单详情", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<Integer> delStatementSellRenew(StatementSellRenew record);
	
	@ApiMethod("经销对账续费(支付宝)导入数据校验")
    @ApiResponse(value = "返回校验结果")
    @ApiParam(name = "statementSell", notes = "对账单详情", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<CheckImportDataVo> CheckStatementSellRzfbImportData(List<StatementSellRzfbImport> importList);
	
	@ApiMethod("经销对账续费(支付宝)导入数据导入")
    @ApiResponse(value = "返回占位")
    @ApiParam(name = "statementSell", notes = "对账单详情", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<Integer> ImportStatementSellRzfbImportData(String operatorName,List<StatementSellRzfbImport> importList);
	
	@ApiMethod("经销对账续费(支付宝)分页显示")
    @ApiResponse(value = "返回对账列表")
    @ApiParam(name = "statementSell", notes = "对账单详情", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<RpcPagination<StatementSellRzfb>> pageStatementSellRzfb(RpcPagination<StatementSellRzfb> pagination);

	@ApiMethod("经销对账续费(支付宝)删除")
    @ApiResponse(value = "返回占位")
    @ApiParam(name = "statementSell", notes = "对账单详情", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<Integer> delStatementSellRzfb(StatementSellRzfb record);
}
