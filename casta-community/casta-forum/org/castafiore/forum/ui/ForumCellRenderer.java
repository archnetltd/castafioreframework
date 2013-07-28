package org.castafiore.forum.ui;

import java.text.SimpleDateFormat;

import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.table.CellRenderer;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.wfs.iterator.FileIterator;
import org.castafiore.wfs.types.Article;

public class ForumCellRenderer implements CellRenderer {

	@Override
	public Container getComponentAt(int row, int column, int page,
			TableModel model, EXTable table) {
		Article article = (Article)model.getValueAt(column, row, page);
		if(column == 0){
			return new EXContainer("", "img").setAttribute("src", "http://www.simpleforum.net/forum/images/post_icons/post_small.gif");
		}else if(column == 1){
			String text= "<font style=\"font-size:10pt\"><b><a href=\"#\">"+article.getTitle()+"</a></b></font><br>"+article.getSummary()+"<br><font class=\"smallfont\"><i>Moderator:  "+article.getOwner()+"</i></font>";
			Container c = new EXContainer("", "div").setText(text);
			return c;
		}else if(column == 2){
			return  new EXContainer("", "span").setText(article.getFiles(Article.class).count() + "");
		}else if(column == 3){
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
		// TODO Auto-generated method stub
		
	}

}
