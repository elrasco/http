package it.lucarasconi.common.http;

import java.io.Serializable;

public class Response implements Serializable{
	
	private static final long serialVersionUID = -8737274245589812947L;
	private String contentType;
	private String content;
	private int status;

	public Response() {
		
	}
	
	public String getContent() {
		return content;
	}

	public int getStatus() {
		return status;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
