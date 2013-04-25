/*
 * @(#)JAutoComboBox.java	1.0 09/12/03
 * 
 * Copyright (c) 2001-2004 Gaudenz Alder
 *  
 */
package com.jgraph;

import java.util.Vector;

import javax.swing.ComboBoxEditor;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.event.EventListenerList;

import com.jgraph.autocombo.JAutoComboBoxEditor;
import com.jgraph.autocombo.JAutoTextField;

/**
 * An auto-complete combo box for Java. The user can select a value from the
 * drop-down list, which appears at the user's request. By default, the combo
 * box is editable and includes an editable field into which the user can type a
 * value. As the user types, the combo box automatically completes the value for
 * the next alphabetical combo item. If no such item exists, the combo box does
 * not accept the user input.
 * <p>
 * The auto combo box uses a JAutoTextField as its default editor. This text
 * field calls the combo box's <code>isValidChar</code> method to check if a
 * specific keystroke is allowed. If a keystroke is not allowed then it is not
 * accepted by the text field.
 * <p>
 * The auto complete combo box requires that its items are sorted. This is not
 * enforced by the component. Instead, a sorted array, vector or model must be
 * passed to the constructor.
 * <p>
 * For the keyboard keys used by this component in the standard Look and Feel
 * (L&F) renditions, see the <a href="doc-files/Key-Index.html#JComboBox">
 * <code>JComboBox</code> key assignments </a>.
 * <p>
 * <strong>Warning: </strong> Serialized objects of this class will not be
 * compatible with future Swing releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running the
 * same version of Swing. As of 1.4, support for long term storage of all
 * JavaBeans <sup><font size="-2">TM </font> </sup> has been added to the
 * <code>java.beans</code> package. Please see java.beans.XMLEncoder.
 * <p>
 * See <a
 * href="http://java.sun.com/docs/books/tutorial/uiswing/components/combobox.html">How
 * to Use Combo Boxes </a> in <a
 * href="http://java.sun.com/Series/Tutorial/index.html">
 * <em>The Java Tutorial</em> </a> for further information.
 * <p>
 * 
 * @see com.jgraph.example.autocombo.AutoComboBoxEditor
 * @see com.jgraph.autocombo.JAutoTextField
 * @see javax.swing.JComboBox
 * 
 * @version 1.0 09/12/03
 * @author Gaudenz Alder
 */
public class JAutoComboBox extends JComboBox {

	/**
	 * Specifies the version of this component.
	 */
	public static final String VERSION = "@NAME@ (v@VERSION@)";

	/**
	 * Specifies allowed characters, such as &, -, @ etc.
	 */
	public static final String chars = "@#¦|[]{}-´.,;:_+\"*ç%&/()=?!$£§°^'~` ";

	/**
	 * This protected field is implementation specific. Do not access directly
	 * or override. Use the accessor methods instead.
	 * 
	 * @see #isStrict
	 * @see #setStrict
	 */
	protected boolean isStrict = true;

	/**
	 * This protected field is implementation specific. Do not access directly
	 * or override. Use the accessor methods instead.
	 * 
	 * @see #isClosePopupOnEnter
	 * @see #setClosePopupOnEnter
	 */
	protected boolean isClosePopupOnEnter = true;

	/**
	 * Creates a <code>JAutoComboBox</code> that takes it's items from an
	 * existing <code>ComboBoxModel</code>. Since the
	 * <code>ComboBoxModel</code> is provided, a combo box created using this
	 * constructor does not create a default combo box model and may impact how
	 * the insert, remove and add methods behave.
	 * 
	 * @param aModel
	 *            the <code>ComboBoxModel</code> that provides the displayed
	 *            list of items
	 * @see javax.swing.DefaultComboBoxModel
	 */
	public JAutoComboBox(ComboBoxModel aModel) {
		super(aModel);
		this.setEditor(createEditor());
		this.setEditable(true);
	}

	/**
	 * Creates a <code>JAutoComboBox</code> that contains the elements in the
	 * specified sorted array. By default the first item in the array (and
	 * therefore the data model) becomes selected.
	 * 
	 * @param items
	 *            an array of objects to insert into the combo box
	 * @see javax.swing.DefaultComboBoxModel
	 * @see java.util.Arrays#sort(Object[])
	 */
	public JAutoComboBox(Object[] items) {
		super(items);
		this.setEditor(createEditor());
		this.setEditable(true);
	}

	/**
	 * Creates a <code>JAutoComboBox</code> that contains the elements in the
	 * specified sorted vector. By default the first item in the vector (and
	 * therefore the data model) becomes selected.
	 * 
	 * @param items
	 *            a vector of objects to insert into the combo box
	 * @see javax.swing.DefaultComboBoxModel
	 * @see java.util.Arrays#sort(Object[])
	 */
	public JAutoComboBox(Vector items) {
		super(items);
		this.setEditor(createEditor());
		this.setEditable(true);
	}

	/**
	 * Returns true if the combo box should only allow entries that are in the
	 * list. By default, this value is set to true.
	 * 
	 * @return true if the combo list should only accept list items
	 */
	public boolean isStrict() {
		return isStrict;
	}

	/**
	 * Determines whether the user is allowed to only type list items.
	 * 
	 * @param isStrict
	 *            a boolean value, where true indicates that the combo should
	 *            only allow list items as values
	 */
	public void setStrict(boolean isStrict) {
		this.isStrict = isStrict;
	}

	/**
	 * Returns true if the popup list should disappear when the user presses
	 * enter. By default, this value is set to true.
	 * 
	 * @return true if the combo list should disappear upon VK_ENTER
	 */
	public boolean isClosePopupOnEnter() {
		return isClosePopupOnEnter;
	}

	/**
	 * Determines whether the popup list should disappear when the user types
	 * enter.
	 * 
	 * @param isClosePopupOnEnter
	 *            a boolean value, where true indicates that the combo list
	 *            should close on enter
	 */
	public void setClosePopupOnEnter(boolean isClosePopupOnEnter) {
		this.isClosePopupOnEnter = isClosePopupOnEnter;
	}

	/**
	 * Returns true if <code>c</code> is a letter, digit or space.
	 * 
	 * @return true if <code>c</code> is a valid character, else false
	 */
	public boolean isValidChar(char c) {
		return Character.isLetterOrDigit(c) || chars.indexOf(c) >= 0;
	}

	/**
	 * Returns a new combo box editor to be used with this combo box. This may
	 * be used by subclassers to provide a custom editor.
	 */
	public ComboBoxEditor createEditor() {
		JAutoComboBoxEditor editor = new JAutoComboBoxEditor(this);

		if (getBorder() != null)
			((JAutoTextField) editor.getEditorComponent())
					.setBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0));

		return editor;
	}

	public EventListenerList getListenerList() {
		return listenerList;
	}

}