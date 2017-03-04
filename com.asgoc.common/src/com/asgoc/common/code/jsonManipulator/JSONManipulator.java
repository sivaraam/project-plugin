package com.asgoc.common.code.jsonManipulator;

import org.json.JSONObject;

import com.asgoc.common.code.Code;

/**
 * Class used to generate and aprse JSON.
 * 
 * @author Kaartic Sivaraam
 */
public abstract class JSONManipulator {
   
	/**
	 * Method used to get a JSONObject representation of a Code.Metadata.CrucialMetadata instance.
	 * 
	 * @param crucialmd
	 * 				The Code.Metadata.CrucialMetadata instance that needs to be converted
	 * to a JSONObject.
	 * 
	 * @return
	 * 			The JSONObject representation of the given string.
	 */
	public static JSONObject getJSON(Code.Metadata.CrucialMetadata crucialmd) {
		JSONObject crucialJSON = new JSONObject();
		crucialJSON.put("title", crucialmd.getTitle());
		crucialJSON.put("description", crucialmd.getDescription());
		crucialJSON.put("location",crucialmd.getLocation());
		return crucialJSON;
	}
	
	/**
	 * Method used to get a JSON String representation of a Code.Metadata.CrucialMetadata instance.
	 * The JSON String has the followings (the ordering of the keys are not guaranteed, as 
	 * JSON Objects are un-ordered,
	 * 
	 * <pre>
	 * {
     *    "location" : "<location-relative-to-base-path>",
     *    "description": "<brief-description>",
 	 *    "title": "<title-of-code-block>"
	 * }
	 * </pre>
	 * @param crucialmd
	 * 				The Code.Metadata.CrucialMetadata instance that needs to be converted
	 * to a JSON string.
	 * 
	 * @return
	 * 			The String representation of the JSON which, typically, has a structure
	 * described above. 
	 */
	public static String getJSONString(Code.Metadata.CrucialMetadata crucialmd) {
		return getJSON(crucialmd).toString();
	}
	

	/**
	 * Method used to get a JSON String representation of a Code.CodeMetadata instance.
	 * The JSON String has the followings (the ordering of the keys are not guaranteed, as 
	 * JSON Objects are un-ordered,
	 * 
	 * <pre>
	 * {
     *    "headers": [
     *       "header-1",
     *       "header-2"
	 *    ],
     *    "documentation": "<documentation>",
     *    "location" : "<location-relative-to-base-path>",
     *    "description": "<brief-description>",
 	 *    "title": "<title-of-code-block>"
	 * }
	 * </pre>
	 * 
	 *  
	 * @param metadata 
	 * 			The object representing the code's metadata 
	 * @return
	 * 			The String representing the JSON  representation of a Code's metadata. 
	 * 		
	 */
	private String getJSONString(Code.Metadata metadata) {
		
		JSONObject metadataJSON = new JSONObject();
		JSONObject crucialJSON = getJSON(metadata.getCrucialMetadata());  					
		
		metadataJSON.put("crucial", crucialJSON);
		metadataJSON.put("documentation", metadata.getDocumentation());
		metadataJSON.put("requiredHeaders", metadata.getHeaders());
		
		return metadataJSON.toString(3);
		
	}
	
}
