package first.handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class AddHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public AddHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		/*IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		MessageDialog.openInformation(
				window.getShell(),
				"Add",
				"Code to be added ");*/
		URL url;
		try {
			 //String path = new Path(Platform.resolve(Platform.find(plugin.getBundle(), new Path("C:\\Users/DELL/Downloads/eclipse/plugins/test.txt"))).getFile()).toFile().toString();
		        //url = new URL("C:\\Users/DELL/Downloads/eclipse/plugins/test.txt");
			url = new URL("file:///C://Users/DELL/Downloads/eclipse/plugins/test.txt");
		    InputStream inputStream = url.openConnection().getInputStream();
		    BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		    String inputLine;
		 
		    while ((inputLine = in.readLine()) != null) {
		        System.out.println(inputLine);
		    }
		 
		    in.close();
		 
		} catch (IOException e) {
		    e.printStackTrace();
		}
		return null;
	}
}
