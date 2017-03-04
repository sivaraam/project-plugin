package com.asgoc.add.ui.popup.actions;

import java.awt.Button;
import java.awt.Composite;
import java.awt.Point;
import java.io.File;
import java.util.ResourceBundle.Control;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.texteditor.AbstractTextEditor;

import repositoryAccessor.InvalidRepositoryOperation;
import repositoryAccessor.RepositoryAccessor;

import com.asgoc.add.ui.Activator;


public class NewAction implements IObjectActionDelegate {
	RepositoryAccessor r;
	private Shell shell;
	String s[]=new String[3];
	/**
	 * Constructor for Action1.
	 */
	public NewAction() {
		super();
		try {
			r=new RepositoryAccessor("D:\\eclipse\\Code\\Functions");
		} catch (InvalidRepositoryOperation e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
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
						if (!iSelection.isEmpty())
						{
							selectedText = ((ITextSelection) iSelection).getText();
							/* MessageDialog.openInformation(
							         shell,
							         "Do Something Menu",
							          "String: " +selectedText );*/
/*							MessageDialog.openQuestion(shell, "Confirmation",selectedText);//(shell,"Confirm","null",selectedText,MessageDialog.QUESTION,)
							MessageDialog(shell,"Confirmation",Dialog.DLG_IMG_INFO,selectedText,MessageDialog.INFORMATION,Dialog.);*/
							r.writeToNewFile("abc.txt",selectedText);
							}
					}
				}
 
			}
		} catch (Exception e) {	
			e.printStackTrace();
		}
	}
	protected void createButtonsForButtonBar(Composite shell)
	{
		
	}
	

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

}
