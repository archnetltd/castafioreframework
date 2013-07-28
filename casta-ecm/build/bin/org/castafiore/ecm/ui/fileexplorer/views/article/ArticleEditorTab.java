package org.castafiore.ecm.ui.fileexplorer.views.article;


import org.castafiore.ui.Container;
import org.castafiore.wfs.types.Article;

public interface ArticleEditorTab extends Container{
	
	public void save(Article art, boolean isNew);
	
	public void init(Article art, boolean isNew);

}
