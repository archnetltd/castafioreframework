package org.castafiore.searchengine.back;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.searchengine.MallUtil;
import org.castafiore.security.api.SecurityService;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.UIException;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;

public class OSManagerV2Impl implements OSManager {

	@Override
	public List<OSBarItem> getOSBarIcons() {
		try{
		SecurityService service = SpringUtil.getSecurityService();
		Merchant m = MallUtil.getCurrentMerchant();
		List<OSBarItem> items = m.getMyApps();
		List<OSBarItem> res = new ArrayList<OSBarItem>();
		
		if(Util.getRemoteUser().equalsIgnoreCase(Util.getLoggedOrganization())){
			return items;
		}
		for(OSBarItem item : items){
			String perm = item.getPermission();
			if(service.isUserAllowed(StringUtil.split(perm, ";"), Util.getRemoteUser())){
				res.add(item);
			}
		}
		
		return res;
		}catch(Exception e){
			throw new UIException(e);
		}
	}

}
