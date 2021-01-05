package cn.com.glsx.supplychain.jxc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import com.github.pagehelper.Page;

import cn.com.glsx.supplychain.jxc.dto.JXCMTOrderInfoDetailDTO;
import cn.com.glsx.supplychain.jxc.model.JXCMTDispatchOrderNumCount;
import cn.com.glsx.supplychain.jxc.model.JXCMTOrderInfoDetail;

@Mapper
public interface JXCMTOrderInfoDetailMapper extends OreMapper<JXCMTOrderInfoDetail>{
    
	public List<JXCMTDispatchOrderNumCount> selectOrderDetailCount(List<String> listOrderCodes);
	
	public Page<JXCMTOrderInfoDetailDTO> pageDispatchOrderDetail(JXCMTOrderInfoDetail record);
}