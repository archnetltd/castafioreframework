package org.castafiore.ui.layout;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.InvalidLayoutDataException;
import org.castafiore.ui.Container;
import org.castafiore.ui.EXGrid;
import org.castafiore.ui.LayoutContainer;
import org.castafiore.ui.UIException;
import org.castafiore.utils.StringUtil;

public class EXGridLayout extends EXGrid implements LayoutContainer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EXGridLayout(String name, int cols, int rows) {
		super(name, cols, rows);
	}

	@Override
	public void addChild(Container child, String layoutData) {
		getContainer(layoutData).addChild(child);
		
	}

	@Override
	public List<Container> getChildren(String layoutData) {
		return getContainer(layoutData).getChildren();
	}

	@Override
	public Container getChild(String name, String layoutData) {
		return getContainer(layoutData).getChild(name);
	}

	@Override
	public Container getDescendentOfType(Class<? extends Container> type,
			String layoutData) {
		return getContainer(layoutData).getDescendentOfType(type);
	}

	@Override
	public Container getDescendentByName(String name, String layoutData) {
		return getContainer(layoutData).getDescendentByName(name);
	}

	@Override
	public Container getDescendentById(String id, String layoutData) {
		return getContainer(layoutData).getDescendentById(id);
	}

	@Override
	public void validateLayoutData(String layoutData)
			throws InvalidLayoutDataException {
		
	}

	@Override
	public List<DroppableSection> getSections() {
		List<DroppableSection> sections = new ArrayList<DroppableSection>();
		for(int i = 0; i < getColumns();i++){
			for(int j =0; j < getRows(); j++){
				Container c = getCell(i, j);
				DroppableSection s = new DroppableSection(c.getId(), i + ":" + j, i + ":" + j);
				sections.add(s);
			}
		}
		return sections;
	}

	@Override
	public Container getContainer(String layoutData) {
		try{
			String[] as = StringUtil.split(layoutData, ":");
			int col = Integer.parseInt(as[0]);
			int row = Integer.parseInt(as[1]);
			return getCell(col, row);
		}catch(Exception e){
			throw new UIException("The layout data " + layoutData + " for " + getClass().getName() + " is incorrect. Should be in form <col>:<row> e.g. 0:0 or 0:1 etc..");
		}
	}

	@Override
	public void removeChildFromLayout(String id) {
		getDescendentById(id).remove();
	}

}
