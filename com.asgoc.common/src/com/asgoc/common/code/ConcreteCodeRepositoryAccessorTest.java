/**
 * 
 */
package com.asgoc.common.code;

import static org.junit.Assert.*;

import java.nio.file.Paths;
import java.util.Collection;

import org.junit.Test;

/**
 * @author unique
 *
 */
class ConcreteCodeRepositoryAccessorTest {

	public ConcreteCodeRepositoryAccessorTest() throws InvalidCodeRepositoryOperation {
		CodeRepositoryAccessorFactory craf = CodeRepositoryAccessorFactory.getInstance();
		cra = craf.getConcreteCodeRepositoryAccessor(Paths.get("/home/unique")); 
	}
	/**
	 * Test method for {@link com.asgoc.common.code.ConcreteCodeRepositoryAccessor#storeCode(com.asgoc.common.code.Code)}.
	 */
	@Test
	public void testStoreCode() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link com.asgoc.common.code.ConcreteCodeRepositoryAccessor#getCode(java.nio.file.Path)}.
	 */
	@Test
	public void testGetCode() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link com.asgoc.common.code.ConcreteCodeRepositoryAccessor#getCrucialMetadata(java.nio.file.Path)}.
	 * @throws InvalidCodeRepositoryOperation 
	 */
	@Test
	public void testGetCrucialMetadata() throws InvalidCodeRepositoryOperation {
		Collection<Code.Metadata.CrucialMetadata> values = cra.getCrucialMetadata(Paths.get("C"));
		for(Code.Metadata.CrucialMetadata cm : values) {
			System.out.println(cm.getTitle());
		}
	}
	
	CodeRepositoryAccessor cra;

}
