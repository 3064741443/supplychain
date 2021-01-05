package glsx.com.cn.task.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import glsx.com.cn.task.model.BsAfterSaleOrder;

@Mapper
public interface BsAfterSaleOrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BsAfterSaleOrder record);

    int insertSelective(BsAfterSaleOrder record);

    BsAfterSaleOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BsAfterSaleOrder record);

    int updateByPrimaryKey(BsAfterSaleOrder record);
    
    List<BsAfterSaleOrder> listBsAfterSaleOrderByConditon(BsAfterSaleOrder record);
}