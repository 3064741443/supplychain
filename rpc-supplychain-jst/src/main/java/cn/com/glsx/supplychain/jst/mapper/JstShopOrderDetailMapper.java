package cn.com.glsx.supplychain.jst.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import cn.com.glsx.supplychain.jst.model.JstShopOrderDetail;

import java.util.List;

@Mapper
public interface JstShopOrderDetailMapper extends OreMapper<JstShopOrderDetail>{

    int insert(JstShopOrderDetail record);

    int insertSelective(JstShopOrderDetail record);

    int updateByPrimaryKeySelective(JstShopOrderDetail record);

    int updateByPrimaryKey(JstShopOrderDetail record);

    List<JstShopOrderDetail>  getListByshopOrderCode(JstShopOrderDetail jstShopOrderDetail);

    List<JstShopOrderDetail> countShopOrderDetail(List<String> shopCodes);

    List<JstShopOrderDetail> pageOrderDetail(JstShopOrderDetail record);
    
}