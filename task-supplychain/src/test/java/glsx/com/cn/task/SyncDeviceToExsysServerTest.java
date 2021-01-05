package glsx.com.cn.task;

import glsx.com.cn.task.service.DeviceStatisService;
import glsx.com.cn.task.service.SyncDeviceToExsysServer;
import glsx.com.cn.task.service.SyncLogDeviceToExsysServer;

import org.springframework.beans.factory.annotation.Autowired;

public class SyncDeviceToExsysServerTest extends BaseTest{

	@Autowired
	private SyncDeviceToExsysServer deviceToExsysServer;
	
	@Autowired
	private SyncLogDeviceToExsysServer deviceLogToExsysServer;
	
	@Autowired
	private DeviceStatisService statisService;
	
	@org.junit.Test
	public void dispatchDeviceToExsysServerTest()
	{
		int im = 0;
	//	deviceToExsysServer.dispatchDeviceToExsysServer();
	//	deviceLogToExsysServer.dispatchLogDeviceToExsysServer();
		statisService.handleDeviceStatis();
		im++;
	}
	
}
