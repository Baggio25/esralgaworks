package com.baggio.esralgaworks.api.exceptionhandler;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;

/*
 * RFC 7807 
 */

@JsonInclude(Include.NON_NULL)
@Getter
@Builder
public class Problem {

	private Integer status;
	private String type;
	private String title;
	private String detail;
	private String userMessage;

	private List<Field> fields;
	
	@Getter
	@Builder
	public static class Field {
		private String name;
		private String userMessage;
	}

}
