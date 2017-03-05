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
	 * Inner class used to represent the metadata of a code block.
	 * 
	 * @author Kaartic Sivaraam
	 *
	 */
	public static final class Metadata {
		
		/**
		 * Inner class used to represent the frequently accessed metadata.  
		 * 
		 * @author Kaartic Sivaraam
		 *
		 */
		public static final class CrucialMetadata {
			
			CrucialMetadata (String title, StringBuilder description, Path relativeLocation) {
				this.title = title;
				this.description = description;
				this.relativeLocation = relativeLocation;
			}
			
			public String getTitle() {
				return title;
			}
			
			public StringBuilder getDescription() {
				return description;
			}
			
			public Path getLocation() {
				return relativeLocation;
			}
			
			/**
			 * Title of the code block.
			 */
			String title;
			
			/**
			 * Brief description about the code block.
			 */
			StringBuilder description;
			
			/**
			 * The location of the code block relative to the base path 
			 * of the code repository.
			 */
			Path relativeLocation;
		}
		
		Metadata(String title, StringBuilder description,
				 StringBuilder documentation, List<String> requiredHeaders,
				 Path relativeLocation) {
			this.crucialMetadata =  new CrucialMetadata(title, description, relativeLocation);
			this.documentation = documentation;
			this.requiredHeaders = requiredHeaders;
		}
		
		public CrucialMetadata getCrucialMetadata() {
			return crucialMetadata;
		}
		
		public StringBuilder getDocumentation() {
			return documentation;
		}

		public List<String> getHeaders() {
			return requiredHeaders;
		}
			
		/**
		 * An instance of the crucial metadata class used to 
		 * represent the crucial metadata. 
		 */
		CrucialMetadata crucialMetadata;
		
		/**
		 * Documentation about the code block. This would allow
		 * a developer to know how to use a code block. 
		 */
		StringBuilder documentation;
		
		/**
		 * The list representing the header files required for 
		 * the proper working of the code block.
		 */
		List<String> requiredHeaders;
		
	}
	
	public Code(String title, StringBuilder description,
				StringBuilder documentation, List<String> requiredHeaders,
				Path relativeLocation, StringBuilder codeBlock) {
		this.metadata = new Metadata(title, description, documentation, requiredHeaders, relativeLocation); 
		this.codeBlock = codeBlock;
	}
	
	public Metadata getMetadata() {
		return metadata;
	}
	
	public StringBuilder getCodeBlock() {
		return codeBlock;
	}
	
	/**
	 * An instance of the Metadata class used to store metadata
	 * about the code.
	 */
	Metadata metadata;
	
	/**
	 * The code block which is used to represent the  block of 
	 * code which is described by the metadata.
	 */
	StringBuilder codeBlock;
	
}
