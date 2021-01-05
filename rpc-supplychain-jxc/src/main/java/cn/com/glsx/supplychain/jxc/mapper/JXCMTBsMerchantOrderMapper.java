package cn.com.glsx.supplychain.jxc.mapper;

import cn.com.glsx.supplychain.jxc.dto.JXCMTBsMerchantOrderDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTBsMerchantOrderExportBspDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTBsMerchantOrderExportDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTSpMerchantOrderDTO;
import cn.com.glsx.supplychain.jxc.model.JXCMTBsMerchantOrder;

import com.github.pagehelper.Page;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface JXCMTBsMerchantOrderMapper extends OreMapper<JXCMTBsMerchantOrder>{
   
	public Page<JXCMTBsMerchantOrderDTO> pageBsMerchantOrderJXC(JXCMTBsMerchantOrder record);

	public Page<JXCMTBsMerchantOrderDTO> pageBsMerchantOrderBSS(JXCMTBsMerchantOrder record);

	public Integer countBsMerchantOrderBSS(JXCMTBsMerchantOrder record);
	
	public List<JXCMTBsMerchantOrderExportDTO> exportBsMerchantOrderBSS(JXCMTBsMerchantOrder record);

	public Integer countBsMerchantOrderBSP(JXCMTBsMerchantOrder record);
	
	public List<JXCMTSpMerchantOrderDTO> pageBsMerchantOrderBSP(JXCMTBsMerchantOrder record);
	
	public List<JXCMTBsMerchantOrderExportBspDTO> exportBsMerchantOrderExportBSP(JXCMTBsMerchantOrder record);

	public List<JXCMTBsMerchantOrderDTO> exportBsMerchantOrderJXC(JXCMTBsMerchantOrder record);

	public List<JXCMTBsMerchantOrderDTO> listMaterialInfoByMerchantOrder(List<String> listMerchantOrders);
}