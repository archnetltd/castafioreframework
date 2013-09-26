package org.castafiore.ui.dynaform;

import org.castafiore.ui.FormComponent;

public abstract class InputVerifier {
	
	  /**
	   * Checks whether the FormComponent input is valid. This method should
	   * have no side effects. It returns a boolean indicating the status
	   * of the argument's input.
	   *
	   * @param input the FormComponent to verify
	   * @return <code>true</code> when valid, <code>false</code> when invalid
	   * @see FormComponent#setInputVerifier
	   * @see FormComponent#getInputVerifier
	   *
	   */
	  
	  public abstract boolean verify(FormComponent<?> input);


	  /**
	   * Calls <code>verify(input)</code> to ensure that the input is valid.
	   * This method can have side effects. In particular, this method
	   * is called when the user attempts to advance focus out of the
	   * argument component into another  component in this window.
	   * If this method returns <code>true</code>, then the focus is transfered
	   * normally; if it returns <code>false</code>, then the focus remains in
	   * the argument component.
	   *
	   * @param input the FormComponent to verify
	   * @return <code>true</code> when valid, <code>false</code> when invalid
	   * @see FormComponent#setInputVerifier
	   * @see FormComponent#getInputVerifier
	   *
	   */

	  public boolean shouldYieldFocus(FormComponent<?> input) {
	    return verify(input);
	  }

}
