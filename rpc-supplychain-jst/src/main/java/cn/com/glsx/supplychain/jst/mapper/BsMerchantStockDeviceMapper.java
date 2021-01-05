package cn.com.glsx.supplychain.jst.mapper;

import java.util.List;

import cn.com.glsx.supplychain.jst.dto.BsMerchantStockDeviceDTO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import cn.com.glsx.supplychain.jst.model.BsMerchantStockDevice;

import java.util.List;

@Mapper
public interface BsMerchantStockDeviceMapper extends OreMapper<BsMerchantStockDevice>{

    int insert(BsMerchantStockDevice record);

    int insertSelective(BsMerchantStockDevice record);

    int updateByPrimaryKeySelective(BsMerchantStockDevice record);

    int updateByPrimaryKey(BsMerchantStockDevice record);

    Page<BsMerchantStockDevice> pageBsMerchantStockDevice(BsMerchantStockDevice bsMerchantStockDevice);

    Integer getSum(BsMerchantStockDevice bsMerchantStockDevice);
    
    int batchBatchInsertReplace(List<BsMerchantStockDevice> listDevices);

    int batchUpdateFinshStatu (List<String> listOrderCodes);
}