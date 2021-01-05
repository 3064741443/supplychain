package glsx.com.cn.task.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import glsx.com.cn.task.model.BsMerchantStock;
import glsx.com.cn.task.model.SyncLastidRecordMerChantStock;

@Mapper
public interface BsMerchantStockMapper extends OreMapper<BsMerchantStock>{
  //  int deleteByPrimaryKey(Integer id);

    int insert(BsMerchantStock record);

    int insertSelective(BsMerchantStock record);

  //  BsMerchantStock selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BsMerchantStock record);

    int updateByPrimaryKey(BsMerchantStock record);
    
    int batchAddAndUpdateMerchantStock(List<BsMerchantStock> list);
}