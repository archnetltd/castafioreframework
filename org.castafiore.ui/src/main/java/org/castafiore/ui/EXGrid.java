/*
 * Copyright (C) 2007-2008 Castafiore
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

package org.castafiore.ui;

import java.util.List;

/**
 * Simple class to create a grid
 * 
 * This class is not a table, but simply rendered a grid
 * 
 * 
 * 
 * @author Kureem Rossaye<br>
 *         kureem@gmail.com Oct 22, 2008
 */
public class EXGrid extends EXContainer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int columns;
	protected int rows;

	/**
	 * Contructor to create a grid with specified number of columns and
	 * specified number of rows
	 * 
	 * @param name
	 *            - The name of the grid
	 * @param cols
	 *            - The number of columns in the grid
	 * @param rows
	 *            - The number of rows in the grid
	 */
	public EXGrid(String name, int cols, int rows) {
		super(name, "table");
		this.columns = cols;
		this.rows = rows;
		setStyle("padding", "0").setStyle("margin", "0");
		refresh();
	}

	/**
	 * Re-renders the grid
	 */
	@Override
	public void refresh() {
		this.getChildren().clear();
		for (int i = 0; i < rows; i++) {
			EXContainer tr = new EXContainer("", "tr");

			for (int j = 0; j < columns; j++) {
				Container td = new EXContainer("", "td").setStyle("padding", "0").setStyle("margin", "0");
				tr.addChild(td);
			}
			addChild(tr);
		}

		this.setRendered(false);

	}

	/**
	 * add an empty column at the end of the grid
	 * 
	 */
	public void addColumn() {
		for (Container row : this.getChildren()) {
			EXContainer col = new EXContainer("", "td");
			row.addChild(col);
		}
		this.columns++;
	}

	/**
	 * adds a component in the specifed column and specified row of the grid
	 * 
	 * @param col
	 *            - The column in which to add the component
	 * @param row
	 *            - The row in which to add the component
	 * @param component
	 *            - The component to add
	 */
	public void addInCell(int col, int row, Container component) {
		this.getChildByIndex(row).getChildByIndex(col).addChild(component);
	}

	/**
	 * returns all children in a specified cell
	 * 
	 * @param col
	 *            - The column of the cell
	 * @param row
	 *            - The row of the cell
	 * @return - The containers in the cell
	 */
	public List<Container> getChildrenInCell(int col, int row) {
		return this.getChildByIndex(row).getChildByIndex(col).getChildren();
	}

	/**
	 * clear all children in the specified cell
	 * 
	 * @param col
	 *            - The column of the cell
	 * @param row
	 *            - The row of the cell
	 */
	public void clearCell(int col, int row) {
		this.getChildByIndex(row).getChildByIndex(col).getChildren().clear();
		this.getChildByIndex(row).getChildByIndex(col).setRendered(false);

	}

	/**
	 * The the cell that meets the specified row and column
	 * 
	 * @param col
	 *            - The column of the cell
	 * @param row
	 *            - The row of the cell
	 * @return The cell
	 */
	public EXContainer getCell(int col, int row) {
		return (EXContainer) this.getChildByIndex(row).getChildByIndex(col);
	}

	/**
	 * adds a row at the end of the grid
	 * 
	 */
	public EXRow addRow() {
		rows++;

		EXRow tr = new EXRow("");

		for (int j = 0; j < columns; j++) {
			EXContainer td = new EXContainer("", "td");
			tr.addChild(td);
		}
		addChild(tr);
		return tr;
	}

	/**
	 * adds a row at the specified position of the grid
	 * 
	 * @param index
	 *            - The first row should have index 0
	 * @return
	 */
	public EXContainer addRowAt(int index) {

		rows++;
		EXContainer tr = new EXContainer("", "tr");

		for (int j = 0; j < columns; j++) {
			EXContainer td = new EXContainer("", "td");
			tr.addChild(td);
		}
		if (index == -1) {
			addChild(tr);
		} else {
			addChildAt(tr, index);
		}

		return tr;
	}

	/**
	 * returns the number of rows in the grid
	 * 
	 * @return
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * returns the number of columns in the grid
	 * 
	 * @return
	 */
	public int getColumns() {
		return columns;
	}

	public class EXRow extends EXContainer {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public EXRow(String name) {
			super(name, "tr");

		}

		public EXRow addInCell(int index, Container c) {
			getChildByIndex(index).addChild(c);
			return this;
		}

	}

}
