package org.castafiore.splashy.templates;

import java.text.SimpleDateFormat;
import java.util.Map;

import org.castafiore.catalogue.Product;
import org.castafiore.designer.marshalling.DesignableUtil;
import org.castafiore.designer.toolbar.menu.EXTemplatesWindow;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.EXWebServletAwareApplication;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.Article;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;

public class EXTemplateItem extends EXXHTMLFragment implements Event{
/*
 * <a  href="">
 * 	<header>
 * 		<h3>Spikes in Bloom</h3>
 * 		<ul>
 * 			<li class="downloads">140</li>
 *		</ul>
 *	</header>
 *	<img alt="" src="/images/thumbnails/313357164">
 *</a>
 */
	public EXTemplateItem(String name, Product article) {
		super(name, ResourceUtil.getDownloadURL("classpath", "org/castafiore/splashy/templates/EXTemplateItem.xhtml"));
		addClass("template-item grid_3");
		setAttribute("href", "#");
		//Container header = new EXContainer("header", "header");
		//addChild(header);
		addChild(new EXContainer("title", "h3").setText("Template title"));
		addChild(new EXContainer("added", "span").setText("12.02.2008"));
		addChild(new EXContainer("provider", "a").setAttribute("href", "http://www.archnetltd.com").setText("ArchNet ltd"));
		addChild(new EXContainer("img", "img").setAttribute("width", "150px").setAttribute("height", "150px").setAttribute("src", "http://www.freecsstemplates.org/download/thumbnail/stabilize/"));
		addChild(new EXContainer("preview", "a").setText("Preview").setAttribute("href", "#pre").addEvent(this, CLICK));
		addChild(new EXContainer("edit", "a").setText("Select").setAttribute("href", "#ed").addEvent(this, CLICK));
		
		if(article != null)
			setTemplate(article);
	}
	
	public void setTemplate(Product art){
		setAttribute("path", art.getAbsolutePath());
		getDescendentByName("title").setText(art.getTitle());
		getDescendentByName("img").setAttribute("src", art.getImageUrl("") );
		getDescendentByName("added").setText(new SimpleDateFormat("dd.MM.yyyy").format(art.getDateCreated().getTime()));
		getDescendentByName("provider").setText(art.getCategory("Provider").getValue());
	}
	
	

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		try{
			Product prod =(Product)SpringUtil.getRepositoryService().getFile(getAttribute("path"), Util.getRemoteUser());
			
			if(getAncestorOfType(EXTemplatesWindow.class) != null){
				getAncestorOfType(EXTemplatesWindow.class).setAttribute("selectedTemplate", prod.getAbsolutePath());
				
				
				for(Container c : getParent().getParent().getChildren()){
					c.removeClass("selectedTemplate");
				}
				getParent().addClass("selectedTemplate");
				
			}else{
			
				getAncestorOfType(EXWebServletAwareApplication.class).getRequest().getSession().setAttribute("selectedTemplate", prod.getAbsolutePath());
				if(container.getName().equalsIgnoreCase("edit")){
					getAncestorOfType(EXSplashyTemplatesApplication.class).edit(prod);
				}else{
					getAncestorOfType(EXSplashyTemplatesApplication.class).preview(prod);
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
		// TODO Auto-generated method stub
		
	}
	

}
