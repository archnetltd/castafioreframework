package org.castafiore.forum.ui;

import org.castafiore.ecm.ui.AbstractFilesModel;
import org.castafiore.wfs.service.QueryParameters;
import org.castafiore.wfs.types.Article;
import org.hibernate.criterion.Restrictions;

public class ForumTopicsTableModel extends AbstractFilesModel{
	
	private final static String[] labels = new String[]{"", "Topic", "Replies", "Author",  "Latest posts"};
	
	private Article forum ;
	
	public ForumTopicsTableModel(Article forum) {
		super();
		this.forum = forum;
	}

	@Override
	public String[] getColumns() {
		return labels;
	}

	@Override
	public QueryParameters getParams() {
		return new QueryParameters().setEntity(Article.class).addRestriction(Restrictions.eq("parent.absolutePath", forum.getAbsolutePath()));
	}
}
