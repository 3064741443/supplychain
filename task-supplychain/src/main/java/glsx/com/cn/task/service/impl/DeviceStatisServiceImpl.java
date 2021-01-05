package glsx.com.cn.task.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.util.StringUtil;

import glsx.com.cn.task.mapper.DeviceFileSnapshotMapper;
import glsx.com.cn.task.mapper.DeviceStatisReportMapper;
import glsx.com.cn.task.model.DeviceFileSnapshot;
import glsx.com.cn.task.model.DeviceStatisReport;
import glsx.com.cn.task.service.DeviceStatisService;

@Service
public class DeviceStatisServiceImpl implements DeviceStatisService{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	public DeviceStatisReportMapper statisMapper;
	@Autowired
	public DeviceFileSnapshotMapper snapshotMapper;
	@Override
	public void handleDeviceStatis() {
		
		logger.info("DeviceStatisServiceImpl::handleDeviceStatis handle start");
		DeviceFileSnapshot record = new DeviceFileSnapshot();
		DeviceStatisReport report = new DeviceStatisReport();
		
		try
		{
			record.setPackageStatu("UA");
			DeviceFileSnapshot snapshot = snapshotMapper.countByPackageStatus(record);
			report.setDeviceUa(snapshot.getStatisCount());
			record.setPackageStatu("AL");
			snapshot = snapshotMapper.countByPackageStatus(record);
			report.setDeviceAl(snapshot.getStatisCount());
			record.setPackageStatu("IN");
			snapshot = snapshotMapper.countByPackageStatus(record);
			report.setDeviceIn(snapshot.getStatisCount());
			report.setDeviceTotal(report.getDeviceAl() + report.getDeviceIn() + report.getDeviceUa());
			report.setDay(getCurDay());
			report.setCreatedBy("admin");
			report.setCreatedDate(new Date());
			report.setUpdatedBy("admin");
			report.setUpdatedDate(new Date());
			report.setDeletedFlag("N");
			statisMapper.insertSelective(report);
			logger.info("DeviceStatisServiceImpl::handleDeviceStatis handle ok!");
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
			logger.info("DeviceStatisServiceImpl::handleDeviceStatis handle failed");
		}
	}

	private String getCurDay()
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(new Date());
	}
	
}
