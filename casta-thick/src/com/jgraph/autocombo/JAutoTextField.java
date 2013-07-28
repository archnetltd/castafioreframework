/*
 * @(#)JAutoTextField.java 1.0 09/12/03
 * 
 * Copyright (c) 2001-2004 Gaudenz Alder
 *  
 */
package com.jgraph.autocombo;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ComboBoxModel;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import com.jgraph.JAutoComboBox;

/**
 * <code>JAutoTextField</code> is intended to be used as the editor component
 * of the auto combo box editor. The superclass should be consulted for
 * additional capabilities.
 * <p>
 * The <code>JAutoTextField</code> does not allow cut and paste operations,
 * and does only acceps chars for which the <code>isValidChar</code> method
 * returns true.
 * <p>
 * Whether the popup list closes when the user presses enter depends on the
 * <code>isClosePopupOnEnter</code> method of the corresponding
 * <code>JAutoComboBox</code>.
 * <p>
 * <strong>Warning: </strong> Serialized objects of this class will not be
 * compatible with future Swing releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running the
 * same version of Swing. As of 1.4, support for long term storage of all
 * JavaBeans <sup><font size="-2">TM </font> </sup> has been added to the
 * <code>java.beans</code> package. Please see java.beans.XMLEncoder.
 * <p>
 * 
 * @see AutoComboBoxEditor
 * 
 * @author Gaudenz Alder
 * @version 1.0 09/12/03
 */
public class JAutoTextField extends JTextField {

	private JAutoComboBox autoCombo = null;

	private ListModel listModel = null;

	private String userInput = new String("");

	private String lastInput = new String("");

	private String lastApproved = new String("");

	private boolean doSetText = true;

	private boolean forcedText = false;

	private int selectedIndex = -1;
	
