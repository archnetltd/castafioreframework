package org.castafiore.ui.ex.form;

import java.io.Serializable;

import org.castafiore.ui.js.JArray;

public interface AutoCompleteSource extends Serializable{
	
	public JArray getSource(String param);

}
