package com.asgoc.common.code.json;

/**
 * Factory method used to return objects of the classes that
 * implement the JSONManipulatorAdaptor interface.
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
	 * one of the implementations of the JSONManipulatorAdaptor interface.
	 * 
	 * @return
	 * 		An instance of the ConcreteJSONManipulator class
	 */
	public JSONManipulatorAdaptor getConcreteJSONManipulator() {
		//Cache values as it would not have any side effect.
		if(manipulatorCache == null)
			manipulatorCache = new ConcreteJSONManipulator();
		return manipulatorCache;
		
	}
	
	/**
	 * Provides an instance of the ConcreteJSONManipulator class, 
	 * one of the implementations of the JSONManipulatorAdaptor interface,
	 * that gets an Object a parameter. 
	 * 
	 * @param initialiser 
	 * 		Initialiser passed to the ConcreteJSONManipulator class.
	 * 
	 * @return
	 * 		An instance of the ConcreteJSONManipulator class
	 */
	public JSONManipulatorAdaptor getConcreteJSONManipulator(Object initialiser) {
		
		return new ConcreteJSONManipulator(initialiser);
		
	}
	
	private JSONManipulatorAdaptor manipulatorCache;
	private static JSONManipulatorFactory instance;
}
