package com.asgoc.add.ui.popup.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.dialogs.ListSelectionDialog;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.texteditor.AbstractTextEditor;

import com.asgoc.add.ui.Activator;

public class NewAction implements IObjectActionDelegate {

	private Shell shell;
	
	/**
	 * Constructor for Action1.
	 */
	public NewAction() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	//See if you could improve the function. It seems too *monolithic* 
	public void run(IAction action) {
		/*MessageDialog.openInformation(
			shell,
			"Add",
			"New Action was executed.");*/
		try {
			//get editor
			IEditorPart editorPart = Activator.getDefault().getWorkbench()
					.getActiveWorkbenchWindow().getActivePage()
					.getActiveEditor();
 
			if (editorPart instanceof AbstractTextEditor) {
				String selectedText = null;
				IEditorSite iEditorSite = editorPart.getEditorSite();
				if (iEditorSite != null) {
					ISelectionProvider selectionProvider = iEditorSite
							.getSelectionProvider();
					if (selectionProvider != null) {
						ISelection iSelection = selectionProvider
								.getSelection();
						if (!iSelection.isEmpty()) {
							selectedText = ((ITextSelection) iSelection)
									.getText();
							 MessageDialog.openInformation(
							         shell,
							         "Do Something Menu",
							          "String: " +selectedText );
							 MessageDialog("confirm","null",selectedText,)
								ListSelectionDialog dlg = new ListSelectionDialog( shell,"bb", new BaseWorkbenchContentProvider(), new WorkbenchLabelProvider(), "Confirm"); 
								dlg.setTitle("Confirm");
								dlg.open();
						}
					}
				}
 
			}
			
			//1. Don't catch *Exception* try to be more specific. 
			//Else you would face a lot of issues.
			//2. Also, don't leave the catch block empty as a result 
			//of which you couldn't see if exceptions occured. 
			//see http://stackoverflow.com/a/1075991/5614968
		} catch (Exception e) {		}
	}
	

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

}
