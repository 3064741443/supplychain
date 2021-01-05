package cn.com.glsx.supplychain.remote;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.enums.PackageStatuEnum;
import cn.com.glsx.supplychain.enums.SystemPlatEnum;
import cn.com.glsx.supplychain.enums.UserFlagTypeEnum;
import cn.com.glsx.supplychain.enums.VehicleFlagTypeEnum;
import cn.com.glsx.supplychain.model.DeviceCode;
import cn.com.glsx.supplychain.model.DeviceFile;
import cn.com.glsx.supplychain.model.DeviceFileSv;
import cn.com.glsx.supplychain.model.DeviceListImport;
import cn.com.glsx.supplychain.model.DeviceType;
import cn.com.glsx.supplychain.model.DeviceUpdateRecordSv;
import cn.com.glsx.supplychain.model.SupplyDeviceCodeRequest;
import cn.com.glsx.supplychain.model.SupplyDeviceFileRequest;
import cn.com.glsx.supplychain.model.SupplyDeviceTypeRequest;
import cn.com.glsx.supplychain.model.SupplyRequest;
import cn.com.glsx.supplychain.vo.CheckImportDataVo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeviceManagerServiceImplTest {
	
	@Autowired
	private  DeviceManagerServiceImpl deviceManagerService;
	
	@Autowired
	private DeviceManagerAdminRemoteImpl deviceAdminService;

	
	
	
	private void getSupplyRequest(SupplyRequest request)
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timeNow = df.format(new Date());
		
		request.setConsumer("admin");
		request.setTime(timeNow);
		request.setVersion("v2.0");
	}
	
	@Test
	public void testPageDeviceType() {
	/*	
		RpcPagination<DeviceType> pagination = new RpcPagination<DeviceType>();
		pagination.setPageNum(1);
		pagination.setPageSize(20);
		SupplyDeviceTypeRequest request = new SupplyDeviceTypeRequest();
		getSupplyRequest(request);
		
		RpcResponse<RpcPagination<DeviceType>> response = deviceManagerService.pageDeviceType(pagination, request);
	
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();	
		assertNull(errCodeEnum);
		
		*/
		
		String DeviceCode = "110270";
		List<DeviceListImport> importList = new  ArrayList<DeviceListImport>();
		DeviceListImport oImport = new DeviceListImport();
		oImport.setModulePhone("13633333333");
		oImport.setIccid("8986061602001633333");
		oImport.setImei("21502058206");
		oImport.setImsi("460060082049666");
		oImport.setVcode("123456");
		oImport.setBatchNo("123456");
		oImport.setMentchantNo("19180009");
		oImport.setPackageNo("41");
		importList.add(oImport);
		Boolean isOnlyimsi = null;
		
		RpcResponse<CheckImportDataVo> response = deviceAdminService.checkImportDeviceList(DeviceCode, importList, isOnlyimsi,false);
		
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();	
		assertNull(errCodeEnum);
		
	
	}

	@Test
	public void testListDeviceType() {
		
		SupplyDeviceTypeRequest request = new SupplyDeviceTypeRequest();
		getSupplyRequest(request);
		request.setName("DV");
		RpcResponse<List<DeviceType>> response = deviceManagerService.listDeviceType(request);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		assertNull(errCodeEnum);
	}

	@Test
	public void testPageDeviceCode() {
		
		RpcPagination<DeviceCode> pagination = new RpcPagination<DeviceCode>();
		pagination.setPageNum(1);
		pagination.setPageSize(20);
		SupplyDeviceCodeRequest request = new SupplyDeviceCodeRequest();
		getSupplyRequest(request);
		
		RpcResponse<RpcPagination<DeviceCode>> response = deviceManagerService.pageDeviceCode(pagination, request);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		assertNull(errCodeEnum);
	}

	@Test
	public void testListDeviceCode() {
		
		SupplyDeviceCodeRequest request = new SupplyDeviceCodeRequest();
		getSupplyRequest(request);
		request.setDeviceCode(110269);
		RpcResponse<List<DeviceCode>> response = deviceManagerService.listDeviceCode(request);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		assertNull(errCodeEnum);
	}

	@Test
	public void testListDeviceCodeByIds() {
		SupplyDeviceCodeRequest request = new SupplyDeviceCodeRequest();
		getSupplyRequest(request);
		List<Integer> listIds = new ArrayList<Integer>();
		listIds.add(100001);
		listIds.add(100024);
		request.setListIds(listIds);
		
		RpcResponse<List<DeviceCode>> response = deviceManagerService.listDeviceCodeByIds(request);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		assertNull(errCodeEnum);
	}

	@Test
	public void testGetDeviceTypeNameById() {
		
		SupplyDeviceTypeRequest request = new SupplyDeviceTypeRequest();
		getSupplyRequest(request);
		request.setId(12307);
		RpcResponse<String> response = deviceManagerService.getDeviceTypeNameById(request);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		assertNull(errCodeEnum);
	}

	@Test
	public void testGetDeviceCodeByIdOrDeviceCode() {
		
		SupplyDeviceCodeRequest request = new SupplyDeviceCodeRequest();
		getSupplyRequest(request);
		request.setDeviceCode(100030);
		RpcResponse<DeviceCode> response = deviceManagerService.getDeviceCodeByIdOrDeviceCode(request);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		assertNull(errCodeEnum);
	}

	@Test
	public void testGetDeviceCodeByManufacturerCode() {
		
		SupplyDeviceCodeRequest request = new SupplyDeviceCodeRequest();
		getSupplyRequest(request);
		request.setManufacturerCode("dddd");
		RpcResponse<DeviceCode> response = deviceManagerService.getDeviceCodeByManufacturerCode(request);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		assertNull(errCodeEnum);
	}

	@Test
	public void testGetDeviceCodeNameByIdOrDeviceCode() {
		
		SupplyDeviceCodeRequest request = new SupplyDeviceCodeRequest();
		getSupplyRequest(request);
		request.setId(100097);
		RpcResponse<String> response = deviceManagerService.getDeviceCodeNameByIdOrDeviceCode(request);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		assertNull(errCodeEnum);
	}

	@Test
	public void testCountDevicesByDeviceType() {
		
		SupplyDeviceTypeRequest request = new SupplyDeviceTypeRequest();
		getSupplyRequest(request);
		request.setId(2);
		RpcResponse<Integer> response = deviceManagerService.countDevicesByDeviceType(request);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		assertNull(errCodeEnum);
	}

	@Test
	public void testCountDevicesByDeviceCode() {
		
		SupplyDeviceCodeRequest request = new SupplyDeviceCodeRequest();
		getSupplyRequest(request);
		request.setDeviceCode(2);
		RpcResponse<Integer> response = deviceManagerService.countDevicesByDeviceCode(request);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		assertNull(errCodeEnum);
	}

	@Test
	public void testCountDevicesByPackageStatus() {
		
		SupplyDeviceFileRequest request = new SupplyDeviceFileRequest();
		getSupplyRequest(request);
		request.setPkgStatus(PackageStatuEnum.PACKAGE_STATU_UNACTIVE);
		RpcResponse<Integer> response = deviceManagerService.countDevicesByPackageStatus(request);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		assertNull(errCodeEnum);
	}

	@Test
	public void testCountDevices() {
		
		SupplyDeviceFileRequest request = new SupplyDeviceFileRequest();
		getSupplyRequest(request);
		RpcResponse<Integer> response = deviceManagerService.countDevices(request);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		assertNull(errCodeEnum);
	}

	@Test
	public void testGetDeviceByDeviceSn() {
		
		SupplyDeviceFileRequest request = new SupplyDeviceFileRequest();
		getSupplyRequest(request);
		request.setSn("21502058209");
		RpcResponse<DeviceFileSv> response = deviceManagerService.getDeviceByDeviceSn(request);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		assertNull(errCodeEnum);
	}

