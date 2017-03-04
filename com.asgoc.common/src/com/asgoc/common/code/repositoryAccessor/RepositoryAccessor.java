package com.asgoc.common.code.repositoryAccessor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE_NEW;
import static java.nio.file.StandardOpenOption.WRITE;

/**
 * This class serves as a wrapper for the file operations provided by Java.
 * 
 * This is done to abstract away the work done to access files. This class is 
 * used to access a repository. It allows the user to read and write contents 
 * from files in the repository. Al operations are done relative to the 
 * {@link #basePath} of the instance.
 * <p>
 * The user is freed from the work of <strong>resource handling</strong> as 
 * it is taken care by the implementation itself.
 * </p>
 * @author Kaartic Sivaraam
 */
public class RepositoryAccessor {

    /**
     * 
     * @param base The base <em> path </em> of the repository (directory) relative to which 
     * all methods (unless otherwise noted) perform their operations.
     * 
     * @throws InvalidRepositoryOperation The constructor throws when the given
     * base path does not exist.
     */
    public RepositoryAccessor(String base) throws InvalidRepositoryOperation {
        basePath = Paths.get(base);
	if(Files.notExists(basePath)) {
		throw new InvalidRepositoryOperation ("The Base Path does not exist");
	}
    }
	
    /**
     * This method reads contents from the <code> source </code> and returns the
     * contents as a <code> StringBuilder </code>.
     * 
     * @param source It is the <em> path </em> of the source file relative to
     * the {@link #basePath}
     * @return <code> StringBuilder </code> The contents of the file are returned as 
     * a <code> StringBuilder </code>.
     * @throws InvalidRepositoryOperation This method throws this class when,
     * <ul>
     *         <li> the file does not exist
     *         <li> an <code> IOException </code> occurs during a file operation
     * </ul>
     */
    public StringBuilder readFromFile(String source) throws InvalidRepositoryOperation {
        Path sourcePath = Paths.get(basePath.toString(), source);
	if(Files.notExists(sourcePath))
            throw new InvalidRepositoryOperation ("File does not exist");
	
        StringBuilder fileContents = new StringBuilder();
	
        try(InputStream in = Files.newInputStream(sourcePath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));) {
                String line;
                while((line = reader.readLine()) != null) {
                    fileContents.append(line).append("\n");
                }        
        }  
        catch (IOException ex) {
            throw new InvalidRepositoryOperation ("IOException when trying to write to file\n"+ex.getMessage());
        }
       
        return fileContents;
    }
    
    /**
     * This method writes the contents of the provided <code> String </code>, to the 
     * a file with the provided <code> fileName </code> which is, assumed to not
     * exist before invocation.
     * 
     * @param fileName The <em> name of file </em> which is to be created. The 
     * file would be created in the base path.
     * @param contentsToWrite The <code> String </code>containing the contents to be
     * written to the file.
     * 
     * @throws InvalidRepositoryOperation This method throws when,
     * <ul>
     *     <li> the Parent of the file doesn't exist
     *     <li> file does exists previously
     *     <li> an <code> IOException </code> occurs during File write operation
     * </ul>
     */
    public void writeToNewFile(String fileName, String contentsToWrite) throws InvalidRepositoryOperation {
        Path destinationPath = Paths.get(basePath.toString(), fileName);
        
        if(Files.notExists(destinationPath.getParent())) {
            throw new InvalidRepositoryOperation ("Parent of file does not exist");
        }
                
        try ( OutputStream out = Files.newOutputStream(destinationPath, CREATE_NEW); 
              BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"))) {
            writer.write(contentsToWrite);
        }        catch (IOException ex) {
            throw new InvalidRepositoryOperation ("IOException when trying to write to file\n"+ex.getMessage());
        }
    }
    
    /**
     * This method writes the contents of the provided <code> String </code>, to the 
     * <code> destination </code> which is assumed to, exist before invocation.
     * 
     * @param fileName The <em> name of the file </em> to which contents would 
     * be appended. The file must exist before trying to append to it.
     * @param contentsToWrite The <code> String </code> containing the contents to be
     * written to the file.
     * 
     * @throws InvalidRepositoryOperation This method throws when,
     * <ul>
     *         <li> the Parent of the file doesn't exist
     *         <li> the file does not exist
     *         <li> an <code> IOException </code> occurs during File write operation
     * </ul>
     */
     public void appendToFile(String fileName, String contentsToWrite) throws InvalidRepositoryOperation {
        Path destinationPath = Paths.get(basePath.toString(), fileName);
        
        if(Files.notExists(destinationPath.getParent())) {
            throw new InvalidRepositoryOperation ("Parent of file does not exist");
        }
        
        if(Files.notExists(destinationPath)) {
            throw new InvalidRepositoryOperation ("File does not exist");
        }
        
        byte[] contentsAsBytes = contentsToWrite.getBytes();
        try ( OutputStream out = Files.newOutputStream(destinationPath, WRITE, APPEND);
        		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"))) {
            out.write(contentsAsBytes, 0, contentsAsBytes.length);
        }
        catch (IOException ex) {
            throw new InvalidRepositoryOperation ("IOException when trying to write to file\n" + ex.getMessage());
        }
    }
     
    /**
     * The member that stores the base path of the repository. 
     * 
     * All methods perform their operation (unless otherwise noted) relative to
     * this path.
     */
    private final Path basePath;
	
}
