package com.blog.search.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApplicationException extends RuntimeException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 85706323977302504L;
	
	public static final int DEFAULT_STATUS = 500;
	
	private final String code;
	private final int status;
	
	public ApplicationException(String code, String message) {
	    this(code, message, null, DEFAULT_STATUS);
	}
	
	public ApplicationException(String code, String message, int status) {
	    this(code, message, null, status);
	}
	
	public ApplicationException(String code, String message, Throwable cause, int status) {
	    super(message, cause);
	    this.code = code;
	    this.status = status;
	}
	
}
