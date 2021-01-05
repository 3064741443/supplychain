package cn.com.glsx.supplychain.jxc.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import com.github.pagehelper.Page;

import cn.com.glsx.supplychain.jxc.dto.JXCMTBsBillDTO;
import cn.com.glsx.supplychain.jxc.model.JXCMTBsMerchantOrderSignSuply;

@Mapper
public interface JXCMTBsMerchantOrderSignSuplyMapper extends OreMapper<JXCMTBsMerchantOrderSignSuply>{
   
	Page<JXCMTBsBillDTO> pageSignBillNumber(JXCMTBsBillDTO condition);
}