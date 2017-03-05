package com.asgoc.common.code;

import com.asgoc.common.code.repository.InvalidRepositoryOperation;

/**
 * Factory method used to return objects of the classes that
 * implement the CodeRepositoryAccessorAdaptor interface.
 * 
 * @author Kaartic Sivaraam
 *
 */
public final class CodeRepositoryAccessorFactory {

	/**
	 * Provides an instance of the CodeRepositoryAccessorAdaptor factory.
	 * 
	 * @return
	 * 		A <em> Singleton </em> instance of CodeRepositoryAccessorFactory
	 */
	public static CodeRepositoryAccessorFactory getInstance() {
		if (instance == null)
			instance = new CodeRepositoryAccessorFactory();
	 	return instance;
	}
	
	/**
	 * Provides an instance of the ConcreteCodeRepositoryAccessor
	 * class which is one of the implementations of the CodeRepositoryAccessorAdaptor
	 * interface.
	 * 
	 * @param basePath
	 * 				The base path of the repository required to instantiate the class.
	 * 
	 * @return
	 * 			An instance of the ConcreteCodeRepositoryAccessor class.
	 * 
	 * @throws InvalidRepositoryOperation
	 * 				Thrown in case of an invalid repository operation.
	 */
	public CodeRepositoryAccessorAdaptor getConcreteCodeRepositoryAccessor(String basePath) throws InvalidRepositoryOperation {
		
		return new ConcreteCodeRepositoryAccessor(basePath);
		
	}

	//prevent the instantiation of the Factory class
	private CodeRepositoryAccessorFactory() {
			
	}
	
	private static CodeRepositoryAccessorFactory instance;
}