	/**
	 * Constructs a new <code>JAutoTextField</code> initialized with the
	 * specified auto combo box. The auto combo box items are used to limit the
	 * possible values a user can type.
	 * 
	 * @param autoCombo
	 *            the parent auto-complete combo box
	 */
	public JAutoTextField(JAutoComboBox autoCombo) {
		this(autoCombo.getModel());
		this.autoCombo = autoCombo;
		// Update the backing list model when the comboboxmodel changes
		autoCombo.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				if (arg0.getPropertyName().equals("model")) {
					listModel = (ComboBoxModel) arg0.getNewValue();
					userInput = new String("");
					lastInput = new String("");
					lastApproved = new String("");
					setText("");
				}
			}
		});
	}

	/**
	 * Constructs a new <code>JAutoTextField</code> initialized with the
	 * specified list model. The list model is used to limit the possible values
	 * a user can type.
	 * 
	 * @param autoCombo
	 *            the parent auto-complete combo box
	 */
	public JAutoTextField(ListModel aListModel) {
		listModel = aListModel;
		// Make the first element default
		setText("");
		// Listen to key events to control user input
		KeyAdapter ka = new KeyAdapter() {

			public void keyPressed(KeyEvent e) {
				if (userInput == null)
					userInput = "";
				forcedText = false;
				int offset = getSelectionStart();
				int len = getSelectionEnd() - offset;
				int user = userInput.length();
				if ((offset < user) && (len > 0)) {
					if (offset + len > user)
						len = user - offset;
					userInput = userInput.substring(0, offset)
							+ userInput.substring(offset + len, user);
				}
				// Backspace
				if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
					if ((userInput.length() > 0) && (offset >= user))
						userInput = userInput.substring(0, user - 1);
					else if (len == 0 && offset > 0
							&& offset <= userInput.length())
						userInput = userInput.substring(0, offset - 1)
								+ userInput.substring(offset, user);
					setText(userInput);
					e.consume();
					// Delete
				} else if (e.getKeyCode() == KeyEvent.VK_DELETE) {
					if (!autoCombo.isStrict()) {
						try {
							((AutoDocument) getDocument()).removeText(
									getSelectionStart(), getSelectionEnd()
											- getSelectionStart());
							userInput = getText();
							setCaretPosition(userInput.length());
							forcedText = true;
						} catch (BadLocationException ex) {
							throw new RuntimeException(ex);
						}
					}
					e.consume();
					// Enter
				} else if ((e.getKeyCode() == KeyEvent.VK_ENTER)
						&& (autoCombo != null)) {
					if (autoCombo.isClosePopupOnEnter()
							&& autoCombo.isPopupVisible()) {
						autoCombo.setPopupVisible(false);
						e.consume();
					}
					Boolean bool = (Boolean) autoCombo
							.getClientProperty("JComboBox.isTableCellEditor");
					if (!autoCombo.isStrict() || (bool != null && bool.booleanValue())) {
						if (selectedIndex >= 0)
							autoCombo.setSelectedIndex(selectedIndex);
						else
							autoCombo.setSelectedItem(getText());
					}
				}
				// Valid character
				else if (isValidChar(e.getKeyChar()))
					userInput = getText().substring(0, offset) + e.getKeyChar();
			}
		};
		addKeyListener(ka);
	}

	/**
	 * Returns true if <code>c</code> is a valid character in the parent auto
	 * combo box, or if no auto combo box is available true is returned if c is
	 * a letter, digit or space.
	 * 
	 * @return true if <code>c</code> is a valid character, else false
	 */
	protected boolean isValidChar(char c) {
		if (autoCombo != null)
			return autoCombo.isValidChar(c);
		return Character.isLetterOrDigit(c) || c == KeyEvent.VK_SPACE;
	}

	/**
	 * Overrides parent method for special behaviour.
	 * 
	 * @param aString
	 *            the string to display
	 */
	public void setText(String aString) {
		if (doSetText) {
			userInput = aString;
			super.setText(aString);
		}
	}

	/**
	 * Overrides parent method with an empty implementation.
	 */
	public void cut() {
	}

	/**
	 * Overrides parent method with an empty implementation.
	 */
	public void paste() {
	}

	/**
	 * Overrides parent method to return a <code>AutoDocument</code>.
	 * 
	 * @param aString
	 *            the string to display
	 */
	protected Document createDefaultModel() {
		return new AutoDocument();
	}

	/**
	 * A document that implements auto completion functionality.
	 */
	protected class AutoDocument extends PlainDocument {

		/**
		 * Overrides parent method for special behaviour.
		 * 
		 * @param offset
		 *            start location of the remove
		 * @param len
		 *            the number of characters to remove
		 */
		public void remove(int offset, int len) throws BadLocationException {
			insertString(0, "", null);
		}

		private void removeText(int offset, int len)
				throws BadLocationException {
			super.remove(offset, len);
		}

		/**
		 * Overrides parent method for special behaviour.
		 * 
		 * @param offset
		 *            start location of the insert
		 * @param str
		 *            the string to insert
		 * @param a
		 *            the attributes for the string
		 */
		public void insertString(int offset, String str, AttributeSet a)
				throws BadLocationException {
			super.remove(0, this.getLength());
			String comp = null;
			int index = -1;
			if (userInput != null) {
				// Find a possible completion here
				for (int i = 0; i < listModel.getSize(); i++) {
					String currentString = listModel.getElementAt(i).toString();
					if (currentString.toLowerCase().startsWith(
							userInput.toLowerCase())) {
						comp = currentString;
						index = i;
						break;
					}
				}
				if (forcedText) {
					str = userInput;
				} else if (comp != null) {
					str = comp;
					lastInput = userInput;
					lastApproved = str;
				} else if (autoCombo != null && !autoCombo.isStrict()) {
					str = userInput;
					userInput = str;
				} else {
					userInput = lastInput;
					str = lastApproved;
				}
			}
			Boolean bool = (Boolean) ((autoCombo != null) ? autoCombo
					.getClientProperty("JComboBox.isTableCellEditor") : null);
			if (!forcedText && (autoCombo != null) &&
					((bool != null && !bool.booleanValue()) || bool == null)) {
				if (index != selectedIndex) {
					try {
						doSetText = false;
						autoCombo.setSelectedIndex(index);
					} catch (Exception e) {
					} finally {
						doSetText = true;
					}
				}
			}
			// Remember the selected Index
			selectedIndex = index;
			super.insertString(0, str, a);
			if (userInput != null)
				JAutoTextField.this.select(userInput.length(), str.length());
		}
	}
	/**
	 * @return Returns the selectedIndex.
	 */
	public int getSelectedIndex() {
		return selectedIndex;
	}
	/**
	 * @param selectedIndex The selectedIndex to set.
	 */
	public void setSelectedIndex(int selectedIndex) {
		this.selectedIndex = selectedIndex;
	}

}