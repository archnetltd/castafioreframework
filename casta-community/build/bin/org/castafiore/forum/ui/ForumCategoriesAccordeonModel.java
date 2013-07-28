package org.castafiore.forum.ui;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.tabbedpane.TabModel;
import org.castafiore.ui.tabbedpane.TabPanel;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.types.Article;
import org.castafiore.wfs.types.Directory;

public class ForumCategoriesAccordeonModel implements TabModel {
	
	private String rootDirectory;
	
	private List<Article> forums = new ArrayList<Article>();
	
	private void init(){
		RepositoryService service = SpringUtil.getRepositoryService();
		Directory dir = service.getDirectory(rootDirectory, Util.getRemoteUser());
		forums = dir.getFiles(Article.class).toList();
	}
	
	
	
	
	

	public ForumCategoriesAccordeonModel(String rootDirectory) {
		super();
		this.rootDirectory = rootDirectory;
		init();
	}

	public ForumCategoriesAccordeonModel(List<Article> forums) {
		super();
		this.forums = forums;
	}


	@Override
	public int getSelectedTab() {
		return 0;
	}

	@Override
	public Container getTabContentAt(TabPanel pane, int index) {
		Article article = forums.get(index);
		//List<Article> fs = article.getFiles(Article.class).toList();
		
		EXTable table = new EXTable("", new ForumTableModel(article));
		table.setCellRenderer(new ForumCellRenderer());
		return new EXContainer("", "div").addChild(table);
		
	}

	@Override
	public String getTabLabelAt(TabPanel pane, int index, boolean selected) {
		return forums.get(index).getTitle();
	}

	@Override
	public int size() {
		return forums.size();
	}

}
