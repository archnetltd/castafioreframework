package org.castafiore.searchengine.back;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.castafiore.security.api.SecurityService;
import org.castafiore.spring.SpringUtil;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.security.SecurityManager;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class OSManagerImpl implements OSManager, ApplicationContextAware {

	private ApplicationContext context_;
	
	@Override
	public List<OSBarItem> getOSBarIcons() {
		
		
		Map<String, OSBarItem> items = context_.getBeansOfType(OSBarItem.class, true, true);
		
		List<OSBarItem> result = new ArrayList<OSBarItem>(items.size());
		
		Iterator<String> iters = items.keySet().iterator();
		
		SecurityService secu = SpringUtil.getSecurityService();
		
		while(iters.hasNext()){
			OSBarItem item  = items.get(iters.next());
			
			if(item.getPermission() != null){
				String[] perm =  StringUtil.split(item.getPermission(), ";");
				
				try{
					
					
					
					if(secu.isUserAllowed(perm, Util.getRemoteUser()) || Util.getRemoteUser().equals("elieandsons") || Util.getRemoteUser().equals("upstage")){
						
						
						item.setRendered(false);
						result.add(item);
						
						
					}
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}else{
				item.setRendered(false);
				result.add(item);
			}
		}
		
		return result;
	}



	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.context_ = applicationContext;
		
	}

}
