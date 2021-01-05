package cn.com.glsx.supplychain.jxc.mapper;

import cn.com.glsx.supplychain.jxc.dto.JXCMTOrderInfoDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTOrderInfoMerchantDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTOrderInfoSignDTO;
import cn.com.glsx.supplychain.jxc.model.JXCMTOrderInfo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface JXCMTOrderInfoMapper extends OreMapper<JXCMTOrderInfo>{
    
	public Page<JXCMTOrderInfoDTO> pageOrderInfo(JXCMTOrderInfo record);

	public List<JXCMTOrderInfoDTO> exportOrderInfo(JXCMTOrderInfo record);
	
	public Page<JXCMTOrderInfoMerchantDTO> pageOrderInfoMerchant(JXCMTOrderInfo record);
	
	public Page<JXCMTOrderInfoSignDTO> pageSignOrders(JXCMTOrderInfoSignDTO record);
}