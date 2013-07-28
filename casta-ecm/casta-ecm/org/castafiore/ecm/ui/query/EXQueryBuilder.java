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
 package org.castafiore.ecm.ui.query;

import org.castafiore.SimpleKeyValuePair;
import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.EXGrid;
import org.castafiore.ui.ex.form.EXDatePicker;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.mac.item.MacColumnItem;
import org.castafiore.ui.mac.renderer.MacFinderColumnViewModel;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.EventUtil;
import org.castafiore.wfs.futil;
import org.castafiore.wfs.service.QueryParameters;
import org.castafiore.wfs.types.File;
import org.castafiore.wfs.types.FileImpl;
import org.hibernate.type.BigIntegerType;
import org.hibernate.type.BinaryType;
import org.hibernate.type.BlobType;
import org.hibernate.type.BooleanType;
import org.hibernate.type.ByteType;
import org.hibernate.type.CalendarType;
import org.hibernate.type.CharacterType;
import org.hibernate.type.DateType;
import org.hibernate.type.DoubleType;
import org.hibernate.type.FloatType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.ShortType;
import org.hibernate.type.StringType;

public class EXQueryBuilder extends EXContainer implements MacColumnItem, MacFinderColumnViewModel{
	
	public final static String TEXT_TYPE = "Text";
	
	public final static String DATE_TYPE = "Date";
	
	public final static String INTEGER_TYPE = "Integer";
	
	public final static String DECIMAL_TYPE = "Decimal";
	
	public final static String BOOLEAN_TYPE = "Boolean";
	
	public final static String BINARY_TYPE = "Binary";
	
	
	public final static String[] DATA_TYPES = {TEXT_TYPE, DATE_TYPE, INTEGER_TYPE, DECIMAL_TYPE, BOOLEAN_TYPE, BINARY_TYPE};

	public EXQueryBuilder(String name) {
		super(name, "div");
		
		EXGrid grid = new EXGrid("", 2, 5);
		grid.setAttribute("width", "100%");
		
		addChild(grid);
		
		grid.addInCell(0, 0, ComponentUtil.getContainer("", "span", "Select entity type :", null).setStyle("line-height", "30px"));
		grid.getCell(0, 0).setStyle("width", "150px");
		DefaultDataModel model = new DefaultDataModel(futil.getEntityTypes());
		EXSelect entityType = new EXSelect("entityType", model);
		entityType.setAttribute("ancestor", getClass().getName());
		entityType.setAttribute("method", "selectEntityType");
		entityType.addEvent(EventUtil.GENERIC_FORM_METHOD_EVENT, Event.CHANGE);
		grid.addInCell(1, 0, entityType);
		
		grid.addInCell(0, 1, ComponentUtil.getContainer("", "span", "Select directories to search in :", null));
		grid.getCell(0, 1).setAttribute("colspan", "2");
		grid.getCell(1, 1).remove();
		grid.addInCell(0, 2, new EXMultiFileSelector(""));
		grid.getCell(0, 2).setAttribute("colspan", "2");
		grid.getCell(1, 2).remove();
		
		
		Container fourthRow = grid.getChildByIndex(3);
		fourthRow.getChildByIndex(0).setAttribute("colspan", "2");
		fourthRow.getChildByIndex(1).remove();
		Container cell = fourthRow.getChildByIndex(0);
		cell.setName("smallExplanation");
		
		Container fifthRow = grid.getChildByIndex(4);
		fifthRow.getChildByIndex(0).setAttribute("colspan", "2");
		fifthRow.getChildByIndex(1).remove();
		EXTable table = new EXTable("table", new EXQueryBuilderTableModel(FileImpl.class.getName()));
		table.setCellRenderer(new QueryBuilderCellRenderer());
		table.setWidth(Dimension.parse("100%"));
		fifthRow.getChildByIndex(0).addChild(table);
		
		
		
	}
	
	public void changeOperator(Container caller){
		String operator =((StatefullComponent)caller).getValue().toString();
		String dataType = caller.getParent().getParent().getDescendentByName("dataType").getText(false);
		Container inputContainer = caller.getParent().getParent().getDescendentByName("inputContainer");
		inputContainer.getChildren().clear();
		inputContainer.setRendered(false);
		if(operator.equals("between")){
			if(dataType.equals(DATE_TYPE)){
				inputContainer.addChild(new EXDatePicker("from"));
				inputContainer.addChild(new EXDatePicker("to"));
			}else{
				inputContainer.addChild(new EXInput("from"));
				inputContainer.addChild(new EXInput("to"));
			}
		}else{
			if(dataType.equals(DATE_TYPE)){
				inputContainer.addChild(new EXDatePicker("from"));
			}else{
				inputContainer.addChild(new EXInput("from"));
			}
		}
		ComponentUtil.applyStyleOnAll(inputContainer, StatefullComponent.class, "border", "solid 1px silver");
		ComponentUtil.applyStyleOnAll(inputContainer, StatefullComponent.class, "width", "110px");
		ComponentUtil.applyStyleOnAll(inputContainer, StatefullComponent.class, "margin", "0 5px 0 3px");
		
	}
	
	
	
	/**
	 * this methods adjust the interface according to the entity type
	 */
	public void selectEntityType(Container caller){
		String val = ((SimpleKeyValuePair)((StatefullComponent)caller).getValue()).getKey();
		getDescendentOfType(EXTable.class).setModel(new EXQueryBuilderTableModel(val));
	}
	
	public QueryParameters getQueryParameters(){
		return null;
	}
	
	
	public static String getType(Object val){
		if(val instanceof IntegerType || val instanceof ShortType || val instanceof LongType || val instanceof BigIntegerType){
			return   DATA_TYPES[2];
		}else if(val instanceof FloatType || val instanceof DoubleType){
			return   DATA_TYPES[3];
		}else if(val instanceof CalendarType || val instanceof DateType){
			return   DATA_TYPES[1];
		}else if(val instanceof BlobType || val instanceof BinaryType || val instanceof ByteType){
			return   DATA_TYPES[5];
		}else if(val instanceof StringType || val instanceof CharacterType){
			return   DATA_TYPES[0];
		}else if(val instanceof BooleanType){
			return   DATA_TYPES[4];
		}else{
			return "Unknown";
		}
	}
	
	/// methods to implement 

	@Override
	public MacColumnItem getValueAt(int index) {
		return this;
	}

	@Override
	public int size() {
		return 1;
	}

}
