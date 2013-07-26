package org.castafiore.shoppingmall.product.ui.tab;

import java.util.List;
import java.util.Map;

import org.castafiore.KeyValuePair;
import org.castafiore.catalogue.Product;
import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.shoppingmall.ShoppingMall;
import org.castafiore.shoppingmall.ShoppingMallManager;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.iterator.FileIterator;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;
import org.castafiore.wfs.types.Value;

public class EXProductCategory extends EXContainer implements Event, EventDispatcher{

	public EXProductCategory() {
		super("EXProductCategory", "tbody");
		
	}
	
	public void setProduct(Product p){
		this.getChildren().clear();
		setRendered(false);
		
		if(p != null){
			List<KeyValuePair> kvs = p.getCategories();
			
			for(KeyValuePair kv : kvs){
				addLine(kv.getKey(), kv.getValue());
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
		
		File first = categories.get(0);
		Container uisCategory = new EXContainer("", "td");
		if(!(first instanceof Directory)|| ((Directory)first).getFiles().toList().size() ==0){
			EXInput input = new EXInput("subCategory");
			uisCategory.addChild(input);
		}else{
			DefaultDataModel<Object> model = new DefaultDataModel<Object>();
			FileIterator<File> children = ((Directory)(first)).getFiles();
			while(children.hasNext()){
				model.addItem(children.next().getName());
			}
			uisCategory.addChild(new EXSelect("subCategory", model));
		}
		
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
			FileIterator<File> children = subCategories.getFiles();
			if(children.count() > 0){
				DefaultDataModel<Object> model = new DefaultDataModel<Object>();
				
				while(children.hasNext()){
					model.addItem(children.next().getName());
				}
				
				if(getDescendentByName("subCategory") instanceof EXSelect)
					((EXSelect)getDescendentByName("subCategory")).setModel(model);
				else{
					Container parent = getDescendentByName("subCategory").getParent();
					getDescendentByName("subCategory").remove();
					parent.addChild(new EXSelect("subCategory",model));
				}
			}else{
				if(getDescendentByName("subCategory") instanceof EXSelect){
					Container parent = getDescendentByName("subCategory").getParent();
					getDescendentByName("subCategory").remove();
					parent.addChild(new EXInput("subCategory"));
				}else{
					
				}
			}
			
			
			if(StringUtil.isNotEmpty(newSubCategory)){
				((StatefullComponent)getDescendentByName("subCategory")).setValue(newSubCategory);
			}
		}
	}
	
	
	public void fillProduct(Product p){
		p.setCategory(null);
		p.setSubCategory(null);
		
		p.setCategory_1(null);
		p.setSubCategory_1(null);
		
		p.setCategory_2(null);
		p.setSubCategory_2(null);
		
		p.setCategory_3(null);
		p.setSubCategory_3(null);
		
		p.setCategory_4(null);
		p.setSubCategory_4(null);
		List<Value> properties = p.getFiles(Value.class).toList();
		for(Value val : properties){
			if(val.getName().contains("category_")){
				val.remove();
			}
		}
		p.save();
		
		int index = 0;
		for(Container c : getChildren()){
			if((c.getChildByIndex(1)).getDescendentOfType(StatefullComponent.class) != null)
				continue;
			
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
			
			if(index > 4){
				p.addCategory(cat, scat);
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
