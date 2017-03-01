package com.asgoc.reuse.ui.handlers;


import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;

import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.ITextEditor;

import repositoryAccessor.InvalidRepositoryOperation;
import repositoryAccessor.RepositoryAccessor;



/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class ReuseHandler extends AbstractHandler{
	/**
	 * The constructor.
	 */
	public ReuseHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	@Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
		StringBuilder fileContents = new StringBuilder();
		try {
			RepositoryAccessor repoHandler = new RepositoryAccessor("/home/unique");
			fileContents = repoHandler.readFromFile("test1234");
		}
		catch (InvalidRepositoryOperation iro) {
			iro.printStackTrace();
		}
		 
		IEditorPart editorPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		IEditorInput input = editorPart.getEditorInput();
		IDocument document=(((ITextEditor)editorPart).getDocumentProvider()).getDocument(input);
		document.set(document.get()+fileContents);
        return null;
	}
}