//	@Test
//	public void testGetDeviceByDeviceId() {
//		
//		SupplyDeviceFileRequest request = new SupplyDeviceFileRequest();
//		getSupplyRequest(request);
//		request.setId(15);
//		RpcResponse<DeviceFileSv> response = deviceManagerService.getDeviceByDeviceId(request);
//		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
//		assertNull(errCodeEnum);
//	}

	@Test
	public void testGetDeviceByImsi() {
		
		SupplyDeviceFileRequest request = new SupplyDeviceFileRequest();
		getSupplyRequest(request);
		request.setImsi("460069022008062");
		request.setCompanyId(1);
		RpcResponse<DeviceFileSv> response = deviceManagerService.getDeviceByImsi(request);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		assertNull(errCodeEnum);
	}

	@Test
	public void testGetSystemPlatByDeviceSn() {
		
		SupplyDeviceFileRequest request = new SupplyDeviceFileRequest();
		getSupplyRequest(request);
		request.setSn("460069022017902");
		RpcResponse<SystemPlatEnum> response = deviceManagerService.getSystemPlatByDeviceSn(request);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		assertNull(errCodeEnum);
	}

	@Test
	public void testGetSystemPlatByImsi() {
		
		SupplyDeviceFileRequest request = new SupplyDeviceFileRequest();
		getSupplyRequest(request);
		request.setImsi("460069022017902");
		request.setCompanyId(1);
		RpcResponse<SystemPlatEnum> response = deviceManagerService.getSystemPlatByImsi(request);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		assertNull(errCodeEnum);
	}

	@Test
	public void testGetDeviceFileByVehicleFlag() {
		
		SupplyDeviceFileRequest request = new SupplyDeviceFileRequest();
		getSupplyRequest(request);
		request.setVehicleFlag("345345345");
		request.setCompanyId(1);
		RpcResponse<DeviceFileSv> response = deviceManagerService.getDeviceFileByVehicleFlag(request);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		assertNull(errCodeEnum);
	}

	@Test
	public void testBindDeviceFileToVehicleFlag() {
		
		SupplyDeviceFileRequest request = new SupplyDeviceFileRequest();
		getSupplyRequest(request);
		request.setVehicleFlag("111111");
		request.setCompanyId(1);
		request.setSn("555555555555555");
		request.setFlagType(VehicleFlagTypeEnum.VEHICLETYPE_UD);
		RpcResponse<Integer> response = deviceManagerService.bindDeviceFileToVehicleFlag(request);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		assertNull(errCodeEnum);
	}

	@Test
	public void testUnBindDeviceFileToVehicleFlag() {
		
		SupplyDeviceFileRequest request = new SupplyDeviceFileRequest();
		getSupplyRequest(request);
		request.setVehicleFlag("111111");
		request.setCompanyId(1);
		request.setSn("555555555555555");
		request.setFlagType(VehicleFlagTypeEnum.VEHICLETYPE_UD);
		RpcResponse<Integer> response = deviceManagerService.unBindDeviceFileToVehicleFlag(request);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		assertNull(errCodeEnum);
	}

	@Test
	public void testBindCardToDeviceFile() {
		
		SupplyDeviceFileRequest request = new SupplyDeviceFileRequest();
		getSupplyRequest(request);
		request.setImsi("22222222224");
		request.setCompanyId(1);
		request.setSn("999999999999999");
		RpcResponse<Integer> response = deviceManagerService.bindCardToDeviceFile(request);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		assertNull(errCodeEnum);
	}

	@Test
	public void testUnBindCardToDeviceFile() {
		
		SupplyDeviceFileRequest request = new SupplyDeviceFileRequest();
		getSupplyRequest(request);
		request.setImsi("22222222224");
		request.setCompanyId(1);
		request.setSn("999999999999999");
		RpcResponse<Integer> response = deviceManagerService.unBindCardToDeviceFile(request);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		assertNull(errCodeEnum);
	}

	@Test
	public void testListDeviceFileByActivePackageUserFlag() {
		
		SupplyDeviceFileRequest request = new SupplyDeviceFileRequest();
		getSupplyRequest(request);
		request.setUserFlag("11111111111");
		request.setUserFlagType(UserFlagTypeEnum.USERTYPE_UD);
		request.setCompanyId(1);
		RpcResponse<List<DeviceFileSv>> response = deviceManagerService.listDeviceFileByActivePackageUserFlag(request);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		assertNull(errCodeEnum);
	}

	@Test
	public void testListDeviceFileByBindingUserFlag() {
		
		SupplyDeviceFileRequest request = new SupplyDeviceFileRequest();
		getSupplyRequest(request);
		request.setUserFlag("11111111111");
		request.setUserFlagType(UserFlagTypeEnum.USERTYPE_UD);
		request.setCompanyId(1);
		RpcResponse<List<DeviceFileSv>> response = deviceManagerService.listDeviceFileByBindingUserFlag(request);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		assertNull(errCodeEnum);
	}

	@Test
	public void testCountDeviceFileByActivePackageUserFlag() {
		
		SupplyDeviceFileRequest request = new SupplyDeviceFileRequest();
		getSupplyRequest(request);
		request.setUserFlag("11111111111");
		request.setUserFlagType(UserFlagTypeEnum.USERTYPE_UD);
		request.setCompanyId(1);
		RpcResponse<Integer> response = deviceManagerService.countDeviceFileByActivePackageUserFlag(request);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		assertNull(errCodeEnum);
	}

	@Test
	public void testCountDeviceFileByBindingUserFlag() {
		
		SupplyDeviceFileRequest request = new SupplyDeviceFileRequest();
		getSupplyRequest(request);
		request.setUserFlag("11111111111");
		request.setUserFlagType(UserFlagTypeEnum.USERTYPE_UD);
		request.setCompanyId(1);
		RpcResponse<Integer> response = deviceManagerService.countDeviceFileByBindingUserFlag(request);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		assertNull(errCodeEnum);
	}

	@Test
	public void testActiveDevicePackage() {

		SupplyDeviceFileRequest request = new SupplyDeviceFileRequest();
		getSupplyRequest(request);
		request.setUserFlag("91711391");
		request.setUserFlagType(UserFlagTypeEnum.USERTYPE_UD);
		request.setFlagType(VehicleFlagTypeEnum.VEHICLETYPE_UD);
		request.setCompanyId(1);
		request.setSn("217720582787876666");
		request.setImsi("460060443049767");
		/*request.setDeviceCode(100006);
		request.setPackageId("");*/
		RpcResponse<Integer> response = deviceManagerService.activeDevicePackage(request);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		assertNull(errCodeEnum);
	}

	@Test
	public void testBindUserToDeviceFile() {
		
		SupplyDeviceFileRequest request = new SupplyDeviceFileRequest();
		getSupplyRequest(request);
		request.setUserFlag("11111111111");
		request.setUserFlagType(UserFlagTypeEnum.USERTYPE_UD);
		request.setCompanyId(1);
		request.setSn("666666666666666");
		RpcResponse<Integer> response = deviceManagerService.bindUserToDeviceFile(request);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		assertNull(errCodeEnum);
	}

	@Test
	public void testUnBindUserToDeviceFile() {
		
		SupplyDeviceFileRequest request = new SupplyDeviceFileRequest();
		getSupplyRequest(request);
		request.setUserFlag("11111111111");
		request.setUserFlagType(UserFlagTypeEnum.USERTYPE_UD);
		request.setCompanyId(1);
		request.setSn("666666666666666");
		RpcResponse<Integer> response = deviceManagerService.unBindUserToDeviceFile(request);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		assertNull(errCodeEnum);
	}

	@Test
	public void testUpdateDeviceFileManuFactureCode() {
		
		SupplyDeviceFileRequest request = new SupplyDeviceFileRequest();
		getSupplyRequest(request);
		request.setSn("666666666666666");
		request.setManufactureCode("yyyyyy");
		RpcResponse<Integer> response = deviceManagerService.updateDeviceFileManuFactureCode(request);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		assertNull(errCodeEnum);
	}

	@Test
	public void testUpdateDeviceFileSoftVersion() {

		SupplyDeviceFileRequest request = new SupplyDeviceFileRequest();
		getSupplyRequest(request);
		request.setSn("666666666666666");
		request.setSoftVersion("v2.0.1");
		RpcResponse<Integer> response = deviceManagerService.updateDeviceFileSoftVersion(request);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		assertNull(errCodeEnum);
	}

	@Test
	public void testListDeviceUpdateRecordByVehicleFlag() {
		
		SupplyDeviceFileRequest request = new SupplyDeviceFileRequest();
		getSupplyRequest(request);
		request.setSn("666666666666666");
		RpcResponse<List<DeviceUpdateRecordSv>> response = deviceManagerService.listDeviceUpdateRecordByVehicleFlag(request);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		assertNull(errCodeEnum);
	}

	@Test
	public void testListDeviceUpdateRecordByImsi() {
		
		SupplyDeviceFileRequest request = new SupplyDeviceFileRequest();
		getSupplyRequest(request);
		request.setSn("666666666666666");
		RpcResponse<List<DeviceUpdateRecordSv>> response = deviceManagerService.listDeviceUpdateRecordByImsi(request);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		assertNull(errCodeEnum);
	}

	@Test
	public void testListDeviceUpdateRecordByActiveUserFlag() {
		
		SupplyDeviceFileRequest request = new SupplyDeviceFileRequest();
		getSupplyRequest(request);
		request.setSn("666666666666666");
		RpcResponse<List<DeviceUpdateRecordSv>> response = deviceManagerService.listDeviceUpdateRecordByActiveUserFlag(request);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		assertNull(errCodeEnum);
	}

	@Test
	public void testListDeviceUpdateRecordByBindUserFlag() {
		
		SupplyDeviceFileRequest request = new SupplyDeviceFileRequest();
		getSupplyRequest(request);
		request.setSn("666666666666666");
		RpcResponse<List<DeviceUpdateRecordSv>> response = deviceManagerService.listDeviceUpdateRecordByBindUserFlag(request);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		assertNull(errCodeEnum);
	}
	
	



}
