package com.asgoc.common.code;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.asgoc.common.code.jsonManipulator.JSONManipulator;
import com.asgoc.common.code.repositoryAccessor.InvalidRepositoryOperation;
import com.asgoc.common.code.repositoryAccessor.RepositoryAccessor;

public final class CodeRepository {
	
	/**
	 * 
	 * @param basePath
	 * 					The base path of the code repository. It should not end with a '/' 
	 * @throws InvalidRepositoryOperation
	 * 					Thrown when the base path does not exist
	 */
	public CodeRepository(String basePath) throws InvalidRepositoryOperation { 
		repoAccessor = new RepositoryAccessor(basePath);
	}
	
	/**
	 * Function to get a JSON String representation of the CodeMetadata instance. 
	 * All values except the location are found in the JSON string as the path is implicit.
	 *  
	 * @param code 
	 * 			The object representing the code's metadata 
	 * @return
	 * 			The String representing the JSON  representation of a Code's metadata. 
	 * It contains all values except the location of the code (it is implicit). 
	 * 		
	 */
	private String getJSONString(Code.CodeMetadata code) {
		
		//Add the key value pairs that are (String, String) pairs
		Map<String, String> stringPairs = new HashMap<>();
		JSONManipulator jm = new JSONManipulator();
		
		stringPairs.put("title", code.title);
		stringPairs.put("description", code.description.toString());
		stringPairs.put("documentation", code.documentation.toString());
		
		jm.appendMap("metadata",stringPairs);
		
		//Add the key value pair that is of type (String, Collection<?>)
		jm.appendArray("requiredHeaders", code.requiredHeaders);
		
		return jm.getJSONString();
		
	}
	
	/**
	 * Method used to add code to the code and it's metadata to the code repository
	 * @param location 
	 * 					The location represents the category of the Code block.
	 * Put another way, it is the sub-directory in which the code is stored.
	 * @param fileName
	 * @param metaData
	 * @param codeBock
	 * @throws InvalidRepositoryOperation
	 */
	public void addCode(String location, String fileName, Code code) throws InvalidRepositoryOperation {
		
		//create a JSONString of the the metadata, from the fields of the "CodeMetadata" class and write to 
		boolean locationProvided = location != "" || location != null ;  
		String metadataLocation = "metadata/"+(locationProvided ? location+"/" : "" )+fileName+".json";
		String codeLocation = "functions/"+(locationProvided ? location+"/" : "")+fileName;
		String metadata = getJSONString(code.metadata);
		
		repoAccessor.writeToNewFile(metadataLocation, metadata);
		repoAccessor.writeToNewFile(codeLocation, code.codeBlock.toString());
		
	}
	
	CodeMetadata getCode(Path locationOfCode){
		
	}
	
	List<Map.Entry<String, Path>> getCodes() {
		
	}
	
	private RepositoryAccessor repoAccessor;

}
