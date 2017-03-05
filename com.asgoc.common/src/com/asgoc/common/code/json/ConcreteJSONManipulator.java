package com.asgoc.common.code.json;

import java.util.Collection;
import java.util.Map;
import org.json.JSONObject;

/**
 * Class that serves as a wrapper for the JSON creation and manipulation library.
 * The class is an implementation of the JSONManipulaotr interface. 
 * It provides an interface only to a small portion of the library that is required.
 * 
 * Most of the methods return the instance of the JSONManipulator used to invoke them
 * to allow nesting of calls.
 * 
 * @author Kaartic Sivaraam
 */
class ConcreteJSONManipulator implements JSONManipulator {
/**
	 * Creates an instance of the JSONManipulator by creating a 
	 * JSONObject with it's default constructor.
	 */
	public ConcreteJSONManipulator() {
		json = new JSONObject();
	}
	
	/**
	 * Creates an instance of the ConcreteJSONManipulator by initialising
	 * the JSONObject field with a JSON object representation of 
	 * the provided Object
	 *  
	 * @param jsonString 
	 * 					The object using which the JSONObject 
	 * is initialised.
	 */
	public ConcreteJSONManipulator(Object obj) {
		json = new JSONObject(obj);
	}
	
	/**
	 * Provides the String representation of the JSONObject field.
	 * 
	 * @return
	 * 			A string that represents the current state of the 
	 * JSONObject field.
	 */
	public String toString() {
		return json.toString();
	}
	
	/**
	 * Provides a <em> pretty-printed </em> String representation 
	 * of the JSONObject field.
	 * 
	 * @param indentFactor
	 * 						The indentFactor used to generate 
	 * the pretty-printed JSON string.		
	 * 	
	 * @return
	 * 			A string that represents the current state of the 
	 * JSONObject field, pretty-printed with specified indents.  
	 */
	public String toString(int indentFactor) {
		return json.toString(indentFactor);
	}
	
	/**
	 * Appends (key, value) to the JSONObject field.
	 * 
	 * @param key 
	 * 				key with which the specified value is to be associated
	 * @param value
	 * 				value to be associated with the specified key
	 * 
	 * @return
	 * 			The handle of the ConcreteJSONManipulator to allow nested 
	 * operations.
	 */
	public ConcreteJSONManipulator appendPair(String key, Object value) {
		json.put(key, value);
		return this;		
	}
	
	/**
     * Appends (key,array of JSON values) to the JSONObject field. The array of
     * JSON values is constructed from the Collection.
     * 
     * @param key
     * 				key with which the specified array of JSON values are
     * to be associated 
     * 
     * @param arrayCollection
     * 				Collection of values used to construct an array of JSON values
     * 
     * @return
     * 			The handle of the ConcreteJSONManipulator to allow nested 
	 * operations.
     */
    public ConcreteJSONManipulator appendArray(String key, Collection<?> arrayCollection) {
      	json.put(key, arrayCollection);
       	return this;
    }

    /**
	 * Appends (key, JSONObject) to the JSONObject field. The JSONObject
	 * is constructed from the Map. The key is associated with the constructed
	 * JSONObject.
	 * 
	 * @param key
	 * 				key with which the provided Map (that would be converted to a
	 *  JSONObject) would be associated.  
	 * 
	 * @param keyValues
	 * 					Map of (key, value) pairs that constitute the value of the
	 *  provided key. The Map would be converted to a JSONObject.
	 * 
	 * @return
	 * 			The handle of the ConcreteJSONManipulator to allow nested 
	 * operations.
	 */
    public ConcreteJSONManipulator appendMap(String key, Map<? ,?> keyValues) {
        json.put(key, keyValues);
        return this;        
    }
    
    /*
    /**
     * Provides a Map of (String, String) pairs representing the JSON key value pairs.
     * This should be used only when the JSONObject is known to contain only
     * (String, String) pairs.
     * 
     * @return
     * 			Map of (String, String) pairs
     
    public Map<String,String> toCollection() {
        
    	Map<String, String> keyValues = new HashMap<>();
        
        for(String key : json.keySet()) {
        	String value = json.getString(key);
        	keyValues.put(key, value);
        }
        
        return keyValues;
    }
        */
    /**
     * The JSONObject field used for all operations.
     */
    private JSONObject json;
}
