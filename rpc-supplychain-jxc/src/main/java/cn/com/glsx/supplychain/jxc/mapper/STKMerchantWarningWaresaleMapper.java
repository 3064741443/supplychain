package cn.com.glsx.supplychain.jxc.mapper;

import cn.com.glsx.supplychain.jxc.dto.STKWarningWaresaleDTO;
import cn.com.glsx.supplychain.jxc.model.STKMerchantWarningWaresale;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface STKMerchantWarningWaresaleMapper extends OreMapper<STKMerchantWarningWaresale>{

	Page<STKWarningWaresaleDTO> pageWarningWaresale(STKMerchantWarningWaresale record);

	List<STKWarningWaresaleDTO> exportWarningWaresale(STKMerchantWarningWaresale record);
}