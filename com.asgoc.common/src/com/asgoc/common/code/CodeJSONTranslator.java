package com.asgoc.common.code;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.asgoc.common.code.json.JSONManipulator;
import com.asgoc.common.code.json.JSONManipulatorFactory;

abstract class CodeJSONTranslator {
	
	/**
	 * Provides the JSON string representation of the provided
	 * Code.Metadata instance. It has the following general structure,
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
	 *
	 * @param metadata
	 * 			instance whose JSOn representation is required
	 * 
	 * @return
	 * 		A JSON string created from the Cde.Metadata instance.
	 */
	static String getMetadataJSON(Code.Metadata metadata) {
		
		JSONManipulator metadataJSONManip = jsonManipProvider.getConcreteJSONManipulator(metadata);
		return metadataJSONManip.toString(PRETTY_PRINT_FACTOR);
				
	}
	
	/**
	 * Provides the JSON string representation of the JSON created by
	 * appending the JSON representation of the provided 
	 * Code.Metadata.CrucialMetadata to the provided indexJSON. The
	 * created new JSON representation serves as the new index structure
	 * which has the following general structure, 
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
	 * 
	 * @param IndexJSON
	 * 			JSON string representation of the index to which the JSON
	 * representation on crucialMetadata is to be appended. When null, a new
	 * JSON string is formed from the crucilaMetadata.
	 * 
	 * @param crucialMetadata
	 * 			The Code.Metadata.CrucialMetadata instance which is appended 
	 * to the provided index JSON (or) used to create the new index JSON
	 *  
	 * @return
	 * 			JSON String representation of the index
	 */
	static String getIndexJSON(String IndexJSON, Code.Metadata.CrucialMetadata crucialMetadata) {
		
		Map<String, String> hmap = new HashMap<>();
		hmap.put(TITLE_IDENTIFIER, crucialMetadata.title);
		hmap.put(DESCRIPTION_IDENTIFIER, crucialMetadata.description.toString());
		
		
		JSONManipulator indexJSONManip = (IndexJSON != null) ? jsonManipProvider.getConcreteJSONManipulator(IndexJSON)
																	: jsonManipProvider.getConcreteJSONManipulator();
		indexJSONManip.appendMap(crucialMetadata.relativeLocation.toString(), hmap);
		
		return indexJSONManip.toString(PRETTY_PRINT_FACTOR);
		
	}
	
	/**
	 * Provides the JSON string representation of the JSON created 
	 * from the instance of Code.Metadata.CrucialMetadata. The
	 * created new JSON representation serves as the new index structure.
	 *  
	 * @param crucialMetadata 
	 * 			The Code.Metadata.CrucialMetadata instance which is
	 * used to create the new index JSON
	 * 
	 * @return
	 * 			JSON String representation of the newly created index
	 */
	static String getIndexJSON(Code.Metadata.CrucialMetadata crucialMetadata) {
		
		return getIndexJSON(null, crucialMetadata);
		
	}
	
	/**
	 * Provides a Code.Metadata.CrucialMetdata instance constructed from the JSON
	 * string that was previously formed from it.
	 * 
	 * @param crucialMetadataJSON
	 * 			The JSON string used to create the Code.Metadata.CrucialMetdata 
	 * instance. 
	 * 
	 * @return
	 * 			Code.Metadata.CrucialMetdata instance created from the JSON string
	 */
	static Code.Metadata.CrucialMetadata getCrucialMetadata(String crucialMetadataJSON) {
		JSONManipulator crucialMDJSONManip = jsonManipProvider.getConcreteJSONManipulator(crucialMetadataJSON);
		
		String title = crucialMDJSONManip.getString(TITLE_IDENTIFIER);
		StringBuilder description = new StringBuilder(crucialMDJSONManip.getString(DESCRIPTION_IDENTIFIER));
		Path location = Paths.get(crucialMDJSONManip.getString(LOCATION_IDENTIFIER));
		
		return new Code.Metadata.CrucialMetadata(title, description, location);
	}
	
	/**
	 * Provides the Code.Metadata representation of the given JSON string
	 *  
	 * @param metadataJSON 
	 * 			String representation of the JSON that is used to create a 
	 * Code.Metdata instance
	 * 
	 * @return
	 * 			Code.Metadata instance representation of the given JSON 
	 * string
	 * 	
	 */
	static Code.Metadata getMetadata(String metadataJSON) {
		JSONManipulator metadataJSONManip = jsonManipProvider.getConcreteJSONManipulator(metadataJSON);
		
		String crucialMetadataJSON = metadataJSONManip.getString(CRUCIAL_METADATA_IDENTIFIER);
		StringBuilder documentation = new StringBuilder(metadataJSONManip.getString(DOCUMENTATION_IDENTIFIER));
		Collection<String> headers = metadataJSONManip.getStringArray(HEADER_IDENTIFIER);
		
		return new Code.Metadata(getCrucialMetadata(crucialMetadataJSON), documentation, headers);
	}
	
	/**
	 * Provides a list of Code.Metadata.CrucialMetdata instances formed from the 
	 * provided JSON string, which was previously generated to store the  index.
	 *  
	 * @param indexJSON
	 * 			String representation of the JSON that is used create the list of 
	 * Code.Metadata.CrucialMetdata instances
	 * 
	 * @return
	 * 			List of Code.Metadata.CrucialMetdata generated from the provided 
	 * index JSON string
	 */
	static Collection<Code.Metadata.CrucialMetadata> getIndex(String indexJSON) {
		
		JSONManipulator indexJSONManip = jsonManipProvider.getConcreteJSONManipulator(indexJSON);
		Collection<Code.Metadata.CrucialMetadata> index = new ArrayList<>();
		
		for(Iterator<String> keys = indexJSONManip.getKeys(); keys.hasNext(); ) {
			String title = keys.next();
			
			JSONManipulator value = jsonManipProvider.getConcreteJSONManipulator(indexJSONManip.get(title));
			StringBuilder description = new StringBuilder(value.get(DESCRIPTION_IDENTIFIER).toString());
			Path locationOfCode = Paths.get(value.get(LOCATION_IDENTIFIER).toString());
			
			index.add(new Code.Metadata.CrucialMetadata(title, description, locationOfCode));
		}
		
		return index;
	}
	
	private static final String TITLE_IDENTIFIER = "title";
	private static final String HEADER_IDENTIFIER = "headers";
	private static final String DOCUMENTATION_IDENTIFIER = "documentation";
	private static final String CRUCIAL_METADATA_IDENTIFIER = "crucialMetadataIdentifier";
	private static final String LOCATION_IDENTIFIER = "location";
	private static final String DESCRIPTION_IDENTIFIER = "description";
	
	private static final int PRETTY_PRINT_FACTOR = 3;
	
	private static JSONManipulatorFactory jsonManipProvider;
}
