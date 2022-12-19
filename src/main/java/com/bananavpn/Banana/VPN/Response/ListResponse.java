package com.bananavpn.Banana.VPN.Response;

import java.util.List;
import java.util.Optional;

import org.springframework.lang.Nullable;

public class ListResponse<T> {
	private Integer code;
	private String message;
	private Boolean status;
	@Nullable
	private List<T> data;
	
	public ListResponse(Integer code, String message,Boolean status,List<T> data) {
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
	public List<T> getData() {
		return this.data;
	}
	public void setData(@Nullable List<T> getData) {
		this.data = getData;
	}

}
