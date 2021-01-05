package glsx.com.cn.task.mapper;

import com.github.pagehelper.Page;
import glsx.com.cn.task.model.JstShop;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface JstShopMapper extends OreMapper<JstShop>{
    
    int insert(JstShop record);

    int insertSelective(JstShop record);
   
    int updateByPrimaryKeySelective(JstShop record);

    int updateByPrimaryKey(JstShop record);

    Page<JstShop> pageMyJstShop(JstShop jstShop);

    Page<JstShop> pageJstShop(JstShop jstShop);
    
    List<JstShop> listJstShopByAgentMerchantCode(@Param("agentMerchantCode") String agentMerchantCode);

    int updateByShopCode(JstShop jstShop);
}