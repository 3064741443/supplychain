package cn.com.glsx.supplychain.remote;

import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.framework.core.enums.DefaultErrorEnum;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.framework.core.util.RpcAssert;
import cn.com.glsx.supplychain.config.DataFormatProperty;
import cn.com.glsx.supplychain.enums.*;
import cn.com.glsx.supplychain.kafka.SendKafkaService;
import cn.com.glsx.supplychain.manager.SupplyChainExternalService;
import cn.com.glsx.supplychain.manager.SupplyChainRedisService;
import cn.com.glsx.supplychain.mapper.DeviceStatisReportMapper;
import cn.com.glsx.supplychain.mapper.ExsysDispatchRuleMapper;
import cn.com.glsx.supplychain.mapper.SyncResultLogMapper;
import cn.com.glsx.supplychain.model.*;
import cn.com.glsx.supplychain.model.am.EcMerchantOrder;
import cn.com.glsx.supplychain.model.bs.*;
import cn.com.glsx.supplychain.service.*;
import cn.com.glsx.supplychain.service.bs.*;
import cn.com.glsx.supplychain.util.StringUtil;
import cn.com.glsx.supplychain.util.SupplychainUtils;
import cn.com.glsx.supplychain.vo.CheckImportDataVo;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.glsx.cloudframework.core.util.BeanUtils;
import com.glsx.oms.fcservice.api.core.FlowResponse;
import com.glsx.oms.fcservice.api.entity.Flowcard;
import com.glsx.oms.fcservice.api.request.FlowCardRequest;
import com.glsx.platform.goods.common.entity.ServicePackage;
import org.apache.commons.lang.math.NumberUtils;
import org.oreframework.jms.kafka.producer.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

@Component("DeviceManagerAdminRemote")
@Service(version = "1.0.0", protocol = "dubbo", timeout = 10000)
public class DeviceManagerAdminRemoteImpl implements DeviceManagerAdminRemote {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private WareHouseInfoService wareHouseInfoService;

	@Autowired
	private DeviceTypeService deviceTypeService;

	@Autowired
	private DeviceCodeService deviceCodeService;

	@Autowired
	private DeviceInfoService deviceInfoService;

	@Autowired
	private DeviceInfoGpsPreimportService deviceInfoGpsService;

	@Autowired
	private DeviceFileService deviceFileService;

	@Autowired
	private DeviceFileSnapshotService deviceFileSnapshotService;

	@Autowired
	private DeviceCardManagerService deviceCardManagerService;

	@Autowired
	private DeviceUserManagerService deviceUserManagerService;

	@Autowired
	private DeviceUpdateRecordService deviceUpdateRecordService;

	@Autowired
	private DeviceResetRecordService deviceResetRecordService;

	@Autowired
	private DeviceVehicleManagerService deviceVehicleManagerService;

	@Autowired
	private SupplyChainExternalService externalService;

	@Autowired
	private AttribInfoService attribInfoService;

	@Autowired
	private AttribManagerSevice attribManagerService;

	@Autowired
	private SupplyChainAdminRemoteService supplayChainAdminRemoteService;

	@Autowired
	private DeviceManagerAdminRemoteService deviceManagerAdminRemoteService;

	@Autowired
	private DeviceFileVirtualService deviceFileVirtualService;

	@Autowired
	private DeviceImeiStockService deviceImeiStockService;

	@Autowired
	private KafkaProducer kafkaProducer;

	@Autowired
	private DataFormatProperty dataFormatProperty;

	@Autowired
	private SupplyChainExternalService supplyChainExternalService;

	@Autowired
	private SupplyChainRedisService redisService;

	@Autowired
	private DeviceFileUnstockService deviceFileUnstockService;

	@Autowired
	private OrderInfoService orderInfoService;

	@Autowired
	private AddressService addressService;

	@Autowired
	private LogisticsService logisticsService;

	@Autowired
	private Environment environment;

	@Autowired
	private SyncResultLogMapper syncResultLogMapper;

	@Autowired
	private DeviceStatisReportMapper deviceStatisReportMapper;

	@Autowired
	private ExsysDispatchRuleMapper exsysDispatchRuleMapper;
	
	@Autowired
	private EcMerchantOrderService ecMerchantOrderService;

	@Autowired
	private MerchantOrderService merchantOrderService;
	
	@Autowired
	private MerchantOrderDetailService merchantOrderDetailService;
	
	@Autowired
	private BsMerchantOrderVehicleService merchantOrderVehicleService;
	
	@Autowired
	private SendKafkaService kafkaService;

	@Autowired
	private BsMerchantStockDeviceService merchantStockDeviceService;

	@Autowired
	private TransferOrderService transferOrderService;

	@Override
	public RpcResponse<List<DeviceType>> listDeviceType(DeviceType record) {
		try {
			List<DeviceType> typeList = deviceTypeService
					.listDeviceType(record);
			return RpcResponse.success(typeList);
		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(), e.getMessage());
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public RpcResponse<List> listMerchantInfo(Integer targetPage,
			Integer pageSize) {
		try {
			List list = externalService.listMerchantInfo(targetPage, pageSize);
			return RpcResponse.success(list);
		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(), e.getMessage());
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public RpcResponse<List> findlistMerchantInfo(
			HashMap<String, Object> condition, Integer targetPage,
			Integer pageSize) {
		try {
			List list = externalService.listMerchantInfo(condition, targetPage,
					pageSize);
			return RpcResponse.success(list);
		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(), e.getMessage());
		}
	}

	@Override
	public RpcResponse<RpcPagination<DeviceCode>> pageDeviceCode(
			RpcPagination<DeviceCode> pagination, DeviceCode deviceCode) {
		try {
			Page<DeviceCode> page = deviceCodeService.pageDeviceCode(
					pagination, deviceCode);
			for (int i = 0; i < page.size(); i++) {
				DeviceCode item = page.get(i);
				try {
					SupplyChainMerchantInfo merchantInfo = externalService
							.findMerchantInfoByMerchantId(item.getMerchantId());
					if (!StringUtils.isEmpty(merchantInfo)) {
						item.setMerchantName(merchantInfo.getName());
					}
				} catch (RpcServiceException e) {
					logger.error("DeviceManagerAdminRemoteImpl::pageDeviceCode: e.getError="
							+ e.getError()
							+ " e.getError.getValue="
							+ e.getError().getValue());
				}
			}
			pagination = RpcPagination.copyPage(page);
			return RpcResponse.success(pagination);
		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(), e.getMessage());
		}
	}

