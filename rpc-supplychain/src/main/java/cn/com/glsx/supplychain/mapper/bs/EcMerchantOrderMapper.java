package cn.com.glsx.supplychain.mapper.bs;

import cn.com.glsx.supplychain.model.am.EcMerchantOrder;
import cn.com.glsx.supplychain.model.bs.MerchantOrder;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface EcMerchantOrderMapper extends OreMapper<EcMerchantOrder> {

    List<EcMerchantOrder>exportEcMerchantOrderExit(EcMerchantOrder ecMerchantOrder);

}