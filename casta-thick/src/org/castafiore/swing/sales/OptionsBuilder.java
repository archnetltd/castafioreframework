package org.castafiore.swing.sales;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.border.TitledBorder;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

import org.openswing.swing.client.ComboBoxControl;
import org.openswing.swing.client.LabelControl;
import org.openswing.swing.client.TextAreaControl;
import org.openswing.swing.domains.java.Domain;
import org.openswing.swing.form.client.Form;

public class OptionsBuilder {
	
	public static Form buildForm(String code)throws Exception{
		Source source = new Source(Thread.currentThread().getContextClassLoader().getResource("org/castafiore/swing/sales/"+code+".xml"));
		List<Element> elements = source.getAllElements("casta:designable");
		TitledBorder border = new TitledBorder("Plan Options");
		border.setTitleColor(Color.ORANGE);
		Form form = new Form();
		form.setBorder(border);
		form.setLayout(new GridBagLayout());
		int rows = 0;
		int col = 0;
		form.setName(code);
		for(Element e : elements){
			String uniqueId = e.getAttributeValue("uniqueId");
			if("core:select".equalsIgnoreCase(uniqueId)){
				String name = e.getAttributeValue("name");
				String label = name;
				String options = "";
				
				for(Element attr : e.getAllElements("casta:attribute")){
					if(attr.getAttributeValue("name").equalsIgnoreCase("label")){
						label = attr.toString().replace("<casta:attribute name=\"label\"><![CDATA[", "").replace("]]></casta:attribute>", "").trim();
					}
					
					if(attr.getAttributeValue("name").equalsIgnoreCase("options")){
						System.out.println(attr.toString());
						options = attr.toString().replace("<casta:attribute name=\"options\"><![CDATA[", "").replace("]]></casta:attribute>", "").trim();
					}
				}
				
				ComboBoxControl control = createCombo(options, name);
				if(col ==0){
					form.add(new LabelControl(label),	new GridBagConstraints(0, rows, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,new Insets(5, 5, 5, 5), 0, 0));
					form.add(control,	 				new GridBagConstraints(1, rows, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,new Insets(5, 5, 5, 5), 0, 0));
					col++;
				}else if(col == 1){
					form.add(new LabelControl(label),	new GridBagConstraints(2, rows, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,new Insets(5, 5, 5, 5), 0, 0));
					form.add(control,	 				new GridBagConstraints(3, rows, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,new Insets(5, 5, 5, 5), 0, 0));
					rows++;
					col = 0;
				}
				
			}else if("core:textarea".equals(uniqueId)){
//				String name = e.getAttributeValue("name");
//				TextAreaControl control = new TextAreaControl();
//				control.setName(name);
//				control.setAttributeName(name);
//				form.add(control,   	new GridBagConstraints(0, rows, 4, 1, 1.0, 1.0,GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
			}
		}
		return form;
		
	}
	
	private static ComboBoxControl createCombo(String options, String name){
		ComboBoxControl control = new ComboBoxControl();
		control.setName(name);
		control.setAttributeName(name);
		String[] opts = options.split(",");
		Domain dom = new Domain(name);
		for(String opt : opts){
			dom.addDomainPair(opt, opt);
		}
		
		control.setDomain(dom);
		
		return control;
		
	}

}
