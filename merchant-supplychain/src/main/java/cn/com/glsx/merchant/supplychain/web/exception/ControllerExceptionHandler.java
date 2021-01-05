package cn.com.glsx.merchant.supplychain.web.exception;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.enums.DefaultErrorEnum;

import com.alibaba.dubbo.rpc.RpcException;

/**
 * @Title: ControllerExceptionHandler.java
 * @Description: 控制层异常处理
 * @author Alvin  
 * @date 2019年5月2日 上午11:19:16
 * @version V1.0  
 * @Company: didihu.com.cn
 * @Copyright Copyright (c) 2018
 */
@RestControllerAdvice
public class ControllerExceptionHandler {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 验证异常处理
	 * @param e
	 * @return
	 * @author Alvin
	 * @date 2019年5月2日 上午11:47:29
	 */
	@ExceptionHandler({BindException.class, ConstraintViolationException.class, MethodArgumentNotValidException.class})
	public ResultEntity<Object> validatorExceptionHandler(Exception e){
		logger.error("validator exception:" + e.getMessage(), e);
		if(e instanceof BindException){
			BindException exception = (BindException) e;
			BindingResult bindingResult = exception.getBindingResult();
			if(bindingResult.hasErrors()){
				List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		        StringBuilder sb = new StringBuilder();
		        fieldErrors.forEach(fieldError -> sb.append(fieldError.getDefaultMessage()).append(","));
		        String errMsg = sb.deleteCharAt(sb.length() - 1).toString().toLowerCase();
		        return ResultEntity.error(DefaultErrorEnum.VALIDATE_FAILED.getValue(), errMsg);
			}
		}else if(e instanceof ConstraintViolationException){
			ConstraintViolationException exception = (ConstraintViolationException) e;
			Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
			StringBuilder sb = new StringBuilder();
	        constraintViolations.forEach(violation -> sb.append(violation.getMessage()).append(","));
	        String errMsg = sb.deleteCharAt(sb.length() - 1).toString().toLowerCase();
	        return ResultEntity.error(DefaultErrorEnum.VALIDATE_FAILED.getValue(), errMsg);
		}else if(e instanceof MethodArgumentNotValidException){
			MethodArgumentNotValidException exception = (MethodArgumentNotValidException)e;
			BindingResult bindingResult = exception.getBindingResult();
			if(bindingResult.hasErrors()){
				List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		        StringBuilder sb = new StringBuilder();
		        fieldErrors.forEach(fieldError -> sb.append(fieldError.getDefaultMessage()).append(","));
		        String errMsg = sb.deleteCharAt(sb.length() - 1).toString().toLowerCase();
		        return ResultEntity.error(DefaultErrorEnum.VALIDATE_FAILED.getValue(), errMsg);
			}
		}
		return ResultEntity.error(DefaultErrorEnum.SERVER_ERROR.getValue(), DefaultErrorEnum.SERVER_ERROR.getDisplayName());
	}
	
	/**
	 * dubbo异常处理
	 * @param e
	 * @return
	 * @author Alvin
	 * @date 2019年5月2日 上午11:47:43
	 */
	@ExceptionHandler({RpcException.class})
	public ResultEntity<Object> rpcExceptionHandler(RpcException e){
		logger.error("rpc exception:" + e.getMessage(), e);
		return ResultEntity.error(DefaultErrorEnum.SERVER_ERROR.getValue(), e.getMessage());
	}
	
	/**
     * 业务异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    public ResultEntity<Object> businessExceptionHandler(BusinessException e){
        logger.error("business exception:"+ e.getMessage());
        return e.getResultEntity();
    }
    
    /**
     * 系统异常
     * @param e
     * @return
     * @author Alvin
     * @date 2019年5月9日 上午1:11:26
     */
    @ExceptionHandler
    public ResultEntity<Object> gobalExceptionHandler(Exception e){
    	logger.error("gobal exception:"+ e.getMessage(), e);
    	return ResultEntity.error(DefaultErrorEnum.SERVER_ERROR.getValue(), e.getMessage());
    }
}
