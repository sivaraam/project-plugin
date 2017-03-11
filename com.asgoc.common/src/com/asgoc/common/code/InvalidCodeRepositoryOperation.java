package com.asgoc.common.code;

public class InvalidCodeRepositoryOperation extends Exception {
	
	private static final long serialVersionUID = 1L;

	public InvalidCodeRepositoryOperation() {
		reason = "Unknown reason";
	}
	
	public InvalidCodeRepositoryOperation(String reason) {
		this.reason = reason;
	}
	
    @Override
	public String getMessage() {
            return reason+((super.getMessage() != null) ? super.getMessage() : "");
	}
	
	String reason;
}
