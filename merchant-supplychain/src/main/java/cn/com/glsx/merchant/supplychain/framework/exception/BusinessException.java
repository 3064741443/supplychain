package cn.com.glsx.merchant.supplychain.framework.exception;

import cn.com.glsx.merchant.supplychain.framework.common.ResultCodeConstants;
import org.oreframework.web.ui.ResultEntity;

/**
 * 时间:2017-7-17下午2:50:38<br/>
 * 功能:业务异常处理 <br/>
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private ResultEntity resultEntity;

    public BusinessException(String message) {
        super(message);
        this.resultEntity = new ResultEntity();
        this.resultEntity.setReturnCode(ResultCodeConstants.ERROR);
        this.resultEntity.setMessage(message);
    }

    public BusinessException(String errorCode, String message) {
        super(message);
        this.resultEntity = new ResultEntity();
        this.resultEntity.setReturnCode(errorCode);
        this.resultEntity.setMessage(message);
    }

    public BusinessException(ResultEntity resultEntity) {
        super(resultEntity.getMessage());
        this.resultEntity = resultEntity;
        resultEntity.setReturnCode(ResultCodeConstants.ERROR);
    }

    public ResultEntity getResultEntity() {
        return resultEntity;
    }

}
