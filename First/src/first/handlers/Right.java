package first.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

public class Right extends AbstractHandler{
	public Right(){
	}
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		MessageDialog.openInformation(
				window.getShell(),
				"Delete",
				"Code to be deleted ");
	MenuManager manager = new MenuManager("#PopupMenu");
	Menu menu = manager.createContextMenu((Control)window);
	((Control) window).setMenu(menu);
	Action deprecated = new Action() {
		public void run() {
		MessageDialog.openInformation(null, "Hello", "World");
		}
		};
		deprecated.setText("Hello");
		manager.add(deprecated);
	return null;
    }
}
	
