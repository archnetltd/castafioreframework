import java.util.List;

import org.castafiore.catalogue.Product;
import org.castafiore.designer.designable.datarepeater.EXDataRepeater;
import org.castafiore.designer.designable.table.JSONTableModel;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.ui.Container;
import org.json.JSONObject;



public class pp {

	/*public void ww(Container me) {
		Directory d = SpringUtil.getRepositoryService().getDirectory(
				"/root/users/phoenixCarGallery/AboutUs", "phoenixCarGallery");
		List<BinaryFile> l = d.getFiles(BinaryFile.class).toList();
		BinaryFile bf = l.get(0);
		List<Article> arts = d.getFiles(Article.class).toList();
		Article art = arts.get(0);
		String img = ResourceUtil.getDownloadURL("ecm", bf.getAbsolutePath());
		String s = "<h1>" + art.getTitle() + "</h1><img src=\"" + img + "\">"
				+ art.getSummary();
		me.setText(s);

		me.getEvents().clear();
	}*/
	
	
	public void sd(Container me)throws Exception{
		me.getChildren().clear();
		Container catalogue = me.getRoot().getDescendentByName("catalogue");
List<Product> ps = MallUtil.getMerchant("phoenixCarGallery").getManager().getMyProducts(Product.STATE_PUBLISHED);
String s = "";
for(Product p : ps){
	if(s.length() == 0)
		s = p.getAbsolutePath();
	else
		s = s + ";" + p.getAbsolutePath();
}

System.out.println(s);
JSONObject object = new JSONObject();
object.put("type", "Files");
object.put("entitytype", Product.class.getName());
object.put("value", s);
EXDataRepeater repeater = catalogue.getDescendentOfType(EXDataRepeater.class);
repeater.setModel(new JSONTableModel(null, object));
repeater.refresh();

//				Studio.goToPage("productDetail", me);
//				Product p = MallUtil.getCurrentMall().getProduct(me.getAttribute("path"));
//				Container page = me.getRoot().getDescendentOfType(PageContainer.class).getDescendentByName("productDetail");
//				page.getDescendentByName("title").setText(p.getTitle());
//				page.getDescendentByName("price").setText(StringUtil.toCurrency("MUR",p.getTotalPrice() ));
//				page.getDescendentByName("detail").setText(p.getSummary());
//				
//				page.getDescendentByName("type").setText(p.getSubCategory());
//				page.getDescendentByName("make").setText(p.getSubCategory_1());
//				page.getDescendentByName("engine").setText(p.getSubCategory_2());
//				page.getDescendentByName("year").setText(p.getSubCategory_3());
//				page.getDescendentByName("color").setText(p.getSubCategory_4());
//
//				page.getDescendentByName("categ1").setText(p.getCategory());
//				page.getDescendentByName("categ2").setText(p.getCategory_1());
//				page.getDescendentByName("categ3").setText(p.getCategory_2());
//				page.getDescendentByName("categ4").setText(p.getCategory_3());
//				page.getDescendentByName("categ5").setText(p.getCategory_4());
//
//				Container imgs = page.getDescendentByName("imgs");
//				
//				List<Link> ii = p.getImages().toList();
//				for(Link l : ii){
//					Container span = new EXContainer("", "span").addClass("image-border");
//					Container a = new EXContainer("",  "a").setAttribute("href", l.getUrl()).setAttribute("rel", "prettyPhoto[gallery]").addClass("image-wrap");
//					span.addChild(a);
//					imgs.addChild(span);
//					a.addChild(new EXContainer("", "img").setAttribute("src", l.getUrl()).setAttribute("width", "200px").setAttribute("height", "120px"));
//				}


//		Studio.goToPage("productDetail", me);
//		Product p = MallUtil.getCurrentMall().getProduct(me.getAttribute("path"));
//		Container page = me.getRoot().getDescendentOfType(PageContainer.class).getDescendentByName("productDetail");
//		page.getDescendentByName("title").setText(p.getTitle());
//		page.getDescendentByName("price").setText(StringUtil.toCurrency("MUR",p.getTotalPrice() ));
//		page.getDescendentByName("detail").setText(p.getSummary());
//		page.getDescendentByName("img").setAttribute("src", p.getImageUrl(""));
//		page.getDescendentByName("type").setText(p.getSubCategory());
//		page.getDescendentByName("make").setText(p.getSubCategory_1());
//		page.getDescendentByName("engine").setText(p.getSubCategory_2());
//		page.getDescendentByName("year").setText(p.getSubCategory_3());
//		page.getDescendentByName("color").setText(p.getSubCategory_4());
//
//		page.getDescendentByName("categ1").setText(p.getCategory());
//		page.getDescendentByName("categ2").setText(p.getCategory_1());
//		page.getDescendentByName("categ3").setText(p.getCategory_2());
//		page.getDescendentByName("categ4").setText(p.getCategory_3());
//		page.getDescendentByName("categ5").setText(p.getCategory_4());
//
//		Container imgs = page.getDescendentByName("imgs");
//		
//		List<Link> ii = p.getImages().toList();
//		for(Link l : ii){
//			imgs.addChild(new EXContainer("", "img").setAttribute("src", l.getUrl()).setStyle("width", "200px").setStyle("height", "120px"));
//		}
		
		
//		Container catalogue = me.getRoot().getDescendentByName("catalogue");
//		try {
//			JSONObject object = new JSONObject();
//			object.put("type", "Directory");
//			object.put("entitytype", Product.class.getName());
//			object.put("value", "/root/users/phoenixCarGallery");
//			EXDataRepeater repeater = catalogue.getDescendentOfType(EXDataRepeater.class);
//			repeater.setModel(new JSONTableModel(null, object));
//			repeater.refresh();
//		} catch (Exception e) {
//			throw new UIException(e);
//		}
	}

