package zzzz.popup.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.dialogs.ListSelectionDialog;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

public class NewAction implements IObjectActionDelegate  {

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
	public void run(IAction action) {
		//MessageDialog dialog = new MessageDialog(shell,"menu","swathte");
		//MessageDialog.openInformation(shell,"menu","swthe");
		//MessageDialog.+-DialogArea();
		ListSelectionDialog dlg = new ListSelectionDialog( shell, "bb", new BaseWorkbenchContentProvider(), new WorkbenchLabelProvider(), "select the functions to delete"); 
		//dlg.setInitialSelections(dirtyEditors); 
		dlg.setTitle("delete");
		dlg.open();


	}

/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

}
