package com.asgoc.common.code;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import com.asgoc.common.code.jsonManipulator.JSONManipulator;
import com.asgoc.common.code.repositoryAccessor.InvalidRepositoryOperation;
import com.asgoc.common.code.repositoryAccessor.RepositoryAccessor;

/**
 * The class used to access and manipulate the Code repository.
 * 
 * @author Kaartic Sivaraam
 *
 */
public final class CodeRepository {
	
	/** 
	 * @param basePath
	 * 					The base path of the code repository. 
	 *   
	 * @throws InvalidRepositoryOperation
	 * 					Thrown when the base path does not exist
	 */
	public CodeRepository(String basePath) throws InvalidRepositoryOperation { 
		repoAccessor = new RepositoryAccessor(basePath);
	}
	
	/**
	 * Method used to store the metadata of the code block to the metdata file
	 * 
	 * @param metadataLocation
	 * 			The location of the metadata file. As in other cases this 
	 * location is also relative to the base path of the repository.
	 *  
	 * @param metadata
	 * 			The metadata which is converted to JSON and stored.
	 * 
	 * @throws InvalidRepositoryOperation
	 * 			Thrown when the invalid repository operations are performed. 
	 * For more information, refer documentation of RepositoryAccessor class. 
	 */
	private void storeMetadata(Path metadataLocation, Code.Metadata metadata) throws InvalidRepositoryOperation {
		
		jsonManip = new JSONManipulator(metadata);
		String metadataString = jsonManip.toString(3);
		repoAccessor.writeToNewFile(metadataLocation, metadataString);
		
	}
	
	/**
	 * Method used to update the index file of the repository. In case 
	 * the index file does not exist it is created.
	 * 
	 * @param indexLocation
	 * 			Location of the index file relative to the base path 
	 * of the repository.
	 * 
	 * @param crucialMd
	 * 			The CurcialMetadata instance used to update the index file.
	 * 
	 * @throws InvalidRepositoryOperation
	 * 			Thrown when the invalid repository operations are performed. 
	 * For more information, refer documentation of RepositoryAccessor class. 
	 */
	private void updateIndex(Path indexLocation, Code.Metadata.CrucialMetadata crucialMd) throws InvalidRepositoryOperation {
		
		String indexString = null;
		Map<String, String> hmap = new HashMap<>();
		hmap.put("title", crucialMd.getTitle());
		hmap.put("description", crucialMd.getDescription().toString());
		
		
		try {
			//Get JSON found in index
			indexString = repoAccessor.readFromFile(indexLocation).toString();
			
			//Append the data about new index file to obtained JSON
			jsonManip = new JSONManipulator(indexString);
			jsonManip.appendMap(crucialMd.getLocation().toString(), hmap);
			
			//write new JSON String back to file
			indexString = jsonManip.toString(3);
			repoAccessor.appendToFile(indexLocation, indexString);
		}
		catch(InvalidRepositoryOperation iro) {
			//If Index file does not exist create it (should occur only once)
			if(iro.getMessage().equals("File does not exist")) {
				//Add the JSON representation for the string
				jsonManip = new JSONManipulator();
				jsonManip.appendMap(crucialMd.getLocation().toString(),hmap);
				
				//Write JSON to new file
				indexString = jsonManip.toString(3); 
				repoAccessor.writeToNewFile(indexLocation, indexString);
			}
			else 
				throw iro;
		}
	}
	
	/**
	 * Method used to store the code and it's metadata to the specified location which should 
	 * be relative to the base path of the code repository.
	 * 
	 * @param code
	 * 				The instance of the Code class that represents the code that needs
	 * to be added to the repository.
	 * 
	 * @throws InvalidRepositoryOperation
	 * 				Thrown when the invalid repository operations are performed. 
	 * For more information refer documentation of RepositoryAccessor class. 
	 */
	public void storeCode(Code code) throws InvalidRepositoryOperation {
		
		//TODO : Remove the extension before naming the metadata file
		Path codeLocation = code.metadata.crucialMetadata.relativeLocation;
		Path metadataLocation = Paths.get(codeLocation.toString()+"-metadata.json");
		Path parent = codeLocation.getParent();
		Path indexLocation = Paths.get( (parent != null ? parent.toString() : "") + "index.json" );
		
		storeMetadata(metadataLocation, code.metadata);
		repoAccessor.writeToNewFile(codeLocation, code.codeBlock.toString());
		updateIndex(indexLocation, code.metadata.crucialMetadata);
		
	}

	
	
	/*Code getCode(String relativeCodeLocation) {
		
	}
	
	Code.Metadata.CrucialMetadata getCodes() {
		
	}
	*/
	
	//TODO Find perfect location of JSONManipulator instance
	private static JSONManipulator jsonManip;
	private RepositoryAccessor repoAccessor;

}
