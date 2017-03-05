package com.asgoc.common.code.json;

/**
 * Factory method used to return objects of the classes that
 * implement the JSONManipulator interface.
 * 
 * @author Kaartic Sivaraam
 *
 */
public class JSONManipulatorFactory {
	
	/**
	 * Provides an instance of the JSONManipulatorFactory
	 * 
	 * @return
	 * 		A <em> Singleton </em> instance of the
	 * JSONManipulatorFactory
	 */
	public static JSONManipulatorFactory getInstance() {
		if(instance == null)
			instance = new JSONManipulatorFactory();
		return instance;
	}
	
	/**
	 * Provides an instance of the ConcreteJSONManipulator class, 
	 * one of the implementations of the JSONManipulator interface.
	 * 
	 * @return
	 * 		An instance of the ConcreteJSONManipulator class
	 */
	public JSONManipulator getConcreteJSONManipulator() {
		
		return new ConcreteJSONManipulator();
		
	}
	
	/**
	 * Provides an instance of the ConcreteJSONManipulator class, 
	 * one of the implementations of the JSONManipulator interface,
	 * that gets an Object a parameter. 
	 * 
	 * @param initialiser 
	 * 		Initialiser passed to the ConcreteJSONManipulator class.
	 * 
	 * @return
	 * 		An instance of the ConcreteJSONManipulator class
	 */
	public JSONManipulator getConcreteJSONManipulator(Object initialiser) {
		
		return new ConcreteJSONManipulator(initialiser);
		
	}
	
	private static JSONManipulatorFactory instance;
}
