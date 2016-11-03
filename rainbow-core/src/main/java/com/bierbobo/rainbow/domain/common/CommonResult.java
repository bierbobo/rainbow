package com.bierbobo.rainbow.domain.common;

import java.io.Serializable;

/**
 * 通用结果类
 * @author
 *
 * @param <T>
 */
public class CommonResult<T> implements Serializable{

	private static final long serialVersionUID = 5206971551148066010L;
	public static final CommonResult successed = new CommonResult(true, 200L, "success");
	public static final CommonResult noData = new CommonResult(false, 201L, "no data");
	public static final CommonResult parameterError = new CommonResult(false, 301L, "parameter is error");
	public static final CommonResult internalError = new CommonResult(false, 302L, "内部执行错误");


	private boolean success; // 是否成功
	private Long code;//结果代码
	private String message;//结果信息
	private T data;//结果返回的数据

	public CommonResult() {}

	public CommonResult(boolean success) {
		this.success = success;
	}

	public CommonResult(boolean success, Long code, String message) {
		this.success = success;
		this.code = code;
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
