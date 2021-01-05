package cn.com.glsx.supplychain.controller;

import javax.servlet.http.HttpServletRequest;

import cn.com.glsx.supplychain.model.AttribInfo;
import cn.com.glsx.supplychain.model.UserInfo;
import cn.com.glsx.supplychain.remote.SupplyChainRemote;

import org.oreframework.boot.security.UserInfoHolder;
import org.oreframework.boot.security.cas.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.config.Constants;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.model.AttribMana;
import cn.com.glsx.supplychain.remote.SupplyChainAdminRemote;
import cn.com.glsx.supplychain.vo.MerchantChannelVo;

import java.util.ArrayList;
import java.util.List;

/**
 * 设备配置管理
 * @author zhoudan
 */
@RestController
@RequestMapping("attribMana")
public class AttribManaController {

	@Autowired
	private UserInfoHolder userInfoHolder;
	
	@Autowired
	private SupplyChainAdminRemote supplyChainAdminRemote;


	/**
	 * 分页查询设备配置
	 * @param attribMana
	 * @param pagination
	 * @return
	 */
	@RequestMapping("listAttribMana")
	public ResultEntity<RpcPagination<AttribMana>> listAttribMana(AttribMana attribMana, RpcPagination<AttribMana> pagination) {
		RpcResponse<RpcPagination<AttribMana>> response = supplyChainAdminRemote.listAttribMana(pagination, attribMana);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		if(errCodeEnum == null){
			response.setMessage("查询成功");
		}else{
			response.setMessage(errCodeEnum.getDescrible());
		}
		return ResultEntity.result(response);
	}

	/**
	 * 新建订单分页查询设备配置
	 * @param attribMana
	 * @param pagination
	 * @return
	 */
	@RequestMapping("orderInfoListAttribMana")
	public ResultEntity<RpcPagination<AttribMana>> orderInfoListAttribMana(AttribMana attribMana, RpcPagination<AttribMana> pagination) {
		pagination.setPageSize(1000);
		RpcResponse<RpcPagination<AttribMana>> response = supplyChainAdminRemote.listAttribMana(pagination, attribMana);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		if(errCodeEnum == null){
			response.setMessage("查询成功");
		}else{
			response.setMessage(errCodeEnum.getDescrible());
		}
		return ResultEntity.result(response);
	}

	/**
	 * 新增或修改设备配置
	 * @param attribMana
	 * @param request
	 * @return
	 */
	@RequestMapping("addAndUpdateAttribMana")
	public ResultEntity<Integer> addAndUpdateAttribMana(AttribMana attribMana,HttpServletRequest request) {
		@SuppressWarnings({ "static-access", "deprecation" })
		User user = userInfoHolder.getUser();
		if (null != user) {
			attribMana.setCreatedBy(user.getRealname());
			attribMana.setUpdatedBy(user.getRealname());
		}
		RpcResponse<Integer> response = supplyChainAdminRemote.addAndUpdateAttribMana(attribMana);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		if(errCodeEnum == null){
			response.setMessage("查询成功");
		}else{
			response.setMessage(errCodeEnum.getDescrible());
		}
		return ResultEntity.result(response);
	}
	
	/**
	 * 查询配置信息（不分页）
	 * @param attribCode
	 * @return
	 */
	@RequestMapping("getAttribManaInfo")
	public ResultEntity<AttribMana> getAttribManaInfo(String attribCode) {
		RpcResponse<AttribMana> response = supplyChainAdminRemote.getAttribManaInfo(attribCode);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		if(errCodeEnum == null){
			response.setMessage("查询成功");
		}else{
			response.setMessage(errCodeEnum.getDescrible());
		}
		return ResultEntity.result(response);
	}

	/**
	 * 查询设备类型配置编码List
	 * @param
	 * @return
	 */
	@RequestMapping("listAttribManaCodes")
	public ResultEntity<List<AttribMana>> listAttribManaCodes() {
		AttribMana attribMana = new AttribMana();
		RpcResponse<List<AttribMana>> response = supplyChainAdminRemote.listAttribManaCodes(attribMana);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		if(errCodeEnum == null){
			response.setMessage("查询成功");
		}else{
			response.setMessage(errCodeEnum.getDescrible());
		}
		return ResultEntity.result(response);
	}

	/**
	 * 查询配置信息根据配置编号
	 * @param
	 * @return
	 */
	@RequestMapping("getAttribManaByManaCode")
	public ResultEntity<AttribMana> getAttribManaByManaCode(String manaCode) {
		RpcResponse<AttribMana> response = supplyChainAdminRemote.getAttribManaByManaCode(manaCode);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		if(errCodeEnum == null){
			response.setMessage("查询成功");
		}else{
			response.setMessage(errCodeEnum.getDescrible());
		}
		return ResultEntity.result(response);
	}


	/**
	 * 查询配置信息根据配置类型
	 * @param
	 * @return
	 */
	@RequestMapping("listAttribInfo")
	public ResultEntity<List<AttribInfo>> listAttribInfo(@RequestBody AttribInfo attribInfo) {
		RpcResponse<List<AttribInfo>> response = supplyChainAdminRemote.listAttribInfo(attribInfo);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		if(errCodeEnum == null){
			response.setMessage("查询成功");
		}else{
			response.setMessage(errCodeEnum.getDescrible());
		}
		return ResultEntity.result(response);
	}

	/**
     * 获取渠道列表
     *
     * @param
     * @return
     */
	@RequestMapping("listMerchantChannel")
	public ResultEntity<List<MerchantChannelVo>> listMerchantChannel(){
		List<MerchantChannelVo> listMerchantChannelVo = new ArrayList<>();
		MerchantChannelVo vo = null;
		AttribInfo condition = new AttribInfo();
		condition.setType(Constants.MERCHANT_CHANNEL_CONFIG_TYPE);
		RpcResponse<List<AttribInfo>> response = supplyChainAdminRemote.listAttribInfo(condition);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		if(null != errCodeEnum){
			return ResultEntity.error(errCodeEnum.getCode(), errCodeEnum.getDescrible());
		}
		List<AttribInfo> listResult = response.getResult();
		if(null != listResult){
			for(AttribInfo attribInfo:listResult){
				vo = new MerchantChannelVo();
				vo.setChannelId(attribInfo.getId()-Constants.MERCHANT_CHANNEL_CONVERT_BASE);
				vo.setChannelValue(attribInfo.getName());
				listMerchantChannelVo.add(vo);
			}
		}
		return ResultEntity.success(listMerchantChannelVo);
	}
	
}
