package org.castafiore.searchengine;

import java.util.List;

import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.ex.EXContainer;

import edu.emory.mathcs.backport.java.util.LinkedList;

public class EXAnimator extends EXContainer{

	//private ClientProxy commands
	
	private List<ClientProxy> commands = new LinkedList();
	
	public EXAnimator() {
		super("EXAnimator", "div");
	}
	
	
	public void addCommand(ClientProxy proxy){
		commands.add(proxy);
		setRendered(false);
	}
	
	
	public void onReady(ClientProxy proxy){
		for(ClientProxy command : commands){
			proxy.mergeCommand(command);
		}
		commands.clear();
	}
}
