package com.asgoc.common.code;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

import com.asgoc.common.code.repository.InvalidRepositoryOperation;
import com.asgoc.common.code.repository.RepositoryAccessorFactory;
import com.asgoc.common.code.repository.RepositoryAccessor;

/**
 * The class used to access and manipulate the Code repository.
 * It is an implementation of the CodeRepositoryAccessor interface. 
 * 
 * @author Kaartic Sivaraam
 *
 */
final class ConcreteCodeRepositoryAccessor implements CodeRepositoryAccessor{
	
	/** 
	 * @param basePath
	 * 					The base path of the code repository. 
	 *   
	 * @throws InvalidRepositoryOperation
	 * 					Thrown when the base path does not exist
	 */
	public ConcreteCodeRepositoryAccessor(Path basePath) throws InvalidCodeRepositoryOperation { 
		RepositoryAccessorFactory raf = RepositoryAccessorFactory.getInstance();
		try {
			repoAccessor = raf.getBufferedCodeRepositoryAccessor(basePath);
		} catch (InvalidRepositoryOperation e) {
			throw new InvalidCodeRepositoryOperation("Base path does not exist");
		}
	}
	
	/**
	 * Method used to store the metadata of the code block to the metdata file.
	 * The JSON of the metadata has the following structure as described in
	 * CodeJSONTranslator
	 * 
	 * @param metadataLocation
	 * 			The location of the metadata file. As in other cases this 
	 * location is also relative to the base path of the repository.
	 *  
	 * @param metadata
	 * 			The metadata which is converted to JSON and stored.
	 * 
	 * @throws InvalidRepositoryOperation
	 * 			Thrown when the invalid repository operations are performed. 
	 * For more information, refer the documentation of implementation of the
	 * RepositoryAccessor interface. 
	 * 
	 */
	private void storeMetadata(Path metadataLocation, Code.Metadata metadata) throws InvalidCodeRepositoryOperation {
		
		String metadataString = CodeJSONTranslator.getMetadataJSON(metadata);
		try {
			repoAccessor.writeToNewFile(metadataLocation, metadataString);
		} catch (InvalidRepositoryOperation e) {
			throw new InvalidCodeRepositoryOperation("Unable to store metadata: "+e.getMessage());
		}
		
	}
	
	/**
	 * Method used to update the index file of the repository. In case 
	 * the index file does not exist it is created. This index is used 
	 * by the {@link #getCrucialMetadata(Path)} method to return the list.
	 * The index has the following general structure,
	
	 * @param indexLocation
	 * 			Location of the index file relative to the base path 
	 * of the repository.
	 * 
	 * @param crucialMd
	 * 			The CurcialMetadata instance used to update the index file.
	 * 
	 * @throws InvalidRepositoryOperation
	 * 			Thrown when invalid repository operations are performed. 
	 * For more information, refer the documentation of implementation of the
	 * RepositoryAccessor interface. 
	 */
	private void updateIndex(Path indexLocation, Code.Metadata.CrucialMetadata crucialMd) throws InvalidCodeRepositoryOperation {
		
		String indexString = null;
		
		try {
			//Get JSON found in index
			indexString = repoAccessor.readFromFile(indexLocation).toString();
			
			//Append the crucial metadata
			indexString = CodeJSONTranslator.getIndexJSON(indexString, crucialMd);
			
			//write new JSON String back to file
			repoAccessor.appendToFile(indexLocation, indexString);
		}
		catch(InvalidRepositoryOperation iro) {
			
			//If Index file does not exist create it (should occur only once)
			if(iro.getMessage().equals("File does not exist")) {
				//Add the JSON representation for the string
				indexString = CodeJSONTranslator.getIndexJSON(crucialMd);
				
				//Write JSON to new file
				try {
					repoAccessor.writeToNewFile(indexLocation, indexString);
				} catch (InvalidRepositoryOperation e) {
					throw new InvalidCodeRepositoryOperation("Unable to update index: "+e.getMessage());
				}
			}
			else 
				throw new InvalidCodeRepositoryOperation("Unable to update index: "+iro.getMessage());
		}
	}
	
