package com.asgoc.common.code.json;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Interface that specifies the required contract for a JSON manipulator.
 * Most methods return references of instances which were used to call them 
 * to allow nesting of method calls.
 * 
 * @author Kaartic Sivaraam
 *
 */
public interface JSONManipulator {

	//================= Manipulating Methods - Start =========================
	public JSONManipulator appendPair(String key, Object bean);
	
	/**
     * Appends (key, array of JSON values) to the JSON representation.
     * The JSON values is constructed from the Collection.
     * 
     * @param key
     * 				key with which the specified array of JSON values are
     * to be associated
     * 
     * @param arrayCollection
     * 				Collection of values used to construct an array of 
     * JSON values
     * 
     * @return
     * 			The handle of the JSONManipulator to allow nested 
	 * operations.
     */
	public JSONManipulator appendArray(String key, Collection<?> arrayCollection);
	
	/**
	 * Appends (key, JSON representation) to the JSON representation. The 
	 * JSON representation is constructed from the Map. The key is associated with
	 * the constructed JSON representation.
	 * 
	 * @param key
	 * 				key with which the provided Map (that would be converted to a
	 *  JSON representation) would be associated.  
	 * 
	 * @param keyValues
	 * 					Map of (key, value) pairs that constitute the value of the
	 *  provided key. The Map would be converted to a JSON representation.
	 * 
	 * @return
	 * 			The handle of the JSONManipulator to allow nested operations.
	 */
	public JSONManipulator appendMap(String key, Map<? , ?> keyValues);
	//===================== Manipulating methods - End =========================
	
	
	//====================== Accessing Methods - Start===========================
	
	/**
	 * Provides the String representation of the JSON representation.
	 * 
	 * @return
	 * 			A string that represents the current state of the 
	 * JSON representation.  
	 */
	@Override
	public String toString();
	
	/**
	 * Provides the <em> pretty-printed </em> String representation 
	 * of the JSONObject field.
	 * 
	 * @param indentFactor
	 * 						The indentFactor used to generate the
	 *  pretty-printed JSON string.		
	 * 	
	 * @return
	 * 			A string that represents the current state of the JSON 
	 * representation, pretty-printed with specified indents.  
	 */
	public String toString(int indentFactor);
	
	/**
	 * Appends (key, value) to the JSON representation.
	 * 
	 * @param key 
	 * 				key with which the specified value is to be associated
	 * @param bean
	 * 				value to be associated with the specified key
	 * 
	 * @return
	 * 			The handle of the JSONManipulator to allow nested 
	 * operations.
	 */
	
	/**
	 * Provides the number of keys in the JSON representation
	 * 
	 * @return
	 * 			the number of keys in the JSON representation
	 */
	public int length();
	
	/**
	 * Provides the value of the given key in the JSON representation
	 * 
	 * @param key
	 * 			key used to find the value
	 * 
	 * @return
	 * 			value corresponding to the given key
	 */
	public Object get(String key);
	
	/**
	 * Provides the String associated with the given key in the 
	 * JSON representation.
	 * 
	 * @param key
	 * 			key used to get the String value
	 * 
	 * @return
	 * 			String associated with the given key
	 */
	public String getString(String key);
	
	/**
	 * Provides an enumeration of the keys in the JSON representation.
	 * 
	 * @return
	 * 		Iterator that enumerates the keys present in the JSON 
	 * representation.
	 */
	public Iterator<String> getKeys();

	/**
	 * Provides a collection of the array of String JSON values.
	 * 
	 * @param key
	 * 			key which has as it's value an array of JSON objects
	 * of the same type 
	 * 
	 * @return
	 * 			Collection of String JSON values
	 */
	public Collection<String> getStringArray(String key);

}
