package cn.com.glsx.supplychain.jxc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.supplychain.jxc.dto.JXCMTBsBillDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTBsMerchantOrderSignSuplyDTO;
import cn.com.glsx.supplychain.jxc.mapper.JXCMTBsMerchantOrderSignSuplyMapper;
import cn.com.glsx.supplychain.jxc.model.JXCMTBsMerchantOrderSignSuply;

@Service
public class JXCMTOrderSignSupplyService {
	private static final Logger logger = LoggerFactory.getLogger(JXCMTOrderSignSupplyService.class);
	@Autowired
	private JXCMTBsMerchantOrderSignSuplyMapper signSuplyMapper;
	
	public Page<JXCMTBsBillDTO> pageSignBillNumber(RpcPagination<JXCMTBsBillDTO> pagination){
		
		PageHelper.startPage(pagination.getPageNum(), pagination.getPageSize());
		return signSuplyMapper.pageSignBillNumber(pagination.getCondition());
	}
	
	public Integer genBillNumberRecord(JXCMTBsMerchantOrderSignSuplyDTO record){
		JXCMTBsMerchantOrderSignSuply orderSign = new JXCMTBsMerchantOrderSignSuply();
		orderSign.setBillSignNumber(record.getBillSignNumber());
		orderSign.setCreatedBy(record.getCreatedBy());
		orderSign.setUpdatedBy(record.getUpdatedBy());
		orderSign.setCreatedDate(record.getCreatedDate());
		orderSign.setUpdatedDate(record.getUpdatedDate());
		orderSign.setDeletedFlag(record.getDeletedFlag());
		orderSign.setDispatchOrderCode(record.getDispatchOrderCode());
		orderSign.setLogisticsNo(record.getLogisticsNo());
		orderSign.setSignUrl(record.getSignUrl());
		orderSign.setBillType(record.getBillType());
		return signSuplyMapper.insertSelective(orderSign);
	}
}