	/**
	 * Method used to store the code and it's metadata to the specified location which should 
	 * be relative to the base path of the code repository.
	 * 
	 * @param code
	 * 				The instance of the Code class that represents the code that needs
	 * to be added to the repository.
	 * 
	 * @throws InvalidRepositoryOperation
	 * 				Thrown when the invalid repository operations are performed. 
	 * For more information, refer the documentation of implementation of the
	 * RepositoryAccessor interface. 
	 */
	@Override
	public void storeCode(Code code) throws InvalidCodeRepositoryOperation {
		
		Path codeLocation = code.metadata.crucialMetadata.relativeLocation;
		Path metadataLocation = Paths.get(codeLocation.toString().replaceFirst(FILE_EXTENSION_REGEX, METADATA_FILE_SUFFIX));
		Path parent = codeLocation.getParent();
		Path indexLocation = Paths.get( (parent != null ? parent.toString() : ""), INDEX_FILE_NAME);
		
		storeMetadata(metadataLocation, code.metadata);
		try {
			repoAccessor.writeToNewFile(codeLocation, code.codeBlock.toString());
		} catch (InvalidRepositoryOperation e) {
				throw new InvalidCodeRepositoryOperation("Unable to store code: "+e.getMessage());
		}
		updateIndex(indexLocation, code.metadata.crucialMetadata);
		
	}

	
	/**
	 * Provides the Code representation for the code found at the specified location.
	 * 
	 * @param location 
	 * 			location of the code block
	 * 
	 * @return
	 * 			Code representation of the code found in the given location
	 * 
	 * @throws InvalidRepositoryOperation
	 * 			Thrown when the invalid repository operations are performed. 
	 * For more information, refer the documentation of implementation of the
	 * RepositoryAccessor interface. 
	 * 
	 */
	@Override
 	public Code getCode(Path location) throws InvalidCodeRepositoryOperation {
 		Path codeLocation = location;
		Path metadataLocation = Paths.get(location.toString().replaceFirst(FILE_EXTENSION_REGEX, METADATA_FILE_SUFFIX));
		Code.Metadata metadata;
		StringBuilder codeBlock;
		
		try {
			codeBlock = repoAccessor.readFromFile(codeLocation);
			StringBuilder metadataJSON = repoAccessor.readFromFile(metadataLocation);
			metadata = CodeJSONTranslator.getMetadata(metadataJSON.toString());
		} catch (InvalidRepositoryOperation e) {
			throw new InvalidCodeRepositoryOperation("Unable to get code: "+e.getMessage());
		}
		
		return new Code(metadata, codeBlock);
		
	}

	/**
	 * Provides the list of crucial metadata about the codes found in the provided location.
	 * 
	 * 
	 * @param location 
	 * 				Location for which the crucial metadata list is to be generated. If the 
	 * codes are in the base path of the repository, then an empty string is expected for the location.
	 * 
	 * @return
	 * 			List of Code.Metdata.CrucialMetdata objects that represent the crucial 
	 * metadata of the codes found in the given location.
	 * 
	 * @throws InvalidRepositoryOperation
	 * 			Thrown when the invalid repository operations are performed. 
	 * For more information refer the documentation of the implementation of
	 * the RepositoryAccessor interface.
	 * 
	 */
	@Override
	public Collection<Code.Metadata.CrucialMetadata> getCrucialMetadata(Path location) throws InvalidCodeRepositoryOperation {
		Path indexLocation = Paths.get(location.toString(), INDEX_FILE_NAME);
		StringBuilder indexJSON;
		try {
			indexJSON = repoAccessor.readFromFile(indexLocation);
		} catch (InvalidRepositoryOperation e) {
			throw new InvalidCodeRepositoryOperation("Unable to get crucial metadata: "+e.getMessage());
		}
		
		return CodeJSONTranslator.getIndex(indexJSON.toString()); 
	}

	private RepositoryAccessor repoAccessor;

	private static final String FILE_EXTENSION_REGEX = "(\\.[a-zA-Z]+)$";
	private static final String INDEX_FILE_NAME = "index.json";
	private static final String METADATA_FILE_SUFFIX = "-metadata.json";
}
