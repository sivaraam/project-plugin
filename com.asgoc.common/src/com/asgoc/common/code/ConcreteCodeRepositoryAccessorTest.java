/**
 * 
 */
package com.asgoc.common.code;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

/**
 * @author unique
 *
 */
public class ConcreteCodeRepositoryAccessorTest {

	public ConcreteCodeRepositoryAccessorTest() throws InvalidCodeRepositoryOperation {
		CodeRepositoryAccessorFactory craf = CodeRepositoryAccessorFactory.getInstance();
		cra = craf.getConcreteCodeRepositoryAccessor(Paths.get("/home/unique")); 
	}
	/**
	 * Test method for {@link com.asgoc.common.code.ConcreteCodeRepositoryAccessor#storeCode(com.asgoc.common.code.Code)}.
	 * @throws InvalidCodeRepositoryOperation 
	 */
	@Test
	public void testStoreCode() throws InvalidCodeRepositoryOperation {
		System.out.println("Store Code"); 
		Code testCode = new Code("Copy Direct Initialization", 
									new StringBuilder("Just a little C++ test code"),
									new StringBuilder("Test for RVO"),
									new ArrayList<>(),
									Paths.get("C++/copy_direct_initialization.cpp"),
									new StringBuilder("#include<iostream>\n" + 
											"class T{\n" + 
											"	public:\n" + 
											"	explicit T(int n){\n" + 
											"		this->n = n;\n" + 
											"		std::cout<<\"Constructor called\";\n" + 
											"	}\n" + 
											"	int n;\n" + 
											"};\n" + 
											"int main(){\n" + 
											"	T t1(10);\n" + 
											"	T t2 = 10;\n" + 
											"}\n" + 
											"")
									);
		cra.storeCode(testCode);
	}

	/**
	 * Test method for {@link com.asgoc.common.code.ConcreteCodeRepositoryAccessor#getCode(java.nio.file.Path)}.
	 * @throws InvalidCodeRepositoryOperation 
	 */
	@Test
	public void testGetCode() throws InvalidCodeRepositoryOperation {
		System.out.println("getCode : ");
		Code testCode = cra.getCode(Paths.get("C++/copy_direct_initialization.cpp"));
		System.out.println(testCode.getMetadata().getCrucialMetadata().getTitle());
	}

	/**
	 * Test method for {@link com.asgoc.common.code.ConcreteCodeRepositoryAccessor#getCrucialMetadata(java.nio.file.Path)}.
	 * @throws InvalidCodeRepositoryOperation 
	 */
	@Test
	public void testGetCrucialMetadata() throws InvalidCodeRepositoryOperation {
		System.out.println("getCrucialMetadata : ");
		Collection<Code.Metadata.CrucialMetadata> values = cra.getCrucialMetadata(Paths.get("C++"));
		for(Code.Metadata.CrucialMetadata cm : values) {
			System.out.println(cm.getTitle());
		}
	}
	
	CodeRepositoryAccessor cra;

}
