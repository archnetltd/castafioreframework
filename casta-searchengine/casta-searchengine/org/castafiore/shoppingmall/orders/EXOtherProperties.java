package org.castafiore.shoppingmall.orders;

import java.util.Iterator;
import java.util.Map;

import org.castafiore.shoppingmall.checkout.BillingInformation;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXLabel;

public class EXOtherProperties extends EXDynaformPanel{

	public EXOtherProperties(String name, BillingInformation billing) {
		super(name,"Other information");
		
		Map<String,String> infos = billing.getOtherProperties();
		Iterator<String> iter = infos.keySet().iterator();
		while(iter.hasNext()){
			String key = iter.next();
			String val = infos.get(key);
			addField(key, new EXLabel(key, val));
		}
		
	}

}
