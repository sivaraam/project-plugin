package com.asgoc.reuse.ui.handlers;


import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
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
	 * the command has been executed, so extract the needed information
	 * from the application context.
	 */
	@Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
		StringBuilder fileContents = new StringBuilder();
		try {
			RepositoryAccessor repoHandler = new RepositoryAccessor("C:\\Users\\DELL\\Downloads\\eclipse\\plugins");
			fileContents = repoHandler.readFromFile("test.txt");
		}
		catch (InvalidRepositoryOperation iro) {
			iro.printStackTrace();
		}
		IEditorPart editorPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		IEditorInput input = editorPart.getEditorInput();
		IDocument document=(((ITextEditor)editorPart).getDocumentProvider()).getDocument(input);
		ITextSelection textSelection = (ITextSelection) editorPart.getSite().getSelectionProvider().getSelection();
		int offset = textSelection.getOffset();
		try {
			document.set(document.get(0,offset)+fileContents+document.get(offset, document.getLength()-offset));
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
        return null;
	}
}
