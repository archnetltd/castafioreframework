package org.castafiore.forum.ui;

import java.text.SimpleDateFormat;

import org.castafiore.security.User;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.wfs.types.Article;

public class EXTopicEntry extends EXXHTMLFragment{

	public EXTopicEntry(String name) {
		super(name, "templates/forum/EXTopicEntry.xhtml");
		addChild(new EXContainer("user", "label"));
		addChild(new EXContainer("avatar", "img"));
		addChild(new EXContainer("title", "h3"));
		addChild(new EXContainer("summary", "p"));
		addChild(new EXContainer("datePosted", "label"));
		
		
		
		
	}
	
	
	public void setTopicEntry(Article entry){
		User u = SpringUtil.getSecurityService().loadUserByUsername(entry.getOwner());
		String avatar =u.getAvatar();
		getChild("avatar").setAttribute("src", avatar);
		getChild("title").setText(entry.getTitle());
		getChild("summary").setText(entry.getSummary());
		getChild("datePosted").setText("Posted on " + new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(entry.getDateCreated().getTime()));
		
	}

}
