package cn.com.glsx.supplychain.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import com.github.pagehelper.Page;

import cn.com.glsx.supplychain.model.AttribMana;

@Mapper
public interface AttribManaMapper extends OreMapper<AttribMana>  {
	int insert(AttribMana record);

	int insertAttribMana(AttribMana record);

    int update(AttribMana record);
    
    Page<AttribMana> listAttribMana(AttribMana record);
    
    int count(AttribMana record);
    
    AttribMana getAttribManaByCode(String manaCode);
    
    AttribMana guessAttribManaByDevmnumId(Integer devMnumId);
    
    //获取类型配置编码列表
    List<AttribMana> getAttribManaCodeList(AttribMana record);
}
