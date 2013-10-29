package org.castafiore.sample.calculator;

import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXApplication;
import org.castafiore.ui.ex.EXGrid;
import org.castafiore.ui.ex.button.EXButton;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.layout.EXBorderLayoutContainer;
import org.castafiore.ui.ex.panel.EXPanel;

public class Calculator extends EXApplication implements Event{
	
	private final EXInput screen = new EXInput("screen");
	
	private String memory = "";

	public Calculator() {
		super("calculator");
		
	}
	
	@Override
	public void initApp() {
		EXBorderLayoutContainer blc = new EXBorderLayoutContainer();
		screen.setStyle("width", "253px").setStyle("margin-left", "3px");
		screen.setStyle("height", "50px");
		screen.setStyle("text-align", "right");
		screen.setValue("0");
		blc.addChild(screen, EXBorderLayoutContainer.TOP);
		
		blc.addChild(getKeyPad(), EXBorderLayoutContainer.CENTER);
		
		EXPanel panel = new EXPanel( "panel", "Calculator");
		panel.setBody(blc);
		addChild(panel);
	}
	
	private EXGrid getKeyPad(){
		EXGrid grid = new EXGrid("keyPad", 5, 6);
		grid.addClass("calc-grid");
		grid.addInCell(0, 0, getKeyPadButton("MC"));
		grid.addInCell(1, 0, getKeyPadButton("MR"));
		grid.addInCell(2, 0, getKeyPadButton("MS"));
		grid.addInCell(3, 0, getKeyPadButton("M+"));
		grid.addInCell(4, 0, getKeyPadButton("M-"));
		
		grid.addInCell(0, 1, getKeyPadButton("<-"));
		grid.addInCell(1, 1, getKeyPadButton("CE"));
		grid.addInCell(2, 1, getKeyPadButton("C"));
		grid.addInCell(3, 1, getKeyPadButton("±"));
		grid.addInCell(4, 1, getKeyPadButton("SQRT"));
		
		grid.addInCell(0, 2, getKeyPadButton("7"));
		grid.addInCell(1, 2, getKeyPadButton("8"));
		grid.addInCell(2, 2, getKeyPadButton("9"));
		grid.addInCell(3, 2, getKeyPadButton("/"));
		grid.addInCell(4, 2, getKeyPadButton("%"));
		
		grid.addInCell(0, 3, getKeyPadButton("4"));
		grid.addInCell(1, 3, getKeyPadButton("5"));
		grid.addInCell(2, 3, getKeyPadButton("6"));
		grid.addInCell(3, 3, getKeyPadButton("*"));
		grid.addInCell(4, 3, getKeyPadButton("1/X"));
		
		grid.addInCell(0, 4, getKeyPadButton("1"));
		grid.addInCell(1, 4, getKeyPadButton("2"));
		grid.addInCell(2, 4, getKeyPadButton("3"));
		grid.addInCell(3, 4, getKeyPadButton("-"));
		Container cell = grid.getCell(4, 4);
		cell.setAttribute("rowspan", "2");
		cell.addChild(getKeyPadButton("=").setStyle("height", "69px"));
		
		
		Container zero = grid.getCell(0, 5);
		zero.setAttribute("colspan", "2");
		zero.addChild(getKeyPadButton("0"));
		grid.getCell(1, 5).remove();
		
		grid.getCell(1, 5).addChild(getKeyPadButton("."));
		grid.addInCell(2, 5, getKeyPadButton("+"));
		grid.getCell(3, 5).remove();
		
		
		
		return grid;
		
	}
	
	private Container getKeyPadButton(String txt){
		EXButton btn = new EXButton("", txt);
		btn.addEvent(this, CLICK);
		return btn.setStyle("width", "100%");
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		String txt = container.getText(false);
		
		if(txt.equals("-") || txt.equals("*") || txt.equals("/")){
			String current = screen.getValue().toString();
			if(!current.endsWith("-") && !current.endsWith("*") && !current.endsWith("/")){
				screen.setValue(screen.getValue().toString() + txt);
				memory = "";
			}
			
		}else if(txt.equals("1/X")){
			memory = "recip";
		}else if(txt.equals("C") || txt.equals("CE")){
			screen.setValue("0");
		}
		
		try{
			
			int number = Integer.parseInt(txt);
			if(memory.equals("recip")){
				txt = (1/number) + "";
				memory = "";
			}
			screen.setValue(screen.getValue().toString() + txt);
			
		}catch(Exception e){
			
		}
		
		
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
