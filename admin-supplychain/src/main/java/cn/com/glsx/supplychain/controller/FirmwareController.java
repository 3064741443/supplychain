package cn.com.glsx.supplychain.controller;

import javax.servlet.http.HttpServletRequest;

import org.oreframework.boot.security.UserInfoHolder;
import org.oreframework.boot.security.cas.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.model.FirmwareInfo;
import cn.com.glsx.supplychain.remote.SupplyChainAdminRemote;

/**
 * 固件版本管理
 * @author zhoudan
 */
@RestController
@RequestMapping("firmwareInfo")
public class FirmwareController {

	@Autowired
	private UserInfoHolder userInfoHolder;
	
	@Autowired
	private SupplyChainAdminRemote supplyChainAdminRemote;

	/**
	 * 分页查询固件版本
	 * @param firmwareInfo
	 * @param pagination
	 * @return
	 */
	@RequestMapping("listFirmwareInfo")
	public ResultEntity<RpcPagination<FirmwareInfo>> listFirmwareInfo(FirmwareInfo firmwareInfo, RpcPagination<FirmwareInfo> pagination) {
		RpcResponse<RpcPagination<FirmwareInfo>> response = supplyChainAdminRemote.listFirmwareInfo(pagination, firmwareInfo);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		if(errCodeEnum == null){
			response.setMessage("查询成功");
		}else{
			response.setMessage(errCodeEnum.getDescrible());
		}
		return ResultEntity.result(response);
	}

	/**
	 * 新增固件版本
	 * @param firmwareInfo 
	 * @param request
	 * @return
	 */
	@RequestMapping("insertFirmwares")
	public ResultEntity<Integer> insertFirmwares(FirmwareInfo firmwareInfo,HttpServletRequest request) {
		@SuppressWarnings({ "static-access", "deprecation" })
		User user = userInfoHolder.getUser();
		if (null != user) {
			firmwareInfo.setCreatedBy(user.getRealname());
			firmwareInfo.setUpdatedBy(user.getRealname());
		}
		RpcResponse<Integer> response = supplyChainAdminRemote.addAndUpdateFirmwareInfo(firmwareInfo);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		if(errCodeEnum == null){
			response.setMessage("新增成功");
		}else{
			response.setMessage(errCodeEnum.getDescrible());
		}
		return ResultEntity.result(response);
	}
	
}
