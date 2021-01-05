package glsx.com.cn.task.mapper;

import glsx.com.cn.task.model.MerchantOrderDetail;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;
@Mapper
public interface MerchantOrderDetailMapper extends OreMapper<MerchantOrderDetail> {

    Integer updateById(MerchantOrderDetail merchantOrderDetail);
}