package org.castafiore.forum.ui;

import java.text.SimpleDateFormat;

import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.table.CellRenderer;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.wfs.iterator.FileIterator;
import org.castafiore.wfs.types.Article;

public class ForumTopicsCellRenderer implements CellRenderer {

	@Override
	public Container getComponentAt(int row, int column, int page,
			TableModel model, EXTable table) {
		Article article = (Article)model.getValueAt(column, row, page);
		if(column == 0){
			return new EXContainer("", "img").setAttribute("src", "http://www.simpleforum.net/forum/images/post_icons/post_small.gif");
		}else if(column == 1){

			return new EXContainer("", "a").setAttribute("href", "y").setText(article.getTitle());
		}else if(column == 2){
			return  new EXContainer("", "span").setText(article.getFiles(Article.class).count() + "");
		}else if(column == 3){
			return  new EXContainer("", "span").setText(article.getOwner());
		}else if(column == 4){
			FileIterator<Article> files = article.getFiles(Article.class);
			if(files.count() > 0){
				Article ar = files.get(0);
				String date=new SimpleDateFormat("dd MMM yyyy @ hh:mm").format(ar.getDateCreated().getTime());
				date = date + "<br>by <a href=\"#\">"+ar.getOwner()+"</a>";
				Container c = new EXContainer("", "div").setText(date);
				return c;
			}else{
				Container c = new EXContainer("", "div");
				return c;
			}
			
		}else{
			return new EXContainer("", "div");
		}
	}

	@Override
	public void onChangePage(Container component, int row, int column,
			int page, TableModel model, EXTable table) {
		Article article = (Article)model.getValueAt(column, row, page);
		if(column == 0){
			 component.setAttribute("src", "http://www.simpleforum.net/forum/images/post_icons/post_small.gif");
		}else if(column == 1){

			component.setText(article.getTitle());
		}else if(column == 2){
			component.setText(article.getFiles(Article.class).count() + "");
		}else if(column == 3){
			component.setText(article.getOwner());
		}else if(column == 4){
			FileIterator<Article> files = article.getFiles(Article.class);
			if(files.count() > 0){
				Article ar = files.get(0);
				String date=new SimpleDateFormat("dd MMM yyyy @ hh:mm").format(ar.getDateCreated().getTime());
				date = date + "<br>by <a href=\"#\">"+ar.getOwner()+"</a>";
				component.setText(date);
				
			}else{
				component.setText("");
			}
			
		}
		
	}

}
