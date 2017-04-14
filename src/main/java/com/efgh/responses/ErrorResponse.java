package com.efgh.responses;

public class ErrorResponse {

	String error;
	String reason;
	
	public ErrorResponse(Exception ex) {
		error = ex.getMessage();
		reason = ex.getCause() != null?ex.getCause().getMessage() : ex.getMessage();
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
