package cn.com.glsx.supplychain.mapper.am;

import cn.com.glsx.supplychain.model.am.KcCustomerRelation;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import com.github.pagehelper.Page;

import java.util.List;

@Mapper
public interface KcCustomerRelationMapper extends OreMapper<KcCustomerRelation> {

    List<KcCustomerRelation> getKcCustomerRelationList(KcCustomerRelation record);

    int add(KcCustomerRelation record);

    int updateById(KcCustomerRelation record);
    
    Page<KcCustomerRelation> pageKcCustomerRelation(KcCustomerRelation record);

}