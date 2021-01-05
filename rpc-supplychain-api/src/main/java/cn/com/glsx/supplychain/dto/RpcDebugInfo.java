package cn.com.glsx.supplychain.dto;

import java.io.Serializable;

public class RpcDebugInfo implements Serializable{

	//操作模块 "redis"
	private String optModule;
	//操作方式  ad:增加 ck:查询 de:删除  
	private String optFunction;  
	
	private String redisKey;
	
	private Serializable redisValue;

	public String getOptModule() {
		return optModule;
	}

	public void setOptModule(String optModule) {
		this.optModule = optModule;
	}

	public String getOptFunction() {
		return optFunction;
	}

	public void setOptFunction(String optFunction) {
		this.optFunction = optFunction;
	}

	public String getRedisKey() {
		return redisKey;
	}

	public void setRedisKey(String redisKey) {
		this.redisKey = redisKey;
	}

	public Serializable getRedisValue() {
		return redisValue;
	}
	
	public <T> T getRedisValue(Class<T> clazz) {
		return clazz.cast(redisValue);
	}

	public void setRedisValue(Serializable redisValue) {
		this.redisValue = redisValue;
	}

	@Override
	public String toString() {
		return "RpcDebugInfo [optModule=" + optModule + ", optFunction="
				+ optFunction + ", redisKey=" + redisKey + ", redisValue="
				+ redisValue + ", getOptModule()=" + getOptModule()
				+ ", getOptFunction()=" + getOptFunction() + ", getRedisKey()="
				+ getRedisKey() + ", getRedisValue()=" + getRedisValue()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
	
	
}
