package first.handlers;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;

import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.ITextEditor;



/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class DeleteHandler extends AbstractHandler{
	/**
	 * The constructor.
	 */
	public DeleteHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	StringBuilder stringbuilder = new StringBuilder();
	@Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
		URL url;
		try {
			url = new URL("file:///C://Users/DELL/Downloads/eclipse/plugins/test.txt");
		    InputStream inputStream = url.openConnection().getInputStream();
		    BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		    String inputLine;
		 
		    while ((inputLine = in.readLine()) != null) {
		        stringbuilder.append(inputLine);
		    }
		 
		    in.close();
		
		IEditorPart editorPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		IEditorInput input = editorPart.getEditorInput();
		IDocument document=(((ITextEditor)editorPart).getDocumentProvider()).getDocument(input);
		System.out.println("Hello"+document.get()); 
		document.set(document.get()+stringbuilder);
		}
		catch (IOException e) {
			    e.printStackTrace();
		}
        return null;
    } 
}
