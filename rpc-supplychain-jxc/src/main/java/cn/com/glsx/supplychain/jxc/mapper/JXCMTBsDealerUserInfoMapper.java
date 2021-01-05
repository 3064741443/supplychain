package cn.com.glsx.supplychain.jxc.mapper;

import cn.com.glsx.supplychain.jxc.dto.JXCDealerUserInfoDTO;
import cn.com.glsx.supplychain.jxc.model.JXCMTBsDealerUserInfo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface JXCMTBsDealerUserInfoMapper extends OreMapper<JXCMTBsDealerUserInfo>{
    
	Page<JXCDealerUserInfoDTO> pageServerMerchant(JXCMTBsDealerUserInfo record);

	List<JXCDealerUserInfoDTO> listServerMerchant(JXCMTBsDealerUserInfo record);

}