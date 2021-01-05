package cn.com.glsx.supplychain.controller;


import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.model.UserInfo;
import cn.com.glsx.supplychain.model.WareHouseInfo;
import cn.com.glsx.supplychain.remote.SupplyChainAdminRemote;
import org.oreframework.boot.security.UserInfoHolder;
import org.oreframework.boot.security.cas.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户信息管理
 * @author zhoudan
 */
@RestController
@RequestMapping("userInfomanage")
public class UserInfoController {

	@Autowired
	private UserInfoHolder userInfoHolder;
	
	@Autowired
	private SupplyChainAdminRemote supplyChainAdminRemote;

	/**
	 * 分页查询用户信息
	 * @param userInfo
	 * @param pagination
	 * @return
	 */
	@RequestMapping("listUserInfo")
	public ResultEntity<RpcPagination<UserInfo>> listUserInfo(UserInfo userInfo,RpcPagination<UserInfo> pagination) {
		RpcResponse<RpcPagination<UserInfo>> response = supplyChainAdminRemote.listUserInfo(pagination, userInfo);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		if(errCodeEnum == null){
			response.setMessage("查询成功");
		}else{
			response.setMessage(errCodeEnum.getDescrible());
		}
		return ResultEntity.result(response);
	}

	/**
	 * 新增用户
	 * @param userInfo
	 * @return
	 */
	@RequestMapping("insertUser")
	public ResultEntity<Integer> insertUser(UserInfo userInfo,HttpServletRequest request){
		@SuppressWarnings({ "static-access", "deprecation" })
		User user = userInfoHolder.getUser();
		if (null != user) {
			userInfo.setCreatedBy(user.getRealname());
			userInfo.setUpdatedBy(user.getRealname());
		}
		RpcResponse<Integer> response =	supplyChainAdminRemote.addAndUpdateUserInfo(userInfo);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		if(errCodeEnum == null){
			response.setMessage("新增成功");
		}else{
			response.setMessage(errCodeEnum.getDescrible());
		}
		return ResultEntity.result(response);
		
	}

	/**
	 * 重置用户密码
	 * @param userInfo
	 * @return
	 */
	@RequestMapping("updatePassword")
	public ResultEntity<Integer> updatePassword(UserInfo userInfo) {
		RpcResponse<Integer> response = supplyChainAdminRemote.addAndUpdateUserInfo(userInfo);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		if(errCodeEnum == null){
			response.setMessage("重置成功");
		}else{
			response.setMessage(errCodeEnum.getDescrible());
		}
		return ResultEntity.result(response);
	
	}

	/**
	 * 查询密码
	 * @param id
	 * @return
	 */
	@RequestMapping("getPassword")
	public ResultEntity<UserInfo> getPassword(Integer id) {
		RpcResponse<UserInfo> response = supplyChainAdminRemote.getUserInfoById(id);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		if(errCodeEnum == null){
			response.setMessage("查询成功");
		}else{
			response.setMessage(errCodeEnum.getDescrible());
		}
		return ResultEntity.result(response);
	}

	/**
	 * 查询仓库信息
	 * @param warehouseId
	 * @return
	 */
	@RequestMapping("getWarehouseInfo")
	public ResultEntity<RpcPagination<WareHouseInfo>> getWarehouseInfo(Integer warehouseId) {
		WareHouseInfo wareHouseInfo = new WareHouseInfo();
		RpcPagination<WareHouseInfo> pagination = new RpcPagination<WareHouseInfo>();
		RpcResponse<RpcPagination<WareHouseInfo>> response = supplyChainAdminRemote.listWarehouseInfo(pagination, wareHouseInfo);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		if(errCodeEnum == null){
			response.setMessage("查询成功");
		}else{
			response.setMessage(errCodeEnum.getDescrible());
		}
		return ResultEntity.result(response);
	}
}
