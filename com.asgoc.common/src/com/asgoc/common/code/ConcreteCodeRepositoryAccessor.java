package com.asgoc.common.code;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
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
     *       "description": "brief-description-of-code",
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
		
		JSONManipulatorAdaptor metdataJSONManip = jsonManipProvider.getConcreteJSONManipulator(metadata);
		String metadataString = metdataJSONManip.toString(3);
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
			JSONManipulatorAdaptor indexJSONManip = jsonManipProvider.getConcreteJSONManipulator(indexString);
			indexJSONManip.appendMap(crucialMd.getLocation().toString(), hmap);
			
			//write new JSON String back to file
			indexString = indexJSONManip.toString(3);
			repoAccessor.appendToFile(indexLocation, indexString);
		}
		catch(InvalidRepositoryOperation iro) {
			//If Index file does not exist create it (should occur only once)
			if(iro.getMessage().equals("File does not exist")) {
				//Add the JSON representation for the string
				JSONManipulatorAdaptor newIndexJSONManip = jsonManipProvider.getConcreteJSONManipulator();
				newIndexJSONManip.appendMap(crucialMd.getLocation().toString(),hmap);
				
				//Write JSON to new file
				indexString = newIndexJSONManip.toString(3); 
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
		
		Path codeLocation = code.metadata.crucialMetadata.relativeLocation;
		Path metadataLocation = Paths.get(codeLocation.toString().replaceFirst(FILE_EXTENSION_REGEX, "-metadata.json"));
		Path parent = codeLocation.getParent();
		Path indexLocation = Paths.get( (parent != null ? parent.toString() : "") + "index.json" );
		
		storeMetadata(metadataLocation, code.metadata);
		repoAccessor.writeToNewFile(codeLocation, code.codeBlock.toString());
		updateIndex(indexLocation, code.metadata.crucialMetadata);
		
	}

	/**
	 * Provides the crucial metdata representation of the given JSON string
	 *  
	 * @param crucialMetadataJSON 
	 * 			String representation of the JSON that was formed from a 
	 * Code.Metdata.CrucialMetadata instance
	 * 
	 * @return
	 * 			Code.Metadata.CrucailMetadata instance representation of the given JSON 
	 * string
	 * 	
	 */
	private Code.Metadata.CrucialMetadata getCrucialMetadata(String crucialMetdataJSON) {
		JSONManipulatorAdaptor crucialMDJSONManip = jsonManipProvider.getConcreteJSONManipulator(crucialMetdataJSON);
		
		String title = crucialMDJSONManip.getString("title");
		StringBuilder description = new StringBuilder(crucialMDJSONManip.getString("description"));
		Path location = Paths.get(crucialMDJSONManip.getString("location"));
		
		return new Code.Metadata.CrucialMetadata(title, description, location);
		
	}
	
	/**
	 * Provides the Code.Metadata representation of the given JSON string
	 *  
	 * @param metadataJSON 
	 * 			String representation of the JSON that was formed from a 
	 * Code.Metdata instance
	 * 
	 * @return
	 * 			Code.Metadata instance representation of the given JSON 
	 * string
	 * 	
	 */
	private Code.Metadata getMetadata(String metdataJSON) {
		JSONManipulatorAdaptor metadataJSONManip = jsonManipProvider.getConcreteJSONManipulator(metdataJSON);
		
		String crucialMetadataJSON = metadataJSONManip.getString("crucialMetadata");
		StringBuilder documentation = new StringBuilder(metadataJSONManip.getString("documentation"));
		Collection<String> headers = metadataJSONManip.getStringArray("headers");
		
		return new Code.Metadata(getCrucialMetadata(crucialMetadataJSON), documentation, headers);
	}
 
	/**
	 * Provides the Code representation for the code found at the specified location.
	 * 
	 * @param location 
	 * 			location of the code block
	 * 
	 * @return
	 * 			Code representation of the code found in the given location
	 * 
	 * @throws InvalidRepositoryOperation
	 * 			Thrown when the invalid repository operations are performed. 
	 * For more information refer documentation of RepositoryAccessorAdaptor class.
	 */
 	public Code getCode(Path location) throws InvalidRepositoryOperation {
 		Path codeLocation = location;
		Path metadataLocation = Paths.get(location.toString().replaceFirst(FILE_EXTENSION_REGEX,"-metadata.json"));
		
		StringBuilder codeBlock = repoAccessor.readFromFile(codeLocation);
		StringBuilder metadataJSON = repoAccessor.readFromFile(metadataLocation);
		
		return new Code(getMetadata(metadataJSON.toString()), codeBlock);
		
	}

	/**
	 * Provides the list of crucial metadata about the codes found in the provided location.
	 * 
	 * 
	 * @param location 
	 * 				Location for which the crucial metadata list is to be generated. If the 
	 * codes are in the base path of the repository, then pass an empty string for the location.
	 * 
	 * @return
	 * 			List of Code.Metdata.CrucialMetdata objects that represent the crucial 
	 * metadata of the codes found in the given location.
	 * 
	 * @throws InvalidRepositoryOperation
	 * 			Thrown when the invalid repository operations are performed. 
	 * For more information refer documentation of RepositoryAccessorAdaptor class. 
	 * 
	 */
	public List<Code.Metadata.CrucialMetadata> getCrucialMetadata(Path location) throws InvalidRepositoryOperation {
		Path indexLocation = Paths.get( (location.toString() != "" ? location.toString() : "") + "index.json" );
		StringBuilder indexJSON = repoAccessor.readFromFile(indexLocation);
		List<Code.Metadata.CrucialMetadata> crucialMetadataList = new ArrayList<> ();
		JSONManipulatorAdaptor metadataJSONManip = jsonManipProvider.getConcreteJSONManipulator(indexJSON.toString());
		
		for(Iterator<String> keys = metadataJSONManip.getKeys(); keys.hasNext(); ) {
			
			String title = keys.next();
			JSONManipulatorAdaptor value = jsonManipProvider.getConcreteJSONManipulator(metadataJSONManip.get(title));
			StringBuilder description = new StringBuilder(value.get("description").toString());
			Path locationOfCode = Paths.get(value.get("location").toString());
			
			crucialMetadataList.add(new Code.Metadata.CrucialMetadata(title, description, locationOfCode));
		}
		
		return crucialMetadataList;
	}

	private JSONManipulatorFactory jsonManipProvider; 
	private RepositoryAccessorAdaptor repoAccessor;
	private static final String FILE_EXTENSION_REGEX = "(\\.[a-zA-Z]+)$";
}
