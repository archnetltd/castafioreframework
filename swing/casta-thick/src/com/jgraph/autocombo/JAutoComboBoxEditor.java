/*
 * @(#)JAutoComboBoxEditor.java	1.0 16-MAR-04
 * 
 * Copyright (c) 2001-2004 Gaudenz Alder
 *  
 */
package com.jgraph.autocombo;

import java.awt.Component;

import javax.swing.plaf.basic.BasicComboBoxEditor;

import com.jgraph.JAutoComboBox;

/**
 * The default editor for auto-complete combo boxes.
 * The editor is implemented as a JAutoTextField.
 *
 * @version 1.0 16-MAR-04
 * @author Gaudenz Alder
 */
public class JAutoComboBoxEditor extends BasicComboBoxEditor {

	/**
	 * Specifies the component to use for editing values.
	 */
	protected JAutoTextField editorComponent = null;
	
	protected JAutoComboBox autoCombo = null;

	/** 
	 * Creates a <code>AutoComboBoxEditor</code> for the specified
	 * auto combo box.
	 *
	 * @param aCombo  the parent combo box for this editor
	 * @see com.jgraph.example.JAutoComboBox
	 * @see javax.swing.JComboBox
	 */
	public JAutoComboBoxEditor(JAutoComboBox autoCombo) {
		editorComponent = createTextField(autoCombo);
		this.autoCombo = autoCombo;
	}

	/** 
	 * Returns the component to use for editing values. 
	 *
	 * @return the value of the editorComponent member
	 */
	public Component getEditorComponent() {
		return editorComponent;
	}

	/** 
	 * Returns the item that is being edited. 
	 *
	 * @return the displayed value of the editor
	 */
	public Object getItem() {
		if (editorComponent.getSelectedIndex() >= 0)
			return autoCombo.getItemAt(editorComponent.getSelectedIndex());
		else
			return editorComponent.getText();
	}

	/** 
	 * Sets the item that should be edited. 
	 *
	 * @param anObject the displayed value of the editor
	 */
	public void setItem(Object anObject) {
		editorComponent.setText((anObject != null) ? anObject.toString() : null);
		editorComponent.setSelectedIndex(autoCombo.getSelectedIndex());
	}

	/** 
	 * Selects the displayed value of the editor. 
	 */
	public void selectAll() {
		editorComponent.selectAll();
	}

	/** 
	 * Returns a new text field to be used with this editor.
	 * This may be used by subclassers to provide a custom text field.
	 */
	public JAutoTextField createTextField(JAutoComboBox autoCombo) {
		return new JAutoTextField(autoCombo);
	}

}