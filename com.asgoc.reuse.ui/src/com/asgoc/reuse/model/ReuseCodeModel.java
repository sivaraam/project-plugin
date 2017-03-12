package com.asgoc.reuse.model;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.ITextEditor;

import repositoryAccessor.InvalidRepositoryOperation;
import repositoryAccessor.RepositoryAccessor;

public class ReuseCodeModel {
	StringBuilder fileContents;
	public void getcontents()
	{
		fileContents = new StringBuilder();
		fileContents.append("\n/* Your code is inserted here*/\n");
		try {
			RepositoryAccessor repoHandler = new RepositoryAccessor("C:\\Users\\DELL\\Downloads\\eclipse\\plugins");
			fileContents.append(repoHandler.readFromFile("test.txt"));
		}
		catch (InvalidRepositoryOperation iro) {
			iro.printStackTrace();
		}
	}
	public void resue()
	{
		IEditorPart editorPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		IEditorInput input = editorPart.getEditorInput();
		IDocument document=(((ITextEditor)editorPart).getDocumentProvider()).getDocument(input);
		ITextSelection textSelection = (ITextSelection) editorPart.getSite().getSelectionProvider().getSelection();
		int offset = textSelection.getOffset();
		try {
			document.set(document.get(0,offset)+fileContents+document.get(offset, document.getLength()-offset));
			ITextEditor edit = (ITextEditor)editorPart;
			edit.selectAndReveal(offset+33,0);
		} catch (BadLocationException e) {
			e.printStackTrace();
		} 

	}
}