	@Override
	public RpcResponse<List<DeviceCode>> exportDeviceCode(DeviceCode deviceCode) {
		try {
			List<DeviceCode> list = deviceCodeService
					.exportDeviceCode(deviceCode);
			for (int i = 0; i < list.size(); i++) {
				DeviceCode item = list.get(i);
				try {
					SupplyChainMerchantInfo merchantInfo = externalService
							.findMerchantInfoByMerchantId(item.getMerchantId());
					if (!StringUtils.isEmpty(merchantInfo)) {
						item.setMerchantName(merchantInfo.getName());
					}
				} catch (RpcServiceException e) {
					logger.error("DeviceManagerAdminRemoteImpl::exportDeviceCode: e.getError="
							+ e.getError()
							+ " e.getError.getValue="
							+ e.getError().getValue());
				}
			}
			return RpcResponse.success(list);
		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(), e.getMessage());
		}

	}

	@Override
	public RpcResponse<Integer> addAndUpdateDeviceCode(DeviceCode deviceCode) {
		try {
			if (!StringUtils.isEmpty(deviceCode)
					&& !StringUtils.isEmpty(deviceCode.getId())) {
				if ("Y".equals(deviceCode.getDeletedFlag())) {
					try {
						Integer count = 0;
						// 判断设备号下是否有订单
						count = supplayChainAdminRemoteService
								.countOrderInfosByDeviceCode(deviceCode.getId());
						// 判断设备号下是否有设备
						count += deviceFileService
								.countDeviceFilesByDeviceCode(deviceCode
										.getId());
						// 判断设备号下是否有虚拟设备
						count += deviceFileVirtualService
								.countDeviceFilesByDeviceCode(deviceCode
										.getId());

						// 判断设备号下是否有套餐
						List oList = externalService
								.listPackageInfoByDeviceCode(deviceCode.getId());
						if (StringUtils.isEmpty(oList)) {
							count += oList.size();
						}
						if (count != 0) {
							return RpcResponse
									.error(ErrorCodeEnum.ERRCODE_DEVICECODE_ALREADY_USED);
						}
					} catch (RpcServiceException e) {
						return RpcResponse.error(e.getError(), e.getMessage());
					}
				}

				deviceCode = deviceCodeService.updateDeviceCode(deviceCode);
			} else {
				deviceCode = deviceCodeService.addDeviceCode(deviceCode);
			}
			return RpcResponse.success(deviceCode.getId());
		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(), e.getMessage());
		}
	}

	@Override
	public RpcResponse<RpcPagination<DeviceFile>> listDeviceFile(
			RpcPagination<DeviceFile> pagination, DeviceFile deviceFile) {
		try {
			logger.info("ttttttttttttttttttttttttttttttt00:"
					+ System.currentTimeMillis());
			if (StringUtils.isEmpty(deviceFile.getSnapshot())) {
				DeviceFileSnapshot deviceFileSnapshot = new DeviceFileSnapshot();
				deviceFile.setSnapshot(deviceFileSnapshot);
			}
			List<DeviceFile> deviceFiles = new ArrayList<>();
			pagination.setList(deviceFiles);
			if (!StringUtils.isEmpty(deviceFile)
					&& !StringUtils.isEmpty(deviceFile.getDeviceCardManager())
					&& !StringUtils.isEmpty(deviceFile.getDeviceCardManager()
							.getIccid())) {
				deviceFile.getDeviceCardManager().setCompanyId(1);
				DeviceCardManager cardManager = deviceCardManagerService
						.getDeviceCardByIccid(deviceFile.getDeviceCardManager());
				if (StringUtils.isEmpty(cardManager)) {
					if (deviceFile.getDeviceCardManager().getIccid().length() == 20) {
						String strSubIccid = deviceFile.getDeviceCardManager()
								.getIccid();
						deviceFile.getDeviceCardManager().setIccid(
								strSubIccid.substring(0,
										strSubIccid.length() - 1));
						cardManager = deviceCardManagerService
								.getDeviceCardByIccid(deviceFile
										.getDeviceCardManager());
					} else if (deviceFile.getDeviceCardManager().getIccid()
							.length() == 19) {
						String strSubIccid = deviceFile.getDeviceCardManager()
								.getIccid();
						for (int i = 0; i < 10; i++) {
							String strTempIccid = strSubIccid + i;
							deviceFile.getDeviceCardManager().setIccid(
									strTempIccid);
							cardManager = deviceCardManagerService
									.getDeviceCardByIccid(deviceFile
											.getDeviceCardManager());
							if (!StringUtils.isEmpty(cardManager)) {
								break;
							}
						}
					}
				}
				if (!StringUtils.isEmpty(cardManager)) {
					deviceFile.getSnapshot().setCardId(cardManager.getId());
				} else {
					return RpcResponse.success(pagination);
				}
			}
			if (!StringUtils.isEmpty(deviceFile)
					&& !StringUtils.isEmpty(deviceFile.getDeviceCardManager())
					&& !StringUtils.isEmpty(deviceFile.getDeviceCardManager()
							.getImsi())) {
				deviceFile.getDeviceCardManager().setCompanyId(1);
				DeviceCardManager cardManager = deviceCardManagerService
						.getDeviceCardByUniqueKey(deviceFile
								.getDeviceCardManager());
				if (!StringUtils.isEmpty(cardManager)) {
					deviceFile.getSnapshot().setCardId(cardManager.getId());
				} else {
					return RpcResponse.success(pagination);
				}
			}
			if (!StringUtils.isEmpty(deviceFile)
					&& !StringUtils.isEmpty(deviceFile.getDeviceUserManager())
					&& !StringUtils.isEmpty(deviceFile.getDeviceUserManager()
							.getUserFlag())) {
				deviceFile.getDeviceUserManager().setCompanyId(1);
				deviceFile.getDeviceUserManager().setFlagType(
						UserFlagTypeEnum.USERTYPE_PH.getValue());
				DeviceUserManager userManager = deviceUserManagerService
						.getDeviceUserByUniqueKey(deviceFile
								.getDeviceUserManager());
				if (!StringUtils.isEmpty(userManager)) {
					deviceFile.getSnapshot().setUserId(userManager.getId());
				} else {
					return RpcResponse.success(pagination);
				}
			}
			// 根据设备编号查询
			if (!StringUtils.isEmpty(deviceFile)
					&& !StringUtils.isEmpty(deviceFile.getDeviceCode())) {
				DeviceCode record = deviceCodeService
						.getDeviceCodeByDeviceCode(deviceFile.getDeviceCode());
				if (!StringUtils.isEmpty(record)) {
					deviceFile.setDeviceCode(record.getDeviceCode());
				} else {
					return RpcResponse.success(pagination);
				}
			}

			if (!StringUtils.isEmpty(deviceFile)
					&& !StringUtils.isEmpty(deviceFile.getDeviceCodeTable())
					&& !StringUtils.isEmpty(deviceFile.getDeviceCodeTable()
							.getTypeId())) {
				DeviceCode record = new DeviceCode();
				record.setTypeId(deviceFile.getDeviceCodeTable().getTypeId());
				List<Integer> deviceCodes = new ArrayList<Integer>();
				List<DeviceCode> listDeviceCode = deviceCodeService
						.listDeviceCode(record);
				if (!StringUtils.isEmpty(listDeviceCode)) {
					for (DeviceCode deviceCode : listDeviceCode) {
						deviceCodes.add(deviceCode.getDeviceCode());
					}
				}
				if (deviceCodes.size() == 0) {
					return RpcResponse.success(pagination);
				}
				deviceFile.setDeviceCodes(deviceCodes);
			}

			logger.info("ttttttttttttttttttttttttttttttt01:"
					+ System.currentTimeMillis());
			Page<DeviceFile> page = deviceFileService.pageDeviceFile(
					pagination, deviceFile);
			logger.info("ttttttttttttttttttttttttttttttt03:"
					+ System.currentTimeMillis());
			List<String> snList=new ArrayList<>();
			List<String> tranOrderCodes=new ArrayList<>();
			List<MdbTransferOrderSn> transferOrderSnList=null;
			for (DeviceFile devFile:page.getResult()) {
				snList.add(devFile.getSn());
			}
			Map<String, BsMerchantStockDevice> merchantStockDeviceMap=merchantStockDeviceService.MapMerchantStockDeviceBySn(snList);
			Map<String,List<MdbTransferOrderSn>> mapTransferOrderSn=transferOrderService.mapTransferOrderSn(snList);
			for (DeviceFile devFile:page.getResult()) {
				if(!StringUtil.isEmpty(mapTransferOrderSn)&&mapTransferOrderSn.size()>0) {
					transferOrderSnList = mapTransferOrderSn.get(devFile.getSn());
				}
				if(!StringUtil.isEmpty(transferOrderSnList)&&transferOrderSnList.size()>0){
					devFile.setTranOrderCode(transferOrderSnList.get(0).getTranOrderCode());
					tranOrderCodes.add(transferOrderSnList.get(0).getTranOrderCode());
				}
			}
			Map<String, MdbTransferOrder> mdbTransferOrderMap=transferOrderService.mapMdbTransferOrder(tranOrderCodes);
			for (int i = 0; i < page.size(); i++) {
				// 获取大类名称
				DeviceFile item = page.get(i);
				if (!StringUtils.isEmpty(item.getDeviceCode())) {
					try {
						DeviceCode devicecodeTable = deviceCodeService
								.getDeviceCodeByDeviceCode(item.getDeviceCode());
						if (!StringUtils.isEmpty(devicecodeTable)) {
							item.setDeviceTypeName(deviceTypeService
									.getDeviceTypeNameById(devicecodeTable
											.getTypeId()));
						}
						item.setDeviceCodeTable(devicecodeTable);
					} catch (RpcServiceException e) {
						logger.error("DeviceManagerAdminRemoteImpl::listDeviceFile: e.getError="
								+ e.getError()
								+ " e.getError.getValue="
								+ e.getError().getValue());
					}
				}
				// 获取套餐名称
				if (!StringUtils.isEmpty(item.getPackageId())) {
					try {
						item.setPackageName(externalService
								.findPackageNameByPackageId(item.getPackageId()));
					} catch (RpcServiceException e) {
						logger.error("DeviceManagerAdminRemoteImpl::listDeviceFile: e.getError="
								+ e.getError()
								+ " e.getError.getValue="
								+ e.getError().getValue());
					}
				}
				// 获取发往商户名称和类型
				if (!StringUtils.isEmpty(item.getSendMerchantNo())) {
					try {
						SupplyChainMerchantInfo merchantInfo = externalService
								.findMerchantInfoByMerchantId(Integer
										.valueOf(item.getSendMerchantNo()));
						if (!StringUtils.isEmpty(merchantInfo)) {
							item.setSendMerchantName(merchantInfo.getName());
							item.setSendMerchantType(merchantInfo.getType());
						}
					} catch (RpcServiceException e) {
						logger.error("DeviceManagerAdminRemoteImpl::listDeviceFile: e.getError="
								+ e.getError()
								+ " e.getError.getValue="
								+ e.getError().getValue());
					}
				}

				item.setDeviceActiveUserManager(null);
				item.setDeviceUserManager(null);
				item.setDeviceCardManager(null);

				if (StringUtils.isEmpty(item.getSnapshot())) {
					continue;
				}

				// 获取当前激活用户信息
				if (!StringUtils.isEmpty(item.getSnapshot().getPackageUserId())) {
					try {
						item.setDeviceActiveUserManager(deviceUserManagerService
								.getDeviceUserById(item.getSnapshot()
										.getPackageUserId()));
					} catch (RpcServiceException e) {
						logger.error("DeviceManagerAdminRemoteImpl::listDeviceFile: e.getError="
								+ e.getError()
								+ " e.getError.getValue="
								+ e.getError().getValue());
					}
				}

				// 获取当前绑定用户信息
				if (!StringUtils.isEmpty(item.getSnapshot().getUserId())) {
					try {
						item.setDeviceUserManager(deviceUserManagerService
								.getDeviceUserById(item.getSnapshot()
										.getUserId()));
					} catch (RpcServiceException e) {
						logger.error("DeviceManagerAdminRemoteImpl::listDeviceFile: e.getError="
								+ e.getError()
								+ " e.getError.getValue="
								+ e.getError().getValue());
					}
				}

				// 获取当前流量卡信息
				if (!StringUtils.isEmpty(item.getSnapshot().getCardId())) {
					try {
						item.setDeviceCardManager(deviceCardManagerService
								.getDeviceCardById(item.getSnapshot()
										.getCardId()));
					} catch (RpcServiceException e) {
						logger.error("DeviceManagerAdminRemoteImpl::listDeviceFile: e.getError="
								+ e.getError()
								+ " e.getError.getValue="
								+ e.getError().getValue());
					}
				}

				// 补全当前流量卡信息
				if (item.getDeviceCardManager() != null
						&& StringUtils.isEmpty(item.getDeviceCardManager()
								.getIccid())) {
					try {
						FlowCardRequest flowCardRequest = new FlowCardRequest();
						String time = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss").format(new Date());
						flowCardRequest.setTime(time); // 时间
						flowCardRequest.setVersion("2.2.0"); // 版本号
						flowCardRequest.setConsumer("task-supplychain"); // 项目名称
						flowCardRequest.setInvoker("glsx");
						flowCardRequest.setKeyWord(item.getDeviceCardManager()
								.getImsi());
						FlowResponse<Flowcard> flowCardFlowResponse = supplyChainExternalService
								.getFlowCardByIccidOrImsi(flowCardRequest);

						if (!StringUtils.isEmpty(flowCardFlowResponse
								.getEntiy())) {
							item.getDeviceCardManager().setIccid(
									flowCardFlowResponse.getEntiy().getIccid());
							deviceCardManagerService.updateIccidByImsi(item
									.getDeviceCardManager());

							// logger.info("补全的流量卡信息:{}",
							// item.getDeviceCardManager());
						}
					} catch (RpcServiceException e) {
						logger.error("DeviceManagerAdminRemoteImpl::listDeviceFile: e.getError="
								+ e.getError()
								+ " e.getError.getValue="
								+ e.getError().getValue());
					}
				}
				//最后所在门店
				if(!StringUtil.isEmpty(merchantStockDeviceMap)&&merchantStockDeviceMap.size()>0) {
					if(!StringUtil.isEmpty(merchantStockDeviceMap.get(page.get(i).getSn()))) {
						page.get(i).setLastSendStore(merchantStockDeviceMap.get(page.get(i).getSn()).getToMerchantName());
					}
				}
				//最后所在服务商
				if(!StringUtil.isEmpty(mdbTransferOrderMap)&&mdbTransferOrderMap.size()>0) {
					if(!StringUtil.isEmpty(mdbTransferOrderMap.get(page.get(i).getTranOrderCode()))) {
						page.get(i).setLastServiceProvider(mdbTransferOrderMap.get(page.get(i).getTranOrderCode()).getInMerchantName());
					}
				}else{
					page.get(i).setLastServiceProvider(page.get(i).getSendMerchantName());
				}
			}

			logger.info("ttttttttttttttttttttttttttttttt04:"
					+ System.currentTimeMillis());
			pagination = RpcPagination.copyPage(page);
			return RpcResponse.success(pagination);
		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(), e.getMessage());
		}
	}

	private DeviceCode getDeviceCode(Map<Integer, DeviceCode> mapDeviceCode,
			Integer deviceCode) {
		DeviceCode result = null;
		try {
			result = mapDeviceCode.get(deviceCode);
			if (!StringUtils.isEmpty(result)) {
				return result;
			}
			result = deviceCodeService.getDeviceCodeByDeviceCode(deviceCode);
			if (!StringUtils.isEmpty(result)) {
				mapDeviceCode.put(deviceCode, result);
			}
		} catch (RpcServiceException e) {
			logger.error("DeviceManagerAdminRemoteImpl::getDeviceCode: e.getError="
					+ e.getError()
					+ " e.getError.getValue="
					+ e.getError().getValue());
		}
		return result;
	}

	private String getDeviceTypeName(Map<Integer, String> mapDeviceType,
			Integer deviceType) {
		String result = null;
		try {
			result = mapDeviceType.get(deviceType);
			if (!StringUtils.isEmpty(result)) {
				return result;
			}
			result = deviceTypeService.getDeviceTypeNameById(deviceType);
			if (!StringUtils.isEmpty(result)) {
				mapDeviceType.put(deviceType, result);
			}
		} catch (RpcServiceException e) {
			logger.error("DeviceManagerAdminRemoteImpl::getDeviceTypeName: e.getError="
					+ e.getError()
					+ " e.getError.getValue="
					+ e.getError().getValue());
		}
		return result;
	}

	private String getPackage(Map<Integer, String> mapPackage,
			Integer devPackage) {
		String result = null;
		try {
			result = mapPackage.get(devPackage);
			if (!StringUtils.isEmpty(result)) {
				return result;
			}
			result = externalService.findPackageNameByPackageId(devPackage);
			if (!StringUtils.isEmpty(result)) {
				mapPackage.put(devPackage, result);
			}
		} catch (RpcServiceException e) {
			logger.error("DeviceManagerAdminRemoteImpl::getPackage: e.getError="
					+ e.getError()
					+ " e.getError.getValue="
					+ e.getError().getValue());
		}
		return result;
	}

	private SupplyChainMerchantInfo getMerchantInfo(
			Map<Integer, SupplyChainMerchantInfo> mapMerchant,
			Integer merchantNo) {
		SupplyChainMerchantInfo result = null;
		try {
			result = mapMerchant.get(merchantNo);
			if (!StringUtils.isEmpty(result)) {
				return result;
			}
			result = externalService.findMerchantInfoByMerchantId(merchantNo);
			if (!StringUtils.isEmpty(result)) {
				mapMerchant.put(merchantNo, result);
			}
		} catch (RpcServiceException e) {
			logger.error("DeviceManagerAdminRemoteImpl::getMerchantInfo: e.getError="
					+ e.getError()
					+ " e.getError.getValue="
					+ e.getError().getValue());
		}
		return result;
	}

	@Override
	public RpcResponse<List<DeviceFile>> exportDeviceFile(DeviceFile deviceFile) {
		try {
			Map<Integer, DeviceCode> mapDeviceCode = new HashMap<Integer, DeviceCode>();
			Map<Integer, String> mapDeviceType = new HashMap<Integer, String>();
			Map<Integer, String> mapPackage = new HashMap<Integer, String>();
			Map<Integer, SupplyChainMerchantInfo> mapMerchant = new HashMap<Integer, SupplyChainMerchantInfo>();
			if (StringUtils.isEmpty(deviceFile.getSnapshot())) {
				DeviceFileSnapshot deviceFileSnapshot = new DeviceFileSnapshot();
				deviceFile.setSnapshot(deviceFileSnapshot);
			}
			if (!StringUtils.isEmpty(deviceFile)
					&& !StringUtils.isEmpty(deviceFile.getDeviceCardManager())
					&& !StringUtils.isEmpty(deviceFile.getDeviceCardManager()
							.getIccid())) {
				deviceFile.getDeviceCardManager().setCompanyId(1);
				DeviceCardManager cardManager = deviceCardManagerService
						.getDeviceCardByIccid(deviceFile.getDeviceCardManager());
				if (StringUtils.isEmpty(cardManager)) {
					if (deviceFile.getDeviceCardManager().getIccid().length() == 20) {
						String strSubIccid = deviceFile.getDeviceCardManager()
								.getIccid();
						deviceFile.getDeviceCardManager().setIccid(
								strSubIccid.substring(0,
										strSubIccid.length() - 1));
						cardManager = deviceCardManagerService
								.getDeviceCardByIccid(deviceFile
										.getDeviceCardManager());
					} else if (deviceFile.getDeviceCardManager().getIccid()
							.length() == 19) {
						String strSubIccid = deviceFile.getDeviceCardManager()
								.getIccid();
						for (int i = 0; i < 10; i++) {
							String strTempIccid = strSubIccid + i;
							deviceFile.getDeviceCardManager().setIccid(
									strTempIccid);
							cardManager = deviceCardManagerService
									.getDeviceCardByIccid(deviceFile
											.getDeviceCardManager());
							if (!StringUtils.isEmpty(cardManager)) {
								break;
							}
						}
					}
				}
				if (!StringUtils.isEmpty(cardManager)) {
					deviceFile.getSnapshot().setCardId(cardManager.getId());
				}
			}
			if (!StringUtils.isEmpty(deviceFile)
					&& !StringUtils.isEmpty(deviceFile.getDeviceCardManager())
					&& !StringUtils.isEmpty(deviceFile.getDeviceCardManager()
							.getImsi())) {
				deviceFile.getDeviceCardManager().setCompanyId(1);
				DeviceCardManager cardManager = deviceCardManagerService
						.getDeviceCardByUniqueKey(deviceFile
								.getDeviceCardManager());
				if (!StringUtils.isEmpty(cardManager)) {
					deviceFile.getSnapshot().setCardId(cardManager.getId());
				}
			}
			if (!StringUtils.isEmpty(deviceFile)
					&& !StringUtils.isEmpty(deviceFile.getDeviceUserManager())
					&& !StringUtils.isEmpty(deviceFile.getDeviceUserManager()
							.getUserFlag())) {
				deviceFile.getDeviceUserManager().setCompanyId(1);
				deviceFile.getDeviceUserManager().setFlagType(
						UserFlagTypeEnum.USERTYPE_PH.getValue());
				DeviceUserManager userManager = deviceUserManagerService
						.getDeviceUserByUniqueKey(deviceFile
								.getDeviceUserManager());
				if (!StringUtils.isEmpty(userManager)) {
					deviceFile.getSnapshot().setUserId(userManager.getId());
				}
			}
			// 根据设备编号查询
			if (!StringUtils.isEmpty(deviceFile)
					&& !StringUtils.isEmpty(deviceFile.getDeviceCode())) {
				DeviceCode record = deviceCodeService
						.getDeviceCodeByDeviceCode(deviceFile.getDeviceCode());
				if (!StringUtils.isEmpty(record)) {
					deviceFile.setDeviceCode(record.getDeviceCode());
				}
			}

			if (!StringUtils.isEmpty(deviceFile)
					&& !StringUtils.isEmpty(deviceFile.getDeviceCodeTable())
					&& !StringUtils.isEmpty(deviceFile.getDeviceCodeTable()
							.getTypeId())) {
				DeviceCode record = new DeviceCode();
				record.setTypeId(deviceFile.getDeviceCodeTable().getTypeId());
				List<Integer> deviceCodes = new ArrayList<Integer>();
				List<DeviceCode> listDeviceCode = deviceCodeService
						.listDeviceCode(record);
				if (!StringUtils.isEmpty(listDeviceCode)) {
					for (DeviceCode deviceCode : listDeviceCode) {
						deviceCodes.add(deviceCode.getDeviceCode());
					}
				}
				deviceFile.setDeviceCodes(deviceCodes);
			}
			List<DeviceFile> list = deviceFileService
					.exportDeviceFile(deviceFile);

			for (DeviceFile item : list) {
				DeviceCode devicecodeTable = this.getDeviceCode(mapDeviceCode,
						item.getDeviceCode());
				if (!StringUtils.isEmpty(devicecodeTable)) {
					item.setDeviceTypeName(this.getDeviceTypeName(
							mapDeviceType, devicecodeTable.getTypeId()));
				}

				item.setPackageName(this.getPackage(mapPackage,
						item.getPackageId()));

				if (!StringUtils.isEmpty(item.getSendMerchantNo())) {
					SupplyChainMerchantInfo merchantInfo = this
							.getMerchantInfo(mapMerchant,
									Integer.valueOf(item.getSendMerchantNo()));
					if (!StringUtils.isEmpty(merchantInfo)) {
						item.setSendMerchantName(merchantInfo.getName());
						item.setSendMerchantType(merchantInfo.getType());
					}
				}

				if (!StringUtils.isEmpty(item.getOperatorMerchantNo())) {
					SupplyChainMerchantInfo merchantInfo = this
							.getMerchantInfo(mapMerchant, Integer.valueOf(item
									.getOperatorMerchantNo()));
					if (!StringUtils.isEmpty(merchantInfo)) {
						item.setOperatorMerchantName(merchantInfo.getName());
						item.setOperatorMerchantType(merchantInfo.getType());
					}
				}
			}

			/*
			 * for (DeviceFile item : list) { //获取大类名称 if
			 * (!StringUtils.isEmpty(item.getDeviceCode())) { try { DeviceCode
			 * devicecodeTable =
			 * deviceCodeService.getDeviceCodeByDeviceCode(item
			 * .getDeviceCode()); if (!StringUtils.isEmpty(devicecodeTable)) {
			 * item.setDeviceTypeName(deviceTypeService.getDeviceTypeNameById(
			 * devicecodeTable.getTypeId())); } } catch (RpcServiceException e)
			 * { logger.error(
			 * "DeviceManagerAdminRemoteImpl::exportDeviceFile: e.getError=" +
			 * e.getError() + " e.getError.getValue=" +
			 * e.getError().getValue()); } }
			 * 
			 * //获取套餐名称 if (!StringUtils.isEmpty(item.getPackageId())) { try {
			 * item
			 * .setPackageName(externalService.findPackageNameByPackageId(item
			 * .getPackageId())); } catch (RpcServiceException e) {
			 * logger.error(
			 * "DeviceManagerAdminRemoteImpl::exportDeviceFile: e.getError=" +
			 * e.getError() + " e.getError.getValue=" +
			 * e.getError().getValue()); } }
			 * 
			 * //获取发往商户名称和类型 if (!StringUtils.isEmpty(item.getSendMerchantNo()))
			 * { try { SupplyChainMerchantInfo merchantInfo =
			 * externalService.findMerchantInfoByMerchantId
			 * (Integer.valueOf(item.getSendMerchantNo())); if
			 * (!StringUtils.isEmpty(merchantInfo)) {
			 * item.setSendMerchantName(merchantInfo.getName());
			 * item.setSendMerchantType(merchantInfo.getType()); } } catch
			 * (RpcServiceException e) { logger.error(
			 * "DeviceManagerAdminRemoteImpl::exportDeviceFile: e.getError=" +
			 * e.getError() + " e.getError.getValue=" +
			 * e.getError().getValue()); } }
			 * 
			 * //获取运营商户名称和类型 if
			 * (!StringUtils.isEmpty(item.getOperatorMerchantNo())) { try {
			 * SupplyChainMerchantInfo merchantInfo =
			 * externalService.findMerchantInfoByMerchantId
			 * (Integer.valueOf(item.getOperatorMerchantNo())); if
			 * (!StringUtils.isEmpty(merchantInfo)) {
			 * item.setOperatorMerchantName(merchantInfo.getName());
			 * item.setOperatorMerchantType(merchantInfo.getType()); } } catch
			 * (RpcServiceException e) { logger.error(
			 * "DeviceManagerAdminRemoteImpl::exportDeviceFile: e.getError=" +
			 * e.getError() + " e.getError.getValue=" +
			 * e.getError().getValue()); } }
			 * 
			 * //获取出库版本号 if (!StringUtils.isEmpty(item.getFirmwareId())) { try {
			 * item
			 * .setSoftVersion(supplayChainAdminRemoteService.getFirmwareInfoById
			 * (item.getFirmwareId()).getSoftVersion()); } catch
			 * (RpcServiceException e) { logger.error(
			 * "DeviceManagerAdminRemoteImpl::exportDeviceFile: e.getError=" +
			 * e.getError() + " e.getError.getValue=" +
			 * e.getError().getValue()); } }
			 * 
			 * 
			 * 
			 * //获取模块类型 if (!StringUtils.isEmpty(item.getOrderCode())) { try {
			 * OrderInfo orderInfo =
			 * supplayChainAdminRemoteService.getOrderInfoByOrderCode
			 * (item.getOrderCode()); if (!StringUtils.isEmpty(orderInfo) &&
			 * !StringUtils.isEmpty(orderInfo.getAttribMana())) { Integer model
			 * = orderInfo.getAttribMana().getModel(); if(model != null){
			 * item.setModelName
			 * (attribInfoService.getAttribInfoNameById(model)); } } } catch
			 * (RpcServiceException e) { logger.error(
			 * "DeviceManagerAdminRemoteImpl::exportDeviceFile: e.getError=" +
			 * e.getError() + " e.getError.getValue=" +
			 * e.getError().getValue()); } }
			 * 
			 * if (StringUtils.isEmpty(item.getSnapshot())) { continue; }
			 * 
			 * //获取当前版本号 if
			 * (!StringUtils.isEmpty(item.getSnapshot().getFirmwareId())) { try
			 * {
			 * item.getSnapshot().setSoftVersion(supplayChainAdminRemoteService
			 * .getFirmwareInfoById
			 * (item.getSnapshot().getFirmwareId()).getSoftVersion()); } catch
			 * (RpcServiceException e) { logger.error(
			 * "DeviceManagerAdminRemoteImpl::exportDeviceFile: e.getError=" +
			 * e.getError() + " e.getError.getValue=" +
			 * e.getError().getValue()); } } }
			 */
			return RpcResponse.success(list);
		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(), e.getMessage());
		}
	}

	@Override
	public RpcResponse<DeviceFile> getDeviceFileById(DeviceFile deviceFile) {

		try {
			DeviceFile retFile = deviceFileService.getDeviceFileById(deviceFile
					.getId());
			if (StringUtils.isEmpty(retFile)) {
				return RpcResponse.success(retFile);
			}
			// 获取快照关系
			DeviceFileSnapshot snapShot = deviceFileSnapshotService
					.getDeviceFileSnapshotBySn(retFile.getSn());
			if (StringUtils.isEmpty(snapShot)) {
				// 默认插入一条设备数据 必须有一条关系数据
				return RpcResponse
						.error(ErrorCodeEnum.ERRCODE_SYSTEM_RECORD_UNUSUAL);
			}
			retFile.setSnapshot(snapShot);

			// 获取当前流量卡信息
			try {
				DeviceCardManager deviceCardManager = deviceCardManagerService
						.getDeviceCardById(snapShot.getCardId());
				retFile.setDeviceCardManager(deviceCardManager);
			} catch (RpcServiceException e) {
				logger.info("DeviceManagerAdminRemoteImpl::getDeviceFileById e.getError="
						+ e.getError()
						+ " e.getError.getValue="
						+ e.getError().getValue());
			}

			// 获取入库流量卡信息
			try {
				DeviceCardManager deviceCardManager = deviceCardManagerService
						.getDeviceCardById(retFile.getCardId());
				retFile.setInitialCardManager(deviceCardManager);
			} catch (RpcServiceException e) {
				logger.info("DeviceManagerAdminRemoteImpl::getDeviceFileById e.getError="
						+ e.getError()
						+ " e.getError.getValue="
						+ e.getError().getValue());
			}

			// 获取设备编号信息
			try {
				DeviceCode deviceCode = deviceCodeService
						.getDeviceCodeByDeviceCode(retFile.getDeviceCode());
				retFile.setDeviceCodeTable(deviceCode);
			} catch (RpcServiceException e) {
				logger.info("DeviceManagerAdminRemoteImpl::getDeviceFileById e.getError="
						+ e.getError()
						+ " e.getError.getValue="
						+ e.getError().getValue());
			}

			// 获取当前激活用户信息
			try {
				DeviceUserManager deviceUser = deviceUserManagerService
						.getDeviceUserById(snapShot.getPackageUserId());
				retFile.setDeviceActiveUserManager(deviceUser);
			} catch (RpcServiceException e) {
				logger.info("DeviceManagerAdminRemoteImpl::getDeviceFileById e.getError="
						+ e.getError()
						+ " e.getError.getValue="
						+ e.getError().getValue());
			}

			// 获取当前绑定用户信息
			try {
				DeviceUserManager deviceUser = deviceUserManagerService
						.getDeviceUserById(snapShot.getUserId());
				retFile.setDeviceUserManager(deviceUser);
			} catch (RpcServiceException e) {
				logger.info("DeviceManagerAdminRemoteImpl::getDeviceFileById e.getError="
						+ e.getError()
						+ " e.getError.getValue="
						+ e.getError().getValue());
			}

			// 获取出库版本软件版本号
			try {
				Integer firmwareId = retFile.getFirmwareId();
				FirmwareInfo firmwareInfo = supplayChainAdminRemoteService
						.getFirmwareInfoById(firmwareId);
				retFile.setSoftVersion(firmwareInfo.getSoftVersion());
			} catch (RpcServiceException e) {
				logger.error("DeviceManagerAdminRemoteImpl::getDeviceFileById: e.getError="
						+ e.getError()
						+ " e.getError.getValue="
						+ e.getError().getValue());
			}

			// 获取当前版本软件版本号
			try {
				Integer firmwareId = snapShot.getFirmwareId();
				FirmwareInfo firmwareInfo = supplayChainAdminRemoteService
						.getFirmwareInfoById(firmwareId);
				snapShot.setSoftVersion(firmwareInfo.getSoftVersion());
			} catch (RpcServiceException e) {
				logger.error("DeviceManagerAdminRemoteImpl::getDeviceFileById: e.getError="
						+ e.getError()
						+ " e.getError.getValue="
						+ e.getError().getValue());
			}

			// 获取入库流量卡上网卡号
			if (!StringUtils.isEmpty(retFile.getInitialCardManager())) {
				String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.format(new Date());
				FlowCardRequest flowCardRequest = new FlowCardRequest();
				String strKeyword = (!StringUtils.isEmpty(retFile
						.getInitialCardManager().getIccid())) ? retFile
						.getInitialCardManager().getIccid().toUpperCase()
						: retFile.getInitialCardManager().getImsi();
				flowCardRequest.setKeyWord(strKeyword);
				flowCardRequest.setVersion("2.2.0");
				flowCardRequest.setTime(time);
				flowCardRequest.setInvoker("glsx");
				flowCardRequest.setConsumer("task-supplychain");

				try {
					FlowResponse<Flowcard> response = externalService
							.getFlowCardByIccidOrImsi(flowCardRequest);
					Flowcard flowCard = response.getEntiy();
					logger.error("DeviceManagerAdminRemoteImpl::getDeviceFileById: 通过iccid获取流量卡信息 flowCardRequest="
							+ flowCardRequest.toString()
							+ "\n response:"
							+ response.toString());
					if (!StringUtils.isEmpty(flowCard)) {
						retFile.getInitialCardManager().setCardNo(
								flowCard.getCardNo());
					}
				} catch (RpcServiceException e) {
					logger.error("DeviceManagerAdminRemoteImpl::getDeviceFileById: e.getError="
							+ e.getError()
							+ " e.getError.getValue="
							+ e.getError().getValue());
				}
			}

			// 获取设备类型名称
			try {
				if (!StringUtils.isEmpty(retFile.getDeviceCodeTable())) {
					String strDeviceTypeName = deviceTypeService
							.getDeviceTypeNameById(retFile.getDeviceCodeTable()
									.getTypeId());
					retFile.setDeviceTypeName(strDeviceTypeName);
				} else {
					retFile.setDeviceTypeName("");
				}

			} catch (RpcServiceException e) {
				logger.error("DeviceManagerAdminRemoteImpl::getDeviceFileById: e.getError="
						+ e.getError()
						+ " e.getError.getValue="
						+ e.getError().getValue());
			}

			// 获取品牌定制商名称
			try {
				if (!StringUtils.isEmpty(retFile.getDeviceCodeTable())) {
					Integer merchantId = retFile.getDeviceCodeTable()
							.getMerchantId();
					SupplyChainMerchantInfo merchantInfo = externalService
							.findMerchantInfoByMerchantId(merchantId);
					if (!StringUtils.isEmpty(merchantInfo)) {
						retFile.getDeviceCodeTable().setMerchantName(
								merchantInfo.getName());
					}
				}
			} catch (RpcServiceException e) {
				logger.error("DeviceManagerAdminRemoteImpl::getDeviceFileById: e.getError="
						+ e.getError()
						+ " e.getError.getValue="
						+ e.getError().getValue());
			}

			// 获取入库商品名称
			try {
				Integer packageId = retFile.getPackageId();
				String packageName = externalService
						.findPackageNameByPackageId(packageId);
				retFile.setPackageName(packageName);
			} catch (RpcServiceException e) {
				logger.error("DeviceManagerAdminRemoteImpl::getDeviceFileById: e.getError="
						+ e.getError()
						+ " e.getError.getValue="
						+ e.getError().getValue());
			}

			// 获取运营商商户名称
			try {
				String operatorMerchantNo = retFile.getOperatorMerchantNo();
				if (!StringUtils.isEmpty(operatorMerchantNo)) {
					SupplyChainMerchantInfo merchantInfo = externalService
							.findMerchantInfoByMerchantId(Integer
									.valueOf(operatorMerchantNo));
					if (!StringUtils.isEmpty(merchantInfo)) {
						retFile.setOperatorMerchantName(merchantInfo.getName());
					}
				}
			} catch (RpcServiceException e) {
				logger.error("DeviceManagerAdminRemoteImpl::getDeviceFileById: e.getError="
						+ e.getError()
						+ " e.getError.getValue="
						+ e.getError().getValue());
			}

			// 获取发往商户名称
			try {
				String sendMerchantNo = retFile.getSendMerchantNo();
				if (!StringUtils.isEmpty(sendMerchantNo)) {
					SupplyChainMerchantInfo merchantInfo = externalService
							.findMerchantInfoByMerchantId(Integer
									.valueOf(sendMerchantNo));
					if (!StringUtils.isEmpty(merchantInfo)) {
						retFile.setSendMerchantName(merchantInfo.getName());
					}
				}
			} catch (RpcServiceException e) {
				logger.error("DeviceManagerAdminRemoteImpl::getDeviceFileById: e.getError="
						+ e.getError()
						+ " e.getError.getValue="
						+ e.getError().getValue());
			}

			return RpcResponse.success(retFile);
		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(), e.getMessage());
		}
	}

	@Override
	public RpcResponse<RpcPagination<DeviceUpdateRecord>> listDeviceUpdateRecord(
			RpcPagination<DeviceUpdateRecord> pagination,
			DeviceUpdateRecord deviceUpdateRecord) {
		try {
			Page<DeviceUpdateRecord> page = deviceUpdateRecordService
					.pageDeviceUpdateRecord(pagination, deviceUpdateRecord);
			for (int i = 0; i < page.size(); i++) {
				DeviceUpdateRecord item = page.get(i);
				UpdateRecordEnum flagType = UpdateRecordEnum.fromString(item
						.getFlagType());
				switch (flagType) {
				case UPDATE_RECORD_CARD: {
					try {
						DeviceCardManager deviceCard = deviceCardManagerService
								.getDeviceCardById(item.getFlagId());
						if (!StringUtils.isEmpty(deviceCard)) {
							item.setFlagName(deviceCard.getImsi());
						}
					} catch (RpcServiceException e) {
						logger.error("DeviceManagerAdminRemoteImpl::listDeviceUpdateRecord: e.getError="
								+ e.getError()
								+ " e.getError.getValue="
								+ e.getError().getValue());
					}

					try {
						DeviceCardManager deviceCard = deviceCardManagerService
								.getDeviceCardById(item.getPreFlagId());
						if (!StringUtils.isEmpty(deviceCard)) {
							item.setPreFlagName(deviceCard.getImsi());
						}
					} catch (RpcServiceException e) {
						logger.error("DeviceManagerAdminRemoteImpl::listDeviceUpdateRecord: e.getError="
								+ e.getError()
								+ " e.getError.getValue="
								+ e.getError().getValue());
					}
				}
					break;
				case UPDATE_RECORD_ACTI:
				case UPDATE_RECORD_USER: {
					try {
						DeviceUserManager deviceUser = deviceUserManagerService
								.getDeviceUserById(item.getFlagId());
						if (!StringUtils.isEmpty(deviceUser)) {
							item.setFlagName(deviceUser.getUserFlag());
						}
					} catch (RpcServiceException e) {
						logger.error("DeviceManagerAdminRemoteImpl::listDeviceUpdateRecord: e.getError="
								+ e.getError()
								+ " e.getError.getValue="
								+ e.getError().getValue());
					}

					try {
						DeviceUserManager deviceUser = deviceUserManagerService
								.getDeviceUserById(item.getPreFlagId());
						if (!StringUtils.isEmpty(deviceUser)) {
							item.setPreFlagName(deviceUser.getUserFlag());
						}
					} catch (RpcServiceException e) {
						logger.error("DeviceManagerAdminRemoteImpl::listDeviceUpdateRecord: e.getError="
								+ e.getError()
								+ " e.getError.getValue="
								+ e.getError().getValue());
					}
				}
					break;
				case UPDATE_RECORD_VEHI: {
					try {
						DeviceVehicleManager deviceVehicle = deviceVehicleManagerService
								.getDeviceVehicleById(item.getFlagId());
						if (!StringUtils.isEmpty(deviceVehicle)) {
							item.setFlagName(deviceVehicle.getVehicleFlag());
						}
					} catch (RpcServiceException e) {
						logger.error("DeviceManagerAdminRemoteImpl::listDeviceUpdateRecord: e.getError="
								+ e.getError()
								+ " e.getError.getValue="
								+ e.getError().getValue());
					}

					try {
						DeviceVehicleManager deviceVehicle = deviceVehicleManagerService
								.getDeviceVehicleById(item.getPreFlagId());
						if (!StringUtils.isEmpty(deviceVehicle)) {
							item.setPreFlagName(deviceVehicle.getVehicleFlag());
						}
					} catch (RpcServiceException e) {
						logger.error("DeviceManagerAdminRemoteImpl::listDeviceUpdateRecord: e.getError="
								+ e.getError()
								+ " e.getError.getValue="
								+ e.getError().getValue());
					}
				}
					break;
				case UPDATE_RECORD_FIRM: {
					try {
						FirmwareInfo firmwareInfo = supplayChainAdminRemoteService
								.getFirmwareInfoById(item.getFlagId());
						if (!StringUtils.isEmpty(firmwareInfo)) {
							item.setFlagName(firmwareInfo.getSoftVersion());
						}
					} catch (RpcServiceException e) {
						logger.error("DeviceManagerAdminRemoteImpl::listDeviceUpdateRecord: e.getError="
								+ e.getError()
								+ " e.getError.getValue="
								+ e.getError().getValue());
					}

					try {
						FirmwareInfo firmwareInfo = supplayChainAdminRemoteService
								.getFirmwareInfoById(item.getPreFlagId());
						if (!StringUtils.isEmpty(firmwareInfo)) {
							item.setPreFlagName(firmwareInfo.getSoftVersion());
						}
					} catch (RpcServiceException e) {
						logger.error("DeviceManagerAdminRemoteImpl::listDeviceUpdateRecord: e.getError="
								+ e.getError()
								+ " e.getError.getValue="
								+ e.getError().getValue());
					}
				}
					break;
				case UPDATE_RECORD_PACK: {
					item.setPreFlagName(String.valueOf(item.getPreFlagId()));
					item.setFlagName(String.valueOf(item.getFlagId()));
				}
					break;
				default:
					break;
				}
			}
			pagination = RpcPagination.copyPage(page);
			return RpcResponse.success(pagination);
		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(), e.getMessage());
		}
	}

	@Override
	public RpcResponse<List<DeviceUpdateRecord>> exportDeviceUpdateRecord(
			DeviceUpdateRecord deviceUpdateRecord) {

		try {
			List<DeviceUpdateRecord> list = deviceUpdateRecordService
					.exportDeviceUpdateRecord(deviceUpdateRecord);
			for (DeviceUpdateRecord item : list) {
				UpdateRecordEnum flagType = UpdateRecordEnum.fromString(item
						.getFlagType());
				switch (flagType) {
				case UPDATE_RECORD_CARD: {
					try {
						DeviceCardManager deviceCard = deviceCardManagerService
								.getDeviceCardById(item.getFlagId());
						if (!StringUtils.isEmpty(deviceCard)) {
							item.setFlagName(deviceCard.getImsi());
						}
					} catch (RpcServiceException e) {
						logger.error("DeviceManagerAdminRemoteImpl::listDeviceUpdateRecord: e.getError="
								+ e.getError()
								+ " e.getError.getValue="
								+ e.getError().getValue());
					}

					try {
						DeviceCardManager deviceCard = deviceCardManagerService
								.getDeviceCardById(item.getPreFlagId());
						if (!StringUtils.isEmpty(deviceCard)) {
							item.setPreFlagName(deviceCard.getImsi());
						}
					} catch (RpcServiceException e) {
						logger.error("DeviceManagerAdminRemoteImpl::listDeviceUpdateRecord: e.getError="
								+ e.getError()
								+ " e.getError.getValue="
								+ e.getError().getValue());
					}
				}
					break;
				case UPDATE_RECORD_ACTI:
				case UPDATE_RECORD_USER: {
					try {
						DeviceUserManager deviceUser = deviceUserManagerService
								.getDeviceUserById(item.getFlagId());
						if (!StringUtils.isEmpty(deviceUser)) {
							item.setFlagName(deviceUser.getUserFlag());
						}
					} catch (RpcServiceException e) {
						logger.error("DeviceManagerAdminRemoteImpl::listDeviceUpdateRecord: e.getError="
								+ e.getError()
								+ " e.getError.getValue="
								+ e.getError().getValue());
					}

					try {
						DeviceUserManager deviceUser = deviceUserManagerService
								.getDeviceUserById(item.getPreFlagId());
						if (!StringUtils.isEmpty(deviceUser)) {
							item.setPreFlagName(deviceUser.getUserFlag());
						}
					} catch (RpcServiceException e) {
						logger.error("DeviceManagerAdminRemoteImpl::listDeviceUpdateRecord: e.getError="
								+ e.getError()
								+ " e.getError.getValue="
								+ e.getError().getValue());
					}
				}
					break;
				case UPDATE_RECORD_VEHI: {
					try {
						DeviceVehicleManager deviceVehicle = deviceVehicleManagerService
								.getDeviceVehicleById(item.getFlagId());
						if (!StringUtils.isEmpty(deviceVehicle)) {
							item.setFlagName(deviceVehicle.getVehicleFlag());
						}
					} catch (RpcServiceException e) {
						logger.error("DeviceManagerAdminRemoteImpl::listDeviceUpdateRecord: e.getError="
								+ e.getError()
								+ " e.getError.getValue="
								+ e.getError().getValue());
					}

					try {
						DeviceVehicleManager deviceVehicle = deviceVehicleManagerService
								.getDeviceVehicleById(item.getPreFlagId());
						if (!StringUtils.isEmpty(deviceVehicle)) {
							item.setPreFlagName(deviceVehicle.getVehicleFlag());
						}
					} catch (RpcServiceException e) {
						logger.error("DeviceManagerAdminRemoteImpl::listDeviceUpdateRecord: e.getError="
								+ e.getError()
								+ " e.getError.getValue="
								+ e.getError().getValue());
					}
				}
					break;
				case UPDATE_RECORD_FIRM: {
					try {
						FirmwareInfo firmwareInfo = supplayChainAdminRemoteService
								.getFirmwareInfoById(item.getFlagId());
						if (!StringUtils.isEmpty(firmwareInfo)) {
							item.setFlagName(firmwareInfo.getSoftVersion());
						}
					} catch (RpcServiceException e) {
						logger.error("DeviceManagerAdminRemoteImpl::listDeviceUpdateRecord: e.getError="
								+ e.getError()
								+ " e.getError.getValue="
								+ e.getError().getValue());
					}

					try {
						FirmwareInfo firmwareInfo = supplayChainAdminRemoteService
								.getFirmwareInfoById(item.getPreFlagId());
						if (!StringUtils.isEmpty(firmwareInfo)) {
							item.setPreFlagName(firmwareInfo.getSoftVersion());
						}
					} catch (RpcServiceException e) {
						logger.error("DeviceManagerAdminRemoteImpl::listDeviceUpdateRecord: e.getError="
								+ e.getError()
								+ " e.getError.getValue="
								+ e.getError().getValue());
					}
				}
					break;
				case UPDATE_RECORD_PACK:
					item.setPreFlagName(StringUtils.isEmpty(item.getPreFlagId()) ? null
							: String.valueOf(item.getPreFlagId()));
					item.setFlagName(StringUtils.isEmpty(item.getFlagId()) ? null
							: String.valueOf(item.getFlagId()));
					break;
				default:
					break;
				}
			}
			return RpcResponse.success(list);
		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(), e.getMessage());
		}
	}

	@Override
	public RpcResponse<RpcPagination<DeviceResetRecord>> listDeviceResetRecord(
			RpcPagination<DeviceResetRecord> pagination,
			DeviceResetRecord deviceResetRecord) {

		try {
			Page<DeviceResetRecord> page = deviceResetRecordService
					.pageDeviceResetRecord(pagination, deviceResetRecord);
			pagination = RpcPagination.copyPage(page);
			return RpcResponse.success(pagination);
		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(), e.getMessage());
		}
	}

	@Override
	public RpcResponse<List<DeviceResetRecord>> exportDeviceResetRecord(
			DeviceResetRecord deviceResetRecord) {
		try {
			List<DeviceResetRecord> list = deviceResetRecordService
					.exportDeviceResetRecord(deviceResetRecord);
			return RpcResponse.success(list);
		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(), e.getMessage());
		}
	}

	@Override
	public RpcResponse<Integer> addAndUpdateDeviceResetRecord(
			DeviceResetRecord deviceResetRecord) {
		try {
			if (!StringUtils.isEmpty(deviceResetRecord)
					&& !StringUtils.isEmpty(deviceResetRecord.getId())) {
				deviceResetRecord.setUpdatedDate(new Date());
				deviceResetRecord = deviceResetRecordService
						.updateDeviceResetRecord(deviceResetRecord);
			} else {
				deviceResetRecord = deviceManagerAdminRemoteService
						.initDeviceFileByDeviceResetRecord(deviceResetRecord);
				redisService.delDeviceFile(deviceResetRecord.getSn());
				if (deviceResetRecord != null
						&& deviceResetRecord.getSn() != null) {
					logger.info("kafka发送初始化消息的sn：" + deviceResetRecord.getSn());
					HashMap<String, String> params = new HashMap<String, String>();
					params.put("sn", deviceResetRecord.getSn());
					kafkaProducer.sendObject(JSONObject.toJSONString(params)
							.getBytes());
					logger.info("kafka发送初始化消息结束的sn："
							+ deviceResetRecord.getSn());

					// 发送到web-supplychain转发http消息到第三方业务系统
					HashMap<String, Object> dipatchs = new HashMap<String, Object>();
					dipatchs.put("sn", deviceResetRecord.getSn());
					dipatchs.put("operatorname",
							deviceResetRecord.getCreatedBy());
					dipatchs.put("timestamp", deviceResetRecord
							.getUpdatedDate().getTime());
					dipatchs.put("remark", deviceResetRecord.getRemark());
					dipatchs.put("systemFlag", "FMS");
					kafkaProducer.sendObject("dispatch_initdevice_to_exsystem",
							JSONObject.toJSONString(dipatchs).getBytes());

					dipatchs.put("systemFlag", "CGD");
					kafkaProducer.sendObject("dispatch_initdevice_to_exsystem",
							JSONObject.toJSONString(dipatchs).getBytes());

				} else {
					logger.error("kafka发送初始化消息发送失败对象：{}", deviceResetRecord);
				}

			}
			return RpcResponse.success(deviceResetRecord.getId());
		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(), e.getMessage());
		}
	}

	@Override
	public RpcResponse<CheckImportDataVo> checkImportDeviceList(
			String deviceCode, List<DeviceListImport> importList,
			Boolean isOnlyimsi, Boolean isAdditional) {

		logger.info("DeviceManagerAdminRemoteImpl::checkImportDeviceList: deviceCode="
				+ deviceCode
				+ " importList.size="
				+ importList.size()
				+ " isOnlyimsi="
				+ (!StringUtils.isEmpty(isOnlyimsi) ? isOnlyimsi : "null")
				+ " isAdditional:" + isAdditional);

		CheckImportDataVo result = new CheckImportDataVo();
		try {
			if (StringUtils.isEmpty(importList) || importList.size() == 0
					|| StringUtils.isEmpty(isOnlyimsi)
					|| StringUtils.isEmpty(deviceCode)) {
				return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
			}
			if (importList.size() > 2000) {
				return RpcResponse.error(ErrorCodeEnum.ERRCODE_MAX_IMPORT_SIZE);
			}

			List<ServicePackage> packageList = externalService
					.listPackageInfoByDeviceCode(Integer.valueOf(deviceCode));

			// 导入成功的数据返回
			List<DeviceListImport> successList = new ArrayList<DeviceListImport>();
			// 导入失败的 数据
			List<DeviceListExport> failList = new ArrayList<>();
			DeviceCardManager cardManagerCondition = new DeviceCardManager();

			Map<String, Integer> mapImei = new HashMap<String, Integer>();
			Map<String, Integer> mapImsi = new HashMap<String, Integer>();

			List<String> listImsis = new ArrayList<>();
			List<String> listImeis = new ArrayList<>();
			
			for (DeviceListImport item : importList) {
				if (!StringUtils.isEmpty(item.getImei())) {
					Integer count = mapImei.get(item.getImei());
					if (count == null || count == 0) {
						mapImei.put(item.getImei(), 1);
					} else {
						count++;
						mapImei.put(item.getImei(), count);
					}
				}

				if (!StringUtils.isEmpty(item.getImsi())) {
					Integer count = mapImsi.get(item.getImsi());
					if (count == null || count == 0) {
						mapImsi.put(item.getImsi(), 1);
					} else {
						count++;
						mapImsi.put(item.getImsi(), count);
					}
				}
				listImsis.add(item.getImsi());
				listImeis.add(item.getImei());
			}
			
			
			List<Integer> listCardIds = new ArrayList<>();
			Map<String,DeviceFileVirtual> mapImsi2DeviceFileVirtual = deviceFileVirtualService.getDeviceFileVirtualByImsis(listImsis);
			Map<String,DeviceFileUnstock> mapImsi2DeviceUnstock = deviceFileUnstockService.getDeviceFileUnstockByImsis(listImsis);
			Map<String,DeviceCardManager> mapImsi2DeviceCardMana = deviceCardManagerService.getDeviceCardManagerWithListIdsByImsis(listImsis,listCardIds);
			Map<Integer,DeviceFileSnapshot> mapCardId2DeviceSnapshot = deviceFileSnapshotService.getDeviceFileSnapshotByCardIds(listCardIds);
			Map<String,DeviceFile> mapSn2DeviceFile = deviceFileService.getDeviceFilesBySns(listImeis);
			
			for (DeviceListImport item : importList) {

				logger.info("DeviceManagerAdminRemoteImpl::checkImportDeviceList handle data item="
						+ item.toString());
				DeviceListExport fail = new DeviceListExport();
				boolean add = true;
				// 验证所有字段是否为空
				if (isEmptyAttribleInDeviceListImport(item, isOnlyimsi)) {
					BeanUtils.copyProperties(fail, item);
					fail.setReason(ReasonInvalidDeviceNum.INVALID_DEVICE_DATA_FORMAT
							.getValue());
					failList.add(fail);
					add = false;
					continue;
				}

			//	logger.info("DeviceManagerAdminRemoteImpl::checkImportDeviceList check column attrible ok!");
				// 验证imsi表格中是否有重复
				Integer count = mapImsi.get(item.getImsi());
				if (count >= 2) {
					BeanUtils.copyProperties(fail, item);
					fail.setReason(ReasonInvalidDeviceNum.INVALID_DEVICE_REPEAT_IMSI_EXCEL
							.getValue());
					failList.add(fail);
					add = false;
					continue;
				}
			//	logger.info("DeviceManagerAdminRemoteImpl::checkImportDeviceList check imsi repeated in excel ok!");
				// 校验所有字段长度是否合法
				if (!isRightDataLength(item)) {
					BeanUtils.copyProperties(fail, item);
					fail.setReason(ReasonInvalidDeviceNum.INVALID_DEVICE_COLUMN_LENGTH
							.getValue());
					failList.add(fail);
					add = false;
					continue;
				}
			//	logger.info("DeviceManagerAdminRemoteImpl::checkImportDeviceList check data length ok!");
				// 验证商品编号与入库是否一致 以及商品是否激活
				if (!isRightPackage(item, packageList)) {
					BeanUtils.copyProperties(fail, item);
					fail.setReason(ReasonInvalidDeviceNum.INVALID_DEVICE_PACKAGE_NO
							.getValue());
					failList.add(fail);
					add = false;
					continue;
				}
			//	logger.info("DeviceManagerAdminRemoteImpl::checkImportDeviceList check package and device_code ok!");

				// 验证发往商户号是否存在
			/*	if (!isExistsMentchantNo(item.getMentchantNo())) {
					BeanUtils.copyProperties(fail, item);
					fail.setReason(ReasonInvalidDeviceNum.INVALID_DEVICE_MERCHANT_NO
							.getValue());
					failList.add(fail);
					add = false;
					continue;
				}*/
			//	logger.info("DeviceManagerAdminRemoteImpl::checkImportDeviceList check merchant ok!");

				// 验证imsi格式是否正确
				if (!StringUtil.isRightImsiFormat(item.getImsi())) {
					BeanUtils.copyProperties(fail, item);
					fail.setReason(ReasonInvalidDeviceNum.INVALID_DEVICE_FORMAT_IMSI
							.getValue());
					failList.add(fail);
					add = false;
					continue;
				}
			//	logger.info("DeviceManagerAdminRemoteImpl::checkImportDeviceList check imsi fromat ok!");

				// 验证模块手机号格式
				if (!StringUtil.isRightModulePhoneFormat(item.getModulePhone())) {
					BeanUtils.copyProperties(fail, item);
					fail.setReason(ReasonInvalidDeviceNum.INVALID_DEVICE_FORMAT_PHONE
							.getValue());
					failList.add(fail);
					add = false;
					continue;
				}
		//		logger.info("DeviceManagerAdminRemoteImpl::checkImportDeviceList check module phone format ok!");

				// 验证imsi是否已经被绑定
				//DeviceFileVirtual deviceFileVirtual = deviceFileVirtualService
				//		.getDeviceFileVirtualByImsi(item.getImsi());
				DeviceFileVirtual deviceFileVirtual = mapImsi2DeviceFileVirtual.get(item.getImsi());
				if (!StringUtils.isEmpty(deviceFileVirtual)
						&& "N".equals(deviceFileVirtual.getDeletedFlag())) {
					BeanUtils.copyProperties(fail, item);
					fail.setReason(ReasonInvalidDeviceNum.INVALID_DEVICE_REPEAT_IMSI
							.getValue());
					failList.add(fail);
					add = false;
					continue;
				}
			//	logger.info("DeviceManagerAdminRemoteImpl::checkImportDeviceList check virtual imsi ok!");

				// 如果是补录的流程 则不需要验证补录的数据检测
				if (!isAdditional) {
					//DeviceFileUnstock deviceFileUnstock = new DeviceFileUnstock();
					//deviceFileUnstock.setImsi(item.getImsi());
					//List<DeviceFileUnstock> deviceFileUnstockList = deviceFileUnstockService
					//		.selectList(deviceFileUnstock);
					DeviceFileUnstock deviceUnstock = mapImsi2DeviceUnstock.get(item.getImsi());
					if (deviceUnstock != null) {
						BeanUtils.copyProperties(fail, item);
						fail.setReason(ReasonInvalidDeviceNum.INVALID_DEVICE_UNSTOCK_IMSI
								.getValue());
						failList.add(fail);
						add = false;
						continue;
					}
				//	logger.info("DeviceManagerAdminRemoteImpl::checkImportDeviceList check device unsock ok!");
				}

				// 卡管理表查imsi
				//cardManagerCondition.setImsi(item.getImsi());
				//cardManagerCondition.setCompanyId(1);
				//DeviceCardManager deviceCardManager = deviceCardManagerService
				//		.getDeviceCardByUniqueKey(cardManagerCondition);
				DeviceCardManager deviceCardManager = mapImsi2DeviceCardMana.get(item.getImsi());
				if (!StringUtils.isEmpty(deviceCardManager)) {
					// 关系表查卡表imsi是否重复
					//DeviceFileSnapshot deviceSnapshot = deviceFileSnapshotService
						//	.getDeviceFileSnapshotByCardId(deviceCardManager
						//			.getId());
					DeviceFileSnapshot deviceSnapshot = mapCardId2DeviceSnapshot.get(deviceCardManager.getId());
					if (!StringUtils.isEmpty(deviceSnapshot)
							&& "N".equals(deviceSnapshot.getDeletedFlag())) {
						BeanUtils.copyProperties(fail, item);
						fail.setReason(ReasonInvalidDeviceNum.INVALID_DEVICE_REPEAT_IMSI
								.getValue());
						failList.add(fail);
						add = false;
						continue;
					}
				}
			//	logger.info("DeviceManagerAdminRemoteImpl::checkImportDeviceList check snapshot imsi ok!");

				if (!isOnlyimsi) {
					// 验证imei在excel表格中是否存在
					count = mapImei.get(item.getImei());
					if (count >= 2) {
						BeanUtils.copyProperties(fail, item);
						fail.setReason(ReasonInvalidDeviceNum.INVALID_DEVICE_REPEAT_IMEI_EXCEL
								.getValue());
						failList.add(fail);
						add = false;
						continue;
					}
				//	logger.info("DeviceManagerAdminRemoteImpl::checkImportDeviceList check repeated imei in excel ok!");

					// 验证sn(imei)是否重复
					DeviceFile deviceFileNew = mapSn2DeviceFile.get(item.getImei());
					// 设备存在且未被初始化
					if (!StringUtils.isEmpty(deviceFileNew)
							&& "N".equals(deviceFileNew.getDeletedFlag())) {
						BeanUtils.copyProperties(fail, item);
						fail.setReason(ReasonInvalidDeviceNum.INVALID_DEVICE_REPEAT_IMEI
								.getValue());
						failList.add(fail);
						add = false;
						continue;
					}
				//	logger.info("DeviceManagerAdminRemoteImpl::checkImportDeviceList check repeated imei in device_file ok!");
				} else {
					logger.info("处理没有sn/imei + item=" + item.toString());
					if (!StringUtils.isEmpty(item.getImei())) {
						BeanUtils.copyProperties(fail, item);
						fail.setReason(ReasonInvalidDeviceNum.INVALID_DEVICE_IMEI_EXISTS
								.getValue());
						add = false;
						failList.add(fail);
						continue;
					}
				//	logger.info("DeviceManagerAdminRemoteImpl::checkImportDeviceList check repeated imei should not exist ok!");
				}

				if (add) {
					successList.add(item);
				}
			}

			logger.info("DeviceManagerAdminRemoteImpl::checkImportDeviceList: check ok !");

			result.setImportList(successList);
			result.setInvalidList(failList);
			return RpcResponse.success(result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			return RpcResponse.error(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
	}

	@Override
	public RpcResponse<CheckImportDataVo> checkImportGpsDevceList(
			List<DeviceInfoGpsPreimport> importList) {
		logger.info("DeviceManagerAdminRemoteImpl::checkImportGpsDevceList: importList.size="
				+ importList.size());
		CheckImportDataVo result = new CheckImportDataVo();
		try {
			if (StringUtils.isEmpty(importList) || importList.size() == 0) {
				return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
			}

			List<OrderInfo> orderList = null;
			List<DeviceInfo> deviceInfoList = null;
			List<DeviceFileSnapshot> deviceFileSnapshotList = null;
			List<DeviceFileVirtual> deviceFileVirtualList = null;
			List<ExsysDispatchRule> exsysDispatchRuleList = null;

			List<MerchantOrderDetail> merchantOrderDetailList = new ArrayList<>();
			List<DeviceInfoGpsPreimport> sucessList = new ArrayList<DeviceInfoGpsPreimport>();
			List<DeviceInfoGpsPreimport> failList = new ArrayList<DeviceInfoGpsPreimport>();
			Map<String, String> attribMnumMap = new HashMap<String, String>();

			Map<String, Integer> mapSn = new HashMap<String, Integer>();
			Map<String, Integer> mapImei = new HashMap<String, Integer>();
			Map<String, Integer> mapIccid = new HashMap<String, Integer>();
			Map<String, Integer> mapImsi = new HashMap<String, Integer>();
			Map<String, Integer> mapOrder = new HashMap<String, Integer>();
			Map<String, Integer> mapOrderUnder = new HashMap<String, Integer>();
			Map<String, Integer> mapOrderSystem = new HashMap<String, Integer>();
			Map<String, Integer> mapWhousSystem = new HashMap<String, Integer>();
			Map<String, Integer> mapDispatch = new HashMap<String, Integer>();

			List<String> listOrderCode = new ArrayList<String>();
			List<String> listSn = new ArrayList<String>();
			List<String> listIccid = new ArrayList<String>();

			MerchantOrderDetail merchantOrderDetail;

			for (DeviceInfoGpsPreimport deviceInfoGps : importList) {
				if (!StringUtils.isEmpty(deviceInfoGps.getOrderCode())) {
					merchantOrderDetail = new MerchantOrderDetail();
					merchantOrderDetail.setDispatchOrderNumber(deviceInfoGps
							.getOrderCode());
					merchantOrderDetailList.add(merchantOrderDetail);
				}

				if (!StringUtils.isEmpty(deviceInfoGps.getSn())) {
					Integer count = mapSn.get(deviceInfoGps.getSn());
					if (count == null || count == 0) {
						mapSn.put(deviceInfoGps.getSn(), 1);
					} else {
						count++;
						mapSn.put(deviceInfoGps.getSn(), count);
					}
				}

				if (!StringUtils.isEmpty(deviceInfoGps.getImei())) {
					Integer count = mapImei.get(deviceInfoGps.getImei());
					if (count == null || count == 0) {
						mapImei.put(deviceInfoGps.getImei(), 1);
					} else {
						count++;
						mapImei.put(deviceInfoGps.getImei(), count);
					}
				}

				if (!StringUtils.isEmpty(deviceInfoGps.getIccid())) {
					Integer count = mapIccid.get(deviceInfoGps.getIccid());
					if (count == null || count == 0) {
						mapIccid.put(deviceInfoGps.getIccid(), 1);
					} else {
						count++;
						mapIccid.put(deviceInfoGps.getIccid(), count);
					}
				}

				if (!StringUtils.isEmpty(deviceInfoGps.getImsi())) {
					Integer count = mapImsi.get(deviceInfoGps.getImsi());
					if (count == null || count == 0) {
						mapImsi.put(deviceInfoGps.getImsi(), 1);
					} else {
						count++;
						mapImsi.put(deviceInfoGps.getImsi(), count);
					}
				}

				if (!StringUtils.isEmpty(deviceInfoGps.getOrderCode())) {
					Integer count = mapOrder.get(deviceInfoGps.getOrderCode());
					if (count == null || count == 0) {
						mapOrder.put(deviceInfoGps.getOrderCode(), 1);
					} else {
						count++;
						mapOrder.put(deviceInfoGps.getOrderCode(), count);
					}
				}
			}
			listOrderCode.addAll(mapOrder.keySet());

			// 拼装分发规则
			if (merchantOrderDetailList != null
					&& merchantOrderDetailList.size() > 0) {
				merchantOrderDetailList = merchantOrderDetailService
						.getMerchantOrderDetailListByDispatchOrderNumberList(merchantOrderDetailList);
				for (MerchantOrderDetail merchantOrderDetailData : merchantOrderDetailList) {
					for (DeviceInfoGpsPreimport importData : importList) {
						if (merchantOrderDetailData.getDispatchOrderNumber()
								.equals(importData.getOrderCode())
								&& !StringUtil.isEmpty(importData.getSn())) {
							importData.setDispatch(importData.getModel()
									+ importData.getSn().substring(0, 2)
									+ merchantOrderDetailData.getSubjectId()
									+ merchantOrderDetailData.getInsure());
						}
					}
				}
			}
			// 获取系统设置分发规则
			exsysDispatchRuleList = exsysDispatchRuleMapper.selectAll();
			for (ExsysDispatchRule exsysDispatchRule : exsysDispatchRuleList) {
				String dispatchKey = exsysDispatchRule.getMnumName()
						+ exsysDispatchRule.getSnHead()
						+ exsysDispatchRule.getSubject()
						+ exsysDispatchRule.getIssure();
				mapDispatch.put(dispatchKey, 1);
			}

			// 计算每个订单还需要发多少货
			if (listOrderCode.size() > 0) {
				orderList = orderInfoService
						.getCountOrderDetails(listOrderCode);
				if (!StringUtils.isEmpty(orderList)) {
					for (OrderInfo orderInfo : orderList) {
						if(StringUtils.isEmpty(orderInfo.getId()))
						{
							continue;
						}
						if (orderInfo.getStatus().equals(
								OrderStatusEnum.STATUS_CL.getValue())
								|| orderInfo.getStatus().equals(
										OrderStatusEnum.STATUS_OV.getValue())) {
							mapOrderUnder.put(orderInfo.getOrderCode(), 0);
						} else {
							mapOrderUnder.put(
									orderInfo.getOrderCode(),
									orderInfo.getTotal()
											- orderInfo.getAlreadyShipped());
						}
					}
				}
			}

			// 计算sn/iccid在系统中是否入库
			listSn.addAll(mapSn.keySet());
			listIccid.addAll(mapIccid.keySet());
			deviceInfoList = deviceInfoService.listDeviceInfoBySn(listSn);
			if (!StringUtils.isEmpty(deviceInfoList)) {
				for (DeviceInfo deviceInfo : deviceInfoList) {
					Integer count = mapSn.get(deviceInfo.getSn());
					count++;
					mapSn.put(deviceInfo.getSn(), count);
				}
				deviceInfoList.clear();
			}
			deviceInfoList = deviceInfoService
					.listDeviceInfoByIccids(listIccid);
			if (!StringUtils.isEmpty(deviceInfoList)) {
				for (DeviceInfo deviceInfo : deviceInfoList) {
					Integer count = mapIccid.get(deviceInfo.getIccid());
					count++;
					mapIccid.put(deviceInfo.getIccid(), count);
				}
			}
			deviceFileVirtualList = deviceFileVirtualService
					.getDeviceFileVirtualByIccids(listIccid);
			if (!StringUtils.isEmpty(deviceFileVirtualList)) {
				for (DeviceFileVirtual virtual : deviceFileVirtualList) {
					Integer count = mapIccid.get(virtual.getIccid());
					count++;
					mapIccid.put(virtual.getIccid(), count);
				}
			}
			deviceFileSnapshotList = deviceFileSnapshotService
					.getDeviceFileSnapshotBySns(listSn);
			if (!StringUtils.isEmpty(deviceFileSnapshotList)) {
				for (DeviceFileSnapshot snapshot : deviceFileSnapshotList) {
					Integer count = mapSn.get(snapshot.getSn());
					count++;
					mapSn.put(snapshot.getSn(), count);
				}
				deviceFileSnapshotList.clear();
			}
			deviceFileSnapshotList = deviceFileSnapshotService
					.getDeviceFileSnapshotByIccids(listIccid);
			if (!StringUtils.isEmpty(deviceFileSnapshotList)) {
				for (DeviceFileSnapshot snapshot : deviceFileSnapshotList) {
					Integer count = mapIccid.get(snapshot.getIccid());
					count++;
					mapIccid.put(snapshot.getIccid(), count);
				}
				deviceFileSnapshotList.clear();
			}

			// 对每条数据进行验证
			for (DeviceInfoGpsPreimport deviceInfoGps : importList) {
				// 验证订单是否存在
				if (!isSendOrderNoExists(deviceInfoGps.getOrderCode(),
						mapOrderSystem)) {
					deviceInfoGps
							.setResultDesc(ReasonInvalidDeviceNum.INVALID_SEND_ORDER_NO_EMPTY
									.getValue());
					failList.add(deviceInfoGps);
					continue;
				}
				// 验证工厂仓库是否存在
				if (!isFactoryNameExists(deviceInfoGps.getFactoryName(),
						mapWhousSystem)) {
					deviceInfoGps
							.setResultDesc(ReasonInvalidDeviceNum.INVALID_WHOUSE_NAME_EMPTY
									.getValue());
					failList.add(deviceInfoGps);
					continue;
				}
				// 验证sn格式是否正确
				if (!StringUtil.isRightFormatSn(deviceInfoGps.getSn())) {
					// sn为空
					deviceInfoGps
							.setResultDesc(ReasonInvalidDeviceNum.INVALID_FORMAT_DEVICE_SN
									.getValue());
					failList.add(deviceInfoGps);
					continue;
				}
				// 验证分发规则是否匹配
				if (!StringUtil.isEmpty(deviceInfoGps.getDispatch())) {
					if (StringUtil.isEmpty(mapDispatch.get(deviceInfoGps
							.getDispatch()))) {
						deviceInfoGps
								.setResultDesc(ReasonInvalidDeviceNum.INVALID_DISPATCH_RULE_NOT_FOUND
										.getValue());
						failList.add(deviceInfoGps);
						continue;
					}
				} else {
					deviceInfoGps
							.setResultDesc(ReasonInvalidDeviceNum.INVALID_DISPATCH_RULE_NOT_FOUND
									.getValue());
					failList.add(deviceInfoGps);
					continue;
				}

				// 验证模块手机号格式是否正确
				if (!StringUtils.isEmpty(deviceInfoGps.getSimCardNo())) {
					if (!StringUtil.isRightModulePhoneFormat(deviceInfoGps
							.getSimCardNo())) {
						deviceInfoGps
								.setResultDesc(ReasonInvalidDeviceNum.INVALID_DEVICE_FORMAT_PHONE
										.getValue());
						failList.add(deviceInfoGps);
						continue;
					}
				} else {
					deviceInfoGps
							.setResultDesc(ReasonInvalidDeviceNum.INVALID_DEVICE_FORMAT_PHONE
									.getValue());
					failList.add(deviceInfoGps);
					continue;
				}
				// 验证iccid格式是否正确
				if (!StringUtil.isRightFormatIccid(deviceInfoGps.getIccid())) {
					deviceInfoGps
							.setResultDesc(ReasonInvalidDeviceNum.INVALID_FORMAT_DEVICE_ICCID
									.getValue());
					failList.add(deviceInfoGps);
					continue;
				}
				// 验证imsi格式是否正确
				if (!StringUtil.isRightFormatImsi(deviceInfoGps.getImsi())) {
					deviceInfoGps
							.setResultDesc(ReasonInvalidDeviceNum.INVALID_FORMAT_DEVICE_IMSI
									.getValue());
					failList.add(deviceInfoGps);
					continue;
				}
				// 验证物流编号是否存在
				if (StringUtils.isEmpty(deviceInfoGps.getLogisticsNo())) {
					deviceInfoGps
							.setResultDesc(ReasonInvalidDeviceNum.INVALID_LOGISTISNO_EMPTY
									.getValue());
					failList.add(deviceInfoGps);
					continue;
				}
				// 验证物流公司是否存在
				if (StringUtils.isEmpty(deviceInfoGps.getLogisticsCpy())) {
					deviceInfoGps
							.setResultDesc(ReasonInvalidDeviceNum.INVALID_LOGISTISCPY_EMPTY
									.getValue());
					failList.add(deviceInfoGps);
					continue;
				}
				// 验证是否重复
				Integer count = mapSn.get(deviceInfoGps.getSn());
				if (count >= 2) {
					deviceInfoGps
							.setResultDesc(ReasonInvalidDeviceNum.INVALID_DEVICE_SN_REPEAT
									.getValue());
					failList.add(deviceInfoGps);
					continue;
				}
				count = mapIccid.get(deviceInfoGps.getIccid());
				if (count >= 2) {
					deviceInfoGps
							.setResultDesc(ReasonInvalidDeviceNum.INVALID_DEVICE_ICCID_REPEAT
									.getValue());
					failList.add(deviceInfoGps);
					continue;
				}
				count = mapImsi.get(deviceInfoGps.getImsi());
				if (count >= 2) {
					deviceInfoGps
							.setResultDesc(ReasonInvalidDeviceNum.INVALID_DEVICE_IMSI_REPEAT
									.getValue());
					failList.add(deviceInfoGps);
					continue;
				}
				if (!StringUtils.isEmpty(deviceInfoGps.getImei())) {
					count = mapImei.get(deviceInfoGps.getImei());
					if (count >= 2) {
						deviceInfoGps
								.setResultDesc(ReasonInvalidDeviceNum.INVALID_DEVICE_IMEI_REPEAT
										.getValue());
						failList.add(deviceInfoGps);
						continue;
					}
				}
				// 验证发货数量是否合理
				Integer excelCount = mapOrder.get(deviceInfoGps.getOrderCode());
				Integer underCount = mapOrderUnder.get(deviceInfoGps
						.getOrderCode());
				if(StringUtils.isEmpty(underCount))
				{
					underCount = 0;
				}
				if(StringUtils.isEmpty(excelCount))
				{
					excelCount = 0;
				}
				if (excelCount > underCount) {
					deviceInfoGps
							.setResultDesc(ReasonInvalidDeviceNum.INVALID_SEND_ORDER_QUANTITY
									.getValue());
					failList.add(deviceInfoGps);
					continue;
				}
				// 验证设备型号是否有对应的硬件类型编码
				String attribCode = this.guessGpsAttribCodeByMNUM(
						deviceInfoGps.getModel(), attribMnumMap);
				if (StringUtils.isEmpty(attribCode)) {
					deviceInfoGps
							.setResultDesc(ReasonInvalidDeviceNum.INVALID_DEVICE_DEVMNUM
									.getValue());
					failList.add(deviceInfoGps);
					continue;
				}

				sucessList.add(deviceInfoGps);
			}

			logger.info("DeviceManagerAdminRemoteImpl::checkImportGpsDevceList: check ok !");

			result.setGpsPreImportSuccessList(sucessList);
			result.setGpsPreImportFailList(failList);
			return RpcResponse.success(result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return RpcResponse.error(e);
		}
	}

	
	@Override
	public RpcResponse<Integer> importGpsDeviceList(String userName,
			List<DeviceInfoGpsPreimport> importList) {

		Integer result = 0;
		logger.info("DeviceManagerAdminRemoteImpl::importGpsDeviceList: importList.size="
				+ importList.size());
		if (importList.size() == 0) {
			return RpcResponse.success(result);
		}
		try {
			String excelImportFlag = StringUtil.getOrderNo();
			Date nowData = new Date();
			if (StringUtils.isEmpty(importList) || importList.size() == 0) {
				return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
			}
			List<DeviceInfo> deviceInfoList = new ArrayList<DeviceInfo>();
			List<OrderInfoDetail> orderDetailList = new ArrayList<OrderInfoDetail>();
			Map<String, OrderInfo> orderInfoMap = new HashMap<String, OrderInfo>();
			Map<String, WareHouseInfo> wareHouseInfoMap = new HashMap<String, WareHouseInfo>();
			Map<String, Logistics> logisticsMap = new HashMap<String, Logistics>();
			Map<String, String> attribMnumMap = new HashMap<String, String>();
			Map<String,Integer> mapOrderSendQulity = new HashMap<String,Integer>();
			Map<String,Integer> mapLogisticsQulity = new HashMap<String,Integer>();
			OrderInfo orderInfo = null;
			Integer qulityFlag = null;
			
			for (DeviceInfoGpsPreimport gpsDevice : importList) {
				orderInfo = orderInfoMap.get(gpsDevice.getOrderCode());
				if (null == orderInfo) {
					orderInfo = orderInfoService.getOrderInfoByOrderCode(gpsDevice.getOrderCode());
					if (null == orderInfo) {
						return RpcResponse.error(ErrorCodeEnum.ERRCODE_ORDER_NOT_EXIST);
					}
					orderInfoMap.put(gpsDevice.getOrderCode(), orderInfo);
				}
				qulityFlag = mapOrderSendQulity.get(gpsDevice.getOrderCode());
				qulityFlag = ((null==qulityFlag)?1:(qulityFlag+1));
				mapOrderSendQulity.put(gpsDevice.getOrderCode(), qulityFlag);

				String warehouseName = gpsDevice.getFactoryName();
				WareHouseInfo wareHoseInfo = wareHouseInfoMap
						.get(warehouseName);
				if (StringUtils.isEmpty(wareHoseInfo)) {
					wareHoseInfo = wareHouseInfoService
							.getWarehouseByName(warehouseName);
					if (StringUtils.isEmpty(wareHoseInfo)) {
						return RpcResponse
								.error(ErrorCodeEnum.ERRCODE_WAREHOUSE_NO_EXISTS);
					}
					wareHouseInfoMap.put(warehouseName, wareHoseInfo);
				}

				Logistics logistics = logisticsMap.get(gpsDevice
						.getLogisticsNo() + gpsDevice.getOrderCode());
				if (StringUtils.isEmpty(logistics)) {
					Address address = new Address();
					address.setName(orderInfo.getContacts());
					address.setAddress(orderInfo.getAddress());
					address.setMobile(orderInfo.getMobile());
					address.setMerchantCode(orderInfo.getSendMerchantNo());
					address.setCreatedBy(userName);
					address.setUpdatedBy(userName);
					address.setCreatedDate(nowData);
					address.setUpdatedDate(nowData);
					address.setId(null);
					address = addressService.addIfNotExist(address);

					logistics = new Logistics();
					logistics.setCode(StringUtil.getOrderNo());
					logistics.setServiceCode(orderInfo.getOrderCode());
					logistics.setType(LogisticsTypeEnum.SEND_ORDER_NO.getCode());
					logistics.setOrderNumber(gpsDevice.getLogisticsNo());
					logistics.setCompany(gpsDevice.getLogisticsCpy());
					logistics.setReceiveId(address.getId());
					logistics.setCreatedBy(userName);
					logistics.setUpdatedBy(userName);
					logistics.setCreatedDate(nowData);
					logistics.setUpdatedDate(nowData);
					logistics.setAccept("N");
					logistics.setDeletedFlag("N");
					logistics.setId(null);
					logistics = logisticsService.addIfNotExist(logistics);				

					if (StringUtils.isEmpty(logistics)) {
						return RpcResponse
								.error(ErrorCodeEnum.ERRCODE_SYSTEM_LOGISTISC_ADD_FAIL);
					}
					logisticsMap.put(gpsDevice.getLogisticsNo() + gpsDevice.getOrderCode(), logistics);	
				}
				qulityFlag = mapLogisticsQulity.get(gpsDevice.getLogisticsNo() + gpsDevice.getOrderCode());
				qulityFlag = ((null==qulityFlag)?1:(qulityFlag+1));
				mapLogisticsQulity.put(gpsDevice.getLogisticsNo() + gpsDevice.getOrderCode(), qulityFlag);

				DeviceInfo deviceInfo = new DeviceInfo();
				OrderInfoDetail orderInfoDetail = new OrderInfoDetail();
				String attribCode = this.guessGpsAttribCodeByMNUM(
						gpsDevice.getModel(), attribMnumMap);
				if (StringUtils.isEmpty(attribCode)) {
					return RpcResponse
							.error(ErrorCodeEnum.ERRCODE_INVALID_DEV_MNUM);
				}

				deviceInfo.setAttribCode(attribCode);
				orderInfoDetail.setAttribCode(attribCode);

				copyGpsDevice2DeviceInfo(gpsDevice, orderInfo, wareHoseInfo,
						nowData, userName, deviceInfo);
				copyGpsDevice2OrderInfoDetail(gpsDevice, orderInfo,
						wareHoseInfo, logistics, nowData, userName,
						orderInfoDetail);
				deviceInfoList.add(deviceInfo);
				orderDetailList.add(orderInfoDetail);

				gpsDevice.setCreatedBy(userName);
				gpsDevice.setUpdatedBy(userName);
				gpsDevice.setCreatedDate(nowData);
				gpsDevice.setUpdatedDate(nowData);
				gpsDevice.setSeedTag(excelImportFlag);
				gpsDevice.setResult("UN");
				gpsDevice.setResultDesc("");
				gpsDevice.setDeletedFlag("N");
			}

			deviceInfoService.batchAddDeviceInfoOnDuplicateKey(deviceInfoList);
			orderInfoService.batchAddOrderInfoDetail(orderDetailList);
			deviceInfoGpsService.batchAddDeviceInfoGpsPerimport(importList);
			
			//修改物流发货数
			for(Map.Entry<String, Logistics> entry:logisticsMap.entrySet()){
				Logistics logistics = entry.getValue();
				qulityFlag = mapLogisticsQulity.get(entry.getKey());
				logistics.setShipmentsQuantity(qulityFlag + (logistics.getShipmentsQuantity()==null?0:logistics.getShipmentsQuantity()));
				logistics.setSendTime(SupplychainUtils.getNowDateString());
				logisticsService.updateById(logistics);
			}
			
			//修改订单
			for(Map.Entry<String, OrderInfo> entry:orderInfoMap.entrySet()){
				orderInfo = entry.getValue();
				qulityFlag = mapOrderSendQulity.get(entry.getKey());
				orderInfo.setSendQuanlity(qulityFlag + (orderInfo.getSendQuanlity()==null?0:orderInfo.getSendQuanlity()));
				if(orderInfo.getSendQuanlity().intValue() >= orderInfo.getTotal().intValue()){
					orderInfo.setStatus(OrderStatusEnum.STATUS_OV.getValue());
					orderInfo.setUpdatedBy(userName);
					orderInfo.setUpdatedDate(nowData);
				}
				orderInfoService.update(orderInfo);	
				String merchantOrder = merchantOrderVehicleService.getBsMerchantOrderCodeByDispatchOrderCode(orderInfo.getOrderCode());
				MerchantOrder bsMerchantOrder = merchantOrderService.getMerchantOrderDbObject(merchantOrder);
				EcMerchantOrder ecMerchantOrder = ecMerchantOrderService.getEcMerchantOrderByOrderNumber(merchantOrder);
				int bsSendQuanlity = (ecMerchantOrder.getAlreadyShipmentQuantity()==null?0:ecMerchantOrder.getAlreadyShipmentQuantity());
				bsSendQuanlity += qulityFlag.intValue();
				int bsCheckQulities = (ecMerchantOrder.getCheckQuantity()==null?0:ecMerchantOrder.getCheckQuantity());
				int bsOwerQulities = bsCheckQulities - bsSendQuanlity;
				ecMerchantOrder.setOweQuantity(bsOwerQulities);
				ecMerchantOrder.setAlreadyShipmentQuantity(bsSendQuanlity);
				ecMerchantOrder.setStatus("已发货");	
				ecMerchantOrder.setSignStatus(MerchantOrderSignStatusEnum.ORDER_SIGN_STATUS_UNSIGN.getName());
				bsMerchantOrder.setStatus(MerchantOrderStatusEnum.ORDER_WAIT_RECEIVE.getCode());
				bsMerchantOrder.setSignStatus(MerchantOrderSignStatusEnum.ORDER_SIGN_STATUS_UNSIGN.getCode());
				if(bsOwerQulities <= 0){
					ecMerchantOrder.setStatus("完成发货");
					bsMerchantOrder.setStatus(MerchantOrderStatusEnum.ORDER_ALREADY_FINISH.getCode());
				}
				merchantOrderService.updateMerchantOrderDbObject(bsMerchantOrder);
				ecMerchantOrderService.updateEcMerchantOrderById(ecMerchantOrder);
			}
			
		} catch (RpcServiceException e) {
			e.printStackTrace();
			return RpcResponse.error(e.getError(), e.getMessage());
		}
		return RpcResponse.success(result);
	}

	private String guessGpsAttribCodeByMNUM(String mnumName,
			Map<String, String> mapMnum) {
		String attribCode = mapMnum.get(mnumName);
		if (!StringUtils.isEmpty(attribCode)) {
			return attribCode;
		}

		for (int i = 10; i > 6; i--) {
			attribCode = attribManagerService.guessAttribManaCodeByMnum(
					mnumName, i);
			if (!StringUtils.isEmpty(attribCode)) {
				mapMnum.put(mnumName, attribCode);
				return attribCode;
			}
		}
		return attribCode;
	}

	private void copyGpsDevice2DeviceInfo(DeviceInfoGpsPreimport gpsDevice,
			OrderInfo orderInfo, WareHouseInfo warHouseInfo, Date nowData,
			String userName, DeviceInfo deviceInfo) {
		deviceInfo.setIccid(gpsDevice.getIccid());
		deviceInfo.setImei(gpsDevice.getImei());
		// deviceInfo.setAttribCode(orderInfo.getAttribCode());
		deviceInfo.setBatch(gpsDevice.getBatch());
		deviceInfo.setStatus(DeviceEnum.STATUS_OUT.getValue());
		deviceInfo.setWareHouseId(warHouseInfo.getId());
		deviceInfo.setWareHouseIdUp(warHouseInfo.getId());
		deviceInfo.setOrderCode(orderInfo.getOrderCode());
		deviceInfo.setCreatedBy(userName);
		deviceInfo.setUpdatedBy(userName);
		deviceInfo.setCreatedDate(nowData);
		deviceInfo.setUpdatedDate(nowData);
		deviceInfo.setDeletedFlag("N");
		deviceInfo.setSn(gpsDevice.getSn());
		deviceInfo.setImsi(gpsDevice.getImsi());
		deviceInfo.setSimCardNo(gpsDevice.getSimCardNo());
		deviceInfo.setVcode(gpsDevice.getVcode());
	}

	private void copyGpsDevice2OrderInfoDetail(
			DeviceInfoGpsPreimport gpsDevice, OrderInfo orderInfo,
			WareHouseInfo warHouseInfo, Logistics logistics, Date nowData,
			String userName, OrderInfoDetail orderInfoDetail) {
		orderInfoDetail.setOrderCode(orderInfo.getOrderCode());
		orderInfoDetail.setIccid(gpsDevice.getIccid());
		orderInfoDetail.setImei(gpsDevice.getImei());
		// orderInfoDetail.setAttribCode(orderInfo.getAttribCode());
		orderInfoDetail.setBatch(gpsDevice.getBatch());
		orderInfoDetail.setWarehouseId(warHouseInfo.getId());
		orderInfoDetail.setWarehouseIdUp(warHouseInfo.getId());
		orderInfoDetail.setCreatedBy(userName);
		orderInfoDetail.setUpdatedBy(userName);
		orderInfoDetail.setCreatedDate(nowData);
		orderInfoDetail.setUpdatedDate(nowData);
		orderInfoDetail.setDeletedFlag("N");
		orderInfoDetail.setLogisticsId(logistics.getId().intValue());
		orderInfoDetail.setSn(gpsDevice.getSn());
	}

	@Override
	public RpcResponse<CheckImportDataVo> checkImportDeviceFileUnstock(
			String deviceCode, List<DeviceListImport> importList,
			Boolean isOnlyimsi) {

		logger.info("DeviceManagerAdminRemoteImpl::checkImportDeviceList: deviceCode="
				+ deviceCode
				+ " importList.size="
				+ importList.size()
				+ " isOnlyimsi="
				+ (!StringUtils.isEmpty(isOnlyimsi) ? isOnlyimsi : "null"));

		CheckImportDataVo result = new CheckImportDataVo();
		try {
			if (StringUtils.isEmpty(importList) || importList.size() == 0
					|| StringUtils.isEmpty(isOnlyimsi)
					|| StringUtils.isEmpty(deviceCode)) {
				return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
			}
			if (importList.size() > 2000) {
				return RpcResponse.error(ErrorCodeEnum.ERRCODE_MAX_IMPORT_SIZE);
			}

			List<ServicePackage> packageList = externalService
					.listPackageInfoByDeviceCode(Integer.valueOf(deviceCode));

			// 导入成功的数据返回
			List<DeviceListImport> successList = new ArrayList<DeviceListImport>();
			// 导入失败的 数据
			List<DeviceListExport> failList = new ArrayList<>();
			DeviceCardManager cardManagerCondition = new DeviceCardManager();

			Map<String, Integer> mapImei = new HashMap<String, Integer>();
			Map<String, Integer> mapImsi = new HashMap<String, Integer>();

			for (DeviceListImport item : importList) {
				if (!StringUtils.isEmpty(item.getImei())) {
					Integer count = mapImei.get(item.getImei());
					if (count == null || count == 0) {
						mapImei.put(item.getImei(), 1);
					} else {
						count++;
						mapImei.put(item.getImei(), count);
					}
				}

				if (!StringUtils.isEmpty(item.getImsi())) {
					Integer count = mapImsi.get(item.getImsi());
					if (count == null || count == 0) {
						mapImsi.put(item.getImsi(), 1);
					} else {
						count++;
						mapImsi.put(item.getImsi(), count);
					}
				}
			}

			for (DeviceListImport item : importList) {

				logger.info("DeviceManagerAdminRemoteImpl::checkImportDeviceList handle data item="
						+ item.toString());
				DeviceListExport fail = new DeviceListExport();
				boolean add = true;
				// 验证所有字段是否为空
				if (isEmptyAttribleInDeviceListImport(item, isOnlyimsi)) {
					BeanUtils.copyProperties(fail, item);
					fail.setReason(ReasonInvalidDeviceNum.INVALID_DEVICE_DATA_FORMAT
							.getValue());
					failList.add(fail);
					add = false;
					continue;
				}

				logger.info("DeviceManagerAdminRemoteImpl::checkImportDeviceList check column attrible ok!");
				// 验证imsi表格中是否有重复
				Integer count = mapImsi.get(item.getImsi());
				if (count >= 2) {
					BeanUtils.copyProperties(fail, item);
					fail.setReason(ReasonInvalidDeviceNum.INVALID_DEVICE_REPEAT_IMSI_EXCEL
							.getValue());
					failList.add(fail);
					add = false;
					continue;
				}
				logger.info("DeviceManagerAdminRemoteImpl::checkImportDeviceList check imsi repeated in excel ok!");
				// 校验所有字段长度是否合法
				if (!isRightDataLength(item)) {
					BeanUtils.copyProperties(fail, item);
					fail.setReason(ReasonInvalidDeviceNum.INVALID_DEVICE_COLUMN_LENGTH
							.getValue());
					failList.add(fail);
					add = false;
					continue;
				}
				logger.info("DeviceManagerAdminRemoteImpl::checkImportDeviceList check data length ok!");
				// 验证商品编号与入库是否一致 以及商品是否激活
				if (!isRightPackage(item, packageList)) {
					BeanUtils.copyProperties(fail, item);
					fail.setReason(ReasonInvalidDeviceNum.INVALID_DEVICE_PACKAGE_NO
							.getValue());
					failList.add(fail);
					add = false;
					continue;
				}
				logger.info("DeviceManagerAdminRemoteImpl::checkImportDeviceList check package and device_code ok!");

				// 验证发往商户号是否存在
				if (!isExistsMentchantNo(item.getMentchantNo())) {
					BeanUtils.copyProperties(fail, item);
					fail.setReason(ReasonInvalidDeviceNum.INVALID_DEVICE_MERCHANT_NO
							.getValue());
					failList.add(fail);
					add = false;
					continue;
				}
				logger.info("DeviceManagerAdminRemoteImpl::checkImportDeviceList check merchant ok!");

				// 验证imsi格式是否正确
				if (!StringUtil.isRightImsiFormat(item.getImsi())) {
					BeanUtils.copyProperties(fail, item);
					fail.setReason(ReasonInvalidDeviceNum.INVALID_DEVICE_FORMAT_IMSI
							.getValue());
					failList.add(fail);
					add = false;
					continue;
				}
				logger.info("DeviceManagerAdminRemoteImpl::checkImportDeviceList check imsi fromat ok!");

				// 验证模块手机号格式
				if (!StringUtil.isRightModulePhoneFormat(item.getModulePhone())) {
					BeanUtils.copyProperties(fail, item);
					fail.setReason(ReasonInvalidDeviceNum.INVALID_DEVICE_FORMAT_PHONE
							.getValue());
					failList.add(fail);
					add = false;
					continue;
				}
				logger.info("DeviceManagerAdminRemoteImpl::checkImportDeviceList check module phone format ok!");

				// 验证imsi是否已经被绑定
				DeviceFileVirtual deviceFileVirtual = deviceFileVirtualService
						.getDeviceFileVirtualByImsi(item.getImsi());
				if (!StringUtils.isEmpty(deviceFileVirtual)
						&& "N".equals(deviceFileVirtual.getDeletedFlag())) {
					BeanUtils.copyProperties(fail, item);
					fail.setReason(ReasonInvalidDeviceNum.INVALID_DEVICE_REPEAT_IMSI
							.getValue());
					failList.add(fail);
					add = false;
					continue;
				}
				logger.info("DeviceManagerAdminRemoteImpl::checkImportDeviceList check virtual imsi ok!");

				// 卡管理表查imsi
				cardManagerCondition.setImsi(item.getImsi());
				cardManagerCondition.setCompanyId(1);
				DeviceCardManager deviceCardManager = deviceCardManagerService
						.getDeviceCardByUniqueKey(cardManagerCondition);
				if (!StringUtils.isEmpty(deviceCardManager)) {
					// 关系表查卡表imsi是否重复
					DeviceFileSnapshot deviceSnapshot = deviceFileSnapshotService
							.getDeviceFileSnapshotByCardId(deviceCardManager
									.getId());
					if (!StringUtils.isEmpty(deviceSnapshot)
							&& "N".equals(deviceSnapshot.getDeletedFlag())) {
						BeanUtils.copyProperties(fail, item);
						fail.setReason(ReasonInvalidDeviceNum.INVALID_DEVICE_REPEAT_IMSI
								.getValue());
						failList.add(fail);
						add = false;
						continue;
					}
				}
				logger.info("DeviceManagerAdminRemoteImpl::checkImportDeviceList check snapshot imsi ok!");

				if (!isOnlyimsi) {
					// 验证imei在excel表格中是否存在
					count = mapImei.get(item.getImei());
					if (count >= 2) {
						BeanUtils.copyProperties(fail, item);
						fail.setReason(ReasonInvalidDeviceNum.INVALID_DEVICE_REPEAT_IMEI_EXCEL
								.getValue());
						failList.add(fail);
						add = false;
						continue;
					}
					logger.info("DeviceManagerAdminRemoteImpl::checkImportDeviceList check repeated imei in excel ok!");

					// 验证sn(imei)是否重复
					DeviceFile deviceFileNew = null;
					try {
						deviceFileNew = deviceFileService
								.getDeviceFileBySn(item.getImei());
					} catch (RpcServiceException e) {
						logger.error(
								"DeviceManagerAdminRemoteImpl::checkImportDeviceList",
								e);
						return RpcResponse.error(e.getError());
					}

					// 设备存在且未被初始化
					if (!StringUtils.isEmpty(deviceFileNew)
							&& "N".equals(deviceFileNew.getDeletedFlag())) {
						BeanUtils.copyProperties(fail, item);
						fail.setReason(ReasonInvalidDeviceNum.INVALID_DEVICE_REPEAT_IMEI
								.getValue());
						failList.add(fail);
						add = false;
						continue;
					}
					logger.info("DeviceManagerAdminRemoteImpl::checkImportDeviceList check repeated imei in device_file ok!");
				} else {
					logger.info("处理没有sn/imei + item=" + item.toString());
					if (!StringUtils.isEmpty(item.getImei())) {
						BeanUtils.copyProperties(fail, item);
						fail.setReason(ReasonInvalidDeviceNum.INVALID_DEVICE_IMEI_EXISTS
								.getValue());
						add = false;
						failList.add(fail);
						continue;
					}
					logger.info("DeviceManagerAdminRemoteImpl::checkImportDeviceList check repeated imei should not exist ok!");
				}

				if (add) {
					successList.add(item);
				}
			}

			logger.info("DeviceManagerAdminRemoteImpl::checkImportDeviceList: check ok !");

			result.setImportList(successList);
			result.setInvalidList(failList);
			return RpcResponse.success(result);
		} catch (RpcServiceException e) {
			e.printStackTrace();
			return RpcResponse.error(e.getError(), e.getMessage());
		}
	}

	@Override
	public RpcResponse<CheckImportDataVo> preImportDeviceImeiStokeList(
			List<DeviceImeiStokeListImport> imeiStokeList) {

		CheckImportDataVo result = new CheckImportDataVo();
		try {
			if (StringUtils.isEmpty(imeiStokeList) || imeiStokeList.size() == 0) {
				return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
			}
			if (imeiStokeList.size() > 2000) {
				return RpcResponse.error(ErrorCodeEnum.ERRCODE_MAX_IMPORT_SIZE);
			}
			// 导入成功的数据返回
			List<DeviceImeiStokeListImport> successList = new ArrayList<DeviceImeiStokeListImport>();
			for (DeviceImeiStokeListImport item : imeiStokeList) {
				successList.add(item);
			}
			result.setImeiStokeList(successList);
			return RpcResponse.success(result);
		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(), e.getMessage());
		}
	}

	@Override
	public RpcResponse<Integer> importDeviceListByDeviceCode(
			String operatorName, String strDeviceCode,
			List<DeviceListImport> importList, Boolean isOnlyimsi) {

		Integer result = 0;
		Date dateNow = new Date();

		if (StringUtils.isEmpty(operatorName)
				|| StringUtils.isEmpty(strDeviceCode)
				|| StringUtils.isEmpty(importList)
				|| StringUtils.isEmpty(isOnlyimsi)) {
			return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}

		logger.info("DeviceManagerAdminRemoteImpl::importDeviceListByDeviceCode: param=operatorName:"
				+ operatorName
				+ " strDeviceCode:"
				+ strDeviceCode
				+ " importList.size():"
				+ importList.size()
				+ " isOnlyimsi:"
				+ isOnlyimsi);

		try {
			if (isOnlyimsi) {
				List<DeviceFileVirtual> virtualList = new ArrayList<DeviceFileVirtual>();
				for (DeviceListImport deviceListImport : importList) {
					DeviceFileVirtual deviceFileVirtual = new DeviceFileVirtual();
					deviceFileVirtual.setId(null);
					deviceFileVirtual.setDeviceCode(Integer
							.valueOf(strDeviceCode));
					deviceFileVirtual.setPhone(deviceListImport
							.getModulePhone());
					deviceFileVirtual.setIccid(deviceListImport.getIccid());
					deviceFileVirtual.setImsi(deviceListImport.getImsi());
					deviceFileVirtual.setVerifCode(deviceListImport.getVcode());
					deviceFileVirtual.setBatchNo(deviceListImport.getBatchNo());
					deviceFileVirtual.setOutStorageType("EX");
					deviceFileVirtual.setPackageId(Integer
							.valueOf(deviceListImport.getPackageNo()));
					deviceFileVirtual.setSendMerchantNo(deviceListImport
							.getMentchantNo());
					deviceFileVirtual.setCreatedBy(operatorName);
					deviceFileVirtual.setUpdatedBy(operatorName);
					deviceFileVirtual.setCreatedDate(dateNow);
					deviceFileVirtual.setUpdatedDate(dateNow);
					deviceFileVirtual.setDeletedFlag("N");
					virtualList.add(deviceFileVirtual);
				}
				deviceFileVirtualService.batchAddDeviceFileVirtual(virtualList);
			} else {
				deviceManagerAdminRemoteService.importDeviceFileForInnerDevice(
						operatorName, strDeviceCode, importList);
			}
		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(), e.getMessage());
		}

		logger.info("DeviceManagerAdminRemoteImpl::importDeviceListByDeviceCode ok!");
		return RpcResponse.success(result);
	}

	@Override
	public RpcResponse<Integer> importDeviceFileUnstockListByDeviceCode(
			String operatorName, String strDeviceCode,
			List<DeviceListImport> importList, Boolean isOnlyimsi) {

		Integer result = 0;
		Date dateNow = new Date();

		if (StringUtils.isEmpty(operatorName)
				|| StringUtils.isEmpty(strDeviceCode)
				|| StringUtils.isEmpty(importList)
				|| StringUtils.isEmpty(isOnlyimsi)) {
			return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}

		logger.info("DeviceManagerAdminRemoteImpl::importDeviceListByDeviceCode: param=operatorName:"
				+ operatorName
				+ " strDeviceCode:"
				+ strDeviceCode
				+ " importList.size():"
				+ importList.size()
				+ " isOnlyimsi:"
				+ isOnlyimsi);

		try {
			if (isOnlyimsi) {
				List<DeviceFileVirtual> virtualList = new ArrayList<DeviceFileVirtual>();
				for (DeviceListImport deviceListImport : importList) {
					DeviceFileVirtual deviceFileVirtual = new DeviceFileVirtual();
					deviceFileVirtual.setId(null);
					deviceFileVirtual.setDeviceCode(Integer
							.valueOf(strDeviceCode));
					deviceFileVirtual.setPhone(deviceListImport
							.getModulePhone());
					deviceFileVirtual.setIccid(deviceListImport.getIccid());
					deviceFileVirtual.setImsi(deviceListImport.getImsi());
					deviceFileVirtual.setVerifCode(deviceListImport.getVcode());
					deviceFileVirtual.setBatchNo(deviceListImport.getBatchNo());
					deviceFileVirtual.setOutStorageType("EX");
					deviceFileVirtual.setPackageId(Integer
							.valueOf(deviceListImport.getPackageNo()));
					deviceFileVirtual.setSendMerchantNo(deviceListImport
							.getMentchantNo());
					deviceFileVirtual.setCreatedBy(operatorName);
					deviceFileVirtual.setUpdatedBy(operatorName);
					deviceFileVirtual.setCreatedDate(dateNow);
					deviceFileVirtual.setUpdatedDate(dateNow);
					deviceFileVirtual.setDeletedFlag("N");
					virtualList.add(deviceFileVirtual);
				}
				deviceFileVirtualService.batchAddDeviceFileVirtual(virtualList);
			} else {
				deviceManagerAdminRemoteService.importDeviceFileForInnerDevice(
						operatorName, strDeviceCode, importList);
			}
			List<HashMap> list = new ArrayList<>();
			DeviceFileUnstock deviceFileUnstock = null;
			for (DeviceListImport deviceListImport : importList) {
				deviceFileUnstock = new DeviceFileUnstock();
				// deviceFileUnstock.setIccid(deviceListImport.getIccid());
				deviceFileUnstock.setSn(deviceListImport.getImei());
				deviceFileUnstock.setImsi(deviceListImport.getImsi());
				deviceFileUnstockService
						.updateDeviceFileUnstock(deviceFileUnstock);
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("sn", deviceListImport.getImei());
				params.put("imsi", deviceListImport.getImsi());
				list.add(params);
			}
			logger.info("kafka发送补录消息：" + list.toString());
			kafkaProducer.send(environment
					.getProperty("additionalRecording.producer.topic"),
					JSONObject.toJSONString(list).getBytes());
		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(), e.getMessage());
		}

		logger.info("DeviceManagerAdminRemoteImpl::importDeviceListByDeviceCode ok!");
		return RpcResponse.success(result);
	}

	@Override
	public RpcResponse<Integer> importDeviceimeiStokeList(String operatorName,
			List<DeviceImeiStokeListImport> importimeiStokeList) {
		Integer result = 0;

		if (StringUtil.isEmpty(operatorName)
				|| StringUtil.isEmpty(importimeiStokeList)) {
			return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}
		try {
			DeviceImeiStock deviceImeiStock = null;
			List<DeviceImeiStock> devoceImeiStockList = new ArrayList<DeviceImeiStock>();

			for (DeviceImeiStokeListImport deviceImeiStokeListImport : importimeiStokeList) {
				if (StringUtils.isEmpty(deviceImeiStokeListImport.getDevType())) {
					return RpcResponse
							.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
				}
				if (deviceImeiStokeListImport.getDevType() != 1
						&& deviceImeiStokeListImport.getDevType() != 2
						&& deviceImeiStokeListImport.getDevType() != 3
						&& deviceImeiStokeListImport.getDevType() != 5
						&& deviceImeiStokeListImport.getDevType() != 6
						&& deviceImeiStokeListImport.getDevType() != 7
						&& deviceImeiStokeListImport.getDevType() != 8
						&& deviceImeiStokeListImport.getDevType() != 9
						&& deviceImeiStokeListImport.getDevType() != 12134
						&& deviceImeiStokeListImport.getDevType() != 12307
						&& deviceImeiStokeListImport.getDevType() != 12316) {
					return RpcResponse
							.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
				}
				if (StringUtils.isEmpty(deviceImeiStokeListImport
						.getExternalFlag())) {
					return RpcResponse
							.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
				}
				if (!deviceImeiStokeListImport.getExternalFlag().equals("IN")
						&& !deviceImeiStokeListImport.getExternalFlag().equals(
								"EX")) {
					return RpcResponse
							.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
				}
				deviceImeiStock = new DeviceImeiStock();
				BeanUtils.copyProperties(deviceImeiStock,
						deviceImeiStokeListImport);
				deviceImeiStock.setImei(deviceImeiStock.getImei());
				deviceImeiStock.setExternalFlag(deviceImeiStock
						.getExternalFlag());
				deviceImeiStock.setDevType(deviceImeiStock.getDevType());
				deviceImeiStock.setMerchantNo(deviceImeiStock.getMerchantNo());
				deviceImeiStock.setCreatedBy(operatorName);
				deviceImeiStock.setCreatedDate(new Date());
				deviceImeiStock.setUpdatedBy(operatorName);
				deviceImeiStock.setUpdatedDate(new Date());
				devoceImeiStockList.add(deviceImeiStock);
			}
			// deviceImeiStockService.addDeviceImeiStock(deviceImeiStock);
			deviceImeiStockService.batchAddDeviceImeiStock(devoceImeiStockList);
			return RpcResponse.success(result);
		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(), e.getMessage());
		}
	}

	@Override
	public RpcResponse<Integer> reomveDeviceImeiStock(Integer id) {
		Integer result = 0;
		try {
			if (!StringUtils.isEmpty(id)) {
				deviceImeiStockService.removeDeviceImeiStock(id);
			}
			return RpcResponse.success(result);
		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(), e.getMessage());
		}
	}

	private Boolean isEmptyAttribleInDeviceListImport(
			DeviceListImport deviceImport, Boolean isOnlyimsi) {

		if (StringUtils.isEmpty(deviceImport))
			return true;
		if (StringUtils.isEmpty(deviceImport.getModulePhone()))
			return true;
		if (StringUtils.isEmpty(deviceImport.getIccid()))
			return true;
		if (StringUtils.isEmpty(deviceImport.getImsi()))
			return true;
		if (!isOnlyimsi) {
			if (StringUtils.isEmpty(deviceImport.getImei()))
				return true;
		}
		if (StringUtils.isEmpty(deviceImport.getVcode()))
			return true;
		if (StringUtils.isEmpty(deviceImport.getBatchNo()))
			return true;
		if (StringUtils.isEmpty(deviceImport.getMentchantNo()))
			return true;
		if (StringUtils.isEmpty(deviceImport.getPackageNo()))
			return true;
		return false;
	}

	private Boolean isRightPackage(DeviceListImport deviceImport,
			List<ServicePackage> packageList) {

		if (StringUtils.isEmpty(deviceImport)) {
			logger.info("DeviceManagerAdminRemoteImpl::isRightPackage: param err !");
			return false;
		}

		if (StringUtils.isEmpty(packageList)) {
			logger.info("DeviceManagerAdminRemoteImpl::isRightPackage: packageList is null !");
			return false;
		}

		if (StringUtils.isEmpty(deviceImport.getPackageNo())) {
			logger.info("DeviceManagerAdminRemoteImpl::isRightPackage: packageno is null !");
			return false;
		}

		if (!NumberUtils.isDigits(deviceImport.getPackageNo())) {
			logger.info("DeviceManagerAdminRemoteImpl::isRightPackage: packageno is error format !");
			return false;
		}
		Integer packageNo;
		try {
			packageNo = Integer.valueOf(deviceImport.getPackageNo());
		} catch (Exception e) {
			logger.error("DeviceManagerAdminRemoteImpl::isRightPackage: packageno 不能转换 Integer !");
			return false;
		}

		for (ServicePackage servicePackage : packageList) {
			logger.info("DeviceManagerAdminRemoteImpl::servicePackage.getid="
					+ servicePackage.getId() + " packageNo=" + packageNo);
			if (packageNo.equals(servicePackage.getId())) {
				return true;
			}
		}

		return false;
	}

	private Boolean isExistsMentchantNo(String merchantNo) {

		if (StringUtils.isEmpty(merchantNo)) {
			logger.info("DeviceManagerAdminRemoteImpl::isExistsMentchantNo: come here........ !");
			return false;
		}

		try {
			SupplyChainMerchantInfo merchantInfo = externalService
					.findMerchantInfoByMerchantId(Integer.valueOf(merchantNo));

			if (StringUtils.isEmpty(merchantInfo))
				return false;

			if (StringUtils.isEmpty(merchantInfo.getId()))
				return false;

		} catch (RpcServiceException e) {
			logger.error("DeviceManagerAdminRemoteImpl::isExistsMentchantNo: e.getError="
					+ e.getError()
					+ " e.getError.getValue="
					+ e.getError().getValue());
			return false;
		}

		return true;
	}

	// 校验字段长度
	private boolean isRightDataLength(DeviceListImport deviceImport) {

		if (StringUtils.isEmpty(deviceImport))
			return false;
		if (deviceImport.getModulePhone().length() > dataFormatProperty
				.getMaxPhoneLen())
			return false;
		if (deviceImport.getIccid().length() > dataFormatProperty
				.getMaxIccidLen())
			return false;
		if (deviceImport.getImsi().length() > dataFormatProperty
				.getMaxImsiLen())
			return false;
		if (!StringUtils.isEmpty(deviceImport.getImei())
				&& deviceImport.getImei().length() > dataFormatProperty
						.getMaxImeiLen())
			return false;
		if (deviceImport.getVcode().length() > dataFormatProperty
				.getMaxVcodeLen())
			return false;
		if (deviceImport.getBatchNo().length() > dataFormatProperty
				.getMaxBatchLen())
			return false;
		if (deviceImport.getPackageNo().length() > dataFormatProperty
				.getMaxCommonLen())
			return false;
		if (deviceImport.getMentchantNo().length() > dataFormatProperty
				.getMaxCommonLen())
			return false;

		return true;
	}

	// 验证订单
	private boolean isSendOrderNoExists(String orderNo,
			Map<String, Integer> orderSystemMap) {
		if (StringUtils.isEmpty(orderNo)) {
			return false;
		}
		Integer nValue = orderSystemMap.get(orderNo);
		if (nValue == null) {
			OrderInfo orderInfo = orderInfoService
					.getOrderInfoByOrderCode(orderNo);
			if (StringUtils.isEmpty(orderInfo)) {
				return false;
			}
			orderSystemMap.put(orderNo, 1);
		}

		return true;
	}

	private boolean isFactoryNameExists(String factoryName,
			Map<String, Integer> mapWhousSystem) {
		if (StringUtils.isEmpty(factoryName)) {
			return false;
		}
		Integer nValue = mapWhousSystem.get(factoryName);
		if (nValue == null) {
			WareHouseInfo wareHouseInfo = wareHouseInfoService
					.getWarehouseByName(factoryName);
			if (StringUtils.isEmpty(wareHouseInfo)) {
				return false;
			}
			mapWhousSystem.put(factoryName, 1);
		}
		return true;
	}

	/**
	 * 通过device_code查询设备类型
	 *
	 * @param deviceCode
	 * @return
	 */
	@Override
	public RpcResponse<DeviceCode> getDeviceCode(DeviceCode deviceCode) {
		try {
			return RpcResponse.success(deviceCodeService
					.getDeviceCodeByDeviceCode(deviceCode.getDeviceCode()));
		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(), e.getMessage());
		}

	}

	/**
	 * 查询虚拟设备信息
	 *
	 * @param record
	 */
	@Override
	public RpcResponse<RpcPagination<DeviceFileVirtual>> listDeviceFileVirtual(
			RpcPagination<DeviceFileVirtual> pagination,
			DeviceFileVirtual record) {

		try {
			Page<DeviceFileVirtual> page = deviceFileVirtualService
					.getDeviceFileVirtual(pagination, record);
			pagination = RpcPagination.copyPage(page);
			return RpcResponse.success(pagination);
		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(), e.getMessage());
		}

	}

	/**
	 * 查询IMEI库存信息
	 *
	 * @param record
	 * @return
	 */
	@Override
	public RpcResponse<RpcPagination<DeviceImeiStock>> listDeviceImeiStock(
			RpcPagination<DeviceImeiStock> pagination, DeviceImeiStock record) {
		try {
			Page<DeviceImeiStock> page = deviceImeiStockService
					.getDeviceImeiStock(pagination, record);
			pagination = RpcPagination.copyPage(page);
			return RpcResponse.success(pagination);
		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(), e.getMessage());
		}
	}

	@Override
	public RpcResponse<RpcPagination<DeviceFileUnstock>> listDeviceFileUnstock(
			RpcPagination<DeviceFileUnstock> pagination) {
		RpcResponse<RpcPagination<DeviceFileUnstock>> result;
		try {
			RpcAssert.assertNotNull(pagination,
					DefaultErrorEnum.PARAMETER_NULL,
					"pagination must not be null");
			RpcAssert
					.assertNotNull(pagination.getPageNum(),
							DefaultErrorEnum.PARAMETER_NULL,
							"pageNum must not be null");
			RpcAssert.assertNotNull(pagination.getPageSize(),
					DefaultErrorEnum.PARAMETER_NULL,
					"pageSize must not be null");
			result = RpcResponse.success(RpcPagination
					.copyPage(deviceFileUnstockService.listDeviceFileUnstock(
							pagination.getPageNum(), pagination.getPageSize(),
							pagination.getCondition())));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = RpcResponse.error(e);
		}
		return result;
	}

	@Override
	public RpcResponse<Integer> updateById(DeviceFileUnstock pagination) {
		RpcResponse<Integer> result;
		try {
			RpcAssert.assertNotNull(pagination,
					DefaultErrorEnum.PARAMETER_NULL,
					"pagination must not be null");
			result = RpcResponse.success(deviceFileUnstockService
					.updateById(pagination));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = RpcResponse.error(e);
		}
		return result;
	}

	@Override
	public RpcResponse<DeviceStatisReport> getMaxDeviceStatisReport() {
		RpcResponse<DeviceStatisReport> result;
		try {
			result = RpcResponse.success(deviceStatisReportMapper
					.getMaxDeviceStatisReport());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = RpcResponse.error(e);
		}
		return result;
	}

	/**
	 * 设备导入(外部程序导入)数据导入gps
	 *
	 * @param
	 * @return 返回导入失败的列表
	 * @throws RpcServiceException
	 */
	@Override
	public RpcResponse<Map<String, List<DeviceListImport>>> importDeviceListByExternalProgram(
			Integer companyId, String operatorName, String strDeviceCode,
			List<DeviceListImport> importList) {
		// 导入成功的数据返回
		Date nowTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(nowTime);
		DeviceCardManager deviceCard = new DeviceCardManager();
		Map<String, List<DeviceListImport>> retMap = new HashMap<String, List<DeviceListImport>>();
		List<DeviceListImport> successList = new ArrayList<DeviceListImport>();
		List<DeviceListImport> failList = new ArrayList<DeviceListImport>();

		List<DeviceFile> deviceFileList = new ArrayList<DeviceFile>();
		List<DeviceFileSnapshot> deviceSnapshotList = new ArrayList<DeviceFileSnapshot>();
		DeviceFile deviceFile;
		DeviceFileSnapshot deviceFileSnapshot;
		if (StringUtils.isEmpty(companyId) || StringUtils.isEmpty(operatorName)
				|| StringUtils.isEmpty(strDeviceCode)
				|| StringUtils.isEmpty(importList)) {
			logger.error("DeviceManagerAdminRemoteImpl::importDeviceListByExternalProgram err param.");
			return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}

		if (importList.size() == 0) {
			logger.info("DeviceManagerAdminRemoteImpl::importDeviceListByExternalProgram importList.size() =0");
			return RpcResponse.success(retMap);
		}

		try {
			DeviceCode deviceCode = deviceCodeService
					.getDeviceCodeByDeviceCode(Integer.valueOf(strDeviceCode));
			if (StringUtils.isEmpty(deviceCode)) {
				logger.error("DeviceManagerAdminRemoteImpl::importDeviceListByExternalProgram deviceCode not exists.");
				return RpcResponse
						.error(ErrorCodeEnum.ERRCODE_DEVICECODE_NOT_EXIST);
			}

			for (DeviceListImport oItem : importList) {
				logger.info(
						"DeviceManagerAdminRemoteImpl::importDeviceListByExternalProgram oItem{}",
						oItem);
				deviceFile = new DeviceFile();
				deviceFileSnapshot = new DeviceFileSnapshot();
				// 检验imsi
				if (!StringUtils.isEmpty(oItem.getImsi())) {
					if (StringUtil.isRightImsiFormat(oItem.getImsi())) {
						deviceCard.setId(null);
						deviceCard.setImsi(oItem.getImsi());
						deviceCard.setCompanyId(companyId);
						deviceCard.setCreatedBy(operatorName);
						deviceCard.setUpdatedBy(operatorName);
						deviceCard.setCreatedDate(nowTime);
						deviceCard.setUpdatedDate(nowTime);
						deviceCard = deviceCardManagerService
								.addDeviceCard(deviceCard);
					}
				}

				// 确认商户号是否存在
				if (!isExistsMentchantNo(oItem.getMentchantNo())) {
					oItem.setFailDesc("商户号不存在!");
					failList.add(oItem);
					continue;
				}

				// 判断imei是否初始化
				DeviceFile deviceFileNew = null;
				deviceFileNew = deviceFileService.getDeviceFileBySn(oItem
						.getImei());
				if (!StringUtils.isEmpty(deviceFileNew)
						&& "N".equals(deviceFileNew.getDeletedFlag())) {
					oItem.setFailDesc("imei重复");
					failList.add(oItem);
					continue;
				}

				// 判断imsi是否重复
				if (!StringUtils.isEmpty(oItem.getImsi())) {
					String imsi = oItem.getImsi();
					// 在虚拟表查imsi
					DeviceFileVirtual deviceFileVirtual = deviceFileVirtualService
							.getDeviceFileVirtualByImsi(imsi);
					if (!StringUtils.isEmpty(deviceFileVirtual)
							&& "N".equals(deviceFileVirtual.getDeletedFlag())) {
						oItem.setFailDesc("虚拟表存在imei!");
						failList.add(oItem);
						continue;
					}
					deviceCard.setImsi(imsi);
					deviceCard.setCompanyId(1);
					DeviceCardManager deviceCardManager = deviceCardManagerService
							.getDeviceCardByUniqueKey(deviceCard);
					if (!StringUtils.isEmpty(deviceCardManager)) {
						// 关系表查卡表imsi是否重复
						DeviceFileSnapshot deviceSnapshot = deviceFileSnapshotService
								.getDeviceFileSnapshotByCardId(deviceCardManager
										.getId());
						if (!StringUtils.isEmpty(deviceSnapshot)
								&& "N".equals(deviceSnapshot)) {
							oItem.setFailDesc("Snapshot表存在imei!");
							failList.add(oItem);
							continue;
						}
					}
				}
				// 填充deviceFileList
				deviceFile.setId(null);
				deviceFile.setSn(oItem.getImei());
				deviceFile.setPackageId(Integer.valueOf(oItem.getPackageNo()));
				deviceFile.setVerifCode(oItem.getVcode());
				deviceFile.setBatchNo(oItem.getBatchNo());
				deviceFile.setSendMerchantNo(oItem.getMentchantNo());
				deviceFile.setCreatedBy(operatorName);
				deviceFile.setUpdatedBy(operatorName);
				deviceFile.setCreatedDate(nowTime);
				deviceFile.setUpdatedDate(nowTime);
				deviceFile.setDeviceCode(Integer.valueOf(strDeviceCode));
				deviceFile.setInStorageTime(dateString);
				deviceFile.setOutStorageTime(dateString);
				deviceFile
						.setOutStorageType(OutStorageTypeEnum.OUT_STORAGE_TYPE_SYNC
								.getValue());
				deviceFile.setDeletedFlag("N");
				if (!StringUtils.isEmpty(deviceCard)) {
					deviceFile.setCardId(deviceCard.getId());
				}
				deviceFileList.add(deviceFile);

				// 填充deviceSnapshotListpageDeviceFile
				deviceFileSnapshot.setId(null);
				deviceFileSnapshot.setSn(oItem.getImei());
				deviceFileSnapshot.setPackageStatu("UA");
				deviceFileSnapshot.setPackageId(Integer.valueOf(oItem
						.getPackageNo()));
				deviceFileSnapshot.setCardId(deviceCard.getId());
				deviceFileSnapshot.setCardTime(dateString);
				deviceFileSnapshot.setCreatedBy(operatorName);
				deviceFileSnapshot.setUpdatedBy(operatorName);
				deviceFileSnapshot.setCreatedDate(nowTime);
				deviceFileSnapshot.setUpdatedDate(nowTime);
				deviceFileSnapshot.setDeletedFlag("N");
				deviceSnapshotList.add(deviceFileSnapshot);
				successList.add(oItem);
			}

			if (deviceFileList != null && deviceFileList.size() > 0) {
				// 批量插入devicefile表
				deviceFileService.batchAddDeviceFile(deviceFileList);
			}

			if (deviceSnapshotList != null && deviceSnapshotList.size() > 0) {
				// 批量插入devicefilesnapshot表
				deviceFileSnapshotService
						.batchAddDeviceFileSnapshot(deviceSnapshotList);
			}

			if (failList != null && failList.size() > 0) {
				List<SyncResultLog> syncResultLogList = new ArrayList<>();
				SyncResultLog syncResultLog;
				for (DeviceListImport deviceListImport : failList) {
					syncResultLog = new SyncResultLog();
					syncResultLog.setIccid(deviceListImport.getIccid());
					syncResultLog.setImei(deviceListImport.getImei());
					syncResultLog
							.setResult(SyncResultLogStatusEnum.SYNC_RESULT_LOG_STATUS_ENUM_FA
									.getCode());
					syncResultLog
							.setFlag(SyncResultLogFlagEnum.SYNC_RESULT_LOG_FLAG_ENUM_EX
									.getCode());
					syncResultLog.setErrorMsg(deviceListImport.getFailDesc());
					syncResultLog.setDeletedFlag("N");
					syncResultLog.setCreatedBy("admin");
					syncResultLog.setUpdatedBy("admin");
					syncResultLog.setCreatedDate(new Date());
					syncResultLog.setUpdatedDate(new Date());
					syncResultLogList.add(syncResultLog);
				}
				syncResultLogMapper.insertList(syncResultLogList);
			}

			retMap.put("success", successList);
			retMap.put("fail", failList);

			return RpcResponse.success(retMap);

		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(), e.getMessage());
		}

	}

	/**
	 * 根据sn获取设备当前状态
	 *
	 * @param
	 * @return 根据sn获取设备当前状态
	 * @throws RpcServiceException
	 */
	@Override
	public RpcResponse<DeviceFileSnapshot> getDeviceFileSnapshotByDeviceSn(
			String strSn) {
		// TODO Auto-generated method stub

		try {
			DeviceFileSnapshot deviceFileSnapshot = deviceFileSnapshotService
					.getDeviceFileSnapshotBySn(strSn);
			return RpcResponse.success(deviceFileSnapshot);
		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(), e.getMessage());
		}
	}

	/**
	 * 根据sn修改设备当前状态
	 *
	 * @param
	 * @return 根据sn修改设备当前状态
	 * @throws RpcServiceException
	 */
	@Override
	public RpcResponse<Integer> updateDeviceFileSnapshotByDeviceSn(
			DeviceFileSnapshot snapshot) {
		// TODO Auto-generated method stub
		Integer ret = 0;
		if (StringUtils.isEmpty(snapshot.getSn())) {
			ret = -1;
			return RpcResponse.success(ret);
		}
		if (!snapshot.getPackageStatu().equals(
				PackageStatuEnum.PACKAGE_STATU_ALACTIVE.getValue())
				&& !snapshot.getPackageStatu().equals(
						PackageStatuEnum.PACKAGE_STATU_UNACTIVE.getValue())
				&& !snapshot.getPackageStatu().equals(
						PackageStatuEnum.PACKAGE_STATU_INITIAL.getValue())) {
			ret = -2;
			return RpcResponse.success(ret);
		}
		DeviceFileSnapshot record = new DeviceFileSnapshot();
		record.setSn(snapshot.getSn());
		record.setPackageStatu(snapshot.getPackageStatu());
		try {
			DeviceFileSnapshot deviceFileSnapshot = deviceFileSnapshotService
					.getDeviceFileSnapshotBySn(record.getSn());
			if (StringUtils.isEmpty(deviceFileSnapshot)) {
				ret = -1;
				return RpcResponse.success(ret);
			}
			if (record.getPackageStatu().equals(
					deviceFileSnapshot.getPackageStatu())) {
				return RpcResponse.success(ret);
			}
			deviceFileSnapshotService.updateDeviceFileSnapshotBySn(record);
			return RpcResponse.success(ret);
		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(), e.getMessage());
		}
	}

	@Override
	public RpcResponse<RpcPagination<DeviceInfoGpsPreimport>> pageGpsDeviceList(
			RpcPagination<DeviceInfoGpsPreimport> pagination) {
		RpcResponse<RpcPagination<DeviceInfoGpsPreimport>> result;
		try {
			RpcAssert.assertNotNull(pagination,
					DefaultErrorEnum.PARAMETER_NULL,
					"pagination must not be null");
			RpcAssert
					.assertNotNull(pagination.getPageNum(),
							DefaultErrorEnum.PARAMETER_NULL,
							"pageNum must not be null");
			RpcAssert.assertNotNull(pagination.getPageSize(),
					DefaultErrorEnum.PARAMETER_NULL,
					"pageSize must not be null");
			result = RpcResponse.success(RpcPagination
					.copyPage(deviceInfoGpsService.pageDeviceInfoGpsPerimport(
							pagination.getPageNum(), pagination.getPageSize(),
							pagination.getCondition())));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = RpcResponse.error(e);
		}
		return result;
	}

}
