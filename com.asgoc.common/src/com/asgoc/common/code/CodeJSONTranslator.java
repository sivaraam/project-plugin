package com.asgoc.common.code;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

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
		
		return new JSONObject(metadata).toString(PRETTY_PRINT_FACTOR);
				
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
		
		JSONObject indexJSONObj = (IndexJSON != null) ? new JSONObject(IndexJSON) : new JSONObject();
		indexJSONObj.append(crucialMetadata.relativeLocation.toString(), hmap);
		
		return indexJSONObj.toString(PRETTY_PRINT_FACTOR);
		
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
	 * Provides a collection of the array of String JSON values.
	 * 
	 * @param json
	 * 			JSONObject which contains the key which should have
	 * an array of Sting values
	 * 
	 * @param key
	 * 			key which has as it's value an array of JSON objects
	 * of the same type
	 * 
	 * @return
	 * 			Collection of String JSON values
	 */
	private static Collection<String> getStringArray(JSONObject json, String key) {
		JSONArray array = json.getJSONArray(key);
		Collection<String> jsonValues = new ArrayList<>();
		
		for(Iterator<Object> arrayIter = array.iterator(); arrayIter.hasNext(); ) {
			jsonValues.add(arrayIter.next().toString());
		}

		return jsonValues;
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
		JSONObject crucialMDJSONObj = new JSONObject(crucialMetadataJSON);
		
		String title = crucialMDJSONObj.getString(TITLE_IDENTIFIER);
		StringBuilder description = new StringBuilder(crucialMDJSONObj.getString(DESCRIPTION_IDENTIFIER));
		Path location = Paths.get(crucialMDJSONObj.getString(LOCATION_IDENTIFIER));
		
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
		JSONObject metadataJSONObj = new JSONObject(metadataJSON);
		
		String crucialMetadataJSON = metadataJSONObj.getJSONObject(CRUCIAL_METADATA_IDENTIFIER).toString();
		StringBuilder documentation = new StringBuilder(metadataJSONObj.getString(DOCUMENTATION_IDENTIFIER));
		Collection<String> headers = getStringArray(metadataJSONObj, HEADER_IDENTIFIER);
		
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
		
		Collection<Code.Metadata.CrucialMetadata> index = new ArrayList<>();
		JSONObject indexJSONObj = new JSONObject(indexJSON);
		
		for(Iterator<String> keys = indexJSONObj.keys(); keys.hasNext(); ) {
			String location = keys.next();
			
			JSONObject currFileJSON = new JSONObject(indexJSONObj.getJSONObject(location).toString());
			StringBuilder description = new StringBuilder(currFileJSON.getString(DESCRIPTION_IDENTIFIER));
			String title = currFileJSON.getString(TITLE_IDENTIFIER);
			
			index.add( new Code.Metadata.CrucialMetadata(title, description, Paths.get(location)) );
	}
		
		return index;
	}
	
	private static final String TITLE_IDENTIFIER = "title";
	private static final String HEADER_IDENTIFIER = "headers";
	private static final String DOCUMENTATION_IDENTIFIER = "documentation";
	private static final String CRUCIAL_METADATA_IDENTIFIER = "crucialMetadata";
	private static final String LOCATION_IDENTIFIER = "location";
	private static final String DESCRIPTION_IDENTIFIER = "description";
	
	private static final int PRETTY_PRINT_FACTOR = 3;
}
