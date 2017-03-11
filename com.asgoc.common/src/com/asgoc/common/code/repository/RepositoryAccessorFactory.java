package com.asgoc.common.code.repository;

import com.asgoc.common.code.repository.InvalidRepositoryOperation;

/**
 * Factory method used to return objects of the classes that
 * implement the RepositoryAccessor interface.
 * 
 * @author Kaartic Sivaraam
 *
 */
public final class RepositoryAccessorFactory {
	
	/**
	 * Provides an instance of the RepositoryAccessor factory.
	 * 
	 * @return
	 * 		A <em> Singleton </em> instance of RepositoryAccessorFactory
	 */
	public static RepositoryAccessorFactory getInstance() {
		if (instance == null)
			instance = new RepositoryAccessorFactory();
	 	return instance;
	}
	
	/**
	 * Provides an instance of the BufferedRepositoryAccessor
	 * class which is one of the implementations of the RepositoryAccessor
	 * interface.
	 * 
	 * @param basePath
	 * 				The base path of the repository required to instantiate the class.
	 * 
	 * @return
	 * 			An instance of the BufferedRepositoryAccessor class.
	 * 
	 * @throws InvalidRepositoryOperation
	 * 				Thrown in case of an invalid repository operation.
	 */
	public RepositoryAccessor getBufferedCodeRepositoryAccessor(String basePath) throws InvalidRepositoryOperation {
		
		return new BufferedRepositoryAccessor(basePath);
	}

	//Prevent the instantiation of the factory
	private RepositoryAccessorFactory() {
		
	}
	
	private static RepositoryAccessorFactory instance;
}
