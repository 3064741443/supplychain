package cn.com.glsx.supplychain.mapper.bs;


import cn.com.glsx.supplychain.model.bs.MaintainProductDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MaintainProductDetailMapper{

    int deleteByPrimaryKey(Long id);

    int insertList(List<MaintainProductDetail> record);

    int insertSelective(MaintainProductDetail record);

    MaintainProductDetail selectById(Long id);

    MaintainProductDetail getMaintainProductDetailInfo(MaintainProductDetail maintainProductDetail);

    int updateByPrimaryKeySelective(MaintainProductDetail record);

    int updateByPrimaryKey(MaintainProductDetail record);

    List<MaintainProductDetail>getMainTainProductDetailByAfterOrderNumber(String afterSaleOrderNumber);

    List<MaintainProductDetail>getMainTainProductDetailList(MaintainProductDetail record);
}