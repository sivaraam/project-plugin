package first.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.TaskBar;
import org.eclipse.ui.editors.text.TextEditor;


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
	@Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
            // get the page
		/*IWorkbenchPage page;
		IEditorPart part = page.getActiveEditor();
		   if (!(part instanceof AbstractTextEditor)
		      return;
		   ITextEditor editor = (ITextEditor)part;
		   IDocumentProvider dp = editor.getDocumentProvider();
		   IDocument doc = dp.getDocument(editor.getEditorInput());
		   int offset = doc.getLineOffset(doc.getNumberOfLines()-4);
		   doc.replace(offset, 0, pasteText+"\n");
		/*IFile myfile;
				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				ITextEditor editor = (ITextEditor) IDE.openEditor(page, myfile);
				editor.selectAndReveal(offset, length);*/
		//TextEditor editor = (TextEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
        IEditorPart editorPart = HandlerUtil.getActiveEditor(event);

        MessageDialog.openInformation(
                window.getShell(),
                "GenerateBuilderProject",
                editorPart.getEditorInput().getName());
		
        return null;
    } 
}
