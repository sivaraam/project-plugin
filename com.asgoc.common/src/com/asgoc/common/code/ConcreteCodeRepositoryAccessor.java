package com.asgoc.common.code;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.asgoc.common.code.json.JSONManipulatorAdaptor;
import com.asgoc.common.code.json.JSONManipulatorFactory;
import com.asgoc.common.code.repository.InvalidRepositoryOperation;
import com.asgoc.common.code.repository.RepositoryAccessorFactory;
import com.asgoc.common.code.repository.RepositoryAccessorAdaptor;

/**
 * The class used to access and manipulate the Code repository.
 * It is an implementation of the CodeRepositoryAccessorAdaptor interface. 
 * 
 * @author Kaartic Sivaraam
 *
 */
final class ConcreteCodeRepositoryAccessor implements CodeRepositoryAccessorAdaptor{
	
	/** 
	 * @param basePath
	 * 					The base path of the code repository. 
	 *   
	 * @throws InvalidRepositoryOperation
	 * 					Thrown when the base path does not exist
	 */
	public ConcreteCodeRepositoryAccessor(String basePath) throws InvalidRepositoryOperation { 
		RepositoryAccessorFactory raf = RepositoryAccessorFactory.getInstance();
		repoAccessor = raf.getBufferedCodeRepositoryAccessor(basePath);
		jsonManipProvider = JSONManipulatorFactory.getInstance();
	}
	
	/**
	 * Method used to store the metadata of the code block to the metdata file.
	 * The JSON of the metadata has the following structure,
	 * 
	 * <pre>
	 * {
   	 *    "headers": [
     *       "header-1",
     *       "header-2"
     *    ],
     *    "documentation": "documentation",
     *    "crucialMetadata": {
     *       "description": "\"brief-descitpion-of-code",
     *       "location": "/path/to/code",
     *       "title": "title-of-code"
     *    }
     * }
	 * </pre>
	 * @param metadataLocation
	 * 			The location of the metadata file. As in other cases this 
	 * location is also relative to the base path of the repository.
	 *  
	 * @param metadata
	 * 			The metadata which is converted to JSON and stored.
	 * 
	 * @throws InvalidRepositoryOperation
	 * 			Thrown when the invalid repository operations are performed. 
	 * For more information, refer documentation of RepositoryAccessorAdaptor class. 
	 */
	private void storeMetadata(Path metadataLocation, Code.Metadata metadata) throws InvalidRepositoryOperation {
		
		jsonManip = jsonManipProvider.getConcreteJSONManipulator(metadata);
		String metadataString = jsonManip.toString(3);
		repoAccessor.writeToNewFile(metadataLocation, metadataString);
		
	}
	
	/**
	 * Method used to update the index file of the repository. In case 
	 * the index file does not exist it is created. This index is used 
	 * by the {@link #getCrucialMetadata(Path)} method to return the list.
	 * The index has the following general structure,
	 * 
	 * <pre>
	 * {
	 *     "location-1": {
	 *        "title": "title-1",
	 *        "description": "description-1",
	 *     },
	 *     "location-2": {
	 *        "title": "title-2",
	 *        "description": "description-2",
	 *     },
	 *     ...
	 * }
	 * </pre>
	 * @param indexLocation
	 * 			Location of the index file relative to the base path 
	 * of the repository.
	 * 
	 * @param crucialMd
	 * 			The CurcialMetadata instance used to update the index file.
	 * 
	 * @throws InvalidRepositoryOperation
	 * 			Thrown when the invalid repository operations are performed. 
	 * For more information, refer documentation of RepositoryAccessorAdaptor class. 
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
			jsonManip = jsonManipProvider.getConcreteJSONManipulator(indexString);
			jsonManip.appendMap(crucialMd.getLocation().toString(), hmap);
			
			//write new JSON String back to file
			indexString = jsonManip.toString(3);
			repoAccessor.appendToFile(indexLocation, indexString);
		}
		catch(InvalidRepositoryOperation iro) {
			//If Index file does not exist create it (should occur only once)
			if(iro.getMessage().equals("File does not exist")) {
				//Add the JSON representation for the string
				jsonManip = jsonManipProvider.getConcreteJSONManipulator();
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
	 * For more information refer documentation of RepositoryAccessorAdaptor class. 
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

	
/*	
	public Code getCode(Path location) {
	
	}

	public List<Code.Metadata.CrucialMetadata> getCrucialMetadata(Path location) {
		Path indexLocation = Paths.get( (location.toString() != "" ? location.toString() : "") + "index.json" );
		StringBuilder index
	}
*/
	private JSONManipulatorFactory jsonManipProvider;
	private JSONManipulatorAdaptor jsonManip; 
	private RepositoryAccessorAdaptor repoAccessor;

}
