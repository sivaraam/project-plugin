package com.asgoc.common.code;

import java.nio.file.Path;
import java.util.Vector;


public final class Code {
	
	Code(String title, StringBuilder description,
		 StringBuilder documentation, Vector<String> requiredHeaders,
		 StringBuilder codeBlock, Path location) {
	
		this.title = title;
		this.description = description;
		this.documentation = documentation;
		this.requiredHeaders = requiredHeaders;
		this.codeBlock = codeBlock;
		this.location = location;
	}
	
	String getTitle() {
		return title;
	}
	
	StringBuilder getDescripition() {
		return description;
	}
	
	StringBuilder getDocumentation() {
		return documentation;
	}
	
	Vector<String> getHeaders() {
		return requiredHeaders;
	}
	
	StringBuilder getCode() {
		return codeBlock;
	}
	
	Path getLocation() {
		return location;
	}
		
	private String title;
	private StringBuilder description;
	private StringBuilder documentation;
	private Vector<String> requiredHeaders;
	private StringBuilder codeBlock;
	private Path location;
}
