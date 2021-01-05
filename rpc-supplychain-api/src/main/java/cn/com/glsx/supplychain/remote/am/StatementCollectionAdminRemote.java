package cn.com.glsx.supplychain.remote.am;

import cn.com.glsx.framework.core.annotation.ApiMethod;
import cn.com.glsx.framework.core.annotation.ApiParam;
import cn.com.glsx.framework.core.annotation.ApiResponse;
import cn.com.glsx.framework.core.annotation.ApiService;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.model.am.StatementCollection;
import cn.com.glsx.supplychain.model.am.StatementCollectionImport;
import cn.com.glsx.supplychain.model.am.StatementCollectionSplit;
import cn.com.glsx.supplychain.model.am.StatementFinanceImport;
import cn.com.glsx.supplychain.model.bs.MerchantOrder;
import cn.com.glsx.supplychain.vo.CheckImportDataVo;
import cn.com.glsx.supplychain.vo.MerchantOrderExcelVo;
import cn.com.glsx.supplychain.vo.StatementCollectSplitExcelVo;

import java.util.Date;
import java.util.List;

/**
 * @author leiming
 * @version V1.0
 * @Title: StatementCollectionAdminRemote.java
 * @Description: 对账单-广联采集管理(针对运营业务后台)
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2019
 */
@ApiService(value = "对账单-广联采集管理(针对运营业务后台)", owner = "supplychain_bs", version = "1.0.0")
public interface StatementCollectionAdminRemote {

    @ApiMethod("分页获取对账单-广联采集列表")
    @ApiResponse(value = "返回对账单-广联采集列表")
    @ApiParam(name = "statementCollection", notes = "对账单-广联采集对象", required = true, dataType = ApiParam.DataTypeEnum.LONG)
    RpcResponse<RpcPagination<StatementCollection>> listStatementCollection(RpcPagination<StatementCollection> pagination);

    @ApiMethod("获取对账单-广联采集列表根据ID")
    @ApiResponse(value = "返回对账单-广联采集列表")
    @ApiParam(name = "statementCollection", notes = "对账单-广联采集对象", required = true, dataType = ApiParam.DataTypeEnum.LONG)
    RpcResponse<StatementCollection> getStatementCollectionByid(Long id);

    @ApiMethod("新增对账单-广联采集")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "statementCollection", notes = "对账单-广联采集对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> add(StatementCollection statementCollection);

    @ApiMethod("修改对账单-广联采集")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "statementCollection", notes = "对账单-广联采集对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> updateById(StatementCollection statementCollection);

    @ApiMethod("删除对账单-广联采集以及拆分结果数据")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "statementCollection", notes = "对账单-广联采集对象以及拆分结果数据", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> deleteStatementCollectionByDate(StatementCollection statementCollection);

    @ApiMethod("对账单-广联采集导入检验")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "statementFinance", notes = "对账单-广联采集对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<CheckImportDataVo> checkImportStatementCollection(List<StatementCollectionImport> statementCollectionImportList);

    /**
     * 对账单-广联采集(excel)数据导入
     *
     * @param
     * @return
     * @throws RpcServiceException
     */
    @ApiMethod("对账单-广联采集(excel)数据导入")
    @ApiResponse(value = "返回对账单-广联采集(excel)数据导入结果")
    @ApiParam(name = "operatorName", notes = "操作人", required = true, dataType = ApiParam.DataTypeEnum.STRING)
    RpcResponse<Integer> importStatementCollectionImport(String operatorName,List<StatementCollectionImport> statementCollectionImportList);

    @ApiMethod("分页获取对账单-广联采集(拆分)列表")
    @ApiResponse(value = "返回对账单-广联采集(拆分)列表")
    @ApiParam(name = "statementCollection", notes = "对账单-广联采集(拆分)对象", required = true, dataType = ApiParam.DataTypeEnum.LONG)
    RpcResponse<RpcPagination<StatementCollectionSplit>> listStatementCollectionSplit(RpcPagination<StatementCollectionSplit> pagination);

    @ApiMethod("新增对账单-广联采集(拆分)")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "statementCollectionSplit", notes = "对账单-广联采集(拆分)对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> add(StatementCollectionSplit statementCollectionSplit);

    @ApiMethod("修改对账单-广联采集(拆分)")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "statementCollectionSplit", notes = "对账单-广联采集(拆分)对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> updateById(StatementCollectionSplit statementCollectionSplit);

    @ApiMethod("对账单-广联采集(拆分)导出")
    @ApiResponse(value = "对账单-广联采集(拆分)列表导出")
    @ApiParam(name = "statementCollectionSplit", notes = "对账单-广联采集(拆分)列表导出", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<List<StatementCollectSplitExcelVo>> exportStatementCollectionSplitExit(StatementCollectionSplit statementCollectionSplit);
}
