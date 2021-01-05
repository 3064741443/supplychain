package glsx.com.cn.task.mapper;


import com.github.pagehelper.Page;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import glsx.com.cn.task.model.MerchantOrder;
import glsx.com.cn.task.vo.MerchantOrderStatement;

import java.util.Date;
import java.util.List;

@Mapper
public interface MerchantOrderMapper extends OreMapper<MerchantOrder> {

    Page<MerchantOrder> listMerchantOrder(MerchantOrder merchantOrder);

    Integer updateByOrderNumber(MerchantOrder merchantOrder);

    List<MerchantOrder> getMerchantOrderListByDate(@Param("startTime") Date startTime,@Param("endTime") Date endTime);

    List<MerchantOrder> exportMerchantOrderExit();
    
    List<MerchantOrderStatement> listMerchantOrderStatementForSn(List<String> list);
    
    List<MerchantOrderStatement> listMerchantOrderStatementForPeiJianFromDB(MerchantOrder merchantOrder);
}