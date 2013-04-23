package org.castafiore.swing.options;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JPanel;

import org.openswing.swing.client.EditButton;
import org.openswing.swing.client.PropertyGridControl;
import org.openswing.swing.client.SaveButton;
import org.openswing.swing.client.TextControl;
import org.openswing.swing.mdi.client.InternalFrame;
import org.openswing.swing.util.java.Consts;

public class OptionsFrame extends InternalFrame{
	
	
	TextControl thermalPrinterName = new TextControl();
	TextControl normalPrinterName = new TextControl();
	
	
	
	public OptionsFrame() {
		super();
		jbInit();
	}



	public void jbInit(){
		setSize(500, 400);
		JPanel buttons = new JPanel();
		buttons.setLayout(new FlowLayout(FlowLayout.LEFT));
		PropertyGridControl properties = new PropertyGridControl();
		thermalPrinterName.setAttributeName("thermalPrinterName");
		normalPrinterName.setAttributeName("normalPrinterName");
		thermalPrinterName.setEnabledOnEdit(true);
		normalPrinterName.setEnabledOnEdit(true);
		properties.addProperty("Thermal Printer",thermalPrinterName , "TM00000", PropertiesUtil.properties.getProperty("thermalPrinterName", "TM00000"));
		properties.addProperty("Normal Printer",normalPrinterName , "HP-4567", PropertiesUtil.properties.getProperty("normalPrinterName", "HP-4567"));
		
		SaveButton save = new SaveButton();
		EditButton edit = new EditButton();
		buttons.add(edit);
		buttons.add(save);
		
		properties.setPropertyValueWidth(300);
		properties.setPropertyNameWidth(180);
		
		properties.setSaveButton(save);
		properties.setEditButton(edit);
		properties.setController(new OptionPropertiesController());
		setLayout(new BorderLayout());
		add(properties, BorderLayout.CENTER);
		add(buttons, BorderLayout.NORTH);
		properties.reload();
		setVisible(true);
	}

}
