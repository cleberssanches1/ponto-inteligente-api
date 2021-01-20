package com.br.sanches.pontointeligente.api.response;

import java.util.List;

public class Response<T> {
	
	private T data;
	private List<String> errors;

	public Response() {
		super();
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

}
