package cn.com.glsx.supplychain.jxc.mapper;

import cn.com.glsx.supplychain.jxc.dto.JXCMdbBsTransferOrderExportDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMdbTransferOrderExportDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCTransferOrderDTO;
import cn.com.glsx.supplychain.jxc.model.JXCMdbTransferOrder;
import com.github.pagehelper.Page;
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
public interface JxcTransferOrderMapper extends OreMapper<JXCMdbTransferOrder> {

   Page<JXCTransferOrderDTO> pageTransferOrderJXC(JXCMdbTransferOrder transferOrder);

   List<JXCMdbBsTransferOrderExportDTO> exportBsTransferOrderJXC(JXCMdbTransferOrder transferOrder);

   List<JXCMdbTransferOrderExportDTO> exportJxcTransferOrder(JXCMdbTransferOrder transferOrder);
}
