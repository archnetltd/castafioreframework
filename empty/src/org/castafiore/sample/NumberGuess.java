package org.castafiore.sample;

import java.util.Map;
import java.util.Random;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXApplication;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.button.EXButton;
import org.castafiore.ui.ex.form.EXInput;

public class NumberGuess extends EXApplication {


	private static final long serialVersionUID = 8832146769192130024L;

	//creates a new instance of h1
	final Container header1 = new EXContainer("", "h1").setText("Enter a number between 1-100");
	
	//create an input box
	final EXInput input = new EXInput("value");
	
	//creates an h3 to hold the result
	final Container result = new EXContainer("result", "h3");
	
	//create 2 buttons (submit and clear)
	final EXButton submit = new EXButton("submit", "Submit");
	final EXButton clear = new EXButton("clear", "Clear");
	
	final Random RAND = new Random();
	
	private int expectedResult = RAND.nextInt(100) +1;
	
	public NumberGuess() {
		super("numguess");
	}
	
	@Override
	public void initApp() {
		
		setStyle("font-size", "64%");
		addChild(header1);
		
		//add the input to the application
		addChild(input);
		
		//adds the h3 in the application
		addChild(result);
		
		//create a container to hold the button
		EXContainer div = new EXContainer("horizontal", "div");
		
		//sets the button to float left and with a margin
		submit.setStyle("float", "left").setStyle("margin", "3px 5px");
		clear.setStyle("float", "left").setStyle("margin", "3px 5px");
		
		//add events on the button. see below for implementation of events
		submit.addEvent(SUBMIT, Event.CLICK);
		clear.addEvent(CLEAR, Event.CLICK);
		
		//adds the buttons to the div
		div.addChild(submit);
		div.addChild(clear);
		
		//add the div to the container
		addChild(div);
	}
	
	/**
	 * Clear the result and input box and select another random number
	 */
	@SuppressWarnings("serial")
	public final Event CLEAR = new Event(){

		@Override
		public void ClientAction(ClientProxy container) {
			container.makeServerRequest(this);
		}

		@Override
		public boolean ServerAction(Container container, Map<String, String> params)
				throws UIException {
			result.setText("");
			input.setValue("");
			expectedResult = RAND.nextInt(100)+1;
			return true;
		}

		@Override
		public void Success(ClientProxy container, Map<String, String> params)
				throws UIException {
			
		}
		
	};
	
	/**
	 * event to add to submit
	 */
	@SuppressWarnings("serial")
	public final  Event SUBMIT = new Event() {
		
		@Override
		public void ClientAction(ClientProxy container) {
			container.makeServerRequest(this);
		}
		
		@Override
		public boolean ServerAction(Container container, Map<String, String> arg1)
				throws UIException {
			
			String value = input.getValue().toString();
			
			try{
				int ivalue = Integer.parseInt(value);
				if(ivalue > expectedResult){
					result.setText("You need a value smaller");
				}else if(ivalue < expectedResult){
					result.setText("You need a value greater than this one");
				}else{
					result.setText("You got it right!!");
				}
			}catch(Exception e){
				result.setText("Please enter a numeric value between 1 and 100");
			}
			return true;
		}
		@Override
		public void Success(ClientProxy arg0, Map<String, String> arg1)
				throws UIException {
			
		}
	};



}
