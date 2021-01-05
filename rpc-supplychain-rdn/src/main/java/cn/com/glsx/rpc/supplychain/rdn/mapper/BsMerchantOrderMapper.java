package cn.com.glsx.rpc.supplychain.rdn.mapper;

import cn.com.glsx.rpc.supplychain.rdn.model.pojo.MdbTransferOrder;
import cn.com.glsx.rpc.supplychain.rdn.model.pojo.MerchantOrder;
import cn.com.glsx.rpc.supplychain.rdn.model.tmp.*;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface BsMerchantOrderMapper extends OreMapper<MerchantOrder>{
   
	public Page<BsMerchantOrderVo> pageBsMerchantOrderJXC(MerchantOrder record);

	public Page<BsMerchantOrderVo> pageBsMerchantOrderBSS(MerchantOrder record);
	
	public List<BsMerchantOrderBssVo> exportBsMerchantOrderBSS(MerchantOrder record);

	public List<BsTransferOrderBssVo> exportBsTransferOrder(MdbTransferOrder record);

	public List<JxcTransferOrderVo> exportJXCTransferOrder(MdbTransferOrder record);

	public Page<SpMerchantOrderVo> pageBsMerchantOrderBSP(MerchantOrder record);
	
	public List<BsMerchantOrderBspVo> exportBsMerchantOrderBSP(MerchantOrder record);

	public List<BsMerchantOrderVo> exportBsMerchantOrderJXC(MerchantOrder record);

	public List<BsMerchantOrderVo> listMaterialInfoByMerchantOrder(List<String> listMerchantOrders);
}