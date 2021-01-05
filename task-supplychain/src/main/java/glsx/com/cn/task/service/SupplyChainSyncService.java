package glsx.com.cn.task.service;

import java.text.ParseException;

public interface SupplyChainSyncService {

	//void SyncDeviceFileToPhysicalDevice();

//	void SyncDeviceInfoToFlowcatPlat();

	//同步库存
	void SyncDeviceFileUnstockToFlowcatPlat();

	void SyncDeviceInfoToDeviceFile();
	
	//void sysDeviceCategories();

	void sysProductPriceRefresh();

	void sysSettlementSendData();
}
