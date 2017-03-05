package com.asgoc.common.code.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Class that serves as a wrapper for the JSON creation and manipulation library.
 * The class is an implementation of the JSONManipulaotr interface. 
 * It provides an interface only to a small portion of the library that is required.
 * 
 * Most of the methods return the instance of the JSONManipulatorAdaptor used to invoke them
 * to allow nesting of calls.
 * 
 * @author Kaartic Sivaraam
 */
class ConcreteJSONManipulator implements JSONManipulatorAdaptor {
/**
	 * Creates an instance of the JSONManipulatorAdaptor by creating a 
	 * JSONObject with it's default constructor.
	 */
	public ConcreteJSONManipulator() {
		json = new JSONObject();
	}
	
	/**
	 * Creates an instance of the ConcreteJSONManipulator by initialising
	 * the JSONObject field with a JSON object representation of 
	 * the provided String
	 * @param sourceJSON
	 */
	public ConcreteJSONManipulator(String sourceJSON) {
		json = new JSONObject(sourceJSON);
	}
	
	/**
	 * Creates an instance of the ConcreteJSONManipulator by initialising
	 * the JSONObject field with a JSON object representation of 
	 * the provided Object. The keys and values are found using bean getters.
	 *  
	 * @param obj 
	 * 					The string using which the JSONObject 
	 * is initialised.
	 */
	public ConcreteJSONManipulator(Object obj) {
		json = new JSONObject(obj);
	}
	
	//=================== Manipulating Methods - Start =========================
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
    //================= Manipulating Methods - End =========================
    
    //====================== Accessing Methods - Start======================    
	/**
	 * Provides the String representation of the JSONObject field.
	 * 
	 * @return
	 * 			A string that represents the current state of the 
	 * JSONObject field.
	 */
	@Override
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
	@Override
	public String toString(int indentFactor) {
		return json.toString(indentFactor);
	}
	
	/**
	 * Provides the number of keys in the JSONObject field.
	 * 
	 * @return
	 * 			the number of keys in the JSONObject field
	 */
	public int length() {
		return json.length();
	}
	
	/**
	 * Provides the value of the given key in the JSONObject field.
	 * 
	 * @param key
	 * 			key used to find the value
	 * 
	 * @return
	 * 			value corresponding to the given key
	 */
	public Object get(String key) {
		return json.get(key);
	}
	
	/**
	 * Provides the String associated with the given key in the 
	 * JSONObject field.
	 * 
	 * @param key
	 * 			key used to get the String value
	 * 
	 * @return
	 * 			String associated with the given key
	 */
	public String getString(String key) {
		return json.getString(key);
	}
	
	/**
	 * Provides an enumeration of the keys in the JSONObject field.
	 * 
	 * @return
	 * 		Iterator that enumerates the keys present in the JSONObject field
	 */
	public Iterator<String> getKeys() {
		return json.keys();
	}
	
	/**
	 * Provides a collection of the array of JSON values which are of 
	 * String type.
	 * 
	 * @param key
	 * 			key which has as it's value an array of JSON objects
	 * of the same type 
	 * 
	 * @return
	 * 			Collection of JSON values
	 */
	public Collection<String> getArray(String key) {
		JSONArray array = json.getJSONArray(key);
		Collection<String> jsonValues = new ArrayList<>();
		
		for(Iterator<Object> arrayIter = array.iterator(); arrayIter.hasNext(); ) {
			jsonValues.add(arrayIter.next().toString());
		}
		
		return jsonValues;
	}

	//====================== Accessing Methods - End======================
	
    /**
     * The JSONObject field used for all operations.
     */
    private final JSONObject json;
}
