package cn.com.glsx.supplychain.jxc.service;

import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.supplychain.jxc.common.Constants;
import cn.com.glsx.supplychain.jxc.dto.STKMerchantStockSnDTO;
import cn.com.glsx.supplychain.jxc.dto.STKMerchantStockSnStatDTO;
import cn.com.glsx.supplychain.jxc.kafka.ExportMerchantStockSn;
import cn.com.glsx.supplychain.jxc.kafka.ExportMerchantStockSnStat;
import cn.com.glsx.supplychain.jxc.kafka.SendKafkaService;
import cn.com.glsx.supplychain.jxc.mapper.STKMerchantStockSnMapper;
import cn.com.glsx.supplychain.jxc.mapper.STKMerchantStockSnStatMapper;
import cn.com.glsx.supplychain.jxc.model.STKMerchantStockSn;
import cn.com.glsx.supplychain.jxc.model.STKMerchantStockSnStat;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class STKMerchantStockSnService {
	private static final Logger logger = LoggerFactory.getLogger(STKMerchantStockSnService.class);
	@Autowired
	private STKMerchantStockSnStatMapper stockSnStatMapper;
	@Autowired
	private STKMerchantStockSnMapper merchantStockSnMapper;

	@Autowired
	private SendKafkaService sendKafkaService;
	
	public List<STKMerchantStockSnStatDTO> getMerchantStockSnStatByDeviceType(STKMerchantStockSnStatDTO record){
		STKMerchantStockSnStat condition = new STKMerchantStockSnStat();
		condition.setActiveOrNot("N");
		condition.setUnActiveDays(record.getUnActiveDays());
		return stockSnStatMapper.getMerchantStockSnStatByDeviceType(condition);
	}
	
	public Page<STKMerchantStockSnStatDTO> pageMerchantStockSnStatByToMerchantCode(RpcPagination<STKMerchantStockSnStatDTO> pagination){
		STKMerchantStockSnStat condition = new STKMerchantStockSnStat();
		condition.setActiveOrNot("N");
		condition.setUnActiveDays(pagination.getCondition().getUnActiveDays());
		PageHelper.startPage(pagination.getPageNum(), pagination.getPageSize());
		return stockSnStatMapper.pageMerchantStockSnStatByToMerchantCode(condition);
	}

	public List<STKMerchantStockSnStatDTO> exportMerchantStockSnStatByToMerchantCode(STKMerchantStockSnStatDTO merchantStockSnStatDTO){
		logger.info("STKMerchantStockSnService::exportMerchantStockSnStatByToMerchantCode merchantStockSnStatDTO::{}",merchantStockSnStatDTO);
		//发送kafka异步执行
		ExportMerchantStockSnStat exportMerchantStockSnStat = generatorMerchantStockSnStat(merchantStockSnStatDTO);
		sendKafkaService.notifyMerchantStockSnStat(merchantStockSnStatDTO.getConsumer(), Constants.TASK_CFG_ID_STK_STOCK_SN_STAT, exportMerchantStockSnStat);
		STKMerchantStockSnStat condition = new STKMerchantStockSnStat();
		condition.setActiveOrNot("N");
		condition.setUnActiveDays(merchantStockSnStatDTO.getUnActiveDays());
		return stockSnStatMapper.exportMerchantStockSnStatByToMerchantCode(condition);
	}

	private ExportMerchantStockSnStat generatorMerchantStockSnStat(STKMerchantStockSnStatDTO merchantStockSnStatDTO)
	{
		ExportMerchantStockSnStat exportMerchantStockSnStat = new ExportMerchantStockSnStat();
		exportMerchantStockSnStat.setUnActiveDayFlag(merchantStockSnStatDTO.getUnActiveDayFlag());
		return exportMerchantStockSnStat;
	}

	public List<STKMerchantStockSnDTO> exportMerchantStockSn(STKMerchantStockSnDTO merchantStockSnDTO){
		logger.info("STKMerchantStockSnService::exportMerchantStockSn merchantStockSnDTO::{}",merchantStockSnDTO);
		//发送kafka异步执行
		ExportMerchantStockSn  exportMerchantStockSn = generatorMerchantStockSn(merchantStockSnDTO);
		sendKafkaService.notifyMerchantStockSn(merchantStockSnDTO.getConsumer(), Constants.TASK_CFG_ID_STK_MERCHANT_STOCK_SN, exportMerchantStockSn);
		Example example=new Example(STKMerchantStockSnStat.class);
		example.createCriteria().andEqualTo("deviceType",merchantStockSnDTO.getDeviceType())
				                .andEqualTo("unActiveDays",merchantStockSnDTO.getUnActiveDays())
		                        .andEqualTo("activeOrNot","N");
		List<STKMerchantStockSnStat> stkMerchantStockSnStatList=stockSnStatMapper.selectByExample(example);
		STKMerchantStockSnStat stkMerchantStockSnStat=null;
        if(!StringUtils.isEmpty(stkMerchantStockSnStatList)&&stkMerchantStockSnStatList.size()>0){
			 stkMerchantStockSnStat=stkMerchantStockSnStatList.get(0);
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(stkMerchantStockSnStat.getCreatedDate());
		//设置为前三个月
		calendar.add(Calendar.MONTH, -3);
		//得到前3月的时间
		Date formNow3Month = calendar.getTime();
		STKMerchantStockSn stkMerchantStockSn=new STKMerchantStockSn();
		stkMerchantStockSn.setDeviceType(merchantStockSnDTO.getDeviceType());
		stkMerchantStockSn.setUpdatedDate(formNow3Month);
		return merchantStockSnMapper.exportMerchantStockSn(stkMerchantStockSn);
	}

	private ExportMerchantStockSn generatorMerchantStockSn(STKMerchantStockSnDTO merchantStockSnDTO)
	{
		ExportMerchantStockSn exportMerchantStockSn = new ExportMerchantStockSn();
		exportMerchantStockSn.setDeviceType(merchantStockSnDTO.getDeviceType());
		exportMerchantStockSn.setUnActiveDayFlag(merchantStockSnDTO.getActiveOrNot());
		return exportMerchantStockSn;
	}
}
