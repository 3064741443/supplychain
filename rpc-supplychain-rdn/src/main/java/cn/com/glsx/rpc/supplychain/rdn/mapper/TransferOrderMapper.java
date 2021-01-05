package cn.com.glsx.rpc.supplychain.rdn.mapper;

import cn.com.glsx.rpc.supplychain.rdn.model.pojo.MdbTransferOrder;
import cn.com.glsx.rpc.supplychain.rdn.model.tmp.BsTransferOrderBssVo;
import cn.com.glsx.rpc.supplychain.rdn.model.tmp.JxcTransferOrderVo;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

/**
 * @author luoqiang
 * @version 1.0
 * @program: supplychain
 * @description:
 * @date 2020/10/26 11:17
 */
@Mapper
public interface TransferOrderMapper extends OreMapper<MdbTransferOrder> {
   List<BsTransferOrderBssVo> exportBsTransferOrder(MdbTransferOrder transferOrder);

   List<JxcTransferOrderVo> exportJXCTransferOrder(MdbTransferOrder transferOrder);
}
