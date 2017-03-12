package com.asgoc.reuse.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import com.asgoc.reuse.model.ReuseCodeModel;
import com.asgoc.reuse.ui.ReuseView;
import org.eclipse.core.commands.ExecutionException;
public class ReuseHandler extends AbstractHandler{
	ReuseCodeModel reuseCodeModel;
	ReuseView reuseView;
	public ReuseHandler() {
		reuseCodeModel = new ReuseCodeModel();
		reuseView = new ReuseView();
	}
	@Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
		reuseCodeModel.getcontents();
		reuseCodeModel.resue();
		reuseView.displayList();
		reuseView.displayCrucialMetadata();
		return null;
	}
}