package org.castafiore.searchengine.back;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.castafiore.searchengine.MallUtil;
import org.castafiore.security.User;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.ui.ex.panel.Panel;
import org.castafiore.wfs.service.QueryParameters;
import org.castafiore.wfs.types.Article;
import org.castafiore.wfs.types.File;
import org.hibernate.criterion.Restrictions;

public class OS extends EXContainer implements OSInterface, Event{
	
	private List<Article> inits;

	public OS(String name, User user) {
		super(name,"div");
		checkAccess(user.getUsername());
		setAttribute("username", user.getUsername());
		addChild(new OSDeskTop("OSDeskTop", user));
		addChild(new OSBar("OSBar"));
		inits = getInits();
		setAttribute("init", "0");
		addChild(new EXContainer("inits", "div").setStyle("width", "0px").setStyle("height", "0px"));
		addEvent(this, READY);
	}
	
	
	private List<Article> getInits(){
		List arts = SpringUtil.getRepositoryService().executeQuery(new QueryParameters().setEntity(Article.class).addRestriction(Restrictions.like("name", "%.cini")), getAttribute("username"));
		if(arts.size() == 0){
			arts = new ArrayList();
			Article art = new Article();
			art.setTitle("com.eliensons.ui.sales.cache.AppFormPanel");
			arts.add(art);
		}
		return arts;
		
	}
	
	public String getCurrency(){
		return MallUtil.getCurrentMerchant().getCurrency();
	}
	
	public String getRemoteUser(){
		return getAttribute("username");
	}
	
	
	public void checkAccess(String username){
		
	}
	
	public Desktop getDeskTop(){
		return getDescendentOfType(OSDeskTop.class);
	}



	@Override
	public void onReady(ClientProxy proxy) {
		super.onReady(proxy);
		proxy.getDescendentByName("OSDeskTop").setStyle("width", ClientProxy.getBrowserWidth()).setStyle("height", ClientProxy.getBrowserHeight());
	}


	@Override
	public void ClientAction(ClientProxy container) {
		if(inits.size() > 0 && !"done".equals(getAttribute("init")))
			container.setTimeout(container.clone().mask("Loading application").makeServerRequest(this), 2000);
		
	}


	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		try{
			String init = container.getAttribute("init");
			if("done".equalsIgnoreCase("init")){
				return true;
			}else{
				int index = Integer.parseInt(init);
				Article a = this.inits.get(index);
				
				Container c = (Container)Thread.currentThread().getContextClassLoader().loadClass(a.getTitle()).newInstance();
				
					getChild("inits").addChild(c);
				
				
				
				
				index = index + 1;
				if(inits.size()>index){
					setAttribute("init", index + "");
					request.put("more", "true");
				}else{
					setAttribute("init", "done");
				}
			}
		}catch(Exception e){
			throw new UIException(e);
		}
		
		return true;
			
		
	}


	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		if(request.containsKey("more")){
			container.setTimeout(container.clone().makeServerRequest(this), 2000);
		}
		
	}

}
