package com.asgoc.common.code;

import java.nio.file.Path;
import java.util.List;

import com.asgoc.common.code.repository.InvalidRepositoryOperation;

/**
 * The interface that specifies the contract for a 
 * code repository accessor.
 * 
 * @author Kaartic Sivaraam
 *
 */
public interface CodeRepositoryAccessorAdaptor {
	
	/**
	 * The method must store the given code object to the 
	 * code repository
	 * 
	 * @param code 
	 * 			Object which contains the data that needs to be stored in
	 * the code repository.
	 * 
	 * @throws InvalidRepositoryOperation
	 * 			Exception thrown  in case of an Invalid repository 
	 * operation.
	 */
	public void storeCode(Code code) throws InvalidRepositoryOperation;
	
	/**
	 * Provide the Code object that represents the Code block and it's metadata
	 * found in the given location.
	 *  
	 * @param location
	 * 			The location in which the Code exists. 
	 * @return
	 * 			Code object that represents the code and it's metadata.
	 *  
	 * @throws InvalidRepositoryOperation
	 * 			Exception thrown  in case of an Invalid repository 
	 * operation.
	 */
	public Code getCode(Path location) throws InvalidRepositoryOperation;
	
	/**
	 * Provides the list of Crucial Metadata of the code found in the given path.
	 * 
	 * @param location
	 * 			The location (directory) for which the Crucial metadata list is 
	 * to be returned.
	 * 
	 * @return
	 * 			The list containing the crucial metdata list of the codes found 
	 * in the given path.
	 * 
	 * @throws InvalidRepositoryOperation
	 * 			Exception thrown  in case of an Invalid repository 
	 * operation.
	 */
	public List<Code.Metadata.CrucialMetadata> getCrucialMetadata(Path location) throws InvalidRepositoryOperation;
	
}
