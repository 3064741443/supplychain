package cn.com.glsx.supplychain.service;

import cn.com.glsx.supplychain.mapper.DeviceStatisReportMapper;
import cn.com.glsx.supplychain.model.DeviceStatisReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DeviceStatisReportService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DeviceStatisReportMapper deviceStatisReportMapper;


    public DeviceStatisReport getMaxDeviceStatisReport() {
        return deviceStatisReportMapper.getMaxDeviceStatisReport();
    }
}
