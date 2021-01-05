package cn.com.glsx.supplychain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.glsx.supplychain.mapper.WrongReportImeiMapper;
import cn.com.glsx.supplychain.model.WrongReportImei;

/**
 * 
 * @Title: WrongReportImeiService
 * @Description: 接收kafka消息修改状态需要过滤不符合逻辑的imei
 * @author Leiyj  
 * @date 2018年1月11日 下午2:43:04
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2015
 */
@Service
public class WrongReportImeiService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private WrongReportImeiMapper wrongReportImeiMapper;
	
	public WrongReportImei getWrongReportImeiBySn(String sn)
	{
		WrongReportImei condition = new WrongReportImei();
		condition.setSn(sn);
		condition.setDeletedFlag("N");
		return wrongReportImeiMapper.selectOne(condition);
	}
	
}
