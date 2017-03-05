package com.asgoc.common.code.repository;

import java.nio.file.Path;

/**
 * The interface that specifies the contract for a repository
 * accessor. The implementation, in general, serves as a wrapper 
 * for the file operations.
 * 
 * @author Kaartic Sivaraam
 *
 */
public interface RepositoryAccessor {
	
	/**
	 * The contents of the file at specified location are 
	 * provided as a StringBuilder.
	 * 
	 * @param location
	 * 			Location from which the contents must be fetched.
	 * 
	 * @return
	 * 			A StringBuilder that represents the contents of 
	 * the specifies location.
	 * 
	 * @throws InvalidRepositoryOperation
	 * 			Exception thrown  in case of an Invalid repository 
	 * operation.
	 */
	public StringBuilder readFromFile(Path location) throws InvalidRepositoryOperation;
	
	/**
	 * The contents provided in the string are written to the provided location. The 
	 * destination should not exist before the invocation of this operation.
	 * 
	 * @param destination
	 * 			Location to which contents need to be written.
	 *  
	 * @param contentsToWrite
	 * 			Contents that need to be written to the specified location.
	 * 
	 * @throws InvalidRepositoryOperation
	 * 			Exception thrown  in case of an Invalid repository 
	 * operation.
	 * 
	 */
	public void writeToNewFile(Path destination, String contentsToWrite) throws InvalidRepositoryOperation;
	
	/**
	 * The contents provided in the string are appended to the provided location. The destination
	 * must exist before this operation.
	 *  
	 * @param destination
	 * 			Location to which content must be appended.
	 * 
	 * @param contentsToAppend
	 * 			Contents that need to be appended to the specified location.
	 * 
	 * @throws InvalidRepositoryOperation
	 * 			Exception thrown  in case of an Invalid repository 
	 * operation.
	 * 
	 */
	public void appendToFile(Path destination, String contentsToAppend) throws InvalidRepositoryOperation;

}