	/*public void fs(Container me) {
		Directory dir = SpringUtil.getRepositoryService().getDirectory("/root/users/phoenixCarGallery/categories/Types","phoenixCarGallery");

		for (File f : dir.getFiles().toList()) {
			Container c = new EXContainer("", "div").setAttribute("style","border-bottom: 1px solid rgb(199, 199, 199); border-top: 1px none rgb(199, 199, 199); height: 30px; width: 250px;cursor:pointer");
			me.addChild(c);
			c.setAttribute("category", f.getName());
			c.setText("<p style='margin-right: 0px; padding-top: 3px; text-indent: 34px; text-align: left; color: rgb(87, 87, 80); font-size: 17px; margin-left: 0px; margin-bottom: 0px; font-family: Helvetica; font-weight: bold;'>"
							+ f.getName() + "</p>");
			c.setAttribute("path", f.getAbsolutePath());
			c.addEvent(new Event() {

				public void ClientAction(ClientProxy container) {
					container.mask().makeServerRequest(this);

				}

				public boolean ServerAction(Container container,
						Map<String, String> request) throws UIException {

					Studio.goToPage("gallery", container);
					Container catalogue = container.getRoot().getDescendentByName("catalogue");
					String name = "Type";
					String value = container.getAttribute("category");
					String merchant = "phoenixCarGallery";
					String sql = "select absolutePath from WFS_FILE where dtype='Product' and providedby = '"	+ merchant +";";

					System.out.println(sql);
					String s = SpringUtil.getBeanOfType(Dao.class).getReadOnlySession().createSQLQuery(sql).list().toString().replace("[", "").replace("]", "")	.replace(",", ";");
					try {
						JSONObject object = new JSONObject();
						object.put("type", "Files");
						object.put("entitytype", Product.class.getName());
						object.put("value", s);

						catalogue.getDescendentOfType(EXDataRepeater.class)	.setModel(new JSONTableModel(null, object));
					} catch (Exception e) {
						throw new UIException(e);
					}

					return true;
				}

				public void Success(ClientProxy container,
						Map<String, String> request) throws UIException {

				}

			}, Event.CLICK);
		}
	}*/

}
