package com.asgoc.common.code;

import com.asgoc.common.code.repository.InvalidRepositoryOperation;

/**
 * Factory method used to return objects of the classes that
 * implement the CodeRepositoryAccessor interface.
 * 
 * @author Kaartic Sivaraam
 *
 */
public final class CodeRepositoryAccessorFactory {
	
	/**
	 * Provides an instance of the CodeRepositoryAccessor factory.
	 * 
	 * @return
	 * 			An instance of CodeRepositoryAccessorFactory
	 */
	public static CodeRepositoryAccessorFactory getInstance() {
		if (instance == null)
			instance = new CodeRepositoryAccessorFactory();
	 	return instance;
	}
	
	/**
	 * Provides an instance of the ConcreteCodeRepositoryAccessor
	 * class which is one of the implementations of the CodeRepositoryAccessor
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
	public CodeRepositoryAccessor getConcreteCodeRepositoryAccessor(String basePath) throws InvalidRepositoryOperation {
		if(craInstance == null) {
			craInstance = new ConcreteCodeRepositoryAccessor(basePath);
		}
		
		return craInstance;
	}
	
	private CodeRepositoryAccessor craInstance; 
	private static CodeRepositoryAccessorFactory instance;
}
