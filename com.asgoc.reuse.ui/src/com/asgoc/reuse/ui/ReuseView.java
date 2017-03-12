package com.asgoc.reuse.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

public class ReuseView {
	static Shell shell;
	List list;
	Label label;
	String[] s;
	/*s[0] = "dnf";
	s[1] = "dfdf";
	s[2] = "ere";
	s[3] = "dfs";*/
	Display display;
	public ReuseView()
	{
		display = Display.getDefault();
		shell = new Shell(display);
		shell.setLocation(new Point(300,100));
		shell.setSize(new Point(500, 500));
	    shell.setLayout(new FillLayout());
	    list = new List(shell, SWT.V_SCROLL|SWT.BORDER);
	    list.setLocation(0,0);
		label = new Label(shell,SWT.BORDER);
        label.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
		label.setLocation(new Point(0,0));
		label.setSize(new Point(200,500));
		s = new String[5];
		s[0] = "dnf";
		s[1] = "dfdf";
		s[2] = "ere";
		s[3] = "dfs";
	}
	public void displayList()
	{
	    for (int i = 0; i < 4; i++) {
		    list.add(s[i]);
		}
	    list.select(0);
	    list.showSelection();
	}
	public void displayCrucialMetadata()
	{
		list.addSelectionListener(new SelectionListener(){
			@Override
			public void widgetDefaultSelected(SelectionEvent s) {
				label.setText("sdfd");
			}
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				int k = list.getSelectionIndex();
				label.setText(s[k]);
			}
	    });
		shell.open();
		while(!shell.isDisposed())
		{
			if(!display.readAndDispatch()) display.sleep();
		}
	}
}
