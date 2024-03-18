package com.jsp.medishop.response;

import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @author vikas
 * @param <T>
 */
@Component
@Data
public class ResponseStructure<T> {

	private T data;
	private String msg;
	private int status;

}
