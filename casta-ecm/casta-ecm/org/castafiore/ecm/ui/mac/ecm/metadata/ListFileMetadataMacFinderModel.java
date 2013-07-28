package org.castafiore.ecm.ui.mac.ecm.metadata;

import java.util.Set;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.mac.item.EXMacLabel;
import org.castafiore.ui.mac.item.MacColumnItem;
import org.castafiore.ui.mac.renderer.MacFinderColumnViewModel;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.Metadata;

public class ListFileMetadataMacFinderModel implements MacFinderColumnViewModel{

	private Metadata[] metadata;
	
	public ListFileMetadataMacFinderModel(String filePath) {
		super();
		
		Set<Metadata> m = SpringUtil.getRepositoryService().getFile(filePath, Util.getRemoteUser()).getMetadata();
		metadata = m.toArray(new Metadata[m.size()]);
	}

	@Override
	public MacColumnItem getValueAt(int index) {
		Metadata met = metadata[index];
		EXMacLabel label = new EXMacLabel("", met.getName());
		label.setRightItem(new EXContainer("", "span").addClass("ui-icon-triangle-1-e"));
		label.setLeftItem(new EXContainer("", "span").addClass("ui-icon-document"));
		return label;
	}

	@Override
	public int size() {
		return metadata.length;
	}

}
