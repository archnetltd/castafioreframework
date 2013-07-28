package org.castafiore.shoppingmall.message.ui;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.shoppingmall.list.AbstractListItem;
import org.castafiore.shoppingmall.user.ui.EXUserInfoLink;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXCheckBox;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.ComponentVisitor;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.Article;
import org.castafiore.wfs.types.Comment;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;
import org.castafiore.wfs.types.Message;

public class EXMessageListItem extends AbstractListItem implements EventDispatcher, ComponentVisitor{
	
	private boolean open = false;

	public EXMessageListItem(String name) {
		super(name);
		
		addChild(new EXContainer("td_from", "td").setStyle("vertical-align","top" ).addChild(new EXUserInfoLink("author").setStyle("color", "green")));
		addChild(new EXContainer("td_title", "td").setStyle("vertical-align","top" ).addChild(new EXContainer("title","a").addEvent(DISPATCHER, Event.CLICK).setAttribute("href", "#").setText("Thank you for your purchase").setStyle("font-weight", "bold").setStyle("color", "#111")));
		addChild(new EXContainer("date", "td").setStyle("vertical-align","top" ).setText(new SimpleDateFormat("dd MMM yyyy").format(new Date())));
		
	}
	
	
	public Comment getItem(){
		Comment comment = (Comment)SpringUtil.getRepositoryService().getFile(getAttribute("path"), Util.getRemoteUser());
		
		return comment;
	}
	
	public void open(){
		if(!open){
			EXMessageList list = getAncestorOfType(EXMessageList.class);
			ComponentUtil.iterateOverDescendentsOfType(list, EXMessageListItem.class,this);
			EXUserInfoLink from  = getDescendentOfType(EXUserInfoLink.class);
			Container title = getDescendentByName("td_title");
			Container userImg = getDescendentByName("userimg");
			EXMessageListDetail detail = getDescendentOfType(EXMessageListDetail.class);
			if(userImg == null){
				userImg = new EXContainer("userimg", "img").setAttribute("style", "width: 60px; height: 60px; margin-top: 0.8em;display:block").setAttribute("src", "http://www.space.com/common/forums/images/avatars/gallery/All/Avatar_gear.jpg");
				from.getParent().addChild(userImg);
			}
			userImg.setDisplay(true);
			
			if(detail == null){
				Comment comment = getItem();
				if(comment.getStatus() == Article.STATE_DRAFT){
					EXNewMessagePopup popup = new EXNewMessagePopup("");
					popup.init((Message)comment);
					getAncestorOfType(PopupContainer.class).addPopup(popup);
					return;
				}else{
					detail = new EXMessageListDetail("listDetail");
					detail.setComment(getItem());
					title.addChild(detail);
				}
			}
			detail.setDisplay(true);
			open = true;
		}
	}
	
	public void toggle(){
		if(open){
			close();
		}else{
			open();
		}
	}
	
	public void close(){
		if(open){
			Container userImg = getDescendentByName("userimg");
			if(userImg != null){
				userImg.setDisplay(false);
			}
			EXMessageListDetail detail = getDescendentOfType(EXMessageListDetail.class);
			if(detail != null){
				detail.setDisplay(false);
			}
			open = false;
		}
		
	}
	

	
	
	
	public void setRecievedMessage(Message message){
		setComment(message);
	}

	
	public void setSentMessage(Message message){

		setComment(message);
		getDescendentOfType(EXUserInfoLink.class).setUsername(message.getDestination());
	}
	
	public void setComment(Comment comment){
		setAttribute("path", comment.getAbsolutePath());
		getDescendentOfType(EXUserInfoLink.class).setUsername(comment.getAuthor());
		getDescendentByName("title").setText(comment.getTitle());
		getDescendentByName("date").setText(new SimpleDateFormat("dd MMM yyyy").format(comment.getDateCreated().getTime()));
	}


	public void delete(){
		File f = SpringUtil.getRepositoryService().getFile(getAttribute("path"), Util.getRemoteUser());
		Directory parent = f.getParent();
		f.remove();
		parent.save();
	}
	
	
	public boolean isChecked(){
		return getDescendentOfType(EXCheckBox.class).isChecked();
	}


	@Override
	public void doVisit(Container c) {
		if(c.getId().equals(getId())){
			
		}else{
			((EXMessageListItem)c).close();
		}
		
	}


	@Override
	public void executeAction(Container source) {
		// TODO Auto-generated method stub
		toggle();
	}
}
