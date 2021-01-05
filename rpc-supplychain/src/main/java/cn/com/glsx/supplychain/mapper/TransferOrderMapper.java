package cn.com.glsx.supplychain.mapper;

import cn.com.glsx.supplychain.model.MdbTransferOrder;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

/**
 * @author luoqiang
 * @version 1.0
 * @program: supplychain
 * @description:
 * @date 2020/10/26 11:17
 */
@Mapper
public interface TransferOrderMapper extends OreMapper<MdbTransferOrder> {
}
