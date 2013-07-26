package org.castafiore.shoppingmall.orders;

import java.util.Map;

import groovy.text.TemplateEngine;

import org.castafiore.ui.Container;
import org.castafiore.ui.events.Event;
import org.castafiore.wfs.types.File;
import org.w3c.dom.NodeList;

public interface OrdersWorkflow extends Event{
	
	public  String getStatus(int status);
	
	public  void addButtons(Container parent,int status, String actor,String organization,String filePath);
	
	public  String getColor(int status);
	
	public  void executeXML(Container source)throws Exception;
	
	public  String getHtml(TemplateEngine e, Map input)throws Exception;
	
	
	public void addSearchButtons(Container parent)throws Exception;
	
	
	public int[] getAvailableStates();
	
	public GUIReactor getReactor();
	
	public WorkflowForm createForm(NodeList fields, String title, File file, String actor, String organization,Container source);
	

}
