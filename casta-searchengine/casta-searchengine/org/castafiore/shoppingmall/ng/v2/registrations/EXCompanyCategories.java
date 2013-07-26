package org.castafiore.shoppingmall.ng.v2.registrations;

import java.util.Map;


import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.shoppingmall.ShoppingMall;
import org.castafiore.shoppingmall.ShoppingMallManager;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.iterator.FileIterator;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;

public class EXCompanyCategories extends EXContainer implements Event, EventDispatcher {

	public EXCompanyCategories() {
		super("EXCompanyCategories", "tbody");
		
	}
	
	public void setCompany(Merchant p){
		this.getChildren().clear();
		setRendered(false);
		if(p != null){
			if(StringUtil.isNotEmpty(p.getCategory())){
				addLine(p.getCategory(), p.getSubCategory());
			}
			
			if(StringUtil.isNotEmpty(p.getCategory_1())){
				addLine(p.getCategory_1(), p.getSubCategory_1());
			}
			
			if(StringUtil.isNotEmpty(p.getCategory_2())){
				addLine(p.getCategory_2(), p.getSubCategory_2());
			}
			
			if(StringUtil.isNotEmpty(p.getCategory_3())){
				addLine(p.getCategory_3(), p.getSubCategory_3());
			}
			
			if(StringUtil.isNotEmpty(p.getCategory_4())){
				addLine(p.getCategory_4(), p.getSubCategory_4());
			}
		}
	}
	
	
	public void addRawLine(){
		
		
		Container tr = new EXContainer("", "tr");
		
		ShoppingMall mall = SpringUtil.getBeanOfType(ShoppingMallManager.class).getDefaultMall();
		FileIterator<File> categories = mall.getRootCategory().getFiles();
		DefaultDataModel<Object> catModel = new DefaultDataModel<Object>();
		while(categories.hasNext()){
			catModel.addItem(categories.next().getName());
		}
		
		EXSelect cat = new EXSelect("category",catModel);
		if(catModel.getSize() > 0)
			cat.setValue(catModel.getValue(0));
		cat.addEvent(DISPATCHER, Event.CHANGE);
		
		Container number = new EXContainer("", "td").setText((getChildren().size() +1) + "");
		Container uiCategory = new EXContainer("", "td").addChild(cat);
		Container uisCategory = new EXContainer("", "td").addChild(new EXSelect("subCategory", new DefaultDataModel<Object>()));
		Container eventtd = new EXContainer("", "td").addChild(new EXContainer("img", "img").setAttribute("src", "icons-2/fugue/icons/tick-button.png").addEvent(this, Event.CLICK));
		
		tr.addChild(number);
		tr.addChild(uiCategory);
		tr.addChild(uisCategory);
		tr.addChild(eventtd);
		addChild(tr);
	}
	
	@Override
	public void executeAction(Container source) {
		refreshSubCategory("");
		
	}
	
	private void refreshSubCategory(String newSubCategory){
		ShoppingMall mall = SpringUtil.getBeanOfType(ShoppingMallManager.class).getDefaultMall();
		Directory root = mall.getRootCategory();
		if(((StatefullComponent)getDescendentByName("category")).getValue() != null){
			String category = ((StatefullComponent)getDescendentByName("category")).getValue().toString();
		
			Directory subCategories = (Directory)root.getFile(category);
		 
			DefaultDataModel<Object> model = new DefaultDataModel<Object>();
			FileIterator<File> children = subCategories.getFiles();
			while(children.hasNext()){
				model.addItem(children.next().getName());
			}
			((EXSelect)getDescendentByName("subCategory")).setModel(model);
			
			if(StringUtil.isNotEmpty(newSubCategory)){
				((StatefullComponent)getDescendentByName("subCategory")).setValue(newSubCategory);
			}
		}
	}
	
	
	public void fillMerchant(Merchant p){
		p.setCategory("");
		p.setSubCategory("");
		
		p.setCategory_1("");
		p.setSubCategory_1("");
		
		p.setCategory_2("");
		p.setSubCategory_2("");
		
		p.setCategory_3("");
		p.setSubCategory_3("");
		
		p.setCategory_4("");
		p.setSubCategory_4("");
		
		
		int index = 0;
		for(Container c : getChildren()){
			String cat = c.getChildByIndex(1).getText();
			String scat = c.getChildByIndex(2).getText();
			if(index == 0){
				p.setCategory(cat);
				p.setSubCategory(scat);
			}
			if(index == 1){
				p.setCategory_1(cat);
				p.setSubCategory_1(scat);
			}
			if(index == 2){
				p.setCategory_2(cat);
				p.setSubCategory_2(scat);
			}
			if(index == 3){
				p.setCategory_3(cat);
				p.setSubCategory_3(scat);
			}
			if(index == 4){
				p.setCategory_4(cat);
				p.setSubCategory_4(scat);
			}
			index++;
		}
	}
	
	public void addLine(String category, String sCategory){
		
		EXContainer tr = new EXContainer("", "tr");
		if((getChildren().size() % 2)== 0){
			tr.addClass("even");
		}
		Container number = new EXContainer("", "td").setText((getChildren().size() +1) + "");
		Container uiCategory = new EXContainer("", "td").setText(category);
		Container uisCategory = new EXContainer("", "td").setText(sCategory);
		Container eventtd = new EXContainer("", "td").addChild(new EXContainer("img", "img").setAttribute("src", "icons-2/fugue/icons/minus-button.png").addEvent(this, Event.CLICK));
		
		tr.addChild(number);
		tr.addChild(uiCategory);
		tr.addChild(uisCategory);
		tr.addChild(eventtd);
		addChild(tr);
		
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		Container tr = container.getParent().getParent();
		if(container.getAttribute("src").equalsIgnoreCase("icons-2/fugue/icons/tick-button.png")){
			//int index = Integer.parseInt(tr.getChildByIndex(0).getText());
			String category = tr.getChildByIndex(1).getDescendentOfType(StatefullComponent.class).getValue().toString();
			String sCategory =tr.getChildByIndex(2).getDescendentOfType(StatefullComponent.class).getValue().toString();
			tr.getChildByIndex(1).getChildren().clear();
			tr.getChildByIndex(1).setText(category);
			tr.getChildByIndex(2).getChildren().clear();
			tr.getChildByIndex(2).setText(sCategory);
			container.setAttribute("src", "icons-2/fugue/icons/minus-button.png");
			tr.setRendered(false);
		}else{
		
			
			tr.remove();
			int index = 1;
			for(Container c : getChildren()){
				c.getChildByIndex(0).setText(index + "");
				index++;
			}
			setRendered(false);
		}
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
	
		
	}
	
}
