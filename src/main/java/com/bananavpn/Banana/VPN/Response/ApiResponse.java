package com.bananavpn.Banana.VPN.Response;

import java.util.Optional;

import org.springframework.lang.Nullable;

public class ApiResponse<T> {
	private Integer code;
	private String message;
	private Boolean status;
	@Nullable
	private Optional<T> data;
	
	public ApiResponse(Integer code, String message,Boolean status,Optional<T> data) {
		this.code = code;
		this.message = message;
		this.status = status;
		this.data = data;
	}
	
	public Integer getCode() {
		return this.code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return this.message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Boolean getStatus() {
		return this.status;
	}
	public void getStatus(Boolean status) {
		this.status = status;
	}
	public Optional<T> getData() {
		return this.data;
	}
	public void setData(@Nullable Optional<T> getData) {
		this.data = getData;
	}
}

