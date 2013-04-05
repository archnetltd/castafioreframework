/*
 * Copyright (C) 2007-2010 Castafiore
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */
 package org.castafiore.ui.ex.form;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.EXGrid;
import org.castafiore.ui.ex.EXGrid.EXRow;
import org.castafiore.ui.ex.form.button.Button;
import org.castafiore.ui.ex.form.button.EXIconButton;
import org.castafiore.ui.ex.form.button.Icons;
import org.castafiore.ui.input.Decoder;
import org.castafiore.ui.input.Encoder;
import org.castafiore.utils.EventUtil;
import org.castafiore.utils.StringUtil;

public abstract class EXMultiInput extends EXContainer implements StatefullComponent {

	
	public EXMultiInput(String name) {
		super(name, "div");
		
		EXGrid grid = new EXGrid("grid", 4, 1);
		grid.addClass("ui-widget ui-widget-content").setAttribute("cellpadding", "0").setAttribute("cellspacing", "0").setWidth(Dimension.parse("100%"));
		addChild(grid);
	}
	
	
	public abstract  StatefullComponent getInput();
	
	protected Button getPencilButton(){
		return getButton("edit", Icons.ICON_PENCIL, "editRow");
	}
	
	
	protected Button getMinusButton(){
		return getButton("delete", Icons.ICON_MINUSTHICK, "deleteRow");
	}
	
	protected Button getPlusButton(){
		return getButton("add", Icons.ICON_PLUSTHICK, "addRow");
	}
	
	protected Button getButton(String name, Icons icon,String method ){
		EXIconButton button = new EXIconButton(name,null, icon);
		button.setWidth(Dimension.parse("0px"));
		button.setHeight(Dimension.parse("4px"));
		button.removeClass("ui-corner-all");
		button.addEvent(EventUtil.getEvent(method, getClass(), getClass()), Event.CLICK);
		button.setStyle("margin", "2px");
		return button;
	}
	
	public void editRow(Container caller){
		StatefullComponent input =  (StatefullComponent)caller.getParent().getParent().getChildByIndex(0).getChildByIndex(0);
		String selected  =  input.getValue().toString();
		Container row = caller.getParent().getParent();
		onEdit(selected, row);
		
		
	}
	
	public abstract void onEdit(String selected, Container row);
	
	public void addRow(String val){
		EXGrid grid = getDescendentOfType(EXGrid.class);
		boolean addMinus = true;
		if(grid.getChildren().size() <=1){
			addMinus = false;
		}
		
		int size = grid.getChildren().size();
		String cls = null;
		if((size % 2) == 0){
			cls = "ui-state-highlight";
		}
		for(Container row : grid.getChildren()){
			if(row.getDescendentByName("add") != null)
				row.getDescendentByName("add").remove();
			if(row.getDescendentByName("delete") != null)
				row.getDescendentByName("delete").remove();
		}
		EXRow row = grid.addRow();
		if(cls != null){
			row.addClass(cls);
		}
		row.getChildByIndex(0).addClass("ui-priority-primary");
		row.getChildByIndex(0).setStyle("padding-left", "5px");
		row.getChildByIndex(1).setAttribute("width", "25px");
		row.getChildByIndex(2).setAttribute("width", "25px");
		row.getChildByIndex(3).setAttribute("width", "25px");
		StatefullComponent input = getInput();
		input.setValue(val);
		row.addInCell(0,  input);
		row.addInCell(1, getPencilButton());
		if(addMinus){
			row.addInCell(2, getMinusButton());
		}
		row.addInCell(3, getPlusButton());
	}
	
	public void addRow(Container caller){
		StatefullComponent input =  (StatefullComponent)caller.getParent().getParent().getChildByIndex(0).getChildByIndex(0);
		String selected  =  input.getValue().toString();
		if(StringUtil.isNotEmpty(selected)){
			addRow("");
			EXGrid grid = caller.getAncestorOfType(EXGrid.class);
			Container newRow = grid.getChildByIndex(grid.getChildren().size()-1);
			onAddRow(selected, newRow);
		}
	}
	
	public abstract void onAddRow(String lastItem, Container newRow);
	
	public void deleteRow(){
		EXGrid grid = getDescendentOfType(EXGrid.class);
		if(grid.getChildren().size() > 1){
			Container lastRow = grid.getChildren().get(grid.getChildren().size()-1);
			lastRow.remove();
			lastRow = grid.getChildren().get(grid.getChildren().size()-1);
			lastRow.getChildByIndex(2).addChild(getMinusButton());
			lastRow.getChildByIndex(3).addChild(getPlusButton());
		}
	}
	
	public Object getValue(){
		EXGrid grid = getDescendentOfType(EXGrid.class);
		List<Container> children  = grid.getChildren();
		List<String> result = new ArrayList<String>();
		for(Container c : children){
			StatefullComponent input = (StatefullComponent)c.getChildByIndex(0).getChildByIndex(0);
			String file = input.getValue().toString();
			if(StringUtil.isNotEmpty(file))
				result.add(file);
		}
		return result;
	}
	
	public void setValue(Object value){
		List files = (List)value;
		EXGrid grid = getDescendentOfType(EXGrid.class);
		
		List<String> result = new ArrayList<String>();
		for(int i = 0; i < files.size(); i ++){
			List<Container> children  = grid.getChildren();
			String file = files.get(i).toString();
			if(children.size() > i){
				((StatefullComponent)children.get(i).getChildByIndex(0).getChildByIndex(0)).setValue(file);
			}else{
				addRow(file);
			}
		}
		//return result;
	}

	public Decoder getDecoder() {
		// TODO Auto-generated method stub
		return null;
	}



	public Encoder getEncoder() {
		// TODO Auto-generated method stub
		return null;
	}



	public String getRawValue() {
		// TODO Auto-generated method stub
		return null;
	}






	public void setDecoder(Decoder decoder) {
		// TODO Auto-generated method stub
		
	}



	public void setEncoder(Encoder encoder) {
		// TODO Auto-generated method stub
		
	}



	public void setRawValue(String rawValue) {
		// TODO Auto-generated method stub
		
	}


	
	
}
