package cn.com.glsx.merchant.supplychain.web.exception;


import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.enums.DefaultErrorEnum;

/**
 * 业务异常
 * <P>Description： </P>
 *
 * @author Alvin
 * @version 1.0
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private ResultEntity<Object> resultEntity;

    public BusinessException(String message) {
        super(message);
        this.resultEntity = new ResultEntity<Object>();
        this.resultEntity.setReturnCode(DefaultErrorEnum.SERVER_ERROR.getValue());
        this.resultEntity.setMessage(message);
    }

    public BusinessException(String errorCode, String message) {
        super(message);
        this.resultEntity = new ResultEntity<Object>();
        this.resultEntity.setReturnCode(errorCode);
        this.resultEntity.setMessage(message);
    }

    public BusinessException(ResultEntity<Object> resultEntity) {
        super(resultEntity.getMessage());
        this.resultEntity = resultEntity;
        this.resultEntity.setReturnCode(DefaultErrorEnum.SERVER_ERROR.getValue());
    }
    
    public ResultEntity<Object> getResultEntity() {
        return resultEntity;
    }

}
