package cn.com.glsx.supplychain.controller;

import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.framework.core.enums.DefaultErrorEnum;
import cn.com.glsx.supplychain.remote.HandleHistoricalDataRemote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName HandleHistoricalDataController
 * @Description TODO
 * @Author yangbin
 * @Date 2019/8/13 11:08
 * @Version 1.0
 */
@RestController
@RequestMapping("handleHistorical")
public class HandleHistoricalDataController {
	
	@Autowired
	HandleHistoricalDataRemote handleHistoricalDataRemote;
	
	@RequestMapping("splitOrder")
	public ResultEntity<Integer> splitOrder() {
		RpcResponse<Integer> response = handleHistoricalDataRemote.splitOrder();
		DefaultErrorEnum  errCodeEnum = (DefaultErrorEnum) response.getError();
		if(errCodeEnum == null){
			response.setMessage("拆分成功");
		}else{
			response.setMessage(response.getMessage());
		}
		return ResultEntity.result(response);
	}

	@RequestMapping("updateProductCode")
	public ResultEntity<Integer> updateProductCode() {
		RpcResponse<Integer> response = handleHistoricalDataRemote.updateProductCode();
		DefaultErrorEnum  errCodeEnum = (DefaultErrorEnum) response.getError();
		if(errCodeEnum == null){
			response.setMessage("修改成功");
		}else{
			response.setMessage(response.getMessage());
		}
		return ResultEntity.result(response);
	}
}
