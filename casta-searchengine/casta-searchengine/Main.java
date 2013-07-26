import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

import org.castafiore.designable.portal.PortalContainer;
import org.castafiore.designer.marshalling.DesignableUtil;
import org.castafiore.designer.service.DesignableService;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.utils.StringUtil;

	class dt{

	void showDetail(Container me){

		String couv =((EXSelect)me).getValue().toString();
		
		EXSelect age = (EXSelect)me.getAncestorOfType(EXDynaformPanel.class).getDescendentByName("age");
		DefaultDataModel model = new DefaultDataModel();
		if(couv.equalsIgnoreCase("familial")){
			model.addItem("0-65Ans");
		}else{
			String [] as = StringUtil.split("18-24 Ans,25-34 Ans,35-44 Ans,45-54 Ans,55-64 Ans,65-70 Ans,71-74 Ans,75 Ans+", ",");
			for(String s : as){
				model.addItem(s);
			}
		}
		
		age.setModel(model);
		
		
		
	}
	


			
}
