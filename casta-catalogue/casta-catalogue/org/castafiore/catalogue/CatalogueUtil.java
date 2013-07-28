package org.castafiore.catalogue;

import java.util.List;
import java.util.Map;

import org.castafiore.KeyValuePair;
import org.castafiore.designer.Studio;
import org.castafiore.designer.designable.datarepeater.EXDataRepeater;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.dynaform.EXFieldSet;
import org.castafiore.ui.ex.form.EXLabel;
import org.castafiore.utils.StringUtil;

public class CatalogueUtil {

	public static void addFilterProductByCategoryEvent(Container me, String category, String subCategory, String organization,final Container page){
		
		
		me.setAttribute("organization", organization).setAttribute("category", category).setAttribute("subcategory", subCategory).addEvent(new Event() {
			
			@Override
			public void Success(ClientProxy container, Map<String, String> request)
					throws UIException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean ServerAction(Container container, Map<String, String> request)
					throws UIException {
				
				String cat = container.getAttribute("category");
				String sub = container.getAttribute("subcategory");
				String org = container.getAttribute("organization");
				EXDataRepeater r = page.getDescendentOfType(EXDataRepeater.class);
				List<Product> pds = SpringUtil.getBeanOfType(CatalogueService.class).getProductsWithCategoryAndSubCategory(cat, sub, org);
				r.setData(pds);
				return true;
			}
			
			@Override
			public void ClientAction(ClientProxy container) {
				container.mask().makeServerRequest(this);
				
			}
		}, Event.CLICK);
	}
	
	
	public static void filterProducts( String cat, String sub, String org,final Container page){
		EXDataRepeater r = page.getDescendentOfType(EXDataRepeater.class);
		List<Product> pds = SpringUtil.getBeanOfType(CatalogueService.class).getProductsWithCategoryAndSubCategory(cat, sub, org);
		r.setData(pds);
	}
	
	
	public static void openProductDetail(Container me, String pageName, String productPath){
		String id = Studio.goToPage(pageName, me);
		Product p = (Product)SpringUtil.getRepositoryService().getFile(productPath, "profocus");
		Container page = me.getRoot().getDescendentById(id);
		page.getDescendentByName("image").setAttribute("src", p.getImageUrl(""));
		page.getDescendentByName("title").setText(p.getTitle());
		page.getDescendentByName("detail").setText(p.getSummary());
		Container categories = page.getDescendentByName("categoriesTable");
				
		Container parent = categories.getParent();
		categories.remove();

		EXFieldSet fs = new EXFieldSet("categoriesTable", "More Info", false);
		parent.addChild(fs);
		List<KeyValuePair> catts = p.getCategories();
		for(KeyValuePair kv : catts){
			if(StringUtil.isNotEmpty(kv.getKey()) && StringUtil.isNotEmpty(kv.getValue())){
				fs.addField(kv.getKey(), new EXLabel(kv.getKey(), kv.getValue()));
			}
		}
		
		
	}
	
	
	
	
}
