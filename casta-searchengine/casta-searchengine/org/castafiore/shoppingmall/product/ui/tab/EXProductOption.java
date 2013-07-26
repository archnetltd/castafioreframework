package org.castafiore.shoppingmall.product.ui.tab;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.castafiore.catalogue.Product;
import org.castafiore.catalogue.ProductOption;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXInput;

public class EXProductOption extends EXContainer implements Event{
	public EXProductOption() {
		super("EXProductOption", "tbody");
		
	}
	
	public void setProduct(Product product){
		this.getChildren().clear();
		setRendered(false);
		if(product != null){
			List<ProductOption> chara= product.getCharacteristics();
			
			for(ProductOption m : chara){
				addLine(m.getName(), m.getString(), m.getOverhead());
			}
		}
	}
	
	
	public void addRawLine(){
		if(getChildren().size() >= 5){
			getAncestorOfType(EXCategories.class).setError("A maximum of 5 categories is allowed");
			return;
		}
		Container tr = new EXContainer("", "tr");

		Container number = new EXContainer("", "td").setText((getChildren().size() +1) + "");
		Container uiName = new EXContainer("", "td").addChild(new EXInput("name"));
		Container uiValue = new EXContainer("", "td").addChild(new EXInput("value"));
		Container uiOverhead = new EXContainer("", "td").addChild(new EXInput("overhead"));
		Container eventtd = new EXContainer("", "td").addChild(new EXContainer("img", "img").setAttribute("src", "icons-2/fugue/icons/tick-button.png").addEvent(this, Event.CLICK));
		
		tr.addChild(number);
		tr.addChild(uiName);
		tr.addChild(uiValue);
		tr.addChild(uiOverhead);
		tr.addChild(eventtd);
		addChild(tr);
	}
	
	public void fillProduct(Product p){
		
		for(Container c : getChildren()){
			String name = c.getChildByIndex(1).getText();
			String value = c.getChildByIndex(2).getText();
			String sOverhead = c.getChildByIndex(3).getText();
			p.createCharacteristics(name, value, new BigDecimal(sOverhead));
		}
	}
	
	public void addLine(String name, String value, BigDecimal overhead){
		
		EXContainer tr = new EXContainer("", "tr");
		if((getChildren().size() % 2)== 0){
			tr.addClass("even");
		}
		Container number = new EXContainer("", "td").setText((getChildren().size() +1) + "");
		Container uiName = new EXContainer("", "td").setText(name);
		Container uiValue = new EXContainer("", "td").setText(value);
		Container uiOverhead = new EXContainer("", "td").setText(overhead.toPlainString());
		Container eventtd = new EXContainer("", "td").addChild(new EXContainer("img", "img").setAttribute("src", "icons-2/fugue/icons/minus-button.png").addEvent(this, Event.CLICK));
		
		tr.addChild(number);
		tr.addChild(uiName);
		tr.addChild(uiValue);
		tr.addChild(uiOverhead);
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
			String name = tr.getChildByIndex(1).getDescendentOfType(StatefullComponent.class).getValue().toString();
			String value =tr.getChildByIndex(2).getDescendentOfType(StatefullComponent.class).getValue().toString();
			String overhead =tr.getChildByIndex(3).getDescendentOfType(StatefullComponent.class).getValue().toString();
			tr.getChildByIndex(1).getChildren().clear();
			tr.getChildByIndex(1).setText(name);
			tr.getChildByIndex(2).getChildren().clear();
			tr.getChildByIndex(2).setText(value);
			tr.getChildByIndex(3).getChildren().clear();
			tr.getChildByIndex(3).setText(overhead);
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
