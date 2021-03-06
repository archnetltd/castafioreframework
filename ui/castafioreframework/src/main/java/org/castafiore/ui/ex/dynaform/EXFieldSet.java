/*
 * 
 */
package org.castafiore.ui.ex.dynaform;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.button.Button;
import org.castafiore.ui.input.Decoder;
import org.castafiore.ui.input.Encoder;
import org.castafiore.utils.ComponentUtil;

public class EXFieldSet extends EXContainer implements StatefullComponent{

	private int columns = 1; 
	
	
	public EXFieldSet(String name, String title, boolean doubleColumn) {
		super(name, "table");
		setWidth(Dimension.parse("100%"));
		addClass("ui-widget-content");
		addClass("EXFieldSet");
		if(doubleColumn)
			this.columns = 2;
		
		addTitle(title);
		addBody();
	}
	
	
	protected void addTitle(String title){
		String sText = "<tr><td colspan=\"4\"><span class=\"EXFieldSetCaption\"><span class=\"ui-icon\" style=\"float:left\"></span>"+title+"</span></td></tr>";
		Container head = new EXContainer("thead", "thead").setText(sText).addClass("ui-widget-header");
		head.setAttribute("onclick", new ClientProxy("#" + getId() + " tbody").toggle().getCompleteJQuery());
		addChild(head);
	}
	
	public EXFieldSet displayBody(boolean display){
		getDescendentByName("tbody").setDisplay(display);
		return this;
	}
	
	
	public void setTitle(String title){
		String sText = "<tr><td colspan=\"4\"><span class=\"EXFieldSetCaption\"><span class=\"ui-icon\" style=\"float:left\"></span>"+title+"</span></td></tr>";
		getChild("thead").setText(sText);
		
	}
	
	
	protected void addBody(){
		Container tBody = new EXContainer("tbody", "tbody");
		addChild(tBody);
	}
	
	
	protected Container[] getLastCellsToAddLabelAndField(){
		Container tBody = getChild("tbody");
		List<Container> rows = tBody.getChildren();
		if(rows.size() == 0){
			Container row  = addRow(true);
			return new Container[]{row.getChildByIndex(0), row.getChildByIndex(1)};
		}else{
			Container row = tBody.getChildByIndex(rows.size()-1);
			if ( row.getChildByIndex(0).getChildren().size() ==0){
				//no children added in row yet
				return new Container[]{row.getChildByIndex(0), row.getChildByIndex(1)};
			}else{
				if(columns == 2){
					//before checking if 2col can be added, must first check colspan
					Container td = row.getChildByIndex(2);
					if(td.getChildren().size() ==0){
						//column is not spanned so can add
						return new Container[]{row.getChildByIndex(2), row.getChildByIndex(3)};
					}else{
						Container row1  = addRow(tBody.getChildren().size()%2==0);
						return new Container[]{row1.getChildByIndex(0), row1.getChildByIndex(1)};
					}
				}else{
					Container row1  = addRow(tBody.getChildren().size()%2==0);
					return new Container[]{row1.getChildByIndex(0), row1.getChildByIndex(1)};
				}
			}
		}
	}
	
	
	public void addButton(Button btn){
		
		if(getDescendentByName("__btnContainers") != null){
			getDescendentByName("__btnContainers").addChild(btn);
		}else
		
			getChild("tbody").addChild(new EXContainer("", "tr").addChild(new EXContainer("__btnContainers", "td").setAttribute("colspan", "8").setStyle("text-align", "center").addChild(btn)));
		
	}
	
	protected Container addRow(boolean odd){
		Container tBody = getChild("tbody");
		
		Container tr = new EXContainer("", "tr");
		if(odd){
			//tr.addClass("ui-state-highlight");
		}else{
			tr.addClass("even");
		}
		
		tBody.addChild(tr);
		//add label
		//add field
		
		Container td = new EXContainer("", "td");
		td.addClass("EXLabel");
		tr.addChild(td);
		Container td1 = new EXContainer("", "td");
		td1.addClass("EXField");
		tr.addChild(td1);
		
		
		//if 2 columns
		if(columns == 2){
			Container td2 = new EXContainer("", "td");
			td2.addClass("EXLabel");
			tr.addChild(td2);
			Container td3 = new EXContainer("", "td");
			td3.addClass("EXField");
			tr.addChild(td3);
		}
		return tr;
	}
	
	
	
	
	public void addField(String label, StatefullComponent input){
		addField(label, input, false);
	}
	
	
	public void addField(Container label, StatefullComponent input){
		addField(label, input, false);
	}
	
	
	public void addField(StatefullComponent input){
		addField(input, false);
	}
	
	
	public void addField(Container label, StatefullComponent input, boolean spancolumn){
		if(!spancolumn){
			Container[] tds = getLastCellsToAddLabelAndField();
			tds[0].addChild(label);
			tds[1].addChild(input);
		}else{
			Container tBody = getChild("tbody");
			Container row = addRow(tBody.getChildren().size()%2==0);
			row.getChildByIndex(0).addChild(label);
			row.getChildByIndex(1).setAttribute("colspan", "3").addChild(input);
		}
	}
	
	public void addField(String label, StatefullComponent input, boolean spancolumn){
		Container eXLabel = new EXContainer(input.getName() + "_label", "a");
		eXLabel.setAttribute("href", "#");
		eXLabel.setText(label);
		addField(eXLabel, input, spancolumn);
		
	}
	
	public void addField(StatefullComponent input, boolean spancolumn){
		addField(input.getName(), input, spancolumn);
	}
	
	public StatefullComponent getField(String name){
		return (StatefullComponent)getDescendentByName(name);
	}
	
	public List<StatefullComponent> getFields(){
		Container tBody = getChild("tbody");
		
		List result = new ArrayList<Container>();
		
		
		ComponentUtil.getDescendentsOfType(tBody, result, StatefullComponent.class);
		
		return result;
	}


	public Decoder getDecoder() {
		
		return null;
	}


	public Encoder getEncoder() {
		
		return null;
	}


	public String getRawValue() {
		
		return null;
	}


	public Object getValue() {
		
		return null;
	}


	public void setDecoder(Decoder decoder) {
		
		
	}


	public void setEncoder(Encoder encoder) {
		
		
	}


	public void setRawValue(String rawValue) {
		
		
	}


	public void setValue(Object value) {
		
		
	}

}
