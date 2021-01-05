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
import cn.com.glsx.supplychain.model.WareHouseInfo;
import cn.com.glsx.supplychain.remote.SupplyChainAdminRemote;

/**
 * 仓库管理
 * @author zhoudan
 */
@RestController
@RequestMapping("warehousemanage")
public class WarehouseController {

	@Autowired
	private UserInfoHolder userInfoHolder;
	
	@Autowired
	private SupplyChainAdminRemote supplyChainAdminRemote;

	/**
	 * 分页查询仓库列表
	 * @param wareHouseInfo
	 * @param pagination
	 * @return
	 */
	@RequestMapping("listWarehouseInfo")
	public ResultEntity<RpcPagination<WareHouseInfo>> listWarehouseInfo(WareHouseInfo wareHouseInfo, RpcPagination<WareHouseInfo> pagination) {
		RpcResponse<RpcPagination<WareHouseInfo>> response = supplyChainAdminRemote.listWarehouseInfo(pagination, wareHouseInfo);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		if(errCodeEnum == null){
			response.setMessage("查询成功");
		}else{
			response.setMessage(errCodeEnum.getDescrible());
		}
		return ResultEntity.result(response);
	}

	/**
	 * 新增仓库
	 * @param wareHouseInfo
	 * @return
	 */
	@SuppressWarnings({ "deprecation", "static-access" })
	@RequestMapping("insertWarehouse")
	public ResultEntity<Integer> insertWarehouse(WareHouseInfo wareHouseInfo,HttpServletRequest request) {
		User user = userInfoHolder.getUser();
		if (null != user) {
			wareHouseInfo.setCreatedBy(user.getRealname());
			wareHouseInfo.setUpdatedBy(user.getRealname());
		}
		RpcResponse<Integer> response = supplyChainAdminRemote.addAndUpdateWareHouseInfo(wareHouseInfo);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		if(errCodeEnum == null){
			response.setMessage("查询成功");
		}else{
			response.setMessage(errCodeEnum.getDescrible());
		}
		return ResultEntity.result(response);
		
	}

	/**
	 * 查询仓库信息（不分页）
	 * @param id
	 * @return
	 */
	@RequestMapping("getWarehouseInfo")
	public ResultEntity<WareHouseInfo> getWarehouseInfo(Integer id) {
		RpcResponse<WareHouseInfo> response = supplyChainAdminRemote.getWareHouseInfoById(id);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		if(errCodeEnum == null){
			response.setMessage("查询成功");
		}else{
			response.setMessage(errCodeEnum.getDescrible());
		}
		return ResultEntity.result(response);
	}
}
