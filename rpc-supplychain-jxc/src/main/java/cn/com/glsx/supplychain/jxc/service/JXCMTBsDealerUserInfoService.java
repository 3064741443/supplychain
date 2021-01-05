package cn.com.glsx.supplychain.jxc.service;

import cn.com.glsx.supplychain.jxc.dto.JXCDealerUserInfoDTO;
import cn.com.glsx.supplychain.jxc.mapper.JXCMTBsDealerUserInfoMapper;
import cn.com.glsx.supplychain.jxc.model.JXCMTBsDealerUserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import tk.mybatis.mapper.entity.Example;

import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class JXCMTBsDealerUserInfoService {

	private static final Logger logger = LoggerFactory.getLogger(JXCMTBsDealerUserInfoService.class);
	@Autowired
	private JXCMTBsDealerUserInfoMapper jxcmtBsDealerUserInfoMapper;
	
	public Page<JXCDealerUserInfoDTO> pageServerMerchant(RpcPagination<JXCDealerUserInfoDTO> pagination){
		JXCMTBsDealerUserInfo condition = new JXCMTBsDealerUserInfo();
		condition.setMerchantCode(pagination.getCondition().getServiceProviderCode());
		condition.setMerchantName(pagination.getCondition().getServiceProviderName());
		PageHelper.startPage(pagination.getPageNum(), pagination.getPageSize());
		return jxcmtBsDealerUserInfoMapper.pageServerMerchant(condition);
	}
	
	public Byte getChannelByMerchantCode(String merchantCode){
		JXCMTBsDealerUserInfo condition = new JXCMTBsDealerUserInfo();
		condition.setMerchantCode(merchantCode);
		condition.setDeletedFlag("N");
		JXCMTBsDealerUserInfo dealerUserInfo = jxcmtBsDealerUserInfoMapper.selectOne(condition);
		if(null == dealerUserInfo){
			return null;
		}
		return dealerUserInfo.getChannel();
	}
	
	public String getMerchantName(String merchantCode){
		JXCMTBsDealerUserInfo condition = new JXCMTBsDealerUserInfo();
		condition.setMerchantCode(merchantCode);
		condition.setDeletedFlag("N");
		JXCMTBsDealerUserInfo dealerUserInfo = jxcmtBsDealerUserInfoMapper.selectOne(condition);
		if(null == dealerUserInfo){
			return null;
		}
		return dealerUserInfo.getMerchantName();
	}
	
	public JXCMTBsDealerUserInfo getBsDealerUserInfo(String merchantCode){
		JXCMTBsDealerUserInfo condition = new JXCMTBsDealerUserInfo();
		condition.setMerchantCode(merchantCode);
		condition.setDeletedFlag("N");
		JXCMTBsDealerUserInfo dealerUserInfo = jxcmtBsDealerUserInfoMapper.selectOne(condition);
		if(null == dealerUserInfo){
			return null;
		}
		return dealerUserInfo;
	}
	
	public Map<String,String> mapMerchantName(List<String> listMerchantCodes){
		Map<String,String> mapResult = new HashMap<>();
		Example example = new Example(JXCMTBsDealerUserInfo.class);
		example.createCriteria().andIn("merchantCode", listMerchantCodes);
		List<JXCMTBsDealerUserInfo> listUserInfo = jxcmtBsDealerUserInfoMapper.selectByExample(example);
		if(null == listUserInfo || listUserInfo.isEmpty()){
			return mapResult;
		}
		for(JXCMTBsDealerUserInfo userInfo:listUserInfo){
			mapResult.put(userInfo.getMerchantCode(), userInfo.getMerchantName());
		}
		return mapResult;
	}

	public List<JXCDealerUserInfoDTO> listServiceProvider(JXCDealerUserInfoDTO dealerUserInfoDTO) {
		List<Byte> channelList=new ArrayList<>();
		channelList.add((byte)1);
		channelList.add((byte)2);
		JXCMTBsDealerUserInfo bsDealerUserInfo=new JXCMTBsDealerUserInfo();
		bsDealerUserInfo.setChannelList(channelList);
		bsDealerUserInfo.setMerchantCode(dealerUserInfoDTO.getServiceProviderCode());
		bsDealerUserInfo.setMerchantName(dealerUserInfoDTO.getServiceProviderName());
		List<JXCDealerUserInfoDTO> listUserInfo = jxcmtBsDealerUserInfoMapper.listServerMerchant(bsDealerUserInfo);
		if(StringUtils.isEmpty(listUserInfo)){
			return null;
		}
		return listUserInfo;
	}

}
