package com.asgoc.common.code;

import java.nio.file.Path;
import java.util.List;

/**
 * Class used to represent a Code block along with it's metadata
 * 
 * @author Kaartic Sivaraam
 *
 */
public final class Code {
	
	/**
	 * Inner class used to represent the metadata of a code block
	 * 
	 * @author Kaartic Sivaraam
	 *
	 */
	public final class CodeMetadata {
		
		public CodeMetadata(String title, StringBuilder description,
			 StringBuilder documentation, List<String> requiredHeaders,
			 Path location) {
		
			this.title = title;
			this.description = description;
			this.documentation = documentation;
			this.requiredHeaders = requiredHeaders;
			this.location = location;
		}
		
		public String getTitle() {
			return title;
		}
		
		public StringBuilder getDescription() {
			return description;
		}
		
		public StringBuilder getDocumentation() {
			return documentation;
		}
		
		public Path getLocation() {
			return location;
		}
		
		public List<String> getHeaders() {
			return requiredHeaders;
		}
			
		String title;
		StringBuilder description;
		StringBuilder documentation;
		Path location;
		List<String> requiredHeaders;
		
	}
	
	public Code(CodeMetadata metadata, StringBuilder codeBlock) {
		this.metadata = metadata;
		this.codeBlock = codeBlock;
	}
	
	public CodeMetadata getMetadata() {
		return metadata;
	}
	
	public StringBuilder getCodeBlock() {
		return codeBlock;
	}
	
	CodeMetadata metadata;
	StringBuilder codeBlock;
	
}
