package org.castafiore.bootstrap.layout;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.InvalidLayoutDataException;
import org.castafiore.ui.Container;
import org.castafiore.ui.EXContainer;
import org.castafiore.ui.LayoutContainer;
import org.castafiore.ui.layout.DroppableSection;
import org.castafiore.utils.StringUtil;

/**
 * Understanding the BTLayout.
 * When constructing a layout, we should pass a string parameter in constructor.<br> 
 * Look at the following examples to better understand
 * 
 * Example 1<br>
 * Layout, with 3 rows<br>
 * 12,12,12<br>
 * The specification above represents a layout with 3 rows, where each row has 1 cell of width 12 units<br>
 * 
 * Example 2<br>
 * A layout with 3 rows, where<br>
 * 1st row is a header with a single column<br>
 * 2nd row has 3 columns. The first and third column are of same size. The second column is 2 times wider than the other 2 columns<br>
 * 3rd row is a footer with a single column<br>
 * 12,3:6:3,12<br>
 * 
 * Example 3<br>
 * A grid with 3 rows and 3 columns of equal size<br>
 * 4:4:4,4:4:4,4:4:4<br>
 * 
 * Example 4<br>
 * A layout with 2 columns of equal width<br>
 * 6:6
 * 
 * 
 * 
 * @author arossaye
 *
 */
public class BTLayout extends EXContainer implements LayoutContainer{
	private String specification;
	
	

	public BTLayout(String name, String specification) {
		super(name, "div");
		this.specification = specification;
		buildIt();
	}
	
	protected void buildIt(){
		addClass("container");
		String[] rows = StringUtil.split(specification, ",");
		
		for(int x =0; x < rows.length; x++){
			Container row = new EXContainer(x + "", "div");
			row.addClass("row");
			addChild(row);
			String s = rows[x];
			String[] cols = StringUtil.split(s, ":");
			for(int y =0; y < cols.length; y++){
				String col = cols[y];
				Container c = new EXContainer(y + "", "div").addClass("col-" + col);
				row.addChild(c);
			}
		}
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
		return getContainer(layoutData).getDescendentByName(layoutData);
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
		int x =0;
		int y =0;
		for(Container c : getChildren()){
			for(Container cc : c.getChildren()){
				DroppableSection section = new DroppableSection(cc.getId(), cc.getName(), "" + x + "," + y);
				sections.add(section);
				x++;
			}
			y++;
		}
		return sections;
	}

	@Override
	public Container getContainer(String layoutData) {
		String[] as = StringUtil.split(layoutData, ",");
		return getChild(as[1]).getChild(as[0]);
	}

	@Override
	public void removeChildFromLayout(String id) {
		getDescendentById(id).remove();
	}

}
